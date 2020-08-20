/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import static BuhInvoice.Invoice.CURRENT_OPERATION_INSERT;
import MyObjectTable.OutPut;
import MyObjectTable.Table;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import forall.HelpA;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
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
public class ArticlesA extends Basic_Buh_ {

    protected Table TABLE_INVERT_2;
    private static final String TABLE_ARTICLES__ID = "ARTIKEL ID";
    private static final String TABLE_ARTICLES__KUND_ID = "KUND ID";
    private static final String TABLE_ARTICLES__LAGER = "LAGER";
    private static final String TABLE_ARTICLES__PRIS = "PRIS";
    private static final String TABLE_ARTICLES__INKOPS_PRIS = "INKÖPSPRIS";
    private static final String TABLE_ARTICLES__NAMN = "NAMN";
    private static final String TABLE_ARTICLES__KOMMENT = "KOMMENT";
    private static final String TABLE_ARTICLES__KATEGORI = "KATEGORI";
    //
    public static boolean CURRENT_OPERATION_INSERT = false;

    protected void SET_CURRENT_OPERATION_INSERT(boolean insert) {
        //
        CURRENT_OPERATION_INSERT = insert;
        //
        if (insert) {
            //
            bim.jLabel_Artikel_Insert_or_Update.setText(LANG.LBL_MSG_5);
            //
            bim.jButton_update_article.setEnabled(false);
            bim.jButton_delete_article.setEnabled(false);
            bim.jButton_add_article.setEnabled(true);
            //
        } else {
            //
            bim.jLabel_Artikel_Insert_or_Update.setText(LANG.LBL_MSG_6);
            //
            bim.jButton_update_article.setEnabled(true);
            bim.jButton_delete_article.setEnabled(true);
            bim.jButton_add_article.setEnabled(false);
            //
        }
    }

    public ArticlesA(BUH_INVOICE_MAIN bim) {
        super(bim);
    }

