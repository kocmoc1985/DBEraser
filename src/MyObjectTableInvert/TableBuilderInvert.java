/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import MyObjectTable.ShowMessage;
import MyObjectTable.Table;
import MyObjectTable.TableData;
import MyObjectTable.TableRow;
import forall.GP;
import forall.HelpA;
import forall.SqlBasicLocal;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mcab
 */
public class TableBuilderInvert {

    private final SqlBasicLocal sql;
    private final RowDataInvert[] CONFIG;
    private final boolean SHOW_UNITS;
    private final ShowMessage SM;
    private final String TABLE_NAME;
    private Color tableInvertBackgroundColor = null;

    public TableBuilderInvert(ShowMessage sm, SqlBasicLocal sql, RowDataInvert[] config, boolean showUnits, String tableName) {
        this.sql = sql;
        this.SM = sm;
        this.CONFIG = config;
        this.SHOW_UNITS = showUnits;
        this.TABLE_NAME = tableName;
    }

    public void showMessage(String msg) {
        SM.showMessage(msg);
    }

    public Table buildTable_B(Basic tableInvertConsumer, Color tableInvertBackground) {
        this.tableInvertBackgroundColor = tableInvertBackground;
        return buildTable_B(tableInvertConsumer);
    }

    /**
     * New [2020-07-10] Not using SQL Used by BuhInvoice
     *
     * @param tableInvertConsumer
     * @return
     */
    public Table buildTable_B(Basic tableInvertConsumer) {
        //
        if (CONFIG == null) {
            return null;
        }
        //
        TableData tableData = new TableData();
        //
        RowDataInvert[] ROWS = CONFIG;
        //
        for (int i = 0; i < ROWS.length; i++) {
            //
            RowDataInvertB CURR_ROW = (RowDataInvertB) ROWS[i];
            //
            CURR_ROW.addRowColumnData(new HeaderInvert(CURR_ROW.getFieldNickName(), CURR_ROW.getFieldOriginalName(), CURR_ROW.getTableName()));
            //
            if (SHOW_UNITS) {
                CURR_ROW.addRowColumnData(new HeaderInvert(CURR_ROW.getUnit(), true));
            }
            //
            //
            String orig_field_name = CURR_ROW.getFieldOriginalName();
//            String key_name = CURR_ROW.getPrimaryOrForeignKeyName();
            //
//            System.out.println("orig_field_name: " + orig_field_name);
            //
            int type = CURR_ROW.getType();
            //
            String value = "";
            //
            ColumnDataEntryInvert cde;
            //
            if (type == RowDataInvert.TYPE_COMMON) {
                cde = new ColumnDataEntryInvert(CURR_ROW.getInitialValue(), "", orig_field_name, CURR_ROW.getFieldNickName());
            } else {
                cde = new ColumnDataEntryInvert(CURR_ROW.getSpecialComponent(value), "", orig_field_name, CURR_ROW.getFieldNickName());
            }
            //
            //
            if (cde.getObject() == null) {
                cde.setObject("NULL");
            }
            //
            CURR_ROW.addRowColumnData(cde);
            //
            //
            //
            addAdditionalComponent(CURR_ROW, getDefaultRowComponents());
            //
            //
            tableData.addRowData(CURR_ROW);
        } //
       
        //
        return cretateTable_buildTable_B(tableData, tableInvertConsumer, tableInvertBackgroundColor);
        //
        //
//        TableInvert table = new TableInvert(tableData, TableRow.GRID_LAYOUT, 45, null, TABLE_NAME, tableInvertConsumer);
//        table.setShowUnits(SHOW_UNITS);
//        table.setTableEmpty(true);
//        HelpA.setTrackingToolTip(table, query);
        //
//        return table;
        //
    }

