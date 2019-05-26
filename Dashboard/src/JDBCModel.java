
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
    private String Passwort;
    
    public JDBCModel(String JDBC_Treiber, String DB_URL, String Username, String Passwort) {
        this.conn = null;
        this.testConn = null;
        this.JDBC_Treiber = JDBC_Treiber;
        this.DB_URL = DB_URL;
        this.Username = Username;
        this.Passwort = Passwort;
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

    public void setPasswort(String Passwort) {
        this.Passwort = Passwort;
    }

    public void verbinden() {
        try {
            Class.forName(JDBC_Treiber);
            conn = DriverManager.getConnection(DB_URL, Username, Passwort);
        } catch (ClassNotFoundException | SQLException ConnE) {
            LogHandler.hinzufuegen(ConnE.getMessage());
        }
    }

    public void trennen() {
        try {
            conn.close();
        } catch (SQLException se) {
            LogHandler.hinzufuegen(se.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                LogHandler.hinzufuegen(se.getMessage());
            }
        }
    }

    public boolean ermittleStatus() {
        try {
            Class.forName(JDBC_Treiber);
            testConn = DriverManager.getConnection(DB_URL);
            return true;
        } catch (ClassNotFoundException | SQLException StatusE) {
            return false;
        } finally {
            try {
                if (testConn != null) {
                    testConn.close();
                }
            } catch (SQLException se) {
                LogHandler.hinzufuegen(se.getMessage());
            }
        }
    }

    public void ladeServereinstellung() {
        //entweder hier File laden und an setter geben, oder Übergabe der 
        //Parameter bei Aufruf von JDBCModel
    }
}