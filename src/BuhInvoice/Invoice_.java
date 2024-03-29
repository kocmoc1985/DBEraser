/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.LANG;
import BuhInvoice.sec.IO;
import BuhInvoice.sec.MomsBuh_F_artikel;
import BuhInvoice.sec.MomsComparator;
import BuhInvoice.sec.RutRot;
import BuhInvoice.sec.RutRotFrame;
import MyObjectTable.OutPut;
import MyObjectTable.Table;
import MyObjectTableInvert.ColumnDataEntryInvert;
import MyObjectTableInvert.JComboBoxInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import forall.HelpA;
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
import MyObjectTableInvert.TableRowInvert;
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
    private static double RUT_AVDRAG_TOTAL = 0;
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
    protected static boolean CREATE_OFFERT__OPERATION_INSERT = false;
    //
    private RutRot rutRot;
    private RutRotFrame rutRotFrame;
    private boolean RUT_ROT__ENABLED = false;
    public static boolean FAKTURA_TAB_ENABLED = true;

    public Invoice_(LAFakturering bim) {
        super(bim);
        initFakturaEntry_();
    }

    public void resetRutRot() {
        //[#RUTROT#]
        rutRot = null;
        rutRotFrame = null;
    }

    private void buttonLogic() {
        //
        boolean rowSelected = HelpA.rowSelected(bim.jTable_InvoiceA_Insert_articles);
        //
        GP_BUH.enableDisableButtons(bim.jPanel9, true); // Buttons: Create New Invoice_, Create new Kontant Invoice_, Abort, Save
        GP_BUH.enableDisableButtons(bim.jPanel11, true);
        GP_BUH.enableDisableButtons(bim.jPanel12, true);
        GP_BUH.setEnabled(bim.jButton_delete_articles_row, true);
        //
        if (FAKTURA_TAB_ENABLED == false) {
            GP_BUH.setEnabled(bim.jButton_create_new_faktura_b, false);
            GP_BUH.setEnabled(bim.jButton_create_new_kontant_faktura_b, false);
        }
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

    protected void SET_CURRENT_OPERATION_INSERT(boolean insert, boolean skipHideSaveNotice) {
        //
        CURRENT_OPERATION_INSERT = insert;
        //
//        bim.FAKTURA_TYPE_CURRENT__OPERATION = bim.getFakturaType();
        //
        if (skipHideSaveNotice == false) {
            GP_BUH.showSaveInvoice_note(false); // [#SAVE-INVOICE-NOTE#] // true: means skip
        }
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
            } else if (CREATE_OFFERT__OPERATION_INSERT == true) {
                //[#OFFERT#]
                bim.jLabel_Faktura_Insert_or_Update.setText(LANG.LBL_MSG_1_3);
                bim.FAKTURA_TYPE_CURRENT__OPERATION = DB.STATIC__FAKTURA_TYPE_OFFERT;
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
                if (bim.isRUT() && bim.isKreditFaktura()) {
                    //[#RUTROT#]
                    bim.jLabel_Faktura_Insert_or_Update.setText(LANG.LBL_MSG_2_3);
                    //
                    GP_BUH.enableDisableButtons(bim.jPanel12, false);
//                    GP_BUH.setEnabled(bim.jButton_confirm_insert_update, false);
                    //
                } else if (bim.isKreditFaktura()) {
                    // OBS! KREDIT FAKTURA [2020-09-15]  
                    String krediteradFakturaNr = bim.getKomment_$();
                    bim.jLabel_Faktura_Insert_or_Update.setText(LANG.LBL_MSG_2_3(krediteradFakturaNr));
                    GP_BUH.setEnabled(bim.jButton_add_article, false);// Shall not be possible to add articles
                    bim.FAKTURA_TYPE_CURRENT__OPERATION = DB.STATIC__FAKTURA_TYPE_KREDIT;
                    //
                } else if (bim.isKontantFaktura()) { // KONTANT FAKTURA
                    bim.jLabel_Faktura_Insert_or_Update.setText(LANG.LBL_MSG_2_1);
                    bim.FAKTURA_TYPE_CURRENT__OPERATION = DB.STATIC__FAKTURA_TYPE_KONTANT;
                } else if (bim.isOffert()) {
                    //[#OFFERT#]
                    bim.jLabel_Faktura_Insert_or_Update.setText(LANG.LBL_MSG_2_1_2);
                    bim.FAKTURA_TYPE_CURRENT__OPERATION = DB.STATIC__FAKTURA_TYPE_OFFERT;
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

    public RutRot getRutRot() {
        //[#RUTROT#]
        return this.rutRot;
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
            if (HelpA.allRowsEmptyColumn(getArticlesTable(), columnName)) {
                HelpA.hideColumnByName(table, columnName);
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
//        showTableInvert_3();
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

    public String insertOrUpdate() {
        return faktura_entry.insertOrUpdate();
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
            if (HelpA.checkIfNumber_b(fakturaNr)) {
                int nr = Integer.parseInt(fakturaNr);
                nr++; // OBS! Iam getting the last so i have to add to get the nr for the act. faktura
                return "" + nr;
            } else {
                return null;
            }
            //
        } catch (Exception ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
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
        String value = HelpA.getValueGivenRow(table, row, parameter);
        //
        if (HelpA.isNumber(value)) {
            return Double.parseDouble(value);
        } else {
            return 0;
        }
        //
    }

//    public double getRabattPercent_JTable(JTable table, int row) {
//        //
//        String rabatt = HelpA.getValueGivenRow(table, row, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT);
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
        String value = HelpA.getValueGivenRow(table, row, parameter);
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
        resetValues();
        //
        displayTotals();
        //
    }

    private void resetValues() {
        FAKTURA_TOTAL = 0;
        RABATT_TOTAL = 0;
        FAKTURA_TOTAL_EXKL_MOMS = 0;
        MOMS_TOTAL = 0;
        MOMS_ARTIKLAR = 0;
        MOMS_FRAKT_AND_EXP_AVG = 0;
        //
        MOMS_SATS__FRAKT_AND_EXP_AVG = 0;
        //
        if (DONT_RESET_RUT__FLAG) {
            // DO NOTHING, [2021-04-30]
        } else {
            RUT_AVDRAG_TOTAL = 0;
        }
        //
        DONT_RESET_RUT__FLAG = false;
        //

    }

    private void displayTotals() {
        //
        if (bim.isKreditFaktura() && CURRENT_OPERATION_INSERT == false) { // bim.isKreditFaktura()
            bim.jLabel4.setText(LANG.ATT_ERHALLA);
        } else {
            bim.jLabel4.setText(LANG.ATT_BETALA);
        }
        //
        LAFakturering.jTextField_total_inkl_moms.setText("" + getTotal(FAKTURA_TOTAL));
        LAFakturering.jTextField_total_exkl_moms.setText("" + getTotal(FAKTURA_TOTAL_EXKL_MOMS));
        LAFakturering.jTextField_moms_total.setText("" + getTotal(MOMS_TOTAL));
        LAFakturering.jTextField_rabatt_total.setText("" + getTotal(RABATT_TOTAL));
        LAFakturering.jTextField_moms_artiklar.setText("" + getTotal(MOMS_ARTIKLAR));
        LAFakturering.jTextField_moms_frakt_expavg.setText("" + getTotal(MOMS_FRAKT_AND_EXP_AVG));
        //
        LAFakturering.jTextField_moms_sats_frakt_exp_avg.setText("" + getTotal(MOMS_SATS__FRAKT_AND_EXP_AVG) * 100);
        //
        LAFakturering.jTextField_frakt.setText("" + getTotal(FRAKT));
        LAFakturering.jTextField_exp_avg.setText("" + getTotal(EXP_AVG));
        //
        if ((bim.isRUT() && CURRENT_OPERATION_INSERT == false) || RUT_ROT__ENABLED) {
            //[#RUTROT#]
            LAFakturering.jTextField_rut_avdrag.setVisible(true);
            LAFakturering.jTextField_rut_total.setVisible(true);
            LAFakturering.jLabel_rut_avdrag.setVisible(true);
            LAFakturering.jLabel_rut_total.setVisible(true);
            //
            LAFakturering.jTextField_rut_avdrag.setText("" + getTotal(RUT_AVDRAG_TOTAL));
            //
            if (RUT_AVDRAG_TOTAL != 0) {
                LAFakturering.jTextField_rut_total.setText("" + getTotal(RUT_AVDRAG_TOTAL + FAKTURA_TOTAL));
            }
            //
        } else {
            LAFakturering.jTextField_rut_avdrag.setVisible(false);
            LAFakturering.jTextField_rut_total.setVisible(false);
            LAFakturering.jLabel_rut_avdrag.setVisible(false);
            LAFakturering.jLabel_rut_total.setVisible(false);
        }

        //
    }

    private boolean DONT_RESET_RUT__FLAG = false;

    public void setRutAvdragTotal(double avdragTotal, RutRot rutRot) {
        RUT_AVDRAG_TOTAL = avdragTotal;
        DONT_RESET_RUT__FLAG = true;
        this.rutRot = rutRot;
        countFakturaTotal(getArticlesTable());
    }

    private double getRutAvdragTotal() {
        //[#RUTROT#]
        //
        String json;
        //
        if (bim.getCopiedFromFakturaId().equals("0") == false) {
            // If comes here it means the faktura is copied
            json = bim.getSELECT_copied_from_faktura_id__doubleWhere(); // [#KREDIT-RUT#]
        } else {
            json = bim.getSELECT_fakturaId__doubleWhere();
        }
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM_GET_RUT, json);
            //
            ArrayList<HashMap<String, String>> entries = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            if (entries.isEmpty() == false) {
                return Double.parseDouble(entries.get(0).get(DB.BUH_FAKTURA_RUT__SKATTEREDUKTION));
            }
            //
        } catch (Exception ex) {
            Logger.getLogger(HelpBuh.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        //
        return 0;
        //
    }

    protected void countFakturaTotal(JTable table) {
        //
        if (table.equals(bim.jTable_InvoiceA_Insert_articles) == false) {
            HelpA.showNotification("WRONG Articles Table!!");
            return;
        }
        //
        // Some methods are called from here because this method (countFakturaTotal)
        // is executed uppon almost all actions [2020-09-30]
        bim.displayArticlesCount();
        //
        SET_CURRENT_OPERATION_INSERT(CURRENT_OPERATION_INSERT, true); // For buttons enabled/disabled logics
        //
        //[#RUTROT#]
        if (bim.isRUT() && CURRENT_OPERATION_INSERT == false && RUT_AVDRAG_TOTAL == 0) {
            DONT_RESET_RUT__FLAG = true;
            RUT_AVDRAG_TOTAL = getRutAvdragTotal();
        }
        //
        String prisColumn = InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS;
        String antalColumn = InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL;
        //
        countFakturaTotal(table, prisColumn, antalColumn);
        //
    }

    private void countFakturaTotal(JTable table, String prisColumn, String antalColumn) {
        //
        if (TABLE_INVERT_2 == null) {
            return;
        }
        //
        resetValues(); // IMPORTANT [2020-02-19]
        //
        double pris_exkl_moms;
        Double antal;
        //
        HashMap<Double, Double> moms_map = new HashMap<>();
        //
        for (int i = 0; i < table.getModel().getRowCount(); i++) {
            //
            double rabatt_percent = getPercent_JTable(table, i, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT);
            double rabatt_kr_eur = getDoubleJTable(table, i, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT_KR);
            double moms_sats = getPercent_JTable(table, i, InvoiceB.TABLE_INVOICE_ARTIKLES__MOMS_SATS);
            //
            //
            pris_exkl_moms = Double.parseDouble(HelpA.getValueGivenRow(table, i, prisColumn));
            antal = Double.parseDouble(HelpA.getValueGivenRow(table, i, antalColumn));
            //
            //
            if (rabatt_percent == 0 && rabatt_kr_eur == 0) {
                double actPris = (pris_exkl_moms * antal);
                //
                if (HelpBuh.FOREIGN_CUSTOMER) {
                    //[#EUR-SEK#]
                    FAKTURA_TOTAL += actPris * getCurrencyRateTableInvert3();
                } else {
                    FAKTURA_TOTAL += actPris;
                }
                //
                double actMoms = (pris_exkl_moms * antal) * moms_sats;
                //
                HelpA.increase_map_value_with_x(moms_sats, actPris, moms_map);
                //
                moms_total_count_help(actMoms);
                //
            } else if (rabatt_kr_eur != 0) {
                //
                double actPris = (pris_exkl_moms - rabatt_kr_eur) * antal;
                //
                if (HelpBuh.FOREIGN_CUSTOMER) {
                    //[#EUR-SEK#]
                    FAKTURA_TOTAL += actPris * getCurrencyRateTableInvert3();
                } else {
                    FAKTURA_TOTAL += actPris;
                }
                //
                if (HelpBuh.FOREIGN_CUSTOMER) {
                    RABATT_TOTAL += (rabatt_kr_eur * antal) * getCurrencyRateTableInvert3();
                } else {
                    RABATT_TOTAL += (rabatt_kr_eur * antal);
                }
                //
                double actMoms = ((pris_exkl_moms - rabatt_kr_eur) * antal) * moms_sats;
                //
                HelpA.increase_map_value_with_x(moms_sats, actPris, moms_map);
                //
                moms_total_count_help(actMoms);
                //
            }
            //
        }
        //
        FRAKT = getDoubleTableInvert((TableInvert) TABLE_INVERT_3, DB.BUH_FAKTURA__FRAKT);
        EXP_AVG = getDoubleTableInvert((TableInvert) TABLE_INVERT_3, DB.BUH_FAKTURA__EXP_AVG);
        //
        //
        MOMS_ARTIKLAR = MOMS_TOTAL;
        //
        //here below is "MOMS_SATS__FRAKT_AND_EXP_AVG" calculated
        if (HelpBuh.FOREIGN_CUSTOMER == false) {
            MOMS_FRAKT_AND_EXP_AVG = countMomsFraktAndExpAvg(FRAKT, EXP_AVG, moms_map);
        }
        //
        MOMS_TOTAL += MOMS_FRAKT_AND_EXP_AVG;
        FAKTURA_TOTAL += MOMS_TOTAL;
        FAKTURA_TOTAL_EXKL_MOMS = FAKTURA_TOTAL - MOMS_TOTAL;
        //
        //
        if (HelpBuh.FOREIGN_CUSTOMER) {
            FAKTURA_TOTAL += FRAKT * getCurrencyRateTableInvert3();
            FAKTURA_TOTAL += EXP_AVG * getCurrencyRateTableInvert3();
        } else {
            FAKTURA_TOTAL += FRAKT;
            FAKTURA_TOTAL += EXP_AVG;
        }

        //
        FAKTURA_TOTAL -= RUT_AVDRAG_TOTAL;
        //
        displayTotals();
        //
//        System.out.println("COUNT FAKTURA TOTAL*********************************************");
        //
    }

    private void moms_total_count_help(double actMoms) {
        if (HelpBuh.FOREIGN_CUSTOMER) {
            MOMS_TOTAL += actMoms * getCurrencyRateTableInvert3();
        } else {
            MOMS_TOTAL += actMoms;
        }
    }

    private double getCurrencyRateTableInvert3() {
        return Double.parseDouble(getValueTableInvert(DB.BUH_FAKTURA__CURRENCY_RATE_A, DB.START_COLUMN, TABLE_INVERT_3));
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
        Collections.sort(list, new MomsComparator());
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
        return GP_BUH.round_double(FAKTURA_TOTAL);
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
        return DB.GET_CONSTANT("STATIC__MOMS_SATS", DB.STATIC__MOMS_SATS);
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
            return JSon._get_special_(DB.GET_CONSTANT("STATIC__MOMS_SATS", DB.STATIC__MOMS_SATS), "0");
        } else {
            return JSon._get_special_(DB.GET_CONSTANT("STATIC__MOMS_SATS", DB.STATIC__MOMS_SATS),
                    HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__MOMS_SATS));
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
        // [#FAKTURAKUND-RELATED-SAVE-RESTORE-JCOMBO#][#INITIAL LOAD#] 
        Object obj = TABLE_INVERT.getObjectAt(0, 1);
        JComboBoxInvert jcbi = (JComboBoxInvert) obj;
        //
        java.awt.EventQueue.invokeLater(() -> {
            jcbi.setSelectedIndex(jcbi.getSelectedIndex() - 1);
            jcbi.setSelectedIndex(jcbi.getSelectedIndex() + 1);
        });
        //
    }

    public void showTableInvert_2(boolean force) {
        //
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_2(), false, "buh_f_artikel");
        TABLE_INVERT_2 = null;
        TABLE_INVERT_2 = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT_2, 5, 0, 5, 0);
        showTableInvert(bim.jPanel_articles, TABLE_INVERT_2);
        //
        // Reason why i cannot have "force=true" in all cases is because:
        // When you edit an Invoice_ and point on an article it showd show data from this row,
        // so consider you did have specified "rabbat" for this article and if force is enabled
        // the specified rabbat will be replaced with "0" [2021-08-27]
        setArticlePrise__and_other(force, null); // [2020-08-19]
        //
    }

    public void showTableInvert_3() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_3(), false, "buh_faktura_b__table_3");
        TABLE_INVERT_3 = null;
        TABLE_INVERT_3 = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT_3, 5, 0, 5, 0);
        showTableInvert(bim.jPanel3_faktura_sec, TABLE_INVERT_3);
    }

    public RowDataInvert[] getConfigTableInvert_insert() {
        // 
        //[#AUTOMATIC-COMMA-WITH-POINT-REPLACEMENT--ARTICLE-NAME#]
        String fixedComboValues_a = requestJComboValuesHttp(DB.PHP_FUNC_PARAM_GET_KUND_ARTICLES, new String[]{DB.BUH_FAKTURA_ARTIKEL___NAMN, DB.BUH_FAKTURA_ARTIKEL___ID, DB.BUH_FAKTURA_ARTIKEL___PRIS, DB.BUH_FAKTURA_ARTIKEL___ARTNR}, true);
        RowDataInvert articles = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_a, DB.BUH_F_ARTIKEL__ARTIKELID, InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN, "", true, true, true);
        articles.enableFixedValuesAdvanced();
