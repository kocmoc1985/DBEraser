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
public class FixedQueryTool_FED_BCR extends javax.swing.JFrame implements Runnable {

    private JavaSysMon monitor = new JavaSysMon();
    private Sql_A sql = new Sql_A();
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
    //=========================
    private String CURRENT_QUERY = "";
    //
    private DefaultTableModel tableModel;
    //
    private static int SELECTED_ROW = -1;
    //
    private static final String QUERY_MAIN = "select * from MC_BARCODES order by date_scan desc";
    private static final String TABLE_NAME = "MC_BARCODES";
    //
    public static int MAX_ROWS = 100000;

    /**
     * Creates new form FQ
     */
    public FixedQueryTool_FED_BCR() {
        initComponents();
//        init_other();
        connect();
        this.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL).getImage()); // maximize window
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("MCBarcodes (" + monitor.currentPid() + ")");
        //
        jButton1.setEnabled(false);
        //
        show_data();
        //
        
    }
    
    private void change_table_columns_names(){
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
                sql.connect(host, port, db_name, user, pass);
            } else if (dbtype.equals(MY_SQL)) {
                sql.connectMySql(host, port, db_name, user, pass);
            } else if (dbtype.equals(ODBC)) {
                sql.connectODBC(odbc_user, odbc_name, odbc);
            }
            addToOutPutWindow(jTextArea_output,"Connection to " + host + " / " + db_name + " established");

        } catch (SQLException ex) {
            addToOutPutWindow(jTextArea_output,"Connection to " + host + " / " + db_name + " failed: " + ex);
            Logger.getLogger(FQ.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addToOutPutWindow(JTextArea jtxt,String str) {
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

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh.png"))); // NOI18N
        jButton2.setToolTipText("Refresh data");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        jButton1.setToolTipText("Apply changes");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit.png"))); // NOI18N
        jButton3.setToolTipText("Select row to be modified");
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
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 901, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        show_data();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void show_data() {
        CURRENT_QUERY = QUERY_MAIN;
        run_query_by_thread();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        update_row_2();
        //
        jButton1.setEnabled(false);
        //
        JOptionPane.showMessageDialog(null, "Changes saved");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        SELECTED_ROW = jTable1.getSelectedRow();
        paint_selected_row(SELECTED_ROW);
        //
        jButton1.setEnabled(true);
        //
    }//GEN-LAST:event_jButton3ActionPerformed

    private void update_row_2() {
        //
        // This is extreamly important
        jTable1.editCellAt(0, 0);
        //
        delete_selected_row(TABLE_NAME);
        String q = build_insert_query(TABLE_NAME, jTable1, SELECTED_ROW, true);
        execute_query_with_result_output(q);
        //
        paint_selected_row(-1); // remowe paint
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
            String formatted_value = isString("" + jTable.getValueAt(selected_row, i), true);
            stringBuilder.append(formatted_value);
            stringBuilder.append(",");
        }
        //
        //
        stringBuilder.deleteCharAt(stringBuilder.toString().length() - 1);
        stringBuilder.append(")");
        return stringBuilder.toString();
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

    private void delete_selected_row(String table_name) {
        String q = "delete from " + table_name + " where nr = " + jTable1.getValueAt(SELECTED_ROW, 0);
        execute_query_with_result_output(q);
    }

    /**
     * @deprecated
     */
    private void update_row_1() {
        int column_count = tableModel.getColumnCount();
        int curr_row = jTable1.getSelectedRow();
        //
//        execute_query_with_result_output(CURRENT_QUERY);
        jTable1.changeSelection(curr_row, 0, true, false);
        //
        String q_1 = "delete from MC_BARCODES where nr = " + jTable1.getValueAt(curr_row, 0);
        System.out.println("" + q_1);

        //
        String q_2 = String.format("INSERT INTO MC_BARCODES (lot,part,quantity,test,date_recieve,date_scan)"
                + " values "
                + "("
                + "'%s','%s','%s','%s','%s','%s'"
                + ")",
                jTable1.getValueAt(curr_row, 1),
                jTable1.getValueAt(curr_row, 2),
                jTable1.getValueAt(curr_row, 3),
                jTable1.getValueAt(curr_row, 4),
                jTable1.getValueAt(curr_row, 5),
                jTable1.getValueAt(curr_row, 6));
        //
        System.out.println("" + q_2);
        try {
            //
            sql.execute(q_1);
            sql.execute_2(q_2);
        } catch (SQLException ex) {
            Logger.getLogger(FixedQueryTool_FED_BCR.class.getName()).log(Level.SEVERE, null, ex);
        }

        //
        run_query_by_thread();

//        Object x = jTable1.getValueAt(curr_row, 0);
//        System.out.println("x = " + x.toString());
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
            change_table_columns_names();
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
            java.util.logging.Logger.getLogger(FixedQueryTool_FED_BCR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FixedQueryTool_FED_BCR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FixedQueryTool_FED_BCR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FixedQueryTool_FED_BCR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //
        HelpA.err_output_to_file();
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FixedQueryTool_FED_BCR().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea_output;
    // End of variables declaration//GEN-END:variables
}
