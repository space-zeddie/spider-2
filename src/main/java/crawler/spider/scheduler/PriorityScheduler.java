package crawler.spider.scheduler;

/**
 * Created by matvii on 11.04.17.
 */

import crawler.spider.Task;
import crawler.utils.LongNumberCompare;
import crawler.web.Request;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityScheduler extends DuplicateRemovedScheduler implements IMonitorableScheduler {

    public static final int INITIAL_CAPACITY = 5;
    private BlockingQueue<Request> noPriorityQueue = new LinkedBlockingQueue<Request>();

    private PriorityBlockingQueue<Request> priorityQueuePlus = new PriorityBlockingQueue<Request>(INITIAL_CAPACITY, new Comparator<Request>() {
        @Override
        public int compare(Request o1, Request o2) {
            return -LongNumberCompare.compareLong(o1.getPriority(), o2.getPriority());
        }
    });

    private PriorityBlockingQueue<Request> priorityQueueMinus = new PriorityBlockingQueue<Request>(INITIAL_CAPACITY, new Comparator<Request>() {
        @Override
        public int compare(Request o1, Request o2) {
            return -LongNumberCompare.compareLong(o1.getPriority(), o2.getPriority());
        }
    });

    @Override
    public void pushWhenNoDuplicate(Request request, Task task) {
        if (request.getPriority() == 0) {
            noPriorityQueue.add(request);
        } else if (request.getPriority() > 0) {
            priorityQueuePlus.put(request);
        } else {
            priorityQueueMinus.put(request);
        }
    }

    @Override
    public synchronized Request poll(Task task) {
        Request poll = priorityQueuePlus.poll();
        if (poll != null) {
            return poll;
        }
        poll = noPriorityQueue.poll();
        if (poll != null) {
            return poll;
        }
        return priorityQueueMinus.poll();
    }

    @Override
    public int getLeftRequestsCount(Task task) {
        return noPriorityQueue.size();
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        return getDuplicateRemover().getTotalRequestsCount(task);
    }
}