//        articles.setUneditable();
        articles.enableEmptyValue(); //["#EMPTY-ARTICLE#"][2020-09-28] -> this makes that is't shown like "-" in the artcles jcombo for the empty entry
        //
        //
        String fixedComboValues_c = JSon._get_special_(
                DB.GET_CONSTANT("STATIC__MOMS_SATS", DB.STATIC__MOMS_SATS),
                IO.loadLastEntered(DB.BUH_F_ARTIKEL__MOMS_SATS, "25")
        );
//        String fixedComboValues_c = defineMomsSats();
        RowDataInvert moms = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_c, DB.BUH_F_ARTIKEL__MOMS_SATS, InvoiceB.TABLE_INVOICE_ARTIKLES__MOMS_SATS, "", true, true, false);
        moms.enableFixedValuesAdvanced();
        moms.setUneditable();
        //
        //
        String fixedComboValues_d = DB.STATIC__JA_NEJ; // This will aquired from SQL
        RowDataInvert omvant_skatt = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_d, DB.BUH_F_ARTIKEL__OMVANT_SKATT, InvoiceB.TABLE_INVOICE_ARTIKLES__OMVAND_SKATT, "", false, true, false);
        omvant_skatt.enableFixedValuesAdvanced();
        omvant_skatt.setUneditable();
        //
