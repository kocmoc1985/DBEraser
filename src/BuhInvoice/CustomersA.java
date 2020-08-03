/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTable.OutPut;
import MyObjectTable.Table;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.JTextFieldInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import forall.HelpA;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
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
public class CustomersA extends Basic_Buh {

    protected Table TABLE_INVERT_2;

    public CustomersA(BUH_INVOICE_MAIN bim) {
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
                fillCustomertsTable();
            }
        });
        //
        
    }
    
    protected void customersTableClicked() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                fillCustomertsTable();
            }
        });
    }

    private void fillJTableheader() {
        //
        //
        JTable table = this.bim.jTable1_kunder;
        //
        String[] headers = {
            DB.BUH_FAKTURA_KUND__KUND_ID,
            DB.BUH_FAKTURA_KUND___KUNDNR,
            DB.BUH_FAKTURA_KUND___NAMN,
            DB.BUH_FAKTURA_KUND___ORGNR,
            DB.BUH_FAKTURA_KUND___VATNR,
            DB.BUH_FAKTURA_KUND___EMAIL,
            DB.BUH_FAKTURA_KUND___KATEGORI
        };
        //
        table.setModel(new DefaultTableModel(null, headers));
        //
//        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        //

    }

    private void fillCustomertsTable() {
        //
        JTable table = bim.jTable1_kunder;
        //
        String json = bim.getSELECT_kundId();
        //
        try {
            //
            String json_str_return = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM_GET_KUDNER_ALL_DATA, json));
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
//        HelpA.hideColumnByName(table, DB.BUH_FAKTURA_KUND__KUND_ID);
        //
    }

    private void addRowJtable(HashMap<String, String> map, JTable table) {
        //
        Object[] jtableRow = new Object[]{
            map.get(DB.BUH_FAKTURA_KUND__KUND_ID), // hidden
            map.get(DB.BUH_FAKTURA_KUND___KUNDNR),
            map.get(DB.BUH_FAKTURA_KUND___NAMN),
            map.get(DB.BUH_FAKTURA_KUND___ORGNR),
            map.get(DB.BUH_FAKTURA_KUND___VATNR),
            map.get(DB.BUH_FAKTURA_KUND___EMAIL),
            map.get(DB.BUH_FAKTURA_KUND___KATEGORI),};
        //
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(jtableRow);
        //
    }

    protected void jtableKunderRowChange() {
        //
        if (containsInvalidatedFields(TABLE_INVERT_2, 1, getConfigTableInvert_2())) {
            HelpA.showNotification(LANG.MSG_4);
            return;
        }
        // 

    }

    public void checkLatest() {
        //
        String json = bim.getLatest(DB.BUH_FAKTURA_KUND___KUNDNR, DB.TABLE__BUH_FAKTURA_KUND);
        //
        try {
            //
            String latest = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_LATEST, json));
            //
            System.out.println("LATEST: " + latest + "   *************************");
            //

        } catch (Exception ex) {
            Logger.getLogger(CustomersA.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertCustomer() {
        //
        HashMap<String, String> map = tableInvertToHashMap_updated_values_only(TABLE_INVERT, 1, getConfigTableInvert());
        //
        map.put(DB.BUH_FAKTURA_KUND__KUND_ID, getKundId());
        //
        String json = JSon.hashMapToJSON(map);
        //
        if (containsInvalidatedFields(TABLE_INVERT, 1, getConfigTableInvert())) {
            HelpA.showNotification(LANG.MSG_2);
            return;
        }
        //
        try {
            //
            HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_FAKTURA_KUND_TO_DB, json));
            //

        } catch (Exception ex) {
            Logger.getLogger(CustomersA.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        //
    }

    @Override
    public void showTableInvert() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert(), false, "buh_faktura_a");
        TABLE_INVERT = null;
        TABLE_INVERT = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT, 5, 0, 5, 0);
        showTableInvert(bim.jPanel5);
        //
        addTableInvertRowListener(TABLE_INVERT, this);
    }

    public void showTableInvert_2() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_2(), false, "buh_faktura_a");
        TABLE_INVERT = null;
        TABLE_INVERT = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT, 5, 0, 5, 0);
        showTableInvert(bim.jPanel5);
        //
        addTableInvertRowListener(TABLE_INVERT, this);

    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        RowDataInvert kundnr = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___KUNDNR, "KUNDNR", "", true, true, true);
        //
        RowDataInvert namn = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___NAMN, "KUND NAMN", "", true, true, true);
        //
        RowDataInvert orgnr = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___ORGNR, "ORGNR", "", true, true, false);
        //
        RowDataInvert vatnr = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___VATNR, "VATNR", "", true, true, false);
        //
        RowDataInvert email = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___EMAIL, "E-POST", "", true, true, false);
        //
        RowDataInvert kund_kategori = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, DB.STATIC__KUND_KATEGORI, DB.BUH_FAKTURA_KUND___KATEGORI, "KUND KATEGORI", "", true, true, false);
        kund_kategori.enableFixedValues();
        kund_kategori.setUneditable();
        //
        RowDataInvert[] rows = {
            kundnr,
            namn,
            orgnr,
            vatnr,
            email,
            kund_kategori
        };
        //
        return rows;
    }

    public RowDataInvert[] getConfigTableInvert_2() {
        //
        RowDataInvert kundnr = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___KUNDNR, "KUNDNR", "", true, true, true);
        //
        RowDataInvert namn = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___NAMN, "KUND NAMN", "", true, true, true);
        //
        RowDataInvert orgnr = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___ORGNR, "ORGNR", "", true, true, false);
        //
        RowDataInvert vatnr = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___VATNR, "VATNR", "", true, true, false);
        //
        RowDataInvert email = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___EMAIL, "E-POST", "", true, true, false);
        //
        RowDataInvert kund_kategori = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, DB.STATIC__KUND_KATEGORI, DB.BUH_FAKTURA_KUND___KATEGORI, "KUND KATEGORI", "", true, true, false);
        kund_kategori.enableFixedValues();
        kund_kategori.setUneditable();
        //
        RowDataInvert[] rows = {
            kundnr,
            namn,
            orgnr,
            vatnr,
            email,
            kund_kategori
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
        if (col_name.equals(DB.BUH_FAKTURA_KUND___EMAIL)) {
            //
            Validator.validateEmail(jli);
            //
        } else if (col_name.equals(DB.BUH_FAKTURA_KUND___ORGNR)) {
            //
            Validator.validateOrgnr(jli);
            //
            orgnr_additional(jli, ti);
            //
        } else if (col_name.equals(DB.BUH_FAKTURA_KUND___KUNDNR)) {
            //
            Validator.checkIfExistInDB(bim, jli, DB.BUH_FAKTURA_KUND___KUNDNR, DB.TABLE__BUH_FAKTURA_KUND);
            //
        } else if (col_name.equals(DB.BUH_FAKTURA_KUND___NAMN)) {
            //
            Validator.checkIfExistInDB(bim, jli, DB.BUH_FAKTURA_KUND___NAMN, DB.TABLE__BUH_FAKTURA_KUND);
            //
        }
        //
    }

    private void orgnr_additional(JLinkInvert jli, TableInvert ti) {
        //
        JTextFieldInvert jtfi = (JTextFieldInvert) jli;
        //
        String orgnr = getValueTableInvert(DB.BUH_FAKTURA_KUND___ORGNR, ti);
        //
        String txt = jtfi.getText();
        //
        if (txt.length() == 6) {
            jtfi.setText(orgnr + "-");
        } else if (txt.contains("--")) {
            txt = txt.replaceAll("--", "-");
            jtfi.setText(txt);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me, int column, int row, String tableName, TableInvert ti) {
        //
        super.mouseClicked(me, column, row, tableName, ti); //To change body of generated methods, choose Tools | Templates.
        //
        JLinkInvert jli = (JLinkInvert) me.getSource();
        //
        String col_name = ti.getCurrentColumnName(me.getSource());
        //
        if (col_name.equals(DB.BUH_FAKTURA_KUND___VATNR)) {
            //
            vatnrAuto(jli, ti);
            //
        }
    }

    private void vatnrAuto(JLinkInvert jli, TableInvert ti) {
        //
        String vatnr = "SE";
        //
        JTextFieldInvert jtfi = (JTextFieldInvert) jli;
        //
        if (jtfi.getText().isEmpty() == false) {
            return;
        }
        //
        String orgnr = getValueTableInvert(DB.BUH_FAKTURA_KUND___ORGNR, ti);
        //
        //
        if (getValidated(DB.BUH_FAKTURA_KUND___ORGNR, ti)) {
            //
            vatnr += orgnr.replace("-", "") + "01";
            //
            jtfi.setText(vatnr);
        }
        //
    }

}
