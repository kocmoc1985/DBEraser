/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.LANG;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableInvert;
import forall.HelpA;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import javax.swing.JTable;

/**
 *
 * @author MCREMOTE
 */
public class InvoiceA_Update extends Invoice {

    private Faktura_Entry_Update faktura_entry_update;

    public InvoiceA_Update(BUH_INVOICE_MAIN buh_invoice_main) {
        super(buh_invoice_main);
        initOther();
    }

    private void initOther() {
        faktura_entry_update = (Faktura_Entry_Update) faktura_entry;
    }

    @Override
    protected Faktura_Entry initFakturaEntry() {
        return new Faktura_Entry_Update(this);
    }

    @Override
    protected void startUp() {

    }

    @Override
    protected void addArticleForJTable(JTable table) {
        //
        this.faktura_entry_update.addArticleForJTable(table);
        //
    }
    

    @Override
    protected void addArticleForDB() {
        //
        this.faktura_entry_update.addArticleForDB();
        //
        // Clearing the rows with the code below
        showTableInvert_2();
        refreshTableInvert(TABLE_INVERT_2);
        //
        insertOrUpdate(); // Update faktura after adding an article [2020-08-12]
        //
    }

   
    
    protected void updateArticle() {
        //
        faktura_entry_update.updateArticle();
        //
    }
    
    protected void deleteFakturaArtikel(){
         //
        if (GP_BUH.confirmWarning(LANG.MSG_3) == false) {
            return;
        }
        //
        JTable table = getArticlesTable();
        int selectedRow = table.getSelectedRow();
        //
        String buh_f_artikel__id = bim.getFakturaArtikelId();
        //
        if (buh_f_artikel__id == null) {
            return;
        }
        //
        HashMap<String, String> map = bim.getDELETE(DB.BUH_F_ARTIKEL__ID, buh_f_artikel__id, DB.TABLE__BUH_F_ARTIKEL);
        //
        String json = JSon.hashMapToJSON(map);
        //
        HelpA.removeRowJTable(table,selectedRow);
        //
        executeDelete(json);
        //
    }

    

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        SET_CURRENT_OPERATION_INSERT(false);
        //
        JTable table = getAllInvoicesTable();
        //
        String fixedComboValues_a = JSon._get(
                HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__KUND),
                HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__KUND_ID),
                requestJComboValuesHttp(DB.PHP_FUNC_PARAM__GET_KUNDER, new String[]{DB.BUH_FAKTURA_KUND___NAMN, DB.BUH_FAKTURA_KUND__ID}));
        //
        RowDataInvert kund = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_a, DB.BUH_FAKTURA__FAKTURAKUND_ID, "KUND", "", false, true, true);
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
        RowDataInvert betal_vilkor = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_b, DB.BUH_FAKTURA__BETAL_VILKOR, "BETAL VILKOR", "", false, true, false);
        betal_vilkor.enableFixedValues();
        betal_vilkor.setUneditable();
        //
        //
        String fixedComboValues_c = JSon._get_special_(
                DB.STATIC__LEV_VILKOR,
                HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__LEV_VILKOR)
        );
        RowDataInvert lev_vilkor = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_c, DB.BUH_FAKTURA__LEV_VILKOR, "LEVERANS VILKOR", "", true, true, false);
        lev_vilkor.enableFixedValuesAdvanced();
        lev_vilkor.setUneditable();
        //
        //
        String fixedComboValues_d = JSon._get_special_(
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
    public RowDataInvert[] getConfigTableInvert_2() {
        //
        if(articlesJTableRowSelected()){
            return getConfigTableInvert_edit_articles();
        }else{
            return getConfigTableInvert_insert();
        }
        //
    }

    @Override
    public RowDataInvert[] getConfigTableInvert_3() {
        //
        JTable table = bim.jTable_invoiceB_alla_fakturor;
        //
        String fixedComboValues_a = JSon._get_special_(
                DB.STATIC__INKL_EXKL_MOMS,
                HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__IS_INKL_MOMS)
        );
        //
//        String fixedComboValues_a = "Inkl moms;1,Exkl moms;0"; // This will aquired from SQL
        RowDataInvert inkl_exkl_moms = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_a, DB.BUH_FAKTURA__INKL_MOMS, "INKL MOMS", "", false, true, false);
        inkl_exkl_moms.enableFixedValuesAdvanced();
        inkl_exkl_moms.setUneditable();
        disableMomsJComboIf(inkl_exkl_moms); // *****
        //
        //
        String fixedComboValues_c = JSon._get_special_(
                DB.STATIC__MOMS_SATS,
                HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__MOMS_SATS)
        );
        //
//        String fixedComboValues_c = "25%;0.25,12%;0.12,6%;0.06"; // This will aquired from SQL
        RowDataInvert moms = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_c, DB.BUH_FAKTURA__MOMS_SATS, "MOMS", "", false, true, false);
        moms.enableFixedValuesAdvanced();
        moms.setUneditable();
        disableMomsJComboIf(moms); // *****
        //
        String order_ = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__ERT_ORDER);
        RowDataInvert order = new RowDataInvertB(order_, DB.BUH_FAKTURA__ERT_ORDER, InvoiceB.TABLE_ALL_INVOICES__ERT_ORDER, "", false, true, false);
        //
        String exp = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__EXP_AVG);
        RowDataInvert expavgift = new RowDataInvertB(exp, DB.BUH_FAKTURA__EXP_AVG, "EXPEDITIONSAVGIFT", "", false, true, false);
        //
        String frkt = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__FRAKT);
        RowDataInvert frakt = new RowDataInvertB(frkt, DB.BUH_FAKTURA__FRAKT, "FRAKT", "", false, true, false);
        //
        String fixedComboValues_b = JSon._get_special_(DB.STATIC__JA_NEJ,
                HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__MAKULERAD)
        );
        //
//        String fixedComboValues_b = "Nej;0,Ja;1"; // This will aquired from SQL
        RowDataInvert makulerad = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_b, DB.BUH_FAKTURA__MAKULERAD, "MAKULERAD", "", false, true, false);
        makulerad.enableFixedValuesAdvanced();
        makulerad.setUneditable();
        //
        RowDataInvert[] rows = {
            inkl_exkl_moms,
            moms,
            order,
            expavgift,
            frakt,
            makulerad
        };
        //
        return rows;
    }

}
