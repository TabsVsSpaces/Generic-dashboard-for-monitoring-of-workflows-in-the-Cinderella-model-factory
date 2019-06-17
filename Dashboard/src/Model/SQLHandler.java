/*
TODO
    -implements runnable
    -secure queryStatement(); call
    -https://alvinalexander.com/java/edu/pj/jdbc/recipes/ResultSet-ColumnType.shtml
    -grouping by missing 
        ->möglich über collectors https://www.mkyong.com/java8/java-8-collectors-groupingby-and-mapping-example/
         select sum(L.pieces) as pieces, L.route from lot L, ptime P where L.state='WAIT' AND L.route=P.route AND L.oper=P.oper AND L.product=p.product group by L.pieces, L.route order by pieces desc;
 */
package Model;

import Helper.*;
import java.util.*;
import java.sql.*;
import org.apache.commons.dbcp2.BasicDataSource;

public class SQLHandler {

    private final String sqlStatement;
    private ResultSet rs;
    private Map<String, List<Object>> resultMap;
    private Map<String, Integer> columnTypes;
    private final int[] SQLDataTypes;
    private String[] columns;

    public SQLHandler(String sqlStatement) {
        this.sqlStatement = sqlStatement;
        this.rs = null;
        this.resultMap = null;
        this.columns = null;
        this.columnTypes = null;
        this.SQLDataTypes = new int[]{-6, -5, 2, 3, 4, 5, 6, 7, 8};
        queryStatement();
    }
    //only temporary!
    public ResultSet getResultSet() {
        Connection conn = null;
        Statement stmt = null;

        try {
            BasicDataSource basicDS = JDBCPool.getInstance().getBasicDS();
            conn = basicDS.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlStatement);
        } catch (SQLException se) {
            LogHandler.add(se.getMessage());
        } 
        return rs;
    }

    public Map<String, List<Object>> getResultMap() {
        return resultMap;
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
    //needs to be reworked with group by functionality
    public List<Object> getValues(String ColumnName) {
        if (resultMap.isEmpty()) {
            return null;
        }
        for (int i = 0; i <= resultMap.size(); ++i) {
            if (resultMap.containsKey(ColumnName)) {
                return resultMap.get(ColumnName);
            } else {
                LogHandler.add("ERROR: ColumnTypesMap is null!");
            }
        }
        return null;
    }
    
    //Query SQLStatement + SQLException handling
    private void queryStatement() {

        Connection conn = null;
        Statement stmt = null;

        try {
            BasicDataSource basicDS = JDBCPool.getInstance().getBasicDS();
            conn = basicDS.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlStatement);
            resultSetToArrayList();
        } catch (SQLException se) {
            LogHandler.add(se.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                LogHandler.add(se.getMessage());
            }
        }
    }

    //converts the resultSet to a resultMap of list
    private void resultSetToArrayList() {
        try {
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            resultMap = new HashMap<>(columns);
            columnTypes = new HashMap<>(columns);
            for (int i = 1; i <= columns; ++i) {
                resultMap.put(md.getColumnName(i), new ArrayList<>());
                //columnTypes.put(md.getColumnName(i), 0);
            }
            while (rs.next()) {
                for (int i = 1; i <= columns; ++i) {
                    //add values
                    resultMap.get(md.getColumnName(i)).add(rs.getObject(i));
                    //add dataTypes
                    columnTypes.put(md.getColumnName(i), md.getColumnType(i));
                }
            }
        } catch (SQLException se) {
            LogHandler.add(se.getMessage());
        }
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
