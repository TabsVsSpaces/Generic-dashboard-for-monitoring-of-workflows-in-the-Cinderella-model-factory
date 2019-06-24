
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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import Model.ViewElement;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

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
    private ImageView ToggleStatus;

    private ObservableList<Report> reportList = FXCollections.observableArrayList();
    private Report newReport;
    private RefreshThread frefreshThread = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //load reports
        reportList = DataManager.loadReport();

        if (reportList.isEmpty()) {
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
        PaneView.getChildren().clear();
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

    public void loadReprot() {
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

    public void closeMainController() {
        System.out.println("Programm wird beendet.");

        stopRefreashThread();

        //save reports
        DataManager.saveReport(reportList);
    }

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

    private void stopRefreashThread() {
        if (frefreshThread != null) {
            frefreshThread.interrupt();
        }
    }
}
