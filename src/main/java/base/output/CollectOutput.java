package base.output;

import java.util.List;

/**
 * Output that can collect and store results.
 *
 * Created by matvii on 11.04.17.
 */
public interface CollectOutput<T> extends Output {

    /**
     * Get all results collected.
     *
     * @return collected results
     */
    List<T> getCollected();
}
