
import Helper.LogHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import Model.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Pane;
import Charts.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 * View in which a new view element can be created <br>
 * Only accessible from report view <br>
 * @author Tom
 */
public class AddViewElementController implements Initializable {

    @FXML
    private AnchorPane AddReport;
    @FXML
    private TextField Diagrammname;
    @FXML
    private ComboBox<Integer> Aktualisierungsrate;
    @FXML
    private TextField SQLStatement;
    @FXML
    private ComboBox<String> Diagrammtyp;
    @FXML
    private ListView<?> X_Achse;
    @FXML
    private TextField NameX;
    @FXML
    private TextField MaßeinheitX;
    @FXML
    private ListView<?> Y_Achse;
    @FXML
    private TextField NameY;
    @FXML
    private TextField MaßeinheitY;
    @FXML
    private Pane chartArea;
    @FXML
    private Button previewButton;
    @FXML
    private Button saveButton;

    /**
     * Result set of the SQL - statement
     */
    private Map<String, List<Object>> map;

    private ReportController reportController;
    
    /**
    * Instance of ViewElement class used to hold all information about a chart 
    */
    private ViewElement element;
    
    /**
     * Instance of SQLHandler class used to parse SQL statements
     */
    private SQLHandler sqlResult;

    private ObservableList<String> diagrammList = FXCollections.observableArrayList();

    /**
     * initializes view <br>
     * disables all options after SQL Test <br>
     * Sets up change listener for chart option <br> 
     * @param url default <br>
     * @param rb  default <br>
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Aktualisierungsrate.getItems().addAll(2, 60, 120, 240, 300, 600, 900, 1800);

        /**
         * Test Statement 
        */
        SQLStatement.setText("select P.tool, sum(L.pieces) as pieces, sum(L.pieces) as pieces2,L.product, L.route, L.oper from lot L, ptime P where L.state='WAIT' AND L.route=P.route AND L.oper=P.oper AND L.product=p.product group by L.route, L.oper, L.product, P.tool order by pieces desc limit 10;");
        Aktualisierungsrate.setValue(2);
        disableFields();

        diagrammList.addAll(
                "Kreisdiagramm",
                "Balkendiagramm",
                "Liniendiagramm",
                "Tabelle"
        );

        Diagrammtyp.getItems().addAll(diagrammList);
        Diagrammtyp.valueProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                System.err.println("Change combobox");
                clearDiagrammPropertys();
                saveButton.setDisable(true);
                chartArea.getChildren().clear();
                switch (Diagrammtyp.getValue()) {
                    case "Kreisdiagramm":
                        setViewForPieChart();
                        break;
                    case "Balkendiagramm":
                        setViewForBalkendiagramm();
                        break;
                    case "Liniendiagramm":
                        setViewForLineChart();
                        break;
                    case "Tabelle":
                        //setViewForTable();
                        disableFields();
                        Diagrammtyp.setDisable(false);
                        break;
                    default:
                        LogHandler.add("Diagrammtyp konnte nicht erkannt werden im Event für Combobox");
                        return;
                }

