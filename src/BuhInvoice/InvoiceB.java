/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTableInvert.Basic;
import MyObjectTableInvert.RowDataInvert;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KOCMOC
 */
public class InvoiceB extends Basic {

    private final BUH_INVOICE_MAIN buh_invoice_main;

    public InvoiceB(BUH_INVOICE_MAIN buh_invoice_main) {
        this.buh_invoice_main = buh_invoice_main;
        initOther();
    }

    private void initOther() {
        fillJTableheader();
    }

    private void fillJTableheader() {
        //
        //
        // "ID" -> "buh_faktura.fakturaId"
        //
        String[] headers = {"ID", "FAKTURANR", "FAKTURATYP",
            "KUNDNR", "KUND", "FAKTURADATUM", "FÖRFALLODATUM",
            "EXKL MOMS", "TOTAL", "VALUTA"}; // Soon also: "BETALD"
        this.buh_invoice_main.jTable2_alla_fakturor.setModel(new DefaultTableModel(null, headers));
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
