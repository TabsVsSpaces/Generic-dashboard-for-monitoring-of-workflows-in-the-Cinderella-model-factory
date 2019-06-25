/*

 */
package Charts;

import Model.*;
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

    public Table(ViewElement element) {
        this.element = element;
    }

    public Scene getSceneWithChart() {
        Scene scene = new Scene(createTable(element));

        return scene;
    }

    private TableView createTable(ViewElement element) {

        try {
            SQLHandler sql = new SQLHandler(element.getSqlStatement());

            String[] columns = sql.getColumns();
            String[] values = sql.getValues(columns[0]).toArray(new String[0]);

            ObservableList<ObservableList> data = FXCollections.observableArrayList();

            TableView tableView = new TableView();

            for (int i = 0; i < columns.length; i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(columns[i]);
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                tableView.getColumns().addAll(col);
            }

            for (int j = 0; j < values.length; j++) {
                //Iterate Row
                ObservableList<Object> row = FXCollections.observableArrayList();
                for (String column : columns) {
                    //Iterate Column
                    row.add(sql.getValues(column).get(j));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);
            }
            tableView.setItems(data);
            return tableView;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return null;
    }
}
