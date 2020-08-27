/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import MyObjectTable.ControlsActionsIF;
import MyObjectTable.RowData;
import MyObjectTable.Table;
import MyObjectTable.TableData;
import MyObjectTable.TableRow;
import MyObjectTable.TableRowHeaders;
import forall.HelpA;
import forall.SqlBasicLocal;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentEvent;
import java.sql.DataTruncation;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author mcab
 */
public class TableInvert extends Table implements ControlsActionsIF {

    private String TABLE_NAME = "";
    private boolean SHOW_UNITS = false;
    private boolean IS_EMPTY = false;
    private Basic TABLE_INVERT_CONSUMER;

    public TableInvert(TableData data, int row_layout, int row_height, int[] column_width_percent, String tableName) {
        super(data, row_layout, row_height, column_width_percent);
        this.TABLE_NAME = tableName;
    }

    public TableInvert(TableData data, int row_layout, int row_height, int[] column_width_percent, String tableName, Basic tableInvertConsumer) {
        super(data, row_layout, row_height, column_width_percent);
        this.TABLE_NAME = tableName;
        this.TABLE_INVERT_CONSUMER = tableInvertConsumer;
    }

    public Basic getTableInvertConsumer() {
        return TABLE_INVERT_CONSUMER;
    }

    public void setShowUnits(boolean show) {
        SHOW_UNITS = show;
    }

    public boolean getShowInits() {
        return SHOW_UNITS;
    }

    public void setMargin(int top, int left, int bottom, int right) {
        setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }

    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    @Override
    public void componentResized(ComponentEvent ce) {
        resizeRows();
    }

    @Override
    public void resizeRows() {
        //
        for (TableRow row : rows_list) {
            //
            row.setPreferredSize(new Dimension(getWidth() - 10, ROW_HEIGHT));
            //
            // [2020-07-21] OBS! Turned out to be extreamly important for Graphic Updating
            row.updateUI();
            //
        }
    }

    @Override
    public void initTable() {
        // 
//        setBackground(Color.yellow); // Good for debuging
        //
        double height = (rows_list.size() * ROW_HEIGHT) * 1.13;//1.13
        RowData rcd = (RowData) TABLE_DATA.get(0);
        //
        //
        setPreferredSize(new Dimension(rcd.getNrColumns() * 150, (int) Math.round(height)));
        scrollPane = new JScrollPane(this);
//        scrollPane.setViewportBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.addComponentListener(this);

    }

    /**
     * Can be good for debugging [2020-07-21]
     */
    public void printRowList() {
        //
        System.out.println("PRINT ROW LIST:");
        //
        for (TableRow row : rows_list) {
            //
            TableRowInvert tri = (TableRowInvert) row;
            //
            System.out.println("w:" + row.getWidth() + " / h:" + row.getHeight() + " / visible: " + tri.getRowConfig().getVisible());
            //
        }
    }

    /**
     * SUPER IMPORTANT [2020-07-21]
     */
    public void refreshTableRows() {
        deleteRowsGraphic();
        addExistingRowsToTable();
    }

    /**
     * [2020-07-21]
     * This method is about adding graphical components
     */
    private void addExistingRowsToTable() {
        //
        for (TableRow tableRow : rows_list) {
            //
            TableRowInvertB row = (TableRowInvertB) tableRow;
            //
            RowDataInvert rdi = row.getRowConfig();
            //
            if (rdi.getVisible() == false) {
                row.setVisible(false);
            } else {
                row.setVisible(true);
            }
            //
            this.addRow_B(row);
            //
        }
        // OBS! PAY ATTENTION AT THIS!! THIS ONE WORKS LIKE A KIND OF REFRESH!!
        // EXTREAMLY IMPORTANT [2020-07-21]
        resizeRows();
        //
    }

    /**
     * [2020-07-21]
     */
    private void addRow_B(TableRow row) {
        if (row instanceof TableRowHeaders) {
            this.add(row);
        } else {
            //
            this.add(row);
            //
        }
    }

