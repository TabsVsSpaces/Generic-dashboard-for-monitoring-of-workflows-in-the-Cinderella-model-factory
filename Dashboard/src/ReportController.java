/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Helper.LogHandler;
import Model.Report;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
    
    private Report report;

    private MainController MainC;
    /**
     * Initializes the controller class.
     */
    public static final ObservableList viewElements = 
        FXCollections.observableArrayList();    // populiert ListView mit Anzeige Elementen
    
    private int reportID;
    
    @FXML
    private ListView<String> ListViewElement;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // get ID from current Report
        
        
        ListViewElement.setItems(viewElements);
        
            ListViewElement.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, 
                    String old_val, String new_val) {
                        LogHandler.add(new_val);
                        
            }
        });
        
    }

     

    @FXML
    private void addViewElement(MouseEvent event)throws Exception {
        
        AnchorPane pane = FXMLLoader.load(getClass().getResource("AddViewElement.fxml"));
        Report.getChildren().setAll(pane);   
    }

    @FXML
    private void addReport(MouseEvent event) {
        
        report.setReportName(Reportname.getText());
        //to -do Addview Elements 
        MainC.SaveReport(report);
        LogHandler.add("Report gespeichert");
    }

    @FXML
    private void deleteViewElement(MouseEvent event) {
    }

    @FXML
    private void changeViewElement(MouseEvent event) {
    }
    
    public void setReport(Report report)
    {
        this.report = report;
    }
    
    public void SetMainControleller(MainController Main)
    {
        MainC = Main;
    }
}
