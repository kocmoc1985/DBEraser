/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.LANG;
import BuhInvoice.sec.IO;
import BuhInvoice.sec.MomsBuh_F_artikel;
import BuhInvoice.sec.MomsComporator;
import MyObjectTable.OutPut;
import MyObjectTable.Table;
import MyObjectTableInvert.Basic;
import MyObjectTableInvert.ColumnDataEntryInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import forall.HelpA_;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.JTextFieldInvert;
import MyObjectTableInvert.RowDataInvertB;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
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
    private static double MOMS_ARTIKLAR = 0;
    private static double MOMS_FRAKT_AND_EXP_AVG = 0;
    //
    private static double FRAKT = 0;
    private static double EXP_AVG = 0;
    private static double MOMS_SATS__FRAKT_AND_EXP_AVG = 0;
    //
    public static boolean CURRENT_OPERATION_INSERT = false;
    //
//    protected final Moms momsSaveEntry = new Moms();
    //
    protected static boolean CREATE_KONTANT_FAKTURA__OPERATION_INSERT = false;
    //

    public Invoice_(BUH_INVOICE_MAIN bim) {
        super(bim);
        initFakturaEntry_();
    }

    private void buttonLogic() {
        //
        boolean rowSelected = HelpA_.rowSelected(bim.jTable_InvoiceA_Insert_articles);
        //
        GP_BUH.enableDisableButtons(bim.jPanel9, true);
        GP_BUH.enableDisableButtons(bim.jPanel11, true);
        GP_BUH.enableDisableButtons(bim.jPanel12, true);
        GP_BUH.setEnabled(bim.jButton_delete_articles_row, true);
        //
        if (articlesJTableEmpty()) {
            GP_BUH.setEnabled(bim.jButton_update_articles_row, false);
            GP_BUH.setEnabled(bim.jButton_delete_articles_row, false);
            GP_BUH.enableDisableButtons(bim.jPanel11, false);
        }
        //
        if (rowSelected == false) {
            GP_BUH.enableDisableButtons(bim.jPanel11, false);
            GP_BUH.setEnabled(bim.jButton_delete_articles_row, false);
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
//                if (bim.isMakulerad()) {
//                    HelpA_.showNotification_separate_thread(LANG.MSG_9);
//                }
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

    /**
     * Good idea, but must be re-thinked. There are many things which makes it
     * complicated.. [2020-10-05]
     *
     * @deprecated
     */
    protected void hideColumnsWithEmptyRows() {
        //
        JTable table = getArticlesTable();
        //
        String[] arr = new String[]{InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN, InvoiceB.TABLE_INVOICE_ARTIKLES__KOMMENT};
        //
        for (String columnName : arr) {
            if (HelpA_.allRowsEmptyColumn(getArticlesTable(), columnName)) {
                HelpA_.hideColumnByName(table, columnName);
            }
        }
        //
    }

    private boolean fakturaBetald() {
        //
        return bim.isBetald();
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
            HelpA_.showNotification(LANG.MSG_2);
            return;
        }
        //
        if (containsInvalidatedFields(TABLE_INVERT_2, DB.START_COLUMN, getConfigTableInvert_2())) {
            HelpA_.showNotification(LANG.MSG_1);
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
//        showTableInvert_3();
        //
    }

    @Override
    protected boolean fieldsValidated(boolean insert) {
        //
        if (containsEmptyObligatoryFields(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert())) {
            HelpA_.showNotification(LANG.MSG_2);
            return false;
        }
        //
        if (containsInvalidatedFields(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert())) {
            HelpA_.showNotification(LANG.MSG_1);
            return false;
        }
        //
        // Yes it's 100% correct that TABLE_INVERT_2 is not validated [2020-10-01]
        //
        if (containsInvalidatedFields(TABLE_INVERT_3, DB.START_COLUMN, getConfigTableInvert_3())) {
            HelpA_.showNotification(LANG.MSG_1);
            return false;
        }
        //
        return true;
        //
    }

    protected boolean fieldsValidatedArticle() {
        //
        if (containsInvalidatedFields(TABLE_INVERT_2, DB.START_COLUMN, getConfigTableInvert_2())) {
            HelpA_.showNotification(LANG.MSG_1);
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

    protected static String getNextFakturaNr() {
        //
        HashMap<String, String> map = new HashMap<>();
//        map.put(DB.BUH_FAKTURA__KUNDID__, kundId);
        String json = JSon.hashMapToJSON(map);
        //
        try {
            //
            String fakturaNr = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_GET_LATEST_FAKTURA_NR, json);
            //
            if (HelpA_.checkIfNumber_b(fakturaNr)) {
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
        String value = HelpA_.getValueGivenRow(table, row, parameter);
        //
        if (HelpA_.isNumber(value)) {
            return Double.parseDouble(value);
        } else {
            return 0;
        }
        //
    }

//    public double getRabattPercent_JTable(JTable table, int row) {
//        //
//        String rabatt = HelpA_.getValueGivenRow(table, row, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT);
//        //
//        try {
//            double rabatt_ = Double.parseDouble(rabatt);
//            if (rabatt_ > 1) {
//                return rabatt_ / 100;
//            } else {
//                return rabatt_;
//            }
//        } catch (Exception ex) {
//            return 0;
//        }
//    }
    public double getPercent_JTable(JTable table, int row, String parameter) {
        //
        String value = HelpA_.getValueGivenRow(table, row, parameter);
        //
        try {
            double value_ = Double.parseDouble(value);
            if (value_ > 1) {
                return value_ / 100;
            } else {
                return value_;
            }
        } catch (Exception ex) {
            return 0;
        }
    }

//    /**
//     * @deprecated @return
//     */
//    public double getMomsSats() {
////        try {
//        return Double.parseDouble(getValueTableInvert(DB.BUH_FAKTURA__MOMS_SATS, TABLE_INVERT_3));
////        } catch (Exception ex) {
////            return 0.25;
////        }
//    }
    public boolean getMakulerad() {
        return Integer.parseInt(getValueTableInvert(DB.BUH_FAKTURA__MAKULERAD, TABLE_INVERT_3)) == 1;
    }

    protected void resetFakturaTotal() {
        //
        bim.resetArticlesCount();
        //
        FAKTURA_TOTAL = 0;
        RABATT_TOTAL = 0;
        FAKTURA_TOTAL_EXKL_MOMS = 0;
        MOMS_TOTAL = 0;
        MOMS_ARTIKLAR = 0;
        MOMS_FRAKT_AND_EXP_AVG = 0;
        //
        MOMS_SATS__FRAKT_AND_EXP_AVG = 0;
        //
        displayTotals();
        //
    }

    private void displayTotals() {
        BUH_INVOICE_MAIN.jTextField_total_inkl_moms.setText("" + getTotal(FAKTURA_TOTAL));
        BUH_INVOICE_MAIN.jTextField_total_exkl_moms.setText("" + getTotal(FAKTURA_TOTAL_EXKL_MOMS));
        BUH_INVOICE_MAIN.jTextField_moms_total.setText("" + getTotal(MOMS_TOTAL));
        BUH_INVOICE_MAIN.jTextField_rabatt_total.setText("" + getTotal(RABATT_TOTAL));
        BUH_INVOICE_MAIN.jTextField_moms_artiklar.setText("" + getTotal(MOMS_ARTIKLAR));
        BUH_INVOICE_MAIN.jTextField_moms_frakt_expavg.setText("" + getTotal(MOMS_FRAKT_AND_EXP_AVG));
        //
        BUH_INVOICE_MAIN.jTextField_moms_sats_frakt_exp_avg.setText("" + getTotal(MOMS_SATS__FRAKT_AND_EXP_AVG) * 100);
        //
        BUH_INVOICE_MAIN.jTextField_frakt.setText("" + getTotal(FRAKT));
        BUH_INVOICE_MAIN.jTextField_exp_avg.setText("" + getTotal(EXP_AVG));
    }

    protected void countFakturaTotal(JTable table) {
        //
        if (table.equals(bim.jTable_InvoiceA_Insert_articles) == false) {
            HelpA_.showNotification("WRONG Articles Table!!");
        }
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
        RABATT_TOTAL = 0;
        //
        double pris_exkl_moms;
        int antal;
        //
        HashMap<Double, Double> moms_map = new HashMap<>();
        //
        for (int i = 0; i < table.getModel().getRowCount(); i++) {
            //
            double rabatt_percent = getPercent_JTable(table, i, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT);
            double rabatt_kr = getDoubleJTable(table, i, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT_KR);
            double moms_sats = getPercent_JTable(table, i, InvoiceB.TABLE_INVOICE_ARTIKLES__MOMS_SATS);
            //
            pris_exkl_moms = Double.parseDouble(HelpA_.getValueGivenRow(table, i, prisColumn));
            antal = Integer.parseInt(HelpA_.getValueGivenRow(table, i, antalColumn));
            //
            //
            if (rabatt_percent == 0 && rabatt_kr == 0) {
                double actPris = (pris_exkl_moms * antal);
                FAKTURA_TOTAL += actPris;
                double actMoms = (pris_exkl_moms * antal) * moms_sats;
                //
                HelpA_.increase_map_value_with_x(moms_sats, actPris, moms_map);
                //
                MOMS_TOTAL += actMoms;
                //
            } else if (rabatt_kr != 0) {
                double actPris = (pris_exkl_moms - rabatt_kr) * antal;
                FAKTURA_TOTAL += actPris;
                RABATT_TOTAL += (rabatt_kr * antal);
                double actMoms = ((pris_exkl_moms - rabatt_kr) * antal) * moms_sats;
                //
                HelpA_.increase_map_value_with_x(moms_sats, actPris, moms_map);
                //
                MOMS_TOTAL += actMoms;
            }
            //
        }
        //
//        double momsSats = getMomsSats();
        FRAKT = getDoubleTableInvert((TableInvert) TABLE_INVERT_3, DB.BUH_FAKTURA__FRAKT);
        EXP_AVG = getDoubleTableInvert((TableInvert) TABLE_INVERT_3, DB.BUH_FAKTURA__EXP_AVG);
        //
//        MOMS_TOTAL = FAKTURA_TOTAL * momsSats + countMomsFraktAndExpAvg(frakt, exp_avg, momsSats);
        //
        MOMS_ARTIKLAR = MOMS_TOTAL;
        //here below is "MOMS_SATS__FRAKT_AND_EXP_AVG" calculated
        MOMS_FRAKT_AND_EXP_AVG = countMomsFraktAndExpAvg(FRAKT, EXP_AVG, moms_map);
        MOMS_TOTAL += MOMS_FRAKT_AND_EXP_AVG;
        FAKTURA_TOTAL += MOMS_TOTAL;
        FAKTURA_TOTAL_EXKL_MOMS = FAKTURA_TOTAL - MOMS_TOTAL;
        //
        //
        FAKTURA_TOTAL += FRAKT;
        FAKTURA_TOTAL += EXP_AVG;
        //
        displayTotals();
        //
    }

    /**
     * Under development [2020-10-02] Moms logic for "frakt" & "exp. avgift"
     * Momssatsen för frakt- och fakturaavgiften/expeditionsavgiften på
     * fakturan/fakturorna blir den momssats som du har på den största
     * summan/beloppet på din faktura. Ifall du inte vill att det ska bli på det
     * viset så kan du lägga din moms för dessa avgifter som artiklar på
     * fakturan istället. Detta är viktigt för enligt mervärdesskattelagen ska
     * beskattningsunderlaget för fraktkostnad och fakturaavgift fördelas på
     * beskattningsunderlaget för respektive vara. Det innebär att du behöver
     * fördela frakt och fakturaavgifter på olika momssatser.
     *
     * @param frakt
     * @param expAvg
     * @return
     */
    private double countMomsFraktAndExpAvg(double frakt, double expAvg, HashMap<Double, Double> map) {
        //
        List<MomsBuh_F_artikel> list = new ArrayList<>();
        //
        for (Map.Entry<Double, Double> entry : map.entrySet()) {
            Double moms = entry.getKey();
            Double sum = entry.getValue();
            //
            list.add(new MomsBuh_F_artikel(moms, sum));
            //
        }
        //
        Collections.sort(list, new MomsComporator());
        //
        //
        double momsSats = 0.25;
        //
        if (list.isEmpty() == false) {
            momsSats = list.get(0).getMomsSats();
        }
        //
        MOMS_SATS__FRAKT_AND_EXP_AVG = momsSats;
        //
        return (frakt + expAvg) * momsSats;
    }

    /**
     * @deprecated @param frakt
     * @param expAvg
     * @param momsSats
     * @return
     */
    private double countMomsFraktAndExpAvg(double frakt, double expAvg, double momsSats) {
        return (frakt + expAvg) * momsSats;
    }

    protected double getFakturaTotal() {
        return GP_BUH.round_double(FAKTURA_TOTAL); // OBS! Rounding till "Whole Number"
    }

    protected double getTotal(double total) {
        return GP_BUH.round_double(total);
    }

    protected double getMomsArtiklarTotal() {
        return GP_BUH.round_double(MOMS_ARTIKLAR);
    }

    protected double getMomsFraktExpAvgTotal() {
        return GP_BUH.round_double(MOMS_FRAKT_AND_EXP_AVG);
    }

    protected double getRabattTotal() {
        return GP_BUH.round_double(RABATT_TOTAL);
    }

    protected double getMomsTotal() {
        return GP_BUH.round_double(MOMS_TOTAL);
    }

    protected double getTotalExklMoms() {
        return GP_BUH.round_double(FAKTURA_TOTAL_EXKL_MOMS);
    }

    protected String defineMomsSats() {
//        if (momsSaveEntry.getMomsSats() == null) {
        return DB.STATIC__MOMS_SATS;
//        } else {
//            return JSon._get_special_(
//                    DB.STATIC__MOMS_SATS,
//                    momsSaveEntry.getMomsSats()
//            );
//        }
    }

    protected String defineMomsSats(JTable table, boolean isOmvantMoms) {
        //
        if (isOmvantMoms) {
            return JSon._get_special_(DB.STATIC__MOMS_SATS, "0");
        } else {
            return JSon._get_special_(DB.STATIC__MOMS_SATS,
                    HelpA_.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__MOMS_SATS));
        }

    }

    public abstract RowDataInvert[] getConfigTableInvert_2();

    public abstract RowDataInvert[] getConfigTableInvert_3();

    @Override
    public void showTableInvert() {
        //
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert(), false, "buh_faktura_a");
        TABLE_INVERT = null;
        TABLE_INVERT = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT, 5, 0, 5, 0);
        showTableInvert(bim.jPanel2_faktura_main);
        //
    }

    public void showTableInvert_2() {
        //
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_2(), false, "buh_f_artikel");
        TABLE_INVERT_2 = null;
        TABLE_INVERT_2 = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT_2, 5, 0, 5, 0);
        showTableInvert(bim.jPanel_articles, TABLE_INVERT_2);
        //
        setArticlePrise__and_other(false); // [2020-08-19]
        //
    }

    public void showTableInvert_3() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_3(), false, "buh_faktura_b");
        TABLE_INVERT_3 = null;
        TABLE_INVERT_3 = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT_3, 5, 0, 5, 0);
        showTableInvert(bim.jPanel3_faktura_sec, TABLE_INVERT_3);
        //
    }

    public RowDataInvert[] getConfigTableInvert_insert() {
        // String fixedComboValues_a = "Skruv;1,Spik;2,Hammare;3,Traktor;4,Skruvmejsel;5"; // This will aquired from SQL
        String fixedComboValues_a = requestJComboValuesHttp(DB.PHP_FUNC_PARAM_GET_KUND_ARTICLES, new String[]{DB.BUH_FAKTURA_ARTIKEL___NAMN, DB.BUH_FAKTURA_ARTIKEL___ID, DB.BUH_FAKTURA_ARTIKEL___PRIS, DB.BUH_FAKTURA_ARTIKEL___ARTNR});
        RowDataInvert articles = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_a, DB.BUH_F_ARTIKEL__ARTIKELID, InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN, "", true, true, true);
        articles.enableFixedValuesAdvanced();
