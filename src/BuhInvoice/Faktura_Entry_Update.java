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
public class Faktura_Entry_Update extends Faktura_Entry_Insert_ {

    public Faktura_Entry_Update(InvoiceA_Update invoiceA_update) {
        super(invoiceA_update);
    }

    @Override
    public void addArticleForDB() {
        super.addArticleForDB(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addArticleForJTable(JTable table) {
        super.addArticleForJTable(table); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * [2020-07-29] Here it's for the update purpose
     */
    @Override
    public void fakturaToHttpDB() {
        //
//        InvoiceA_Update invoice_update = (InvoiceA_Update)invoiceA;
        //
        JTable table = invoiceA.bim.jTable_invoiceB_alla_fakturor;
        //
        setMainFakturaData();
        //
        String json = JSon.hashMapToJSON(this.fakturaMap);
        //
        String fakturaId = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__FAKTURA_ID);
        System.out.println("FAKTURA ID AQUIRED: " + fakturaId);
        //
        if (verifyFakturaId(fakturaId) == false) {
            HelpA.showNotification("Kunde inte updatera fakturan (Faktura id saknas)");
        }
        //
        try {
            //
            HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_UPDATE_AUTO, json));
            //
        } catch (Exception ex) {
            Logger.getLogger(Faktura_Entry_Update.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        setFakturaIdForArticles(fakturaId);
        //
        articlesToHttpDB();
        //
    }

    @Override
    protected void setMainFakturaData() {
        //
        JTable table = invoiceA.bim.jTable_invoiceB_alla_fakturor;
        InvoiceA_Update iu = (InvoiceA_Update) invoiceA;
        //
        this.mainMap = iu.tableInvertToHashMap(iu.TABLE_INVERT, 1, iu.getConfigTableInvert());
        this.secMap = iu.tableInvertToHashMap(iu.TABLE_INVERT_3, 1, iu.getConfigTableInvert_3());
        //
        //
        String fakturaId = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_ALL_INVOICES__FAKTURA_ID);
        //
        //
        HashMap<String, String> update_map = invoiceA.bim.getUPDATE(
                DB.BUH_FAKTURA__ID__,
                fakturaId,
                DB.DB__BUH_FAKTURA
        );
        //
        HashMap<String, String> map_temp = HelpA.joinHashMaps(mainMap, secMap);
        //
        this.fakturaMap = HelpA.joinHashMaps(map_temp, update_map);
        //
        //
        this.fakturaMap.put(DB.BUH_FAKTURA__TOTAL__, "" + FAKTURA_TOTAL);
        this.fakturaMap.put(DB.BUH_FAKTURA__TOTAL_EXKL_MOMS__, "" + FAKTURA_TOTAL_EXKL_MOMS);
        this.fakturaMap.put(DB.BUH_FAKTURA__MOMS_TOTAL__, "" + MOMS_TOTAL);
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
