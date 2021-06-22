/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.BlinkThread;
import MyObjectTableInvert.JTextAreaJLink;
import BuhInvoice.sec.LANG;
import BuhInvoice.sec.RutRot;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableInvert;
import forall.HelpA;
import icons.ICON;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KOCMOC
 */
public class InvoiceB extends Basic_Buh {

    //
    public static String TABLE_ALL_INVOICES__FAKTURA_ID = "ID";
    public static String TABLE_ALL_INVOICES__KUND = "KUND";
    public static String TABLE_ALL_INVOICES__FAKTURANR = "FAKTURANR";
    public static String TABLE_ALL_INVOICES__FAKTURANR_ALT = "ALTERNATIV FAKTURANR";
    public static String TABLE_ALL_INVOICES__FAKTURA_TYP = "FAKTURATYP";
    public static String TABLE_ALL_INVOICES__KUND_ID = "KUND ID";
    public static String TABLE_ALL_INVOICES__KUND_NR = "KUND NR";
    public static String TABLE_ALL_INVOICES__DATUM = "FAKTURADATUM";
    public static String TABLE_ALL_INVOICES__FORFALLODATUM = "FÖRFALLODATUM";
    public static String TABLE_ALL_INVOICES__VAR_REF = "VAR REF";
    public static String TABLE_ALL_INVOICES__ER_REF = "ER REF";
    public static String TABLE_ALL_INVOICES__ERT_ORDER = "BESTÄLLNING / NOTIS";
    public static String TABLE_ALL_INVOICES__BET_VILKOR = "B VILKOR";
    public static String TABLE_ALL_INVOICES__LEV_VILKOR = "LEV VILKOR";
    public static String TABLE_ALL_INVOICES__LEV_SATT = "LEV SATT";
    public static String TABLE_ALL_INVOICES__IMPORTANT_KOMMENT = "IKOMM";
    //
    public static String TABLE_ALL_INVOICES__TOTAL_INKL_MOMS = "TOTAL";
    public static String TABLE_ALL_INVOICES__RABATT_TOTAL_KR = "RABATT";
    public static String TABLE_ALL_INVOICES__EXKL_MOMS = "EXKL MOMS";
    public static String TABLE_ALL_INVOICES__MOMS = "MOMS";
    public static String TABLE_ALL_INVOICES__FRAKT = "FRAKT";
    public static String TABLE_ALL_INVOICES__EXP_AVG = "EXP. AVGIFT";
    public static String TABLE_ALL_INVOICES__DROJSMALSRANTA = "DRÖJSMÅLSRÄNTA %";
    public static String TABLE_ALL_INVOICES__MAKULERAD = "MAKULERAD";
    public static String TABLE_ALL_INVOICES__RUT = "RUT ROT AVDRAG";
    public static String TABLE_ALL_INVOICES__OMVAND_SKATTSKYLDIGHET = "OMVÄND SKATTSKYLDIGHET";
    public static String TABLE_ALL_INVOICES__VALUTA = "VALUTA";
    public static String TABLE_ALL_INVOICES__BETALD = "BETALD";
    public static String TABLE_ALL_INVOICES__SKICKAD = "SKICKAD";
    public static String TABLE_ALL_INVOICES__KOMMENT_$ = "KOMMENT"; // OBS! This field will be used for diff purposes as for example like in case with kredit faktura when i save the invoice which was "krediterat"
    public static String TABLE_ALL_INVOICES__CHANGED_BY = "CHANGED BY";
    public static String TABLE_ALL_INVOICES__DATE_CREATED = "DATE CREATED";
    public static String TABLE_ALL_INVOICES__FAKTURA_KUND__IS_PERSON = "IS PERSON"; // hidden
    public static String TABLE_ALL_INVOICES__COPIED_FROM_ID = "COPIED FROM ID"; // hidden //[#KREDIT-RUT#]
    public static String TABLE_ALL_INVOICES__OMVANT_SKATT = "OMVANT SKATT"; // hidden //[#INVOICE-HAS-OMVAND-SKATT#]
    public static String TABLE_ALL_INVOICES__UTSKRIVEN = "PRINT"; //[#IS_PRINTED#]
    //OBS! Search for [#ADD-TO-MAIN-TABLE#]
    //
    //
    public static String TABLE_INVOICE_ARTIKLES__FAKTURA_ID = "FAKTURA ID";
    public static String TABLE_INVOICE_ARTIKLES__ID = "ID";
    public static String TABLE_INVOICE_ARTIKLES__ARTIKEL_ID = "ARTIKEL ID"; // hidden
    public static String TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN = "ARTIKEL";
    public final static String TABLE_INVOICE_ARTIKLES__KOMMENT = "BESKRIVNING";
    public static String TABLE_INVOICE_ARTIKLES__ANTAL = "ANTAL";
    public static String TABLE_INVOICE_ARTIKLES__ENHET = "ENHET";
    public static String TABLE_INVOICE_ARTIKLES__PRIS = "PRIS";
    public static String TABLE_INVOICE_ARTIKLES__RABATT = "RABATT %";
    public static String TABLE_INVOICE_ARTIKLES__RABATT_KR = "RABATT KR";
    public static String TABLE_INVOICE_ARTIKLES__MOMS_SATS = "MOMS %";
    public static String TABLE_INVOICE_ARTIKLES__OMVAND_SKATT = "OMVÄND SKATTSKYLDIGHET";

    public static final HashMap<String, String> ARTICLES_TABLE_DICT = new HashMap<>();

    static {
        ARTICLES_TABLE_DICT.put(TABLE_INVOICE_ARTIKLES__KOMMENT, DB.BUH_F_ARTIKEL__KOMMENT);
        ARTICLES_TABLE_DICT.put(TABLE_INVOICE_ARTIKLES__RABATT, DB.BUH_F_ARTIKEL__RABATT);
        ARTICLES_TABLE_DICT.put(TABLE_INVOICE_ARTIKLES__RABATT_KR, DB.BUH_F_ARTIKEL__RABATT_KR);
        ARTICLES_TABLE_DICT.put(TABLE_INVOICE_ARTIKLES__MOMS_SATS, DB.BUH_F_ARTIKEL__MOMS_SATS);
        ARTICLES_TABLE_DICT.put(TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN, DB.BUH_FAKTURA_ARTIKEL___NAMN);
        ARTICLES_TABLE_DICT.put(TABLE_INVOICE_ARTIKLES__PRIS, DB.BUH_F_ARTIKEL__PRIS);
        ARTICLES_TABLE_DICT.put(TABLE_INVOICE_ARTIKLES__ANTAL, DB.BUH_F_ARTIKEL__ANTAL);
        ARTICLES_TABLE_DICT.put(TABLE_INVOICE_ARTIKLES__ENHET, DB.BUH_F_ARTIKEL__ENHET);
        ARTICLES_TABLE_DICT.put(TABLE_INVOICE_ARTIKLES__OMVAND_SKATT, DB.BUH_F_ARTIKEL__OMVANT_SKATT);
    }

    public InvoiceB(LAFakturering buh_invoice_main) {
        super(buh_invoice_main);
    }

    @Override
    protected void startUp() {
        //#THREAD#
        fillJTableheader();
        hideColumnsFakturaTable(bim.jTable_invoiceB_alla_fakturor);
        refresh(null); // this is used instead of fillFakturaTable
        //
        java.awt.EventQueue.invokeLater(() -> {
            fillJComboSearchByFakturaKund();
        });
        //
    }

    protected void fillJComboSearchByFakturaKund() {
        //
        String fixedComboValues_a = requestJComboValuesHttp(DB.PHP_FUNC_PARAM__GET_KUNDER, new String[]{DB.BUH_FAKTURA_KUND___NAMN, DB.BUH_FAKTURA_KUND__ID});
        HelpA.ComboBoxObject[] boxObjects = HelpA.extract_comma_separated_objects(fixedComboValues_a, 2);
        //
        if (boxObjects != null) {
            HelpA.fillComboBox(bim.jComboBox_faktura_kunder_filter, boxObjects, null);
        }
        //
        if (bim.jComboBox_faktura_kunder_filter.getItemCount() == 0) {
            bim.jComboBox_faktura_kunder_filter.setVisible(false);
            bim.jButton_search_by_kund.setVisible(false);
        }
        //
    }

    protected boolean noCustomersPresent() {
        try {
            String customers = requestJComboValuesHttp(DB.PHP_FUNC_PARAM__GET_KUNDER, new String[]{DB.BUH_FAKTURA_KUND___NAMN, DB.BUH_FAKTURA_KUND__ID});
            return customers.isEmpty();
        } catch (Exception ex) {
            return false;
        }
    }

