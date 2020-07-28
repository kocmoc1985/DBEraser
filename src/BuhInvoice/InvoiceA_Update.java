/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import forall.HelpA;
import javax.swing.JTable;

/**
 *
 * @author MCREMOTE
 */
public class InvoiceA_Update extends InvoiceA_Insert {

    private Faktura_Entry_Update faktura_Entry_Update;
    
    public InvoiceA_Update(BUH_INVOICE_MAIN buh_invoice_main) {
        super(buh_invoice_main);
    }

    @Override
    protected Faktura_Entry_Insert initFakturaEntry() {
        return new Faktura_Entry_Update(this);
    }
    
    @Override
    protected void startUp() {
    }

    @Override
    public void showTableInvert_3() {
        //
        super.showTableInvert_3(); //To change body of generated methods, choose Tools | Templates.
        //
        hideMomsSatsIfExklMoms();
        //
    }

  
    
    

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        JTable table = bim.jTable_invoiceB_alla_fakturor;
        //
        String fixedComboValues_a = JSon._get(
                HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__KUND),
                HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__KUND_ID),
                requestJComboValuesHttp(DB.PHP_FUNC_PARAM__GET_KUNDER, DB.BUH_FAKTURA_KUND___NAMN, DB.BUH_FAKTURA_KUND__ID));
        //
        RowDataInvert kund = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_a, DB.BUH_FAKTURA__FAKTURAKUND_ID, "KUND", "", true, true, true);
        kund.enableFixedValuesAdvanced();
        kund.setUneditable();
        //
        //
        String faktura_datum_val = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__DATUM);
        String faktura_datum_forfallo = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__FORFALLODATUM);
        RowDataInvert faktura_datum = new RowDataInvertB(faktura_datum_val, DB.BUH_FAKTURA__FAKTURA_DATUM, "FAKTURADATUM", "", true, true, true);
        RowDataInvert forfalo_datum = new RowDataInvertB(faktura_datum_forfallo, DB.BUH_FAKTURA__FORFALLO_DATUM, "FÖRFALLODATUM", "", true, true, false);
        forfalo_datum.setUneditable();
        //
        //
        String erref = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__ER_REF);
        String varref = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__VAR_REF);
        RowDataInvert er_ref = new RowDataInvertB(erref, DB.BUH_FAKTURA__ER_REFERENS, "ER REFERENS", "", true, true, false);
        RowDataInvert var_referens = new RowDataInvertB(varref, DB.BUH_FAKTURA__VAR_REFERENS, "VÅR REFERENS", "", true, true, false);
        //
        String fixedComboValues_b = JSon._get_simple(
                HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__BET_VILKOR),
                DB.STATIC__BETAL_VILKOR
        );
        //
        RowDataInvert betal_vilkor = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_b, DB.BUH_FAKTURA__BETAL_VILKOR, "BETAL VILKOR", "", true, true, false);
        betal_vilkor.enableFixedValues();
        betal_vilkor.setUneditable();
        //
        //
        String fixedComboValues_c = JSon._get_special(
                DB.STATIC__LEV_VILKOR,
                HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__LEV_VILKOR)
        );
        RowDataInvert lev_vilkor = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_c, DB.BUH_FAKTURA__LEV_VILKOR, "LEVERANS VILKOR", "", true, true, false);
        lev_vilkor.enableFixedValuesAdvanced();
        lev_vilkor.setUneditable();
        //
        //
        String fixedComboValues_d = JSon._get_special(
                DB.STATIC__LEV_SATT,
                HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__LEV_SATT)
        );
        RowDataInvert lev_satt = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_d, DB.BUH_FAKTURA__LEV_SATT, "LEVERANS SÄTT", "", true, true, false);
        lev_satt.enableFixedValuesAdvanced();
        lev_satt.setUneditable();
        //
        RowDataInvert[] rows = {
            kund,
            faktura_datum,
            forfalo_datum,
            er_ref,
            var_referens,
            betal_vilkor,
            lev_vilkor,
            lev_satt
        };
        //
        return rows;
    }

    @Override
    public RowDataInvert[] getConfigTableInvert_3() {
        //
        JTable table = bim.jTable_invoiceB_alla_fakturor;
        //
        String fixedComboValues_a = JSon._get_special(
                DB.STATIC__INKL_EXKL_MOMS,
                HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__INKL_MOMS)
        );
        //
//        String fixedComboValues_a = "Inkl moms;1,Exkl moms;0"; // This will aquired from SQL
        RowDataInvert inkl_exkl_moms = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_a, DB.BUH_FAKTURA__INKL_MOMS, "INKL MOMS", "", true, true, false);
        inkl_exkl_moms.enableFixedValuesAdvanced();
        inkl_exkl_moms.setUneditable();
        //
        //
        String fixedComboValues_c = JSon._get_special(
                DB.STATIC__MOMS_SATS,
                HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__MOMS_SATS)
        );
        //
//        String fixedComboValues_c = "25%;0.25,12%;0.12,6%;0.06"; // This will aquired from SQL
        RowDataInvert moms = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_c, DB.BUH_FAKTURA__MOMS_SATS, "MOMS", "", true, true, false);
        moms.enableFixedValuesAdvanced();
        moms.setUneditable();
        //
        //
        String exp = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__EXP_AVG);
        RowDataInvert expavgift = new RowDataInvertB(exp, DB.BUH_FAKTURA__EXP_AVG, "EXPEDITIONSAVGIFT", "", true, true, false);
        //
        String frkt = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__FRAKT);
        RowDataInvert frakt = new RowDataInvertB(frkt, DB.BUH_FAKTURA__FRAKT, "FRAKT", "", true, true, false);
        //
        String fixedComboValues_b = JSon._get_special(
                DB.STATIC__MAKULERAD_JA_NEJ,
                HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__MAKULERAD)
        );
        //
//        String fixedComboValues_b = "Nej;0,Ja;1"; // This will aquired from SQL
        RowDataInvert makulerad = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_b, DB.BUH_FAKTURA__MAKULERAD, "MAKULERAD", "", true, true, false);
        makulerad.enableFixedValuesAdvanced();
        makulerad.setUneditable();
        //
        RowDataInvert[] rows = {
            inkl_exkl_moms,
            moms,
            expavgift,
            frakt,
            makulerad
        };
        //
        return rows;
    }

}
