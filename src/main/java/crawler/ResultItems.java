package crawler;

/**
 * Created by matvii on 21.04.17.
 */
import crawler.web.Request;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResultItems {

    private Map<String, Object> fields = new LinkedHashMap<String, Object>();
    private Request request;
    private boolean skip;

    public <T> T get(String key) {
        Object o = fields.get(key);
        if (o == null) {
            return null;
        }
        return (T) fields.get(key);
    }

    public Map<String, Object> getAll() {
        return fields;
    }

    public <T> ResultItems put(String key, T value) {
        fields.put(key, value);
        return this;
    }

    public Request getRequest() {
        return request;
    }

    public ResultItems setRequest(Request request) {
        this.request = request;
        return this;
    }

    public boolean isSkip() {
        return skip;
    }

    public ResultItems setSkip(boolean skip) {
        this.skip = skip;
        return this;
    }

    public boolean equalFields(ResultItems that) {
        if (this.fields.size() != that.fields.size())
            return false;
        for (String key : this.fields.keySet()) {
            if (!that.fields.keySet().contains(key))
                return false;
            if (!that.get(key).equals(this.get(key)))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResultItems{" +
                "fields=" + fields +
                ", request=" + request +
                ", skip=" + skip +
                '}';
    }
}
