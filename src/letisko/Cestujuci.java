package letisko;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Trieda reprezentujúca cestujúceho.
 * @author Jakub Cachovan
 */
public class Cestujuci extends Osoba implements Serializable{

    private ArrayList<String> destinacie;
            
    /**
     * Konštruktor na vytvorenie objektu typu Cestujuci.
     * @param meno
     * @param priezvisko
     * @param RC 
     */
    public Cestujuci(String meno, String priezvisko, String RC) {
        super(meno, priezvisko, RC);
        
        destinacie = new ArrayList<>();
    }

    /**
     * Getter pre pole destinácii, kde su uložene všetky destinácie cestujúceho.
     * @return destinacie
     */
    public ArrayList<String> getDestinacie() {
        return destinacie;
    }
    
    /**
     * Pridanie destinacie pre cestujuceho.
     * @param dest
     * @return true alebo false.
     */
    public boolean addDestinacia(String dest){
        return destinacie.add(dest);
    }

    /**
     * Setter pre pole destinácií pre cestujúceho.
     * @param destinacie 
     */
    public void setDestinacie(ArrayList<String> destinacie) {
        this.destinacie = destinacie;
    }
    
    /**
     * Getter pre priezvisko cestujuceho.
     * @return 
     */
    @Override
    public String getPriezvisko() {
        return super.getPriezvisko(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Getter pre meno cestujuceho.
     * @return 
     */
    @Override
    public String getMeno() {
        return super.getMeno(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Getter pre rodné číslo cestujuceho.
     * @return 
     */
    @Override
    public String getRC() {
        return super.getRC(); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Metóda na vytvorenie a vrátenie reťazca použiteľného pre súbor typu CSV.
     * Nesie informácie o mene a priezvisku cestujuceho.
     * @return String
     */
    public String exportCSV(){
        return getMeno() 
                + ";" + getPriezvisko() 
                + ";" + getRC();
    } 

    /**
     * Znaková reprezentácia objektu typu Cestujuci
     * @return String
     */
    @Override
    public String toString() {
        return "Cestujuci{" + "destinacie=" + destinacie + '}';
    }
}