//        articles.setUneditable();
        articles.enableEmptyValue(); //[2020-09-28] -> this makes that is't shown like "-" in the artcles jcombo for the empty entry
        //
        //
        String fixedComboValues_c = JSon._get_special_(
                DB.STATIC__MOMS_SATS,
                IO.loadLastEntered(DB.BUH_F_ARTIKEL__MOMS_SATS, "25")
        );
//        String fixedComboValues_c = defineMomsSats();
        RowDataInvert moms = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_c, DB.BUH_F_ARTIKEL__MOMS_SATS, InvoiceB.TABLE_INVOICE_ARTIKLES__MOMS_SATS, "", true, true, false);
        moms.enableFixedValuesAdvanced();
        moms.setUneditable();
        //
        //
        String fixedComboValues_d = DB.STATIC__JA_NEJ; // This will aquired from SQL
        RowDataInvert omvant_skatt = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_d, DB.BUH_F_ARTIKEL__OMVANT_SKATT, InvoiceB.TABLE_INVOICE_ARTIKLES__OMVANT_SKATT, "", false, true, false);
        omvant_skatt.enableFixedValuesAdvanced();
        omvant_skatt.setUneditable();
        //
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
            moms,
            omvant_skatt,
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

    private boolean isOmvant(String shortVal) {
        return shortVal.equals("1");
    }

    /**
     * This config is for editing of articles
     *
     * @return
     */
    public RowDataInvert[] getConfigTableInvert_edit_articles() {
        //
        JTable table = getArticlesTable();
        //
        String fixedComboValues_a = JSon._get__with_merge(HelpA_.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN),
                HelpA_.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_ID),
                requestJComboValuesHttp(DB.PHP_FUNC_PARAM_GET_KUND_ARTICLES, new String[]{DB.BUH_FAKTURA_ARTIKEL___NAMN, DB.BUH_FAKTURA_ARTIKEL___ID, DB.BUH_FAKTURA_ARTIKEL___PRIS, DB.BUH_FAKTURA_ARTIKEL___ARTNR}));
