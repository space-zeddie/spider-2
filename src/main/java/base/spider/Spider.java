package base.spider;

/**
 * Created by matvii on 13.04.17.
 */

import base.CountableThreadPool;
import base.IPageProcessor;
import base.Task;
import base.output.ConsoleOutput;
import base.output.ICollectOutput;
import base.output.IOutput;
import base.output.ResultItemsCollectOutput;
import base.reader.IReader;
import base.reader.JsonFileReader;
import base.scheduler.IScheduler;
import base.scheduler.QueueScheduler;
import base.utils.UrlUtils;
import base.utils.CollectionUtils;
import base.web.Page;
import base.web.Request;
import base.web.Site;
import base.web.downloaders.HTTPClientDownloader;
import base.web.downloaders.IDownloader;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Spider implements Runnable, Task {

    protected IDownloader downloader;
    protected List<IOutput> pipelines = new ArrayList<IOutput>();
    protected IReader reader = new JsonFileReader();
    protected IPageProcessor pageProcessor;
    protected List<Request> startRequests;
    protected Site site;
    protected String id;
    protected IScheduler scheduler = new QueueScheduler();
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected CountableThreadPool threadPool;
    protected ExecutorService executorService;
    protected int threadNum = 1;
    protected AtomicInteger stat = new AtomicInteger(STAT_INIT);
    protected boolean exitWhenComplete = true;
    protected final static int STAT_INIT = 0;
    protected final static int STAT_RUNNING = 1;
    protected final static int STAT_STOPPED = 2;
    protected boolean spawnUrl = true;
    protected boolean destroyWhenExit = true;
    private ReentrantLock newUrlLock = new ReentrantLock();
    private Condition newUrlCondition = newUrlLock.newCondition();
    private List<ISpiderListener> spiderListeners;
    private final AtomicLong pageCount = new AtomicLong(0);
    private Date startTime;
    private int emptySleepTime = 30000;

    public static Spider create(IPageProcessor pageProcessor) {
        return new Spider(pageProcessor);
    }

    public Spider(IPageProcessor pageProcessor) {
        this.pageProcessor = pageProcessor;
        this.site = pageProcessor.getSite();
    }


    public Spider startUrls(List<String> startUrls) {
        checkIfRunning();
        this.startRequests = UrlUtils.convertToRequests(startUrls);
        return this;
    }

    public Spider startRequest(List<Request> startRequests) {
        checkIfRunning();
        this.startRequests = startRequests;
        return this;
    }

    public Spider setId(String id) {
        this.id = id;
        return this;
    }

    public Spider scheduler(IScheduler scheduler) {
        return setScheduler(scheduler);
    }

    public Spider setScheduler(IScheduler scheduler) {
        checkIfRunning();
        IScheduler oldScheduler = this.scheduler;
        this.scheduler = scheduler;
        if (oldScheduler != null) {
            Request request;
            while ((request = oldScheduler.poll(this)) != null) {
                this.scheduler.push(request, this);
            }
        }
        return this;
    }

    public Spider pipeline(IOutput pipeline) {
        return addPipeline(pipeline);
    }

    public Spider addPipeline(IOutput pipeline) {
        checkIfRunning();
        this.pipelines.add(pipeline);
        return this;
    }

    public Spider setPipelines(List<IOutput> pipelines) {
        checkIfRunning();
        this.pipelines = pipelines;
        return this;
    }

    public Spider clearPipeline() {
        pipelines = new ArrayList<IOutput>();
        return this;
    }

    public Spider downloader(IDownloader downloader) {
        return setDownloader(downloader);
    }

    public Spider setDownloader(IDownloader downloader) {
        checkIfRunning();
        this.downloader = downloader;
        return this;
    }

    protected void initComponent() {
        if (downloader == null) {
            this.downloader = new HTTPClientDownloader();
        }
        if (pipelines.isEmpty()) {
            pipelines.add(new ConsoleOutput());
        }
        downloader.setThread(threadNum);
        if (threadPool == null || threadPool.isShutdown()) {
            if (executorService != null && !executorService.isShutdown()) {
                threadPool = new CountableThreadPool(threadNum, executorService);
            } else {
                threadPool = new CountableThreadPool(threadNum);
            }
        }
        if (startRequests != null) {
            for (Request request : startRequests) {
                addRequest(request);
            }
            startRequests.clear();
        }
        startTime = new Date();
    }

    @Override
    public void run() {
        checkRunningStat();
        initComponent();
        logger.info("Spider " + getId() + " started!");
        while (!Thread.currentThread().isInterrupted() && stat.get() == STAT_RUNNING) {
            final Request request = scheduler.poll(this);
            if (request == null) {
                if (threadPool.getThreadAlive() == 0 && exitWhenComplete) {
                    break;
                }
                // wait until new url added
                waitNewUrl();
            } else {
                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            processRequest(request);
                            onSuccess(request);
                        } catch (Exception e) {
                            onError(request);
                            logger.error("process request " + request + " error", e);
                        } finally {
                            pageCount.incrementAndGet();
                            signalNewUrl();
                        }
                    }
                });
            }
        }
        stat.set(STAT_STOPPED);
        // release resources
        if (destroyWhenExit) {
            close();
        }
    }

    protected void onError(Request request) {
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(spiderListeners)) {
            for (ISpiderListener spiderListener : spiderListeners) {
                spiderListener.onError(request);
            }
        }
    }

    protected void onSuccess(Request request) {
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(spiderListeners)) {
            for (ISpiderListener spiderListener : spiderListeners) {
                spiderListener.onSuccess(request);
            }
        }
    }

    private void checkRunningStat() {
        while (true) {
            int statNow = stat.get();
            if (statNow == STAT_RUNNING) {
                throw new IllegalStateException("Spider is already running!");
            }
            if (stat.compareAndSet(statNow, STAT_RUNNING)) {
                break;
            }
        }
    }

    public void close() {
        destroyEach(downloader);
        destroyEach(pageProcessor);
        destroyEach(scheduler);
        for (IOutput pipeline : pipelines) {
            destroyEach(pipeline);
        }
        threadPool.shutdown();
    }

    private void destroyEach(Object object) {
        if (object instanceof Closeable) {
            try {
                ((Closeable) object).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // process specific urls without url discovering.
    public void test(String... urls) {
        initComponent();
        if (urls.length > 0) {
            for (String url : urls) {
                processRequest(new Request(url));
            }
        }
    }

    private void processRequest(Request request) {
        Page page = downloader.download(request, this);
        if (page.isDownloadSuccess()){
            onDownloadSuccess(request, page);
        } else {
            onDownloaderFail(request);
        }
    }

    private void onDownloadSuccess(Request request, Page page) {
        onSuccess(request);
        if (site.getAcceptStatCode().contains(page.getStatus())){
            pageProcessor.process(page);
            extractAndAddRequests(page, spawnUrl);
            if (!page.getResultItems().isSkip()) {
                for (IOutput pipeline : pipelines) {
                    pipeline.process(page.getResultItems(), this);
                }
            }
        }
        sleep(site.getSleepTime());
        return;
    }

    private void onDownloaderFail(Request request) {
        if (site.getCycleRetryTimes() == 0) {
            sleep(site.getSleepTime());
        } else {
            // for cycle retry
            doCycleRetry(request);
        }
        onError(request);
    }

    private void doCycleRetry(Request request) {
        Object cycleTriedTimesObject = request.getExtra(Request.CYCLE_TRIED_TIMES);
        if (cycleTriedTimesObject == null) {
            addRequest(SerializationUtils.clone(request).setPriority(0).putExtra(Request.CYCLE_TRIED_TIMES, 1));
        } else {
            int cycleTriedTimes = (Integer) cycleTriedTimesObject;
            cycleTriedTimes++;
            if (cycleTriedTimes < site.getCycleRetryTimes()) {
                addRequest(SerializationUtils.clone(request).setPriority(0).putExtra(Request.CYCLE_TRIED_TIMES, cycleTriedTimes));
            }
        }
        sleep(site.getRetrySleepTime());
    }

    protected void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            logger.error("Thread interrupted when sleep",e);
        }
    }

    protected void extractAndAddRequests(Page page, boolean spawnUrl) {
        if (spawnUrl && org.apache.commons.collections.CollectionUtils.isNotEmpty(page.getTargetRequests())) {
            for (Request request : page.getTargetRequests()) {
                addRequest(request);
            }
        }
    }

    private void addRequest(Request request) {
        if (site.getDomain() == null && request != null && request.getUrl() != null) {
            site.setDomain(UrlUtils.getDomain(request.getUrl()));
        }
        scheduler.push(request, this);
    }

    protected void checkIfRunning() {
        if (stat.get() == STAT_RUNNING) {
            throw new IllegalStateException("Spider is already running!");
        }
    }

    public void runAsync() {
        Thread thread = new Thread(this);
        thread.setDaemon(false);
        thread.start();
    }

    public Spider addUrl(String... urls) {
        for (String url : urls) {
            addRequest(new Request(url));
        }
        signalNewUrl();
        return this;
    }

    public <T> List<T> getAll(Collection<String> urls) {
        destroyWhenExit = false;
        spawnUrl = false;
        if (startRequests!=null){
            startRequests.clear();
        }
        for (Request request : UrlUtils.convertToRequests(urls)) {
            addRequest(request);
        }
        ICollectOutput<T> collectorPipeline = getCollectorPipeline();
        pipelines.add(collectorPipeline);
        run();
        spawnUrl = true;
        destroyWhenExit = true;
        return collectorPipeline.getCollected();
    }

    protected ICollectOutput getCollectorPipeline() {
        return new ResultItemsCollectOutput();
    }

    public <T> T get(String url) {
        List<String> urls = CollectionUtils.newArrayList(url);
        List<T> resultItemses = getAll(urls);
        if (resultItemses != null && resultItemses.size() > 0) {
            return resultItemses.get(0);
        } else {
            return null;
        }
    }

    public Spider addRequest(Request... requests) {
        for (Request request : requests) {
            addRequest(request);
        }
        signalNewUrl();
        return this;
    }

    private void waitNewUrl() {
        newUrlLock.lock();
        try {
            //double check
            if (threadPool.getThreadAlive() == 0 && exitWhenComplete) {
                return;
            }
            newUrlCondition.await(emptySleepTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            logger.warn("waitNewUrl interrupted, error {}", e);
        } finally {
            newUrlLock.unlock();
        }
    }

    private void signalNewUrl() {
        try {
            newUrlLock.lock();
            newUrlCondition.signalAll();
        } finally {
            newUrlLock.unlock();
        }
    }

    public void start() {
        runAsync();
    }

    public void stop() {
        if (stat.compareAndSet(STAT_RUNNING, STAT_STOPPED)) {
            logger.info("Spider " + getId() + " stop success!");
        } else {
            logger.info("Spider " + getId() + " stop fail!");
        }
    }

    public Spider thread(int threadNum) {
        checkIfRunning();
        this.threadNum = threadNum;
        if (threadNum <= 0) {
            throw new IllegalArgumentException("threadNum should be more than one!");
        }
        return this;
    }

    public Spider thread(ExecutorService executorService, int threadNum) {
        checkIfRunning();
        this.threadNum = threadNum;
        if (threadNum <= 0) {
            throw new IllegalArgumentException("threadNum should be more than one!");
        }
        this.executorService = executorService;
        return this;
    }

    public boolean isExitWhenComplete() {
        return exitWhenComplete;
    }


    public Spider setExitWhenComplete(boolean exitWhenComplete) {
        this.exitWhenComplete = exitWhenComplete;
        return this;
    }

    public boolean isSpawnUrl() {
        return spawnUrl;
    }

    public long getPageCount() {
        return pageCount.get();
    }


    public Status getStatus() {
        return Status.fromValue(stat.get());
    }


    public enum Status {
        Init(0), Running(1), Stopped(2);

        private Status(int value) {
            this.value = value;
        }

        private int value;

        int getValue() {
            return value;
        }

        public static Status fromValue(int value) {
            for (Status status : Status.values()) {
                if (status.getValue() == value) {
                    return status;
                }
            }
            //default value
            return Init;
        }
    }

    public int getThreadAlive() {
        if (threadPool == null) {
            return 0;
        }
        return threadPool.getThreadAlive();
    }

    public Spider setSpawnUrl(boolean spawnUrl) {
        this.spawnUrl = spawnUrl;
        return this;
    }

    @Override
    public String getId() {
        if (id != null) {
            return id;
        }
        if (site != null) {
            return site.getDomain();
        }
        id = UUID.randomUUID().toString();
        return id;
    }

    public Spider setExecutorService(ExecutorService executorService) {
        checkIfRunning();
        this.executorService = executorService;
        return this;
    }

    @Override
    public Site getSite() {
        return site;
    }

    public List<ISpiderListener> getSpiderListeners() {
        return spiderListeners;
    }

    public Spider setSpiderListeners(List<ISpiderListener> spiderListeners) {
        this.spiderListeners = spiderListeners;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public IScheduler getScheduler() {
        return scheduler;
    }

    public void setEmptySleepTime(int emptySleepTime) {
        this.emptySleepTime = emptySleepTime;
    }
}

