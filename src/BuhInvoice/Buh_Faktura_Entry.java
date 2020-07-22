/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import forall.JSon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KOCMOC
 */
public class Buh_Faktura_Entry {

    private final InvoiceA invoiceA;
    private HashMap<String, String> mainMap = new HashMap<>();
    private ArrayList<HashMap<String, String>> articlesList = new ArrayList<>();
    private ArrayList<HashMap<String, String>> articlesListJTable = new ArrayList<>();
    //
    private double fakturaTotalExklMoms;
    private double fakturaTotalInklMoms;
    private double momsTotal;

    public Buh_Faktura_Entry(InvoiceA invoiceA) {
        this.invoiceA = invoiceA;
    }

    public void articlesToHttpDB() {
        //
        for (HashMap<String, String> article_row_map : articlesList) {
            //
            String json = JSon.hashMapToJSON(article_row_map);
            //
            try {
                //
                HelpBuh.http_get_content_post(HelpBuh.sendArticles(json));
                //
            } catch (Exception ex) {
                Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
        }
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

    /**
     * The "MainMap" contains data from SQL table "buh_faktura"
     *
     */
    public void setMainFakturaData() {
        //
        this.mainMap = invoiceA.tableInvertToHashMap(invoiceA.TABLE_INVERT, 1, invoiceA.getConfigTableInvert());
        //
        //Adding obligatory values not present in the "TABLE_INVERT"
        mainMap.put(DB.BUH_FAKTURA__KUNDID, "" + invoiceA.getKundId());
        mainMap.put(DB.BUH_FAKTURA__FAKTURANR, "" + invoiceA.getFakturaNr());
        //
        this.mainMap.entrySet().forEach((entry) -> {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + "=" + value);
        });
        //
        JSon.hashMapToJSON(mainMap); // Temporary here
        //
    }

    public void addArticleForDB() {
        //
        int jcomboBoxParamToReturnManuallySpecified = 2; // returning the "artikelId" -> refers to "HelpA.ComboBoxObject"
        HashMap<String, String> map_for_adding_to_db = invoiceA.tableInvertToHashMap(invoiceA.TABLE_INVERT_2, 1, invoiceA.getConfigTableInvert_2(), jcomboBoxParamToReturnManuallySpecified);
        //
        //Adding obligatory values not present in the "TABLE_INVERT_2"
        map_for_adding_to_db.put(DB.BUH_F_ARTIKEL__FAKTURAID, "" + invoiceA.getFakturaId());
        //
        this.articlesList.add(map_for_adding_to_db);
        //
        articlesListToJson(articlesList);
        //
        countFakturaTotal(map_for_adding_to_db);
        //
    }

    public void addArticleForJTable(JTable table) {
        int jcomboBoxParamToReturnManuallySpecified = 1; // returning the artikel "name" -> refers to "HelpA.ComboBoxObject"
        HashMap<String, String> map_for_show_in_jtable = invoiceA.tableInvertToHashMap(invoiceA.TABLE_INVERT_2, 1, invoiceA.getConfigTableInvert_2(), jcomboBoxParamToReturnManuallySpecified);
        this.articlesListJTable.add(map_for_show_in_jtable);
        addToJTable(map_for_show_in_jtable, table);
    }

    private void countFakturaTotal(HashMap<String, String> map) {
        int antal = Integer.parseInt(map.get(DB.BUH_F_ARTIKEL__ANTAL));
        fakturaTotalInklMoms += Double.parseDouble(map.get(DB.BUH_F_ARTIKEL__PRIS)) * antal;
        momsTotal = fakturaTotalInklMoms * 0.25;
        fakturaTotalInklMoms += momsTotal;
        fakturaTotalExklMoms = fakturaTotalInklMoms - momsTotal;
        System.out.println("faktura total inkl. moms: " + fakturaTotalInklMoms);
        System.out.println("faktura total exkl. moms: " + fakturaTotalExklMoms);
        System.out.println("moms total: " + momsTotal);
        BUH_INVOICE_MAIN.jTextField_total_inkl_moms.setText("" + fakturaTotalInklMoms);
        BUH_INVOICE_MAIN.jTextField_total_exkl_moms.setText("" + fakturaTotalExklMoms);
        BUH_INVOICE_MAIN.jTextField_moms.setText("" + momsTotal);
    }

    private void addToJTable(HashMap<String, String> map, JTable table) {
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
}
