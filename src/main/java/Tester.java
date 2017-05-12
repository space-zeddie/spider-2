import crawler.output.JsonFileOutput;
import crawler.reader.IDbLoader;
import crawler.reader.JsonDbLoader;
import crawler.spider.ResearchPageProcessor;
import crawler.spider.Spider;

/**
 * Created by matvii on 07.05.17.
 */
public class Tester {

    public static void main(String[] args) {

        IDbLoader dbLoader = new JsonDbLoader();
        dbLoader.addSourceURLs("http://nz.ukma.edu.ua/index.php?option=com_content&task=view&id=560&Itemid=47",
                "http://nz.ukma.edu.ua/index.php?option=com_content&task=view&id=197&Itemid=31");

        Spider.create(new ResearchPageProcessor())
                .setDbLoader(dbLoader)
                .addPipeline(new JsonFileOutput())
                .thread(5)
                .run();

        /*String c1 = "Ключові\nслова:\n";
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
            int i1 = c2_utf8.indexOf(c1_utf8) + c1_utf8.length();
            int i2 = c2_utf8.indexOf(CyrillicUtils.convertToUTF8("."), i1);
            System.out.println(c2_utf8.substring(i1, i2));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/

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
