/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import BuhInvoice.LAFakturering;
import BuhInvoice.DB;
import BuhInvoice.GP_BUH;
import BuhInvoice.InvoiceB;
import BuhInvoice.Invoice_;
import MyObjectTableInvert.TableInvert;
import forall.HelpA;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Sedan 1 januari 2021 är taket för rutavdraget 75 000 kronor per person och
 * år. Rotavdraget kvarstår med max 50 000 kronor per person och år. Sammanlagt
 * kan en privatperson få 75 000 kronor i rotavdrag och rutavdrag per år.
 *
 * @author KOCMOC
 */
public class RutRotFrame extends javax.swing.JFrame {

    private final LAFakturering bim;
    private final Invoice_ invoice;
    private RutRot rut;
    private final JTable articlesTable;
    private double AVDRAGS_GILL_BELOPP = 0;
    protected double AVDRAG_TOTAL = 0;
    private double AVDRAG_PER_PERSON = 0;
    private static double ROT_ELLER_RUT__PERCENT = DB.GET_CONSTANT__DOUBLE("ROT_PERCENT", DB.ROT_PERCENT); // RUT = 50% -> tvätt, städ / ROT = 30% ->bygg
    private String PNR = "";
    private String EFTERNAMN = "";
    private String FORNAMN = "";

    /**
     * Creates new form RutRot
     *
     * @param bim
     * @param articlesTable
     */
//    public RutRotFrame() {
//        initComponents();
//    }
    public RutRotFrame(LAFakturering bim, JTable articlesTable, Invoice_ invoice) {
        initComponents();
        this.bim = bim;
        this.invoice = invoice;
        this.articlesTable = articlesTable;
        init();
    }

    private void init() {
        //
        this.setIconImage(GP_BUH.getBuhInvoicePrimIcon());
        this.setTitle("RUT-Avdrag");
        GP_BUH.centerAndBringToFront(this);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getPersonalData();
        this.rut = new RutRot(bim, this, PNR, FORNAMN, EFTERNAMN);
        this.rut.showTableInvert();
        this.rut.showTableInvert_2();
        //
        jCheckBox_ROT.setSelected(true);
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

    private void getPersonalData() {
        TableInvert ti = bim.getTableInvert();
        HashMap<String, String> map_e__lev_data = invoice.getFakturaKundData(DB.PHP_FUNC_PARAM_GET_ONE_FAKTURA_KUND_ALL_DATA, ti);
        PNR = map_e__lev_data.getOrDefault(DB.BUH_FAKTURA_KUND___ORGNR, "");
        String namnFornamn = map_e__lev_data.getOrDefault(DB.BUH_FAKTURA_KUND___NAMN, "");
        //
        if (namnFornamn == null || namnFornamn.isEmpty()) {
            return;
        }
        //
        String[] arr = namnFornamn.split(" ");
        //
        if (arr.length == 2) {
            FORNAMN = arr[0];
            EFTERNAMN = arr[1];
        } else if (arr.length == 1) {
            FORNAMN = arr[0];
        }
        //
    }

    public void makeVisible() {
        this.setVisible(true);
        //
        String fastighetBetackning = rut.getFastighetsBeteckning(); // Needed otherwise "fastighetBetackning" will not be shown
        //
        this.rut.showTableInvert();
        this.rut.showTableInvert_2();
        //
        rut.setFastighetsBeteckning(fastighetBetackning);
    }

    private void hideCols(JTable table) {
        //
//        HelpA.hideColumnByName(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL);
        HelpA.hideColumnByName(table, InvoiceB.TABLE_INVOICE_ARTIKLES__OMVAND_SKATT);
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
            RutRot.COL__AVDRAG,
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
            map.get(DB.BUH_FAKTURA_RUT_PERSON__FORNAMN),
            map.get(DB.BUH_FAKTURA_RUT_PERSON__EFTERNAMN),
            "0",
            map.get(DB.BUH_FAKTURA_RUT_PERSON__PNR)
        };
        //
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(jtableRow);
        //
        //
//        HelpA.setValueAllRows(table, RutRot.COL__AVDRAG, "" + AVDRAG_PER_PERSON);
        recalcAndSetAvdragPerPers();
        //
        //
        HelpA.markFirstRowJtable(table);
        //
        rut.showTableInvert();
        //
        TableInvert ti = (TableInvert) rut.TABLE_INVERT;
        ti.clearAllRows();
    }