//        hideFieldIfPerson(omvant_skatt); //******
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
        //[#AUTOMATIC-COMMA-WITH-POINT-REPLACEMENT--ARTICLE-NAME#]
        String fixedComboValues_a = JSon._get__with_merge(
                HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN),
                HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_ID),
                requestJComboValuesHttp(DB.PHP_FUNC_PARAM_GET_KUND_ARTICLES, new String[]{DB.BUH_FAKTURA_ARTIKEL___NAMN,
            DB.BUH_FAKTURA_ARTIKEL___ID, DB.BUH_FAKTURA_ARTIKEL___PRIS, DB.BUH_FAKTURA_ARTIKEL___ARTNR}, true));
        RowDataInvert articles = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_a, DB.BUH_F_ARTIKEL__ARTIKELID, InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN, "", true, true, true);
        articles.enableFixedValuesAdvanced();
        articles.enableEmptyValue(); //["#EMPTY-ARTICLE#"][2021-04-12] -> this makes that is't shown like "-" in the artcles jcombo for the empty entry
//        articles.setUneditable();
//        articles.setDisabled();
        //
        //
        String valSelectedRow = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__OMVAND_SKATT);
        String valSelectedRow_translated = JSon.getShortName(DB.STATIC__JA_NEJ, valSelectedRow);
        String fixedComboValues_d = JSon._get_special_(DB.STATIC__JA_NEJ, valSelectedRow_translated);
        RowDataInvert omvant_skatt = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_d, DB.BUH_F_ARTIKEL__OMVANT_SKATT, InvoiceB.TABLE_INVOICE_ARTIKLES__OMVAND_SKATT, "", false, true, false);
        omvant_skatt.enableFixedValuesAdvanced();
        omvant_skatt.setUneditable();
        //
