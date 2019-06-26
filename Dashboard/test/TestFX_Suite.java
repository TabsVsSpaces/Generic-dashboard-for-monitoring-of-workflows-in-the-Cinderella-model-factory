/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.Assert.*;

import org.junit.experimental.categories.Category;


/*Import for TestFX*/       
import javafx.application.Application;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.categories.TestFX;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;        
import javafx.stage.Stage;
import org.testfx.framework.junit5.*;
import org.loadui.testfx.utils.FXTestUtils;
import static org.loadui.testfx.GuiTest.find;
import org.assertj.core.api.*;
import javafx.scene.Node;
import javafx.scene.control.ListView;


// Import SRC-Class
import Model.Report;
import Model.ViewElement;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import static oracle.sql.NUMBER.e;

/**
 *
 * @author sebastianring
 */
public class TestFX_Suite extends ApplicationTest {
    
    MainController mainController;
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();
        this.mainController = loader.getController();

        //mainController.startLogThread(logThread = null);

        Scene scene = new Scene(root, 1200, 800);

        stage.setTitle("Generic Dashboard");
        stage.setScene(scene);
        stage.show();
    }
    
     @BeforeClass
    public static void setUpClass() {
            FXTestUtils.launchApp(Main.class);
            
        //here is that closure I talked about above, you instantiate the getRootNode abstract method
        //which requires you to return a 'parent' object, luckily for us, getRoot() gives a parent!
        //getRoot() is available from ALL Node objects, which makes it easy.
        /*GuiTest controller = new GuiTest() {
            @Override
            protected Parent getRootNode() {
                return Main.getRoot();
            }//stage_returner
        };
            System.out.println(System.getProperty("user.dir"));
            ((TextField)GuiTest.find("#ncw_f_tf_File")).setText(System.getProperty("user.dir") 
                    + "Whatever_text_you_want");
                controller.click("#main_button");

        */
        }
    
    @Test
    public void checkMainComponents(){
        //ListView Reports = find("ListViewReports");
        //assertNotNull(Reports);
        final Button button = find("#BtnMainAddReport");
        
       clickOn(button);
       verifyThat("#BtnMainAddReport");
        
        //assertNull(stage);
        
        
    }
    
    
    @Test
    public void checkComponentsText() {
        TextField massEin = find("MaßeinheitY");
        assertTrue(massEin.getText().equals("Maßeinheit"));
 
        Button absendenBtn = find("saveButton");
        assertTrue(absendenBtn.getText().equals("Hinzufügen"));
 
        Button schliessenBtn = find("reviewButton");
        assertTrue(schliessenBtn.getText().equals("Vorschau"));
    }

}
