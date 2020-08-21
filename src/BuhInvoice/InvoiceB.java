/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTableInvert.Basic;
import MyObjectTableInvert.RowDataInvert;
import forall.HelpA;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KOCMOC
 */
public class InvoiceB extends Basic {

    private final BUH_INVOICE_MAIN bim;
    //
    public static String TABLE_ALL_INVOICES__FAKTURA_ID = "ID";
    public static String TABLE_ALL_INVOICES__KUND = "KUND";
    public static String TABLE_ALL_INVOICES__FAKTURANR = "FAKTURANR";
    public static String TABLE_ALL_INVOICES__FAKTURA_TYP = "FAKTURATYP";
    public static String TABLE_ALL_INVOICES__KUND_ID = "KUND ID";
    public static String TABLE_ALL_INVOICES__KUND_NR = "KUND NR";
    public static String TABLE_ALL_INVOICES__DATUM = "FAKTURADATUM";
    public static String TABLE_ALL_INVOICES__FORFALLODATUM = "FÖRFALLODATUM";
    public static String TABLE_ALL_INVOICES__VAR_REF = "VAR REF";
    public static String TABLE_ALL_INVOICES__ER_REF = "ER REF";
    public static String TABLE_ALL_INVOICES__BET_VILKOR = "B VILKOR";
    public static String TABLE_ALL_INVOICES__LEV_VILKOR = "LEV VILKOR";
    public static String TABLE_ALL_INVOICES__LEV_SATT = "LEV SATT";
    //
    public static String TABLE_ALL_INVOICES__IS_INKL_MOMS = "MOMS INKL";
    public static String TABLE_ALL_INVOICES__TOTAL_INKL_MOMS = "TOTAL";
    public static String TABLE_ALL_INVOICES__EXKL_MOMS = "EXKL MOMS";
    public static String TABLE_ALL_INVOICES__MOMS = "MOMS";
    public static String TABLE_ALL_INVOICES__MOMS_SATS = "MOMS SATS";
    public static String TABLE_ALL_INVOICES__FRAKT = "FRAKT";
    public static String TABLE_ALL_INVOICES__EXP_AVG = "EXP AVG";
    public static String TABLE_ALL_INVOICES__MAKULERAD = "MAKULERAD";
    public static String TABLE_ALL_INVOICES__VALUTA = "VALUTA";
    public static String TABLE_ALL_INVOICES__BETALD = "BETALD";
    //
    //
    public static String TABLE_INVOICE_ARTIKLES__FAKTURA_ID = "FAKTURA ID";
    public static String TABLE_INVOICE_ARTIKLES__ID = "ID";
    public static String TABLE_INVOICE_ARTIKLES__ARTIKEL_ID = "ARTIKEL ID"; // hidden
    public static String TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN = "ARTIKEL";
    public static String TABLE_INVOICE_ARTIKLES__KOMMENT = "KOMMENT";
    public static String TABLE_INVOICE_ARTIKLES__ANTAL = "ANTAL";
    public static String TABLE_INVOICE_ARTIKLES__ENHET = "ENHET";
    public static String TABLE_INVOICE_ARTIKLES__PRIS = "PRIS";
    public static String TABLE_INVOICE_ARTIKLES__RABATT = "RABATT %";
    public static String TABLE_INVOICE_ARTIKLES__RABATT_KR = "RABATT KR";

    public InvoiceB(BUH_INVOICE_MAIN buh_invoice_main) {
        this.bim = buh_invoice_main;
        initOther();
    }

    private void initOther() {
        //
        fillJTableheader();
        //
        fillFakturaTable();
        //
        // fillFakturaArticlesTable();
    }

    /**
     * [2020-08-12] This one keeps the "marking line" on the same row as before
     * the refresh
     */
    protected void refresh_b() {
        JTable table = bim.jTable_invoiceB_alla_fakturor;
        int row = table.getSelectedRow();
        fillFakturaTable();
        HelpA.markGivenRow(bim.jTable_invoiceB_alla_fakturor, row);
        String fakturaId = bim.getFakturaId();
        all_invoices_table_clicked(fakturaId);
    }

    protected void refresh() {
        fillFakturaTable();
        HelpA.markFirstRowJtable(bim.jTable_invoiceB_alla_fakturor);
        String fakturaId = bim.getFakturaId();
        all_invoices_table_clicked(fakturaId);
    }

