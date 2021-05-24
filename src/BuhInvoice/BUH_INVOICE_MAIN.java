/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import forall.BackgroundPanel;
import BuhInvoice.sec.BlinkThread;
import BuhInvoice.sec.GDPR;
import BuhInvoice.sec.IO;
import MyObjectTableInvert.JTextAreaJLink;
import BuhInvoice.sec.LANG;
import BuhInvoice.sec.OffertCopyOrOmvandlaFrame_;
import BuhInvoice.sec.RutRot;
import MyObjectTableInvert.TableInvert;
import XY_BUH_INVOICE.Buh_Invoice_Main__IF;
import forall.HelpA;
import forall.JComboBoxA;
import icons.ICON;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MCREMOTE
 */
public class BUH_INVOICE_MAIN extends javax.swing.JFrame implements MouseListener, KeyListener, Buh_Invoice_Main__IF {

    private InvoiceA_Insert_ invoiceA_insert;
    private InvoiceA_Update invoiceA_update;
    private CustomersA_ customersA;
    private ForetagA foretagA;
    private ArticlesA articlesA;
    protected InvoiceB invoiceB;
    protected Home home;
    protected OptionsTab optionsTab;
    protected StatistikTab statistikTab;

    private String ACTUAL_TAB_NAME;
    private String PREVIOUS_TAB_NAME;
    public final static String TAB_HOME = "HEM";
    public final static String TAB_INVOICES_OVERVIEW = "ALLA FAKTUROR";
    private final static String TAB_FAKTURA = "FAKTURA";
    public final static String TAB_KUDNER = "KUNDER";
    private final static String TAB_ARTIKLAR = "ARTIKLAR";
    private final static String TAB_FTG_SETUP = "FÖRETAGS INSTÄLLNINGAR";
    private final static String TAB_OTHER_SETUP = "ÖVRIGA INSTÄLLNINGAR";
    private final static String TAB_STATISTIK = "GRAFISK VY";
    //
    private ArrayList<HashMap<String, String>> ARTICLES_ACTUAL_INVOICE;
    //
    public String PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FILTER;
    private final ArrayList<JToggleButton> toggleBtnList = new ArrayList<>();
    //
    protected String FAKTURA_TYPE_CURRENT__OPERATION;
    //
    private RutRot rutRot;

    /**
     * Creates new form BUH_INVOICE_MAIN
     */
    public BUH_INVOICE_MAIN() {
        initComponents();
        initOhter();
        //
        SET_SEARCH_FILTER(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ONE_YEAR_BACK, true);
        //
        setHomePageBackground();
        //
        setMarginLeftLabelsHomeTab();
        //
        gdpr();
        //
    }

    private void gdpr() {
        if (GP_BUH.isGdprAccepted()) {
            this.setEnabled(false);
            GDPR gdpr = new GDPR(this);
            GP_BUH.centerAndBringToFront(gdpr);
        }
    }

    private void setHomePageBackground() {
        //
        BackgroundPanel bg = (BackgroundPanel) jPanel19;
        //
        try {
            Image image = ImageIO.read(new File("io/bg.jpg"));
            bg.go(image);
        } catch (Exception ex) {
            // Will set the initial background
        }
    }

    private void resetRutRot() {
        System.out.println("RUT RESET ************************AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        this.rutRot = null;
    }

    public void setRutRot(RutRot rut) {
        this.rutRot = rut;
    }

    public RutRot getRutRot() {
        return this.rutRot;
    }

    private void setMarginLeftLabelsHomeTab() {
        this.jLabel_inloggning.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        this.jLabel_register_new.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        this.jLabel_restore_password.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        this.jLabel_share_account.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
    }

    private void setMarginJLabelsHomeTab() {
        jLabel_inloggning.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
    }

    protected void RESET_SEARCH_FILTER() {
        SET_SEARCH_FILTER(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ONE_YEAR_BACK, false);
    }

    private void SET_SEARCH_FILTER(String filter, boolean affectNyckelTalInfo) {
        //
        PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FILTER = filter;
        //
        if (affectNyckelTalInfo) {
            //
            if (filter.equals(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__MAKULERAD)) {
                jLabel_nyckel_tal__info_label.setText("Nyckeltal, urval: " + DB.FILTER_DICT_MAP.get(PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FILTER));
            } else {
                jLabel_nyckel_tal__info_label.setText("Nyckeltal, urval: " + DB.FILTER_DICT_MAP.get(PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FILTER) + " (exkl. makulerade)");
            }
            //
        }
        //
    }

    protected boolean isInitialFilter() {
        if (PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FILTER.equals(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ONE_YEAR_BACK)) {
            return true;
        } else {
            return false;
        }
    }

    protected void enableTabs(boolean enable) {
        jTabbedPane1.setEnabled(enable);
    }

    protected void faktua_tab_blockUntilSavedOrAborted_invoice(boolean disabled) {
        //
        if (disabled) {
            jButton_dont_save_settings.setEnabled(true);
        } else {
            jButton_dont_save_settings.setEnabled(false);
        }
        //
        GP_BUH.setEnabled(jTabbedPane1, disabled);
        GP_BUH.setEnabled(jButton_create_new_faktura_b, disabled);
        GP_BUH.setEnabled(jButton_create_new_kontant_faktura_b, disabled);
        //
    }

    private void initOhter() {
        //
//        DEFINE_KUNDID();
        //
        enableTabs(false);
        //
        this.jTabbedPane1.addMouseListener(this);
        this.jTable_invoiceB_alla_fakturor.addMouseListener(this);
        this.jTable_invoiceB_alla_fakturor.addKeyListener(this);
        //
        this.jTable_InvoiceA_Insert_articles.addMouseListener(this);
        this.jTable_kunder.addMouseListener(this);
        this.jTable_kund_adresses.addMouseListener(this);
        this.jTable_ArticlesA_articles.addMouseListener(this);
        //
        this.jTextArea_faktura_komment.addKeyListener(this);
        //
        home = new Home(this);
        //
        invoiceA_update = new InvoiceA_Update(this);
        //
        setUneditableAllJTables();
        //
        this.setTitle(GP_BUH.PRODUCT_NAME + " " + GP_BUH.VERSION);
        this.setIconImage(GP_BUH.getBuhInvoicePrimIcon());
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //
        initToggleBtnList();
        //
    }

    private void initToggleBtnList() {
        toggleBtnList.add(jToggleButton_not_send_filter);
        toggleBtnList.add(jToggleButton_obetald_filter);
        toggleBtnList.add(jToggleButton_delvis_betald_filter);
        toggleBtnList.add(jToggleButton_makulerad_filter);
        toggleBtnList.add(jToggleButton_act_month_filter);
        toggleBtnList.add(jToggleButton_forfallen_filter);
    }

    protected void untoggleAll() {
        for (JToggleButton btn : toggleBtnList) {
            btn.setSelected(false);
        }
    }

    private void untoggleAllExcept(JToggleButton exceptBtn) {
        for (JToggleButton btn : toggleBtnList) {
            if (btn.equals(exceptBtn) == false) {
                btn.setSelected(false);
            }
        }
    }

    protected void openTabByName(String tabName) {
        HelpA.openTabByName(jTabbedPane1, tabName);
        ACTUAL_TAB_NAME = tabName;
        PREVIOUS_TAB_NAME = ACTUAL_TAB_NAME;
    }

    private void setUneditableAllJTables() {
        HelpA.setUneditableJTable(jTable_invoiceB_alla_fakturor);
        HelpA.setUneditableJTable(jTable_invoiceB_faktura_artiklar);
        HelpA.setUneditableJTable(jTable_InvoiceA_Insert_articles);
        HelpA.setUneditableJTable(jTable_ArticlesA_articles);
        HelpA.setUneditableJTable(jTable_ftg);
        HelpA.setUneditableJTable(jTable_ftg_addr);
        HelpA.setUneditableJTable(jTable_kund_adresses);
        HelpA.setUneditableJTable(jTable_kunder);
    }

    protected void hideShowButtonsDependingOnConditions() {
        //
        GP_BUH.enableDisableButtons(jPanel4, true); // Enable ALL
        GP_BUH.setEnabled(jButton4_save_faktura_komment, true);
        GP_BUH.setEnabled(jButton4_delete_faktura_komment1, true);
        GP_BUH.copy_btn_adjustment(jButton_copy_faktura, false);
        //
        if (jTable_invoiceB_alla_fakturor.getRowCount() == 0) {
            GP_BUH.setEnabled(jButton_edit_faktura, false);
            GP_BUH.setEnabled(jButton_copy_faktura, false);
            GP_BUH.setEnabled(jButton_kredit_faktura, false);
            GP_BUH.setEnabled(jButton_delete_faktura, false);
            GP_BUH.setEnabled(jButton_print_faktura, false);
            GP_BUH.setEnabled(jButton_inbetalning, false);
            GP_BUH.setEnabled(jButton_show_actions, false);
            GP_BUH.setEnabled(jButton_send_reminder, false);
            //
            GP_BUH.setEnabled(jButton4_save_faktura_komment, false);
            GP_BUH.setEnabled(jButton4_delete_faktura_komment1, false);
            //
            return;
        }
        //
        if (isKreditFaktura()) {
            GP_BUH.setEnabled(jButton_kredit_faktura, false);
            GP_BUH.setEnabled(jButton_copy_faktura, false);
            GP_BUH.setEnabled(jButton_inbetalning, false);
            GP_BUH.setEnabled(jButton_send_reminder, false);
        } else if (isKontantFaktura()) {
            GP_BUH.setEnabled(jButton_kredit_faktura, false);
            GP_BUH.setEnabled(jButton_send_reminder, false);
        } else if (isOffert()) {
            GP_BUH.copy_btn_adjustment(jButton_copy_faktura, true);
            GP_BUH.setEnabled(jButton_inbetalning, false);
            GP_BUH.setEnabled(jButton_send_reminder, false);
            GP_BUH.setEnabled(jButton_kredit_faktura, false);
        } else if (isMakulerad()) {
            GP_BUH.setEnabled(jButton_kredit_faktura, false);
            GP_BUH.setEnabled(jButton_copy_faktura, false);
            GP_BUH.setEnabled(jButton_inbetalning, false);
            GP_BUH.setEnabled(jButton_send_reminder, false);
        }
    }

    public TableInvert getTableInvert() {
        if (Invoice_.CURRENT_OPERATION_INSERT && invoiceA_insert != null) {
            return (TableInvert) invoiceA_insert.TABLE_INVERT;
        } else {
            return (TableInvert) invoiceA_update.TABLE_INVERT;
        }
    }

    protected TableInvert getTableInvert_3() {
        if (Invoice_.CURRENT_OPERATION_INSERT && invoiceA_insert != null) {
            return (TableInvert) invoiceA_insert.TABLE_INVERT_3;
        } else {
            return (TableInvert) invoiceA_update.TABLE_INVERT_3;
        }
    }

    protected void displayArticlesCount() {
        if (invoiceB != null) {
            invoiceB.displayArticlesCount();
        }
    }

    protected void resetArticlesCount() {
        if (invoiceB != null) {
            invoiceB.resetArticlesCount();
        }

    }

    protected void setArticlesMarkedInvoice(ArrayList<HashMap<String, String>> list) {
        this.ARTICLES_ACTUAL_INVOICE = list;
    }

    protected ArrayList<HashMap<String, String>> getArticlesMarkedInvoice() {
        return this.ARTICLES_ACTUAL_INVOICE;
    }

    protected ArrayList<HashMap<String, String>> getArticlesCurrInvoiceJTable() {
        return JSon.jTableToHashMaps(jTable_InvoiceA_Insert_articles, InvoiceB.ARTICLES_TABLE_DICT);
    }

    /**
     * OBS! Note if "kundId" does not exist it will not be possible to insert a
     * invoice because of the "foreign key constraints".
     *
     * It will maybe make sense to verify if the "kundId" exist in cloud
     *
     * BY[2020-10-08] To login with needed account goto GP_BUH and change "USER"
     * & "PASS"
     *
     * @return
     */
    protected String getKundId() {
        return GP_BUH.KUND_ID;
    }

    protected boolean noCustomersPresent() {
        return invoiceB.noCustomersPresent();
    }

    protected boolean articlesLimitReached() {
        if (getInvoiceArticleCount() >= GP_BUH.MAX_AMMOUNT_ARTICLES__FAKTURA) {
            return true;
        } else {
            return false;
        }
    }

    protected int getInvoiceArticleCount() {
        return jTable_InvoiceA_Insert_articles.getRowCount();
    }

    protected String getFakturaId() {
        return HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__FAKTURA_ID);
    }

