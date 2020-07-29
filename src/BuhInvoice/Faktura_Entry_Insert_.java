/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import forall.HelpA;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KOCMOC
 */
public class Faktura_Entry_Insert_ extends Faktura_Entry {

    protected ArrayList<HashMap<String, String>> articlesList = new ArrayList<>();
    protected ArrayList<HashMap<String, String>> articlesListJTable = new ArrayList<>();

    public Faktura_Entry_Insert_(InvoiceA_Insert invoiceA) {
        super(invoiceA);
    }

    @Override
    public void insertOrUpdate() {
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
        if (verifyFakturaId(fakturaId)) {
            //
            setFakturaIdForArticles(fakturaId);
            //
            articlesToHttpDB();
            //
        } else {
            //
            HelpA.showNotification("Kunde inte ladda up fakturan (Faktura id saknas)");
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
        this.mainMap = invoiceA.tableInvertToHashMap(invoiceA.TABLE_INVERT, 1, invoiceA.getConfigTableInvert());
        this.secMap = invoiceA.tableInvertToHashMap(invoiceA.TABLE_INVERT_3, 1, invoiceA.getConfigTableInvert_3());
        //
        this.fakturaMap = HelpA.joinHashMaps(mainMap, secMap);
        //
        //Adding obligatory values not present in the "TABLE_INVERT"
        this.fakturaMap.put(DB.BUH_FAKTURA__KUNDID__, invoiceA.getKundId());
        this.fakturaMap.put(DB.BUH_FAKTURA__FAKTURANR__, invoiceA.getFakturaNr()); // OBS! Aquired from http
        //
        this.fakturaMap.put(DB.BUH_FAKTURA__TOTAL__, "" + FAKTURA_TOTAL);
        this.fakturaMap.put(DB.BUH_FAKTURA__TOTAL_EXKL_MOMS__, "" + FAKTURA_TOTAL_EXKL_MOMS);
        this.fakturaMap.put(DB.BUH_FAKTURA__MOMS_TOTAL__, "" + MOMS_TOTAL);
        //
        this.fakturaMap.put(DB.BUH_FAKTURA__DATE_CREATED__, getDateWithTime());
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
        JSon.hashMapToJSON(this.fakturaMap); // Temporary here
        //
    }

    protected void setFakturaIdForArticles(String fakturaId) {
        //
        for (HashMap<String, String> article_row_map : articlesList) {
            article_row_map.put(DB.BUH_F_ARTIKEL__FAKTURAID, fakturaId);
        }
        //
    }


    protected void articlesToHttpDB() {
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

    public void addArticleForDB() {
        //
        int jcomboBoxParamToReturnManuallySpecified = 2; // returning the "artikelId" -> refers to "HelpA.ComboBoxObject"
        HashMap<String, String> map_for_adding_to_db = invoiceA.tableInvertToHashMap(invoiceA.TABLE_INVERT_2, 1, invoiceA.getConfigTableInvert_2(), jcomboBoxParamToReturnManuallySpecified);
        //
        this.articlesList.add(map_for_adding_to_db);
        //
//        articlesListToJson(articlesList);
        //
        countFakturaTotal(map_for_adding_to_db);
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

    public void addArticleForJTable(JTable table) {
        int jcomboBoxParamToReturnManuallySpecified = 1; // returning the artikel "name" -> refers to "HelpA.ComboBoxObject"
        HashMap<String, String> map_for_show_in_jtable = invoiceA.tableInvertToHashMap(invoiceA.TABLE_INVERT_2, 1, invoiceA.getConfigTableInvert_2(), jcomboBoxParamToReturnManuallySpecified);
        this.articlesListJTable.add(map_for_show_in_jtable);
        addRowToJTable(map_for_show_in_jtable, table);
    }


    private void addRowToJTable(HashMap<String, String> map, JTable table) {
        //
        Object[] jtableRow = new Object[]{
            map.get(DB.BUH_F_ARTIKEL__ARTIKELID),
            map.get(DB.BUH_F_ARTIKEL__KOMMENT),
            map.get(DB.BUH_F_ARTIKEL__ANTAL),
            map.get(DB.BUH_F_ARTIKEL__ENHET),
            map.get(DB.BUH_F_ARTIKEL__PRIS),
            map.get(DB.BUH_F_ARTIKEL__RABATT)
        };
        //
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(jtableRow);
        //
    }
  
}
