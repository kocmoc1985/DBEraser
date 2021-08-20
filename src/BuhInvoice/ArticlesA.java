/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.LANG;
import MyObjectTable.OutPut;
import MyObjectTable.Table;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import XYG_BARGRAPH.BARGraph;
import XYG_BARGRAPH.MyGraphXY_BG;
import XYG_BARGRAPH.MyPoint_BG;
import XYG_BASIC.MyGraphContainer;
import XYG_BASIC.MyPoint;
import XYG_STATS.BarGraphListener;
import XYG_STATS.BasicGraphListener;
import XYG_STATS.XyGraph_M;
import XY_BUH_INVOICE.MyGraphXY_BuhInvoice;
import XY_BUH_INVOICE.XyGraph_BuhInvoice;
import forall.HelpA;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import other.StringDouble;

/**
 *
 * @author MCREMOTE
 */
public class ArticlesA extends Basic_Buh implements BarGraphListener {

    protected Table TABLE_INVERT_2;
    private static final String TABLE_ARTICLES__ID = "ARTIKEL ID";
    private static final String TABLE_ARTICLES__KUND_ID = "KUND ID";
//    private static final String TABLE_ARTICLES__LAGER = "LAGER";
    private static final String TABLE_ARTICLES__PRIS = "PRIS (EXKL MOMS)";
//    private static final String TABLE_ARTICLES__INKOPS_PRIS = "INKÖPSPRIS";
    public static final String TABLE_ARTICLES__NAMN = "NAMN";
    private static final String TABLE_ARTICLES__ARTNR = "ARTIKELNR";
    private static final String TABLE_ARTICLES__KOMMENT_A = "KOMMENT A";
    private static final String TABLE_ARTICLES__KOMMENT_B = "KOMMENT B";
    private static final String TABLE_ARTICLES__KOMMENT_C = "KOMMENT C";
    private static final String TABLE_ARTICLES__KATEGORI = "KATEGORI";
    //
    private boolean CURRENT_OPERATION_INSERT = false;
    //
    public static String ARTIKEL_NAME__OR__NR__COLUMN = TABLE_ARTICLES__NAMN;
    //
    private final static String SERIE_NAME__BARGRAPH__TOTAL_PER_MONTH = "bar_graph_total_per_month";
    private final static String SERIE_NAME__BARGRAPH__AMMOUNT_PER_MONTH = "bar_graph_ammount_per_month";
    private XyGraph_BuhInvoice xygraph;

    protected void SET_CURRENT_OPERATION_INSERT(boolean insert) {
        //
        CURRENT_OPERATION_INSERT = insert;
        //
        if (insert) {
            //
            bim.jLabel_Artikel_Insert_or_Update.setText(LANG.LBL_MSG_5);
            //
            GP_BUH.setEnabled(bim.jButton_delete_article, false);
            //
        } else {
            //
            bim.jLabel_Artikel_Insert_or_Update.setText(LANG.LBL_MSG_6);
            //
            GP_BUH.setEnabled(bim.jButton_delete_article, true);
            //
        }
    }

    public ArticlesA(LAFakturering bim) {
        super(bim);
    }

    protected boolean getCurrentOperationInsert() {
        return CURRENT_OPERATION_INSERT;
    }

    public void setJComboParam_colName(boolean artikelNr) {
        //
        if (artikelNr) {
            ARTIKEL_NAME__OR__NR__COLUMN = TABLE_ARTICLES__ARTNR;
        } else {
            ARTIKEL_NAME__OR__NR__COLUMN = TABLE_ARTICLES__NAMN;
        }
        //
        fillSearchJCombo(ARTIKEL_NAME__OR__NR__COLUMN);
        //
    }

