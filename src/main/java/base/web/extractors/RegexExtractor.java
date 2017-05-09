package base.web.extractors;

/**
 * Created by matvii on 10.04.17.
 */
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexExtractor implements IExtractor {

    private String regexStr;

    private Pattern regex;

    private int group = 1;

    public RegexExtractor(String regexStr, int group) {
        if (StringUtils.isBlank(regexStr)) {
            throw new IllegalArgumentException("empty regex");
        }
        if (!hasGroup(regexStr)) {
            regexStr = "(" + regexStr + ")";
        }
        this.regexStr = regexStr;
        try {
            regex = Pattern.compile(regexStr, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("invalid regex", e);
        }
        this.group = group;
    }

    public RegexExtractor(String regexStr) {
        this(regexStr, 1);
    }

    private boolean hasGroup(String regexStr) {
        if (StringUtils.countMatches(regexStr, "(") - StringUtils.countMatches(regexStr, "\\(") ==
                StringUtils.countMatches(regexStr, "(?:") - StringUtils.countMatches(regexStr, "\\(?:")){
            return false;
        }
        if (StringUtils.countMatches(regexStr, "(") - StringUtils.countMatches(regexStr, "\\(") ==
                StringUtils.countMatches(regexStr, "(?=") - StringUtils.countMatches(regexStr, "\\(?=") ) {
            return false;
        }
        if (StringUtils.countMatches(regexStr, "(") - StringUtils.countMatches(regexStr, "\\(") ==
                StringUtils.countMatches(regexStr, "(?<") - StringUtils.countMatches(regexStr, "\\(?<") ) {
            return false;
        }
        if (StringUtils.countMatches(regexStr, "(") - StringUtils.countMatches(regexStr, "\\(") ==
                StringUtils.countMatches(regexStr, "(?!") - StringUtils.countMatches(regexStr, "\\(?!") ) {
            return false;
        }
        if (StringUtils.countMatches(regexStr, "(") - StringUtils.countMatches(regexStr, "\\(") ==
                StringUtils.countMatches(regexStr, "(?#") - StringUtils.countMatches(regexStr, "\\(?#") ) {
            return false;
        }
        return true;
    }

    @Override
    public String select(String text) {
        return selectGroup(text).get(group);
    }

    @Override
    public List<String> selectList(String text) {
        List<String> strings = new ArrayList<String>();
        List<RegexResult> results = selectGroupList(text);
        for (RegexResult result : results) {
            strings.add(result.get(group));
        }
        return strings;
    }

    public RegexResult selectGroup(String text) {
        Matcher matcher = regex.matcher(text);
        if (matcher.find()) {
            String[] groups = new String[matcher.groupCount() + 1];
            for (int i = 0; i < groups.length; i++) {
                groups[i] = matcher.group(i);
            }
            return new RegexResult(groups);
        }
        return RegexResult.EMPTY_RESULT;
    }

    public List<RegexResult> selectGroupList(String text) {
        Matcher matcher = regex.matcher(text);
        List<RegexResult> resultList = new ArrayList<RegexResult>();
        while (matcher.find()) {
            String[] groups = new String[matcher.groupCount() + 1];
            for (int i = 0; i < groups.length; i++) {
                groups[i] = matcher.group(i);
            }
            resultList.add(new RegexResult(groups));
        }
        return resultList;
    }

    @Override
    public String toString() {
        return regexStr;
    }

}
