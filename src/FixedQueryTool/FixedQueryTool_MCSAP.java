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

import MCCompound.BuildWorkInstrTable;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class FixedQueryTool_MCSAP extends javax.swing.JFrame implements Runnable {

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

    /**
     * Creates new form FQ
     */
    public FixedQueryTool_MCSAP() {
        initComponents();
        init_other();
        connect();
        this.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL).getImage()); // maximize window
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("MCSAP Test Inviroment (" + monitor.currentPid() + ")");
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
            this.jTable1.setModel(new DefaultTableModel(content, headers));
            jTable1.setAutoCreateRowSorter(true);
        } catch (SQLException ex) {
            Logger.getLogger(FQ.class.getName()).log(Level.SEVERE, null, ex);
        }

        int nr_rows = jTable1.getRowCount();
        for (int i = 1; i < nr_rows; i++) {
            jTable1.setSelectionBackground(Color.yellow);
            System.out.println("");
        }
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
        jLabel1 = new javax.swing.JLabel();
        jTextField1RowsMax = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();

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

        jButton1.setText("recipe");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("raw_material");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("recipe_raw_material");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("raw_material_vendor");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("vendor");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("vendor_contact");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("item_price");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("interface_trigger");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("Build WorkInstructions for recipe");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
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
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 259, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField1RowsMax, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton9)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1RowsMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6)
                    .addComponent(jButton7)
                    .addComponent(jButton8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton9)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CURRENT_QUERY = "select "
                + "parentItemCode,"
                + "workInstructions,"
                + "familySubGroup,"
                + "groupName,"
                + "description,"
                + "recipeStatus,"
                + "developedOn,"
                + "lastUpdate,"
                + "dateExport,"
                + "dateProcessed,"
                + "status"
                + " from recipe"
                + " order by"
                + " parentItemCode asc";
        run_query_by_thread();
//        showSqlResult(CURRENT_QUERY);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        CURRENT_QUERY = "select * from raw_material order by itemCode asc";
        run_query_by_thread();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        CURRENT_QUERY = "select * from recipe_raw_material order by parentItemCode asc";
        run_query_by_thread();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        CURRENT_QUERY = "select * from raw_material_vendor order by itemCode asc";
        run_query_by_thread();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        CURRENT_QUERY = "select * from vendor";
        run_query_by_thread();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        CURRENT_QUERY = "select * from vendor_contact";
        run_query_by_thread();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        CURRENT_QUERY = "select * from item_price";
        run_query_by_thread();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        CURRENT_QUERY = "select * from interface_trigger";
        run_query_by_thread();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        String workInstr = "";
        String recipe = JOptionPane.showInputDialog("Please type recipe");
        String q = "select workInstructions from recipe"
                + " where "
                + "parentItemCode ='" + recipe + "'";
        try {
            ResultSet rs = sql.execute(q);
            rs.next();
            workInstr = rs.getString("workInstructions");
            BuildWorkInstrTable.build(workInstr, recipe);
        } catch (SQLException ex) {
            Logger.getLogger(FixedQueryTool_MCSAP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton9ActionPerformed

    private void cursorSetWaitCursor(boolean yesno) {
        Cursor defCursor = Cursor.getDefaultCursor();
        Cursor waitCursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
        Frame.getFrames()[0].setCursor(yesno ? waitCursor : defCursor);
    }

    private void showSqlResult(String query) {
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
        showSqlResult(CURRENT_QUERY);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        HelpA.create_dir_if_missing("err_output");
        try {
            String ERR_OUTPUT_FILE_NAME = "err_" + HelpA.get_proper_date_time_same_format_on_all_computers_err_output() + ".txt";
            String ERR_OUTPUT_PATH = "err_output/" + ERR_OUTPUT_FILE_NAME;

            PrintStream out = new PrintStream(new FileOutputStream(ERR_OUTPUT_PATH));
            System.setErr(out);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FixedQueryTool_MCSAP.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FixedQueryTool_MCSAP().setVisible(true);
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
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1RowsMax;
    // End of variables declaration//GEN-END:variables
}
