/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Helper.LogHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import Model.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Pane;
import Charts.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;

/**
 * FXML Controller class
 *
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

    
    
    private Map<String, List<Object>> map;
    
    private ReportController reportController;
    private ViewElement element;
    private DisplayElemConstruc sqlResult;
    
    private ObservableList<String> diagrammList = FXCollections.observableArrayList();
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Aktualisierungsrate.getItems().addAll(2,60,120,240,300,600,900,1800);
        
        //test
        SQLStatement.setText("select P.tool, sum(L.pieces) as pieces, sum(L.pieces) as pieces2,L.product, L.route, L.oper from lot L, ptime P where L.state='WAIT' AND L.route=P.route AND L.oper=P.oper AND L.product=p.product group by L.route, L.oper, L.product, P.tool order by pieces desc limit 10;");
        Aktualisierungsrate.setValue(2);
       // disableFields();
        
       diagrammList.addAll(
                "Kreisdiagramm",
                "Balkendiagramm",
                "Liniendiagramm",
                "Tabelle"
       );
       
       Diagrammtyp.getItems().addAll(diagrammList); 
       Diagrammtyp.valueProperty().addListener(new ChangeListener<String>(){
           public void changed(ObservableValue<? extends String> ov, String old_val, String new_val){
                clearDiagrammPropertys();
                
                switch(Diagrammtyp.getValue()){ 
                case "Kreisdiagramm": 

                    break; 
                case "Balkendiagramm": 
                    setViewForBalkendiagramm();
                    break; 
                case "Liniendiagramm": 
                    setViewForLineChart();
                    break; 
                case "Tabelle": 

                    break; 
                default: 
                    LogHandler.add("Diagrammtyp konnte nicht erkannt werden im Event für Combobox");
                } 
               
           }
       
       });
    }

    private void setViewForBalkendiagramm(){
        X_Achse.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        Y_Achse.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        final ObservableList XcolumnsList = FXCollections.observableArrayList();
        final ObservableList YcolumnsList = FXCollections.observableArrayList();
        ObservableList columnsList = getColumnList();
        
        for (int i = 0; i < columnsList.size(); i++) {
            if (sqlResult.isColumnNumeric(columnsList.get(i).toString())){
                YcolumnsList.add(columnsList.get(i).toString());
            } else {
                XcolumnsList.add(columnsList.get(i).toString());
            }
        }
        
        Y_Achse.setItems(YcolumnsList);
        X_Achse.setItems(XcolumnsList);
        activateFields();
    }
    
    private void setViewForLineChart(){
        X_Achse.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        Y_Achse.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        final ObservableList XcolumnsList = FXCollections.observableArrayList();
        final ObservableList YcolumnsList = FXCollections.observableArrayList();
        ObservableList columnsList = getColumnList();
        
        for (int i = 0; i < columnsList.size(); i++) {
            if (sqlResult.isColumnNumeric(columnsList.get(i).toString())){
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
    
    private ObservableList getColumnList(){
        final ObservableList columnsList = FXCollections.observableArrayList();  
      
        columnsList.addAll(Arrays.asList(sqlResult.getColumns()));
        return columnsList;
    }
    
    
    private String getText(TextField target)
    {
        return target.getText();
    }

    

    @FXML
    private void testSQL(MouseEvent event) {
        disableFields();
        Y_Achse.setItems(null);
        X_Achse.setItems(null);
        Diagrammtyp.setValue(null);
        this.sqlResult = new DisplayElemConstruc(SQLStatement.getText());
        LogHandler.add("Statment Korrekt");
        Diagrammtyp.setDisable(false);
    }

    @FXML
    private void genChart(MouseEvent event) throws Exception {
        List<String> selectedItemsXAxis =  (List<String>) X_Achse.getSelectionModel().getSelectedItems();
        List<String> selectedItemsYAxis =  (List<String>) Y_Achse.getSelectionModel().getSelectedItems();
        
        saveViewElementData();
        switch(Diagrammtyp.getValue()){ 
        case "Kreisdiagramm": 
            createPieChart(); 
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
            break; 
        default: 
            LogHandler.add("Diagrammtyp konnte nicht erkannt werden im AddViewElmentController.");
        } 
        
        selectedItemsXAxis.forEach((s) -> {
            System.out.println("selected item " + s);
        });

    }

    @FXML
    private void cancel(MouseEvent event) throws Exception {
        reportController.redraw();
    }

    @FXML
    private void addViewElement(MouseEvent event) throws Exception { 
        saveViewElementData();
        closeWithSave();
    }
    
    public void setReportController(ReportController reportController){
        this.reportController = reportController;
    }
    
    public void setElement(ViewElement element){
        this.element = element;
        if (element.getDiagramId() > 0) loadElmentData();
    }
    
    private void loadElmentData(){
        Diagrammname.setText(element.getDiagramtName());
        SQLStatement.setText(element.getSqlStatement());
        NameX.setText(element.getxAxisName());
        NameY.setText(element.getyAxisName());
        MaßeinheitX.setText(element.getxAxisMeasure());
        MaßeinheitY.setText(element.getYAxisMeasure());
        element.getXAxisValues();
        Aktualisierungsrate.setValue(element.getRefreshRate());
        Diagrammtyp.setValue(element.getDiagramtName());
        this.sqlResult = new DisplayElemConstruc(SQLStatement.getText());
        fillAxis();
        activateFields();
    }
    
    private void saveViewElementData() throws Exception{
        if( element == null ) {
            LogHandler.add("ViewElemnt konnte nicht gespeichert werden da das Objekt leer ist.");
            return;
        }
        
        element.setDiagramtName(Diagrammname.getText());
        element.setRefreshRate(Aktualisierungsrate.getValue());
        element.setDiagramType(Diagrammtyp.getValue());
        element.setSqlStatement(SQLStatement.getText());
        element.setxAxisName(NameX.getText());
        element.setyAxisName(NameY.getText());
        element.setxAxisMeasure(MaßeinheitX.getText());
        element.setYAxisMeasure(MaßeinheitY.getText());
        element.setXAxisValues((List<String>) X_Achse.getSelectionModel().getSelectedItems());
        element.setYAxisValues((List<String>) Y_Achse.getSelectionModel().getSelectedItems());
    }
    
    private void closeWithSave() throws Exception{
        this.reportController.addViewElement(element);
        reportController.redraw();
    }
    
    public void fillAxis(){
      final ObservableList columnsList = FXCollections.observableArrayList();  
      
      columnsList.addAll(Arrays.asList(sqlResult.getColumns()));
      X_Achse.setItems(columnsList);
      Y_Achse.setItems(columnsList);
    }
    
    public void disableFields(){
        Diagrammtyp.setDisable(true);
        X_Achse.setDisable(true);
        Y_Achse.setDisable(true);
        MaßeinheitX.setDisable(true);
        MaßeinheitY.setDisable(true);
        NameX.setDisable(true);
        NameY.setDisable(true);
    }
    
    public void activateFields(){
        Diagrammtyp.setDisable(false);
        X_Achse.setDisable(false);
        Y_Achse.setDisable(false);
        MaßeinheitX.setDisable(false);
        MaßeinheitY.setDisable(false);
        NameX.setDisable(false);
        NameY.setDisable(false);
    }
    
    private void clearDiagrammPropertys(){
        Y_Achse.setItems(null);
        X_Achse.setItems(null);
    
    }
    
    
    public void createPieChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList( 
            new PieChart.Data("Iphone 5S", 13), 
            new PieChart.Data("Samsung Grand", 25), 
            new PieChart.Data("MOTO G", 10), 
            new PieChart.Data("Nokia Lumia", 22));
        
        //Creating a Pie chart 
        PieChart pieChart = new PieChart(pieChartData);
        
        //Setting the title of the Pie chart 
        pieChart.setTitle("Mobile Sales");
        
        //Setting the labels of the pie chart visible  
        pieChart.setLabelsVisible(true);
        
        //Setting the start angle of the pie chart 
        pieChart.setStartAngle(180); 

        //wie in anzeigen lassen?
    }
}
