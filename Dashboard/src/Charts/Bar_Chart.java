/*
TODO
    -this is an example, Adaption is nessecary
    -https://www.tutorialspoint.com/javafx/bar_chart.htm
 */
package Charts;

import Model.SQLHandler;
import Model.ViewElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.stage.Stage;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Bar_Chart  {
    ViewElement element;
    
    public Bar_Chart(ViewElement element){
        this.element=element;
    }
    
    public Scene getSceneWithChart() {
        
        //Creating a Group object 
        Group root = new Group(createChart(element));

        //Creating a scene object
        Scene scene = new Scene(root, 350, 330);
        
        return scene;
    }
    
    private BarChart createChart(ViewElement element) {
        SQLHandler resultSet = new SQLHandler(element.getSqlStatement());
        
        //Defining the axes              
        CategoryAxis xAxis = new CategoryAxis();
        
        //Arrays.asList(resultSet.getValues(element.getXAxisValues().get(0)));
        
        //String[] xValues = resultSet.getValues(element.getXAxisValues().get(0)).toArray(new String[0]);      
        xAxis.setCategories(FXCollections.<String>observableArrayList(element.getYAxisValues()));
        xAxis.setLabel(element.getxAxisName());

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(element.getyAxisName());

        //Creating the Bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(element.getDiagramtName());
        
        
        
        List<XYChart.Series<String, Number>> seriesList = new ArrayList<>();
        
        for(int i=0; i < resultSet.getValues(element.getXAxisValues().get(0)).size(); i++){
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            String name = resultSet.getValues(element.getXAxisValues().get(0)).get(i).toString();
            series.setName(name);
            
            for (int j = 0; j < element.getYAxisValues().size(); j++) {
                Number vNumber = (Number) resultSet.getValues(element.getYAxisValues().get(j)).get(i);
                series.getData().add(new XYChart.Data<>(
                        element.getYAxisValues().get(j), 
                        vNumber
                ));
            }
            
            seriesList.add(series);
        }
        
        //Setting the data to bar chart       
        barChart.getData().addAll(seriesList);
        return barChart;
    }
    
    
    public Scene createBarChart() {
        //Defining the axes              
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("Speed", "User rating", "Milage", "Safety")));
        xAxis.setLabel("category");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("score");

        //Creating the Bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Comparison between various cars");

        //Prepare XYChart.Series objects by setting data       
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Fiat");
        series1.getData().add(new XYChart.Data<>("Speed", 1.0));
        series1.getData().add(new XYChart.Data<>("User rating", 3.0));
        series1.getData().add(new XYChart.Data<>("Milage", 5.0));
        series1.getData().add(new XYChart.Data<>("Safety", 5.0));

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Audi");
        series2.getData().add(new XYChart.Data<>("Speed", 5.0));
        series2.getData().add(new XYChart.Data<>("User rating", 6.0));
        series2.getData().add(new XYChart.Data<>("Milage", 10.0));
        series2.getData().add(new XYChart.Data<>("Safety", 4.0));

        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName("Ford");
        series3.getData().add(new XYChart.Data<>("Speed", 4.0));
        series3.getData().add(new XYChart.Data<>("User rating", 2.0));
        series3.getData().add(new XYChart.Data<>("Milage", 3.0));
        series3.getData().add(new XYChart.Data<>("Safety", 6.0));

        //Setting the data to bar chart       
        barChart.getData().addAll(series1, series2, series3);

        //Creating a Group object 
        Group root = new Group(barChart);

        //Creating a scene object
        Scene scene = new Scene(root, 350, 330);
        
        return scene;
    }

    
}
