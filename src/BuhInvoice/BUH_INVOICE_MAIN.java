/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import forall.HelpA;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MCREMOTE
 */
public class BUH_INVOICE_MAIN extends javax.swing.JFrame implements MouseListener, KeyListener {

    private InvoiceA_Insert invoiceA_insert;
    private InvoiceA_Update invoiceA_update;
    private CustomersA customersA;
    private ArticlesA articlesA;
    private InvoiceB invoiceB;

    private String ACTUAL_TAB_NAME;
    private String PREVIOUS_TAB_NAME;
    private final static String TAB_INVOICES_OVERVIEW = "FAKTUROR";
    private final static String TAB_CREATE_FAKTURA = "FAKTURA";
    private final static String TAB_KUDNER = "KUNDER";
    private final static String TAB_ARTIKLAR = "ARTIKLAR";

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
        invoiceB = new InvoiceB(this);
        HelpA.markFirstRowJtable(jTable_invoiceB_alla_fakturor);
        jtable_InvoiceB_all_invoices_clicked();
        //
        invoiceA_update = new InvoiceA_Update(this);
        //
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void setInsertOrUpdateLabel(boolean insert){
        if(insert){
            jLabel_Faktura_Insert_or_Update.setText("SKAPA NY FAKTURA");
        }else{
            jLabel_Faktura_Insert_or_Update.setText("BEARBETA FAKTURA");
        }
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
    
    protected String getFakturaId(){
        return HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__FAKTURA_ID);
    }
    