    private void fillJTableheader() {
        //
        //
        JTable table = this.bim.jTable_invoiceB_alla_fakturor;
        //        
        // "ID" -> "buh_faktura.fakturaId"
        //
        String[] headers = {
            TABLE_ALL_INVOICES__FAKTURA_ID, // hidden
            TABLE_ALL_INVOICES__KUND_ID, // hidden
            TABLE_ALL_INVOICES__KUND_NR, // hidden
            TABLE_ALL_INVOICES__VAR_REF, // hidden
            TABLE_ALL_INVOICES__ER_REF, // hidden
            TABLE_ALL_INVOICES__BET_VILKOR, // hidden
            TABLE_ALL_INVOICES__LEV_VILKOR, // hidden
            TABLE_ALL_INVOICES__LEV_SATT, // hidden
            TABLE_ALL_INVOICES__IS_INKL_MOMS,
            TABLE_ALL_INVOICES__MOMS_SATS,
            TABLE_ALL_INVOICES__FRAKT,
            TABLE_ALL_INVOICES__EXP_AVG,
            TABLE_ALL_INVOICES__MAKULERAD,
            //
            TABLE_ALL_INVOICES__FAKTURANR,
            TABLE_ALL_INVOICES__FAKTURA_TYP,
            TABLE_ALL_INVOICES__KUND,
            TABLE_ALL_INVOICES__DATUM,
            TABLE_ALL_INVOICES__FORFALLODATUM,
            TABLE_ALL_INVOICES__EXKL_MOMS,
            TABLE_ALL_INVOICES__TOTAL_INKL_MOMS,
            TABLE_ALL_INVOICES__MOMS,
            TABLE_ALL_INVOICES__VALUTA,
            TABLE_ALL_INVOICES__BETALD
        };
        //
        table.setModel(new DefaultTableModel(null, headers));
        //
//        System.out.println("" + table.getFont());
        //
//        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        //
        //
        //
        JTable table_b = this.bim.jTable_invoiceB_faktura_artiklar;
        //
        String[] headers_b = {
            TABLE_INVOICE_ARTIKLES__ID, // hidden
            TABLE_INVOICE_ARTIKLES__FAKTURA_ID,
            TABLE_INVOICE_ARTIKLES__ARTIKEL_ID,
            TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN,
            TABLE_INVOICE_ARTIKLES__KOMMENT,
            TABLE_INVOICE_ARTIKLES__ANTAL,
            TABLE_INVOICE_ARTIKLES__ENHET,
            TABLE_INVOICE_ARTIKLES__PRIS,
            TABLE_INVOICE_ARTIKLES__RABATT,
            TABLE_INVOICE_ARTIKLES__RABATT_KR
        };
        //
        table_b.setModel(new DefaultTableModel(null, headers_b));
        //
    }