//        hideFieldIfPerson(omvant_skatt); //  ******
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
        String fixedComboValues_b = JSon._get(HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ENHET),
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
//            //
//            if (jli.getValue().isEmpty()) {
//                String er_referens_last = IO.loadLastEntered(IO.getErReferens(getFakturaKundId()), "");
//                setValueTableInvert(DB.BUH_FAKTURA__ER_REFERENS, TABLE_INVERT, er_referens_last);
//            }
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
            jtfi.setText(GP_BUH.getDate_yyyy_MM_dd());
            //
            if (Validator.validateDate(jli,true)) {
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
                date_new = GP_BUH.get_date_time_plus_some_time_in_days(date, value);
            } else {
                date_new = GP_BUH.get_date_time_minus_some_time_in_days(date, value);
            }
            //
            setValueTableInvert(DB.BUH_FAKTURA__FAKTURA_DATUM, TABLE_INVERT, date_new);
            //
            forfalloDatumAutoChange(ti);
            //
            Validator.validateDate(jli,true);
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
                || col_name.equals(DB.BUH_FAKTURA__DROJSMALSRANTA)
                || col_name.equals(DB.BUH_FAKTURA__CURRENCY_RATE_A)) {
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
                    || col_name.equals(DB.BUH_FAKTURA__EXP_AVG)
                    || col_name.equals(DB.BUH_FAKTURA__CURRENCY_RATE_A)) {
                //
                if (digitalInputValidated == false) {
                    return;
                }
                //
                if (col_name.equals(DB.BUH_FAKTURA__CURRENCY_RATE_A) == false) {
                    saveInput(col_name);
                }
                //
                if (col_name.equals(DB.BUH_FAKTURA__FRAKT) || col_name.equals(DB.BUH_FAKTURA__EXP_AVG) || col_name.equals(DB.BUH_FAKTURA__CURRENCY_RATE_A)) {
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
            if (Validator.validateDate(jli,true)) {
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
        String date_new = GP_BUH.get_date_time_plus_some_time_in_days(date, value);
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
        if (ie.getStateChange() != 1) {
            return;
        }
        //
        if (col_name.equals(DB.BUH_FAKTURA__BETAL_VILKOR)) {
            //
            forfalloDatumAutoChange(ti);
            //
            //[#FAKTURAKUND-RELATED-SAVE-RESTORE-JCOMBO#][SAVE][DB.BUH_FAKTURA__BETAL_VILKOR]
            IO.writeToFile(IO.get_universal(DB.BUH_FAKTURA__BETAL_VILKOR, getFakturaKundId()), jli.getValue());
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__LEV_VILKOR)) {
            //[#FAKTURAKUND-RELATED-SAVE-RESTORE-JCOMBO#][SAVE][DB.BUH_FAKTURA__LEV_VILKOR]
            JComboBoxInvert boxInvert = (JComboBoxInvert) jli;
            IO.writeToFile(IO.get_universal(DB.BUH_FAKTURA__LEV_VILKOR, getFakturaKundId()), boxInvert.getValue(1, 2)); // jli.getValue() -> does not fit here
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__LEV_SATT)) {
            //[#FAKTURAKUND-RELATED-SAVE-RESTORE-JCOMBO#][SAVE][DB.BUH_FAKTURA__LEV_SATT]
            JComboBoxInvert boxInvert = (JComboBoxInvert) jli;
            IO.writeToFile(IO.get_universal(DB.BUH_FAKTURA__LEV_SATT, getFakturaKundId()), boxInvert.getValue(1, 2)); // jli.getValue() -> does not fit here
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__MAKULERAD)) {
            //
            System.out.println("FAKTURA MAKULERAD");
            //
        } else if (col_name.equals(DB.BUH_FAKTURA_ARTIKEL___ID)) {
            //[#ARTICLE-KATEGORI-CONDITION#]
            Validator.validateJComboInput((JComboBox) ie.getSource()); // OBS! JCombo input validation
            //
            String artikelId = jli.getValue(); // verified, yes it's the artikelId
            HashMap<String, String> map = getFakturaArtikelData(DB.PHP_FUNC_PARAM_GET_ONE_FAKTURA_ARTICLE_ALL_DATA, artikelId);
            //
            setArticlePrise__and_other(true, map);
            //
        } else if (col_name.equals(DB.BUH_FAKTURA_KUND__ID)) {
            //
            Validator.validateJComboInput((JComboBox) ie.getSource()); // OBS! JCombo input validation
            //
            //===================
            //[#EUR-SEK#]
            String fakturaKundId = jli.getValue();
            HashMap<String, String> fakturaKundMap = getFakturaKundData_b(DB.PHP_FUNC_PARAM_GET_ONE_FAKTURA_KUND_ALL_DATA, fakturaKundId);
            //
            if (fakturaKundMap != null) {
                //[#KUND-KATEGORI-CONDITION#]
                HelpBuh.defineForeignCustomers(fakturaKundMap.get(DB.BUH_FAKTURA_KUND___KATEGORI));//bim.getFakturaKundKategori()
                //
                if (HelpBuh.FOREIGN_CUSTOMER) {
                    //OBS! Setting MOMS/VAT = 0%
                    // Because of some reason i had it disabled under some period of time and then enabled it again [2021-09-09]
                    setMomsSats_tableInvert(0);
                }
                //
            }
            //
            hideField_IF_NOT_ForeignCustomer(TABLE_INVERT_3, DB.BUH_FAKTURA__CURRENCY_RATE_A); //[#EUR-SEK#]
            //
            //====================
            //
            //[#SHOW-HIDE-RUT--IS-PESRON#]
            hideFieldIfNotPerson_b(TABLE_INVERT_3, DB.BUH_FAKTURA__RUT);
            //
            hideFieldIfPerson_OR_ForeignCustomer(TABLE_INVERT_2, DB.BUH_F_ARTIKEL__OMVANT_SKATT);
            //
            //
            restore_fakturaKund_related__jcombo_only(ti, DB.BUH_FAKTURA__BETAL_VILKOR, "30", false);
            restore_fakturaKund_related__jcombo_only(ti, DB.BUH_FAKTURA__LEV_VILKOR, "Fritt vårt lager", true);
            restore_fakturaKund_related__jcombo_only(ti, DB.BUH_FAKTURA__LEV_SATT, "Post", true);
            //
            String er_referens_last = IO.loadLastEntered(IO.getErReferens(jli.getValue()), ""); // getFakturaKundId() 
            setValueTableInvert(DB.BUH_FAKTURA__ER_REFERENS, ti, er_referens_last);
            //
        } else if (col_name.equals(DB.BUH_F_ARTIKEL__MOMS_SATS)) {
            //
//            String omvant = getValueTableInvert(DB.BUH_F_ARTIKEL__OMVANT_SKATT, ti);
//            //
//            if (omvant.equals("0")) {
//                IO.writeToFile(DB.BUH_F_ARTIKEL__MOMS_SATS, jli.getValue());
//            }
            //
        } else if (col_name.equals(DB.BUH_F_ARTIKEL__OMVANT_SKATT)) {
            //
            String omvant = jli.getValue();
            //
            ColumnDataEntryInvert cde = getColumnDataEntryInvert(DB.BUH_F_ARTIKEL__MOMS_SATS, ti);
            JComboBox box = (JComboBox) cde.getObject();
            //
            if (omvant.equals("1")) { // Means using omvänt skattskyldighet
//                setValueTableInvert(DB.BUH_F_ARTIKEL__MOMS_SATS, ti, new HelpA.ComboBoxObject("0%", "", "", ""));
                setMomsSats_tableInvert(0);
                box.setEnabled(false);
            } else if (omvant.equals("0")) {
                setMomsSats_tableInvert(25);
//                setValueTableInvert(DB.BUH_F_ARTIKEL__MOMS_SATS, ti, new HelpA.ComboBoxObject("25%", "", "", ""));
                box.setEnabled(true);
            }
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__RUT)) {
            //
            String rutavdrag = jli.getValue();
            JComboBox box = (JComboBox) jli;
            //
            if (ie.getStateChange() != 1) {
                return;
            }
            //
            if (rutavdrag.equals("1") && HelpA.isEmtyJTable(getArticlesTable())) {
                HelpA.showNotification(LANG.MSG_25);
                resetRutComboBox(box);
                return;
            }
            //
            if (rutavdrag.equals("1") && HelpA.isEmtyJTable(getArticlesTable()) == false) {
                if (HelpA.confirm(LANG.MSG_25_1) == false) {
                    resetRutComboBox(box);
                    return;
                }
            }
            //
            if (rutavdrag.equals("1")) {
                //
                if (rutRotFrame == null) {
                    rutRotFrame = new RutRotFrame(bim, bim.jTable_InvoiceA_Insert_articles, this);
                } else {
                    rutRotFrame.makeVisible();
                }
                //
                RUT_ROT__ENABLED = true;
                //
            } else {
                RUT_ROT__ENABLED = false;
            }
            //
        }

    }

    /**
     * "jcombo_only" means for the values stored in "jcombos" like "betal
     * vilkor", "leverans sätt" ....
     *
     * @param ti
     * @param DB__PARAMETER
     * @param default_
     */
    private void restore_fakturaKund_related__jcombo_only(TableInvert ti, String DB__PARAMETER, String default_, boolean boxObject) {
        //[#FAKTURAKUND-RELATED-SAVE-RESTORE-JCOMBO#][RESTORE]
        String fileName = IO.get_universal(DB__PARAMETER, getFakturaKundId());
        String lastEntered = IO.loadLastEntered(fileName, null);
        //
        if (lastEntered != null && boxObject) { // HelpA.ComboBoxObject
            setValueTableInvert(DB__PARAMETER, ti, new HelpA.ComboBoxObject(lastEntered, "", "", ""));
        }
        //
        if (lastEntered == null && boxObject) {// HelpA.ComboBoxObject
            setValueTableInvert(DB__PARAMETER, ti, new HelpA.ComboBoxObject(default_, "", "", ""));
        }
        //
        //
        if (lastEntered != null && boxObject == false) { //String
            setValueTableInvert(DB__PARAMETER, ti, lastEntered);
        }
        //
        if (lastEntered == null && boxObject == false) { //String
            setValueTableInvert(DB__PARAMETER, ti, default_);
        }
        //
    }

    private void resetRutComboBox(JComboBox box) {
        box.setSelectedItem(new HelpA.ComboBoxObject("Nej", "0", "", ""));
    }

    private void setArticlePrise__and_other(boolean force, HashMap<String, String> fakturaArticleMap) {
        //[#ARTICLE-KATEGORI-CONDITION#]
        boolean momsSet = false;
        //
        Map<String, Integer> map = new HashMap<String, Integer>() {
            {
                put(DB.MOMS_0, 0);
                put(DB.MOMS_6, 6);
                put(DB.MOMS_12, 12);
            }
        };
        //
        if (fakturaArticleMap != null) {
            //
            String articleKategori = fakturaArticleMap.get(DB.BUH_FAKTURA_ARTIKEL___KATEGORI);
            //
            if (articleKategori.contains("MOMS")) {
                setMomsSats_tableInvert(map.get(articleKategori));
                momsSet = true;
            }
            //
        }
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
                //
                if (pris == null || pris.equals("null")) { //[2021-09-03] Bug fix -> This was needed when you created a new Invoice_ added an empty article "-", then you tried to edit it. But the price became "null"
                    return;
                }
                //
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
                setValueTableInvert(DB.BUH_F_ARTIKEL__OMVANT_SKATT, TABLE_INVERT_2, new HelpA.ComboBoxObject("Nej", "", "", ""));
//                setValueTableInvert(DB.BUH_F_ARTIKEL__MOMS_SATS, TABLE_INVERT_2, new HelpA.ComboBoxObject("25%", "", "", ""));
                if (momsSet == false && HelpBuh.FOREIGN_CUSTOMER == false) {
                    setMomsSats_tableInvert(25);
                } else if (momsSet == false && HelpBuh.FOREIGN_CUSTOMER == true) {
                    setMomsSats_tableInvert(0);
                }

            }
            //
        }
        //
    }

    private void setMomsSats_tableInvert(int momsToSet) {
        // For the foreign customers moms shall be 0% by default
        if (HelpBuh.FOREIGN_CUSTOMER && momsToSet == 25) {
            return;
        }
        //
        String momssats = momsToSet + "%";
        //
        setValueTableInvert(DB.BUH_F_ARTIKEL__MOMS_SATS, TABLE_INVERT_2, new HelpA.ComboBoxObject(momssats, "", "", ""));
        //
    }

    /**
     *
     * @return
     */
    protected String getActualFakturaKundId() {
        return getValueTableInvert(DB.BUH_FAKTURA_KUND__ID, TABLE_INVERT);
    }

    /**
     * [#SHOW-HIDE-RUT--IS-PESRON#]
     *
     * @param rdi
     * @param rut
     */
    protected void hideFieldIfNotPerson(RowDataInvert rdi) {
        //
        String fakturaKundId = getActualFakturaKundId();
        //
        if (bim.isPerson(fakturaKundId) == false) {
            rdi.setVisible_(false);
        }
        //
    }

    /**
     * [#SHOW-HIDE-RUT--IS-PESRON#] [#SHOW-HIDE--IF#]
     *
     * @param ti
     * @param colName
     */
    protected void hideFieldIfNotPerson_b(Table ti, String colName) {
        //
        TableInvert table = (TableInvert) ti;
        TableRowInvert tri = (TableRowInvert) table.getRowByColName(colName);
        RowDataInvert rdi = tri.getRowConfig();
        //
        String fakturaKundId = getActualFakturaKundId();
        //
        if (bim.isPerson(fakturaKundId) == false) {
            rdi.setVisible_(false);
            refreshTableInvert(ti);
        } else {
            rdi.setVisible_(true);
            refreshTableInvert(ti);
        }
        //
    }

