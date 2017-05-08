package letisko;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Date;
import javax.swing.JOptionPane;
/**
 * Trieda reprezencuje Let v letisku
 * @author Jakub Cachovan
 */
public class Let implements Serializable {
    private int ID;
    private String destinacia;
    private Kapitan kapitan;
    private Date datumOdletu;
    private Lietadla typLietadla;
    private ArrayList<Cestujuci> zoznamCestujucich;

    /**
     * Konštruktor pre vytvorenie objektu typu Let.
     * @param destinacia
     * @param kapitan
     * @param datumOdletu
     * @param typLietadla 
     */
    public Let(String destinacia, Kapitan kapitan, Date datumOdletu, Lietadla typLietadla) {
        this.destinacia = destinacia;
        this.kapitan = kapitan;
        this.datumOdletu = datumOdletu;
        this.typLietadla = typLietadla;
        this.zoznamCestujucich = new ArrayList<>();
    }
    /**
     * Rezervacia letetenky predstavuje pridanie objektu Cestujuci do pola zoznamCestujucich.
     * @param datumRezervacie - dátum letu
     * @param cestujuci - objekt typu Cestujuci
     * @return true alebo false v závislosti od úspešného rezervovania letenky
     */
    public boolean rezervujLetenku(Date datumRezervacie, Cestujuci cestujuci){
        try {
            zoznamCestujucich.add(cestujuci);
            datumOdletu = datumRezervacie;
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
    
    /**
     * Zrusenie rezervácie pre cestujúceho so zadaných rodným číslom.
     * @param rc - rodné číslo cestujúcehos
     * @return true alebo false
     */
    public boolean zrusRezervaciu(String rc){
        try {
            for (Cestujuci cestujuci : zoznamCestujucich) {
                if(cestujuci.getRC().equals(rc)) {
                    zoznamCestujucich.remove(cestujuci);
                    return true;
                }                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
        return false;
    }
    /**
     * Vyhľadavanie objektu typu cestujúci na základe mena a priezviska cestujúceho.
     * @param meno
     * @param priezvisko
     * @return objekt typu Cestujúci alebo null
     */
    public Cestujuci vyhladajCestujuceho(String meno, String priezvisko){
        for (Cestujuci cestujuci : zoznamCestujucich) {
            if((cestujuci.getMeno().equals(meno)) && (cestujuci.getPriezvisko().equals(priezvisko))){
                return cestujuci;
            }
        }
        return null;
    }
    
    /**
     * Vyhľadavanie objektu typu cestujúci na základe rodného čísla cestujúceho.
     * @param rc
     * @return objekt typu Cestujuci alebo null
     */
    public Cestujuci vyhladajCestujuceho(String rc){
        for (Cestujuci cestujuci : zoznamCestujucich) {
            if (cestujuci.getRC().equals(rc)) return cestujuci;
        }
        return null;
    }   
    
    /**
     * Getter pre vrátenie kapacity konktétneho lietadla.
     * @return kapacita lietadla
     */
    public int getPocetVolnychMiest(){
        return typLietadla.getKapacita();
    }

    /**
     * Getter pre zoznam cestujúcich.
     * @return zoznam cestujúcich
     */
    public ArrayList<Cestujuci> getZoznamCestujucich() {
        return zoznamCestujucich;
    }

    /**
     * Getter pre ID letu.
     * @return id letu
     */
    public int getID() {
        return ID;
    }

    /**
     * Setter pre id letu.
     * @param ID letu
     */
    public void setID(int ID) {
        this.ID = ID;
    }  

    /**
     * Getter pre destináciu letu.
     * @return destinacia
     */
    public String getDestinacia() {
        return destinacia;
    }

    /**
     * Getter pre kapitána letu.
     * @return kapitan
     */
    public Kapitan getKapitan() {
        return kapitan;
    }

    /**
     * Getter pre dátum odletu.
     * @return datumOdletu
     */
    public Date getDatumOdletu() {
        return datumOdletu;
    }

    /**
     * Getter pre objekt typu Lietadla.
     * @return typLietadla
     */
    public Lietadla getTypLietadla() {
        return typLietadla;
    }

    /**
     * Setter pre zoznam cestujúcich.
     * @param zoznamCestujucich 
     */
    public void setZoznamCestujucich(ArrayList<Cestujuci> zoznamCestujucich) {
        this.zoznamCestujucich = zoznamCestujucich;
    }
    /**
     * Vráti retazec typu String, ktorý je vo formáte vhodnom pre súbor typu CSV.
     * Nesie nasledovné informácie:
     * ID letu
     * destinacia
     * datum odletu
     * meno a priezisko kapitána
     * názov a kapacita lietadla
     * @return reťazec typu String 
     */
    public String exportCSV(){
        return "\n;" + ID 
                + ";" + destinacia  
                + ";" + datumOdletu
                + ";" + kapitan.getMeno()+ " "+kapitan.getPriezvisko()
                + ";" + typLietadla.name()
                + ";" + typLietadla.getKapacita();
    } 

    /**
     * Znaková reprezentácia objektu Let
     * @return String
     */
    @Override
    public String toString() {
        return "Let{" + "ID=" + ID + ", destinacia=" + destinacia + ", kapitan=" + kapitan + ", datumOdletu=" + datumOdletu + ", typLietadla=" + typLietadla + ", zoznamCestujucich=" + zoznamCestujucich + '}';
    }

    
    
    
       
}