    private TableInvert cretateTable_buildTable_B(TableData tableData, Basic tableInvertConsumer,Color backgroundColor) {
        //
        TableInvert table;
        //
        if(backgroundColor == null){
             table = new TableInvert(tableData, TableRow.GRID_LAYOUT, 45, null, TABLE_NAME, tableInvertConsumer);
        }else{
            table = new TableInvert(tableData, TableRow.GRID_LAYOUT, 45, null, TABLE_NAME, tableInvertConsumer,backgroundColor);
        }
        //
        table.setShowUnits(SHOW_UNITS);
        table.setTableEmpty(true);
        return table;
    }

    public Table buildTable(String query, Basic tableInvertConsumer) throws SQLException {
        //
        return buildTable(query, tableInvertConsumer, TableRow.GRID_LAYOUT);
        //
    }

    public Table buildTable(String query, Basic tableInvertConsumer, int layout) throws SQLException {
        //
        if (CONFIG == null) {
            return null;
        }
        //
        ResultSet rs = sql.execute(query);
        //
//        if(TABLE_NAME.equals("recipe_detailed_1")){
//            System.out.println("");
//        }
        //
        TableData tableData = new TableData();
        //
        RowDataInvert[] ROWS = CONFIG;
        //
        boolean not_empty = rs.next();
        //
        for (int i = 0; i < ROWS.length; i++) {
            RowDataInvert CURR_ROW = (RowDataInvert) ROWS[i];
            //
            CURR_ROW.addRowColumnData(new HeaderInvert(CURR_ROW.getFieldNickName(), CURR_ROW.getFieldOriginalName(), CURR_ROW.getTableName()));
            //
            if (SHOW_UNITS) {
                CURR_ROW.addRowColumnData(new HeaderInvert(CURR_ROW.getUnit(), true));
            }
            //
            //Inserting empty fields in case if no entries
            if (not_empty == false) {//is empty!
                CURR_ROW.addRowColumnData(new ColumnDataEntryInvert("", "-1", CURR_ROW.getFieldOriginalName(), CURR_ROW.getFieldNickName()));
                tableData.addRowData(CURR_ROW);
                continue;
            }
            //
            //
            rs.beforeFirst();
            //
            String orig_field_name = CURR_ROW.getFieldOriginalName();
            String key_name = CURR_ROW.getPrimaryOrForeignKeyName();
            //
//            if(orig_field_name.equals("UpdatedOn")){
//                System.out.println("");
//            }
            //
            System.out.println("orig_field_name: " + orig_field_name);
            //
            int type = CURR_ROW.getType();
            //
            while (rs.next()) {
                //
                String value;
                //
                try {
                    Timestamp t = rs.getTimestamp(orig_field_name);
                    value = "" + millisToDefaultDate(t.getTime());
                } catch (Exception ex) {
                    try {
                        value = rs.getString(orig_field_name);
                    } catch (Exception ex2) {
                        //OBS! Shall attention shall be payed to this [2020-10-05]
                        Logger.getLogger(TableBuilderInvert.class.getName()).log(Level.SEVERE, null, ex);
                        value = null;
                    }
                }
                //
                //
                ColumnDataEntryInvert cde;
                //
                if (type == RowDataInvert.TYPE_COMMON) {
                    //
                    String val = value == null ? "" : value;
                    //
                    cde = new ColumnDataEntryInvert(val, rs.getString(key_name), orig_field_name, CURR_ROW.getFieldNickName());
                } else {
                    cde = new ColumnDataEntryInvert(CURR_ROW.getSpecialComponent(value), rs.getString(key_name), orig_field_name, CURR_ROW.getFieldNickName());
                }
                //
                //
                if (cde.getObject() == null) {
                    cde.setObject("NULL");
                }
                //
                CURR_ROW.addRowColumnData(cde);
                //
            }
            //
            //
            addAdditionalComponent(CURR_ROW, getDefaultRowComponents());
            //
            //
            tableData.addRowData(CURR_ROW);
        }
        //
        //
        TableInvert table = new TableInvert(tableData, layout, 45, null, TABLE_NAME, tableInvertConsumer);
        table.setShowUnits(SHOW_UNITS);
        table.setSql(sql);
        table.setTableEmpty(not_empty);
        HelpA.setTrackingToolTip(table, query);
        //
        return table;
        //
    }

