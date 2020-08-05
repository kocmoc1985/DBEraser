/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTable.OutPut;
import MyObjectTable.Table;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import forall.HelpA;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MCREMOTE
 */
public class ArticlesA extends Basic_Buh {

    protected Table TABLE_INVERT_2;
    
    public ArticlesA(BUH_INVOICE_MAIN bim) {
        super(bim);
    }

    @Override
    protected void startUp() {
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                showTableInvert();
                fillJTableheader();
                fillArtiklesJTable();
            }
        });
        //
    }
    
    private static final String TABLE_ARTICLES__ID = "ARTIKEL ID";
    private static final String TABLE_ARTICLES__KUND_ID = "KUND ID";
    private static final String TABLE_ARTICLES__LAGER = "LAGER";
    private static final String TABLE_ARTICLES__PRIS = "PRIS";
    private static final String TABLE_ARTICLES__INKOPS_PRIS = "INKÖPSPRIS";
    private static final String TABLE_ARTICLES__NAMN = "NAMN";
    private static final String TABLE_ARTICLES__KOMMENT = "KOMMENT";
    
    private void fillJTableheader() {
        //
        //
        JTable table = this.bim.jTable_articles;
        //
        String[] headers = {
            TABLE_ARTICLES__ID,
            TABLE_ARTICLES__KUND_ID,
            TABLE_ARTICLES__NAMN,
            TABLE_ARTICLES__PRIS,
            TABLE_ARTICLES__INKOPS_PRIS,
            TABLE_ARTICLES__LAGER,
            TABLE_ARTICLES__KOMMENT
        };
        //
        table.setModel(new DefaultTableModel(null, headers));
        //
//        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        //
    }
    
    private void fillArtiklesJTable() {
        //
        JTable table = bim.jTable_articles;
        //
        String json = bim.getSELECT_kundId();
        //
        try {
            //
            String json_str_return = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM_GET_KUND_ARTICLES_ALL_DATA, json));
            //
            ArrayList<HashMap<String, String>> invoices = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            //
            for (HashMap<String, String> invoice_map : invoices) {
                addRowJtable(invoice_map, table);
            }
            //
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
//        HelpA.hideColumnByName(table, DB.BUH_FAKTURA_ARTIKEL___ID);
        //
    }

    private void addRowJtable(HashMap<String, String> map, JTable table) {
        //
        Object[] jtableRow = new Object[]{
            map.get(DB.BUH_FAKTURA_ARTIKEL___ID), // hidden
            map.get(DB.BUH_FAKTURA_ARTIKEL___KUND_ID), // hidden
            map.get(DB.BUH_FAKTURA_ARTIKEL___NAMN),
            map.get(DB.BUH_FAKTURA_ARTIKEL___PRIS),
            map.get(DB.BUH_FAKTURA_ARTIKEL___INKOPS_PRIS),
            map.get(DB.BUH_FAKTURA_ARTIKEL___LAGER),
            map.get(DB.BUH_FAKTURA_ARTIKEL___KOMMENT)
        };
        //
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(jtableRow);
        //
    }

    public void insertArtikel() {
        //
        HashMap<String, String> map = tableInvertToHashMap(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert());
        //
        map.put(DB.BUH_FAKTURA_ARTIKEL___KUND_ID, getKundId());
        //
        String json = JSon.hashMapToJSON(map);
        //
        if (containsInvalidatedFields(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert())) {
            HelpA.showNotification(LANG.MSG_3);
            return;
        }
        //
        try {
            //
            HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_ARTIKEL_TO_DB, json));
            //
        } catch (Exception ex) {
            Logger.getLogger(CustomersA.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
    }

    @Override
    public void showTableInvert() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert(), false, "buh_faktura_a");
        TABLE_INVERT = null;
        TABLE_INVERT = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT, 5, 0, 5, 0);
        showTableInvert(bim.jPanel6);
        //
        addTableInvertRowListener(TABLE_INVERT, this);
    }
    
    
    public void showTableInvert_2() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_2(), false, "buh_faktura_a");
        TABLE_INVERT_2 = null;
        TABLE_INVERT_2 = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT_2, 5, 0, 5, 0);
        showTableInvert(bim.jPanel6, TABLE_INVERT_2);
        //
        addTableInvertRowListener(TABLE_INVERT_2, this);
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        RowDataInvert namn = new RowDataInvertB("", DB.BUH_FAKTURA_ARTIKEL___NAMN, "ARTIKEL NAMN", "", true, true, true);
        //
        RowDataInvert pris = new RowDataInvertB("", DB.BUH_FAKTURA_ARTIKEL___PRIS, "PRIS", "", true, true, false);
        //
        RowDataInvert inkopspris = new RowDataInvertB("", DB.BUH_FAKTURA_ARTIKEL___INKOPS_PRIS, "INKÖPS PRIS", "", true, true, false);
        //
        RowDataInvert lager = new RowDataInvertB("", DB.BUH_FAKTURA_ARTIKEL___LAGER, "LAGER", "", true, true, false);
        //
        RowDataInvert komment = new RowDataInvertB("", DB.BUH_FAKTURA_ARTIKEL___KOMMENT, "KOMMENT", "", true, true, false);
        //
        //
        RowDataInvert[] rows = {
            namn,
            pris,
            inkopspris,
            lager,
            komment
        };
        //
        return rows;
    }
    
    public RowDataInvert[] getConfigTableInvert_2() {
        //
        JTable table = bim.jTable_articles;
        //
        String namn_ = HelpA.getValueSelectedRow(table, TABLE_ARTICLES__NAMN);
        RowDataInvert namn = new RowDataInvertB(namn_, DB.BUH_FAKTURA_ARTIKEL___NAMN, TABLE_ARTICLES__NAMN, "", true, true, true);
        //
        String pris_ = HelpA.getValueSelectedRow(table, TABLE_ARTICLES__PRIS);
        RowDataInvert pris = new RowDataInvertB(pris_, DB.BUH_FAKTURA_ARTIKEL___PRIS, TABLE_ARTICLES__PRIS, "", true, true, false);
        //
        String inkopspris_ = HelpA.getValueSelectedRow(table, TABLE_ARTICLES__INKOPS_PRIS);
        RowDataInvert inkopspris = new RowDataInvertB(inkopspris_, DB.BUH_FAKTURA_ARTIKEL___INKOPS_PRIS, TABLE_ARTICLES__INKOPS_PRIS, "", true, true, false);
        //
        String lager_ = HelpA.getValueSelectedRow(table, TABLE_ARTICLES__LAGER);
        RowDataInvert lager = new RowDataInvertB(lager_, DB.BUH_FAKTURA_ARTIKEL___LAGER, TABLE_ARTICLES__LAGER, "", true, true, false);
        //
        String komment_ = HelpA.getValueSelectedRow(table, TABLE_ARTICLES__KOMMENT);
        RowDataInvert komment = new RowDataInvertB(komment_, DB.BUH_FAKTURA_ARTIKEL___KOMMENT, TABLE_ARTICLES__KOMMENT, "", true, true, false);
        //
        //
        RowDataInvert[] rows = {
            namn,
            pris,
            inkopspris,
            lager,
            komment
        };
        //
        return rows;
    }

    @Override
    public void keyReleasedForward(TableInvert ti, KeyEvent ke) {
        //
        super.keyReleasedForward(ti, ke); //To change body of generated methods, choose Tools | Templates.
        //
        JLinkInvert jli = (JLinkInvert) ke.getSource();
        //
        String col_name = ti.getCurrentColumnName(ke.getSource());
        //
        if (col_name.equals(DB.BUH_FAKTURA_ARTIKEL___PRIS)
                || col_name.equals(DB.BUH_FAKTURA_ARTIKEL___INKOPS_PRIS)
                || col_name.equals(DB.BUH_FAKTURA_ARTIKEL___LAGER)) {
            //
            Validator.validateDigitalInput(jli);
            //
        }else if (col_name.equals(DB.BUH_FAKTURA_ARTIKEL___NAMN)) {
            //
            Validator.checkIfExistInDB(bim, jli, DB.BUH_FAKTURA_ARTIKEL___NAMN, DB.TABLE__BUH_FAKTURA_ARTIKEL);
            //
        }
        //
    }
}
