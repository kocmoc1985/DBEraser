/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTable.OutPut;
import MyObjectTable.Table;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert;
import forall.HelpA;
import java.util.HashMap;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KOCMOC
 */
public class ForetagA extends CustomerAForetagA {

    protected static final String TABLE__ID = "KUND ID";
    private static final String TABLE__NAMN = "NAMN";
    private static final String TABLE__ORGNR = "ORGNR";
    private static final String TABLE__VATNR = "VATNR";
    private static final String TABLE__EPOST = "E-POST";
    private static final String TABLE__BANK_GIRO = "BG";
    private static final String TABLE__POST_GIRO = "PG";
    private static final String TABLE__KONTO = "KONTO";
    private static final String TABLE__IBAN = "IBAN";
    private static final String TABLE__SWISH = "SWISH";

    public ForetagA(BUH_INVOICE_MAIN bim) {
        super(bim);
    }

    @Override
    protected void SET_CURRENT_OPERATION_INSERT(boolean insert) {

    }
    
    
    @Override
     protected void fillJTable_header_main() {
        //
        //
        JTable table = getTableMain();
        //
        String[] headers = {
            TABLE__ID,
            TABLE__NAMN,
            TABLE__ORGNR,
            TABLE__VATNR,
            TABLE__EPOST,
            TABLE__BANK_GIRO,
            TABLE__POST_GIRO,
            TABLE__KONTO,
            TABLE__IBAN,
            TABLE__SWISH
        };
        //
        table.setModel(new DefaultTableModel(null, headers));
        //
    }
   

    /**
     * INSERT
     */
    @Override
    public void showTableInvert() {
//        //
//        SET_CURRENT_OPERATION_INSERT(true);
//        //
//        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert(), false, "");
//        TABLE_INVERT = null;
//        TABLE_INVERT = tableBuilder.buildTable_B(this);
//        setMargin(TABLE_INVERT, 5, 0, 5, 0);
//        showTableInvert(bim.jPanel16); // ************************ CHANGE
//        //
//        addTableInvertRowListener(TABLE_INVERT, this);
    }
    
    public void showTableInvert_2() {
        //
        SET_CURRENT_OPERATION_INSERT(false);
        //
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_2(), false, "");
        TABLE_INVERT_2 = null;
        TABLE_INVERT_2 = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT_2, 5, 0, 5, 0);
        showTableInvert(bim.jPanel16); // ************************ CHANGE
        //
        addTableInvertRowListener(TABLE_INVERT_2, this);
    }
    
    public void showTableInvert_3() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_3(), false, "");
        TABLE_INVERT_3 = null;
        TABLE_INVERT_3 = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT_3, 5, 0, 5, 0);
        showTableInvert(bim.jPanel17); // ************************ CHANGE
        //
        addTableInvertRowListener(TABLE_INVERT_3, this);
    }
    
//    public void showTableInvert_4() {
//        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_4(), false, "");
//        TABLE_INVERT_4 = null;
//        TABLE_INVERT_4 = tableBuilder.buildTable_B(this);
//        setMargin(TABLE_INVERT_4, 5, 0, 5, 0);
//        showTableInvert(bim.jPanel17); // ************************ CHANGE
//        //
//        addTableInvertRowListener(TABLE_INVERT_4, this);
//    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {

        return null;
    }

    public RowDataInvert[] getConfigTableInvert_2() {
        //
        JTable table = getTableMain();
        //
        String namn_ = HelpA.getValueSelectedRow(table, TABLE__NAMN);
        RowDataInvert namn = new RowDataInvertB(namn_, DB.BUH_KUND__NAMN, TABLE__NAMN , "", true, true, true);
        //
        String orgnr_ = HelpA.getValueSelectedRow(table, TABLE__ORGNR);
        RowDataInvert orgnr = new RowDataInvertB(orgnr_, DB.BUH_KUND__ORGNR, TABLE__ORGNR , "", true, true, false);
        //
        String vatnr_ = HelpA.getValueSelectedRow(table, TABLE__VATNR);
        RowDataInvert vatnr = new RowDataInvertB(vatnr_, DB.BUH_KUND__VATNR, TABLE__VATNR , "", true, true, false);
        //
        String email_ = HelpA.getValueSelectedRow(table, TABLE__EPOST);
        RowDataInvert email = new RowDataInvertB(email_, DB.BUH_KUND__EPOST, TABLE__EPOST , "", true, true, false);
        //
        String bg_ = HelpA.getValueSelectedRow(table, TABLE__BANK_GIRO);
        RowDataInvert bg = new RowDataInvertB(bg_, DB.BUH_KUND__BANK_GIRO, TABLE__BANK_GIRO , "", true, true, false);
        //
        String pg_ = HelpA.getValueSelectedRow(table, TABLE__POST_GIRO);
        RowDataInvert pg = new RowDataInvertB(pg_, DB.BUH_KUND__POST_GIRO, TABLE__POST_GIRO , "", true, true, false);
        //
        String konto_ = HelpA.getValueSelectedRow(table, TABLE__KONTO);
        RowDataInvert konto = new RowDataInvertB(konto_, DB.BUH_KUND__KONTO, TABLE__KONTO , "", true, true, false);
        //
        String iban_ = HelpA.getValueSelectedRow(table, TABLE__IBAN);
        RowDataInvert iban = new RowDataInvertB(iban_, DB.BUH_KUND__IBAN, TABLE__IBAN , "", true, true, false);
        //
        String swish_ = HelpA.getValueSelectedRow(table, TABLE__SWISH);
        RowDataInvert swish = new RowDataInvertB(swish_, DB.BUH_KUND__SWISH, TABLE__SWISH , "", true, true, false);
        //
        RowDataInvert[] rows = {
            namn,
            orgnr,
            vatnr,
            email,
            bg,
            pg,
            konto,
            iban,
            swish
        };
        //
        return rows;
    }


    @Override
    protected void hideColumnsMainTable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void addRowJtable_main(HashMap<String, String> map, JTable table) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void updateMainData(String fakturaKundId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void updateAddressData(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

}
