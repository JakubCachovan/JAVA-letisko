/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import letisko.Cestujuci;

/**
 *
 * @author Acer
 */
public class DeleteFromDB {
    public static boolean zrusitLet(int idLetu, String destinacia, String dbPath){
        try (Connection con = sql_connect.ConnectDB(dbPath);){           
            
            PreparedStatement state = con.prepareStatement("DELETE FROM Letenka WHERE id=?;");
            state.setString(1, idLetu+"");
            state.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Všetky rezervácie vymazané !!!");       

            state = con.prepareStatement("DELETE FROM lety WHERE id=?;");
            state.setString(1, idLetu+"");
            state.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Let s ID " + idLetu + " bol úspešne zrušený !");
            
            con.close();
            return true;
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false;
    }
    
    public static boolean deleteCestujuceho(String rc_cestujuceho, String dbPath){
        if(maCestujuciLetenky(rc_cestujuceho, dbPath)){
            if(deleteLetenkyCestujuceho(rc_cestujuceho, dbPath)){
                JOptionPane.showMessageDialog(null, "Celá história rezervácii leteniek cestujúceho boli vymazané!");
            } 
        }
        try (Connection con = sql_connect.ConnectDB(dbPath);){
            PreparedStatement state = con.prepareStatement("DELETE FROM cestujuci WHERE rodne_cislo=?");
            state.setString(1, rc_cestujuceho);
            state.executeUpdate();
            con.close();
            return true;          
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false;
    }
    
    public static boolean deleteLetenkyCestujuceho(String rc_cestujuceho, String dbPath){
        try (Connection con = sql_connect.ConnectDB(dbPath);){
            PreparedStatement state = con.prepareStatement("DELETE from Letenka WHERE rodne_cislo=?;");
            state.setString(1, rc_cestujuceho);
            state.executeUpdate();
            con.close();
            return true;   
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false;
    }
    
    public static boolean maCestujuciLetenky(String rc_cestujuceho, String dbPath){
        int pocet = 0;
        try (Connection con = sql_connect.ConnectDB(dbPath);){
            PreparedStatement state = con.prepareStatement("SELECT count(*) as pocet FROM Letenka WHERE rodne_cislo=?;");
            state.setString(1, rc_cestujuceho);
            ResultSet rs = state.executeQuery();         
            while (rs.next()) {
                pocet = Integer.parseInt(rs.getString("pocet"));
            }
            con.close();                  
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return pocet > 0;  
    }
    
    
}
