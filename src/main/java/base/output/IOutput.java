package base.output;

import base.ResultItems;
import base.Task;

/**
 * IOutput is the persistent and offline process part of crawler
 * The interface IOutput can be implemented to customize ways of persistent.
 *
 * Created by matvii on 11.04.17.
 */

public interface IOutput {

    void process(ResultItems resultItems, Task task);
}
