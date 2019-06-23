/*
TODO
    -this is an example, Adaption is nessecary
    -https://javabeginners.de/Frameworks/JavaFX/Nodes/TableView/Tabelle_erstellen.php
    -http://tutorials.jenkov.com/javafx/tableview.html
    -https://gist.github.com/james-d/be5bbd6255a4640a5357
 */
package Charts;

import Model.SQLHandler;
import Model.ViewElement;
import java.sql.ResultSet;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class Table {
    ViewElement element;
    
    public Table(ViewElement element){
        this.element=element;
    }
    
    public Scene getSceneWithChart() {
        //Creating a Group object 
        //Group root = new Group(createTable(element));
        Scene scene = new Scene(createTable(element)); 
        //Creating a scene object
        //Scene scene = new Scene(root, 350, 330);
        
        return scene;
    }
    
    
    private TableView createTable(ViewElement element){
        try{
            SQLHandler sql = new SQLHandler(element.getSqlStatement());
            ResultSet resultSet = sql.getResultSet();
            ObservableList<ObservableList> data = FXCollections.observableArrayList();
        
            TableView tableView = new TableView();

            /**********************************
            * TABLE COLUMN ADDED DYNAMICALLY *
            **********************************/
            for(int i=0 ; i<resultSet.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;                
                TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });

                tableView.getColumns().addAll(col);
            }
            
            /********************************
             * Data added to ObservableList *
             ********************************/
            while(resultSet.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=resultSet.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(resultSet.getString(i));
                }
                System.out.println("Row [1] added "+row );
                data.add(row);
            }

            //FINALLY ADDED TO TableView
            tableView.setItems(data); 
            sql.close();
            return tableView;
            
            
         }catch(Exception e){
              e.printStackTrace();
              System.out.println("Error on Building Data");             
        }   
        return null;
    }
}