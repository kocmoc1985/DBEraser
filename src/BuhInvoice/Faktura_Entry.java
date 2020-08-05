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

    protected final Invoice invoice;
    protected HashMap<String, String> mainMap = new HashMap<>();
    protected HashMap<String, String> secMap = new HashMap<>();
    protected HashMap<String, String> fakturaMap = new HashMap<>();
    //
  

    public Faktura_Entry(Invoice invoice) {
        this.invoice = invoice;
    }

    protected abstract void insertOrUpdate();

    protected abstract void setData();
    
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

}
