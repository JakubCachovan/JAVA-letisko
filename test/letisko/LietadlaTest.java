/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package letisko;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Acer
 */
public class LietadlaTest {
    
    public LietadlaTest() {
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
     * Test of values method, of class Lietadla.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        Lietadla[] expResult = new Lietadla[2];
        expResult[0] = Lietadla.Boeing747;
        expResult[1] = Lietadla.Boeing737;
        Lietadla[] result = Lietadla.values();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of valueOf method, of class Lietadla.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String name = "Boeing747";
        Lietadla expResult = Lietadla.Boeing747;
        Lietadla result = Lietadla.valueOf(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of getKapacita method, of class Lietadla.
     */
    @Test
    public void testGetKapacita() {
        System.out.println("getKapacita");
        Lietadla instance = Lietadla.Boeing737;
        int expResult = 5;
        int result = instance.getKapacita();
        assertEquals(expResult, result);
    }
    
}
