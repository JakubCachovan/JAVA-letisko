package gui;
import java.awt.HeadlessException;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import letisko.*;
import sql.*;

/**
 * Hlavná trieda, obsahujúca vizuálne prvky pre aplikáciu
 * @author Jakub Cachovan
 */
public final class Aplikacia extends javax.swing.JFrame {

    private String dbPath;
    private ResultSet rs = null;   
    private Letisko _letisko = null;
    
    /**
     * Konštruktor, pre inicializáciu GUI prvkov ako aj ich nastavenie.
     * Umožňuje vybrať zdroj dát (databáza alebo súbor). 
     * Zabezpečuje načítanie kľučového objektu typu Letisko pomocou statickej metódy nacitajLetisko(dbPath:String),
     * ktorá čerpá informácie z databázy.
     */
    public Aplikacia() {            
        initComponents(); 
        LoadDataDiaglog loadData = new LoadDataDiaglog(this, true);
        loadData.setLocationRelativeTo(null);
        loadData.setVisible(true);
        if(LoadDataDiaglog.isFromDB){
            dbPath = loadData.getPath();                            
        }else{
            System.exit(0);
        }         
        _letisko = LoadFromDB.nacitajLetisko(dbPath);
        nacitajVsetkyTabulky();
        volneMiestaLabel.setVisible(false);
        
        jTableCestujuci.setAutoCreateRowSorter(true);
        jTableDestinacie.setAutoCreateRowSorter(true);
        jTableKapitan.setAutoCreateRowSorter(true);
        jTableLetenky.setAutoCreateRowSorter(true);
        jTableLety.setAutoCreateRowSorter(true);
        jTableLietadla.setAutoCreateRowSorter(true);
        jTableZoznamCestujucich.setAutoCreateRowSorter(true);
        loadIcons();
    }
    
    /**
     * Načítanie ikon pre buttons.
     */
    public void loadIcons(){
        jButtonHladajCestujuceho.setIcon(new ImageIcon("./icons/search.png"));
        jButtonHladajNajblizsiLet.setIcon(new ImageIcon("./icons/search.png"));
        jButtonObnovit.setIcon(new ImageIcon("./icons/obnovit.png"));
        jButtonOdoberKapitana.setIcon(new ImageIcon("./icons/cancel.png"));
        jButtonOverRezervaciu.setIcon(new ImageIcon("./icons/confirm.png"));
        jButtonPridajCestujuceho.setIcon(new ImageIcon("./icons/plus.png"));
        jButtonPridatKapitana.setIcon(new ImageIcon("./icons/plus.png"));
        jButtonRefreshCestujuci.setIcon(new ImageIcon("./icons/obnovit.png"));
        jButtonRezervaciaLetenky.setIcon(new ImageIcon("./icons/rezervacia.png"));
        jButtonUloz.setIcon(new ImageIcon("./icons/save.png"));
        jButtonVymazCestujuceho.setIcon(new ImageIcon("./icons/cancel.png"));
        jButtonZriadLet.setIcon(new ImageIcon("./icons/plane.png"));
        jButtonZrusLet.setIcon(new ImageIcon("./icons/cancel.png"));
        jButtonZrusitRezervaciu.setIcon(new ImageIcon("./icons/cancel.png"));        
    }
    
    /**
     * Načítanie množiny tabuliek.
     */
    public void nacitajVsetkyTabulky(){
        fillTableKapitan();    
        fillTableLety();
        fillTableCestujuci();
        fillTableLietadlo();
        fillTableZoznamLeteniek(); 
    }
    
