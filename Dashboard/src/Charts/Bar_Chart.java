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
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
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
        xAxis.setCategories(FXCollections.<String>observableArrayList(element.getYAxisColumn()));
        xAxis.setLabel(element.getxAxisName() + " in " + element.getxAxisMeasure());

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(element.getyAxisName()+ " in " + element.getYAxisMeasure());

        //Creating the Bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(element.getDiagramtName());
        
        
        
        List<XYChart.Series<String, Number>> seriesList = new ArrayList<>();
        
        for(int i=0; i < resultSet.getValues(element.getXAxisColumn().get(0)).size(); i++){
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            String name = resultSet.getValues(element.getXAxisColumn().get(0)).get(i).toString();
            series.setName(name);
            
            for (int j = 0; j < element.getYAxisColumn().size(); j++) {
                Number vNumber = (Number) resultSet.getValues(element.getYAxisColumn().get(j)).get(i);
                series.getData().add(new XYChart.Data<>(
                        element.getYAxisColumn().get(j), 
                        vNumber
                ));
            }
            
            seriesList.add(series);
        }
        
        //Setting the data to bar chart       
        barChart.getData().addAll(seriesList);
        
        barChart.setAnimated(false);
        
        return barChart;
    } 
}
