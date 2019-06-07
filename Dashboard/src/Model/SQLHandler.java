/*
TODO
    -setter for map is mising
    -examineResultSet() is missing! ->extra class mit new SQLHandler and getMap()
    -possible solution for saving the resultSet http://commons.apache.org/proper/commons-beanutils/apidocs/org/apache/commons/beanutils/RowSetDynaClass.html
 */
package Model;

import Helper.*;
import java.util.*;
import java.sql.*;
import org.apache.commons.dbcp2.BasicDataSource;

public class SQLHandler {

    private static ResultSet rs = null;
    public static Map<String, List<Object>> map;

    public void queryStatement(String sqlStatement) {

        SQLHandler sqlHandler = new SQLHandler();;

        sqlHandler.getResultSet(sqlStatement);
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

    //Method to handle SQLExceptions primarily //returns an resultSet
    private void getResultSet(String sqlStatement) {

        Connection conn = null;
        Statement stmt = null;

        try {
            BasicDataSource basicDS = JDBCPool.getInstance().getBasicDS();
            conn = basicDS.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlStatement);
            resultSetToArrayList(rs);
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

    //converts the resultSet to a map of list
    private Map<String, List<Object>> resultSetToArrayList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        map = new HashMap<>(columns);
        for (int i = 1; i <= columns; ++i) {
            map.put(md.getColumnName(i), new ArrayList<>());
        }
        while (rs.next()) {
            for (int i = 1; i <= columns; ++i) {
                map.get(md.getColumnName(i)).add(rs.getObject(i));
            }
        }
        return map;
    }
}
