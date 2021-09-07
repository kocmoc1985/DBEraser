/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import static BuhInvoice.GP_BUH._get;
import BuhInvoice.sec.JTableRowData;
import BuhInvoice.sec.LANG;
import forall.HelpA;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 *
 * @author MCREMOTE
 */
public class Faktura_Entry_Update extends Faktura_Entry {

    public Faktura_Entry_Update(Invoice_ invoice) {
        super(invoice);
    }

    /**
     * [2020-07-29] Here it's for the update purpose
     *
     * @return
     */
    @Override
    public void insertOrUpdate() {
        //
        setData();
        //
        String json = JSon.hashMapToJSON(this.fakturaMap);
        //
        HelpBuh.update(json);
        //
//        resetLists();
        //
    }

    private boolean ADDING_SAME_ARTICLE__UPDATE = false;
    private int ANTAL_ADDING_SAME__UPDATE = -1;

    @Override
    public void addArticleForJTable(JTable table) {
        //
        ADDING_SAME_ARTICLE__UPDATE = false;
        ANTAL_ADDING_SAME__UPDATE = 0;
        //
        int jcomboBoxParamToReturnManuallySpecified = 1; // returning the artikel "name" -> refers to "HelpA.ComboBoxObject"
        HashMap<String, String> map = invoice.tableInvertToHashMap(invoice.TABLE_INVERT_2, DB.START_COLUMN, jcomboBoxParamToReturnManuallySpecified);
        //
        HashMap<String, String> map_with_artikelId = invoice.tableInvertToHashMap(invoice.TABLE_INVERT_2, DB.START_COLUMN, 2); // OBS! *2*
        //
        // [#SAME-ARTICLE-ADDED-TWICE#]
        //
        String artikelNamn = _get(map, DB.BUH_F_ARTIKEL__ARTIKELID, true);
        String artikelPris = map.get(DB.BUH_F_ARTIKEL__PRIS);
        //
        JTableRowData jtrd = new JTableRowData(map_with_artikelId);
        jtrd.setArtikelNamn(artikelNamn);
        Object[] jtableRow;
        //
        if (this.articlesHashSet.contains(jtrd) == false) { // not the same article added
            //
            jtableRow = new Object[]{
                "",
                "",
                "",
                artikelNamn, // [#AUTOMATIC-COMMA-WITH-POINT-REPLACEMENT--ARTICLE-NAME#] -> here replacing of "¤" with "," is made
                _get(map, DB.BUH_F_ARTIKEL__KOMMENT, true),
                map.get(DB.BUH_F_ARTIKEL__ANTAL),
                map.get(DB.BUH_F_ARTIKEL__ENHET),
                artikelPris,
                map.get(DB.BUH_F_ARTIKEL__RABATT),
                map.get(DB.BUH_F_ARTIKEL__RABATT_KR),
                map.get(DB.BUH_F_ARTIKEL__MOMS_SATS).replaceAll("%", ""),
                map.get(DB.BUH_F_ARTIKEL__OMVANT_SKATT)
            };
            //
            if (jtrd.getArtikelNamn().equals("-") == false) {
                this.articlesHashSet.add(jtrd); // [2021-09-03] Bug fix, when you add an "-" article it should not be added to the hashset
            }
            //
            this.articlesList.add(map_with_artikelId);
            //
            HelpA.addRowToJTable(jtableRow, table);
            //
        } else { // Article which already exist added once more time
            //
            ADDING_SAME_ARTICLE__UPDATE = true;
            //
            int row = HelpA.getRowByValue_double_param(table,
                    InvoiceB.TABLE_INVOICE_ARTIKLES__ARTIKEL_NAMN, artikelNamn,
                    InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS, artikelPris);
            //
            int antal_actual = Integer.parseInt(HelpA.getValueGivenRow(table, row, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL));
            //
            int antal_new = Integer.parseInt(jtrd.getArtikelAntal());
            //
            GP_BUH.showNotification(LANG.MSG_30(antal_new));
            //
            ANTAL_ADDING_SAME__UPDATE = (antal_actual + antal_new);
            //
            HelpA.setValueGivenRow(table, row, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL, "" + ANTAL_ADDING_SAME__UPDATE);
            //
            addArticleForJTable_update_hashset(jtrd, ANTAL_ADDING_SAME__UPDATE);
            //
        }
        //
    }

    @Override
    public void addArticleForDB(Object other) {
        //[#SAME-ARTICLE-ADDED-TWICE#]
        if (ADDING_SAME_ARTICLE__UPDATE == false) {
            addArticleForDB_common();
        } else {
            addArticleForDB_adding_same();
        }
    }

    private void addArticleForDB_adding_same() {
        updateArticle(true, ANTAL_ADDING_SAME__UPDATE);
    }

