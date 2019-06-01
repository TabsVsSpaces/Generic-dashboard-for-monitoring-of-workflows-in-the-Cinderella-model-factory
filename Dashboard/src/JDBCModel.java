
import java.sql.*;

/*
TODO: 
    -loadConnectionInfo () is not required due to instantiation
    -Pooling der Verbindung?
 */

public class JDBCModel {
    //JDBC Verbindungsobjekt
    private Connection conn;
    private Connection testConn;
    
    //JDBC Aufbau DB_URL "jdbc:[SqlTyp]://[Host]:[Port]/[DB_Name]"
    private String JDBC_Driver;
    private String DB_URL;
    private String Username;
    private String Password;
    
    public JDBCModel(String JDBC_Driver, String DB_URL, String Username, String Password) {
        this.conn = null;
        this.testConn = null;
        this.JDBC_Driver = JDBC_Driver;
        this.DB_URL = DB_URL;
        this.Username = Username;
        this.Password = Password;
    }

    public void setJDBC_Driver(String JDBC_Driver) {
        this.JDBC_Driver = JDBC_Driver;
    }

    public void setDB_URL(String DB_URL) {
        this.DB_URL = DB_URL;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public void setPasswort(String Password) {
        this.Password = Password;
    }

    public void connect() {
        try {
            Class.forName(JDBC_Driver);
            conn = DriverManager.getConnection(DB_URL, Username, Password);
        } catch (ClassNotFoundException | SQLException ConnE) {
            LogHandler.add(ConnE.getMessage());
        }
    }

    public void disconnect() {
        try {
            conn.close();
        } catch (SQLException se) {
            LogHandler.add(se.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                LogHandler.add(se.getMessage());
            }
        }
    }

    public boolean getState() {
        try {
            Class.forName(JDBC_Driver);
            testConn = DriverManager.getConnection(DB_URL);
            return true;
        } catch (ClassNotFoundException | SQLException se) {
            return false;
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