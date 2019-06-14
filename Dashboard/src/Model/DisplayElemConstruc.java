/*
TODO
    -creatElement is not implemented yet
    -setter for selected Columns, which should be used displayed in the element
    -https://alvinalexander.com/java/edu/pj/jdbc/recipes/ResultSet-ColumnType.shtml
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
    
    public List<Object> getValues(String ColumnName){
        if (resultMap.isEmpty()) return null;
        for (int i = 0; i <= resultMap.size(); ++i) {
            if (resultMap.containsKey(ColumnName)){
                return resultMap.get(ColumnName);
            } else {
                //loghandler
            }   
            
        }
        return null;
    }
}
