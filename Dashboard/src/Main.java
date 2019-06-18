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
        MainController mainController;
        Thread logThread;
        static JDBCPool pool;

  @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();
        this.mainController = loader.getController();
        
        mainController.startLogThread(logThread = null);
        
        Scene scene = new Scene(root, 1200, 800);
    
        stage.setTitle("Generic Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //initiates the JDBC ConnectionPool
        pool = JDBCPool.getInstance();
        
        launch(args);
        
        
        
        //if exit ->pool.close();
    }
    
    @Override
    public void stop(){
    System.out.println("Stage is closing");
    mainController.closeMainController();
    logThread.interrupt();
    
    //JDBCPool pool = JDBCPool.getInstance();
    try{pool.close();}
    catch(SQLException e)
    { System.out.println(e.toString());}
    
    }
    
}
