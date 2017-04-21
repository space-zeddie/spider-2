package base.web;

/**
 * Created by matvii on 21.04.17.
 */
import base.web.selectors.JsonPathExtractor;
import base.web.selectors.PlainText;
import base.web.selectors.Extractable;
import com.alibaba.fastjson.JSON;
import us.codecraft.xsoup.XTokenQueue;

import java.util.List;

public class Json extends PlainText {

    public Json(List<String> strings) {
        super(strings);
    }

    public Json(String text) {
        super(text);
    }

    /**
     * remove padding for JSONP
     * @param padding padding
     * @return json after padding removed
     */
    public Json removePadding(String padding) {
        String text = getFirstSourceText();
        XTokenQueue tokenQueue = new XTokenQueue(text);
        tokenQueue.consumeWhitespace();
        tokenQueue.consume(padding);
        tokenQueue.consumeWhitespace();
        String chompBalanced = tokenQueue.chompBalancedNotInQuotes('(', ')');
        return new Json(chompBalanced);
    }

    public <T> T toObject(Class<T> clazz) {
        if (getFirstSourceText() == null) {
            return null;
        }
        return JSON.parseObject(getFirstSourceText(), clazz);
    }

    public <T> List<T> toList(Class<T> clazz) {
        if (getFirstSourceText() == null) {
            return null;
        }
        return JSON.parseArray(getFirstSourceText(), clazz);
    }

    @Override
    public Extractable jsonPath(String jsonPath) {
        JsonPathExtractor jsonPathExtractor = new JsonPathExtractor(jsonPath);
        return selectList(jsonPathExtractor,getSourceTexts());
    }
}
