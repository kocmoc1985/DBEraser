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
import BuhInvoice.LAFakturering;
import forall.HelpA;
import java.io.Serializable;
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

    public static void main(String[] args) {
        //
        GP_BUH.USER = "andrej.brassas@gmail.com";
        GP_BUH.PASS = "KpxHs5jufF";
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

    private void restoreBackup() {
        //[#BACKUP-RESTORE-DATA#]
        //======================================================================
        //Step 0 - DELETE FROM ALL TABLES EXCEPT "buh_kund"
        //
        //
        HttpResponce responce = HelpBuh.deleteBeforeRestore(GP_BUH.PASS);
        //
        if (responce.responce.equals("false")) {
            return;
        }
        //
        //======================================================================
        //======================================================================
        //======================================================================
        //Step 1 - "buh_kund" -> RUN "sql update" instead of "insert"
        //
        for (HashMap<String, String> ftg_data_map : backup_All.buh_kund__1) {
            //
            String ftg_namn = ftg_data_map.get(DB.BUH_KUND__NAMN);
            //
            HashMap<String, String> updateMap = LAFakturering.getUPDATE_static(DB.BUH_KUND__NAMN, ftg_namn, DB.TABLE__BUH_KUND);
            //
            HashMap<String, String> final_map = JSon.joinHashMaps(ftg_data_map, updateMap);
            //
            String json = JSon.hashMapToJSON(final_map);
            //
            final_map.remove(DB.BUH_KUND__ID);
            //
            HelpBuh.update(json);
            //
        }
        //======================================================================
        //Step 2 - "buh_faktura_artikel"
        for (HashMap<String, String> article_ : backup_All.buh_faktura_artikel__2) {
            //[#BACKUP-REDEFINE-ID#]
            //
            String artikelId_old = article_.get(DB.BUH_FAKTURA_ARTIKEL___ID);
            article_.remove(DB.BUH_FAKTURA_ARTIKEL___ID);//[#BACKUP-REMOVE-ID#][USED-IN-OTHER-TABLES]
            //
            article_.put(DB.BUH_FAKTURA_ARTIKEL___DATE_CREATED, GP_BUH.getDateCreated_special());
            String json = JSon.hashMapToJSON(article_);
            //
            try {
                //
                String artikelId = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                        DB.PHP_FUNC_ARTIKEL_TO_DB, json);
                //
                article_.put(DB.BUH_FAKTURA_ARTIKEL___ID, artikelId);
                article_.put(DB.BUH_FAKTURA_ARTIKEL___ID + "_old", artikelId_old);
                //
            } catch (Exception ex) {
                Logger.getLogger(Backup_Restore.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
        }
        //======================================================================
        //Step 3 - "buh_faktura_kund"
        for (HashMap<String, String> faktura_kund__map : backup_All.buh_faktura_kund__3) {
            //
            String faktuKundId__old = faktura_kund__map.get(DB.BUH_FAKTURA_KUND__ID);
            faktura_kund__map.remove(DB.BUH_FAKTURA_KUND__ID);//[#BACKUP-REMOVE-ID#][USED-IN-OTHER-TABLES]
            //
            String json = JSon.hashMapToJSON(faktura_kund__map);
            //
            try {
                //
                String fakturaKundId = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                        DB.PHP_FUNC_FAKTURA_KUND_TO_DB, json);
                //
                faktura_kund__map.put(DB.BUH_FAKTURA_KUND__ID, fakturaKundId);
                faktura_kund__map.put(DB.BUH_FAKTURA_KUND__ID + "_old", faktuKundId__old);
                //
            } catch (Exception ex) {
                Logger.getLogger(Backup_Restore.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
        }
        //======================================================================
        //Step 4 - "buh_faktura"
        for (Backup_Invoice bi : backup_All.allInvoicesList__4_5) {
            //
            String fakturaId_old = bi.getInvoice().get(DB.BUH_FAKTURA__ID__);
            bi.getInvoice().remove(DB.BUH_FAKTURA__ID__); //[#BACKUP-REMOVE-ID#][USED-IN-OTHER-TABLES]
            //
            String fakturaKundId = bi.getInvoice().get(DB.BUH_FAKTURA__FAKTURAKUND_ID);
            //
            for (HashMap<String, String> faktura_kund__map : backup_All.buh_faktura_kund__3) {
                //[#BACKUP-REDEFINE-ID#] -> Redefining foreign key: buh_faktura -> fakturaKundId
                String fakturaKundId__old = faktura_kund__map.get(DB.BUH_FAKTURA_KUND__ID + "_old");
                String fakturaKundId__new = faktura_kund__map.get(DB.BUH_FAKTURA_KUND__ID);
                //
                if (fakturaKundId.trim().equals(fakturaKundId__old.trim())) {
                    bi.getInvoice().put(DB.BUH_FAKTURA_KUND__ID, fakturaKundId__new);
                    break;
                }
                //
            }
            //
            String json = JSon.hashMapToJSON(bi.getInvoice());
            //
            try {
                String fakturaId_new = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                        DB.PHP_FUNC_FAKTURA_TO_DB, json);
                //
                bi.getInvoice().put(DB.BUH_FAKTURA__ID__, fakturaId_new);//[#BACKUP-REDEFINE-ID#]
                bi.getInvoice().put(DB.BUH_FAKTURA__ID__ + "_old", fakturaId_old);
                //
            } catch (Exception ex) {
                Logger.getLogger(Backup_Restore.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
            //
            //Step X1 - modifying the id's for "buh_f_artikel" - OBS! No update to DB done at this step
            for (HashMap<String, String> article : bi.getArticles()) {
                //
                //[#BACKUP-REDEFINE-ID#] -> Redefining foreign key: buh_f_artikel -> fakturaId
                String buh_f_artikel__fakturaId = article.get(DB.BUH_F_ARTIKEL__FAKTURAID);
                //
                String buh_faktura__fakturaId__old = bi.getInvoice().get(DB.BUH_FAKTURA__ID__ + "_old");
                String buh_faktura__fakturaId__new = bi.getInvoice().get(DB.BUH_FAKTURA__ID__);
                //
                if (buh_f_artikel__fakturaId.trim().equals(buh_faktura__fakturaId__old.trim())) {
                    article.put(DB.BUH_F_ARTIKEL__FAKTURAID, buh_faktura__fakturaId__new);
                }
                //
            }
            //
            //Step X2 - modifying the id's for "buh_f_artikel" - OBS! No update to DB done at this step
            for (HashMap<String, String> article : bi.getArticles()) {
                //
                String artikleId = article.get(DB.BUH_F_ARTIKEL__ARTIKELID);
                //
                for (HashMap<String, String> article_ : backup_All.buh_faktura_artikel__2) {
                    //[#BACKUP-REDEFINE-ID#] -> Redefining foreign key: buh_f_artikel -> artikelId
                    String artikelid_old = article_.get(DB.BUH_FAKTURA_ARTIKEL___ID + "_old");
                    String artikelId_new = article_.get(DB.BUH_FAKTURA_ARTIKEL___ID);
                    //
                    if (artikelid_old.trim().equals(artikleId.trim())) {
                        article.put(DB.BUH_F_ARTIKEL__ARTIKELID, artikelId_new);
                    }
                    //
                }
                //
            }
            //
            //
            //Step 5 - "buh_f_artikel"
            for (HashMap<String, String> article : bi.getArticles()) {
                //
                article.remove(DB.BUH_F_ARTIKEL__ID); //[#BACKUP-REMOVE-ID#]
                //
                String artikelId = article.get(DB.BUH_F_ARTIKEL__ARTIKELID);
                //
                if (artikelId == null || artikelId.isEmpty() || artikelId.equals("null") || artikelId.equals("NULL")) {
                    article.remove(DB.BUH_F_ARTIKEL__ARTIKELID); // This is for the cases when you don't use an article but only using comment instead
                }
                //
                String json_ = JSon.hashMapToJSON(article);
                //
                try {
                    //
                    HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                            DB.PHP_FUNC_ARTICLES_TO_DB, json_);
                    //
                } catch (Exception ex) {
                    Logger.getLogger(Backup_Restore.class.getName()).log(Level.SEVERE, null, ex);
                }
                //
            }
            //
        }
        //======================================================================
        //Step 6 - "buh_faktura_inbet"
        for (HashMap<String, String> inbet : backup_All.buh_faktura_inbet__6) {
            //
            inbet.remove(DB.BUH_FAKTURA_INBET__INBET_ID);//[#BACKUP-REMOVE-ID#]
            //
            String buh_faktura_inbet__fakturaId = inbet.get(DB.BUH_FAKTURA_INBET__FAKTURA_ID);
            //
            for (Backup_Invoice bi : backup_All.allInvoicesList__4_5) {
                //[#BACKUP-REDEFINE-ID#] -> Redefining foreign key: buh_faktura_inbet -> fakturaId
                String buh_faktura__fakturaId_old = bi.getInvoice().get(DB.BUH_FAKTURA__ID__ + "_old");
                String buh_faktura__fakturaId_new = bi.getInvoice().get(DB.BUH_FAKTURA__ID__);
                //
                if (buh_faktura_inbet__fakturaId.trim().equals(buh_faktura__fakturaId_old.trim())) {
                    inbet.put(DB.BUH_FAKTURA_INBET__FAKTURA_ID, buh_faktura__fakturaId_new);
                    break;
                }
                //
            }
            //
            String json = JSon.hashMapToJSON(inbet);
            //
            try {
                //
                HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                        DB.PHP_FUNC_FAKTURA_INBET_TO_DB, json);
                //
            } catch (Exception ex) {
                Logger.getLogger(Backup_Restore.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
        }
        //======================================================================
        //Step 7 - "buh_address"
        for (HashMap<String, String> address : backup_All.buh_address__7) {
            //
            address.remove(DB.BUH_ADDR__ID);//[#BACKUP-REMOVE-ID#]
            //
            String fakturaKundId = address.get(DB.BUH_ADDR__FAKTURAKUND_ID);
            //
            for (HashMap<String, String> faktura_kund__map : backup_All.buh_faktura_kund__3) {
                //[#BACKUP-REDEFINE-ID#] -> Redefining foreign key: buh_address -> fakturaKundId
                String fakturaKundId__old = faktura_kund__map.get(DB.BUH_FAKTURA_KUND__ID + "_old");
                String fakturaKundId__new = faktura_kund__map.get(DB.BUH_FAKTURA_KUND__ID);
                //
                if (fakturaKundId.trim().equals(fakturaKundId__old.trim())) {
                    address.put(DB.BUH_ADDR__FAKTURAKUND_ID, fakturaKundId__new);
                    break;
                }
                //
            }
            //
            if (fakturaKundId == null || fakturaKundId.isEmpty() || fakturaKundId.equals("null") || fakturaKundId.equals("NULL")) {
                // IMPORTANT Means that this address belongs to the company it's self
                // OBS! This makes that the address of company IS NOT RESTORED
                continue;
            }
            //
            String json = JSon.hashMapToJSON(address);
            //
            try {
                //
                HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                        DB.PHP_FUNC_FAKTURA_KUND_ADDR_TO_DB, json);
                //
            } catch (Exception ex) {
                Logger.getLogger(Backup_Restore.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
        }
        //======================================================================
        //Step 8 - "buh_faktura_send"
        for (HashMap<String, String> send : backup_All.buh_faktura_send_8) {
            //
            send.remove(DB.BUH_FAKTURA_SEND__ID);//[#BACKUP-REMOVE-ID#]
            //
            String buh_faktura_send__fakturaId = send.get(DB.BUH_FAKTURA_SEND__FAKTURA_ID);
            //
            for (Backup_Invoice bi : backup_All.allInvoicesList__4_5) {
                //[#BACKUP-REDEFINE-ID#] -> Redefining foreign key: buh_faktura_send -> fakturaId
                String buh_faktura__fakturaId_old = bi.getInvoice().get(DB.BUH_FAKTURA__ID__ + "_old");
                String buh_faktura__fakturaId_new = bi.getInvoice().get(DB.BUH_FAKTURA__ID__);
                //
                if (buh_faktura_send__fakturaId.trim().equals(buh_faktura__fakturaId_old.trim())) {
                    send.put(DB.BUH_FAKTURA_SEND__FAKTURA_ID, buh_faktura__fakturaId_new);
                    break;
                }
                //
            }
            //
            String json = JSon.hashMapToJSON(send);
            //
            try {
                //
                HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                        DB.PHP_FUNC_FAKTURA_SEND_TO_DB, json);
                //
            } catch (Exception ex) {
                Logger.getLogger(Backup_Restore.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
        }
        //======================================================================
        //Step 9 - "buh_faktura_rut"
        for (HashMap<String, String> rut : backup_All.buh_faktura_rut_9) {
            //
            String rutId_old = rut.get(DB.BUH_FAKTURA_RUT__ID);
            rut.remove(DB.BUH_FAKTURA_RUT__ID); //[#BACKUP-REMOVE-ID#][USED-IN-OTHER-TABLES]
            //
            for (Backup_Invoice bi : backup_All.allInvoicesList__4_5) {
                //[#BACKUP-REDEFINE-ID#] -> Redefining: buh_faktura_rut -> fakturaId
                String buh_faktura_rut__fakturaId = rut.get(DB.BUH_FAKTURA_RUT__FAKTURAID);
                String buh_faktura__fakturaId_old = bi.getInvoice().get(DB.BUH_FAKTURA__ID__ + "_old");
                String buh_faktura__fakturaId_new = bi.getInvoice().get(DB.BUH_FAKTURA__ID__);
                //
                if (buh_faktura_rut__fakturaId.trim().equals(buh_faktura__fakturaId_old.trim())) {
                    rut.put(DB.BUH_FAKTURA_RUT__FAKTURAID, buh_faktura__fakturaId_new);
                    break;
                }
                //
            }
            //
            String json = JSon.hashMapToJSON(rut);
            //
            try {
                String rutId = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                        DB.PHP_FUNC_FAKTURA_RUT_ENTRY_TO_DB, json);
                //
                rut.put(DB.BUH_FAKTURA_RUT__ID, rutId);
                rut.put(DB.BUH_FAKTURA_RUT__ID + "_old", rutId_old);
                //
            } catch (Exception ex) {
                Logger.getLogger(Backup_Restore.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
        }
        //======================================================================
        //Step 10 - "buh_faktura_rut_person"
        for (HashMap<String, String> rut_person : backup_All.buh_faktura_rut_person_10) {
            //
            String rut_person_rutId = rut_person.get(DB.BUH_FAKTURA_RUT_PERSON__RUTID);
            //
            for (HashMap<String, String> rut : backup_All.buh_faktura_rut_9) {
                //[#BACKUP-REDEFINE-ID#] -> Redefining foreign key: buh_faktura_rut_person -> rutId
                String rutId_old = rut.get(DB.BUH_FAKTURA_RUT__ID + "_old");
                String rutId_new = rut.get(DB.BUH_FAKTURA_RUT__ID);
                //
                if (rut_person_rutId.trim().equals(rutId_old)) {
                    rut_person.put(DB.BUH_FAKTURA_RUT_PERSON__RUTID, rutId_new);
                }
                //
            }
            //
            //
            String buh_faktura_rut_person__fakturaId = rut_person.get(DB.BUH_FAKTURA_RUT_PERSON__FAKTURAID);
            //
            for (Backup_Invoice bi : backup_All.allInvoicesList__4_5) {
                //[#BACKUP-REDEFINE-ID#] -> Redefining foreign key: buh_faktura_rut_person -> fakturaId
                String buh_faktura__fakturaId_old = bi.getInvoice().get(DB.BUH_FAKTURA__ID__ + "_old");
                String buh_faktura__fakturaId_new = bi.getInvoice().get(DB.BUH_FAKTURA__ID__);
                //
                if (buh_faktura_rut_person__fakturaId.trim().equals(buh_faktura__fakturaId_old.trim())) {
                    rut_person.put(DB.BUH_FAKTURA_RUT_PERSON__FAKTURAID, buh_faktura__fakturaId_new);
                    break;
                }
                //
            }
            //
            String json = JSon.hashMapToJSON(rut_person);
            //
            try {
                HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                        DB.PHP_FUNC_FAKTURA_RUT_PERSON_ENTRY_TO_DB, json);
            } catch (Exception ex) {
                Logger.getLogger(Backup_Restore.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
        }
        //======================================================================
    }

}
