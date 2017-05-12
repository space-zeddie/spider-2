package crawler.reader;

import java.util.List;

/**
 * Created by matvii on 12/05/17.
 */
public interface IDbLoader {

    void addSourceFile(String path);

    List<String> getSourcePaths();

}
