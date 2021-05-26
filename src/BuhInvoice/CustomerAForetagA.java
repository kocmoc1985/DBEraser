/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.LANG;
import MyObjectTable.Table;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.JTextFieldInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
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
public abstract class CustomerAForetagA extends Basic_Buh {

    //
    protected Table TABLE_INVERT_2;
    protected Table TABLE_INVERT_3;
    protected Table TABLE_INVERT_4;
    //
    protected boolean CURRENT_OPERATION_INSERT = false;
    //
    protected static String TABLE__COL_ORGNR__PNR = "";
    //
    protected static final String TABLE_FAKTURA_KUND_ADDR__ID = "ID";
    protected static final String TABLE_FAKTURA_KUND_ADDR__FAKTURAKUND_ID = "FKUNDID";
    protected static final String TABLE_FAKTURA_KUND_ADDR__IS_PRIMARY = "PRIMÄR";
    protected static final String TABLE_FAKTURA_KUND_ADDR__POSTADDR_A = "ADRESS 1";
    protected static final String TABLE_FAKTURA_KUND_ADDR__POSTADDR_B = "ADRESS 2";
    protected static final String TABLE_FAKTURA_KUND_ADDR__BESOKSADDR = "BESÖKS ADR";
    protected static final String TABLE_FAKTURA_KUND_ADDR__ZIP = "POSTNR";
    protected static final String TABLE_FAKTURA_KUND_ADDR__ORT = "ORT";
    protected static final String TABLE_FAKTURA_KUND_ADDR__LAND = "LAND";
    protected static final String TABLE_FAKTURA_KUND_ADDR__TEL_A = "TEL 1";
    protected static final String TABLE_FAKTURA_KUND_ADDR__TEL_B = "TEL 2";
    protected static final String TABLE_FAKTURA_KUND_ADDR__OTHER = "ANNAT";
    //
    protected boolean IS_PERSON__CUSTOMERS_A = false;

    //
    public CustomerAForetagA(BUH_INVOICE_MAIN bim) {
        super(bim);
    }

    protected abstract void SET_CURRENT_OPERATION_INSERT(boolean insert);

    protected boolean getCurrentOperationInsert() {
        return CURRENT_OPERATION_INSERT;
    }

