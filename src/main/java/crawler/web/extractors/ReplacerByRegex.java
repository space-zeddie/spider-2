package crawler.web.extractors;

/**
 * Created by matvii on 10.04.17.
 */
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ReplacerByRegex implements IExtractor {

    private String regexStr;

    private String replacement;

    private Pattern regex;

    public ReplacerByRegex(String regexStr, String replacement) {
        this.regexStr = regexStr;
        this.replacement = replacement;
        try {
            regex = Pattern.compile(regexStr);
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("invalid regex", e);
        }
    }

    @Override
    public String select(String text) {
        Matcher matcher = regex.matcher(text);
        return matcher.replaceAll(replacement);
    }

    @Override
    public List<String> selectList(String text) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return regexStr + "_" + replacement;
    }

}
