/*
TODO
    -this is an example, Adaption is nessecary
    -https://www.tutorialspoint.com/javafx/line_chart.htm
 */
package Charts;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class Line_Chart extends Application {

    @Override
    public void start(Stage stage) {
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
        Scene scene = new Scene(root, 600, 400);

        //Setting title to the Stage 
        stage.setTitle("Line Chart");

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
