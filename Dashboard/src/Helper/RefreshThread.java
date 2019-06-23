package Helper;

import Charts.*;
import Model.*;
import static java.lang.Thread.sleep;
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

    public RefreshThread(Report tempReport, Pane PaneView, ImageView ToggleStatus) {
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

            if (JDBCTest.getState()) {
                GridPane elementGrid = refreshView();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ToggleStatus.setImage(new Image("Icons/dbconGood.png"));
                        PaneView.getChildren().clear();
                        PaneView.getChildren().add(elementGrid);
                    }
                });
            } else {
                System.err.println("DB cannot reach:");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ToggleStatus.setImage(new Image("Icons/dbconBad.png"));
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
