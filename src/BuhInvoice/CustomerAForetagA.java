/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import static BuhInvoice.CustomersA.TABLE_FAKTURA_KUNDER__FAKTURA_KUND_ID;
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
public abstract class CustomerAForetagA extends Basic_Buh_ {

    //
    protected Table TABLE_INVERT_2;
    protected Table TABLE_INVERT_3;
    protected Table TABLE_INVERT_4;
    //
    protected boolean CURRENT_OPERATION_INSERT = false;
    //
    protected static final String TABLE_FAKTURA_KUND_ADDR__ID = "ID";
    protected static final String TABLE_FAKTURA_KUND_ADDR__FAKTURAKUND_ID = "FKUNDID";
    protected static final String TABLE_FAKTURA_KUND_ADDR__NAMN = "NAMN";
    protected static final String TABLE_FAKTURA_KUND_ADDR__IS_PRIMARY = "PRIMÄR";
    protected static final String TABLE_FAKTURA_KUND_ADDR__POSTADDR_A = "ADRESS 1";
    protected static final String TABLE_FAKTURA_KUND_ADDR__POSTADDR_B = "ADRESS 2";
    protected static final String TABLE_FAKTURA_KUND_ADDR__BESOKSADDR = "BESÖKS ADR";
    protected static final String TABLE_FAKTURA_KUND_ADDR__ZIP = "POSTNR";
    protected static final String TABLE_FAKTURA_KUND_ADDR__ORT = "ORT";
    protected static final String TABLE_FAKTURA_KUND_ADDR__TEL_A = "TEL 1";
    protected static final String TABLE_FAKTURA_KUND_ADDR__TEL_B = "TEL 2";
    protected static final String TABLE_FAKTURA_KUND_ADDR__OTHER = "ANNAT";

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
        fillJTable_header_main();
        fillJTable_header_address();
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

    protected void refresh() {
        //
        fillMainTable();
        HelpA.markFirstRowJtable(getTableMain());
        //
        if (this instanceof CustomersA) {
            bim.jTableCustomersA_kunder_clicked();
        } else if (this instanceof ForetagA) {
            bim.jTableForetagA_ftg_table_clicked();
        }
        //
    }

    protected abstract void fillJTable_header_main();

    protected JTable getTableMain() {
        if (this instanceof CustomersA) {
            return bim.jTable_kunder;
        } else if (this instanceof ForetagA) {
            return bim.jTable_ftg;
        } else {
            return null;
        }
    }

    protected JTable getTableAdress() {
        if (this instanceof CustomersA) {
            return bim.jTable_kund_adresses;
        } else if (this instanceof ForetagA) {
            return bim.jTable_ftg_addr;
        } else {
            return null;
        }
    }

    protected void hideAdressTable() {
        if (this instanceof CustomersA) {
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
        if (this instanceof CustomersA) {
            idColName = CustomersA.TABLE_FAKTURA_KUNDER__FAKTURA_KUND_ID;
            phpFunc = DB.PHP_FUNC_PARAM_GET_FAKTURA_KUND_ADDRESSES;
        } else if (this instanceof ForetagA) {
            idColName = ForetagA.TABLE__ID;
            phpFunc = DB.PHP_FUNC_PARAM_GET_KUND_ADDRESS; // **********************************************
        }
        //
        String id = HelpA.getValueSelectedRow(getTableMain(), idColName);
        //
        String json = bim.getSELECT_fakturaKundId(id);
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

    protected void fillMainTable() {
        //
        JTable table = getTableMain();
        //
        HelpA.clearAllRowsJTable(table);
        //
        String json = bim.getSELECT_kundId();
        //
        String phpFunc = "";
        //
        if (this instanceof CustomersA) {
            phpFunc = DB.PHP_FUNC_PARAM_GET_FAKTURA_KUNDER_ALL_DATA;
        } else if (this instanceof ForetagA) {
            phpFunc = DB.PHP_FUNC_PARAM_GET_KUND_DATA; // ****************************************************
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
        hideColumnsMainTable();
        //
    }

    protected abstract void hideColumnsMainTable();

    protected abstract void addRowJtable_main(HashMap<String, String> map, JTable table);

    protected void update() {
        //
        String id = "";
        String address_id = HelpA.getValueSelectedRow(getTableAdress(), TABLE_FAKTURA_KUND_ADDR__ID);
        //
        if (this instanceof CustomersA) {
            id = HelpA.getValueSelectedRow(getTableMain(), CustomersA.TABLE_FAKTURA_KUNDER__FAKTURA_KUND_ID);
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
        if (this instanceof CustomersA) {
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

    public abstract RowDataInvert[] getConfigTableInvert_4();

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
            Validator.validateEmail(jli);
            //
        } else if (col_name.equals(DB.BUH_FAKTURA_KUND___ORGNR)) {
            //
            Validator.validateOrgnr(jli);
            //
            orgnr_additional(jli, ti);
            //
        }
        //
    }

    private int prevLengthOrgnr;

    private void orgnr_additional(JLinkInvert jli, TableInvert ti) {
        //
        JTextFieldInvert jtfi = (JTextFieldInvert) jli;
        //
        String orgnr = getValueTableInvert(DB.BUH_FAKTURA_KUND___ORGNR, ti);
        //
        String txt = jtfi.getText();
        //
        if (txt.length() == 6 && prevLengthOrgnr == 5) {
            jtfi.setText(orgnr + "-");
        } else if (txt.contains("--")) {
            txt = txt.replaceAll("--", "-");
            jtfi.setText(txt);
        }
        //
        prevLengthOrgnr = txt.length();
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
