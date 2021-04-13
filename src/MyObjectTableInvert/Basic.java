/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import BuhInvoice.GP_BUH;
import Reporting.InvertTableRow;
import MCCompound.PROD_PLAN;
import MCRecipe.MC_RECIPE;
import static MCRecipe.MC_RECIPE.USER_ROLE;
import static MCRecipe.MC_RECIPE.USER_ROLES_ADMIN_DEVELOPER_ACCESS;
import MyObjectTable.SaveIndicator;
import MyObjectTable.Table;
import Reporting.TableInvertBasicRepport;
import Reporting.JTableBasicRepport;
import forall.HelpA;
import static forall.HelpA.run_application_with_associated_application;
import forall.TextFieldCheck;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author KOCMOC
 */
public abstract class Basic implements SaveIndicator.SaveIndicatorIF {

    public Table TABLE_INVERT;

    public Basic() {
    }

    public String getValueHashMap(String value) {
        //
        if (value == null || value.isEmpty() || value.equals("null") || value.equals("NULL")) {
            return "";
        } else {
            return value;
        }
        //
    }

    /**
     * [2020-11-21] This value brings an JOptionPane when double clicking on a
     * JTextFieldInvert Shall be called from: "mouseClickedForward(...)"
     *
     * @param me
     * @param ti
     */
    public void enableEditingLongValues(MouseEvent me) {
        //
        if (me.getSource() instanceof JTextFieldInvert && me.getClickCount() == 2) {//colName.equals("DESCR")
            //
            JLinkInvert jli = (JLinkInvert) me.getSource();
            TableRowInvert tri = jli.getParentObj();
            RowDataInvert rdi = tri.getRowConfig();
            JTextField jtf = (JTextField) jli;
            //
            if (rdi.isEditable() == false || jtf.isEnabled() == false) {
                return;
            }
            //
            //
            String initialVal = jli.getValue();
            //
            TextFieldCheck tfc = new TextFieldCheck(initialVal, null, 70, 12);
            //
            boolean yesNo = HelpA.chooseFromJTextFieldWithCheck(tfc, "Edit");
            //
            if (yesNo == false) {
                return;
            }
            //
            String value = tfc.getText();
            //
            ColumnDataEntryInvert cdi = jli.getChildObject();
            cdi.setValue(value);
            //
            tri.keyReleased_(me); //OBS!! MUST Simulate this
            //
        }
        //
    }

    public void mouseClickedForward(MouseEvent me, int column, int row, String tableName, TableInvert ti) {
        System.out.println(this.getClass() + ": mouseClickedForward() from TableRowInvert. curr_col_check: " + ti.getCurrentColumnName(me.getSource()));
    }

    /**
     * Call from: TableRowInvertB
     *
     * @param ti
     * @param ke
     */
    public void keyReleasedForward(TableInvert ti, KeyEvent ke) {
        System.out.println(this.getClass() + ":  keyReleasedForward() from TableRowInvert. curr_col_check: " + ti.getCurrentColumnName(ke.getSource()));
    }

    /**
     * Call from: TableRowInvertB
     *
     * @param ti
     * @param e
     * @param ke
     */
    public void mouseWheelForward(TableInvert ti, MouseWheelEvent e) {
        System.out.println(this.getClass() + "mouseWheelForward() from TableRowInvert. curr col: " + ti.getCurrentColumnName(e.getSource()));
    }

    /**
     * Call from: TableRowInvertB
     *
     * @param ti
     * @param ie
     */
    public void jComboBoxItemStateChangedForward(TableInvert ti, ItemEvent ie) {
        System.out.println(this.getClass() + ":   jComboBoxItemStateChangedForward() from TableRowInvert. curr_col_check: " + ti.getCurrentColumnName(ie.getSource()));
    }

    /**
     * [2020-08-19]
     *
     * @param e
     */
    public void mouseWheelNumberChange(MouseWheelEvent e) {
        //
        JLinkInvert jli = (JLinkInvert) e.getSource();
        //
        if (jli instanceof JTextFieldInvert == false) { // This one is for JTextFieldInvert
            return;
        }
        //
        long value = getValueWheelRotation(e);
        //
        try {
            //
            long antal = Long.parseLong(jli.getValue());
            //
            if (value < 0 && antal == 0) {
                return;
            } else {
                antal += value; // OBS! it's always "+" because value is "+" or "-"
            }
            //
            JTextFieldInvert jtf = (JTextFieldInvert) jli;
            jtf.setValue("" + antal);
            //
        } catch (Exception ex) {

        }
        //
    }

