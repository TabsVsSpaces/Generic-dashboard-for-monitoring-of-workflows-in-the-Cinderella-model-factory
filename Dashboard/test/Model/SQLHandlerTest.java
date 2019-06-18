/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 *
 * @author sebastianring
 */
public class SQLHandlerTest {
    
    private String sqlStatement = "select P.tool, sum(L.pieces) as pieces, L.product, L.route, L.oper from lot L, ptime P where L.state='WAIT' AND L.route=P.route AND L.oper=P.oper AND L.product=p.product group by L.route, L.oper, L.product, P.tool order by pieces desc limit 10;";
    private SQLHandler instance = new SQLHandler(sqlStatement);
    
    
    public SQLHandlerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    
    
    /**
     * Test of getResultMap method, of class SQLHandler.
     */
    
    
    @org.junit.Test
    public void testGetResultMap() {
        System.out.println("Funktion - 2");
        System.out.println("getResultMap");
        String sqlStatement = "select P.tool, sum(L.pieces) as pieces, L.product, L.route, L.oper from lot L, ptime P where L.state='WAIT' AND L.route=P.route AND L.oper=P.oper AND L.product=p.product group by L.route, L.oper, L.product, P.tool order by pieces desc limit 10;";
        SQLHandler instance = new SQLHandler(sqlStatement);
        System.out.println("new instance");
        Map<String, List<Object>> expResult = null;
        Map<String, List<Object>> result = instance.getResultMap();
        System.out.println("result");
        //String expResult = result.get(getColumnName(0),);
        System.out.println(result.get("pieces"));
        String testString = result.get("pieces").toString();
        System.out.println("String:"+testString);
        //System.out.println("String:"+testStringexp);
        //assertEquals("something",result,expResult);
        String message = "erfolgreich";
        assertNotNull(testString,message);
        // TODO review the generated test code and remove the default call to fail.
        //fail("Keine Datenbankverbindung");
    }
    
    /**
     * Test of testGetColumns method, of class SQLHandler.
     */
    @org.junit.Test
    public void testGetColumns() {
        System.out.println("getColumns");
        //DisplayElemConstruc instance = null;
        String[] expResult = {"pieces","product","oper","route","tool"};
        String[] result = instance.getColumns();
        for(int i=0;i<result.length;i++){
            System.out.println(result[i]);
        }
        assertArrayEquals(expResult, result);
        //assertArrayEquals(expResult,result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of isColumnNumeric method, of class SQLHandler.
     */
    @org.junit.Test
    public void testIsColumnNumeric() {
        System.out.println("isColumnNumeric");
        String column = "pieces"; // is known by the test SQL statement and is numeric
        //SQLHandler instance = null;
        boolean expResult = false;
        boolean result = instance.isColumnNumeric(column);
        assertNotEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
   

    /**
     * Test of close method, of class SQLHandler.
     */
    @org.junit.Test
    public void testClose() {
        System.out.println("close");
        SQLHandler newInstance = null;
        instance.close();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getValues method, of class SQLHandler.
     */
    @org.junit.Test
    public void testGetValues() {
        System.out.println("getValues");
        String ColumnName = "pieces";
        //SQLHandler instance = null;
        List<Object> expResult = null;
        List<Object> result = instance.getValues(ColumnName);
        assertNotEquals(expResult, result);
        // Values are not Equal when values in result, which values in result is not import, because these are changeing in ja Live System
        //fail("The test case is a prototype.");
    }
    
    public static void main(String[] args){
        
        System.out.println("First_Line");
        SQLHandlerTest testClass = new SQLHandlerTest();
        System.out.println("Second_Line");
        testClass.testGetResultMap();
        System.out.println("Third_Line");
        testClass.testGetColumns();
        System.out.println("Fourth_Line");
        System.out.println("End_Line");
    }

}


