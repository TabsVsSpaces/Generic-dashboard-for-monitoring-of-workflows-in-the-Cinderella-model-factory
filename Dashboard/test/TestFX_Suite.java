/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.util.ResourceBundle;
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
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.categories.TestFX;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;        
/**
 *
 * @author sebastianring
 */
public class TestFX_Suite extends GuiTest {
    
    @Override
	protected Parent getRootNode() {
		Parent parent = null;
		try {
			parent = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
			return parent;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return parent;
	}
    
     @BeforeClass
    public static void setUpClass() {
            FXTestUtils.launchApp(Main.class);

            //here is that closure I talked about above, you instantiate the getRootNode abstract method
            //which requires you to return a 'parent' object, luckily for us, getRoot() gives a parent!
            //getRoot() is available from ALL Node objects, which makes it easy. 
            controller = new GuiTest() {
                @Override
                protected Parent getRootNode() {
                    return Main.stage_returner().getScene().getRoot();
                }
            };
            System.out.println(System.getProperty("user.dir"));
            ((TextField)GuiTest.find("#ncw_f_tf_File")).setText(System.getProperty("user.dir") 
                    + "Whatever_text_you_want");
                controller.click("#main_button");


        }
    

}
