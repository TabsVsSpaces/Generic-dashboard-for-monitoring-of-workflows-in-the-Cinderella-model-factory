/*
Manages properties and report files

TODO
    -Properties encryption
 */
package Helper;

import java.io.*;
import java.util.Properties;

public class DataManager {

    public static Properties getPropFile(String PropertiesFile) {
        
        Properties props = new Properties();
        try {
            InputStream input = DataManager.class.getClassLoader().getResourceAsStream(PropertiesFile);
            props.load(input);
            input.close();
        } catch (FileNotFoundException ex) {
            LogHandler.add(PropertiesFile + " nicht gefunden!");
        } catch (IOException ex) {
            LogHandler.add(ex.getMessage());
        }
        return props;
    }
    //setProperties()

    //saveReport()
    //loadReport()
    //deleteReport()
}
