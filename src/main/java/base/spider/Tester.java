package base.spider;

import base.ResultItems;
import base.output.JsonFileOutput;
import base.reader.JsonFileReader;
import base.utils.CyrillicUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Currency;
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

        String c1 = "Ключові\nслова:\n";
        String c2 = "робив сам Хопфілд, після чого відбулася низка модифікацій запропонованого ним алгоритму. \n" +
                "Тобто всі представлені у \n" +
                "статті методи є \n" +
                "модифікаціями один одного та розвивалися послідовно. \n" +
                "Також наведено деякі характеристики методів на основі мережі Хопфілда в \n" +
                "порівнянні з \n" +
                "іншими \n" +
                "(не пов’язаними з нейронними мережами) алгоритмами розв’язання CSP.\n" +
                "Ключові\n" +
                "слова:\n" +
                " CSP, задача задоволення обмежень, нейронна мережа, нейронна мережа Хопфілда.";
        try {
            String c1_utf8 = CyrillicUtils.convertToUTF8(c1);
            String c2_utf8 = CyrillicUtils.convertToUTF8(c2);
            int i1 = c2_utf8.indexOf(c1_utf8);
            int i2 = c2_utf8.indexOf(CyrillicUtils.convertToUTF8("."), i1);
            System.out.println();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

       /* System.out.println("/home/matvii/Jun-KMA/nz.ukma.edu.ua/".matches("([^\\s]+(\\.(?i)(json))$)"));
        JsonFileReader jsonFileReader = new JsonFileReader();
        try {
            jsonFileReader.loadFromPath("/home/matvii/Jun-KMA/nz.ukma.edu.ua/");
            Set<ResultItems> resultItemsSet = jsonFileReader.getSavedItems();
            for (ResultItems ri : resultItemsSet) {
                System.out.println(ri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

}
