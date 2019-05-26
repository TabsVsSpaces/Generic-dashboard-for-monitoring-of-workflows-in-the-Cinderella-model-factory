
import java.util.ArrayList;

/*
TODO: 
    - Erklärung Liste: https://docs.oracle.com/javase/1.5.0/docs/api/java/util/ArrayList.html ; http://www.codeadventurer.de/?p=1751
    - Timestamp für Fehlermeldung
 */

public class LogHandler {
    
//ArrayList notwendig für dynamisches add() von Strings
    private static ArrayList<String> FehlerLog = new ArrayList<>();

    public static void hinzufuegen(String Fehler) {
        FehlerLog.add(Fehler);
        
        //saebern der List auf 10 Elemente
        if(FehlerLog.size()>=10){
            while(FehlerLog.size()>=10){
                FehlerLog.remove(0);
            }
        }
    }
    
    public static String[] anzeigen(){
        String LogFenster [] = new String [10];
        LogFenster = FehlerLog.toArray(LogFenster);
        return LogFenster;
    }
}