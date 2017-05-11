package crawler.output;

import crawler.spider.ResultItems;
import crawler.spider.Task;
import crawler.utils.FileUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by matvii on 07.05.17.
 */
public class JsonFileOutput extends FileUtils implements IOutput {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public JsonFileOutput() {
        setPath("/home/matvii/Jun-KMA");
    }

    public JsonFileOutput(String path) {
        setPath(path);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String path = this.path + PATH_SEPERATOR + task.getId() + PATH_SEPERATOR;
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(getFile(path + DigestUtils.md5Hex(resultItems.getRequest().getUrl()) + ".json")));
            printWriter.write(JSON.toJSONString(resultItems.getAll()));
            printWriter.close();
        } catch (IOException e) {
            logger.warn("write json file error", e);
        }
    }
}
