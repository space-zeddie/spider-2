package crawler.reader;

import crawler.output.JsonFileOutput;
import crawler.spider.ResultItems;
import crawler.spider.Task;
import crawler.utils.FileUtils;
import crawler.web.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by matvii on 12/05/17.
 */
public class JsonDbLoader implements IDbLoader {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private JsonFileOutput jsonFileOutput;
    private String sourcePath;
    private String rootDirPath;
    private List<String> urls;

    public JsonDbLoader() {
        this.urls = new ArrayList<String>();
        this.rootDirPath = "/home/matvii/Jun-KMA";
        this.sourcePath = "";
        this.jsonFileOutput = new JsonFileOutput(rootDirPath);
    }

    public JsonDbLoader(String rootDirPath) {
        this.urls = new ArrayList<String>();
        this.rootDirPath = rootDirPath;
        this.sourcePath = "";
        this.jsonFileOutput = new JsonFileOutput(rootDirPath);
    }


    @Override
    public void addSourceURLs(String... urls) {
        ResultItems resultItems = new ResultItems();
        this.urls = new ArrayList<String>();
        for (String url : urls) {
            this.urls.add(url);
        }
        resultItems.put("list", this.urls);
        Task writeToDb = new Task() {

            @Override
            public String getId() {
                Random random = new Random();
                return "url_list_"+random.nextInt();
            }

            @Override
            public Site getSite() {
                return null;
            }
        };
        jsonFileOutput.process(resultItems, writeToDb);
        this.sourcePath = rootDirPath + FileUtils.PATH_SEPERATOR + writeToDb.getId() + FileUtils.PATH_SEPERATOR;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    @Override
    public List<String> getSourceUrls() {
        if (urls == null || urls.size() == 0)
            loadUrls();
        return urls;
    }

    private void loadUrls() {
        IReader reader = new JsonFileReader();
        urls = new ArrayList<String>();
        try {
            reader.loadFromPath(this.sourcePath);
            Set<ResultItems> resultItemss = reader.getSavedItems();
            for (ResultItems r : resultItemss) {
                urls.addAll((List<String>)r.get("list"));
            }
        } catch (IOException e) {
            logger.warn("error reading db - ", e.getMessage());
        }

    }

}