    private void recalcAndSetAvdragPerPers() {
        HelpA.setValueAllRows(jTable3, RutRot.COL__AVDRAG, "" + AVDRAG_PER_PERSON);
    }

    private boolean is_ROT__Bygg() {
        if (ROT_ELLER_RUT__PERCENT == 0.3) { // ROT -> BYGG -> 30%
            return true;
        } else {
            return false;
        }
    }

    public double defineAvdragsTak() {
        //
        if(rut != null && rut.TABLE_INVERT != null){
            //
            String val = rut.getValueTableInvert(DB.BUH_FAKTURA_RUT_PERSON__AVDRAGSTAK_VALUE_NOT_AQUIRE);
            //
            if(HelpA.isNumber(val)){
               return Double.parseDouble(val);  
            }
            //
        }
        //
        if (is_ROT__Bygg()) {
            return DB.GET_CONSTANT__DOUBLE("ROT_MAX", DB.ROT_MAX);
        } else {
            return DB.GET_CONSTANT__DOUBLE("RUT_MAX", DB.RUT_MAX);
        }
        //
    }

    private void countAvdrag(int antalPers) {
        //
        AVDRAGS_GILL_BELOPP = countJTable(jTable2); // ja det inkluderar moms
        //
        AVDRAG_TOTAL = AVDRAGS_GILL_BELOPP * ROT_ELLER_RUT__PERCENT;
        //
        double avdragsTaket = defineAvdragsTak();
        //
        if (AVDRAG_TOTAL > avdragsTaket) {
            AVDRAG_TOTAL = avdragsTaket;
        }
        //
        System.out.println("RUT BELOPP INNAN AVDRAG: " + AVDRAGS_GILL_BELOPP);
        System.out.println("AVDRAG TOTAL: " + AVDRAG_TOTAL);
        //
        double antal_pers_som_delar_på_avdraget = jTable3.getRowCount();
        //
        if (antal_pers_som_delar_på_avdraget == 0 && antalPers == -1) {
            return;
        }
        //
        if (antalPers != -1) {
            antal_pers_som_delar_på_avdraget = antalPers;
        }
        //
        AVDRAG_PER_PERSON = AVDRAG_TOTAL / antal_pers_som_delar_på_avdraget;
        //
        System.out.println("AVDRAG PER PERSON: " + AVDRAG_PER_PERSON);
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
        ArrayList<TrippleParamEntry> list = new ArrayList<>();
        //
        for (int x = 0; x < table.getRowCount(); x++) {
            //
            int col_pris = HelpA.getColByName(table, InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS);
            int col_percent = HelpA.getColByName(table, InvoiceB.TABLE_INVOICE_ARTIKLES__MOMS_SATS);
            int col_antal = HelpA.getColByName(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL);
            //
            String val_pris = (String) table.getValueAt(x, col_pris);
            String val_percent = (String) table.getValueAt(x, col_percent);
            String val_antal = (String) table.getValueAt(x, col_antal);
            //
            if (val_pris == null || val_pris.isEmpty() || val_pris.equals("null")
                    || val_percent == null || val_percent.isEmpty() || val_percent.equals("null")) {
                //
                return null;
                //
            }
            //
            TrippleParamEntry dpe = new TrippleParamEntry(val_pris, val_percent, val_antal);
            list.add(dpe);
            //
        }
        //
        for (TrippleParamEntry entry : list) {
            double pris = entry.getParam_a__double();
            double moms = entry.getParam_b__percent();
            double antal = entry.getParam_c__double();
            double inkl_moms = (pris + (pris * moms)) * antal;
            sum += inkl_moms;
        }
        //
        return sum;
        //
    }

