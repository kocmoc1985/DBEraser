/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * FQ.java
 *
 * Created on 2013-mar-11, 11:57:31
 */
package FixedQueryTool;

import BuhInvoice.DB;
import BuhInvoice.GP_BUH;
import BuhInvoice.HelpBuh;
import BuhInvoice.InvoiceB;
import BuhInvoice.JSon;
import DatabaseBrowser.FQ;
import forall.SimpleLoggerLight;
import com.jezhumble.javasysmon.JavaSysMon;
import forall.GP;
import forall.HelpA;
import forall.Sql_A;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Frame;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class FixedQueryTool_FED_LIMITS extends javax.swing.JFrame implements Runnable {

    private JavaSysMon monitor = new JavaSysMon();
    private Sql_A sql = new Sql_A();
    //==========================
    private String DB_TYPE = "";
    private final String MS_SQL = "mssql";
    private final String MY_SQL = "mysql";
    private final String ODBC = "odbc";
    //==========================
    private final String LOG_FILE = "sql_log.log";
    private final String QUERY_OK_LOG_FILE = "queries_ok.log";
    private final String QUERY_WRONG_LOG_FILE = "queries_wrong.log";
    private final Font FONT_1 = new Font("Arial", Font.BOLD, 16);
    //==========================
    //=========================

    //
    private DefaultTableModel tableModel;
    //
    private static int SELECTED_ROW = -1;
    //
    private static final String TABLE_NAME_A_0 = "npmc_alarms_active";
    private static final String QUERY_MAIN_A_0 = "select * from " + TABLE_NAME_A_0 + " order by alarm_nick_name desc";
    //
    private static final String TABLE_NAME_A = "npmc_limits_by_recipe";
    private static final String QUERY_MAIN_A = "select * from " + TABLE_NAME_A + " order by recipe desc";
    //
    private static final String TABLE_NAME_B = "npmc_limits_all_recipes";
    private static final String QUERY_MAIN_B = "select * from " + TABLE_NAME_B + " order by limit_name desc";
    //
    //
    private static String CURRENT_TABLE = TABLE_NAME_A;
    private String CURRENT_QUERY = QUERY_MAIN_A;
    //
    public static int MAX_ROWS = 100000;
    //
    private DateChooserWindow_FED chooserWindow_FED;
    //
    private static long ONE_DAY_MILLIS = 86400000;

    /**
     * Creates new form FQ
     */
    public FixedQueryTool_FED_LIMITS() {
        initComponents();
//        init_other();
        connect();
        this.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL).getImage()); // maximize window
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("OnlineAlarm limits (" + monitor.currentPid() + ")");
        //
        jButton1.setEnabled(false);
        //
        show_data__table_a();
        //

    }

    private String get_date_time_plus_some_time_in_ms(String date, String date_format, long time_to_plus) {
        long ms = dateToMillisConverter3(date, date_format);
        long new_date_in_ms = ms + time_to_plus;
        String new_date = millisToDateConverter("" + new_date_in_ms, date_format);
        return new_date;
    }

    private long dateToMillisConverter3(String date, String date_format) {

        DateFormat formatter = new SimpleDateFormat(date_format);
        try {
            return formatter.parse(date).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(FixedQueryTool_FED_LIMITS.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    private String millisToDateConverter(String millis, String format) {
        DateFormat formatter = new SimpleDateFormat(format); // this works to!
        long now = Long.parseLong(millis);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now);
        return formatter.format(calendar.getTime());
    }

    public void search_by_date_btn_pressed() {
        //#ALARMS-EXPORT-FROM-ONE-COM#
        HelpBuh.DOMAIN_LA = false; // DOMAIN_LA = false: means domain "mixcont.com" active
        //
        HelpA.clearAllRowsJTable(jTable1);
        //
        fillJTable_header_alarms_report(jTable1);
        //
        String json;
        //
        String date_from = chooserWindow_FED.getDateFrom();
        String date_to = chooserWindow_FED.getDateTo();
        date_to = get_date_time_plus_some_time_in_ms(date_to, "yyyy-MM-dd", ONE_DAY_MILLIS); // added on 2024-12-16
        //
        json = getSELECT_trippleWhere("company", "federalmogul", "date_", date_from, "date_tmp", date_to);
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP("_json_requests", "get_npmc_alarms_time_period", json);
            //
            // Very important replacement 2024-06-19
            json_str_return = json_str_return.replaceAll("start;", "start");
            json_str_return = json_str_return.replaceAll("end;", "end");
            json_str_return = json_str_return.replaceAll("limit;", "limit");
            json_str_return = json_str_return.replaceAll("value;", "value");
            json_str_return = json_str_return.replaceAll("_", " ");
            json_str_return = json_str_return.replaceAll("->", "    ");
            json_str_return = json_str_return.replaceAll("order ", "order_");
            json_str_return = json_str_return.replaceAll("date ", "date_");
            //
            ArrayList<HashMap<String, String>> alarms = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            for (HashMap<String, String> invoice_map : alarms) {
                addRowJtable(invoice_map, jTable1);
            }
            //
        } catch (Exception ex) {
            Logger.getLogger(InvoiceB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        HelpA.setColumnWidthByIndex(0, jTable1, 10.0);
        HelpA.setColumnWidthByIndex(1, jTable1, 10.0);
        HelpA.setColumnWidthByIndex(2, jTable1, 10.0);
        HelpA.setColumnWidthByIndex(3, jTable1, 60.0);
        HelpA.setColumnWidthByIndex(4, jTable1, 10.0);
        //
    }

    protected void fillJTable_header_alarms_report(JTable table_) {
        //
        //
        JTable table = table_;
        //
        String[] headers = {
            "RECIPE",
            "ORDER",
            "BATCH",
            "ALARM",
            "DATE"
        };
        //
        table.setModel(new DefaultTableModel(null, headers));
        //
    }

    private void addRowJtable(HashMap<String, String> map, JTable table) {
        //
        Object[] jtableRow = new Object[]{
            map.get("recipe"),
            map.get("order_"), // hidden
            map.get("batch"),
            map.get("alarmdescr"),
            map.get("date_")
        };
        //
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(jtableRow);
        //
    }

    private String getSELECT_trippleWhere(String whereColName, String whereValue, String whereColName_b, String whereValue_b, String whereColName_c, String whereValue_c) {
        //
        HashMap<String, String> map = new HashMap<>();
        //
        map.put("where", whereColName); // $whereCoulunName
        map.put(whereColName, whereValue); // $whereValue
        //
        map.put("where_b", whereColName_b);
        map.put(whereColName_b, whereValue_b);
        //
        map.put("where_c", whereColName_c);
        map.put(whereColName_c, whereValue_c);
        //
        return JSon.hashMapToJSON(map);
    }

    private void change_table_columns_names() {
        HelpA.changeTableHeaderTitleOfOneColumn(jTable1, "nr", "NR");
        HelpA.changeTableHeaderTitleOfOneColumn(jTable1, "lot", "LOT");
        HelpA.changeTableHeaderTitleOfOneColumn(jTable1, "part", "PART");
        HelpA.changeTableHeaderTitleOfOneColumn(jTable1, "quantity", "QUANTITY");
        HelpA.changeTableHeaderTitleOfOneColumn(jTable1, "test", "TEST");
        HelpA.changeTableHeaderTitleOfOneColumn(jTable1, "date_recieve", "DATE RECIEVE");
        HelpA.changeTableHeaderTitleOfOneColumn(jTable1, "date_scan", "DATE SCAN");
    }

    private void init_other() {
        //========
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? new Color(244, 244, 252) : Color.WHITE);
                return c;
            }
        });
    }

    private void paint_selected_row(final int row_) {
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row == row_) {
                    c.setBackground(new Color(244, 244, 252));
                    c.setBackground(Color.LIGHT_GRAY);
                    return c;
                } else {
                    c.setBackground(Color.WHITE);
                    return c;
                }
            }
        });
        //
        SwingUtilities.updateComponentTreeUI(this.getContentPane());
    }

    private void connect() {
        Properties properties = GP.FREE_QUERY_AND_NOT_ONLY_PROPS;
        String odbc = properties.getProperty("odbc", "");
        String odbc_user = properties.getProperty("odbc_user", "");
        String odbc_name = properties.getProperty("odbc_name", "");
        //==========================================
        String dbtype = properties.getProperty("db_type", "");
        String host = properties.getProperty("host", "");
        String port = properties.getProperty("port", "");
        String db_name = properties.getProperty("db_name", "");
        String user = properties.getProperty("user", "");
        String pass = properties.getProperty("pass", "");
        try {
            if (dbtype.equals(MS_SQL)) {
                DB_TYPE = MS_SQL;
                sql.connect(host, port, db_name, user, pass);
            } else if (dbtype.equals(MY_SQL)) {
                DB_TYPE = MY_SQL;
                sql.connectMySql(host, port, db_name, user, pass);
            } else if (dbtype.equals(ODBC)) {
                DB_TYPE = ODBC;
                sql.connectODBC(odbc_user, odbc_name, odbc);
            }
            addToOutPutWindow(jTextArea_output, "Connection to " + host + " / " + db_name + " established");

        } catch (SQLException ex) {
            addToOutPutWindow(jTextArea_output, "Connection to " + host + " / " + db_name + " failed: " + ex);
            Logger.getLogger(FQ.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addToOutPutWindow(JTextArea jtxt, String str) {
        jtxt.append("\n " + HelpA.get_proper_date_time_same_format_on_all_computers() + " " + str);
    }

    private String[] getHeaders(ResultSet rs) throws SQLException {
        ResultSetMetaData meta; // Returns the number of columns
        String[] headers; // skapar en ny array att lagra titlar i
        meta = rs.getMetaData(); // Den parameter som skickas in "ResultSet rs" innehåller Sträng vid initialisering
        headers = new String[meta.getColumnCount()]; // ger arrayen "headers" initialisering och anger antalet positioner
        for (int i = 0; i < headers.length; i++) {
            headers[i] = meta.getColumnLabel(i + 1);
        }
        return headers;
    }

    private Object[][] getContent(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmt;
        Object[][] content;
        int columns;
        rsmt = rs.getMetaData(); // får in antalet columner
        columns = rsmt.getColumnCount(); // retrieves number of columns och lagrar det i "columns".

        int rows = 0;
        while (rs.next()) {
            rows++;
        }

        rs = sql.execute(CURRENT_QUERY);

        if (rows < MAX_ROWS) {
            content = new Object[rows][columns]; // ger arrayen content som är en "Object"
        } else {
            content = new Object[MAX_ROWS][columns];
        }

        int row = 0;
        while (rs.next() && row < (MAX_ROWS - 1)) {
            for (int col = 0; col < columns; col++) {
                try {
                    content[row][col] = rs.getObject(col + 1);
                } catch (Exception ex) {
                    //do nothing
                }
            }
            row++;
        }

        return content;
    }

    private void build_table(ResultSet rs) {
        if (rs == null) {
            return;
        }
        try {
            String[] headers = getHeaders(rs);
            Object[][] content = getContent(rs);
            tableModel = new DefaultTableModel(content, headers);
            this.jTable1.setModel(tableModel);
            jTable1.setAutoCreateRowSorter(true);
        } catch (SQLException ex) {
            Logger.getLogger(FQ.class.getName()).log(Level.SEVERE, null, ex);
        }

//        jTable1.setSelectionBackground(Color.yellow);
    }

    private void run_query_by_thread() {
        Thread x = new Thread(this);
        x.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea_output = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton_edit = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea_output.setColumns(20);
        jTextArea_output.setRows(5);
        jScrollPane2.setViewportView(jTextArea_output);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(jTable1);

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton2.setText("A");
        jButton2.setToolTipText("Limits by recipe");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_basic/save.png"))); // NOI18N
        jButton1.setToolTipText("Apply changes");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_basic/edit.png"))); // NOI18N
        jButton_edit.setToolTipText("Select row to be modified");
        jButton_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_editActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton4.setText("B");
        jButton4.setToolTipText("Limits all recipes");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton5.setText("C");
        jButton5.setToolTipText("Alarms by date");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_basic/new.png"))); // NOI18N
        jButton6.setToolTipText("Export table to Excel");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton3.setText("A0");
        jButton3.setToolTipText("Enable / disable");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 291, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(162, 162, 162)
                        .addComponent(jButton_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        show_data__table_a();
        disableEnbaleEditButton(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void show_data__table_a() {
        CURRENT_TABLE = TABLE_NAME_A;
        CURRENT_QUERY = QUERY_MAIN_A;
        run_query_by_thread();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        update_row_2();
        //
        jButton1.setEnabled(false);
        //
        JOptionPane.showMessageDialog(null, "Changes saved");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_editActionPerformed
        SELECTED_ROW = jTable1.getSelectedRow();
        paint_selected_row(SELECTED_ROW);
        //
        jButton1.setEnabled(true);
        //
    }//GEN-LAST:event_jButton_editActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        show_data__table_b();
        disableEnbaleEditButton(true);
    }//GEN-LAST:event_jButton4ActionPerformed


    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        chooserWindow_FED = new DateChooserWindow_FED(this);
        HelpA.centerAndBringToFront(chooserWindow_FED);
        disableEnbaleEditButton(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        HelpA.jTableToCSV(jTable1, true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        show_data__table_a0();
        disableEnbaleEditButton(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void show_data__table_a0() {
        CURRENT_TABLE = TABLE_NAME_A_0;
        CURRENT_QUERY = QUERY_MAIN_A_0;
        run_query_by_thread();
    }
    
    private void disableEnbaleEditButton(boolean enabled) {
        jButton_edit.setEnabled(enabled);
    }

    private void show_data__table_b() {
        CURRENT_TABLE = TABLE_NAME_B;
        CURRENT_QUERY = QUERY_MAIN_B;
        run_query_by_thread();
    }

    private void update_row_2() {
        //
        // This is extreamly important
        jTable1.editCellAt(0, 0);
        //
        String q;
        //
        if (CURRENT_TABLE.equals(TABLE_NAME_A)) {
            delete_selected_row(CURRENT_TABLE, "recipe");
        } else if (CURRENT_TABLE.equals(TABLE_NAME_A_0)) {
            delete_selected_row(CURRENT_TABLE, "alarm_nick_name");
        }else if (CURRENT_TABLE.equals(TABLE_NAME_B)) {
            delete_selected_row(CURRENT_TABLE, "limit_name");
        }
        //
        q = build_insert_query(CURRENT_TABLE, jTable1, SELECTED_ROW, false);
        //
        execute_query_with_result_output(q);
        //
        paint_selected_row(-1); // remowe paint
    }

//    private String build_insert_query(String table_name, JTable jTable, int selected_row) {
//        StringBuilder stringBuilder = new StringBuilder("insert into " + table_name + " values(");
//        //
//        int column_count = jTable.getModel().getColumnCount();
//        for (int i = 0; i < column_count; i++) {
//            //
//            String formatted_value = isString("" + jTable.getValueAt(selected_row, i), true);
//            stringBuilder.append(formatted_value);
//            stringBuilder.append(",");
//        }
//        //
//        //
//        stringBuilder.deleteCharAt(stringBuilder.toString().length() - 1);
//        stringBuilder.append(")");
//        return stringBuilder.toString();
//    }
//    
    private String build_insert_query(String table_name, JTable jTable, int selected_row, boolean auto_increment) {
        //
//        if (auto_increment && DB_TYPE.equals(MY_SQL)) {
//           auto_increment = false;
//        }
        //
        StringBuilder stringBuilder = new StringBuilder("insert into " + table_name + " values(");
        //
        boolean first_record = true;
        //
        int column_count = jTable.getModel().getColumnCount();
        for (int i = 0; i < column_count; i++) {
            if (first_record && auto_increment) {
                first_record = false;
                continue;
            }
            //
            String formatted_value = isString("" + jTable.getValueAt(selected_row, i), true);
            stringBuilder.append(formatted_value);
            stringBuilder.append(",");
        }
        //
        //
        stringBuilder.deleteCharAt(stringBuilder.toString().length() - 1);
        stringBuilder.append(")");
        String q = stringBuilder.toString();
        System.out.println("q: " + q);
        return q;
    }

    /**
     *
     * @param value
     * @param only_string_values - this means that table contains only string
     * values
     * @return
     */
    private String isString(String value, boolean only_string_values) {
        //
        if (only_string_values) {
            return "'" + value + "'";
        }
        //
        try {
            Double.parseDouble(value);
            return value;
        } catch (Exception e) {
            return "'" + value + "'";
        }
    }

    private void delete_selected_row(String table_name, String where_col_name) {
        String q = "delete from " + table_name + " where " + where_col_name + " = '" + jTable1.getValueAt(SELECTED_ROW, 0) + "'";
        execute_query_with_result_output(q);
    }

    private void cursorSetWaitCursor(boolean yesno) {
        Cursor defCursor = Cursor.getDefaultCursor();
        Cursor waitCursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
        Frame.getFrames()[0].setCursor(yesno ? waitCursor : defCursor);
    }

    private void execute_query_with_result_output(String query) {
        try {
            cursorSetWaitCursor(true);
            ResultSet rs = sql.execute(query);
            build_table(rs);
//            change_table_columns_names();
//            addToOutPutWindow("Executing ok: " + query);
            SimpleLoggerLight.logg(QUERY_OK_LOG_FILE, query);
            cursorSetWaitCursor(false);
        } catch (SQLException ex) {
//            addToOutPutWindow("Executing failed, see output file " + LOG_FILE);
            SimpleLoggerLight.logg(QUERY_WRONG_LOG_FILE, query);
            SimpleLoggerLight.logg(LOG_FILE, ex.toString());
            Logger.getLogger(FQ.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        execute_query_with_result_output(CURRENT_QUERY);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            //
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FixedQueryTool_FED_LIMITS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FixedQueryTool_FED_LIMITS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FixedQueryTool_FED_LIMITS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FixedQueryTool_FED_LIMITS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //
        if (HelpA.runningInNetBeans() == false) {
            HelpA.err_output_to_file();
        }
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FixedQueryTool_FED_LIMITS().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton_edit;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea_output;
    // End of variables declaration//GEN-END:variables
}
