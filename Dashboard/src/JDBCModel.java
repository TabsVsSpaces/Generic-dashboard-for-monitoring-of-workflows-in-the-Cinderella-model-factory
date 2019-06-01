
import java.sql.*;

/*
TODO: 
    -
 */

public class JDBCModel {
    //JDBC Verbindungsobjekt
    private Connection conn;
    private Connection testConn;
    
    //JDBC Aufbau DB_URL "jdbc:[SqlTyp]://[Host]:[Port]/[DB_Name]"
    private String JDBC_Treiber;
    private String DB_URL;
    private String Username;
    private String Password;
    
    public JDBCModel(String JDBC_Treiber, String DB_URL, String Username, String Password) {
        this.conn = null;
        this.testConn = null;
        this.JDBC_Treiber = JDBC_Treiber;
        this.DB_URL = DB_URL;
        this.Username = Username;
        this.Password = Password;
    }

    public void setJDBC_Treiber(String JDBC_Treiber) {
        this.JDBC_Treiber = JDBC_Treiber;
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
            Class.forName(JDBC_Treiber);
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
            Class.forName(JDBC_Treiber);
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

    public void ladeServereinstellung() {
        //entweder hier File laden und an setter geben, oder Übergabe der 
        //Parameter bei Aufruf von JDBCModel
    }
}