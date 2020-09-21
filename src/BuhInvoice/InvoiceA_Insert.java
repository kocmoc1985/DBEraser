/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.LANG;
import BuhInvoice.sec.IO;
import BuhInvoice.sec.Moms;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableInvert;
import forall.HelpA;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MCREMOTE
 */
public class InvoiceA_Insert extends Invoice {
    
    private final Faktura_Entry_Insert faktura_entry_insert;
    public static boolean EDIT__ARTICLE_UPPON_INSERT__SWITCH = false;
    
    public InvoiceA_Insert(BUH_INVOICE_MAIN bim) {
        super(bim);
        this.faktura_entry_insert = (Faktura_Entry_Insert) faktura_entry;
    }
    
    @Override
    protected Faktura_Entry initFakturaEntry() {
        return new Faktura_Entry_Insert(this);
    }
    
    @Override
    protected void startUp() {
        //
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                showTableInvert();
//                showTableInvert_2();
//                showTableInvert_3();
//            }
//        });
        //
        fillJTableheader();
        //
    }
    
    protected void createNew() {
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //
                showTableInvert();
                showTableInvert_2();
                showTableInvert_3();
                //
                refreshTableInvert(TABLE_INVERT);
                refreshTableInvert(TABLE_INVERT_2);
                refreshTableInvert(TABLE_INVERT_3);
                //
                fillJTableheader();
                HelpA.clearAllRowsJTable(getArticlesTable());
                //
                resetFakturaTotal();
                //
            }
        });
        //
    }
    
    private void fillJTableheader() {
        //
        String[] headers = {
            InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN,
            InvoiceB.TABLE_INVOICE_ARTIKLES__KOMMENT,
            InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL,
            InvoiceB.TABLE_INVOICE_ARTIKLES__ENHET,
            InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS,
            InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT,
            InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT_KR
        };
        //
        getArticlesTable().setModel(new DefaultTableModel(null, headers));
        //
    }
    
    protected void deleteArtikel() {
        //
        if (GP_BUH.confirmWarning(LANG.MSG_4) == false) {
            return;
        }
        //
        faktura_entry_insert.deleteArtikel();
        countFakturaTotal(getArticlesTable());
        //
    }
    
    protected void submitEditedArticle() {
        this.faktura_entry_insert.submitEditedArticle();
        // Clearing the rows with the code below
        showTableInvert_2();
        refreshTableInvert(TABLE_INVERT_2);
    }
    
    @Override
    protected void addArticleForJTable(JTable table) {
        //
        this.faktura_entry_insert.addArticleForJTable(table);
        //
    }
    
    @Override
    protected void addArticleForDB() {
        //
        this.faktura_entry_insert.addArticleForDB();
        //
        // Clearing the rows with the code below
        showTableInvert_2();
        refreshTableInvert(TABLE_INVERT_2);
        //
    }
    
    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        SET_CURRENT_OPERATION_INSERT(true);
        //
