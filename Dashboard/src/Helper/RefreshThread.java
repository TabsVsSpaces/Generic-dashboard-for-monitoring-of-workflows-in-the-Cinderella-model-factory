/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;


import Charts.Bar_Chart;
import Charts.Line_Chart;
import Charts.Pie_Chart;
import Charts.Table;
//import MainController;
import Model.Report;
import Model.ViewElement;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author Thanados
 */
public class RefreshThread extends Thread {
    Report tempReport=null;
    Pane PaneView;
    boolean keeprunning = true;
    
    public RefreshThread(Report tempReport, Pane PaneView) {
        this.tempReport = tempReport;
        this.PaneView = PaneView;
    }
    
    public void stopping() {
        this.keeprunning = false;
    }
    private boolean isrunning(){
        return this.keeprunning;
    }
           
    @Override
    public void run() {
        while (isrunning()) {                    
            System.err.println("Thread is running for report : " + tempReport.getReportName());
            GridPane elementGrid = refreshView();
                    
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // entsprechende UI Komponente updaten
                    PaneView.getChildren().clear();
                    PaneView.getChildren().add(elementGrid);
                }});
                    
            
            System.err.println("thread is running");
                    try {
                        sleep(3000);
                    } catch (InterruptedException ex) {
                        //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
        }

    }
            
            
    private GridPane refreshView(){
    
        GridPane elementGrid = new GridPane();
        int row = 0;
        int column = 1;

        for(int i = 0 ; i < tempReport.getListElementSize() ; i++)
        {
            ViewElement tempElement = tempReport.getViewEelementbyIndex(i);
            switch(tempElement.getDiagramType()){ 
            case "Kreisdiagramm":    
                Pie_Chart p = new Pie_Chart(tempElement);
                elementGrid.add(p.getSceneWithChart().getRoot(),column,row);
                //PaneView.getChildren().addAll(p.getSceneWithChart().getRoot());
                break; 

            case "Balkendiagramm":   
                Bar_Chart b = new Bar_Chart(tempElement);
                elementGrid.add(b.getSceneWithChart().getRoot(),column,row);
               // PaneView.getChildren().addAll(b.getSceneWithChart().getRoot()); 
                break; 

            case "Liniendiagramm": 
                Line_Chart l = new Line_Chart(tempElement); 
                elementGrid.add(l.getSceneWithChart().getRoot(),column,row);
               // PaneView.getChildren().addAll(l.getSceneWithChart().getRoot());
                break; 

            case "Tabelle": 
                Table t = new Table(tempElement);
                elementGrid.add(t.getSceneWithChart().getRoot(),column,row);
               // PaneView.getChildren().addAll(t.getSceneWithChart().getRoot());
                break; 

            default: 
                LogHandler.add("Diagrammtyp konnte nicht erkannt werden im AddViewElmentController.");
            } 

            if (row == 0 )
            { 
                row++;
            }
            else
            {
                column++;
                row = 0;
            }
        }
        return elementGrid;
    }
}
