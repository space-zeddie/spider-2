package crawler.output;

import crawler.spider.ResultItems;
import crawler.spider.Task;

import java.util.Map;

/**
 * Created by matvii on 11.04.17.
 */
public class ConsoleOutput implements IOutput {

    @Override
    public void process(ResultItems resultItems, Task task) {
        System.out.println("retrieving page: " + resultItems.getRequest().getUrl());
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println(entry.getKey() + ":\t" + entry.getValue());
        }
    }
}
