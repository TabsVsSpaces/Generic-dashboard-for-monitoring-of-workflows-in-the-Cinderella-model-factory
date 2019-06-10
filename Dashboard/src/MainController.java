

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
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
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
    
    public static final ObservableList reportName = 
        FXCollections.observableArrayList();
    
    @FXML
    private ListView ListViewReports;
    @FXML
    private ListView Log;
    @FXML
    private ToggleButton ToggleStatus;
    
    
    private List<Report> reportList = new ArrayList<>();
    
    private Report newReport;

    @Override
    public void initialize(URL url, ResourceBundle rb)  {
      
      Report tempRep = new Report();
      tempRep.setReportName("Default Report");
      tempRep.setReportId(1);
      
      reportName.add(tempRep.getReportName()+" ID: " + Integer.toString(tempRep.getReportId()));
      ListViewReports.setItems(reportName);
       
      // Register Change in selected Report used for determining which report should be displayed and which is choosen with the change option
      ListViewReports.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, 
                    String old_val, String new_val) {
                        LogHandler.add(new_val);
                        //Get Report Num
            }
        });
        
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
    }

    @FXML
    private void changeReport(MouseEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddViewElement.fxml"));
        Parent root = loader.load();
        ReportController viewcon = loader.getController();
        
        

        PaneView.getChildren().addAll(root);
    }

    @FXML
    private void addReport(MouseEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Report.fxml"));
        Parent root = loader.load();
        ReportController repcon = loader.getController();
        
        repcon.SetMainControleller(this);
        repcon.setReport(newReport = new Report());
        
        PaneView.getChildren().addAll(root);
    }

    @FXML
    private void databaseConnect(MouseEvent event) throws Exception {
       
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DatabaseView.fxml"));
        Parent root = loader.load();
        ReportController databasecon = loader.getController();
    
        PaneView.getChildren().addAll(root);
       
    }
    
    private void refreshReportList()
    {
        reportName.clear();
        for(int i = 0; i<reportList.size();i++)
        {
            reportName.add(reportList.get(i).getReportName());
        }
        ListViewReports.setItems(reportName);
    
    }

    public void SaveReport(Report toSaveReport)
    {
        if(toSaveReport.getReportId() == 0)
        {
            toSaveReport.setReportId(65);
            reportList.add(toSaveReport);
        }
        
       refreshReportList();
        
    }
   

    
    
}

    