                previewButton.setDisable(false);

            }

        });
    }

    /**
     * gets all values for a bar chart <br>
     * is called via ChangeListener from chart choice list after successful SQL statement execution <br>
     */
    private void setViewForBalkendiagramm() {
        X_Achse.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        Y_Achse.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        final ObservableList XcolumnsList = FXCollections.observableArrayList();
        final ObservableList YcolumnsList = FXCollections.observableArrayList();
        ObservableList columnsList = getColumnList();

        for (int i = 0; i < columnsList.size(); i++) {
            if (sqlResult.isColumnNumeric(columnsList.get(i).toString())) {
                YcolumnsList.add(columnsList.get(i).toString());
            } else {
                XcolumnsList.add(columnsList.get(i).toString());
            }
        }

        Y_Achse.setItems(YcolumnsList);
        X_Achse.setItems(XcolumnsList);
        activateFields();
    }
    
    /**
     * gets all values for a pie chart <br>
     * is called via ChangeListener from chart choice list after successful SQL statement execution <br>
     */
    private void setViewForPieChart() {
        X_Achse.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        Y_Achse.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        final ObservableList XcolumnsList = FXCollections.observableArrayList();
        final ObservableList YcolumnsList = FXCollections.observableArrayList();
        ObservableList columnsList = getColumnList();

        for (int i = 0; i < columnsList.size(); i++) {
            if (sqlResult.isColumnNumeric(columnsList.get(i).toString())) {
                YcolumnsList.add(columnsList.get(i).toString());
            } else {
                XcolumnsList.add(columnsList.get(i).toString());
            }
        }

        Y_Achse.setItems(YcolumnsList);
        X_Achse.setItems(XcolumnsList);
        activateFields();
        MaßeinheitX.setDisable(true);
        MaßeinheitY.setDisable(true);
        NameX.setDisable(true);
        NameY.setDisable(true);

    }
    /**
     * gets all values for a line chart <br>
     * is called via ChangeListener from chart choice list after successful SQL statement execution <br>
     */
    private void setViewForLineChart() {
        X_Achse.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        Y_Achse.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        final ObservableList XcolumnsList = FXCollections.observableArrayList();
        final ObservableList YcolumnsList = FXCollections.observableArrayList();
        ObservableList columnsList = getColumnList();

        for (int i = 0; i < columnsList.size(); i++) {
            if (sqlResult.isColumnNumeric(columnsList.get(i).toString())) {
                YcolumnsList.add(columnsList.get(i).toString());
                XcolumnsList.add(columnsList.get(i).toString());
            } else {
                XcolumnsList.add(columnsList.get(i).toString());
            }
        }

        Y_Achse.setItems(YcolumnsList);
        X_Achse.setItems(XcolumnsList);
        activateFields();

    }
    
    /**
     * Gets all SQL results and returns them as a list for the setViewFor*Chart() functions <br>
     * @return List containing SQL results <br>
     */
    private ObservableList getColumnList() {
        final ObservableList columnsList = FXCollections.observableArrayList();

        columnsList.addAll(Arrays.asList(sqlResult.getColumns()));
        return columnsList;
    }

    private String getText(TextField target) {
        return target.getText();
    }

    @FXML
    private void testSQL(MouseEvent event) {
        disableFields();
        chartArea.getChildren().clear();
        Y_Achse.setItems(null);
        X_Achse.setItems(null);
        Diagrammtyp.setValue(null);
        this.sqlResult = new SQLHandler(SQLStatement.getText());
        if (sqlResult.getResultMap().isEmpty()) {
        } else {
            LogHandler.add("Statement korrekt");
            Diagrammtyp.setDisable(false);
        }
    }

    /**
     * Gets selction for X and Y axis <br>
     * Calls chart class for selected chart with an instance of the ViewElement class  <br>
     * @param event Button event <br>
     * @throws Exception required option <br>
     */
    @FXML
    private void genChart(MouseEvent event) throws Exception {
        List<String> selectedItemsXAxis = (List<String>) X_Achse.getSelectionModel().getSelectedItems();
        List<String> selectedItemsYAxis = (List<String>) Y_Achse.getSelectionModel().getSelectedItems();

        if (!Diagrammtyp.getValue().equals("Tabelle")) {
            if (!columesSelected(selectedItemsXAxis, selectedItemsYAxis)) {
                return;
            }
        }

        saveViewElementData();
        switch (Diagrammtyp.getValue()) {
            case "Kreisdiagramm":
                System.out.println("Kreisdiagramm");

                chartArea.getChildren().clear();

                Pie_Chart p = new Pie_Chart(element);

                chartArea.getChildren().addAll(p.getSceneWithChart().getRoot());
                break;
            case "Balkendiagramm":
                System.out.println("Balkendiagramm");
                chartArea.getChildren().clear();
                //Bar_Chart b = new Bar_Chart();  

                Bar_Chart b = new Bar_Chart(element);
                chartArea.getChildren().addAll(b.getSceneWithChart().getRoot());
                break;
            case "Liniendiagramm":
                System.out.println("Liniendiagramm");
                chartArea.getChildren().clear();
                //Line_Chart l = new Line_Chart(); 

                Line_Chart l = new Line_Chart(element);
                chartArea.getChildren().addAll(l.getSceneWithChart().getRoot());
                break;
            case "Tabelle":
                System.out.println("Tabelle");
                chartArea.getChildren().clear();

                Table t = new Table(element);
                chartArea.getChildren().addAll(t.getSceneWithChart().getRoot());
                break;
            default:
                LogHandler.add("Diagrammtyp konnte nicht erkannt werden im AddViewElmentController.");
        }

        selectedItemsXAxis.forEach((s) -> {
            System.out.println("selected item " + s);
        });
        saveButton.setDisable(false);
    }

    /**
     * Reloads complete AddViewElement view using a methode from the ReportController class <br>
     * @param event Button event <br>
     * @throws Exception required option <br>
     */
    @FXML
    private void cancel(MouseEvent event) throws Exception {
        reportController.redraw();
    }

    /**
     * Calls functions to save ViewElement data
     * @param event Button event <br>
     * @throws Exception required option <br>
     */
    @FXML
    private void addViewElement(MouseEvent event) throws Exception {
        saveViewElementData();
        closeWithSave();
    }

    /**
     * Gets the current instance of the ReportController class  <br>
     * @param reportController 
     */
    public void setReportController(ReportController reportController) {
        this.reportController = reportController;
    }

    /**
     * Gets exsisting ViewElement <br>
     * @param element Element containing data
     */
    public void setElement(ViewElement element) {
        this.element = element;
        if (element.getDiagramId() > 0) {
            loadElmentData();
        }
    }

    /**
     * Loads all information out of a ViewElement and displays them <br>
     * Used in an editing scenario <br>
     */
    private void loadElmentData() {

        Diagrammname.setText(element.getDiagramtName());
        SQLStatement.setText(element.getSqlStatement());
        NameX.setText(element.getxAxisName());
        NameY.setText(element.getyAxisName());
        MaßeinheitX.setText(element.getxAxisMeasure());
        MaßeinheitY.setText(element.getYAxisMeasure());
        element.getXAxisColumn();
        Aktualisierungsrate.setValue(element.getRefreshRate() / 1000);

        this.sqlResult = new SQLHandler(SQLStatement.getText());
        fillAxis();
        Diagrammtyp.setDisable(false);
        Diagrammtyp.setValue(element.getDiagramType());

        //activateFields();
    }

    /**
     * Writes the ViewElement Data to the current ViewElement instance <br>
     * @throws Exception required option <br>
     */
    private void saveViewElementData() throws Exception {
        if (element == null) {
            LogHandler.add("ViewElemnt konnte nicht gespeichert werden, weil das Objekt leer ist.");
            return;
        }

        if (Diagrammname.getText().trim().isEmpty()) {
            LogHandler.add("Diagrammname fehlt.");
            return;
        }

        element.setDiagramtName(Diagrammname.getText());
        element.setRefreshRate(Aktualisierungsrate.getValue() * 1000);
        element.setDiagramType(Diagrammtyp.getValue());
        element.setSqlStatement(SQLStatement.getText());
        element.setxAxisName(NameX.getText());
        element.setyAxisName(NameY.getText());
        element.setxAxisMeasure(MaßeinheitX.getText());
        element.setYAxisMeasure(MaßeinheitY.getText());
        element.setXAxisColumn((List<String>) X_Achse.getSelectionModel().getSelectedItems());
        element.setYAxisColumn((List<String>) Y_Achse.getSelectionModel().getSelectedItems());
    }

    /**
     * Saves the current ViewElement <br>
     * Reloads complete AddViewElement view using a methode from the ReportController class <br>
     * @throws Exception required option <br>
     */
    private void closeWithSave() throws Exception {
        this.reportController.addViewElement(element);
        reportController.redraw();
    }

    /**
     * Sets X and Y option List with exsisting values in a editing scenario <br>
     */
    public void fillAxis() {
        final ObservableList columnsList = FXCollections.observableArrayList();

        columnsList.addAll(Arrays.asList(sqlResult.getColumns()));
        X_Achse.setItems(columnsList);
        Y_Achse.setItems(columnsList);
    }

    /**
     * Disables certain GUI options for improved workflow <br>
     */
    public void disableFields() {
        Diagrammtyp.setDisable(true);
        X_Achse.setDisable(true);
        Y_Achse.setDisable(true);
        MaßeinheitX.setDisable(true);
        MaßeinheitY.setDisable(true);
        NameX.setDisable(true);
        NameY.setDisable(true);
        previewButton.setDisable(true);
        saveButton.setDisable(true);
    }

    /**
     * Activates GUI Options when required <br>
     */
    public void activateFields() {
        Diagrammtyp.setDisable(false);
        X_Achse.setDisable(false);
        Y_Achse.setDisable(false);
        MaßeinheitX.setDisable(false);
        MaßeinheitY.setDisable(false);
        NameX.setDisable(false);
        NameY.setDisable(false);
    }

    /**
     * Clears chart properties before a new chart is created <br>
     */
    private void clearDiagrammPropertys() {
        Y_Achse.setItems(null);
        X_Achse.setItems(null);

    }

    /**
     * Checks if an x and y column where selected <br>
     * @param x selection for X <br>
     * @param y selection for Y <br>
     * @return true or false <br>
     */
    private boolean columesSelected(List x, List y) {
        if (x.isEmpty()) {
            LogHandler.add("Bitte wählen Sie eine Spalte für die x-Achse aus.");
            return false;
        }

        if (y.isEmpty()) {
            LogHandler.add("Bitte wählen Sie eine Spalte für die y-Achse aus.");
            return false;
        }

        return true;
    }

}
