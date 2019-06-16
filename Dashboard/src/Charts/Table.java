/*
TODO
    -this is an example, Adaption is nessecary
    -https://javabeginners.de/Frameworks/JavaFX/Nodes/TableView/Tabelle_erstellen.php
    -http://tutorials.jenkov.com/javafx/tableview.html
    -https://gist.github.com/james-d/be5bbd6255a4640a5357
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Table {
    ViewElement element;
    
    public Table(ViewElement element){
        this.element=element;
    }
    
    public Scene getSceneWithChart() {
        //Creating a Group object 
        //Group root = new Group(createTable(element));

        VBox vbox = new VBox(createTable(element));

        Scene scene = new Scene(vbox, 350, 300);

        //Creating a scene object
        //Scene scene = new Scene(root, 350, 330);
        
        return scene;
    }
    
    
    private TableView createTable(ViewElement element){
        DisplayElemConstruc resultSet = new DisplayElemConstruc(element.getSqlStatement());
        TableView tableView = new TableView();
        
        List<TableColumn<String, String>> coulumnList= new ArrayList<>();
        for(int i=0; i < element.getXAxisValues().size(); i++) {
            String name = element.getXAxisValues().get(i).toString();
            TableColumn<String, String> column = new TableColumn<>(name);
            coulumnList.add(column);
        }
        
        tableView.getColumns().addAll(coulumnList);
        
        ObservableList row = FXCollections.observableArrayList();

        List<String> valueList = new ArrayList<>();
        for (int j = 0; j < resultSet.getValues(element.getXAxisValues().get(0)).size(); j++) {
            valueList.clear();
            for(int k=0; k < element.getXAxisValues().size(); k++) {
                valueList.add(resultSet.getValues(element.getXAxisValues().get(k)).get(j).toString());
            }
            row.addAll(valueList);
        }
        
        tableView.getItems().addAll(row);
    
        return tableView;
    }

}