    @Override
    protected void startUp() {
        //
        if (GP_BUH.CUSTOMER_MODE == true) {
            hideAdressTable();
        }
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //
                refresh();
                //
            }
        });
        //
    }

    private void defineOrgnrOrPnr() {
        //
        if (this instanceof CustomersA_ && IS_PERSON__CUSTOMERS_A) {
            TABLE__COL_ORGNR__PNR = "PERSONNUMMER (YYMMDD-XXXX)";
        } else {
            TABLE__COL_ORGNR__PNR = "ORGNR";
        }
        //
    }

    protected void refresh() {
        //#THREAD#
        //===================================
        //  Important block of code, change wit extreem caution [2020-12-29]
        defineOrgnrOrPnr();
        //
        fillJTable_header_main();
        hideColumnsMainTable();
        fillJTable_header_address();
        //====================================
        //
        Thread x = new Thread(() -> {
            //
            fillMainTable();
            HelpA.markFirstRowJtable(getTableMain());
            //
            java.awt.EventQueue.invokeLater(() -> {
                if (this instanceof CustomersA_) {
                    //
                    if (this instanceof CustomersA_) {
                        CustomersA_ ca = (CustomersA_) this;
                        ca.fillSearchJCombo(CustomersA_.TABLE_FAKTURA_KUNDER__KUND_NAMN);
                    }
                    //
                    bim.jTableCustomersA_kunder_clicked();
                    //
                } else if (this instanceof ForetagA) {
                    bim.jTableForetagA_ftg_table_clicked();
                }
            });
            //
        });
        //
        x.start();
        //
    }

    protected abstract void fillJTable_header_main();

    protected JTable getTableMain() {
        if (this instanceof CustomersA_) {
            return bim.jTable_kunder;
        } else if (this instanceof ForetagA) {
            return bim.jTable_ftg;
        } else {
            return null;
        }
    }

    protected JTable getTableAdress() {
        if (this instanceof CustomersA_) {
            return bim.jTable_kund_adresses;
        } else if (this instanceof ForetagA) {
            return bim.jTable_ftg_addr;
        } else {
            return null;
        }
    }

    protected void hideAdressTable() {
        if (this instanceof CustomersA_) {
            bim.jScrollPane8.setVisible(false);
        } else if (this instanceof ForetagA) {
            bim.jScrollPane12.setVisible(false);
            bim.jScrollPane11.setVisible(false);
        }
    }

    protected void fillJTable_header_address() {
        //
        JTable table = getTableAdress();
        //
        String[] headers = {
            TABLE_FAKTURA_KUND_ADDR__ID,
            TABLE_FAKTURA_KUND_ADDR__FAKTURAKUND_ID,
            TABLE_FAKTURA_KUND_ADDR__IS_PRIMARY,
            TABLE_FAKTURA_KUND_ADDR__POSTADDR_A,
            TABLE_FAKTURA_KUND_ADDR__POSTADDR_B,
            TABLE_FAKTURA_KUND_ADDR__BESOKSADDR,
            TABLE_FAKTURA_KUND_ADDR__ZIP,
            TABLE_FAKTURA_KUND_ADDR__ORT,
            TABLE_FAKTURA_KUND_ADDR__LAND,
            TABLE_FAKTURA_KUND_ADDR__TEL_A,
            TABLE_FAKTURA_KUND_ADDR__TEL_B,
            TABLE_FAKTURA_KUND_ADDR__OTHER
        };
        //
        table.setModel(new DefaultTableModel(null, headers));
        //
    }

    protected void fillAddressTable() {
        //
        JTable table = getTableAdress();
        //
        HelpA.clearAllRowsJTable(table);
        //
        String idColName = "";
        String phpFunc = "";
        //
        String id = null;
        String json = null;
        //
        if (this instanceof CustomersA_) {
            idColName = CustomersA_.TABLE_FAKTURA_KUNDER__FAKTURA_KUND_ID;
            phpFunc = DB.PHP_FUNC_PARAM_GET_FAKTURA_KUND_ADDRESSES;
            id = HelpA.getValueSelectedRow(getTableMain(), idColName);
            //
            if (id == null || id.isEmpty()) {
                return;
            }
            //
            json = bim.getSELECT_fakturaKundId__doubleWhere(id);
            //
        } else if (this instanceof ForetagA) {
            idColName = ForetagA.TABLE__ID;
            phpFunc = DB.PHP_FUNC_PARAM_GET_FORETAG_ADDRESS; // **********************************************
            //
            json = bim.getSELECT_kundId();
            //
        }
        //

        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    phpFunc, json);
            //
            ArrayList<HashMap<String, String>> invoices = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            //
            for (HashMap<String, String> invoice_map : invoices) {
                addRowJtable_adress(invoice_map, table);
            }
            //
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
    }

    protected void addRowJtable_adress(HashMap<String, String> map, JTable table) {
        //
        Object[] jtableRow = new Object[]{
            map.get(DB.BUH_ADDR__ID),
            map.get(DB.BUH_ADDR__FAKTURAKUND_ID), // hidden
            map.get(DB.BUH_ADDR__IS_PRIMARY_ADDR),
            map.get(DB.BUH_ADDR__ADDR_A),
            map.get(DB.BUH_ADDR__ADDR_B),
            map.get(DB.BUH_ADDR__BESOKS_ADDR),
            map.get(DB.BUH_ADDR__POSTNR_ZIP),
            map.get(DB.BUH_ADDR__ORT),
            map.get(DB.BUH_ADDR__LAND),
            map.get(DB.BUH_ADDR__TEL_A),
            map.get(DB.BUH_ADDR__TEL_B),
            map.get(DB.BUH_ADDR__OTHER)
        };
        //
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(jtableRow);
        //
    }

    protected void fillMainTable() {
        //
        JTable table = getTableMain();
        //
        HelpA.clearAllRowsJTable(table);
        //
        HelpA.rowsorter_jtable_add_reset(table);
        //
        String json = bim.getSELECT_kundId();
        //
        String phpFunc = "";
        //
        if (this instanceof CustomersA_) {
            //
            if (IS_PERSON__CUSTOMERS_A) {
                phpFunc = DB.PHP_FUNC_PARAM_GET_FAKTURA_KUNDER_ALL_DATA__PERSON;
            } else {
                phpFunc = DB.PHP_FUNC_PARAM_GET_FAKTURA_KUNDER_ALL_DATA;
            }
            //
        } else if (this instanceof ForetagA) {
            phpFunc = DB.PHP_FUNC_PARAM_GET_FORETAG_DATA; // ****************************************************
        }
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    phpFunc, json);
            //
            ArrayList<HashMap<String, String>> invoices = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            //
            for (HashMap<String, String> invoice_map : invoices) {
                addRowJtable_main(invoice_map, table);
            }
            //
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //

        //
    }

    protected abstract void hideColumnsMainTable();

    protected abstract void addRowJtable_main(HashMap<String, String> map, JTable table);

    protected void update() {
        //
        String id = "";
        String address_id = HelpA.getValueSelectedRow(getTableAdress(), TABLE_FAKTURA_KUND_ADDR__ID);
        //
        if (this instanceof CustomersA_) {
            id = HelpA.getValueSelectedRow(getTableMain(), CustomersA_.TABLE_FAKTURA_KUNDER__FAKTURA_KUND_ID);
        } else if (this instanceof ForetagA) {
            id = HelpA.getValueSelectedRow(getTableMain(), ForetagA.TABLE__ID);
        }
        //
        if (GP_BUH.verifyId(id)) {
            updateMainData(id);
        }
        //
        if (GP_BUH.verifyId(address_id)) {
            updateAddressData(address_id);
        } else {

        }
        //
        refresh();
        //
    }

    private void updateMainData(String id) {
        //
        String idColName = "";
        String tableName = "";
        //
        if (this instanceof CustomersA_) {
            idColName = DB.BUH_FAKTURA_KUND__ID;
            tableName = DB.TABLE__BUH_FAKTURA_KUND;
        } else if (this instanceof ForetagA) {
            idColName = DB.BUH_KUND__ID;
            tableName = DB.TABLE__BUH_KUND;
        }
        //
        HashMap<String, String> map = tableInvertToHashMap(TABLE_INVERT_2, DB.START_COLUMN);
        //
        HashMap<String, String> update_map = bim.getUPDATE(idColName, id, tableName);
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
        HashMap<String, String> map = tableInvertToHashMap(TABLE_INVERT_3, DB.START_COLUMN);
        //
        if (map == null || map.isEmpty()) {
            return;
        }
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

    @Override
    protected boolean fieldsValidated(boolean insert) {
        //
        if (insert) {
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
            if (containsInvalidatedFields(TABLE_INVERT_4, DB.START_COLUMN, getConfigTableInvert_4())) {
                HelpA.showNotification(LANG.MSG_1);
                return false;
            }
            // 
        } else {
            //
            if (containsEmptyObligatoryFields(TABLE_INVERT_2, DB.START_COLUMN, getConfigTableInvert_2())) {
                HelpA.showNotification(LANG.MSG_2);
                return false;
            }
            //
            if (containsInvalidatedFields(TABLE_INVERT_2, DB.START_COLUMN, getConfigTableInvert_2())) {
                HelpA.showNotification(LANG.MSG_1);
                return false;
            }
            //
            if (containsInvalidatedFields(TABLE_INVERT_3, DB.START_COLUMN, getConfigTableInvert_3())) {
                HelpA.showNotification(LANG.MSG_1);
                return false;
            }
            // 
        }

        return true;
    }

    /**
     * [UPDATE]
     *
     * @return
     */
    public abstract RowDataInvert[] getConfigTableInvert_2();

    /**
     * [UPDATE ADDRESS]
     *
     * @return
     */
    public RowDataInvert[] getConfigTableInvert_3() {
        //
        JTable table = getTableAdress();
        //
        String addr_a_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUND_ADDR__POSTADDR_A);
        RowDataInvert addr_a = new RowDataInvertB(addr_a_, DB.BUH_ADDR__ADDR_A, TABLE_FAKTURA_KUND_ADDR__POSTADDR_A, "", true, true, false);
        //
        String addr_b_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUND_ADDR__POSTADDR_B);
        RowDataInvert addr_b = new RowDataInvertB(addr_b_, DB.BUH_ADDR__ADDR_B, TABLE_FAKTURA_KUND_ADDR__POSTADDR_B, "", true, true, false);
        //