    /**
     * [2020-11-16] OBS! Uses 2 ResultSet's In contrast to "buldTable_B" this
     * one builds the table from multiple rows. See the visio file
     * "InvertedTable.vsd" for more info
     */
    public Table buildTable_C_C(String query, String query_2, Basic tableInvertConsumer, int layout) throws SQLException {
        //
        if (CONFIG == null) {
            return null;
        }
        //
        ResultSet rs = sql.execute(query);
        //
        TableData tableData = new TableData();
        //
        RowDataInvert[] ROWS = CONFIG;
        //
        for (int i = 0; i < ROWS.length; i++) {
            //
            boolean hasNext = rs.next();
            //
            if (hasNext == false) {
                rs = sql.execute_2(query_2);
                rs.next();
            }
            //
            RowDataInvert CURR_ROW = (RowDataInvert) ROWS[i];
            //
            CURR_ROW.addRowColumnData(new HeaderInvert(CURR_ROW.getFieldNickName(), CURR_ROW.getFieldOriginalName(), CURR_ROW.getTableName()));
            //
            if (SHOW_UNITS) {
                CURR_ROW.addRowColumnData(new HeaderInvert(CURR_ROW.getUnit(), true));
            }
            //
            String orig_field_name = CURR_ROW.getFieldOriginalName();
            String key_name = CURR_ROW.getPrimaryOrForeignKeyName();
            //
            int type = CURR_ROW.getType();
            //
            //
            String value;
            //
            try {
                Timestamp t = rs.getTimestamp(orig_field_name);
                value = "" + millisToDefaultDate(t.getTime());
            } catch (Exception ex) {
                try {
                    value = rs.getString(orig_field_name);
                } catch (Exception ex2) {
                    //OBS! Shall attention shall be payed to this [2020-10-05]
                    Logger.getLogger(TableBuilderInvert.class.getName()).log(Level.SEVERE, null, ex);
                    value = null;
                }
            }
            //
            //
            ColumnDataEntryInvert cde;
            //
            if (type == RowDataInvert.TYPE_COMMON) {
                //
                String val = value == null ? "" : value;
                //
                cde = new ColumnDataEntryInvert(val, rs.getString(key_name), orig_field_name, CURR_ROW.getFieldNickName());
            } else {
                cde = new ColumnDataEntryInvert(CURR_ROW.getSpecialComponent(value), rs.getString(key_name), orig_field_name, CURR_ROW.getFieldNickName());
            }
            //
            //
            if (cde.getObject() == null) {
                cde.setObject("NULL");
            }
            //
            CURR_ROW.addRowColumnData(cde);
            //
            addAdditionalComponent(CURR_ROW, getDefaultRowComponents());
            //
            tableData.addRowData(CURR_ROW);
        }
        //
        //
        TableInvert table = new TableInvert(tableData, layout, 45, null, TABLE_NAME, tableInvertConsumer);
        table.setShowUnits(SHOW_UNITS);
        table.setSql(sql);
        table.setTableEmpty(false);
        HelpA.setTrackingToolTip(table, query);
        //
        return table;
        //
    }

