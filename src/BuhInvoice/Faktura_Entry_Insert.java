/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.LANG;
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

    public Faktura_Entry_Insert(Invoice invoice) {
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
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        if (GP_BUH.verifyId(fakturaId)) {
            //
            setFakturaIdForArticles(fakturaId);
            //
            articlesToHttpDB(articlesList);
            //
            EditPanel_Send.insert(fakturaId, DB.STATIC__SENT_STATUS__SKAPAD, DB.STATIC__SENT_TYPE_FAKTURA);
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
        String kundId = invoice.getKundId();
        //
        this.fakturaMap.put(DB.BUH_FAKTURA__KUNDID__, kundId);
        this.fakturaMap.put(DB.BUH_FAKTURA__FAKTURANR__, Invoice.getNextFakturaNr(kundId)); // OBS! Aquired from http
        //
        this.fakturaMap.put(DB.BUH_FAKTURA__TOTAL__, "" + invoice.getFakturaTotal());
        this.fakturaMap.put(DB.BUH_FAKTURA__RABATT_TOTAL, "" + invoice.getRabattTotal());
        this.fakturaMap.put(DB.BUH_FAKTURA__TOTAL_EXKL_MOMS__, "" + invoice.getTotalExklMoms());
        this.fakturaMap.put(DB.BUH_FAKTURA__MOMS_TOTAL__, "" + invoice.getMomsTotal());
        //
        //OBS! KONTANT FAKTURA [2020-09-22]
        if(Invoice.CREATE_KONTANT_FAKTURA__OPERATION_INSERT){
            this.fakturaMap.put(DB.BUH_FAKTURA__FAKTURATYP, DB.STATIC__FAKTURA_TYPE_KONTANT__NUM); // 2 = KONTANT FAKTURA
            this.fakturaMap.put(DB.BUH_FAKTURA__BETALD, "1"); // 1 = Betald
        }
        //
        //
        this.fakturaMap.put(DB.BUH_FAKTURA__DATE_CREATED__, GP_BUH.getDateCreated());
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
                Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            //
        }
        //
        return true;
        //
    }

    protected void deleteArtikel() {
        //
        JTable table = getArticlesTable();
        //
        int currRow = table.getSelectedRow();
        //
        HelpA.removeRowJTable(table, currRow);
        //
        articlesList.remove(currRow);
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
        HelpA.setValueGivenRow(table, currRow, InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN, map.get(DB.BUH_F_ARTIKEL__ARTIKELID));
        HelpA.setValueGivenRow(table, currRow, InvoiceB.TABLE_INVOICE_ARTIKLES__KOMMENT, map.get(DB.BUH_F_ARTIKEL__KOMMENT));
        HelpA.setValueGivenRow(table, currRow, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL, map.get(DB.BUH_F_ARTIKEL__ANTAL));
        HelpA.setValueGivenRow(table, currRow, InvoiceB.TABLE_INVOICE_ARTIKLES__ENHET, map.get(DB.BUH_F_ARTIKEL__ENHET));
        HelpA.setValueGivenRow(table, currRow, InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS, map.get(DB.BUH_F_ARTIKEL__PRIS));
        HelpA.setValueGivenRow(table, currRow, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT, map.get(DB.BUH_F_ARTIKEL__RABATT));
        //
        //
        articlesList.remove(currRow);
        addArticleForDB();
        //
        //
    }

    @Override
    public void addArticleForDB() {
        //
        int jcomboBoxParamToReturnManuallySpecified = 2; // returning the "artikelId" -> refers to "HelpA.ComboBoxObject"
        //
        // Yes it's obligatory to use "tableInvertToHashMap_exclude_null()" 
        // If not used the empty checkbox returns "NULL" as value and when sending it to PHP/PDO
        // it causes "PDOException"
        HashMap<String, String> map_for_adding_to_db = invoice.tableInvertToHashMap(invoice.TABLE_INVERT_2, DB.START_COLUMN, jcomboBoxParamToReturnManuallySpecified);
        //
        //
        this.articlesList.add(map_for_adding_to_db);
        //
//        articlesListToJson(articlesList);
        //
//        invoice.countFakturaTotal(getArticlesTable(), InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL);
        invoice.countFakturaTotal(getArticlesTable());
        //
    }

}
