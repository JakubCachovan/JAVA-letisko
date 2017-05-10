package letisko;

import java.util.ArrayList;
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
public class CestujuciTest {
    
    public CestujuciTest() {
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
     * Test of getDestinacie method, of class Cestujuci.
     */
    @Test
    public void testGetDestinacie() {
        System.out.println("getDestinacie");
        Cestujuci instance = new Cestujuci("Meno", "Priezvisko", "123");
        ArrayList<String> expResult = new ArrayList<>();
        ArrayList<String> result = instance.getDestinacie();
        assertEquals(expResult, result);

    }

    /**
     * Test of addDestinacia method, of class Cestujuci.
     */
    @Test
    public void testAddDestinacia() {
        System.out.println("addDestinacia");
        String dest = "Dubaj";
        Cestujuci instance = new Cestujuci("Meno", "Priezvisko", "123");
        boolean expResult = true;
        boolean result = instance.addDestinacia(dest);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPriezvisko method, of class Cestujuci.
     */
    @Test
    public void testGetPriezvisko() {
        System.out.println("getPriezvisko");
        Cestujuci instance = new Cestujuci("Meno", "Priezvisko", "123");
        String expResult = "Priezvisko";
        String result = instance.getPriezvisko();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMeno method, of class Cestujuci.
     */
    @Test
    public void testGetMeno() {
        System.out.println("getMeno");
        Cestujuci instance = new Cestujuci("Meno", "Priezvisko", "123");
        String expResult = "Meno";
        String result = instance.getMeno();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRC method, of class Cestujuci.
     */
    @Test
    public void testGetRC() {
        System.out.println("getRC");
        Cestujuci instance = new Cestujuci("Meno", "Priezvisko", "123");
        String expResult = "123";
        String result = instance.getRC();
        assertEquals(expResult, result);
    }
    
}
