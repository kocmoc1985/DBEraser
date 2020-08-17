/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTable.OutPut;
import MyObjectTable.Table;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.JTextFieldInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import forall.HelpA;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KOCMOC
 */
public class CustomersA extends Basic_Buh_ {

    //
    protected Table TABLE_INVERT_2;
    protected Table TABLE_INVERT_3;
    protected Table TABLE_INVERT_4;
    //
    private static final String TABLE_FAKTURA_KUNDER__FAKTURA_KUND_ID = "FKUNDID";
    private static final String TABLE_FAKTURA_KUNDER__KUND_ID = "KUND ID";
    private static final String TABLE_FAKTURA_KUNDER__KUNDNR = "KUNDNR";
    private static final String TABLE_FAKTURA_KUNDER__KUND_NAMN = "KUND NAMN";
    private static final String TABLE_FAKTURA_KUNDER__ORGNR = "ORGNR";
    private static final String TABLE_FAKTURA_KUNDER__VATNR = "VATNR";
    private static final String TABLE_FAKTURA_KUNDER__EPOST = "E-POST";
    private static final String TABLE_FAKTURA_KUNDER__KATEGORI = "KUND KATEGORI";
    //
    private static final String TABLE_FAKTURA_KUND_ADDR__ID = "ID";
    private static final String TABLE_FAKTURA_KUND_ADDR__FAKTURAKUND_ID = "FKUNDID";
    private static final String TABLE_FAKTURA_KUND_ADDR__NAMN = "NAMN";
    private static final String TABLE_FAKTURA_KUND_ADDR__IS_PRIMARY = "PRIMÄR";
    private static final String TABLE_FAKTURA_KUND_ADDR__POSTADDR_A = "ADRESS 1";
    private static final String TABLE_FAKTURA_KUND_ADDR__POSTADDR_B = "ADRESS 2";
    private static final String TABLE_FAKTURA_KUND_ADDR__BESOKSADDR = "BESÖKS ADR";
    private static final String TABLE_FAKTURA_KUND_ADDR__ZIP = "POSTNR";
    private static final String TABLE_FAKTURA_KUND_ADDR__ORT = "ORT";
    private static final String TABLE_FAKTURA_KUND_ADDR__TEL_A = "TEL 1";
    private static final String TABLE_FAKTURA_KUND_ADDR__TEL_B = "TEL 2";
    private static final String TABLE_FAKTURA_KUND_ADDR__OTHER = "ANNAT";
    //
    public static boolean CURRENT_OPERATION_INSERT = false;

    public CustomersA(BUH_INVOICE_MAIN bim) {
        super(bim);
    }

    private void SET_CURRENT_OPERATION_INSERT(boolean insert) {
        //
        CURRENT_OPERATION_INSERT = insert;
        //
        if (insert) {
            //
            bim.jLabel_Kund_Insert_or_Update.setText(LANG.LBL_MSG_3);
            //
            bim.jButton_update_kund.setEnabled(false);
            bim.jButton_add_customer.setEnabled(true);
            //
        } else {
            //
            bim.jLabel_Kund_Insert_or_Update.setText(LANG.LBL_MSG_4);
            //
            bim.jButton_update_kund.setEnabled(true);
            bim.jButton_add_customer.setEnabled(false);
            //
        }
    }

    private JTable getTableKunder() {
        return bim.jTable_kunder;
    }

    private JTable getTableAdresses() {
        return bim.jTable_kund_adresses;
    }

    private void hideAdressesTable() {
        bim.jScrollPane8.setVisible(false);
    }

    private void refresh() {
        fillKundJTable();
        HelpA.markFirstRowJtable(getTableKunder());
        bim.jTableCustomersA_kunder_clicked();
    }