    public HashMap<String, String> tableInvertToHashMap(Table table_invert, int startColumn) {
        return tableInvertToHashMap(table_invert, startColumn, -1); // -1 means: "HelpA->ComboBoxObject->getParamAuto()"
    }

    /**
     * Important changes made on [2020-08-24]
     *
     * @param table_invert
     * @param startColumn
     * @param jcomboParamToReturn
     * @return
     */
    public HashMap<String, String> tableInvertToHashMap(Table table_invert, int startColumn, int jcomboParamToReturn) {
        //
        if (table_invert == null) {
            return null;
        }
        //
        HashMap<String, String> mapToReturn = new HashMap<>();
        //
        TableInvert tableInvert = (TableInvert) table_invert;
        //
        for (int i = 0; i < tableInvert.getRowCount(); i++) {
            //
            RowDataInvert dataInvert = tableInvert.getRowConfig(i);
            //
            for (int x = startColumn; x < getColumnCount(table_invert); x++) {
                //
                HashMap<String, ColumnValue> map = tableInvert.getColumnData(x);
                //
                ColumnValue columnValue = map.get(dataInvert.getFieldNickName());
                //
                String val;
                //
                if (jcomboParamToReturn == -1) {
                    val = columnValue.getValue();
                } else {
                    val = columnValue.getValue(jcomboParamToReturn);
                }
                //
                val = GP_BUH.replaceColon(val, false); // replace ":" with "#"
                val = GP_BUH.replaceComma(val, false); // replace "," with "¤"
                val = GP_BUH.replacePlus(val, false); // replace "+" with "£"
                val = GP_BUH.replaceAnd(val, false); // replace "&" with "~"
                //
                // [2020-08-18] Not taking into account empty or null
                // Using 'DEFAULT' in Database helps when inserting
                if (val == null || val.isEmpty() || val.equals("NULL") || val.equals("null")) {
                    //
                    if (dataInvert.saveEmptyStringValue()) {
                        mapToReturn.put(dataInvert.getFieldOriginalName(), "");
                    }
                    //
                    if (dataInvert.saveEmptyNumber()) {
                        mapToReturn.put(dataInvert.getFieldOriginalName(), "0");
                    }
                    //
                } else {
                    //
                    if (dataInvert.aquire()) {
                        mapToReturn.put(dataInvert.getFieldOriginalName(), val);
                    }
                    //
                }
                //
            }
        }
        //
        return mapToReturn;
    }

    /**
     * [2020-08-19]
     *
     * @param e
     */
    public long getValueWheelRotation(MouseWheelEvent e) {
        //
        double wheelRotation = e.getPreciseWheelRotation();
        //
        double scroll = wheelRotation;
        double scroll_rounded = Math.round(scroll);
        return (long) scroll_rounded;
    }

    /**
     *
     * @param table
     * @param landscape - for report
     * @param writeToFile - for export
     */
    public void tableCommonExportOrRepport(JTable table, boolean landscape) {
        //
        JComboBox box = new JComboBox(new String[]{"REPORT", "CSV"});
        //
        boolean cond_0 = HelpA.chooseFromComboBoxDialog(box, "Report or CSV");
        //
        boolean cond_1 = HelpA.getComboBoxSelectedValue(box).equals("REPORT");
        //
        boolean cond_2 = HelpA.getComboBoxSelectedValue(box).equals("CSV");
        //
        if (cond_0 && cond_1) {
            tableCommonRepport(table, landscape);
        } else if (cond_0 && cond_2) {
            jTableToCSV(table, true);
        }
        //
    }

