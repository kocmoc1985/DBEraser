/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.LANG;
import MyObjectTable.OutPut;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.JTextFieldInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import XYG_BARGRAPH.BARGraph;
import XYG_BARGRAPH.MyGraphXY_BG;
import XYG_BARGRAPH.MyPoint_BG;
import XYG_BASIC.MyGraphContainer;
import XYG_BASIC.MyPoint;
import XYG_STATS.BarGraphListener;
import XYG_STATS.BasicGraphListener;
import XYG_STATS.XyGraph_M;
import XY_BUH_INVOICE.MyGraphXY_BuhInvoice;
import XY_BUH_INVOICE.XyGraph_BuhInvoice;
import forall.HelpA;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import other.StringDouble;

/**
 *
 * @author KOCMOC
 */
public class CustomersA_ extends CustomerAForetagA implements BarGraphListener {

    //
    protected static final String TABLE_FAKTURA_KUNDER__FAKTURA_KUND_ID = "FKUNDID";
    private static final String TABLE_FAKTURA_KUNDER__KUND_ID = "KUND ID";
    private static final String TABLE_FAKTURA_KUNDER__KUNDNR = "KUNDNR";
    public static final String TABLE_FAKTURA_KUNDER__KUND_NAMN = "KUND NAMN";
    private static final String TABLE_FAKTURA_KUNDER__VATNR = "VATNR";
    public static final String TABLE_FAKTURA_KUNDER__EPOST = "E-POST";
    private static final String TABLE_FAKTURA_KUNDER__KATEGORI = "KUND KATEGORI";
    //
//    private static final String TABLE_INVERT__PERSONNUMMER = "PERSONNUMMER";
    //
    private XyGraph_BuhInvoice xygraph;
    private final static String SERIE_NAME__BARGRAPH__TOTAL_PER_MONTH = "bar_graph_total_per_month";
    private final static String SERIE_NAME__BARGRAPH__AMMOUNT_PER_MONTH = "bar_graph_ammount_per_month";

    public CustomersA_(LAFakturering bim) {
        super(bim);
    }

    @Override
    protected void SET_CURRENT_OPERATION_INSERT(boolean insert) {
        //
        CURRENT_OPERATION_INSERT = insert;
        //
        if (insert) {
            //
            bim.jLabel_Kund_Insert_or_Update.setText(LANG.LBL_MSG_3);
            //
            GP_BUH.setEnabled(bim.jButton_delete_customer, false);
            //
        } else {
            //
            bim.jLabel_Kund_Insert_or_Update.setText(LANG.LBL_MSG_4);
            //
            GP_BUH.setEnabled(bim.jButton_delete_customer, true);
            //
        }
    }

