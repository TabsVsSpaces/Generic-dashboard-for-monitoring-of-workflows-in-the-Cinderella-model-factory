import Helper.DataManager;
import Helper.LogHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import Model.*;

/**
 * FXML Controller class
 *
 * @author Tom
 */
public class DatabaseViewController implements Initializable {
    private static final String PROP_NAME = "DBconnection.properties";

    @FXML
    private AnchorPane DatabaseView;
    @FXML
    private TextField user;
    @FXML
    private TextField dbURL;
    @FXML
    private TextField password;
    @FXML
    private TextField jdbcPort;


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    private String[] getTextfield(){
    
        String[] connection = new String[4];
        
        connection[0] = jdbcPort.getText();
        connection[1] = dbURL.getText();
        connection[2] = user.getText();
        connection[3] = password.getText();
       
       
        return connection;
    }

    private void clearText(){
        
        dbURL.clear();
        jdbcPort.clear();
        user.clear();
        password.clear();
    }
    
    @FXML
    private void testConnection(MouseEvent event)throws Exception {

        String[] connection = getTextfield();

        
        if ( !connection[0].isEmpty() && !connection[1].isEmpty() && !connection[2].isEmpty() && !connection[3].isEmpty())
        {
            JDBCTest.getState(connection[0], connection[1], connection[2], connection[3]);
        }
        else {
           LogHandler.add("Bitte alle Fenster ausfüllen");
        }
    }

    @FXML
    private void setConnection(MouseEvent event) {
        
        String[] connection = getTextfield();
        DataManager.getProperties(PROP_NAME).setProperty("DB_URL", connection[0]);
        DataManager.getProperties(PROP_NAME).setProperty("JDBC_DRIVER", connection[1]);
        DataManager.getProperties(PROP_NAME).setProperty("USER", connection[2]);
        DataManager.getProperties(PROP_NAME).setProperty("PASS", connection[3]);    
    } 
}