    @Override
    public void addDataToTable() {
        //
        for (Object row_column_data_entry : TABLE_DATA) {
            //
            RowDataInvert rcd;
            TableRowInvert row;
            //
            if (row_column_data_entry instanceof RowDataInvertB) {
                rcd = (RowDataInvertB) row_column_data_entry;
                row = new TableRowInvertB(rcd, "-1", ROW_COUNTER, TABLE_ROW_LAYOUT, this);
            } else if (row_column_data_entry instanceof RowDataInvert) {
                rcd = (RowDataInvert) row_column_data_entry;
                row = new TableRowInvert(rcd, "-1", ROW_COUNTER, TABLE_ROW_LAYOUT, this);
            } else {
                rcd = null;
                row = null;
            }
            //
            rows_list.add(row);
            //
            if (rcd.getVisible() == false) {
                row.setVisible(false);
            }
            //
            this.addRow(row);
            //
            this.ROW_COUNTER++;
        }
        //
//        resizeRows();
        //
    }

    public void addTableRowInvertListener(TableRowInvertListener tril) {
        for (TableRow row : rows_list) {
            TableRowInvert tri = (TableRowInvert) row;
            tri.addTableRowInvertListener(tril);
        }
    }

    public HashMap<String, ColumnValue> getColumnData(int colNr) {
        //
        HashMap<String, ColumnValue> map = new HashMap<>();
        //
        Set set = row_col_object__column_count__map.keySet();
        Iterator it = set.iterator();
        //
        while (it.hasNext()) {
            //
            Component c = (Component) it.next();
            int col_nr = row_col_object__column_count__map.get(c);
            //
            if (col_nr == colNr) {
                String nickName = row_col_object__column_nick_name__map.get(c);
//                String origName =  row_col_object__column_name__map.get(c);
                map.put(nickName, new ColumnValue(c));
            }
        }
        return map;
    }

    public void setTableEmpty(boolean not_empty) {
        if (not_empty) {
            this.IS_EMPTY = false;
        } else {
            this.IS_EMPTY = true;
        }
    }

    public boolean tableEmpty() {
        return IS_EMPTY;
    }

