package base.spider;

import base.output.FileOutput;
import base.output.JsonFileOutput;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by matvii on 07.05.17.
 */
public class Tester {

    public static void main(String[] args) {

        Spider.create(new ResearchPageProcessor())
                .addUrl("http://nz.ukma.edu.ua/index.php?option=com_content&task=view&id=560&Itemid=47")
                .addPipeline(new JsonFileOutput())
                .thread(5)
                .run();

    }

}
