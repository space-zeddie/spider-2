package crawler.utils;

/**
 * Created by matvii on 11.04.17.
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionUtils {

    public static <T> Set<T> newHashSet(T... t){
        Set<T> set = new HashSet<T>(t.length);
        for (T t1 : t) {
            set.add(t1);
        }
        return set;
    }

    public static <T> List<T> newArrayList(T... t){
        List<T> set = new ArrayList<T>(t.length);
        for (T t1 : t) {
            set.add(t1);
        }
        return set;
    }
}
