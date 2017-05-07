package base.web.downloaders;

import base.Task;
import base.web.Page;
import base.web.Request;

/**
 * Created by matvii on 07.05.17.
 */
public interface IDownloader {

    public Page download(Request request, Task task);

    // Tell the IDownloader how many threads the spider used
    public void setThread(int threadNum);
}