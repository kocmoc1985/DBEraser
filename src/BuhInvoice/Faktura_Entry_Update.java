/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import forall.HelpA;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 *
 * @author MCREMOTE
 */
public class Faktura_Entry_Update extends Faktura_Entry {

    public Faktura_Entry_Update(Invoice invoice) {
        super(invoice);
    }

    /**
     * [2020-07-29] Here it's for the update purpose
     */
    @Override
    public void insertOrUpdate() {
        //
        setData();
        //
        String json = JSon.hashMapToJSON(this.fakturaMap);
        //
        HelpBuh.update(json);
        //
        resetLists();
        //
    }

    @Override
    public void addArticleForJTable(JTable table) {
        //
        int jcomboBoxParamToReturnManuallySpecified = 1; // returning the artikel "name" -> refers to "HelpA.ComboBoxObject"
        HashMap<String, String> map = invoice.tableInvertToHashMap(invoice.TABLE_INVERT_2, DB.START_COLUMN, invoice.getConfigTableInvert_2(), jcomboBoxParamToReturnManuallySpecified);
        //        
        this.articlesListJTable.add(map);
        //
        Object[] jtableRow = new Object[]{
            "",
            "",
            "",
            map.get(DB.BUH_F_ARTIKEL__ARTIKELID),
            map.get(DB.BUH_F_ARTIKEL__KOMMENT),
            map.get(DB.BUH_F_ARTIKEL__ANTAL),
            map.get(DB.BUH_F_ARTIKEL__ENHET),
            map.get(DB.BUH_F_ARTIKEL__PRIS),
            map.get(DB.BUH_F_ARTIKEL__RABATT)
        };
        //
        HelpA.addRowToJTable(jtableRow, table);
        //
    }

    @Override
    public void addArticleForDB() {
        //
        // As i see it today [2020-08-11], i should do it somehow like "updateArticle()"
        //
        // Also look at: Faktura_Entry_Insert.class -> articlesToHttpDB()
        //
        //
        InvoiceA_Update invoic = (InvoiceA_Update) invoice;
        //
        String fakturaId = invoice.bim.getFakturaId();
        //
        int jcomboBoxParamToReturnManuallySpecified = 2; // returning the "artikelId" -> refers to "HelpA.ComboBoxObject"
        //
        // Yes it's obligatory to use "tableInvertToHashMap_exclude_null()" 
        // If not used the empty checkbox returns "NULL" as value and when sending it to PHP/PDO
        // it causes "PDOException"
        HashMap<String, String> map = invoic.tableInvertToHashMap(invoic.TABLE_INVERT_2, DB.START_COLUMN, invoic.getConfigTableInvert_2(), jcomboBoxParamToReturnManuallySpecified);
        map.put(DB.BUH_F_ARTIKEL__FAKTURAID, fakturaId);
        //
        this.articlesList.add(map);
//        invoice.countFakturaTotal(getArticlesTable(), InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL);
        invoice.countFakturaTotal(getArticlesTable());
        //
        String json = JSon.hashMapToJSON(map);
        //
        try {
            //
            String query = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_ARTICLES_TO_DB, json));
//            System.out.println("QUERY: " + query + "    *******************************************"); 
            //
        } catch (Exception ex) {
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
    }

    protected void updateArticle() {
        //
        InvoiceA_Update invoic = (InvoiceA_Update) invoice;
        //
        if (invoic.containsInvalidatedFields(invoic.TABLE_INVERT_2, DB.START_COLUMN, invoic.getConfigTableInvert_2())) {
            HelpA.showNotification(LANG.MSG_1);
            return;
        }
        //
//        ArrayList<HashMap<String, String>> articlesList = new ArrayList<>();
        //
        JTable table = getArticlesTable();
        //
        HashMap<String, String> map = invoic.tableInvertToHashMap(invoic.TABLE_INVERT_2, DB.START_COLUMN, invoic.getConfigTableInvert_2());
        //
        String buh_f_artikel_id = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ID);
        HashMap<String, String> updateMap = invoic.bim.getUPDATE(DB.BUH_F_ARTIKEL__ID, buh_f_artikel_id, DB.TABLE__BUH_F_ARTIKEL);
        //
        // OBS! Important by now [2020-07-29] i don't allow to change artikel, therefore removing "artikelId" entry
//        map.remove(DB.BUH_F_ARTIKEL__ARTIKELID); // 
        //
        HashMap<String, String> final_map = JSon.joinHashMaps(map, updateMap);
        //
        String json = JSon.hashMapToJSON(final_map);
        //
        System.out.println("UPDATE json: " + json);
        //
        HelpBuh.update(json);
        //
        //
        HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__KOMMENT, map.get(DB.BUH_F_ARTIKEL__KOMMENT));
        HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL, map.get(DB.BUH_F_ARTIKEL__ANTAL));
        HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ENHET, JSon.getLongName(DB.STATIC__ENHET, map.get(DB.BUH_F_ARTIKEL__ENHET)));
        HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS, map.get(DB.BUH_F_ARTIKEL__PRIS));
        HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT, map.get(DB.BUH_F_ARTIKEL__RABATT));
        //
        //
//        invoic.countFakturaTotal(table, InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL);
        invoic.countFakturaTotal(table);
        //
        //
    }

    @Override
    protected void setData() {
        //
        JTable table = invoice.bim.jTable_invoiceB_alla_fakturor;
        InvoiceA_Update iu = (InvoiceA_Update) invoice;
        //
        this.mainMap = iu.tableInvertToHashMap(iu.TABLE_INVERT, DB.START_COLUMN, iu.getConfigTableInvert());
        this.secMap = iu.tableInvertToHashMap(iu.TABLE_INVERT_3, DB.START_COLUMN, iu.getConfigTableInvert_3());
        //
        //
        String fakturaId = invoice.bim.getFakturaId();
        //
        //
        HashMap<String, String> update_map = invoice.bim.getUPDATE(DB.BUH_FAKTURA__ID__,
                fakturaId,
                DB.TABLE__BUH_FAKTURA
        );
        //
        HashMap<String, String> map_temp;
        //
        if (mainMap != null && secMap != null) { // [2020-08-12]
            //
            map_temp = HelpA.joinHashMaps(mainMap, secMap);
            //
            this.fakturaMap = JSon.joinHashMaps(map_temp, update_map);
            //
        } else { // The case below is for updating the "totals" only
            //
            map_temp = new HashMap<>();
            //
            this.fakturaMap = JSon.joinHashMaps(map_temp, update_map);
            //
        }
        //
        this.fakturaMap.put(DB.BUH_FAKTURA__TOTAL__, "" + invoice.getFakturaTotal());
        this.fakturaMap.put(DB.BUH_FAKTURA__TOTAL_EXKL_MOMS__, "" + invoice.getTotalExklMoms());
        this.fakturaMap.put(DB.BUH_FAKTURA__MOMS_TOTAL__, "" + invoice.getMomsTotal());
        //
        //
//        System.out.println("-------------------------------------------------");
//        //
//        this.fakturaMap.entrySet().forEach((entry) -> {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            System.out.println(key + "=" + value);
//        });
//        //
//        System.out.println("-------------------------------------------------");
        //
//        JSon.hashMapToJSON(this.fakturaMap); // Temporary here
        //
    }

}
