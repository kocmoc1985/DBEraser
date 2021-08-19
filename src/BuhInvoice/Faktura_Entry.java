/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import static BuhInvoice.GP_BUH._get;
import forall.HelpA;
import java.util.ArrayList;
import java.util.HashMap;
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
    protected ArrayList<HashMap<String, String>> articlesListJTable = new ArrayList<>(); // It was not used somehow [2020-08-14]
    protected ArrayList<HashMap<String, String>> articlesList = new ArrayList<>();
    //

    public Faktura_Entry(Invoice_ invoice) {
        this.invoice = invoice;
    }

    protected void resetLists() {
        this.articlesList.clear();
        this.articlesListJTable.clear();
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
        this.articlesListJTable.add(map);
        //
        Object[] jtableRow = new Object[]{
            //            map.get(DB.BUH_F_ARTIKEL__ARTIKELID),
            _get(map, DB.BUH_F_ARTIKEL__ARTIKELID, true), // [#AUTOMATIC-COMMA-WITH-POINT-REPLACEMENT--ARTICLE-NAME#] -> here replacing of "¤" with "," is made
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
        HelpA.addRowToJTable(jtableRow, table);
        //
    }

}
