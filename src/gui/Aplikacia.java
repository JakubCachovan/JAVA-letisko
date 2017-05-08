/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;
import java.awt.HeadlessException;
import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import javax.swing.table.DefaultTableModel;
import letisko.*;
import sql.*;
/**
 * 
 * @author Acer
 */
public final class Aplikacia extends javax.swing.JFrame {

    static String DbPath;
    static String FilePath;
    private Connection con = null;
    private ResultSet rs = null;   
    private Letisko _letisko = null;
    /**
     * Creates new form Aplikacia
     */
    public Aplikacia() {            
        initComponents(); 
        LoadDataDiaglog loadData = new LoadDataDiaglog(this, true);
        loadData.setLocationRelativeTo(null);
        loadData.setVisible(true);
        if(LoadDataDiaglog.isFromDB){
            DbPath = loadData.getPath();                            
        }else{
            System.exit(0);
        }         
        _letisko = LoadFromDB.nacitajLetisko(DbPath);       
        if(_letisko != null){
            nacitajVsetkyTabulky();
            volneMiestaLabel.setVisible(false);
        } 
        
        jTableCestujuci.setAutoCreateRowSorter(true);
        jTableDestinacie.setAutoCreateRowSorter(true);
        jTableKapitan.setAutoCreateRowSorter(true);
        jTableLetenky.setAutoCreateRowSorter(true);
        jTableLety.setAutoCreateRowSorter(true);
        jTableLietadla.setAutoCreateRowSorter(true);
        jTableZoznamCestujucich.setAutoCreateRowSorter(true);
    }
    
    public void nacitajVsetkyTabulky(){
        FillTableKapitan();    
        FillTableLety();
        FillTableCestujuci();
        FillTableLietadlo();
        FillTableZoznamLeteniek(); 
    }
    
