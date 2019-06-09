/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Tom
 */
public class AddViewElementController implements Initializable {

    @FXML
    private AnchorPane AddReport;
    @FXML
    private TextField Diagrammname;
    @FXML
    private ComboBox<?> Aktualisierungsrate;
    @FXML
    private TextArea SQLStatement;
    @FXML
    private ComboBox<?> Diagrammtyp;
    @FXML
    private ListView<?> X_Achse;
    @FXML
    private TextField NameX;
    @FXML
    private TextField MaßeinheitX;
    @FXML
    private ListView<?> Y_Achse;
    @FXML
    private TextField NameY;
    @FXML
    private TextField MaßeinheitY;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
