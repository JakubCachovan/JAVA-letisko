package letisko;

import java.io.Serializable;

/**
 * Trieda reprezentujúca kapitána.
 * @author Jakub Cachovan
 */
public class Kapitan extends Osoba implements Serializable{
    
    private int nalietaneHodiny;
    /**
     * Konštruktor pre kapitána.
     * @param nalietaneHodiny
     * @param meno
     * @param priezvisko
     * @param RC 
     */
    public Kapitan(int nalietaneHodiny, String meno, String priezvisko, String RC) {
        super(meno, priezvisko, RC);
        this.nalietaneHodiny = nalietaneHodiny;
    }

    /**
     * Getter pre nalietané hodiny kapitána.
     * @return nalietaneHodiny
     */
    public int getNalietaneHodiny() {
        return nalietaneHodiny;
    }

    /**
     * Setter pre nalietané hodiny kapitána.
     * @param nalietaneHodiny 
     */
    public void setNalietaneHodiny(int nalietaneHodiny) {
        this.nalietaneHodiny = nalietaneHodiny;
    }

    /**
     * Getter pre rodné číslo kapitána.
     * @return 
     */
    @Override
    public String getRC() {
        return super.getRC();
    }

    /**
     * Getter pre priezvisko kapitána.
     * @return 
     */
    @Override
    public String getPriezvisko() {
        return super.getPriezvisko();
    }

    /**
     * Getter pre meno kapitána.
     * @return 
     */
    @Override
    public String getMeno() {
        return super.getMeno();
    }

    /**
     * @return toString
     */
    @Override
    public String toString() {
        return "\n;"+ this.getMeno() + ";" 
                + this.getPriezvisko() + ";"
                + this.getRC() + ";"
                + this.getNalietaneHodiny() + ";";
    }

    
    
    
}