    /**
     * Same as _C_C but using singe query
     *
     * @param query
     * @param tableInvertConsumer
     * @param layout
     * @return
     * @throws SQLException
     */
    private Table buildTable_C(String query, Basic tableInvertConsumer, int layout) throws SQLException {
        //
        if (CONFIG == null) {
            return null;
        }
        //
        ResultSet rs = sql.execute(query);
        //
        TableData tableData = new TableData();
        //
        RowDataInvert[] ROWS = CONFIG;
        //
        for (int i = 0; i < ROWS.length; i++) {
            //
            rs.next();
            //
            RowDataInvert CURR_ROW = (RowDataInvert) ROWS[i];
            //
            CURR_ROW.addRowColumnData(new HeaderInvert(CURR_ROW.getFieldNickName(), CURR_ROW.getFieldOriginalName(), CURR_ROW.getTableName()));
            //
            if (SHOW_UNITS) {
                CURR_ROW.addRowColumnData(new HeaderInvert(CURR_ROW.getUnit(), true));
            }
            //
            String orig_field_name = CURR_ROW.getFieldOriginalName();
            String key_name = CURR_ROW.getPrimaryOrForeignKeyName();
            //
            int type = CURR_ROW.getType();
            //
            //
            String value;
            //
            try {
                Timestamp t = rs.getTimestamp(orig_field_name);
                value = "" + millisToDefaultDate(t.getTime());
            } catch (Exception ex) {
                try {
                    value = rs.getString(orig_field_name);
                } catch (Exception ex2) {
                    //OBS! Shall attention shall be payed to this [2020-10-05]
                    Logger.getLogger(TableBuilderInvert.class.getName()).log(Level.SEVERE, null, ex);
                    value = null;
                }
            }
            //
            //
            ColumnDataEntryInvert cde;
            //
            if (type == RowDataInvert.TYPE_COMMON) {
                //
                String val = value == null ? "" : value;
                //
                cde = new ColumnDataEntryInvert(val, rs.getString(key_name), orig_field_name, CURR_ROW.getFieldNickName());
            } else {
                cde = new ColumnDataEntryInvert(CURR_ROW.getSpecialComponent(value), rs.getString(key_name), orig_field_name, CURR_ROW.getFieldNickName());
            }
            //
            //
            if (cde.getObject() == null) {
                cde.setObject("NULL");
            }
            //
            CURR_ROW.addRowColumnData(cde);
            //
            addAdditionalComponent(CURR_ROW, getDefaultRowComponents());
            //
            tableData.addRowData(CURR_ROW);
        }
        //
        //
        TableInvert table = new TableInvert(tableData, layout, 45, null, TABLE_NAME, tableInvertConsumer);
        table.setShowUnits(SHOW_UNITS);
        table.setSql(sql);
        table.setTableEmpty(false);
        HelpA.setTrackingToolTip(table, query);
        //
        return table;
        //
    }

    private String millisToDefaultDate(long millis) {
        //
        Locale locale = Locale.getDefault();
        //
        if (GP.IS_DATE_FORMAT_DE_RUS || locale == Locale.GERMAN || locale == Locale.GERMANY || locale.getCountry().equals("CH")) {
            return HelpA.millisToDateConverter("" + millis, GP.DATE_FORMAT_DE_RUS);
        } else {
            return HelpA.millisToDateConverter("" + millis, GP.DATE_FORMAT_COMMON);
        }
        //
    }

    /**
     * @deprecated @param millis
     * @return
     */
    private String millisToDefaultDate_(long millis) {
        TimeZone tz = TimeZone.getDefault();
        Calendar cal = Calendar.getInstance(tz);
        //
        int style = 3;
        //
        Locale locale = Locale.getDefault();
        //
        if (locale == Locale.GERMAN || locale == Locale.GERMANY || locale.getCountry().equals("CH")) {
            style = 2;
        }
        //
        DateFormat f1 = DateFormat.getDateInstance(style);
        cal.setTimeInMillis(millis);
        Date d = cal.getTime();
        return f1.format(d);
    }

    public HashMap<String, Object> getDefaultRowComponents() {
        HashMap<String, Object> componentMap = new HashMap<String, Object>();
        //
//        componentMap.put("Test", new JButton("Test"));
        //
        return componentMap;
    }

    private void addAdditionalComponent(RowDataInvert CURR_ROW, HashMap<String, Object> additionalComponentsMap) {
        Set set = additionalComponentsMap.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            Object component = additionalComponentsMap.get(key);
            CURR_ROW.addRowColumnData(new ColumnDataEntryInvert(component, "-1", "-", CURR_ROW.getFieldNickName()));
        }
    }
}
