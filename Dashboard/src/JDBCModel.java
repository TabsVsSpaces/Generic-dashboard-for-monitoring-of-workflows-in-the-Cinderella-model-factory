
import java.sql.*;

/*
TODO: 
    -printStackTrace() ersetzen durch ordentliches Logging ->entspricht sendeLog()
    -Connection conn ordentlich definieren
 */

/**
 *
 * @author Ben
 */
public class JDBCModel implements LogHandler{

    //JDBC Aufbau DB_URL "jdbc:[SqlTyp]://[Host]:[Port]/[DB_Name]"
    private String JDBC_Treiber;
    private String DB_URL;
    private String Username;
    private String Passwort;

    public JDBCModel(String JDBC_Treiber, String DB_URL, String Username, String Passwort) {
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

    Connection conn = null;//wie ordentlich deklarieren?

    public void verbinden() {
        try {
            Class.forName(JDBC_Treiber);
            conn = DriverManager.getConnection(DB_URL, Username, Passwort);
        } catch (ClassNotFoundException | SQLException ConnE) {
            ConnE.printStackTrace();
        }
    }

    public void trennen() {
        try {
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    
    //alternativ als public boolean
    public void ermittleStatus(){
        Connection testConn = null;
        try{
            Class.forName(JDBC_Treiber);
            testConn = DriverManager.getConnection(DB_URL);
        }catch(ClassNotFoundException | SQLException StatusE){
        StatusE.printStackTrace();//hier fehlt noch die Rückgabe des Status
        }finally {
            try {
                if (testConn != null) {
                    testConn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    
    public void ladeServereinstellung(){
        //entweder hier File laden und an setter geben, oder Übergabe der 
        //Parameter bei Aufruf von JDBCModel
    }
}