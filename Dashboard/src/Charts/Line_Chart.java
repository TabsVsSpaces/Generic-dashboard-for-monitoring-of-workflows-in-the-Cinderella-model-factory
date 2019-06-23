/*
TODO
    -this is an example, Adaption is nessecary
    -https://www.tutorialspoint.com/javafx/line_chart.htm
 */
package Charts;

import Model.SQLHandler;
import Model.ViewElement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class Line_Chart  {
    ViewElement element;
    
    public Line_Chart(ViewElement element){
        this.element=element;
    }
    
    public Scene getSceneWithChart() {
        //Creating a Group object 
        Group root = new Group(createChart(element));

        //Creating a scene object
        Scene scene = new Scene(root, 350, 330);
        
        return scene;
    }
    
    private LineChart createChart(ViewElement element) {
        LineChart lineChart = createWithStringAndNumber();
        return lineChart;
    }
    
    private LineChart createWithStringAndNumber(){
        SQLHandler resultSet = new SQLHandler(element.getSqlStatement());
        
        //Defining the x axis             
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel(element.getxAxisName() + " in " + element.getxAxisMeasure());

        //Defining the y axis   
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(element.getyAxisName() + " in " + element.getYAxisMeasure());

        //Creating the line chart 
        LineChart linechart = new LineChart(xAxis, yAxis);
        linechart.setTitle(element.getDiagramtName());
    
        List<XYChart.Series<String, Number>> seriesList = new ArrayList<>();
        
        
        for(int i=0; i < element.getYAxisColumn().size(); i++){
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            //Series<String, Number> series = new Series<String, Number>();
            String name = element.getYAxisColumn().get(i).toString();
            series.setName(name);
            
            for (int j = 0; j < resultSet.getValues(element.getYAxisColumn().get(0)).size(); j++) {
                Number vNumber = (Number) resultSet.getValues(element.getYAxisColumn().get(i)).get(j);
                series.getData().add(new XYChart.Data<>(
                        resultSet.getValues(element.getXAxisColumn().get(0)).get(j).toString(), 
                        vNumber
                ));
            }
            
            seriesList.add(series);
        }
    
        linechart.getData().addAll(seriesList);
        return linechart;
    }
}
