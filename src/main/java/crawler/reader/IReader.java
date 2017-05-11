package crawler.reader;

import crawler.spider.ResultItems;

import java.io.IOException;
import java.util.Set;

/**
 * Reads the objects already collected by the spider
 *
 * Created by matvii on 09.05.17.
 */

public interface IReader {

    void loadFromPath(String path) throws IOException;

    Set<ResultItems> getSavedItems();

}