    public void tableInvertExportOrRepport(Table table_invert, int startColumn, RowDataInvert[] cfg) {
        //
        JComboBox box = new JComboBox(new String[]{"REPORT", "CSV"});
        //
        boolean cond_0 = HelpA.chooseFromComboBoxDialog(box, "Report or CSV");
        //
        boolean cond_1 = HelpA.getComboBoxSelectedValue(box).equals("REPORT");
        //
        boolean cond_2 = HelpA.getComboBoxSelectedValue(box).equals("CSV");
        //
        if (cond_0 && cond_1) {
            tableInvertRepport(table_invert, startColumn, cfg);
        } else if (cond_0 && cond_2) {
            tableInvertToCSV(table_invert, startColumn, cfg, true);
        }
        //
    }

    public void tableCommonRepport(JTable table, boolean landscape) {
        new JTableBasicRepport(table, landscape);
    }

    public void tableInvertRepport(Table table_invert, int startColumn, RowDataInvert[] cfg) {
        String csv = tableInvertToCSV(table_invert, startColumn, cfg, false);
        makeRepportTableInvert(csv);
    }

    public void setVerticalScrollBarDisabled(Table table_invert) {
        TableInvert tableInvert = (TableInvert) table_invert;
        JScrollPane jsp = (JScrollPane) tableInvert.getTable();
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    }

    public void setMargin(Table table_invert, int top, int left, int bottom, int right) {
        TableInvert tableInvert = (TableInvert) table_invert;
        tableInvert.setMargin(top, left, bottom, right);
    }

    public boolean tableEmpty(Table table_invert) {
        TableInvert tableInvert = (TableInvert) table_invert;
        return tableInvert.tableEmpty();
    }

    public void changeValueAndSave(Table table_invert, Object newValue, String rowName, int column) {
        setValueTableInvert(rowName, table_invert, newValue);
        addToUnsaved(table_invert, rowName, column);
        saveChangesTableInvert(table_invert);
    }

    public void changeValueNoSave(Table table_invert, Object newValue, String rowName, int column) {
        setValueTableInvert(rowName, table_invert, newValue);
        addToUnsaved(table_invert, rowName, column);
    }

    public boolean unsavedEntriesExist(Table table_invert) {
        //
        if (table_invert == null) {
            return false;
        }
        //
        if (table_invert.unsaved_entries_map.isEmpty() == false) {
            return true;
        } else {
            return false;
        }
        //
    }

    public void setUnsaved(Table table_invert, String rowName, int column) {
        addToUnsaved(table_invert, rowName, column);
    }

    public void addToUnsaved(Table table_invert, String rowName, int column) {
        TableInvert tableInvert = (TableInvert) table_invert;
        tableInvert.addToUnsaved(rowName, column);
    }

    public int getColumnCount(Table table_invert) {
        TableInvert tableInvert = (TableInvert) table_invert;
        return tableInvert.getColumnCount();
    }

    public void clearAllRowsTableInvert_jcombobox_special(Table table_invert, int jcomboSelecetedIndex) {
        //
        TableInvert tableInvert = (TableInvert) table_invert;
        //
        if (tableInvert == null) {
            return;
        }
        //
        tableInvert.clearAllRowsJComboBoxSpecial(jcomboSelecetedIndex);
        //
    }

    public void clearAllRowsTableInvert(Table table_invert) {
        //
        TableInvert tableInvert = (TableInvert) table_invert;
        //
        if (tableInvert == null) {
            return;
        }
        //
        tableInvert.clearAllRows();
        //
    }

    public void clearRows(Table table_invert, int start) {
        TableInvert tableInvert = (TableInvert) table_invert;
        //
        if (tableInvert == null) {
            return;
        }
        //
        tableInvert.clearRows(start);
    }

    public void clearRows(Table table_invert, int start, int notToClearFromEnd) {
        TableInvert tableInvert = (TableInvert) table_invert;
        //
        if (tableInvert == null) {
            return;
        }
        //
        tableInvert.clearRows(start, notToClearFromEnd);
    }

    public void setValueTableInvert(String rowName, Table tableInvert, Object value) {
        TableInvert ti = (TableInvert) tableInvert;
        ti.setValueAt(rowName, value, defineValueColumnIndex(tableInvert));
    }

    public void setColorTableInvert(String rowName, Table tableInvert, Color color) {
        TableInvert ti = (TableInvert) tableInvert;
        ti.setColorAt(rowName, color);
    }

