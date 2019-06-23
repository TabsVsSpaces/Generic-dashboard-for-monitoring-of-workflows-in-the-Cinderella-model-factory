/*
Class for testing JDBC configurations
 */
package Model;

import Helper.*;
import java.sql.*;
import org.apache.commons.dbcp2.BasicDataSource;

public class JDBCTest {

    public static void testConn(String JDBC_Driver, String DB_URL, String User, String Password) {

        Connection testConn = null;

        try {
            Class.forName(JDBC_Driver);
            testConn = DriverManager.getConnection(DB_URL, User, Password);
            LogHandler.add("Verbindungstest erfolgreich.");
        } catch (ClassNotFoundException | SQLException se) {
            LogHandler.add("Verbindungstest fehlgeschlagen!");
        } finally {
            try {
                if (testConn != null) {
                    testConn.close();
                }
            } catch (SQLException se) {
            }
        }
    }

    public static boolean getState() {

        Connection poolConn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            BasicDataSource basicDS = JDBCPool.getInstance().getBasicDS();
            poolConn = basicDS.getConnection();
            stmt = poolConn.createStatement();
            rs = stmt.executeQuery("select 1");
            return true;
        } catch (SQLException e) {
            //LogHandler.add("ERROR: Datenbankverbindung verloren!");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (stmt != null) {
                    stmt.close();
                }

                if (poolConn != null) {
                    poolConn.close();
                }
            } catch (SQLException e) {
            }
        }
        return false;
    }
}
