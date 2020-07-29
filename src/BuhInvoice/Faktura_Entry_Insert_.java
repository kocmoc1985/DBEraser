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
public class Faktura_Entry_Insert_ {

    protected final InvoiceA_Insert invoiceA;
    protected HashMap<String, String> mainMap = new HashMap<>();
    protected HashMap<String, String> secMap = new HashMap<>();
    protected HashMap<String, String> fakturaMap = new HashMap<>();
    private ArrayList<HashMap<String, String>> articlesList = new ArrayList<>();
    private ArrayList<HashMap<String, String>> articlesListJTable = new ArrayList<>();
    //
    protected double FAKTURA_TOTAL_EXKL_MOMS = 0;
    protected double FAKTURA_TOTAL = 0;
    protected double MOMS_TOTAL = 0;

    public Faktura_Entry_Insert_(InvoiceA_Insert invoiceA) {
        this.invoiceA = invoiceA;
    }
    
    public void fakturaToHttpDB() {
        //
        //
        setMainFakturaData();
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
    protected void setMainFakturaData() {
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

    protected boolean verifyFakturaId(String fakturaId) {
        //
        int id;
        //
        try {
            id = Integer.parseInt(fakturaId);
        } catch (Exception ex) {
            id = -1;
        }
        //
        return id != -1;
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

    private void countFakturaTotal(HashMap<String, String> map) {
        //
        int antal = Integer.parseInt(map.get(DB.BUH_F_ARTIKEL__ANTAL));
        //
        FAKTURA_TOTAL += Double.parseDouble(map.get(DB.BUH_F_ARTIKEL__PRIS)) * antal;
        //
        if (invoiceA.getInklMoms()) {
            MOMS_TOTAL = FAKTURA_TOTAL * invoiceA.getMomsSats();
            FAKTURA_TOTAL += MOMS_TOTAL;
            FAKTURA_TOTAL_EXKL_MOMS = FAKTURA_TOTAL - MOMS_TOTAL;
        }
        //
        BUH_INVOICE_MAIN.jTextField_total_inkl_moms.setText("" + FAKTURA_TOTAL);
        BUH_INVOICE_MAIN.jTextField_total_exkl_moms.setText("" + FAKTURA_TOTAL_EXKL_MOMS);
        BUH_INVOICE_MAIN.jTextField_moms.setText("" + MOMS_TOTAL);
        //
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

    private void articlesListToJson(ArrayList<HashMap<String, String>> list) {
        //
        for (HashMap<String, String> article_map : list) {
            JSon.hashMapToJSON(article_map);
        }
        //
    }

    protected String getDateWithTime() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss.SSS");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }
}
