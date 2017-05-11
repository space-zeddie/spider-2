package crawler.spider.scheduler;

/**
 * Created by matvii on 11.04.17.
 */

import crawler.spider.Task;
import crawler.utils.HTTPConsts;
import crawler.web.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DuplicateRemovedScheduler implements IScheduler {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private IDuplicateRemover duplicatedRemover = new HashSetDuplicateRemover();
    public IDuplicateRemover getDuplicateRemover() {
        return duplicatedRemover;
    }
    public DuplicateRemovedScheduler setDuplicateRemover(IDuplicateRemover duplicatedRemover) {
        this.duplicatedRemover = duplicatedRemover;
        return this;
    }

    @Override
    public void push(Request request, Task task) {
        logger.trace("get a candidate url {}", request.getUrl());
        if (shouldReserved(request) || noNeedToRemoveDuplicate(request) || !duplicatedRemover.isDuplicate(request, task)) {
            logger.debug("push to queue {}", request.getUrl());
            pushWhenNoDuplicate(request, task);
        }
    }

    protected boolean shouldReserved(Request request) {
        return request.getExtra(Request.CYCLE_TRIED_TIMES) != null;
    }

    protected boolean noNeedToRemoveDuplicate(Request request) {
        return HTTPConsts.Method.POST.equalsIgnoreCase(request.getMethod());
    }

    protected void pushWhenNoDuplicate(Request request, Task task) {
    // TO DO ???

    }
}
