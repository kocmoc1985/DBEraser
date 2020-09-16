/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.JTextAreaJLink;
import BuhInvoice.sec.LANG;
import forall.GP;
import forall.HelpA;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MCREMOTE
 */
public class BUH_INVOICE_MAIN extends javax.swing.JFrame implements MouseListener, KeyListener {

    private InvoiceA_Insert invoiceA_insert;
    private InvoiceA_Update invoiceA_update;
    private CustomersA_ customersA;
    private ForetagA foretagA;
    private ArticlesA articlesA;
    protected InvoiceB invoiceB;

    private String ACTUAL_TAB_NAME;
    private String PREVIOUS_TAB_NAME;
    public final static String TAB_INVOICES_OVERVIEW = "ALLA FAKTUROR";
    private final static String TAB_FAKTURA = "FAKTURA";
    public final static String TAB_KUDNER = "KUNDER";
    private final static String TAB_ARTIKLAR = "ARTIKLAR";
    private final static String TAB_FTG_SETUP = "FÖRETAGS INSTÄLLNINGAR";
    //
    private ArrayList<HashMap<String, String>> ARTICLES_ACTUAL_INVOICE;
    //

    /**
     * Creates new form BUH_INVOICE_MAIN
     */
    public BUH_INVOICE_MAIN() {
        initComponents();
        initOhter();
    }

