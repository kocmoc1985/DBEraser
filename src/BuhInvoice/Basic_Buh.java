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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public abstract class Basic_Buh extends Basic {

    protected final BUH_INVOICE_MAIN bim;

    public Basic_Buh(BUH_INVOICE_MAIN bim) {
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

    protected String requestJComboValuesHttp(String php_function, String keyOne, String keyTwo) {
        //
        String comboString;
        //
        String json = bim.getSELECT_kundId();
        //
        try {
            //
            String json_str_return = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    php_function, json));
            //
            //
            comboString = JSon.phpJsonResponseToComboBoxString(json_str_return, keyOne, keyTwo);
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

    @Override
    public void keyReleasedForward(TableInvert ti, KeyEvent ke) {
        //
        super.keyReleasedForward(ti, ke); //To change body of generated methods, choose Tools | Templates.
        //
        JLinkInvert jli = (JLinkInvert) ke.getSource();
        //
        String col_name = ti.getCurrentColumnName(ke.getSource());
        //
//        TEST_REFERENSES(ke);
        fieldUpdateWatcher(ke);
        //
        //
        if (col_name.equals(DB.BUH_F_ARTIKEL__ANTAL)
                || col_name.equals(DB.BUH_F_ARTIKEL__PRIS)
                || col_name.equals(DB.BUH_F_ARTIKEL__RABATT)
                //
                || col_name.equals(DB.BUH_FAKTURA__EXP_AVG)
                || col_name.equals(DB.BUH_FAKTURA__FRAKT)) {
            //
            Validator.validateDigitalInput(jli);
            //
        } else if (col_name.equals(DB.BUH_FAKTURA_KUND___EMAIL)) {
            //
            Validator.validateEmail(jli);
            //
        } else if (col_name.equals(DB.BUH_FAKTURA_KUND___ORGNR)) {
            //
            Validator.validateOrgnr(jli);
            //
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
        }
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
        if (Validator.validateOrgnr(orgnr)) {
            vatnr += orgnr.replace("-", "") + "01";
            //
            jtfi.setText(vatnr);
        }
        //
    }

    @Override
    public void jComboBoxItemStateChangedForward(TableInvert ti, ItemEvent ie) {
        //
        super.jComboBoxItemStateChangedForward(ti, ie); //To change body of generated methods, choose Tools | Templates.
        //
        fieldUpdateWatcher(ie);
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
