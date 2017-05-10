package gui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import letisko.Kapitan;
import letisko.Let;
import letisko.Lietadla;
import sql.sql_connect;

/**
 * 
 * @author Jakub Cachovan
 */
public class LetJDialog extends javax.swing.JDialog {

    private ResultSet rs = null;
    private Let let = null;
    private Kapitan kapitan = null;
    private String dbPath;
    /**
     * Creates new form LetJDialog
     */
    public LetJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();         
    }

    /**
     * Setter pre adresu databázy
     * @param dbPath 
     */
    public void setDbPath(String dbPath) {
        this.dbPath = dbPath;
    }
   
    /**
     * Getter pre objekt typu Let.
     * @return 
     */
    public Let getLet(){
        return let;
    }
    
    /**
     * Načítanie prvku combobox priezviskom kapitánov.
     */
    public void nacitajKapitanovFromDB(){
        try (Connection con = sql_connect.ConnectDB(this.dbPath);
                PreparedStatement state = con.prepareStatement("SELECT * from kapitan");){   
            rs = state.executeQuery();
            while(rs.next()){
                jComboBoxKapitan.addItem(rs.getString("priezvisko"));
            }   
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * Načítanie prvku combobox názvami lietadiel.
     */
    public void nacitajLietadlaFromDB(){
        try (Connection con = sql_connect.ConnectDB(this.dbPath);
                PreparedStatement state = con.prepareStatement("SELECT nazov from lietadlo");) {
            rs = state.executeQuery();
            while(rs.next()){
                jComboBoxLietadlo.addItem(rs.getString("nazov"));
            } 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }   
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextDestinacia = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jButtonZriadLet = new javax.swing.JButton();
        jComboBoxKapitan = new javax.swing.JComboBox<>();
        jComboBoxLietadlo = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Zriadenie letu");

        jLabel1.setText("Destinácia");

        jLabel2.setText("Dátum odletu");

        jLabel3.setText("Kapitán");

        jLabel4.setText("Lietadlo");

        jButtonZriadLet.setText("Zriadiť let");
        jButtonZriadLet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonZriadLetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextDestinacia, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBoxKapitan, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBoxLietadlo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButtonZriadLet, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextDestinacia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxKapitan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxLietadlo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonZriadLet, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * 
     * @param evt 
     */
    private void jButtonZriadLetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonZriadLetActionPerformed
        try (Connection con = sql_connect.ConnectDB(this.dbPath);
                PreparedStatement state = con.prepareStatement("SELECT * from kapitan where priezvisko=?;");) { 
            state.setString(1, jComboBoxKapitan.getSelectedItem().toString());
            rs = state.executeQuery();
            kapitan = new Kapitan(Integer.parseInt(rs.getString("naliet_hodiny")), rs.getString("meno"), rs.getString("priezvisko"), rs.getString("rodne_cislo"));   
            java.sql.Date datumOdletu = new Date(jDateChooser1.getDate().getTime());
            let = new Let(jTextDestinacia.getText(), kapitan , datumOdletu, Lietadla.valueOf(jComboBoxLietadlo.getSelectedItem().toString()));
            dispose();
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButtonZriadLetActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonZriadLet;
    private javax.swing.JComboBox<String> jComboBoxKapitan;
    private javax.swing.JComboBox<String> jComboBoxLietadlo;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField jTextDestinacia;
    // End of variables declaration//GEN-END:variables
}
