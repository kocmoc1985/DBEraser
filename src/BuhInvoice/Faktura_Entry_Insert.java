/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

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
        System.out.println("INSER OR UPDATE: " + getClass().getName()+ " ***********************************");
        //
        boolean invalidated_1 = invoice.containsInvalidatedFields(invoice.TABLE_INVERT, DB.START_COLUMN, invoice.getConfigTableInvert());
        boolean invalidated_2 = invoice.containsInvalidatedFields(invoice.TABLE_INVERT_2, DB.START_COLUMN, invoice.getConfigTableInvert_2());
        boolean invalidated_3 = invoice.containsInvalidatedFields(invoice.TABLE_INVERT_3, DB.START_COLUMN, invoice.getConfigTableInvert_3());
        //
        if (invalidated_1 || invalidated_2 || invalidated_3) {
            HelpA.showNotification(LANG.MSG_1);
            return;
        }
        //
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
            fakturaId = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_FAKTURA_TO_DB, json));
            //
            System.out.println("FAKTURA ID AQUIRED: " + fakturaId);
            //
        } catch (Exception ex) {
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        if (BUH_INVOICE_MAIN.verifyId(fakturaId)) {
            //
            setFakturaIdForArticles(fakturaId);
            //
            articlesToHttpDB();
            //
        } else {
            //
            HelpA.showNotification(LANG.MSG_ERROR_1);
            //
        }
        //
    }

    /**
     * The "MainMap" contains data from SQL table "buh_faktura"
     *
     */
    @Override
    protected void setData() {
        //
        this.mainMap = invoice.tableInvertToHashMap(invoice.TABLE_INVERT, DB.START_COLUMN, invoice.getConfigTableInvert());
        this.secMap = invoice.tableInvertToHashMap(invoice.TABLE_INVERT_3, DB.START_COLUMN, invoice.getConfigTableInvert_3());
        //
        this.fakturaMap = JSon.joinHashMaps(mainMap, secMap);
        //
        //Adding obligatory values not present in the "TABLE_INVERT"
        this.fakturaMap.put(DB.BUH_FAKTURA__KUNDID__, invoice.getKundId());
        this.fakturaMap.put(DB.BUH_FAKTURA__FAKTURANR__, invoice.getNextFakturaNr()); // OBS! Aquired from http
        //
        this.fakturaMap.put(DB.BUH_FAKTURA__TOTAL__, "" + invoice.getFakturaTotal());
        this.fakturaMap.put(DB.BUH_FAKTURA__TOTAL_EXKL_MOMS__, "" + invoice.getTotalExklMoms());
        this.fakturaMap.put(DB.BUH_FAKTURA__MOMS_TOTAL__, "" + invoice.getMomsTotal());
        //
        this.fakturaMap.put(DB.BUH_FAKTURA__DATE_CREATED__, BUH_INVOICE_MAIN.getDateCreated());
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

    private void articlesToHttpDB() {
        //
        for (HashMap<String, String> article_row_map : articlesList) {
            //
            String json = JSon.hashMapToJSON(article_row_map);
            //
            try {
                //
//                HelpBuh.http_get_content_post(HelpBuh.sendArticles(json));
                HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                        DB.PHP_FUNC_ARTICLES_TO_DB, json));
                //
            } catch (Exception ex) {
                Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
        }
        //
    }

    @Override
    public void addArticleForDB() {
        //
        int jcomboBoxParamToReturnManuallySpecified = 2; // returning the "artikelId" -> refers to "HelpA.ComboBoxObject"
        HashMap<String, String> map_for_adding_to_db = invoice.tableInvertToHashMap(invoice.TABLE_INVERT_2, DB.START_COLUMN, invoice.getConfigTableInvert_2(), jcomboBoxParamToReturnManuallySpecified);
        //
        this.articlesList.add(map_for_adding_to_db);
        //
//        articlesListToJson(articlesList);
        //
//        invoice.countFakturaTotal(getArticlesTable(), InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL);
        invoice.countFakturaTotal(getArticlesTable());
        //
    }

    public void htmlFaktura() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                HTMLPrint_A hTMLPrint_A = new HTMLPrint_A(articlesList);
                hTMLPrint_A.setVisible(true);
            }
        });
    }


}
