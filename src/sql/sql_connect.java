/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Acer
 */
public class sql_connect {

    Connection conn = null;

    public static Connection ConnectDB(String dbPath) {
        try {
            Class.forName("org.sqlite.JDBC");
            //Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Acer\\Documents\\NetBeansProjects\\Letisko\\letisko.db.sqlite");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:"+dbPath);
            //JOptionPane.showMessageDialog(null, "Pripojenie úspešné !");
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}
