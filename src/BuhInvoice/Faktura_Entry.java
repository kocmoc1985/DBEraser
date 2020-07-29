/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 *
 * @author KOCMOC
 */
public abstract class Faktura_Entry {

    protected final InvoiceA_Insert invoiceA;
    protected HashMap<String, String> mainMap = new HashMap<>();
    protected HashMap<String, String> secMap = new HashMap<>();
    protected HashMap<String, String> fakturaMap = new HashMap<>();
    //
    protected double FAKTURA_TOTAL_EXKL_MOMS = 0;
    protected double FAKTURA_TOTAL = 0;
    protected double MOMS_TOTAL = 0;

    public Faktura_Entry(InvoiceA_Insert invoiceA) {
        this.invoiceA = invoiceA;
    }
    
    protected abstract void insertOrUpdate();
    
    protected abstract void setData();
    
    protected void countFakturaTotal(HashMap<String, String> map) {
        //
        int antal = Integer.parseInt(map.get(DB.BUH_F_ARTIKEL__ANTAL));
        //
        FAKTURA_TOTAL += Double.parseDouble(map.get(DB.BUH_F_ARTIKEL__PRIS)) * antal;
        //
        if (invoiceA.getInklMoms()) {
            MOMS_TOTAL = FAKTURA_TOTAL * invoiceA.getMomsSats();
            FAKTURA_TOTAL += MOMS_TOTAL;
            FAKTURA_TOTAL_EXKL_MOMS = FAKTURA_TOTAL - MOMS_TOTAL;
        }
        //
        BUH_INVOICE_MAIN.jTextField_total_inkl_moms.setText("" + FAKTURA_TOTAL);
        BUH_INVOICE_MAIN.jTextField_total_exkl_moms.setText("" + FAKTURA_TOTAL_EXKL_MOMS);
        BUH_INVOICE_MAIN.jTextField_moms.setText("" + MOMS_TOTAL);
        //
    }
    
    protected boolean verifyFakturaId(String fakturaId) {
        //
        int id;
        //
        try {
            id = Integer.parseInt(fakturaId);
        } catch (Exception ex) {
            id = -1;
        }
        //
        return id != -1;
        //
    }
    
    protected String getDateWithTime() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss.SSS");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

}
