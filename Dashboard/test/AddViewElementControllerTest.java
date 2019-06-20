/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Model.ViewElement;
import java.net.URL;
import java.util.ResourceBundle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;

/**
 *
 * @author sebastianring
 */
public class AddViewElementControllerTest {
    
    public AddViewElementControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setReportController method, of class AddViewElementController.
     */
    @org.junit.Test
    public void testSetReportController() {
        System.out.println("setReportController");
        ReportController reportController = null;
        AddViewElementController instance = new AddViewElementController();
        instance.setReportController(reportController);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of setElement method, of class AddViewElementController.
     */
    @org.junit.Test
    public void testSetElement() {
        System.out.println("setElement");
        ViewElement element = null;
        AddViewElementController instance = new AddViewElementController();
        instance.setElement(element);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of fillAxis method, of class AddViewElementController.
     */
    @org.junit.Test
    public void testFillAxis() {
        System.out.println("fillAxis");
        AddViewElementController instance = new AddViewElementController();
        instance.fillAxis();
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of disableFields method, of class AddViewElementController.
     */
   @org.junit.Test
    public void testDisableFields() {
        System.out.println("disableFields");
        AddViewElementController instance = new AddViewElementController();
        instance.disableFields();
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of activateFields method, of class AddViewElementController.
     */
    @org.junit.Test
    public void testActivateFields() {
        System.out.println("activateFields");
        AddViewElementController instance = new AddViewElementController();
        instance.activateFields();
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
