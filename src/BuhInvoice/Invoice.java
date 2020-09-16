/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.LANG;
import BuhInvoice.sec.IO;
import MyObjectTable.OutPut;
import MyObjectTable.Table;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert_;
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
import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public abstract class Invoice extends Basic_Buh_ {

    //
    protected Table TABLE_INVERT_2;
    protected Table TABLE_INVERT_3;
    protected Faktura_Entry faktura_entry;
    //
    private static double FAKTURA_TOTAL_EXKL_MOMS = 0;
    private static double FAKTURA_TOTAL = 0;
    private static double MOMS_TOTAL = 0;
    //
    public static boolean CURRENT_OPERATION_INSERT = false;
    //
    
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
            enableDisableButtons(bim.jPanel12, true);
            bim.jButton_confirm_insert_update.setEnabled(true);
            //
            bim.jButton_update_articles_row.setEnabled(false);
            //
        } else { // UPDATE
            //
            enableDisableButtons(bim.jPanel11, false); // Hide/Show Edit and Submit btns for editing of article when "INSERT"
            //
            if (fakturaBetald()) {
                //
                bim.jLabel_Faktura_Insert_or_Update.setText(LANG.LBL_MSG_2_2);
                //
                enableDisableButtons(bim.jPanel12, false);
                bim.jButton_confirm_insert_update.setEnabled(false);
                //
                bim.jButton_update_articles_row.setEnabled(false);
                //
            } else {
                //
                if(bim.isMakulerad()){
//                    bim.jScrollPane1_faktura.setBorder(BorderFactory.createLineBorder(Color.red, 4));
                    HelpA.showNotification_separate_thread(LANG.MSG_9);
                }
                //
                if(bim.isKreditFaktura() == false){
                  //
                  bim.jLabel_Faktura_Insert_or_Update.setText(LANG.LBL_MSG_2);
                  enableDisableButtons(bim.jPanel12, true);
                  //
                }else{
                  // OBS! KREDIT FAKTURA [2020-09-15]  
                  String krediteradFakturaNr = bim.getKomment_$();
                  bim.jLabel_Faktura_Insert_or_Update.setText(LANG.LBL_MSG_2_3(krediteradFakturaNr));
                  bim.jButton_add_article.setEnabled(false); // Shall not be possible to add articles
                  //
                }
                //
                //
                bim.jButton_confirm_insert_update.setEnabled(true);
                //
                bim.jButton_update_articles_row.setEnabled(true);
                //
            }
            //
        }
    }

    private boolean fakturaBetald() {
        //
        String betald = HelpA.getValueSelectedRow(getAllInvoicesTable(), InvoiceB.TABLE_ALL_INVOICES__BETALD);
        //
        return !betald.equals(DB.STATIC__NO) && !betald.equals(DB.STATIC_BET_STATUS_KREDIT);
        //
    }
    
    protected boolean articlesJTableEmpty(){
        if(getArticlesTable().getModel().getRowCount() > 0){
            return false;
        }else{
            return true;
        }
    }
    
    protected boolean articlesJTableRowSelected(){
        int selectedRow = getArticlesTable().getSelectedRow();
        return selectedRow != -1;
    }
    
    protected void deselectRowArticlesTable(){
        getArticlesTable().clearSelection();
    }
    
    protected void disableMomsJComboIf(RowDataInvert rdi){
        if(articlesJTableEmpty() == false){
            rdi.setDisabled();
        }
    }

    private void enableDisableButtons(JPanel parent, boolean enabled) {
        Component[] components = parent.getComponents();
        for (Component c : components) {
            if (c instanceof JButton) {
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
        //
        // This statement is needed to redraw the tableInvert because if some
        // element was disabled it muste be redrawn. [2020-09-07] i implemented that
        // after adding one article the user is not able to change "moms" anymore
        showTableInvert_3();
        //
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
        if (containsInvalidatedFields(TABLE_INVERT_3, DB.START_COLUMN, getConfigTableInvert_3())) {
            HelpA.showNotification(LANG.MSG_1);
            return false;
        }
        //
        return true;
        //
    }

    protected boolean fieldsValidatedArticle() {
        //
        if (containsInvalidatedFields(TABLE_INVERT_2, DB.START_COLUMN, getConfigTableInvert_2())) {
            HelpA.showNotification(LANG.MSG_1);
            return false;
        }
        //
        return true;
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

    protected static String getNextFakturaNr(String kundId) {
        //
        HashMap<String, String> map = new HashMap<>();
        map.put(DB.BUH_FAKTURA__KUNDID__, kundId);
        String json = JSon.hashMapToJSON(map);
        //
        try {
            //
            String fakturaNr = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_GET_LATEST_FAKTURA_NR, json);
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

    public double getRabattPercent_JTable(JTable table, int row) {
        //
        String rabatt = HelpA.getValueGivenRow(table, row, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT);
        //
        try {
            double rabatt_ = Double.parseDouble(rabatt);
            if (rabatt_ > 1) {
                return rabatt_ / 100;
            } else {
                return rabatt_;
            }
        } catch (Exception ex) {
            return 0;
        }
    }

    public double getRabattKr_JTable(JTable table, int row) {
        //
        String rabatt = HelpA.getValueGivenRow(table, row, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT_KR);
        //
        if (HelpA.isNumber(rabatt)) {
            return Double.parseDouble(rabatt);
        } else {
            return 0;
        }
        //
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
        if (TABLE_INVERT_2 == null) {
            return;
        }
        //
        FAKTURA_TOTAL = 0;
        FAKTURA_TOTAL_EXKL_MOMS = 0;
        MOMS_TOTAL = 0;
        //
        double pris_exkl_moms;
        int antal;
        //
        for (int i = 0; i < table.getModel().getRowCount(); i++) {
            //
            double rabatt_percent = getRabattPercent_JTable(table, i);
            double rabatt_kr = getRabattKr_JTable(table, i);
            //
            pris_exkl_moms = Double.parseDouble(HelpA.getValueGivenRow(table, i, prisColumn));
            antal = Integer.parseInt(HelpA.getValueGivenRow(table, i, antalColumn));
            //
            //
            if (rabatt_percent == 0 && rabatt_kr == 0) {
                FAKTURA_TOTAL += (pris_exkl_moms * antal);
            } else if (rabatt_kr != 0) {
                FAKTURA_TOTAL += (pris_exkl_moms - rabatt_kr) * antal;
            }
            //
        }
        //
        if (getInklMoms()) {
            MOMS_TOTAL = FAKTURA_TOTAL * getMomsSats();
            FAKTURA_TOTAL += MOMS_TOTAL;
            FAKTURA_TOTAL_EXKL_MOMS = FAKTURA_TOTAL - MOMS_TOTAL;
        } else {
            FAKTURA_TOTAL_EXKL_MOMS = FAKTURA_TOTAL;
        }
        //
        BUH_INVOICE_MAIN.jTextField_total_inkl_moms.setText("" + getFakturaTotal());
        BUH_INVOICE_MAIN.jTextField_total_exkl_moms.setText("" + getTotalExklMoms());
        BUH_INVOICE_MAIN.jTextField_moms.setText("" + getMomsTotal());
        //
    }

    protected double getFakturaTotal() {
        return GP_BUH.round_double(FAKTURA_TOTAL);
    }

    protected double getMomsTotal() {
        return GP_BUH.round_double(MOMS_TOTAL);
    }

    protected double getTotalExklMoms() {
        return GP_BUH.round_double(FAKTURA_TOTAL_EXKL_MOMS);
    }

    public abstract RowDataInvert[] getConfigTableInvert_2();

    public abstract RowDataInvert[] getConfigTableInvert_3();

    @Override
    public void showTableInvert() {
        TableBuilderInvert_ tableBuilder = new TableBuilderInvert_(new OutPut(), null, getConfigTableInvert(), false, "buh_faktura_a");
        TABLE_INVERT = null;
        TABLE_INVERT = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT, 5, 0, 5, 0);
        showTableInvert(bim.jPanel2_faktura_main);
        //
        addTableInvertRowListener(TABLE_INVERT, this);
        //
    }

    public void showTableInvert_2() {
        TableBuilderInvert_ tableBuilder = new TableBuilderInvert_(new OutPut(), null, getConfigTableInvert_2(), false, "buh_f_artikel");
        TABLE_INVERT_2 = null;
        TABLE_INVERT_2 = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT_2, 5, 0, 5, 0);
        showTableInvert(bim.jPanel_articles, TABLE_INVERT_2);
        //
        addTableInvertRowListener(TABLE_INVERT_2, this);
        //
        setArticlePrise(false); // [2020-08-19]
        //
    }

    public void showTableInvert_3() {
        TableBuilderInvert_ tableBuilder = new TableBuilderInvert_(new OutPut(), null, getConfigTableInvert_3(), false, "buh_faktura_b");
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

    public RowDataInvert[] getConfigTableInvert_insert() {
         // String fixedComboValues_a = "Skruv;1,Spik;2,Hammare;3,Traktor;4,Skruvmejsel;5"; // This will aquired from SQL
            String fixedComboValues_a = requestJComboValuesHttp(DB.PHP_FUNC_PARAM_GET_KUND_ARTICLES, new String[]{DB.BUH_FAKTURA_ARTIKEL___NAMN, DB.BUH_FAKTURA_ARTIKEL___ID,DB.BUH_FAKTURA_ARTIKEL___PRIS});
            RowDataInvert articles = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_a, DB.BUH_F_ARTIKEL__ARTIKELID, InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN, "", true, true, true);
            articles.enableFixedValuesAdvanced();
            articles.setUneditable();
            //
            RowDataInvert komment = new RowDataInvertB("", DB.BUH_F_ARTIKEL__KOMMENT, InvoiceB.TABLE_INVOICE_ARTIKLES__KOMMENT, "", true, true, false);
            //
            RowDataInvert antal = new RowDataInvertB("1", DB.BUH_F_ARTIKEL__ANTAL, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL, "", true, true, false);
            //
            String fixedComboValues_b = DB.STATIC__ENHET;
            RowDataInvert enhet = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_b, DB.BUH_F_ARTIKEL__ENHET, InvoiceB.TABLE_INVOICE_ARTIKLES__ENHET, "", true, true, false);
            enhet.enableFixedValuesAdvanced();
            enhet.setUneditable();
            //
            RowDataInvert pris = new RowDataInvertB("", DB.BUH_F_ARTIKEL__PRIS, InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS, "", true, true, true);
            //
            RowDataInvert rabatt = new RowDataInvertB("0", DB.BUH_F_ARTIKEL__RABATT, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT, "", true, true, false);
            //
            RowDataInvert rabatt_kr = new RowDataInvertB("0", DB.BUH_F_ARTIKEL__RABATT_KR, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT_KR, "", true, true, false);
//            rabatt_kr.setDontAquireTableInvertToHashMap();
            //
            RowDataInvert[] rows = {
                articles,
                komment,
                antal,
                enhet,
                pris,
                rabatt,
                rabatt_kr
            };
            //
            return rows;
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
        String fixedComboValues_a = JSon._get__with_merge(
                HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN),
                HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_ID),
                requestJComboValuesHttp(DB.PHP_FUNC_PARAM_GET_KUND_ARTICLES, new String[]{DB.BUH_FAKTURA_ARTIKEL___NAMN, DB.BUH_FAKTURA_ARTIKEL___ID,DB.BUH_FAKTURA_ARTIKEL___PRIS}));
