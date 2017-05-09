package base.web.downloaders;

/**
 * Created by matvii on 07.05.17.
 */

import base.Task;
import base.web.Page;
import base.web.Request;

public interface IDownloader {

    public Page download(Request request, Task task);

    // Tell the downloader how many threads the spider uses
    public void setThread(int threadNum);
}