/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import forall.HelpA;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public abstract class Faktura_Entry {

    //
    protected final Invoice invoice;
    protected HashMap<String, String> mainMap = new HashMap<>();
    protected HashMap<String, String> secMap = new HashMap<>();
    protected HashMap<String, String> fakturaMap = new HashMap<>();
    protected ArrayList<HashMap<String, String>> articlesListJTable = new ArrayList<>(); // It was not used somehow [2020-08-14]
    protected ArrayList<HashMap<String, String>> articlesList = new ArrayList<>();
    //
  
    public Faktura_Entry(Invoice invoice) {
        this.invoice = invoice;
    }

    protected void resetLists(){
        this.articlesList.clear();
        this.articlesListJTable.clear();
    }
    
    protected JTable getArticlesTable(){
        return invoice.getArticlesTable();
    }
    
    protected abstract void insertOrUpdate();

    protected abstract void setData();
    
    public abstract void addArticleForDB();
    
    public void addArticleForJTable(JTable table) {
        //
        int jcomboBoxParamToReturnManuallySpecified = 1; // returning the artikel "name" -> refers to "HelpA.ComboBoxObject"
        HashMap<String, String> map = invoice.tableInvertToHashMap(invoice.TABLE_INVERT_2, DB.START_COLUMN, jcomboBoxParamToReturnManuallySpecified);
        //
        this.articlesListJTable.add(map);
        //
        Object[] jtableRow = new Object[]{
            map.get(DB.BUH_F_ARTIKEL__ARTIKELID),
            map.get(DB.BUH_F_ARTIKEL__KOMMENT),
            map.get(DB.BUH_F_ARTIKEL__ANTAL),
            map.get(DB.BUH_F_ARTIKEL__ENHET),
            map.get(DB.BUH_F_ARTIKEL__PRIS),
            map.get(DB.BUH_F_ARTIKEL__RABATT),
            map.get(DB.BUH_F_ARTIKEL__RABATT_KR)
        };
        //
        HelpA.addRowToJTable(jtableRow, table);
        //
    }
    

}
