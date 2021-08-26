/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import static BuhInvoice.InvoiceB.TABLE_ALL_INVOICES__KUND_ID;
import MyObjectTable.Table;
import MyObjectTable.TableData;
import MyObjectTableInvert.Basic;
import MyObjectTableInvert.ColumnDataEntryInvert;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.JTextFieldInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableInvert;
import MyObjectTableInvert.TableRowInvert;
import forall.HelpA;
import java.awt.AWTEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;

/**
 *
 * @author KOCMOC
 */
public abstract class Basic_Buh extends Basic {

    protected final LAFakturering bim;

    public Basic_Buh(LAFakturering bim) {
        this.bim = bim;
        initOther();
    }

    private void initOther() {
        startUp();
    }

    protected abstract void startUp();

    protected String getKundId() {
        return bim.getKundId();
    }

    @Override
    public void showTableInvert(JComponent container, Table tableInvert) {
        //
        GP_BUH.INVOICE_TABLES_INITIALIZATION_READY = false; //[#SAVE-INVOICE-NOTE#]
        //
        super.showTableInvert(container, tableInvert); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doOtherRepaintThread(Table table) {
        TableInvert ti = (TableInvert) table;
        String tableName = ti.getTABLE_NAME();
        if (tableName.equals("buh_faktura_b__table_3")) {
            GP_BUH.INVOICE_TABLES_INITIALIZATION_READY = true; // [#SAVE-INVOICE-NOTE#]
        }
    }

    /**
     *
     * @param fakturaId
     */
    public static void executeSetFakturaSentPerEmail(String fakturaId, boolean sent) {
        //
        HashMap<String, String> map = LAFakturering.getUPDATE_static(DB.BUH_FAKTURA__ID__, fakturaId, DB.TABLE__BUH_FAKTURA);
        //
        if (sent) {
            map.put(DB.BUH_FAKTURA__SENT, "1"); // 1 = "yes", "0"
        } else {
            map.put(DB.BUH_FAKTURA__SENT, "0"); // 1 = "yes", "0"
        }
        //
        String json = JSon.hashMapToJSON(map);
        //
        HelpBuh.update(json);
        //
    }

    /**
     *
     * @param fakturaId
     * @param status 0 = ej betald; 1 = betald; 2 = delvis; 3 = över
     */
    protected void executeSetFakturaBetald(String fakturaId, int status) {
        //
        HashMap<String, String> map = bim.getUPDATE(DB.BUH_FAKTURA__ID__, fakturaId, DB.TABLE__BUH_FAKTURA);
        //
        map.put(DB.BUH_FAKTURA__BETALD, "" + status);
        //
        String json = JSon.hashMapToJSON(map);
        //
        HelpBuh.update(json);
        //
    }

    protected void executeDelete(String json) {
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_DELETE, json);
            //
            System.out.println("query: " + json_str_return);
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected String requestJComboValuesHttp(String php_function, String[] keys, boolean specialSeparator) {
        //
        String comboString;
        //
        String json = bim.getSELECT_kundId();
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    php_function, json);
            //
            if (json_str_return.equals(DB.PHP_SCRIPT_RETURN_EMPTY)) {
                return "";
            }
            //[#AUTOMATIC-COMMA-WITH-POINT-REPLACEMENT--ARTICLE-NAME#]
            comboString = JSon.phpJsonResponseToComboBoxString(json_str_return, keys, specialSeparator);
            //
//            System.out.println("combo string: " + comboString);
            //
        } catch (Exception ex) {
            Logger.getLogger(Basic_Buh.class.getName()).log(Level.SEVERE, null, ex);
            comboString = null;
        }
        //
        return comboString;
    }

    protected abstract boolean fieldsValidated(boolean insert);

    @Override
    public void keyReleasedForward(TableInvert ti, KeyEvent ke) {
        //
        super.keyReleasedForward(ti, ke); //To change body of generated methods, choose Tools | Templates.
        //
//        TEST_REFERENSES(ke);
//        fieldUpdateWatcher(ke);
        //
    }

    @Override
    public void valueChangedForward(TableInvert ti, DocumentEvent evt, JLinkInvert parent, String colName) {
        //
        super.valueChangedForward(ti, evt, parent, colName); //To change body of generated methods, choose Tools | Templates.
        //
        if (GP_BUH.INVOICE_TABLES_INITIALIZATION_READY) {
            //[#SAVE-INVOICE-NOTE#]
            GP_BUH.showSaveInvoice_note(true);
            //
        }
        //
    }

    @Override
    public void jComboBoxItemStateChangedForward(TableInvert ti, ItemEvent ie) {
        //
        super.jComboBoxItemStateChangedForward(ti, ie); //To change body of generated methods, choose Tools | Templates.
        //
        if (GP_BUH.INVOICE_TABLES_INITIALIZATION_READY) {
            //[#SAVE-INVOICE-NOTE#]
            GP_BUH.showSaveInvoice_note(true);
            //
        }
        //
    }

    /**
     * [2020-07-30] Marking the field is considered to be updated.
     */
    private void fieldUpdateWatcher(AWTEvent evt) {
        //
        if (evt.getSource() instanceof JLinkInvert) {
            //
            JLinkInvert jli = (JLinkInvert) evt.getSource();
            //
            jli.setFieldUpdatedAuto();
            //
        }
        //
    }

    /**
     * IMPORTANT EXAMPLE [2020-07-30]
     *
     * @param evt
     */
    private void TEST_REFERENSES(AWTEvent evt) {
        //
        if (evt.getSource() instanceof JLinkInvert) {
            //
            JLinkInvert jli = (JLinkInvert) evt.getSource();
            //
            TableRowInvert tri = jli.getParentObj();
            //
            TableInvert ti_ = (TableInvert) tri.getTable();
            //
            TableData ta = ti_.TABLE_DATA;
            //
            RowDataInvert rdi = tri.getRowConfig();
            //
            ColumnDataEntryInvert cde = jli.getChildObject();
            //
            String initialValue = cde.getInitialValue();
            //
            String actualValue = jli.getValue();
            //
            boolean valuChanged = jli.valueUpdated();
            //
            System.out.println("");
            System.out.println("InitialValue: " + initialValue);
            System.out.println("ActualValue: " + actualValue);
            System.out.println("Value Changed: " + valuChanged);
            System.out.println("");
            //
        }
        //
    }

    private int prevLengthOrgnr;

    protected void orgnr_additional(JLinkInvert jli, TableInvert ti) {
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

    protected void pnr_additional(JLinkInvert jli, TableInvert ti) {
        //
        JTextFieldInvert jtfi = (JTextFieldInvert) jli;
        //
        String orgnr = getValueTableInvert(DB.BUH_FAKTURA_RUT_PERSON__PNR, ti);
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

    public HashMap<String, String> getFakturaKundData(String phpFunction, TableInvert ti) {
        //
        String fakturaKundId;
        //
        if (ti == null) {
            fakturaKundId = _get(TABLE_ALL_INVOICES__KUND_ID);
        } else {
            fakturaKundId = getValueTableInvert(DB.BUH_FAKTURA__FAKTURAKUND_ID, ti);
        }
        //
        String json = bim.getSELECT_fakturaKundId__doubleWhere(fakturaKundId); // [#CRUSUAL-CHANGES-EXAMPLE-A#]
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    phpFunction, json);
            //
            ArrayList<HashMap<String, String>> addresses = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            return addresses.get(0);
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public HashMap<String, String> getFakturaKundData_b(String phpFunction, String fakturaKundId) {
        //
        if (fakturaKundId == null || fakturaKundId.equals("NULL") || fakturaKundId.isEmpty()) {
            return null;
        }
        //
        String json = bim.getSELECT_fakturaKundId__doubleWhere(fakturaKundId); // [#CRUSUAL-CHANGES-EXAMPLE-A#]
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    phpFunction, json);
            //
            ArrayList<HashMap<String, String>> map = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            return map.get(0);
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public HashMap<String, String> getFakturaArtikelData(String phpFunction, String artikelId) {
        //
        if (artikelId == null || artikelId.equals("NULL") || artikelId.isEmpty() || artikelId.equals("-")) {
            return null;
        }
        //
        String json = bim.getSELECT_artikelId__doubleWhere(artikelId);
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    phpFunction, json);
            //
            ArrayList<HashMap<String, String>> map = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            return map.get(0);
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String _get(String colNameJTable) {
        JTable table = bim.jTable_invoiceB_alla_fakturor;
        return HelpA.getValueSelectedRow(table, colNameJTable);
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

}
