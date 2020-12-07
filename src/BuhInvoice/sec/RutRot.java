/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import BuhInvoice.BUH_INVOICE_MAIN;
import BuhInvoice.Basic_Buh;
import BuhInvoice.DB;
import MyObjectTable.OutPut;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert;
import javax.swing.JPanel;

/**
 *
 * @author KOCMOC
 */
public class RutRot extends Basic_Buh {

    private final RutRotFrame rutRotFrame;

    public RutRot(BUH_INVOICE_MAIN bim, RutRotFrame rutRotFrame) {
        super(bim);
        this.rutRotFrame = rutRotFrame;
    }

    public JPanel getTableInvertPanel() {
        return rutRotFrame.jPanel_table_invert;
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
        RowDataInvert namn = new RowDataInvertB("", DB.BUH_FAKTURA_ARTIKEL___NAMN, "FÖRNAMN", "", true, true, true);
        //
        RowDataInvert[] rows = {
            namn
        };
        //
        return rows;
    }

    @Override
    protected void startUp() {
    }

    @Override
    protected boolean fieldsValidated(boolean insert) {
        return true;
    }

}