    private void checkIfForfallnaFakturorExist() {
        //
        JTable table = bim.jTable_invoiceB_alla_fakturor;
        JButton button = bim.jButton_send_reminder;
        int forfallna = GP_BUH.countForfallnaFakturorJTable(table, TABLE_ALL_INVOICES__FORFALLODATUM);
        //
        if (forfallna > 0) {
//            button.setIcon(ICON.getImageIcon("bell_b.png", 32, 32));
            GP_BUH.reminder_btn_adjustment(button, true);
        } else {
//            button.setIcon(ICON.getImageIcon("bell.png", 32, 32));
            GP_BUH.reminder_btn_adjustment(button, false);
        }
        //
        button.setToolTipText(LANG.TOOL_TIP_1(forfallna));
        //
    }

    protected void refresh_sync(String fakturaKundId) {
        //#THREAD#
        fillFakturaTable(fakturaKundId);
        checkIfForfallnaFakturorExist();
        HelpA.markFirstRowJtable(bim.jTable_invoiceB_alla_fakturor);
        String fakturaId = bim.getFakturaId();
        all_invoices_table_clicked(fakturaId);
        //
    }

    protected void refresh(String fakturaKundId) {
        //#THREAD#
        Thread x = new Thread(() -> {
            fillFakturaTable(fakturaKundId);
            checkIfForfallnaFakturorExist();
            HelpA.markFirstRowJtable(bim.jTable_invoiceB_alla_fakturor);
            String fakturaId = bim.getFakturaId();
            all_invoices_table_clicked(fakturaId);
        });
        //
        x.start();
        //
    }

    /**
     * Think twice when using "refresh_c" because it downloads entire data each
     * time
     */
    protected void refresh_c() {
        //#THREAD#
        Thread x = new Thread(() -> {
            //
            String fakturaNr = bim.getFakturaNr();// OBS! Important before "fill"
            //
            if (fakturaNr.isEmpty()) {
                return;
            }
            //
            fillFakturaTable(null);
            checkIfForfallnaFakturorExist();
            //
            HelpA.markRowByValue(bim.jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__FAKTURANR, fakturaNr);
            String fakturaId = bim.getFakturaId();
            all_invoices_table_clicked(fakturaId);

        });
        //
        x.start();
    }

    /**
     * [2020-08-12] This one keeps the "marking line" on the same row as before
     * the refresh
     *
     * @deprecated
     */
    private void refresh_b() {
        JTable table = bim.jTable_invoiceB_alla_fakturor;
        int row = table.getSelectedRow();
        fillFakturaTable(null);
        HelpA.markGivenRow(bim.jTable_invoiceB_alla_fakturor, row);
        String fakturaId = bim.getFakturaId();
        all_invoices_table_clicked(fakturaId);
    }

    protected void displayArticlesCount() {
        int articlesCount = bim.getInvoiceArticleCount();
        bim.jLabel_ammount_of_articles_.setText("<html><div style='margin-left:5px'>" + articlesCount + "</div></html>");
    }

    protected void resetArticlesCount() {
        bim.jLabel_ammount_of_articles_.setText("");
    }

    private void fillJTableheader() {
        //
        //
        JTable table = this.bim.jTable_invoiceB_alla_fakturor;
        //        
        // "ID" -> "buh_faktura.fakturaId"
        //[#ADD-TO-MAIN-TABLE#]
        String[] headers = {
            TABLE_ALL_INVOICES__FAKTURA_ID,
            TABLE_ALL_INVOICES__KUND_ID,
            TABLE_ALL_INVOICES__KUND_NR,
            TABLE_ALL_INVOICES__VAR_REF,
            TABLE_ALL_INVOICES__ER_REF,
            TABLE_ALL_INVOICES__ERT_ORDER,
            TABLE_ALL_INVOICES__BET_VILKOR,
            TABLE_ALL_INVOICES__LEV_VILKOR,
            TABLE_ALL_INVOICES__LEV_SATT,
            TABLE_ALL_INVOICES__FRAKT,
            TABLE_ALL_INVOICES__EXP_AVG,
            TABLE_ALL_INVOICES__DROJSMALSRANTA,
            //
            TABLE_ALL_INVOICES__FAKTURANR,
            TABLE_ALL_INVOICES__FAKTURANR_ALT,
            TABLE_ALL_INVOICES__FAKTURA_TYP,
            TABLE_ALL_INVOICES__KUND,
            TABLE_ALL_INVOICES__DATUM,
            TABLE_ALL_INVOICES__FORFALLODATUM,
            TABLE_ALL_INVOICES__EXKL_MOMS,
            TABLE_ALL_INVOICES__TOTAL_INKL_MOMS,
            TABLE_ALL_INVOICES__RABATT_TOTAL_KR,
            TABLE_ALL_INVOICES__MOMS,
            TABLE_ALL_INVOICES__VALUTA,
            TABLE_ALL_INVOICES__MAKULERAD,
            TABLE_ALL_INVOICES__RUT,
            TABLE_ALL_INVOICES__BETALD,
            TABLE_ALL_INVOICES__SKICKAD,
            TABLE_ALL_INVOICES__IMPORTANT_KOMMENT,
            TABLE_ALL_INVOICES__KOMMENT_$,
            TABLE_ALL_INVOICES__CHANGED_BY,
            TABLE_ALL_INVOICES__DATE_CREATED,
            TABLE_ALL_INVOICES__FAKTURA_KUND__IS_PERSON,
            TABLE_ALL_INVOICES__COPIED_FROM_ID,
            TABLE_ALL_INVOICES__OMVANT_SKATT,
            TABLE_ALL_INVOICES__UTSKRIVEN
        };
        //
        table.setModel(new DefaultTableModel(null, headers));
        //
//        System.out.println("" + table.getFont());
        //
//        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        //
        //
        //
        JTable table_b = this.bim.jTable_invoiceB_faktura_artiklar;
        //
        String[] headers_b = {
            TABLE_INVOICE_ARTIKLES__ID, // hidden
            TABLE_INVOICE_ARTIKLES__FAKTURA_ID,
            TABLE_INVOICE_ARTIKLES__ARTIKEL_ID,
            TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN,
            TABLE_INVOICE_ARTIKLES__KOMMENT,
            TABLE_INVOICE_ARTIKLES__ANTAL,
            TABLE_INVOICE_ARTIKLES__ENHET,
            TABLE_INVOICE_ARTIKLES__PRIS,
            TABLE_INVOICE_ARTIKLES__RABATT,
            TABLE_INVOICE_ARTIKLES__RABATT_KR,
            TABLE_INVOICE_ARTIKLES__MOMS_SATS,
            TABLE_INVOICE_ARTIKLES__OMVAND_SKATT
        };
        //
        table_b.setModel(new DefaultTableModel(null, headers_b));
        //
    }

    protected void all_invoices_table_clicked(String fakturaId) {
        //
        fillFakturaArticlesTable(fakturaId);
        //
        showImportantKomment();
        //
        showLowPriorityKomment();
        //
        showOtherFakturaInfo();
        //
        showInfoIcons();
        //
        bim.hideShowButtonsDependingOnConditions();
    }