    protected String getCopiedFromFakturaId() {
        return HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__COPIED_FROM_ID);
    }

    protected void deleteFaktura(String fakturaId) {
        invoiceB.deleteFakturaPrimary(fakturaId);
    }

    protected String getFakturaNr() {
        return HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__FAKTURANR);
    }

    protected String getFakturaKund() {
        return HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__KUND);
    }

    protected String getFakturaKundId() {
        return HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__KUND_ID);
    }

    protected double getFakturaTotal() {
        return Double.parseDouble(HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__TOTAL_INKL_MOMS));
    }

    protected int getBetalVilkor() {
        return Integer.parseInt(HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__BET_VILKOR));
    }

    protected String getFakturaArtikelId() {
        return HelpA.getValueSelectedRow(jTable_InvoiceA_Insert_articles, InvoiceB.TABLE_INVOICE_ARTIKLES__ID);
    }

    protected String getFakturaType() {
        return HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__FAKTURA_TYP);
    }

    protected String getFakturaType_actual_operation() {
        return FAKTURA_TYPE_CURRENT__OPERATION;
    }

    protected Boolean isPrinted() {
        //
        String val = HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__UTSKRIVEN);
        //
        if (val.equals(DB.STATIC__YES)) {
            return true;
        } else {
            return false;
        }
    }

    protected Boolean isOmvantSkatt() {
        //
        String val = HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__OMVANT_SKATT);
        //
        if (val.equals(DB.STATIC__YES)) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean isBetald() {
        //
        JTable table = jTable_invoiceB_alla_fakturor;
        //
        if (table.getRowCount() == 0) {
            return false;
        }
        //
        String betald = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__BETALD);
        //
        return !betald.equals(DB.STATIC__NO) && !betald.equals(DB.STATIC_BET_STATUS_KREDIT);
        //
    }

    protected boolean isSent() {
        //
        JTable table = jTable_invoiceB_alla_fakturor;
        //
        if (table.getRowCount() == 0) {
            return false;
        }
        //
        String sent = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__SKICKAD);
        //
        return sent.equals(DB.STATIC__YES);
        //
    }

    protected boolean isForfallen() {
        //
        String dateFormat = GP_BUH.DATE_FORMAT_BASIC;
        String dateNow = GP_BUH.getDate_yyyy_MM_dd();
        String forfallodatum = HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__FORFALLODATUM);
        //
        if (forfallodatum == null || forfallodatum.isEmpty()) {
            return false;
        }
        //
        int daysForfallen = GP_BUH.get_diff_in_days__two_dates(dateNow, dateFormat, forfallodatum, dateFormat);
        //
        if (isBetald()) {
            return false;
        }
        //
        if (daysForfallen < 0) {
            return false;
        } else {
            return true;
        }
        //
    }

    protected boolean isMakulerad() {
        //
        String makulerad = HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__MAKULERAD);
        //
        if (makulerad.equals(DB.STATIC__YES)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * [#RUTROT#]
     *
     * @return
     */
    protected boolean isRUT() {
        //
        String makulerad = HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__RUT);
        //
        if (makulerad.equals(DB.STATIC__YES)) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean isPerson() {
        //
        String makulerad = HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__FAKTURA_KUND__IS_PERSON);
        //
        if (makulerad.equals("1")) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean isPerson(String fakturaKundId) {
        //
        String json = getSELECT(DB.BUH_FAKTURA_KUND__ID, fakturaKundId);
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM_GET__FAKTURA_KUND__IS_PERSON, json);
            //
            ArrayList<HashMap<String, String>> entries = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            if (entries == null || entries.isEmpty()) {
                return false;
            } else {
                return true;
            }
            //
        } catch (Exception ex) {
            Logger.getLogger(HelpBuh.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        return false;
    }

    protected boolean isKreditFaktura() {
        //
        String fakturaTyp = HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__FAKTURA_TYP);
        //
        if (fakturaTyp == null) {
            return false;
        }
        //
        if (fakturaTyp.equals(DB.STATIC__FAKTURA_TYPE_KREDIT)) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean isKontantFaktura() {
        //
        String fakturaTyp = HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__FAKTURA_TYP);
        //
        if (fakturaTyp == null) {
            return false;
        }
        //
        if (fakturaTyp.equals(DB.STATIC__FAKTURA_TYPE_KONTANT)) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean isOffert() {
        //
        if (FAKTURA_TYPE_CURRENT__OPERATION != null && FAKTURA_TYPE_CURRENT__OPERATION.equals(DB.STATIC__FAKTURA_TYPE_OFFERT)) {
            return true;
        }
        //
        String fakturaTyp = HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__FAKTURA_TYP);
        //
        if (fakturaTyp == null) {
            return false;
        }
        //
        if (fakturaTyp.equals(DB.STATIC__FAKTURA_TYPE_OFFERT)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * OBS! Remember the "komment" field is used internally for different
     * purposes. One of usages is to save the "krediterad" invoice number
     * [2020-09-15]
     *
     * @return
     */
    protected String getKomment_$() {
        String val = HelpA.getValueSelectedRow(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__KOMMENT_$);
        return JSon.getValNoNull(val);
    }

    protected String getSELECT_fakturaId() {
        return getSELECT(DB.BUH_FAKTURA__ID__, getFakturaId());
    }

    protected String getSELECT_copied_from_faktura_id() {
        return getSELECT(DB.BUH_FAKTURA__COPIED_FROM_ID, getCopiedFromFakturaId());
    }

    protected String getSELECT_kundId() {
        return getSELECT(DB.BUH_FAKTURA__KUNDID__, "777");// [#KUND-ID-INSERT#]
    }

    protected String getSELECT_kundId__doubleWhere(String secondWhereValue) {
        return getSELECT_doubleWhere(DB.BUH_FAKTURA__KUNDID__, "777", DB.BUH_FAKTURA_KUND__ID, secondWhereValue); // [#KUND-ID-INSERT#]
    }

    protected String getSELECT_fakturaKundId(String fakturaKundId) {
        return getSELECT(DB.BUH_FAKTURA_KUND__ID, fakturaKundId);
    }

    public static String getSELECT_(String whereColName, String whereValue) {
        //
        HashMap<String, String> map = new HashMap<>();
        map.put("where", whereColName); // $whereCoulunName
        map.put(whereColName, whereValue); // $whereValue
        //
        return JSon.hashMapToJSON(map);
    }

    protected String getSELECT(String whereColName, String whereValue) {
        //
        HashMap<String, String> map = new HashMap<>();
        map.put("where", whereColName); // $whereCoulunName
        map.put(whereColName, whereValue); // $whereValue
        //
        return JSon.hashMapToJSON(map);
    }

    protected String getSELECT_doubleWhere(String whereColName, String whereValue, String whereColName_b, String whereValue_b) {
        //
        HashMap<String, String> map = new HashMap<>();
        //
        map.put("where", whereColName); // $whereCoulunName
        map.put(whereColName, whereValue); // $whereValue
        //
        map.put("where_b", whereColName_b);
        map.put(whereColName_b, whereValue_b);
        //
        return JSon.hashMapToJSON(map);
    }

    /**
     * OBS! this function returns only basic parameters for "update". So you
     * will have to add the update values after calling this function.
     *
     * @param whereColName
     * @param whereValue
     * @param tableName
     * @return
     */
    protected HashMap<String, String> getUPDATE(String whereColName, String whereValue, String tableName) {
        //
        HashMap<String, String> map = new HashMap<>();
        map.put("where", whereColName); // $whereCoulunName
        map.put(whereColName, whereValue); // $whereValue
        map.put("table", tableName); // $table
        //
        return map;
    }

    protected static HashMap<String, String> getUPDATE_static(String whereColName, String whereValue, String tableName) {
        //
        HashMap<String, String> map = new HashMap<>();
        map.put("where", whereColName); // $whereCoulunName
        map.put(whereColName, whereValue); // $whereValue
        map.put("table", tableName); // $table
        //
        return map;
    }

    protected HashMap<String, String> getDELETE(String whereColName, String whereValue, String tableName) {
        return getUPDATE(whereColName, whereValue, tableName);
    }

    protected String getExist(String columnName, String value, String tableName) {
        //
        HashMap<String, String> map = new HashMap<>();
        //
        map.put("column", columnName);
        map.put("value", value);
        map.put("table", tableName);
        map.put("kundId", "777"); // [#KUND-ID-INSERT#]
        //
        return JSon.hashMapToJSON(map);
    }

    protected String getLatest(String columnName, String tableName) {
        //
        HashMap<String, String> map = new HashMap<>();
        //
        map.put("column", columnName);
        map.put("table", tableName);
        map.put("kundId", "777"); //[#KUND-ID-INSERT#]
        //
        return JSon.hashMapToJSON(map);
    }

    protected String getForeignKeyBindings(JTable table, String idColName, String whereParam, String phpFunc, String getFromMapParam) {
        //
        String returnStr = "";
        //
        String id = HelpA.getValueSelectedRow(table, idColName);
        //
        String json = getSELECT(whereParam, id);
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN, phpFunc, json);
            //
            ArrayList<HashMap<String, String>> invoices = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            for (HashMap<String, String> map : invoices) {
                //
                String rst = map.get(getFromMapParam);
                //
                returnStr += rst + ",";
                //
            }
            //
            if (returnStr.isEmpty()) {
                return returnStr;
            } else {
                return JSon.delete_last_letter_in_string(returnStr, ",");
            }
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public void setValueAllInvoicesJTable(String colName, String value) {
        HelpA.setValueCurrentRow(jTable_invoiceB_alla_fakturor, colName, value);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel19 = new BackgroundPanel();
        jPanel_inloggning = new javax.swing.JPanel();
        jButton_logg_in = new javax.swing.JButton();
        jCheckBox_spara_inloggning = new javax.swing.JCheckBox();
        jLabel_inloggning = new javax.swing.JLabel();
        jPanel_register_new = new javax.swing.JPanel();
        jLabel_register_new = new javax.swing.JLabel();
        jButton_register_new_user = new javax.swing.JButton();
        jPanel_restore_password = new javax.swing.JPanel();
        jLabel_restore_password = new javax.swing.JLabel();
        jButton_forgot_password = new javax.swing.JButton();
        jPanel_share_account = new javax.swing.JPanel();
        jLabel_share_account = new javax.swing.JLabel();
        jButton_share_account = new javax.swing.JButton();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTable_shared_users = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        jButton_delete_account_sharing = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17_new__version = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable_invoiceB_alla_fakturor = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable_invoiceB_faktura_artiklar = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButton15 = new javax.swing.JButton();
        jButton_edit_faktura = new javax.swing.JButton();
        jButton_create_new_faktura = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton_kontant_faktura = new javax.swing.JButton();
        jButton_kredit_faktura = new javax.swing.JButton();
        jButton_copy_faktura = new javax.swing.JButton();
        jButton_delete_faktura = new javax.swing.JButton();
        jButton_print_faktura = new javax.swing.JButton();
        jButton_send_reminder = new javax.swing.JButton();
        jButton_inbetalning = new javax.swing.JButton();
        jButton_show_actions = new javax.swing.JButton();
        jLabel_all_invoices_list = new javax.swing.JLabel();
        jLabel_all_invoices_list1 = new javax.swing.JLabel();
        jTextArea_faktura_komment = new JTextAreaJLink();
        jButton4_save_faktura_komment = new javax.swing.JButton();
        jButton4_delete_faktura_komment1 = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jToggleButton_obetald_filter = new javax.swing.JToggleButton();
        jToggleButton_delvis_betald_filter = new javax.swing.JToggleButton();
        jToggleButton_forfallen_filter = new javax.swing.JToggleButton();
        jToggleButton_not_send_filter = new javax.swing.JToggleButton();
        jToggleButton_makulerad_filter = new javax.swing.JToggleButton();
        jToggleButton_act_month_filter = new javax.swing.JToggleButton();
        jComboBox_faktura_kunder_filter = new JComboBoxA();
        jButton_search_by_kund = new javax.swing.JButton();
        jLabel_faktura_changed_by__user = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel_info__forfallen = new javax.swing.JLabel();
        jLabel_info_is_person = new javax.swing.JLabel();
        jLabel_info_rut__or_omvant_skatt = new javax.swing.JLabel();
        jLabel_info__betald = new javax.swing.JLabel();
        jLabel_info__printed = new javax.swing.JLabel();
        jLabel_info__sent = new javax.swing.JLabel();
        jLabel_info__kontant_faktura = new javax.swing.JLabel();
        jLabel_info__kredit_faktura = new javax.swing.JLabel();
        jLabel_info__offert = new javax.swing.JLabel();
        jLabel_info__makulerad = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel_nycke_tal__ing_moms = new javax.swing.JLabel();
        jLabel_nycke_tal__total_inkl_moms = new javax.swing.JLabel();
        jLabel_nycke_tal__total_exkl_moms = new javax.swing.JLabel();
        jLabel_nycke_tal__antal_fakturor = new javax.swing.JLabel();
        jLabel__nyckel_tal__ing_moms = new javax.swing.JLabel();
        jLabel__nyckel_tal__tot_inkl_moms = new javax.swing.JLabel();
        jLabel__nyckel_tal__tot_exkl_moms = new javax.swing.JLabel();
        jLabel__nyckel_tal__antal_fakturor = new javax.swing.JLabel();
        jLabel_nyckel_tal__info_label = new javax.swing.JLabel();
        jScrollPane1_faktura = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2_faktura_main = new javax.swing.JPanel();
        jPanel_articles = new JPanel();
        jPanel3_faktura_sec = new javax.swing.JPanel();
        jLabel_Faktura_Insert_or_Update = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_InvoiceA_Insert_articles = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jButton_create_new_faktura_b = new javax.swing.JButton();
        jButton_create_new_kontant_faktura_b = new javax.swing.JButton();
        jButton_dont_save_settings = new javax.swing.JButton();
        jButton_confirm_insert_update = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jButton_add_article = new javax.swing.JButton();
        jButton_update_articles_row = new javax.swing.JButton();
        jButton_delete_articles_row = new javax.swing.JButton();
        jLabel5_separator = new javax.swing.JLabel();
        jLabel_ammount_of_articles_ = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel_rut_total = new javax.swing.JLabel();
        jLabel_rut_avdrag = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField_rut_total = new javax.swing.JTextField();
        jTextField_rut_avdrag = new javax.swing.JTextField();
        jTextField_frakt = new javax.swing.JTextField();
        jTextField_exp_avg = new javax.swing.JTextField();
        jTextField_moms_frakt_expavg = new javax.swing.JTextField();
        jTextField_moms_artiklar = new javax.swing.JTextField();
        jTextField_moms_sats_frakt_exp_avg = new javax.swing.JTextField();
        jTextField_total_exkl_moms = new javax.swing.JTextField();
        jTextField_rabatt_total = new javax.swing.JTextField();
        jTextField_moms_total = new javax.swing.JTextField();
        jTextField_total_inkl_moms = new javax.swing.JTextField();
        jScrollPane19 = new javax.swing.JScrollPane();
        jPanel24 = new javax.swing.JPanel();
        jPanel_graph_panel_a = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel_graph_panel_b = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel_graph_panel_c = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel_graph_panel_d = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jPanel4_Customers = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable_kunder = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable_kund_adresses = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jButton13 = new javax.swing.JButton();
        jButton_update_kund = new javax.swing.JButton();
        jButton_delete_customer = new javax.swing.JButton();
        jLabel_Kund_Insert_or_Update = new javax.swing.JLabel();
        jCheckBox__person = new javax.swing.JCheckBox();
        jButton3 = new javax.swing.JButton();
        jComboBox_customers_a__tab = new JComboBoxA();
        jPanel4_Articles = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable_ArticlesA_articles = new javax.swing.JTable();
        jLabel_Artikel_Insert_or_Update = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jButton16 = new javax.swing.JButton();
        jButton_update_article = new javax.swing.JButton();
        jButton_delete_article = new javax.swing.JButton();
        jComboBox_articles_a__tab = new JComboBoxA();
        jButton_articles_a__tab__search_btn = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jScrollPane10 = new javax.swing.JScrollPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable_ftg = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTable_ftg_addr = new javax.swing.JTable();
        jButton_update_kund_data = new javax.swing.JButton();
        jScrollPane13 = new javax.swing.JScrollPane();
        jPanel20 = new javax.swing.JPanel();
        jPanel_email_client_options = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jButton_save_smtp_settings = new javax.swing.JButton();
        jButton_test_smtp = new javax.swing.JButton();
        jButton_delete_smtp_settings = new javax.swing.JButton();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTextArea_reminder_message = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jButton_save_reminder_msg = new javax.swing.JButton();
        jButton_delete_reminder_msg = new javax.swing.JButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane16 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton_erase_account_btn = new javax.swing.JButton();
        jScrollPane18 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 1));

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel_inloggning.setLayout(new java.awt.BorderLayout());

        jButton_logg_in.setText("Logga in");
        jButton_logg_in.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_logg_inActionPerformed(evt);
            }
        });

        jCheckBox_spara_inloggning.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox_spara_inloggning.setText("Spara Inloggning");
        jCheckBox_spara_inloggning.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_spara_inloggningActionPerformed(evt);
            }
        });

        jLabel_inloggning.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_inloggning.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_inloggning.setText("Inloggning");

        jPanel_register_new.setLayout(new java.awt.BorderLayout());

        jLabel_register_new.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_register_new.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_register_new.setText("Saknar du konto? Registrera ett");

        jButton_register_new_user.setText("Registrera");
        jButton_register_new_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_register_new_userActionPerformed(evt);
            }
        });

        jPanel_restore_password.setLayout(new java.awt.BorderLayout());

        jLabel_restore_password.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_restore_password.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_restore_password.setText("Glömt lösenord?");

        jButton_forgot_password.setText("Återställ");
        jButton_forgot_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_forgot_passwordActionPerformed(evt);
            }
        });

        jPanel_share_account.setLayout(new java.awt.BorderLayout());

        jLabel_share_account.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_share_account.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_share_account.setText("Dela konto med en annan");

        jButton_share_account.setText("Dela");
        jButton_share_account.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_share_accountActionPerformed(evt);
            }
        });

        jTable_shared_users.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane17.setViewportView(jTable_shared_users);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Du delar konto med följande användare");

        jButton_delete_account_sharing.setText("Radera");
        jButton_delete_account_sharing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_delete_account_sharingActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 3, 48)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 250));
        jLabel16.setText("LAFakturering");

        jLabel17_new__version.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17_new__version.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jButton_delete_account_sharing)
                            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel_share_account, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                                .addComponent(jPanel_restore_password, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                                .addComponent(jPanel_inloggning, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                                .addGroup(jPanel19Layout.createSequentialGroup()
                                    .addComponent(jButton_logg_in)
                                    .addGap(18, 18, 18)
                                    .addComponent(jCheckBox_spara_inloggning))
                                .addComponent(jPanel_register_new, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                                .addComponent(jLabel_inloggning, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton_register_new_user)
                                .addComponent(jLabel_register_new, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton_forgot_password)
                                .addComponent(jButton_share_account, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane17)
                                .addComponent(jLabel_restore_password, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel_share_account, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap(898, Short.MAX_VALUE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel17_new__version, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17_new__version, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_inloggning)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_inloggning, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_logg_in)
                    .addComponent(jCheckBox_spara_inloggning))
                .addGap(25, 25, 25)
                .addComponent(jLabel_register_new)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_register_new, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButton_register_new_user)
                .addGap(25, 25, 25)
                .addComponent(jLabel_restore_password)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_restore_password, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButton_forgot_password)
                .addGap(25, 25, 25)
                .addComponent(jLabel_share_account)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_share_account, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButton_share_account)
                .addGap(25, 25, 25)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButton_delete_account_sharing)
                .addContainerGap(293, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel19);

        jTabbedPane1.addTab("HEM", jScrollPane1);

        jScrollPane3.setPreferredSize(new java.awt.Dimension(1290, 500));

        jPanel3.setPreferredSize(new java.awt.Dimension(1288, 1000));

        jTable_invoiceB_alla_fakturor.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(jTable_invoiceB_alla_fakturor);

        jTable_invoiceB_faktura_artiklar.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(jTable_invoiceB_faktura_artiklar);

        jPanel4.setLayout(new java.awt.GridLayout(1, 8));

        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh.png"))); // NOI18N
        jButton15.setToolTipText("Uppdatera listan");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton15);

        jButton_edit_faktura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit.png"))); // NOI18N
        jButton_edit_faktura.setToolTipText("Bearbeta faktura");
        jButton_edit_faktura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_edit_fakturaActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_edit_faktura);

        jButton_create_new_faktura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new.png"))); // NOI18N
        jButton_create_new_faktura.setToolTipText("Skapa ny faktura");
        jButton_create_new_faktura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_create_new_fakturaActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_create_new_faktura);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new_c.png"))); // NOI18N
        jButton2.setToolTipText("Skapa ny offert");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton2);

        jButton_kontant_faktura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/kontantfaktura.png"))); // NOI18N
        jButton_kontant_faktura.setToolTipText("Skapa ny kontantfaktura");
        jButton_kontant_faktura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_kontant_fakturaActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_kontant_faktura);

        jButton_kredit_faktura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new_b.png"))); // NOI18N
        jButton_kredit_faktura.setToolTipText("Skapa kreditfaktura - välj först en faktura ur listan och tryck sedan på knappen");
        jButton_kredit_faktura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_kredit_fakturaActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_kredit_faktura);

        jButton_copy_faktura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/copy.png"))); // NOI18N
        jButton_copy_faktura.setToolTipText("");
        jButton_copy_faktura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_copy_fakturaActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_copy_faktura);

        jButton_delete_faktura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton_delete_faktura.setToolTipText("Radera faktura");
        jButton_delete_faktura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_delete_fakturaActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_delete_faktura);

        jButton_print_faktura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/print_send.png"))); // NOI18N
        jButton_print_faktura.setToolTipText("Skriv ut eller skicka faktura");
        jButton_print_faktura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_print_fakturaActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_print_faktura);

        jButton_send_reminder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bell.png"))); // NOI18N
        jButton_send_reminder.setToolTipText("Skicka påminnelse");
        jButton_send_reminder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_send_reminderActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_send_reminder);

        jButton_inbetalning.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/payed.png"))); // NOI18N
        jButton_inbetalning.setToolTipText("Registrera inbetalning");
        jButton_inbetalning.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_inbetalningActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_inbetalning);

        jButton_show_actions.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/event.png"))); // NOI18N
        jButton_show_actions.setToolTipText("Se historia över skickade e-post och påminnelser");
        jButton_show_actions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_show_actionsActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_show_actions);

        jLabel_all_invoices_list.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_all_invoices_list.setForeground(new java.awt.Color(153, 153, 153));
        jLabel_all_invoices_list.setText("FAKTUROR");

        jLabel_all_invoices_list1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_all_invoices_list1.setForeground(new java.awt.Color(153, 153, 153));
        jLabel_all_invoices_list1.setText("FAKTURA ARTIKLAR");

        jTextArea_faktura_komment.setColumns(20);
        jTextArea_faktura_komment.setForeground(new java.awt.Color(102, 102, 102));
        jTextArea_faktura_komment.setLineWrap(true);
        jTextArea_faktura_komment.setRows(2);
        jTextArea_faktura_komment.setToolTipText("Skriv faktura kommentar här");
        jTextArea_faktura_komment.setWrapStyleWord(true);

        jButton4_save_faktura_komment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ok_sm.png"))); // NOI18N
        jButton4_save_faktura_komment.setToolTipText("Uppdatera faktura kommentar");
        jButton4_save_faktura_komment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4_save_faktura_kommentActionPerformed(evt);
            }
        });

        jButton4_delete_faktura_komment1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/close_sm.png"))); // NOI18N
        jButton4_delete_faktura_komment1.setToolTipText("Radera faktura kommentar");
        jButton4_delete_faktura_komment1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4_delete_faktura_komment1ActionPerformed(evt);
            }
        });

        jPanel18.setLayout(new java.awt.GridLayout(1, 4));

        jToggleButton_obetald_filter.setForeground(new java.awt.Color(102, 102, 102));
        jToggleButton_obetald_filter.setText("OBETALDA");
        jToggleButton_obetald_filter.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jToggleButton_obetald_filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_obetald_filterActionPerformed(evt);
            }
        });
        jPanel18.add(jToggleButton_obetald_filter);

        jToggleButton_delvis_betald_filter.setForeground(new java.awt.Color(102, 102, 102));
        jToggleButton_delvis_betald_filter.setText("VISA ALLA");
        jToggleButton_delvis_betald_filter.setToolTipText("Visar samtliga fakturor");
        jToggleButton_delvis_betald_filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_delvis_betald_filterActionPerformed(evt);
            }
        });
        jPanel18.add(jToggleButton_delvis_betald_filter);

        jToggleButton_forfallen_filter.setForeground(new java.awt.Color(102, 102, 102));
        jToggleButton_forfallen_filter.setText("FÖRFALLNA");
        jToggleButton_forfallen_filter.setToolTipText("Visar förfallna, ej betalda, ej makulerade fakturor av typen NORMAL");
        jToggleButton_forfallen_filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_forfallen_filterActionPerformed(evt);
            }
        });
        jPanel18.add(jToggleButton_forfallen_filter);

        jToggleButton_not_send_filter.setForeground(new java.awt.Color(102, 102, 102));
        jToggleButton_not_send_filter.setText("EJ SKICKADE");
        jToggleButton_not_send_filter.setToolTipText("Visar obetalda fakturor av typen NORMAL");
        jToggleButton_not_send_filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_not_send_filterActionPerformed(evt);
            }
        });
        jPanel18.add(jToggleButton_not_send_filter);

        jToggleButton_makulerad_filter.setForeground(new java.awt.Color(102, 102, 102));
        jToggleButton_makulerad_filter.setText("MAKULERADE");
        jToggleButton_makulerad_filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_makulerad_filterActionPerformed(evt);
            }
        });
        jPanel18.add(jToggleButton_makulerad_filter);

        jToggleButton_act_month_filter.setForeground(new java.awt.Color(102, 102, 102));
        jToggleButton_act_month_filter.setText("AKT. MÅNAD");
        jToggleButton_act_month_filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_act_month_filterActionPerformed(evt);
            }
        });
        jPanel18.add(jToggleButton_act_month_filter);

        jComboBox_faktura_kunder_filter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));

        jButton_search_by_kund.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search.png"))); // NOI18N
        jButton_search_by_kund.setToolTipText("Sök");
        jButton_search_by_kund.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_search_by_kundActionPerformed(evt);
            }
        });

        jLabel_faktura_changed_by__user.setForeground(new java.awt.Color(153, 153, 153));

        jPanel23.setLayout(new java.awt.GridLayout(10, 1));

        jLabel_info__forfallen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bell.png"))); // NOI18N
        jLabel_info__forfallen.setToolTipText("Förfallen");
        jPanel23.add(jLabel_info__forfallen);

        jLabel_info_is_person.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/person.png"))); // NOI18N
        jLabel_info_is_person.setToolTipText("Privatperson (Ej företag)");
        jPanel23.add(jLabel_info_is_person);

        jLabel_info_rut__or_omvant_skatt.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_info_rut__or_omvant_skatt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/rut.png"))); // NOI18N
        jLabel_info_rut__or_omvant_skatt.setToolTipText("");
        jPanel23.add(jLabel_info_rut__or_omvant_skatt);

        jLabel_info__betald.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/payed.png"))); // NOI18N
        jLabel_info__betald.setToolTipText("Betald");
        jPanel23.add(jLabel_info__betald);

        jLabel_info__printed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        jLabel_info__printed.setToolTipText("Utskriven");
        jPanel23.add(jLabel_info__printed);

        jLabel_info__sent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/send_b.png"))); // NOI18N
        jLabel_info__sent.setToolTipText("Skickad");
        jPanel23.add(jLabel_info__sent);

        jLabel_info__kontant_faktura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/kontantfaktura.png"))); // NOI18N
        jLabel_info__kontant_faktura.setToolTipText("Kontantfaktura");
        jPanel23.add(jLabel_info__kontant_faktura);

        jLabel_info__kredit_faktura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new_b.png"))); // NOI18N
        jLabel_info__kredit_faktura.setToolTipText("Kreditfaktura");
        jPanel23.add(jLabel_info__kredit_faktura);

        jLabel_info__offert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new_c.png"))); // NOI18N
        jLabel_info__offert.setToolTipText("Offert");
        jPanel23.add(jLabel_info__offert);

        jLabel_info__makulerad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/makulerad.png"))); // NOI18N
        jLabel_info__makulerad.setToolTipText("Makulerad");
        jPanel23.add(jLabel_info__makulerad);

        jPanel25.setLayout(new java.awt.GridLayout(2, 4));

        jLabel_nycke_tal__ing_moms.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_nycke_tal__ing_moms.setForeground(new java.awt.Color(102, 102, 102));
        jLabel_nycke_tal__ing_moms.setText("UTGÅENDE MOMS");
        jPanel25.add(jLabel_nycke_tal__ing_moms);

        jLabel_nycke_tal__total_inkl_moms.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_nycke_tal__total_inkl_moms.setForeground(new java.awt.Color(102, 102, 102));
        jLabel_nycke_tal__total_inkl_moms.setText("TOTAL INKL. MOMS");
        jPanel25.add(jLabel_nycke_tal__total_inkl_moms);

        jLabel_nycke_tal__total_exkl_moms.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_nycke_tal__total_exkl_moms.setForeground(new java.awt.Color(102, 102, 102));
        jLabel_nycke_tal__total_exkl_moms.setText("TOTAL EXKL. MOMS");
        jPanel25.add(jLabel_nycke_tal__total_exkl_moms);

        jLabel_nycke_tal__antal_fakturor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_nycke_tal__antal_fakturor.setForeground(new java.awt.Color(102, 102, 102));
        jLabel_nycke_tal__antal_fakturor.setText("ANTAL FAKTUROR");
        jPanel25.add(jLabel_nycke_tal__antal_fakturor);

        jLabel__nyckel_tal__ing_moms.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel__nyckel_tal__ing_moms.setForeground(new java.awt.Color(102, 102, 102));
        jPanel25.add(jLabel__nyckel_tal__ing_moms);

        jLabel__nyckel_tal__tot_inkl_moms.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel__nyckel_tal__tot_inkl_moms.setForeground(new java.awt.Color(102, 102, 102));
        jPanel25.add(jLabel__nyckel_tal__tot_inkl_moms);

        jLabel__nyckel_tal__tot_exkl_moms.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel__nyckel_tal__tot_exkl_moms.setForeground(new java.awt.Color(102, 102, 102));
        jPanel25.add(jLabel__nyckel_tal__tot_exkl_moms);

        jLabel__nyckel_tal__antal_fakturor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel__nyckel_tal__antal_fakturor.setForeground(new java.awt.Color(102, 102, 102));
        jPanel25.add(jLabel__nyckel_tal__antal_fakturor);

        jLabel_nyckel_tal__info_label.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_nyckel_tal__info_label.setForeground(new java.awt.Color(153, 153, 153));
        jLabel_nyckel_tal__info_label.setText("Sedan årsskiftet:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel_faktura_changed_by__user, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(606, 606, 606)
                                    .addComponent(jLabel_all_invoices_list1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextArea_faktura_komment, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton4_save_faktura_komment, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2)
                                        .addComponent(jButton4_delete_faktura_komment1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 726, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(jComboBox_faktura_kunder_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton_search_by_kund, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel_all_invoices_list, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(5, 5, 5)
                        .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel_nyckel_tal__info_label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE))))
                .addGap(178, 178, 178))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4_save_faktura_komment, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextArea_faktura_komment, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4_delete_faktura_komment1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox_faktura_kunder_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton_search_by_kund, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel_all_invoices_list, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_all_invoices_list1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_faktura_changed_by__user, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 378, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel_nyckel_tal__info_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(155, 155, 155))
        );

        jScrollPane3.setViewportView(jPanel3);

        jTabbedPane1.addTab("ALLA FAKTUROR", jScrollPane3);

        jPanel1.setPreferredSize(new java.awt.Dimension(1286, 1000));

        jPanel2_faktura_main.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2_faktura_main.setLayout(new java.awt.BorderLayout());

        jPanel_articles.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel_articles.setLayout(new java.awt.BorderLayout());

        jPanel3_faktura_sec.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3_faktura_sec.setLayout(new java.awt.GridLayout(1, 0));

        jLabel_Faktura_Insert_or_Update.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel_Faktura_Insert_or_Update.setForeground(new java.awt.Color(153, 153, 153));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new java.awt.GridLayout(1, 1));

        jTable_InvoiceA_Insert_articles.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable_InvoiceA_Insert_articles);

        jPanel2.add(jScrollPane2);

        jPanel9.setLayout(new java.awt.GridLayout(1, 0));

        jButton_create_new_faktura_b.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new.png"))); // NOI18N
        jButton_create_new_faktura_b.setToolTipText("Skapa ny faktura");
        jButton_create_new_faktura_b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_create_new_faktura_bActionPerformed(evt);
            }
        });
        jPanel9.add(jButton_create_new_faktura_b);

        jButton_create_new_kontant_faktura_b.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/kontantfaktura.png"))); // NOI18N
        jButton_create_new_kontant_faktura_b.setToolTipText("Skapa ny kontantfaktura");
        jButton_create_new_kontant_faktura_b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_create_new_kontant_faktura_bActionPerformed(evt);
            }
        });
        jPanel9.add(jButton_create_new_kontant_faktura_b);

        jButton_dont_save_settings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton_dont_save_settings.setToolTipText("Lämna utan att spara");
        jButton_dont_save_settings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_dont_save_settingsActionPerformed(evt);
            }
        });
        jPanel9.add(jButton_dont_save_settings);

        jButton_confirm_insert_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton_confirm_insert_update.setToolTipText("Spara faktura");
        jButton_confirm_insert_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_confirm_insert_updateActionPerformed(evt);
            }
        });
        jPanel9.add(jButton_confirm_insert_update);

        jPanel10.setLayout(new java.awt.GridLayout(1, 0));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/print_preview.png"))); // NOI18N
        jButton1.setToolTipText("Förhandsgranska");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton1);

        jPanel11.setLayout(new java.awt.GridLayout(1, 0));

        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit.png"))); // NOI18N
        jButton19.setToolTipText("Bearbeta artikel");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton19);

        jButton20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton20.setToolTipText("Bekräfta ändringar");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton20);

        jPanel12.setLayout(new java.awt.GridLayout(1, 3));

        jButton_add_article.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButton_add_article.setToolTipText("Lägg till artikel");
        jButton_add_article.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_add_articleActionPerformed(evt);
            }
        });
        jPanel12.add(jButton_add_article);

        jButton_update_articles_row.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton_update_articles_row.setToolTipText("Uppdatera artikel (gör ändringar i tabellen till vänster)");
        jButton_update_articles_row.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_update_articles_rowActionPerformed(evt);
            }
        });
        jPanel12.add(jButton_update_articles_row);

        jButton_delete_articles_row.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton_delete_articles_row.setToolTipText("Radera artikel");
        jButton_delete_articles_row.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_delete_articles_rowActionPerformed(evt);
            }
        });
        jPanel12.add(jButton_delete_articles_row);
        jPanel12.add(jLabel5_separator);

        jLabel_ammount_of_articles_.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel_ammount_of_articles_.setForeground(new java.awt.Color(153, 153, 153));
        jLabel_ammount_of_articles_.setToolTipText("Antal artiklar");
        jLabel_ammount_of_articles_.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel12.add(jLabel_ammount_of_articles_);

        jPanel15.setLayout(new java.awt.GridLayout(2, 12, 5, 0));

        jLabel_rut_total.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_rut_total.setForeground(new java.awt.Color(102, 102, 102));
        jLabel_rut_total.setText("RUT-Total");
        jPanel15.add(jLabel_rut_total);

        jLabel_rut_avdrag.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_rut_avdrag.setForeground(new java.awt.Color(102, 102, 102));
        jLabel_rut_avdrag.setText("RUT-Avdrag");
        jPanel15.add(jLabel_rut_avdrag);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setText("Frakt");
        jPanel15.add(jLabel13);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setText("Exp. Avg");
        jPanel15.add(jLabel12);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Moms frakt");
        jLabel6.setToolTipText("Moms på frakt och avgifter");
        jPanel15.add(jLabel6);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Moms artiklar");
        jPanel15.add(jLabel5);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Moms frakt %");
        jPanel15.add(jLabel7);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Exkl. moms");
        jPanel15.add(jLabel2);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(103, 103, 103));
        jLabel1.setText("Rabatt ");
        jPanel15.add(jLabel1);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Moms total");
        jPanel15.add(jLabel3);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Att betala");
        jPanel15.add(jLabel4);

        jTextField_rut_total.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_rut_total.setText("0");
        jPanel15.add(jTextField_rut_total);

        jTextField_rut_avdrag.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_rut_avdrag.setText("0");
        jPanel15.add(jTextField_rut_avdrag);

        jTextField_frakt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_frakt.setText("0");
        jPanel15.add(jTextField_frakt);

        jTextField_exp_avg.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_exp_avg.setText("0");
        jPanel15.add(jTextField_exp_avg);

        jTextField_moms_frakt_expavg.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_moms_frakt_expavg.setText("0");
        jTextField_moms_frakt_expavg.setToolTipText("Moms på frakt och exp. avg");
        jPanel15.add(jTextField_moms_frakt_expavg);

        jTextField_moms_artiklar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_moms_artiklar.setText("0");
        jPanel15.add(jTextField_moms_artiklar);

        jTextField_moms_sats_frakt_exp_avg.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_moms_sats_frakt_exp_avg.setText("0");
        jPanel15.add(jTextField_moms_sats_frakt_exp_avg);

        jTextField_total_exkl_moms.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_total_exkl_moms.setText("0");
        jTextField_total_exkl_moms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_total_exkl_momsActionPerformed(evt);
            }
        });
        jPanel15.add(jTextField_total_exkl_moms);

        jTextField_rabatt_total.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_rabatt_total.setText("0");
        jPanel15.add(jTextField_rabatt_total);

        jTextField_moms_total.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_moms_total.setText("0");
        jPanel15.add(jTextField_moms_total);

        jTextField_total_inkl_moms.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_total_inkl_moms.setText("0");
        jTextField_total_inkl_moms.setToolTipText("Frakt + Exp.Avg + Moms frakt + Moms artiklar + Exkl. moms");
        jPanel15.add(jTextField_total_inkl_moms);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 1186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel2_faktura_main, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
                            .addComponent(jPanel_articles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3_faktura_sec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel_Faktura_Insert_or_Update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(66, 66, 66))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel_Faktura_Insert_or_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2_faktura_main, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3_faktura_sec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(5, 5, 5)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel_articles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1520, 1520, 1520))
        );

        jScrollPane1_faktura.setViewportView(jPanel1);

        jTabbedPane1.addTab("FAKTURA", jScrollPane1_faktura);

        jPanel_graph_panel_a.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel_graph_panel_a.setLayout(new java.awt.GridLayout(1, 0));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(153, 153, 153));
        jLabel10.setText("Alla fakturor (föregående tolv månader)");

        jPanel_graph_panel_b.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel_graph_panel_b.setLayout(new java.awt.GridLayout(1, 0));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(153, 153, 153));
        jLabel11.setText("Fakturor - aktuell månad");

        jPanel_graph_panel_c.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel_graph_panel_c.setLayout(new java.awt.GridLayout(1, 0));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(153, 153, 153));
        jLabel14.setText("Total per månad (föregående tolv månader)");

        jPanel_graph_panel_d.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel_graph_panel_d.setLayout(new java.awt.GridLayout(1, 0));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(153, 153, 153));
        jLabel17.setText("Antal fakturor per månad (föregående tolv månader)");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(696, 696, 696))
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addComponent(jPanel_graph_panel_b, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(12, 12, 12)))
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel_graph_panel_d, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel_graph_panel_a, javax.swing.GroupLayout.PREFERRED_SIZE, 878, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel_graph_panel_c, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel14))
                .addGap(10, 10, 10)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel_graph_panel_c, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel_graph_panel_a, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE))
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel11)
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel_graph_panel_d, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel_graph_panel_b, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE))
                .addContainerGap())
        );

        jScrollPane19.setViewportView(jPanel24);

        jTabbedPane1.addTab("GRAFISK VY", jScrollPane19);

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jTable_kunder.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(jTable_kunder);

        jTable_kund_adresses.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(jTable_kund_adresses);

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel7.setPreferredSize(new java.awt.Dimension(555, 416));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel13.setLayout(new java.awt.GridLayout(1, 0));

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new.png"))); // NOI18N
        jButton13.setToolTipText("Skapa ny");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton13);

        jButton_update_kund.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton_update_kund.setToolTipText("Uppdatera faktura kund data");
        jButton_update_kund.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_update_kundActionPerformed(evt);
            }
        });
        jPanel13.add(jButton_update_kund);

        jButton_delete_customer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton_delete_customer.setToolTipText("Radera kund");
        jButton_delete_customer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_delete_customerActionPerformed(evt);
            }
        });
        jPanel13.add(jButton_delete_customer);

        jLabel_Kund_Insert_or_Update.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_Kund_Insert_or_Update.setForeground(new java.awt.Color(153, 153, 153));

        jCheckBox__person.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBox__person.setText("Kunden är en privatperson");
        jCheckBox__person.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox__personActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jComboBox_customers_a__tab.setModel(new javax.swing.DefaultComboBoxModel<>());

        javax.swing.GroupLayout jPanel4_CustomersLayout = new javax.swing.GroupLayout(jPanel4_Customers);
        jPanel4_Customers.setLayout(jPanel4_CustomersLayout);
        jPanel4_CustomersLayout.setHorizontalGroup(
            jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4_CustomersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4_CustomersLayout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBox__person, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
                    .addComponent(jScrollPane8)
                    .addGroup(jPanel4_CustomersLayout.createSequentialGroup()
                        .addComponent(jLabel_Kund_Insert_or_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox_customers_a__tab, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );
        jPanel4_CustomersLayout.setVerticalGroup(
            jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4_CustomersLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Kund_Insert_or_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox__person)
                    .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jComboBox_customers_a__tab, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4_CustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(142, 142, 142))
        );

        jScrollPane9.setViewportView(jPanel4_Customers);

        jTabbedPane1.addTab("KUNDER", jScrollPane9);

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jTable_ArticlesA_articles.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(jTable_ArticlesA_articles);

        jLabel_Artikel_Insert_or_Update.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_Artikel_Insert_or_Update.setForeground(new java.awt.Color(153, 153, 153));

        jPanel14.setLayout(new java.awt.GridLayout(1, 4));

        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new.png"))); // NOI18N
        jButton16.setToolTipText("Skapa ny artikel");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton16);

        jButton_update_article.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton_update_article.setToolTipText("Uppdatera / lägga till artikel");
        jButton_update_article.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_update_articleActionPerformed(evt);
            }
        });
        jPanel14.add(jButton_update_article);

        jButton_delete_article.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton_delete_article.setToolTipText("Radera artikel");
        jButton_delete_article.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_delete_articleActionPerformed(evt);
            }
        });
        jPanel14.add(jButton_delete_article);

        jComboBox_articles_a__tab.setModel(new javax.swing.DefaultComboBoxModel<>());

        jButton_articles_a__tab__search_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search.png"))); // NOI18N
        jButton_articles_a__tab__search_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_articles_a__tab__search_btnActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Artikelnr");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4_ArticlesLayout = new javax.swing.GroupLayout(jPanel4_Articles);
        jPanel4_Articles.setLayout(jPanel4_ArticlesLayout);
        jPanel4_ArticlesLayout.setHorizontalGroup(
            jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4_ArticlesLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4_ArticlesLayout.createSequentialGroup()
                        .addComponent(jLabel_Artikel_Insert_or_Update, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox_articles_a__tab, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_articles_a__tab__search_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );
        jPanel4_ArticlesLayout.setVerticalGroup(
            jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4_ArticlesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox_articles_a__tab, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox1))
                        .addComponent(jButton_articles_a__tab__search_btn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel_Artikel_Insert_or_Update, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4_ArticlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(187, 187, 187))
        );

        jTabbedPane1.addTab("ARTIKLAR", jPanel4_Articles);

        jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel16.setLayout(new java.awt.BorderLayout());

        jTable_ftg.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane11.setViewportView(jTable_ftg);

        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel17.setLayout(new java.awt.BorderLayout());

        jTable_ftg_addr.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane12.setViewportView(jTable_ftg_addr);

        jButton_update_kund_data.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton_update_kund_data.setToolTipText("Uppdatera kund data");
        jButton_update_kund_data.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_update_kund_dataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton_update_kund_data, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(241, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jButton_update_kund_data, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE))
                .addGap(50, 50, 50))
        );

        jPanel16.getAccessibleContext().setAccessibleDescription("");

        jScrollPane10.setViewportView(jPanel8);

        jTabbedPane1.addTab("FÖRETAGS INSTÄLLNINGAR", jScrollPane10);

        jPanel_email_client_options.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel_email_client_options.setLayout(new java.awt.BorderLayout());

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(153, 153, 153));
        jLabel8.setText("E-Post SMTP intällningar");

        jPanel21.setLayout(new java.awt.GridLayout(1, 0));

        jButton_save_smtp_settings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton_save_smtp_settings.setToolTipText("Spara e-post SMTP inställningar");
        jButton_save_smtp_settings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_save_smtp_settingsActionPerformed(evt);
            }
        });
        jPanel21.add(jButton_save_smtp_settings);

        jButton_test_smtp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/send_b.png"))); // NOI18N
        jButton_test_smtp.setToolTipText("Testa intsällningar, en test e-post skickas till angiven adress");
        jButton_test_smtp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_test_smtpActionPerformed(evt);
            }
        });
        jPanel21.add(jButton_test_smtp);

        jButton_delete_smtp_settings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton_delete_smtp_settings.setToolTipText("Radera SMTP inställningar");
        jButton_delete_smtp_settings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_delete_smtp_settingsActionPerformed(evt);
            }
        });
        jPanel21.add(jButton_delete_smtp_settings);

        jTextArea_reminder_message.setColumns(20);
        jTextArea_reminder_message.setRows(5);
        jScrollPane14.setViewportView(jTextArea_reminder_message);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 153, 153));
        jLabel9.setText("Påmminelse text");

        jPanel22.setLayout(new java.awt.GridLayout(1, 0));

        jButton_save_reminder_msg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/correct.png"))); // NOI18N
        jButton_save_reminder_msg.setToolTipText("Spara meddelande som kommer visas i påminnelsen");
        jButton_save_reminder_msg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_save_reminder_msgActionPerformed(evt);
            }
        });
        jPanel22.add(jButton_save_reminder_msg);

        jButton_delete_reminder_msg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-1.png"))); // NOI18N
        jButton_delete_reminder_msg.setToolTipText("Återställ påminnelse");
        jButton_delete_reminder_msg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_delete_reminder_msgActionPerformed(evt);
            }
        });
        jPanel22.add(jButton_delete_reminder_msg);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Här till vänster ändrar du texten för påminnelsen. Om du skulle vilja ha \nfakturanummer med i texten så skriver du %s. Det ersätts av\nfakturanummer när du skriver ut eller skickar påminnelsen.\n\nOm du vill återställa standard text då trycker du på \"kryss\".\n");
        jScrollPane15.setViewportView(jTextArea1);

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setText("SMTP E-post inställningar tillåter dig att skicka e-post från\nden e-post du själv anger. Om du inte anger din egen e-post\nså skickas alla e-post genom vår standard server.\n\nKomm också ihåg att kan alltid skicka fakturor via din lokala\ne-post klient, exemplevis Outlook.\n\nOm du undrar var du hittar de inställningarna så gör man det enklast\ngenom att googla på \"smtp inställningar\" för din e-post leverantör. \n\nObservera att visa e-post leverantörer tillåter inte SMTP \nanvändning.\n");
        jTextArea2.setWrapStyleWord(true);
        jScrollPane16.setViewportView(jTextArea2);

        jButton_erase_account_btn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_erase_account_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/warning.png"))); // NOI18N
        jButton_erase_account_btn.setText("RADERA KONTO");
        jButton_erase_account_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_erase_account_btnActionPerformed(evt);
            }
        });

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jTextArea3.setText("Du kan när som helst radera ditt konto och all relaterat data hos LAFakturering.\nDu kommer att behöva ange lösenordet du använder vid inloggning för\natt bekräfta borttagningen.\n\nOBS! All data kommer att raderas permanent\n\nAtt radera kontot är ej möjligt från en gästkonto.");
        jScrollPane18.setViewportView(jTextArea3);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 363, Short.MAX_VALUE)
                        .addComponent(jLabel8))
                    .addComponent(jPanel_email_client_options, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jButton_erase_account_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)))
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                    .addComponent(jScrollPane16)
                    .addComponent(jScrollPane18))
                .addContainerGap(154, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane16)
                    .addComponent(jPanel_email_client_options, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane15)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
                .addGap(50, 50, 50)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton_erase_account_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(491, Short.MAX_VALUE))
        );

        jScrollPane13.setViewportView(jPanel20);

        jTabbedPane1.addTab("ÖVRIGA INSTÄLLNINGAR", jScrollPane13);

        getContentPane().add(jTabbedPane1);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jButton_delete_fakturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_delete_fakturaActionPerformed
        //
        if (HelpA.rowSelected(jTable_invoiceB_alla_fakturor) == false) {
            return;
        }
        //
        if (GP_BUH.confirmWarning(LANG.MSG_3_1(isOffert())) == false) {
            return;
        }
        //
        deleteFaktura(getFakturaId());
        //
    }//GEN-LAST:event_jButton_delete_fakturaActionPerformed


    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        customersA.createNewFakturaKund();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton_update_kundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_update_kundActionPerformed
        if (customersA.getCurrentOperationInsert()) {
            if (customersA.fieldsValidated(true)) {
                customersA.insert();
            }
        } else {
            if (customersA.fieldsValidated(false)) {
                customersA.update();
            }
        }
    }//GEN-LAST:event_jButton_update_kundActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        //"Refresh btn"
        //
        SET_SEARCH_FILTER(PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FILTER, true);
        //
        invoiceB.refresh(null);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton_delete_customerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_delete_customerActionPerformed
        //
        if (HelpA.rowSelected(jTable_kunder) == false) {
            return;
        }
        //
        customersA.delete();
        invoiceB.refresh(null);
    }//GEN-LAST:event_jButton_delete_customerActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        articlesA.createNew();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton_delete_articleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_delete_articleActionPerformed
        //
