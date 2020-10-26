/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTable.TableData;
import MyObjectTableInvert.Basic;
import MyObjectTableInvert.ColumnDataEntryInvert;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.JTextFieldInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableInvert;
import MyObjectTableInvert.TableRowInvert;
import java.awt.AWTEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public abstract class Basic_Buh extends Basic {

    protected final BUH_INVOICE_MAIN_ bim;

    public Basic_Buh(BUH_INVOICE_MAIN_ bim) {
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

    /**
     * 
     * @param fakturaId 
     */
    public static void executeSetFakturaSentPerEmail(String fakturaId){
       //
        HashMap<String, String> map = BUH_INVOICE_MAIN_.getUPDATE_static(DB.BUH_FAKTURA__ID__, fakturaId, DB.TABLE__BUH_FAKTURA);
        // 
        map.put(DB.BUH_FAKTURA__SENT, "1"); // 1 = "yes", "0"
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

    protected String requestJComboValuesHttp(String php_function, String[] keys) {
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
            if(json_str_return.equals(DB.PHP_SCRIPT_RETURN_EMPTY)){
                return "";
            }
            //
            comboString = JSon.phpJsonResponseToComboBoxString(json_str_return, keys);
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
    public void jComboBoxItemStateChangedForward(TableInvert ti, ItemEvent ie) {
        //
        super.jComboBoxItemStateChangedForward(ti, ie); //To change body of generated methods, choose Tools | Templates.
        //
//        fieldUpdateWatcher(ie);
        //       
    }

    @Override
    public void mouseClicked(MouseEvent me, int column, int row, String tableName, TableInvert tableInvert) {
        //
        super.mouseClicked(me, column, row, tableName, tableInvert); //To change body of generated methods, choose Tools | Templates.
        //
//        fieldUpdateWatcher(me);
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
    
    protected  void orgnr_additional(JLinkInvert jli, TableInvert ti) {
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

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showTableInvert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initializeSaveIndicators() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getUnsaved(int nr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
