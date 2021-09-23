/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import BuhInvoice.DB;
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

    private void restoreBackup(){
        //
        // I will train with step "3"
        //
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
            //
            //
            //
        }
        //
    }
    
    
    private void singleTableToDB(ArrayList<HashMap<String, String>>list){
        
    }
    
    private void invoicesToDB(ArrayList<Backup_Invoice>list){
        
    }
    
  
    
    public static void main(String[] args) {
        try {
            //
            Backup_All ba = (Backup_All) HelpA.fileToObject(Backup_All.BACKUP_FILE_NAME);
            //
            Backup_Restore bg = new Backup_Restore(ba);
            //
            System.out.println("");
            //
//            bg.restoreBackup();
            //
        } catch (Exception ex) {
            Logger.getLogger(Backup_Restore.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