    protected void all_invoices_table_clicked(String fakturaId) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //
                fillFakturaArticlesTable(fakturaId);
                //
            }
        });
    }

    private void fillFakturaTable() {
        //
        JTable table = bim.jTable_invoiceB_alla_fakturor;
        //
        HelpA.clearAllRowsJTable(table);
        //
        String json = bim.getSELECT_kundId();
        //
        try {
            //
            String json_str_return = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR, json));
            //
            ArrayList<HashMap<String, String>> invoices = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            //
            for (HashMap<String, String> invoice_map : invoices) {
                addRowJtable_all_invoices(invoice_map, table);
            }
            //
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        if (GP_BUH.CUSTOMER_MODE) {
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__FAKTURA_ID);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__KUND_ID);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__KUND_NR);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__IS_INKL_MOMS);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__MOMS_SATS);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__FRAKT);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__EXP_AVG);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__MAKULERAD);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__VALUTA);
            //
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__KUND_ID);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__VAR_REF);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__ER_REF);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__BET_VILKOR);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__LEV_VILKOR);
            HelpA.hideColumnByName(table, TABLE_ALL_INVOICES__LEV_SATT);
        }
        //
        //

        //
    }

    private void addRowJtable_all_invoices(HashMap<String, String> map, JTable table) {
        //
        Object[] jtableRow = new Object[]{
            map.get(DB.BUH_FAKTURA__ID__), // hidden
            map.get(DB.BUH_FAKTURA_KUND__ID), // hidden
            map.get(DB.BUH_FAKTURA_KUND___KUNDNR), // hidden
            map.get(DB.BUH_FAKTURA__VAR_REFERENS), // hidden
            map.get(DB.BUH_FAKTURA__ER_REFERENS), // hidden
            map.get(DB.BUH_FAKTURA__BETAL_VILKOR), // hidden
            map.get(DB.BUH_FAKTURA__LEV_VILKOR), // hidden
            map.get(DB.BUH_FAKTURA__LEV_SATT), // hidden
            map.get(DB.BUH_FAKTURA__INKL_MOMS),
            map.get(DB.BUH_FAKTURA__MOMS_SATS),
            map.get(DB.BUH_FAKTURA__FRAKT),
            map.get(DB.BUH_FAKTURA__EXP_AVG),
            map.get(DB.BUH_FAKTURA__MAKULERAD),
            //
            map.get(DB.BUH_FAKTURA__FAKTURANR__),
            map.get(DB.BUH_FAKTURA__FAKTURATYP),
            map.get(DB.BUH_FAKTURA_KUND___NAMN),
            map.get(DB.BUH_FAKTURA__FAKTURA_DATUM),
            map.get(DB.BUH_FAKTURA__FORFALLO_DATUM),
            map.get(DB.BUH_FAKTURA__TOTAL_EXKL_MOMS__),
            map.get(DB.BUH_FAKTURA__TOTAL__),
            map.get(DB.BUH_FAKTURA__MOMS_TOTAL__),
            map.get(DB.BUH_FAKTURA__VALUTA),
            map.get(DB.BUH_FAKTURA__BETALD)
        };
        //
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(jtableRow);
        //
    }

    private void fillFakturaArticlesTable(String fakturaId) {
        //
        JTable table = bim.jTable_invoiceB_faktura_artiklar;
        //
        HelpA.clearAllRowsJTable(table);
        //
        if(fakturaId == null || fakturaId.isEmpty()){
            return;
        }
        //
        String json = bim.getSELECT(DB.BUH_F_ARTIKEL__FAKTURAID, fakturaId);
        //
        try {
            //
            String json_str_return = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM_GET_FAKTURA_ARTICLES, json));
            //
            //
            if (json_str_return.equals("empty")) { // this value='empty' is returned by PHP script
                return;
            }
            //
            //
            ArrayList<HashMap<String, String>> articles = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            bim.setArticlesActualInvoice(articles); //[2020-08-18]
            //
            for (HashMap<String, String> articles_map : articles) {
                addRowJtable_faktura_articles(articles_map, table);
            }
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        hideColumnsArticlesTable(table);
        //
    }

    protected void hideColumnsArticlesTable(JTable table) {
        //
        if (GP_BUH.CUSTOMER_MODE) {
            HelpA.hideColumnByName(table, TABLE_INVOICE_ARTIKLES__ID);
            HelpA.hideColumnByName(table, TABLE_INVOICE_ARTIKLES__ARTIKEL_ID);
            HelpA.hideColumnByName(table, TABLE_INVOICE_ARTIKLES__FAKTURA_ID);
            //
            HelpA.setColumnWidthByName(TABLE_INVOICE_ARTIKLES__KOMMENT, table, 0.35);
            HelpA.setColumnWidthByName(TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN, table, 0.15);
            //
        }
        //
    }

    private void addRowJtable_faktura_articles(HashMap<String, String> map, JTable table) {
        //
        Object[] jtableRow = new Object[]{
            map.get(DB.BUH_F_ARTIKEL__ID),
            map.get(DB.BUH_F_ARTIKEL__FAKTURAID),
            HelpA.getValHashMap(map.get(DB.BUH_FAKTURA_ARTIKEL___ID)),
            HelpA.getValHashMap(map.get(DB.BUH_FAKTURA_ARTIKEL___NAMN)),
            map.get(DB.BUH_F_ARTIKEL__KOMMENT),
            map.get(DB.BUH_F_ARTIKEL__ANTAL),
            JSon.getLongName(DB.STATIC__ENHET, map.get(DB.BUH_F_ARTIKEL__ENHET)),
            map.get(DB.BUH_F_ARTIKEL__PRIS),
            map.get(DB.BUH_F_ARTIKEL__RABATT),
            map.get(DB.BUH_F_ARTIKEL__RABATT_KR)
        };
        //
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(jtableRow);
        //
    }

    protected void deleteFaktura() {
        //
        String fakturaId = bim.getFakturaId();
        //
        deleteFakturaArticles(fakturaId);
        //
        deleteFaktura(fakturaId);
        //
        //
        refresh();
        //
    }

    private void deleteFaktura(String fakturaId) {
        //
        HashMap<String, String> map = bim.getDELETE(DB.BUH_FAKTURA__ID__, fakturaId, DB.TABLE__BUH_FAKTURA);
        //
        String json = JSon.hashMapToJSON(map);
        //
        executeDelete(json);
    }

    private void deleteFakturaArticles(String fakturaId) {
        //
        HashMap<String, String> map = bim.getDELETE(DB.BUH_F_ARTIKEL__FAKTURAID, fakturaId, DB.TABLE__BUH_F_ARTIKEL);
        //
        String json = JSon.hashMapToJSON(map);
        //
        executeDelete(json);
    }

    private void executeDelete(String json) {
        //
//        System.out.println("UPDATE json: " + json);
        //
        try {
            //
            HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_DELETE, json));
            //
        } catch (Exception ex) {
            Logger.getLogger(Faktura_Entry_Update.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String _get(String colNameJTable){
        JTable table = bim.jTable_invoiceB_alla_fakturor;
        return HelpA.getValueSelectedRow(table, colNameJTable);
    }
    
    private String _get_percent(String colNameJTable){
        JTable table = bim.jTable_invoiceB_alla_fakturor;
        double val = Double.parseDouble(HelpA.getValueSelectedRow(table, colNameJTable));
        int rst = (int) (val * 100);
        return ""+ rst;
    }
    
    private HashMap<String,String> getFakturaKundAddress(){
        //
        String fakturaKundId = _get(TABLE_ALL_INVOICES__KUND_ID);
        //
        String json = bim.getSELECT_fakturaKundId(fakturaKundId);
        //
        try {
            //
            String json_str_return = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM_GET_FAKTURA_KUND_ADDRESSES, json));
            //
            ArrayList<HashMap<String, String>> addresses = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            return addresses.get(0);
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        //
    }
    
    public void htmlFaktura_b() {
        //
//        BUH_INVOICE_MAIN bim = invoice.bim;
        //
        HashMap<String,String>map_a = new HashMap<>();
        HashMap<String,String>map_b = new HashMap<>();
        HashMap<String,String>map_c = new HashMap<>();
        HashMap<String,String>map_d = new HashMap<>();
        HashMap<String,String>map_e__lev_addr = getFakturaKundAddress();
        //
        map_a.put(HTMLPrint_A.T__FAKTURA_NR, _get(TABLE_ALL_INVOICES__FAKTURANR));
        map_a.put(HTMLPrint_A.T__KUND_NR, _get(TABLE_ALL_INVOICES__KUND_NR));
        map_a.put(HTMLPrint_A.T__FAKTURA_DATUM, _get(TABLE_ALL_INVOICES__DATUM));
        //
        map_b.put(HTMLPrint_A.T__FAKTURA_ER_REF, _get(TABLE_ALL_INVOICES__ER_REF));
        map_b.put(HTMLPrint_A.T__FAKTURA_ERT_ORDER_NR, ""); //**************************EMPTY
        map_b.put(HTMLPrint_A.T__FAKTURA_LEV_VILKOR, _get(TABLE_ALL_INVOICES__LEV_VILKOR));
        map_b.put(HTMLPrint_A.T__FAKTURA_LEV_SATT, _get(TABLE_ALL_INVOICES__LEV_SATT));
        map_b.put(HTMLPrint_A.T__FAKTURA_ERT_VAT_NR, ""); //**************************EMPTY
        //
        map_c.put(HTMLPrint_A.T__FAKTURA_VAR_REF, _get(TABLE_ALL_INVOICES__VAR_REF));
        map_c.put(HTMLPrint_A.T__FAKTURA_BETAL_VILKOR, _get(TABLE_ALL_INVOICES__BET_VILKOR));
        map_c.put(HTMLPrint_A.T__FAKTURA_FORFALLODATUM, _get(TABLE_ALL_INVOICES__FORFALLODATUM));
        map_c.put(HTMLPrint_A.T__FAKTURA_DROJMALSRANTA, ""); //**************************EMPTY
        map_c.put(HTMLPrint_A.T__FAKTURA_XXXXXXX, ""); //**************************EMPTY
        //
        map_d.put(HTMLPrint_A.T__FAKTURA_FRAKT, _get(TABLE_ALL_INVOICES__FRAKT));
        map_d.put(HTMLPrint_A.T__FAKTURA_EXP_AVG, _get(TABLE_ALL_INVOICES__EXP_AVG));
        map_d.put(HTMLPrint_A.T__FAKTURA_EXKL_MOMS, _get(TABLE_ALL_INVOICES__EXKL_MOMS));
        map_d.put(HTMLPrint_A.T__FAKTURA_MOMS_PERCENT, _get_percent(TABLE_ALL_INVOICES__MOMS_SATS));
        map_d.put(HTMLPrint_A.T__FAKTURA_MOMS_KR, _get(TABLE_ALL_INVOICES__MOMS));
        map_d.put(HTMLPrint_A.T__FAKTURA_ATT_BETALA, _get(TABLE_ALL_INVOICES__TOTAL_INKL_MOMS));
        //
        
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                HTMLPrint_A hTMLPrint_A = new HTMLPrint_A(
                        bim.getArticlesActualInvoice(),
                        map_a,
                        map_b,
                        map_c,
                        map_d,
                        map_e__lev_addr
                );
                hTMLPrint_A.setVisible(true);
            }
        });
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showTableInvert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initializeSaveIndicators() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getUnsaved(int nr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