    private void showInfoIcons() {
        //[#ADD-TO-MAIN-TABLE#][#A-BIT-OFF-TOPIC#]
        bim.jLabel_info__forfallen.setVisible(false);
        bim.jLabel_info_is_person.setVisible(false);
        bim.jLabel_info_rut__or_omvant_skatt.setVisible(false);
        bim.jLabel_info__printed.setVisible(false);
        bim.jLabel_info__sent.setVisible(false);
        bim.jLabel_info__betald.setVisible(false);
        bim.jLabel_info__makulerad.setVisible(false);
        bim.jLabel_info__kredit_faktura.setVisible(false);
        bim.jLabel_info__kontant_faktura.setVisible(false);
        bim.jLabel_info__offert.setVisible(false);
        //
        boolean forfallen = bim.isForfallen();
        boolean is_person = bim.isPerson();
        boolean rut = bim.isRUT();
        boolean makulerad = bim.isMakulerad();
        boolean skickad = bim.isSent();
        boolean isKreditFaktura = bim.isKreditFaktura();
        boolean isKontantFaktura = bim.isKontantFaktura();
        boolean betald = bim.isBetald();
        boolean printed = bim.isPrinted();
        boolean isOffert = bim.isOffert();
        boolean isOmvantSkatt = bim.isOmvantSkatt();
        //
        if (forfallen && bim.isKreditFaktura() == false && isOffert == false && bim.isKontantFaktura() == false && makulerad == false) {
            bim.jLabel_info__forfallen.setVisible(true);
        }
        if (is_person && makulerad == false) {
            bim.jLabel_info_is_person.setVisible(true);
        }
        if (rut && makulerad == false) {
            GP_BUH.setLabelIconAndToolTip(bim.jLabel_info_rut__or_omvant_skatt, "rut.png", "RUT / ROT-Avdrag");
            bim.jLabel_info_rut__or_omvant_skatt.setVisible(true);
        }
        if (isOmvantSkatt && makulerad == false) {
            GP_BUH.setLabelIconAndToolTip(bim.jLabel_info_rut__or_omvant_skatt, "swap.png", "Omvänd moms");
            bim.jLabel_info_rut__or_omvant_skatt.setVisible(true);
        }
        if (printed && makulerad == false) {
            bim.jLabel_info__printed.setVisible(true);
        }
        if (skickad && makulerad == false) {
            bim.jLabel_info__sent.setVisible(true);
        }
        if (betald) {
            bim.jLabel_info__betald.setVisible(true);
        }
        if (makulerad) {
            bim.jLabel_info__makulerad.setVisible(true);
        }
        if (isKreditFaktura) {
            bim.jLabel_info__kredit_faktura.setVisible(true);
        }
        if (isKontantFaktura) {
            bim.jLabel_info__kontant_faktura.setVisible(true);
        }
        if (isOffert) {
            bim.jLabel_info__offert.setVisible(true);
        }

        //
    }

