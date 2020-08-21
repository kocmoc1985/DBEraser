/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTable.OutPut;
import MyObjectTable.Table;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert;
import java.util.HashMap;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KOCMOC
 */
public class ForetagA extends CustomerAForetagA {

    protected static final String TABLE_ARTICLES__ID = "KUND ID";
    private static final String TABLE_ARTICLES__NAMN = "NAMN";
    private static final String TABLE_ARTICLES__ORGNR = "ORGNR";
    private static final String TABLE_ARTICLES__VATNR = "VATNR";
    private static final String TABLE_ARTICLES__EPOST = "E-POST";
    private static final String TABLE_ARTICLES__BANK_GIRO = "BG";
    private static final String TABLE_ARTICLES__POST_GIRO = "PG";
    private static final String TABLE_ARTICLES__KONTO = "KONTO";
    private static final String TABLE_ARTICLES__IBAN = "IBAN";
    private static final String TABLE_ARTICLES__SWISH = "SWISH";

    public ForetagA(BUH_INVOICE_MAIN bim) {
        super(bim);
    }

    @Override
    protected void SET_CURRENT_OPERATION_INSERT(boolean insert) {

    }
    
    

    @Override
    protected void startUp() {
        //
        if (GP_BUH.CUSTOMER_MODE == true) {
            hideAdressTable();
        }
         //
        fillJTable_header_main();
        fillJTable_header_address();
        //
    }
    
    
    
    @Override
     protected void fillJTable_header_main() {
        //
        //
        JTable table = getTableMain();
        //
        String[] headers = {
            TABLE_ARTICLES__ID,
            TABLE_ARTICLES__NAMN,
            TABLE_ARTICLES__ORGNR,
            TABLE_ARTICLES__VATNR,
            TABLE_ARTICLES__EPOST,
            TABLE_ARTICLES__BANK_GIRO,
            TABLE_ARTICLES__POST_GIRO,
            TABLE_ARTICLES__KONTO,
            TABLE_ARTICLES__IBAN,
            TABLE_ARTICLES__SWISH
        };
        //
        table.setModel(new DefaultTableModel(null, headers));
        //
    }

    @Override
    protected void fillJTable_header_address() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     
    @Override
    protected void addRowJtable_kund_adresses(HashMap<String, String> map, JTable table) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * INSERT
     */
    @Override
    public void showTableInvert() {
        //
        SET_CURRENT_OPERATION_INSERT(true);
        //
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert(), false, "");
        TABLE_INVERT = null;
        TABLE_INVERT = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT, 5, 0, 5, 0);
        showTableInvert(bim.jPanel16); // ************************ CHANGE
        //
        addTableInvertRowListener(TABLE_INVERT, this);
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
    
    public void showTableInvert_4() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_4(), false, "");
        TABLE_INVERT_4 = null;
        TABLE_INVERT_4 = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT_4, 5, 0, 5, 0);
        showTableInvert(bim.jPanel17); // ************************ CHANGE
        //
        addTableInvertRowListener(TABLE_INVERT_4, this);
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
//        RowDataInvert kundnr = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___KUNDNR, TABLE_FAKTURA_KUNDER__KUNDNR, "", true, true, true);
//        //
//        RowDataInvert namn = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___NAMN, TABLE_FAKTURA_KUNDER__KUND_NAMN, "", true, true, true);
//        //
//        RowDataInvert orgnr = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___ORGNR, TABLE_FAKTURA_KUNDER__ORGNR, "", true, true, false);
//        //
//        RowDataInvert vatnr = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___VATNR, TABLE_FAKTURA_KUNDER__VATNR, "", true, true, false);
//        //
//        RowDataInvert email = new RowDataInvertB("", DB.BUH_FAKTURA_KUND___EMAIL, TABLE_FAKTURA_KUNDER__EPOST, "", true, true, false);
//        //
//        RowDataInvert kund_kategori = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, DB.STATIC__KUND_AND_ARTICLE__KATEGORI, DB.BUH_FAKTURA_KUND___KATEGORI, TABLE_FAKTURA_KUNDER__KATEGORI, "", true, true, false);
//        kund_kategori.enableFixedValues();
//        kund_kategori.setUneditable();
//        //
//        RowDataInvert[] rows = {
//            kundnr,
//            namn,
//            orgnr,
//            vatnr,
//            email,
//            kund_kategori
//        };
        //
//        return rows;
        return null;
    }

    public RowDataInvert[] getConfigTableInvert_2() {
        return null;
    }

    public RowDataInvert[] getConfigTableInvert_3() {
        return null;
    }

    public RowDataInvert[] getConfigTableInvert_4() {
        return null;
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
