
package Helper;

import java.util.ArrayList;

/*
TODO: 
    - Erklärung Liste: https://docs.oracle.com/javase/1.5.0/docs/api/java/util/ArrayList.html ; http://www.codeadventurer.de/?p=1751
    - Timestamp für Fehlermeldung
 */

public class LogHandler {
    
//ArrayList notwendig für dynamisches add() von Strings
    private static ArrayList<String> ErrorLog = new ArrayList<>();

    public static void add(String Fehler) {
        ErrorLog.add(Fehler);
        
        //saeubern der List auf 10 Elemente
        if(ErrorLog.size()>=10){
            while(ErrorLog.size()>=10){
                ErrorLog.remove(0);
            }
        }
    }
    
    public static String[] show(){
        String LogFenster [] = new String [10];
        LogFenster = ErrorLog.toArray(LogFenster);
        return LogFenster;
    }
}