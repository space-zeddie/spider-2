package crawler.web.downloaders;

/**
 * Created by matvii on 07.05.17.
 */

import crawler.spider.Task;
import crawler.web.Page;
import crawler.web.Request;

public interface IDownloader {

    public Page download(Request request, Task task);

    // Tell the downloader how many threads the spider uses
    public void setThread(int threadNum);
}