import base.PageProcessor;
import base.web.Page;
import base.web.Site;

/**
 * Created by matvii on 21.04.17.
 */
public class Tester {

    public static void main(String[] args) {
        System.out.println("Hello world!");
        PageProcessor pageProcessor = new PageProcessor() {
            @Override
            public void process(Page page) {

            }

            @Override
            public Site getSite() {
                return null;
            }
        };
    }
}
