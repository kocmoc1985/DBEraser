/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTable.OutPut;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import forall.HelpA;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KOCMOC
 */
public class EditPanel_Basic extends javax.swing.JFrame {

    private Basic_Buh_ basic;
    private final BUH_INVOICE_MAIN_ bim;
    private final String fakturaId;
    private final String fakturaNr;
    private final String fakturaKund;
    //
    private static final String TABLE_INBET__ID = "ID";
    private static final String TABLE_INBET__FAKTURA_ID = "F ID";
    private static final String TABLE_INBET__INBETALD = "INBETALD";
    private static final String TABLE_INBET__INBET_METOD = "BETALMETOD";
    private static final String TABLE_INBET__ANNAT = "ANNAT";
    private static final String TABLE_INBET__DATUM = "DATUM";

    /**
     * Creates new form EditPanel
     *
     * @param bim
     * @param fakturaId
     * @param fakturaNr
     * @param fakturaKund
     */
    public EditPanel_Basic(BUH_INVOICE_MAIN_ bim, String fakturaId, String fakturaNr, String fakturaKund) {
        initComponents();
        this.bim = bim;
        this.fakturaId = fakturaId;
        this.fakturaNr = fakturaNr;
        this.fakturaKund = fakturaKund;
        init();
    }

    private void init() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle();
        initBasicTab();
    }
    
    private JTable getTableInbet(){
        return jTable_faktura_inbets;
    }

    private void setTitle() {
        this.jLabel1.setText(fakturaKund + " - " + fakturaNr);
    }
    
     private void refresh() {
        fillInbetalningarJTable();
//        HelpA.markFirstRowJtable(getTableInbet());
//        bim.jTableArticles_clicked();
    }
     
     private void insert() {
        //
        HashMap<String, String> map = basic.tableInvertToHashMap(basic.TABLE_INVERT, DB.START_COLUMN);
        //
        map.put(DB.BUH_FAKTURA_INBET__FAKTURA_ID, fakturaId);
        //
        String json = JSon.hashMapToJSON(map);
        //
        try {
            //
            HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_FAKTURA_IBET_TO_DB, json));
            //
        } catch (Exception ex) {
            Logger.getLogger(CustomersA.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        refresh();
        //
    }

    private void fillInbetalningarJTable() {
        //
        JTable table = getTableInbet();
        //
        HelpA.clearAllRowsJTable(table);
        //
        String json = bim.getSELECT_fakturaId();
        //
        try {
            //
            String json_str_return = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM__GET_FAKTURA_INBET, json));
            //
            ArrayList<HashMap<String, String>> invoices = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            //
            for (HashMap<String, String> invoice_map : invoices) {
                addRowJtable(invoice_map, table);
            }
            //
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        if (GP_BUH.CUSTOMER_MODE) {
            HelpA.hideColumnByName(table, TABLE_INBET__ID);
            HelpA.hideColumnByName(table, TABLE_INBET__FAKTURA_ID);
        }
        //
    }

    private void addRowJtable(HashMap<String, String> map, JTable table) {
        //
        Object[] jtableRow = new Object[]{
            map.get(DB.BUH_FAKTURA_INBET__INBET_ID), // hidden
            map.get(DB.BUH_FAKTURA_INBET__FAKTURA_ID), // hidden
            map.get(DB.BUH_FAKTURA_INBET__INBETALD),
            map.get(DB.BUH_FAKTURA_INBET__BETAL_METHOD),
            map.get(DB.BUH_FAKTURA_INBET__ANNAT),
            map.get(DB.BUH_FAKTURA_INBET__BETAL_DATUM)
        };
        //
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(jtableRow);
        //
    }

    private void fillJTableheader() {
        //
        //
        JTable table = getTableInbet();
        //
        String[] headers = {
            TABLE_INBET__ID,
            TABLE_INBET__FAKTURA_ID,
            TABLE_INBET__INBETALD,
            TABLE_INBET__INBET_METOD,
            TABLE_INBET__ANNAT,
            TABLE_INBET__DATUM
        };
        //
        table.setModel(new DefaultTableModel(null, headers));
        //
    }

    public void initBasicTab() {
        //
        basic = new Basic_Buh_(bim) {

            @Override
            protected void startUp() {
                fillJTableheader();
                fillInbetalningarJTable();
                showTableInvert();
            }

            @Override
            protected boolean fieldsValidated(boolean insert) {
                //
                if (containsEmptyObligatoryFields(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert())) {
                    HelpA.showNotification(LANG.MSG_2);
                    return false;
                }
                //
                if (containsInvalidatedFields(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert())) {
                    HelpA.showNotification(LANG.MSG_1);
                    return false;
                }
                //
                return true;
            }

            @Override
            public RowDataInvert[] getConfigTableInvert() {
                //
                RowDataInvert inbetald_kr = new RowDataInvertB("", DB.BUH_FAKTURA_INBET__INBETALD, TABLE_INBET__INBETALD, "", false, true, true);
                //
                RowDataInvert annat = new RowDataInvertB("", DB.BUH_FAKTURA_INBET__ANNAT, TABLE_INBET__ANNAT, "", true, true, false);
                //
                RowDataInvert betalmetod = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, DB.STATIC__BETAL_METHODS, DB.BUH_FAKTURA_INBET__BETAL_METHOD, TABLE_INBET__INBET_METOD, "", true, true, false);
                betalmetod.enableFixedValuesAdvanced();
                betalmetod.setUneditable();
                //
                RowDataInvert[] rows = {
                    inbetald_kr,
                    betalmetod,
                    annat
                };
                //
                return rows;
            }

            @Override
            public void showTableInvert() {
                TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert(), false, "");
                TABLE_INVERT = null;
                TABLE_INVERT = tableBuilder.buildTable_B(this);
                setMargin(TABLE_INVERT, 5, 0, 5, 0);
                showTableInvert(jPanel1);
                //
                addTableInvertRowListener(TABLE_INVERT, this);
            }

            @Override
            public void keyReleasedForward(TableInvert ti, KeyEvent ke) {
                //
                super.keyReleasedForward(ti, ke); //To change body of generated methods, choose Tools | Templates.
                //
                JLinkInvert jli = (JLinkInvert) ke.getSource();
                //
                String col_name = ti.getCurrentColumnName(ke.getSource());
                //
                if (col_name.equals(DB.BUH_FAKTURA_INBET__INBETALD)) {
                    //
                    Validator.validateDigitalInput(jli);
                    //
                } else if (col_name.equals(DB.BUH_FAKTURA_INBET__ANNAT)) {
                    //
                    Validator.validateMaxInputLength(jli, 50);
                    //
                }
            }

        };
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_faktura_inbets = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 153, 153));
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jTable_faktura_inbets.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable_faktura_inbets);

        jButton1.setText("Insert");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(260, 260, 260)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       if(basic.fieldsValidated(true)){
          insert(); 
       }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(EditPanel_Basic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(EditPanel_Basic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(EditPanel_Basic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(EditPanel_Basic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new EditPanel_Basic().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    protected javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    protected javax.swing.JTable jTable_faktura_inbets;
    // End of variables declaration//GEN-END:variables
}
