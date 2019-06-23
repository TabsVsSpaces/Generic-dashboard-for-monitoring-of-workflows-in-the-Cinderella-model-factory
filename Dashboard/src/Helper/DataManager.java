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

    public static ObservableList<Report> loadReport() {
        final int NUMBER_ATTRIBUTES = 11;
        int i;

        ObservableList<Report> reportList = FXCollections.observableArrayList();
        BufferedReader br = null;

        try {
            FileInputStream fis = new FileInputStream(REPORT_FILE);

            br = new BufferedReader(new InputStreamReader(fis));

            String line = null;
            while ((line = br.readLine()) != null) {
                String[] tmpArray = line.split("/");

                Report report = new Report(Integer.parseInt(tmpArray[0]), tmpArray[1]);

                for (i = 2; i < tmpArray.length; i += NUMBER_ATTRIBUTES) {
                    ViewElement viewelement = new ViewElement(
                            Integer.parseInt(tmpArray[i]), tmpArray[i + 1],
                            Integer.parseInt(tmpArray[i + 2]), tmpArray[i + 3],
                            tmpArray[i + 4], tmpArray[i + 5], tmpArray[i + 6],
                            tmpArray[i + 7], tmpArray[i + 8]);

                    viewelement.setXAxisColumn(Arrays.asList(tmpArray[i + 9]));
                    viewelement.setYAxisColumn(Arrays.asList(tmpArray[i + 10]));

                    report.addViewElement(viewelement);
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
