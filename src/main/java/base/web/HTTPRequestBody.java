package base.web;

/**
 * Created by matvii on 10.04.17.
 */
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HTTPRequestBody {

    public static abstract class ContentType {

        public static final String JSON = "application/json";

        public static final String XML = "text/xml";

        public static final String FORM = "application/x-www-form-urlencoded";

        public static final String MULTIPART = "multipart/form-data";
    }

    private final byte[] body;

    private final String contentType;

    private final String encoding;

    public HTTPRequestBody(byte[] body, String contentType, String encoding) {
        this.body = body;
        this.contentType = contentType;
        this.encoding = encoding;
    }

    public String getContentType() {
        return contentType;
    }

    public String getEncoding() {
        return encoding;
    }

    public static HTTPRequestBody json(String json, String encoding) throws UnsupportedEncodingException {
        return new HTTPRequestBody(json.getBytes(encoding), ContentType.JSON, encoding);
    }

    public static HTTPRequestBody xml(String xml, String encoding) throws UnsupportedEncodingException {
        return new HTTPRequestBody(xml.getBytes(encoding), ContentType.XML, encoding);
    }

    public static HTTPRequestBody custom(byte[] body, String contentType, String encoding) throws UnsupportedEncodingException {
        return new HTTPRequestBody(body, contentType, encoding);
    }

    public static HTTPRequestBody form(Map<String,Object> params, String encoding) throws UnsupportedEncodingException {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(params.size());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
        }
        return new HTTPRequestBody(URLEncodedUtils.format(nameValuePairs, encoding).getBytes(encoding), ContentType.FORM, encoding);
    }

    public byte[] getBody() {
        return body;
    }
}
