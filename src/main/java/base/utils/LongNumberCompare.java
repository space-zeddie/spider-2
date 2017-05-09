package base.utils;

/**
 * Created by matvii on 11.04.17.
 */

public abstract class LongNumberCompare {

    public static int compareLong(long o1, long o2) {
        if (o1 < o2) {
            return -1;
        } else if (o1 == o2) {
            return 0;
        } else {
            return 1;
        }
    }
}
