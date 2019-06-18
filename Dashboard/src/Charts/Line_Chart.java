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
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.stage.Stage;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

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
        xAxis.setLabel(element.getxAxisName());

        //Defining the y axis   
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(element.getyAxisName());

        //Creating the line chart 
        LineChart linechart = new LineChart(xAxis, yAxis);
        linechart.setTitle(element.getDiagramtName());
    
        List<XYChart.Series<String, Number>> seriesList = new ArrayList<>();
        
        
        for(int i=0; i < element.getYAxisValues().size(); i++){
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            //Series<String, Number> series = new Series<String, Number>();
            String name = element.getYAxisValues().get(i).toString();
            series.setName(name);
            
            for (int j = 0; j < resultSet.getValues(element.getYAxisValues().get(0)).size(); j++) {
                Number vNumber = (Number) resultSet.getValues(element.getYAxisValues().get(i)).get(j);
                series.getData().add(new XYChart.Data<>(
                        resultSet.getValues(element.getXAxisValues().get(0)).get(j).toString(), 
                        vNumber
                ));
            }
            
            seriesList.add(series);
        }
    
        linechart.getData().addAll(seriesList);
        return linechart;
    }
            
            

   
    public Scene createLineChart() {
        //Defining the x axis             
        NumberAxis xAxis = new NumberAxis(1960, 2020, 10); //X-Achse (min, max, steps)
        xAxis.setLabel("Years");

        //Defining the y axis   
        NumberAxis yAxis = new NumberAxis(0, 350, 50);
        yAxis.setLabel("No.of schools");

        //Creating the line chart 
        LineChart linechart = new LineChart(xAxis, yAxis);

        //Prepare XYChart.Series objects by setting data 
        XYChart.Series series = new XYChart.Series();
        series.setName("No of schools in an year");

        series.getData().add(new XYChart.Data(1970, 15));
        series.getData().add(new XYChart.Data(1980, 30));
        series.getData().add(new XYChart.Data(1990, 60));
        series.getData().add(new XYChart.Data(2000, 120));
        series.getData().add(new XYChart.Data(2013, 240));
        series.getData().add(new XYChart.Data(2014, 300));

        //Setting the data to Line chart    
        linechart.getData().add(series);

        //Creating a Group object  
        Group root = new Group(linechart);

        //Creating a scene object 
        Scene scene = new Scene(root, 350, 330);
       

        return scene;
       
    }
    /*
    public static void main(String args[]) {
        launch(args);
    }
     */
}
