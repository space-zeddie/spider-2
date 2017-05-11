package crawler.output;

import java.util.List;

/**
 * IOutput that can collect and store results.
 *
 * Created by matvii on 11.04.17.
 */
public interface ICollectOutput<T> extends IOutput {

    List<T> getCollected();
}
