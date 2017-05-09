package base.reader;

import base.ResultItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by matvii on 09.05.17.
 */
public class FileReader implements IReader {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void processFile(File file) {

    }

    @Override
    public ResultItems getSavedItems() {
        return null;
    }
}