//        String fixedComboValues_a = "Securitas;1,Telenor;2,Telia;3";
        String fixedComboValues_a = requestJComboValuesHttp(DB.PHP_FUNC_PARAM__GET_KUNDER, new String[]{DB.BUH_FAKTURA_KUND___NAMN, DB.BUH_FAKTURA_KUND__ID});
        RowDataInvert kund = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_a, DB.BUH_FAKTURA__FAKTURAKUND_ID, "KUND", "", true, true, true);
        kund.enableFixedValuesAdvanced();
        kund.setUneditable();
        //
        String faktura_datum_val = HelpA.get_proper_date_yyyy_MM_dd();
        String faktura_datum_forfallo = HelpA.get_today_plus_x_days(30);
        RowDataInvert faktura_datum = new RowDataInvertB(faktura_datum_val, DB.BUH_FAKTURA__FAKTURA_DATUM, "FAKTURADATUM", "", true, true, true);
        RowDataInvert forfalo_datum = new RowDataInvertB(faktura_datum_forfallo, DB.BUH_FAKTURA__FORFALLO_DATUM, "FÖRFALLODATUM", "", true, true, false);
        forfalo_datum.setUneditable();
        //
        RowDataInvert er_ref = new RowDataInvertB("", DB.BUH_FAKTURA__ER_REFERENS, "ER REFERENS", "", true, true, false);
        RowDataInvert var_referens = new RowDataInvertB(HelpA.loadLastEntered(IO.VAR_REFERENS), DB.BUH_FAKTURA__VAR_REFERENS, "VÅR REFERENS", "", true, true, false);
        //
        String fixedComboValues_b = DB.STATIC__BETAL_VILKOR;
        RowDataInvert betal_vilkor = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_b, DB.BUH_FAKTURA__BETAL_VILKOR, "BETAL VILKOR", "", true, true, false);
        betal_vilkor.enableFixedValues();
        betal_vilkor.setUneditable();
        //
        String fixedComboValues_c = DB.STATIC__LEV_VILKOR;
        RowDataInvert lev_vilkor = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_c, DB.BUH_FAKTURA__LEV_VILKOR, "LEVERANS VILKOR", "", true, true, false);
        lev_vilkor.enableFixedValuesAdvanced();
        lev_vilkor.setUneditable();
        //
        String fixedComboValues_d = DB.STATIC__LEV_SATT;
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
        if (EDIT__ARTICLE_UPPON_INSERT__SWITCH) { // [2020-08-14]
            //
            EDIT__ARTICLE_UPPON_INSERT__SWITCH = false;
            //
            return getConfigTableInvert_edit_articles();
            //
        } else {
            return getConfigTableInvert_insert();
        }
        //
    }
    
    private String defineMomsSats() {
        if (momsSaveEntry.getMomsSats() == null) {
            return DB.STATIC__MOMS_SATS;
        } else {
            return JSon._get_special_(
                    DB.STATIC__MOMS_SATS,
                    momsSaveEntry.getMomsSats()
            );
        }
    }
    
    private String defineInklExklMoms(){
        if (momsSaveEntry.getInklExklMoms() == null) {
            return DB.STATIC__INKL_EXKL_MOMS;
        }else{
            return JSon._get_special_(
                    DB.STATIC__INKL_EXKL_MOMS,
                    momsSaveEntry.getInklExklMoms()
            );
        }
    }
    
    @Override
    public RowDataInvert[] getConfigTableInvert_3() {
        //
        String fixedComboValues_a = defineInklExklMoms();
        RowDataInvert inkl_exkl_moms = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_a, DB.BUH_FAKTURA__INKL_MOMS, "INKL MOMS", "", true, true, false);
        inkl_exkl_moms.enableFixedValuesAdvanced();
        inkl_exkl_moms.setUneditable();
        disableMomsJComboIf(inkl_exkl_moms);
        //
        String fixedComboValues_c = defineMomsSats();
        //
        RowDataInvert moms = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_c, DB.BUH_FAKTURA__MOMS_SATS, "MOMS", "", true, true, false);
        moms.enableFixedValuesAdvanced();
        moms.setUneditable();
        disableMomsJComboIf(moms);
        //
        RowDataInvert order = new RowDataInvertB("", DB.BUH_FAKTURA__ERT_ORDER, InvoiceB.TABLE_ALL_INVOICES__ERT_ORDER, "", true, true, false);
        //
        RowDataInvert expavgift = new RowDataInvertB("0", DB.BUH_FAKTURA__EXP_AVG, "EXPEDITIONSAVGIFT", "", true, true, false);
        //
        RowDataInvert frakt = new RowDataInvertB("0", DB.BUH_FAKTURA__FRAKT, "FRAKT", "", true, true, false);
        //
        String fixedComboValues_b = DB.STATIC__JA_NEJ; // This will aquired from SQL
        RowDataInvert makulerad = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_b, DB.BUH_FAKTURA__MAKULERAD, "MAKULERAD", "", true, true, false);
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
    
    
    
    @Override
    public void jComboBoxItemStateChangedForward(TableInvert ti, ItemEvent ie) {
        super.jComboBoxItemStateChangedForward(ti, ie); //To change body of generated methods, choose Tools | Templates.
        //
        String col_name = ti.getCurrentColumnName(ie.getSource());
        //
        JLinkInvert jli = (JLinkInvert) ie.getSource();
        //
        if (col_name.equals(DB.BUH_FAKTURA__MOMS_SATS)) {
            //
            momsSaveEntry.setMomsSats(jli.getValue());
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__INKL_MOMS)) {
            //
            momsSaveEntry.setInklExklMoms(jli.getValue());
            //
        }
        //
    }
    
}
