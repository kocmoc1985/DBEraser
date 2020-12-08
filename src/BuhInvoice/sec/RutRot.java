/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import BuhInvoice.BUH_INVOICE_MAIN;
import BuhInvoice.Basic_Buh;
import BuhInvoice.DB;
import BuhInvoice.Validator;
import MyObjectTable.OutPut;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import forall.HelpA;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import javax.swing.JPanel;

/**
 *
 * @author KOCMOC
 */
public class RutRot extends Basic_Buh {

    private final RutRotFrame rutRotFrame;
    public static final String COL__FORNAMN = "FÖRNAMN";
    public static final String COL__EFTERNAMN = "EFTERNAMN";
    public static final String COL__AVDRAG = "AVDRAG";
    public static final String COL__PNR = "PERSONNUMMER";

    public RutRot(BUH_INVOICE_MAIN bim, RutRotFrame rutRotFrame) {
        super(bim);
        this.rutRotFrame = rutRotFrame;
    }

    public JPanel getTableInvertPanel() {
        return rutRotFrame.jPanel_table_invert;
    }

    public HashMap<String, String> getValuesTableInvert() {
        return tableInvertToHashMap(TABLE_INVERT, DB.START_COLUMN);
    }

    @Override
    public void showTableInvert() {
        //
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert(), false, "buh_faktura_a");
        TABLE_INVERT = null;
        TABLE_INVERT = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT, 5, 0, 5, 0);
        showTableInvert(getTableInvertPanel());
        //
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        RowDataInvert fornamn = new RowDataInvertB("", DB.BUH_FAKTURA_RUT__FORNAMN, COL__FORNAMN, "", true, true, true);
        //
        RowDataInvert efternamn = new RowDataInvertB("", DB.BUH_FAKTURA_RUT__EFTERNAMN, COL__EFTERNAMN, "", true, true, true);
        //
        RowDataInvert pnr = new RowDataInvertB("", DB.BUH_FAKTURA_RUT__PNR, COL__PNR + " (12 SIFFROR)", "", true, true, true);
        //
        RowDataInvert[] rows = {
            fornamn,
            efternamn,
            pnr
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
        if (col_name.equals(DB.BUH_FAKTURA_RUT__PNR)) {
            //
            if (Validator.validateMaxInputLength(jli, 13)) {
                Validator.validatePnr(jli);
                pnr_additional(jli, ti);
            }
            //
        } else if (col_name.equals(DB.BUH_FAKTURA_RUT__FORNAMN)
                || col_name.equals(DB.BUH_FAKTURA_RUT__EFTERNAMN)) {
            //
            Validator.validateMaxInputLength(jli, 100);
            //
        }
    }

    @Override
    protected void startUp() {
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
        return true;
    }

}
