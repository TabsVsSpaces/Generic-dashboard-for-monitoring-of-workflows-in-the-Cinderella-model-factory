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
import Model.JDBCPool;
//import MainController;
import Model.Report;
import Model.ViewElement;
import com.sun.org.apache.bcel.internal.generic.ICONST;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


/**
 *
 * @author Thanados
 */
public class RefreshThread extends Thread {

    Report tempReport = null;
    Pane PaneView;
    ImageView ToggleStatus;
    boolean keeprunning = true;

    public RefreshThread(Report tempReport, Pane PaneView,ImageView ToggleStatus) {
        this.tempReport = tempReport;
        this.PaneView = PaneView;
        this.ToggleStatus = ToggleStatus;
    }

    public void stopping() {
        this.keeprunning = false;
    }

    private boolean isrunning() {
        return this.keeprunning;
    }

    @Override
    public void run() {
        int rate = getLowestRefreshRate();
        System.err.println("Refreshrate: " + rate);
        while (isrunning()) {
            System.err.println("Thread is running for report : " + tempReport.getReportName());

            JDBCPool jp = JDBCPool.getInstance();
            if (jp.getPoolHealth()) {
                GridPane elementGrid = refreshView();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        // Icons to create Tom will do that
                        //ToggleStatus.setImage(new Image("Icons/lamp.png"));
                        PaneView.getChildren().clear();
                        PaneView.getChildren().add(elementGrid);
                    }
                });
            } else {
                System.err.println("DB cannot reach:");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        //ToggleStatus.setImage(new Image("Icons/edit.png"));
                        System.err.println("DB cannot reach:");
                    }
                });
            }

            try {
                sleep(rate);
            } catch (InterruptedException ex) {
                //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private int getLowestRefreshRate() {
        int rate = 0;

        for (int i = 0; i < tempReport.getListElementSize(); i++) {
            if (rate == 0) {
                rate = tempReport.getViewEelementbyIndex(i).getRefreshRate();
            } else {
                if (tempReport.getViewEelementbyIndex(i).getRefreshRate() < rate) {
                    rate = tempReport.getViewEelementbyIndex(i).getRefreshRate();
                }
            }
        }
        return rate;
    }

    private GridPane refreshView() {

        GridPane elementGrid = new GridPane();
        int row = 0;
        int column = 1;

        for (int i = 0; i < tempReport.getListElementSize(); i++) {
            ViewElement tempElement = tempReport.getViewEelementbyIndex(i);
            switch (tempElement.getDiagramType()) {
                case "Kreisdiagramm":
                    Pie_Chart p = new Pie_Chart(tempElement);
                    elementGrid.add(p.getSceneWithChart().getRoot(), column, row);
                    //PaneView.getChildren().addAll(p.getSceneWithChart().getRoot());
                    break;

                case "Balkendiagramm":
                    Bar_Chart b = new Bar_Chart(tempElement);
                    elementGrid.add(b.getSceneWithChart().getRoot(), column, row);
                    // PaneView.getChildren().addAll(b.getSceneWithChart().getRoot()); 
                    break;

                case "Liniendiagramm":
                    Line_Chart l = new Line_Chart(tempElement);
                    elementGrid.add(l.getSceneWithChart().getRoot(), column, row);
                    // PaneView.getChildren().addAll(l.getSceneWithChart().getRoot());
                    break;

                case "Tabelle":
                    Table t = new Table(tempElement);
                    elementGrid.add(t.getSceneWithChart().getRoot(), column, row);
                    // PaneView.getChildren().addAll(t.getSceneWithChart().getRoot());
                    break;

                default:
                    LogHandler.add("Diagrammtyp konnte nicht erkannt werden im AddViewElmentController.");
            }

            if (row == 0) {
                row++;
            } else {
                column++;
                row = 0;
            }
        }
        return elementGrid;
    }
}
