/*
TODO
    -this is an example, Adaption is nessecary
    -https://www.tutorialspoint.com/javafx/pie_chart.htm
 */
package Charts;

import Model.DisplayElemConstruc;
import Model.ViewElement;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.PieChart;

public class Pie_Chart {
    ViewElement element;
    
    public Pie_Chart(ViewElement element){
        this.element=element;
    }
    
    public Scene getSceneWithChart() {
        
        //Creating a Group object 
        Group root = new Group(createChart( element));

        //Creating a scene object
        Scene scene = new Scene(root, 350, 330);
        
        return scene;
    }

    private PieChart createChart(ViewElement element){
        DisplayElemConstruc resultSet = new DisplayElemConstruc(element.getSqlStatement());
        
        //List<PieChart.Data> seriesList = new ArrayList<>();
        ObservableList<PieChart.Data> seriesList = FXCollections.observableArrayList();
        
        for(int i=0; i < resultSet.getValues(element.getXAxisValues().get(0)).size(); i++){
            String nameValue = resultSet.getValues(element.getXAxisValues().get(0)).get(i).toString();
            Number vNumber = (Number) resultSet.getValues(element.getYAxisValues().get(0)).get(i);
              
            System.out.println(nameValue + " : " + vNumber); 
            seriesList.add(new PieChart.Data(nameValue, vNumber.doubleValue()));
        }

        //Creating a Pie chart 
        PieChart pieChart = new PieChart( seriesList);
        
        //Setting the title of the Pie chart 
        pieChart.setTitle(element.getDiagramtName());

        //setting the direction to arrange the data 
        pieChart.setClockwise(true);

        //Setting the length of the label line 
        pieChart.setLabelLineLength(50);

        //Setting the labels of the pie chart visible  
        pieChart.setLabelsVisible(true);

        //Setting the start angle of the pie chart  
        pieChart.setStartAngle(180);
        
        return pieChart;
    }
    
    public Scene createPieChart() {
        //Preparing ObservbleList object         
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Iphone 5S", 13),
                new PieChart.Data("Samsung Grand", 25),
                new PieChart.Data("MOTO G", 10),
                new PieChart.Data("Nokia Lumia", 22));

        //Creating a Pie chart 
        PieChart pieChart = new PieChart(pieChartData);

        //Setting the title of the Pie chart 
        pieChart.setTitle("Mobile Sales");

        //setting the direction to arrange the data 
        pieChart.setClockwise(true);

        //Setting the length of the label line 
        pieChart.setLabelLineLength(50);

        //Setting the labels of the pie chart visible  
        pieChart.setLabelsVisible(true);

        //Setting the start angle of the pie chart  
        pieChart.setStartAngle(180);

        //Creating a Group object  
        Group root = new Group(pieChart);

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
