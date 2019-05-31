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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Tom
 */
public class MainController implements Initializable {

    @FXML
    private AnchorPane MainView;
    @FXML
    private Pane PaneView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void deleteReport(MouseEvent event) throws Exception{
    }

    @FXML
    private void changeReport(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ChangeReport.fxml"));
        PaneView.getChildren().addAll(root);
    }

    @FXML
    private void addReport(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("AddReport.fxml"));
        PaneView.getChildren().addAll(root);
    }

    @FXML
    private void databaseConnect(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("DatabaseView.fxml"));
        PaneView.getChildren().addAll(root);
    }
    
}
