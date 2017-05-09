package base.scheduler;

/**
 * Created by matvii on 11.04.17.
 */

import base.Task;

public interface IMonitorableScheduler extends IScheduler {

    int getLeftRequestsCount(Task task);

    int getTotalRequestsCount(Task task);

}