    private void initOhter() {
        //
        this.jTabbedPane1.addMouseListener(this);
        this.jTable_invoiceB_alla_fakturor.addMouseListener(this);
        this.jTable_invoiceB_alla_fakturor.addKeyListener(this);
        //
        this.jTable_InvoiceA_Insert_articles.addMouseListener(this);
        this.jTable_kunder.addMouseListener(this);
        this.jTable_kund_adresses.addMouseListener(this);
        this.jTable_ArticlesA_articles.addMouseListener(this);
        //
        this.jTextArea_faktura_komment.addKeyListener(this);
        //
        invoiceB = new InvoiceB(this);
        HelpA.markFirstRowJtable(jTable_invoiceB_alla_fakturor);
        jtable_InvoiceB_all_invoices_clicked();
        //
        invoiceA_update = new InvoiceA_Update(this);
        //
        this.setTitle("Fakturering");
        this.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL_PROD_PLAN).getImage());
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    protected void setArticlesActualInvoice(ArrayList<HashMap<String, String>> list) {
        this.ARTICLES_ACTUAL_INVOICE = list;
    }

    protected ArrayList<HashMap<String, String>> getArticlesActualInvoice() {
        return this.ARTICLES_ACTUAL_INVOICE;
    }

    /**
     * OBS! Note if "kundId" does not exist it will not be possible to insert a
     * invoice because of the "foreign key constraints".
     *
     * It will maybe make sense to verify if the "kundId" exist in cloud
     *
     * @return
     */
    protected String getKundId() {
        return "1";
    }

    protected String getFakturaId() {
        return HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__FAKTURA_ID);
    }

    protected String getFakturaNr() {
        return HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__FAKTURANR);
    }

    protected String getFakturaKund() {
        return HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__KUND);
    }

    protected double getFakturaTotal() {
        return Double.parseDouble(HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__TOTAL_INKL_MOMS));
    }

    protected String getFakturaArtikelId() {
        return HelpA.getValueSelectedRow(jTable_InvoiceA_Insert_articles, InvoiceB.TABLE_INVOICE_ARTIKLES__ID);
    }
    
    protected boolean isKreditFaktura(){
       String fakturaTyp = HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__FAKTURA_TYP);
       if(fakturaTyp.equals(DB.STATIC__FAKTURA_TYPE_KREDIT)){
           return true;
       }else{
           return false;
       }
    }
    
    /**
     * OBS! Remember the "komment" field is used internally for different purposes.
     * One of usages is to save the "krediterad" invoice number [2020-09-15]
     * @return 
     */
    protected String getKomment_$(){
       String val = HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__KOMMENT_$);
       return GP_BUH.getValNoNull(val);
    }

    protected String getSELECT_fakturaId() {
        return getSELECT(DB.BUH_FAKTURA__ID__, getFakturaId());
    }

    protected String getSELECT_kundId() {
        return getSELECT(DB.BUH_FAKTURA__KUNDID__, getKundId());
    }

    protected String getSELECT_fakturaKundId(String fakturaKundId) {
        return getSELECT(DB.BUH_FAKTURA_KUND__ID, fakturaKundId);
    }

    protected String getSELECT(String whereColName, String whereValue) {
        //
        HashMap<String, String> map = new HashMap<>();
        map.put("where", whereColName); // $whereCoulunName
        map.put(whereColName, whereValue); // $whereValue
        //
        return JSon.hashMapToJSON(map);
    }

    protected String getSELECT_doubleWhere(String whereColName, String whereValue, String whereColName_b, String whereValue_b) {
        //
        HashMap<String, String> map = new HashMap<>();
        //
        map.put("where", whereColName); // $whereCoulunName
        map.put(whereColName, whereValue); // $whereValue
        //
        map.put("where_b", whereColName_b);
        map.put(whereColName_b, whereValue_b);
        //
        return JSon.hashMapToJSON(map);
    }

    /**
     * OBS! this function returns only basic parameters for "update". So you
     * will have to add the update values after calling this function.
     *
     * @param whereColName
     * @param whereValue
     * @param tableName
     * @return
     */
    protected HashMap<String, String> getUPDATE(String whereColName, String whereValue, String tableName) {
        //
        HashMap<String, String> map = new HashMap<>();
        map.put("where", whereColName); // $whereCoulunName
        map.put(whereColName, whereValue); // $whereValue
        map.put("table", tableName); // $table
        //
        return map;
    }
    
    protected static HashMap<String, String> getUPDATE_static(String whereColName, String whereValue, String tableName) {
        //
        HashMap<String, String> map = new HashMap<>();
        map.put("where", whereColName); // $whereCoulunName
        map.put(whereColName, whereValue); // $whereValue
        map.put("table", tableName); // $table
        //
        return map;
    }

    protected HashMap<String, String> getDELETE(String whereColName, String whereValue, String tableName) {
        return getUPDATE(whereColName, whereValue, tableName);
    }

    protected String getExist(String columnName, String value, String tableName) {
        //
        HashMap<String, String> map = new HashMap<>();
        //
        map.put("column", columnName);
        map.put("value", value);
        map.put("table", tableName);
        map.put("kundId", getKundId());
        //
        return JSon.hashMapToJSON(map);
    }

    protected String getLatest(String columnName, String tableName) {
        //
        HashMap<String, String> map = new HashMap<>();
        //
        map.put("column", columnName);
        map.put("table", tableName);
        map.put("kundId", getKundId());
        //
        return JSon.hashMapToJSON(map);
    }

    protected String getForeignKeyBindings(JTable table, String idColName, String whereParam, String phpFunc, String getFromMapParam) {
        //
        String returnStr = "";
        //
        String id = HelpA.getValueSelectedRow(table, idColName);
        //
        String json = getSELECT(whereParam, id);
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN, phpFunc, json);
            //
            ArrayList<HashMap<String, String>> invoices = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            for (HashMap<String, String> map : invoices) {
                //
                String rst = map.get(getFromMapParam);
                //
                returnStr += rst + ",";
                //
            }
            //
            returnStr = JSon.delete_last_letter_in_string(returnStr, ",");
            //
            return returnStr;
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
    
    public void setValueAllInvoicesJTable(String colName,String value){
        HelpA.setValueCurrentRow(jTable_invoiceB_alla_fakturor, colName, value);
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
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable_invoiceB_alla_fakturor = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable_invoiceB_faktura_artiklar = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButton15 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton_kredit_faktura = new javax.swing.JButton();
        jButton4_copy_faktura = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton_print_faktura = new javax.swing.JButton();
        jLabel_btn_separator_1 = new javax.swing.JLabel();
        jButton1_inbetalning = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel_all_invoices_list = new javax.swing.JLabel();
        jLabel_all_invoices_list1 = new javax.swing.JLabel();
        jTextArea_faktura_komment = new JTextAreaJLink();
        jButton4_save_faktura_komment = new javax.swing.JButton();
        jButton4_delete_faktura_komment1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2_faktura_main = new javax.swing.JPanel();
        jPanel_articles = new JPanel();
        jPanel3_faktura_sec = new javax.swing.JPanel();
        jLabel_Faktura_Insert_or_Update = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_InvoiceA_Insert_articles = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jButton_confirm_insert_update = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jButton_add_article = new javax.swing.JButton();
        jButton_update_articles_row = new javax.swing.JButton();
        jButton_delete_articles_row = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField_total_exkl_moms = new javax.swing.JTextField();
        jTextField_moms = new javax.swing.JTextField();
        jTextField_total_inkl_moms = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        jPanel4_Customers = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable_kunder = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable_kund_adresses = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jButton13 = new javax.swing.JButton();
        jButton_update_kund = new javax.swing.JButton();
        jButton_delete_customer = new javax.swing.JButton();
        jLabel_Kund_Insert_or_Update = new javax.swing.JLabel();
        jPanel4_Articles = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable_ArticlesA_articles = new javax.swing.JTable();
        jLabel_Artikel_Insert_or_Update = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jButton16 = new javax.swing.JButton();
        jButton_update_article = new javax.swing.JButton();
        jButton_delete_article = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable_ftg = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTable_ftg_addr = new javax.swing.JTable();
        jButton_update_kund_data = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 1));

        jScrollPane3.setPreferredSize(new java.awt.Dimension(1290, 500));

        jPanel3.setPreferredSize(new java.awt.Dimension(1288, 1000));

        jTable_invoiceB_alla_fakturor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable_invoiceB_alla_fakturor);

        jTable_invoiceB_faktura_artiklar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTable_invoiceB_faktura_artiklar);

        jPanel4.setLayout(new java.awt.GridLayout(1, 8));

        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh.png"))); // NOI18N
        jButton15.setToolTipText("Uppdatera listan");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton15);

        jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit.png"))); // NOI18N
        jButton18.setToolTipText("Bearbeta faktura");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton18);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new.png"))); // NOI18N
        jButton3.setToolTipText("Skapa ny faktura");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3);

        jButton_kredit_faktura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new_b.png"))); // NOI18N
        jButton_kredit_faktura.setToolTipText("Skapa kreditfaktura - välj först en faktura ur listan och tryck sedan på knappen");
        jButton_kredit_faktura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_kredit_fakturaActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_kredit_faktura);

        jButton4_copy_faktura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/copy.png"))); // NOI18N
        jButton4_copy_faktura.setToolTipText("Kopiera faktura");
        jButton4_copy_faktura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4_copy_fakturaActionPerformed(evt);
            }
        });
        jPanel4.add(jButton4_copy_faktura);

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton12.setToolTipText("Radera faktura");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton12);

        jButton_print_faktura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        jButton_print_faktura.setToolTipText("Skriv ut faktura");
        jButton_print_faktura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_print_fakturaActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_print_faktura);
        jPanel4.add(jLabel_btn_separator_1);

        jButton1_inbetalning.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/payed.png"))); // NOI18N
        jButton1_inbetalning.setToolTipText("Registrera inbetalning");
        jButton1_inbetalning.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1_inbetalningActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1_inbetalning);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/event.png"))); // NOI18N
        jButton2.setToolTipText("Se historia över skickade e-post och påminnelser");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton2);

        jLabel_all_invoices_list.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_all_invoices_list.setForeground(new java.awt.Color(153, 153, 153));
        jLabel_all_invoices_list.setText("FAKTUROR");

        jLabel_all_invoices_list1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_all_invoices_list1.setForeground(new java.awt.Color(153, 153, 153));
        jLabel_all_invoices_list1.setText("FAKTURA ARTIKLAR");

        jTextArea_faktura_komment.setColumns(20);
        jTextArea_faktura_komment.setLineWrap(true);
        jTextArea_faktura_komment.setRows(2);
        jTextArea_faktura_komment.setToolTipText("Skriv faktura kommentar här");
        jTextArea_faktura_komment.setWrapStyleWord(true);

        jButton4_save_faktura_komment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ok_sm.png"))); // NOI18N
        jButton4_save_faktura_komment.setToolTipText("Uppdatera faktura kommentar");
        jButton4_save_faktura_komment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4_save_faktura_kommentActionPerformed(evt);
            }
        });

        jButton4_delete_faktura_komment1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/close_sm.png"))); // NOI18N
        jButton4_delete_faktura_komment1.setToolTipText("Radera faktura kommentar");
        jButton4_delete_faktura_komment1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4_delete_faktura_komment1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(571, 571, 571)
                                .addComponent(jTextArea_faktura_komment, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(jButton4_save_faktura_komment, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jButton4_delete_faktura_komment1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(142, 142, 142)
                                .addComponent(jLabel_all_invoices_list, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(1043, 1043, 1043)
                                .addComponent(jLabel_all_invoices_list1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(12, 12, 12))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4_save_faktura_komment, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel_all_invoices_list, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextArea_faktura_komment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton4_delete_faktura_komment1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel_all_invoices_list1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(271, 271, 271))
        );

        jScrollPane3.setViewportView(jPanel3);

        jTabbedPane1.addTab("ALLA FAKTUROR", jScrollPane3);

        jPanel1.setPreferredSize(new java.awt.Dimension(1286, 1000));

        jPanel2_faktura_main.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2_faktura_main.setLayout(new java.awt.BorderLayout());

        jPanel_articles.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel_articles.setLayout(new java.awt.BorderLayout());

        jPanel3_faktura_sec.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3_faktura_sec.setLayout(new java.awt.GridLayout(1, 0));

        jLabel_Faktura_Insert_or_Update.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel_Faktura_Insert_or_Update.setForeground(new java.awt.Color(153, 153, 153));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new java.awt.GridLayout(1, 1));

        jTable_InvoiceA_Insert_articles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable_InvoiceA_Insert_articles);

        jPanel2.add(jScrollPane2);

        jPanel9.setLayout(new java.awt.GridLayout(1, 0));

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new.png"))); // NOI18N
        jButton11.setToolTipText("Skapa ny faktura");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton11);

        jButton_confirm_insert_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton_confirm_insert_update.setToolTipText("Spara faktura (insert or update)");
        jButton_confirm_insert_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_confirm_insert_updateActionPerformed(evt);
            }
        });
        jPanel9.add(jButton_confirm_insert_update);

        jPanel10.setLayout(new java.awt.GridLayout(1, 0));

        jPanel11.setLayout(new java.awt.GridLayout(1, 0));

        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit.png"))); // NOI18N
        jButton19.setToolTipText("Bearbeta artikel (insert)");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton19);

        jButton20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton20.setToolTipText("Bekräfta ändringar (insert)");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton20);

        jPanel12.setLayout(new java.awt.GridLayout(1, 3));

        jButton_add_article.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButton_add_article.setToolTipText("Lägg till artikel");
        jButton_add_article.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_add_articleActionPerformed(evt);
            }
        });
        jPanel12.add(jButton_add_article);

        jButton_update_articles_row.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton_update_articles_row.setToolTipText("Uppdatera artikel");
        jButton_update_articles_row.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_update_articles_rowActionPerformed(evt);
            }
        });
        jPanel12.add(jButton_update_articles_row);

        jButton_delete_articles_row.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton_delete_articles_row.setToolTipText("Radera artikel");
        jButton_delete_articles_row.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_delete_articles_rowActionPerformed(evt);
            }
        });
        jPanel12.add(jButton_delete_articles_row);

        jPanel15.setLayout(new java.awt.GridLayout(2, 3));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Exkl. moms");
        jPanel15.add(jLabel2);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Moms");
        jPanel15.add(jLabel3);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Total");
        jPanel15.add(jLabel4);

        jTextField_total_exkl_moms.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_total_exkl_moms.setText("0");
        jTextField_total_exkl_moms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_total_exkl_momsActionPerformed(evt);
            }
        });
        jPanel15.add(jTextField_total_exkl_moms);

        jTextField_moms.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_moms.setText("0");
        jPanel15.add(jTextField_moms);

        jTextField_total_inkl_moms.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_total_inkl_moms.setText("0");
        jPanel15.add(jTextField_total_inkl_moms);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 474, Short.MAX_VALUE)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2_faktura_main, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
                    .addComponent(jPanel_articles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3_faktura_sec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel_Faktura_Insert_or_Update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(66, 66, 66))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel_Faktura_Insert_or_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2_faktura_main, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3_faktura_sec, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(5, 5, 5)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel_articles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1520, 1520, 1520))
        );

        jScrollPane1.setViewportView(jPanel1);

        jTabbedPane1.addTab("FAKTURA", jScrollPane1);

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jTable_kunder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTable_kunder);

        jTable_kund_adresses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(jTable_kund_adresses);

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel7.setPreferredSize(new java.awt.Dimension(555, 416));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel13.setLayout(new java.awt.GridLayout(1, 0));

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new.png"))); // NOI18N
        jButton13.setToolTipText("Skapa ny");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton13);

        jButton_update_kund.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton_update_kund.setToolTipText("Uppdatera faktura kund data");
        jButton_update_kund.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_update_kundActionPerformed(evt);
            }
        });
        jPanel13.add(jButton_update_kund);

        jButton_delete_customer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton_delete_customer.setToolTipText("Radera kund");
        jButton_delete_customer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_delete_customerActionPerformed(evt);
            }
        });
        jPanel13.add(jButton_delete_customer);

        jLabel_Kund_Insert_or_Update.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_Kund_Insert_or_Update.setForeground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout jPanel4_CustomersLayout = new javax.swing.GroupLayout(jPanel4_Customers);
        jPanel4_Customers.setLayout(jPanel4_CustomersLayout);
        jPanel4_CustomersLayout.setHorizontalGroup(
            jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4_CustomersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
                        .addComponent(jScrollPane8))
                    .addComponent(jLabel_Kund_Insert_or_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );
        jPanel4_CustomersLayout.setVerticalGroup(
            jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4_CustomersLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Kund_Insert_or_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(142, 142, 142))
        );

        jScrollPane9.setViewportView(jPanel4_Customers);

        jTabbedPane1.addTab("KUNDER", jScrollPane9);

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jTable_ArticlesA_articles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(jTable_ArticlesA_articles);

        jLabel_Artikel_Insert_or_Update.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_Artikel_Insert_or_Update.setForeground(new java.awt.Color(153, 153, 153));

        jPanel14.setLayout(new java.awt.GridLayout(1, 4));

        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new.png"))); // NOI18N
        jButton16.setToolTipText("Skapa ny artikel");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton16);

        jButton_update_article.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton_update_article.setToolTipText("Uppdatera artikel data");
        jButton_update_article.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_update_articleActionPerformed(evt);
            }
        });
        jPanel14.add(jButton_update_article);

        jButton_delete_article.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton_delete_article.setToolTipText("Radera artikel");
        jButton_delete_article.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_delete_articleActionPerformed(evt);
            }
        });
        jPanel14.add(jButton_delete_article);

        javax.swing.GroupLayout jPanel4_ArticlesLayout = new javax.swing.GroupLayout(jPanel4_Articles);
        jPanel4_Articles.setLayout(jPanel4_ArticlesLayout);
        jPanel4_ArticlesLayout.setHorizontalGroup(
            jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4_ArticlesLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_Artikel_Insert_or_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );
        jPanel4_ArticlesLayout.setVerticalGroup(
            jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4_ArticlesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Artikel_Insert_or_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );

        jTabbedPane1.addTab("ARTIKLAR", jPanel4_Articles);

        jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel16.setLayout(new java.awt.BorderLayout());

        jTable_ftg.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane11.setViewportView(jTable_ftg);

        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel17.setLayout(new java.awt.BorderLayout());

        jTable_ftg_addr.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane12.setViewportView(jTable_ftg_addr);

        jButton_update_kund_data.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton_update_kund_data.setToolTipText("Uppdatera kund data");
        jButton_update_kund_data.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_update_kund_dataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton_update_kund_data, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jButton_update_kund_data, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE))
                .addGap(50, 50, 50))
        );

        jPanel16.getAccessibleContext().setAccessibleDescription("");

        jScrollPane10.setViewportView(jPanel8);

        jTabbedPane1.addTab("FÖRETAGS INSTÄLLNINGAR", jScrollPane10);

        getContentPane().add(jTabbedPane1);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        //
        if (HelpA.rowSelected(jTable_invoiceB_alla_fakturor) == false) {
            return;
        }
        //
        if (GP_BUH.confirmWarning(LANG.MSG_3_1) == false) {
            return;
        }
        //
        invoiceB.deleteFaktura();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        customersA.createNewFakturaKund();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton_update_kundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_update_kundActionPerformed
        if (customersA.getCurrentOperationInsert()) {
            if (customersA.fieldsValidated(true)) {
                customersA.insert();
            }
        } else {
            if (customersA.fieldsValidated(false)) {
                customersA.update();
            }
        }
    }//GEN-LAST:event_jButton_update_kundActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        //"Refresh btn"
        invoiceB.refresh();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton_delete_customerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_delete_customerActionPerformed
        //
        if (HelpA.rowSelected(jTable_kunder) == false) {
            return;
        }
        //
        customersA.delete();
        invoiceB.refresh();
    }//GEN-LAST:event_jButton_delete_customerActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        articlesA.createNew();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton_delete_articleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_delete_articleActionPerformed
        articlesA.delete();
    }//GEN-LAST:event_jButton_delete_articleActionPerformed

    private void jButton_update_articleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_update_articleActionPerformed
        if (articlesA.getCurrentOperationInsert()) {
            if (articlesA.fieldsValidated(true)) {
                articlesA.insert();
            }
        } else {
            if (articlesA.fieldsValidated(false)) {
                articlesA.update();
            }
        }

    }//GEN-LAST:event_jButton_update_articleActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        editFakturaBtnKlicked();
    }//GEN-LAST:event_jButton18ActionPerformed

    public void editFakturaBtnKlicked(){
        HelpA.openTabByName(jTabbedPane1, TAB_FAKTURA);
        fakturaTabClicked();
    }
    
    private void jButton_print_fakturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_print_fakturaActionPerformed
        invoiceB.htmlFaktura_b();
    }//GEN-LAST:event_jButton_print_fakturaActionPerformed

    private void jButton_delete_articles_rowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_delete_articles_rowActionPerformed
        //
        if (HelpA.rowSelected(jTable_InvoiceA_Insert_articles) == false) {
            return;
        }
        //
        if (InvoiceA_Insert.CURRENT_OPERATION_INSERT) {
            //
            invoiceA_insert.deleteArtikel();
            //
            invoiceA_insert.showTableInvert_3(); // some kind of redraw, OBS! needed -> see: "Invoice.disableMomsJComboIf()"
            //
        } else {
            //
            invoiceA_update.deleteFakturaArtikel();
            invoiceA_update.deselectRowArticlesTable(); // Deselecting due to "InvoiceA_Updte->getConfigTableInvert_2() -> articlesJTableRowSelected()"
            invoiceA_update.countFakturaTotal(jTable_InvoiceA_Insert_articles);
            invoiceA_update.insertOrUpdate(); // Update entire faktura after delete
            invoiceB.refresh_b();
            //
            invoiceA_update.showTableInvert_2(); // some kind of redraw, OBS! needed
            invoiceA_update.showTableInvert_3(); // some kind of redraw, OBS! needed
            //
        }
    }//GEN-LAST:event_jButton_delete_articles_rowActionPerformed

    private void jButton_update_articles_rowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_update_articles_rowActionPerformed
        //
        if (HelpA.rowSelected(jTable_InvoiceA_Insert_articles) == false) {
            GP_BUH.confirmWarning(LANG.MSG_5);
            return;
        }
        //
        if (invoiceA_update.fieldsValidatedArticle()) {
            //
            invoiceA_update.updateArticle();
            //
            invoiceA_update.insertOrUpdate(); // update entire faktura on updated article
            //
            int selected_row = jTable_InvoiceA_Insert_articles.getSelectedRow();
            //
            invoiceB.refresh_b();
            //
            HelpA.markGivenRow(jTable_InvoiceA_Insert_articles, selected_row);
            jTable_InvoiceA_Insert_articles_clicked();
            //
        }
        //
    }//GEN-LAST:event_jButton_update_articles_rowActionPerformed

    private void jButton_add_articleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_add_articleActionPerformed
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (InvoiceA_Update.CURRENT_OPERATION_INSERT) {
                    if (invoiceA_insert.fieldsValidatedArticle()) {
                        invoiceA_insert.addArticle();
                    }
                } else {
                    if (invoiceA_update.fieldsValidatedArticle()) {
                        invoiceA_update.deselectRowArticlesTable(); // Deselecting due to "InvoiceA_Updte->getConfigTableInvert_2() -> articlesJTableRowSelected()"
                        invoiceA_update.addArticle();
                        invoiceB.refresh_b(); // correct
                    }
                }

            }
        });
        //
    }//GEN-LAST:event_jButton_add_articleActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        //
        if (HelpA.rowSelected(jTable_InvoiceA_Insert_articles) == false) {
            return;
        }
        //
        invoiceA_insert.submitEditedArticle();
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        //
        if (HelpA.rowSelected(jTable_InvoiceA_Insert_articles) == false) {
            return;
        }
        //
        InvoiceA_Insert.EDIT__ARTICLE_UPPON_INSERT__SWITCH = true;
        invoiceA_insert.showTableInvert_2();
        invoiceA_insert.refreshTableInvert(invoiceA_insert.TABLE_INVERT_2);
        //
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton_confirm_insert_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_confirm_insert_updateActionPerformed
        // "FAKTURA HTTP" BTN
        if (InvoiceA_Update.CURRENT_OPERATION_INSERT) {
            if (invoiceA_insert.fieldsValidated(true)) {
                invoiceA_insert.insertOrUpdate();
                invoiceB.refresh();
                HelpA.openTabByName(jTabbedPane1, BUH_INVOICE_MAIN.TAB_INVOICES_OVERVIEW);
            }
        } else {
            if (invoiceA_update.fieldsValidated(false)) {
                invoiceA_update.insertOrUpdate();
                invoiceB.refresh();
            }
        }
        //
    }//GEN-LAST:event_jButton_confirm_insert_updateActionPerformed

    private void createNewFaktura() {
        //
        if (invoiceA_insert == null) {
            invoiceA_insert = new InvoiceA_Insert(this);
        }
        //
        invoiceA_insert.createNew();
        //
    }

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
         if (GP_BUH.confirmWarning(LANG.MSG_5_1) == false) {
            return;
        }
        createNewFaktura();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jTextField_total_exkl_momsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_total_exkl_momsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_total_exkl_momsActionPerformed

    private void jButton_update_kund_dataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_update_kund_dataActionPerformed
        if (foretagA.fieldsValidated(false)) {
            foretagA.update();
        }
    }//GEN-LAST:event_jButton_update_kund_dataActionPerformed

    private void jButton1_inbetalningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1_inbetalningActionPerformed
        EditPanel_Inbet epb = new EditPanel_Inbet(this, getFakturaId(), getFakturaNr(), getFakturaKund());
        epb.setVisible(true);
    }//GEN-LAST:event_jButton1_inbetalningActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        EditPanel_Send eps = new EditPanel_Send(this, getFakturaId(), getFakturaNr(), getFakturaKund());
        eps.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        HelpA.openTabByName(jTabbedPane1, TAB_FAKTURA);
        createNewFaktura();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4_save_faktura_kommentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4_save_faktura_kommentActionPerformed
        invoiceB.updateKomment(false);
    }//GEN-LAST:event_jButton4_save_faktura_kommentActionPerformed

    private void jButton4_copy_fakturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4_copy_fakturaActionPerformed
        copyFaktura(false);
    }//GEN-LAST:event_jButton4_copy_fakturaActionPerformed

    private void copyFaktura(boolean isKreditFaktura){
        //
        if (HelpA.rowSelected(jTable_invoiceB_alla_fakturor) == false) {
            return;
        }
        //
        String msg;
        //
        if(isKreditFaktura){
            msg = LANG.FAKTURA_KREDIT_MSG(getFakturaNr());
        }else{
            msg = LANG.FAKTURA_COPY_MSG_A(getFakturaNr());
        }
        //
        if (GP_BUH.confirm(msg) == false) {
            return;
        }
        //
        invoiceB.copy(isKreditFaktura);
        //
    }
    
    private void jButton4_delete_faktura_komment1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4_delete_faktura_komment1ActionPerformed
        //
        if (GP_BUH.confirmWarning(LANG.MSG_3_3) == false) {
            return;
        }
        //
        invoiceB.deleteComment();
    }//GEN-LAST:event_jButton4_delete_faktura_komment1ActionPerformed

    private void jButton_kredit_fakturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_kredit_fakturaActionPerformed
        copyFaktura(true); // true = "kreditfaktura"
    }//GEN-LAST:event_jButton_kredit_fakturaActionPerformed

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
            java.util.logging.Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BUH_INVOICE_MAIN().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton1_inbetalning;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4_copy_faktura;
    protected javax.swing.JButton jButton4_delete_faktura_komment1;
    protected javax.swing.JButton jButton4_save_faktura_komment;
    protected javax.swing.JButton jButton_add_article;
    protected javax.swing.JButton jButton_confirm_insert_update;
    protected javax.swing.JButton jButton_delete_article;
    protected javax.swing.JButton jButton_delete_articles_row;
    protected javax.swing.JButton jButton_delete_customer;
    private javax.swing.JButton jButton_kredit_faktura;
    protected javax.swing.JButton jButton_print_faktura;
    protected javax.swing.JButton jButton_update_article;
    protected javax.swing.JButton jButton_update_articles_row;
    protected javax.swing.JButton jButton_update_kund;
    protected javax.swing.JButton jButton_update_kund_data;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    protected javax.swing.JLabel jLabel_Artikel_Insert_or_Update;
    protected javax.swing.JLabel jLabel_Faktura_Insert_or_Update;
    protected javax.swing.JLabel jLabel_Kund_Insert_or_Update;
    protected javax.swing.JLabel jLabel_all_invoices_list;
    protected javax.swing.JLabel jLabel_all_invoices_list1;
    private javax.swing.JLabel jLabel_btn_separator_1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    protected javax.swing.JPanel jPanel11;
    protected javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    protected javax.swing.JPanel jPanel16;
    protected javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    protected javax.swing.JPanel jPanel2_faktura_main;
    private javax.swing.JPanel jPanel3;
    protected javax.swing.JPanel jPanel3_faktura_sec;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel4_Articles;
    protected javax.swing.JPanel jPanel4_Customers;
    protected javax.swing.JPanel jPanel5;
    protected javax.swing.JPanel jPanel6;
    protected javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    protected javax.swing.JPanel jPanel_articles;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    protected javax.swing.JScrollPane jScrollPane11;
    protected javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    protected javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    protected javax.swing.JTable jTable_ArticlesA_articles;
    protected javax.swing.JTable jTable_InvoiceA_Insert_articles;
    protected javax.swing.JTable jTable_ftg;
    protected javax.swing.JTable jTable_ftg_addr;
    protected javax.swing.JTable jTable_invoiceB_alla_fakturor;
    protected javax.swing.JTable jTable_invoiceB_faktura_artiklar;
    protected javax.swing.JTable jTable_kund_adresses;
    protected javax.swing.JTable jTable_kunder;
    protected javax.swing.JTextArea jTextArea_faktura_komment;
    protected static javax.swing.JTextField jTextField_moms;
    protected static javax.swing.JTextField jTextField_total_exkl_moms;
    protected static javax.swing.JTextField jTextField_total_inkl_moms;
    // End of variables declaration//GEN-END:variables

    
    private void mousePressedOnTab(MouseEvent me) {      
        //
        if (me.getSource() == jTabbedPane1) {
            //
            String title = jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex());
            ACTUAL_TAB_NAME = title;
            //
            boolean sameTabClicked = ACTUAL_TAB_NAME.equals(PREVIOUS_TAB_NAME);
            //
            if (ACTUAL_TAB_NAME.equals(TAB_INVOICES_OVERVIEW)) {
                //
                invoiceB.refresh_b();
                jtable_InvoiceB_all_invoices_clicked();
                //
            } else if (ACTUAL_TAB_NAME.equals(TAB_FAKTURA) && sameTabClicked == false) {
                //
                fakturaTabClicked();
                //
            } else if (ACTUAL_TAB_NAME.equals(TAB_KUDNER)) {
                //
                if (customersA == null) {
                    customersA = new CustomersA_(this);
                } else {
                    jTableCustomersA_kunder_clicked();
                }
                //
            } else if (ACTUAL_TAB_NAME.equals(TAB_ARTIKLAR)) {
                //
                if (articlesA == null) {
                    articlesA = new ArticlesA(this);
                } else {
                    jTableArticles_clicked();
                }
                //
            } else if (ACTUAL_TAB_NAME.equals(TAB_FTG_SETUP)) {
                //
                if (foretagA == null) {
                    foretagA = new ForetagA(this);
                } else {
                    jTableForetagA_ftg_table_clicked();
                }
                //
            }
            //
            PREVIOUS_TAB_NAME = ACTUAL_TAB_NAME;
            //
        }
        //
    }

    private void fakturaTabClicked() {
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //
                DefaultTableModel dtm = (DefaultTableModel) jTable_invoiceB_faktura_artiklar.getModel();
                jTable_InvoiceA_Insert_articles.setModel(dtm);
                invoiceB.hideColumnsArticlesTable(jTable_InvoiceA_Insert_articles); //***
                //
                HelpA.markFirstRowJtable(jTable_InvoiceA_Insert_articles);
                jTable_InvoiceA_Insert_articles_clicked();
                //
                invoiceA_update.countFakturaTotal(jTable_InvoiceA_Insert_articles);
                //
                invoiceA_update.showTableInvert();
                invoiceA_update.refreshTableInvert(invoiceA_update.TABLE_INVERT);
                //
                invoiceA_update.showTableInvert_3();
                invoiceA_update.refreshTableInvert(invoiceA_update.TABLE_INVERT_3);
                //
            }
        });

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //
        mousePressedOnTab(e);
        //
        if (e.getSource() == jTable_invoiceB_alla_fakturor && (e.getClickCount() == 1)) {
            //
            Thread x = new Thread(() -> {
                jtable_InvoiceB_all_invoices_clicked();
            });
            //
            x.start();
            //
        } else if (e.getSource() == jTable_InvoiceA_Insert_articles && (e.getClickCount() == 1)) {
            //
            jTable_InvoiceA_Insert_articles_clicked();
            //
        } else if (e.getSource() == jTable_kunder && (e.getClickCount() == 1)) {
            //
            jTableCustomersA_kunder_clicked();
            //
        } else if (e.getSource() == jTable_kund_adresses && (e.getClickCount() == 1)) {
            //
            jTableCustomersA_adress_clicked();
            //
        } else if (e.getSource() == jTable_ArticlesA_articles && (e.getClickCount() == 1)) {
            //
            jTableArticles_clicked();
            //
        }
    }

    protected void jTableArticles_clicked() {
        articlesA.showTableInvert_2();
        articlesA.refreshTableInvert(articlesA.TABLE_INVERT_2);
    }

    protected void jTableCustomersA_adress_clicked() {
        customersA.showTableInvert_3();
        customersA.refreshTableInvert(customersA.TABLE_INVERT_3);
    }

    protected void jTableForetagA_adress_clicked() {
        foretagA.showTableInvert_3();
        foretagA.refreshTableInvert(foretagA.TABLE_INVERT_3);
    }

    protected void jTableCustomersA_kunder_clicked() {
        //
        customersA.showTableInvert_2();
        customersA.refreshTableInvert(customersA.TABLE_INVERT_2);
        customersA.fillAddressTable();
        //
        HelpA.markFirstRowJtable(jTable_kund_adresses);
        jTableCustomersA_adress_clicked();
        //
    }

    protected void jTableForetagA_ftg_table_clicked() {
        //
        foretagA.showTableInvert_2();
        foretagA.refreshTableInvert(foretagA.TABLE_INVERT_2);
        foretagA.fillAddressTable();
        //
        HelpA.markFirstRowJtable(jTable_ftg_addr);
        jTableForetagA_adress_clicked();
        //
    }

    private void jTable_InvoiceA_Insert_articles_clicked() {
        //
        if (Invoice.CURRENT_OPERATION_INSERT == false) {
            invoiceA_update.showTableInvert_2();
        }
        //
    }

    protected void jtable_InvoiceB_all_invoices_clicked() {
        //
        String fakturaId = getFakturaId();
        //
        invoiceB.all_invoices_table_clicked(fakturaId);
        //
    }

    protected void hideShowButtonsDependingOnFakturaType(){
        //
        if(isKreditFaktura()){
            jButton_kredit_faktura.setEnabled(false);
            jButton4_copy_faktura.setEnabled(false);
            jButton1_inbetalning.setEnabled(false);
        }else{
            jButton_kredit_faktura.setEnabled(true);
            jButton4_copy_faktura.setEnabled(true);
            jButton1_inbetalning.setEnabled(true);
        }
        //
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //
        boolean cond_1 = (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP);
        //
        if (e.getSource() == jTable_invoiceB_alla_fakturor && cond_1) {
            //
             Thread x = new Thread(() -> {
                jtable_InvoiceB_all_invoices_clicked();
            });
            //
            x.start();
            //
        } else if (e.getSource() == jTextArea_faktura_komment) {
            //
            JTextAreaJLink txt = (JTextAreaJLink) jTextArea_faktura_komment;
            //
            Validator.validateMaxInputLength(txt, 100);
            //
            System.out.println("" + txt.getText().length());
            //
        }
        //
    }

    

}
