package base.reader;

import base.ResultItems;

import java.io.File;
import java.io.IOException;

/**
 * Reads the objects already collected by the spider
 *
 * Created by matvii on 09.05.17.
 */

public interface IReader {

    void loadFromPath(String path) throws IOException;

    ResultItems getSavedItems();

}
