/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KOCMOC
 */
public class Buh_Faktura_Entry {

    private HashMap<String, String> mainMap = new HashMap<>();
    private ArrayList<HashMap<String, String>> articlesMap = new ArrayList<>();
    private ArrayList<HashMap<String, String>> articlesMapJTable = new ArrayList<>();

    public void setMainMap(HashMap<String, String> mainMap) {
        this.mainMap = mainMap;
    }

    public void addArticleForDB(HashMap<String, String> article) {
        this.articlesMap.add(article);
    }

    public void addArticleForJTable(HashMap<String, String> article, JTable table) {
        this.articlesMapJTable.add(article);
        addToJTable(article, table);

    }

    private void addToJTable(HashMap<String, String> map, JTable table) {
        //
        Object[] jtableRow = new Object[]{
            map.get(DB.BUH_F_ARTIKEL__ARTIKELID),
            map.get(DB.BUH_F_ARTIKEL__KOMMENT),
            map.get(DB.BUH_F_ARTIKEL__ANTAL),
            map.get(DB.BUH_F_ARTIKEL__ENHET),
            map.get(DB.BUH_F_ARTIKEL__PRIS),
            map.get(DB.BUH_F_ARTIKEL__RABATT)
        };
        //
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(jtableRow);
        //
    }
}
