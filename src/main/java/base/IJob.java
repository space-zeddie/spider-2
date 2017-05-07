package base;

import base.web.Site;

/**
 * Created by matvii on 09.04.17.
 */
public interface IJob {

    /**
     * unique id for a task.
     *
     * @return uuid
     */
    String getUUID();

    /**
     * site of a task
     *
     * @return site
     */
    Site getSite();

}

