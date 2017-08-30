/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FQ.java
 *
 * Created on 2013-mar-11, 11:57:31
 */
package FreeQuery;

import ClientSqlRemote.ClientProtocolSR;
import ClientSqlRemote.ClientSqlRemote;
import Interfaces.ShowMessage;
import Interfaces.SqlBasic;
import forall.SimpleLoggerLight;
import forall.GP;
import forall.HelpA;
import forall.Sql_C;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class FQ extends javax.swing.JFrame implements Runnable, ShowMessage {

    private SqlBasic sql = new Sql_C();
    //==========================
    private final String MS_SQL = "mssql";
    private final String MY_SQL = "mysql";
    private final String ODBC = "odbc";
    private final String MDB = "mdb";
    private final String NPMS = "npms";
    //==========================
    private final String LOG_FILE = "sql_log.log";
    private final String QUERY_OK_LOG_FILE = "queries_ok.log";
    private final String QUERY_WRONG_LOG_FILE = "queries_wrong.log";
    private final Font FONT_1 = new Font("Arial", Font.BOLD, 16);
    //==========================
    private final String SYSTEM_QUIRIES_INFO = "help/system_quiries.rtf";
    private final String SELECT_QUIRIES_INFO = "help/select_quiries.rtf";
    //=========================
    private String CURRENT_QUERY = "";
    //=========================
    private HashMap<String, String> properties_to_use_map = new HashMap();

    /**
     * Creates new form FQ
     */
    public FQ() {
        initComponents();
        init_other();
        this.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL).getImage());
        this.setTitle("FreeQuery");
        showMessage("Java ver: " + System.getProperty("java.version") + " (ODBC and MDB are not supported by versions older then 1.7.X.X)");
    }

    private void startConnectThread() {
        Thread x = new Thread(this);
        x.start();
    }

    private void init_other() {
        jTextArea1QueryInput.setFont(FONT_1);
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
        Properties properties = HelpA.choose_properties(".");
        //==========
        GP.SQL_NPMS_HOST = properties.getProperty("sql_npms_host", "localhost");
        GP.SQL_NPMS_PORT = Integer.parseInt(properties.getProperty("sql_npms_port", "1111"));
        //
        GP.MSSQL_CREATE_STATEMENT_SIMPLE = properties.getProperty("mssql_create_statement_simple", "false");
        boolean simpleStatement = Boolean.parseBoolean(GP.MSSQL_CREATE_STATEMENT_SIMPLE);
        GP.MSSQL_LOGIN_TIME_OUT = Integer.parseInt(properties.getProperty("login_time_out", "60"));
        GP.SQL_LIBRARY_JTDS = Boolean.parseBoolean(properties.getProperty("use_jtds_library", "false"));
        GP.JTDS_USE_NAMED_PIPES = Boolean.parseBoolean(properties.getProperty("use_named_pipes", "false"));
        GP.JTDS_DOMAIN_WORKGROUP = properties.getProperty("domain_or_workgroup", "");
        GP.JTDS_INSTANCE_PARAMETER = properties.getProperty("jtds_instance", "");
        //
        showMessage("mssql_create_statement_simple: " + GP.MSSQL_CREATE_STATEMENT_SIMPLE);
        showMessage("login_time_out: " + GP.MSSQL_LOGIN_TIME_OUT);
        showMessage("use_jtds_library: " + GP.SQL_LIBRARY_JTDS);
        showMessage("use_named_pipes: " + GP.JTDS_USE_NAMED_PIPES);
        showMessage("domain_or_workgroup: " + GP.JTDS_DOMAIN_WORKGROUP);
        //
        //==========
        String odbc = properties.getProperty("odbc", "");
        //==========================================
        String mdb_path = properties.getProperty("mdb_path", "");
        //==========================================
        String dbtype = properties.getProperty("db_type", "");
        String host = properties.getProperty("host", "");
        String port = properties.getProperty("port", "");
        String db_name = properties.getProperty("db_name", "");
        String user = properties.getProperty("user", "");
        String pass = properties.getProperty("pass", "");
        //
        showMessage("sql_type: " + dbtype);
        //
        Sql_C sql_c = (Sql_C) sql;
        //
        if (dbtype.equals(NPMS) == false) {
            showMessage("Connecting to: " + host + " / " + db_name + odbc + mdb_path);
        }
        //
        try {
            if (dbtype.equals(MS_SQL)) {
                sql_c.connect(host, port, db_name, user, pass, simpleStatement);
            } else if (dbtype.equals(MY_SQL)) {
                sql_c.connectMySql(host, port, db_name, user, pass);
            } else if (dbtype.equals(ODBC)) {
                sql_c.connectODBC(user, pass, odbc, simpleStatement);
            } else if (dbtype.equals(MDB)) {
                sql_c.connectMDB(user, pass, mdb_path, simpleStatement);
            } else if (dbtype.equals(NPMS)) {
                sql = createRemoteSql(GP.SQL_NPMS_HOST, GP.SQL_NPMS_PORT,
                        host, Integer.parseInt(port), db_name, user, pass,
                        GP.SQL_LIBRARY_JTDS, GP.JTDS_INSTANCE_PARAMETER);
            }
            //
            if (dbtype.equals(NPMS) == false) {
                showMessage("Connection to " + host + " / " + db_name + odbc + mdb_path + " established");
            }
            //
            jButton7.setEnabled(false);
            jButton1.setEnabled(true);
            jButton5.setEnabled(true);
            //
        } catch (Exception ex) {
            showMessage("Connection to " + host + " / " + db_name + odbc + " failed: " + ex);
            Logger.getLogger(FQ.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        showMessage(GP.CONNECTION_STRING);
        //
        HelpA.goToEndPosition(jTextArea2);
        //
    }

    private SqlBasic createRemoteSql(String npms_ip, int npms_port,
            String sqlHost, int port, String dbName, String user, String pass, boolean jtds, String instance) {
        //
        ClientSqlRemote sqlRemote = new ClientSqlRemote(this, sqlHost, port, dbName, user, pass, jtds, instance);
        ClientProtocolSR protocolSR = new ClientProtocolSR(sqlRemote, this);
        //
        sqlRemote.start(npms_ip, npms_port, protocolSR);
        return sqlRemote;
    }

    @Override
    public void showMessage(String str) {
        jTextArea2.append("\n " + HelpA.get_proper_date_time_same_format_on_all_computers() + " " + str);
    }

    public String[] getHeaders(ResultSet rs) throws SQLException {
        ResultSetMetaData meta; // Returns the number of columns
        String[] headers; // skapar en ny array att lagra titlar i
        meta = rs.getMetaData(); // Den parameter som skickas in "ResultSet rs" innehåller Sträng vid initialisering
        headers = new String[meta.getColumnCount()]; // ger arrayen "headers" initialisering och anger antalet positioner
        for (int i = 0; i < headers.length; i++) {
            headers[i] = meta.getColumnLabel(i + 1);
        }
        return headers;
    }

    public static int getRowCountResultSet(ResultSet rs) {
        try {
            rs.last();
            return rs.getRow();
        } catch (Exception ex) {
            return -1;
        }
    }

    public Object[][] getContent(ResultSet rs) throws Exception {
        //
        int MAX_ROWS = 0;
        //
        try {
            MAX_ROWS = Integer.parseInt(jTextField1RowsMax.getText());
        } catch (NumberFormatException ex) {
            MAX_ROWS = Integer.MAX_VALUE;
        }
        //
        ResultSetMetaData rsmt;
        Object[][] content;
        int columns;
        rsmt = rs.getMetaData(); // får in antalet columner
        columns = rsmt.getColumnCount(); // retrieves number of columns och lagrar det i "columns".
        //
        int rows = 0;
        //
        rows = getRowCountResultSet(rs);
        //
        if (rows != 0 && rows != -1) {
            rs.first();
        } else {
            while (rs.next()) {
                try {
                    rs.getString(1);
                    rows++;
                } catch (Exception ex) {
                    break;
                }
            }
        }
        //
        if (MAX_ROWS > rows) {
            MAX_ROWS = rows;
        }
        //
        jLabel2Rows.setText("rows: " + rows);
        //
        rs = sql.execute(CURRENT_QUERY);
        //
        if (rows < MAX_ROWS) {
            content = new Object[rows][columns]; // ger arrayen content som är en "Object"
        } else {
            content = new Object[MAX_ROWS][columns];
        }
        //
        int row = 0;
        boolean break_while = false;
        while (rs.next() && row < (MAX_ROWS - 1)) {
            //
            for (int col = 0; col < columns; col++) {
                try {
                    content[row][col] = rs.getObject(col + 1);
                } catch (Exception ex) {
                    break_while = true;
                }
            }
            //
            if (break_while) {
               content = removeEmptyRows(row,columns, content);
                System.out.println("");
                break;
            }
            //
            row++;
            jLabel2RowsInTable.setText("rows in table: " + (row));
        }
        //
        return content;
    }
    
    private Object[][] removeEmptyRows(int rows_added,int columns, Object[][]content){
        Object[][] content_new = new Object[rows_added][columns];
        System.arraycopy(content, 0, content_new, 0, rows_added);
         return content_new;
    }

    class BuildTableThr implements Runnable {

        private ResultSet rs;

        public BuildTableThr(ResultSet rs) {
            this.rs = rs;
        }

        @Override
        public void run() {
            build_table(rs);
        }
    }

    public void build_table(ResultSet rs) {
        if (rs == null) {
            return;
        }
        try {
            String[] headers = getHeaders(rs);
            Object[][] content = getContent(rs);
            this.jTable1.setModel(new DefaultTableModel(content, headers));
            jLabel2RowsInTable.setText("rows in table: " + jTable1.getRowCount());
        } catch (Exception ex) {
            Logger.getLogger(FQ.class.getName()).log(Level.SEVERE, null, ex);
        }

        int nr_rows = jTable1.getRowCount();
        for (int i = 1; i < nr_rows; i++) {
            jTable1.setSelectionBackground(Color.yellow);
        }
        //
        showMessage("Executing ok");
        //
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1QueryInput = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextField1RowsMax = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel2Rows = new javax.swing.JLabel();
        jLabel2RowsInTable = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1QueryInput.setColumns(20);
        jTextArea1QueryInput.setRows(5);
        jTextArea1QueryInput.setText("select * from");
        jScrollPane1.setViewportView(jTextArea1QueryInput);

        jButton1.setText("Execute");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

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
        jScrollPane3.setViewportView(jTable1);

        jLabel1.setText("Rows max");

        jTextField1RowsMax.setText("300");
        jTextField1RowsMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1RowsMaxActionPerformed(evt);
            }
        });

        jButton2.setText("Recent quiries");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton5.setText("See tables");
        jButton5.setEnabled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Options");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Connect");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Ping");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("ODBC");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton3.setText("IPconfig");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Home Folder");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel2Rows.setText("rows:");

        jLabel2RowsInTable.setText("rows in table: ");

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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1RowsMax, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                            .addComponent(jScrollPane1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2Rows, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2RowsInTable, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1RowsMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton5)
                    .addComponent(jButton9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2Rows)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2RowsInTable)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private HashSet<String> queryOkSet = new HashSet<String>();

    private void showSqlResult(String query) {
        try {
            ResultSet rs = sql.execute(query);
//            build_table(rs);
            //
            Thread thread = new Thread(new BuildTableThr(rs));
            thread.start();
            //
            if (queryOkSet.contains(query) == false) {
                SimpleLoggerLight.logg(QUERY_OK_LOG_FILE, query);
            }
            //

        } catch (Exception ex) {
            showMessage("Executing failed, see output file " + LOG_FILE);
            SimpleLoggerLight.logg(QUERY_WRONG_LOG_FILE, query);
            SimpleLoggerLight.logg(LOG_FILE, ex.toString());
            Logger.getLogger(FQ.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CURRENT_QUERY = jTextArea1QueryInput.getText();
        showSqlResult(CURRENT_QUERY);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            HelpA.run_application_with_associated_application(new File(QUERY_OK_LOG_FILE));
        } catch (IOException ex) {
            Logger.getLogger(FQ.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        CURRENT_QUERY = "select name,xtype,refdate from sysobjects where xtype = 'U'";
        showSqlResult(CURRENT_QUERY);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            HelpA.run_application_exe_or_jar("propertiesreader.jar", ".");
        } catch (IOException ex) {
            Logger.getLogger(FQ.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        startConnectThread();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        Thread x = new Thread(new Pingthread());
        x.start();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        HelpA.run_with_cmd("odbcad32", "");
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Thread x = new Thread(new Ipconfigthread());
        x.start();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        HelpA.open_dir(".");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField1RowsMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1RowsMaxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1RowsMaxActionPerformed

    class Pingthread implements Runnable {

        @Override
        public void run() {
            HelpA.run_program_with_catching_output(jTextArea2, "ping", JOptionPane.showInputDialog("Type ip"), "");
        }
    }

    class Ipconfigthread implements Runnable {

        @Override
        public void run() {
            HelpA.run_program_with_catching_output(jTextArea2, "ipconfig", "", "");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {//Write error stream to a file
        //
        HelpA.nimbusLookAndFeel();
        //
        HelpA.create_dir_if_missing("err_output");
        try {
            String ERR_OUTPUT_FILE_NAME = "err_" + HelpA.get_proper_date_time_same_format_on_all_computers_err_output() + ".txt";
            String ERR_OUTPUT_PATH = "err_output/" + ERR_OUTPUT_FILE_NAME;

            PrintStream out = new PrintStream(new FileOutputStream(ERR_OUTPUT_PATH));
            System.setErr(out);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FQ.class.getName()).log(Level.SEVERE, null, ex);
        }

        //======================================================================
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FQ().setVisible(true);
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
    private javax.swing.JLabel jLabel2Rows;
    private javax.swing.JLabel jLabel2RowsInTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private static javax.swing.JTextArea jTextArea1QueryInput;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1RowsMax;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        connect();
    }
}
