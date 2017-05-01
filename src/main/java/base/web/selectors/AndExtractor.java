package base.web.selectors;

/**
 * Created by matvii on 11.04.17.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * All extractors will be arranged as a output. <br>
 * The next selector uses the result of the previous as source.
 */
public class AndExtractor implements Extractor {

    private List<Extractor> extractors = new ArrayList<Extractor>();

    public AndExtractor(Extractor... extractors) {
        for (Extractor extractor : extractors) {
            this.extractors.add(extractor);
        }
    }

    public AndExtractor(List<Extractor> extractors) {
        this.extractors = extractors;
    }

    @Override
    public String select(String text) {
        for (Extractor extractor : extractors) {
            if (text == null) {
                return null;
            }
            text = extractor.select(text);
        }
        return text;
    }

    @Override
    public List<String> selectList(String text) {
        List<String> results = new ArrayList<String>();
        boolean first = true;
        for (Extractor extractor : extractors) {
            if (first) {
                results = extractor.selectList(text);
                first = false;
            } else {
                List<String> resultsTemp = new ArrayList<String>();
                for (String result : results) {
                    resultsTemp.addAll(extractor.selectList(result));
                }
                results = resultsTemp;
                if (results == null || results.size() == 0) {
                    return results;
                }
            }
        }
        return results;
    }
}
