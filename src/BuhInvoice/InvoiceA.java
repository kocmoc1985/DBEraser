/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTable.OutPut;
import MyObjectTable.Table;
import MyObjectTableInvert.Basic;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import MyObjectTableInvert.TableRowInvert;
import forall.HelpA;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.DefaultMenuLayout;

/**
 *
 * @author MCREMOTE
 */
public class InvoiceA extends Basic {

    private final BUH_INVOICE_MAIN buh_invoice_main;
    protected Table TABLE_INVERT_2;
    protected Table TABLE_INVERT_3;
    private Buh_Faktura_Entry buh_Faktura_Entry = new Buh_Faktura_Entry(this);

    public InvoiceA(BUH_INVOICE_MAIN buh_invoice_main) {
        this.buh_invoice_main = buh_invoice_main;
    }

    /**
     * OBS! This is important and by [2020-07-17] i don't really know where to
     * get it from. In any case it seems that it should be done by making a
     * request to HTTP.
     *
     * @return
     */
    protected int getFakturaId() {
        return 1;
    }

    protected int getKundId() {
        return 1;
    }

    protected int getFakturaNr() {
        return 1;
    }

    public boolean getInklMoms() {
        try {
            return Integer.parseInt(getValueTableInvert(DB.BUH_FAKTURA__INKL_MOMS, TABLE_INVERT_3)) == 1;
        } catch (Exception ex) {
            return true;
        }
    }

    public boolean getMakulerad() {
        return Integer.parseInt(getValueTableInvert(DB.BUH_FAKTURA__MAKULERAD, TABLE_INVERT_3)) == 1;
    }

    public void htmlFaktura() {
        this.buh_Faktura_Entry.htmlFaktura();
    }

    protected void setMainFakturaData() {
        this.buh_Faktura_Entry.setMainFakturaData();
    }

    public void addArticleForJTable(JTable table) {
        this.buh_Faktura_Entry.addArticleForJTable(table);
    }

    public void addArticleForDB() {
        this.buh_Faktura_Entry.addArticleForDB();
    }

    public Buh_Faktura_Entry getBuhFakturaEntry() {
        return buh_Faktura_Entry;
    }

