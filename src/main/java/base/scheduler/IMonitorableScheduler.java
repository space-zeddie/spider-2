package base.scheduler;


import base.Task;

/**
 * Created by matvii on 11.04.17.
 */
public interface IMonitorableScheduler extends IScheduler {

    int getLeftRequestsCount(Task task);

    int getTotalRequestsCount(Task task);

}