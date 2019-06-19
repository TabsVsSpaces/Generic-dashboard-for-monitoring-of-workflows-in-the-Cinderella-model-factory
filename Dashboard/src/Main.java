/*
TODO
    -event which registrates the closing of the application
 */
import Model.*;

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
        pool = JDBCPool.getInstance();

        launch(args);
    }

    @Override
    public void stop() {

        mainController.closeMainController();
        logThread.interrupt();

        if (pool != null) {
            try {
                pool.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        System.out.println("Stage is closing");
    }
}