    protected String getFakturaArtikelId(){
        return HelpA.getValueSelectedRow(jTable_invoiceB_faktura_artiklar, InvoiceB.TABLE_INVOICE_ARTIKLES__ID);
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
        jButton12 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2_faktura_main = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel_articles = new JPanel();
        jButton5 = new javax.swing.JButton();
        jTextField_total_inkl_moms = new javax.swing.JTextField();
        jTextField_moms = new javax.swing.JTextField();
        jTextField_total_exkl_moms = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jPanel3_faktura_sec = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jLabel_Faktura_Insert_or_Update = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_InvoiceA_Insert_articles = new javax.swing.JTable();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jPanel4_Customers = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable_kunder = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable_kund_adresses = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel4_Articles = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable_ArticlesA_articles = new javax.swing.JTable();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 1));

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

        jButton12.setText("Radera faktura");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton15.setText("Refresh");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton18.setText("Radera artikel");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton18)
                        .addGap(51, 51, 51)
                        .addComponent(jButton15))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1197, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12)
                    .addComponent(jButton15)
                    .addComponent(jButton18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        jScrollPane3.setViewportView(jPanel3);

        jTabbedPane1.addTab("ALLA FAKTUROR", jScrollPane3);

        jPanel2_faktura_main.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2_faktura_main.setLayout(new java.awt.BorderLayout());

        jButton3.setText("FAKTURA HTTP");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel_articles.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel_articles.setLayout(new java.awt.BorderLayout());

        jButton5.setText("ADD ARTICLE");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTextField_total_inkl_moms.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_total_inkl_moms.setText("0");

        jTextField_moms.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_moms.setText("0");

        jTextField_total_exkl_moms.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_total_exkl_moms.setText("0");
        jTextField_total_exkl_moms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_total_exkl_momsActionPerformed(evt);
            }
        });

        jButton4.setText("TO HTML");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel3_faktura_sec.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3_faktura_sec.setLayout(new java.awt.GridLayout(1, 0));

        jButton6.setText("FAKTURA UPDATE");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton2.setText("UPDATE ARTICLE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton7.setText("CLEAR");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton11.setText("Skapa Ny");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel_Faktura_Insert_or_Update.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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

        jButton19.setText("EDIT ARTICLE INSERT");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton20.setText("SUBMIT EDITED ARTICLE");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jTextField_total_exkl_moms, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField_total_inkl_moms, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField_moms, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2_faktura_main, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton11)
                                .addGap(0, 503, Short.MAX_VALUE))
                            .addComponent(jPanel_articles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3_faktura_sec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(1, 1, 1)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton6)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton20, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)))
                            .addComponent(jLabel_Faktura_Insert_or_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(111, 111, 111))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_Faktura_Insert_or_Update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3_faktura_sec, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(jButton2)
                            .addComponent(jButton7)
                            .addComponent(jButton19))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5)
                            .addComponent(jButton3)
                            .addComponent(jButton6)
                            .addComponent(jButton20)))
                    .addComponent(jPanel2_faktura_main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_articles, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_total_exkl_moms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_total_inkl_moms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_moms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1546, 1546, 1546))
        );

        jScrollPane1.setViewportView(jPanel1);

        jTabbedPane1.addTab("FAKTURA", jScrollPane1);

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jButton8.setText("INSERT");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("TEST LATEST");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

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
        jPanel7.setLayout(new java.awt.BorderLayout());

        jButton13.setText("Skapa Ny");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setText("UPDATE");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton1.setText("DELETE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4_CustomersLayout = new javax.swing.GroupLayout(jPanel4_Customers);
        jPanel4_Customers.setLayout(jPanel4_CustomersLayout);
        jPanel4_CustomersLayout.setHorizontalGroup(
            jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4_CustomersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4_CustomersLayout.createSequentialGroup()
                        .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
                            .addComponent(jScrollPane8)))
                    .addGroup(jPanel4_CustomersLayout.createSequentialGroup()
                        .addComponent(jButton13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton14)
                        .addGap(18, 18, 18)
                        .addComponent(jButton9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))))
        );
        jPanel4_CustomersLayout.setVerticalGroup(
            jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4_CustomersLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton8)
                    .addComponent(jButton9)
                    .addComponent(jButton13)
                    .addComponent(jButton14)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50))
        );

        jTabbedPane1.addTab("KUNDER", jPanel4_Customers);

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jButton10.setText("INSERT");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

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

        jButton16.setText("Skapa Ny");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton17.setText("Delete");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton21.setText("UPDATE");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4_ArticlesLayout = new javax.swing.GroupLayout(jPanel4_Articles);
        jPanel4_Articles.setLayout(jPanel4_ArticlesLayout);
        jPanel4_ArticlesLayout.setHorizontalGroup(
            jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4_ArticlesLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4_ArticlesLayout.createSequentialGroup()
                        .addComponent(jButton10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton21))
                    .addGroup(jPanel4_ArticlesLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4_ArticlesLayout.setVerticalGroup(
            jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4_ArticlesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10)
                    .addComponent(jButton16)
                    .addComponent(jButton17)
                    .addComponent(jButton21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))
                .addGap(50, 50, 50))
        );

        jTabbedPane1.addTab("ARTIKLAR", jPanel4_Articles);

        getContentPane().add(jTabbedPane1);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // "FAKTURA HTTP" BTN
        invoiceA_insert.insertOrUpdate();
        invoiceB.refresh();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (InvoiceA_Update.CURRENT_OPERATION_INSERT) {
                    invoiceA_insert.addArticle();
                }else{
                    invoiceA_update.addArticle();
                }

            }
        });
        //
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        invoiceA_insert.htmlFaktura();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        //
        invoiceA_update.insertOrUpdate();
        //
        //
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //
        invoiceA_update.updateArticle();
        //
        invoiceA_update.insertOrUpdate(); // update entire faktura on updated article
        //
