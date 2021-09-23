/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import forall.HelpA;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author KOCMOC
 */
public class Backup_All implements Serializable {

    protected final static String BACKUP_FILE_NAME = "BACKUP";

    protected final ArrayList<HashMap<String, String>> buh_kund__1;
    protected final ArrayList<HashMap<String, String>> buh_faktura_artikel__2;
    protected final ArrayList<HashMap<String, String>> buh_faktura_kund__3;
    protected final ArrayList<Backup_Invoice> allInvoicesList__4_5;
    protected final ArrayList<HashMap<String, String>> buh_faktura_inbet__6;
    protected final ArrayList<HashMap<String, String>> buh_address__7;

    public Backup_All(ArrayList<HashMap<String, String>> buh_kund__1, ArrayList<HashMap<String, String>> buh_faktura_artikel__2, ArrayList<HashMap<String, String>> buh_faktura_kund__3, ArrayList<Backup_Invoice> allInvoicesList__4_5, ArrayList<HashMap<String, String>> buh_faktura_inbet__6, ArrayList<HashMap<String, String>> buh_address__7) {
        this.buh_kund__1 = buh_kund__1;
        this.buh_faktura_artikel__2 = buh_faktura_artikel__2;
        this.buh_faktura_kund__3 = buh_faktura_kund__3;
        this.allInvoicesList__4_5 = allInvoicesList__4_5;
        this.buh_faktura_inbet__6 = buh_faktura_inbet__6;
        this.buh_address__7 = buh_address__7;
        //
        toFile();
        //
    }

    private void toFile() {
        HelpA.objectToFile(BACKUP_FILE_NAME, this); // HelpA.get_proper_date_time_same_format_on_all_computers_err_output()
    }

}