    /**
     *
     * [2020-07-30]
     *
     * @param parent - Usually it's taken from an "Event" like "me.getSource()"
     * @return
     */
    public ColumnDataEntryInvert getColumnData(Object parent) {
        if (parent instanceof JLinkInvert) {
            JLinkInvert jpi = (JLinkInvert) parent;
            return jpi.getChildObject();
        } else {
            return null;
        }
    }

    /**
     * Basic method for getting value from a TableInvert Obs! The rowName is the
     * column name from DB not the nickName
     *
     * @param rowName
     * @return
     */
    public String getValueTableInvert(String rowName) {
        TableInvert ti = (TableInvert) TABLE_INVERT;
        return ti.getValueAt(rowName);
    }

    /**
     * [2020-07-31]
     *
     * @param rowName
     * @param tableInvert
     * @return
     */
    public boolean getValidated(String rowName, Table tableInvert) {
        ColumnDataEntryInvert cde = getColumnDataEntryInvert(rowName, tableInvert);
        return cde.isValidated();
    }

    /**
     * MEGA IMPORTANT [2020-07-31]
     *
     * @param rowName
     * @param tableInvert
     * @return
     */
    public ColumnDataEntryInvert getColumnDataEntryInvert(String rowName, Table tableInvert) {
        TableInvert ti = (TableInvert) tableInvert;
        return ti.getColumnDataEntryInvertAt(rowName);
    }

    /**
     * [2020-07-31]
     *
     * @param rowName
     * @param tableInvert
     * @param column
     * @return
     */
    public ColumnDataEntryInvert getColumnDataEntryInvert(String rowName, Table tableInvert, int column) {
        TableInvert ti = (TableInvert) tableInvert;
        return ti.getColumnDataEntryInvertAt(rowName, column);
    }

    public Object getObjectTableInvert(String rowName, Table tableInvert) {
        TableInvert ti = (TableInvert) tableInvert;
        return ti.getObjectAt(rowName);
    }

    /**
     * [2020-10-21] This one is for getting other values which are not shown
     * from JCombo
     *
     * @param rowName
     * @param tableInvert
     * @param paramToReturn
     * @return
     */
    public String getValueTableInvertJComboBox(String rowName, Table tableInvert, int paramToReturn) {
        TableInvert ti = (TableInvert) tableInvert;
        return ti.getValueAtJComboBox(rowName, paramToReturn);
    }

    public void trimValueTableInvert(JLinkInvert jli) {
        //
        if (jli instanceof JTextFieldInvert == false) {
            return;
        }
        //
        String val = jli.getValue().trim();
        ColumnDataEntryInvert cdei = jli.getChildObject();
        cdei.setValue(val);
        //
    }

    public String getValueTableInvert(String rowName, Table tableInvert) {
        TableInvert ti = (TableInvert) tableInvert;
        return ti.getValueAt(rowName);
    }

    public String getValueTableInvert(String rowName, int column, Table tableInvert) {
        TableInvert ti = (TableInvert) tableInvert;
        return ti.getValueAt(rowName, column);
    }

    public void saveChangesTableInvert(Table tableInvert) {
        //       
        TableInvert ti = (TableInvert) tableInvert;
        //
        automaticFieldUpdate(tableInvert);
        //
        ti.applyChanges();
        //
    }

    public void saveChangesTableInvert() {
        //
        TableInvert ti = (TableInvert) TABLE_INVERT;
        //
        automaticFieldUpdate(TABLE_INVERT);
        //
        ti.applyChanges();
        //
    }

    public void automaticFieldUpdate(Table tableInvert) {
        TableInvert ti = (TableInvert) tableInvert;
        ti.handleAutomaticFieldUpdate("UpdatedOn", HelpA.updatedOn());
        ti.handleAutomaticFieldUpdate("UpdatedBy", HelpA.updatedBy());
    }

    /**
     * OBS! Works in "connection" with TableBuilderInvert -> buildTable_C_C(...)
     */
    public void saveChangesTableInvert_C_C(TableInvert ti) {
        //
        if (MC_RECIPE.isAdminOrDeveloper() == false) {
            return;
        }
        //
        TableInvert ti_ = (TableInvert) ti;
        //
        autoSaveUpdadetOnBy_C_C(ti_);
        //
        ti_.applyChanges();
        //
    }

