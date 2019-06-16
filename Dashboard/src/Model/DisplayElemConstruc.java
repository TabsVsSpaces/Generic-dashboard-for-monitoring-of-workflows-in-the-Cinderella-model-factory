/*
TODO
    -https://alvinalexander.com/java/edu/pj/jdbc/recipes/ResultSet-ColumnType.shtml
    -grouping by missing 
        ->möglich über collectors https://www.mkyong.com/java8/java-8-collectors-groupingby-and-mapping-example/
    select sum(L.pieces) as pieces, L.route from lot L, ptime P where L.state='WAIT' AND L.route=P.route AND L.oper=P.oper AND L.product=p.product group by L.pieces, L.route order by pieces desc;
 */
package Model;

import Helper.LogHandler;
import java.util.*;

public class DisplayElemConstruc {

    private final SQLHandler sqlHandler;
    private final Map<String, List<Object>> resultMap;
    private final Map<String, Integer> columnTypes;
    private final int[] SQLDataTypes;
    private String[] columns;

    public DisplayElemConstruc(String sqlStatement) {
        this.sqlHandler = new SQLHandler(sqlStatement);
        this.resultMap = sqlHandler.getResultMap();
        this.columnTypes = sqlHandler.getColumnTypes();
        this.SQLDataTypes = new int[]{-6, -5, 2, 3, 4, 5, 6, 7, 8};
        this.columns = null;
    }

    public String[] getColumns() {
        if (resultMap != null) {
            columns = resultMap.keySet().toArray(new String[0]);
        } else {
            LogHandler.add("ERROR: ResultMap is null!");
        }
        return columns;
    }

    public boolean isColumnNumeric(String column) {
        if (columnTypes != null) {
            return contains(SQLDataTypes, columnTypes.get(column));
        } else {
            LogHandler.add("ERROR: ColumnTypesMap is null!");
        }
        return false;
    }
    
    public List<Object> getValues(String ColumnName){
        if (resultMap.isEmpty()) return null;
        for (int i = 0; i <= resultMap.size(); ++i) {
            if (resultMap.containsKey(ColumnName)){
                return resultMap.get(ColumnName);
            } else {
                 LogHandler.add("ERROR: ColumnTypesMap is null!");
            }   
        }
        return null;
    }

    private boolean contains(final int[] array, final int v) {
        boolean result = false;
        for (int i : array) {
            if (i == v) {
                result = true;
                break;
            }
        }
        return result;
    }
}
