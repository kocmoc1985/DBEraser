/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.LANG;
import MyObjectTable.OutPut;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert_;
import MyObjectTableInvert.TableInvert;
import forall.GP;
import forall.HelpA;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KOCMOC
 */
public class EditPanel_Inbet extends javax.swing.JFrame {

    private Basic_Buh_ basic;
    private final BUH_INVOICE_MAIN bim;
    private final String fakturaId;
    private final String fakturaNr;
    private final String fakturaKund;
    //
    private static final String TABLE_INBET__ID = "ID";
    private static final String TABLE_INBET__FAKTURA_ID = "F ID";
    private static final String TABLE_INBET__INBETALD = "INBETALD";
    private static final String TABLE_INBET__INBET_METOD = "BETALMETOD";
    private static final String TABLE_INBET__ANNAT = "KOMMENT";
    private static final String TABLE_INBET__DATUM = "DATUM";

    /**
     * Creates new form EditPanel
     *
     * @param bim
     * @param fakturaId
     * @param fakturaNr
     * @param fakturaKund
     */
    public EditPanel_Inbet(BUH_INVOICE_MAIN bim, String fakturaId, String fakturaNr, String fakturaKund) {
        initComponents();
        this.bim = bim;
        this.fakturaId = fakturaId;
        this.fakturaNr = fakturaNr;
        this.fakturaKund = fakturaKund;
        init();
    }

