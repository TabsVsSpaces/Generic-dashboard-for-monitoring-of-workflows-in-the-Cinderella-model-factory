
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
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Tom
 */
public class DatabaseViewController implements Initializable {

    private static final String PROP_NAME = "./src/properties/DBconnection.properties";

    @FXML
    private AnchorPane DatabaseView;
    @FXML
    private TextField user;
    @FXML
    private TextField dbURL;
    @FXML
    private TextField password;
    @FXML
    private TextField jdbcDriver;
    
    private MainController MainC;
    @FXML
    private Button saveDBCon;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        jdbcDriver.setText(DataManager.getProperties(PROP_NAME).getProperty("JDBC_DRIVER"));
        dbURL.setText(DataManager.getProperties(PROP_NAME).getProperty("DB_URL"));
        user.setText(DataManager.getProperties(PROP_NAME).getProperty("USER"));
        password.setText(DataManager.getProperties(PROP_NAME).getProperty("PASS"));
        saveDBCon.setDisable(true);

    }
    
    public void SetMainControleller(MainController Main)
    {
        MainC = Main;
    }
    
    private String[] getTextfield() {

        String[] connection = new String[4];

        connection[0] = jdbcDriver.getText();
        connection[1] = dbURL.getText();
        connection[2] = user.getText();
        connection[3] = password.getText();

        return connection;
    }

    //really necessary?
    private void clearText() {

        dbURL.clear();
        jdbcDriver.clear();
        user.clear();
        password.clear();
    }

    @FXML
    private void testConnection(MouseEvent event) throws Exception {

        String[] connection = getTextfield();

        if (!connection[0].isEmpty() && !connection[1].isEmpty() && !connection[2].isEmpty() && !connection[3].isEmpty()) {
            JDBCTest.getState(connection[0], connection[1], connection[2], connection[3]);
            saveDBCon.setDisable(false);
        } else {
            LogHandler.add("Bitte alle Fenster ausfüllen");
        }
    }

    @FXML
    private void setConnection(MouseEvent event) {

        String[] connection = getTextfield();
        DataManager.setProperties(PROP_NAME, "JDBC_DRIVER", connection[0]);
        DataManager.setProperties(PROP_NAME, "DB_URL", connection[1]);
        DataManager.setProperties(PROP_NAME, "USER", connection[2]);
        DataManager.setProperties(PROP_NAME, "PASS", connection[3]);
        LogHandler.add("Servereinstellungen gespeichert.");
        MainC.loadReprot();
    }
}
