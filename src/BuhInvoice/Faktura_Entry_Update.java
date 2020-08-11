/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import forall.HelpA;
import java.util.HashMap;
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
    }

    @Override
    public void addArticleForDB() {
        //
        // As i see it today [2020-08-11], i should do it somehow like "updateArticle()"
        //
    }

    protected void updateArticle() {
        //
        InvoiceA_Insert invoic = (InvoiceA_Insert) invoice;
        //
        if (invoic.containsInvalidatedFields(invoic.TABLE_INVERT_2, DB.START_COLUMN, invoic.getConfigTableInvert_2())) {
            HelpA.showNotification(LANG.MSG_1);
            return;
        }
        //
//        ArrayList<HashMap<String, String>> articlesList = new ArrayList<>();
        //
        JTable table = invoic.bim.jTable_InvoiceA_Insert_articles;
        //
        HashMap<String, String> map = invoic.tableInvertToHashMap(invoic.TABLE_INVERT_2, DB.START_COLUMN, invoic.getConfigTableInvert_2());
        //
        String buh_f_artikel_id = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ID);
        HashMap<String, String> updateMap = invoic.bim.getUPDATE(DB.BUH_F_ARTIKEL__ID, buh_f_artikel_id, DB.TABLE__BUH_F_ARTIKEL);
        //
        // OBS! Important by now [2020-07-29] i don't allow to change artikel, therefore removing "artikelId" entry
        map.remove(DB.BUH_F_ARTIKEL__ARTIKELID); // 
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
        HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ENHET, map.get(DB.BUH_F_ARTIKEL__ENHET));
        HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS, map.get(DB.BUH_F_ARTIKEL__PRIS));
        HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT, map.get(DB.BUH_F_ARTIKEL__RABATT));
        //
        //
        invoic.countFakturaTotal(table, InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL);
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
        String fakturaId = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__FAKTURA_ID);
        //
        //
        HashMap<String, String> update_map = invoice.bim.getUPDATE(DB.BUH_FAKTURA__ID__,
                fakturaId,
                DB.TABLE__BUH_FAKTURA
        );
        //
        HashMap<String, String> map_temp = HelpA.joinHashMaps(mainMap, secMap);
        //
        this.fakturaMap = JSon.joinHashMaps(map_temp, update_map);
        //
        //
        this.fakturaMap.put(DB.BUH_FAKTURA__TOTAL__, "" + invoice.getFakturaTotal());
        this.fakturaMap.put(DB.BUH_FAKTURA__TOTAL_EXKL_MOMS__, "" + invoice.getTotalExklMoms());
        this.fakturaMap.put(DB.BUH_FAKTURA__MOMS_TOTAL__, "" + invoice.getMomsTotal());
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

}
