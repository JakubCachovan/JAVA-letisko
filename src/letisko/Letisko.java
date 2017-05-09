package letisko;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;
import java.time.LocalDate;
import javax.swing.JOptionPane;

/**
 * Trieda reprezentujúca Letisko
 * @author Jakub Cachovan
 */
public class Letisko implements Serializable {

    private ArrayList<Kapitan> zoznamKapitanov;
    private ArrayList<Lietadla> zoznamLietadiel;
    private ArrayList<Let> zoznamLetov;

    /**
     * Konštruktor pre triedu Letisko.
     * Slúži na inicializáciu polí
     */
    public Letisko() {
        zoznamKapitanov = new ArrayList<>();
        zoznamLetov = new ArrayList<>();
        zoznamLietadiel = new ArrayList<>();
        zoznamLietadiel.addAll(Arrays.asList(Lietadla.values()));
    }
    
    /**
     * Metóda na vytvorenie letu v letisku.
     * Vytvorenie inštancie objektu typu Let pomocou parametrov tejto metódy.
     * Pridanie objektu do zoznamu letov.
     * @param destinacia
     * @param datum
     * @param kapitan
     * @param lietadlo
     * @return objekt typu Let alebo null
     */
    public Let zriadLet(String destinacia, Date datum, Kapitan kapitan, Lietadla lietadlo){
        try {
            Let let = new Let(destinacia, kapitan, datum, lietadlo);
            //set ID letu
            zoznamLetov.add(let);
            return let;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }    
    }
    
    /**
     * Zrušenie letu v letisku na zaklade id letu.
     * @param id - id letu
     * @return true alebo false
     */
    public boolean zrusLet(int id){
        try {
            for (Let let : zoznamLetov) {
                if(let.getID() == id){
                    zoznamLetov.remove(let);
                    return true;
                }
            }            
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        } 
        return false;
    }
    /**
     * Nájdenie letu podľa zadaného id.
     * @param idLetu
     * @return let alebo null
     */
    public Let najdiLetPodlaId(int idLetu){
        for (Let let : zoznamLetov) {
            if(let.getID() == idLetu){
                return let;
            }
        }
        return null;
    }
    
    /**
     * Nájdenie všetkých letov, ktoré má cestujúci rezervované.
     * @param rc_cestujuceho
     * @return ArrayList zoznam Letov
     */
    public ArrayList<Let> najdiZoznamLetovCestujuceho(String rc_cestujuceho){
        ArrayList<Let> zoznamLetovCestujuceho = new ArrayList<>();
        for (Let let : zoznamLetov) {
            for (Cestujuci cestujuci : let.getZoznamCestujucich()) {
                if(cestujuci.getRC().equalsIgnoreCase(rc_cestujuceho)){
                    zoznamLetovCestujuceho.add(let);
                }
            }
        }
        return zoznamLetovCestujuceho;
    }
    
