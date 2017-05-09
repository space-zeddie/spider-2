package base.web.extractors;

/**
 * Created by matvii on 11.04.17.
 */
import org.jsoup.nodes.Element;

import java.util.List;

public interface IElementExtractor {

    String select(Element element);

    List<String> selectList(Element element);

}
