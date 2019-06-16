/*
TODO
    -implements runnable
    -secure queryStatement(); call
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

    public SQLHandler(String sqlStatement) {
        this.sqlStatement = sqlStatement;
        this.rs = null;
        this.resultMap = null;
        this.columnTypes = null;
    }

    public Map<String, List<Object>> getResultMap() {
        queryStatement();
        return resultMap;
    }
    
    public Map<String, Integer> getColumnTypes() {
        return columnTypes;
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
    
    public ResultSet getResultSet(){
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
}