/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Acer
 */
public class LoadDataDiaglog extends javax.swing.JDialog {

    private String path;
    private File file;
    static boolean isFromDB = false;
    /**
     * Creates new form LoadDataDiaglog
     */
    public LoadDataDiaglog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        jButtonDefault.setIcon(new ImageIcon("./icons/database.png"));
        jButtonNacitaj.setIcon(new ImageIcon("./icons/refreshing.png"));
        jButtonVybrat.setIcon(new ImageIcon("./icons/more.png"));
    }

    public String getPath() {
        return path;
    }

    public File getFile() {
        return file;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jButtonVybrat = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButtonNacitaj = new javax.swing.JButton();
        jButtonDefault = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Načítanie dát");

        jButtonVybrat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVybratActionPerformed(evt);
            }
        });

        jLabel1.setText("Vyberte súbor pre načítanie dát pre letisko");

        jButtonNacitaj.setIcon(new javax.swing.ImageIcon("C:\\Users\\Acer\\Documents\\NetBeansProjects\\Letisko\\icons\\refreshing.png")); // NOI18N
        jButtonNacitaj.setText("Načítať");
        jButtonNacitaj.setEnabled(false);
        jButtonNacitaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNacitajActionPerformed(evt);
            }
        });

        jButtonDefault.setText("Vybrať predvolené");
        jButtonDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDefaultActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonVybrat, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
                    .addComponent(jButtonDefault, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonNacitaj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonDefault, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonVybrat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonNacitaj, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonVybratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVybratActionPerformed
     
        try {
            JFileChooser chooser = new JFileChooser("./");
            chooser.showOpenDialog(null);            
            file = chooser.getSelectedFile();
            path = file.getAbsolutePath();
            jTextField1.setText(path);
            jButtonNacitaj.setEnabled(true);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Nebol vybratý žiadny súbor!", "Chyba", JOptionPane.ERROR_MESSAGE);
        }   
    }//GEN-LAST:event_jButtonVybratActionPerformed

    private void jButtonNacitajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNacitajActionPerformed
        Pattern p = Pattern.compile("(.sqlite)");
        Matcher m = p.matcher(path);   
        if(m.find()){
            //from DB
            isFromDB = true;
            dispose();
        }else{
            JOptionPane.showMessageDialog(null, "Nepodporovaný typ súboru !");
        }
    }//GEN-LAST:event_jButtonNacitajActionPerformed

    private void jButtonDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDefaultActionPerformed

        path = "C:\\Users\\Acer\\Documents\\NetBeansProjects\\Letisko\\letisko.db.sqlite";       
        file = new File(path);
        if(file.exists() && !file.isDirectory()) { 
            isFromDB = true;
            dispose();
        }else{
            JOptionPane.showMessageDialog(null, "Predvolené načítavanie nie je dostupné pre toto PC");
        }
        
    }//GEN-LAST:event_jButtonDefaultActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDefault;
    private javax.swing.JButton jButtonNacitaj;
    private javax.swing.JButton jButtonVybrat;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
