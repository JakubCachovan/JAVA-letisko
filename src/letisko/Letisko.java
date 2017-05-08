/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * 
 * @author Jakub Cachovan
 */
public class Letisko implements Serializable {

    private ArrayList<Kapitan> zoznamKapitanov;
    private ArrayList<Lietadla> zoznamLietadiel;
    private ArrayList<Let> zoznamLetov;

    public Letisko() {
        zoznamKapitanov = new ArrayList<>();
        zoznamLetov = new ArrayList<>();
        zoznamLietadiel = new ArrayList<>();

        zoznamLietadiel.addAll(Arrays.asList(Lietadla.values()));
       
    }
    
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
    
    public boolean setCestujucichPreLet(int idLetu, ArrayList<Cestujuci> cestujuci){
        for (Let let : zoznamLetov) {
            if(let.getID() == idLetu){
                let.setZoznamCestujucich(cestujuci);
                return true;
            }
        }
        return false;
    }
    
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
    
    public Cestujuci najdiCestujuceho(String meno, String priezvisko){
        Cestujuci najdeny = null;
        for (Let let : zoznamLetov) {
            najdeny = let.vyhladajCestujuceho(meno, priezvisko);
            return najdeny;
        }
        return null;
    }
    
    public Cestujuci najdiCestujuceho(String rc){
        Cestujuci najdeny = null;
        for (Let let : zoznamLetov) {
            najdeny = let.vyhladajCestujuceho(rc);
            return najdeny;
        }
        return null;
    }
    
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
    
    public Let najdiNajBlizsiLet(Date datum, String destinacia){
        Date najblizsiDatum;           
        Date aktualDatum = Date.valueOf(LocalDate.now());
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

    public ArrayList<Kapitan> getZoznamKapitanov() {
        return zoznamKapitanov;
    }

    public ArrayList<Lietadla> getZoznamLietadiel() {
        return zoznamLietadiel;
    }

    public ArrayList<Let> getZoznamLetov() {
        return zoznamLetov;
    }
    
    public Kapitan najdiKapitanaPodlaRC(String rc){
        for (Kapitan kapitan : zoznamKapitanov) {
            if(kapitan.getRC().equalsIgnoreCase(rc)){
                return kapitan;
            }
        }
        return null;
    }
    
    public void addKapitan(Kapitan kapitan){
        if(kapitan != null){
           zoznamKapitanov.add(kapitan); 
        }    
    }
    
    public void removeKapitan(Kapitan kapitan){
        if(zoznamKapitanov.contains(kapitan)){
            zoznamKapitanov.remove(kapitan);
        }
    }

    public void setZoznamKapitanov(ArrayList<Kapitan> zoznamKapitanov) {
        this.zoznamKapitanov = zoznamKapitanov;
    }

    public void setZoznamLietadiel(ArrayList<Lietadla> zoznamLietadiel) {
        this.zoznamLietadiel = zoznamLietadiel;
    }

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
