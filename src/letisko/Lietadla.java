package letisko;

import java.io.Serializable;


/**
 * Enum pre lietadla
 * @author Jakub Cachovan
 */
public enum Lietadla implements Serializable{
    Boeing747(250),
    Boeing737(205);
    
    private int kapacita;

    /**
     * Konštruktor pre triedu Lietadla.
     * @param kapacita - kapacita lietadla.
     */
    private Lietadla(int kapacita) {
        this.kapacita = kapacita;
    }

    /**
     * Getter pre kapacitu lietadla
     * @return kapacita
     */
    public int getKapacita() {
        return kapacita;
    }

    /**
     * Znaková reprezentácia objektu typu Lietadla
     * @return String
     */
    @Override
    public String toString() {
        return "\n;" + this.name() +";" + kapacita + ";";
    }
    
    
}