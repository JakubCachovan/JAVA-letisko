/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package letisko;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.time.Instant;
import java.util.GregorianCalendar;
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
public class LetiskoTest {
    
    public LetiskoTest() {
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
     * Test of zriadLet method, of class Letisko.
     */
    @Test
    public void testZriadLet() {
        System.out.println("zriadLet");
        String destinacia = "destinacia";
        Date datum = new Date(java.util.Date.from(Instant.MIN).getTime());
        Kapitan kapitan = new Kapitan(0, "", "", "");
        Lietadla lietadlo = Lietadla.Boeing737;
        Letisko instance = new Letisko();        
        Let result = instance.zriadLet(destinacia, datum, kapitan, lietadlo);
        Let expResult = instance.getZoznamLetov().get(0);
        assertEquals(expResult, result);
    }

    /**
     * Test of zrusLet method, of class Letisko.
     */
    @Test
    public void testZrusLet() {
        System.out.println("zrusLet");
        int id = 0;
        Letisko instance = new Letisko();
        boolean expResult = true;
        Let let = instance.zriadLet("", null, null, null);
        boolean result = instance.zrusLet(id);
        assertEquals(expResult, result);

    }

    /**
     * Test of najdiCestujuceho method, of class Letisko.
     */
    @Test
    public void testNajdiCestujuceho() {
        System.out.println("najdiCestujuceho");
        String rc = "123";
        int idLetu = 0;
        Letisko instance = new Letisko();
        Let let = instance.zriadLet("destinacia", null, null, Lietadla.Boeing747);    
        let.setID(idLetu);
        Cestujuci cestujuci = new Cestujuci("Meno", "", "123");
        let.rezervujLetenku(null, cestujuci);
        
        boolean expResult = true;
        boolean result = instance.najdiCestujuceho(rc, idLetu);
        assertEquals(expResult, result);
    }

    /**
     * Test of najdiNajBlizsiLet method, of class Letisko.
     */
    @Test
    public void testNajdiNajBlizsiLet() {
        System.out.println("najdiNajBlizsiLet");
        /*Date datum = new Date();
        Date datum1 = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();
        Date datum2 = new GregorianCalendar(2019, Calendar.FEBRUARY, 11).getTime();
        Date datum3 = new GregorianCalendar(2018, Calendar.FEBRUARY, 11).getTime();
        
        String destinacia = "destinacia";

        Letisko instance = new Letisko();
        Let let1 = instance.zriadLet(destinacia, datum1, null, Lietadla.Boeing747);
        Let let2 = instance.zriadLet(destinacia, datum2, null, Lietadla.Boeing747);
        Let let3 = instance.zriadLet(destinacia, datum3, null, Lietadla.Boeing747);
        
        Let expResult =  let3;      
        Let result = instance.najdiNajBlizsiLet(datum, destinacia);
        assertEquals(expResult, result);*/
    }

    /**
     * Test of getZoznamKapitanov method, of class Letisko.
     */
    @Test
    public void testGetZoznamKapitanov() {
        System.out.println("getZoznamKapitanov");
        Letisko instance = new Letisko();
        ArrayList<Kapitan> expResult = new ArrayList<>();
        ArrayList<Kapitan> result = instance.getZoznamKapitanov();
        assertEquals(expResult, result);
    }

    /**
     * Test of getZoznamLietadiel method, of class Letisko.
     */
    @Test
    public void testGetZoznamLietadiel() {
        System.out.println("getZoznamLietadiel");
        Letisko instance = new Letisko();
        ArrayList<Lietadla> expResult = new ArrayList<>();
        ArrayList<Lietadla> result = instance.getZoznamLietadiel();
        assertEquals(expResult, result);
    }

    /**
     * Test of getZoznamLetov method, of class Letisko.
     */
    @Test
    public void testGetZoznamLetov() {
        System.out.println("getZoznamLetov");
        Letisko instance = new Letisko();
        ArrayList<Let> expResult = new ArrayList<>();
        ArrayList<Let> result = instance.getZoznamLetov();
        assertEquals(expResult, result);
    }

    /**
     * Test of addKapitan method, of class Letisko.
     */
    @Test
    public void testAddKapitan() {
        System.out.println("addKapitan");
        Kapitan kapitan = new Kapitan(0, "Meno", "Priezvisko", "");
        Letisko instance = new Letisko();
        instance.addKapitan(kapitan);
        ArrayList<Kapitan> zoznamKap = instance.getZoznamKapitanov();
        Kapitan expResult = zoznamKap.get(0);
        assertEquals(expResult, kapitan);
    }

    /**
     * Test of load method, of class Letisko.
     */
    @Test
    public void testLoad() {
        System.out.println("load");
        File f = null;
        Letisko instance = new Letisko();
        boolean expResult = true;
        boolean result = instance.load(f);
        assertEquals(expResult, result);
    }

    /**
     * Test of save method, of class Letisko.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        File f = null;
        Letisko instance = new Letisko();
        boolean expResult = true;
        boolean result = instance.save(f);
        assertEquals(expResult, result);
    }


    /**
     * Test of najdiCestujuceho method, of class Letisko.
     */
    @Test
    public void testNajdiCestujuceho_String_int() {
        System.out.println("najdiCestujuceho");
        String rc = "";
        int idLetu = 0;
        Letisko instance = new Letisko();
        boolean expResult = false;
        boolean result = instance.najdiCestujuceho(rc, idLetu);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of najdiCestujuceho method, of class Letisko.
     */
    @Test
    public void testNajdiCestujuceho_String_String() {
        System.out.println("najdiCestujuceho");
        String meno = "";
        String priezvisko = "";
        Letisko instance = new Letisko();
        Cestujuci expResult = null;
        Cestujuci result = instance.najdiCestujuceho(meno, priezvisko);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of najdiCestujuceho method, of class Letisko.
     */
    @Test
    public void testNajdiCestujuceho_String() {
        System.out.println("najdiCestujuceho");
        String rc = "";
        Letisko instance = new Letisko();
        Cestujuci expResult = null;
        Cestujuci result = instance.najdiCestujuceho(rc);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setZoznamKapitanov method, of class Letisko.
     */
    @Test
    public void testSetZoznamKapitanov() {
        System.out.println("setZoznamKapitanov");
        ArrayList<Kapitan> zoznamKapitanov = null;
        Letisko instance = new Letisko();
        instance.setZoznamKapitanov(zoznamKapitanov);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setZoznamLietadiel method, of class Letisko.
     */
    @Test
    public void testSetZoznamLietadiel() {
        System.out.println("setZoznamLietadiel");
        ArrayList<Lietadla> zoznamLietadiel = null;
        Letisko instance = new Letisko();
        instance.setZoznamLietadiel(zoznamLietadiel);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setZoznamLetov method, of class Letisko.
     */
    @Test
    public void testSetZoznamLetov() {
        System.out.println("setZoznamLetov");
        ArrayList<Let> zoznamLetov = null;
        Letisko instance = new Letisko();
        instance.setZoznamLetov(zoznamLetov);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Letisko.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Letisko instance = new Letisko();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
