/*

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
    private Connection conn = null;
    private Statement stmt = null;

    public SQLHandler(String sqlStatement) {
        this.sqlStatement = sqlStatement;
        this.rs = null;
        this.resultMap = null;
        this.columns = null;
        this.columnTypes = null;
        this.SQLDataTypes = new int[]{-6, -5, 2, 3, 4, 5, 6, 7, 8};
        this.conn = null;
        this.stmt = null;
        queryStatement();
    }

    public Map<String, List<Object>> getResultMap() {
        return resultMap;
    }

    //only temporary!
    public ResultSet getResultSet() {

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

    public void close() {
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