    private void init() {
        //
        this.setTitle("Inbetalning");
        this.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL_PROD_PLAN).getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle();
        //
        initBasicTab();
        //
        fillJTableheader();
        fillInbetalningarJTable();
        basic.showTableInvert();
        countRestToPayAndShow();
    }

    private JTable getTableInbet() {
        return jTable_faktura_inbets;
    }

    private double getFakturaTotal() {
        return bim.getFakturaTotal();
    }

    private void setTitle() {
        this.jLabel_fakturanr_value.setText("Fakturanr: " + fakturaNr);
        this.jLabel_kund_value.setText("Fakturakund: " + fakturaKund);
    }

    private void refresh() {
        fillInbetalningarJTable();
        basic.clearAllRowsTableInvert_jcombobox_special(basic.TABLE_INVERT, 1);

//        HelpA.markFirstRowJtable(getTableInbet());
//        bim.jTableArticles_clicked();
    }

    private double coundInbetald() {
        return HelpA.countSumJTable(jTable_faktura_inbets, TABLE_INBET__INBETALD);
    }

    private int defineBetaldStatus() {
        //
        double fakturaTotal = getFakturaTotal();
        //
        double inbetTotal = coundInbetald();
        //
        if (inbetTotal == 0) {
            return 0;
        }
        //
        if (fakturaTotal == inbetTotal) {
            return 1; // BETALD
        } else if (inbetTotal < fakturaTotal) {
            return 2; // DELVIS
        } else if (inbetTotal > fakturaTotal) {
            return 3; // ÖVERBETALD
        } else {
            return 0; // INTE BETALD
        }
    }

    private void delete_insert_end_action() {
        //
        refresh();
        //
        basic.executeSetFakturaBetald(fakturaId, defineBetaldStatus());
        //
        countRestToPayAndShow();
        //
        bim.invoiceB.refresh_b();
    }

    private void delete() {
        //
        String inbetId = HelpA.getValueSelectedRow(getTableInbet(), TABLE_INBET__ID);
        //
        HashMap<String, String> map = bim.getDELETE(DB.BUH_FAKTURA_INBET__INBET_ID, inbetId, DB.TABLE__BUH_FAKTURA_INBET);
        //
        String json = JSon.hashMapToJSON(map);
        //
        basic.executeDelete(json);
        //
        //
        delete_insert_end_action();
        //
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
            HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_FAKTURA_IBET_TO_DB, json);
            //
        } catch (Exception ex) {
            Logger.getLogger(CustomersA.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        //
        delete_insert_end_action();
        //
    }

    private final String kvar_att_betala_tableinvert_fake = "kvar";

    private void countRestToPayAndShow() {
        //
        double total = getFakturaTotal();
        //
        double inbetald = coundInbetald();
        //
        if (inbetald < total) {
            double restToPay = total - inbetald;
            basic.setValueTableInvert(DB.BUH_FAKTURA_INBET__INBETALD, basic.TABLE_INVERT, "" + restToPay);
            basic.setValueTableInvert(kvar_att_betala_tableinvert_fake, basic.TABLE_INVERT, "" + restToPay);
            basic.setColorTableInvert(kvar_att_betala_tableinvert_fake,  basic.TABLE_INVERT, Color.white);
        } else if (inbetald == total) {
            basic.setValueTableInvert(kvar_att_betala_tableinvert_fake, basic.TABLE_INVERT, "BETALD");
            basic.setColorTableInvert(kvar_att_betala_tableinvert_fake,  basic.TABLE_INVERT, Color.green);
        } else if (inbetald > total) {
            double overbet = inbetald - total;
            basic.setValueTableInvert(kvar_att_betala_tableinvert_fake, basic.TABLE_INVERT, "ÖVERBETALD  (" + overbet + ")");
            basic.setColorTableInvert(kvar_att_betala_tableinvert_fake,  basic.TABLE_INVERT, Color.yellow);
        }
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
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM__GET_FAKTURA_INBET, json);
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
            Logger.getLogger(EditPanel_Inbet.class.getName()).log(Level.SEVERE, null, ex);
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
            basic.getLongName(DB.STATIC__BETAL_METHODS, map.get(DB.BUH_FAKTURA_INBET__BETAL_METHOD)),
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
                RowDataInvert kvar_att_betala = new RowDataInvertB("", kvar_att_betala_tableinvert_fake, "KVAR ATT BETALA", "", true, true, false);
                kvar_att_betala.setDontAquireTableInvertToHashMap();
                kvar_att_betala.setUneditable();
//                kvar_att_betala.setDisabled();
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
                    kvar_att_betala,
                    inbetald_kr,
                    betalmetod,
                    annat
                };
                //
                return rows;
            }

            @Override
            public void showTableInvert() {
                TableBuilderInvert_ tableBuilder = new TableBuilderInvert_(new OutPut(), null, getConfigTableInvert(), false, "");
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_faktura_inbets = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel_fakturanr_value = new javax.swing.JLabel();
        jLabel_kund_value = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new java.awt.BorderLayout());

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

        jPanel2.setLayout(new java.awt.GridLayout(2, 2));

        jLabel_fakturanr_value.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_fakturanr_value.setForeground(new java.awt.Color(153, 153, 153));
        jPanel2.add(jLabel_fakturanr_value);

        jLabel_kund_value.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_kund_value.setForeground(new java.awt.Color(153, 153, 153));
        jPanel2.add(jLabel_kund_value);

        jPanel3.setLayout(new java.awt.GridLayout(1, 2));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton1.setToolTipText("Registrera inbetalning");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton2.setToolTipText("Radera Inbetalning");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (basic.fieldsValidated(true)) {
            insert();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //
        if (HelpA.rowSelected(getTableInbet()) == false) {
            return;
        }
        //
        if (GP_BUH.confirmWarning(LANG.MSG_3_2) == false) {
            return;
        }
        //
        delete();
        //
    }//GEN-LAST:event_jButton2ActionPerformed

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
//            java.util.logging.Logger.getLogger(EditPanel_Inbet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(EditPanel_Inbet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(EditPanel_Inbet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(EditPanel_Inbet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new EditPanel_Inbet().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel_fakturanr_value;
    private javax.swing.JLabel jLabel_kund_value;
    protected javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    protected javax.swing.JTable jTable_faktura_inbets;
    // End of variables declaration//GEN-END:variables
}
