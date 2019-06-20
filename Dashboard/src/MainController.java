

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
import java.util.Optional;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ToggleButton;
import Charts.*;
import Model.ViewElement;
import static java.lang.Thread.sleep;
import java.util.Arrays;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;

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
    private RefreshThread frefreshThread=null;

    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        
      //load reports  
      if (reportList.isEmpty()){
          createDefaultReport();
      }
      
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

        Log.itemsProperty().bind(listProperty);
        listProperty.set(FXCollections.observableArrayList(LogHandler.show()));
        loadReprot();
    }

    public Thread startLogThread() {
        Thread logThread;
        logThread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                // entsprechende UI Komponente updaten
                                listProperty.set(FXCollections.observableArrayList(LogHandler.show()));
                            }
                        });

                        sleep(2000);

                    }

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        logThread.start();
        return logThread;
    }

    @FXML
    private void deleteReport(MouseEvent event) throws Exception {
        Report removeReport = ListViewReports.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Report löschen bestätigen Dialog");
        alert.setHeaderText("Sie sind dabei den Report mit dem Namen: "
                + removeReport.getReportName() + " zu löschen.");
        alert.setContentText("Wollen Sie den Report wirklich löschen?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            deleteReport(removeReport);
        }
    }

    @FXML
    private void changeReport(MouseEvent event) throws Exception {
        loadReportView(ListViewReports.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void addReport(MouseEvent event) throws Exception {
        loadReportView(newReport = new Report());
    }

    public void loadReportView(Report report) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Report.fxml"));
        Parent root = loader.load();
        ReportController repcon = loader.getController();

        //LogHandler.add(String.valueOf(report.getListElement().size()));
        repcon.SetMainControleller(this);
        repcon.setReport(report);
        repcon.loadViewElements();
        stopRefreashThread();
        PaneView.getChildren().addAll(root);
    }

    public void SaveReport(Report toSaveReport) {
        if (toSaveReport.getReportId() == 0) {
            toSaveReport.setReportId(createReportID());
            LogHandler.add("Neuer Report wurde hinzugefügt.");
            reportList.add(toSaveReport);
        } else {
            LogHandler.add("Report wurde aktualisiert.");
        }
        loadReprot();
    }

    private int createReportID() {
        int id = 1;
        boolean search = true;

        while (search) {
            for (int i = 0; i < reportList.size(); i++) {
                if (reportList.get(i).getReportId() == id) {
                    id++;
                    break;
                };
            }

            search = false;
        }

        return id;
    }

    private void deleteReport(Report report) {
        for (int i = 0; i < reportList.size(); i++) {
            if (reportList.get(i).getReportId() == report.getReportId()) {
                reportList.remove(i);
                LogHandler.add("Report " + report.getReportName() + " wurde gelöscht.");
            };
        }
        stopRefreashThread();
    }

    @FXML
    private void loadReport(MouseEvent event) {
        loadReprot();
    }
    
    public void loadReprot(){
        Report tempReport=null;
        if (ListViewReports.getSelectionModel().isEmpty()){
            tempReport = ListViewReports.getItems().get(0);
        } else {
            tempReport = ListViewReports.getSelectionModel().getSelectedItem();
        }
        
        stopRefreashThread();
        

        frefreshThread = new RefreshThread(tempReport, PaneView);
        frefreshThread.start();
    }

    @FXML
    private void databaseConnect(ActionEvent event) throws Exception {
        stopRefreashThread();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DatabaseView.fxml"));
        Parent root = loader.load();
        DatabaseViewController databasecon = loader.getController();
        databasecon.SetMainControleller(this);
        
        PaneView.getChildren().addAll(root);
    }
    
    public void closeMainController(){
       System.out.println("Programm wird beendet.");
       
       stopRefreashThread();
       
       //save reports
    }

    public void createDefaultReport(){
      String sqlStm = "select P.tool, sum(L.pieces) as pieces, sum(L.pieces) as pieces2,L.product, L.route, L.oper from lot L, ptime P where L.state='WAIT' AND L.route=P.route AND L.oper=P.oper AND L.product=p.product group by L.route, L.oper, L.product, P.tool order by pieces desc limit 10;";
        
      ViewElement e_1 = new ViewElement();
      e_1.setDiagramtName("Element 1");
      e_1.setDiagramType("Kreisdiagramm");
      e_1.setRefreshRate(5000);
      e_1.setSqlStatement(sqlStm);
      e_1.setxAxisName("x Achse");
      e_1.setyAxisName("y Achse");
      e_1.setYAxisMeasure("Y Mess");
      e_1.setxAxisMeasure("X Mess");
      e_1.setXAxisValues(Arrays.asList("route"));
      e_1.setYAxisValues(Arrays.asList("pieces"));
        
      ViewElement e_2 = new ViewElement();
      e_2.setDiagramtName("Element 2");
      e_2.setDiagramType("Balkendiagramm");
      e_2.setRefreshRate(5000);
      e_2.setSqlStatement(sqlStm);
      e_2.setxAxisName("x Achse");
      e_2.setyAxisName("y Achse");
      e_2.setYAxisMeasure("Y Mess");
      e_2.setxAxisMeasure("X Mess");
      e_2.setXAxisValues(Arrays.asList("route"));
      e_2.setYAxisValues(Arrays.asList("pieces"));
      
      ViewElement e_3 = new ViewElement();
      e_3.setDiagramtName("Element 3");
      e_3.setDiagramType("Liniendiagramm");
      e_3.setRefreshRate(5000);
      e_3.setSqlStatement(sqlStm);
      e_3.setxAxisName("x Achse");
      e_3.setyAxisName("y Achse");
      e_3.setYAxisMeasure("Y Mess");
      e_3.setxAxisMeasure("X Mess");
      e_3.setXAxisValues(Arrays.asList("pieces"));
      e_3.setYAxisValues(Arrays.asList("pieces"));
      
      ViewElement e_4 = new ViewElement();
      e_4.setDiagramtName("Element 4");
      e_4.setDiagramType("Tabelle");
      e_4.setRefreshRate(5000);
      e_4.setSqlStatement(sqlStm);
      e_4.setxAxisName("x Achse");
      e_4.setyAxisName("y Achse");
      e_4.setYAxisMeasure("Y Mess");
      e_4.setxAxisMeasure("X Mess");
      e_4.setXAxisValues(Arrays.asList("route"));
      e_4.setYAxisValues(Arrays.asList("pieces"));
        
      Report tempRep = new Report();
      tempRep.setReportName("Default Report 1");
      tempRep.setReportId(1);
      tempRep.addViewElement(e_1);
      tempRep.addViewElement(e_2);
      tempRep.addViewElement(e_3);
      tempRep.addViewElement(e_4);
      reportList.add(tempRep);
      
      tempRep = new Report();
      tempRep.setReportName("Default Report 2");
      tempRep.setReportId(2);
      tempRep.addViewElement(e_1);
      tempRep.addViewElement(e_2);
      reportList.add(tempRep);
      
      tempRep = new Report();
      tempRep.setReportName("Default Report 3");
      tempRep.setReportId(2);
      tempRep.addViewElement(e_1);
      reportList.add(tempRep);
    
    }
    
    private void stopRefreashThread(){
        if (frefreshThread != null) {
            frefreshThread.stopping();
        } 
    }
    
    
    
}
