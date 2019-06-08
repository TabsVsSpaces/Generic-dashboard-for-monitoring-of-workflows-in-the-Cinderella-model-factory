/*
TODO
    -implements runnable
    -possible solution for saving the resultSet http://commons.apache.org/proper/commons-beanutils/apidocs/org/apache/commons/beanutils/RowSetDynaClass.html
 */
package Model;

import Helper.*;
import java.util.*;
import java.sql.*;
import org.apache.commons.dbcp2.BasicDataSource;

public class SQLHandler {

    private final String sqlStatement;
    private ResultSet rs;
    private Map<String, List<Object>> map;

    public SQLHandler(String sqlStatement) {
        this.sqlStatement = sqlStatement;
        this.rs = null;
        this.map = null;
    }

    public Map<String, List<Object>> getResultMap() {
        queryStatement();
        return map;
    }

    //Query SQLStatement + SQLException handling
    private void queryStatement() {

        Connection conn = null;
        Statement stmt = null;

        try {
            BasicDataSource basicDS = JDBCPool.getInstance().getBasicDS();
            conn = basicDS.getConnection();
            stmt = conn.createStatement();
            this.rs = stmt.executeQuery(sqlStatement);
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

    //converts the resultSet to a map of list
    private void resultSetToArrayList() {
        try {
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            this.map = new HashMap<>(columns);
            for (int i = 1; i <= columns; ++i) {
                this.map.put(md.getColumnName(i), new ArrayList<>());
            }
            while (rs.next()) {
                for (int i = 1; i <= columns; ++i) {
                    this.map.get(md.getColumnName(i)).add(rs.getObject(i));
                }
            }
        } catch (SQLException se) {
            LogHandler.add(se.getMessage());
        }
    }
}
