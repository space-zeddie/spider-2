package base.spider;

import base.ResultItems;
import base.output.JsonFileOutput;
import base.reader.JsonFileReader;

import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by matvii on 07.05.17.
 */
public class Tester {

    public static void main(String[] args) {

        /*Spider.create(new ResearchPageProcessor())
                .addUrl("http://nz.ukma.edu.ua/index.php?option=com_content&task=view&id=560&Itemid=47")
                .addPipeline(new JsonFileOutput())
                .thread(5)
                .run();*/

        System.out.println("/home/matvii/Jun-KMA/nz.ukma.edu.ua/".matches("([^\\s]+(\\.(?i)(json))$)"));
        JsonFileReader jsonFileReader = new JsonFileReader();
        try {
            jsonFileReader.loadFromPath("/home/matvii/Jun-KMA/nz.ukma.edu.ua/");
            Set<ResultItems> resultItemsSet = jsonFileReader.getSavedItems();
            for (ResultItems ri : resultItemsSet) {
                System.out.println(ri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
