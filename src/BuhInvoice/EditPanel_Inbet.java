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
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import forall.HelpA;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
public class EditPanel_Inbet extends javax.swing.JFrame implements MouseListener {

    protected Basic_Buh basic;
    protected final LAFakturering bim;
    private final String fakturaId;
    private final String fakturaNr;
    private final String fakturaKund;
    //
    private final boolean IS_KONTANT_FAKTURA;
    //
    private static final String TABLE_INBET__ID = "ID";
    private static final String TABLE_INBET__FAKTURA_ID = "F ID";
    private static final String TABLE_INBET__INBETALD = "INBETALNING";
    private static final String TABLE_INBET__INBET_METOD = "BETALMETOD";
    private static final String TABLE_INBET__ANNAT = "KOMMENT";
    private static final String TABLE_INBET__DATUM = "DATUM";
    private static final String TABLE_INBET__DONE_BY = "ANVÄNDARE";

    /**
     * Creates new form EditPanel
     *
     * @param bim
     * @param isKontantFaktura
     * @param fakturaId
     * @param fakturaNr
     * @param fakturaKund
     */
    public EditPanel_Inbet(LAFakturering bim,boolean isKontantFaktura, String fakturaId, String fakturaNr, String fakturaKund) {
        initComponents();
        this.bim = bim;
        this.IS_KONTANT_FAKTURA = isKontantFaktura;
        this.fakturaId = fakturaId;
        this.fakturaNr = fakturaNr;
        this.fakturaKund = fakturaKund;
        init_();
    }

    private void init_() {
        //
        getJTable().addMouseListener(this);
        //
        HelpA.setUneditableJTable(jTable_1);
        //
        init();
    }

    protected void init() {
        //
        this.setTitle(LANG.FRAME_TITLE_2);
        this.setIconImage(GP_BUH.getBuhInvoicePrimIcon());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setHeader();
        //
        initBasicTab();
        //
        fillJTableheader();
        fillJTable();
        //
        basic.showTableInvert();
        countRestToPayAndShow();
    }

   
    
    protected JTable getJTable() {
        return jTable_1;
    }

    private double getFakturaTotal() {
        return bim.getFakturaTotal();
    }
    
    protected void setHeaderAfterCreationOfKontantFaktura() {
        this.jLabel_fakturanr_value.setText("Kontantfaktura: " + fakturaNr + ", " + fakturaKund);
        this.jLabel_kund_value.setText("Viktigt! Välj betalmetod och spara");
    }

    protected void setHeader() {
        this.jLabel_fakturanr_value.setText("Fakturanr: " + fakturaNr);
        this.jLabel_kund_value.setText("Fakturakund: " + fakturaKund);
    }

    protected void refresh() {
        fillJTable();
        basic.clearAllRowsTableInvert_jcombobox_special(basic.TABLE_INVERT, 1);

//        HelpA.markFirstRowJtable(getJTable());
//        bim.jTableArticles_clicked();
    }

    private double coundInbetald() {
        return HelpA.countSumJTable(jTable_1, TABLE_INBET__INBETALD);
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
        bim.invoiceB.refresh_c();
    }

