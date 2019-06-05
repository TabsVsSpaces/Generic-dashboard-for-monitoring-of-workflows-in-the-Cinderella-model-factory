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
    private ListView myLog;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
       
      myLog.itemsProperty().bind(listProperty);
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

    

