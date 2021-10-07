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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class Backup_Make_Backup {

    public static void main(String[] args) {
        //
        GP_BUH.USER = "andrej.brassas@gmail.com";
        GP_BUH.PASS = "KpxHs5jufF";
        //
        backup();
        //
    }

    private static ArrayList<HashMap<String, String>> backup_single_table(String phpFunc) {
        //
        String json = LAFakturering.getSELECT_(DB.BUH_KUND__ID, "777");
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    phpFunc, json);
            //
            ArrayList<HashMap<String, String>> entries = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            return entries;
            //
        } catch (Exception ex) {
            Logger.getLogger(HelpBuh.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private static void backup() {
        //[#BACKUP-RESTORE-DATA#]
        ArrayList<HashMap<String, String>> buh_kund__1 = backup_single_table(DB.PHP_FUNC_PARAM_GET_FORETAG_DATA);
        //
        ArrayList<HashMap<String, String>> buh_faktura_artikel__2 = backup_single_table(DB.PHP_FUNC_PARAM_GET_KUND_ARTICLES_ALL_DATA);
        //
        ArrayList<HashMap<String, String>> buh_faktura_kund__3 = backup_single_table(DB.PHP_FUNC_PARAM_GET_FAKTURA_KUNDER_ALL_DATA_SIMPLE);
        //
        ArrayList<Backup_Invoice> allInvoicesList__4_5 = backup_4_5();
        //
        ArrayList<HashMap<String, String>> buh_faktura_inbet__6 = backup_single_table(DB.PHP_FUNC_PARAM__GET_FAKTURA_INBET_SIMPLE);
        //
        ArrayList<HashMap<String, String>> buh_address__7 = backup_single_table(DB.PHP_FUNC_PARAM_GET_FAKTURA_KUND_ADDRESSES_SIMPLE);
        //
        ArrayList<HashMap<String, String>> buh_faktura_send_8 = backup_single_table(DB.PHP_FUNC_PARAM__GET_FAKTURA_SEND_SIMPLE);
        //
        ArrayList<HashMap<String, String>> buh_faktura_rut_9 = backup_single_table(DB.PHP_FUNC_PARAM_GET_RUT_SIMPLE);
        //
        ArrayList<HashMap<String, String>> buh_faktura_rut_person_10 = backup_single_table(DB.PHP_FUNC_PARAM_GET_RUT_PERSON_SIMPLE);
        //
        Backup_All backup_All = new Backup_All(
                buh_kund__1,
                buh_faktura_artikel__2,
                buh_faktura_kund__3,
                allInvoicesList__4_5,
                buh_faktura_inbet__6,
                buh_address__7,
                buh_faktura_send_8,
                buh_faktura_rut_9,
                buh_faktura_rut_person_10
        );
        //
        System.out.println("");
        //
    }

    private static ArrayList<Backup_Invoice> backup_4_5() { // Backup of all invoices with all articles
        //
        String json = LAFakturering.getSELECT_(DB.BUH_FAKTURA__KUNDID__, "777");
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ALL__SIMPLE, json);
            //
            ArrayList<HashMap<String, String>> allInvoices = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            ArrayList<Backup_Invoice> allInvoicesList = new ArrayList<>();
            //
            for (HashMap<String, String> invoice : allInvoices) {
                //
                String fakturaId = invoice.get("fakturaId");
                //
                String json_ = LAFakturering.getSELECT_doubleWhere_(DB.BUH_F_ARTIKEL__KUND_ID, "777", DB.BUH_F_ARTIKEL__FAKTURAID, fakturaId);
                //
                String json_str_return_ = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                        DB.PHP_FUNC_PARAM_GET_FAKTURA_ARTICLES__BUH_F_ARTIKEL, json_);
                //
                if (json_str_return_.equals(DB.PHP_SCRIPT_RETURN_EMPTY)) { // this value='empty' is returned by PHP script
                    continue;
                }
                //
                ArrayList<HashMap<String, String>> articles = JSon.phpJsonResponseToHashMap(json_str_return_);
                //
                Backup_Invoice bie = new Backup_Invoice(0, invoice, articles);
                //
                allInvoicesList.add(bie);
                //
            }
            //
            return allInvoicesList;
            //
        } catch (Exception ex) {
            Logger.getLogger(HelpBuh.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
