/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import BuhInvoice.BUH_INVOICE_MAIN;
import BuhInvoice.DB;
import BuhInvoice.GP_BUH;
import BuhInvoice.InvoiceB;
import forall.HelpA;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KOCMOC
 */
public class RutRotFrame extends javax.swing.JFrame {

    private BUH_INVOICE_MAIN bim;
    private RutRot rut;
    private JTable articlesTable;

    /**
     * Creates new form RutRot
     *
     * @param bim
     * @param articlesTable
     */
//    public RutRotFrame() {
//        initComponents();
//    }
    public RutRotFrame(BUH_INVOICE_MAIN bim, JTable articlesTable) {
        initComponents();
        this.bim = bim;
        this.articlesTable = articlesTable;
        init();
    }

    private void init() {
        //
        this.setIconImage(GP_BUH.getBuhInvoicePrimIcon());
        this.setTitle("RUT-Avdrag");
        GP_BUH.centerAndBringToFront(this);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.rut = new RutRot(bim, this);
        this.rut.showTableInvert();
        //
        fillJTableheader_person();
        //
        HelpA.copyTableHeadersFromOneTableToAnother(bim.jTable_InvoiceA_Insert_articles, jTable1);
        hideCols(jTable1);
        HelpA.copyTableHeadersFromOneTableToAnother(bim.jTable_InvoiceA_Insert_articles, jTable2);
        hideCols(jTable2);
        //
        HelpA.copyDataFromOneTableToAnother(bim.jTable_InvoiceA_Insert_articles, jTable1);
        //
        HelpA.markFirstRowJtable(jTable1);
        //
        autodefineRutArticlesJTable(jTable1);
        //
    }

    private void hideCols(JTable table) {
        //
        HelpA.hideColumnByName(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL);
        HelpA.hideColumnByName(table, InvoiceB.TABLE_INVOICE_ARTIKLES__OMVANT_SKATT);
        HelpA.hideColumnByName(table, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT);
        HelpA.hideColumnByName(table, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT_KR);
        //
    }
    
    

    private void fillJTableheader_person() {
        //
        JTable table = jTable3;
        //
        String[] headers = {
            RutRot.COL__FORNAMN,
            RutRot.COL__EFTERNAMN,
            RutRot.COL__PNR,};
        //
        table.setModel(new DefaultTableModel(null, headers));
        //
    }

    private void deletePerson() {
        //
        JTable table = jTable3;
        //
        HelpA.removeRowJTable(table, table.getSelectedRow());
        //
    }

    private void addPerson() {
        //
        if (rut.fieldsValidated(false) == false) {
            return;
        }
        //
        JTable table = jTable3;
        //
        HashMap<String, String> map = rut.getValuesTableInvert();
        //
        Object[] jtableRow = new Object[]{
            map.get(DB.BUH_FAKTURA_RUT__FORNAMN),
            map.get(DB.BUH_FAKTURA_RUT__EFTERNAMN),
            map.get(DB.BUH_FAKTURA_RUT__PNR)
        };
        //
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(jtableRow);
        //
        //
        HelpA.markFirstRowJtable(table);
        //
    }

    private double AVDRAGS_GILL_BELOPP = 0;
    private double AVDRAG_TOTAL = 0;
    private double AVDRAG_PER_PERSON = 0;
    
     private void countAvdrag() {
        //
        double belopp_att_gora_avdrag_pa = countJTable(jTable2); // ja det inkluderar moms
        //
        double avdrag = belopp_att_gora_avdrag_pa * 0.3;
        //
        System.out.println("RUT BELOPP INNAN AVDRAG: " + belopp_att_gora_avdrag_pa);
        System.out.println("AVDRAG TOTAL: " + avdrag);
        //
        double antal_pers_som_delar_på_avdraget = jTable3.getRowCount();
        //
        if(antal_pers_som_delar_på_avdraget == 0){
            return;
        }
        //
        double avdrag_per_person = avdrag / antal_pers_som_delar_på_avdraget;
        //
        System.out.println("AVDRAG PER PERSON: " + avdrag_per_person);
        //
    }
    
   

    private static Double countJTable(JTable table) {
        //
        double sum = 0;
        //
        if (table.getRowCount() == 0) {
            return sum;
        }
        //
        ArrayList<DoubleParamEntry> list = new ArrayList<>();
        //
        for (int x = 0; x < table.getRowCount(); x++) {
            //
            int col_pris = HelpA.getColByName(table, InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS);
            int col_percent = HelpA.getColByName(table, InvoiceB.TABLE_INVOICE_ARTIKLES__MOMS_SATS);
            //
            String val_pris = (String) table.getValueAt(x, col_pris);
            String val_percent = (String) table.getValueAt(x, col_percent);
            //
            if (val_pris == null || val_pris.isEmpty() || val_pris.equals("null")
                    || val_percent == null || val_percent.isEmpty() || val_percent.equals("null")) {
                //
                return null;
                //
            }
            //
            DoubleParamEntry dpe = new DoubleParamEntry(val_pris, val_percent);
            list.add(dpe);
            //
        }
        //
        for (DoubleParamEntry entry : list) {
            double pris = entry.getParam_a__double();
            double moms = entry.getParam_b__percent();
            double inkl_moms = pris + (pris * moms);
            sum += inkl_moms;
        }
        //
        return sum;
        //
    }

    private void autodefineRutArticlesJTable(JTable table) {
        //
        String[] dict = new String[]{"arbete"};
        //
        for (int row = 0; row < table.getRowCount(); row++) {
            //
            int col_artikel = HelpA.getColByName(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN);
            int col_beskrivning = HelpA.getColByName(table, InvoiceB.TABLE_INVOICE_ARTIKLES__KOMMENT);
            //
            String val_artikel = (String) table.getValueAt(row, col_artikel);
            String val_beskrivning = (String) table.getValueAt(row, col_beskrivning);
            //
            if (val_artikel != null && val_beskrivning != null) {
                //
                for (String str : dict) {
                    //
                    if (val_artikel.toLowerCase().contains(str) || val_beskrivning.toLowerCase().contains(str)) {
                        //
                        HelpA.addRowFromOneTableToAnother_withRemove(jTable1, jTable2, row);
                        //
                        countAvdrag();
                        //
                    }
                    //
                }
                //
            }
            //
        }
        //
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
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel_table_invert = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/next.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/prev.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTable3);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 153, 153));
        jLabel1.setText("Artiklar som omfattas av RUT-avdrag");

        jPanel_table_invert.setLayout(new java.awt.BorderLayout());

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/next.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
                            .addComponent(jPanel_table_invert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(jPanel_table_invert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jTable1.getRowCount() > 0) {
            HelpA.addRowFromOneTableToAnother_withRemove(jTable1, jTable2, -1);
            countAvdrag();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (jTable2.getRowCount() > 0) {
            HelpA.addRowFromOneTableToAnother_withRemove(jTable2, jTable1, -1);
            countAvdrag();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        addPerson();
        countAvdrag();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        deletePerson();
        countAvdrag();
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    protected javax.swing.JPanel jPanel_table_invert;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    // End of variables declaration//GEN-END:variables
}
