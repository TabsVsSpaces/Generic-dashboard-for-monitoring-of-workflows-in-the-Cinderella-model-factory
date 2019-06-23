
import Helper.LogHandler;
import Model.Report;
import Model.ViewElement;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
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
    public static final ObservableList<ViewElement> elementList
            = FXCollections.observableArrayList();    // populiert ListView mit Anzeige Elementen

    private int reportID;

    @FXML
    private ListView<ViewElement> ListViewElement;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // get ID from current Report

        ListViewElement.setItems(elementList);

        ListViewElement.setCellFactory(param -> new ListCell<ViewElement>() {
            @Override
            protected void updateItem(ViewElement item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getDiagramtName() == null) {
                    setText(null);
                } else {
                    setText(item.getDiagramtName());
                }
            }
        });

        /*
            ListViewElement.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, 
                    String old_val, String new_val) {
                        LogHandler.add(new_val);
                        
            }
        });
        
         */
    }

    @FXML
    private void addViewElement(MouseEvent event) throws Exception {
        loadElementView(new ViewElement());
    }

    @FXML
    private void addReport(MouseEvent event) {

        if (Reportname.getText().trim().isEmpty()) {
            LogHandler.add("Reportname fehlt.");
            return;
        } else {
            report.setReportName(Reportname.getText());
        }

        //to -do Addview Elements 
        MainC.SaveReport(report);
    }

    @FXML
    private void deleteViewElement(MouseEvent event) {
        ViewElement element = ListViewElement.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("VieElement löschen bestätigen Dialog");
        alert.setHeaderText("Sie sind dabei das ViewElement mit dem Namen: "
                + element.getDiagramtName() + " zu löschen.");
        alert.setContentText("Wollen Sie das ViewElement wirklich löschen?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            deleteViewElement(element);
        }
    }

    @FXML
    private void changeViewElement(MouseEvent event) throws IOException {
        loadElementView(ListViewElement.getSelectionModel().getSelectedItem());
    }

    public void setReport(Report report) {
        this.report = report;
        Reportname.setText(report.getReportName());
    }

    public void SetMainControleller(MainController Main) {
        MainC = Main;
    }

    public void addViewElement(ViewElement element) {
        if (element.getDiagramId() == 0) {

            LogHandler.add("Neues ViewElement wurde hinzugefügt.");
            this.report.addViewElement(element);
        } else {
            LogHandler.add("ViewElement wurde aktualisiert.");
        }
    }

    public void loadViewElements() {
        elementList.clear();
        for (int i = 0; i < report.getListElementSize(); i++) {
            elementList.add(report.getViewEelementbyIndex(i));
        }
    }

    public void loadElementView(ViewElement element) throws IOException {
        this.report.setReportName(Reportname.getText()); //schould change when test box is changing

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddViewElement.fxml"));
        AnchorPane pane = loader.load();
        AddViewElementController AddElemnetController = loader.getController();

        AddElemnetController.setReportController(this);
        AddElemnetController.setElement(element);

        //AnchorPane pane = FXMLLoader.load(getClass().getResource("AddViewElement.fxml"));
        Report.getChildren().setAll(pane);

    }

    public void redraw() throws Exception {
        MainC.loadReportView(report);
    }

    private void deleteViewElement(ViewElement element) {
        for (int i = 0; i < elementList.size(); i++) {
            if (elementList.get(i).getDiagramId() == element.getDiagramId()) {
                elementList.remove(i);
                LogHandler.add("ViewElement " + element.getDiagramtName() + " wurde gelöscht.");
            };
        }
    }
}
