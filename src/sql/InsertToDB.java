/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import letisko.*;

/**
 * Trieda uchovávajúca statické metódy pre vkladanie do databázy.
 * @author Jakub Cachovan
 */
public class InsertToDB {
    
    /**
     * Statická metóda pre vykonanie rezervácie v databáze.
     * @param let
     * @param cestujuci
     * @param dbPath
     * @return true/false
     */
    public static boolean insertLetenka(Let let, Cestujuci cestujuci, String dbPath){
        try (Connection con = sql_connect.ConnectDB(dbPath);
            PreparedStatement state = con.prepareStatement("INSERT INTO Letenka(id,rodne_cislo) VALUES (?,?);");){
            state.setString(1, let.getID()+"");
            state.setString(2, cestujuci.getRC());
            state.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false;
    }
    
    /**
     * Statická metóda pre vloženie informácií o lete do databázy.
     * @param let
     * @param dbPath
     * @return true/false
     */
    public static boolean insertLet(Let let, String dbPath){
        try (Connection con = sql_connect.ConnectDB(dbPath);){                       
            PreparedStatement state = con.prepareStatement("INSERT INTO lety(destinacia, rc_kapitan, datum, lietadlo) VALUES (?,?,?,?);");       
            //SimpleDateFormat dFormat = new SimpleDateFormat("dd.MM.yyyy");
            String datumOdletu = new SimpleDateFormat("dd.MM.yyyy").format(let.getDatumOdletu());           
            state.setString(1, let.getDestinacia());
            state.setString(2, let.getKapitan().getRC());
            state.setString(3, datumOdletu);
            state.setString(4, let.getTypLietadla().name());               
            state.executeUpdate();
                
            state = con.prepareStatement("SELECT id from lety WHERE rc_kapitan=\""+let.getKapitan().getRC()+"\" AND datum=\""+datumOdletu+"\";");
            ResultSet rs = state.executeQuery();
            while(rs.next()){ 
                int idLetu = Integer.parseInt(rs.getString("id"));
                let.setID(idLetu);
            }
                con.close();
                return true;
            } catch (SQLException | NumberFormatException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        return false;
    }
    
    /**
     * Statická metóda pre vloženie cestujúceho do databázy.
     * @param cestujuci
     * @param dbPath
     * @return true/false
     */
    public static boolean insertCestujuceho(Cestujuci cestujuci, String dbPath){
        try (Connection con = sql_connect.ConnectDB(dbPath);){
                PreparedStatement state = con.prepareStatement("INSERT INTO cestujuci(rodne_cislo,meno,priezvisko) VALUES(?,?,?);");
                state.setString(1, cestujuci.getRC());
                state.setString(2, cestujuci.getMeno());
                state.setString(3, cestujuci.getPriezvisko());
                state.executeUpdate(); 
                con.close();
                return true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        return false;
    }
}
