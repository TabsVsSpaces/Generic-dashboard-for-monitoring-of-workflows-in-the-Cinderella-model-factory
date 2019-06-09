/*
TODO
    -event which registrates the closing of the application
        -poolClose() is missing 
 */
import Model.*;

import java.lang.*;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


  @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
    
        Scene scene = new Scene(root, 1000, 690);
    
        stage.setTitle("Generic Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //initiates the JDBC ConnectionPool
        JDBCPool pool = JDBCPool.getInstance();
        
        launch(args);
        
        
        
        //if exit ->pool.close();
    }
    
    @Override
    public void stop(){
    System.out.println("Stage is closing");
    
    JDBCPool pool = JDBCPool.getInstance();
    try{pool.close();}
    catch(SQLException e)
    { System.out.println(e.toString());}
    
    }
    
}