    public void jTableArticles_clicked() {
        //
        JTable table = getTableArticles();
        //
        if (getTableArticles().getRowCount() == 0) {
            showTableInvert();
            refreshTableInvert();
        } else {
            //
            String artikelId = HelpA.getValueSelectedRow(table, TABLE_ARTICLES__ID);
            //
            drawGraph_basic(artikelId, bim.jPanel__artcles_a__graph_panel_c, "invoices_which_include_article_curr_year", DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR_GIVEN_ARTICLE__CURR_YEAR);
            get_data__and_draw__bar_graph(artikelId);
            //
            showTableInvert_2();
            refreshTableInvert(TABLE_INVERT_2);
        }
    }

    @Override
    protected void startUp() {
        //
        fillJTableheader();
        //

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
        //
        fillArtiklesJTable();
        HelpA.markFirstRowJtable(getTableArticles());
        jTableArticles_clicked();
        //
        fillSearchJCombo(ARTIKEL_NAME__OR__NR__COLUMN); // 2021-05-24
        //
        //#THREAD# Causes trouble due to asynchron execution [2020-10-15]
//        Thread x = new Thread(() -> {
//            //
//            fillArtiklesJTable();
//            HelpA.markFirstRowJtable(getTableArticles());
//            //
//            java.awt.EventQueue.invokeLater(() -> {
//                bim.jTableArticles_clicked();
//            });
//            //
//        });
//        //
//        x.start();

    }

    protected void createNew() {
        //
        showTableInvert();
        //
        refreshTableInvert();
        //
    }

    protected JTable getTableArticles() {
        return this.bim.jTable_ArticlesA_articles;
    }

    private void fillSearchJCombo(String colName) {
        //
        Object[] objects = HelpA.getValuesOneColumnJTable(getTableArticles(), colName);
        //
        if (objects != null) {
            HelpA.fillComboBox(bim.jComboBox_articles_a__tab, objects, "");
        }
        //
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
            TABLE_ARTICLES__ARTNR,
            TABLE_ARTICLES__PRIS,
            //            TABLE_ARTICLES__INKOPS_PRIS,
            //            TABLE_ARTICLES__LAGER,
            TABLE_ARTICLES__KOMMENT_A,
            TABLE_ARTICLES__KOMMENT_B,
            TABLE_ARTICLES__KOMMENT_C,
            TABLE_ARTICLES__KATEGORI
        };
        //
        table.setModel(new DefaultTableModel(null, headers));
        //
    }

    @Override
    public boolean fieldsValidated(boolean insert) {
        //
        if (insert) {
            //
            if (containsEmptyObligatoryFields(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert())) {
                HelpA.showNotification(LANG.MSG_2);
                return false;
            }
            //
            if (containsInvalidatedFields(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert())) {
                HelpA.showNotification(LANG.MSG_1);
                return false;
            }
            //
        } else {
            //
            if (containsEmptyObligatoryFields(TABLE_INVERT_2, DB.START_COLUMN, getConfigTableInvert_2())) {
                HelpA.showNotification(LANG.MSG_2);
                return false;
            }
            //
            if (containsInvalidatedFields(TABLE_INVERT_2, DB.START_COLUMN, getConfigTableInvert_2())) {
                HelpA.showNotification(LANG.MSG_1);
                return false;
            }
            //
        }
        //
        return true;
        //
    }

    public void update() {
        //
        String artikelId = HelpA.getValueSelectedRow(getTableArticles(), TABLE_ARTICLES__ID);
        //
        updateArticleData(artikelId);
        //
        refresh();
    }

    private void updateArticleData(String artikelId) {
        //
        HashMap<String, String> map = tableInvertToHashMap(TABLE_INVERT_2, DB.START_COLUMN);
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
        HelpA.rowsorter_jtable_add_reset(table);
        //
        String json = bim.getSELECT_kundId();
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM_GET_KUND_ARTICLES_ALL_DATA, json);
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
            HelpA.hideColumnByName(table, TABLE_ARTICLES__KOMMENT_B);
            HelpA.hideColumnByName(table, TABLE_ARTICLES__KOMMENT_C);
