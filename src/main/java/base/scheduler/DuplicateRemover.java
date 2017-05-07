package base.scheduler;


import base.Task;
import base.web.Request;

/**
 * Created by matvii on 11.04.17.
 */
public interface DuplicateRemover {

    public boolean isDuplicate(Request request, Task task);

    public void resetDuplicateCheck(Task task);

    public int getTotalRequestsCount(Task task);

}
