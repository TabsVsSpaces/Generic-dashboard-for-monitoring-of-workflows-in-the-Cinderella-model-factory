/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

/**
 *
 * @author susan
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private LineChart<?, ?> BNChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        XYChart.Series series =new XYChart.Series();
        series.getData().add(new XYChart.Data("l", 23)); //Hier Daten aus DB kommen lassen
        series.getData().add(new XYChart.Data("2", 2));
        series.getData().add(new XYChart.Data("3", 26));
        series.getData().add(new XYChart.Data("4", 15));
        series.getData().add(new XYChart.Data("5", 14));
        series.getData().add(new XYChart.Data("6", 13));
        series.getData().add(new XYChart.Data("7", 20));
        
        XYChart.Series series2 =new XYChart.Series();
        series2.getData().add(new XYChart.Data("l", 55)); //Hier Daten als Tupel aus DB kommen lassen
        series2.getData().add(new XYChart.Data("2", 12));
        series2.getData().add(new XYChart.Data("3", 26));
        series2.getData().add(new XYChart.Data("4", 15));
        series2.getData().add(new XYChart.Data("5", 18));
        series2.getData().add(new XYChart.Data("6", 25));
        series2.getData().add(new XYChart.Data("7", 20));
        
                
        BNChart.getData().addAll(series, series2);
    }    
    
}
