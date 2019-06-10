/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.chart.Chart;

/**
 *
 * @author Thanados
 */
public class ViewElement {
    private int diagramId;
    private int refreshRate;
    private String DiagramtName;
    private String sqlStatement;
    private String diagramType;
    private String xAxisName;
    private String xAxisMeasure;
    private String yAxisName;
    private String YAxisMeasure;
    private List<String> XAxisValues= new ArrayList<>();
    private List<String> YAxisValues = new ArrayList<>();
    private Chart viewElementChart;

    //setter -----------------------------------------------------
    public void setDiagramId(int diagramId) {
        this.diagramId = diagramId;
    }
    
    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }
    
    public void setDiagramtName(String DiagramtName) {
        this.DiagramtName = DiagramtName;
    }
    
    public void setSqlStatement(String sqlStatement) {
        this.sqlStatement = sqlStatement;
    }
    
    public void setDiagramType(String diagramType) {
        this.diagramType = diagramType;
    }
    
    public void setxAxisName(String xAxisName) {
        this.xAxisName = xAxisName;
    }
    
    public void setxAxisMeasure(String xAxisMeasure) {
        this.xAxisMeasure = xAxisMeasure;
    }
    
        public void setyAxisName(String yAxisName) {
        this.yAxisName = yAxisName;
    }
        
    public void setYAxisMeasure(String YAxisMeasure) {
        this.YAxisMeasure = YAxisMeasure;
    }    
    
    public void setXAxisValues(List<String> xAxisValues) {
        this.XAxisValues = xAxisValues;
    }
    
    public void setYAxisValues(List<String> YAxisValues) {
        this.YAxisValues = YAxisValues;
    }
        
    public void setViewElementChart(Chart newChart){
        this.viewElementChart = newChart;
    }
    
    // getter -----------------------------------------------------
    public int getDiagramId() {
        return diagramId;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public String getDiagramtName() {
        return DiagramtName;
    }

    public String getSqlStatement() {
        return sqlStatement;
    }

    public String getDiagramType() {
        return diagramType;
    }

    public String getxAxisName() {
        return xAxisName;
    }

    public String getxAxisMeasure() {
        return xAxisMeasure;
    }

    public String getyAxisName() {
        return yAxisName;
    }

    public String getYAxisMeasure() {
        return YAxisMeasure;
    }

    public List<String> getXAxisValues() {
        return XAxisValues;
    }

    public List<String> getYAxisValues() {
        return YAxisValues;
    }

    public Chart getViewElementChart(){
        return viewElementChart;
    }
}
