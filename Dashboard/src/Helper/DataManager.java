/*
Manages properties and report files

TODO
    -Properties encryption
    -https://www.tutorialspoint.com/java/lang/system_setproperty.htm
    -https://www.tutorialspoint.com/xstream/
    -http://x-stream.github.io/tutorial.html
    -https://dom4j.github.io/
    -dom4j?
 */
package Helper;

import Model.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.*;
import javax.xml.bind.*;
import java.util.Properties;

public class DataManager {

    private static final String XML_FILE = "./src/dataStorage/reports.xml";
    private Report report;
    private ViewElement viewElement;

    public static Properties getProperties(String propertyFile) {
        Properties prop = new Properties();

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(propertyFile)))) {
            prop.load(bis);
            bis.close();
        } catch (Exception e) {
            LogHandler.add("ERROR: Properties konnten nicht geladen werden!");
        }
        return prop;
    }

    public static void setProperties(String propertyFile, String propertyName, String propertyValue) {
        Properties prop = getProperties(propertyFile);
        
        prop.setProperty(propertyName, propertyValue);
        
        try (BufferedOutputStream bis = new BufferedOutputStream(new FileOutputStream(new File(propertyFile)))) {
            prop.store(bis, null);
        } catch (Exception e) {
            LogHandler.add("ERROR: Properties konnten nicht gespeichert werden!");
        }
    }
    
    //call in ReportController ->add viewElement() //input reportList in MainController?
    public static void saveReportToXML(Report report) {

        XStream xstream = new XStream();
        xstream.alias("report", Report.class);
        xstream.alias("viewElement", ViewElement.class);

        try {
            FileOutputStream fs = new FileOutputStream(XML_FILE);
            xstream.toXML(report, fs);
        } catch (FileNotFoundException e) {
            LogHandler.add("ERROR: Report konnte nicht gespeichert werden!");
        }

    }

    //load all 
    public Report loadReportFromXML() {
        XStream xstream = new XStream(new DomDriver());
        Report newReport = new Report();

        try {
            FileInputStream fis = new FileInputStream(XML_FILE);
            xstream.fromXML(fis, newReport);
            return newReport;
        } catch (FileNotFoundException e) {
            LogHandler.add("ERROR: Report konnte nicht geladen werden!");
        }
        return newReport;
    }

    public void deleteReport() {

    }
}
