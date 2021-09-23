/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author KOCMOC
 */
public class Backup_Invoice implements Serializable{

    private final int fakturaId;
    private final HashMap<String, String> invoice;
    private final ArrayList<HashMap<String, String>> articles;

    public Backup_Invoice(int fakturaId, HashMap<String, String> invoice, ArrayList<HashMap<String, String>> articles) {
        this.fakturaId = fakturaId;
        this.invoice = invoice;
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "FakturaId: " + fakturaId;
    }

    public ArrayList<HashMap<String, String>> getArticles() {
        return articles;
    }

    public int getFakturaId() {
        return fakturaId;
    }

    public HashMap<String, String> getInvoice() {
        return invoice;
    }
    
    

}
