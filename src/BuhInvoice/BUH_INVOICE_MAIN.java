/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MCRecipe.Lang.ERRORS;
import MyObjectTable.Table;
import MyObjectTableInvert.ColumnValue;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableInvert;
import Reporting.InvertTableRow;
import forall.HelpA;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
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

    protected void displayInsertOrUpdate() {
        this.setTitle("INSERT OR UPDATE: " + Invoice.isInsertOrUpdate());
    }

    private void initOhter() {
        //
        this.jTabbedPane1.addMouseListener(this);
        this.jTable_invoiceB_alla_fakturor.addMouseListener(this);
        this.jTable_invoiceB_alla_fakturor.addKeyListener(this);
        //
        this.jTable_InvoiceA_articles.addMouseListener(this);
        this.jTable_kunder.addMouseListener(this);
        this.jTable_articles.addMouseListener(this);
        //
        invoiceA_insert = new InvoiceA_Insert(this);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2_faktura_main = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel_articles = new JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_InvoiceA_articles = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jTextField_total_inkl_moms = new javax.swing.JTextField();
        jTextField_moms = new javax.swing.JTextField();
        jTextField_total_exkl_moms = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jPanel3_faktura_sec = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable_invoiceB_alla_fakturor = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable_invoiceB_faktura_artiklar = new javax.swing.JTable();
        jPanel4_Customers = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable_kunder = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable_kund_adresses = new javax.swing.JTable();
        jPanel4_Articles = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable_articles = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 1));

        jPanel2_faktura_main.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2_faktura_main.setLayout(new java.awt.BorderLayout());

        jButton1.setText("TO CSV");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("FAKTURA HTTP");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel_articles.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel_articles.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jTable_InvoiceA_articles.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTable_InvoiceA_articles);

        jPanel2.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jScrollPane2.setViewportView(jPanel2);

        jButton5.setText("ADD ARTICLE");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTextField_total_inkl_moms.setText("0");

        jTextField_moms.setText("0");

        jTextField_total_exkl_moms.setText("0");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel_articles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2_faktura_main, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField_total_exkl_moms, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_moms, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField_total_inkl_moms, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3)
                                .addGap(6, 6, 6)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3_faktura_sec, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7)))
                .addContainerGap(351, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3_faktura_sec, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_total_exkl_moms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)
                            .addComponent(jTextField_total_inkl_moms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_moms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5)
                            .addComponent(jButton3)
                            .addComponent(jButton6)))
                    .addComponent(jPanel2_faktura_main, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 80, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel_articles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(150, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("FAKTURA", jPanel1);

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1197, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(314, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(330, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("FAKTUROR", jPanel3);

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

        javax.swing.GroupLayout jPanel4_CustomersLayout = new javax.swing.GroupLayout(jPanel4_Customers);
        jPanel4_Customers.setLayout(jPanel4_CustomersLayout);
        jPanel4_CustomersLayout.setHorizontalGroup(
            jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4_CustomersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4_CustomersLayout.createSequentialGroup()
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE)
                    .addComponent(jScrollPane8))
                .addContainerGap(158, Short.MAX_VALUE))
        );
        jPanel4_CustomersLayout.setVerticalGroup(
            jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4_CustomersLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE))
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4_CustomersLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton8)
                            .addComponent(jButton9)))
                    .addGroup(jPanel4_CustomersLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(501, Short.MAX_VALUE))
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

        jTable_articles.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(jTable_articles);

        javax.swing.GroupLayout jPanel4_ArticlesLayout = new javax.swing.GroupLayout(jPanel4_Articles);
        jPanel4_Articles.setLayout(jPanel4_ArticlesLayout);
        jPanel4_ArticlesLayout.setHorizontalGroup(
            jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4_ArticlesLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton10)
                    .addGroup(jPanel4_ArticlesLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(351, 351, 351))
        );
        jPanel4_ArticlesLayout.setVerticalGroup(
            jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4_ArticlesLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jButton10)
                .addContainerGap(698, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("ARTIKLAR", jPanel4_Articles);

        jScrollPane1.setViewportView(jTabbedPane1);

        getContentPane().add(jScrollPane1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        invoiceA_insert.tableInvertToCSV(invoiceA_insert.TABLE_INVERT, 1, invoiceA_insert.getConfigTableInvert(), true);
    }//GEN-LAST:event_jButton1ActionPerformed


    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        invoiceA_insert.insertOrUpdate();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        //
        invoiceA_insert.addArticleForJTable(jTable_InvoiceA_articles);
        //
        invoiceA_insert.addArticleForDB();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        invoiceA_insert.htmlFaktura();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        //
        //
        invoiceA_update.insertOrUpdate();
        //
        //
//        HashMap<String, String> map = getUPDATE(DB.BUH_F_ARTIKEL__FAKTURAID, "1", DB.TABLE__BUH_F_ARTIKEL);
//        //
//        map.put("pris", "159");
//        map.put("rabatt", "20");
//        map.put("komment", "kontrollera");
//        //
//        String json = JSon.hashMapToJSON(map);
//        //
//        try {
//            //
//            String json_str_return = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
//                    DB.PHP_FUNC_UPDATE_AUTO, json));
//            //
//        } catch (Exception ex) {
//            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
//        }
        //
        //

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //
        invoiceA_update.jtableArticlesRowChange();
        //
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        invoiceA_insert.clearAllRowsTableInvert(invoiceA_insert.TABLE_INVERT);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        customersA.insertCustomer();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        customersA.getNextKundnr();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        articlesA.insertArtikel();
    }//GEN-LAST:event_jButton10ActionPerformed

    private String tableInvertToHTML(Table table, int startColumn, RowDataInvert[] cfg) {
        String csv = tableInvertToCSV(table, startColumn, cfg);
        ArrayList<InvertTableRow> tableRowsList = buildTableRowList(csv);
        //
        String html = "<table class='table-invert'>";
        //
        //
        for (InvertTableRow invertTableRow : tableRowsList) {
            html += "<tr>";
            html += "<td style='background-color:light-grey'>" + invertTableRow.getColumnName() + "</td>";
            //
            try {
                html += "<td>" + invertTableRow.getValue(0) + "</td>";
            } catch (Exception ex) {
                html += "<td>" + ERRORS.VAL_MISSING_REPORT + "</td>";
            }
            //
            html += "</tr>";
        }
        //
        //
        html += "</table>";
        //
        return html;
    }

    private String tableInvertToCSV(Table table_invert, int startColumn, RowDataInvert[] rdi) {
        //
        TableInvert tableInvert = (TableInvert) table_invert;
        //
        String csv = "";
        //
        for (RowDataInvert dataInvert : rdi) {
            //
            if (dataInvert.getVisible() == false) {
                continue;
            }
            //
            csv += dataInvert.getFieldNickName() + ";";
            //
            if (dataInvert.getUnit() instanceof String) {
                String unit = (String) dataInvert.getUnit();
                //
                if (unit.isEmpty() == false) {
                    csv += unit + ";";
                } else {
                    csv += "unit" + ";";
                }
                //
            }
            //
            for (int x = startColumn; x < getColumnCountTableInvert(table_invert); x++) {
                //
                HashMap<String, ColumnValue> map = tableInvert.getColumnData(x);
                //
                ColumnValue columnValue = map.get(dataInvert.getFieldNickName());
                //
                csv += columnValue.getValue() + ";";
                // 
            }
            //
            csv += "\n";
            //
        }
        //
//        System.out.println("CSV: \n" + csv);
        //
        //
        return csv;
    }

    private int getColumnCountTableInvert(Table table_invert) {
        TableInvert tableInvert = (TableInvert) table_invert;
        return tableInvert.getColumnCount();
    }

    private ArrayList<InvertTableRow> buildTableRowList(String csv) {
        //
        String[] lines = csv.split("\n");
        //
        ArrayList<InvertTableRow> tableRowsList = new ArrayList<InvertTableRow>();
        //
        for (String line : lines) {
            String arr[] = line.split(";");

            String columnName = arr[0];
            String unit = arr[1];

            InvertTableRow row = new InvertTableRow(columnName, unit);

            for (int i = 2; i < arr.length; i++) {
                row.addValue(arr[i]);
            }
            //
            tableRowsList.add(row);
            //
        }
        return tableRowsList;
    }

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
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    protected javax.swing.JPanel jPanel2_faktura_main;
    private javax.swing.JPanel jPanel3;
    protected javax.swing.JPanel jPanel3_faktura_sec;
    private javax.swing.JPanel jPanel4_Articles;
    protected javax.swing.JPanel jPanel4_Customers;
    protected javax.swing.JPanel jPanel5;
    protected javax.swing.JPanel jPanel6;
    protected javax.swing.JPanel jPanel_articles;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    protected javax.swing.JTable jTable_InvoiceA_articles;
    protected javax.swing.JTable jTable_articles;
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
            if (title.equals(TAB_INVOICES_OVERVIEW)) {
                //
                if (invoiceB == null) {
                    invoiceB = new InvoiceB(this);
                    invoiceA_update = new InvoiceA_Update(this);
                }
                //
            } else if (title.equals(TAB_CREATE_FAKTURA)) {
                //
                if (invoiceA_insert != null) {
                    //
                    //
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            //
                            invoiceA_update.showTableInvert(); //[$TEST$]
                            invoiceA_update.refreshTableInvert(invoiceA_update.TABLE_INVERT); // //[$TEST$]
                            //
                            invoiceA_update.showTableInvert_3();
                            invoiceA_update.refreshTableInvert(invoiceA_update.TABLE_INVERT_3);
                            //
                            DefaultTableModel dtm = (DefaultTableModel) jTable_invoiceB_faktura_artiklar.getModel();
                            jTable_InvoiceA_articles.setModel(dtm);
                        }
                    });
                    //
                }
                //
            } else if (title.equals(TAB_KUDNER)) {
                //
                if (customersA == null) {
                    customersA = new CustomersA(this);
                }
                //
            } else if (title.equals(TAB_ARTIKLAR)) {
                //
                if (articlesA == null) {
                    articlesA = new ArticlesA(this);
                }
                //
            }
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
            jtableAllInvoicesClicked();
            //
        } else if (e.getSource() == jTable_InvoiceA_articles && (e.getClickCount() == 1)) {
            //
            invoiceA_update.showTableInvert_2();
            invoiceA_update.refreshTableInvert(invoiceA_update.TABLE_INVERT_2);
            //
        }else if (e.getSource() == jTable_kunder && (e.getClickCount() == 1)) {
            //
            customersA.showTableInvert_2();
            customersA.refreshTableInvert(customersA.TABLE_INVERT_2);
            //
            customersA.fillJTableCustomerAdresses();
            //
        }else if (e.getSource() == jTable_articles && (e.getClickCount() == 1)) {
            //
            articlesA.showTableInvert_2();
            articlesA.refreshTableInvert(articlesA.TABLE_INVERT_2);
            //
        }
    }

    private void jtableAllInvoicesClicked() {
        //
        String valueSelectedRow = HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, "ID");
        //
        invoiceB.all_invoices_table_clicked(valueSelectedRow);
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
            jtableAllInvoicesClicked();
            //
        }
        //
    }
}
