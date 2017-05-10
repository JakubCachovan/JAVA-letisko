/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package letisko;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Acer
 */
public class OsobaTest {
    

    public OsobaTest() {

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
     * Test of getMeno method, of class Osoba.
     * 
    */
    @Test
    public void testGetMeno() {
        System.out.println("getMeno");
        Osoba o = new Osoba("Peter", "Peteris", "6525623");
        String expResult = "Peter";
        String result = o.getMeno();
        assertEquals(expResult, result);       
    }

    /**
     * Test of setMeno method, of class Osoba.
     */
    @Test
    public void testSetMeno() {
        System.out.println("setMeno");
        String meno = "Jozo";
        Osoba o = new Osoba("Peter", "Peteris", "6525623");
        o.setMeno(meno);
    }

    /**
     * Test of getPriezvisko method, of class Osoba.
     */
    @Test
    public void testGetPriezvisko() {
        System.out.println("getPriezvisko");
        Osoba o = new Osoba("Peter", "Peteris", "6525623");
        String expResult = "Peteris";
        String result = o.getPriezvisko();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPriezvisko method, of class Osoba.
     */
    @Test
    public void testSetPriezvisko() {
        System.out.println("setPriezvisko");
        String priezvisko = "Ony";
        Osoba o = new Osoba("Peter", "Peteris", "6525623");
        o.setPriezvisko(priezvisko);
    }

    /**
     * Test of getRC method, of class Osoba.
     */
    @Test
    public void testGetRC() {
        System.out.println("getRC");
        Osoba o = new Osoba("Peter", "Peteris", "6525623");
        String expResult = "6525623";
        String result = o.getRC();
        assertEquals(expResult, result);
    }

    /**
     * Test of setRC method, of class Osoba.
     */
    @Test
    public void testSetRC() {
        System.out.println("setRC");
        String RC = "12345";
        Osoba o = new Osoba("Peter", "Peteris", "6525623");
        o.setRC(RC);
    }

    /**
     * Test of toString method, of class Osoba.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Osoba instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}
