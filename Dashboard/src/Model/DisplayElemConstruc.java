/*
TODO
    -creatElement is not implemented yet
    -setter for selected Columns, which should be used displayed in the element
    -https://www.baeldung.com/convert-map-values-to-array-list-set
    -https://www.techiedelight.com/convert-map-array-java/
 */
package Model;

import Helper.LogHandler;
import java.util.*;

public class DisplayElemConstruc {

    private final String sqlStatement;
    private Map<String, List<Object>> map;
    private String[] columns;

    public DisplayElemConstruc(String sqlStatement) {
        this.sqlStatement = sqlStatement;
        this.map = null;
        this.columns = null;
    }
    
    public String[] getColumns(){
        examineResultMap();
        return columns;
    }

    private void examineResultMap() {

        SQLHandler sqlHandler = new SQLHandler(sqlStatement);
        map = sqlHandler.getResultMap();

        if (map != null) {
            this.columns = map.keySet().toArray(new String[0]);
            /*
            Iterator<Map.Entry<String, List<Object>>> entries = map.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, List<Object>> entry = entries.next();
                //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            }*/
            //System.out.println(Arrays.toString(columns));

        } else {
            LogHandler.add("ERROR: Map is null!");
        }
    }

    private void createElement() {

    }
}
