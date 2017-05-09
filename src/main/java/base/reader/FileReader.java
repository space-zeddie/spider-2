package base.reader;

import base.ResultItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by matvii on 09.05.17.
 */
public class FileReader implements IReader {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private Set<ResultItems> savedItems = new HashSet<ResultItems>();
    private Set<String> checkedFilePaths = new HashSet<String>();

    public FileReader() {}

    @Override
    public void processFile(File file) {

    }

    @Override
    public ResultItems getSavedItems() {
        return null;
    }
}
