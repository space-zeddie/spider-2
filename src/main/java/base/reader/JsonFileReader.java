package base.reader;

import base.ResultItems;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by matvii on 09.05.17.
 */
public class JsonFileReader implements IReader {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private Set<ResultItems> savedItems = new HashSet<ResultItems>();
    private Set<String> checkedFilePaths = new HashSet<String>();


    public JsonFileReader() {}

    @Override
    public void loadFromPath(String path) throws IOException {
        File dir = new File(path);
        for (final File file : dir.listFiles()) {
            if (file.isDirectory())
                loadFromPath(file.getAbsolutePath());
            else {
                if (file.getName().matches("([^\\s]+(\\.(?i)(json))$)")
                        && !checkedFilePaths.contains(file.getAbsolutePath())) {
                    String json = readAllLines(path);
                    ResultItems resultItems = JSON.parseObject(json, ResultItems.class);
                    savedItems.add(resultItems);
                    checkedFilePaths.add(file.getAbsolutePath());
                }
            }
        }
    }

    private String readAllLines(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(path));
        try {
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
        } finally {
            br.close();
        }
        return sb.toString();
    }

    @Override
    public Set<ResultItems> getSavedItems() {
        return savedItems;
    }


}