//        if (GP_BUH.confirmWarning(LANG.MSG_4) == false) {
//            return;
//        }
        //
        articlesA.delete();
    }//GEN-LAST:event_jButton_delete_articleActionPerformed

    private void jButton_update_articleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_update_articleActionPerformed
        if (articlesA.getCurrentOperationInsert()) {
            if (articlesA.fieldsValidated(true)) {
                //
                articlesA.insert();
                //
                articlesA.createNew(); // To be able to create several in row without clicking new each time [2020-10-01]
                //
            }
        } else {
            if (articlesA.fieldsValidated(false)) {
                articlesA.update();
            }
        }

    }//GEN-LAST:event_jButton_update_articleActionPerformed

    private void jButton_edit_fakturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_edit_fakturaActionPerformed
        editFakturaBtnKlicked();
    }//GEN-LAST:event_jButton_edit_fakturaActionPerformed

    public void editFakturaBtnKlicked() {
        openTabByName(TAB_FAKTURA);
        fakturaTabClicked(getFakturaNr());
    }

    @Override
    public void goToFaktura(String fakturaNr) {
//        String fakturaNr = "1"; // this should be sent as a parameter later
        openTabByName(TAB_INVOICES_OVERVIEW);
        HelpA.markRowByValue(jTable_invoiceB_alla_fakturor, InvoiceB.TABLE_ALL_INVOICES__FAKTURANR, fakturaNr);
        changeToFakturaWithsync();
    }


    private void jButton_print_fakturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_print_fakturaActionPerformed
        //
        if (isMakulerad()) {
            HelpA.showNotification_separate_thread(LANG.MSG_9);
        }
        //
        String fakturatype = getFakturaType();