//        String fixedComboValues_a = "Skruv;1,Spik;2,Hammare;3,Traktor;4,Skruvmejsel;5"; // This will aquired from SQL
        RowDataInvert articles = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_a, DB.BUH_F_ARTIKEL__ARTIKELID, InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN, "", true, true, true);
        articles.enableFixedValuesAdvanced();
//        articles.setUneditable();
//        articles.setDisabled();
        //
        //
        String valSelectedRow = HelpA_.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__OMVANT_SKATT);
        String valSelectedRow_translated = JSon.getShortName(DB.STATIC__JA_NEJ, valSelectedRow);
        String fixedComboValues_d = JSon._get_special_(DB.STATIC__JA_NEJ, valSelectedRow_translated);
        RowDataInvert omvant_skatt = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_d, DB.BUH_F_ARTIKEL__OMVANT_SKATT, InvoiceB.TABLE_INVOICE_ARTIKLES__OMVANT_SKATT, "", false, true, false);
        omvant_skatt.enableFixedValuesAdvanced();
        omvant_skatt.setUneditable();
        //
        boolean omvant = isOmvant(valSelectedRow_translated);
        //
        String fixedComboValues_c = defineMomsSats(table, omvant);
        RowDataInvert moms = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_c, DB.BUH_F_ARTIKEL__MOMS_SATS, InvoiceB.TABLE_INVOICE_ARTIKLES__MOMS_SATS, "", false, true, false);
        moms.enableFixedValuesAdvanced();
        moms.setUneditable();
        if (omvant) {
            moms.setDisabled();
        }
