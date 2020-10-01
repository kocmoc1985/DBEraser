/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.LANG;
import BuhInvoice.sec.IO;
import BuhInvoice.sec.Moms;
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
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public abstract class Invoice_ extends Basic_Buh {

    //
    protected Table TABLE_INVERT_2;
    protected Table TABLE_INVERT_3;
    protected Faktura_Entry faktura_entry;
    //
    private static double FAKTURA_TOTAL_EXKL_MOMS = 0;
    private static double FAKTURA_TOTAL = 0;
    private static double RABATT_TOTAL = 0;
    private static double MOMS_TOTAL = 0;
    //
    public static boolean CURRENT_OPERATION_INSERT = false;
    //
    protected final Moms momsSaveEntry = new Moms();
    //
    protected static boolean CREATE_KONTANT_FAKTURA__OPERATION_INSERT = false;
    //

    public Invoice_(BUH_INVOICE_MAIN bim) {
        super(bim);
        initFakturaEntry_();
    }

    private void buttonLogic() {
        //
        GP_BUH.enableDisableButtons(bim.jPanel9, true);
        GP_BUH.enableDisableButtons(bim.jPanel11, true);
        GP_BUH.enableDisableButtons(bim.jPanel12, true);
        //
        if (articlesJTableEmpty()) {
            GP_BUH.setEnabled(bim.jButton_update_articles_row, false);
            GP_BUH.setEnabled(bim.jButton_delete_articles_row, false);
            GP_BUH.enableDisableButtons(bim.jPanel11, false);
        }
        //
    }

    protected void SET_CURRENT_OPERATION_INSERT(boolean insert) {
        //
        CURRENT_OPERATION_INSERT = insert;
        //
        bim.FAKTURA_TYPE_CURRENT__OPERATION = bim.getFakturaType();
        //
        buttonLogic();
        //
        if (insert) {
            //
            if (CREATE_KONTANT_FAKTURA__OPERATION_INSERT == true) {
                // KONTANT FAKTURA
                bim.jLabel_Faktura_Insert_or_Update.setText(LANG.LBL_MSG_1_2);
                bim.FAKTURA_TYPE_CURRENT__OPERATION = DB.STATIC__FAKTURA_TYPE_KONTANT;
                //
            } else {
                // NORMAL FAKTURA
                bim.jLabel_Faktura_Insert_or_Update.setText(LANG.LBL_MSG_1);
                bim.FAKTURA_TYPE_CURRENT__OPERATION = DB.STATIC__FAKTURA_TYPE_NORMAL;
                //
            }
            //
            GP_BUH.setEnabled(bim.jButton_update_articles_row, false);
            //
        } else { // UPDATE
            //
            GP_BUH.enableDisableButtons(bim.jPanel11, false); // Hide/Show Edit and Submit btns for editing of article when "INSERT"
            //
            if (fakturaBetald()) {
                //
                bim.jLabel_Faktura_Insert_or_Update.setText(LANG.LBL_MSG_2_2);
                //
                GP_BUH.enableDisableButtons(bim.jPanel12, false);
                GP_BUH.setEnabled(bim.jButton_confirm_insert_update, false);
                //
            } else {
                //
                if (bim.isMakulerad()) {
                    HelpA.showNotification_separate_thread(LANG.MSG_9);
                }
                //
                if (bim.isKreditFaktura()) {
                    // OBS! KREDIT FAKTURA [2020-09-15]  
                    String krediteradFakturaNr = bim.getKomment_$();
                    bim.jLabel_Faktura_Insert_or_Update.setText(LANG.LBL_MSG_2_3(krediteradFakturaNr));
                    GP_BUH.setEnabled(bim.jButton_add_article, false);// Shall not be possible to add articles
                    bim.FAKTURA_TYPE_CURRENT__OPERATION = DB.STATIC__FAKTURA_TYPE_KREDIT;
                    //
                } else if (bim.isKontantFaktura()) { // KONTANT FAKTURA
                    bim.jLabel_Faktura_Insert_or_Update.setText(LANG.LBL_MSG_2_1);
                    bim.FAKTURA_TYPE_CURRENT__OPERATION = DB.STATIC__FAKTURA_TYPE_KONTANT;
                } else {
                    // NORMAL FAKTURA
                    bim.jLabel_Faktura_Insert_or_Update.setText(LANG.LBL_MSG_2);
                    bim.FAKTURA_TYPE_CURRENT__OPERATION = DB.STATIC__FAKTURA_TYPE_NORMAL;
                }
                //
                //
            }
            //
        }
    }

    protected void resetSavedMoms_jCombo() {
        momsSaveEntry.reset();
    }

    private boolean fakturaBetald() {
        //
        return bim.fakturaBetald();
        //
    }

    protected boolean articlesJTableEmpty() {
        if (getArticlesTable().getRowCount() > 0) {
            return false;
        } else {
            return true;
        }
    }

    protected boolean articlesJTableRowSelected() {
        int selectedRow = getArticlesTable().getSelectedRow();
        return selectedRow != -1;
    }

    protected void deselectRowArticlesTable() {
        getArticlesTable().clearSelection();
    }

    protected void disableMomsJComboIf(RowDataInvert rdi) {
        if (articlesJTableEmpty() == false) {
            rdi.setDisabled();
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
        // Yes it's 100% correct that TABLE_INVERT_2 is not validated [2020-10-01]
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
        return getValueTableInvert(DB.BUH_FAKTURA_KUND__ID);
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

    private double getDoubleTableInvert(TableInvert ti, String rowName) {
        //
//        if(ti == null){
//            return 0;
//        }
        //
        String value = getValueTableInvert(rowName, ti);
        //
        if (value == null || value.isEmpty()) {
            return 0;
        } else {
            return Double.parseDouble(value);
        }
    }

    /**
     * This one is only for articles table
     *
     * @param table
     * @param row
     * @param parameter
     * @return
     */
    private double getDoubleJTable(JTable table, int row, String parameter) {
        //
        String rabatt = HelpA.getValueGivenRow(table, row, parameter);
        //
        if (HelpA.isNumber(rabatt)) {
            return Double.parseDouble(rabatt);
        } else {
            return 0;
        }
        //
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

    public boolean getInklMoms() {
        return Integer.parseInt(getValueTableInvert(DB.BUH_FAKTURA__INKL_MOMS, TABLE_INVERT_3)) == 1;
    }

    public double getMomsSats() {
//        try {
        return Double.parseDouble(getValueTableInvert(DB.BUH_FAKTURA__MOMS_SATS, TABLE_INVERT_3));
//        } catch (Exception ex) {
//            return 0.25;
//        }
    }

    public boolean getMakulerad() {
        return Integer.parseInt(getValueTableInvert(DB.BUH_FAKTURA__MAKULERAD, TABLE_INVERT_3)) == 1;
    }

    protected void resetFakturaTotal() {
        //
        FAKTURA_TOTAL = 0;
        RABATT_TOTAL = 0;
        FAKTURA_TOTAL_EXKL_MOMS = 0;
        MOMS_TOTAL = 0;
        //
        BUH_INVOICE_MAIN.jTextField_total_inkl_moms.setText("" + getFakturaTotal());
        BUH_INVOICE_MAIN.jTextField_total_exkl_moms.setText("" + getTotalExklMoms());
        BUH_INVOICE_MAIN.jTextField_moms.setText("" + getMomsTotal());
        BUH_INVOICE_MAIN.jTextField_rabatt_total.setText("" + getRabattTotal());
    }

    protected void countFakturaTotal(JTable table) {
        //
        // Some methods are called from here because this method (countFakturaTotal)
        // is executed uppon almost all actions [2020-09-30]
        bim.displayArticlesCount();
        //
        SET_CURRENT_OPERATION_INSERT(CURRENT_OPERATION_INSERT); // For buttons enabled/disabled logics
        //
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
            double rabatt_kr = getDoubleJTable(table, i, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT_KR);
            //
            pris_exkl_moms = Double.parseDouble(HelpA.getValueGivenRow(table, i, prisColumn));
            antal = Integer.parseInt(HelpA.getValueGivenRow(table, i, antalColumn));
            //
            //
            if (rabatt_percent == 0 && rabatt_kr == 0) {
                FAKTURA_TOTAL += (pris_exkl_moms * antal);
            } else if (rabatt_kr != 0) {
                FAKTURA_TOTAL += (pris_exkl_moms - rabatt_kr) * antal;
                RABATT_TOTAL += (rabatt_kr * antal);
            }
            //
        }
        //
        double momsSats = getMomsSats();
        double frakt = getDoubleTableInvert((TableInvert) TABLE_INVERT_3, DB.BUH_FAKTURA__FRAKT);
        double exp_avg = getDoubleTableInvert((TableInvert) TABLE_INVERT_3, DB.BUH_FAKTURA__EXP_AVG);
        //
//        if (getInklMoms()) {
        MOMS_TOTAL = FAKTURA_TOTAL * momsSats + countMomsFraktAndExpAvg(frakt, exp_avg, momsSats);
        FAKTURA_TOTAL += MOMS_TOTAL;
        FAKTURA_TOTAL_EXKL_MOMS = FAKTURA_TOTAL - MOMS_TOTAL;
//        } else {
//            FAKTURA_TOTAL_EXKL_MOMS = FAKTURA_TOTAL;
//        }
        //
        FAKTURA_TOTAL += frakt;
        FAKTURA_TOTAL += exp_avg;
        //
        BUH_INVOICE_MAIN.jTextField_total_inkl_moms.setText("" + getFakturaTotal());
        BUH_INVOICE_MAIN.jTextField_total_exkl_moms.setText("" + getTotalExklMoms());
        BUH_INVOICE_MAIN.jTextField_moms.setText("" + getMomsTotal());
        BUH_INVOICE_MAIN.jTextField_rabatt_total.setText("" + getRabattTotal());
        //
    }

    private double countMomsFraktAndExpAvg(double frakt, double expAvg, double momsSats) {
        return (frakt + expAvg) * momsSats;
    }

    protected double getRabattTotal() {
        return GP_BUH.round_double(RABATT_TOTAL);
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
        //
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
        String fixedComboValues_a = requestJComboValuesHttp(DB.PHP_FUNC_PARAM_GET_KUND_ARTICLES, new String[]{DB.BUH_FAKTURA_ARTIKEL___NAMN, DB.BUH_FAKTURA_ARTIKEL___ID, DB.BUH_FAKTURA_ARTIKEL___PRIS});
        RowDataInvert articles = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_a, DB.BUH_F_ARTIKEL__ARTIKELID, InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN, "", true, true, true);
        articles.enableFixedValuesAdvanced();
//        articles.setUneditable();
        articles.enableEmptyValue(); //[2020-09-28] -> this makes that is't shown like "-" in the artcles jcombo for the empty entry
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
        RowDataInvert pris = new RowDataInvertB("0", DB.BUH_F_ARTIKEL__PRIS, InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS, "", true, true, true);
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
                requestJComboValuesHttp(DB.PHP_FUNC_PARAM_GET_KUND_ARTICLES, new String[]{DB.BUH_FAKTURA_ARTIKEL___NAMN, DB.BUH_FAKTURA_ARTIKEL___ID, DB.BUH_FAKTURA_ARTIKEL___PRIS}));
//        String fixedComboValues_a = "Skruv;1,Spik;2,Hammare;3,Traktor;4,Skruvmejsel;5"; // This will aquired from SQL
        RowDataInvert articles = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_a, DB.BUH_F_ARTIKEL__ARTIKELID, InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN, "", true, true, true);
        articles.enableFixedValuesAdvanced();
//        articles.setUneditable();
//        articles.setDisabled();
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
                String er_referens_last = HelpA.loadLastEntered(IO.getErReferens(getFakturaKundId()), "");
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
        if (e.getSource() instanceof JLinkInvert == false) {
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
                || col_name.equals(DB.BUH_FAKTURA__FRAKT)
                || col_name.equals(DB.BUH_FAKTURA__DROJSMALSRANTA)) {
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
                || col_name.equals(DB.BUH_FAKTURA__FRAKT)
                || col_name.equals(DB.BUH_FAKTURA__DROJSMALSRANTA)) {
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
            } else if (col_name.equals(DB.BUH_FAKTURA__DROJSMALSRANTA)
                    || col_name.equals(DB.BUH_FAKTURA__FRAKT)
                    || col_name.equals(DB.BUH_FAKTURA__EXP_AVG)) {
                //
                if (digitalInputValidated == false) {
                    return;
                }
                //
                referensSave(col_name);
                //
                if (col_name.equals(DB.BUH_FAKTURA__FRAKT) || col_name.equals(DB.BUH_FAKTURA__EXP_AVG)) {
                    countFakturaTotal(getArticlesTable());
                }
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
            Validator.validateMaxInputLength(jli, 150);
            //
        } else if (col_name.equals(DB.BUH_F_ARTIKEL__KOMMENT)) {
            //
            Validator.validateMaxInputLength(jli, 200);
            //
        }
    }

    private void referensSave(String colName) {
        //
        //
        if (colName.equals(DB.BUH_FAKTURA__VAR_REFERENS)) {
            //
            String value = getValueTableInvert(colName);
            //
            try {
                HelpA.writeToFile(DB.BUH_FAKTURA__VAR_REFERENS, value);
            } catch (IOException ex) {
                Logger.getLogger(InvoiceA_Insert.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
        } else if (colName.equals(DB.BUH_FAKTURA__ER_REFERENS)) {
            //
            String value = getValueTableInvert(colName);
            //
            try {
                HelpA.writeToFile(IO.getErReferens(getFakturaKundId()), value);
            } catch (IOException ex) {
                Logger.getLogger(InvoiceA_Insert.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (colName.equals(DB.BUH_FAKTURA__DROJSMALSRANTA)
                || colName.equals(DB.BUH_FAKTURA__FRAKT)
                || colName.equals(DB.BUH_FAKTURA__EXP_AVG)) {
            //
            String value = getValueTableInvert(colName, TABLE_INVERT_3);
            //
            try {
                HelpA.writeToFile(colName, value);
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
        } else if (col_name.equals(DB.BUH_FAKTURA__MAKULERAD)) {
            //
            System.out.println("FAKTURA MAKULERAD");
            //
        } else if (col_name.equals(DB.BUH_FAKTURA_ARTIKEL___ID)) {
            //
            Validator.validateJComboInput((JComboBox) ie.getSource()); // OBS! JCombo input validation
            //
            setArticlePrise(true);
            //
        }else if (col_name.equals(DB.BUH_FAKTURA_KUND__ID)) {
            //
            Validator.validateJComboInput((JComboBox) ie.getSource()); // OBS! JCombo input validation
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__MOMS_SATS)) {
            //
            momsSaveEntry.setMomsSats(jli.getValue());
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__INKL_MOMS)) {
            //
            momsSaveEntry.setInklExklMoms(jli.getValue());
            //
            hideMomsSatsIfExklMoms();
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
            //
            Object selectedObj = box.getSelectedItem();
            //
            if (selectedObj instanceof HelpA.ComboBoxObject) {
                HelpA.ComboBoxObject cbo = (HelpA.ComboBoxObject) box.getSelectedItem();
                String pris = cbo.getParam_3();
                if (pris.isEmpty()) {
                    setValueTableInvert(DB.BUH_FAKTURA_ARTIKEL___PRIS, TABLE_INVERT_2, "0");
                } else {
                    setValueTableInvert(DB.BUH_FAKTURA_ARTIKEL___PRIS, TABLE_INVERT_2, pris);
                }
            } else { // This is when choosing empty
                setValueTableInvert(DB.BUH_FAKTURA_ARTIKEL___PRIS, TABLE_INVERT_2, "0");
            }
            //
            if (force && CURRENT_OPERATION_INSERT == false) {
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
