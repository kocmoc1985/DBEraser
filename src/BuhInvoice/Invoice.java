/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTable.OutPut;
import MyObjectTable.Table;
import MyObjectTable.TableData;
import MyObjectTableInvert.Basic;
import MyObjectTableInvert.ColumnDataEntryInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import MyObjectTableInvert.TableRowInvert;
import forall.HelpA;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JTextField;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.JTextFieldInvert;
import java.awt.AWTEvent;
import java.awt.Event;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;

/**
 *
 * @author KOCMOC
 */
public abstract class Invoice extends Basic {

    protected final BUH_INVOICE_MAIN bim;
    protected Table TABLE_INVERT_2;
    protected Table TABLE_INVERT_3;
    protected Faktura_Entry faktura_entry;
    private static int INSERT_OR_UPDATE_CLASS = 0; // MUST BE STATIC [2020-07-29]
    private final Pattern DATE_YYYY_MM_DD = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

    public Invoice(BUH_INVOICE_MAIN bim) {
        this.bim = bim;
        initOther();
    }

    private void initOther() {
        //
        faktura_entry = initFakturaEntry();
        //
        startUp();
        //
    }

    protected abstract Faktura_Entry initFakturaEntry();

    protected abstract void startUp();

    public void insertOrUpdate() {
        faktura_entry.insertOrUpdate();
    }

    /**
     * VERY IMPORTANT ! [2020-07-29]
     *
     * @return 1 = Insert, 2 = Update, 0 = Undefined
     */
    public static int isInsertOrUpdate() {
        return INSERT_OR_UPDATE_CLASS;
    }

    public void setInsertOrUpdateClassActive() {
        if (this instanceof InvoiceA_Insert) {
            INSERT_OR_UPDATE_CLASS = 1;
        } else if (this instanceof InvoiceA_Update) {
            INSERT_OR_UPDATE_CLASS = 2;
        } else {
            INSERT_OR_UPDATE_CLASS = 0;
        }
    }

    protected String getKundId() {
        return bim.getKundId();
    }

    protected String getFakturaKundId() {
        return getValueTableInvert(DB.BUH_FAKTURA_KUND__ID, TABLE_INVERT); // "fakturaKundId"
    }

    protected String getFakturaNr() {
        //
        HashMap<String, String> map = new HashMap<>();
        map.put(DB.BUH_FAKTURA__KUNDID__, bim.getKundId());
        String json = JSon.hashMapToJSON(map);
        //
        try {
            //
            String fakturaNr = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_GET_LATEST_FAKTURA_NR, json));
            //
            if (HelpA.checkIfNumber_b(fakturaNr)) {
                int nr = Integer.parseInt(fakturaNr);
                nr++; // OBS! Iam getting the last so i have to add to get the nr for the act. faktura
                return "" + nr;
            } else {
                return null;
            }
            //
        } catch (Exception ex) {
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean getInklMoms() {
        try {
            return Integer.parseInt(getValueTableInvert(DB.BUH_FAKTURA__INKL_MOMS, TABLE_INVERT_3)) == 1;
        } catch (Exception ex) {
            return true;
        }
    }

    public double getMomsSats() {
        try {
            return Double.parseDouble(getValueTableInvert(DB.BUH_FAKTURA__MOMS_SATS, TABLE_INVERT_3));
        } catch (Exception ex) {
            return 0.25;
        }
    }

    public boolean getMakulerad() {
        return Integer.parseInt(getValueTableInvert(DB.BUH_FAKTURA__MAKULERAD, TABLE_INVERT_3)) == 1;
    }

    public abstract RowDataInvert[] getConfigTableInvert_2();

    public abstract RowDataInvert[] getConfigTableInvert_3();

    @Override
    public void showTableInvert() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert(), false, "buh_faktura_a");
        TABLE_INVERT = null;
        TABLE_INVERT = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT, 5, 0, 5, 0);
        showTableInvert(bim.jPanel2_faktura_main);
        //
        addTableInvertRowListener(TABLE_INVERT, this);
        //
        setInsertOrUpdateClassActive(); //  ************************ [2020-07-29] IMPORTANT
        //
        bim.displayInsertOrUpdate(); // FOR TRACING
    }

    public void showTableInvert_2() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_2(), false, "buh_f_artikel");
        TABLE_INVERT_2 = null;
        TABLE_INVERT_2 = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT_2, 5, 0, 5, 0);
        showTableInvert(bim.jPanel_articles, TABLE_INVERT_2);
        //
        addTableInvertRowListener(TABLE_INVERT_2, this);
    }

    public void showTableInvert_3() {
        TableBuilderInvert tableBuilder = new TableBuilderInvert(new OutPut(), null, getConfigTableInvert_3(), false, "buh_faktura_b");
        TABLE_INVERT_3 = null;
        TABLE_INVERT_3 = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT_3, 5, 0, 5, 0);
        showTableInvert(bim.jPanel3_faktura_sec, TABLE_INVERT_3);
        //
        addTableInvertRowListener(TABLE_INVERT_3, this);
        //
        hideMomsSatsIfExklMoms(); // **********************************
        //
    }

    protected void hideMomsSatsIfExklMoms() {
        //
        System.out.println("INKL_MOMS----------------------");
        //
        boolean momsInk = getInklMoms();
        //
        TableInvert table = (TableInvert) TABLE_INVERT_3;
        TableRowInvert tri = (TableRowInvert) table.getRowByColName(DB.BUH_FAKTURA__MOMS_SATS);
        RowDataInvert rdi = tri.getRowConfig();
        //
        if (momsInk == false) {
            //
            // Both ways below working (regarding: "setVisible()")
//                tri.setVisible(false);
            rdi.setVisible_(false);
            //
            refreshTableInvert(TABLE_INVERT_3);
            //
        } else {
            //
            // Both ways below working
//                tri.setVisible(true);
            rdi.setVisible_(true);
            //
            refreshTableInvert(TABLE_INVERT_3);
            //
        }
    }

    protected String requestJComboValuesHttp(String php_function, String keyOne, String keyTwo) {
        //
        String comboString;
        //
        String json = bim.getSELECT_kundId();
        //
        try {
            //
            String json_str_return = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    php_function, json));
            //
            //
            comboString = JSon.phpJsonResponseToComboBoxString(json_str_return, keyOne, keyTwo);
            //
