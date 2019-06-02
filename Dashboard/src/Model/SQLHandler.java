/*
TODO
    -define output!
    -examineResultSet() is missing!
 */
package Model;

import Helper.*;
import java.sql.*;
import org.apache.commons.dbcp2.BasicDataSource;

public class SQLHandler {

    public static void queryStatement(String sqlStatement) {
        SQLHandler sqlHandler = new SQLHandler();
        try {
            sqlHandler.handleStatement(sqlStatement);
        } catch (Exception e) {
            LogHandler.add(e.getMessage());
        }
    }

    private void handleStatement(String sqlStatement) {

        Connection conn = null;
        Statement stmt = null;
        try {
            BasicDataSource basicDS = JDBCPool.getInstance().getBasicDS();
            conn = basicDS.getConnection();
            stmt = conn.prepareStatement(sqlStatement);
            ResultSet rs = stmt.executeQuery(sqlStatement);
            /*
            while(rs.next()) {
            ...
            alternat.return rs?
            }
             */
            rs.close();
        } catch (SQLException se) {
            LogHandler.add(se.getMessage());
        } finally {
            try {
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
}