    private void delete() {
        //
        String inbetId = HelpA.getValueSelectedRow(getJTable(), TABLE_INBET__ID);
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
        map.put(DB.BUH_FAKTURA_INBET__KUND_ID, "777");//[#KUND-ID-INSERT#]
        map.put(DB.BUH_FAKTURA_INBET__DONE_BY, GP_BUH.getChangedBy());
        map.put(DB.BUH_FAKTURA_INBET__FAKTURA_ID, fakturaId);
        //
        String json = JSon.hashMapToJSON(map);
        //
        try {
            //
            HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN, DB.PHP_FUNC_FAKTURA_INBET_TO_DB, json);
            //
        } catch (Exception ex) {
            Logger.getLogger(CustomersA_.class.getName()).log(Level.SEVERE, null, ex);
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
            double restToPay = GP_BUH.round_double(total - inbetald);
            basic.setValueTableInvert(DB.BUH_FAKTURA_INBET__INBETALD, basic.TABLE_INVERT, "" + restToPay);
            basic.setValueTableInvert(kvar_att_betala_tableinvert_fake, basic.TABLE_INVERT, "" + restToPay);
            basic.setColorTableInvert(kvar_att_betala_tableinvert_fake, basic.TABLE_INVERT, Color.white);
        } else if (inbetald == total) {
            basic.setValueTableInvert(kvar_att_betala_tableinvert_fake, basic.TABLE_INVERT, "BETALD");
            basic.setColorTableInvert(kvar_att_betala_tableinvert_fake, basic.TABLE_INVERT, Color.green);
        } else if (inbetald > total) {
            double overbet = GP_BUH.round_double(inbetald - total);
            basic.setValueTableInvert(kvar_att_betala_tableinvert_fake, basic.TABLE_INVERT, "ÖVERBETALD  (" + overbet + ")");
            basic.setColorTableInvert(kvar_att_betala_tableinvert_fake, basic.TABLE_INVERT, Color.yellow);
        }
        //
    }

    protected void fillJTable() {
        //
        JTable table = getJTable();
        //
        HelpA.clearAllRowsJTable(table);
        //
//        String json = bim.getSELECT_fakturaId();
        String json = bim.getSELECT_fakturaId__doubleWhere();
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

    protected void addRowJtable(HashMap<String, String> map, JTable table) {
        //
        Object[] jtableRow = new Object[]{
            map.get(DB.BUH_FAKTURA_INBET__INBET_ID), // hidden
            map.get(DB.BUH_FAKTURA_INBET__FAKTURA_ID), // hidden
            map.get(DB.BUH_FAKTURA_INBET__INBETALD),
            JSon.getLongName(DB.STATIC__BETAL_METHODS, map.get(DB.BUH_FAKTURA_INBET__BETAL_METHOD)),
            map.get(DB.BUH_FAKTURA_INBET__ANNAT),
            map.get(DB.BUH_FAKTURA_INBET__BETAL_DATUM),
            map.get(DB.BUH_FAKTURA_INBET__DONE_BY)
        };
        //
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(jtableRow);
        //
    }

    protected void fillJTableheader() {
        //
        //
        JTable table = getJTable();
        //
        String[] headers = {
            TABLE_INBET__ID,
            TABLE_INBET__FAKTURA_ID,
            TABLE_INBET__INBETALD,
            TABLE_INBET__INBET_METOD,
            TABLE_INBET__ANNAT,
            TABLE_INBET__DATUM,
            TABLE_INBET__DONE_BY
        };
        //
        table.setModel(new DefaultTableModel(null, headers));
        //
    }

    public void initBasicTab() {
        //
        basic = new Basic_Buh(bim) {

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
                if(IS_KONTANT_FAKTURA){
                    inbetald_kr.setUneditable();
                }
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
                TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert(), false, "");
                TABLE_INVERT = null;
                TABLE_INVERT = tableBuilder.buildTable_B(this);
                setMargin(TABLE_INVERT, 5, 0, 5, 0);
                showTableInvert(jPanel1);
                //
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
        jTable_1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel_fakturanr_value = new javax.swing.JLabel();
        jLabel_kund_value = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1_commit = new javax.swing.JButton();
        jButton2_delete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jTable_1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable_1);

        jPanel2.setLayout(new java.awt.GridLayout(2, 2));

        jLabel_fakturanr_value.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_fakturanr_value.setForeground(new java.awt.Color(153, 153, 153));
        jPanel2.add(jLabel_fakturanr_value);

        jLabel_kund_value.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_kund_value.setForeground(new java.awt.Color(153, 153, 153));
        jPanel2.add(jLabel_kund_value);

        jPanel3.setLayout(new java.awt.GridLayout(1, 2));

        jButton1_commit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton1_commit.setToolTipText("Registrera inbetalning");
        jButton1_commit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1_commitActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1_commit);

        jButton2_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton2_delete.setToolTipText("Radera Inbetalning");
        jButton2_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2_deleteActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2_delete);

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

    private void jButton1_commitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1_commitActionPerformed
        jButton1ActionPerformed();
    }//GEN-LAST:event_jButton1_commitActionPerformed

    protected void jButton1ActionPerformed() {
        if (basic.fieldsValidated(true)) {
            insert();
        }
    }

    private void jButton2_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2_deleteActionPerformed
       jButton2ActionPerformed_delete();
    }//GEN-LAST:event_jButton2_deleteActionPerformed

    protected void jButton2ActionPerformed_delete(){
        //
        if (HelpA.rowSelected(getJTable()) == false) {
            HelpA.showNotification(LANG.MSG_3_2_2);
            return;
        }
        //
        if (GP_BUH.confirmWarning(LANG.MSG_3_2) == false) {
            return;
        }
        //
        delete();
        //
    }
    
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
//            java.util.logging.Logger.getLogger(EditPanel_Inbet_.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(EditPanel_Inbet_.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(EditPanel_Inbet_.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(EditPanel_Inbet_.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new EditPanel_Inbet_().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton jButton1_commit;
    protected javax.swing.JButton jButton2_delete;
    private javax.swing.JLabel jLabel_fakturanr_value;
    private javax.swing.JLabel jLabel_kund_value;
    protected javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    protected javax.swing.JTable jTable_1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
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
}
