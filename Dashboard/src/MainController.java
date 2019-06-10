

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
import Helper.*;
import Model.Report;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ToggleButton;


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

    protected ListProperty<String> listProperty = new SimpleListProperty<>();
    
    @FXML
    private ListView<Report> ListViewReports;
    @FXML
    private ListView Log;
    @FXML
    private ToggleButton ToggleStatus;
    
    private ObservableList<Report> reportList = FXCollections.observableArrayList();
    
    private Report newReport;

    @Override
    public void initialize(URL url, ResourceBundle rb)  {
      
      Report tempRep = new Report();
      tempRep.setReportName("Default Report 1");
      tempRep.setReportId(1);
      
      reportList.add(tempRep);
      
      tempRep = new Report();
      tempRep.setReportName("Default Report 2");
      tempRep.setReportId(2);
      reportList.add(tempRep);
      
      tempRep = new Report();
      tempRep.setReportName("Default Report 3");
      tempRep.setReportId(3);
      reportList.add(tempRep);
      
      ListViewReports.setItems(reportList);
      ListViewReports.setCellFactory(param -> new ListCell<Report>() {
            @Override
            protected void updateItem(Report item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getReportName() == null) {
                    setText(null);
                } else {
                    setText(item.getReportName());
                }
            }
        });
      
      /*
      // Register Change in selected Report used for determining which report should be displayed and which is choosen with the change option
      ListViewReports.getSelectionModel().selectedItemProperty().addListener((InvalidationListener) new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, 
                    String old_val, String new_val) {
                        LogHandler.add(new_val);
                        //Get Report Num
            }
        });
        */
      Log.itemsProperty().bind(listProperty);
      LogHandler.add("Clicked");
      listProperty.set(FXCollections.observableArrayList(LogHandler.show()));
      
      Runnable runnable = new Runnable() {
      public void run() {
          
     // myLog.itemsProperty().bind(listProperty);
      listProperty.set(FXCollections.observableArrayList(LogHandler.show()));
      }
    };
    
    ScheduledExecutorService service = Executors
                    .newSingleThreadScheduledExecutor();
    service.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.SECONDS);

    }    

    @FXML
    private void deleteReport(MouseEvent event) throws Exception{
        Report removeReport = ListViewReports.getSelectionModel().getSelectedItem();
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Report löschen bestätigen Dialog");
        alert.setHeaderText("Sie sind dabei den Report mit dem Namen: " + 
                removeReport.getReportName() + " zu löschen.");
        alert.setContentText("Wollen Sie den Report wirklich löschen?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            deleteReport(removeReport);
        } 
    }

    @FXML
    private void changeReport(MouseEvent event) throws Exception{
        loadReportView(ListViewReports.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void addReport(MouseEvent event) throws Exception{
        loadReportView(newReport = new Report());
    }

    @FXML
    private void databaseConnect(MouseEvent event) throws Exception {
       
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DatabaseView.fxml"));
        Parent root = loader.load();
        ReportController databasecon = loader.getController();
    
        PaneView.getChildren().addAll(root);
       
    }

    public void loadReportView(Report report) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Report.fxml"));
        Parent root = loader.load();
        ReportController repcon = loader.getController();
        
        //LogHandler.add(String.valueOf(report.getListElement().size()));
        
        repcon.SetMainControleller(this);
        repcon.setReport(report);
        repcon.loadViewElements();
        
        PaneView.getChildren().addAll(root);
    }
    
    public void SaveReport(Report toSaveReport)
    {
        if(toSaveReport.getReportId() == 0)
        {
            toSaveReport.setReportId(createReportID());
            LogHandler.add("Neuer Report wurde hinzugefügt.");
            reportList.add(toSaveReport);
        } else {
            LogHandler.add("Report wurde aktualisiert.");
        }
                
    }
    
        private int createReportID(){
        int id = 1;
        boolean search=true;
        
        while(search){ 
            for (int i = 0 ; i < reportList.size(); i++) {
                if (reportList.get(i).getReportId()== id) {
                    id++;
                    break;
                };
            }
            
            search=false;
        }

        return id;
    }
  
    private void deleteReport(Report report){
        for (int i = 0 ; i < reportList.size(); i++) {
                if (reportList.get(i).getReportId()== report.getReportId()) {
                    reportList.remove(i);
                    LogHandler.add("Report " + report.getReportName() + " wurde gelöscht.");
                };
        }
    }
}

    

