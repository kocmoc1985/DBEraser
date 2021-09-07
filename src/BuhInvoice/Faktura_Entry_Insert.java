/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import static BuhInvoice.GP_BUH._get;
import BuhInvoice.sec.JTableRowData;
import BuhInvoice.sec.LANG;
import BuhInvoice.sec.RutRot;
import forall.HelpA;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public class Faktura_Entry_Insert extends Faktura_Entry {

    public Faktura_Entry_Insert(Invoice_ invoice) {
        super(invoice);
    }

    @Override
    public void insertOrUpdate() {
        //
        setData();
        //
        //
        String json = JSon.hashMapToJSON(this.fakturaMap);
        //
        String fakturaId = "-1";
        //
        try {
            //
            fakturaId = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_FAKTURA_TO_DB, json);
            //
//            System.out.println("FAKTURA ID AQUIRED: " + fakturaId);
            //
        } catch (Exception ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        if (GP_BUH.verifyId(fakturaId)) {
            //
            setFakturaIdForArticles(fakturaId);
            //
            articlesToHttpDB(articlesList);
            //
            sendRutRotToDb(fakturaId);// [#RUTROT#]
            //
            if (invoice.bim.isOffert()) {
                EditPanel_Send.insert(fakturaId, DB.STATIC__SENT_STATUS__SKAPAD, DB.STATIC__SENT_TYPE_OFFERT,null);
            } else {
                EditPanel_Send.insert(fakturaId, DB.STATIC__SENT_STATUS__SKAPAD, DB.STATIC__SENT_TYPE_FAKTURA,null);
            }
            //
        } else {
            //
            HelpA.showNotification(LANG.MSG_ERROR_1);
            //
        }
        //
        resetLists();
        //
    }

    private void sendRutRotToDb(String fakturaId) {
        //
        RutRot rut = invoice.getRutRot();
        //
        if (rut == null) {
            return;
        }
        //
        rut.sendRutDataToDB(fakturaId);
        //
    }

    /**
     * The "MainMap" contains data from SQL table "buh_faktura"
     *
     */
    @Override
    protected void setData() {
        //
        this.mainMap = invoice.tableInvertToHashMap(invoice.TABLE_INVERT, DB.START_COLUMN);
        this.secMap = invoice.tableInvertToHashMap(invoice.TABLE_INVERT_3, DB.START_COLUMN);
        //
        this.fakturaMap = JSon.joinHashMaps(mainMap, secMap);
        //
        //Adding obligatory values not present in the "TABLE_INVERT"
        //
        this.fakturaMap.put(DB.BUH_FAKTURA__KUNDID__, "777"); // [#KUND-ID-INSERT#] [2020-10-26] OBS! The value sent does not have any meaning any longer
        this.fakturaMap.put(DB.BUH_FAKTURA__CHANGED_BY, GP_BUH.getChangedBy()); // [2020-10-28]
        this.fakturaMap.put(DB.BUH_FAKTURA__FAKTURANR__, Invoice_.getNextFakturaNr()); // OBS! Aquired from http
        //
        this.fakturaMap.put(DB.BUH_FAKTURA__TOTAL__, "" + invoice.getFakturaTotal());
        this.fakturaMap.put(DB.BUH_FAKTURA__RABATT_TOTAL, "" + invoice.getRabattTotal());
        this.fakturaMap.put(DB.BUH_FAKTURA__TOTAL_EXKL_MOMS__, "" + invoice.getTotalExklMoms());
        this.fakturaMap.put(DB.BUH_FAKTURA__MOMS_TOTAL__, "" + invoice.getMomsTotal());
        //
        //OBS! KONTANT FAKTURA [2020-09-22]
        if (Invoice_.CREATE_KONTANT_FAKTURA__OPERATION_INSERT) {
            this.fakturaMap.put(DB.BUH_FAKTURA__FAKTURATYP, DB.STATIC__FAKTURA_TYPE_KONTANT__NUM); // 2 = KONTANT FAKTURA
            this.fakturaMap.remove(DB.BUH_FAKTURA__FORFALLO_DATUM);
//            this.fakturaMap.put(DB.BUH_FAKTURA__BETALD, "1"); // 1 = Betald
        } else if (Invoice_.CREATE_OFFERT__OPERATION_INSERT) {
            //[#OFFERT#]
            this.fakturaMap.put(DB.BUH_FAKTURA__FAKTURATYP, DB.STATIC__FAKTURA_TYPE_OFFERT__NUM); // 3 = OFFERT
        }
        //
        if (containsOmvandMoms()) {
            //[#INVOICE-HAS-OMVAND-SKATT#]
            this.fakturaMap.put(DB.BUH_FAKTURA__OMVANT_SKATT, "1");
        }
        //
        this.fakturaMap.put(DB.BUH_FAKTURA__DATE_CREATED__, GP_BUH.getDateCreated_special());
        //
        System.out.println("-------------------------------------------------");
        //
        this.fakturaMap.entrySet().forEach((entry) -> {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + "=" + value);
        });
        //
        System.out.println("-------------------------------------------------");
        //
//        JSon.hashMapToJSON(this.fakturaMap); // Temporary here
        //
    }

    protected void setFakturaIdForArticles(String fakturaId) {
        //
        for (HashMap<String, String> article_row_map : articlesList) {
            article_row_map.put(DB.BUH_F_ARTIKEL__FAKTURAID, fakturaId);
        }
        //
    }

    protected static boolean articlesToHttpDB(ArrayList<HashMap<String, String>> articles) {
        //
        for (HashMap<String, String> article_row_map : articles) {
            //
            String json = JSon.hashMapToJSON(article_row_map);
            //
            try {
                //
                HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN, DB.PHP_FUNC_ARTICLES_TO_DB, json);
                //
            } catch (Exception ex) {
                Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            //
        }
        //
        return true;
        //
    }

    protected void deleteFakturaArtikel() {
        //
        JTable table = getArticlesTable();
        //
        int currRow = table.getSelectedRow();
        //
        deleteFakturaArtikel_help(table, currRow);
        //
    }

    protected void submitEditedArticle() {
        //
        JTable table = getArticlesTable();
        //
        int currRow = table.getSelectedRow();
        //
        //Below making changes to the edited article in the JTable
        int jcomboBoxParamToReturnManuallySpecified = 1; // returning the artikel "name" -> refers to "HelpA.ComboBoxObject"
        HashMap<String, String> map = invoice.tableInvertToHashMap(invoice.TABLE_INVERT_2, DB.START_COLUMN, jcomboBoxParamToReturnManuallySpecified);
        //
        HelpA.setValueGivenRow(table, currRow, InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN, _get(map, DB.BUH_F_ARTIKEL__ARTIKELID, true));
        HelpA.setValueGivenRow(table, currRow, InvoiceB.TABLE_INVOICE_ARTIKLES__KOMMENT, _get(map, DB.BUH_F_ARTIKEL__KOMMENT, true));
        HelpA.setValueGivenRow(table, currRow, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL, map.get(DB.BUH_F_ARTIKEL__ANTAL));
        HelpA.setValueGivenRow(table, currRow, InvoiceB.TABLE_INVOICE_ARTIKLES__ENHET, map.get(DB.BUH_F_ARTIKEL__ENHET));
        HelpA.setValueGivenRow(table, currRow, InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS, map.get(DB.BUH_F_ARTIKEL__PRIS));
        HelpA.setValueGivenRow(table, currRow, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT, map.get(DB.BUH_F_ARTIKEL__RABATT));
        HelpA.setValueGivenRow(table, currRow, InvoiceB.TABLE_INVOICE_ARTIKLES__MOMS_SATS, map.get(DB.BUH_F_ARTIKEL__MOMS_SATS).replaceAll("%", ""));
        HelpA.setValueGivenRow(table, currRow, InvoiceB.TABLE_INVOICE_ARTIKLES__OMVAND_SKATT, map.get(DB.BUH_F_ARTIKEL__OMVANT_SKATT));
        //
        //
        articlesList.remove(currRow);
        addArticleForDB(currRow);
        //
        //
    }

    private boolean ADDING_SAME_ARTICLE__INSERT = false;
    private int ANTAL_ADDING_SAME__INSERT = -1;
    private int ROW_NR_ADDING_SAME__INSERT = -1;

    @Override
    public void addArticleForJTable(JTable table) {
        //
        ADDING_SAME_ARTICLE__INSERT = false;
        ANTAL_ADDING_SAME__INSERT = 0;
        ROW_NR_ADDING_SAME__INSERT = -1;
        //
        int jcomboBoxParamToReturnManuallySpecified = 1; // returning the artikel "name" -> refers to "HelpA.ComboBoxObject"
        HashMap<String, String> map = invoice.tableInvertToHashMap(invoice.TABLE_INVERT_2, DB.START_COLUMN, jcomboBoxParamToReturnManuallySpecified);
        //
        HashMap<String, String> map_with_artikelId = invoice.tableInvertToHashMap(invoice.TABLE_INVERT_2, DB.START_COLUMN, 2);
        //
        //[#SAME-ARTICLE-ADDED-TWICE#]
        //
        String artikelNamn = _get(map, DB.BUH_F_ARTIKEL__ARTIKELID, true);
        String artikelPris = map.get(DB.BUH_F_ARTIKEL__PRIS);
        //
        JTableRowData jtrd = new JTableRowData(map_with_artikelId);
        jtrd.setArtikelNamn(artikelNamn);
        Object[] jtableRow;
        //
        if (this.articlesHashSet.contains(jtrd) == false) {
            jtableRow = new Object[]{
                artikelNamn,// [#AUTOMATIC-COMMA-WITH-POINT-REPLACEMENT--ARTICLE-NAME#] -> here replacing of "¤" with "," is made
                _get(map, DB.BUH_F_ARTIKEL__KOMMENT, true),
                map.get(DB.BUH_F_ARTIKEL__ANTAL),
                map.get(DB.BUH_F_ARTIKEL__ENHET),
                artikelPris,
                map.get(DB.BUH_F_ARTIKEL__RABATT),
                map.get(DB.BUH_F_ARTIKEL__RABATT_KR),
                map.get(DB.BUH_F_ARTIKEL__MOMS_SATS).replaceAll("%", ""),
                map.get(DB.BUH_F_ARTIKEL__OMVANT_SKATT)
            };
            //
            if(jtrd.getArtikelNamn().equals("-") == false){
                this.articlesHashSet.add(jtrd); // [2021-09-03] Bug fix, when you add an "-" article it should not be added to the hashset
            }
            //
            HelpA.addRowToJTable(jtableRow, table);
            //
        } else {
            //
            ADDING_SAME_ARTICLE__INSERT = true;
            //
            ROW_NR_ADDING_SAME__INSERT = HelpA.getRowByValue_double_param(table,
                    InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN, artikelNamn,
                    InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS, artikelPris);
            //
            int antal_actual = Integer.parseInt(HelpA.getValueGivenRow(table, ROW_NR_ADDING_SAME__INSERT, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL));
            //
            int antal_new = Integer.parseInt(jtrd.getArtikelAntal());
            //
            GP_BUH.showNotification(LANG.MSG_30(antal_new));
            //
            ANTAL_ADDING_SAME__INSERT = (antal_actual + antal_new);
            //
            HelpA.setValueGivenRow(table, ROW_NR_ADDING_SAME__INSERT, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL, "" + ANTAL_ADDING_SAME__INSERT);
            //
            addArticleForJTable_update_hashset(jtrd, ANTAL_ADDING_SAME__INSERT);
            //
        }
        //
    }

    @Override
    public void addArticleForDB(Object other) {
        //OBS! It can seem that it's added to the DB on this step - NO, here it's only PREPARED for adding
        //
        int jcomboBoxParamToReturnManuallySpecified = 2; // returning the "artikelId" -> refers to "HelpA.ComboBoxObject"
        //
        // Yes it's obligatory to use "tableInvertToHashMap_exclude_null()" 
        // If not used the empty checkbox returns "NULL" as value and when sending it to PHP/PDO
        // it causes "PDOException"
        HashMap<String, String> map_for_adding_to_db = invoice.tableInvertToHashMap(invoice.TABLE_INVERT_2, DB.START_COLUMN, jcomboBoxParamToReturnManuallySpecified);
        //
        map_for_adding_to_db.put(DB.BUH_F_ARTIKEL__KUND_ID, "777"); // [#KUND-ID-INSERT#]
        //
        if (ADDING_SAME_ARTICLE__INSERT) {
            ADDING_SAME_ARTICLE__INSERT = false;
            map_for_adding_to_db.remove(DB.BUH_F_ARTIKEL__ANTAL);
            map_for_adding_to_db.put(DB.BUH_F_ARTIKEL__ANTAL, "" + ANTAL_ADDING_SAME__INSERT);
            this.articlesList.remove(ROW_NR_ADDING_SAME__INSERT);
            this.articlesList.add(ROW_NR_ADDING_SAME__INSERT, map_for_adding_to_db);
        } else {
            if (other == null) {
                this.articlesList.add(map_for_adding_to_db);
            } else {
                Integer row = (Integer) other;
                this.articlesList.add(row, map_for_adding_to_db);
            }
        }
        //
        invoice.countFakturaTotal(getArticlesTable());
        //
    }

}
