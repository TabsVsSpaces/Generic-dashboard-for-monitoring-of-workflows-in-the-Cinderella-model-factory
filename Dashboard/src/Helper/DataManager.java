/*
Manages properties and report files

TODO
    -Properties encryption
 */
package Helper;

import java.io.*;
import java.util.Properties;

public class DataManager {

    public static Properties getPropFile(String PropertyName) {
        
        Properties props = new Properties();
        try {
            InputStream input = DataManager.class.getClassLoader().getResourceAsStream(PropertyName);
            props.load(input);
            input.close();
        } catch (FileNotFoundException ex) {
            LogHandler.add(PropertyName + " nicht gefunden!");
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