    private void autodefineRutArticlesJTable(JTable table) {
        //
        String[] dict = new String[]{"arbet"};
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
                        countAvdrag(-1);
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
        jCheckBox_ROT = new javax.swing.JCheckBox();
        jCheckBox_RUT = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel_fastighets_beteckning = new javax.swing.JPanel();

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
        jLabel1.setText("Artiklar som omfattas av ROT eller RUT");

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

        jCheckBox_ROT.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jCheckBox_ROT.setForeground(new java.awt.Color(153, 153, 153));
        jCheckBox_ROT.setText("ROT - renovering, ombyggnad och tillbyggnad");
        jCheckBox_ROT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_ROTActionPerformed(evt);
            }
        });

        jCheckBox_RUT.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jCheckBox_RUT.setForeground(new java.awt.Color(153, 153, 153));
        jCheckBox_RUT.setText("RUT - rengöring, underhåll och tvätt");
        jCheckBox_RUT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_RUTActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("Samtliga faktura artiklar");

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton5.setText("KLART");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jPanel_fastighets_beteckning.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jCheckBox_ROT, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
                                .addComponent(jPanel_table_invert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jCheckBox_RUT))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
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
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE))))
                                .addGap(0, 15, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addComponent(jPanel_fastighets_beteckning, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCheckBox_ROT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox_RUT))
                    .addComponent(jPanel_fastighets_beteckning, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 81, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel_table_invert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jTable1.getRowCount() > 0) {
            HelpA.addRowFromOneTableToAnother_withRemove(jTable1, jTable2, -1);
            countAvdrag(-1);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (jTable2.getRowCount() > 0) {
            HelpA.addRowFromOneTableToAnother_withRemove(jTable2, jTable1, -1);
            countAvdrag(-1);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //
        if (HelpA.isEmtyJTable(jTable2)) {
            HelpA.showNotification(LANG.MSG_27);
            return;
        }
        //
        countAvdrag(jTable3.getRowCount() + 1);
        //
        addPerson();
        //
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //
        deletePerson();
        //
        int antal_pers = jTable3.getRowCount();
        //
        if (antal_pers == 0 || antal_pers == 1) {
            countAvdrag(-1);
        } else {
            countAvdrag(antal_pers - 1);
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jCheckBox_ROTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_ROTActionPerformed
        setRotOrRut_CheckBoxes(evt);
    }//GEN-LAST:event_jCheckBox_ROTActionPerformed

    private void jCheckBox_RUTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_RUTActionPerformed
        setRotOrRut_CheckBoxes(evt);
    }//GEN-LAST:event_jCheckBox_RUTActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // [#RUTROT#]
        // Setting "AVDRAG_TOTAL" for counting
        //
        if (HelpA.isEmtyJTable(jTable3)) {
            HelpA.showNotification(LANG.MSG_26);
            return;
        }
        //
        invoice.setRutAvdragTotal(AVDRAG_TOTAL, rut);
        //
        this.setVisible(false);
        //
    }//GEN-LAST:event_jButton5ActionPerformed

    private void setRotOrRut_CheckBoxes(java.awt.event.ActionEvent evt) {
        //
        JCheckBox box = (JCheckBox) evt.getSource();
        //
        if (box.equals(jCheckBox_ROT)) {
            jCheckBox_RUT.setSelected(false);
            ROT_ELLER_RUT__PERCENT = DB.GET_CONSTANT__DOUBLE("ROT_PERCENT", DB.ROT_PERCENT); // ROT -> BYGG -> 30%
            //
            rut.setValueTableInvert(DB.BUH_FAKTURA_RUT_PERSON__AVDRAGSTAK_VALUE_NOT_AQUIRE, rut.TABLE_INVERT, DB.ROT_MAX);
            //
        } else if (box.equals(jCheckBox_RUT)) {
            jCheckBox_ROT.setSelected(false);
            ROT_ELLER_RUT__PERCENT = DB.GET_CONSTANT__DOUBLE("RUT_PERCENT", DB.RUT_PERCENT); // RUT -> STÄD -> 50%
            //
            rut.setValueTableInvert(DB.BUH_FAKTURA_RUT_PERSON__AVDRAGSTAK_VALUE_NOT_AQUIRE, rut.TABLE_INVERT, DB.RUT_MAX);
            //
        }
        //
        //IF both unselected
        if (jCheckBox_RUT.isSelected() == false && jCheckBox_ROT.isSelected() == false) {
            jCheckBox_ROT.setSelected(true);
            jCheckBox_RUT.setSelected(false);
        }
        //
        countAvdrag(-1);
        recalcAndSetAvdragPerPers();
        //
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JCheckBox jCheckBox_ROT;
    private javax.swing.JCheckBox jCheckBox_RUT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    public javax.swing.JPanel jPanel_fastighets_beteckning;
    protected javax.swing.JPanel jPanel_table_invert;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    public javax.swing.JTable jTable3;
    // End of variables declaration//GEN-END:variables
}