//        String visit_addr_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUND_ADDR__BESOKSADDR);
//        RowDataInvert visit_addr = new RowDataInvertB(visit_addr_, DB.BUH_ADDR__BESOKS_ADDR, TABLE_FAKTURA_KUND_ADDR__BESOKSADDR, "", true, true, false);
        //
        String zip_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUND_ADDR__ZIP);
        RowDataInvert zip = new RowDataInvertB(zip_, DB.BUH_ADDR__POSTNR_ZIP, TABLE_FAKTURA_KUND_ADDR__ZIP, "", true, true, false);
        //
        String ort_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUND_ADDR__ORT);
        RowDataInvert ort = new RowDataInvertB(ort_, DB.BUH_ADDR__ORT, TABLE_FAKTURA_KUND_ADDR__ORT, "", true, true, false);
        //
        String land_ = HelpA.getValueSelectedRow(table, TABLE_FAKTURA_KUND_ADDR__LAND);
        RowDataInvert land = new RowDataInvertB(land_, DB.BUH_ADDR__LAND, TABLE_FAKTURA_KUND_ADDR__LAND, "", true, true, false);
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
            //            visit_addr,
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

    public abstract RowDataInvert[] getConfigTableInvert_4();

//    @Override
//    public void mouseClicked(MouseEvent me, int column, int row, String tableName, TableInvert ti) {
//        //
//        super.mouseClicked(me, column, row, tableName, ti); //To change body of generated methods, choose Tools | Templates.
//        //
//        JLinkInvert jli = (JLinkInvert) me.getSource();
//        //
//        String col_name = ti.getCurrentColumnName(me.getSource());
//        //
//        if (col_name.equals(DB.BUH_FAKTURA_KUND___VATNR)) {
//            //
//            vatnrAuto(jli, ti, DB.BUH_FAKTURA_KUND___ORGNR);
//            //
//        }
//    }
    @Override
    public void mouseClickedForward(MouseEvent me, int column, int row, String tableName, TableInvert ti) {
        //
        super.mouseClickedForward(me, column, row, tableName, ti);
        //
        JLinkInvert jli = (JLinkInvert) me.getSource();
        //
        String col_name = ti.getCurrentColumnName(me.getSource());
        //
        if (col_name.equals(DB.BUH_FAKTURA_KUND___VATNR)) {
            //
            vatnrAuto(jli, ti, DB.BUH_FAKTURA_KUND___ORGNR);
            //
        }
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
            if (Validator.validateMaxInputLength(jli, 320)) {
                Validator.validateEmail(jli);
            }
            //
        } else if (col_name.equals(DB.BUH_FAKTURA_KUND___ORGNR)) {
            //
            if (Validator.validateMaxInputLength(jli, 30)) {
                Validator.validateOrgnr(jli);
                orgnr_additional(jli, ti);
            }
            //
        } //
        //
        // This is for both "CustomersA" & "ForetagA" [2020-09-30]
        else if (col_name.equals(DB.BUH_ADDR__ADDR_A)
                || col_name.equals(DB.BUH_ADDR__ADDR_B)
                || col_name.equals(DB.BUH_ADDR__BESOKS_ADDR)) {
            //
            Validator.validateMaxInputLength(jli, 300);
            //
        } else if (col_name.equals(DB.BUH_ADDR__POSTNR_ZIP)) {
            //
            Validator.validateMaxInputLength(jli, 10);
            //
        } else if (col_name.equals(DB.BUH_ADDR__ORT) || col_name.equals(DB.BUH_ADDR__LAND)) {
            //
            Validator.validateMaxInputLength(jli, 100);
            //
        } else if (col_name.equals(DB.BUH_ADDR__TEL_A) || col_name.equals(DB.BUH_ADDR__TEL_B)) {
            //
            Validator.validateMaxInputLength(jli, 50);
            //
        } else if (col_name.equals(DB.BUH_ADDR__OTHER)) {
            //
            Validator.validateMaxInputLength(jli, 500);
            //
        } else if (col_name.equals(DB.BUH_FAKTURA_KUND___VATNR)) {
            //
            Validator.validateMaxInputLength(jli, 30);
            //
        }
        //
    }

    protected void vatnrAuto(JLinkInvert jli, TableInvert ti, String param) {
        //
        String vatnr = "SE";
        //
        JTextFieldInvert jtfi = (JTextFieldInvert) jli;
        //
        if (jtfi.getText().isEmpty() == false) {
            return;
        }
        //
        String orgnr = getValueTableInvert(param, ti);
        //
        if (orgnr.isEmpty()) {
            return;
        }
        //
        if (getValidated(param, ti)) {
            //
            vatnr += orgnr.replace("-", "") + "01";
            //
            jtfi.setText(vatnr);
        }
        //
    }

}
