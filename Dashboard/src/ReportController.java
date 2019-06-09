/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Tom
 */
public class ReportController implements Initializable {

    @FXML
    private AnchorPane Report;
    @FXML
    private TextField Reportname;
    @FXML
    private ListView<String> ListViewReport;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addViewElement(MouseEvent event)throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AddViewElement.fxml"));  
        Report.getChildren().addAll(root);
    }

    @FXML
    private void addReport(MouseEvent event) {
    }
    
}