//            HelpA.hideColumnByName(table, TABLE_ARTICLES__LAGER);
        }
        //
    }

    private void addRowJtable(HashMap<String, String> map, JTable table) {
        //
        Object[] jtableRow = new Object[]{
            map.get(DB.BUH_FAKTURA_ARTIKEL___ID), // hidden
            map.get(DB.BUH_FAKTURA_ARTIKEL___KUND_ID), // hidden
            map.get(DB.BUH_FAKTURA_ARTIKEL___NAMN),
            map.get(DB.BUH_FAKTURA_ARTIKEL___ARTNR),
            map.get(DB.BUH_FAKTURA_ARTIKEL___PRIS),
            //            map.get(DB.BUH_FAKTURA_ARTIKEL___INKOPS_PRIS),
            //            map.get(DB.BUH_FAKTURA_ARTIKEL___LAGER),
            map.get(DB.BUH_FAKTURA_ARTIKEL___KOMMENT),
            map.get(DB.BUH_FAKTURA_ARTIKEL___KOMMENT_B),
            map.get(DB.BUH_FAKTURA_ARTIKEL___KOMMENT_C),
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
            if (GP_BUH.confirmWarning(LANG.MSG_DELETE_WARNING_ARTICLE(str)) == false) {
                return;
            }
        } else {
            if (GP_BUH.confirmWarning(LANG.MSG_3) == false) {
                return;
            }
        }
        //
        String artikelId = HelpA.getValueSelectedRow(getTableArticles(), TABLE_ARTICLES__ID);
        //
        String json = bim.getSELECT(DB.BUH_F_ARTIKEL__ARTIKELID, artikelId);
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM_GET_INVOICES_GIVEN_ARTICLEID, json);
            //
            ArrayList<HashMap<String, String>> invoices = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            for (HashMap<String, String> map : invoices) {
                //
                String fakturaId = map.get(DB.BUH_FAKTURA__ID__);
                //
                bim.deleteFaktura(fakturaId); // ********** DELETE
                //
            }
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        delete__buh_faktura_artikel(artikelId); // ********** DELETE
        //
        refresh();
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
        HashMap<String, String> map = tableInvertToHashMap(TABLE_INVERT, DB.START_COLUMN);
        //
        map.put(DB.BUH_FAKTURA_ARTIKEL___KUND_ID, "777"); //[#KUND-ID-INSERT#]
        //
        String json = JSon.hashMapToJSON(map);
        //
        try {
            //
            HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_ARTIKEL_TO_DB, json);
            //
        } catch (Exception ex) {
            Logger.getLogger(CustomersA_.class.getName()).log(Level.SEVERE, null, ex);
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
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        RowDataInvert namn = new RowDataInvertB("", DB.BUH_FAKTURA_ARTIKEL___NAMN, "ARTIKEL NAMN", "", true, true, true);
        //
        RowDataInvert artnr = new RowDataInvertB("", DB.BUH_FAKTURA_ARTIKEL___ARTNR, TABLE_ARTICLES__ARTNR, "", true, true, false);
        //
        RowDataInvert pris = new RowDataInvertB("0", DB.BUH_FAKTURA_ARTIKEL___PRIS, TABLE_ARTICLES__PRIS, "", false, true, false);
        //
//        RowDataInvert inkopspris = new RowDataInvertB("0", DB.BUH_FAKTURA_ARTIKEL___INKOPS_PRIS, "INKÖPS PRIS", "", false, true, false);
        //
//        RowDataInvert lager = new RowDataInvertB("0", DB.BUH_FAKTURA_ARTIKEL___LAGER, "LAGER", "", false, true, false);
        //
        RowDataInvert komment = new RowDataInvertB("", DB.BUH_FAKTURA_ARTIKEL___KOMMENT, TABLE_ARTICLES__KOMMENT_A, "", true, true, false);
        //
        RowDataInvert komment_b = new RowDataInvertB("", DB.BUH_FAKTURA_ARTIKEL___KOMMENT_B, TABLE_ARTICLES__KOMMENT_B, "", true, true, false);
        //
        RowDataInvert komment_c = new RowDataInvertB("", DB.BUH_FAKTURA_ARTIKEL___KOMMENT_C, TABLE_ARTICLES__KOMMENT_C, "", true, true, false);
        //
        RowDataInvert kund_kategori = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, DB.STATIC__ARTICLE__KATEGORI, DB.BUH_FAKTURA_ARTIKEL___KATEGORI, TABLE_ARTICLES__KATEGORI, "", true, true, false);
        kund_kategori.enableFixedValues();
        kund_kategori.setUneditable();
        //
        RowDataInvert[] rows = {
            namn,
            artnr,
            pris,
            //            inkopspris,
            //            lager,
            komment,
            komment_b,
            komment_c,
            kund_kategori
        };
        //
        return rows;
    }

    /**
     * [UPDATE]
     *
     * @return
     */
    public RowDataInvert[] getConfigTableInvert_2() {
        //
        JTable table = getTableArticles();
        //
        String namn_ = HelpA.getValueSelectedRow(table, TABLE_ARTICLES__NAMN);
        RowDataInvert namn = new RowDataInvertB(namn_, DB.BUH_FAKTURA_ARTIKEL___NAMN, TABLE_ARTICLES__NAMN, "", true, true, true);
        //
        String artnr_ = HelpA.getValueSelectedRow(table, TABLE_ARTICLES__ARTNR);
        RowDataInvert artnr = new RowDataInvertB(artnr_, DB.BUH_FAKTURA_ARTIKEL___ARTNR, TABLE_ARTICLES__ARTNR, "", true, true, false);
        //
        String pris_ = HelpA.getValueSelectedRow(table, TABLE_ARTICLES__PRIS);
        RowDataInvert pris = new RowDataInvertB(pris_, DB.BUH_FAKTURA_ARTIKEL___PRIS, TABLE_ARTICLES__PRIS, "", true, true, false);
        //
//        String inkopspris_ = HelpA.getValueSelectedRow(table, TABLE_ARTICLES__INKOPS_PRIS);
//        RowDataInvert inkopspris = new RowDataInvertB(inkopspris_, DB.BUH_FAKTURA_ARTIKEL___INKOPS_PRIS, TABLE_ARTICLES__INKOPS_PRIS, "", true, true, false);
        //
//        String lager_ = HelpA.getValueSelectedRow(table, TABLE_ARTICLES__LAGER);
//        RowDataInvert lager = new RowDataInvertB(lager_, DB.BUH_FAKTURA_ARTIKEL___LAGER, TABLE_ARTICLES__LAGER, "", true, true, false);
        //
        String komment_ = HelpA.getValueSelectedRow(table, TABLE_ARTICLES__KOMMENT_A);
        RowDataInvert komment = new RowDataInvertB(komment_, DB.BUH_FAKTURA_ARTIKEL___KOMMENT, TABLE_ARTICLES__KOMMENT_A, "", true, true, false);
        //
        String komment_b_ = HelpA.getValueSelectedRow(table, TABLE_ARTICLES__KOMMENT_B);
        RowDataInvert komment_b = new RowDataInvertB(komment_b_, DB.BUH_FAKTURA_ARTIKEL___KOMMENT_B, TABLE_ARTICLES__KOMMENT_B, "", true, true, false);
        //
        String komment_c_ = HelpA.getValueSelectedRow(table, TABLE_ARTICLES__KOMMENT_C);
        RowDataInvert komment_c = new RowDataInvertB(komment_c_, DB.BUH_FAKTURA_ARTIKEL___KOMMENT_C, TABLE_ARTICLES__KOMMENT_C, "", true, true, false);
        //
//        String kategori_ = HelpA.getValueSelectedRow(table, TABLE_ARTICLES__KATEGORI);
//        RowDataInvert kategori = new RowDataInvertB(kategori_, DB.BUH_FAKTURA_ARTIKEL___KATEGORI, TABLE_ARTICLES__KATEGORI, "", true, true, false);
        //
        //
        String fixedComboValues_b = JSon._get_simple(HelpA.getValueSelectedRow(table, TABLE_ARTICLES__KATEGORI),
                DB.STATIC__ARTICLE__KATEGORI
        );
        //
        RowDataInvert kategori = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_b, DB.BUH_FAKTURA_ARTIKEL___KATEGORI, TABLE_ARTICLES__KATEGORI, "", true, true, false);
        kategori.enableFixedValues();
        kategori.setUneditable();
        //
        RowDataInvert[] rows = {
            namn,
            artnr,
            pris,
            //            inkopspris,
            //            lager,
            komment,
            komment_b,
            komment_c,
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
            if (Validator.validateMaxInputLength(jli, 50)) {
                Validator.checkIfExistInJTable(getTableArticles(), jli, TABLE_ARTICLES__NAMN);
            }
            //
        } else if (col_name.equals(DB.BUH_FAKTURA_ARTIKEL___ARTNR)) {
            //
            if (Validator.validateMaxInputLength(jli, 50)) {
                Validator.checkIfExistInJTable(getTableArticles(), jli, TABLE_ARTICLES__ARTNR);
            }
            //
        } else if (col_name.equals(DB.BUH_FAKTURA_ARTIKEL___KOMMENT)
                || col_name.equals(DB.BUH_FAKTURA_ARTIKEL___KOMMENT_B)
                || col_name.equals(DB.BUH_FAKTURA_ARTIKEL___KOMMENT_C)) {
            //
            Validator.validateMaxInputLength(jli, 200);
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

    //==========================================================================
    //==========================================================================
    //==========================================================================
    private void drawGraph_basic(String artikelId, JPanel container, String name, String phpScript) {
        //
        new Thread(() -> {
            //
            container.removeAll();
            container.revalidate();
            container.repaint();
            //
            String dateNow = GP_BUH.getDate_yyyy_MM_dd();
            String dateFormat = GP_BUH.DATE_FORMAT_BASIC;
            //
            XyGraph_BuhInvoice xygm = new XyGraph_BuhInvoice(name, DB.BUH_FAKTURA__TOTAL_EXKL_MOMS__, new MyGraphXY_BuhInvoice(bim), MyGraphContainer.DISPLAY_MODE_FULL_SCREEN, dateNow, dateFormat);
            //
            if (phpScript.equals(DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR_GIVEN_ARTICLE__CURR_YEAR)) {
                xygraph = xygm;
            }
            //
            System.out.println("Thread:" + Thread.currentThread().getName());
            //
            container.add(xygm.getGraph());
            //
            Thread x = new Thread(new Thread_A_A(artikelId, phpScript, xygm));
            x.setName("Thread_A_A");
            x.start();
            //
        }).start();
    }

    class Thread_A_A implements Runnable {

        private final String artikelId;
        private final String phpScript;
        private final XyGraph_BuhInvoice xghm;

        public Thread_A_A(String artikelId, String phpScript, XyGraph_BuhInvoice xghm) {
            this.artikelId = artikelId;
            this.phpScript = phpScript;
            System.out.println("CCC:" + this.phpScript);
            this.xghm = xghm;
        }

        @Override
        public void run() {
            getData_and_add_to_graph();
        }

        private void getData_and_add_to_graph() {
            //
            String json = bim.getSELECT_doubleWhere(DB.BUH_F_ARTIKEL__ARTIKELID, artikelId, DB.BUH_F_ARTIKEL__KUND_ID, "777");
            //
            String json_str_return = "";
            //
            try {
                json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN, phpScript, json);
            } catch (Exception ex) {
                Logger.getLogger(StatistikTab.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
            ArrayList<HashMap<String, String>> invoices = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            if (invoices == null || invoices.isEmpty()) {
                this.xghm.getGraph().removeAll();
                this.xghm.getGraph().revalidate();
                this.xghm.getGraph().repaint();
                return;
            }
            //
            // OBS! HERE Below it's done with AWT-Thread
            java.awt.EventQueue.invokeLater(() -> {
                System.out.println("Thread addData: " + Thread.currentThread());
                this.xghm.addData(invoices, new String[]{DB.BUH_FAKTURA__FAKTURA_DATUM, DB.BUH_FAKTURA__FORFALLO_DATUM});
            });
            //
        }

    }

    private void get_data__and_draw__bar_graph(String artikelId) {
        //
        new Thread(() -> {
            //
            String json = bim.getSELECT_doubleWhere(DB.BUH_F_ARTIKEL__ARTIKELID, artikelId, DB.BUH_F_ARTIKEL__KUND_ID, "777");
            //
            try {
                //
                String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                        DB.PHP_FUNC_PARAM_GET_ARTICLE_TOTALS_CURR_YEAR, json);
                //
                ArrayList<HashMap<String, String>> totals = JSon.phpJsonResponseToHashMap(json_str_return);
                //
                java.awt.EventQueue.invokeLater(() -> {
                    drawGraph_bargraph(totals, bim.jPanel__artcles_a__graph_panel_a, bim.jPanel__artcles_a__graph_panel_b, SERIE_NAME__BARGRAPH__TOTAL_PER_MONTH, SERIE_NAME__BARGRAPH__AMMOUNT_PER_MONTH);
                });
                //
            } catch (Exception ex) {
                Logger.getLogger(ArticlesA.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        //
    }

    private void drawGraph_bargraph(ArrayList<HashMap<String, String>> totals, JPanel containerTotalPerMonth, JPanel containerAmmountPerMonth, String name_a, String name_b) {
        //
        containerTotalPerMonth.removeAll();
        containerAmmountPerMonth.removeAll();
        containerTotalPerMonth.revalidate();
        containerTotalPerMonth.repaint();
        containerAmmountPerMonth.revalidate();
        containerAmmountPerMonth.repaint();
        //
        if (totals == null || totals.isEmpty()) {
            return;
        }
        //====================================================
        BasicGraphListener gg__total_per_month;
        MyGraphXY_BG mgxyhm;
        //
        final XyGraph_M xygraph = new XyGraph_M(name_a, MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
        //
        mgxyhm = new MyGraphXY_BG("Total", ":-");
        mgxyhm.addBarGraphListener(this);
        gg__total_per_month = new BARGraph(name_a, mgxyhm, MyGraphContainer.DISPLAY_MODE_FULL_SCREEN); // MyGraphContainer.DISPLAY_MODE_FOOT_DISABLED
        //
        xygraph.setGraph(gg__total_per_month);
        containerTotalPerMonth.add(gg__total_per_month.getGraph()); //***** //[#WAIT-FOR-HEIGHT#]
        //
        //====================================================
        //
        BasicGraphListener gg__ammount_per_month;
        MyGraphXY_BG mgxyhm_b;
        //
        final XyGraph_M xygraph_b = new XyGraph_M(name_b, MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
        //
        mgxyhm_b = new MyGraphXY_BG("Antal", " st");
        mgxyhm_b.addBarGraphListener(this);
        gg__ammount_per_month = new BARGraph(name_b, mgxyhm_b, MyGraphContainer.DISPLAY_MODE_FULL_SCREEN); // MyGraphContainer.DISPLAY_MODE_FOOT_DISABLED
        //
        xygraph_b.setGraph(gg__ammount_per_month);
        containerAmmountPerMonth.add(gg__ammount_per_month.getGraph()); //***** //[#WAIT-FOR-HEIGHT#]
        //
        //====================================================
        //
        Thread x = new Thread(new Thread_B_B(totals, gg__total_per_month, gg__ammount_per_month));
        x.setName("Thread_B_B");
        x.start();
        //
    }

    class Thread_B_B implements Runnable {

        private final ArrayList<HashMap<String, String>> totals;
        private final BasicGraphListener gg__total_per_month;
        private final BasicGraphListener gg__ammount_per_month;

        public Thread_B_B(ArrayList<HashMap<String, String>> totals, BasicGraphListener gg__total_per_month, BasicGraphListener gg__ammount_per_month) {
            this.totals = totals;
            this.gg__total_per_month = gg__total_per_month;
            this.gg__ammount_per_month = gg__ammount_per_month;
        }

        @Override
        public void run() {
            getData_and_add_to_graph();
        }

        private void getData_and_add_to_graph() {
            //
            final LinkedHashMap<String, Double> month_sum_map = new LinkedHashMap<>();
            final LinkedHashMap<String, Double> month_ammount_map = new LinkedHashMap<>();
            //
            //
            for (HashMap<String, String> map : totals) {
                //
                String fakturadatum = map.get(DB.BUH_FAKTURA__FAKTURA_DATUM);
                String total = map.get("total"); // the column name is the "as" column which means it's not present in the table but defined in the select statement
                String antal = map.get(DB.BUH_F_ARTIKEL__ANTAL);
                //
                //
                String[] arr = fakturadatum.split("-");
                String faktura_datum_short = arr[0] + "-" + arr[1];
                //
                HelpA.increase_map_value_with_x(faktura_datum_short, Double.parseDouble(total), month_sum_map);
                //
                HelpA.increase_map_value_with_x(faktura_datum_short, Double.parseDouble(antal), month_ammount_map);
                //
            }
            //
            //====================================================
            //
            Set set = month_sum_map.keySet();
            Iterator it = set.iterator();
            //
            final ArrayList<StringDouble> barGraphValuesList_total = new ArrayList<>();
            //
            while (it.hasNext()) {
                String key = (String) it.next();
                Double value = month_sum_map.get(key);
//                System.out.println("key = " + key + "  value = " + value);
                barGraphValuesList_total.add(new StringDouble(key, value));
            }
            //
            if (barGraphValuesList_total.size() < 12) {
                //
                while (barGraphValuesList_total.size() < 12) {
                    barGraphValuesList_total.add(new StringDouble("", 0));
                }
                //
            }
            //
            Collections.reverse(barGraphValuesList_total);
            //
            BARGraph barg_a = (BARGraph) gg__total_per_month;
            //
            java.awt.EventQueue.invokeLater(() -> {
                barg_a.addData(barGraphValuesList_total);
            });
            //
            //====================================================
            //
            Set set_b = month_ammount_map.keySet();
            Iterator it_b = set_b.iterator();
            //
            final ArrayList<StringDouble> barGraphValuesList_ammount = new ArrayList<>();
            //
            while (it_b.hasNext()) {
                String key = (String) it_b.next();
                Double value = month_ammount_map.get(key);
                System.out.println("key = " + key + "  value = " + value);
                barGraphValuesList_ammount.add(new StringDouble(key, value));
            }
            //
            if (barGraphValuesList_ammount.size() < 12) {
                //
                while (barGraphValuesList_ammount.size() < 12) {
                    barGraphValuesList_ammount.add(new StringDouble("", 0));
                }
                //
            }
            //
            Collections.reverse(barGraphValuesList_ammount);
            //
            BARGraph barg_b = (BARGraph) gg__ammount_per_month;
            //
            java.awt.EventQueue.invokeLater(() -> {
                barg_b.addData(barGraphValuesList_ammount);
            });
            //
            //====================================================
            //
        }
    }

    @Override
    public void barGraphHoverEvent(MouseEvent e, MyPoint mp) {
        if (e.getSource() instanceof MyPoint_BG) {
            //
            MyPoint_BG mpbg = (MyPoint_BG) e.getSource();
            //
            BARGraph bg = mpbg.getBarGraph();
            //
            bg.highlight_a(mpbg, xygraph, DB.BUH_FAKTURA__FAKTURA_DATUM);
            //
//            highlight_a(mpbg, xygraph);
            //
        }
    }

    @Override
    public void barGraphHoverOutEvent(MouseEvent me) {
        xygraph.getSerie().resetPointsColorAndForm();
    }

}
