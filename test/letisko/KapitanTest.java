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
public class KapitanTest {
    
    public KapitanTest() {
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

    @Test
    public void testSomeMethod() {
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of getNalietaneHodiny method, of class Kapitan.
     */
    @Test
    public void testGetNalietaneHodiny() {
        System.out.println("getNalietaneHodiny");
        Kapitan instance = new Kapitan(25, "Meno", "Priezvisko", "123");
        int expResult = 25;
        int result = instance.getNalietaneHodiny();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of setNalietaneHodiny method, of class Kapitan.
     */
    @Test
    public void testSetNalietaneHodiny() {
        System.out.println("setNalietaneHodiny");
        int nalietaneHodiny = 0;
        Kapitan instance = new Kapitan(25, "Meno", "Priezvisko", "123");
        instance.setNalietaneHodiny(nalietaneHodiny);
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
