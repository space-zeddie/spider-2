package crawler.utils;

/**
 * Created by matvii on 11.04.17.
 */

import crawler.web.Request;
import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtils {

    private static final Pattern patternForCharset = Pattern.compile("charset\\s*=\\s*['\"]*([^\\s;'\"]*)");

    public static String canonicalizeUrl(String url, String refer) {
        URL base;
        try {
            try {
                base = new URL(refer);
            } catch (MalformedURLException e) {
                URL abs = new URL(refer);
                return abs.toExternalForm();
            }
            if (url.startsWith("?"))
                url = base.getPath() + url;
            URL abs = new URL(base, url);
            return encodeIllegalCharacterInUrl(abs.toExternalForm());
        } catch (MalformedURLException e) {
            return "";
        }
    }


    public static String encodeIllegalCharacterInUrl(String url) {
        return url.replace(" ", "%20");
    }

    public static String getHost(String url) {
        String host = url;
        int i = StringUtils.ordinalIndexOf(url, "/", 3);
        if (i > 0) {
            host = StringUtils.substring(url, 0, i);
        }
        return host;
    }

    private static Pattern patternForProtocal = Pattern.compile("[\\w]+://");

    public static String removeProtocol(String url) {
        return patternForProtocal.matcher(url).replaceAll("");
    }

    public static String getDomain(String url) {
        String domain = removeProtocol(url);
        int i = StringUtils.indexOf(domain, "/", 1);
        if (i > 0) {
            domain = StringUtils.substring(domain, 0, i);
        }
        return removePort(domain);
    }

    public static String removePort(String domain) {
        int portIndex = domain.indexOf(":");
        if (portIndex != -1) {
            return domain.substring(0, portIndex);
        }else {
            return domain;
        }
    }

    public static List<Request> convertToRequests(Collection<String> urls) {
        List<Request> requestList = new ArrayList<Request>(urls.size());
        for (String url : urls) {
            requestList.add(new Request(url));
        }
        return requestList;
    }

    public static List<String> convertToUrls(Collection<Request> requests) {
        List<String> urlList = new ArrayList<String>(requests.size());
        for (Request request : requests) {
            urlList.add(request.getUrl());
        }
        return urlList;
    }

    public static String getCharset(String contentType) {
        Matcher matcher = patternForCharset.matcher(contentType);
        if (matcher.find()) {
            String charset = matcher.group(1);
            if (Charset.isSupported(charset)) {
                return charset;
            }
        }
        return null;
    }

}