    /**
     * Not used since [2021-05-18]
     *
     * @deprecated
     * @return
     */
    private boolean isPrinted() {
        //
//        String json = bim.getSELECT_fakturaId();
        String json = bim.getSELECT_fakturaId__doubleWhere();
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM__GET_FAKTURA_SEND, json);
            //
            ArrayList<HashMap<String, String>> entries = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            //
            for (HashMap<String, String> map : entries) {
                //
                String send_status = map.get(DB.BUH_FAKTURA_SEND__SEND_OK);
                //
                if (send_status.equals(DB.STATIC__SENT_STATUS__UTSKRIVEN)) {
                    return true;
                }
                //
            }
            //
            //
        } catch (Exception ex) {
            Logger.getLogger(EditPanel_Inbet.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        return false;
    }

    private void showOtherFakturaInfo() {
        //
        bim.jLabel_faktura_changed_by__user.setText(""); // Reset
        //
        String changedBy = _get(TABLE_ALL_INVOICES__CHANGED_BY);
        String date_created = _get(TABLE_ALL_INVOICES__DATE_CREATED);
        //
        if (date_created != null && date_created.isEmpty() == false) {
            date_created = date_created.substring(0, 19).replaceAll("\\.", ":");
            setText_(LANG.LBL_MSG_7(date_created));
        }
        //
        if (changedBy != null && changedBy.isEmpty() == false) {
            setText_(LANG.LBL_MSG_8(changedBy));
        }
        //
    }

    private void setText_(String txt) {
        JLabel lbl = bim.jLabel_faktura_changed_by__user;
        String actText = lbl.getText();
        //
        if (actText == null || actText.isEmpty()) {
            lbl.setText(txt);
        } else {
            lbl.setText(actText + "          " + txt); // 10 spaces
        }
        //
    }

    private void showLowPriorityKomment() {
        //
        if (bim.jTextArea_faktura_komment.getText().isEmpty() && bim.jTable_invoiceB_alla_fakturor.getRowCount() != 0) {
            //
            String fakturannr_alt = _get(TABLE_ALL_INVOICES__FAKTURANR_ALT);
            //
            if (fakturannr_alt.equals("0") == false) {
                bim.jTextArea_faktura_komment.setText("Alternativ fakturanummer: " + fakturannr_alt);
            }
            //
        }
        //
    }

    private void showImportantKomment() {
        //
        JTable table = bim.jTable_invoiceB_alla_fakturor;
        //
        String komment = HelpA.getValueSelectedRow(table, TABLE_ALL_INVOICES__IMPORTANT_KOMMENT);
        //
        bim.jTextArea_faktura_komment.setText(komment);
        //
        Validator.resetValidation(bim.jTextArea_faktura_komment);
        //
    }

    protected void updateKomment(boolean clear) {
        //
        JTextAreaJLink jtxt = (JTextAreaJLink) bim.jTextArea_faktura_komment;
        //
        if (jtxt.getValidated() == false) {
            HelpA.showNotification(LANG.MSG_8);
            return;
        }
        //
        JTable table = bim.jTable_invoiceB_alla_fakturor;
        //
        String fakturaId = bim.getFakturaId();
        //
        String important_komment;
        //
        if (clear) {
            important_komment = "";
            jtxt.setText("");
        } else {
            important_komment = bim.jTextArea_faktura_komment.getText();
            important_komment = GP_BUH.replaceColon(important_komment, false);
        }
        //
        HashMap<String, String> update_map = bim.getUPDATE(DB.BUH_FAKTURA__ID__, fakturaId, DB.TABLE__BUH_FAKTURA);
        //
        update_map.put(DB.BUH_FAKTURA__IMPORTANT_KOMMENT, important_komment);
        //
        String json = JSon.hashMapToJSON(update_map);
        //
        HelpBuh.update(json);
        //
        // OBS! This is done to skip refreshing the entire faktura list
        HelpA.setValueCurrentRow(table, TABLE_ALL_INVOICES__IMPORTANT_KOMMENT, important_komment);
        //
        //
        if (clear) {
            BlinkThread bt = new BlinkThread(jtxt, true);
        } else {
            BlinkThread bt = new BlinkThread(jtxt, false);
        }
        //
    }

    protected void deleteComment() {
        updateKomment(true);
    }

    private void fillFakturaTable(String fakturaKundId) {
        //
        JTable table = bim.jTable_invoiceB_alla_fakturor;
        //
        HelpA.clearAllRowsJTable(table);
        //
        HelpA.rowsorter_jtable_add_reset(table);
        //
        table.setDefaultRenderer(Object.class, GP_BUH.getRendererForfalloDatum());
        //
        String json;
        //
        if (fakturaKundId != null) {
            json = bim.getSELECT_fakturaKundId__doubleWhere(fakturaKundId);
        } else {
            json = bim.getSELECT_kundId();
        }
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    bim.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FILTER, json);
            //
            //=======
            //
            //
            if (bim.isInitialFilter()) {
                bim.untoggleAll();
            }
            //
            //=======
            //
            ArrayList<HashMap<String, String>> invoices = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            //
            //[#NYCKEL-TAL#]
            countNyckelTal(invoices, bim.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FILTER);
            //
            //
            bim.RESET_SEARCH_FILTER(); //***************
            //
            //
            for (HashMap<String, String> invoice_map : invoices) {
                addRowJtable_all_invoices(invoice_map, table);
            }
            //
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        hideColumnsFakturaTable(table);
        //
    }

    public static int NYCKEL_TAL__ANTAL_FAKTUROR_SAMTLIGA = 0;
    public static double NYCKEL_TAL__ING_MOMS_TOTAL = 0;
    public static double NYCKEL_TAL__TOTAL_INKL_MOMS = 0;
    public static double NYCKEL_TAL__TOTAL_EXKL_MOMS = 0;

    private void countNyckelTal(ArrayList<HashMap<String, String>> invoices, String filter) {
        //
        NYCKEL_TAL__ANTAL_FAKTUROR_SAMTLIGA = 0;
        NYCKEL_TAL__ING_MOMS_TOTAL = 0;
        NYCKEL_TAL__TOTAL_INKL_MOMS = 0;
        NYCKEL_TAL__TOTAL_EXKL_MOMS = 0;
        //
        String start_of_year_date = HelpA.get_act_year_first_date();
        //
        for (HashMap<String, String> map : invoices) {
            //
            String fakturadatum = map.get(DB.BUH_FAKTURA__FAKTURA_DATUM);
            int makulerad = Integer.parseInt(map.get(DB.BUH_FAKTURA__MAKULERAD));
            int fakturaTyp = Integer.parseInt(map.get(DB.BUH_FAKTURA__FAKTURATYP));
            //
            //==========
            if (filter.equals(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__MAKULERAD) && makulerad == 1) {
                countNyckelTal_help(map, fakturaTyp);
            }
            if (makulerad == 1) {
                continue;
            }
            //==========
            //
            if (filter.equals(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ONE_YEAR_BACK)
                    && GP_BUH.compareDates_b(start_of_year_date, GP_BUH.DATE_FORMAT_BASIC, fakturadatum, GP_BUH.DATE_FORMAT_BASIC)) {
                //
                countNyckelTal_help(map, fakturaTyp);
                //
            } else {
                //
                countNyckelTal_help(map, fakturaTyp);
                //
            }
            //
        }
        //
        bim.jLabel__nyckel_tal__ing_moms.setText("" + GP_BUH.round_double_b(NYCKEL_TAL__ING_MOMS_TOTAL));
        bim.jLabel__nyckel_tal__antal_fakturor.setText("" + GP_BUH.round_double_b(NYCKEL_TAL__ANTAL_FAKTUROR_SAMTLIGA)); // NYCKEL_TAL__ANTAL_FAKTUROR_SAMTLIGA
        bim.jLabel__nyckel_tal__tot_inkl_moms.setText("" + GP_BUH.round_double_b(NYCKEL_TAL__TOTAL_INKL_MOMS)); // NYCKEL_TAL__TOTAL_INKL_MOMS
        bim.jLabel__nyckel_tal__tot_exkl_moms.setText("" + GP_BUH.round_double_b(NYCKEL_TAL__TOTAL_EXKL_MOMS)); // NYCKEL_TAL__TOTAL_EXKL_MOMS
        //
    }

    private void countNyckelTal_help(HashMap<String, String> map, int fakturaTyp) {
        //
        int normal = Integer.parseInt(DB.STATIC__FAKTURA_TYPE_NORMAL__NUM);
        int kredit = Integer.parseInt(DB.STATIC__FAKTURA_TYPE_KREDIT__NUM);
        int kontant = Integer.parseInt(DB.STATIC__FAKTURA_TYPE_KONTANT__NUM);
        int offert = Integer.parseInt(DB.STATIC__FAKTURA_TYPE_OFFERT__NUM);
        //
        double moms = Double.parseDouble(map.get(DB.BUH_FAKTURA__MOMS_TOTAL__));
        double inkl_moms = Double.parseDouble(map.get(DB.BUH_FAKTURA__TOTAL__));
        double exkl_moms = Double.parseDouble(map.get(DB.BUH_FAKTURA__TOTAL_EXKL_MOMS__));
        //
        if (fakturaTyp == normal || fakturaTyp == kontant) {
            NYCKEL_TAL__ING_MOMS_TOTAL += moms;
            NYCKEL_TAL__TOTAL_INKL_MOMS += inkl_moms;
            NYCKEL_TAL__TOTAL_EXKL_MOMS += exkl_moms;
        }
        //
        if (fakturaTyp == kredit) {
            NYCKEL_TAL__ING_MOMS_TOTAL -= moms;
            NYCKEL_TAL__TOTAL_INKL_MOMS -= inkl_moms;
            NYCKEL_TAL__TOTAL_EXKL_MOMS -= exkl_moms;
        }
        //
        if (fakturaTyp == offert) {
            // DO NOTHING
        }
        //
        NYCKEL_TAL__ANTAL_FAKTUROR_SAMTLIGA++;
        //
    }

    private void hideColumnsFakturaTable(JTable table) {
        //[#ADD-TO-MAIN-TABLE#]
        if (GP_BUH.CUSTOMER_MODE) {
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__FAKTURA_ID);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__KUND_ID);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__KUND_NR);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__FAKTURANR_ALT);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__ERT_ORDER);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__FRAKT);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__EXP_AVG);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__DROJSMALSRANTA);
//            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__MAKULERAD);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__VALUTA);
//            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__RABATT_TOTAL_KR);
            //
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__KUND_ID);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__VAR_REF);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__ER_REF);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__BET_VILKOR);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__LEV_VILKOR);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__LEV_SATT);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__IMPORTANT_KOMMENT);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__KOMMENT_$);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__SKICKAD);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__CHANGED_BY);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__DATE_CREATED);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__RUT);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__FAKTURA_KUND__IS_PERSON);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__COPIED_FROM_ID);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__OMVANT_SKATT);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__UTSKRIVEN);
        }
        //
    }

    private void addRowJtable_all_invoices(HashMap<String, String> map, JTable table) {
        //[#ADD-TO-MAIN-TABLE#]
        Object[] jtableRow = new Object[]{
            map.get(DB.BUH_FAKTURA__ID__),
            map.get(DB.BUH_FAKTURA_KUND__ID),
            map.get(DB.BUH_FAKTURA_KUND___KUNDNR),
            map.get(DB.BUH_FAKTURA__VAR_REFERENS),
            map.get(DB.BUH_FAKTURA__ER_REFERENS),
            map.get(DB.BUH_FAKTURA__ERT_ORDER),
            map.get(DB.BUH_FAKTURA__BETAL_VILKOR),
            map.get(DB.BUH_FAKTURA__LEV_VILKOR),
            map.get(DB.BUH_FAKTURA__LEV_SATT),
            //            map.get(DB.BUH_FAKTURA__INKL_MOMS), // deleted [2020-10-02]
            //            map.get(DB.BUH_FAKTURA__MOMS_SATS), // deleted [2020-10-02]
            map.get(DB.BUH_FAKTURA__FRAKT),
            map.get(DB.BUH_FAKTURA__EXP_AVG),
            map.get(DB.BUH_FAKTURA__DROJSMALSRANTA),
            //
            map.get(DB.BUH_FAKTURA__FAKTURANR__),
            map.get(DB.BUH_FAKTURA__FAKTURANR_ALT),
            JSon.getLongName(DB.STATIC__FAKTURA_TYPES, map.get(DB.BUH_FAKTURA__FAKTURATYP)),
            map.get(DB.BUH_FAKTURA_KUND___NAMN),
            map.get(DB.BUH_FAKTURA__FAKTURA_DATUM),
            map.get(DB.BUH_FAKTURA__FORFALLO_DATUM),
            map.get(DB.BUH_FAKTURA__TOTAL_EXKL_MOMS__),
            map.get(DB.BUH_FAKTURA__TOTAL__),
            map.get(DB.BUH_FAKTURA__RABATT_TOTAL),
            map.get(DB.BUH_FAKTURA__MOMS_TOTAL__),
            map.get(DB.BUH_FAKTURA__VALUTA),
            JSon.getLongName(DB.STATIC__JA_NEJ__EMPTY_NEJ, map.get(DB.BUH_FAKTURA__MAKULERAD)),
            JSon.getLongName(DB.STATIC__JA_NEJ__EMPTY_NEJ, map.get(DB.BUH_FAKTURA__RUT)),
            JSon.getLongName(DB.STATIC__BETAL_STATUS, map.get(DB.BUH_FAKTURA__BETALD)),
            JSon.getLongName(DB.STATIC__JA_NEJ, map.get(DB.BUH_FAKTURA__SENT)),
            map.get(DB.BUH_FAKTURA__IMPORTANT_KOMMENT),
            map.get(DB.BUH_FAKTURA__KOMMENT),
            map.get(DB.BUH_FAKTURA__CHANGED_BY),
            map.get(DB.BUH_FAKTURA__DATE_CREATED__),
            map.get(DB.BUH_FAKTURA_KUND___IS_PERSON),
            map.get(DB.BUH_FAKTURA__COPIED_FROM_ID),
            JSon.getLongName(DB.STATIC__JA_NEJ, map.get(DB.BUH_FAKTURA__OMVANT_SKATT)),
            JSon.getLongName(DB.STATIC__JA_NEJ, map.get(DB.BUH_FAKTURA__PRINTED))
        };
        //
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(jtableRow);
        //
    }

    private void fillFakturaArticlesTable(String fakturaId) {
        //
        JTable table = bim.jTable_invoiceB_faktura_artiklar;
        //
        HelpA.clearAllRowsJTable(table);
        //
        if (fakturaId == null || fakturaId.isEmpty()) {
            return;
        }
        //
//        String json = bim.getSELECT(DB.BUH_F_ARTIKEL__FAKTURAID, fakturaId);
        String json = bim.getSELECT_doubleWhere(DB.BUH_F_ARTIKEL__KUND_ID, "777", DB.BUH_F_ARTIKEL__FAKTURAID, fakturaId);
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM_GET_FAKTURA_ARTICLES, json);
            //
            //
            if (json_str_return.equals(DB.PHP_SCRIPT_RETURN_EMPTY)) { // this value='empty' is returned by PHP script
                return;
            }
            //
            //
            ArrayList<HashMap<String, String>> articles = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            bim.setArticlesMarkedInvoice(articles); //[2020-08-18]
            //
            for (HashMap<String, String> articles_map : articles) {
                addRowJtable_faktura_articles(articles_map, table);
            }
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        hideColumnsArticlesTable(table);
        //
    }

    protected void hideColumnsArticlesTable(JTable table) {
        //
        if (GP_BUH.CUSTOMER_MODE) {
            // [#HIDE-COLS-ARTICLES-TABLE#]
            HelpA.hideColumnByName(table, TABLE_INVOICE_ARTIKLES__ID);
            HelpA.hideColumnByName(table, TABLE_INVOICE_ARTIKLES__ARTIKEL_ID);
            HelpA.hideColumnByName(table, TABLE_INVOICE_ARTIKLES__FAKTURA_ID);
            HelpA.hideColumnByName(table, TABLE_INVOICE_ARTIKLES__ENHET);
            HelpA.hideColumnByName(table, TABLE_INVOICE_ARTIKLES__RABATT_KR);
            HelpA.hideColumnByName(table, TABLE_INVOICE_ARTIKLES__OMVAND_SKATT);
            //
            try {
                HelpA.setColumnWidthByName(TABLE_INVOICE_ARTIKLES__KOMMENT, table, 0.25);
                HelpA.setColumnWidthByName(TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN, table, 0.15);
            } catch (Exception ex) {

            }
            //
        }
        //
    }

    private void addRowJtable_faktura_articles(HashMap<String, String> map, JTable table) {
        //
        Object[] jtableRow = new Object[]{
            map.get(DB.BUH_F_ARTIKEL__ID),
            map.get(DB.BUH_F_ARTIKEL__FAKTURAID),
            getValueHashMap(map.get(DB.BUH_FAKTURA_ARTIKEL___ID)),
            getValueHashMap(map.get(DB.BUH_FAKTURA_ARTIKEL___NAMN)),
            map.get(DB.BUH_F_ARTIKEL__KOMMENT),
            map.get(DB.BUH_F_ARTIKEL__ANTAL),
            JSon.getLongName(DB.STATIC__ENHET, map.get(DB.BUH_F_ARTIKEL__ENHET)),
            map.get(DB.BUH_F_ARTIKEL__PRIS),
            map.get(DB.BUH_F_ARTIKEL__RABATT),
            map.get(DB.BUH_F_ARTIKEL__RABATT_KR),
            map.get(DB.BUH_F_ARTIKEL__MOMS_SATS),
            JSon.getLongName(DB.STATIC__JA_NEJ, map.get(DB.BUH_F_ARTIKEL__OMVANT_SKATT))
        };
        //
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(jtableRow);
        //
    }

    protected void deleteFakturaPrimary(String fakturaId) {
        //
        deleteFakturaArticles(fakturaId);
        //
        deleteFakturaInbetalningar(fakturaId);
        //
        deleteFakturaSend(fakturaId);
        //
        deleteFakturaRut(fakturaId);//[#RUTROT#]
        //
        deleteFaktura(fakturaId);
        //
        refresh(null);
        //
    }

    private void deleteFakturaRut(String fakturaId) {
        //
        // DELETE from "buh_faktura_rut_person"
        HashMap<String, String> map_ = bim.getDELETE(DB.BUH_FAKTURA__ID__, fakturaId, DB.TABLE__BUH_FAKTURA_RUT_PERSON);
        String json_ = JSon.hashMapToJSON(map_);
        executeDelete(json_);
        //
        //DELETE from "buh_faktura_rut"
        HashMap<String, String> map = bim.getDELETE(DB.BUH_FAKTURA__ID__, fakturaId, DB.TABLE__BUH_FAKTURA_RUT);
        String json = JSon.hashMapToJSON(map);
        executeDelete(json);
        //
    }

    private void deleteFaktura(String fakturaId) {
        //
        HashMap<String, String> map = bim.getDELETE(DB.BUH_FAKTURA__ID__, fakturaId, DB.TABLE__BUH_FAKTURA);
        //
        String json = JSon.hashMapToJSON(map);
        //
        executeDelete(json);
    }

    private void deleteFakturaInbetalningar(String fakturaId) {
        //
        HashMap<String, String> map = bim.getDELETE(DB.BUH_FAKTURA_INBET__FAKTURA_ID, fakturaId, DB.TABLE__BUH_FAKTURA_INBET);
        //
        String json = JSon.hashMapToJSON(map);
        //
        executeDelete(json);
    }

    private void deleteFakturaSend(String fakturaId) {
        //
        HashMap<String, String> map = bim.getDELETE(DB.BUH_FAKTURA_SEND__FAKTURA_ID, fakturaId, DB.TABLE__BUH_FAKTURA_SEND);
        //
        String json = JSon.hashMapToJSON(map);
        //
        executeDelete(json);
        //
    }

    private void deleteFakturaArticles(String fakturaId) {
        //
        HashMap<String, String> map = bim.getDELETE(DB.BUH_F_ARTIKEL__FAKTURAID, fakturaId, DB.TABLE__BUH_F_ARTIKEL);
        //
        String json = JSon.hashMapToJSON(map);
        //
        executeDelete(json);
    }

