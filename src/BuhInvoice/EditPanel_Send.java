/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.LANG;
import MyObjectTable.OutPut;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert_;
import MyObjectTableInvert.TableInvert;
import forall.HelpA;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KOCMOC
 */
public class EditPanel_Send extends EditPanel_Inbet_ {

    private static final String TABLE_SEND__ID = "ID";
    private static final String TABLE_SEND__FAKTURA_ID = "F ID";
    private static final String TABLE_SEND__SEND_TYPE = "TYP";
    private static final String TABLE_SEND__SEND_OK = "STATUS";
    private static final String TABLE_SEND__DATUM = "DATUM";
    private static final String TABLE_SEND__ANNAT = "KOMMENT";
    private static final String TABLE_SEND__DONE_BY = "ANVÄNDARE";

    public EditPanel_Send(BUH_INVOICE_MAIN_ bim,boolean isKontantFaktura, String fakturaId, String fakturaNr, String fakturaKund) {
        super(bim,isKontantFaktura, fakturaId, fakturaNr, fakturaKund);
    }

    @Override
    protected void init() {
        //
        this.setTitle("Händelser");
        this.setIconImage(GP_BUH.getBuhInvoicePrimIcon());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setHeader();
//        jButton2_delete.setVisible(false);
        jButton1_commit.setToolTipText("Spara kommentar");
        //
        initBasicTab();
        //
        //
        fillJTableheader();
        refresh();
        //
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //
        if (e.getSource() == getJTable() && (e.getClickCount() == 1)) {
            //
            basic.showTableInvert();
            //
        }
        //
    }

    @Override
    protected void refresh() {
        //
        JTable table = getJTable();
        //
        fillJTable();
        //
        if(table.getRowCount() != 0){
            HelpA.markFirstRowJtable(table);
        }
        //
        basic.showTableInvert();
    }
    
    
    @Override
    protected void fillJTableheader() {
        //
        JTable table = getJTable();
        //
        String[] headers = {
            TABLE_SEND__ID,
            TABLE_SEND__FAKTURA_ID,
            TABLE_SEND__SEND_TYPE,
            TABLE_SEND__SEND_OK,
            TABLE_SEND__DATUM,
            TABLE_SEND__ANNAT,
            TABLE_SEND__DONE_BY
        };
        //
        table.setModel(new DefaultTableModel(null, headers));
        //
    }

    @Override
    protected void addRowJtable(HashMap<String, String> map, JTable table) {
        //
        Object[] jtableRow = new Object[]{
            map.get(DB.BUH_FAKTURA_SEND__ID), // hidden
            map.get(DB.BUH_FAKTURA_SEND__FAKTURA_ID), // hidden
            basic.getLongName(DB.STATIC__SEND_TYPES, map.get(DB.BUH_FAKTURA_SEND__SEND_TYPE)),
            basic.getLongName(DB.STATIC__SENT_STATUS, map.get(DB.BUH_FAKTURA_SEND__SEND_OK)),
            map.get(DB.BUH_FAKTURA_SEND__SEND_DATUM),
            map.get(DB.BUH_FAKTURA_SEND__ANNAT),
            map.get(DB.BUH_FAKTURA_SEND__DONE_BY)
        };
        //
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(jtableRow);
        //
    }

    @Override
    protected void fillJTable() {
        //
        JTable table = getJTable();
        //
        HelpA.clearAllRowsJTable(table);
        //
        String json = bim.getSELECT_fakturaId();
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM__GET_FAKTURA_SEND, json);
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
            Logger.getLogger(EditPanel_Inbet_.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        if (GP_BUH.CUSTOMER_MODE) {
            HelpA.hideColumnByName(table, TABLE_SEND__ID);
            HelpA.hideColumnByName(table, TABLE_SEND__FAKTURA_ID);
        }
        //
    }

