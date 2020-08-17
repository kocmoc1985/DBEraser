/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTable.OutPut;
import MyObjectTable.Table;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import MyObjectTableInvert.TableRowInvert;
import forall.HelpA;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.JTextFieldInvert;
import MyObjectTableInvert.RowDataInvertB;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public abstract class Invoice extends Basic_Buh_ {
    
    protected Table TABLE_INVERT_2;
    protected Table TABLE_INVERT_3;
    protected Faktura_Entry faktura_entry;
    //
    private static double FAKTURA_TOTAL_EXKL_MOMS = 0;
    private static double FAKTURA_TOTAL = 0;
    private static double MOMS_TOTAL = 0;
    //
    public static boolean CURRENT_OPERATION_INSERT = false;
    
    public Invoice(BUH_INVOICE_MAIN bim) {
        super(bim);
        initFakturaEntry_();
    }
    
    protected void SET_CURRENT_OPERATION_INSERT(boolean insert) {
        //
        CURRENT_OPERATION_INSERT = insert;
        //
        if (insert) {
            //
            bim.jLabel_Faktura_Insert_or_Update.setText(LANG.LBL_MSG_1);
            //
            enableDisableButtons(bim.jPanel11, true);// Hide/Show Edit and Submit btns for editing of article when "INSERT"
            bim.jButton_update_article.setEnabled(false);
        } else {
            //
            bim.jLabel_Faktura_Insert_or_Update.setText(LANG.LBL_MSG_2);
            //
            enableDisableButtons(bim.jPanel11, false); // Hide/Show Edit and Submit btns for editing of article when "INSERT"
            bim.jButton_update_article.setEnabled(true);
        }
    }
    
    private void enableDisableButtons(JPanel parent,boolean enabled) {
        Component[] components = parent.getComponents();
        for (Component c : components) {
            if(c instanceof JButton){
              c.setEnabled(enabled);  
            }
            
        }
        
    }
    
    private void initFakturaEntry_() {
        faktura_entry = initFakturaEntry();
    }
    
    protected abstract Faktura_Entry initFakturaEntry();
    
    protected JTable getAllInvoicesTable() {
        return bim.jTable_invoiceB_alla_fakturor;
    }
    
    protected JTable getArticlesTable() {
        return bim.jTable_InvoiceA_Insert_articles;
    }
    
    protected void addArticle() {
        //
        if (containsEmptyObligatoryFields(TABLE_INVERT_2, DB.START_COLUMN, getConfigTableInvert_2())) {
            HelpA.showNotification(LANG.MSG_2);
            return;
        }
        //
        if (containsInvalidatedFields(TABLE_INVERT_2, DB.START_COLUMN, getConfigTableInvert_2())) {
            HelpA.showNotification(LANG.MSG_1);
            return;
        }
        //
        addArticleForJTable(getArticlesTable());
        //
        // For "UPDATE" the article is submited directly to DB
        // For "INSERT" the article is added to a LIST
        addArticleForDB();
        //

    }
    
    protected abstract void addArticleForJTable(JTable table);
    
    protected abstract void addArticleForDB();
    
    public void insertOrUpdate() {
        faktura_entry.insertOrUpdate();
    }
    
    protected String getFakturaKundId() {
        return getValueTableInvert(DB.BUH_FAKTURA_KUND__ID, TABLE_INVERT); // "fakturaKundId"
    }
    
    protected String getNextFakturaNr() {
        //
        HashMap<String, String> map = new HashMap<>();
        map.put(DB.BUH_FAKTURA__KUNDID__, bim.getKundId());
        String json = JSon.hashMapToJSON(map);
        //
        try {
            //
            String fakturaNr = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_GET_LATEST_FAKTURA_NR, json));
            //
            if (HelpA.checkIfNumber_b(fakturaNr)) {
                int nr = Integer.parseInt(fakturaNr);
                nr++; // OBS! Iam getting the last so i have to add to get the nr for the act. faktura
                return "" + nr;
            } else {
                return null;
            }
            //
        } catch (Exception ex) {
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean getInklMoms() {
        try {
            return Integer.parseInt(getValueTableInvert(DB.BUH_FAKTURA__INKL_MOMS, TABLE_INVERT_3)) == 1;
        } catch (Exception ex) {
            return true;
        }
    }
    
    public double getMomsSats() {
        try {
            return Double.parseDouble(getValueTableInvert(DB.BUH_FAKTURA__MOMS_SATS, TABLE_INVERT_3));
        } catch (Exception ex) {
            return 0.25;
        }
    }
    
    public boolean getMakulerad() {
        return Integer.parseInt(getValueTableInvert(DB.BUH_FAKTURA__MAKULERAD, TABLE_INVERT_3)) == 1;
    }
    
    protected void resetFakturaTotal() {
        //
        FAKTURA_TOTAL = 0;
        FAKTURA_TOTAL_EXKL_MOMS = 0;
        MOMS_TOTAL = 0;
        //
        BUH_INVOICE_MAIN.jTextField_total_inkl_moms.setText("" + getFakturaTotal());
        BUH_INVOICE_MAIN.jTextField_total_exkl_moms.setText("" + getTotalExklMoms());
        BUH_INVOICE_MAIN.jTextField_moms.setText("" + getMomsTotal());
    }
    
    protected void countFakturaTotal(JTable table) {
        String prisColumn = InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS;
        String antalColumn = InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL;
        countFakturaTotal(table, prisColumn, antalColumn);
    }
    
    private void countFakturaTotal(JTable table, String prisColumn, String antalColumn) {
        //
        FAKTURA_TOTAL = 0;
        FAKTURA_TOTAL_EXKL_MOMS = 0;
        MOMS_TOTAL = 0;
        //
        double pris;
        int antal;
        //
        for (int i = 0; i < table.getModel().getRowCount(); i++) {
            pris = Double.parseDouble(HelpA.getValueGivenRow(table, i, prisColumn));
            antal = Integer.parseInt(HelpA.getValueGivenRow(table, i, antalColumn));
            //
            FAKTURA_TOTAL += (pris * antal);
            //
        }
        //
        if (getInklMoms()) {
            MOMS_TOTAL = FAKTURA_TOTAL * getMomsSats();
            FAKTURA_TOTAL += MOMS_TOTAL;
            FAKTURA_TOTAL_EXKL_MOMS = FAKTURA_TOTAL - MOMS_TOTAL;
        }
        //
        BUH_INVOICE_MAIN.jTextField_total_inkl_moms.setText("" + getFakturaTotal());
        BUH_INVOICE_MAIN.jTextField_total_exkl_moms.setText("" + getTotalExklMoms());
        BUH_INVOICE_MAIN.jTextField_moms.setText("" + getMomsTotal());
        //
    }
    
    protected double getFakturaTotal() {
        return HelpA.round_double(FAKTURA_TOTAL);
    }
    
    protected double getMomsTotal() {
        return HelpA.round_double(MOMS_TOTAL);
    }
    
    protected double getTotalExklMoms() {
        return HelpA.round_double(FAKTURA_TOTAL_EXKL_MOMS);
    }
    
    public abstract RowDataInvert[] getConfigTableInvert_2();
    
    public abstract RowDataInvert[] getConfigTableInvert_3();
    
    @Override
    public void showTableInvert() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert(), false, "buh_faktura_a");
        TABLE_INVERT = null;
        TABLE_INVERT = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT, 5, 0, 5, 0);
        showTableInvert(bim.jPanel2_faktura_main);
        //
        addTableInvertRowListener(TABLE_INVERT, this);
        //
    }
    
    public void showTableInvert_2() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_2(), false, "buh_f_artikel");
        TABLE_INVERT_2 = null;
        TABLE_INVERT_2 = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT_2, 5, 0, 5, 0);
        showTableInvert(bim.jPanel_articles, TABLE_INVERT_2);
        //
        addTableInvertRowListener(TABLE_INVERT_2, this);
    }
    
    public void showTableInvert_3() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_3(), false, "buh_faktura_b");
        TABLE_INVERT_3 = null;
        TABLE_INVERT_3 = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT_3, 5, 0, 5, 0);
        showTableInvert(bim.jPanel3_faktura_sec, TABLE_INVERT_3);
        //
        addTableInvertRowListener(TABLE_INVERT_3, this);
        //
        hideMomsSatsIfExklMoms(); // **********************************
        //
    }

    /**
     * This config is for editing of articles
     *
     * @param reverse
     * @return
     */
    public RowDataInvert[] getConfigTableInvert_edit_articles() {
        //
        JTable table = getArticlesTable();
        //
        String fixedComboValues_a = JSon._get(
                HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN),
                HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_ID),
                requestJComboValuesHttp(DB.PHP_FUNC_PARAM_GET_KUND_ARTICLES, DB.BUH_FAKTURA_ARTIKEL___NAMN, DB.BUH_F_ARTIKEL__ARTIKELID));