    public void FillTableZoznamLeteniek(){
        DefaultTableModel m = (DefaultTableModel)jTableLetenky.getModel();
        m.setRowCount(0);
        for (Let let : _letisko.getZoznamLetov()) {
            for (Cestujuci cestujuci : let.getZoznamCestujucich()) {
                String [] row = {let.getID()+"",
                let.getDestinacia(),
                let.getDatumOdletu().toString(),
                cestujuci.getMeno(),
                cestujuci.getPriezvisko(),
                cestujuci.getRC()};
                m.addRow(row); 
            }       
        }    
    }
    public void FillTableZoznamCestujucich(){         
        int selectedRow = -1;
        selectedRow = jTableLety.getSelectedRow();
        if(selectedRow != -1){
            DefaultTableModel m = (DefaultTableModel)jTableLety.getModel();
            int idLetu = Integer.parseInt(m.getValueAt(selectedRow, 0).toString());

            DefaultTableModel modelCest = (DefaultTableModel)jTableZoznamCestujucich.getModel();  
            modelCest.setRowCount(0);
            for (Let let : _letisko.getZoznamLetov()) {
                if(let.getID() == idLetu){
                    for (Cestujuci cestujuci : let.getZoznamCestujucich()) {
                        String [] riadok = {cestujuci.getMeno(),cestujuci.getPriezvisko(),cestujuci.getRC()};
                        modelCest.addRow(riadok); 
                    }             
                }
            }       
        }
    }
    public void FillTableLety(){
        DefaultTableModel m = (DefaultTableModel)jTableLety.getModel();  
        m.setRowCount(0);
        for (Let let : _letisko.getZoznamLetov()) {
            String [] row = {let.getID()+"", 
                let.getDestinacia(), 
                let.getDatumOdletu().toString(), 
                let.getKapitan().getMeno() + " " + let.getKapitan().getPriezvisko(), 
                let.getTypLietadla().name()};
            m.addRow(row);
        }    
    }  
    public void FillTableKapitan(){
        DefaultTableModel m = (DefaultTableModel)jTableKapitan.getModel();
        m.setRowCount(0);
        for (Kapitan kapitan : _letisko.getZoznamKapitanov()) {
            String [] row = {kapitan.getMeno(),
                kapitan.getPriezvisko(),
                kapitan.getRC(),
                kapitan.getNalietaneHodiny()+""};
            m.addRow(row);
        }
    }  
    public void FillTableCestujuci(){
        try {
            con = sql_connect.ConnectDB(DbPath);
            Statement state = con.createStatement();
            rs = state.executeQuery("SELECT * FROM cestujuci");
            DefaultTableModel m = (DefaultTableModel)jTableCestujuci.getModel();
            m.setRowCount(0);
            while (rs.next()) {
               String [] row = {rs.getString("meno"), rs.getString("priezvisko"), rs.getString("rodne_cislo")};
               m.addRow(row);                
            } 
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }  
    public void FillTableLietadlo(){
        DefaultTableModel m = (DefaultTableModel)jTableLietadla.getModel();
        m.setRowCount(0);
        for (Lietadla lietadla : _letisko.getZoznamLietadiel()) {
            String [] row = {lietadla.name(), lietadla.getKapacita()+""};
            m.addRow(row);
        }       
    }         
    public void zobrazitDestinacie(){       
        int selectedRow = -1;
        selectedRow = jTableCestujuci.getSelectedRow();
        if(selectedRow != -1){
            DefaultTableModel m = (DefaultTableModel)jTableCestujuci.getModel();
            String rc = m.getValueAt(selectedRow, 2).toString();  

            DefaultTableModel modelDestinacie = (DefaultTableModel)jTableDestinacie.getModel(); 
            modelDestinacie.setRowCount(0);

            for (Let let : _letisko.getZoznamLetov()) {
                for (Cestujuci cestujuci : let.getZoznamCestujucich()) {
                    if(cestujuci.getRC().equalsIgnoreCase(rc)){
                        for (String destinacie : cestujuci.getDestinacie()) {
                            String [] riadok = {destinacie};
                            modelDestinacie.addRow(riadok);   
                        }                    
                    }                    
                }
                break;
            }
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jButtonZriadLet = new javax.swing.JButton();
        jButtonZrusLet = new javax.swing.JButton();
        jButtonHladajNajblizsiLet = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableLety = new javax.swing.JTable();
        jButtonRezervaciaLetenky = new javax.swing.JButton();
        jButtonZrusitRezervaciu = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTableZoznamCestujucich = new javax.swing.JTable();
        volneMiestaLabel = new javax.swing.JLabel();
        jButtonHladajCestujuceho = new javax.swing.JButton();
        jButtonOverRezervaciu = new javax.swing.JButton();
        jButtonUloz = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableLetenky = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jButtonObnovit = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableKapitan = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jButtonOdoberKapitana = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableLietadla = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButtonPridajCestujuceho = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableCestujuci = new javax.swing.JTable();
        jButtonVymazCestujuceho = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableDestinacie = new javax.swing.JTable();
        jButtonRefreshCestujuci = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Letisko");

        jButtonZriadLet.setIcon(new javax.swing.ImageIcon("C:\\Users\\Acer\\Documents\\NetBeansProjects\\Letisko\\icons\\plane.png")); // NOI18N
        jButtonZriadLet.setText("Zriaď let");
        jButtonZriadLet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonZriadLetActionPerformed(evt);
            }
        });

