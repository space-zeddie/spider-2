package base.utils;

import java.io.UnsupportedEncodingException;

/**
 * Created by matvii on 10.05.17.
 */
public class CyrillicUtils {

    public static String ISO_8859_1 = "ISO-8859-1";
    public static String UTF_8 = "UTF-8";

    public static String convertToUTF8(String cyrillic) throws UnsupportedEncodingException {
        byte bytes[] = cyrillic.getBytes(ISO_8859_1);
        String value = new String(bytes, UTF_8);
        return value;
    }
}