    /**
     * Static because needed to be called from "outside"
     *
     * 
     * Status: Ej Skickad;0,Skickad;1,-;2
     * Send_type: "Faktura;0,Påminnelse;1,Makulerad;2,Utskriven;3"
     * 
     * @param fakturaId
     * @param status
     * @param type
     * @param faktura
     */
    protected static void insert(String fakturaId, String status, String type) {
        //
        HashMap<String, String> map = new HashMap<>();
        //
        map.put(DB.BUH_FAKTURA_SEND__KUND_ID, "777"); // [#KUND-ID-INSERT#]
        map.put(DB.BUH_FAKTURA_SEND__DONE_BY, GP_BUH.getChangedBy());
        map.put(DB.BUH_FAKTURA_SEND__FAKTURA_ID, fakturaId);
        //
        map.put(DB.BUH_FAKTURA_SEND__SEND_OK, status); // 0 is default
        //
        map.put(DB.BUH_FAKTURA_SEND__SEND_TYPE, type); // 0 is default
        //
        String json = JSon.hashMapToJSON(map);
        //
        try {
            //
            HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN, DB.PHP_FUNC_FAKTURA_SEND_TO_DB, json);
            //
        } catch (Exception ex) {
            Logger.getLogger(CustomersA_.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
    }

    @Override
    protected void jButton2ActionPerformed() {
         //
        if (HelpA.rowSelected(getJTable()) == false) {
            return;
        }
        //
        if (GP_BUH.confirmWarning(LANG.MSG_3_3) == false) {
            return;
        }
        //
        updateKomment(true);
    }

    
    
    
    
    @Override
    protected void jButton1ActionPerformed(){
        updateKomment(false);
    }
    
    private void updateKomment(boolean saveEmpty){
        //
        if (HelpA.rowSelected(getJTable()) == false) {
            return;
        }
        //
        if(basic.fieldsValidated(false)){
            //
            updateKomment_(saveEmpty);
            //
            refresh();
            //
        }
        //
    }
    
    private void updateKomment_(boolean saveEmpty){
        //
        JTable table = getJTable();
        //
        String send_id = HelpA.getValueSelectedRow(table, TABLE_SEND__ID);
        //
        HashMap<String, String> update_map = bim.getUPDATE(DB.BUH_FAKTURA_SEND__ID, send_id, DB.TABLE__BUH_FAKTURA_SEND);
        //
        HashMap<String, String> map = basic.tableInvertToHashMap(basic.TABLE_INVERT, DB.START_COLUMN);
        //
        if(saveEmpty){
            map.put(DB.BUH_FAKTURA_SEND__ANNAT, ""); // This is for delete purpose
        }
        //
        HashMap<String, String> final_map = JSon.joinHashMaps(map, update_map);
        //
        String json = JSon.hashMapToJSON(final_map);
        //
        HelpBuh.update(json);
        //
    }

    @Override
    public void initBasicTab() {
        //
        basic = new Basic_Buh(bim) {

            @Override
            protected void startUp() {

            }

            @Override
            protected boolean fieldsValidated(boolean insert) {
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
                return true;
            }

            /**
             * Show Empty purpose ...
             * @return 
             */
            @Override
            public RowDataInvert[] getConfigTableInvert() {
                //
                JTable table = getJTable();
                //
                if(table.getRowCount() == 0 || table.getSelectedRow() == -1){
                    return getConfigTableInvert_show_empty();
                }else{
                   return getConfigTableInvert_update();
                }
                //
            }
            
            public RowDataInvert[] getConfigTableInvert_show_empty() {
                //
                RowDataInvert send_type = new RowDataInvertB("", DB.BUH_FAKTURA_SEND__SEND_TYPE, TABLE_SEND__SEND_TYPE, "", true, true, false);
                RowDataInvert send_ok = new RowDataInvertB("", DB.BUH_FAKTURA_SEND__SEND_OK, TABLE_SEND__SEND_OK, "", true, true, false);
                RowDataInvert datum = new RowDataInvertB("", DB.BUH_FAKTURA_SEND__SEND_DATUM, TABLE_SEND__DATUM, "", true, true, false);
                RowDataInvert komment = new RowDataInvertB("", DB.BUH_FAKTURA_SEND__ANNAT, TABLE_SEND__ANNAT, "", true, true, false);
                //
                send_type.setUneditable();
                send_ok.setUneditable();
                datum.setUneditable();
                //
                RowDataInvert[] rows = {
                    send_type,
                    send_ok,
                    datum,
                    komment
                };
                //
                return rows;
            }
            
            public RowDataInvert[] getConfigTableInvert_update() {
                //
                JTable table = getJTable();
                //
                String send_type_ = HelpA.getValueSelectedRow(table, TABLE_SEND__SEND_TYPE);
                RowDataInvert send_type = new RowDataInvertB(send_type_, DB.BUH_FAKTURA_SEND__SEND_TYPE, TABLE_SEND__SEND_TYPE, "", true, true, false);
                //
                String send_ok_ = HelpA.getValueSelectedRow(table, TABLE_SEND__SEND_OK);
                RowDataInvert send_ok = new RowDataInvertB(send_ok_, DB.BUH_FAKTURA_SEND__SEND_OK, TABLE_SEND__SEND_OK, "", true, true, false);
                //
                String datum_ = HelpA.getValueSelectedRow(table, TABLE_SEND__DATUM);
                RowDataInvert datum = new RowDataInvertB(datum_, DB.BUH_FAKTURA_SEND__SEND_DATUM, TABLE_SEND__DATUM, "", true, true, false);
                //
                String komment_ = HelpA.getValueSelectedRow(table, TABLE_SEND__ANNAT);
                RowDataInvert komment = new RowDataInvertB(komment_, DB.BUH_FAKTURA_SEND__ANNAT, TABLE_SEND__ANNAT, "", true, true, false);
                //
                send_type.setDisabled();
                send_ok.setDisabled();
                datum.setDisabled();
                //
                send_type.setDontAquireTableInvertToHashMap();
                send_ok.setDontAquireTableInvertToHashMap();
                datum.setDontAquireTableInvertToHashMap();
                //
                if(komment_.isEmpty() == false){
                   komment.enableToolTipTextJTextField(); 
                }
                //
                RowDataInvert[] rows = {
                    send_type,
                    send_ok,
                    datum,
                    komment
                };
                //
                return rows;
            }

            @Override
            public void showTableInvert() {
                TableBuilderInvert_ tableBuilder = new TableBuilderInvert_(new OutPut(), null, getConfigTableInvert(), false, "");
                TABLE_INVERT = null;
                TABLE_INVERT = tableBuilder.buildTable_B(this);
                setMargin(TABLE_INVERT, 5, 0, 5, 0);
                showTableInvert(jPanel1);
                //
                addTableInvertRowListener(TABLE_INVERT, this);
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
               if (col_name.equals(DB.BUH_FAKTURA_SEND__ANNAT)) {
                    //
                    Validator.validateMaxInputLength(jli, 50);
                    //
                }
            }

        };
    }

}
