package base.scheduler;


import base.Task;

/**
 * Created by matvii on 11.04.17.
 */
public interface MonitorableScheduler extends Scheduler {

    int getLeftRequestsCount(Task task);

    int getTotalRequestsCount(Task task);

}