//    private String _get(String colNameJTable) {
//        JTable table = bim.jTable_invoiceB_alla_fakturor;
//        return HelpA.getValueSelectedRow(table, colNameJTable);
//    }
    private String _get_percent(String colNameJTable) {
        JTable table = bim.jTable_invoiceB_alla_fakturor;
        double val = Double.parseDouble(HelpA.getValueSelectedRow(table, colNameJTable));
        int rst = (int) (val * 100);
        return "" + rst;
    }

    protected void copy(boolean isKreditFaktura, boolean isOffert, boolean omvandlaOffertToFaktura) {
        //
        String fakturaId = bim.getFakturaId();
        //
        HashMap<String, String> faktura_data_map = getOneFakturaData();
        //
        faktura_data_map = JSon.removeEntriesWhereValueNullOrEmpty(faktura_data_map);
        //
        String fakturaNrCopy = bim.getFakturaNr();
        //
        // OBS! OBS! OBS! OBS! OBS!*****
        processFakturaMapCopy(faktura_data_map, fakturaNrCopy, isKreditFaktura, isOffert, omvandlaOffertToFaktura, fakturaId); // IMPORTANT! Remove/Reset some entries like "faktura datum" etc.
        //
//        String json = bim.getSELECT(DB.BUH_F_ARTIKEL__FAKTURAID, fakturaId);
        String json = bim.getSELECT_doubleWhere(DB.BUH_F_ARTIKEL__KUND_ID, "777", DB.BUH_F_ARTIKEL__FAKTURAID, fakturaId);
        //
        ArrayList<HashMap<String, String>> faktura_articles = null;
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM_GET_FAKTURA_ARTICLES, json);
            //
//            System.out.println("Articles: " + json_str_return);
            //