//        String fixedComboValues_a = "Skruv;1,Spik;2,Hammare;3,Traktor;4,Skruvmejsel;5"; // This will aquired from SQL
        RowDataInvert kund = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_a, DB.BUH_F_ARTIKEL__ARTIKELID, "ARTIKEL", "", true, true, true);
        kund.enableFixedValuesAdvanced();
        kund.setUneditable();
        //
        //
        String komm = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__KOMMENT);
        RowDataInvert komment = new RowDataInvertB(komm, DB.BUH_F_ARTIKEL__KOMMENT, "KOMMENTAR", "", true, true, false);
        //
        String ant = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL);
        RowDataInvert antal = new RowDataInvertB(ant, DB.BUH_F_ARTIKEL__ANTAL, "ANTAL", "", false, true, false);
        //
//        String fixedComboValues_b = JSon._get_special_(
//                DB.STATIC__ENHET,
//                HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ENHET)
//        );
        //
        String fixedComboValues_b = JSon._get(
                HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ENHET),
                "",
                DB.STATIC__ENHET
        );
        //
        RowDataInvert enhet = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_b, DB.BUH_F_ARTIKEL__ENHET, "ENHET", "", true, true, false);
        enhet.enableFixedValuesAdvanced();
        enhet.setUneditable();
        //
        //
        String pris_ = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS);
        RowDataInvert pris = new RowDataInvertB(pris_, DB.BUH_F_ARTIKEL__PRIS, "PRIS", "", false, true, true);
        //
        String rabatt_ = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT);
        RowDataInvert rabatt = new RowDataInvertB(rabatt_, DB.BUH_F_ARTIKEL__RABATT, "RABATT %", "", false, true, false);
        //
        RowDataInvert[] rows = {
            kund,
            komment,
            antal,
            enhet,
            pris,
            rabatt
        };
        //
        return rows;
    }
    
    protected void hideMomsSatsIfExklMoms() {
        //
        System.out.println("INKL_MOMS----------------------");
        //
        boolean momsInk = getInklMoms();
        //
        TableInvert table = (TableInvert) TABLE_INVERT_3;
        TableRowInvert tri = (TableRowInvert) table.getRowByColName(DB.BUH_FAKTURA__MOMS_SATS);
        RowDataInvert rdi = tri.getRowConfig();
        //
        if (momsInk == false) {
            //
            // Both ways below working (regarding: "setVisible()")
//                tri.setVisible(false);
            rdi.setVisible_(false);
            //
            refreshTableInvert(TABLE_INVERT_3);
            //
        } else {
            //
            // Both ways below working
//                tri.setVisible(true);
            rdi.setVisible_(true);
            //
            refreshTableInvert(TABLE_INVERT_3);
            //
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent me, int column, int row, String tableName, TableInvert ti) {
        //
        super.mouseClicked(me, column, row, tableName, ti); //To change body of generated methods, choose Tools | Templates.
        //
        String col_name = ti.getCurrentColumnName(me.getSource());
        //
        if (col_name.equals(DB.BUH_FAKTURA__ER_REFERENS)) {
            //
            JLinkInvert jli = (JLinkInvert) me.getSource();
            //
            if (jli.getValue().isEmpty()) {
                String er_referens_last = HelpA.loadLastEntered(IO.getErReferens(getFakturaKundId()));
                setValueTableInvert(DB.BUH_FAKTURA__ER_REFERENS, TABLE_INVERT, er_referens_last);
            }
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__FAKTURA_DATUM)) {
            //
            restoreFakturaDatumIfEmty(me, ti);
            //
        }
        //
    }
    
    private void restoreFakturaDatumIfEmty(MouseEvent me, TableInvert ti) {
        //
        JLinkInvert jli = (JLinkInvert) me.getSource();
        JTextFieldInvert jtfi = (JTextFieldInvert) jli;
        //
        if (jli.getValue().isEmpty()) {
            //
            jtfi.setText(HelpA.get_proper_date_yyyy_MM_dd());
            //
            if (Validator.validateDate(jli)) {
                forfalloDatumAutoChange(ti);
            }
            //
        }
    }
    
    @Override
    public void initializeSaveIndicators() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean getUnsaved(int nr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void mouseWheelForward(TableInvert ti, MouseWheelEvent e) {
        //
        super.mouseWheelForward(ti, e); //To change body of generated methods, choose Tools | Templates.
        //
        String col_name = ti.getCurrentColumnName(e.getSource());
        //
        if (col_name == null) {
            return;
        }
        //
        if (col_name.equals(DB.BUH_FAKTURA__FAKTURA_DATUM)) {
            //
            double wheelRotation = e.getPreciseWheelRotation();
            //
            double scroll = wheelRotation;
            double scroll_rounded = Math.round(scroll);
            long value = (long) scroll_rounded;
            //
            String date = getValueTableInvert(DB.BUH_FAKTURA__FAKTURA_DATUM);
            //
            String date_new;
            //
            if (wheelRotation > 0) {
                date_new = HelpA.get_date_time_plus_some_time_in_days(date, value);
            } else {
                date_new = HelpA.get_date_time_minus_some_time_in_days(date, value);
            }
            //
            setValueTableInvert(DB.BUH_FAKTURA__FAKTURA_DATUM, TABLE_INVERT, date_new);
            //
            forfalloDatumAutoChange(ti);
            //
        }
        //
    }

    /**
     * [2020-07-XX] SUPER important here you catch the event when key released
     * on some component so you can process this event as required
     *
     * @param ti
     * @param ke
     */
    @Override
    public void keyReleasedForward(TableInvert ti, KeyEvent ke) {
        //
        super.keyReleasedForward(ti, ke); //To change body of generated methods, choose Tools | Templates.
        //
        JLinkInvert jli = (JLinkInvert) ke.getSource();
        //
        String col_name = ti.getCurrentColumnName(ke.getSource());
        //
        if (col_name.equals(DB.BUH_F_ARTIKEL__ANTAL)
                || col_name.equals(DB.BUH_F_ARTIKEL__PRIS)
                || col_name.equals(DB.BUH_F_ARTIKEL__RABATT)
                //
                || col_name.equals(DB.BUH_FAKTURA__EXP_AVG)
                || col_name.equals(DB.BUH_FAKTURA__FRAKT)) {
            //
            Validator.validateDigitalInput(jli);
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__VAR_REFERENS)) {
            //
            referensSave(DB.BUH_FAKTURA__VAR_REFERENS);
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__ER_REFERENS)) {
            //
            referensSave(DB.BUH_FAKTURA__ER_REFERENS);
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__FAKTURA_DATUM)) {
            //
            if (Validator.validateDate(jli)) {
                forfalloDatumAutoChange(ti);
            }
            //
        }
    }
    
    private void referensSave(String colName) {
        //
        String er_referens = getValueTableInvert(colName);
        //
        if (colName.equals(DB.BUH_FAKTURA__VAR_REFERENS)) {
            try {
                HelpA.writeToFile(IO.VAR_REFERENS, er_referens);
            } catch (IOException ex) {
                Logger.getLogger(InvoiceA_Insert.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
        } else if (colName.equals(DB.BUH_FAKTURA__ER_REFERENS)) {
            try {
                HelpA.writeToFile(IO.getErReferens(getFakturaKundId()), er_referens);
            } catch (IOException ex) {
                Logger.getLogger(InvoiceA_Insert.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //
    }
    
    private void forfalloDatumAutoChange(TableInvert ti) {
        //
        String val = getValueTableInvert(DB.BUH_FAKTURA__BETAL_VILKOR);
        //
        if (val.equals("NULL")) {
            return;
        }
        //
        long value = Long.parseLong(val);
        String date = getValueTableInvert(DB.BUH_FAKTURA__FAKTURA_DATUM, ti);
        
        String date_new = HelpA.get_date_time_plus_some_time_in_days(date, value);
        setValueTableInvert(DB.BUH_FAKTURA__FORFALLO_DATUM, ti, date_new);
    }

    /**
     * [2020-07-XX] SUPER important here you catch the event when some jcombobox
     * item is changed so you can process this event as required
     *
     * @param ti
     * @param ie
     */
    @Override
    public void jComboBoxItemStateChangedForward(TableInvert ti, ItemEvent ie) {
        //
        super.jComboBoxItemStateChangedForward(ti, ie);
        //
        String col_name = ti.getCurrentColumnName(ie.getSource());
        //
        if (col_name.equals(DB.BUH_FAKTURA__BETAL_VILKOR)) {
            //
            forfalloDatumAutoChange(ti);
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__INKL_MOMS)) {
            //
            hideMomsSatsIfExklMoms();
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__MAKULERAD)) {
            //
            System.out.println("FAKTURA MAKULERAD");
            //
        }
        //
    }
    
}
