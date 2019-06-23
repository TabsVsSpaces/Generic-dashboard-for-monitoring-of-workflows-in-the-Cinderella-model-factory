
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

    private String DiagramName;

    private String sqlStatement;

    private String diagramType;

    private String xAxisName;

    private String xAxisMeasure;

    private String yAxisName;

    private String yAxisMeasure;

    private List<String> XAxisColumn;

    private List<String> YAxisColumn;

    private Chart viewElementChart;

    public ViewElement() {
        this.diagramId = 0;
        this.refreshRate = 60000;
        this.DiagramName = "";
        this.sqlStatement = "";
        this.diagramType = "";
        this.xAxisName = "";
        this.xAxisMeasure = "";
        this.yAxisName = "";
        this.yAxisMeasure = "";
        this.XAxisColumn = new ArrayList<>();
        this.YAxisColumn = new ArrayList<>();
    }

    public ViewElement(int diagramId, String DiagramName, int refreshRate, String sqlStatement,
            String diagramType, String xAxisName, String xAxisMeasure, String yAxisName, String yAxisMeasure) {

        this.diagramId = diagramId;
        this.refreshRate = refreshRate;
        this.DiagramName = DiagramName;
        this.sqlStatement = sqlStatement;
        this.diagramType = diagramType;
        this.xAxisName = xAxisName;
        this.xAxisMeasure = xAxisMeasure;
        this.yAxisName = yAxisName;
        this.yAxisMeasure = yAxisMeasure;
        this.XAxisColumn = new ArrayList<>();
        this.YAxisColumn = new ArrayList<>();
    }

    //setter -----------------------------------------------------
    public void setDiagramId(int diagramId) {
        this.diagramId = diagramId;
    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }

    public void setDiagramtName(String DiagramtName) {
        this.DiagramName = DiagramtName;
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
        this.yAxisMeasure = YAxisMeasure;
    }

    public void setXAxisColumn(List<String> xAxisValues) {
        this.XAxisColumn = xAxisValues;
    }

    public void setYAxisColumn(List<String> YAxisColumn) {
        this.YAxisColumn = YAxisColumn;
    }

    public void setViewElementChart(Chart newChart) {
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
        return DiagramName;
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
        return yAxisMeasure;
    }

    public List<String> getXAxisColumn() {
        return XAxisColumn;
    }

    public List<String> getYAxisColumn() {
        return YAxisColumn;
    }

    public Chart getViewElementChart() {
        return viewElementChart;
    }

    @Override
    public String toString() {
        return diagramId + "/"
                + DiagramName + "/"
                + refreshRate + "/"
                + sqlStatement + "/"
                + diagramType + "/"
                + xAxisName + "/"
                + xAxisMeasure + "/"
                + yAxisName + "/"
                + yAxisMeasure + "/"
                + XAxisColumn + "/"
                + YAxisColumn + "/";
    }
}