//    protected void hideField_IF_NOT_ForeignCustomer(RowDataInvert rdi) {
//        //
//        if (HelpBuh.FOREIGN_CUSTOMER == false) {//HelpBuh.COMPANY_MIXCONT == false || (HelpBuh.COMPANY_MIXCONT && HelpBuh.FOREIGN_CUSTOMER == false)
//            rdi.setVisible_(false);
//        }
//        //
//    }
    protected void hideField_IF_NOT_ForeignCustomer(Table ti, String colName) {
        //
        TableInvert table = (TableInvert) ti;
        TableRowInvert tri = (TableRowInvert) table.getRowByColName(colName);
        RowDataInvert rdi = tri.getRowConfig();
        //
        if (HelpBuh.FOREIGN_CUSTOMER == false) {//HelpBuh.COMPANY_MIXCONT == false || (HelpBuh.COMPANY_MIXCONT && HelpBuh.FOREIGN_CUSTOMER == false)
            rdi.setVisible_(false);
            refreshTableInvert(ti);
        } else {
            rdi.setVisible_(true);
            refreshTableInvert(ti);
        }
        //
    }

    /**
     * [#SHOW-HIDE--IF#]
     *
     * @param rdi
     */
    protected void hideFieldIfPerson(RowDataInvert rdi) {
        //
        String fakturaKundId = getActualFakturaKundId();
        //
        if (bim.isPerson(fakturaKundId)) {
            rdi.setVisible_(false);
        }
        //
    }

    /**
     * [#SHOW-HIDE--IF#]
     *
     * @param ti
     * @param colName
     */
    protected void hideFieldIfPerson_OR_ForeignCustomer(Table ti, String colName) {
        //
        TableInvert table = (TableInvert) ti;
        TableRowInvert tri = (TableRowInvert) table.getRowByColName(colName);
        RowDataInvert rdi = tri.getRowConfig();
        //
        String fakturaKundId = getActualFakturaKundId();
        //
        if (bim.isPerson(fakturaKundId) || HelpBuh.FOREIGN_CUSTOMER) {
            rdi.setVisible_(false);
            refreshTableInvert(ti);
        } else {
            rdi.setVisible_(true);
            refreshTableInvert(ti);
        }
    }

}