//        String fixedComboValues_a = "Skruv;1,Spik;2,Hammare;3,Traktor;4,Skruvmejsel;5"; // This will aquired from SQL
        RowDataInvert articles = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_a, DB.BUH_F_ARTIKEL__ARTIKELID, InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN, "", true, true, true);
        articles.enableFixedValuesAdvanced();
        articles.setUneditable();
        //
        //
        String komm = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__KOMMENT);
        RowDataInvert komment = new RowDataInvertB(komm, DB.BUH_F_ARTIKEL__KOMMENT, InvoiceB.TABLE_INVOICE_ARTIKLES__KOMMENT, "", true, true, false);
        //
        String ant = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL);
        RowDataInvert antal = new RowDataInvertB(ant, DB.BUH_F_ARTIKEL__ANTAL, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL, "", false, true, false);
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
        RowDataInvert enhet = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_b, DB.BUH_F_ARTIKEL__ENHET, InvoiceB.TABLE_INVOICE_ARTIKLES__ENHET, "", true, true, false);
        enhet.enableFixedValuesAdvanced();
        enhet.setUneditable();
        //
        //
        String pris_ = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS);
        RowDataInvert pris = new RowDataInvertB(pris_, DB.BUH_F_ARTIKEL__PRIS, "PRIS", "", false, true, true);
        //
        String rabatt_ = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT);
        RowDataInvert rabatt = new RowDataInvertB(rabatt_, DB.BUH_F_ARTIKEL__RABATT, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT, "", false, true, false);
        //
        String rabatt_kr_ = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT_KR);
        RowDataInvert rabatt_kr = new RowDataInvertB(rabatt_kr_, DB.BUH_F_ARTIKEL__RABATT_KR, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT_KR, "", false, true, false);
        //
        RowDataInvert[] rows = {
            articles,
            komment,
            antal,
            enhet,
            pris,
            rabatt,
            rabatt_kr
        };
        //
        return rows;
    }

    protected void hideMomsSatsIfExklMoms() {
        //
//        System.out.println("INKL_MOMS----------------------");
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
        if(e.getSource() instanceof JLinkInvert == false){
            return;
        }
        //
        JLinkInvert jli = (JLinkInvert) e.getSource();
        //
        String col_name = ti.getCurrentColumnName(e.getSource());
        //
        if (col_name == null) {
            return;
        }
        //
        if (col_name.equals(DB.BUH_FAKTURA__FAKTURA_DATUM)) {
            //
            long value = getValueWheelRotation(e);
            //
//            System.out.println("VALUE: " + value);
            //
            String date = getValueTableInvert(DB.BUH_FAKTURA__FAKTURA_DATUM);
            //
            String date_new;
            //
            if (value > 0) {
                date_new = HelpA.get_date_time_plus_some_time_in_days(date, value);
            } else {
                date_new = HelpA.get_date_time_minus_some_time_in_days(date, value);
            }
            //
            setValueTableInvert(DB.BUH_FAKTURA__FAKTURA_DATUM, TABLE_INVERT, date_new);
            //
            forfalloDatumAutoChange(ti);
            //
            Validator.validateDate(jli);
            //
        } else if (col_name.equals(DB.BUH_F_ARTIKEL__ANTAL)
                || col_name.equals(DB.BUH_F_ARTIKEL__PRIS)
                //                || col_name.equals(DB.BUH_F_ARTIKEL__RABATT)
                //                || col_name.equals(DB.BUH_F_ARTIKEL__RABATT_KR)
                || col_name.equals(DB.BUH_FAKTURA__EXP_AVG)
                || col_name.equals(DB.BUH_FAKTURA__FRAKT)) {
            //
            mouseWheelNumberChange(e);
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
                || col_name.equals(DB.BUH_F_ARTIKEL__RABATT_KR)
                //
                || col_name.equals(DB.BUH_FAKTURA__EXP_AVG)
                || col_name.equals(DB.BUH_FAKTURA__FRAKT)) {
            //
            boolean digitalInputValidated = Validator.validateDigitalInput(jli);
            //
            if (col_name.equals(DB.BUH_F_ARTIKEL__RABATT)) {
                //
                if (Validator.validatePercentInput(jli) == false || digitalInputValidated == false) {
                    return;
                }
                //
                double rabatt_percent = Double.parseDouble(jli.getValue());
                double pris = Double.parseDouble(getValueTableInvert(DB.BUH_F_ARTIKEL__PRIS, DB.START_COLUMN, TABLE_INVERT_2));
                double rabatt_kr = pris * (rabatt_percent / 100);
                //
                setValueTableInvert(DB.BUH_F_ARTIKEL__RABATT_KR, ti, GP_BUH.round_double(rabatt_kr));
                JLinkInvert linkInvert = (JLinkInvert) getObjectTableInvert(DB.BUH_F_ARTIKEL__RABATT_KR, TABLE_INVERT_2);
                Validator.validateDigitalInput(linkInvert); // Validating after setting the value
                //
            } else if (col_name.equals(DB.BUH_F_ARTIKEL__RABATT_KR)) {
                //
                if (digitalInputValidated == false) {
                    return;
                }
                //
                double rabatt_kr = Double.parseDouble(jli.getValue());
                double pris = Double.parseDouble(getValueTableInvert(DB.BUH_F_ARTIKEL__PRIS, DB.START_COLUMN, TABLE_INVERT_2));
                double rabatt_percent = (rabatt_kr / pris) * 100;
                rabatt_percent = GP_BUH.round_double(rabatt_percent);
                //
                setValueTableInvert(DB.BUH_F_ARTIKEL__RABATT, ti, "" + rabatt_percent);
                JLinkInvert linkInvert = (JLinkInvert) getObjectTableInvert(DB.BUH_F_ARTIKEL__RABATT, TABLE_INVERT_2);
                Validator.validatePercentInput(linkInvert); // Validating after setting the value
                //
            }
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__VAR_REFERENS)) {
            //
            referensSave(DB.BUH_FAKTURA__VAR_REFERENS);
            //
            Validator.validateMaxInputLength(jli, 100);
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__ER_REFERENS)) {
            //
            referensSave(DB.BUH_FAKTURA__ER_REFERENS);
            //
            Validator.validateMaxInputLength(jli, 100);
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__FAKTURA_DATUM)) {
            //
            if (Validator.validateDate(jli)) {
                forfalloDatumAutoChange(ti);
            }
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__ERT_ORDER)) {
            //
            Validator.validateMaxInputLength(jli, 40); // OBS! 200 is taken from DB "buh_faktura" varchar(40)
            //
        } else if (col_name.equals(DB.BUH_F_ARTIKEL__KOMMENT)) {
            //
            Validator.validateMaxInputLength(jli, 200);
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
        JLinkInvert jli = (JLinkInvert) ie.getSource();
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
        } else if (col_name.equals(DB.BUH_FAKTURA_ARTIKEL___ID)) {
            //
            setArticlePrise(true);
            //
        }
        //
    }

    private void setArticlePrise(boolean force) {
        //
        boolean conditionSpecial = CURRENT_OPERATION_INSERT == false && articlesJTableEmpty() == true;
        //
        if (CURRENT_OPERATION_INSERT || conditionSpecial || force) {
            //
            JComboBox box = (JComboBox) getObjectTableInvert(DB.BUH_FAKTURA_ARTIKEL___ID, TABLE_INVERT_2);
            HelpA.ComboBoxObject cbo = (HelpA.ComboBoxObject) box.getSelectedItem();
            String pris = cbo.getParam_3();
            setValueTableInvert(DB.BUH_FAKTURA_ARTIKEL___PRIS, TABLE_INVERT_2, pris);
            //
            if(force && CURRENT_OPERATION_INSERT == false){
                setValueTableInvert(DB.BUH_FAKTURA_ARTIKEL___KOMMENT, TABLE_INVERT_2, "");
                setValueTableInvert(DB.BUH_F_ARTIKEL__ANTAL, TABLE_INVERT_2, "1");
                setValueTableInvert(DB.BUH_F_ARTIKEL__RABATT, TABLE_INVERT_2, "0");
                setValueTableInvert(DB.BUH_F_ARTIKEL__RABATT_KR, TABLE_INVERT_2, "0");
            }
            //
        }
        //
    }

}
