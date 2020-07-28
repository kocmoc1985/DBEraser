/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import forall.HelpA;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 *
 * @author MCREMOTE
 */
public class Faktura_Entry_Update extends Faktura_Entry_Insert {

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
    

    @Override
    public void fakturaToHttpDB() {
        //
        //
        setMainFakturaData();
        //
        //
        String json = JSon.hashMapToJSON(this.fakturaMap);
        //
        String fakturaId = "OBS! EXIST";
        //
        try {
            //
            HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    "XXXXXX-XXXXX-XXXXX-XXXXX", json));
            //
            System.out.println("FAKTURA ID AQUIRED: " + fakturaId);
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
        InvoiceA_Update iu = (InvoiceA_Update) invoiceA;
        //
        this.mainMap = iu.tableInvertToHashMap(iu.TABLE_INVERT, 1, iu.getConfigTableInvert());
        this.secMap = iu.tableInvertToHashMap(iu.TABLE_INVERT_3, 1, iu.getConfigTableInvert_3());
        //
        this.fakturaMap = HelpA.joinHashMaps(mainMap, secMap);
        //
//        this.fakturaMap.put(DB.BUH_FAKTURA__KUNDID__, invoiceA.getKundId());
//        this.fakturaMap.put(DB.BUH_FAKTURA__FAKTURANR__, invoiceA.getFakturaNr()); // OBS! Aquired from http
        //
        this.fakturaMap.put(DB.BUH_FAKTURA__TOTAL__, "" + FAKTURA_TOTAL);
        this.fakturaMap.put(DB.BUH_FAKTURA__TOTAL_EXKL_MOMS__, "" + FAKTURA_TOTAL_EXKL_MOMS);
        this.fakturaMap.put(DB.BUH_FAKTURA__MOMS_TOTAL__, "" + MOMS_TOTAL);
        //
//        this.fakturaMap.put(DB.BUH_FAKTURA__DATE_CREATED__, getDateWithTime());
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

}
