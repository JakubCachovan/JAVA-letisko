/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package letisko;

import java.util.ArrayList;
import java.sql.Date;
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
public class LetTest {
    
    public LetTest() {
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
     * Test of rezervujLetenku method, of class Let.
     */
    @Test
    public void testRezervujLetenku() {
        System.out.println("rezervujLetenku");
        Date datumRezervacie = new Date(117,11,25);
        Cestujuci cestujuci = new Cestujuci("Meno", "Priezvisko", "123");
        Let instance = new Let("destinacia", null, datumRezervacie, Lietadla.Boeing747);
        boolean expResult = true;
        boolean result = instance.rezervujLetenku(datumRezervacie, cestujuci);
        assertEquals(expResult, result);

    }

    /**
     * Test of zrusRezervaciu method, of class Let.
     */
    @Test
    public void testZrusRezervaciu() {
        System.out.println("zrusRezervaciu");
        String rc = "123";
        Date datumRezervacie = new Date(117,11,25);
        Cestujuci cestujuci = new Cestujuci("Meno", "Priezvisko", "123");
        Let instance = new Let("destinacia", null, datumRezervacie, Lietadla.Boeing747);
        boolean expResult = true;
        instance.rezervujLetenku(datumRezervacie, cestujuci);
        boolean result = instance.zrusRezervaciu(rc);
        assertEquals(expResult, result);
    }

    /**
     * Test of vyhladajCestujuceho method, of class Let.
     */
    @Test
    public void testVyhladajCestujuceho_String_String() {
        System.out.println("vyhladajCestujuceho");
        String meno = "Meno";
        String priezvisko = "Priezvisko";
        Date datumRezervacie = new Date(117,11,25);
        Cestujuci expResult = new Cestujuci(meno, priezvisko, "123"); 
        
        Let instance = new Let("destinacia", null, datumRezervacie, Lietadla.Boeing747);
        instance.rezervujLetenku(datumRezervacie, expResult);
                   
        Cestujuci result = instance.vyhladajCestujuceho(meno, priezvisko);
        assertEquals(expResult, result);
    }

    /**
     * Test of vyhladajCestujuceho method, of class Let.
     */
    @Test
    public void testVyhladajCestujuceho_String() {
        System.out.println("vyhladajCestujuceho");
        String rc = "123";
        Let instance = new Let("destinacia", null, null, Lietadla.Boeing747);
        
        Cestujuci expResult = new Cestujuci("Meno", "", rc); 
        instance.rezervujLetenku(null, expResult);
        Cestujuci result = instance.vyhladajCestujuceho(rc);
        assertEquals(expResult, result);

    }

    /**
     * Test of getPocetVolnychMiest method, of class Let.
     */
    @Test
    public void testGetPocetVolnychMiest() {
        System.out.println("getPocetVolnychMiest");
        Let instance = new Let("destinacia", null, null, Lietadla.Boeing747);
        int expResult = 5;
        int result = instance.getPocetVolnychMiest();
        assertEquals(expResult, result);
    }

    /**
     * Test of getZoznamCestujucich method, of class Let.
     */
    @Test
    public void testGetZoznamCestujucich() {
        System.out.println("getZoznamCestujucich");
        Let instance = new Let("destinacia", null, null, Lietadla.Boeing747);
        ArrayList<Cestujuci> expResult = new ArrayList<>();
        ArrayList<Cestujuci> result = instance.getZoznamCestujucich();
        assertEquals(expResult, result);
    }

    /**
     * Test of getID method, of class Let.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        Let instance = new Let("destinacia", null, null, Lietadla.Boeing747);
        int expResult = 0;
        int result = instance.getID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDestinacia method, of class Let.
     */
    @Test
    public void testGetDestinacia() {
        System.out.println("getDestinacia");
        Let instance = new Let("destinacia", null, null, Lietadla.Boeing747);
        String expResult = "destinacia";
        String result = instance.getDestinacia();
        assertEquals(expResult, result);
    }

    /**
     * Test of getKapitan method, of class Let.
     */
    @Test
    public void testGetKapitan() {
        System.out.println("getKapitan");
        Let instance = new Let("destinacia", null, null, Lietadla.Boeing747);
        Kapitan expResult = null;
        Kapitan result = instance.getKapitan();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDatumOdletu method, of class Let.
     */
    @Test
    public void testGetDatumOdletu() {
        System.out.println("getDatumOdletu");
        Let instance = new Let("destinacia", null, null, Lietadla.Boeing747);
        Date expResult = null;
        Date result = instance.getDatumOdletu();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTypLietadla method, of class Let.
     */
    @Test
    public void testGetTypLietadla() {
        System.out.println("getTypLietadla");
        Let instance = new Let("destinacia", null, null, Lietadla.Boeing747);
        Lietadla expResult = Lietadla.Boeing747;
        Lietadla result = instance.getTypLietadla();
        assertEquals(expResult, result);
    }

    /**
     * Test of setID method, of class Let.
     */
    @Test
    public void testSetID() {
        System.out.println("setID");
        int ID = 0;
        Let instance = new Let("destinacia", null, null, Lietadla.Boeing747);
        instance.setID(ID);
        int result = instance.getID();
        int expResult = ID;       
        assertEquals(expResult, result);
    }
    
}