    /**
     * Naplnenie tabulky zoznamu leteniek
     */
    public void fillTableZoznamLeteniek(){
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
    /**
     * Naplnenie tabulky zoznamu cestujúcich
     */
    public void fillTableZoznamCestujucich(){         
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
    /**
     *  Naplnenie tabulky zoznamu letov
     */
    public void fillTableLety(){
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
    
    /**
     * Naplnenie tabulky zoznamu kapitánov
     */
    public void fillTableKapitan(){
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
    
    /**
     * Naplnenie tabulky zoznamu cestujúcich
     */
    public void fillTableCestujuci(){
        try (Connection con = sql_connect.ConnectDB(dbPath);
                PreparedStatement state = con.prepareStatement("SELECT * FROM cestujuci");) {         
            rs = state.executeQuery();
            DefaultTableModel m = (DefaultTableModel)jTableCestujuci.getModel();
            m.setRowCount(0);
            while (rs.next()) {
               String [] row = {rs.getString("meno"), rs.getString("priezvisko"), rs.getString("rodne_cislo")};
               m.addRow(row);                
            } 
            //con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }  
    
    /**
     * Naplnenie tabulky zoznamu lietadiel
     */
    public void fillTableLietadlo(){
        DefaultTableModel m = (DefaultTableModel)jTableLietadla.getModel();
        m.setRowCount(0);
        for (Lietadla lietadla : _letisko.getZoznamLietadiel()) {
            String [] row = {lietadla.name(), lietadla.getKapacita()+""};
            m.addRow(row);
        }       
    } 
    
    /**
     * Naplnenie tabulky zoznamu destinácií pre cestujúceho
     */
    public void fillTableDestinacie(){       
        int selectedRow = -1;
        selectedRow = jTableCestujuci.getSelectedRow();
        DefaultTableModel m = (DefaultTableModel)jTableCestujuci.getModel();
        DefaultTableModel modelDestinacie = (DefaultTableModel)jTableDestinacie.getModel(); 
        if(selectedRow != -1){  
            String rc = m.getValueAt(selectedRow, 2).toString();  
            modelDestinacie.setRowCount(0);
            ArrayList<String> destinacieCestujuceho = new ArrayList<>();
            for (Let let : _letisko.getZoznamLetov()) {
                for (Cestujuci cestujuci : let.getZoznamCestujucich()) {
                    if(cestujuci.getRC().equalsIgnoreCase(rc)){
                        for (String destinacie : cestujuci.getDestinacie()) {                           
                            if(!destinacieCestujuceho.contains(destinacie)){
                                destinacieCestujuceho.add(destinacie);
                                String [] riadok = {destinacie};
                                modelDestinacie.addRow(riadok);   
                            }
                                
                        }                    
                    }                    
                }
            }
        }else{
            modelDestinacie.setRowCount(0);
        }    
    } 
    
    /**
     * Metóda pre načítanie počtu voľných miest na sedenie v lietadle.
     * Uskutoční sa po kliknutí na riadok v tabulke zoznam letov.
     * @param selectedRow - označený riadok tabulky
     * @return pocet volnych miest
     */
    public int loadPocetVolnychMiest(int selectedRow){
        
        DefaultTableModel m = (DefaultTableModel)jTableLety.getModel();
        String idLetu = m.getValueAt(selectedRow, 0).toString();     
        Let najdeny = _letisko.najdiLetPodlaId(Integer.parseInt(idLetu));
        int pocetVolnychMiest = najdeny.getPocetVolnychMiest();
        volneMiestaLabel.setText("Počet voľných miest "+pocetVolnychMiest+"");    
        return pocetVolnychMiest; 
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
        jButtonPridatKapitana = new javax.swing.JButton();
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
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButtonZrusLet)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButtonOverRezervaciu)
                            .addGap(22, 22, 22)
                            .addComponent(jButtonHladajNajblizsiLet)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonZriadLet)
                    .addComponent(jButtonZrusLet)
                    .addComponent(jButtonHladajNajblizsiLet)
                    .addComponent(jButtonHladajCestujuceho)
                    .addComponent(jButtonUloz)
                    .addComponent(jButtonOverRezervaciu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                    .addComponent(jScrollPane7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRezervaciaLetenky)
                    .addComponent(jButtonZrusitRezervaciu)
                    .addComponent(volneMiestaLabel))
                .addGap(15, 15, 15))
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

        jButtonPridatKapitana.setIcon(new javax.swing.ImageIcon("C:\\Users\\Acer\\Documents\\NetBeansProjects\\Letisko\\icons\\plus.png")); // NOI18N
        jButtonPridatKapitana.setText("Pridaj");
        jButtonPridatKapitana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPridatKapitanaActionPerformed(evt);
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
                        .addComponent(jButtonPridatKapitana)
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
                    .addComponent(jButtonPridatKapitana, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Kapitáni", jPanel2);

        jButtonPridajCestujuceho.setIcon(new javax.swing.ImageIcon("C:\\Users\\Acer\\Documents\\NetBeansProjects\\Letisko\\icons\\plus.png")); // NOI18N
        jButtonPridajCestujuceho.setText("Pridaj cestujúceho");
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
        jButtonVymazCestujuceho.setText("Vymaž cestujúceho");
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
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonPridajCestujuceho, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(jButtonVymazCestujuceho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonRefreshCestujuci, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    /**
     * Tlačidlo pre vymazanie kapitána.
     * @param evt 
     */
    private void jButtonOdoberKapitanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOdoberKapitanaActionPerformed
        /*vymaz kapitana*/
        DefaultTableModel m = (DefaultTableModel)jTableKapitan.getModel();
        int selectedRow = -1;
        selectedRow = jTableKapitan.getSelectedRow();
        if(selectedRow != -1){
            try (Connection con = sql_connect.ConnectDB(dbPath);
                    PreparedStatement state = con.prepareStatement("DELETE FROM kapitan WHERE rodne_cislo=?;");){        
                String rc_kapitan = m.getValueAt(selectedRow, 2).toString();
                state.setString(1, rc_kapitan);
                state.executeUpdate();
                m.removeRow(selectedRow);
                _letisko.removeKapitan(_letisko.najdiKapitanaPodlaRC(rc_kapitan));
                jButtonOdoberKapitana.setEnabled(false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Najprv označte kapitána v tabulke");
        }
        
    }//GEN-LAST:event_jButtonOdoberKapitanaActionPerformed

    /**
     * Tlačidlo pre pridanie kapitána.
     * @param evt 
     */
    private void jButtonPridatKapitanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPridatKapitanaActionPerformed
        /*pridaj kapitána*/
        KapitanJDialog kapitanJDialog = new KapitanJDialog(this, true);
        kapitanJDialog.setLocationRelativeTo(null);
        kapitanJDialog.setVisible(true);

        Kapitan kapitan = kapitanJDialog.getKapitan();
        if(kapitan != null){
            try (Connection con = sql_connect.ConnectDB(dbPath);
                    PreparedStatement state = con.prepareStatement("INSERT INTO kapitan (naliet_hodiny,rodne_cislo,meno,priezvisko) VALUES (?,?,?,?)");){
                /* uloz do DB*/
                state.setString(1, kapitan.getNalietaneHodiny()+"");
                state.setString(2, kapitan.getRC());
                state.setString(3, kapitan.getMeno());
                state.setString(4, kapitan.getPriezvisko());
                state.executeUpdate();
                /* vloz do GUI */
                DefaultTableModel m = (DefaultTableModel)jTableKapitan.getModel();
                String [] row = {kapitan.getMeno(), kapitan.getPriezvisko(), kapitan.getRC(), ""+kapitan.getNalietaneHodiny()+""};
                m.addRow(row);
                _letisko.addKapitan(kapitan);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_jButtonPridatKapitanaActionPerformed

    /**
     * Obnovenie tabulky zoznam leteniek
     * @param evt 
     */
    private void jButtonObnovitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonObnovitActionPerformed
        fillTableZoznamLeteniek();
    }//GEN-LAST:event_jButtonObnovitActionPerformed

    /**
     * Vyvolá sa po kliknutí na tabulku zoznam cestujúcich.
     * @param evt 
     */
    private void jTableZoznamCestujucichMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableZoznamCestujucichMouseClicked

        int selectedRow = -1;
        selectedRow = jTableZoznamCestujucich.getSelectedRow();
        if(selectedRow != -1) {
            jButtonZrusitRezervaciu.setEnabled(true);                    
        }
    }//GEN-LAST:event_jTableZoznamCestujucichMouseClicked

    /**
     * Zrušenie rezervácie.
     * Vykoná vymazanie rezervácie letu pre označeného cestujúceho,
     * 
     * @param evt 
     */
    private void jButtonZrusitRezervaciuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonZrusitRezervaciuActionPerformed
        //vymazanie z letenky
        DefaultTableModel cest = (DefaultTableModel)jTableZoznamCestujucich.getModel();
        int selectedRowCest = jTableZoznamCestujucich.getSelectedRow();

        DefaultTableModel lety = (DefaultTableModel)jTableLety.getModel();
        int selectedRowLety = jTableLety.getSelectedRow();

        String letID = lety.getValueAt(selectedRowLety, 0).toString();
        String rc = cest.getValueAt(selectedRowCest, 2).toString();

        for (Let let : _letisko.getZoznamLetov()) {
            if(let.getID() == Integer.parseInt(letID)){
                let.zrusRezervaciu(rc);   
            }          
        }
        try (Connection con = sql_connect.ConnectDB(dbPath);
                PreparedStatement state = con.prepareStatement("DELETE FROM Letenka WHERE id=? AND rodne_cislo=?;");){                 
            state.setString(1, letID);
            state.setString(2, rc);
            state.executeUpdate();
            /*vymazanie z gui*/
            cest.removeRow(selectedRowCest);
            jButtonZrusitRezervaciu.setEnabled(false);
            JOptionPane.showMessageDialog(null, "Rezervácia pre let s ID " + letID + " bola úspešne zrušená !", "Úspech", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }finally{
            loadPocetVolnychMiest(selectedRowLety);
        }
    }//GEN-LAST:event_jButtonZrusitRezervaciuActionPerformed

    /**
     * Rezervovanie letu pre cestujúceho, resp. vytvorenie letenky.
     * Vykonáva vkladanie letenky do databázy.
     * @param evt 
     */
    private void jButtonRezervaciaLetenkyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRezervaciaLetenkyActionPerformed
        /* rezervacia letenky */
        DefaultTableModel m = (DefaultTableModel)jTableLety.getModel();
        int selectedRow = jTableLety.getSelectedRow();
        String letID = m.getValueAt(selectedRow, 0).toString();
        
        RezervaciaJDialog rezervaciaJDialog = new RezervaciaJDialog(this, true);
        rezervaciaJDialog.setLocationRelativeTo(null);
        rezervaciaJDialog.setDbPath(dbPath);
        rezervaciaJDialog.setZoznamLetov(_letisko.getZoznamLetov());
        rezervaciaJDialog.fillTableCestujuci();
        rezervaciaJDialog.setVisible(true);
        Cestujuci cestujuci = rezervaciaJDialog.getCestujuci();
        
        if(cestujuci != null){
            for (Let let : _letisko.getZoznamLetov()) {
                if(let.getID() == Integer.parseInt(letID)){
                    if(let.rezervujLetenku(let.getDatumOdletu(), cestujuci)){
                        if(InsertToDB.insertLetenka(let, cestujuci, dbPath)){
                            fillTableZoznamCestujucich();
                            JOptionPane.showMessageDialog(null, "Letenka pre let " +letID+" úspešne rezervovaná !");
                            loadPocetVolnychMiest(selectedRow);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Letenka pre tohto cestujúceho už bola rezervovaná!", "Upozornenie!", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_jButtonRezervaciaLetenkyActionPerformed


    /**
     * Vyvolá sa po kliknutí na tabulku zoznam letov.
     * @param evt 
     */
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
            fillTableZoznamCestujucich();
            jButtonZrusitRezervaciu.setEnabled(false);
            jButtonOverRezervaciu.setEnabled(true);
        }
    }//GEN-LAST:event_jTableLetyMouseClicked

    /**
     * Tlačidlo pre hľadanie najližšieho letu na základe požadovaných vlastností.
     * @param evt 
     */
    private void jButtonHladajNajblizsiLetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHladajNajblizsiLetActionPerformed
        //hladaj najblizsi let
        HladajLetJDialog hladaj = new HladajLetJDialog(this, true);
        hladaj.setLocationRelativeTo(null);
        hladaj.setDbPath(dbPath);
        hladaj.naplnDestinacie();
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

    /**
     * Zrušenie letu v letisku.
     * Vykonáva aj vymazávanie všetkých rezervácií, pre koknrétny let,
     * @param evt 
     */
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
        if(DeleteFromDB.zrusitLet(Integer.parseInt(letID), destinacia, dbPath)){
            JOptionPane.showMessageDialog(null, "Let s ID " + letID + " bol úspešne zrušený !");
            DefaultTableModel modelCest = (DefaultTableModel)jTableZoznamCestujucich.getModel();
            modelCest.setRowCount(0);
            jButtonZrusLet.setEnabled(false);
            jButtonRezervaciaLetenky.setEnabled(false);
            jButtonOverRezervaciu.setEnabled(false);
            model.removeRow(selectedRow);
        }
    }//GEN-LAST:event_jButtonZrusLetActionPerformed

    /**
     * Zriadenie letu v letisku.
     * Pridanie letu do zoznamu letov a databázy.
     * @param evt 
     */
    private void jButtonZriadLetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonZriadLetActionPerformed
        /* zriadenie letu na letisku*/
        LetJDialog letJDialog = new LetJDialog(this, true);
        letJDialog.setLocationRelativeTo(null);
        letJDialog.setDbPath(dbPath);
        letJDialog.nacitajKapitanovFromDB();
        letJDialog.nacitajLietadlaFromDB();
        letJDialog.setVisible(true);

        Let let = letJDialog.getLet();
        if(let != null){   
            let = _letisko.zriadLet(let.getDestinacia(), let.getDatumOdletu(), let.getKapitan(), let.getTypLietadla());
            // set id podla DB ↓
            if(InsertToDB.insertLet(let, dbPath)){
                /*vloz do GUI */
                DefaultTableModel m = (DefaultTableModel)jTableLety.getModel();
                //String datumOdletu = new SimpleDateFormat("dd.MM.yyyy").format(let.getDatumOdletu());
                String [] row = {let.getID()+"", let.getDestinacia(), let.getDatumOdletu().toString(), let.getKapitan().getMeno()+" "+let.getKapitan().getPriezvisko(), let.getTypLietadla().name()};
                m.addRow(row);
            } 
        }
    }//GEN-LAST:event_jButtonZriadLetActionPerformed
  
    /**
     * Vyhľadávanie cestujúceho na základe mena a priezviska 
     * alebo na základne rodného čísla.
     * @param evt 
     */
    private void jButtonHladajCestujucehoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHladajCestujucehoActionPerformed
        HladajCestDialog cestDialog = new HladajCestDialog(this, true);
        cestDialog.setLocationRelativeTo(null);       
        cestDialog.setVisible(true);
        Cestujuci najdeny = null;
        boolean hladalSom = false;
        if(!cestDialog.getMeno().equalsIgnoreCase("") && !cestDialog.getPriezvisko().equalsIgnoreCase("")){
            String meno = cestDialog.getMeno();
            String priezvisko = cestDialog.getPriezvisko();  
            najdeny = _letisko.najdiCestujuceho(meno,priezvisko); 
            hladalSom = true;
        }else if(!cestDialog.getRC().equalsIgnoreCase("")){
            String rc = cestDialog.getRC();
            najdeny = _letisko.najdiCestujuceho(rc); 
            hladalSom = true;
        }
        if(najdeny != null){
            PrehladCestujucehoDialog prehlad = new PrehladCestujucehoDialog(this, true);
            prehlad.setLocationRelativeTo(null);        
            prehlad.setDbPath(dbPath);
            prehlad.setCestujuci(najdeny);
            prehlad.setZoznamLetov(_letisko.najdiZoznamLetovCestujuceho(najdeny.getRC()));
            prehlad.nacitajCestujuceho();
            prehlad.setVisible(true);
        }else if (hladalSom){
            JOptionPane.showMessageDialog(null, "Zadaný cestujúci sa nenašiel !", "Varovanie", JOptionPane.WARNING_MESSAGE);
        }
                
    }//GEN-LAST:event_jButtonHladajCestujucehoActionPerformed

    /**
     * Overenie rezervácie na základe rodného čísla.
     * @param evt 
     */
    private void jButtonOverRezervaciuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOverRezervaciuActionPerformed
        // TODO add your handling code here:
        HladajRezervaciu rezDialog = new HladajRezervaciu(this, true);
        rezDialog.setLocationRelativeTo(null);
        rezDialog.setVisible(true);
        if(!rezDialog.getRC().isEmpty()){
            String rc = rezDialog.getRC();
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

    /**
     * Vyvolá sa po kliknutí na tabulku zoznam cestujúcich.
     * @param evt 
     */
    private void jTableCestujuciMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCestujuciMouseClicked
        int selectedRow = -1;
        selectedRow = jTableCestujuci.getSelectedRow();
        if(selectedRow != -1){
            fillTableDestinacie();
            jButtonVymazCestujuceho.setEnabled(true);
        }
    }//GEN-LAST:event_jTableCestujuciMouseClicked

    /**
     * Tlačidlo pre pridanie cestujúceho tabulky zoznamu cestujúcich a do databázy.
     * @param evt 
     */
    private void jButtonPridajCestujucehoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPridajCestujucehoActionPerformed
        /*pridaj cestujuceho*/
        // ak chceme pridat cestujuceho je nutne ho priradit letu a zaroven mu pridat destinaciu pretože cestujuci su evidovani ako zoznam pod každym letom.
        // v inom pripade nepojde 
        CestujuciJDialog cestujuciJDialog = new CestujuciJDialog(this, true);
        cestujuciJDialog.setLocationRelativeTo(null);
        cestujuciJDialog.setVisible(true);

        Cestujuci cestujuci = cestujuciJDialog.getCestujuci();
        
        if(cestujuci != null){
            if(InsertToDB.insertCestujuceho(cestujuci, dbPath)){
                /*vloz do GUI */
                DefaultTableModel m = (DefaultTableModel)jTableCestujuci.getModel();
                String [] row = {cestujuci.getMeno(), cestujuci.getPriezvisko(), cestujuci.getRC()};
                m.addRow(row);
            }
        }
    }//GEN-LAST:event_jButtonPridajCestujucehoActionPerformed

    /**
     * Vymazanie cestujúceho z tabulky zoznam cestujúcich a databázy.
     * Vykoná tiež vymazanie všetkých rezervácií cestujúceho.
     * @param evt 
     */
    private void jButtonVymazCestujucehoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVymazCestujucehoActionPerformed
      
        int row = jTableCestujuci.getSelectedRow();
        DefaultTableModel m = (DefaultTableModel)jTableCestujuci.getModel();
        String rc = m.getValueAt(row, 2).toString();
        int pocetVymazanychLetov = 0;
        pocetVymazanychLetov = _letisko.vymazLetyCestujuceho(rc);
        JOptionPane.showMessageDialog(null, "Bolo vymazanych " + pocetVymazanychLetov + " letov");
        
        if(DeleteFromDB.deleteCestujuceho(rc, dbPath)){
            m.removeRow(row);
            JOptionPane.showMessageDialog(null, "Cestujúci bol vymazaný z DB");
            jButtonVymazCestujuceho.setEnabled(false);
            nacitajVsetkyTabulky();
        }
        
    }//GEN-LAST:event_jButtonVymazCestujucehoActionPerformed

    /**
     * Vykoná sa po kliknutí na tabulku zoznam kapitánov.
     * @param evt 
     */
    private void jTableKapitanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableKapitanMouseClicked
        // TODO add your handling code here:
        int selectedRow = -1;
        selectedRow = jTableKapitan.getSelectedRow();
        if(selectedRow != -1){           
            jButtonOdoberKapitana.setEnabled(true);
        }
    }//GEN-LAST:event_jTableKapitanMouseClicked
  
    
    /**
     * Uloženie do súboru.
     * Formát reťazca je pre súbor typu CSV.
     * @param evt 
     */
    private void jButtonUlozActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUlozActionPerformed
        try {
            JFileChooser fileChooser = new JFileChooser("./");
            if (fileChooser.showSaveDialog(this)== JFileChooser.APPROVE_OPTION) {
              File file = fileChooser.getSelectedFile();
              if(_letisko.save(file)){
                  JOptionPane.showMessageDialog(null, "Uloženie úspešné!", "Info", JOptionPane.INFORMATION_MESSAGE);
              }else{
                  JOptionPane.showMessageDialog(null, "Chyba pri ukladaní do súboru", "Chyba", JOptionPane.ERROR_MESSAGE);
              }
            }               
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButtonUlozActionPerformed

    /**
     * Vyvolanie metód fillTableCestujuci() a fillTableDestinácie().
     * @param evt 
     */
    private void jButtonRefreshCestujuciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshCestujuciActionPerformed
        fillTableCestujuci();
        fillTableDestinacie();
    }//GEN-LAST:event_jButtonRefreshCestujuciActionPerformed
  
    
    /**
     * Metóda spúštajúca program.
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
                Aplikacia aplikacia = new Aplikacia();
                aplikacia.setLocationRelativeTo(null);
                aplikacia.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonHladajCestujuceho;
    private javax.swing.JButton jButtonHladajNajblizsiLet;
    private javax.swing.JButton jButtonObnovit;
    private javax.swing.JButton jButtonOdoberKapitana;
    private javax.swing.JButton jButtonOverRezervaciu;
    private javax.swing.JButton jButtonPridajCestujuceho;
    private javax.swing.JButton jButtonPridatKapitana;
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
