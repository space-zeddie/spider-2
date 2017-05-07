package base.spider;

/**
 * Created by matvii on 07.05.17.
 */
public class Tester {

    public static void main(String[] args) {
        Spider.create(new ResearchPageProcessor())
                .addUrl("http://nz.ukma.edu.ua/index.php?option=com_content&task=view&id=560&Itemid=47")
                .thread(5)
                .run();
    }

}