    protected void jTableCustomersA_kunder_clicked() {
        //
        JTable table = getTableMain();
        //
        if (table.getRowCount() == 0) {
            // This makes that when there are no custmers it's opened directly not for "update" but for "insert" [2020-09-29]
            showTableInvert();
            refreshTableInvert();
            showTableInvert_4();
            refreshTableInvert(TABLE_INVERT_4);
            //
        } else {
            //
            String fakturaKundId = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUNDER__FAKTURA_KUND_ID);
            //
            drawGraph_basic(fakturaKundId, bim.jPanel__customers_a__graph_panel_a, "fakturor_given_faktura_kund", DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FAKTURA_KUND_CURR_YEAR);
            get_data__and_draw__bar_graph(fakturaKundId);
            //
            showTableInvert_2();
            refreshTableInvert(TABLE_INVERT_2);
            //
            fillAddressTable();
            HelpA.markFirstRowJtable(getTableAdress());
            bim.jTableCustomersA_adress_clicked();
            //
        }
        //
    }

    protected void createNewFakturaKund() {
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //
                showTableInvert();
                showTableInvert_4();
                //
                refreshTableInvert(TABLE_INVERT);
                refreshTableInvert(TABLE_INVERT_4);
            }
        });
        //
    }

    public void fillSearchJCombo(String colName) {
        //
        Object[] objects = HelpA.getValuesOneColumnJTable(getTableMain(), colName);
        //
        if (objects != null) {
            HelpA.fillComboBox(bim.jComboBox_customers_a__tab, objects, "");
        }
        //
    }

    protected void delete() {
        //
        //
        String str = bim.getForeignKeyBindings(getTableMain(),
                TABLE_FAKTURA_KUNDER__FAKTURA_KUND_ID,
                DB.BUH_FAKTURA__FAKTURAKUND_ID,
                DB.PHP_FUNC_PARAM_GET_INVOICES_USING_CUSTOMER,
                DB.BUH_FAKTURA__FAKTURANR__
        );
        //
        if (str.isEmpty() == false && str.equals("null") == false) {
            if (GP_BUH.confirmWarning(LANG.MSG_DELETE_WARNING_CUSTOMER(str)) == false) {
                return;
            }
        } else {
            if (GP_BUH.confirmWarning(LANG.MSG_3) == false) {
                return;
            }
        }
        //
        String fakturaKundId = HelpA.getValueSelectedRow(getTableMain(), TABLE_FAKTURA_KUNDER__FAKTURA_KUND_ID);
        //
        String json = bim.getSELECT(DB.BUH_FAKTURA__FAKTURAKUND_ID, fakturaKundId);
        //
        delete__buh_address(fakturaKundId); // ********** DELETE
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM_GET_INVOICES_GIVEN_FKID, json);
            //
            ArrayList<HashMap<String, String>> invoices = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            for (HashMap<String, String> map : invoices) {
                //
                String fakturaId = map.get(DB.BUH_FAKTURA__ID__);
                //
                delete__buh_f_artikel(fakturaId); // ********** DELETE
                //
                bim.deleteFaktura(fakturaId); // ********** DELETE
                //
            }
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        delete__buh_faktura_kund(fakturaKundId); // ********** DELETE
        //
        refresh();
        //
    }

    private void delete__buh_address(String fakturaKundId) {
        //
        HashMap<String, String> map = bim.getDELETE(DB.BUH_FAKTURA__FAKTURAKUND_ID, fakturaKundId, DB.TABLE__BUH_ADDRESS);
        //
        String json = JSon.hashMapToJSON(map);
        //
        executeDelete(json);
        //
    }

    private void delete__buh_f_artikel(String fakturaId) {
        //
        HashMap<String, String> map = bim.getDELETE(DB.BUH_FAKTURA__ID__, fakturaId, DB.TABLE__BUH_F_ARTIKEL);
        //
        String json = JSon.hashMapToJSON(map);
        //
        executeDelete(json);
        //
    }

    private void delete__buh_faktura_kund(String fakturaKundId) {
        //
        HashMap<String, String> map = bim.getDELETE(DB.BUH_FAKTURA__FAKTURAKUND_ID, fakturaKundId, DB.TABLE__BUH_FAKTURA_KUND);
        //
        String json = JSon.hashMapToJSON(map);
        //
        executeDelete(json);
        //
    }

    public void insert() {
        //
        if (containsEmptyObligatoryFields(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert())) {
            HelpA.showNotification(LANG.MSG_2);
            return;
        }
        //
        if (containsInvalidatedFields(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert())) {
            HelpA.showNotification(LANG.MSG_1);
            return;
        }
        //
        insertMainData();
        //
        //
        //
        refresh();
    }

    private void insertMainData() {
        //
        HashMap<String, String> map = tableInvertToHashMap(TABLE_INVERT, DB.START_COLUMN);
        //
        map.put(DB.BUH_FAKTURA_KUND__KUND_ID, "777"); //[#KUND-ID-INSERT#]
        //
        map.put(DB.BUH_FAKTURA_KUND__DATE_CREATED, GP_BUH.getDateCreated_special()); // required
        //
        if (IS_PERSON__CUSTOMERS_A) {
            map.put(DB.BUH_FAKTURA_KUND___IS_PERSON, "1");
            map.put(DB.BUH_FAKTURA_KUND___KATEGORI, "P");
        } else {
            map.put(DB.BUH_FAKTURA_KUND___IS_PERSON, "0");
        }
        //
        String json = JSon.hashMapToJSON(map);
        //
        String fakturaKundId = "-1";
        //
        try {
            //
            fakturaKundId = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_FAKTURA_KUND_TO_DB, json);
            //
            System.out.println("FAKTURA_KUND_ID AQUIRED: " + fakturaKundId);
            //
        } catch (Exception ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        if (GP_BUH.verifyId(fakturaKundId)) {
            //
            insertAddressData(fakturaKundId);
            //
        } else {
            HelpA.showNotification(LANG.MSG_ERROR_1);
        }
        //
    }

    private void insertAddressData(String fakturaKundId) {
        //
        HashMap<String, String> map = tableInvertToHashMap(TABLE_INVERT_4, DB.START_COLUMN);
        //
        map.put(DB.BUH_ADDR__FAKTURAKUND_ID, fakturaKundId);
        map.put(DB.BUH_ADDR__KUND_ID, "777");// [#KUND-ID-INSERT#]
        //
        String json = JSon.hashMapToJSON(map);
        //
        try {
            //
            HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_FAKTURA_KUND_ADDR_TO_DB, json);
            //
        } catch (Exception ex) {
            Logger.getLogger(CustomersA_.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void fillJTable_header_main() {
        //
        //
        JTable table = getTableMain();
        //
        String[] headers = {
            TABLE_FAKTURA_KUNDER__FAKTURA_KUND_ID,
            TABLE_FAKTURA_KUNDER__KUND_ID,
            TABLE_FAKTURA_KUNDER__KUND_NAMN,
            TABLE_FAKTURA_KUNDER__KUNDNR,
            TABLE__COL_ORGNR__PNR,
            TABLE_FAKTURA_KUNDER__VATNR,
            TABLE_FAKTURA_KUNDER__EPOST,
            TABLE_FAKTURA_KUNDER__KATEGORI
        };
        //
        table.setModel(new DefaultTableModel(null, headers));
        //
    }

    @Override
    protected void hideColumnsMainTable() {
        //
        JTable table = getTableMain();
        //
        if (GP_BUH.CUSTOMER_MODE) {
            HelpA.hideColumnByName(table, TABLE_FAKTURA_KUNDER__KUND_ID);
            HelpA.hideColumnByName(table, TABLE_FAKTURA_KUNDER__FAKTURA_KUND_ID);
//            HelpA.hideColumnByName(table, TABLE_FAKTURA_KUNDER__KUNDNR);
            HelpA.hideColumnByName(table, TABLE_FAKTURA_KUNDER__EPOST);
            HelpA.hideColumnByName(table, TABLE_FAKTURA_KUNDER__VATNR);
        }
    }

    @Override
    protected void addRowJtable_main(HashMap<String, String> map, JTable table) {
        //
        Object[] jtableRow = new Object[]{
            map.get(DB.BUH_FAKTURA_KUND__ID), // hidden
            map.get(DB.BUH_FAKTURA_KUND__KUND_ID), // hidden
            map.get(DB.BUH_FAKTURA_KUND___NAMN),
            map.get(DB.BUH_FAKTURA_KUND___KUNDNR),
            map.get(DB.BUH_FAKTURA_KUND___ORGNR),
            map.get(DB.BUH_FAKTURA_KUND___VATNR),
            map.get(DB.BUH_FAKTURA_KUND___EMAIL),
            map.get(DB.BUH_FAKTURA_KUND___KATEGORI),};
        //
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(jtableRow);
        //
    }

    /**
     * INSERT
     */
    @Override
    public void showTableInvert() {
        //
        SET_CURRENT_OPERATION_INSERT(true);
        //
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null,
                getConfigTableInvert(), false, "table_invert_1");
        TABLE_INVERT = null;
        TABLE_INVERT = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT, 5, 0, 5, 0);
        showTableInvert(bim.jPanel5);
        //
    }

    /**
     * UPDATE
     */
    public void showTableInvert_2() {
        //
        SET_CURRENT_OPERATION_INSERT(false);
        //
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null,
                getConfigTableInvert_2(), false, "table_invert_2");
        TABLE_INVERT_2 = null;
        TABLE_INVERT_2 = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT_2, 5, 0, 5, 0);
        showTableInvert(bim.jPanel5, TABLE_INVERT_2);
        //

    }

    /**
     * UPDATE
     */
    public void showTableInvert_3() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null,
                getConfigTableInvert_3(), false, "table_invert_3");
        TABLE_INVERT_3 = null;
        TABLE_INVERT_3 = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT_3, 5, 0, 5, 0);
        showTableInvert(bim.jPanel7, TABLE_INVERT_3);
        //
    }

    /**
     * INSERT
     */
    public void showTableInvert_4() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null,
        getConfigTableInvert_4(), false, "table_invert_4");
        TABLE_INVERT_4 = null;
        TABLE_INVERT_4 = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT_4, 5, 0, 5, 0);
        showTableInvert(bim.jPanel7, TABLE_INVERT_4);
        //
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        RowDataInvert kundnr = new RowDataInvertB(getNextKundnr(), DB.BUH_FAKTURA_KUND___KUNDNR, TABLE_FAKTURA_KUNDER__KUNDNR, "", true, true, true);
        kundnr.setDisabled();
        //
        RowDataInvert namn = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___NAMN, TABLE_FAKTURA_KUNDER__KUND_NAMN, "", true, true, true);
        //
        RowDataInvert orgnr;
        orgnr = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___ORGNR, TABLE__COL_ORGNR__PNR, "", true, true, false);
        //
        RowDataInvert vatnr = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___VATNR, TABLE_FAKTURA_KUNDER__VATNR, "", true, true, false);
        //
        RowDataInvert email = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___EMAIL, TABLE_FAKTURA_KUNDER__EPOST, "", true, true, false);
        //
        RowDataInvert kund_kategori = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, DB.STATIC__KUND__KATEGORI, DB.BUH_FAKTURA_KUND___KATEGORI, TABLE_FAKTURA_KUNDER__KATEGORI, "", true, true, false);
        kund_kategori.enableFixedValues();
        kund_kategori.setUneditable();
        //
        if (IS_PERSON__CUSTOMERS_A) {
            vatnr.setVisible_(false);
            kund_kategori.setVisible_(false);
        }
        //
        RowDataInvert[] rows = {
            kundnr,
            namn,
            orgnr,
            vatnr,
            email,
            kund_kategori
        };
        //
        return rows;
    }

    /**
     * UPDATE
     *
     * @return
     */
    @Override
    public RowDataInvert[] getConfigTableInvert_2() {
        //
        JTable table = getTableMain();
        //
        String kundnr_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUNDER__KUNDNR);
        RowDataInvert kundnr = new RowDataInvertB(kundnr_, DB.BUH_FAKTURA_KUND___KUNDNR, TABLE_FAKTURA_KUNDER__KUNDNR, "", true, true, true);
        kundnr.setDisabled();
        //
        String kundnamn_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUNDER__KUND_NAMN);
        RowDataInvert kundnamn = new RowDataInvertB(kundnamn_, DB.BUH_FAKTURA_KUND___NAMN, TABLE_FAKTURA_KUNDER__KUND_NAMN, "", true, true, true);
        //
        String orgnr_ = HelpA.getValueSelectedRow(table, TABLE__COL_ORGNR__PNR);
        RowDataInvert orgnr;
        orgnr = new RowDataInvertB(orgnr_, DB.BUH_FAKTURA_KUND___ORGNR, TABLE__COL_ORGNR__PNR, "", true, true, false);
        //
        String vatnr_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUNDER__VATNR);
        RowDataInvert vatnr = new RowDataInvertB(vatnr_, DB.BUH_FAKTURA_KUND___VATNR, TABLE_FAKTURA_KUNDER__VATNR, "", true, true, false);
        //
        String epost_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUNDER__EPOST);
        RowDataInvert epost = new RowDataInvertB(epost_, DB.BUH_FAKTURA_KUND___EMAIL, TABLE_FAKTURA_KUNDER__EPOST, "", true, true, false);
        //
        //
        String fixedComboValues_b = JSon._get_simple(HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUNDER__KATEGORI),
                DB.STATIC__KUND__KATEGORI
        );
        //
        RowDataInvert kategori = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_b, DB.BUH_FAKTURA_KUND___KATEGORI, TABLE_FAKTURA_KUNDER__KATEGORI, "", true, true, false);
        kategori.enableFixedValues();
        kategori.setUneditable();
        //
        if (IS_PERSON__CUSTOMERS_A) {
            vatnr.setVisible_(false);
            kategori.setVisible_(false);
        }
        //
        RowDataInvert[] rows = {
            kundnr,
            kundnamn,
            orgnr,
            vatnr,
            epost,
            kategori
        };
        //
        return rows;
    }

    /**
     * [INSERT]
     *
     * @return
     */
    @Override
    public RowDataInvert[] getConfigTableInvert_4() {
        //
        RowDataInvert addr_a = new RowDataInvertB("", DB.BUH_ADDR__ADDR_A, TABLE_FAKTURA_KUND_ADDR__POSTADDR_A, "", true, true, false);
        //
        RowDataInvert addr_b = new RowDataInvertB("", DB.BUH_ADDR__ADDR_B, TABLE_FAKTURA_KUND_ADDR__POSTADDR_B, "", true, true, false);
        //
        RowDataInvert visit_addr = new RowDataInvertB("", DB.BUH_ADDR__BESOKS_ADDR, TABLE_FAKTURA_KUND_ADDR__BESOKSADDR, "", true, true, false);
        //
        RowDataInvert zip = new RowDataInvertB("", DB.BUH_ADDR__POSTNR_ZIP, TABLE_FAKTURA_KUND_ADDR__ZIP, "", true, true, false);
        //
        RowDataInvert ort = new RowDataInvertB("", DB.BUH_ADDR__ORT, TABLE_FAKTURA_KUND_ADDR__ORT, "", true, true, false);
        //
        RowDataInvert land = new RowDataInvertB("Sverige", DB.BUH_ADDR__LAND, TABLE_FAKTURA_KUND_ADDR__LAND, "", true, true, false);
        //
        RowDataInvert tel_a = new RowDataInvertB("", DB.BUH_ADDR__TEL_A, TABLE_FAKTURA_KUND_ADDR__TEL_A, "", true, true, false);
        //
        RowDataInvert tel_b = new RowDataInvertB("", DB.BUH_ADDR__TEL_B, TABLE_FAKTURA_KUND_ADDR__TEL_B, "", true, true, false);
        //
        RowDataInvert other = new RowDataInvertB("", DB.BUH_ADDR__OTHER, TABLE_FAKTURA_KUND_ADDR__OTHER, "", true, true, false);
        //
        RowDataInvert[] rows = {
            addr_a,
            addr_b,
            visit_addr,
            zip,
            ort,
            land,
            tel_a,
            tel_b,
            other
        };
        //
        return rows;
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
        if (col_name.equals(DB.BUH_FAKTURA_KUND___KUNDNR)) {
            //
//            Validator.checkIfExistInDB(bim, jli, DB.BUH_FAKTURA_KUND___KUNDNR, DB.TABLE__BUH_FAKTURA_KUND);
            Validator.checkIfExistInJTable(getTableMain(), jli, TABLE_FAKTURA_KUNDER__KUNDNR);
            //
        } else if (col_name.equals(DB.BUH_FAKTURA_KUND___NAMN)) {
            //
//            Validator.checkIfExistInDB(bim, jli, DB.BUH_FAKTURA_KUND___NAMN, DB.TABLE__BUH_FAKTURA_KUND);
            //
            if (Validator.validateMaxInputLength(jli, 150)) {
                if (IS_PERSON__CUSTOMERS_A) {
                    //
                } else {
                    Validator.checkIfExistInJTable(getTableMain(), jli, TABLE_FAKTURA_KUNDER__KUND_NAMN);
                }

            }
            //
        }
        //
    }

    @Override
    public void mouseClickedForward(MouseEvent me, int column, int row, String tableName, TableInvert ti) {
        //
        super.mouseClickedForward(me, column, row, tableName, ti); //To change body of generated methods, choose Tools | Templates.
        //
        JLinkInvert jli = (JLinkInvert) me.getSource();
        //
        String col_name = ti.getCurrentColumnName(me.getSource());
        //
        if (col_name.equals(DB.BUH_FAKTURA_KUND___KUNDNR)) {
            //
//            supposeNextKundNr(jli);
            //
            Validator.checkIfExistInJTable(getTableMain(), jli, TABLE_FAKTURA_KUNDER__KUNDNR);
            //
        }
        //
    }

    public String getNextKundnr() {
        //
        String json = bim.getLatest(DB.BUH_FAKTURA_KUND___KUNDNR, DB.TABLE__BUH_FAKTURA_KUND);
        //
        try {
            //
            String latest = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_LATEST, json);
            //
//            System.out.println("LATEST: " + latest + "   *************************");
            //
            if (HelpA.checkIfNumber_b(latest)) {
                int nr = Integer.parseInt(latest);
                nr++; // OBS! Iam getting the last so i have to add to get the nr for the act. faktura
                return "" + nr;
            } else {
                return "";
            }
            //
        } catch (Exception ex) {
            Logger.getLogger(CustomersA_.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        //
        //
        //
        //
        //
        //
        //
        //
    }

    //==========================================================================
    //==========================================================================
    //==========================================================================
    private void supposeNextKundNr(JLinkInvert jli) {
        //
        JTextFieldInvert jtfi = (JTextFieldInvert) jli;
        //
        String next = getNextKundnr();
        //
        jtfi.setText(next);
    }

    private void get_data__and_draw__bar_graph(String fakturaKundId) {
        //
        new Thread(() -> {
            //
            String json = bim.getSELECT_doubleWhere(DB.BUH_FAKTURA_KUND__KUND_ID, "777", DB.BUH_FAKTURA_KUND__ID, fakturaKundId);
            //
            try {
                //
                String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                        DB.PHP_FUNC_PARAM_GET_FAKTUROR_TOTALS__GIVEN_KUND_CURR_YEAR, json);
                //
                ArrayList<HashMap<String, String>> totals = JSon.phpJsonResponseToHashMap(json_str_return);
                //
                java.awt.EventQueue.invokeLater(() -> {
                    drawGraph_bargraph(totals, bim.jPanel__customers_a__graph_panel_c, bim.jPanel__customers_a__graph_panel_b, SERIE_NAME__BARGRAPH__TOTAL_PER_MONTH, SERIE_NAME__BARGRAPH__AMMOUNT_PER_MONTH);
                });
                //
            } catch (Exception ex) {
                Logger.getLogger(ArticlesA.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
        }).start();
        //
    }

    private void drawGraph_bargraph(ArrayList<HashMap<String, String>> totals, JPanel containerTotalPerMonth, JPanel containerAmmountPerMonth, String name_a, String name_b) {
        //
        containerTotalPerMonth.removeAll();
        containerAmmountPerMonth.removeAll();
        containerTotalPerMonth.revalidate();
        containerTotalPerMonth.repaint();
        containerAmmountPerMonth.revalidate();
        containerAmmountPerMonth.repaint();
        //
        if (totals == null || totals.isEmpty()) {
            return;
        }
        //====================================================
        BasicGraphListener gg__total_per_month;
        MyGraphXY_BG mgxyhm;
        //
        final XyGraph_M xygraph = new XyGraph_M(name_a, MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
        //
        mgxyhm = new MyGraphXY_BG("Total", ":-");
        mgxyhm.addBarGraphListener(this);
        gg__total_per_month = new BARGraph(name_a, mgxyhm, MyGraphContainer.DISPLAY_MODE_FULL_SCREEN); // MyGraphContainer.DISPLAY_MODE_FOOT_DISABLED
        //
        xygraph.setGraph(gg__total_per_month);
        containerTotalPerMonth.add(gg__total_per_month.getGraph()); //***** //[#WAIT-FOR-HEIGHT#]
        //
        //====================================================
        //
        BasicGraphListener gg__ammount_per_month;
        MyGraphXY_BG mgxyhm_b;
        //
        final XyGraph_M xygraph_b = new XyGraph_M(name_b, MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
        //
        mgxyhm_b = new MyGraphXY_BG("Antal", " st");
        mgxyhm_b.addBarGraphListener(this);
        gg__ammount_per_month = new BARGraph(name_b, mgxyhm_b, MyGraphContainer.DISPLAY_MODE_FULL_SCREEN); // MyGraphContainer.DISPLAY_MODE_FOOT_DISABLED
        //
        xygraph_b.setGraph(gg__ammount_per_month);
        containerAmmountPerMonth.add(gg__ammount_per_month.getGraph()); //***** //[#WAIT-FOR-HEIGHT#]
        //
        //====================================================
        //
        Thread x = new Thread(new Thread_B_B(totals, gg__total_per_month, gg__ammount_per_month));
        x.setName("Thread_B_B");
        x.start();
        //
    }

    @Override
    public void barGraphHoverEvent(MouseEvent e, MyPoint mp) {
        if (e.getSource() instanceof MyPoint_BG) {
            //
            MyPoint_BG mpbg = (MyPoint_BG) e.getSource();
            //
            BARGraph bg = mpbg.getBarGraph();
            //
            bg.highlight_a(mpbg, xygraph, DB.BUH_FAKTURA__FAKTURA_DATUM);
            //
//            highlight_a(mpbg, xygraph);
            //
        }
    }

    @Override
    public void barGraphHoverOutEvent(MouseEvent me) {
        xygraph.getSerie().resetPointsColorAndForm();
    }

    class Thread_B_B implements Runnable {

        private final ArrayList<HashMap<String, String>> totals;
        private final BasicGraphListener gg__total_per_month;
        private final BasicGraphListener gg__ammount_per_month;

        public Thread_B_B(ArrayList<HashMap<String, String>> totals, BasicGraphListener gg__total_per_month, BasicGraphListener gg__ammount_per_month) {
            this.totals = totals;
            this.gg__total_per_month = gg__total_per_month;
            this.gg__ammount_per_month = gg__ammount_per_month;
        }

        @Override
        public void run() {
            getData_and_add_to_graph();
        }

        private void getData_and_add_to_graph() {
            //
            final LinkedHashMap<String, Double> month_sum_map = new LinkedHashMap<>();
            final LinkedHashMap<String, Double> month_ammount_map = new LinkedHashMap<>();
            //
            //
            for (HashMap<String, String> map : totals) {
                //
                String fakturadatum = map.get(DB.BUH_FAKTURA__FAKTURA_DATUM);
                String total = map.get("total_exkl_moms"); // the column name is the "as" column which means it's not present in the table but defined in the select statement
                //
                String[] arr = fakturadatum.split("-");
                String faktura_datum_short = arr[0] + "-" + arr[1];
                //
                HelpA.increase_map_value_with_x(faktura_datum_short, Double.parseDouble(total), month_sum_map);
                //
                HelpA.increase_map_value_with_x(faktura_datum_short, 1.0, month_ammount_map);
                //
            }
            //
            //====================================================
            //
            Set set = month_sum_map.keySet();
            Iterator it = set.iterator();
            //
            final ArrayList<StringDouble> barGraphValuesList_total = new ArrayList<>();
            //
            while (it.hasNext()) {
                String key = (String) it.next();
                Double value = month_sum_map.get(key);
//                System.out.println("key = " + key + "  value = " + value);
                barGraphValuesList_total.add(new StringDouble(key, value));
            }
            //
            if (barGraphValuesList_total.size() < 12) {
                //
                while (barGraphValuesList_total.size() < 12) {
                    barGraphValuesList_total.add(new StringDouble("", 0));
                }
                //
            }
            //
            Collections.reverse(barGraphValuesList_total);
            //
            BARGraph barg_a = (BARGraph) gg__total_per_month;
            //
            java.awt.EventQueue.invokeLater(() -> {
                barg_a.addData(barGraphValuesList_total);
            });
            //
            //====================================================
            //
            Set set_b = month_ammount_map.keySet();
            Iterator it_b = set_b.iterator();
            //
            final ArrayList<StringDouble> barGraphValuesList_ammount = new ArrayList<>();
            //
            while (it_b.hasNext()) {
                String key = (String) it_b.next();
                Double value = month_ammount_map.get(key);
                System.out.println("key = " + key + "  value = " + value);
                barGraphValuesList_ammount.add(new StringDouble(key, value));
            }
            //
            if (barGraphValuesList_ammount.size() < 12) {
                //
                while (barGraphValuesList_ammount.size() < 12) {
                    barGraphValuesList_ammount.add(new StringDouble("", 0));
                }
                //
            }
            //
            Collections.reverse(barGraphValuesList_ammount);
            //
            BARGraph barg_b = (BARGraph) gg__ammount_per_month;
            //
            java.awt.EventQueue.invokeLater(() -> {
                barg_b.addData(barGraphValuesList_ammount);
            });
            //
            //====================================================
            //
        }
    }

    private void drawGraph_basic(String fakturaKundId, JPanel container, String name, String phpScript) {
        //
        new Thread(() -> {
            //
            container.removeAll();
            container.revalidate();
            container.repaint();
            //
            String dateNow = GP_BUH.getDate_yyyy_MM_dd();
            String dateFormat = GP_BUH.DATE_FORMAT_BASIC;
            //
            XyGraph_BuhInvoice xygm = new XyGraph_BuhInvoice(name, DB.BUH_FAKTURA__TOTAL_EXKL_MOMS__, new MyGraphXY_BuhInvoice(bim), MyGraphContainer.DISPLAY_MODE_FULL_SCREEN, dateNow, dateFormat);
            //
            if (phpScript.equals(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FAKTURA_KUND_CURR_YEAR)) {
                xygraph = xygm;
            }
            //
            System.out.println("Thread:" + Thread.currentThread().getName());
            //
            container.add(xygm.getGraph());
            //
            Thread x = new Thread(new Thread_A_A(fakturaKundId, phpScript, xygm));
            x.setName("Thread_A_A");
            x.start();
            //
        }).start();
    }

    class Thread_A_A implements Runnable {

        private final String fakturaKundId;
        private final String phpScript;
        private final XyGraph_BuhInvoice xghm;

        public Thread_A_A(String fakturaKundId, String phpScript, XyGraph_BuhInvoice xghm) {
            this.fakturaKundId = fakturaKundId;
            this.phpScript = phpScript;
            System.out.println("CCC:" + this.phpScript);
            this.xghm = xghm;
        }

        @Override
        public void run() {
            getData_and_add_to_graph();
        }

        private void getData_and_add_to_graph() {
            //
            String json = bim.getSELECT_doubleWhere(DB.BUH_FAKTURA_KUND__KUND_ID, "777", DB.BUH_FAKTURA_KUND__ID, fakturaKundId);
            //
            String json_str_return = "";
            //
            try {
                json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN, phpScript, json);
            } catch (Exception ex) {
                Logger.getLogger(StatistikTab.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
            ArrayList<HashMap<String, String>> invoices = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            if (invoices == null || invoices.isEmpty()) {
                if (this.xghm.getGraph() != null && this.xghm.getGraph().getParent() != null) {
                    this.xghm.getGraph().getParent().removeAll();
                }
                return;
            }
            //
            // OBS! HERE Below it's done with AWT-Thread
            java.awt.EventQueue.invokeLater(() -> {
                System.out.println("Thread addData: " + Thread.currentThread());
                this.xghm.addData(invoices, new String[]{DB.BUH_FAKTURA__FAKTURA_DATUM, DB.BUH_FAKTURA__FORFALLO_DATUM});
            });
            //
        }

    }

}