//            System.out.println("combo string: " + comboString);
            //
        } catch (Exception ex) {
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
            comboString = null;
        }
        //
        return comboString;
    }

    @Override
    public void mouseClicked(MouseEvent me, int column, int row, String tableName, TableInvert ti) {
        //
        super.mouseClicked(me, column, row, tableName, ti); //To change body of generated methods, choose Tools | Templates.
        //
        //
        setFieldUpdated(me.getSource()); // *************************
        //
        //
        String col_name = ti.getCurrentColumnName(me.getSource());
        //
        if (col_name.equals(DB.BUH_FAKTURA__ER_REFERENS)) {
            String er_referens_last = HelpA.loadLastEntered(IO.getErReferens(getFakturaKundId()));
            setValueTableInvert(DB.BUH_FAKTURA__ER_REFERENS, TABLE_INVERT, er_referens_last);
        }
        //
    }

    @Override
    public void initializeSaveIndicators() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getUnsaved(int nr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseWheelForward(TableInvert ti, MouseWheelEvent e) {
        //
        super.mouseWheelForward(ti, e); //To change body of generated methods, choose Tools | Templates.
        //
        String col_name = ti.getCurrentColumnName(e.getSource());
        //
        if (col_name.equals(DB.BUH_FAKTURA__FAKTURA_DATUM)) {
            //
            double wheelRotation = e.getPreciseWheelRotation();
            //
            double scroll = wheelRotation;
            double scroll_rounded = Math.round(scroll);
            long value = (long) scroll_rounded;
            //
            String date = getValueTableInvert(DB.BUH_FAKTURA__FAKTURA_DATUM);
            //
            String date_new;
            //
            if (wheelRotation > 0) {
                date_new = HelpA.get_date_time_plus_some_time_in_days(date, value);
            } else {
                date_new = HelpA.get_date_time_minus_some_time_in_days(date, value);
            }
            //
            setValueTableInvert(DB.BUH_FAKTURA__FAKTURA_DATUM, TABLE_INVERT, date_new);
            //
            forfalloDatumAutoChange();
            //
        }
        //
    }

    /**
     * [2020-07-30] Marking the field is considered to be updated. In future it
     * makes sense to make better the criteria of considering the field updated.
     * Now it's considered to be updated when a user clicks on a field
     *
     * @param eventSourceObj
     */
    private void setFieldUpdated(Object eventSourceObj) {
        //
        ColumnDataEntryInvert cde = getColumnData(eventSourceObj);
        //
        if (cde != null) {
            cde.setUpdated(true);
            System.out.println("FIELD: '" + cde.getOriginalColumn_name() + "' " + "MARKED AS UPDATED");
        }
    }
    
    /**
     * IMPORTANT EXAMPLE [2020-07-30]
     * @param evt 
     */
    private void TEST_REFERENSES(AWTEvent evt){
        //
        if (evt.getSource() instanceof JLinkInvert) {
            //
            JLinkInvert jli = (JLinkInvert) evt.getSource();
            //
            TableRowInvert tri = jli.getParentObj();
            //
            TableInvert ti_ = (TableInvert) tri.getTable();
            //
            TableData ta = ti_.TABLE_DATA;
            //
            RowDataInvert rdi = tri.getRowConfig();
            //
            ColumnDataEntryInvert cde = jli.getChildObject();
            //
            String initialValue = cde.getInitialValue();
            //
            String actualValue = jli.getValue();
            //
            boolean valuChanged = jli.valueUpdated();
            //
            System.out.println("");
            System.out.println("InitialValue: " + initialValue);
            System.out.println("ActualValue: " + actualValue);
            System.out.println("Value Changed: " + valuChanged);
            System.out.println("");
            //
        }
        //
    }

    /**
     * [2020-07-XX] SUPER important here you catch the event when key released
     * on some component so you can process this event as required
     *
     * @param ti
     * @param ke
     */
    @Override
    public void keyReleasedForward(TableInvert ti, KeyEvent ke) {
        //
        super.keyReleasedForward(ti, ke); //To change body of generated methods, choose Tools | Templates.
        //
        String col_name = ti.getCurrentColumnName(ke.getSource());
        //
        //
        TEST_REFERENSES(ke);
        //
        //
        if (col_name.equals(DB.BUH_FAKTURA__VAR_REFERENS)) {
            //
            String var_referens = getValueTableInvert(DB.BUH_FAKTURA__VAR_REFERENS);
            //
            try {
                HelpA.writeToFile(IO.VAR_REFERENS, var_referens);
            } catch (IOException ex) {
                Logger.getLogger(InvoiceA_Insert.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__ER_REFERENS)) {
            //
            String er_referens = getValueTableInvert(DB.BUH_FAKTURA__ER_REFERENS);
            //
            try {
                HelpA.writeToFile(IO.getErReferens(getFakturaKundId()), er_referens);
            } catch (IOException ex) {
                Logger.getLogger(InvoiceA_Insert.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__FAKTURA_DATUM)) {
            //
            String val = getValueTableInvert(DB.BUH_FAKTURA__FAKTURA_DATUM, TABLE_INVERT);
            //
            boolean validated = validate(DATE_YYYY_MM_DD, val);
            //
            JTextField jtf = (JTextField) ke.getSource();
            //
            if (validated && HelpA.isDateValid(val)) {
                JTextField field = new JTextField();
                Color initialColor = field.getForeground();
                jtf.setForeground(initialColor);
                forfalloDatumAutoChange();
            } else {
                jtf.setForeground(Color.RED);
            }
            //
//            System.out.println("validated: " + validated);
        }
    }

    private boolean validate(Pattern pattern, String stringToCheck) {
        Matcher matcher = pattern.matcher(stringToCheck);
        return matcher.find();
    }

    private void forfalloDatumAutoChange() {
        //
        String val = getValueTableInvert(DB.BUH_FAKTURA__BETAL_VILKOR);
        //
        if (val.equals("NULL")) {
            return;
        }
        //
        long value = Long.parseLong(val);
        String date = getValueTableInvert(DB.BUH_FAKTURA__FAKTURA_DATUM);
        String date_new = HelpA.get_date_time_plus_some_time_in_days(date, value);
        setValueTableInvert(DB.BUH_FAKTURA__FORFALLO_DATUM, TABLE_INVERT, date_new);
    }

    /**
     * [2020-07-XX] SUPER important here you catch the event when some jcombobox
     * item is changed so you can process this event as required
     *
     * @param ti
     * @param ie
     */
    @Override
    public void jComboBoxItemStateChangedForward(TableInvert ti, ItemEvent ie) {
        //
        super.jComboBoxItemStateChangedForward(ti, ie);
        //
        TEST_REFERENSES(ie);
        //
        String col_name = ti.getCurrentColumnName(ie.getSource());
        //
        if (col_name.equals(DB.BUH_FAKTURA__BETAL_VILKOR)) {
            //
            forfalloDatumAutoChange();
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__INKL_MOMS)) {
            //
            hideMomsSatsIfExklMoms();
            //
        } else if (col_name.equals(DB.BUH_FAKTURA__MAKULERAD)) {
            //
            System.out.println("FAKTURA MAKULERAD");
            //
        }
        //
    }

}