    private void addArticleForDB_common() {
        //
        // As i see it today [2020-08-11], i should do it somehow like "updateArticle()"
        //
        // Also look at: Faktura_Entry_Insert.class -> articlesToHttpDB()
        //
        //
        InvoiceA_Update invoic = (InvoiceA_Update) invoice;
        //
        String fakturaId = invoice.bim.getFakturaId();
        //
        int jcomboBoxParamToReturnManuallySpecified = 2; // returning the "artikelId" -> refers to "HelpA.ComboBoxObject"
        //
        // Yes it's obligatory to use "tableInvertToHashMap_exclude_null()" 
        // If not used the empty checkbox returns "NULL" as value and when sending it to PHP/PDO
        // it causes "PDOException"
        HashMap<String, String> map = invoic.tableInvertToHashMap(invoic.TABLE_INVERT_2, DB.START_COLUMN, jcomboBoxParamToReturnManuallySpecified);
        map.put(DB.BUH_F_ARTIKEL__FAKTURAID, fakturaId);
        //
        map.put(DB.BUH_F_ARTIKEL__KUND_ID, "777"); // [#KUND-ID-INSERT#]
        //
        //[#SAME-ARTICLE-ADDED-TWICE#]
        //
        invoice.countFakturaTotal(getArticlesTable());
        //
        String json = JSon.hashMapToJSON(map);
        //
        try {
            //
            String query = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN, DB.PHP_FUNC_ARTICLES_TO_DB, json);
//            System.out.println("QUERY: " + query + "    *******************************************"); 
            //
        } catch (Exception ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
    }

