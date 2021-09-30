/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import BuhInvoice.DB;
import BuhInvoice.GP_BUH;
import BuhInvoice.HelpBuh;
import BuhInvoice.JSon;
import forall.HelpA;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class Backup_Restore implements Serializable {

    private final Backup_All backup_All;

    public Backup_Restore(Backup_All backup_All) {
        this.backup_All = backup_All;
    }

    private void restoreBackup() {
        //======================================================================
        //Step 0 - DELETE FROM ALL TABLES EXCEPT "buh_kund"
        //
        // OptionsTab_.class -> deleteCustomerDataPermanent()
        //
        //======================================================================
        //Step 1 - "buh_kund" -> RUN "sql update" instead of "insert"
        //
        //======================================================================
        //Step 2 - "buh_faktura_artikel"
        for (HashMap<String, String> article_ : backup_All.buh_faktura_artikel__2) {
             //
            String json = JSon.hashMapToJSON(article_);
            //
            try {
                HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                        DB.PHP_FUNC_ARTIKEL_TO_DB, json);
            } catch (Exception ex) {
                Logger.getLogger(Backup_Restore.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
        }
        //======================================================================
        //Step 3 - "buh_faktura_kund"
        for (HashMap<String, String> faktura_kund__map : backup_All.buh_faktura_kund__3) {
            //
            String json = JSon.hashMapToJSON(faktura_kund__map);
            //
            try {
                HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                        DB.PHP_FUNC_FAKTURA_KUND_TO_DB, json);
            } catch (Exception ex) {
                Logger.getLogger(Backup_Restore.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
        }
        //======================================================================
        //Step 4 - "buh_faktura"
        for (Backup_Invoice bi : backup_All.allInvoicesList__4_5) {
            //
            String json = JSon.hashMapToJSON(bi.getInvoice());
            //
            try {
                HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                        DB.PHP_FUNC_FAKTURA_TO_DB, json);
            } catch (Exception ex) {
                Logger.getLogger(Backup_Restore.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
            //Step 5 - "buh_f_artikel"
            for (HashMap<String, String> article : bi.getArticles()) {
                //
                article.remove(DB.BUH_F_ARTIKEL__ID); // VERY IMPORTANT
                //
                String json_ = JSon.hashMapToJSON(article);
                //
                try {
                    HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                            DB.PHP_FUNC_ARTICLES_TO_DB, json_);
                } catch (Exception ex) {
                    Logger.getLogger(Backup_Restore.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //
        }
        //======================================================================
        //Step 6 - "buh_faktura_inbet"
//        for (HashMap<String, String> inbetalning : backup_All.buh_faktura_inbet__6) {
//            
//        }
        //======================================================================
         //Step 7 - "buh_address"
//        for (HashMap<String, String> address : backup_All.buh_address__7) {
//            
//        }
        //
    }

    private void singleTableToDB(ArrayList<HashMap<String, String>> list) {

    }

    private void invoicesToDB(ArrayList<Backup_Invoice> list) {

    }

    public static void main(String[] args) {
        //
        GP_BUH.USER = "andrej.brassas@gmail.com";
        GP_BUH.PASS = "09WYJK1aUy";
        //
        try {
            //
            Backup_All ba = (Backup_All) HelpA.fileToObject(Backup_All.BACKUP_FILE_NAME);
            //
            Backup_Restore bg = new Backup_Restore(ba);
            //
            System.out.println("");
            //
            bg.restoreBackup();
            //
        } catch (Exception ex) {
            Logger.getLogger(Backup_Restore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