//        invoiceB.refresh_b();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        invoiceA_insert.clearAllRowsTableInvert(invoiceA_insert.TABLE_INVERT);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        customersA.insert();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        customersA.getNextKundnr();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        articlesA.insert();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        //
        if (invoiceA_insert == null) {
            invoiceA_insert = new InvoiceA_Insert(this);
        }
        //
        invoiceA_insert.createNew();
        //
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        invoiceB.deleteFaktura();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        customersA.createNewFakturaKund();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        customersA.update();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        //"Refresh btn"
        invoiceB.refresh();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        customersA.delete();
        invoiceB.refresh();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        articlesA.createNew();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        articlesA.delete();
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        invoiceB.deleteFakturaArtikel();
        invoiceA_update.countFakturaTotal(jTable_invoiceB_faktura_artiklar);
        invoiceA_update.insertOrUpdate(); // Update entire faktura after delete
        invoiceB.refresh_b();
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        InvoiceA_Insert.EDIT__ARTICLE_UPPON_INSERT__SWITCH = true;
        invoiceA_insert.showTableInvert_2();
        invoiceA_insert.refreshTableInvert(invoiceA_insert.TABLE_INVERT_2);
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
       invoiceA_insert.submitEditedArticle();
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jTextField_total_exkl_momsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_total_exkl_momsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_total_exkl_momsActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        articlesA.update();
    }//GEN-LAST:event_jButton21ActionPerformed

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BUH_INVOICE_MAIN().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel_Faktura_Insert_or_Update;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    protected javax.swing.JPanel jPanel2_faktura_main;
    private javax.swing.JPanel jPanel3;
    protected javax.swing.JPanel jPanel3_faktura_sec;
    private javax.swing.JPanel jPanel4_Articles;
    protected javax.swing.JPanel jPanel4_Customers;
    protected javax.swing.JPanel jPanel5;
    protected javax.swing.JPanel jPanel6;
    protected javax.swing.JPanel jPanel7;
    protected javax.swing.JPanel jPanel_articles;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    protected javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    protected javax.swing.JTable jTable_ArticlesA_articles;
    protected javax.swing.JTable jTable_InvoiceA_Insert_articles;
    protected javax.swing.JTable jTable_invoiceB_alla_fakturor;
    protected javax.swing.JTable jTable_invoiceB_faktura_artiklar;
    protected javax.swing.JTable jTable_kund_adresses;
    protected javax.swing.JTable jTable_kunder;
    public static javax.swing.JTextField jTextField_moms;
    public static javax.swing.JTextField jTextField_total_exkl_moms;
    public static javax.swing.JTextField jTextField_total_inkl_moms;
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
            } else if (ACTUAL_TAB_NAME.equals(TAB_CREATE_FAKTURA) && sameTabClicked == false) {
                //
                //
                java.awt.EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        //
                        invoiceA_update.showTableInvert();
                        invoiceA_update.refreshTableInvert(invoiceA_update.TABLE_INVERT);
                        //
                        invoiceA_update.showTableInvert_3();
                        invoiceA_update.refreshTableInvert(invoiceA_update.TABLE_INVERT_3);
                        //
                        DefaultTableModel dtm = (DefaultTableModel) jTable_invoiceB_faktura_artiklar.getModel();
                        jTable_InvoiceA_Insert_articles.setModel(dtm);
                        invoiceB.hideColumnsArticlesTable(jTable_InvoiceA_Insert_articles); //***
                        //
                        HelpA.markFirstRowJtable(jTable_InvoiceA_Insert_articles);
                        jTable_InvoiceA_Insert_articles_clicked();
                        //
//                        invoiceA_update.countFakturaTotal(jTable_InvoiceA_Insert_articles, InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL);
                        invoiceA_update.countFakturaTotal(jTable_InvoiceA_Insert_articles);
                    }
                });
                //
                //
            } else if (ACTUAL_TAB_NAME.equals(TAB_KUDNER)) {
                //
                if (customersA == null) {
                    customersA = new CustomersA(this);
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
            }
            //
            PREVIOUS_TAB_NAME = ACTUAL_TAB_NAME;
            //
        }
        //
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
            jtable_InvoiceB_all_invoices_clicked();
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

    protected void jTableCustomersA_kunder_clicked() {
        //
        customersA.showTableInvert_2();
        customersA.refreshTableInvert(customersA.TABLE_INVERT_2);
        customersA.fillJTableKundAdresses();
        //
        HelpA.markFirstRowJtable(jTable_kund_adresses);
        jTableCustomersA_adress_clicked();
        //
    }

    private void jTable_InvoiceA_Insert_articles_clicked() {
        //
        if (jTable_InvoiceA_Insert_articles.getModel().getRowCount() != 0 && Invoice.CURRENT_OPERATION_INSERT == false) {
            invoiceA_update.showTableInvert_2();
            invoiceA_update.refreshTableInvert(invoiceA_update.TABLE_INVERT_2);
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
            jtable_InvoiceB_all_invoices_clicked();
            //
        }
        //
    }

    public static String getDateCreated() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss.SSS");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

    public static boolean verifyId(String fakturaId) {
        //
        int id;
        //
        try {
            id = Integer.parseInt(fakturaId);
        } catch (Exception ex) {
            id = -1;
        }
        //
        return id != -1;
        //
    }
}
