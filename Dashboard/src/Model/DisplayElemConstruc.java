/*
TODO
    -examineResultSet() is missing! 
*/
package Model;

import Helper.LogHandler;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DisplayElemConstruc {
    
    private Map<String, List<Object>> map;
    
    //returns a HashMap, which contains the resultSet content
    public void createElement (String sqlStatement) {

        SQLHandler sqlHandler = new SQLHandler(sqlStatement);
        map = sqlHandler.getResultMap();
        //sqlHandler.queryStatement(sqlStatement);
        if (map != null) {
            try {
                Iterator<Map.Entry<String, List<Object>>> entries = map.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<String, List<Object>> entry = entries.next();
                    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                }
                System.out.println("Erfolg");
            } catch (Exception se) {
                LogHandler.add(se.getMessage());
            }
        } else {
            System.out.println("Fehler");
        }
    }
}
