

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
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


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
    private VBox VBox;
    @FXML
    private ListView ListViewReports;
    @FXML
    private ListView Log;
    @FXML
    private ImageView ToggleStatus;
    
    private int ReportNum = 0;
    

    @Override
    public void initialize(URL url, ResourceBundle rb)  {
      
      for(int i = 1; i <= 10;i++)
      {
          reportName.add("Report: " + Integer.toString(i));
      }
        
      ListViewReports.setItems(reportName);
       
      // Register Change in selected Report used for determining which report should be displayed and which is choosen with the change option
      ListViewReports.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, 
                    String old_val, String new_val) {
                        LogHandler.add(new_val);
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
        Parent root = FXMLLoader.load(getClass().getResource("AddViewElement.fxml"));  
        PaneView.getChildren().addAll(root);
    }

    @FXML
    private void addReport(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Report.fxml"));
        PaneView.getChildren().addAll(root);
    }

    @FXML
    private void databaseConnect(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("DatabaseView.fxml"));
        PaneView.getChildren().addAll(root);
    }

    
    public int getReportNum()
    {return ReportNum;}
    
    public void SetReportNum(int ReportNum)
    {this.ReportNum = ReportNum;}

    
    
    
}

    