    protected void updateArticle(boolean addingSameArticle, int antal) {
        //
        InvoiceA_Update invoic = (InvoiceA_Update) invoice;
        //
        JTable table = getArticlesTable();
        //
        HashMap<String, String> map = invoic.tableInvertToHashMap(invoic.TABLE_INVERT_2, DB.START_COLUMN);
        //
        HashMap<String, String> updateMap;
        //
        if (addingSameArticle) {
            //[#SAME-ARTICLE-ADDED-TWICE#]
            map.remove(DB.BUH_F_ARTIKEL__ANTAL);
            map.put(DB.BUH_F_ARTIKEL__ANTAL, "" + antal);
            //
            String artikelId = map.get(DB.BUH_F_ARTIKEL__ARTIKELID);
            String fakturaId = getFakturaId();
            String artikelPris = map.get(DB.BUH_F_ARTIKEL__PRIS);
            updateMap = invoic.bim.getUPDATE_trippleWhere(DB.BUH_F_ARTIKEL__ARTIKELID, artikelId, DB.BUH_FAKTURA__ID__, fakturaId, DB.BUH_F_ARTIKEL__PRIS, artikelPris, DB.TABLE__BUH_F_ARTIKEL);
//            updateMap = invoic.bim.getUPDATE_doubleWhere(DB.BUH_F_ARTIKEL__ARTIKELID, artikelId, DB.BUH_FAKTURA__ID__, fakturaId, DB.TABLE__BUH_F_ARTIKEL);
            //
        } else {
            //
            //OBS! The "getUpdate" here is more precise as it's using "buh_f_artikel.id" -> the "id" of the row
            String buh_f_artikel_id = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ID);
            updateMap = invoic.bim.getUPDATE(DB.BUH_F_ARTIKEL__ID, buh_f_artikel_id, DB.TABLE__BUH_F_ARTIKEL);
            //
        }
        //
        // OBS! Important by now [2020-07-29] i don't allow to change artikel, therefore removing "artikelId" entry
        map.remove(DB.BUH_F_ARTIKEL__ARTIKELID); //     
        //
        HashMap<String, String> final_map = JSon.joinHashMaps(map, updateMap);
        //
        String json = JSon.hashMapToJSON(final_map);
        //
        System.out.println("UPDATE json: " + json);
        //
        HelpBuh.update(json);
        //
        if (addingSameArticle == false) {
            HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__KOMMENT, _get(map, DB.BUH_F_ARTIKEL__KOMMENT, true));
            HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL, map.get(DB.BUH_F_ARTIKEL__ANTAL));
            HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ENHET, JSon.getLongName(DB.STATIC__ENHET, map.get(DB.BUH_F_ARTIKEL__ENHET)));
            HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS, map.get(DB.BUH_F_ARTIKEL__PRIS));
            HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT, map.get(DB.BUH_F_ARTIKEL__RABATT));
            HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT_KR, map.get(DB.BUH_F_ARTIKEL__RABATT_KR));
            HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__MOMS_SATS, map.get(DB.BUH_F_ARTIKEL__MOMS_SATS));
            HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__OMVAND_SKATT, JSon.getLongName(DB.STATIC__JA_NEJ, map.get(DB.BUH_F_ARTIKEL__OMVANT_SKATT)));
        }
        //
        invoic.countFakturaTotal(table);
        //
    }

    private void checkIfMakuleradAndReport(String fakturaId) {
        String makulerad = secMap.get(DB.BUH_FAKTURA__MAKULERAD);
        if (makulerad.equals("1")) {
            EditPanel_Send.insert(fakturaId, DB.STATIC__SENT_STATUS__MAKULERAD, DB.STATIC__SENT_TYPE_FAKTURA,null);
        }
    }

    @Override
    protected void setData() {
        //
//        JTable table = invoice.bim.jTable_invoiceB_alla_fakturor;
        InvoiceA_Update iu = (InvoiceA_Update) invoice;
        //
        this.mainMap = iu.tableInvertToHashMap(iu.TABLE_INVERT, DB.START_COLUMN);
        this.secMap = iu.tableInvertToHashMap(iu.TABLE_INVERT_3, DB.START_COLUMN);
        //
        //
        String fakturaId = getFakturaId();
        //
        //
        checkIfMakuleradAndReport(fakturaId);
        //
        //
        HashMap<String, String> update_map = invoice.bim.getUPDATE(DB.BUH_FAKTURA__ID__,
                fakturaId,
                DB.TABLE__BUH_FAKTURA
        );
        //
        HashMap<String, String> map_temp;
        //
        if (mainMap != null && secMap != null) { // [2020-08-12]
            //
            map_temp = HelpA.joinHashMaps(mainMap, secMap);
            //
            this.fakturaMap = JSon.joinHashMaps(map_temp, update_map);
            //
        } else { // The case below is for updating the "totals" only
            //
            map_temp = new HashMap<>();
            //
            this.fakturaMap = JSon.joinHashMaps(map_temp, update_map);
            //
        }
        //
        if (containsOmvandMoms()) {
            //[#INVOICE-HAS-OMVAND-SKATT#]
            this.fakturaMap.put(DB.BUH_FAKTURA__OMVANT_SKATT, "1");
        }
        //
        //
        this.fakturaMap.put(DB.BUH_FAKTURA__CHANGED_BY, GP_BUH.getChangedBy()); // [2020-10-28]
        //
        this.fakturaMap.put(DB.BUH_FAKTURA__TOTAL__, "" + invoice.getFakturaTotal());
        this.fakturaMap.put(DB.BUH_FAKTURA__RABATT_TOTAL, "" + invoice.getRabattTotal());
        this.fakturaMap.put(DB.BUH_FAKTURA__TOTAL_EXKL_MOMS__, "" + invoice.getTotalExklMoms());
        this.fakturaMap.put(DB.BUH_FAKTURA__MOMS_TOTAL__, "" + invoice.getMomsTotal());
        //
        //
//        System.out.println("-------------------------------------------------");
//        //
//        this.fakturaMap.entrySet().forEach((entry) -> {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            System.out.println(key + "=" + value);
//        });
//        //
//        System.out.println("-------------------------------------------------");
        //
//        JSon.hashMapToJSON(this.fakturaMap); // Temporary here
        //
    }

    private void updateArticle_(boolean addingSameArticle, int antal) {
        //
        InvoiceA_Update invoic = (InvoiceA_Update) invoice;
        //
        JTable table = getArticlesTable();
        //
        HashMap<String, String> map = invoic.tableInvertToHashMap(invoic.TABLE_INVERT_2, DB.START_COLUMN);
        //
        String buh_f_artikel_id;
        //
        if (addingSameArticle) {
            //[#SAME-ARTICLE-ADDED-TWICE#]
            map.remove(DB.BUH_F_ARTIKEL__ANTAL);
            map.put(DB.BUH_F_ARTIKEL__ANTAL, "" + antal);
        }
        //
        buh_f_artikel_id = HelpA.getValueSelectedRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ID);
        HashMap<String, String> updateMap = invoic.bim.getUPDATE(DB.BUH_F_ARTIKEL__ID, buh_f_artikel_id, DB.TABLE__BUH_F_ARTIKEL);
        //
        // OBS! Important by now [2020-07-29] i don't allow to change artikel, therefore removing "artikelId" entry
        map.remove(DB.BUH_F_ARTIKEL__ARTIKELID); //     
        //
        HashMap<String, String> final_map = JSon.joinHashMaps(map, updateMap);
        //
        String json = JSon.hashMapToJSON(final_map);
        //
        System.out.println("UPDATE json: " + json);
        //
        HelpBuh.update(json);
        //
        if (addingSameArticle == false) {
            HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__KOMMENT, _get(map, DB.BUH_F_ARTIKEL__KOMMENT, true));
            HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ANTAL, map.get(DB.BUH_F_ARTIKEL__ANTAL));
            HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__ENHET, JSon.getLongName(DB.STATIC__ENHET, map.get(DB.BUH_F_ARTIKEL__ENHET)));
            HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__PRIS, map.get(DB.BUH_F_ARTIKEL__PRIS));
            HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT, map.get(DB.BUH_F_ARTIKEL__RABATT));
            HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__RABATT_KR, map.get(DB.BUH_F_ARTIKEL__RABATT_KR));
            HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__MOMS_SATS, map.get(DB.BUH_F_ARTIKEL__MOMS_SATS));
            HelpA.setValueCurrentRow(table, InvoiceB.TABLE_INVOICE_ARTIKLES__OMVAND_SKATT, JSon.getLongName(DB.STATIC__JA_NEJ, map.get(DB.BUH_F_ARTIKEL__OMVANT_SKATT)));
        }
        //
        invoic.countFakturaTotal(table);
        //
    }

}