    public ArrayList getComponentsWithColNr(int colNr) {
        ArrayList list = new ArrayList();
        //
        Set set = row_col_object__column_count__map.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Component c = (Component) it.next();
            int col_nr = row_col_object__column_count__map.get(c);
            if (col_nr == colNr) {
                list.add(c);
            }
        }
        return list;
    }

    public void addToUnsaved(String rowName, int column) {
        Object obj = getComponentAt(rowName, column);
        TableRow tableRow = getRow(0);
        TableRowInvert tri = (TableRowInvert) tableRow;
        tri.add_to_unsaved(obj);
    }

    public void clearAllRows() {
        //
        for (Object rowInvert : rows_list) {
            //
            TableRowInvert tri = (TableRowInvert) rowInvert;
            //
            Object obj;
            //
            try {
                obj = tri.getComponentAt(2);
            } catch (Exception ex) {
                obj = tri.getComponentAt(1);
            }
            //
            if (obj instanceof JTextField) {
                JTextField jtf = (JTextField) obj;
                jtf.setText("");
            } else if (obj instanceof JComboBox) {
                JComboBox jcb = (JComboBox) obj;
                jcb.setSelectedItem("");
            }
        }
    }
    
     public void clearAllRowsJComboBoxSpecial(int jcomboSelecetedIndex) {
        //
        for (Object rowInvert : rows_list) {
            //
            TableRowInvert tri = (TableRowInvert) rowInvert;
            //
            Object obj;
            //
            try {
                obj = tri.getComponentAt(2);
            } catch (Exception ex) {
                obj = tri.getComponentAt(1);
            }
            //
            if (obj instanceof JTextField) {
                JTextField jtf = (JTextField) obj;
                jtf.setText("");
            } else if (obj instanceof JComboBox) {
                JComboBox jcb = (JComboBox) obj;
                jcb.setSelectedIndex(jcomboSelecetedIndex);
            }
        }
    }

    public void clearRows(int start, int notToClearFromEnd) {
        for (int i = start; i < rows_list.size() - notToClearFromEnd; i++) {
            Object rowInvert = rows_list.get(i);
            TableRowInvert tri = (TableRowInvert) rowInvert;
            //
            Object obj;
            //
            try {
                obj = tri.getComponentAt(2);
            } catch (Exception ex) {
                obj = tri.getComponentAt(1);
            }
            //
            if (obj instanceof JTextField) {
                JTextField jtf = (JTextField) obj;
                jtf.setText("");
            } else if (obj instanceof JComboBox) {
                JComboBox jcb = (JComboBox) obj;
                jcb.setSelectedItem("");
            }
        }
    }

    public void clearRows(int start) {
        clearRows(start, 0);
    }

    public RowDataInvert getRowConfig(int row) {
        TableRowInvert rowInvert = getRow(row);
        return rowInvert.getRowConfig();
    }

    /**
     * [2020-07-20]
     *
     * @param columnNameNotNickName
     * @return
     */
    public TableRowInvert getRowByColName(String columnNameNotNickName) {
        return (TableRowInvert) row__col_name__tablerowinvert_map.get(columnNameNotNickName);
    }

    @Override
    public TableRowInvert getRow(int i) {
        return (TableRowInvert) this.getComponent(i);
    }

    public void setValueAt(String rowName, Object value, int column) {
        TableRow tableRow = getRow(getRowNrByName(rowName));
        tableRow.setValueAt(column, value);
    }

    public void setValueAt(String rowName, Object value) {
        TableRow tableRow = getRow(getRowNrByName(rowName));
        tableRow.setValueAt(1, value);
    }
    
     public void setColorAt(String rowName, Color color) {
        TableRow tableRow = getRow(getRowNrByName(rowName));
        tableRow.setColorAt(1, color);
    }

    public Object getComponentAt(String rowName, int column) {
        TableRow tableRow = getRow(getRowNrByName(rowName));
        return tableRow.getComponentAt(column);
    }
    
    public ColumnDataEntryInvert getColumnDataEntryInvertAt(String rowName){
        TableRow tableRow = getRow(getRowNrByName(rowName));
        JLinkInvert jli = tableRow.getLinkInvertAt(1);
        return jli.getChildObject();
    }
    
    public ColumnDataEntryInvert getColumnDataEntryInvertAt(String rowName, int column){
        TableRow tableRow = getRow(getRowNrByName(rowName));
        JLinkInvert jli = tableRow.getLinkInvertAt(column);
        return jli.getChildObject();
    }
    
    public Object getObjectAt(String rowName){
        TableRow tableRow = getRow(getRowNrByName(rowName));
        return tableRow.getObjectAt(1);
    }

    public String getValueAt(String rowName) {
        TableRow tableRow = getRow(getRowNrByName(rowName));
        String value = tableRow.getValueAt(1);
        return value;
    }

    public String getValueAt(String rowName, int column) {
        TableRow tableRow = getRow(getRowNrByName(rowName));
        String value = tableRow.getValueAt(column);
        return value;
    }

    public int getColumnCount() {
        TableRowInvert tableRowInvert = (TableRowInvert) rows_list.get(0);
        return tableRowInvert.getColumnCount();
    }

    public int getRowNrByName(String name) {
        return col_name__row_nr__map.get(name) - 1;
    }

    public String getCurrentColumnName(Object source) {
        return row_col_object__column_name__map.get((Component) source);
    }

    public String getCurrentColumnNickName(Object source) {
        return row_col_object__column_nick_name__map.get((Component) source);
    }

    public TableRowInvert getTableRowInvertByComponent(Object field) {
        return (TableRowInvert) row___col_object__map.get((Component) field);
    }

    class Entry {

        private UnsavedEntryInvert unsavedEntryInvert;
        private String RowName;

        public Entry(UnsavedEntryInvert unsavedEntryInvert, String RowName) {
            this.unsavedEntryInvert = unsavedEntryInvert;
            this.RowName = RowName;
        }

        public String getRowName() {
            return RowName;
        }

        public UnsavedEntryInvert getUnsavedEntryInvert() {
            return unsavedEntryInvert;
        }
    }

    public void handleAutomaticFieldUpdate(String rowName, String value) {
        Set<Entry> list = new HashSet<Entry>();
        //
        Set set = unsaved_entries_map.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Object key = it.next();
            UnsavedEntryInvert unsavedEntryInvert = unsaved_entries_map.get(key);
            list.add(new Entry(unsavedEntryInvert, rowName));
        }
        //
        for (Entry entry : list) {
            handle(entry.getUnsavedEntryInvert(), entry.getRowName(), value);
        }
        //
    }

    private void handle(UnsavedEntryInvert unsavedEntryInvert, String rowName, String value) {
        int colNr = unsavedEntryInvert.getColumnNr();
        //
        if (columnNameExists(rowName, colNr)) {
            changeValueNoSave(rowName, colNr, value);
        }
    }

    private void changeValueNoSave(String rowName, int colNr, String value) {
        setValueAt(rowName, value, colNr);
        addToUnsaved(rowName, colNr);
    }

    @Override
    public void applyChanges() {
        //
        Set set = unsaved_entries_map.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Object key = it.next();
            UnsavedEntryInvert unsavedEntryInvert = unsaved_entries_map.get(key);

            String updateOtherTablesBeforeInstruction = unsavedEntryInvert.getUpdateOtherTablesBefore();

            if (updateOtherTablesBeforeInstruction.isEmpty() == false) {
                UpdateBefore.updateBefore(unsavedEntryInvert, getSql(), updateOtherTablesBeforeInstruction);
            }

            boolean ok = updateFieldString(
                    unsavedEntryInvert.getTableName(),
                    unsavedEntryInvert.getColumnName(),
                    unsavedEntryInvert.getValue(),
                    unsavedEntryInvert.getPrimareyOrForeignKeyName(),
                    unsavedEntryInvert.getDbID(),
                    unsavedEntryInvert.isString(),
                    unsavedEntryInvert.keyIsString());
            //
            try {
                //
                if (!ok) {
                    return;
                }
                //
                Component c = (Component) unsavedEntryInvert.getDataField();
                c.setForeground(Color.green);
                //
                if (c instanceof JComboBox) {
                    JComboBox box = (JComboBox) c;
                    box.setBorder(BorderFactory.createLineBorder(Color.green, 2));
                }
                //
            } catch (Exception ex) {
                Logger.getLogger(TableInvert.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //
        unsaved_entries_map.clear();
        //
    }

    private boolean updateFieldString(String tableName,
            String columnName,
            String value,
            String keyName,
            String db_id,
            boolean isString,
            boolean keyIsString) {
        //
        try {
            //
            SqlBasicLocal sql = getSql();
            //
            String q = "UPDATE " + tableName
                    + " SET [" + columnName + "]=" + "?" + ""
                    + " WHERE " + keyName + "=" + "?" + "";
            //
            System.out.println("SQL SAVE TABLE INVERT: " + q);
            //
            sql.prepareStatement(q);
            //
            String dateFormat = HelpA.define_date_format(value);
            //
            if (dateFormat != null) {
                long millis = HelpA.dateToMillisConverter3(value, dateFormat);
                Timestamp timestamp = new Timestamp(millis);
                sql.getPreparedStatement().setTimestamp(1, timestamp);
            } else {
                if (isString) {
                    sql.getPreparedStatement().setString(1, value);
                } else {
                    sql.getPreparedStatement().setInt(1, Integer.parseInt(value));
                }
            }
            //
            if (keyIsString) {
                sql.getPreparedStatement().setString(2, db_id);
            } else {
                sql.getPreparedStatement().setInt(2, Integer.parseInt(db_id.trim()));
            }
            //
            sql.getPreparedStatement().executeUpdate();
            //
        } catch (DataTruncation ex) {
            HelpA.showNotification_Data_Truncation_Error();
            Logger.getLogger(TableInvert.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (SQLException ex) {
            HelpA.showNotification_SQL_Error();
            Logger.getLogger(TableInvert.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //
        return true;

//        return "UPDATE " + tableName
//                + " SET [" + columnName + "]=" + value + ""
//                + " WHERE " + keyName + "=" + db_id + "";
    }

    private boolean columnNameExists(String rowName, int colNr) {
        try {
            getValueAt(rowName, colNr);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
