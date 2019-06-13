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

    private SQLHandler sqlHandler;
    private Map<String, List<Object>> resultMap;
    private String[] columns;
    private double[] XValues;
    private String[] YValues;

    public DisplayElemConstruc(String sqlStatement) {
        this.sqlHandler = new SQLHandler(sqlStatement);
        this.resultMap = sqlHandler.getResultMap();
        this.columns = null;
    }

    public String[] getColumns() {
        if (resultMap != null) {
            columns = resultMap.keySet().toArray(new String[0]);
        } else {
            LogHandler.add("ERROR: Map is null!");
        }
        return columns;
    }

    public double[] getXValues(String XColumn) {
        if (resultMap != null) {
            try {
                String[] temp = resultMap.get(XColumn).toArray(new String[0]);
                for (int i = 0; i < temp.length; i++) {
                    XValues[i] = Double.parseDouble(temp[i]);
                }
            } catch (NumberFormatException e) {
                LogHandler.add("ERROR: X-Werte in "+XColumn+" sind nicht numerisch!");
            }
        } else {
            LogHandler.add("ERROR: Map is null!");
        }
        return XValues;
    }

    public String[] getYValues(String YColumn) {
        if (resultMap != null) {
            try {
                YValues = resultMap.get(YColumn).toArray(new String[0]);
            } catch (Exception e) {
                LogHandler.add(e.getMessage());
            }
        } else {
            LogHandler.add("ERROR: Map is null!");
        }
        return YValues;
    }
/*
    private void examineResultMap() {
            Iterator<Map.Entry<String, List<Object>>> entries = resultMap.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, List<Object>> entry = entries.next();
                //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            }
        //System.out.println(Arrays.toString(columns));
    }

    private void createElement() {

    }
*/
}
