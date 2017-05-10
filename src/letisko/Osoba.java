package letisko;

import java.io.Serializable;

/**
 * 
 * @author Jakub Cachovan
 */
public class Osoba implements Serializable{
    private String meno;
    private String priezvisko;
    private String RC;

    /**
     * Konštruktor pre objekt typu Osoba.
     * @param meno
     * @param priezvisko
     * @param RC 
     */
    public Osoba(String meno, String priezvisko, String RC) {
        this.meno = meno;
        this.priezvisko = priezvisko;
        this.RC = RC;
    }
    
    /**
    * Getter pre meno osoby
    * @return meno
    */
    public String getMeno() {
        return meno;
    }
    
    /**
     * Setter pre meno osoby
     * @param meno
     */
    public void setMeno(String meno) {
        this.meno = meno;
    }
    
    /**
     * Gettter pre priezvisko osoby
     * @return priezvisko
     */
    public String getPriezvisko() {
        return priezvisko;
    }
    
    /**
     * Setter pre priezisko osoby
     * @param priezvisko
     */
    public void setPriezvisko(String priezvisko) {
        this.priezvisko = priezvisko;
    }
    
    /**
     * Getter pre rodné číslo osoby
     * @return
     */
    public String getRC() {
        return RC;
    }
    
    /**
     * Setter pre rodné číslo osoby
     * @param RC
     */
    public void setRC(String RC) {
        this.RC = RC;
    }
    
    /**
     * Znaková reprezentácia objektu typu Osoba
     * @return
     */
    @Override
    public String toString() {
        return "Osoba{" + "meno=" + meno + ", priezvisko=" + priezvisko + ", RC=" + RC + '}';
    }
    
    
}
