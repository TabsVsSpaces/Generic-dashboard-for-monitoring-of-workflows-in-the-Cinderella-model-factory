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

    private static final String XML_FILE = "/Dashboard/dataStorage/reports.xml";
    private Report report;
    private ViewElement viewElement;
    
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

    public static void saveReport(Report report, ViewElement viewElement) {
        
        XStream xstream = new XStream();
        xstream.alias("report", Report.class);
        xstream.alias("viewElement", ViewElement.class);
        
        try{
            FileOutputStream fs = new FileOutputStream(XML_FILE);
            xstream.toXML(report, fs);
        }
        catch (FileNotFoundException e) {
            LogHandler.add("ERROR: Report konnte nicht gespeichert werden!");
        }
        
    }
    //what is the input? reportId? load all?
    public Report loadReport() {
        XStream xstream = new XStream(new DomDriver());
        Report newReport = new Report();
        
        try{
            FileInputStream fis = new FileInputStream(XML_FILE);
            xstream.fromXML(fis, newReport);
            return newReport;
        }    
        catch (FileNotFoundException e) {
            LogHandler.add("ERROR: Report konnte nicht geladen werden!");
        }
        return newReport;
    }

    public void deleteReport() {

    }
}