    @Override
    protected void startUp() {
        //
        if (GP_BUH.CUSTOMER_MODE == true) {
            hideAdressesTable();
        }
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
//                showTableInvert();
                fillJTable_header_kunder();
                fillJTable_header_kund_addresses();

                //
                refresh();
                //
            }
        });
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

    protected void delete() {
        //
        if (HelpA.confirmWarning(LANG.MSG_3) == false) {
            return;
        }
        //
        String fakturaKundId = HelpA.getValueSelectedRow(getTableKunder(), TABLE_FAKTURA_KUNDER__FAKTURA_KUND_ID);
        //
        String json = bim.getSELECT(DB.BUH_FAKTURA__FAKTURAKUND_ID, fakturaKundId);
        //
        delete__buh_address(fakturaKundId); // ********** DELETE
        //
        try {
            //
            String json_str_return = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM_GET_INVOICES_GIVEN_FKID, json));
            //
            ArrayList<HashMap<String, String>> invoices = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            for (HashMap<String, String> map : invoices) {
                //
                String fakturaId = map.get(DB.BUH_FAKTURA__ID__);
                //
                delete__buh_f_artikel(fakturaId); // ********** DELETE
                //
            }
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        delete__buh_faktura(fakturaKundId); // ********** DELETE
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

    private void delete__buh_faktura(String fakturaKundId) {
        //
        HashMap<String, String> map = bim.getDELETE(DB.BUH_FAKTURA__FAKTURAKUND_ID, fakturaKundId, DB.TABLE__BUH_FAKTURA);
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

    public void update() {
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
        String fakturaKundId = HelpA.getValueSelectedRow(getTableKunder(), TABLE_FAKTURA_KUNDER__FAKTURA_KUND_ID);
        String address_id = HelpA.getValueSelectedRow(getTableAdresses(), TABLE_FAKTURA_KUND_ADDR__ID);
        //
        if (BUH_INVOICE_MAIN.verifyId(fakturaKundId)) {
            updateCustomerData(fakturaKundId);
        }
        //
        if (BUH_INVOICE_MAIN.verifyId(address_id)) {
            updateAddressData(address_id);
        } else {

        }
        //
        //
        refresh();
        //
    }

    private void updateCustomerData(String fakturaKundId) {
        //
        HashMap<String, String> map = tableInvertToHashMap(TABLE_INVERT_2, DB.START_COLUMN, getConfigTableInvert_2());
        //
        HashMap<String, String> update_map = bim.getUPDATE(DB.BUH_FAKTURA_KUND__ID, fakturaKundId, DB.TABLE__BUH_FAKTURA_KUND);
        //
        HashMap<String, String> final_map = JSon.joinHashMaps(map, update_map);
        //
        String json = JSon.hashMapToJSON(final_map);
        //
        HelpBuh.update(json);
        //
    }

    private void updateAddressData(String id) {
        //
        HashMap<String, String> map = tableInvertToHashMap(TABLE_INVERT_3, DB.START_COLUMN, getConfigTableInvert_3());
        //
        HashMap<String, String> update_map = bim.getUPDATE(DB.BUH_ADDR__ID, id, DB.TABLE__BUH_ADDRESS);
        //
        HashMap<String, String> final_map = JSon.joinHashMaps(map, update_map);
        //
        String json = JSon.hashMapToJSON(final_map);
        //
        HelpBuh.update(json);
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
        insertCustomerData();
        //
        //
        //
        refresh();
    }

    private void insertCustomerData() {
        //
        HashMap<String, String> map = tableInvertToHashMap(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert());
        //
        map.put(DB.BUH_FAKTURA_KUND__KUND_ID, getKundId()); // required
        //
        map.put(DB.BUH_FAKTURA_KUND__DATE_CREATED, BUH_INVOICE_MAIN.getDateCreated()); // required
        //
        String json = JSon.hashMapToJSON(map);
        //
        String fakturaKundId = "-1";
        //
        try {
            //
            fakturaKundId = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_FAKTURA_KUND_TO_DB, json));
            //
            System.out.println("FAKTURA_KUND_ID AQUIRED: " + fakturaKundId);
            //
        } catch (Exception ex) {
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        if (BUH_INVOICE_MAIN.verifyId(fakturaKundId)) {
            //
            insertCustomerAddress(fakturaKundId);
            //
        } else {
            HelpA.showNotification(LANG.MSG_ERROR_1);
        }
        //
    }

    private void insertCustomerAddress(String fakturaKundId) {
        //
        HashMap<String, String> map = tableInvertToHashMap(TABLE_INVERT_4, DB.START_COLUMN, getConfigTableInvert_4());
        //
        map.put(DB.BUH_ADDR__FAKTURAKUND_ID, fakturaKundId);
        //
        String json = JSon.hashMapToJSON(map);
        //
        try {
            //
            HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_FAKTURA_KUND_ADDR_TO_DB, json));
            //
        } catch (Exception ex) {
            Logger.getLogger(CustomersA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fillJTable_header_kund_addresses() {
        //
        JTable table = getTableAdresses();
        //
        String[] headers = {
            TABLE_FAKTURA_KUND_ADDR__ID,
            TABLE_FAKTURA_KUND_ADDR__FAKTURAKUND_ID,
            TABLE_FAKTURA_KUND_ADDR__NAMN,
            TABLE_FAKTURA_KUND_ADDR__IS_PRIMARY,
            TABLE_FAKTURA_KUND_ADDR__POSTADDR_A,
            TABLE_FAKTURA_KUND_ADDR__POSTADDR_B,
            TABLE_FAKTURA_KUND_ADDR__BESOKSADDR,
            TABLE_FAKTURA_KUND_ADDR__ZIP,
            TABLE_FAKTURA_KUND_ADDR__ORT,
            TABLE_FAKTURA_KUND_ADDR__TEL_A,
            TABLE_FAKTURA_KUND_ADDR__TEL_B,
            TABLE_FAKTURA_KUND_ADDR__OTHER
        };
        //
        table.setModel(new DefaultTableModel(null, headers));
        //
//        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        //
    }

    private void fillJTable_header_kunder() {
        //
        //
        JTable table = getTableKunder();
        //
        String[] headers = {
            TABLE_FAKTURA_KUNDER__FAKTURA_KUND_ID,
            TABLE_FAKTURA_KUNDER__KUND_ID,
            TABLE_FAKTURA_KUNDER__KUNDNR,
            TABLE_FAKTURA_KUNDER__KUND_NAMN,
            TABLE_FAKTURA_KUNDER__ORGNR,
            TABLE_FAKTURA_KUNDER__VATNR,
            TABLE_FAKTURA_KUNDER__EPOST,
            TABLE_FAKTURA_KUNDER__KATEGORI
        };
        //
        table.setModel(new DefaultTableModel(null, headers));
        //
//        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        //
    }

    public void fillJTableKundAdresses() {
        //
        JTable table = getTableAdresses();
        //
        HelpA.clearAllRowsJTable(table);
        //
        String fakturaKundId = HelpA.getValueSelectedRow(getTableKunder(), TABLE_FAKTURA_KUNDER__FAKTURA_KUND_ID);
        //
        String json = bim.getSELECT_fakturaKundId(fakturaKundId);
        //
        try {
            //
            String json_str_return = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM_GET_KUND_ADDRESSES, json));
            //
            ArrayList<HashMap<String, String>> invoices = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            //
            for (HashMap<String, String> invoice_map : invoices) {
                addRowJtable_kund_adresses(invoice_map, table);
            }
            //
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
//        HelpA.hideColumnByName(table, DB.BUH_FAKTURA_KUND__KUND_ID);
        //
    }

    private void fillKundJTable() {
        //
        JTable table = getTableKunder();
        //
        HelpA.clearAllRowsJTable(table);
        //
        String json = bim.getSELECT_kundId();
        //
        try {
            //
            String json_str_return = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM_GET_KUDNER_ALL_DATA, json));
            //
            ArrayList<HashMap<String, String>> invoices = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            //
            for (HashMap<String, String> invoice_map : invoices) {
                addRowJtable_kunder(invoice_map, table);
            }
            //
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        if (GP_BUH.CUSTOMER_MODE) {
            HelpA.hideColumnByName(table, TABLE_FAKTURA_KUNDER__KUND_ID);
            HelpA.hideColumnByName(table, TABLE_FAKTURA_KUNDER__FAKTURA_KUND_ID);
            HelpA.hideColumnByName(table, TABLE_FAKTURA_KUNDER__KUNDNR);
        }
        //
    }

    private void addRowJtable_kund_adresses(HashMap<String, String> map, JTable table) {
        //
        Object[] jtableRow = new Object[]{
            map.get(DB.BUH_ADDR__ID),
            map.get(DB.BUH_ADDR__FAKTURAKUND_ID), // hidden
            map.get(DB.BUH_ADDR__NAMN),
            map.get(DB.BUH_ADDR__IS_PRIMARY_ADDR),
            map.get(DB.BUH_ADDR__ADDR_A),
            map.get(DB.BUH_ADDR__ADDR_B),
            map.get(DB.BUH_ADDR__BESOKS_ADDR),
            map.get(DB.BUH_ADDR__POSTNR_ZIP),
            map.get(DB.BUH_ADDR__ORT),
            map.get(DB.BUH_ADDR__TEL_A),
            map.get(DB.BUH_ADDR__TEL_B),
            map.get(DB.BUH_ADDR__OTHER)
        };
        //
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(jtableRow);
        //
    }

    private void addRowJtable_kunder(HashMap<String, String> map, JTable table) {
        //
        Object[] jtableRow = new Object[]{
            map.get(DB.BUH_FAKTURA_KUND__ID), // hidden
            map.get(DB.BUH_FAKTURA_KUND__KUND_ID), // hidden
            map.get(DB.BUH_FAKTURA_KUND___KUNDNR),
            map.get(DB.BUH_FAKTURA_KUND___NAMN),
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
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert(), false, "");
        TABLE_INVERT = null;
        TABLE_INVERT = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT, 5, 0, 5, 0);
        showTableInvert(bim.jPanel5);
        //
        addTableInvertRowListener(TABLE_INVERT, this);
    }

    /**
     * UPDATE
     */
    public void showTableInvert_2() {
        //
        SET_CURRENT_OPERATION_INSERT(false);
        //
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_2(), false, "");
        TABLE_INVERT_2 = null;
        TABLE_INVERT_2 = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT_2, 5, 0, 5, 0);
        showTableInvert(bim.jPanel5, TABLE_INVERT_2);
        //
        addTableInvertRowListener(TABLE_INVERT_2, this);

    }

    /**
     * UPDATE
     */
    public void showTableInvert_3() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_3(), false, "");
        TABLE_INVERT_3 = null;
        TABLE_INVERT_3 = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT_3, 5, 0, 5, 0);
        showTableInvert(bim.jPanel7, TABLE_INVERT_3);
        //
        addTableInvertRowListener(TABLE_INVERT_3, this);
    }

    /**
     * INSERT
     */
    public void showTableInvert_4() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_4(), false, "");
        TABLE_INVERT_4 = null;
        TABLE_INVERT_4 = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT_4, 5, 0, 5, 0);
        showTableInvert(bim.jPanel7, TABLE_INVERT_4);
        //
        addTableInvertRowListener(TABLE_INVERT_4, this);
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        RowDataInvert kundnr = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___KUNDNR, TABLE_FAKTURA_KUNDER__KUNDNR, "", true, true, true);
        //
        RowDataInvert namn = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___NAMN, TABLE_FAKTURA_KUNDER__KUND_NAMN, "", true, true, true);
        //
        RowDataInvert orgnr = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___ORGNR, TABLE_FAKTURA_KUNDER__ORGNR, "", true, true, false);
        //
        RowDataInvert vatnr = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___VATNR, TABLE_FAKTURA_KUNDER__VATNR, "", true, true, false);
        //
        RowDataInvert email = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___EMAIL, TABLE_FAKTURA_KUNDER__EPOST, "", true, true, false);
        //
        RowDataInvert kund_kategori = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, DB.STATIC__KUND_KATEGORI, DB.BUH_FAKTURA_KUND___KATEGORI, TABLE_FAKTURA_KUNDER__KATEGORI, "", true, true, false);
        kund_kategori.enableFixedValues();
        kund_kategori.setUneditable();
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
    public RowDataInvert[] getConfigTableInvert_2() {
        //
        JTable table = getTableKunder();
        //
        String kundnr_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUNDER__KUNDNR);
        RowDataInvert kundnr = new RowDataInvertB(kundnr_, DB.BUH_FAKTURA_KUND___KUNDNR, TABLE_FAKTURA_KUNDER__KUNDNR, "", true, true, true);
        //
        String kundnamn_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUNDER__KUND_NAMN);
        RowDataInvert kundnamn = new RowDataInvertB(kundnamn_, DB.BUH_FAKTURA_KUND___NAMN, TABLE_FAKTURA_KUNDER__KUND_NAMN, "", true, true, true);
        //
        String orgnr_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUNDER__ORGNR);
        RowDataInvert orgnr = new RowDataInvertB(orgnr_, DB.BUH_FAKTURA_KUND___ORGNR, TABLE_FAKTURA_KUNDER__ORGNR, "", true, true, false);
        //
        String vatnr_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUNDER__VATNR);
        RowDataInvert vatnr = new RowDataInvertB(vatnr_, DB.BUH_FAKTURA_KUND___VATNR, TABLE_FAKTURA_KUNDER__VATNR, "", true, true, false);
        //
        String epost_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUNDER__EPOST);
        RowDataInvert epost = new RowDataInvertB(epost_, DB.BUH_FAKTURA_KUND___EMAIL, TABLE_FAKTURA_KUNDER__EPOST, "", true, true, false);
        //
        //
        String fixedComboValues_b = JSon._get_simple(
                HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUNDER__KATEGORI),
                DB.STATIC__KUND_KATEGORI
        );
        //
        RowDataInvert kategori = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_b, DB.BUH_FAKTURA_KUND___KATEGORI, TABLE_FAKTURA_KUNDER__KATEGORI, "", true, true, false);
        kategori.enableFixedValues();
        kategori.setUneditable();
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
     * Shows address data NOT for INSERT but for UPDATE
     *
     * @return
     */
    public RowDataInvert[] getConfigTableInvert_3() {
        //
        JTable table = getTableAdresses();
        //
        String addr_a_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUND_ADDR__POSTADDR_A);
        RowDataInvert addr_a = new RowDataInvertB(addr_a_, DB.BUH_ADDR__ADDR_A, TABLE_FAKTURA_KUND_ADDR__POSTADDR_A, "", true, true, false);
        //
        String addr_b_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUND_ADDR__POSTADDR_B);
        RowDataInvert addr_b = new RowDataInvertB(addr_b_, DB.BUH_ADDR__ADDR_B, TABLE_FAKTURA_KUND_ADDR__POSTADDR_B, "", true, true, false);
        //
        String visit_addr_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUND_ADDR__BESOKSADDR);
        RowDataInvert visit_addr = new RowDataInvertB(visit_addr_, DB.BUH_ADDR__BESOKS_ADDR, TABLE_FAKTURA_KUND_ADDR__BESOKSADDR, "", true, true, false);
        //
        String zip_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUND_ADDR__ZIP);
        RowDataInvert zip = new RowDataInvertB(zip_, DB.BUH_ADDR__POSTNR_ZIP, TABLE_FAKTURA_KUND_ADDR__ZIP, "", true, true, false);
        //
        String ort_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUND_ADDR__ORT);
        RowDataInvert ort = new RowDataInvertB(ort_, DB.BUH_ADDR__ORT, TABLE_FAKTURA_KUND_ADDR__ORT, "", true, true, false);
        //
        String tel_a_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUND_ADDR__TEL_A);
        RowDataInvert tel_a = new RowDataInvertB(tel_a_, DB.BUH_ADDR__TEL_A, TABLE_FAKTURA_KUND_ADDR__TEL_A, "", true, true, false);
        //
        String tel_b_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUND_ADDR__TEL_B);
        RowDataInvert tel_b = new RowDataInvertB(tel_b_, DB.BUH_ADDR__TEL_B, TABLE_FAKTURA_KUND_ADDR__TEL_B, "", true, true, false);
        //
        String other_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUND_ADDR__OTHER);
        RowDataInvert other = new RowDataInvertB(other_, DB.BUH_ADDR__OTHER, TABLE_FAKTURA_KUND_ADDR__OTHER, "", true, true, false);
        //
        RowDataInvert[] rows = {
            addr_a,
            addr_b,
            visit_addr,
            zip,
            ort,
            tel_a,
            tel_b,
            other
        };
        //
        return rows;
    }

    /**
     * "INSERT" This one is for INSERTING of address data
     *
     * @return
     */
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
        if (col_name.equals(DB.BUH_FAKTURA_KUND___EMAIL)) {
            //
            Validator.validateEmail(jli);
            //
        } else if (col_name.equals(DB.BUH_FAKTURA_KUND___ORGNR)) {
            //
            Validator.validateOrgnr(jli);
            //
            orgnr_additional(jli, ti);
            //
        } else if (col_name.equals(DB.BUH_FAKTURA_KUND___KUNDNR)) {
            //
            Validator.checkIfExistInDB(bim, jli, DB.BUH_FAKTURA_KUND___KUNDNR, DB.TABLE__BUH_FAKTURA_KUND);
            //
        } else if (col_name.equals(DB.BUH_FAKTURA_KUND___NAMN)) {
            //
            Validator.checkIfExistInDB(bim, jli, DB.BUH_FAKTURA_KUND___NAMN, DB.TABLE__BUH_FAKTURA_KUND);
            //
        }
        //
    }

    private void orgnr_additional(JLinkInvert jli, TableInvert ti) {
        //
        JTextFieldInvert jtfi = (JTextFieldInvert) jli;
        //
        String orgnr = getValueTableInvert(DB.BUH_FAKTURA_KUND___ORGNR, ti);
        //
        String txt = jtfi.getText();
        //
        if (txt.length() == 6) {
            jtfi.setText(orgnr + "-");
        } else if (txt.contains("--")) {
            txt = txt.replaceAll("--", "-");
            jtfi.setText(txt);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me, int column, int row, String tableName, TableInvert ti) {
        //
        super.mouseClicked(me, column, row, tableName, ti); //To change body of generated methods, choose Tools | Templates.
        //
        JLinkInvert jli = (JLinkInvert) me.getSource();
        //
        String col_name = ti.getCurrentColumnName(me.getSource());
        //
        if (col_name.equals(DB.BUH_FAKTURA_KUND___VATNR)) {
            //
            vatnrAuto(jli, ti);
            //
        } else if (col_name.equals(DB.BUH_FAKTURA_KUND___KUNDNR)) {
            //
            supposeNextKundNr(jli);
            //
        }
    }

    public String getNextKundnr() {
        //
        String json = bim.getLatest(DB.BUH_FAKTURA_KUND___KUNDNR, DB.TABLE__BUH_FAKTURA_KUND);
        //
        try {
            //
            String latest = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_LATEST, json));
            //
            System.out.println("LATEST: " + latest + "   *************************");
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
            Logger.getLogger(CustomersA.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        //
    }

    private void supposeNextKundNr(JLinkInvert jli) {
        //
        JTextFieldInvert jtfi = (JTextFieldInvert) jli;
        //
        String next = getNextKundnr();
        //
        jtfi.setText(next);
    }

    private void vatnrAuto(JLinkInvert jli, TableInvert ti) {
        //
        String vatnr = "SE";
        //
        JTextFieldInvert jtfi = (JTextFieldInvert) jli;
        //
        if (jtfi.getText().isEmpty() == false) {
            return;
        }
        //
        String orgnr = getValueTableInvert(DB.BUH_FAKTURA_KUND___ORGNR, ti);
        //
        //
        if (getValidated(DB.BUH_FAKTURA_KUND___ORGNR, ti)) {
            //
            vatnr += orgnr.replace("-", "") + "01";
            //
            jtfi.setText(vatnr);
        }
        //
    }

}
