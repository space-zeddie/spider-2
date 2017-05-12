package crawler.reader;

import crawler.output.JsonFileOutput;
import crawler.spider.ResultItems;
import crawler.spider.Task;
import crawler.web.Site;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by matvii on 12/05/17.
 */
public class JsonDbLoader implements IDbLoader {

    private JsonFileOutput jsonFileOutput;
    private String sourcePath;
    private List<String> urls;

    public JsonDbLoader() {
        this.urls = new ArrayList<String>();
        this.sourcePath = "/home/matvii/Jun-KMA";
        this.jsonFileOutput = new JsonFileOutput();
    }

    public JsonDbLoader(String sourcePath) {
        this.urls = new ArrayList<String>();
        this.sourcePath = sourcePath;
        this.jsonFileOutput = new JsonFileOutput(sourcePath);
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
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    @Override
    public List<String> getSourceUrls() {
        return null;
    }

}