        jButtonZrusLet.setIcon(new javax.swing.ImageIcon("C:\\Users\\Acer\\Documents\\NetBeansProjects\\Letisko\\icons\\cancel.png")); // NOI18N
        jButtonZrusLet.setText("Zrušiť let");
        jButtonZrusLet.setEnabled(false);
        jButtonZrusLet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonZrusLetActionPerformed(evt);
            }
        });

        jButtonHladajNajblizsiLet.setIcon(new javax.swing.ImageIcon("C:\\Users\\Acer\\Documents\\NetBeansProjects\\Letisko\\icons\\search.png")); // NOI18N
        jButtonHladajNajblizsiLet.setText("Hľadaj najbližší let");
        jButtonHladajNajblizsiLet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHladajNajblizsiLetActionPerformed(evt);
            }
        });

        jTableLety.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Destinácia", "Dátum odletu", "Kapitán", "Lietadlo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableLety.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableLetyMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTableLety);

        jButtonRezervaciaLetenky.setIcon(new javax.swing.ImageIcon("C:\\Users\\Acer\\Documents\\NetBeansProjects\\Letisko\\icons\\rezervacia.png")); // NOI18N
        jButtonRezervaciaLetenky.setText("Rezervácia letenky");
        jButtonRezervaciaLetenky.setEnabled(false);
        jButtonRezervaciaLetenky.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRezervaciaLetenkyActionPerformed(evt);
            }
        });

        jButtonZrusitRezervaciu.setIcon(new javax.swing.ImageIcon("C:\\Users\\Acer\\Documents\\NetBeansProjects\\Letisko\\icons\\cancel.png")); // NOI18N
        jButtonZrusitRezervaciu.setText("Zrušiť rezerváciu");
        jButtonZrusitRezervaciu.setEnabled(false);
        jButtonZrusitRezervaciu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonZrusitRezervaciuActionPerformed(evt);
            }
        });

        jTableZoznamCestujucich.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Meno", "Priezvisko", "Rodné číslo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableZoznamCestujucich.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableZoznamCestujucichMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(jTableZoznamCestujucich);

        volneMiestaLabel.setText("Počet voľných miest: ?");

        jButtonHladajCestujuceho.setIcon(new javax.swing.ImageIcon("C:\\Users\\Acer\\Documents\\NetBeansProjects\\Letisko\\icons\\search.png")); // NOI18N
        jButtonHladajCestujuceho.setText("Hľadaj cestujúeho");
        jButtonHladajCestujuceho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHladajCestujucehoActionPerformed(evt);
            }
        });

        jButtonOverRezervaciu.setIcon(new javax.swing.ImageIcon("C:\\Users\\Acer\\Documents\\NetBeansProjects\\Letisko\\icons\\confirm.png")); // NOI18N
        jButtonOverRezervaciu.setText("Overenie rezervácie");
        jButtonOverRezervaciu.setEnabled(false);
        jButtonOverRezervaciu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOverRezervaciuActionPerformed(evt);
            }
        });

        jButtonUloz.setIcon(new javax.swing.ImageIcon("C:\\Users\\Acer\\Documents\\NetBeansProjects\\Letisko\\icons\\csv.png")); // NOI18N
        jButtonUloz.setText("Export CSV");
        jButtonUloz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUlozActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane5)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jButtonZriadLet)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButtonZrusLet)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButtonOverRezervaciu)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButtonHladajNajblizsiLet)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButtonHladajCestujuceho)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButtonRezervaciaLetenky)
                        .addGap(18, 18, 18)
                        .addComponent(volneMiestaLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButtonZrusitRezervaciu)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonUloz)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonOverRezervaciu, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonZriadLet)
                        .addComponent(jButtonZrusLet)
                        .addComponent(jButtonHladajNajblizsiLet)
                        .addComponent(jButtonHladajCestujuceho)
                        .addComponent(jButtonUloz)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                    .addComponent(jScrollPane7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRezervaciaLetenky)
                    .addComponent(jButtonZrusitRezervaciu)
                    .addComponent(volneMiestaLabel))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Letisko", jPanel4);

        jTableLetenky.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID - letu", "Destinácia", "Dátum odletu", "Meno", "Priezvisko", "Rodné číslo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Short.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTableLetenky);

        jLabel5.setText("Zoznam všetkých rezervovaných leteniek");

        jButtonObnovit.setIcon(new javax.swing.ImageIcon("C:\\Users\\Acer\\Documents\\NetBeansProjects\\Letisko\\icons\\obnovit.png")); // NOI18N
        jButtonObnovit.setText("Obnoviť");
        jButtonObnovit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonObnovitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1084, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonObnovit)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonObnovit)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Letenky", jPanel3);

        jTableKapitan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "meno", "priezvisko", "rodné číslo", "nalietané hodiny"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableKapitan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableKapitanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableKapitan);

        jButton5.setIcon(new javax.swing.ImageIcon("C:\\Users\\Acer\\Documents\\NetBeansProjects\\Letisko\\icons\\plus.png")); // NOI18N
        jButton5.setText("Pridaj");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButtonOdoberKapitana.setIcon(new javax.swing.ImageIcon("C:\\Users\\Acer\\Documents\\NetBeansProjects\\Letisko\\icons\\cancel.png")); // NOI18N
        jButtonOdoberKapitana.setText("Odober");
        jButtonOdoberKapitana.setEnabled(false);
        jButtonOdoberKapitana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOdoberKapitanaActionPerformed(evt);
            }
        });

        jTableLietadla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Názov", "Kapacita"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane6.setViewportView(jTableLietadla);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonOdoberKapitana)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOdoberKapitana)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Kapitáni", jPanel2);

        jButtonPridajCestujuceho.setIcon(new javax.swing.ImageIcon("C:\\Users\\Acer\\Documents\\NetBeansProjects\\Letisko\\icons\\plus.png")); // NOI18N
        jButtonPridajCestujuceho.setText("Pridaj cestujúceho do DB");
        jButtonPridajCestujuceho.setToolTipText("");
        jButtonPridajCestujuceho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPridajCestujucehoActionPerformed(evt);
            }
        });

        jTableCestujuci.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Meno", "Priezvisko", "Rodné číslo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableCestujuci.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableCestujuciMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableCestujuci);

        jButtonVymazCestujuceho.setIcon(new javax.swing.ImageIcon("C:\\Users\\Acer\\Documents\\NetBeansProjects\\Letisko\\icons\\cancel.png")); // NOI18N
        jButtonVymazCestujuceho.setText("Vymaž cestujúceho z DB");
        jButtonVymazCestujuceho.setEnabled(false);
        jButtonVymazCestujuceho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVymazCestujucehoActionPerformed(evt);
            }
        });

        jTableDestinacie.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Destinácie"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTableDestinacie);

        jButtonRefreshCestujuci.setIcon(new javax.swing.ImageIcon("C:\\Users\\Acer\\Documents\\NetBeansProjects\\Letisko\\icons\\obnovit.png")); // NOI18N
        jButtonRefreshCestujuci.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshCestujuciActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonPridajCestujuceho)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonVymazCestujuceho)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonRefreshCestujuci))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 719, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonPridajCestujuceho, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonVymazCestujuceho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonRefreshCestujuci, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Cestujúci", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1109, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonOdoberKapitanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOdoberKapitanaActionPerformed
        /*vymaz kapitana*/
        DefaultTableModel m = (DefaultTableModel)jTableKapitan.getModel();
        int selectedRow = -1;
        selectedRow = jTableKapitan.getSelectedRow();
        if(selectedRow != -1){
            try {
                con = sql_connect.ConnectDB(DbPath);           
                String kapitan = m.getValueAt(selectedRow, 2).toString();
                Statement state = con.createStatement();
                String sql = "DELETE FROM kapitan WHERE rodne_cislo=\""+kapitan+"\";";
                state.executeUpdate(sql);
                m.removeRow(selectedRow);
                _letisko.removeKapitan(_letisko.najdiKapitanaPodlaRC(kapitan));
                con.close();
                jButtonOdoberKapitana.setEnabled(false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Najprv označte kapitána v tabulke");
        }
        
    }//GEN-LAST:event_jButtonOdoberKapitanaActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        /*pridaj kapitána*/
        KapitanJDialog kapitanJDialog = new KapitanJDialog(this, true);
        kapitanJDialog.setLocationRelativeTo(null);
        kapitanJDialog.setVisible(true);

        Kapitan kapitan = kapitanJDialog.getKapitan();
        if(kapitan != null){
            try {
                /* uloz do DB*/
                con = sql_connect.ConnectDB(DbPath);
                Statement state = con.createStatement();
                String sql = "INSERT INTO \"main\".\"kapitan\" (\"naliet_hodiny\",\"rodne_cislo\",\"meno\",\"priezvisko\") VALUES ("+kapitan.getNalietaneHodiny()+",\""+kapitan.getRC()+"\",\""+kapitan.getMeno()+"\",\""+kapitan.getPriezvisko()+"\")";
                state.executeUpdate(sql);
                /* vloz do GUI */
                DefaultTableModel m = (DefaultTableModel)jTableKapitan.getModel();
                String [] row = {kapitan.getMeno(), kapitan.getPriezvisko(), kapitan.getRC(), ""+kapitan.getNalietaneHodiny()+""};
                m.addRow(row);
                _letisko.addKapitan(kapitan);
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButtonObnovitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonObnovitActionPerformed
        // TODO add your handling code here:
        FillTableZoznamLeteniek();
    }//GEN-LAST:event_jButtonObnovitActionPerformed

    private void jTableZoznamCestujucichMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableZoznamCestujucichMouseClicked

        int selectedRow = -1;
        selectedRow = jTableZoznamCestujucich.getSelectedRow();
        if(selectedRow != -1) {
            jButtonZrusitRezervaciu.setEnabled(true);                    
        }
    }//GEN-LAST:event_jTableZoznamCestujucichMouseClicked

    /**
     * @param evt 
     */
    private void jButtonZrusitRezervaciuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonZrusitRezervaciuActionPerformed
        //vymazanie z letenky
        //vymazanie z destinacie
        DefaultTableModel cest = (DefaultTableModel)jTableZoznamCestujucich.getModel();
        int selectedRowCest = jTableZoznamCestujucich.getSelectedRow();

        DefaultTableModel lety = (DefaultTableModel)jTableLety.getModel();
        int selectedRowLety = jTableLety.getSelectedRow();

        String letID = lety.getValueAt(selectedRowLety, 0).toString();
        String destinacia = lety.getValueAt(selectedRowLety, 1).toString();
        String rc = cest.getValueAt(selectedRowCest, 2).toString();

        for (Let let : _letisko.getZoznamLetov()) {
            let.zrusRezervaciu(rc);
        }
        
        try {
            con = sql_connect.ConnectDB(DbPath);
            Statement state = con.createStatement();
            String sql = "DELETE FROM Letenka WHERE id=\""+letID+"\" AND rodne_cislo=\""+rc+"\";";
            state.executeUpdate(sql);
            /*vymazanie z gui*/
            cest.removeRow(selectedRowCest);
            con.close();
            JOptionPane.showMessageDialog(null, "Rezervácia pre let s ID " + letID + " bola úspešne zrušená !", "Úspech", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }finally{
            loadPocetVolnychMiest(selectedRowLety);
        }
    }//GEN-LAST:event_jButtonZrusitRezervaciuActionPerformed

    private void jButtonRezervaciaLetenkyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRezervaciaLetenkyActionPerformed
        /* rezervacia letenky */
        RezervaciaJDialog rezervaciaJDialog = new RezervaciaJDialog(this, true);
        rezervaciaJDialog.setLocationRelativeTo(null);
        rezervaciaJDialog.setVisible(true);
        Cestujuci cestujuci = rezervaciaJDialog.getCestujuci();
        DefaultTableModel m = (DefaultTableModel)jTableLety.getModel();
        int selectedRow = jTableLety.getSelectedRow();
        String letID = m.getValueAt(selectedRow, 0).toString();
        if(cestujuci != null){
            for (Let let : _letisko.getZoznamLetov()) {
                if(let.getID() == Integer.parseInt(letID)){
                    let.rezervujLetenku(let.getDatumOdletu(), cestujuci);
                    if(InsertToDB.insertLetenka(let, cestujuci, DbPath)){
                        FillTableZoznamCestujucich();
                        FillTableZoznamCestujucich();
                        JOptionPane.showMessageDialog(null, "Letenka pre let " +letID+" úspešne rezervovaná !");
                        loadPocetVolnychMiest(selectedRow);
                    }
                }
            }
        }
    }//GEN-LAST:event_jButtonRezervaciaLetenkyActionPerformed

    public int loadPocetVolnychMiest(int selectedRow){
        DefaultTableModel m = (DefaultTableModel)jTableLety.getModel();
        String lietadlo = m.getValueAt(selectedRow, 4).toString();
        String idLetu = m.getValueAt(selectedRow, 0).toString();
        int pocet = 0;
        
        int kapacita = Lietadla.valueOf(lietadlo).getKapacita();
        
        try {
            con = sql_connect.ConnectDB(DbPath);
            Statement state = con.createStatement();
            String sql = "SELECT count(id) as pocet from Letenka WHERE id=\""+idLetu+"\" GROUP BY id;";
            rs = state.executeQuery(sql);                  
            while (rs.next()) {                        
                pocet = rs.getInt("pocet");//getInt("pocet");
            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }    
        volneMiestaLabel.setText("Počet voľných miest "+(kapacita-pocet)+"");    
        return (kapacita-pocet);
    }
      
    private void jTableLetyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableLetyMouseClicked
        int selectedRow = -1;
        selectedRow = jTableLety.getSelectedRow();
        if(selectedRow != -1) {
            jButtonZrusLet.setEnabled(true);        
            if(loadPocetVolnychMiest(selectedRow) > 0) {
                jButtonRezervaciaLetenky.setEnabled(true);            
            }else{
                jButtonRezervaciaLetenky.setEnabled(false);
            }
            volneMiestaLabel.setVisible(true);
            FillTableZoznamCestujucich();
            jButtonZrusitRezervaciu.setEnabled(false);
            jButtonOverRezervaciu.setEnabled(true);
        }
    }//GEN-LAST:event_jTableLetyMouseClicked

    private void jButtonHladajNajblizsiLetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHladajNajblizsiLetActionPerformed
        //hladaj najblizsi let
        HladajLetJDialog hladaj = new HladajLetJDialog(this, true);
        hladaj.setLocationRelativeTo(null);
        hladaj.setVisible(true);
        String destinacia = hladaj.getDestinacia();  
        Date datum = null;
        if(null != hladaj.getDatum()){
            datum = new Date(hladaj.getDatum().getTime());
        }     
        if(!destinacia.isEmpty() && datum != null){
            Let najblizsiLet = _letisko.najdiNajBlizsiLet(datum, destinacia);
            if(najblizsiLet != null){
                JOptionPane.showMessageDialog(null, "Najbližší let do \n\ndestinácia: \""+destinacia+"\" \nID letu: \""+najblizsiLet.getID()+"\"\ndátum odletu: \""+najblizsiLet.getDatumOdletu()+"\"\n\n", "Výsledok hľadania...", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Let pre do destináciu v zadanom datume sa nenašiel", "Výsledok hľadania...", JOptionPane.WARNING_MESSAGE);
            }
        }
        
    }//GEN-LAST:event_jButtonHladajNajblizsiLetActionPerformed

    private void jButtonZrusLetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonZrusLetActionPerformed
        /* zrušiť let */
        // vymazanie vsetkych rezervácií pre daný let
        // vymaž destináciu pre cestujúceho
        // vymazanie letu
        
        DefaultTableModel model = (DefaultTableModel)jTableLety.getModel();
        int selectedRow = jTableLety.getSelectedRow();
        String letID = model.getValueAt(selectedRow, 0).toString();
        String destinacia = model.getValueAt(selectedRow, 1).toString();
        _letisko.zrusLet(Integer.parseInt(letID));
        if(DeleteFromDB.zrusitLet(Integer.parseInt(letID), destinacia, DbPath)){
            JOptionPane.showMessageDialog(null, "Let s ID " + letID + " bol úspešne zrušený !");
            DefaultTableModel modelCest = (DefaultTableModel)jTableZoznamCestujucich.getModel();
            modelCest.setRowCount(0);
            jButtonZrusLet.setEnabled(false);
            jButtonRezervaciaLetenky.setEnabled(false);
            jButtonOverRezervaciu.setEnabled(false);
            model.removeRow(selectedRow);
        }
    }//GEN-LAST:event_jButtonZrusLetActionPerformed

    private void jButtonZriadLetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonZriadLetActionPerformed
        /* zriadenie letu na letisku*/
        LetJDialog letJDialog = new LetJDialog(this, true);
        letJDialog.setLocationRelativeTo(null);
        letJDialog.setVisible(true);

        Let let = letJDialog.getLet();
        if(let != null){   
            _letisko.zriadLet(let.getDestinacia(), let.getDatumOdletu(), let.getKapitan(), let.getTypLietadla());
            if(InsertToDB.insertLet(let, DbPath)){
                /*vloz do GUI */
                DefaultTableModel m = (DefaultTableModel)jTableLety.getModel();
                String datumOdletu = new SimpleDateFormat("dd.MM.yyyy").format(let.getDatumOdletu());
                String [] row = {let.getID()+"", let.getDestinacia(), datumOdletu, let.getKapitan().getMeno()+" "+let.getKapitan().getPriezvisko(), let.getTypLietadla().name()};
                m.addRow(row);
            } 
        }
    }//GEN-LAST:event_jButtonZriadLetActionPerformed
  
    
    private void jButtonHladajCestujucehoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHladajCestujucehoActionPerformed

        HladajCestDialog cestDialog = new HladajCestDialog(this, true);
        cestDialog.setLocationRelativeTo(null);
        cestDialog.setVisible(true);
        Cestujuci najdeny = null;
        if(cestDialog.getMeno() != "" && cestDialog.getPriezvisko() != ""){
            String meno = cestDialog.getMeno();
            String priezvisko = cestDialog.getPriezvisko();       
            try {
                con = sql_connect.ConnectDB(DbPath);
                Statement state = con.createStatement();
                rs = state.executeQuery("SELECT * from cestujuci WHERE meno=\""+meno+"\"AND priezvisko=\""+priezvisko+"\";");
                while (rs.next()) {                    
                    najdeny = new Cestujuci(rs.getString("meno"), rs.getString("priezvisko"), rs.getString("rodne_cislo"));
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                JOptionPane.showMessageDialog(null, "Zadaný cestujúci sa nenašiel !", "Varovanie", JOptionPane.WARNING_MESSAGE);
            }              
        }else if(!cestDialog.getRc().equalsIgnoreCase("")){
            String rc = cestDialog.getRc();
            try {
                con = sql_connect.ConnectDB(DbPath);
                Statement state = con.createStatement();
                rs = state.executeQuery("SELECT * from cestujuci WHERE rodne_cislo=\""+rc+"\";");
                while (rs.next()) {                    
                    najdeny = new Cestujuci(rs.getString("meno"), rs.getString("priezvisko"), rs.getString("rodne_cislo"));
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                JOptionPane.showMessageDialog(null, "Zadaný cestujúci sa nenašiel !");
            }    
        }
        if(najdeny != null){
            PrehladCestujucehoDialog prehlad = new PrehladCestujucehoDialog(this, true);
            prehlad.setLocationRelativeTo(null);          
            prehlad.setCestujuci(najdeny);
            prehlad.nacitajCestujuceho();
            prehlad.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "Zadaný cestujúci sa nenašiel !", "Varovanie", JOptionPane.WARNING_MESSAGE);
        }
                
    }//GEN-LAST:event_jButtonHladajCestujucehoActionPerformed

    private void jButtonOverRezervaciuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOverRezervaciuActionPerformed
        // TODO add your handling code here:
        HladajRezervaciu rezDialog = new HladajRezervaciu(this, true);
        rezDialog.setLocationRelativeTo(null);
        rezDialog.setVisible(true);
        if(!rezDialog.getRc().isEmpty()){
            String rc = rezDialog.getRc();
            DefaultTableModel m = (DefaultTableModel)jTableLety.getModel();
            int selRow = jTableLety.getSelectedRow();
            String idLetu = m.getValueAt(selRow, 0).toString();
            boolean najdeny = _letisko.najdiCestujuceho(rc, Integer.parseInt(idLetu));
            if(najdeny){
                 JOptionPane.showMessageDialog(null, "OK! cestujúci s RC: "+rc+" má rezervovaný let "+idLetu);
            }else{
                JOptionPane.showMessageDialog(null, "Lutujeme! rezervácia sa nenašla...");
            }           
        }    
    }//GEN-LAST:event_jButtonOverRezervaciuActionPerformed

    private void jTableCestujuciMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCestujuciMouseClicked
        int selectedRow = -1;
        selectedRow = jTableCestujuci.getSelectedRow();
        if(selectedRow != -1){
            zobrazitDestinacie();
            jButtonVymazCestujuceho.setEnabled(true);
        }
    }//GEN-LAST:event_jTableCestujuciMouseClicked

    private void jButtonPridajCestujucehoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPridajCestujucehoActionPerformed
        /*pridaj cestujuceho*/
        CestujuciJDialog cestujuciJDialog = new CestujuciJDialog(this, true);
        cestujuciJDialog.setLocationRelativeTo(null);
        cestujuciJDialog.setVisible(true);

        Cestujuci cestujuci = cestujuciJDialog.getCestujuci();
        if(cestujuci != null){
            if(InsertToDB.insertCestujuceho(cestujuci, DbPath)){
                /*vloz do GUI */
                DefaultTableModel m = (DefaultTableModel)jTableCestujuci.getModel();
                String [] row = {cestujuci.getMeno(), cestujuci.getPriezvisko(), cestujuci.getRC()};
                m.addRow(row);
            }
        }
    }//GEN-LAST:event_jButtonPridajCestujucehoActionPerformed

    private void jButtonVymazCestujucehoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVymazCestujucehoActionPerformed
      
        int row = jTableCestujuci.getSelectedRow();
        DefaultTableModel m = (DefaultTableModel)jTableCestujuci.getModel();
        String rc = m.getValueAt(row, 2).toString();
        int pocetVymazanychLetov = 0;
        pocetVymazanychLetov = _letisko.vymazLetyCestujuceho(rc);
        JOptionPane.showMessageDialog(null, "Bolo vymazanych " + pocetVymazanychLetov + " letov");
        
        if(DeleteFromDB.deleteCestujuceho(rc, DbPath)){
            m.removeRow(row);
            JOptionPane.showMessageDialog(null, "Cestujúci bol vymazaný z DB");
            jButtonVymazCestujuceho.setEnabled(false);
            nacitajVsetkyTabulky();
        }
        
    }//GEN-LAST:event_jButtonVymazCestujucehoActionPerformed

    private void jTableKapitanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableKapitanMouseClicked
        // TODO add your handling code here:
        int selectedRow = -1;
        selectedRow = jTableKapitan.getSelectedRow();
        if(selectedRow != -1){           
            jButtonOdoberKapitana.setEnabled(true);
        }
    }//GEN-LAST:event_jTableKapitanMouseClicked
  
    
    /**
     * 
     * @param evt 
     */
    private void jButtonUlozActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUlozActionPerformed
        // TODO add your handling code here:
        try {
            JFileChooser fileChooser = new JFileChooser("./");
            if (fileChooser.showSaveDialog(this)== JFileChooser.APPROVE_OPTION) {
              File file = fileChooser.getSelectedFile();
              String filePath = file.getAbsolutePath();
              if(Letisko.save(file, _letisko)){
                  JOptionPane.showMessageDialog(null, "Uloženie úspešné!", "Info", JOptionPane.INFORMATION_MESSAGE);
              }else{
                  JOptionPane.showMessageDialog(null, "Chyba pri ukladaní do súboru", "Chyba", JOptionPane.ERROR_MESSAGE);
              }
            }               
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButtonUlozActionPerformed

    private void jButtonRefreshCestujuciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshCestujuciActionPerformed
        // TODO add your handling code here:
        FillTableCestujuci();
    }//GEN-LAST:event_jButtonRefreshCestujuciActionPerformed
  
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Aplikacia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Aplikacia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Aplikacia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Aplikacia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {                
                //new Aplikacia().setVisible(true);
                Aplikacia aplikacia = new Aplikacia();
                aplikacia.setLocationRelativeTo(null);
                aplikacia.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonHladajCestujuceho;
    private javax.swing.JButton jButtonHladajNajblizsiLet;
    private javax.swing.JButton jButtonObnovit;
    private javax.swing.JButton jButtonOdoberKapitana;
    private javax.swing.JButton jButtonOverRezervaciu;
    private javax.swing.JButton jButtonPridajCestujuceho;
    private javax.swing.JButton jButtonRefreshCestujuci;
    private javax.swing.JButton jButtonRezervaciaLetenky;
    private javax.swing.JButton jButtonUloz;
    private javax.swing.JButton jButtonVymazCestujuceho;
    private javax.swing.JButton jButtonZriadLet;
    private javax.swing.JButton jButtonZrusLet;
    private javax.swing.JButton jButtonZrusitRezervaciu;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableCestujuci;
    private javax.swing.JTable jTableDestinacie;
    private javax.swing.JTable jTableKapitan;
    private javax.swing.JTable jTableLetenky;
    private javax.swing.JTable jTableLety;
    private javax.swing.JTable jTableLietadla;
    private javax.swing.JTable jTableZoznamCestujucich;
    private javax.swing.JLabel volneMiestaLabel;
    // End of variables declaration//GEN-END:variables
}