//        System.out.println("AA " + fakturaTyp);
        invoiceB.htmlFakturaOrReminder(fakturatype, false);
    }//GEN-LAST:event_jButton_print_fakturaActionPerformed

    private void jButton_delete_articles_rowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_delete_articles_rowActionPerformed
        //
        if (HelpA.rowSelected(jTable_InvoiceA_Insert_articles) == false) {
            return;
        }
        //
        if (InvoiceA_Insert_.CURRENT_OPERATION_INSERT) {
            //
            invoiceA_insert.deleteArtikel();
            //
            invoiceA_insert.showTableInvert_3(); // some kind of redraw, OBS! needed -> see: "Invoice.disableMomsJComboIf()"
            //
        } else {
            //
            invoiceA_update.deleteFakturaArtikel();
            invoiceA_update.deselectRowArticlesTable(); // Deselecting due to "InvoiceA_Updte->getConfigTableInvert_2() -> articlesJTableRowSelected()"
            invoiceA_update.countFakturaTotal(jTable_InvoiceA_Insert_articles);
            invoiceA_update.insertOrUpdate(); // Update entire faktura after delete
//            invoiceB.refresh_c();
            //
            invoiceA_update.showTableInvert_2(); // some kind of redraw, OBS! needed
//            invoiceA_update.showTableInvert_3(); // some kind of redraw, OBS! needed
            //
        }
        //
    }//GEN-LAST:event_jButton_delete_articles_rowActionPerformed

    private void jButton_update_articles_rowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_update_articles_rowActionPerformed
        //
        if (HelpA.rowSelected(jTable_InvoiceA_Insert_articles) == false) {
            GP_BUH.confirmWarning(LANG.MSG_5);
            return;
        }
        //
        if (invoiceA_update.fieldsValidatedArticle()) {
            //
            invoiceA_update.updateArticle();
            //
//            BlinkThread bt = new BlinkThread(jButton_confirm_insert_update, false);
            //
            invoiceA_update.insertOrUpdate(); // update entire faktura on updated article
            //
            int selected_row = jTable_InvoiceA_Insert_articles.getSelectedRow();
            //
//            invoiceB.refresh_c();
            //
            HelpA.markGivenRow(jTable_InvoiceA_Insert_articles, selected_row);
            jTable_InvoiceA_Insert_articles_clicked();
            //
        }
        //
    }//GEN-LAST:event_jButton_update_articles_rowActionPerformed

    private void jButton_add_articleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_add_articleActionPerformed
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //
                if (articlesLimitReached()) {
                    HelpA.showNotification(LANG.MSG_5_2);
                    return;
                }
                //
                if (InvoiceA_Update.CURRENT_OPERATION_INSERT) {
                    if (invoiceA_insert.fieldsValidatedArticle()) {
                        invoiceA_insert.addArticle();
                        BlinkThread bt = new BlinkThread(jButton_confirm_insert_update, false);
                        faktua_tab_blockUntilSavedOrAborted_invoice(false);
                    }
                } else {
                    if (invoiceA_update.fieldsValidatedArticle()) {
                        invoiceA_update.deselectRowArticlesTable(); // Deselecting due to "InvoiceA_Updte->getConfigTableInvert_2() -> articlesJTableRowSelected()"
                        invoiceA_update.addArticle();
//                        invoiceB.refresh_c(); // correct
                    }
                }
                //
            }
        });
        //
    }//GEN-LAST:event_jButton_add_articleActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        //
        if (HelpA.rowSelected(jTable_InvoiceA_Insert_articles) == false) {
            return;
        }
        //
        invoiceA_insert.submitEditedArticle();
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        //
        if (HelpA.rowSelected(jTable_InvoiceA_Insert_articles) == false) {
            return;
        }
        //
        InvoiceA_Insert_.EDIT__ARTICLE_UPPON_INSERT__SWITCH = true;
        invoiceA_insert.showTableInvert_2();
        invoiceA_insert.refreshTableInvert(invoiceA_insert.TABLE_INVERT_2);
        //
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton_confirm_insert_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_confirm_insert_updateActionPerformed
        // "FAKTURA HTTP" BTN
        jButton_confirm_insert_updateActionPerformed();
        //
    }//GEN-LAST:event_jButton_confirm_insert_updateActionPerformed

    public void jButton_confirm_insert_updateActionPerformed() {
        // "FAKTURA HTTP" BTN
        if (InvoiceA_Update.CURRENT_OPERATION_INSERT) {
            if (invoiceA_insert.fieldsValidated(true)) {
                //
                invoiceA_insert.insertOrUpdate();
                //
                if (invoiceB != null) {
                    invoiceB.refresh_sync(null);// OBS! Important [2020-10-11]  
                } else {
                    invoiceB = new InvoiceB(this);
                }
                //
                faktua_tab_blockUntilSavedOrAborted_invoice(true);
                //
                openTabByName(BUH_INVOICE_MAIN.TAB_INVOICES_OVERVIEW);
                //
            }
            //
//            boolean isKontantFaktura = isKontantFaktura();
            boolean isKontantFaktura = Invoice_.CREATE_KONTANT_FAKTURA__OPERATION_INSERT;
            //
            if (isKontantFaktura) {
                makeInbetalning(isKontantFaktura, true);
            }
            //
        } else {
            if (invoiceA_update.fieldsValidated(true)) {
                invoiceA_update.insertOrUpdate();
                invoiceB.refresh_c();
                openTabByName(BUH_INVOICE_MAIN.TAB_INVOICES_OVERVIEW);
            }
        }
        //
    }

    private void createNewFaktura(String fakturaType) {
        //
        if (invoiceA_insert == null) {
            invoiceA_insert = new InvoiceA_Insert_(this);
        }
        //
        if (invoiceB != null) {
            if (noCustomersPresent()) {
                HelpA.showNotification_separate_thread(LANG.MSG_11);
            }
        }
        //
//        invoiceA_insert.resetSavedMoms_jCombo();
        //
        resetRutRot();
        //
        invoiceA_insert.resetRutRot();
        invoiceA_insert.createNew(fakturaType);//[#OFFERT#] IMPORTANT - "isKontantfaktura" shall be changed to faktura type
        //
    }

    private void jButton_create_new_faktura_bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_create_new_faktura_bActionPerformed
        //
        if (GP_BUH.confirmWarning(LANG.MSG_5_1) == false) {
            return;
        }
        //
        createNewFaktura(DB.STATIC__FAKTURA_TYPE_NORMAL);//false
        //
    }//GEN-LAST:event_jButton_create_new_faktura_bActionPerformed

    private void jTextField_total_exkl_momsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_total_exkl_momsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_total_exkl_momsActionPerformed

    private void jButton_update_kund_dataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_update_kund_dataActionPerformed
        if (foretagA.fieldsValidated(false)) {
            foretagA.update();
        }
    }//GEN-LAST:event_jButton_update_kund_dataActionPerformed

    private void jButton_inbetalningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_inbetalningActionPerformed
        makeInbetalning(isKontantFaktura(), false);
    }//GEN-LAST:event_jButton_inbetalningActionPerformed

    private void makeInbetalning(boolean isKontantFaktura, boolean directlyAfterCreation) {
        //
        EditPanel_Inbet epb = new EditPanel_Inbet(this, isKontantFaktura, getFakturaId(), getFakturaNr(), getFakturaKund());
        //
        if (directlyAfterCreation) {
            epb.setHeaderAfterCreationOfKontantFaktura();
        }
        //
        GP_BUH.centerAndBringToFront(epb);
        //
    }

    private void jButton_show_actionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_show_actionsActionPerformed
        EditPanel_Send eps = new EditPanel_Send(this, isKontantFaktura(), getFakturaId(), getFakturaNr(), getFakturaKund());
        GP_BUH.centerAndBringToFront(eps);
    }//GEN-LAST:event_jButton_show_actionsActionPerformed

    private void jButton_create_new_fakturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_create_new_fakturaActionPerformed
        openTabByName(TAB_FAKTURA);
        createNewFaktura(DB.STATIC__FAKTURA_TYPE_NORMAL);//false
    }//GEN-LAST:event_jButton_create_new_fakturaActionPerformed

    private void jButton4_save_faktura_kommentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4_save_faktura_kommentActionPerformed
        invoiceB.updateKomment(false);
    }//GEN-LAST:event_jButton4_save_faktura_kommentActionPerformed

    private void jButton_copy_fakturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_copy_fakturaActionPerformed
        copyFaktura_createKreditFaktura(false);
    }//GEN-LAST:event_jButton_copy_fakturaActionPerformed

    private void copyFaktura_createKreditFaktura(boolean isKreditFaktura) {
        //
        if (HelpA.rowSelected(jTable_invoiceB_alla_fakturor) == false) {
            return;
        }
        //
        if (invoiceA_insert != null) {
            invoiceA_insert.resetRutRot();
        }
        //
        if (invoiceA_update != null) {
            invoiceA_update.resetRutRot();
        }
        //
        String msg;
        //
        String fakturaNr = getFakturaNr();
        //
        if (isKreditFaktura) {
            msg = LANG.FAKTURA_KREDIT_MSG(fakturaNr);
        } else {
            msg = LANG.FAKTURA_COPY_MSG_A(fakturaNr);
        }
        //
        if (isOffert()) {
            OffertCopyOrOmvandlaFrame_ ocoof = new OffertCopyOrOmvandlaFrame_(this);
            GP_BUH.centerAndBringToFront(ocoof);
            return;
        }
        //
        if (GP_BUH.confirm(msg) == false) {
            return;
        }
        //
        invoiceB.copy(isKreditFaktura, isOffert(), true); // OMVANDLA If isOffert
//        invoiceB.copy(isKreditFaktura, isOffert(), false); // COPY If isOffert
        //
    }

    public void copyOrOmvandlaOffert(boolean omvandla) {
        invoiceB.copy(false, true, omvandla);
    }

    private void jButton4_delete_faktura_komment1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4_delete_faktura_komment1ActionPerformed
        //
        if (GP_BUH.confirmWarning(LANG.MSG_3_3) == false) {
            return;
        }
        //
        invoiceB.deleteComment();
    }//GEN-LAST:event_jButton4_delete_faktura_komment1ActionPerformed

    private void jButton_kredit_fakturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_kredit_fakturaActionPerformed
        copyFaktura_createKreditFaktura(true); // true = "kreditfaktura"
    }//GEN-LAST:event_jButton_kredit_fakturaActionPerformed

    private void jButton_kontant_fakturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_kontant_fakturaActionPerformed
        openTabByName(TAB_FAKTURA);
        createNewFaktura(DB.STATIC__FAKTURA_TYPE_KONTANT);//true
    }//GEN-LAST:event_jButton_kontant_fakturaActionPerformed

    private void jButton_send_reminderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_send_reminderActionPerformed
        //
        if (isBetald()) {
            HelpA.showNotification_separate_thread(LANG.MSG_12);
        }
        //
        if (isForfallen() == false) {
            HelpA.showNotification_separate_thread(LANG.MSG_12_2);
        }
        //
        invoiceB.htmlFakturaOrReminder(null, true);
    }//GEN-LAST:event_jButton_send_reminderActionPerformed


    private void jToggleButton_obetald_filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_obetald_filterActionPerformed
        toggleFilterBtnPressed(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__OBETALD, evt);
    }//GEN-LAST:event_jToggleButton_obetald_filterActionPerformed

    private void jToggleButton_not_send_filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_not_send_filterActionPerformed
        toggleFilterBtnPressed(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__EJ_SKICKAD, evt);
    }//GEN-LAST:event_jToggleButton_not_send_filterActionPerformed

    private void jToggleButton_delvis_betald_filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_delvis_betald_filterActionPerformed
        toggleFilterBtnPressed(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ALL, evt);
    }//GEN-LAST:event_jToggleButton_delvis_betald_filterActionPerformed

    private void jToggleButton_makulerad_filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_makulerad_filterActionPerformed
        toggleFilterBtnPressed(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__MAKULERAD, evt);
    }//GEN-LAST:event_jToggleButton_makulerad_filterActionPerformed

    private void jButton_search_by_kundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_search_by_kundActionPerformed
