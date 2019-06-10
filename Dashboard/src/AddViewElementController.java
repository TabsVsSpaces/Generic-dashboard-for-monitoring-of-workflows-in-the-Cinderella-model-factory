/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Helper.LogHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import Model.*;
import java.util.List;
import java.util.Map;

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
    private TextField SQLStatement;
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

    private Map<String, List<Object>> map;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private String getText(TextField target)
    {
        return target.getText();
    }

    

    @FXML
    private void testSQL(MouseEvent event) {
        
        SQLHandler handler = new SQLHandler(getText(SQLStatement));
        map = handler.getResultMap();
        
        LogHandler.add("Statment Korrekt");
    }

    @FXML
    private void genChart(MouseEvent event) {
    }

    @FXML
    private void cancel(MouseEvent event) {
    }

    @FXML
    private void addViewElement(MouseEvent event) {
    }
    
}