    protected String getFakturaKundId() {
        return getValueTableInvert("fakturaKundId", TABLE_INVERT);
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        String fixedComboValues_a = "Securitas;1,Telenor;2,Telia;3";
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
        String fixedComboValues_b = "30,60,20,15,10,5";
        RowDataInvert betal_vilkor = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_b, DB.BUH_FAKTURA__BETAL_VILKOR, "BETAL VILKOR", "", true, true, false);
        betal_vilkor.enableFixedValues();
        betal_vilkor.setUneditable();
        //
        String fixedComboValues_c = "Fritt vårt lager;FVL,CIF;CIF,FAS;FAS,Fritt Kund;FK,FOB;FOB";
        RowDataInvert lev_vilkor = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_c, DB.BUH_FAKTURA__LEV_VILKOR, "LEVERANS VILKOR", "", true, true, false);
        lev_vilkor.enableFixedValuesAdvanced();
        lev_vilkor.setUneditable();
        //
        String fixedComboValues_d = "Post;P,Hämtas;HAM";
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

    public RowDataInvert[] getConfigTableInvert_2() {
        //
        String fixedComboValues_a = "Skruv;1,Spik;2,Hammare;3,Traktor;4,Skruvmejsel;5"; // This will aquired from SQL
        RowDataInvert kund = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_a, DB.BUH_F_ARTIKEL__ARTIKELID, "ARTIKEL", "", true, true, true);
        kund.enableFixedValuesAdvanced();
        kund.setUneditable();
        //
        RowDataInvert komment = new RowDataInvertB("", DB.BUH_F_ARTIKEL__KOMMENT, "KOMMENTAR", "", true, true, false);
        //
        RowDataInvert antal = new RowDataInvertB("1", DB.BUH_F_ARTIKEL__ANTAL, "ANTAL", "", true, true, false);
        //
        String fixedComboValues_b = "Styck;st,Förp;Förp,Timmar;Tim";
        RowDataInvert enhet = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_b, DB.BUH_F_ARTIKEL__ENHET, "ENHET", "", true, true, false);
        enhet.enableFixedValuesAdvanced();
        enhet.setUneditable();
        //
        RowDataInvert pris = new RowDataInvertB("0", DB.BUH_F_ARTIKEL__PRIS, "PRIS", "", true, true, true);
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

    public RowDataInvert[] getConfigTableInvert_3() {
        //
        String fixedComboValues_a = "Inkl moms;1,Exkl moms;0"; // This will aquired from SQL
        RowDataInvert inkl_exkl_moms = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_a, DB.BUH_FAKTURA__INKL_MOMS, "INKL MOMS", "", true, true, false);
        inkl_exkl_moms.enableFixedValuesAdvanced();
        inkl_exkl_moms.setUneditable();
        //
        String fixedComboValues_c = "25%;0.25,12%;0.12,6%;0.06"; // This will aquired from SQL
        RowDataInvert moms = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_c, DB.BUH_FAKTURA__MOMS_SATS, "MOMS", "", true, true, false);
        moms.enableFixedValuesAdvanced();
        moms.setUneditable();
        //
        RowDataInvert expavgift = new RowDataInvertB("0", DB.BUH_FAKTURA__EXP_AVG, "EXPEDITIONSAVGIFT", "", true, true, false);
        //
        RowDataInvert frakt = new RowDataInvertB("0", DB.BUH_FAKTURA__FRAKT, "FRAKT", "", true, true, false);
        //
        String fixedComboValues_b = "Nej;0,Ja;1"; // This will aquired from SQL
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

    @Override
    public void showTableInvert() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert(), false, "buh_faktura_a");
        TABLE_INVERT = null;
        TABLE_INVERT = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT, 5, 0, 5, 0);
        showTableInvert(buh_invoice_main.jPanel2_faktura_main);
        //
        addTableInvertRowListener(TABLE_INVERT, this);
        //
    }

    public void showTableInvert_2() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_2(), false, "buh_f_artikel");
        TABLE_INVERT_2 = null;
        TABLE_INVERT_2 = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT_2, 5, 0, 5, 0);
        showTableInvert(buh_invoice_main.jPanel_articles, TABLE_INVERT_2);
        //
        addTableInvertRowListener(TABLE_INVERT_2, this);
    }

    public void showTableInvert_3() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_3(), false, "buh_faktura_b");
        TABLE_INVERT_3 = null;
        TABLE_INVERT_3 = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT_3, 5, 0, 5, 0);
        showTableInvert(buh_invoice_main.jPanel3_faktura_sec, TABLE_INVERT_3);
        //
        addTableInvertRowListener(TABLE_INVERT_2, this);
    }

    @Override
    public void mouseClicked(MouseEvent me, int column, int row, String tableName, TableInvert ti) {
        super.mouseClicked(me, column, row, tableName, ti); //To change body of generated methods, choose Tools | Templates.
        //
        String col_name = ti.getCurrentColumnName(me.getSource());
        //
        if (col_name.equals(DB.BUH_FAKTURA__ER_REFERENS)) {
            String er_referens_last = HelpA.loadLastEntered(IO.getErReferens(getFakturaKundId()));
            setValueTableInvert(DB.BUH_FAKTURA__ER_REFERENS, TABLE_INVERT, er_referens_last);
        }
        //
    }

    @Override
    public void initializeSaveIndicators() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getUnsaved(int nr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * SUPER important here you catch the event when key released on some
     * component so you can process this event as required
     *
     * @param ti
     * @param ke
     */
    @Override
    public void keyReleasedForward(TableInvert ti, KeyEvent ke) {
        super.keyReleasedForward(ti, ke); //To change body of generated methods, choose Tools | Templates.
        //
        String col_name = ti.getCurrentColumnName(ke.getSource());
        //
        if (col_name.equals(DB.BUH_FAKTURA__VAR_REFERENS)) {
            //
            String var_referens = getValueTableInvert(DB.BUH_FAKTURA__VAR_REFERENS);
            //
            try {
                HelpA.writeToFile(IO.VAR_REFERENS, var_referens);
            } catch (IOException ex) {
                Logger.getLogger(InvoiceA.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__ER_REFERENS)) {
            //
            String er_referens = getValueTableInvert(DB.BUH_FAKTURA__ER_REFERENS);
            //
            try {
                HelpA.writeToFile(IO.getErReferens(getFakturaKundId()), er_referens);
            } catch (IOException ex) {
                Logger.getLogger(InvoiceA.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
        }
    }

    /**
     * SUPER important here you catch the event when some jcombobox item is
     * changed so you can process this event as required
     *
     * @param ti
     * @param ie
     */
    @Override
    public void jComboBoxItemStateChangedForward(TableInvert ti, ItemEvent ie) {
        //
        super.jComboBoxItemStateChangedForward(ti, ie);
        //
        String col_name = ti.getCurrentColumnName(ie.getSource());
        //
        if (col_name.equals(DB.BUH_FAKTURA__BETAL_VILKOR)) {
            long value = Long.parseLong(getValueTableInvert(DB.BUH_FAKTURA__BETAL_VILKOR));
            String date = getValueTableInvert(DB.BUH_FAKTURA__FAKTURA_DATUM);
            String date_new = HelpA.get_date_time_plus_some_time_in_days(date, value);
            setValueTableInvert(DB.BUH_FAKTURA__FORFALLO_DATUM, TABLE_INVERT, date_new);
        } else if (col_name.equals(DB.BUH_FAKTURA__INKL_MOMS)) {
            //
            boolean momsInk = getInklMoms();
            //
            TableInvert table = (TableInvert) TABLE_INVERT_3;
            TableRowInvert tri = (TableRowInvert) table.getRow(DB.BUH_FAKTURA__MOMS_SATS);
            RowDataInvert rdi = tri.getRowConfig();
            //
            if (momsInk == false) {
                //
                tri.setVisible(false);
                //
                refreshTableInvert(TABLE_INVERT_3);
                //
            } else {
                //
                tri.setVisible(true);
                //
                refreshTableInvert(TABLE_INVERT_3);
                //
            }
            //
        }
        //
    }

    public void test() {
        TableInvert table = (TableInvert) TABLE_INVERT_3;
        table.printRowList();
        table.resizeRows();
    }

    public void test_b() {

    }

}
