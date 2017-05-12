package crawler.reader;

import java.util.List;

/**
 * Created by matvii on 12/05/17.
 */
public interface IDbLoader {

    void addSourceURLs(String... urls);

    List<String> getSourceUrls();

}