    private void autoSaveUpdadetOnBy_C_C(TableInvert ti) {
        //
        HashMap<Object, UnsavedEntryInvert> unsaved_entries_map = TABLE_INVERT.unsaved_entries_map;
        ///
        Set set = unsaved_entries_map.keySet();
        Iterator it = set.iterator();
        //
        while (it.hasNext()) {
            //
            Object key = it.next();
            UnsavedEntryInvert unsavedEntryInvert = unsaved_entries_map.get(key);
            //
            String tableName = unsavedEntryInvert.getTableName();
            String idColName = unsavedEntryInvert.getPrimareyOrForeignKeyName();
            String id = unsavedEntryInvert.getDbID();
            //
            ti.updateFieldString(tableName,
                    "UpdatedOn",
                    HelpA.updatedOn(),
                    idColName,
                    id,
                    true,
                    true);
            //
            ti.updateFieldString(tableName,
                    "UpdatedBy",
                    HelpA.updatedBy(),
                    idColName,
                    id,
                    true,
                    true);
            //
        }
        //
    }

    public boolean columnNameExists(String colName, Table tableInvert) {
        try {
            getValueTableInvert(colName, defineValueColumnIndex(tableInvert), tableInvert);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public int defineValueColumnIndex(Table tableInvert) {
        TableInvert ti = (TableInvert) tableInvert;
        //
        if (ti.getShowInits() == false) {
            return 1;
        } else {
            return 2;
        }

    }

    /**
     * This one is needed due to JEdtiorPane can display ".rtf" but it doesn't
     * display plain text
     *
     * @param parent
     * @param replace
     * @param replaceWith
     */
    public void replaceComponent(JComponent parent, JScrollPane jScrollPane, Component replace, Component replaceWith) {
        //
        Component[] c_arr = parent.getComponents();
        //
        for (Component component : c_arr) {
            if (component == replace) {
                jScrollPane.remove(replace);
                jScrollPane.setViewportView(replaceWith);
            }
        }
    }

    public abstract RowDataInvert[] getConfigTableInvert();

    public abstract void showTableInvert();

    public String jTableToCSV(JTable table, boolean writeToFile) {
        return HelpA.jTableToCSV(table, writeToFile);
    }

    public String jTableToCSV(JTable table, boolean writeToFile, String[] columns) {
        return HelpA.jTableToCSV(table, writeToFile, columns);
    }

    /**
     * [2020-07-30]
     *
     * @param table_invert
     * @param startColumn
     * @param rdi
     * @return
     */
    public boolean containsInvalidatedFields(Table table_invert, int startColumn, RowDataInvert[] rdi) {
        //
        TableInvert tableInvert = (TableInvert) table_invert;
        //
        for (RowDataInvert dataInvert : rdi) {
            //
            for (int x = startColumn; x < getColumnCount(table_invert); x++) {
                //
                HashMap<String, ColumnValue> map = tableInvert.getColumnData(x);
                //
                ColumnValue columnValue = map.get(dataInvert.getFieldNickName());
                //
                ColumnDataEntryInvert cde = columnValue.getColumnDataEntryInvert();
                //
                if (cde.isValidated() == false) {
                    return true;
                }
                //
            }
            //
        }
        //
        return false;
        //
    }

    /**
     * [2020-08-03]
     *
     * @param table_invert
     * @param startColumn
     * @param rdi
     * @return
     */
    public boolean containsEmptyObligatoryFields(Table table_invert, int startColumn, RowDataInvert[] rdi) {
        //
        TableInvert tableInvert = (TableInvert) table_invert;
        //
        boolean contains = false;
        //
        for (RowDataInvert dataInvert : rdi) {
            //
            for (int x = startColumn; x < getColumnCount(table_invert); x++) {
                //
                HashMap<String, ColumnValue> map = tableInvert.getColumnData(x);
                //
                ColumnValue columnValue = map.get(dataInvert.getFieldNickName());
                //
                ColumnDataEntryInvert cde = columnValue.getColumnDataEntryInvert();
                //
                if (dataInvert.important == true) {
                    //
                    if (cde.getActualValue().isEmpty() || cde.getActualValue().equals("NULL")) {
                        cde.markInvalidated();
                        contains = true;
                    }
                    //
                }
                //
            }
            //
        }
        //
        return contains;
        //
    }

    /**
     * OBS! Try using "startColumn=1 or 2" will not work with 0 [2020-07-10] Use
     * 2 if using units, 1 otherwise
     *
     * @param table_invert
     * @param startColumn
     * @param rdi
     * @param writeToFile
     * @return
     */
    public String tableInvertToCSV(Table table_invert, int startColumn, RowDataInvert[] rdi, boolean writeToFile) {
        //
        TableInvert tableInvert = (TableInvert) table_invert;
        //
        String csv = "";
        //
        for (RowDataInvert dataInvert : rdi) {
            //
            if (dataInvert.getVisible() == false) {
                continue;
            }
            //
            csv += dataInvert.getFieldNickName() + ";";
            //
            if (dataInvert.getUnit() instanceof String) {
                String unit = (String) dataInvert.getUnit();
                //
                if (unit.isEmpty() == false) {
                    csv += unit + ";";
                } else {
                    csv += "unit" + ";";
                }
                //
            }
            //
            for (int x = startColumn; x < getColumnCount(table_invert); x++) {
                //
                HashMap<String, ColumnValue> map = tableInvert.getColumnData(x);
                //
                ColumnValue columnValue = map.get(dataInvert.getFieldNickName());
                //
                if (columnValue == null) {
                    //
                } else {
                    csv += columnValue.getValue() + ";";
                }
                // 
            }
            //
            csv += "\n";
            //
        }
        //
//        System.out.println("CSV: \n" + csv);
        //
        //
        String path = HelpA.get_desktop_path() + "\\"
                + HelpA.get_proper_date_time_same_format_on_all_computers_err_output() + ".csv";
        //
        if (writeToFile) {
            try {
                HelpA.writeToFile(path, csv);
//                JOptionPane.showMessageDialog(null, "Export file ready, the file is in: " + path);
                run_application_with_associated_application(new File(path));
            } catch (IOException ex) {
                Logger.getLogger(PROD_PLAN.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //
        return csv;
    }

    private void makeRepportTableInvert(String csv) {
        String[] lines = csv.split("\n");
        //
        ArrayList<InvertTableRow> tableRowsList = new ArrayList<InvertTableRow>();
        //
        for (String line : lines) {
            String arr[] = line.split(";");

            String columnName = arr[0];
            String unit = arr[1];

            InvertTableRow row = new InvertTableRow(columnName, unit);

            for (int i = 2; i < arr.length; i++) {
                row.addValue(arr[i]);
            }
            //
            tableRowsList.add(row);
            //
        }
        //
        new TableInvertBasicRepport(tableRowsList);
        //
    }

    /**
     * This one is called when adding a new row to a table
     *
     * @param table
     * @param list
     */
    public void addRow(JTable table, LinkedList<Integer> list) {
        HelpA.addRowJTable(table);
        //
        int added_row_nr = table.getRowCount();
        //
        list.add(added_row_nr - 1);
        //
    }

    /**
     * This is called when a editing of a table is done
     *
     * @param row_id
     * @param listChanged
     * @param listAdded
     * @param table
     */
    public void addPotentiallyUnsavedEntries(int row_id, LinkedList<Integer> listChanged, LinkedList<Integer> listAdded, JTable table) {
        if (listChanged.contains(row_id) == false && listAdded.contains(row_id) == false) {
            listChanged.add(row_id);
        }
    }

    /**
     * SUPER IMPORTANT AND EFFECTIVE METHOD [2020-07-21]
     */
    public void refreshTableInvert() {
        TableInvert ti = (TableInvert) TABLE_INVERT;
        ti.refreshTableRows();
    }

    /**
     * SUPER IMPORTANT AND EFFECTIVE METHOD [2020-07-21]
     *
     * @param ti
     */
    public void refreshTableInvert(Table tableInvert) {
        TableInvert ti = (TableInvert) tableInvert;
        ti.refreshTableRows();
    }

    /**
     *
     * @param container
     * @param tableInvert
     */
    public void showTableInvertIn(JComponent container, Table tableInvert) {
        showTableInvert(container, tableInvert);
    }

    /**
     * OBS! Mostly used once at start up [2020-07-21]
     *
     * @param container
     * @param tableInvert
     */
    public void showTableInvert(final JComponent container, final Table tableInvert) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Thread x = new Thread(new RepaintThread(container, tableInvert));
                x.setPriority(Thread.MAX_PRIORITY);
                x.start();
            }
        });

    }

    public void showTableInvert(final JComponent container) {
        //
        if (TABLE_INVERT == null) {
            return;
        }
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Thread x = new Thread(new RepaintThread(container, TABLE_INVERT));
                x.setPriority(Thread.MAX_PRIORITY);
                x.start();
            }
        });
    }

    class RepaintThread implements Runnable {

        private final JComponent container;
        private final Table table;

        public RepaintThread(JComponent container, Table table) {
            this.container = container;
            this.table = table;
        }

        @Override
        public void run() {
            //
            if (table == null) {
                return;
            } else {
                container.removeAll();
            }
            //OBS! OBS! Don't forget that the JPanel to which "TABLE_INVERT" is added
            //shall have Layout = "GridLayout"
            container.add(table.getTable());
            container.invalidate();
            container.validate();
//            container.updateUI(); // This causes "JavaEventQue Exceptions"
            container.repaint();
            //
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    SwingUtilities.updateComponentTreeUI(table);
                }
            });
        }
    }

    /**
     * Is called from the "public RowDataInvert[] getConfigTableInvert()". Like:
 if (MC_RECIPE.SHOW_EXTRA_PARAMS_RECIPE_TABLE_INVERT == false) { String[]
 toRemove = new String[]{T_INV.LANG("PRICE/KG"), T_INV.LANG("PRICE/L")};
 return removeFromTableConfigInvert(rows, toRemove); } else { return rows;
 }
     *
     * @param arr
     * @param columnsToRemove
     * @return
     */
    public RowDataInvert[] removeFromTableConfigInvert(RowDataInvert[] arr, String[] columnsToRemove) {
        //
        ArrayList<RowDataInvert> list = new ArrayList<RowDataInvert>();
        //
        list.addAll(Arrays.asList(arr));
        //
        RowDataInvert[] toReturn = new RowDataInvert[list.size() - columnsToRemove.length];
        //
        int flag = 0;
        //
        for (RowDataInvert rdi : list) {
            if (a01(columnsToRemove, rdi.getFieldNickName()) == false) {
                toReturn[flag] = rdi;
                flag++;
            }
        }
        //
        return toReturn;
    }

    private boolean a01(String[] columnsToRemove, String currColName) {
        for (String colName : columnsToRemove) {
            if (currColName.equals(colName)) {
                return true;
            }
        }
        return false;
    }

    public void paint_selected_rows_a(final LinkedList<Integer> rowsToPaint, final JTable jTable, final Color color) {
        jTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                setBackground(null);
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                for (Integer r : rowsToPaint) {
                    if (row == r) {
                        c.setBackground(color);
                        return c;
                    }
                }

                return this;
            }
        });
        //
        jTable.repaint();
    }

    public void unpaintAllRows_a(JTable table) {
        paint_selected_rows_a(new LinkedList<Integer>(), table, null);
    }

    /**
     *
     *
     * @param rowsToHighlight
     * @param jTable
     * @param idColumnName
     * @param color
     */
    public void paint_selected_rows_b(final LinkedList<Integer> rowsToHighlight, final JTable jTable, final String idColumnName, final Color color) {
        jTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                setBackground(null);
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                //
                for (Integer planid : rowsToHighlight) {
                    if (row == HelpA.getRowByValue(table, idColumnName, "" + planid)) {
                        c.setBackground(color);
                        return c;
                    }
                }

                return this;
            }
        });
        //
        jTable.repaint();
    }

    public void unpaintRow_b(JTable table, final String id, String idColumnName, final LinkedList<Integer> rowsToHighlight) {
        Iterator<Integer> iter = rowsToHighlight.iterator();
        //
        while (iter.hasNext()) {
            //
            int res;
            //
            synchronized (iter) {
                res = iter.next();
            }
            //
            if (res == Integer.parseInt(id)) {
                iter.remove();// this removes from the list also
            }
            //
        }
        //
        paint_selected_rows_b(rowsToHighlight, table, idColumnName, Color.lightGray);
    }
}
