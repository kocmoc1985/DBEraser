/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Exporter.java
 *
 * Created on 2013-jan-18, 15:08:31
 */
package exporterMills;

import com.jezhumble.javasysmon.JavaSysMon;
import forall.GP;
import forall.Sql_A;
import exporterMills.Mills.Dbentry;
import forall.HelpA;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 * 2013-02-20 - works to both directions (from sql to mdb and reverse)
 * 2013-02-20 - problems with duplications solved
 *
 * @author Administrator
 */
public class Exporter extends javax.swing.JFrame implements Runnable {
    public final static Properties EXPORTER_PROPERTIES = HelpA.choose_properties(".");
    //=================================================
    public final static String EXPORTER_MILLS__MAIN_TABLE_NAME = EXPORTER_PROPERTIES.getProperty("main_table_name", "MainTable");
    public final static String EXPORTER_MILLS__CURVES_TABLE_NAME = EXPORTER_PROPERTIES.getProperty("curves_table_name", "Curvestable");
    public final static String EXPORTER_MILLS__CALCULATIONS_TABLE_NAME = EXPORTER_PROPERTIES.getProperty("calculations_table_name", "Calculations");
    public final static String EXPORTER_MILLS__DAYRECORD_TABLE_NAME = EXPORTER_PROPERTIES.getProperty("dayrecord_table_name", "DAYRECORD");
    public final static String EXPORTER_MILLS__PRIMARY_ID_NAME = EXPORTER_PROPERTIES.getProperty("primary_id_name", "BatchID");
    //
    private JavaSysMon monitor = new JavaSysMon();
    private Sql_A sql_dist = new Sql_A();
    private Sql_A sql_source = new Sql_A();
    private Mills mills = new Mills();
    private ArrayList<Integer> id_list = new ArrayList<Integer>();
    private final static String PRIMARY_ID_NAME = EXPORTER_MILLS__PRIMARY_ID_NAME;
    private final static String PRIMARY_TABLE_NAME = EXPORTER_MILLS__MAIN_TABLE_NAME;
    private static String DATE_FROM = "";
    private static String DATE_TO = "";// Should add 1 wrong otherwise, DB interprets "<=" wrong!!
    private int counter = 0;
    private Properties properties = EXPORTER_PROPERTIES;
    private int RECORDED;
    