    @Override
    protected void startUp() {
        //
        fillJTableheader();
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //
                refresh();
                //
            }
        });
        //
    }

    private void refresh() {
        fillArtiklesJTable();
        HelpA.markFirstRowJtable(getTableArticles());
        bim.jTableArticles_clicked();
    }

    protected void createNew() {
        //
        showTableInvert();
        //
        refreshTableInvert();
        //
    }

    private JTable getTableArticles() {
        return this.bim.jTable_ArticlesA_articles;
    }

    private void fillJTableheader() {
        //
        //
        JTable table = getTableArticles();
        //
        String[] headers = {
            TABLE_ARTICLES__ID,
            TABLE_ARTICLES__KUND_ID,
            TABLE_ARTICLES__NAMN,
            TABLE_ARTICLES__PRIS,
            TABLE_ARTICLES__INKOPS_PRIS,
            TABLE_ARTICLES__LAGER,
            TABLE_ARTICLES__KOMMENT,
            TABLE_ARTICLES__KATEGORI
        };
        //
        table.setModel(new DefaultTableModel(null, headers));
        //
//        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        //
    }

    public void update() {
        //
        if (containsEmptyObligatoryFields(TABLE_INVERT_2, DB.START_COLUMN, getConfigTableInvert_2())) {
            HelpA.showNotification(LANG.MSG_2);
            return;
        }
        //
        if (containsInvalidatedFields(TABLE_INVERT_2, DB.START_COLUMN, getConfigTableInvert_2())) {
            HelpA.showNotification(LANG.MSG_1);
            return;
        }
        //
        String artikelId = HelpA.getValueSelectedRow(getTableArticles(), TABLE_ARTICLES__ID);
        //
        updateArticleData(artikelId);
        //
        refresh();
    }

    private void updateArticleData(String artikelId) {
        //
        HashMap<String, String> map = tableInvertToHashMap(TABLE_INVERT_2, DB.START_COLUMN, getConfigTableInvert_2());
        //
        HashMap<String, String> update_map = bim.getUPDATE(DB.BUH_FAKTURA_ARTIKEL___ID, artikelId, DB.TABLE__BUH_FAKTURA_ARTIKEL);
        //
        HashMap<String, String> final_map = JSon.joinHashMaps(map, update_map);
        //
        String json = JSon.hashMapToJSON(final_map);
        //
        HelpBuh.update(json);
        //
    }

    private void fillArtiklesJTable() {
        //
        JTable table = getTableArticles();
        //
        HelpA.clearAllRowsJTable(table);
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
        if (GP_BUH.CUSTOMER_MODE) {
            HelpA.hideColumnByName(table, TABLE_ARTICLES__ID);
            HelpA.hideColumnByName(table, TABLE_ARTICLES__KUND_ID);
        }
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
            map.get(DB.BUH_FAKTURA_ARTIKEL___KOMMENT),
            map.get(DB.BUH_FAKTURA_ARTIKEL___KATEGORI)
        };
        //
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(jtableRow);
        //
    }


    protected void delete() {
        //
        String str = bim.getForeignKeyBindings(
                getTableArticles(),
                TABLE_ARTICLES__ID,
                DB.BUH_FAKTURA_ARTIKEL___ID,
                DB.PHP_FUNC_PARAM_GET_INVOICES_USING_ARTICLE,
                DB.BUH_FAKTURA__FAKTURANR__
        );
        //
        if (str.isEmpty() == false && str.equals("null") == false) {
            if (HelpA.confirmWarning(LANG.MSG_DELETE_WARNING_ARTICLE(str)) == false) {
                return;
            }
        } else {
            if (HelpA.confirmWarning(LANG.MSG_3) == false) {
                return;
            }
        }
        //
        String artikelId = HelpA.getValueSelectedRow(getTableArticles(), TABLE_ARTICLES__ID);
        //
        delete__buh_f_artikel(artikelId);
        //
        delete__buh_faktura_artikel(artikelId);
        //
        refresh();
        //
    }

    private void delete__buh_f_artikel(String artikelId) {
        //
        HashMap<String, String> map = bim.getDELETE(DB.BUH_F_ARTIKEL__ARTIKELID, artikelId, DB.TABLE__BUH_F_ARTIKEL);
        //
        String json = JSon.hashMapToJSON(map);
        //
        executeDelete(json);
        //
    }

    private void delete__buh_faktura_artikel(String artikelId) {
        //
        HashMap<String, String> map = bim.getDELETE(DB.BUH_F_ARTIKEL__ARTIKELID, artikelId, DB.TABLE__BUH_FAKTURA_ARTIKEL);
        //
        String json = JSon.hashMapToJSON(map);
        //
        executeDelete(json);
        //
    }

    protected void insert() {
        //
        HashMap<String, String> map = tableInvertToHashMap(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert());
        //
        map.put(DB.BUH_FAKTURA_ARTIKEL___KUND_ID, getKundId());
        //
        String json = JSon.hashMapToJSON(map);
        //
        if (containsEmptyObligatoryFields(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert())) {
            HelpA.showNotification(LANG.MSG_2);
            return;
        }
        //
        if (containsInvalidatedFields(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert())) {
            HelpA.showNotification(LANG.MSG_1);
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
        refresh();
        //
    }

    /**
     * INSERT
     */
    @Override
    public void showTableInvert() {
        //
        SET_CURRENT_OPERATION_INSERT(true);
        //
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert(), false, "buh_faktura_a");
        TABLE_INVERT = null;
        TABLE_INVERT = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT, 5, 0, 5, 0);
        showTableInvert(bim.jPanel6);
        //
        addTableInvertRowListener(TABLE_INVERT, this);
    }

    /**
     * UPDATE
     */
    public void showTableInvert_2() {
        //
        SET_CURRENT_OPERATION_INSERT(false);
        //
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
        RowDataInvert pris = new RowDataInvertB("0", DB.BUH_FAKTURA_ARTIKEL___PRIS, "PRIS", "", false, true, false);
        //
        RowDataInvert inkopspris = new RowDataInvertB("0", DB.BUH_FAKTURA_ARTIKEL___INKOPS_PRIS, "INKÖPS PRIS", "", false, true, false);
        //
        RowDataInvert lager = new RowDataInvertB("0", DB.BUH_FAKTURA_ARTIKEL___LAGER, "LAGER", "", false, true, false);
        //
        RowDataInvert komment = new RowDataInvertB("", DB.BUH_FAKTURA_ARTIKEL___KOMMENT, "KOMMENT", "", true, true, false);
        //
        //
        RowDataInvert[] rows = {
            namn,
            pris,
//            inkopspris,
//            lager,
            komment
        };
        //
        return rows;
    }

    public RowDataInvert[] getConfigTableInvert_2() {
        //
        JTable table = getTableArticles();
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
//        String kategori_ = HelpA.getValueSelectedRow(table, TABLE_ARTICLES__KATEGORI);
//        RowDataInvert kategori = new RowDataInvertB(kategori_, DB.BUH_FAKTURA_ARTIKEL___KATEGORI, TABLE_ARTICLES__KATEGORI, "", true, true, false);
        //
        //
        String fixedComboValues_b = JSon._get_simple(HelpA.getValueSelectedRow(table, TABLE_ARTICLES__KATEGORI),
                DB.STATIC__KUND_AND_ARTICLE__KATEGORI
        );
        //
        RowDataInvert kategori = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_b, DB.BUH_FAKTURA_ARTIKEL___KATEGORI, TABLE_ARTICLES__KATEGORI, "", true, true, false);
        kategori.enableFixedValues();
        kategori.setUneditable();
        //
        RowDataInvert[] rows = {
            namn,
            pris,
//            inkopspris,
//            lager,
            komment,
            kategori
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
        } else if (col_name.equals(DB.BUH_FAKTURA_ARTIKEL___NAMN)) {
            //
//            Validator.checkIfExistInDB(bim, jli, DB.BUH_FAKTURA_ARTIKEL___NAMN, DB.TABLE__BUH_FAKTURA_ARTIKEL);
            Validator.checkIfExistInJTable(getTableArticles(), jli, TABLE_ARTICLES__NAMN);
            //
        }
        //
    }
    
    @Override
    public void mouseWheelForward(TableInvert ti, MouseWheelEvent e) {
        //
        super.mouseWheelForward(ti, e); //To change body of generated methods, choose Tools | Templates.
        //
        String col_name = ti.getCurrentColumnName(e.getSource());
        //
        if (col_name == null) {
            return;
        }
        //
        if (col_name.equals(DB.BUH_FAKTURA_ARTIKEL___PRIS)) {
            //
            mouseWheelNumberChange(e);
            //
        }
    }
    
}
