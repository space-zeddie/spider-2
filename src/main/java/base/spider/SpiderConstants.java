package base.spider;

/**
 * Created by matvii on 07.05.17.
 */
public class SpiderConstants {

    public static final int RETRY_TIMES = 3;
    public static final int SLEEP_TIME = 1000;
    public static final int TIME_OUT = 10000;

    public static final int PDF_LINK_PRIORITY = 10;
    public static final int NON_PDF_LINK_PRIORITY = 1;

    public static final String PDF_LINK_PATTERN = "([^\\s]+(\\.(?i)(pdf))$)";

}
