/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTable.OutPut;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import forall.HelpA;
import java.awt.Color;
import java.awt.event.KeyEvent;
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
//    private static final String TABLE__COL_ORGNR__PNR = "ORGNR";

    private static final String TABLE__VATNR = "VATNR";
    private static final String TABLE__EPOST = "E-POST";
    private static final String TABLE__BANK_GIRO = "BG";
    private static final String TABLE__POST_GIRO = "PG";
    private static final String TABLE__KONTO = "KONTO";
    private static final String TABLE__IBAN = "IBAN";
    private static final String TABLE__SWISH = "SWISH";
    private static final String TABLE__F_SKATT = "F-SKATT";

    public ForetagA(LAFakturering bim) {
        super(bim);
//        GP_BUH.setPageBackground(bim.jPanel8, GP_BUH.BASIC_BACKGROUND_IMG__PATH);
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
            TABLE__COL_ORGNR__PNR,
            TABLE__VATNR,
            TABLE__EPOST,
            TABLE__BANK_GIRO,
            TABLE__POST_GIRO,
            TABLE__KONTO,
            TABLE__IBAN,
            TABLE__SWISH,
            TABLE__F_SKATT
        };
        //
        table.setModel(new DefaultTableModel(null, headers));
        //
    }

    @Override
    protected void addRowJtable_main(HashMap<String, String> map, JTable table) {
        //
        Object[] jtableRow = new Object[]{
            map.get(DB.BUH_KUND__ID),
            map.get(DB.BUH_KUND__NAMN),
            map.get(DB.BUH_KUND__ORGNR),
            map.get(DB.BUH_KUND__VATNR),
            map.get(DB.BUH_KUND__EPOST),
            map.get(DB.BUH_KUND__BANK_GIRO),
            map.get(DB.BUH_KUND__POST_GIRO),
            map.get(DB.BUH_KUND__KONTO),
            map.get(DB.BUH_KUND__IBAN),
            map.get(DB.BUH_KUND__SWISH),
            map.get(DB.BUH_KUND__F_SKATT)
        };
        //
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(jtableRow);
        //
    }

    @Override
    protected void hideColumnsMainTable() {
        //
        JTable table = getTableMain();
        //
        if (GP_BUH.CUSTOMER_MODE) {
            HelpA.hideColumnByName(table, TABLE__ID);
        }
    }

    /**
     * INSERT
     */
    @Override
    public void showTableInvert() {

    }

    public void showTableInvert_2() {
        //
        SET_CURRENT_OPERATION_INSERT(false);
        //
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_2(), false, "");
        TABLE_INVERT_2 = null;
        TABLE_INVERT_2 = tableBuilder.buildTable_B(this); // , Color.BLACK
        setMargin(TABLE_INVERT_2, 5, 0, 5, 0);
        showTableInvert(bim.jPanel16, TABLE_INVERT_2);
        //
    }

    public void showTableInvert_3() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_3(), false, "");
        TABLE_INVERT_3 = null;
        TABLE_INVERT_3 = tableBuilder.buildTable_B(this); //, Color.BLACK
        setMargin(TABLE_INVERT_3, 5, 0, 5, 0);
        showTableInvert(bim.jPanel17, TABLE_INVERT_3);
        //
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        return null;
    }

    @Override
    public RowDataInvert[] getConfigTableInvert_2() {
        //
        JTable table = getTableMain();
        //
        String namn_ = HelpA.getValueSelectedRow(table, TABLE__NAMN);
        RowDataInvert namn = new RowDataInvertB(namn_, DB.BUH_KUND__NAMN, TABLE__NAMN, "", true, true, true);
        //
        String orgnr_ = HelpA.getValueSelectedRow(table, TABLE__COL_ORGNR__PNR);
        RowDataInvert orgnr = new RowDataInvertB(orgnr_, DB.BUH_KUND__ORGNR, TABLE__COL_ORGNR__PNR, "", true, true, false);
        //
        String vatnr_ = HelpA.getValueSelectedRow(table, TABLE__VATNR);
        RowDataInvert vatnr = new RowDataInvertB(vatnr_, DB.BUH_KUND__VATNR, TABLE__VATNR, "", true, true, false);
        //
        String email_ = HelpA.getValueSelectedRow(table, TABLE__EPOST);
        RowDataInvert email = new RowDataInvertB(email_, DB.BUH_KUND__EPOST, TABLE__EPOST, "", true, true, false);
        //
        String bg_ = HelpA.getValueSelectedRow(table, TABLE__BANK_GIRO);
        RowDataInvert bg = new RowDataInvertB(bg_, DB.BUH_KUND__BANK_GIRO, TABLE__BANK_GIRO, "", true, true, false);
        //
        String pg_ = HelpA.getValueSelectedRow(table, TABLE__POST_GIRO);
        RowDataInvert pg = new RowDataInvertB(pg_, DB.BUH_KUND__POST_GIRO, TABLE__POST_GIRO, "", true, true, false);
        //
        String konto_ = HelpA.getValueSelectedRow(table, TABLE__KONTO);
        RowDataInvert konto = new RowDataInvertB(konto_, DB.BUH_KUND__KONTO, TABLE__KONTO, "", true, true, false);
        //
        String iban_ = HelpA.getValueSelectedRow(table, TABLE__IBAN);
        RowDataInvert iban = new RowDataInvertB(iban_, DB.BUH_KUND__IBAN, TABLE__IBAN, "", true, true, false);
        //
        String swish_ = HelpA.getValueSelectedRow(table, TABLE__SWISH);
        RowDataInvert swish = new RowDataInvertB(swish_, DB.BUH_KUND__SWISH, TABLE__SWISH, "", true, true, false);
        //
        //
        String fixedComboValues_b = JSon._get_special_(DB.STATIC__JA_NEJ,
                HelpA.getValueSelectedRow(table, TABLE__F_SKATT));
        //
        RowDataInvert f_skatt = new RowDataInvertB(RowDataInvert.TYPE_JCOMBOBOX, fixedComboValues_b, DB.BUH_KUND__F_SKATT, TABLE__F_SKATT, "", true, true, false);
        f_skatt.enableFixedValuesAdvanced();
        f_skatt.setUneditable();
        //
        RowDataInvert[] rows = {
            namn,
            orgnr,
            f_skatt,
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
    public RowDataInvert[] getConfigTableInvert_4() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleasedForward(TableInvert ti, KeyEvent ke) {
        //
        super.keyReleasedForward(ti, ke);
        //
        JLinkInvert jli = (JLinkInvert) ke.getSource();
        //
        String col_name = ti.getCurrentColumnName(ke.getSource());
        //
        if (col_name.equals(DB.BUH_KUND__NAMN)) {
            //
            Validator.validateMaxInputLength(jli, 150);
            //
        } else if (col_name.equals(DB.BUH_KUND__BANK_GIRO) || col_name.equals(DB.BUH_KUND__POST_GIRO)) {
            //
            Validator.validateMaxInputLength(jli, 20);
            //
        } else if (col_name.equals(DB.BUH_KUND__KONTO) || col_name.equals(DB.BUH_KUND__IBAN)) {
            //
            Validator.validateMaxInputLength(jli, 30);
            //
        } else if (col_name.equals(DB.BUH_KUND__SWISH)) {
            //
            Validator.validateMaxInputLength(jli, 30);
            //
        }

    }

}
