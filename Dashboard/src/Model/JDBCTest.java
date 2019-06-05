/*
Class for testing JDBC configurations
 */
package Model;

import Helper.*;
import java.sql.*;

public class JDBCTest {

    public static void getState(String JDBC_Driver, String DB_URL, String User, String Password) {

        Connection testConn = null;
        
        try {
            Class.forName(JDBC_Driver);
            testConn = DriverManager.getConnection(DB_URL,User, Password);
            LogHandler.add("Verbindungstest erfolgreich.");
        } catch (ClassNotFoundException | SQLException se) {
            LogHandler.add("Verbindungstest fehlgeschlagen!");
        } finally {
            try {
                if (testConn != null) {
                    testConn.close();
                }
            } catch (SQLException se) {
                LogHandler.add(se.getMessage());
            }
        }
    }
}