//        disableMomsJComboIf(moms); // *****
        //
        //
        String komm = HelpA_.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__KOMMENT);
        RowDataInvert komment = new RowDataInvertB(komm, DB.BUH_F_ARTIKEL__KOMMENT, InvoiceB.TABLE_INVOICE_ARTIKLES__KOMMENT, "", true, true, false);
        //
        String ant = HelpA_.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL);
        RowDataInvert antal = new RowDataInvertB(ant, DB.BUH_F_ARTIKEL__ANTAL, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL, "", false, true, false);
        //
//        String fixedComboValues_b = JSon._get_special_(
//                DB.STATIC__ENHET,
//                HelpA_.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ENHET)
//        );
        //
        String fixedComboValues_b = JSon._get(HelpA_.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ENHET),
                "",
                DB.STATIC__ENHET
        );
        //
        RowDataInvert enhet = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_b, DB.BUH_F_ARTIKEL__ENHET, InvoiceB.TABLE_INVOICE_ARTIKLES__ENHET, "", true, true, false);
        enhet.enableFixedValuesAdvanced();
        enhet.setUneditable();
        //
        //
        String pris_ = HelpA_.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS);
        RowDataInvert pris = new RowDataInvertB(pris_, DB.BUH_F_ARTIKEL__PRIS, "PRIS", "", false, true, true);
        //
        String rabatt_ = HelpA_.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT);
        RowDataInvert rabatt = new RowDataInvertB(rabatt_, DB.BUH_F_ARTIKEL__RABATT, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT, "", false, true, false);
        //
        String rabatt_kr_ = HelpA_.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT_KR);
        RowDataInvert rabatt_kr = new RowDataInvertB(rabatt_kr_, DB.BUH_F_ARTIKEL__RABATT_KR, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT_KR, "", false, true, false);
        //
        RowDataInvert[] rows = {
            articles,
            moms,
            omvant_skatt,
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

    @Override
    public void mouseClickedForward(MouseEvent me, int column, int row, String tableName, TableInvert ti) {
        //
        super.mouseClickedForward(me, column, row, tableName, ti); 
        //
        String col_name = ti.getCurrentColumnName(me.getSource());
        //
        if (col_name.equals(DB.BUH_FAKTURA__ER_REFERENS)) {
            //
            JLinkInvert jli = (JLinkInvert) me.getSource();
            //
            if (jli.getValue().isEmpty()) {
                String er_referens_last = IO.loadLastEntered(IO.getErReferens(getFakturaKundId()), "");
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
    
    

//    @Override
//    public void mouseClicked(MouseEvent me, int column, int row, String tableName, TableInvert ti) {
//        //
//        super.mouseClicked(me, column, row, tableName, ti); //To change body of generated methods, choose Tools | Templates.
//        //
//        String col_name = ti.getCurrentColumnName(me.getSource());
//        //
//        if (col_name.equals(DB.BUH_FAKTURA__ER_REFERENS)) {
//            //
//            JLinkInvert jli = (JLinkInvert) me.getSource();
//            //
//            if (jli.getValue().isEmpty()) {
//                String er_referens_last = IO.loadLastEntered(IO.getErReferens(getFakturaKundId()), "");
//                setValueTableInvert(DB.BUH_FAKTURA__ER_REFERENS, TABLE_INVERT, er_referens_last);
//            }
//            //
//        } else if (col_name.equals(DB.BUH_FAKTURA__FAKTURA_DATUM)) {
//            //
//            restoreFakturaDatumIfEmty(me, ti);
//            //
//        }
//        //
//    }

    private void restoreFakturaDatumIfEmty(MouseEvent me, TableInvert ti) {
        //
        JLinkInvert jli = (JLinkInvert) me.getSource();
        JTextFieldInvert jtfi = (JTextFieldInvert) jli;
        //
        if (jli.getValue().isEmpty()) {
            //
            jtfi.setText(HelpA_.get_proper_date_yyyy_MM_dd());
            //
            if (Validator.validateDate(jli)) {
                forfalloDatumAutoChange(ti);
            }
            //
        }
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
                date_new = HelpA_.get_date_time_plus_some_time_in_days(date, value);
            } else {
                date_new = HelpA_.get_date_time_minus_some_time_in_days(date, value);
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
            if (col_name.equals(DB.BUH_F_ARTIKEL__PRIS)) {
                //
                setValueTableInvert(DB.BUH_F_ARTIKEL__RABATT, ti, "0");
                setValueTableInvert(DB.BUH_F_ARTIKEL__RABATT_KR, ti, "0");
                //
            } else if (col_name.equals(DB.BUH_F_ARTIKEL__RABATT)) {
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
                saveInput(col_name);
                //
                if (col_name.equals(DB.BUH_FAKTURA__FRAKT) || col_name.equals(DB.BUH_FAKTURA__EXP_AVG)) {
                    countFakturaTotal(getArticlesTable());
                }
                //
            }
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__VAR_REFERENS)) {
            //
            saveInput(DB.BUH_FAKTURA__VAR_REFERENS);
            //
            Validator.validateMaxInputLength(jli, 100);
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__ER_REFERENS)) {
            //
            saveInput(DB.BUH_FAKTURA__ER_REFERENS);
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
        } else if (col_name.equals(DB.BUH_FAKTURA__FAKTURANR_ALT)) {
            //
            Validator.validateDigitalInput(jli);
            //
        } else if (col_name.equals(DB.BUH_F_ARTIKEL__KOMMENT)) {
            //
            Validator.validateMaxInputLength(jli, 200);
            //
        }
    }

    private void saveInput(String colName) {
        //
        //
        if (colName.equals(DB.BUH_FAKTURA__VAR_REFERENS)) {
            //
            String value = getValueTableInvert(colName);
            //
            IO.writeToFile(DB.BUH_FAKTURA__VAR_REFERENS, value);
            //
        } else if (colName.equals(DB.BUH_FAKTURA__ER_REFERENS)) {
            //
            String value = getValueTableInvert(colName);
            //
            IO.writeToFile(IO.getErReferens(getFakturaKundId()), value);
            //
        } else if (colName.equals(DB.BUH_FAKTURA__DROJSMALSRANTA)
                || colName.equals(DB.BUH_FAKTURA__FRAKT)
                || colName.equals(DB.BUH_FAKTURA__EXP_AVG)) {
            //
            String value = getValueTableInvert(colName, TABLE_INVERT_3);
            //
            IO.writeToFile(colName, value);
            //
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
        //
        String date_new = HelpA_.get_date_time_plus_some_time_in_days(date, value);
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
            setArticlePrise__and_other(true);
            //
        } else if (col_name.equals(DB.BUH_FAKTURA_KUND__ID)) {
            //
            Validator.validateJComboInput((JComboBox) ie.getSource()); // OBS! JCombo input validation
            //
        } else if (col_name.equals(DB.BUH_F_ARTIKEL__MOMS_SATS)) {
            //
            String omvant = getValueTableInvert(DB.BUH_F_ARTIKEL__OMVANT_SKATT, ti);
            if (omvant.equals("0")) {
                IO.writeToFile(DB.BUH_F_ARTIKEL__MOMS_SATS, jli.getValue());
            }
            //
        } else if (col_name.equals(DB.BUH_F_ARTIKEL__OMVANT_SKATT)) {
            //
            String omvant = jli.getValue();
            //
            ColumnDataEntryInvert cde = getColumnDataEntryInvert(DB.BUH_F_ARTIKEL__MOMS_SATS, ti);
            JComboBox box = (JComboBox) cde.getObject();
            //
            if (omvant.equals("1")) { // Means using omvänt skattskyldighet
                setValueTableInvert(DB.BUH_F_ARTIKEL__MOMS_SATS, ti, new HelpA_.ComboBoxObject("0%", "", "", ""));
                box.setEnabled(false);
            } else if (omvant.equals("0")) {
                setValueTableInvert(DB.BUH_F_ARTIKEL__MOMS_SATS, ti, new HelpA_.ComboBoxObject("25%", "", "", ""));
                box.setEnabled(true);
            }
            //
        }

    }

    private void setArticlePrise__and_other(boolean force) {
        //
        boolean conditionSpecial = CURRENT_OPERATION_INSERT == false && articlesJTableEmpty() == true;
        //
        if (CURRENT_OPERATION_INSERT || conditionSpecial || force) {
            //
            JComboBox box = (JComboBox) getObjectTableInvert(DB.BUH_FAKTURA_ARTIKEL___ID, TABLE_INVERT_2);
            //
            Object selectedObj = box.getSelectedItem();
            //
            if (selectedObj instanceof HelpA_.ComboBoxObject) {
                HelpA_.ComboBoxObject cbo = (HelpA_.ComboBoxObject) box.getSelectedItem();
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
            if (force) { // && CURRENT_OPERATION_INSERT == false
                setValueTableInvert(DB.BUH_FAKTURA_ARTIKEL___KOMMENT, TABLE_INVERT_2, "");
                setValueTableInvert(DB.BUH_F_ARTIKEL__ANTAL, TABLE_INVERT_2, "1");
                setValueTableInvert(DB.BUH_F_ARTIKEL__RABATT, TABLE_INVERT_2, "0");
                setValueTableInvert(DB.BUH_F_ARTIKEL__RABATT_KR, TABLE_INVERT_2, "0");
                setValueTableInvert(DB.BUH_F_ARTIKEL__OMVANT_SKATT, TABLE_INVERT_2, new HelpA_.ComboBoxObject("Nej", "", "", ""));
                setValueTableInvert(DB.BUH_F_ARTIKEL__MOMS_SATS, TABLE_INVERT_2, new HelpA_.ComboBoxObject("25%", "", "", ""));

            }
            //
        }
        //
    }

}
