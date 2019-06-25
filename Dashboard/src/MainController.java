
import static java.lang.Thread.sleep;
import Helper.*;
import Model.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import java.util.Optional;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

/**
 * FXML Main View Controller class <br>
 * This class controlls the main view, is initialised once on programm start and closed with the programm <br>
 * Also holds the list of all created reports including view elements <br>
 * Starts all Threads 
 * @author Tom
 */
public class MainController implements Initializable {

    @FXML
    private AnchorPane MainView;
    
    /**
     * Pane in which charts will be displayed
     */
    @FXML
    private Pane PaneView;

    protected ListProperty<String> listProperty = new SimpleListProperty<>();

    /**
     * Used to register report selection <br>
     */
    @FXML
    private ListView<Report> ListViewReports;
     /**
     * GUI Item <br>
     */
    @FXML
    private ListView Log;
    @FXML
    private ImageView ToggleStatus;

    /**
     * Holds all Reports and their individual view elements <br>
     */
    private ObservableList<Report> reportList = FXCollections.observableArrayList();
    private Report newReport;
    private RefreshThread frefreshThread = null;

    /**
     * Log is initialised and set up with an ObservableList <br>
     * ObservableList allow to set a property upon the list so changes can be registered <br>
     * @param url default <br>
     * @param rb  default <br>
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //load reports
        try {
            reportList = DataManager.loadReport();

            if (reportList == null || reportList.isEmpty()) {
                createDefaultReport();
            }
        } catch (Exception e) {
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
        loadReport();
    }
    /**
     * Starts Thread which checks for new log entrys every 2 seconds <br>
     * @return Log Thread <br>
     */
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
                                listProperty.set(FXCollections.observableArrayList(LogHandler.show()));
                            }
                        });
                        sleep(2000);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
        logThread.start();
        return logThread;
    }

    /**
     * Deletes reports via getting the selected report from the ObservableList <br>
     * @param event Button event <br>
     * @throws Exception required option <br>
     */
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

    /**
     * @param event Button event <br>
     * @throws Exception required option <br>
     */
    @FXML
    private void changeReport(MouseEvent event) throws Exception {
        loadReportView(ListViewReports.getSelectionModel().getSelectedItem());
    }

    /**
     * 
     * @param event Button event <br>
     * @throws Exception required option <br>
     */
    @FXML
    private void addReport(MouseEvent event) throws Exception {
        loadReportView(newReport = new Report());
    }

    /**
     * Displays report view and gives the current instance of the MainController to the ReportController <br>
     * @param report gets currently selected report <br>
     * @throws Exception required option <br>
     */
    public void loadReportView(Report report) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Report.fxml"));
        Parent root = loader.load();
        ReportController repcon = loader.getController();
        PaneView.getChildren().clear();
        repcon.SetMainControleller(this);
        repcon.setReport(report);
        repcon.loadViewElements();
        stopRefreashThread();
        PaneView.getChildren().addAll(root);
    }

    /**
     * Saves the report <br>
     * Is called from the ReportController <br>
     * @param toSaveReport currently selected report <br>
     */
    public void SaveReport(Report toSaveReport) {
        if (toSaveReport.getReportId() == 0) {
            toSaveReport.setReportId(createReportID());
            LogHandler.add("Neuer Report wurde hinzugefügt.");
            reportList.add(toSaveReport);
        } else {
            LogHandler.add("Report wurde aktualisiert.");
        }
        loadReport();
    }

    /**
     * 
     * @return creates ID
     */
    private int createReportID() {
        int id = 0;
        List<Integer> IDs = new ArrayList<>();

        boolean search = true;

        while (search) {
            for (int i = 0; i < reportList.size(); i++) {
                IDs.add(reportList.get(i).getReportId());
            }

            Collections.sort(IDs);

            for (int i = 0; i < IDs.size(); i++) {
                if (id == IDs.get(i)) {
                    id++;
                } else {
                    break;
                }
            }
            search = false;
        }
        return id;
    }

    /**
     * 
     * @param report gets currently selected report <br>
     */
    private void deleteReport(Report report) {
        for (int i = 0; i < reportList.size(); i++) {
            if (reportList.get(i).getReportId() == report.getReportId()) {
                reportList.remove(i);
                LogHandler.add("Report " + report.getReportName() + " wurde gelöscht.");
            };
        }
        stopRefreashThread();
    }

    /**
     * 
     * @param event Button event <br>
     */
    @FXML
    private void loadReport(MouseEvent event) {
        loadReport();
    }

    /**
     * Loads Report from ReportList <br>
     */
    public void loadReport() {
        Report tempReport = null;
        if (ListViewReports.getSelectionModel().isEmpty()) {
            tempReport = ListViewReports.getItems().get(0);
        } else {
            tempReport = ListViewReports.getSelectionModel().getSelectedItem();
        }

        stopRefreashThread();

        frefreshThread = new RefreshThread(tempReport, PaneView, ToggleStatus);
        frefreshThread.start();
    }

    /**
     * 
     * @param event Button event <br>
     * @throws Exception required option <br>
     */
    @FXML
    private void databaseConnect(ActionEvent event) throws Exception {
        stopRefreashThread();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DatabaseView.fxml"));
        Parent root = loader.load();
        DatabaseViewController databasecon = loader.getController();
        databasecon.SetMainControleller(this);
        PaneView.getChildren().clear();
        PaneView.getChildren().addAll(root);
    }

    /**
     * Stops refreshThread and initializes saving of ReportList 
     */
    public void closeMainController() {
        System.out.println("Programm wird beendet.");

        stopRefreashThread();

        DataManager.saveReport(reportList);
    }

    
    /**
     * Used when ReportList is empty 
     */
    public void createDefaultReport() {
        String sqlStm = "select P.tool, sum(L.pieces) as pieces, sum(L.pieces) as pieces2,L.product,L.route, L.oper "
                + "from lot L, ptime P where L.state='WAIT' AND L.route=P.route AND L.oper=P.oper AND L.product=p.product "
                + "group by L.route, L.oper, L.product, P.tool order by pieces desc limit 10;";

        ViewElement e_1 = new ViewElement(1, "Element 1", 5000, sqlStm, "Kreisdiagramm", "x Achse", "X Mess", "y Achse", "Y Mess");
        e_1.setXAxisColumn(Arrays.asList("route"));
        e_1.setYAxisColumn(Arrays.asList("pieces"));

        ViewElement e_2 = new ViewElement(2, "Element 2", 5000, sqlStm, "Balkendiagramm", "x Achse", "X Mess", "y Achse", "Y Mess");
        e_2.setXAxisColumn(Arrays.asList("route"));
        e_2.setYAxisColumn(Arrays.asList("pieces"));

        ViewElement e_3 = new ViewElement(3, "Element 3", 5000, sqlStm, "Liniendiagramm", "x Achse", "X Mess", "y Achse", "Y Mess");
        e_3.setXAxisColumn(Arrays.asList("pieces"));
        e_3.setYAxisColumn(Arrays.asList("pieces"));

        ViewElement e_4 = new ViewElement(4, "Element 4", 5000, sqlStm, "Tabelle", "x Achse", "X Mess", "y Achse", "Y Mess");
        e_4.setXAxisColumn(Arrays.asList("route"));
        e_4.setYAxisColumn(Arrays.asList("pieces"));

        Report tempRep = new Report(1, "Default Report 1");
        tempRep.addViewElement(e_1);
        tempRep.addViewElement(e_2);
        tempRep.addViewElement(e_3);
        tempRep.addViewElement(e_4);
        reportList.add(tempRep);

        tempRep = new Report(2, "Default Report 2");
        tempRep.addViewElement(e_1);
        tempRep.addViewElement(e_2);
        reportList.add(tempRep);

        tempRep = new Report(3, "Default Report 3");
        tempRep.addViewElement(e_1);
        reportList.add(tempRep);
    }

    /**
     * 
     */
    private void stopRefreashThread() {
        if (frefreshThread != null) {
            frefreshThread.interrupt();
        }
    }
}
