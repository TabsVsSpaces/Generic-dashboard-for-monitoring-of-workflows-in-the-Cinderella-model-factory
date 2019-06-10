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
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

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
    private ComboBox<?> Aktualisierungsrate;
    @FXML
    private TextField SQLStatement;
    @FXML
    private ComboBox<?> Diagrammtyp;
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

    private Map<String, List<Object>> map;
    
    private ReportController reportController;
    private ViewElement element;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private String getText(TextField target)
    {
        return target.getText();
    }

    

    @FXML
    private void testSQL(MouseEvent event) {
        
        SQLHandler handler = new SQLHandler(getText(SQLStatement));
        map = handler.getResultMap();
        
        LogHandler.add("Statment Korrekt");
    }

    @FXML
    private void genChart(MouseEvent event) {
    }

    @FXML
    private void cancel(MouseEvent event) throws Exception {
        reportController.redraw();
    }

    @FXML
    private void addViewElement(MouseEvent event) throws Exception { 
        saveViewElementData();
    }
    
    public void setReportController(ReportController reportController){
        this.reportController = reportController;
    }
    
    public void setElement(ViewElement element){
        this.element = element;
        loadElmentData();
    }
    
    private void loadElmentData(){
        Diagrammname.setText(element.getDiagramtName());
        SQLStatement.setText(element.getSqlStatement());
        NameX.setText(element.getxAxisName());
        NameY.setText(element.getyAxisName());
        MaßeinheitX.setText(element.getxAxisMeasure());
        MaßeinheitY.setText(element.getYAxisMeasure());
        
        //X_Achse.setItems((ObservableList<?>) element.getXAxisValues());
        //Y_Achse.setItems((ObservableList<?>) element.getYAxisValues());
        
        //Aktualisierungsrate -> aktivate value
        //Diagrammtyp -> aktivate  value   
    
    }
    
    private void saveViewElementData() throws Exception{
        if( element == null ) {
            LogHandler.add("ViewElemnt konnte nicht gespeichert werden da das Objekt leer ist.");
            return;
        }
        
        element.setDiagramtName(Diagrammname.getText());
        //element.setRefreshRate(Integer.parseInt(String.valueOf(Aktualisierungsrate.getValue())));
        //element.setDiagramType(String.valueOf(Diagrammtyp.getValue()));
        element.setSqlStatement(SQLStatement.getText());
        element.setxAxisName(NameX.getText());
        element.setyAxisName(NameY.getText());
        element.setxAxisMeasure(MaßeinheitX.getText());
        element.setYAxisMeasure(MaßeinheitY.getText());
        element.setXAxisValues((List<String>) X_Achse.getSelectionModel().getSelectedItems());
        element.setYAxisValues((List<String>) Y_Achse.getSelectionModel().getSelectedItems());

        this.reportController.addViewElement(element);
        reportController.redraw();
    }
    
}