    /**
     * Creates new form Exporter
     */
    public Exporter() {
        initComponents();
        this.setVisible(true);
        this.setTitle("DBExporter - Mills (" + monitor.currentPid() + ")");
        this.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL).getImage()); 
        jTextField1.setText(HelpA.updatedOn());
        jTextField2.setText(HelpA.updatedOn());
    }

    private void connect() {
        connectToSourceDb();
        connectToDistDb();
    }

    private void connectToSourceDb() {
        String dbtype = properties.getProperty("db_type");
        String host = properties.getProperty("host");
        String port = properties.getProperty("port");
        String db_name = properties.getProperty("db_name");
        String user = properties.getProperty("user");
        String pass = properties.getProperty("pass");

        try {
            //change to "sql_dist" here if you want to record to db from .mdb
            if (!jCheckBox1.isSelected()) {
                sql_source.connect(host, port, db_name, user, pass); // sql to .mdb
                show_info("src sql : " + host + " / " + db_name);
            } else {
                sql_dist.connect(host, port, db_name, user, pass); // .mdb to sql
                show_info("dist sql: " + host + " / " + db_name);
            }

        } catch (SQLException ex) {
            if (!jCheckBox1.isSelected()) {
                show_info("connection to src sql failed: " + ex);
            } else {
                show_info("connection to dist sql failed: " + ex);
            }
            Logger.getLogger(Exporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void connectToDistDb() {
        try {
            //change to "sql_source" here if you want to record to db from .mdb
            String odbc = properties.getProperty("odbc", "MILLSMDB");
            if (!jCheckBox1.isSelected()) {
                sql_dist.connectODBC("", "", properties.getProperty("odbc", "MILLSMDB"));
                show_info("dist mdb : " + odbc);
            } else {
                sql_source.connectODBC("", "", properties.getProperty("odbc", "MILLSMDB"));
                show_info("src mdb : " + odbc);
            }
        } catch (SQLException ex) {
            if (!jCheckBox1.isSelected()) {
                show_info("connection to dist mdb failed: " + ex);
            } else {
                show_info("connection to src mdb failed: " + ex);
            }
            Logger.getLogger(Exporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void show_info(String msg) {
        textArea1.append(HelpA.get_proper_date_time_same_format_on_all_computers() + "    " + msg + "\n");
    }

    private void fileOperations() {
        File f = new File(".");//c:/tmp 
        File[] flist = f.listFiles();
        for (File file : flist) {
            if (file.getName().equals("mills_export.mdb")) {
                show_info("Deleted: " + file.getName());
            }
        }

        try {
            HelpA.copyFile("lib/mills_export_empty.mdb", "mills_export.mdb");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Exporter.class.getName()).log(Level.SEVERE, null, ex);
            show_info("mills_export_empty.mdb  NOT FOUND");
        } catch (IOException ex) {
            Logger.getLogger(Exporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void show_progress(String table, int batches, int total) {
        jLabel2.setText(table + ": " + batches + " / " + total);//visu
    }

    /**
     * Copying from Sql_A to mdb. It might be other than mdb also ofcourse!
     */
    private void export() {
        //Note that the order of Tables is ver important
        //MainTable first....
        DATE_TO = HelpA.addDay(DATE_TO);
        for (String table : mills.table_names_bound) {
            RECORDED = 0;
            String q;
            if (table.equals(PRIMARY_TABLE_NAME)) {
                q = "select * from " + table + " where " + Mills.MT.datum + " >='" + DATE_FROM + "' and " + Mills.MT.datum + " <= '" + DATE_TO + "' order by '"
                        + Mills.MT.datum + "' asc";
                exportEngine_ordinary(q, table);
                show_info("export ready " + table);
            } else {// secondary tables
                for (Integer id : id_list) {
                    q = "select * from " + table + " where " + PRIMARY_ID_NAME.toLowerCase() + " = " + id;
                    exportEngine_ordinary(q, table);
                }
                show_info("export ready " + table);
            }
        }
        //This section is for special tables as "DAYRECORD" which are not bound with id from the primary table
        for (String table : mills.table_names_unbound) {
            String q;
            if (table.equals(EXPORTER_MILLS__DAYRECORD_TABLE_NAME)) {
                q = "select * from " + table.toUpperCase() + " where " + Mills.DAYRECORD.datum + " >='" + DATE_FROM + "' and " + Mills.DAYRECORD.datum + " <= '" + DATE_TO + "' order by '"
                        + Mills.DAYRECORD.datum + "' asc";
                exportEngine_millsBrowser_dayrecord_prepared_statement(q);
                show_info("export ready " + table);
            }
            show_info("Ready!");
        }
    }

    /**
     * Gets a result from DB and then replicates it to the mdb by using prepared
     * statements This method suits only for the "DAYRECORD" table which is used
     * in connection with mills
     *
     * @param q
     */
    private void exportEngine_millsBrowser_dayrecord_prepared_statement(String q) {
        try {
            ResultSet rs = sql_source.execute(q);

            while (rs.next()) {
//                String q1 = "insert into DAYRECORD(r_id,datum,dayRecord,line,millsNr) values(?,?,?,?,?)";

                String q1;
                if (!jCheckBox1.isSelected()) {
                    q1 = "insert into " + Mills.DAYRECORD.tablename + "(" + Mills.DAYRECORD.recordid + ","
                            + Mills.DAYRECORD.datum + "," + Mills.DAYRECORD.dayrecord + "," + Mills.DAYRECORD.line + ","
                            + Mills.DAYRECORD.millnr + ") " + "values(?,?,?,?,?)";
                } else {
                    q1 = "insert into " + Mills.DAYRECORD.tablename + "("
                            + Mills.DAYRECORD.datum + "," + Mills.DAYRECORD.dayrecord + "," + Mills.DAYRECORD.line + ","
                            + Mills.DAYRECORD.millnr + ") " + "values(?,?,?,?)";
                }

                byte[] bytes = rs.getBytes(Mills.DAYRECORD.dayrecord);

                if (bytes == null) {
                    return;
                }

                if (bytes.length > 1) {
                    sql_dist.prepareStatement(q1);
                    if (!jCheckBox1.isSelected()) {
                        //from sql to .mdb
                        sql_dist.getPreparedStatement().setString(1, rs.getString(Mills.DAYRECORD.recordid));
                        sql_dist.getPreparedStatement().setString(2, rs.getString(Mills.DAYRECORD.datum));
                        sql_dist.getPreparedStatement().setBytes(3, bytes);
                        sql_dist.getPreparedStatement().setString(4, rs.getString(Mills.DAYRECORD.line));
                        sql_dist.getPreparedStatement().setInt(5, rs.getInt(Mills.DAYRECORD.millnr));
                    } else {
                        //from .mdb to sql
                        sql_dist.getPreparedStatement().setString(1, rs.getString(Mills.DAYRECORD.datum));
                        sql_dist.getPreparedStatement().setBytes(2, bytes);
                        sql_dist.getPreparedStatement().setString(3, rs.getString(Mills.DAYRECORD.line));
                        sql_dist.getPreparedStatement().setInt(4, rs.getInt(Mills.DAYRECORD.millnr));
                    }
                    try {
                        sql_dist.executeUpdatePreparedStatement();
                    } catch (SQLException ex2) {
                        System.out.println("dayrecord already exists ");
                    }

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Exporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void exportEngine_ordinary(String q, String table) {
        StringBuilder insert_str;
        int id = -1;
        int id_prev = -2;
        boolean add;
        try {
            long m = System.currentTimeMillis();
            ResultSet rs = sql_source.execute(q);
            counter++;
            //
            System.out.println("COUNTER: " + counter + " / " + "time = " + (System.currentTimeMillis() - m));
            //
            LinkedList<Dbentry> curr_table = mills.table_map.get(table);
            while (rs.next()) {
                insert_str = new StringBuilder("values(");
                //
                for (Iterator<Dbentry> it = curr_table.iterator(); it.hasNext();) {
                    Dbentry dbentry = it.next();
                    String data_type = dbentry.getDataType();
                    String column_name = dbentry.getColumnName();
                    if (data_type.equals("int")) {
                        //
                        int val = rs.getInt(column_name.trim());
                        buildInsertValues(insert_str, it, data_type, val);
                        if (column_name.toLowerCase().equals(PRIMARY_ID_NAME.toLowerCase())
                                && table.equals(PRIMARY_TABLE_NAME)) { // important, this is only for prim table!
                            id = val; // id is added later!
                        }
                        //
                    } else if (data_type.equals("String")) {
                        String value = rs.getString(dbentry.getColumnName());
                        buildInsertValues(insert_str, it, data_type, value);
                    }
                }
                //=====
                //Calculating progress
                String q_1_2 = "select count (*) from " + table;
                ResultSet rs2 = sql_source.execute_2(q_1_2);
                int total = 0;
                if (rs2.next()) {
                    total = rs2.getInt(1);
                }
                //
                //=======
                insert_str.append(")");
                //
                String q2 = "insert into " + table + " " + insert_str.toString();
                //
                try {
                    sql_dist.update(q2);
                    add = true;
                    RECORDED++;
                    show_progress(table, RECORDED, total);
                } catch (SQLException ex2) {
                    System.out.println("skip");
                    add = false;
                }

                if (add && id != -1 && id != id_prev) {
                    id_prev = id;
                    id_list.add(id);
                }
            }

        } catch (SQLException ex) {
            show_info("sql_error: " + ex);
            Logger.getLogger(Exporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param insert_str
     * @param it
     * @param data_type
     * @param value
     */
    private void buildInsertValues(StringBuilder insert_str, Iterator<Dbentry> it, String data_type, Object value) {

        if (data_type.equals("int")) {
            int val = (Integer) value;
            insert_str.append(val);
            if (it.hasNext()) {
                insert_str.append(",");
            }

        } else if (data_type.equals("String")) {
            String val = (String) value;

            if (val == null) {
                insert_str.append("null");
            } else {
                val = val.trim();
                insert_str.append("'");
                insert_str.append(val);
                insert_str.append("'");
            }
            if (it.hasNext()) {
                insert_str.append(",");
            }
        }
    }

    @Override
    public void run() {
//        System.out.println("thread runs");
        fileOperations();
        connect();
        export();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        textArea1 = new java.awt.TextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("...");

        jTextField1.setText("date from");

        jTextField2.setText("date to");

        jButton1.setText("start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("invert (.mdb to sql)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jCheckBox1)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(20, 20, 20)
                            .addComponent(jButton1))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(2, 2, 2)
                            .addComponent(textArea1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jCheckBox1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(textArea1, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DATE_FROM = jTextField1.getText();
        DATE_TO = jTextField2.getText();
        Thread thisThr = new Thread(this);
        thisThr.start();

    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        HelpA.err_output_to_file();
        Exporter exporter = new Exporter();
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            @Override
//            public void run() {
//                new Exporter().setVisible(true);
//            }
//        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    public static javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private java.awt.TextArea textArea1;
    // End of variables declaration//GEN-END:variables
}
