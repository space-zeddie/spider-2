package base.output;

import base.ResultItems;
import base.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matvii on 11.04.17.
 */
public class ResultItemsCollectOutput implements CollectOutput<ResultItems> {

    private List<ResultItems> collector = new ArrayList<ResultItems>();

    @Override
    public synchronized void process(ResultItems resultItems, Task task) {
        collector.add(resultItems);
    }

    @Override
    public List<ResultItems> getCollected() {
        return collector;
    }
}
