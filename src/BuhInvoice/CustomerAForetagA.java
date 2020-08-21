/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import static BuhInvoice.CustomersA.TABLE_FAKTURA_KUNDER__FAKTURA_KUND_ID;
import MyObjectTable.Table;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.JTextFieldInvert;
import MyObjectTableInvert.TableInvert;
import forall.HelpA;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public abstract class CustomerAForetagA extends Basic_Buh_ {

    //
    protected Table TABLE_INVERT_2;
    protected Table TABLE_INVERT_3;
    protected Table TABLE_INVERT_4;
    //
    protected boolean CURRENT_OPERATION_INSERT = false;

    //
    public CustomerAForetagA(BUH_INVOICE_MAIN bim) {
        super(bim);
    }

    protected abstract void SET_CURRENT_OPERATION_INSERT(boolean insert);

    protected void refresh() {
        //
        fillMainTable();
        HelpA.markFirstRowJtable(getTableMain());
        //
        if (this instanceof CustomersA) {
            bim.jTableCustomersA_kunder_clicked();
        }else if (this instanceof ForetagA) {
            bim.jTableForetagA_ftg_table_clicked();
        }
        //
    }
    
    protected abstract void fillJTable_header_main();

    protected abstract void fillJTable_header_address();

    protected JTable getTableMain() {
        if (this instanceof CustomersA) {
            return bim.jTable_kunder;
        } else if (this instanceof ForetagA) {
            return bim.jTable_ftg;
        } else {
            return null;
        }
    }

    protected JTable getTableAdress() {
        if (this instanceof CustomersA) {
            return bim.jTable_kund_adresses;
        } else if (this instanceof ForetagA) {
            return bim.jTable_ftg_addr;
        } else {
            return null;
        }
    }

    protected void hideAdressTable() {
        if (this instanceof CustomersA) {
            bim.jScrollPane8.setVisible(false);
        } else if (this instanceof ForetagA) {
            bim.jScrollPane12.setVisible(false);
        }
    }

    protected void fillAddressTable() {
        //
        JTable table = getTableAdress();
        //
        HelpA.clearAllRowsJTable(table);
        //
        String idColName = "";
        String phpFunc = "";
        //
        if (this instanceof CustomersA) {
            idColName = CustomersA.TABLE_FAKTURA_KUNDER__FAKTURA_KUND_ID;
            phpFunc = DB.PHP_FUNC_PARAM_GET_KUND_ADDRESSES;
        } else if (this instanceof ForetagA) {
            idColName = ForetagA.TABLE_ARTICLES__ID;
            phpFunc = null; // **********************************************
        }
        //
        String fakturaKundId = HelpA.getValueSelectedRow(getTableMain(), idColName);
        //
        String json = bim.getSELECT_fakturaKundId(fakturaKundId);
        //
        try {
            //
            String json_str_return = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    phpFunc, json));
            //
            ArrayList<HashMap<String, String>> invoices = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            //
            for (HashMap<String, String> invoice_map : invoices) {
                addRowJtable_kund_adresses(invoice_map, table);
            }
            //
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
    }

    protected abstract void addRowJtable_kund_adresses(HashMap<String, String> map, JTable table);

    protected void fillMainTable() {
        //
        JTable table = getTableMain();
        //
        HelpA.clearAllRowsJTable(table);
        //
        String json = bim.getSELECT_kundId();
        //
        String phpFunc = "";
        //
        if (this instanceof CustomersA) {
            phpFunc = DB.PHP_FUNC_PARAM_GET_KUDNER_ALL_DATA;
        } else if (this instanceof ForetagA) {
            phpFunc = ""; // ****************************************************
        }
        //
        try {
            //
            String json_str_return = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    phpFunc, json));
            //
            ArrayList<HashMap<String, String>> invoices = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            //
            for (HashMap<String, String> invoice_map : invoices) {
                addRowJtable_main(invoice_map, table);
            }
            //
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        hideColumnsMainTable();
        //
    }

    protected abstract void hideColumnsMainTable();

    protected abstract void addRowJtable_main(HashMap<String, String> map, JTable table);

    protected abstract void update();
    
    protected abstract void updateMainData(String fakturaKundId);
    
    protected abstract void updateAddressData(String id);
    
    
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
    
    protected void vatnrAuto(JLinkInvert jli, TableInvert ti, String param) {
        //
        String vatnr = "SE";
        //
        JTextFieldInvert jtfi = (JTextFieldInvert) jli;
        //
        if (jtfi.getText().isEmpty() == false) {
            return;
        }
        //
        String orgnr = getValueTableInvert(param, ti);
        //
        if(orgnr.isEmpty()){
            return;
        }
        //
        if (getValidated(param, ti)) {
            //
            vatnr += orgnr.replace("-", "") + "01";
            //
            jtfi.setText(vatnr);
        }
        //
    }
   

}
