package crawler.spider.scheduler;

/**
 * Created by matvii on 11.04.17.
 */

import crawler.spider.Task;

public interface IMonitorableScheduler extends IScheduler {

    int getLeftRequestsCount(Task task);

    int getTotalRequestsCount(Task task);

}