//            faktura_articles = JSon.phpJsonResponseToHashMap(json_str_return);
            faktura_articles = JSon.phpJsonResponseToHashMap__for_copy_and_krediting(json_str_return);
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        String fakturaId_new = copy_a__faktura_to_db(faktura_data_map);
        //
        if (GP_BUH.verifyId(fakturaId_new) && faktura_articles != null && faktura_articles.size() > 0) {
            //
            boolean ok = copy_b__faktura_articles_to_db(faktura_articles, fakturaId_new);
            //
            String newFakturaNr = faktura_data_map.get(DB.BUH_FAKTURA__FAKTURANR__);
            //
            //
            if (isOffert && omvandlaOffertToFaktura == false) { // COPY OFFERT
                //[#OFFERT#]
                HelpA.showNotification(LANG.OFFERT_COPY_MSG(fakturaNrCopy, newFakturaNr));
                //
                EditPanel_Send.insert(fakturaId, DB.STATIC__SENT_STATUS__KOPIERAD, DB.STATIC__SENT_TYPE_OFFERT);
                EditPanel_Send.insert(fakturaId_new, DB.STATIC__SENT_STATUS__SKAPAD, DB.STATIC__SENT_TYPE_OFFERT);
                //
                refresh_sync(null);
                //
            } else if (isOffert && omvandlaOffertToFaktura) { // OMVANDLA OFFERT
                //[#OFFERT#]
                HelpA.showNotification(LANG.OFFERT_OMVANDLA_MSG(fakturaNrCopy, newFakturaNr));
                //
                EditPanel_Send.insert(fakturaId, DB.STATIC__SENT_STATUS__OMVANDLAT, DB.STATIC__SENT_TYPE_OFFERT);
                EditPanel_Send.insert(fakturaId_new, DB.STATIC__SENT_STATUS__SKAPAD, DB.STATIC__SENT_TYPE_FAKTURA);
                //
                refresh_sync(null);
                //
            } else if (isKreditFaktura == false && ok) { // COPY NORMAL FAKTURA
                //
                HelpA.showNotification(LANG.FAKTURA_COPY_MSG_B(fakturaNrCopy, newFakturaNr));
                //
                EditPanel_Send.insert(fakturaId, DB.STATIC__SENT_STATUS__KOPIERAD, DB.STATIC__SENT_TYPE_FAKTURA);
                EditPanel_Send.insert(fakturaId_new, DB.STATIC__SENT_STATUS__SKAPAD, DB.STATIC__SENT_TYPE_FAKTURA);
                //
                refresh_sync(null);
                //
            } else if (isKreditFaktura && ok) { // KREDIT FAKTURA
                //
                refresh_sync(null);
                //
                bim.editFakturaBtnKlicked();
                //
                EditPanel_Send.insert(fakturaId_new, DB.STATIC__SENT_STATUS__SKAPAD, DB.STATIC__SENT_TYPE_FAKTURA);
                //
            }
            //
        }
    }

    private boolean copy_b__faktura_articles_to_db(ArrayList<HashMap<String, String>> faktura_articles, String fakturaId) {
        //
        for (HashMap<String, String> article_row_map : faktura_articles) {
            //
            article_row_map.put(DB.BUH_F_ARTIKEL__FAKTURAID, fakturaId);
            article_row_map.remove(DB.BUH_F_ARTIKEL__ID); // [IMPORTANT]
            article_row_map.remove(DB.BUH_FAKTURA_ARTIKEL___NAMN); // [IMPORTANT]
            article_row_map.remove(DB.BUH_FAKTURA_ARTIKEL___ARTNR); // [IMPORTANT]
            article_row_map = JSon.removeEntriesWhereValueNullOrEmpty(article_row_map);
            //
            article_row_map.put(DB.BUH_F_ARTIKEL__KUND_ID, "777"); // [#KUND-ID-INSERT#]
            //
        }
        //
        return Faktura_Entry_Insert.articlesToHttpDB(faktura_articles);
        //
    }

    private String copy_a__faktura_to_db(HashMap<String, String> faktura_data_map) {
        //
        String json = JSon.hashMapToJSON(faktura_data_map);
        //
        String fakturaId;
        //
        try {
            //
            fakturaId = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_FAKTURA_TO_DB, json);
            //
//            System.out.println("FAKTURA ID AQUIRED: " + fakturaId);
            //
        } catch (Exception ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
            fakturaId = "-1";
        }
        //
        return fakturaId;
        //
    }

    private void processFakturaMapCopy(HashMap<String, String> faktura_data_map, String fakturaNrCopy, boolean isKreditFaktura, boolean isOffert, boolean omvandlaOffertToFaktura, String fakturaId) {
        //
        String komment;
        //
        if (isKreditFaktura) {
            //
            faktura_data_map.remove(DB.BUH_FAKTURA__FAKTURA_DATUM);
            faktura_data_map.remove(DB.BUH_FAKTURA__FORFALLO_DATUM);
            //
            faktura_data_map.put(DB.BUH_FAKTURA__FAKTURATYP, DB.STATIC__FAKTURA_TYPE_KREDIT__NUM); // 1 = KREDIT FAKTURA
            faktura_data_map.put(DB.BUH_FAKTURA__BETALD, "4"); // 4 = KREDIT FAKTURA -> shows "-" in table
            faktura_data_map.put(DB.BUH_FAKTURA__KOMMENT, fakturaNrCopy); // Saving the "krediterad" invoice
            //
            komment = "Krediterar fakturanummer# " + fakturaNrCopy; // "#" for ":" [2020-09-14]
            //
        } else if (isOffert && omvandlaOffertToFaktura) {
            //[#OFFERT#]
            faktura_data_map = setForfalloDatumCopy(faktura_data_map, bim.getBetalVilkor());
            faktura_data_map.put(DB.BUH_FAKTURA__FAKTURATYP, "0"); // set to STATIC__FAKTURA_TYPE_NORMAL__NUM
            //
            komment = "Skapad från offertummer# " + fakturaNrCopy;
            //
        } else if (bim.isKontantFaktura()) {
            //
            faktura_data_map.remove(DB.BUH_FAKTURA__FAKTURA_DATUM);
            faktura_data_map.remove(DB.BUH_FAKTURA__FORFALLO_DATUM);
            //
            faktura_data_map.put(DB.BUH_FAKTURA__BETALD, "0");
            komment = "Kopierad från fakturanummer# " + fakturaNrCopy;
            //
        } else { // Normal faktura
            komment = "Kopierad från fakturanummer# " + fakturaNrCopy; // "#" for ":" [2020-09-14]
            faktura_data_map.remove(DB.BUH_FAKTURA__BETALD);
            faktura_data_map.remove(DB.BUH_FAKTURA__ERT_ORDER);
            faktura_data_map.remove(DB.BUH_FAKTURA__RUT); // I have decided to not allow to copy the "RUT" related data when a common invoice is copied
            faktura_data_map = setForfalloDatumCopy(faktura_data_map, bim.getBetalVilkor());
        }
        //
        faktura_data_map.put(DB.BUH_FAKTURA__CHANGED_BY, GP_BUH.getChangedBy()); // [2020-10-28]
        //
        faktura_data_map.put(DB.BUH_FAKTURA__KUNDID__, "777"); // [#KUND-ID-INSERT#] [2020-10-26] OBS! The value sent does not have any meaning any longer
        faktura_data_map.put(DB.BUH_FAKTURA__FAKTURANR__, Invoice_.getNextFakturaNr()); // OBS! Aquired from http
        faktura_data_map.put(DB.BUH_FAKTURA__DATE_CREATED__, GP_BUH.getDateCreated_special());
        faktura_data_map.put(DB.BUH_FAKTURA__IMPORTANT_KOMMENT, komment);
        faktura_data_map.put(DB.BUH_FAKTURA__COPIED_FROM_ID, fakturaId); //[#KREDIT-RUT#]
        //
        faktura_data_map.remove(DB.BUH_FAKTURA__ID__); // [IMPORTANT]
        faktura_data_map.remove(DB.BUH_FAKTURA__MAKULERAD);
        faktura_data_map.remove(DB.BUH_FAKTURA__SENT);
        //
    }

    private HashMap<String, String> setForfalloDatumCopy(HashMap<String, String> faktura_data_map, int betalVilkor) {
//        String fakturaDatum = faktura_data_map.get(DB.BUH_FAKTURA__FAKTURA_DATUM);
        String fakturaDatum = GP_BUH.getDate_yyyy_MM_dd();
        String forfallodatum = GP_BUH.get_date_time_plus_some_time_in_days(fakturaDatum, betalVilkor);
        faktura_data_map.put(DB.BUH_FAKTURA__FAKTURA_DATUM, fakturaDatum);
        faktura_data_map.put(DB.BUH_FAKTURA__FORFALLO_DATUM, forfallodatum);
        return faktura_data_map;
    }

    private HashMap<String, String> getOneFakturaData() {
        //
        String json = bim.getSELECT_fakturaId__doubleWhere();
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM_GET_ONE_FAKTURA_ALL_DATA, json);
            //
            ArrayList<HashMap<String, String>> addresses = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            return addresses.get(0);
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
    }

    private HashMap<String, String> getForetagData(String phpFunction, boolean kundId) {
        //
        String json;
        //
        if (kundId) {
            json = bim.getSELECT_kundId();
        } else {
            if (bim.getCopiedFromFakturaId().equals("0") == false) {
                json = bim.getSELECT_copied_from_faktura_id__doubleWhere();
            } else {
                json = bim.getSELECT_fakturaId__doubleWhere();
            }
        }
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    phpFunction, json);
            //
            ArrayList<HashMap<String, String>> list = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            if (list.isEmpty()) {
                return null;
            } else {
                return list.get(0);
            }
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        return null;
    }

    private ArrayList<HashMap<String, String>> getForetagData_B(String phpFunction, boolean kundId) {
        //
        String json;
        //
        if (kundId) {
            json = bim.getSELECT_kundId();
        } else {
            if (bim.getCopiedFromFakturaId().equals("0") == false) {
                json = bim.getSELECT_copied_from_faktura_id__doubleWhere();
            } else {
                json = bim.getSELECT_fakturaId__doubleWhere();
            }
        }
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    phpFunction, json);
            //
            return JSon.phpJsonResponseToHashMap(json_str_return);
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        return null;
        //
    }

    private String getKontantFakturaBetalMetod() {
        //
//        String json = bim.getSELECT_fakturaId();
        String json = bim.getSELECT_fakturaId__doubleWhere();
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM__GET_FAKTURA_INBET, json);
            //
            ArrayList<HashMap<String, String>> addresses = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            if (addresses.isEmpty()) {
                return "";
            }
            //
            String betalMetod_shortName = addresses.get(0).get(DB.BUH_FAKTURA_INBET__BETAL_METHOD);
            //
            return JSon.getLongName(DB.STATIC__BETAL_METHODS, betalMetod_shortName);
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        //
        //
        //
        //
    }

    private String getMomsSatsString(boolean preview) {
        //
        JTable table;
        //
        if (preview) {
            table = bim.jTable_InvoiceA_Insert_articles;
        } else {
            table = bim.jTable_invoiceB_faktura_artiklar;
        }
        //
        HashMap<String, String> map = new HashMap<>();
        //
        for (int x = 0; x < table.getRowCount(); x++) {
            //
            int col = HelpA.getColByName(table, TABLE_INVOICE_ARTIKLES__MOMS_SATS);
            //
            String val = (String) table.getValueAt(x, col);
            //
            if (val == null || val.isEmpty() || val.equals("null")) {
                //
            } else {
                map.put(val, "");
            }
            //
        }
        //
        String str = "";
        int i = 0;
        //
        for (Map.Entry<String, String> entry : map.entrySet()) {
            //
            String key = entry.getKey();
            //
            if (i == 0) {
                str += key + "%";
            } else {
                str += ", " + key + "%";
            }
            //
            i++;
            //
        }
        //
        return str;
        //
    }

    private String getFakturaNrCommonOrAlt() {
        String fakturanr_alt = _get(TABLE_ALL_INVOICES__FAKTURANR_ALT);
        if (fakturanr_alt.equals("0") == false) {
            return _get(TABLE_ALL_INVOICES__FAKTURANR_ALT);
        } else {
            return _get(TABLE_ALL_INVOICES__FAKTURANR);
        }
    }

    public void htmlFakturaOrReminder(String fakturatype, boolean paminnelse) {
        //
//        BUH_INVOICE_MAIN bim = invoice.bim;
        //
        HashMap<String, String> map_a_0 = new HashMap<>();
        HashMap<String, String> map_a = new HashMap<>();
        HashMap<String, String> map_b = new HashMap<>();
        HashMap<String, String> map_c = new HashMap<>();
        HashMap<String, String> map_d = new HashMap<>();
        HashMap<String, String> map_e__lev_addr = getFakturaKundData(DB.PHP_FUNC_PARAM_GET_FAKTURA_KUND_ADDRESSES, null);
        HashMap<String, String> map_e__lev_data = getFakturaKundData(DB.PHP_FUNC_PARAM_GET_ONE_FAKTURA_KUND_ALL_DATA, null);
        HashMap<String, String> map_f__ftg_data = getForetagData(DB.PHP_FUNC_PARAM_GET_FORETAG_DATA, true);
        HashMap<String, String> map_g__ftg_addr = getForetagData(DB.PHP_FUNC_PARAM_GET_FORETAG_ADDRESS, true);
        HashMap<String, String> map_rut = getForetagData(DB.PHP_FUNC_PARAM_GET_RUT, false);
        ArrayList<HashMap<String, String>> map_rut_pers = getForetagData_B(DB.PHP_FUNC_PARAM_GET_RUT_PERSON, false);
        //
        map_a_0.put(DB.BUH_FAKTURA__ID__, _get(TABLE_ALL_INVOICES__FAKTURA_ID));
        //
        map_a.put(HTMLPrint_A.T__FAKTURA_NR, getFakturaNrCommonOrAlt());
        map_a.put(HTMLPrint_A.T__KUND_NR, _get(TABLE_ALL_INVOICES__KUND_NR));
        map_a.put(HTMLPrint_A.T__FAKTURA_DATUM, _get(TABLE_ALL_INVOICES__DATUM));
        //
        map_b.put(HTMLPrint_A.T__FAKTURA_ER_REF, _get(TABLE_ALL_INVOICES__ER_REF));
        map_b.put(HTMLPrint_A.T__FAKTURA_ERT_ORDER_NR, _get(TABLE_ALL_INVOICES__ERT_ORDER)); //**************************EMPTY
        map_b.put(HTMLPrint_A.T__FAKTURA_LEV_VILKOR, _get(TABLE_ALL_INVOICES__LEV_VILKOR));
        map_b.put(HTMLPrint_A.T__FAKTURA_LEV_SATT, _get(TABLE_ALL_INVOICES__LEV_SATT));
        map_b.put(HTMLPrint_A.T__FAKTURA_ERT_VAT_NR, GP_BUH._get(map_e__lev_data, DB.BUH_FAKTURA_KUND___VATNR)); //**************************EMPTY
        //
        map_c.put(HTMLPrint_A.T__FAKTURA_VAR_REF, _get(TABLE_ALL_INVOICES__VAR_REF));
        map_c.put(HTMLPrint_A.T__FAKTURA_BETAL_VILKOR__FLEX, _get(TABLE_ALL_INVOICES__BET_VILKOR));
        map_c.put(HTMLPrint_A.T__FAKTURA_FORFALLODATUM__FLEX, _get(TABLE_ALL_INVOICES__FORFALLODATUM));
        map_c.put(HTMLPrint_A.T__FAKTURA_DROJMALSRANTA__FLEX, _get(TABLE_ALL_INVOICES__DROJSMALSRANTA));
        map_c.put(HTMLPrint_A.T__FAKTURA_BETAL_METOD, getKontantFakturaBetalMetod());
        map_c.put(HTMLPrint_A.T__FAKTURA_UTSKRIVET, GP_BUH.getDateTime_yyyy_MM_dd());
        map_c.put(HTMLPrint_A.T__FAKTURA_KREDITERAR_FAKTURA_NR, bim.getKomment_$());
        map_c.put(HTMLPrint_A.T__FAKTURA_XXXXXXX, ""); //**************************EMPTY
        //
        map_d.put(HTMLPrint_A.T__FAKTURA_FRAKT, _get(TABLE_ALL_INVOICES__FRAKT));
        map_d.put(HTMLPrint_A.T__FAKTURA_EXP_AVG, _get(TABLE_ALL_INVOICES__EXP_AVG));
        map_d.put(HTMLPrint_A.T__FAKTURA_EXKL_MOMS, _get(TABLE_ALL_INVOICES__EXKL_MOMS));
        map_d.put(HTMLPrint_A.T__FAKTURA_MOMS_PERCENT, getMomsSatsString(false));
        map_d.put(HTMLPrint_A.T__FAKTURA_MOMS_KR, _get(TABLE_ALL_INVOICES__MOMS));
        map_d.put(HTMLPrint_A.T__FAKTURA_RABATT_KR, _get(TABLE_ALL_INVOICES__RABATT_TOTAL_KR));
        //
        //[#OFFERT#]
        if (paminnelse == false) {
            map_d.put(HTMLPrint_A.getAttBetalaTitle(fakturatype), _get(TABLE_ALL_INVOICES__TOTAL_INKL_MOMS));
        } else {
            map_d.put(HTMLPrint_B.getAttBetalaTitle(), _get(TABLE_ALL_INVOICES__TOTAL_INKL_MOMS));
        }
        //
        //
        if (paminnelse == false) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    HTMLPrint_A hTMLPrint_A = new HTMLPrint_A(
                            bim,
                            fakturatype,
                            false,
                            bim.getArticlesMarkedInvoice(),
                            map_a_0,
                            map_a,
                            map_b,
                            map_c,
                            map_d,
                            map_e__lev_addr,
                            map_e__lev_data,
                            map_f__ftg_data,
                            map_g__ftg_addr,
                            map_rut,
                            map_rut_pers
                    );
                    //
                    hTMLPrint_A.setVisible(true);
                    //
//                hTMLPrint_A.printSilent();
                    //
                }
            });
        } else {
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    HTMLPrint_B hTMLPrint_B = new HTMLPrint_B(
                            bim,
                            fakturatype,
                            false,
                            bim.getArticlesMarkedInvoice(),
                            map_a_0,
                            map_a,
                            map_b,
                            map_c,
                            map_d,
                            map_e__lev_addr,
                            map_e__lev_data,
                            map_f__ftg_data,
                            map_g__ftg_addr
                    );
                    //
                    hTMLPrint_B.setVisible(true);
                    //
                }
            });
        }

    }

    private String getFakturaNrAltIfExist(Invoice_ invoice) {
        TableInvert ti3 = (TableInvert) invoice.TABLE_INVERT_3;
        String fakturanr_alt = ti3.getValueAt(DB.BUH_FAKTURA__FAKTURANR_ALT);
        if (fakturanr_alt.equals("0") == false) {
            return fakturanr_alt;
        } else {
            return "-";
        }
    }

    private HashMap<String, String> getRut_preview() {
        //
        RutRot rut = bim.getRutRot();
        //
        if (Invoice_.CURRENT_OPERATION_INSERT == false) { // "UPDATE / BEARBETA"
            //
            if (bim.isRUT() && rut == null) {
                return getForetagData(DB.PHP_FUNC_PARAM_GET_RUT, false);
            }
            //
        } else { // CREATE / SKAPA
            //
            if (rut != null) {
                return rut.getFakturaPreview_rut();
            }
            //
        }
        //
        return null;
        //
    }

    private ArrayList<HashMap<String, String>> getRutPerson_preview() {
        //
        RutRot rut = bim.getRutRot();
        //
        if (bim.isRUT() && rut == null) {
            return getForetagData_B(DB.PHP_FUNC_PARAM_GET_RUT_PERSON, false);
        }
        //
        if (rut == null && bim.isRUT() == false) {
            return null;
        }
        //
        return rut.getFakturaPreview_rut_pers();
    }

    public void htmlFakturaOrReminder_preview(String fakturatype, boolean paminnelse, Invoice_ invoice) {
        //
//        BUH_INVOICE_MAIN bim = invoice.bim;
        //
        TableInvert ti = bim.getTableInvert();
        TableInvert ti_3 = bim.getTableInvert_3();
        //
        HashMap<String, String> map_a_0 = new HashMap<>();
        HashMap<String, String> map_a = new HashMap<>();
        HashMap<String, String> map_b = new HashMap<>();
        HashMap<String, String> map_c = new HashMap<>();
        HashMap<String, String> map_d = new HashMap<>();
        HashMap<String, String> map_e__lev_addr = getFakturaKundData(DB.PHP_FUNC_PARAM_GET_FAKTURA_KUND_ADDRESSES, ti);
        HashMap<String, String> map_e__lev_data = getFakturaKundData(DB.PHP_FUNC_PARAM_GET_ONE_FAKTURA_KUND_ALL_DATA, ti);
        HashMap<String, String> map_f__ftg_data = getForetagData(DB.PHP_FUNC_PARAM_GET_FORETAG_DATA, true);
        HashMap<String, String> map_g__ftg_addr = getForetagData(DB.PHP_FUNC_PARAM_GET_FORETAG_ADDRESS, true);
        HashMap<String, String> map_rut = getRut_preview(); // ***
        ArrayList<HashMap<String, String>> map_rut_pers = getRutPerson_preview(); // ***
        //
        map_a_0.put(DB.BUH_FAKTURA__ID__, _get(TABLE_ALL_INVOICES__FAKTURA_ID)); // In fact not needed for preview as the "id" is required for sending
        //
        map_a.put(HTMLPrint_A.T__FAKTURA_NR, getFakturaNrAltIfExist(invoice));
        map_a.put(HTMLPrint_A.T__KUND_NR, ti.getValueAtJComboBox(DB.BUH_FAKTURA__FAKTURAKUND_ID, 2));
        map_a.put(HTMLPrint_A.T__FAKTURA_DATUM, getValueTableInvert(DB.BUH_FAKTURA__FAKTURA_DATUM, ti));
        //
        map_b.put(HTMLPrint_A.T__FAKTURA_ER_REF, getValueTableInvert(DB.BUH_FAKTURA__ER_REFERENS, ti));
        map_b.put(HTMLPrint_A.T__FAKTURA_ERT_ORDER_NR, getValueTableInvert(DB.BUH_FAKTURA__ERT_ORDER, ti_3)); //**************************EMPTY
        map_b.put(HTMLPrint_A.T__FAKTURA_LEV_VILKOR, getValueTableInvert(DB.BUH_FAKTURA__LEV_VILKOR, ti));
        map_b.put(HTMLPrint_A.T__FAKTURA_LEV_SATT, getValueTableInvert(DB.BUH_FAKTURA__LEV_SATT, ti));
        map_b.put(HTMLPrint_A.T__FAKTURA_ERT_VAT_NR, GP_BUH._get(map_e__lev_data, DB.BUH_FAKTURA_KUND___VATNR)); //**************************EMPTY
        //
        map_c.put(HTMLPrint_A.T__FAKTURA_VAR_REF, getValueTableInvert(DB.BUH_FAKTURA__VAR_REFERENS, ti));
        map_c.put(HTMLPrint_A.T__FAKTURA_BETAL_VILKOR__FLEX, getValueTableInvert(DB.BUH_FAKTURA__BETAL_VILKOR, ti));
        map_c.put(HTMLPrint_A.T__FAKTURA_FORFALLODATUM__FLEX, getValueTableInvert(DB.BUH_FAKTURA__FORFALLO_DATUM, ti));
        map_c.put(HTMLPrint_A.T__FAKTURA_DROJMALSRANTA__FLEX, getValueTableInvert(DB.BUH_FAKTURA__DROJSMALSRANTA, ti_3));
        map_c.put(HTMLPrint_A.T__FAKTURA_BETAL_METOD, ""); // getKontantFakturaBetalMetod()
        map_c.put(HTMLPrint_A.T__FAKTURA_UTSKRIVET, GP_BUH.getDateTime_yyyy_MM_dd());
        map_c.put(HTMLPrint_A.T__FAKTURA_KREDITERAR_FAKTURA_NR, ""); // bim.getKomment_$()
        map_c.put(HTMLPrint_A.T__FAKTURA_XXXXXXX, ""); //**************************EMPTY
        //
        map_d.put(HTMLPrint_A.T__FAKTURA_FRAKT, getValueTableInvert(DB.BUH_FAKTURA__FRAKT, ti_3));
        map_d.put(HTMLPrint_A.T__FAKTURA_EXP_AVG, getValueTableInvert(DB.BUH_FAKTURA__EXP_AVG, ti_3));
        map_d.put(HTMLPrint_A.T__FAKTURA_EXKL_MOMS, "" + invoice.getTotalExklMoms());
        map_d.put(HTMLPrint_A.T__FAKTURA_MOMS_PERCENT, getMomsSatsString(true)); // "" + ((int)invoice.getMomsSats() * 100)
        map_d.put(HTMLPrint_A.T__FAKTURA_MOMS_KR, "" + invoice.getMomsTotal());
        map_d.put(HTMLPrint_A.T__FAKTURA_RABATT_KR, "" + invoice.getRabattTotal());
        //
        String fakturaTotal = "" + invoice.getFakturaTotal();
        //
        if (paminnelse == false) {
            map_d.put(HTMLPrint_A.getAttBetalaTitle(fakturatype), fakturaTotal);//_get(TABLE_ALL_INVOICES__TOTAL_INKL_MOMS)
        } else {
            map_d.put(HTMLPrint_B.getAttBetalaTitle(), fakturaTotal); //_get(TABLE_ALL_INVOICES__TOTAL_INKL_MOMS)
        }
        //
        //
        if (paminnelse == false) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    HTMLPrint_A hTMLPrint_A = new HTMLPrint_A(
                            bim,
                            fakturatype,
                            true,
                            bim.getArticlesCurrInvoiceJTable(), // OBS! Important [2020-10-01]
                            map_a_0,
                            map_a,
                            map_b,
                            map_c,
                            map_d,
                            map_e__lev_addr,
                            map_e__lev_data,
                            map_f__ftg_data,
                            map_g__ftg_addr,
                            map_rut,
                            map_rut_pers
                    );
                    //
                    hTMLPrint_A.setVisible(true);
                    //
//                hTMLPrint_A.printSilent();
                    //
                }
            });
        } else {
            //
            // Here is for Påminnelse Preview but indeed it's not used by now [2020-10-20]
            //
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    HTMLPrint_B hTMLPrint_B = new HTMLPrint_B(
                            bim,
                            fakturatype,
                            true,
                            bim.getArticlesCurrInvoiceJTable(), // OBS! Important [2020-10-01]
                            map_a_0,
                            map_a,
                            map_b,
                            map_c,
                            map_d,
                            map_e__lev_addr,
                            map_e__lev_data,
                            map_f__ftg_data,
                            map_g__ftg_addr
                    );
                    //
                    hTMLPrint_B.setVisible(true);
                    //
                }
            });
        }

    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showTableInvert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getUnsaved(int nr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean fieldsValidated(boolean insert) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
