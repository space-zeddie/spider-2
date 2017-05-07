package base.scheduler.component;

import base.Task;
import base.web.Request;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by matvii on 11.04.17.
 */
public class HashSetDuplicateRemover implements DuplicateRemover {

    private Set<String> urls = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

    @Override
    public boolean isDuplicate(Request request, Task task) {
        return !urls.add(getUrl(request));
    }

    protected String getUrl(Request request) {
        return request.getUrl();
    }

    @Override
    public void resetDuplicateCheck(Task task) {
        urls.clear();
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        return urls.size();
    }
}
