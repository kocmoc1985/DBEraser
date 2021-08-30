/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import static BuhInvoice.GP_BUH._get;
import BuhInvoice.sec.JTableRowData;
import forall.HelpA;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public abstract class Faktura_Entry {

    //
    protected final Invoice_ invoice;
    protected HashMap<String, String> mainMap = new HashMap<>();
    protected HashMap<String, String> secMap = new HashMap<>();
    protected HashMap<String, String> fakturaMap = new HashMap<>();
//    protected ArrayList<HashMap<String, String>> articlesListJTable = new ArrayList<>(); // It was not used somehow [2020-08-14]
    protected ArrayList<HashMap<String, String>> articlesList = new ArrayList<>();
    protected HashSet<JTableRowData> articlesHashSet = new HashSet<>();
    //

    public Faktura_Entry(Invoice_ invoice) {
        this.invoice = invoice;
    }

    protected void resetLists() {
        this.articlesList.clear();
        this.articlesHashSet.clear();
    }

    protected JTable getArticlesTable() {
        return invoice.getArticlesTable();
    }

    protected abstract void insertOrUpdate();

    protected abstract void setData();

    public abstract void addArticleForDB();

    /**
     * [2021-05-17] [#INVOICE-HAS-OMVAND-SKATT#]
     *
     * @return
     */
    public boolean containsOmvandMoms() {
        //
        for (HashMap<String, String> article_row_map : articlesList) {
            int omvant_skatt = Integer.parseInt(article_row_map.getOrDefault(DB.BUH_F_ARTIKEL__OMVANT_SKATT, "0"));
            if (omvant_skatt == 1) {
                return true;
            }
        }
        //
        return false;
    }

    public void addArticleForJTable(JTable table) {
        //
        int jcomboBoxParamToReturnManuallySpecified = 1; // returning the artikel "name" -> refers to "HelpA.ComboBoxObject"
        HashMap<String, String> map = invoice.tableInvertToHashMap(invoice.TABLE_INVERT_2, DB.START_COLUMN, jcomboBoxParamToReturnManuallySpecified);
        //
        HashMap<String, String> map_with_artikelId = invoice.tableInvertToHashMap(invoice.TABLE_INVERT_2, DB.START_COLUMN, 2);
        //
        //[#SAME-ARTICLE-ADDED-TWICE#]
        //
        JTableRowData jtrd = new JTableRowData(map_with_artikelId);
        jtrd.setArtikelNamn(_get(map, DB.BUH_F_ARTIKEL__ARTIKELID, true));
        Object[] jtableRow;
        //
        if (this.articlesHashSet.contains(jtrd) == false) {
            jtableRow = new Object[]{
                _get(map, DB.BUH_F_ARTIKEL__ARTIKELID, true),// [#AUTOMATIC-COMMA-WITH-POINT-REPLACEMENT--ARTICLE-NAME#] -> here replacing of "¤" with "," is made
                _get(map, DB.BUH_F_ARTIKEL__KOMMENT, true),
                map.get(DB.BUH_F_ARTIKEL__ANTAL),
                map.get(DB.BUH_F_ARTIKEL__ENHET),
                map.get(DB.BUH_F_ARTIKEL__PRIS),
                map.get(DB.BUH_F_ARTIKEL__RABATT),
                map.get(DB.BUH_F_ARTIKEL__RABATT_KR),
                map.get(DB.BUH_F_ARTIKEL__MOMS_SATS).replaceAll("%", ""),
                map.get(DB.BUH_F_ARTIKEL__OMVANT_SKATT)
            };
            //
            this.articlesHashSet.add(jtrd);
            //
            HelpA.addRowToJTable(jtableRow, table);
            //
        } else {
            //
            int row = HelpA.getRowByValue(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN, _get(map, DB.BUH_F_ARTIKEL__ARTIKELID, true));
            //
            int antal_actual = Integer.parseInt(HelpA.getValueGivenRow(table, row, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL));
            //
            int antal_new = Integer.parseInt(jtrd.getArtikelAntal());
            //
            HelpA.setValueGivenRow(table, row, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL, "" + (antal_actual + antal_new));
            //
        }
        //
    }

    protected void deleteFakturaArtikel_help(JTable table, int currRow) {
        //Yes, "artikelNamn" is correct - the artikelId is not available here[2021-08-30]
        String artikelNamn = HelpA.getValueGivenRow(table, currRow, InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN);
        //
        //[#SAME-ARTICLE-ADDED-TWICE#]
        //
        for (JTableRowData jtrd : articlesHashSet) {
            //
            if (jtrd.getArtikelNamn().equals(artikelNamn)) {
                articlesHashSet.remove(jtrd);
                break;
            }
            //
        }
        //
        HelpA.removeRowJTable(table, currRow);
        articlesList.remove(currRow);
        //
    }

}
