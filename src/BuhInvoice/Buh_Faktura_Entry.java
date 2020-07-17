/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import forall.JSon;
import java.util.ArrayList;
import java.util.HashMap;
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

    public Buh_Faktura_Entry(InvoiceA invoiceA) {
        this.invoiceA = invoiceA;
    }

    /**
     * The "MainMap" contains data from SQL table "buh_faktura"
     *
     * @param mainMap
     */
    public void setMainFakturaData() {
        //
        this.mainMap = invoiceA.tableInvertToHashMap(invoiceA.TABLE_INVERT, 1, invoiceA.getConfigTableInvert());
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
        HashMap<String, String> map_for_adding_to_db = invoiceA.tableInvertToHashMap(invoiceA.TABLE_INVERT_2, 1, invoiceA.getConfigTableInvert_2(),jcomboBoxParamToReturnManuallySpecified);
        this.articlesList.add(map_for_adding_to_db);
        //
        articlesListToJson(articlesList);
    }

    public void addArticleForJTable(JTable table) {
        int jcomboBoxParamToReturnManuallySpecified = 1; // returning the artikel "name" -> refers to "HelpA.ComboBoxObject"
        HashMap<String, String> map_for_show_in_jtable = invoiceA.tableInvertToHashMap(invoiceA.TABLE_INVERT_2, 1, invoiceA.getConfigTableInvert_2(), jcomboBoxParamToReturnManuallySpecified);
        this.articlesListJTable.add(map_for_show_in_jtable);
        addToJTable(map_for_show_in_jtable, table);
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
    
    private void articlesListToJson(ArrayList<HashMap<String, String>> list){
        //
        for (HashMap<String, String> article : list) {
            JSon.hashMapToJSON(article);
        }
        //
    }
}
