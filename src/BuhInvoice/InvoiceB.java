/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTableInvert.Basic;
import MyObjectTableInvert.RowDataInvert;
import forall.JSon;
import java.awt.Font;
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

    public InvoiceB(BUH_INVOICE_MAIN buh_invoice_main) {
        this.bim = buh_invoice_main;
        initOther();
    }

    private void initOther() {
        //
        fillJTableheader();
        //
        fillFakturaTable();
    }

    private void fillJTableheader() {
        //
        //
        JTable table = this.bim.jTable2_alla_fakturor;
        //        
        // "ID" -> "buh_faktura.fakturaId"
        //
        String[] headers = {"FAKTURA ID", "FAKTURANR", "FAKTURATYP",
            "KUNDNR", "KUND", "FAKTURADATUM", "FÖRFALLODATUM",
            "EXKL MOMS", "TOTAL", "VALUTA", "BETALD"};
        //
        table.setModel(new DefaultTableModel(null, headers));
        //
//        System.out.println("" + table.getFont());
        //
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        //
        
    }

    private void fillFakturaTable() {
        //
        String json = bim.getKundId_JSON_Entry();
        //
        try {
            //
            String json_str_return = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__$, json));
            //
            ArrayList<HashMap<String, String>> invoices = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            //
            for (HashMap<String, String> invoice_map : invoices) {
                addRowToJTable(invoice_map, bim.jTable2_alla_fakturor);
            }
            //
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        //
    }

    private void addRowToJTable(HashMap<String, String> map, JTable table) {
        //
        Object[] jtableRow = new Object[]{
            map.get(DB.BUH_FAKTURA__ID__),
            map.get(DB.BUH_FAKTURA__FAKTURANR__),
            map.get(DB.BUH_FAKTURA__FAKTURATYP),
            map.get(DB.BUH_FAKTURA_KUND___KUNDNR),
            map.get(DB.BUH_FAKTURA_KUND___NAMN),
            map.get(DB.BUH_FAKTURA__FAKTURA_DATUM),
            map.get(DB.BUH_FAKTURA__FORFALLO_DATUM),
            map.get(DB.BUH_FAKTURA__TOTAL_EXKL_MOMS__),
            map.get(DB.BUH_FAKTURA__TOTAL__),
            map.get(DB.BUH_FAKTURA__VALUTA),
            map.get(DB.BUH_FAKTURA__BETALD),};
        //
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(jtableRow);
        //
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
