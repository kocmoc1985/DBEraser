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

import FreeQuery.*;
import forall.SimpleLoggerLight;
import com.jezhumble.javasysmon.JavaSysMon;
import forall.GP;
import forall.HelpA_;
import forall.Sql_A;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Frame;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class FixedQueryTool_Basic extends javax.swing.JFrame implements Runnable {

    private final JavaSysMon monitor = new JavaSysMon();
    private final Sql_A sql = new Sql_A();
    //==========================
    private final String MS_SQL = "mssql";
    private final String MY_SQL = "mysql";
    private final String ODBC = "odbc";
    //==========================
    private final String LOG_FILE = "sql_log.log";
    private final String QUERY_OK_LOG_FILE = "queries_ok.log";
    private final String QUERY_WRONG_LOG_FILE = "queries_wrong.log";
    private final Font FONT_1 = new Font("Arial", Font.BOLD, 16);
    //==========================
    private String CURRENT_QUERY = "";
    private int SELECTED_ROW = -1;
    //=========================
    private String TITLE;
    private boolean USE_EXTERNAL_LIBRARIES;
    private String PROPERTIES_PATH;
    private Properties PROPS;
    private String TABLE_NAME;
    private boolean AUTO_INCREMENT;
    private boolean STRING_ONLY;

    /**
     * Creates new form FQ
     */
    public FixedQueryTool_Basic() {
        initComponents();
        defineHeadVariables();
        initOther();
        connect();
        this.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL).getImage()); // maximize window
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        if (USE_EXTERNAL_LIBRARIES) {
            this.setTitle(TITLE + "(" + monitor.currentPid() + ")");
        } else {
            this.setTitle(TITLE);
        }
    }

    private void defineHeadVariables() {
        this.TITLE = "FixedQueryTool";
        this.USE_EXTERNAL_LIBRARIES = true;
        this.PROPERTIES_PATH = "freeq.properties";
        this.PROPS = HelpA_.properties_load_properties(PROPERTIES_PATH, false);
        GP.MSSQL_CREATE_STATEMENT_SIMPLE = "false";
        GP.SQL_LIBRARY_JTDS = true;
        //
        //
        //Note that this setting won't work for MS ACCESS AND MYSQL
        this.AUTO_INCREMENT = false;
        //
        this.STRING_ONLY = true;
    }

    private void initOther() {
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? new Color(244, 244, 252) : Color.WHITE);
                return c;
            }
        });
    }

    private void connect() {
        String odbc = PROPS.getProperty("odbc", "");
        String odbc_user = PROPS.getProperty("odbc_user", "");
        String odbc_name = PROPS.getProperty("odbc_name", "");
        //==========================================
        String dbtype = PROPS.getProperty("db_type", "");
        String host = PROPS.getProperty("host", "");
        String port = PROPS.getProperty("port", "");
        String db_name = PROPS.getProperty("db_name", "");
        String user = PROPS.getProperty("user", "");
        String pass = PROPS.getProperty("pass", "");
        try {
            if (dbtype.equals(MS_SQL)) {
                sql.connect(host, port, db_name, user, pass);
                showMessage("Connection to " + host + " / " + db_name + " established");
            } else if (dbtype.equals(MY_SQL)) {
                sql.connectMySql(host, port, db_name, user, pass);
                showMessage("Connection to " + host + " / " + db_name + " established");
            } else if (dbtype.equals(ODBC)) {
                sql.connectODBC(odbc_user, odbc_name, odbc);
                showMessage("Connection to ODBC: " + odbc + " established");
            }

        } catch (SQLException ex) {
            if (dbtype.equals(ODBC)) {
                showMessage("Connection to ODBC: " + odbc + " failed: " + ex);
            } else {
                showMessage("Connection to " + host + " / " + db_name + " failed: " + ex);
            }
            Logger.getLogger(FQ.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showMessage(String str) {
        jTextArea2.append("\n " + HelpA_.get_proper_date_time_same_format_on_all_computers() + " " + str);
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
        int MAX_ROWS = Integer.parseInt(jTextField1RowsMax.getText());
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
                } catch (SQLException ex) {
                    Logger.getLogger(FQ.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            row++;
        }

        return content;
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
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextField1RowsMax = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

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

        jLabel1.setText("Rows max");

        jTextField1RowsMax.setText("100000");

        jButton2.setText("Btn 1");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Btn2");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setText("Select Row");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setText("Apply Change");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 901, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addGap(92, 92, 92)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1RowsMax, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1RowsMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)
                    .addComponent(jButton1)
                    .addComponent(jButton4))
                .addGap(49, 49, 49)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String table = "main_table";
        this.TABLE_NAME = table;
        CURRENT_QUERY = "select * from " + table;
        run_query_by_thread();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String table = "mc_batchinfo";
        this.TABLE_NAME = table;
        CURRENT_QUERY = "select * from " + table;
        run_query_by_thread();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        SELECTED_ROW = jTable1.getSelectedRow();
        paint_selected_row(SELECTED_ROW);
        //
        jButton4.setEnabled(true);
        //
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        update_row_2();
        //
        jButton4.setEnabled(false);
        //
        JOptionPane.showMessageDialog(null, "Changes saved");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void cursorSetWaitCursor(boolean yesno) {
        Cursor defCursor = Cursor.getDefaultCursor();
        Cursor waitCursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
        Frame.getFrames()[0].setCursor(yesno ? waitCursor : defCursor);
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

    private void update_row_2() {
        //
        // This is extreamly important
        jTable1.editCellAt(0, 0);
        //
        delete_selected_row(TABLE_NAME);
        String q = build_insert_query(TABLE_NAME, jTable1, SELECTED_ROW, AUTO_INCREMENT);
        execute_query_with_result_output(q);
        //
        paint_selected_row(-1); // remove paint
    }

    private String build_insert_query(String table_name, JTable jTable, int selected_row, boolean auto_increment) {
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
            String formatted_value = convert("" + jTable.getValueAt(selected_row, i), STRING_ONLY);
            stringBuilder.append(formatted_value);
            stringBuilder.append(",");
        }
        //
        //
        stringBuilder.deleteCharAt(stringBuilder.toString().length() - 1);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private String convert(String value, boolean only_string_values) {
        //
        if (only_string_values) {
            return "'" + value + "'";
        }
        //
        try {
            double val = Double.parseDouble(value);

            if (val > 100000) {
                return "'" + value + "'";
            }

            return value;
        } catch (NumberFormatException e) {
            return "'" + value + "'";
        }
    }

    private boolean isString(String value) {
        try {
            Double.parseDouble(value);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private void delete_selected_row(String table_name) {
        String selected_value_row = "" + jTable1.getValueAt(SELECTED_ROW, 0);

        String column_name = jTable1.getModel().getColumnName(0);

        String q;
        if (isString(selected_value_row)) {
            q = "delete from " + table_name + " where " + column_name + " = '" + selected_value_row + "'";
        } else if (STRING_ONLY) {
            q = "delete from " + table_name + " where " + column_name + " = '" + selected_value_row + "'";
        } else {
            q = "delete from " + table_name + " where " + column_name + " = " + selected_value_row + "";
        }

        execute_query_with_result_output(q);
    }

    private void execute_query_with_result_output(String query) {
        try {
            cursorSetWaitCursor(true);
            ResultSet rs = sql.execute(query);
            build_table(rs);
            showMessage("Executing ok: " + query);
            SimpleLoggerLight.logg(QUERY_OK_LOG_FILE, query);
            cursorSetWaitCursor(false);
        } catch (SQLException ex) {
            showMessage("Executing failed, see output file " + LOG_FILE);
            SimpleLoggerLight.logg(QUERY_WRONG_LOG_FILE, query);
            SimpleLoggerLight.logg(LOG_FILE, ex.toString());
            Logger.getLogger(FQ.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void run_query_by_thread() {
        Thread x = new Thread(this);
        x.start();
    }

    @Override
    public void run() {
        showSqlResult(CURRENT_QUERY);
    }

    private void showSqlResult(String query) {
        try {
            cursorSetWaitCursor(true);
            ResultSet rs = sql.execute(query);
            build_table(rs);
            showMessage("Executing ok: " + query);
            SimpleLoggerLight.logg(QUERY_OK_LOG_FILE, query);
            cursorSetWaitCursor(false);
        } catch (SQLException ex) {
            showMessage("Executing failed, see output file " + LOG_FILE);
            SimpleLoggerLight.logg(QUERY_WRONG_LOG_FILE, query);
            SimpleLoggerLight.logg(LOG_FILE, ex.toString());
            Logger.getLogger(FQ.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void build_table(ResultSet rs) {
        if (rs == null) {
            return;
        }
        try {
            String[] headers = getHeaders(rs);
            Object[][] content = getContent(rs);
            this.jTable1.setModel(new DefaultTableModel(content, headers));
            jTable1.setAutoCreateRowSorter(true);
        } catch (SQLException ex) {
            Logger.getLogger(FQ.class.getName()).log(Level.SEVERE, null, ex);
        }

        int nr_rows = jTable1.getRowCount();
        for (int i = 1; i < nr_rows; i++) {
            jTable1.setSelectionBackground(Color.yellow);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        HelpA_.create_dir_if_missing("err_output");
        try {
            String ERR_OUTPUT_FILE_NAME = "err_" + HelpA_.get_proper_date_time_same_format_on_all_computers_err_output() + ".txt";
            String ERR_OUTPUT_PATH = "err_output/" + ERR_OUTPUT_FILE_NAME;

            PrintStream out = new PrintStream(new FileOutputStream(ERR_OUTPUT_PATH));
            System.setErr(out);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FixedQueryTool_Basic.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FixedQueryTool_Basic().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1RowsMax;
    // End of variables declaration//GEN-END:variables
}
