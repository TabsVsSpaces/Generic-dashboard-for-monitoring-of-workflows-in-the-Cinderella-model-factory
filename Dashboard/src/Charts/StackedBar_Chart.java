/*
TODO
    -this is an example, Adaption is nessecary
    -https://www.tutorialspoint.com/javafx/stacked_bar_chart.htm
 */
package Charts;

import java.util.Arrays;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.stage.Stage;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

public class StackedBar_Chart extends Application {

    @Override
    public void start(Stage stage) {
        //Defining the axes               
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("Africa", "America", "Asia", "Europe", "Oceania")));

        xAxis.setLabel("category");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Population (In millions)");

        //Creating the Bar chart 
        StackedBarChart<String, Number> stackedBarChart
                = new StackedBarChart<>(xAxis, yAxis);
        stackedBarChart.setTitle("Historic World Population by Region");

        //Prepare XYChart.Series objects by setting data 
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("1800");
        series1.getData().add(new XYChart.Data<>("Africa", 107));
        series1.getData().add(new XYChart.Data<>("America", 31));
        series1.getData().add(new XYChart.Data<>("Asia", 635));
        series1.getData().add(new XYChart.Data<>("Europe", 203));
        series1.getData().add(new XYChart.Data<>("Oceania", 2));

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("1900");
        series2.getData().add(new XYChart.Data<>("Africa", 133));
        series2.getData().add(new XYChart.Data<>("America", 156));
        series2.getData().add(new XYChart.Data<>("Asia", 947));
        series2.getData().add(new XYChart.Data<>("Europe", 408));
        series1.getData().add(new XYChart.Data<>("Oceania", 6));

        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName("2008");
        series3.getData().add(new XYChart.Data<>("Africa", 973));
        series3.getData().add(new XYChart.Data<>("America", 914));
        series3.getData().add(new XYChart.Data<>("Asia", 4054));
        series3.getData().add(new XYChart.Data<>("Europe", 732));
        series1.getData().add(new XYChart.Data<>("Oceania", 34));

        //Setting the data to bar chart
        stackedBarChart.getData().addAll(series1, series2, series3);

        //Creating a Group object  
        Group root = new Group(stackedBarChart);

        //Creating a scene object 
        Scene scene = new Scene(root, 600, 400);

        //Setting title to the Stage 
        stage.setTitle("stackedBarChart");

        //Adding scene to the stage 
        stage.setScene(scene);

        //Displaying the contents of the stage 
        stage.show();
    }
    /*
    public static void main(String args[]) {
        launch(args);
    }
     */
}
