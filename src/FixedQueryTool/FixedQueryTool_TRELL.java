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
import MCRecipe.MC_RECIPE;
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
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class FixedQueryTool_TRELL extends javax.swing.JFrame implements Runnable {

    private REPORT REPort = new REPORT();
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
    private static final String QUERY_MAIN = "select * from interf order by TDATETIME desc";
    private static final String TABLE_NAME = "interf";

    /**
     * Creates new form FQ
     */
    public FixedQueryTool_TRELL() {
        initComponents();
//        init_other();
        connect();
        this.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL).getImage()); // maximize window
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("MixCont DB-interface (" + monitor.currentPid() + ")");
        //
//        jButton1.setEnabled(false);
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
            addToOutPutWindow("Connection to " + host + " / " + db_name + " established");

        } catch (SQLException ex) {
            addToOutPutWindow("Connection to " + host + " / " + db_name + " failed: " + ex);
            Logger.getLogger(FQ.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addToOutPutWindow(String str) {
        jTextArea2.append("\n " + HelpA.get_proper_date_time_same_format_on_all_computers() + " " + str);
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
        int MAX_ROWS = 10000000;
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
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButtonRecipeInitialPrintTable1 = new javax.swing.JButton();
        jButtonFind = new javax.swing.JButton();
        jButtonRecipeInitialGo = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

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

        jTextField1.setText("YYYY-MM-DD");

        jTextField2.setText("YYYY-MM-DD");

        jLabel2.setText("Date from");

        jLabel3.setText("Date to");

        jButtonRecipeInitialPrintTable1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print.png"))); // NOI18N
        jButtonRecipeInitialPrintTable1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeInitialPrintTable1ActionPerformed(evt);
            }
        });

        jButtonFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mark_2.png"))); // NOI18N
        jButtonFind.setText("search");
        jButtonFind.setToolTipText("Find");
        jButtonFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFindActionPerformed(evt);
            }
        });

        jButtonRecipeInitialGo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ok.png"))); // NOI18N
        jButtonRecipeInitialGo.setText("show all");
        jButtonRecipeInitialGo.setToolTipText("");
        jButtonRecipeInitialGo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecipeInitialGoActionPerformed(evt);
            }
        });

        jLabel1.setText("Order");

        jLabel4.setText("Article");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jButtonFind)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRecipeInitialGo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                        .addComponent(jButtonRecipeInitialPrintTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)
                                .addComponent(jLabel1))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jButtonRecipeInitialPrintTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonFind, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonRecipeInitialGo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void show_data() {
        CURRENT_QUERY = QUERY_MAIN;
        run_query_by_thread();
    }

    private void jButtonRecipeInitialPrintTable1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeInitialPrintTable1ActionPerformed
        REPort.tableCommonExportOrRepport(jTable1, true);
    }//GEN-LAST:event_jButtonRecipeInitialPrintTable1ActionPerformed

    private void jButtonFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFindActionPerformed
//        if (jTextField1.getText().length() != 10 || jTextField2.getText().length() != 10) {
//            HelpA.showNotification("Date format not correct, should be like (2010-01-01)");
//            return;
//        }
        //
        CURRENT_QUERY = selectDateRangeQuery(jTextField1.getText(), jTextField2.getText(), jTextField4.getText(), jTextField3.getText());
        run_query_by_thread();
    }//GEN-LAST:event_jButtonFindActionPerformed

    private void jButtonRecipeInitialGoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecipeInitialGoActionPerformed
        show_data();
    }//GEN-LAST:event_jButtonRecipeInitialGoActionPerformed

    public static String selectDateRangeQuery(String dateFrom, String dateTo, String article, String order) {
        String q = "select * from " + TABLE_NAME;

        if (HelpA.checkIfDate(dateFrom) && HelpA.checkIfDate(dateTo)) {
            q += " where CAST(TDATETIME as Datetime) >='" + dateFrom + "'";
            q += " and CAST(TDATETIME as Datetime) <='" + dateTo + "'";
            //
            if (order.length() != 0 && article.length() != 0) {
                q += " and VHMFNO ='" + order + "'";
                q += " and VHPRNO ='" + article + "'";
            } else if (order.length() != 0 && article.length() == 0) {
                q += " and VHMFNO ='" + order + "'";
            } else if (article.length() != 0 && order.length() == 0) {
                q += " and VHPRNO ='" + article + "'";
            }
            //
        } else {
            if (order.length() != 0 && article.length() != 0) {
                q += " where VHMFNO ='" + order + "'";
                q += " and VHPRNO ='" + article + "'";
            } else if (order.length() != 0 && article.length() == 0) {
                q += " where VHMFNO ='" + order + "'";
            } else if (article.length() != 0 && order.length() == 0) {
                q += " where VHPRNO ='" + article + "'";
            }

        }

        q += " order by TDATETIME desc";

        return q;


//        return "select * from " + TABLE_NAME
//                + " where CAST(TDATETIME as Datetime) >='" + dateFrom + "'"
//                + " and CAST(TDATETIME as Datetime) <='" + dateTo + "'"
//                + " and VHMFNO ='" + order + "'"
//                + " and VHPRNO ='" + article + "'"
//                + " order by TDATETIME desc";
    }

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
            Logger.getLogger(FixedQueryTool_TRELL.class.getName()).log(Level.SEVERE, null, ex);
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
            addToOutPutWindow("Executing ok: " + query);
            SimpleLoggerLight.logg(QUERY_OK_LOG_FILE, query);
            cursorSetWaitCursor(false);
        } catch (SQLException ex) {
            addToOutPutWindow("Executing failed, see output file " + LOG_FILE);
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
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MC_RECIPE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MC_RECIPE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MC_RECIPE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MC_RECIPE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //
//        HelpA.err_output_to_file();
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FixedQueryTool_TRELL().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonFind;
    public javax.swing.JButton jButtonRecipeInitialGo;
    protected javax.swing.JButton jButtonRecipeInitialPrintTable1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