//        PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FILTER = PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FAKTURA_KUND;
        SET_SEARCH_FILTER(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FAKTURA_KUND, true);
        String fakturaKundId = HelpA.getComboBoxSelectedValue(jComboBox_faktura_kunder_filter, 2, -1);
        untoggleAll();
        invoiceB.refresh(fakturaKundId);
    }//GEN-LAST:event_jButton_search_by_kundActionPerformed

    private void jToggleButton_act_month_filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_act_month_filterActionPerformed
        toggleFilterBtnPressed(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ACT_MONTH, evt);
    }//GEN-LAST:event_jToggleButton_act_month_filterActionPerformed

    private void jToggleButton_forfallen_filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_forfallen_filterActionPerformed
        toggleFilterBtnPressed(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FORFALLEN, evt);
    }//GEN-LAST:event_jToggleButton_forfallen_filterActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (Invoice_.CURRENT_OPERATION_INSERT && invoiceA_insert != null) {
            invoiceB.htmlFakturaOrReminder_preview(FAKTURA_TYPE_CURRENT__OPERATION, false, invoiceA_insert);
        } else {
            invoiceB.htmlFakturaOrReminder_preview(FAKTURA_TYPE_CURRENT__OPERATION, false, invoiceA_update);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton_logg_inActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_logg_inActionPerformed
        home.loggaIn();
    }//GEN-LAST:event_jButton_logg_inActionPerformed

    private void jCheckBox_spara_inloggningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_spara_inloggningActionPerformed
        if (jCheckBox_spara_inloggning.isSelected()) {
            IO.writeToFile(Home.CHECK_BOX__SAVE_LOGIN_STATE, "1");
            home.saveUserAndPass();
        } else {
            IO.writeToFile(Home.CHECK_BOX__SAVE_LOGIN_STATE, "0");
            home.deleteUserAndPass();
        }
    }//GEN-LAST:event_jCheckBox_spara_inloggningActionPerformed

    private void jButton_save_smtp_settingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_save_smtp_settingsActionPerformed
        optionsTab.saveSmtpSettings();
    }//GEN-LAST:event_jButton_save_smtp_settingsActionPerformed

    private void jButton_test_smtpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_test_smtpActionPerformed
        optionsTab.testSmtpSettings();
    }//GEN-LAST:event_jButton_test_smtpActionPerformed

    private void jButton_delete_smtp_settingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_delete_smtp_settingsActionPerformed
        optionsTab.deleteSmtpSettings();
    }//GEN-LAST:event_jButton_delete_smtp_settingsActionPerformed

    private void jButton_save_reminder_msgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_save_reminder_msgActionPerformed
        optionsTab.saveReminderMessage();
    }//GEN-LAST:event_jButton_save_reminder_msgActionPerformed

    private void jButton_delete_reminder_msgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_delete_reminder_msgActionPerformed
        optionsTab.restoreReminderMessage();
    }//GEN-LAST:event_jButton_delete_reminder_msgActionPerformed

    private void jButton_register_new_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_register_new_userActionPerformed
        home.processUserRegistration();
    }//GEN-LAST:event_jButton_register_new_userActionPerformed

    private void jButton_forgot_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_forgot_passwordActionPerformed
        home.processForgotPass();
    }//GEN-LAST:event_jButton_forgot_passwordActionPerformed

    private void jButton_create_new_kontant_faktura_bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_create_new_kontant_faktura_bActionPerformed
        //
        if (GP_BUH.confirmWarning(LANG.MSG_5_1) == false) {
            return;
        }
        //
        createNewFaktura(DB.STATIC__FAKTURA_TYPE_KONTANT);//true
        //
    }//GEN-LAST:event_jButton_create_new_kontant_faktura_bActionPerformed

    private void jButton_share_accountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_share_accountActionPerformed
        home.processShareAccount();
    }//GEN-LAST:event_jButton_share_accountActionPerformed

    private void jButton_delete_account_sharingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_delete_account_sharingActionPerformed
        //
        if (GP_BUH.confirmWarning(LANG.MSG_4_2) == false) {
            return;
        }
        //
        home.processDeleteGuest();
    }//GEN-LAST:event_jButton_delete_account_sharingActionPerformed

    private void jButton_erase_account_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_erase_account_btnActionPerformed
        //
        optionsTab.deleteCustomerDataPermanent();
        //
    }//GEN-LAST:event_jButton_erase_account_btnActionPerformed

    private void jButton_dont_save_settingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_dont_save_settingsActionPerformed
        if (HelpA.confirmWarning(LANG.MSG_24)) {
            faktua_tab_blockUntilSavedOrAborted_invoice(true);
            openTabByName(BUH_INVOICE_MAIN.TAB_INVOICES_OVERVIEW);
        }
    }//GEN-LAST:event_jButton_dont_save_settingsActionPerformed

    private void jCheckBox__personActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox__personActionPerformed
        if (jCheckBox__person.isSelected()) {
            customersA.IS_PERSON__CUSTOMERS_A = true;
            customersA.refresh();
        } else {
            customersA.IS_PERSON__CUSTOMERS_A = false;
            customersA.refresh();
        }
    }//GEN-LAST:event_jCheckBox__personActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        openTabByName(TAB_FAKTURA);
        createNewFaktura(DB.STATIC__FAKTURA_TYPE_OFFERT);//true [#OFFERT#]
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton_articles_a__tab__search_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_articles_a__tab__search_btnActionPerformed
        String val = HelpA.getComboBoxSelectedValue(jComboBox_articles_a__tab);
        HelpA.markRowByValue(articlesA.getTableArticles(), ArticlesA.ARTIKEL_NAME__OR__NR__COLUMN, val);
        jTableArticles_clicked();
        jComboBox_articles_a__tab.setSelectedItem(null);
    }//GEN-LAST:event_jButton_articles_a__tab__search_btnActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
       if(jCheckBox1.isSelected()){
           articlesA.setJComboParam_colName(true);
       }else{
           articlesA.setJComboParam_colName(false);
       }
       //
       jComboBox_articles_a__tab.requestFocus();
       //
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String val = HelpA.getComboBoxSelectedValue(jComboBox_customers_a__tab);
        HelpA.markRowByValue(customersA.getTableMain(), CustomersA_.TABLE_FAKTURA_KUNDER__KUND_NAMN, val);
        jTableCustomersA_kunder_clicked();
        jComboBox_customers_a__tab.setSelectedItem(null);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void toggleFilterBtnPressed(String phpFunc, ActionEvent evt) {
        //
        JToggleButton jtb = (JToggleButton) evt.getSource();
        //
        if (jtb.isSelected() == true) {
//            PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FILTER = phpFunc;
            SET_SEARCH_FILTER(phpFunc, true);
            invoiceB.refresh(null);
            untoggleAllExcept((JToggleButton) evt.getSource());
        } else {
            jtb.setSelected(false);
            invoiceB.refresh(null);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //
        if (HelpA.runningInNetBeans() == false && HelpBuh.ERR_OUTPUT_TO_FILE__DISTRIBUTED) {
            HelpA.err_output_to_file();
        }
        //
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BUH_INVOICE_MAIN().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton3;
    protected javax.swing.JButton jButton4_delete_faktura_komment1;
    protected javax.swing.JButton jButton4_save_faktura_komment;
    protected javax.swing.JButton jButton_add_article;
    protected javax.swing.JButton jButton_articles_a__tab__search_btn;
    protected javax.swing.JButton jButton_confirm_insert_update;
    private javax.swing.JButton jButton_copy_faktura;
    private javax.swing.JButton jButton_create_new_faktura;
    private javax.swing.JButton jButton_create_new_faktura_b;
    private javax.swing.JButton jButton_create_new_kontant_faktura_b;
    protected javax.swing.JButton jButton_delete_account_sharing;
    protected javax.swing.JButton jButton_delete_article;
    protected javax.swing.JButton jButton_delete_articles_row;
    protected javax.swing.JButton jButton_delete_customer;
    private javax.swing.JButton jButton_delete_faktura;
    private javax.swing.JButton jButton_delete_reminder_msg;
    protected javax.swing.JButton jButton_delete_smtp_settings;
    public javax.swing.JButton jButton_dont_save_settings;
    private javax.swing.JButton jButton_edit_faktura;
    protected javax.swing.JButton jButton_erase_account_btn;
    protected javax.swing.JButton jButton_forgot_password;
    private javax.swing.JButton jButton_inbetalning;
    private javax.swing.JButton jButton_kontant_faktura;
    private javax.swing.JButton jButton_kredit_faktura;
    protected javax.swing.JButton jButton_logg_in;
    protected javax.swing.JButton jButton_print_faktura;
    protected javax.swing.JButton jButton_register_new_user;
    private javax.swing.JButton jButton_save_reminder_msg;
    protected javax.swing.JButton jButton_save_smtp_settings;
    protected javax.swing.JButton jButton_search_by_kund;
    protected javax.swing.JButton jButton_send_reminder;
    protected javax.swing.JButton jButton_share_account;
    private javax.swing.JButton jButton_show_actions;
    protected javax.swing.JButton jButton_test_smtp;
    protected javax.swing.JButton jButton_update_article;
    protected javax.swing.JButton jButton_update_articles_row;
    protected javax.swing.JButton jButton_update_kund;
    protected javax.swing.JButton jButton_update_kund_data;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox__person;
    protected javax.swing.JCheckBox jCheckBox_spara_inloggning;
    public javax.swing.JComboBox<String> jComboBox_articles_a__tab;
    public javax.swing.JComboBox<String> jComboBox_customers_a__tab;
    protected javax.swing.JComboBox<String> jComboBox_faktura_kunder_filter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    public static javax.swing.JLabel jLabel17_new__version;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel5_separator;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    protected javax.swing.JLabel jLabel_Artikel_Insert_or_Update;
    protected javax.swing.JLabel jLabel_Faktura_Insert_or_Update;
    protected javax.swing.JLabel jLabel_Kund_Insert_or_Update;
    public javax.swing.JLabel jLabel__nyckel_tal__antal_fakturor;
    public javax.swing.JLabel jLabel__nyckel_tal__ing_moms;
    public javax.swing.JLabel jLabel__nyckel_tal__tot_exkl_moms;
    public javax.swing.JLabel jLabel__nyckel_tal__tot_inkl_moms;
    protected javax.swing.JLabel jLabel_all_invoices_list;
    protected javax.swing.JLabel jLabel_all_invoices_list1;
    protected javax.swing.JLabel jLabel_ammount_of_articles_;
    protected javax.swing.JLabel jLabel_faktura_changed_by__user;
    protected javax.swing.JLabel jLabel_info__betald;
    protected javax.swing.JLabel jLabel_info__forfallen;
    protected javax.swing.JLabel jLabel_info__kontant_faktura;
    protected javax.swing.JLabel jLabel_info__kredit_faktura;
    protected javax.swing.JLabel jLabel_info__makulerad;
    protected javax.swing.JLabel jLabel_info__offert;
    protected javax.swing.JLabel jLabel_info__printed;
    protected javax.swing.JLabel jLabel_info__sent;
    protected javax.swing.JLabel jLabel_info_is_person;
    public javax.swing.JLabel jLabel_info_rut__or_omvant_skatt;
    protected javax.swing.JLabel jLabel_inloggning;
    private javax.swing.JLabel jLabel_nycke_tal__antal_fakturor;
    private javax.swing.JLabel jLabel_nycke_tal__ing_moms;
    private javax.swing.JLabel jLabel_nycke_tal__total_exkl_moms;
    private javax.swing.JLabel jLabel_nycke_tal__total_inkl_moms;
    private javax.swing.JLabel jLabel_nyckel_tal__info_label;
    private javax.swing.JLabel jLabel_register_new;
    private javax.swing.JLabel jLabel_restore_password;
    protected static javax.swing.JLabel jLabel_rut_avdrag;
    protected static javax.swing.JLabel jLabel_rut_total;
    private javax.swing.JLabel jLabel_share_account;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    protected javax.swing.JPanel jPanel11;
    protected javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    protected javax.swing.JPanel jPanel16;
    protected javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    public javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    protected javax.swing.JPanel jPanel2_faktura_main;
    private javax.swing.JPanel jPanel3;
    protected javax.swing.JPanel jPanel3_faktura_sec;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel4_Articles;
    protected javax.swing.JPanel jPanel4_Customers;
    protected javax.swing.JPanel jPanel5;
    protected javax.swing.JPanel jPanel6;
    protected javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    protected javax.swing.JPanel jPanel9;
    protected javax.swing.JPanel jPanel_articles;
    protected javax.swing.JPanel jPanel_email_client_options;
    public javax.swing.JPanel jPanel_graph_panel_a;
    public javax.swing.JPanel jPanel_graph_panel_b;
    public javax.swing.JPanel jPanel_graph_panel_c;
    public javax.swing.JPanel jPanel_graph_panel_d;
    public javax.swing.JPanel jPanel_inloggning;
    protected javax.swing.JPanel jPanel_register_new;
    protected javax.swing.JPanel jPanel_restore_password;
    protected javax.swing.JPanel jPanel_share_account;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    protected javax.swing.JScrollPane jScrollPane11;
    protected javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    protected javax.swing.JScrollPane jScrollPane1_faktura;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    protected javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    protected javax.swing.JTable jTable_ArticlesA_articles;
    public javax.swing.JTable jTable_InvoiceA_Insert_articles;
    protected javax.swing.JTable jTable_ftg;
    protected javax.swing.JTable jTable_ftg_addr;
    protected javax.swing.JTable jTable_invoiceB_alla_fakturor;
    protected javax.swing.JTable jTable_invoiceB_faktura_artiklar;
    protected javax.swing.JTable jTable_kund_adresses;
    protected javax.swing.JTable jTable_kunder;
    protected javax.swing.JTable jTable_shared_users;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    protected javax.swing.JTextArea jTextArea_faktura_komment;
    protected javax.swing.JTextArea jTextArea_reminder_message;
    protected static javax.swing.JTextField jTextField_exp_avg;
    protected static javax.swing.JTextField jTextField_frakt;
    protected static javax.swing.JTextField jTextField_moms_artiklar;
    protected static javax.swing.JTextField jTextField_moms_frakt_expavg;
    protected static javax.swing.JTextField jTextField_moms_sats_frakt_exp_avg;
    protected static javax.swing.JTextField jTextField_moms_total;
    protected static javax.swing.JTextField jTextField_rabatt_total;
    protected static javax.swing.JTextField jTextField_rut_avdrag;
    protected static javax.swing.JTextField jTextField_rut_total;
    protected static javax.swing.JTextField jTextField_total_exkl_moms;
    protected static javax.swing.JTextField jTextField_total_inkl_moms;
    private javax.swing.JToggleButton jToggleButton_act_month_filter;
    private javax.swing.JToggleButton jToggleButton_delvis_betald_filter;
    private javax.swing.JToggleButton jToggleButton_forfallen_filter;
    private javax.swing.JToggleButton jToggleButton_makulerad_filter;
    private javax.swing.JToggleButton jToggleButton_not_send_filter;
    private javax.swing.JToggleButton jToggleButton_obetald_filter;
    // End of variables declaration//GEN-END:variables

    private void mousePressedOnTab(MouseEvent me) {
        //
        if (jTabbedPane1.isEnabled() == false) {
            return;
        }
        //
        if (me.getSource() == jTabbedPane1) {
            //
            String title = jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex());
            ACTUAL_TAB_NAME = title;
            //
            boolean sameTabClicked = ACTUAL_TAB_NAME.equals(PREVIOUS_TAB_NAME);
            //
            if (ACTUAL_TAB_NAME.equals(TAB_INVOICES_OVERVIEW)) {
                //
                allInvoicesTabClicked();
                //
            } else if (ACTUAL_TAB_NAME.equals(TAB_HOME) && sameTabClicked == false) {
                //
                home.refresh();
                //
            } else if (ACTUAL_TAB_NAME.equals(TAB_FAKTURA) && sameTabClicked == false) {
                //
                fakturaTabClicked(getFakturaNr());
                //
            } else if (ACTUAL_TAB_NAME.equals(TAB_KUDNER)) {
                //
                if (customersA == null) {
                    customersA = new CustomersA_(this);
                } else {
                    jTableCustomersA_kunder_clicked();
                }
                //
            } else if (ACTUAL_TAB_NAME.equals(TAB_ARTIKLAR)) {
                //
                if (articlesA == null) {
                    articlesA = new ArticlesA(this);
                } else {
                    jTableArticles_clicked();
                }
                //
            } else if (ACTUAL_TAB_NAME.equals(TAB_FTG_SETUP)) {
                //
                if (foretagA == null) {
                    foretagA = new ForetagA(this);
                } else {
                    jTableForetagA_ftg_table_clicked();
                }
                //
            } else if (ACTUAL_TAB_NAME.equals(TAB_OTHER_SETUP) && sameTabClicked == false) {
                //
                if (optionsTab == null) {
                    optionsTab = new OptionsTab(this);
                } else {
                    optionsTab.refresh();
                }
                //
            } else if (ACTUAL_TAB_NAME.equals(TAB_STATISTIK) && sameTabClicked == false) {
                //
                if (statistikTab == null) {
                    statistikTab = new StatistikTab(this);
                } else {
                    statistikTab.refresh();
                }
                //
            }
            //
            PREVIOUS_TAB_NAME = ACTUAL_TAB_NAME;
            //
        }
        //
    }

    protected void allInvoicesTabClicked() {
        if (invoiceB == null) {
            invoiceB = new InvoiceB(this);
            HelpA.markFirstRowJtable(jTable_invoiceB_alla_fakturor);
            jtable_InvoiceB_all_invoices_clicked();
        } else {
            invoiceB.refresh_c();
            jtable_InvoiceB_all_invoices_clicked();
        }
    }

    private void fakturaTabClicked(String fakturaNr) {
        //
        DefaultTableModel dtm = (DefaultTableModel) jTable_invoiceB_faktura_artiklar.getModel();
        jTable_InvoiceA_Insert_articles.setModel(dtm);
        //
        if (invoiceB != null) {
            invoiceB.hideColumnsArticlesTable(jTable_InvoiceA_Insert_articles); //***    
        }
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //
                if (fakturaNr == null || fakturaNr.isEmpty()) {
                    //It's for the cases when the faktura list is empty
                    createNewFaktura(DB.STATIC__FAKTURA_TYPE_NORMAL);//false
                    return;
                }
                //
                if (isMakulerad()) {
                    HelpA.showNotification_separate_thread(LANG.MSG_9);
                }
                //
                invoiceA_update.resetRutRot();
                //
                invoiceA_update.showTableInvert();
                invoiceA_update.refreshTableInvert(invoiceA_update.TABLE_INVERT);
                //
                if (getInvoiceArticleCount() > 0) {
                    HelpA.markFirstRowJtable(jTable_InvoiceA_Insert_articles);
                    jTable_InvoiceA_Insert_articles_clicked();
                } else {
                    invoiceA_update.showTableInvert_2();
                    invoiceA_update.refreshTableInvert(invoiceA_update.TABLE_INVERT_2);
                }
                //
                invoiceA_update.showTableInvert_3();
                invoiceA_update.refreshTableInvert(invoiceA_update.TABLE_INVERT_3);
                //
                invoiceA_update.countFakturaTotal(jTable_InvoiceA_Insert_articles);
                //
            }
        });

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //
        mousePressedOnTab(e);
        //
        if (e.getSource() == jTable_invoiceB_alla_fakturor && (e.getClickCount() == 1)) {
            //
            changeToFakturaWithsync();
            //
        } else if (e.getSource() == jTable_invoiceB_alla_fakturor && (e.getClickCount() == 2)) {
            //
            editFakturaBtnKlicked();
            //
        } else if (e.getSource() == jTable_InvoiceA_Insert_articles && (e.getClickCount() == 1)) {
            //
            jTable_InvoiceA_Insert_articles_clicked();
            //
        } else if (e.getSource() == jTable_kunder && (e.getClickCount() == 1)) {
            //
            jTableCustomersA_kunder_clicked();
            //
        } else if (e.getSource() == jTable_kund_adresses && (e.getClickCount() == 1)) {
            //
            jTableCustomersA_adress_clicked();
            //
        } else if (e.getSource() == jTable_ArticlesA_articles && (e.getClickCount() == 1)) {
            //
            jTableArticles_clicked();
            //
        }
    }

    protected void jTableArticles_clicked() {
        //
        if (articlesA.getTableArticles().getRowCount() == 0) {
            articlesA.showTableInvert();
            articlesA.refreshTableInvert();
        } else {
            articlesA.showTableInvert_2();
            articlesA.refreshTableInvert(articlesA.TABLE_INVERT_2);
        }
        //
    }

    protected void jTableCustomersA_adress_clicked() {
        customersA.showTableInvert_3();
        customersA.refreshTableInvert(customersA.TABLE_INVERT_3);
    }

    protected void jTableForetagA_adress_clicked() {
        foretagA.showTableInvert_3();
        foretagA.refreshTableInvert(foretagA.TABLE_INVERT_3);
    }

    protected void jTableCustomersA_kunder_clicked() {
        //
        if (customersA.getTableMain().getRowCount() == 0) {
            // This makes that when there are no custmers it's opened directly not for "update" but for "insert" [2020-09-29]
            customersA.showTableInvert();
            customersA.refreshTableInvert();
            customersA.showTableInvert_4();
            customersA.refreshTableInvert(customersA.TABLE_INVERT_4);
        } else {
            //
            customersA.showTableInvert_2();
            customersA.refreshTableInvert(customersA.TABLE_INVERT_2);
            //
            customersA.fillAddressTable();
            HelpA.markFirstRowJtable(jTable_kund_adresses);
            jTableCustomersA_adress_clicked();
        }
        //
    }

    protected void jTableForetagA_ftg_table_clicked() {
        //
        foretagA.showTableInvert_2();
        foretagA.refreshTableInvert(foretagA.TABLE_INVERT_2);
        foretagA.fillAddressTable();
        //
        HelpA.markFirstRowJtable(jTable_ftg_addr);
        jTableForetagA_adress_clicked();
        //
    }

    private void jTable_InvoiceA_Insert_articles_clicked() {
        //
        if (Invoice_.CURRENT_OPERATION_INSERT == false) {
            invoiceA_update.SET_CURRENT_OPERATION_INSERT(false);
            invoiceA_update.showTableInvert_2();
        } else {
            invoiceA_insert.SET_CURRENT_OPERATION_INSERT(true);
        }
        //
    }

    /**
     * [2021-05-16] [#SWITCH-FAKTURA-NO-COLLISION#]
     */
    protected void changeToFakturaWithsync() {
        //
        Thread x = new Thread(() -> {
            jtable_InvoiceB_all_invoices_clicked();
        });
        //
        x.start();
        //
        try {
            x.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
    }

    protected void jtable_InvoiceB_all_invoices_clicked() {
        //
        String fakturaId = getFakturaId();
        //
        invoiceB.all_invoices_table_clicked(fakturaId);
        //
        resetRutRot();//[#RUTROT#]
        //
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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //
        boolean cond_1 = (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP);
        //
        if (e.getSource() == jTable_invoiceB_alla_fakturor && cond_1) {
            //
            changeToFakturaWithsync();
            //
        } else if (e.getSource() == jTextArea_faktura_komment) {
            //
            JTextAreaJLink txt = (JTextAreaJLink) jTextArea_faktura_komment;
            //
            Validator.validateMaxInputLength(txt, 100);
            //
//            System.out.println("" + txt.getText().length());
            //
        }
        //
    }

}
