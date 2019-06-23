/*
Manages properties and report files

    -https://stackoverflow.com/questions/7775364/how-can-i-remove-a-substring-from-a-given-string
    -https://stackoverflow.com/questions/30413227/how-to-read-and-write-an-object-to-a-text-file-in-java
 */
package Helper;

import Model.*;
import java.io.*;
import java.util.Arrays;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataManager {

    private static final String REPORT_FILE = "./src/dataStorage/reports.txt";

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
    
    //härten gegen fehlende achsenbeschriftung
    //siehe save ->Tabelle
    public static void saveReport(ObservableList<Report> report) {
        BufferedWriter bw = null;

        try {
            File file = new File(REPORT_FILE);
            FileOutputStream fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            for (Report currReport : report) {
                bw.write(currReport.toString().replaceAll("\\[", "").replaceAll("]", "").replaceAll("/, ", "/"));
                bw.newLine();
            }
        } catch (Exception e) {
            LogHandler.add("ERROR: Reports konnten nicht gespeichter werden!");
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (Exception e) {
            }
        }
    }

    //Problem der Variablen länge
    //[ ] führen zu Problem
    public static ObservableList<Report> loadReport() {
        ObservableList<Report> reportList = FXCollections.observableArrayList();
        BufferedReader br = null;
        int i;

        try {
            FileInputStream fis = new FileInputStream(REPORT_FILE);

            br = new BufferedReader(new InputStreamReader(fis));

            String line = null;
            while ((line = br.readLine()) != null) {
                String[] array = line.split("/");

                Report report = new Report(Integer.parseInt(array[0]), array[1]);
                System.out.println(report.toString());

                for (i = 2; i < array.length; i += 11) {
                    System.out.println(array.length);
                    System.out.println("i start: " + i);
                    ViewElement viewelement = new ViewElement(
                            Integer.parseInt(array[i]), array[i + 1],
                            Integer.parseInt(array[i + 2]), array[i + 3],
                            array[i + 4], array[i + 5], array[i + 6],
                            array[i + 7], array[i + 8]);

                    viewelement.setXAxisColumn(Arrays.asList(array[i + 9]));
                    viewelement.setYAxisColumn(Arrays.asList(array[i + 10]));

                    report.addViewElement(viewelement);
                    System.out.println(viewelement.toString());
                }
                reportList.add(report);
            }
            return reportList;

        } catch (Exception e) {
            LogHandler.add("ERROR: Reports konnten nicht geladen werden!");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
            }
        }
        return null;
    }
}
