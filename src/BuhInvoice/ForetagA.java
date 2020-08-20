/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTable.OutPut;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert;

/**
 *
 * @author KOCMOC
 */
public class ForetagA extends Basic_Buh_ {

    private boolean CURRENT_OPERATION_INSERT = false;
    
    private static final String TABLE_ARTICLES__ID = "ARTIKEL ID";
    
    public ForetagA(BUH_INVOICE_MAIN bim) {
        super(bim);
    }
    
    protected void SET_CURRENT_OPERATION_INSERT(boolean insert) {
        
    }

    @Override
    protected void startUp() {
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
        showTableInvert(bim.jPanel5); // ************************ CHANGE
        //
        addTableInvertRowListener(TABLE_INVERT, this);
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
    
}