    /**
     * Setter pre priradenie zoznamu cestujucich pre konkrétny let.
     * Alebo setter pre nastavenie všetkých leteniek daného letu.
     * @param idLetu - id letu
     * @param cestujuci - zoznam cestujucich
     * @return true alebo false
     */
    public boolean setCestujucichPreLet(int idLetu, ArrayList<Cestujuci> cestujuci){
        for (Let let : zoznamLetov) {
            if(let.getID() == idLetu){
                let.setZoznamCestujucich(cestujuci);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Metóda na vyhľadávanie cestujúceho na základe rodného čísla pre konkrétny let.
     * @param rc
     * @param idLetu
     * @return true alebo false.
     */
    public boolean najdiCestujuceho(String rc, int idLetu){
        for (Let let : zoznamLetov) {
            if(let.getID() == idLetu){
                for (Cestujuci cestujuci : let.getZoznamCestujucich()) {
                    if(cestujuci.getRC().equalsIgnoreCase(rc)) 
                        return true;
                }
            }          
        }
        return false;
    }
    
    /**
     * Nájdenie cestujúceho na základe rodného čísla.
     * @param rc
     * @return objekt typu Cestujuci alebo null
     */
    public Cestujuci najdiCestujuceho(String rc){
        Cestujuci najdeny = null;
        for (Let let : zoznamLetov) {
            najdeny = let.vyhladajCestujuceho(rc);
            return najdeny;
        }
        return null;
    }
    
    /**
     * Nájdenie cestujúceho na základe meno a priezviska.
     * @param meno
     * @param priezvisko
     * @return objekt typu Cestujuci alebo null
     */
    public Cestujuci najdiCestujuceho(String meno, String priezvisko){
        Cestujuci najdeny = null;
        for (Let let : zoznamLetov) {
            najdeny = let.vyhladajCestujuceho(meno, priezvisko);
            return najdeny;
        }
        return null;
    }   
    
    /**
     * Vymazanie rezervácií vo všetkých letoch, kde sa nachádza cestujúci so zadaným rodným číslom.
     * @param rc
     * @return počet vymazaných
     */
    public int vymazLetyCestujuceho(String rc){
        int pocet = 0;
        for (Let let : zoznamLetov) {
            ArrayList<Cestujuci> novyZoznamCestujucich = new ArrayList<>();
            for (Cestujuci cestujuci : let.getZoznamCestujucich()) {
                if(!cestujuci.getRC().equalsIgnoreCase(rc)){
                    novyZoznamCestujucich.add(cestujuci);
                }else{
                    pocet++;
                }
            }
            let.setZoznamCestujucich(novyZoznamCestujucich);
        }
        return pocet;
    }
    
    /**
     * Nájdenie najbližšieho letu.
     * @param datum - dátum odletu
     * @param destinacia - destinácia letu
     * @return objekt typu Let alebo null
     */
    public Let najdiNajBlizsiLet(Date datum, String destinacia){
        Date najblizsiDatum;           
        //Date aktualDatum = Date.valueOf(LocalDate.now());
        boolean letNajdeny = false;     
        for(Let let : zoznamLetov){ 
            int denOdlet = let.getDatumOdletu().getDate();
            int mesiacOdlet = let.getDatumOdletu().getMonth();
            int rokOdletu = let.getDatumOdletu().getYear();
            int den = datum.getDate();
            int mesiac = datum.getMonth();
            int rok = datum.getYear();
            if(denOdlet == den && mesiacOdlet == mesiac && rokOdletu == rok && let.getDestinacia().equals(destinacia)){
                return let;
            }
        }
        for (Let let1 : zoznamLetov) {     
            if(let1.getDestinacia().equals(destinacia) && (let1.getDatumOdletu().after(datum))){ 
                najblizsiDatum = let1.getDatumOdletu();
                for(Let let : zoznamLetov){
                    //ak rozdiel medzi datumami prehladavaneho letu so zadanym letom je mensi ako rozdiel prehladavaneho letu s momentalne najblizsim 
                    if (let.getDestinacia().equals(destinacia)
                            //((let.getDatumOdletu().getTime() - datum.getTime()) <= (najblizsiDatum.getTime() - datum.getTime()))
                            //&& (let.getDatumOdletu().getTime() >= datum.getTime())){
                            && let.getDatumOdletu().before(najblizsiDatum) 
                            && (let.getDatumOdletu().after(datum))){
                            
                        najblizsiDatum = let.getDatumOdletu();
                        letNajdeny = true;                                     
                    }   
                }
                if(letNajdeny || (datum.before(najblizsiDatum))){ //najdeny let musi mat 
                    return let1;
                }                    
                else{
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * Getter pre zoznam kapitánov.
     * @return 
     */
    public ArrayList<Kapitan> getZoznamKapitanov() {
        return zoznamKapitanov;
    }

    /**
     * Getter pre zoznam lietadiel.
     * @return 
     */
    public ArrayList<Lietadla> getZoznamLietadiel() {
        return zoznamLietadiel;
    }

    /**
     * Getter pre zoznam letov.
     * @return 
     */
    public ArrayList<Let> getZoznamLetov() {
        return zoznamLetov;
    }
    
    /**
     * Metóda pre hľadanie kapitána podľa rodného čísla.
     * @param rc
     * @return objekt typu Kapitan alebo null
     */
    public Kapitan najdiKapitanaPodlaRC(String rc){
        for (Kapitan kapitan : zoznamKapitanov) {
            if(kapitan.getRC().equalsIgnoreCase(rc)){
                return kapitan;
            }
        }
        return null;
    }
    
    /**
     * Pridanie kapitána do zoznamu kapitánov
     * @param kapitan - objekt typu Kapitan
     */
    public void addKapitan(Kapitan kapitan){
        if(kapitan != null){
           zoznamKapitanov.add(kapitan); 
        }    
    }
    
    /**
     * Vymazanie kapitána zo zoznamu kapitánov pokiaľ existuje v zozname.
     * @param kapitan 
     */
    public void removeKapitan(Kapitan kapitan){
        if(zoznamKapitanov.contains(kapitan)){
            zoznamKapitanov.remove(kapitan);
        }
    }

    /**
     * Setter pre zoznam kapitánov
     * @param zoznamKapitanov 
     */
    public void setZoznamKapitanov(ArrayList<Kapitan> zoznamKapitanov) {
        this.zoznamKapitanov = zoznamKapitanov;
    }

    /**
     * Setter pre zoznam lietadiel.
     * @param zoznamLietadiel 
     */
    public void setZoznamLietadiel(ArrayList<Lietadla> zoznamLietadiel) {
        this.zoznamLietadiel = zoznamLietadiel;
    }

    /**
     * Setter pre zoznam letov.
     * @param zoznamLetov 
     */
    public void setZoznamLetov(ArrayList<Let> zoznamLetov) {
        this.zoznamLetov = zoznamLetov;
    }
    
    
    public static boolean load(File f){         
        return true;
    }
    
    public boolean save(File f){
        return true;
    }
    
    
    
    /**
     * 
     * @param f
     * @param _letisko
     * @return 
     */
    public static boolean save(File f, Letisko _letisko){
        try
        {           
            FileWriter fw = new FileWriter(f.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(_letisko.exportCSVLety());
            bw.close();
            return true;
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
    
    public String exportCSVLety(){
        String ret = "";
        
        ret += "\nZoznam letov\n" 
                + ";ID;Destinacia;Datum odletu;Kapitan;Typ lietadla;Kapacita lietadla";
        for (Let let : zoznamLetov) {
            ret += let.exportCSV();
        }
        ret += "\n\nZoznam cestujucich\n";
        ret += ";ID letu;Meno;Priezvisko;Rodne cislo\n";
        for (Let let : zoznamLetov) {
            for (Cestujuci cestujuci : let.getZoznamCestujucich()) {
                ret += ";"+let.getID()+";"+cestujuci.exportCSV() + "\n";
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        return  "\nZoznam kapitanov\n" 
                +";Meno;Priezvisko;Rodne cislo;Nalietane hodiny"               
                + zoznamKapitanov.toString()
                +";;;;;\n"
                + "\nZoznam lietadiel\n"
                +";Nazov lietadla; kapacita"
                + zoznamLietadiel.toString();               
    }
   
    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) {
        // TODO code application logic here
        Aplikacia a = new Aplikacia();
        a.show();
    }*/
    
}
