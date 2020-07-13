/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTable.OutPut;
import MyObjectTableInvert.Basic;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert;

/**
 *
 * @author MCREMOTE
 */
public class InvoiceA extends Basic {

    private final BUH_INVOICE_MAIN buh_invoice_main;

    public InvoiceA(BUH_INVOICE_MAIN buh_invoice_main) {
        this.buh_invoice_main = buh_invoice_main;
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        String fixedComboValues_a = "Securitas;1,Telenor;2,Telia;3";
        RowDataInvert kund = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_a, "fakturaKundId", "KUND", "", true, true, true);
        kund.enableFixedValuesAdvanced();
        //
        RowDataInvert er_ref = new RowDataInvertB("er_referens", "ER REFERENS", "", true, true, false);
        RowDataInvert var_referens = new RowDataInvertB("er_referens", "VÅR REFERENS", "", true, true, false);
        //
        String fixedComboValues_b = "5,10,15,20,30";
        RowDataInvert betal_vilkor = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_b, "betal_vilkor", "BETAL VILKOR", "", true, true, false);
        betal_vilkor.enableFixedValues();
        //
        String fixedComboValues_c = "Fritt vårt lager;FVL,CIF;CIF,FAS;FAS,Fritt Kund;FK,FOB;FOB";
        RowDataInvert lev_vilkor = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_c, "lev_vilkor", "LEVERANS VILKOR", "", true, true, false);
        lev_vilkor.enableFixedValuesAdvanced();
        //
        String fixedComboValues_d = "Post;P,Hämtas;HAM";
        RowDataInvert lev_satt = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_d, "lev_vilkor", "LEVERANS VILKOR", "", true, true, false);
        lev_satt.enableFixedValuesAdvanced();
        //
        RowDataInvert faktura_datum = new RowDataInvertB("fakturadatum", "FAKTURADATUM", "", true, true, true);
        RowDataInvert forfalo_datum = new RowDataInvertB("forfallodatum", "FÖRFALLODATUM", "", true, true, true);
        //
        kund.enableFixedValuesAdvanced();
        RowDataInvert[] rows = {
            kund,
            er_ref,
            var_referens,
            betal_vilkor,
            lev_vilkor,
            lev_satt,
            faktura_datum,
            forfalo_datum
        };
        //
        return rows;
    }

    @Override
    public void showTableInvert() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert(), false, "invoice_a");
        TABLE_INVERT = null;
        TABLE_INVERT = tableBuilder.buildTable_B();
        setMargin(TABLE_INVERT, 5, 0, 5, 0);
        showTableInvert(buh_invoice_main.jPanel2_faktura_main);
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
