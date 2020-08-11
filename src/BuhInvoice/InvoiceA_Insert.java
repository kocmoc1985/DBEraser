/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableInvert;
import forall.HelpA;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MCREMOTE
 */
public class InvoiceA_Insert extends Invoice {

    private Faktura_Entry_Insert faktura_entry_insert;

    public InvoiceA_Insert(BUH_INVOICE_MAIN_ bim) {
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
            }
        });
        //
    }

    private void fillJTableheader() {
        //
        String[] headers = {"ARTIKEL", "KOMMENTAR", "ANTAL", "ENHET", "PRIS", "RABATT"};
        String[][] content = new String[][]{ //            {"", "", "", "", "", ""},
        //             {"", "", "", "", "", ""}
        };
        //
        this.bim.jTable_InvoiceA_Insert_articles.setModel(new DefaultTableModel(null, headers));
    }

    public void htmlFaktura() {
        this.faktura_entry_insert.htmlFaktura();
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
        // Clearing the rows with the code below
        showTableInvert_2();
        refreshTableInvert(TABLE_INVERT_2);
        //
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        CURRENT_OPERATION_INSERT = true;
        System.out.println("CURR OPERATION INSERT = TRUE");
        //
//        String fixedComboValues_a = "Securitas;1,Telenor;2,Telia;3";
        String fixedComboValues_a = requestJComboValuesHttp(DB.PHP_FUNC_PARAM__GET_KUNDER, DB.BUH_FAKTURA_KUND___NAMN, DB.BUH_FAKTURA_KUND__ID);
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
        String fixedComboValues_a = requestJComboValuesHttp(DB.PHP_FUNC_PARAM_GET_KUND_ARTICLES, DB.BUH_FAKTURA_ARTIKEL___NAMN, DB.BUH_FAKTURA_ARTIKEL___ID);
//        String fixedComboValues_a = "Skruv;1,Spik;2,Hammare;3,Traktor;4,Skruvmejsel;5"; // This will aquired from SQL
        RowDataInvert kund = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_a, DB.BUH_F_ARTIKEL__ARTIKELID, "ARTIKEL", "", true, true, true);
        kund.enableFixedValuesAdvanced();
        kund.setUneditable();
        //
        RowDataInvert komment = new RowDataInvertB("", DB.BUH_F_ARTIKEL__KOMMENT, "KOMMENTAR", "", true, true, false);
        //
        RowDataInvert antal = new RowDataInvertB("1", DB.BUH_F_ARTIKEL__ANTAL, "ANTAL", "", true, true, false);
        //
        String fixedComboValues_b = DB.STATIC__ENHET;
        RowDataInvert enhet = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_b, DB.BUH_F_ARTIKEL__ENHET, "ENHET", "", true, true, false);
        enhet.enableFixedValuesAdvanced();
        enhet.setUneditable();
        //
        RowDataInvert pris = new RowDataInvertB("", DB.BUH_F_ARTIKEL__PRIS, "PRIS", "", true, true, true);
        //
        RowDataInvert rabatt = new RowDataInvertB("0", DB.BUH_F_ARTIKEL__RABATT, "RABATT %", "", true, true, false);
        //
        RowDataInvert[] rows = {
            kund,
            komment,
            antal,
            enhet,
            pris,
            rabatt
        };
        //
        return rows;
    }

    @Override
    public RowDataInvert[] getConfigTableInvert_3() {
        //
        String fixedComboValues_a = DB.STATIC__INKL_EXKL_MOMS; // This will aquired from SQL
        RowDataInvert inkl_exkl_moms = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_a, DB.BUH_FAKTURA__INKL_MOMS, "INKL MOMS", "", true, true, false);
        inkl_exkl_moms.enableFixedValuesAdvanced();
        inkl_exkl_moms.setUneditable();
        //
        String fixedComboValues_c = DB.STATIC__MOMS_SATS; // This will aquired from SQL
        RowDataInvert moms = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_c, DB.BUH_FAKTURA__MOMS_SATS, "MOMS", "", true, true, false);
        moms.enableFixedValuesAdvanced();
        moms.setUneditable();
        //
        RowDataInvert expavgift = new RowDataInvertB("0", DB.BUH_FAKTURA__EXP_AVG, "EXPEDITIONSAVGIFT", "", true, true, false);
        //
        RowDataInvert frakt = new RowDataInvertB("0", DB.BUH_FAKTURA__FRAKT, "FRAKT", "", true, true, false);
        //
        String fixedComboValues_b = DB.STATIC__MAKULERAD_JA_NEJ; // This will aquired from SQL
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

    public void test() {
        TableInvert table = (TableInvert) TABLE_INVERT_3;
        table.printRowList();
        table.resizeRows();
    }

}
