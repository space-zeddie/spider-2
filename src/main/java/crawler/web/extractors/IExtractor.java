package crawler.web.extractors;

/**
 * Created by matvii on 10.04.17.
 */
import java.util.List;

public interface IExtractor {

    String select(String text);

    List<String> selectList(String text);

}
