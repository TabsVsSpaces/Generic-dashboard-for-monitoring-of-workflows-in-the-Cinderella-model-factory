package Helper;

import java.util.*;
import java.text.*;

public class LogHandler {

    private static final String PROP_FILE = "./src/properties/Application.properties";
    private static final int LOG_SIZE = Integer.parseInt(DataManager.getProperties(PROP_FILE).getProperty("LogSize"));

    //ArrayList for dynamic add of strings
    private static final ArrayList<String> ERROR_LOG = new ArrayList<>();

    public static synchronized void add(String Fehler) {

        ERROR_LOG.add(0, getCurrDate() + "    " + Fehler);
        //prune the list to n elements (n=LOG_SIZE)     
        if (ERROR_LOG.size() > LOG_SIZE) {
            while (ERROR_LOG.size() > LOG_SIZE) {
                ERROR_LOG.remove(ERROR_LOG.size() - 1);
            }
        }
    }

    public static String[] show() {
        String LogFenster[] = new String[ERROR_LOG.size()];
        LogFenster = ERROR_LOG.toArray(LogFenster);
        return LogFenster;
    }

    private static String getCurrDate() {
        Date dNow = new Date();
        SimpleDateFormat simpleForm = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
        return simpleForm.format(dNow);
    }
}
