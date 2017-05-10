/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import letisko.*;

/**
 * Trieda uchovávajúca statické metódy pre načítanie objektu typu Letisko z databázy.
 * @author Jakub Cachovan
 */
public class LoadFromDB {
    
    /**
     * Statická metóda pre načítanie objektu typu Letisko z databázy.
     * @param dbPath
     * @return 
     */
    public static Letisko nacitajLetisko(String dbPath){
        Letisko letisko = new Letisko();
        letisko.setZoznamKapitanov(nacitajKapitanov(dbPath));
        letisko.setZoznamLetov(nacitajLety(dbPath));
        return letisko;
    }
    
    /**
     * Statická metóda pre načítanie objektu typu Kapitán z databázy na základe rodného čísla kapitána.
     * @param rc - rodné číslo kapitána
     * @param dbPath - cesta k databáze
     * @return 
     */
    public static Kapitan najdiKapitanaPodlaRC(String rc, String dbPath){
        try (Connection con = sql_connect.ConnectDB(dbPath);) {           
            PreparedStatement state = con.prepareStatement("SELECT * from kapitan WHERE rodne_cislo=?");
            state.setString(1, rc);
            ResultSet rs = state.executeQuery();
            String meno, priezvisko;
            int nalietaneHodiny;
            Kapitan kapitan = null;
            while (rs.next()) {           
                nalietaneHodiny = Integer.parseInt(rs.getString("naliet_hodiny"));
                meno = rs.getString("meno");
                priezvisko = rs.getString("priezvisko");
                kapitan = new Kapitan(nalietaneHodiny, meno, priezvisko, rc);
            }            
            con.close();
            return kapitan;
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
    
    /**
     * Statická metóda pre načítanie všetkých letov z databázy.
     * @param dbPath
     * @return 
     */
    public static ArrayList<Let> nacitajLety(String dbPath){
        ArrayList<Let> nacitaneLety = new ArrayList<>();
        try (Connection con = sql_connect.ConnectDB(dbPath);){
            Statement state = con.createStatement();
            String sql = "SELECT * from lety;";
            ResultSet rs = state.executeQuery(sql);
            int id_letu;
            String destinacia;
            String rcKapitana;
            String lietadlo;
            String sqlDatum;
            
            while (rs.next()) {                
                id_letu = Integer.parseInt(rs.getString("id"));
                destinacia = rs.getString("destinacia");
                rcKapitana = rs.getString("rc_kapitan");
                lietadlo = rs.getString("lietadlo");
                sqlDatum = rs.getString("datum");
                DateFormat format = new SimpleDateFormat("dd.MM.yyyy");  
                int den = format.parse(sqlDatum).getDate();
                int mesiac = format.parse(sqlDatum).getMonth();
                int rok = format.parse(sqlDatum).getYear();
                Date datum = new Date(rok, mesiac, den);   
                Let let = new Let(destinacia, 
                        najdiKapitanaPodlaRC(rcKapitana, dbPath), 
                        datum, 
                        Lietadla.valueOf(lietadlo));
                let.setID(id_letu);
                nacitaneLety.add(let);
            }           
            con.close(); 
            // nacitanie cestujucich pre konkretny let
            for (Let let : nacitaneLety) {
                let.setZoznamCestujucich(nacitajCestujucichLetu(let.getID(), dbPath));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
        return nacitaneLety;
    }
    
    /**
     * Statická metóda pre načítanie všetkých kapitánov z databázy.
     * @param dbPath
     * @return 
     */
    public static ArrayList<Kapitan> nacitajKapitanov(String dbPath){
        ArrayList<Kapitan> nacitanyKapitani = new ArrayList<>();
        try (Connection con = sql_connect.ConnectDB(dbPath);){
            Statement state = con.createStatement();
            String sql = "SELECT * from kapitan;";
            ResultSet rs = state.executeQuery(sql);
            int naliet_hodiny;
            String meno;
            String priezvisko;
            String rc;            
            while (rs.next()) {                
                naliet_hodiny = Integer.parseInt(rs.getString("naliet_hodiny"));
                meno = rs.getString("meno");
                priezvisko = rs.getString("priezvisko");
                rc = rs.getString("rodne_cislo");    
                Kapitan kapitan = new Kapitan(naliet_hodiny, meno, priezvisko, rc);
                nacitanyKapitani.add(kapitan);
            }            
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
        return nacitanyKapitani;
    }
    
    /**
     * Statická metóda pre načítanie všetkých cestujúcich z databázy na základne id letu.
     * @param idLetu
     * @param dbPath
     * @return 
     */
    public static ArrayList<Cestujuci> nacitajCestujucichLetu(int idLetu, String dbPath){
        ArrayList<Cestujuci> nacitanyCestujuci = new ArrayList<>();
        try (Connection con = sql_connect.ConnectDB(dbPath);){
            String sql = "SELECT * from cestujuci JOIN Letenka USING (rodne_cislo) JOIN lety USING (id) where id=?;";            
            PreparedStatement state = con.prepareStatement(sql);
            state.setString(1, idLetu+"");
            ResultSet rs = state.executeQuery();
            String meno;
            String priezvisko;
            String rc;            
            while (rs.next()) {                
                meno = rs.getString("meno");
                priezvisko = rs.getString("priezvisko");
                rc = rs.getString("rodne_cislo");    
                Cestujuci cestujuci = new Cestujuci(meno, priezvisko, rc);                
                nacitanyCestujuci.add(cestujuci);
            }            
            con.close();
            priradDestinacieCestujucim(nacitanyCestujuci, dbPath);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
        return nacitanyCestujuci;
    }
    
    /**
     * Statická metóda pre nastavenie destinácii pre cestujúcich na základe letov, ktoré majú rezervované.
     * @param zoznamCestujucich
     * @param dbPath 
     */
    public static void priradDestinacieCestujucim(ArrayList<Cestujuci> zoznamCestujucich, String dbPath){      
        for (Cestujuci cestujuci : zoznamCestujucich) {
            try (Connection con = sql_connect.ConnectDB(dbPath);){
                    PreparedStatement state = con.prepareStatement("SELECT destinacia from lety JOIN Letenka USING (id) JOIN cestujuci USING (rodne_cislo) WHERE rodne_cislo=?;");
                    state.setString(1, cestujuci.getRC());
                    ResultSet rs = state.executeQuery();
                    ArrayList<String> zoznamDestinacii = new ArrayList<>();
                    while(rs.next()){
                        zoznamDestinacii.add(rs.getString("destinacia"));
                    }
                    cestujuci.setDestinacie(zoznamDestinacii);

                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }  
        }
    }
}
