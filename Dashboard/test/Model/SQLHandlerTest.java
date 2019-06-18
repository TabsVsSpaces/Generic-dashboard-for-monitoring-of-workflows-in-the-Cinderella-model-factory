/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author sebastianring
 */
public class SQLHandlerTest {
    
    public static void main(String[] args){
        
        System.out.println("First_Line");
        SQLHandlerTest test = new SQLHandlerTest();
        test.testNew();
        test.testGetResultMap();
        System.out.println("End_Line");
    }
    
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

    
    @Test
    public void testNew(){
        System.out.println("Funktion - 1");
    }
    /**
     * Test of getResultMap method, of class SQLHandler.
     */
    
    
    @Test
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
    
    
}


