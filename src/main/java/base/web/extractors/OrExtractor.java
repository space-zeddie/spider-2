package base.web.extractors;

/**
 * Created by matvii on 11.04.17.
 */
import java.util.ArrayList;
import java.util.List;

public class OrExtractor implements Extractor {

    private List<Extractor> extractors = new ArrayList<Extractor>();

    public OrExtractor(Extractor... extractors) {
        for (Extractor extractor : extractors) {
            this.extractors.add(extractor);
        }
    }

    public OrExtractor(List<Extractor> extractors) {
        this.extractors = extractors;
    }

    @Override
    public String select(String text) {
        for (Extractor extractor : extractors) {
            String result = extractor.select(text);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    @Override
    public List<String> selectList(String text) {
        List<String> results = new ArrayList<String>();
        for (Extractor extractor : extractors) {
            List<String> strings = extractor.selectList(text);
            results.addAll(strings);
        }
        return results;
    }
}
