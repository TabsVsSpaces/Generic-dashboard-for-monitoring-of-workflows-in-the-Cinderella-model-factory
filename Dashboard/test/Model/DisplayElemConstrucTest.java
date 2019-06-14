/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.lang.Object;
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
public class DisplayElemConstrucTest {
    
    private String sqlStatement = "select P.tool, sum(L.pieces) as pieces, L.product, L.route, L.oper from lot L, ptime P where L.state='WAIT' AND L.route=P.route AND L.oper=P.oper AND L.product=p.product group by L.route, L.oper, L.product, P.tool order by pieces desc limit 10;";
    private DisplayElemConstruc instance = new DisplayElemConstruc(sqlStatement);
    
    
    public DisplayElemConstrucTest() {
        
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
     * Test of getColumns method, of class DisplayElemConstruc.
     */
    @Test
    public void testGetColumns() {
        System.out.println("getColumns");
        //DisplayElemConstruc instance = null;
        String[] expResult = null;
        String[] result = instance.getColumns();
        for(int i=0;i<result.length;i++){
            System.out.println(result[i]);
        }
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isColumnNumeric method, of class DisplayElemConstruc.
     */
    @Test
    public void testIsColumnNumeric() {
        System.out.println("isColumnNumeric");
        String column = "";
        DisplayElemConstruc instance = null;
        boolean expResult = false;
        boolean result = instance.isColumnNumeric(column);
        assertNotEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
 
    
    public static void main(String[] args){
        System.out.println("Hallo");
        //String sqlSt = "select P.tool, sum(L.pieces) as pieces, L.product, L.route, L.oper from lot L, ptime P where L.state='WAIT' AND L.route=P.route AND L.oper=P.oper AND L.product=p.product group by L.route, L.oper, L.product, P.tool order by pieces desc limit 10;";
        DisplayElemConstrucTest testClass = new DisplayElemConstrucTest();
        testClass.testGetColumns();
        System.out.println("Ende");
    
    }
}
