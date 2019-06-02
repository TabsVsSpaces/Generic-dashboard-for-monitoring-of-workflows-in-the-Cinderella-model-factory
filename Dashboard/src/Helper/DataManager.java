/*
Manages properties and report files

TODO
    -Properties encryption
    -https://www.tutorialspoint.com/java/lang/system_setproperty.htm
 */
package Helper;

import java.io.*;
import javax.xml.bind.*;
import java.util.Properties;

public class DataManager {

    public static Properties getProperties(String PropertyName) {

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

    public void saveReport() {
        ReportObject reportObj = new ReportObject();
        reportObj.setReportName("");
        reportObj.setUpdateRate(0);
        reportObj.setSqlStatement("");
        reportObj.setMeasureXAxis("");
        reportObj.setMeasureYAxis("");
        reportObj.setColumnXAxis("");
        reportObj.setColumnYAxis("");
    }

    public void loadReport() {

    }

    public void deleteReport() {

    }
}
