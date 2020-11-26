/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Eraser.java
 *
 * Created on 2012-nov-15, 10:44:02
 */
package eraser;

import com.jezhumble.javasysmon.JavaSysMon;
import forall.GP;
import forall.HelpA;
import forall.Sql_A;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Administrator
 */
public class Eraser extends javax.swing.JFrame {

    private JavaSysMon monitor = new JavaSysMon();
    private Sql_A sql = new Sql_A();
    //==========================
    private final String MS_SQL = "mssql";
    private final String MY_SQL = "mysql";

    /**
     * Creates new form Eraser
     */
    public Eraser() {
        initComponents();
        this.setTitle("DBEraser (" + monitor.currentPid() + ")");
        this.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL).getImage());
        connect();
    }

    private void connect() {
        Properties properties = HelpA.choose_properties(".");
        String dbtype = properties.getProperty("db_type");
        String host = properties.getProperty("host");
        String port = properties.getProperty("port");
        String db_name = properties.getProperty("db_name");
        String user = properties.getProperty("user");
        String pass = properties.getProperty("pass");
        try {
            if (dbtype.equals(MS_SQL)) {
                sql.connect(host, port, db_name, user, pass);
            } else if (dbtype.equals(MY_SQL)) {
                sql.connectMySql(host, port, db_name, user, pass);
            }
            addToOutPutWindow("Connection to " + host + " established");

        } catch (SQLException ex) {
            addToOutPutWindow("Connection to " + host + " failed: " + ex);
            Logger.getLogger(Eraser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void selectiveErase(String q, String primery_key_name, String[] table_names) {
        ArrayList<String> id_list = new ArrayList<String>();
        ResultSet rs;
        try {
            rs = sql.execute(q);

            while (rs.next()) {
                String act_id = rs.getString(primery_key_name);
                id_list.add(act_id);
            }

            for (String table : table_names) {
                for (String id : id_list) {
                    //i have tested: it doesn't matter for the sql if the prim_key_name is
                    //written in small or big large case
                    String del_q = "delete from " + table + " where " + primery_key_name + " = " + id;
                    sql.execute(del_q);
                }
                addToOutPutWindow("Delete from " + table + " table OK!");
            }
            addToOutPutWindow("Operation succeded!");
        } catch (SQLException ex) {
            addToOutPutWindow("Operation failed!");
            addToOutPutWindow("" + ex);
            Logger.getLogger(Eraser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //===========================================================================

    private void testDb(String q) {
        ResultSet rs = null;
        try {
            rs = sql.execute(q);
            addToOutPutWindow("Test ok!");
        } catch (Exception ex) {
            addToOutPutWindow("Test failed - " + ex);
        }
    }

//===========================================================================
    private void addToOutPutWindow(String str) {
        jTextArea3OutPut.append("\n " + str + "\n");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1Erase = new javax.swing.JButton();
        jTextField1Tables = new javax.swing.JTextField();
        jTextField2PrimKey = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3OutPut = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("select BatchId from MainTable where datum\nlike '%2012-10-14%'");
        jScrollPane1.setViewportView(jTextArea1);

        jButton1Erase.setText("Erase");
        jButton1Erase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1EraseActionPerformed(evt);
            }
        });

        jTextField1Tables.setText("Calculations,CurvesTable,MainTable");

        jTextField2PrimKey.setText("BatchId");

        jTextArea3OutPut.setColumns(20);
        jTextArea3OutPut.setRows(5);
        jScrollPane3.setViewportView(jTextArea3OutPut);

        jButton1.setText("Test DB (No erase!)");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(jTextField2PrimKey, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(jTextField1Tables, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(jButton1Erase, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField2PrimKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1Tables, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1Erase)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1EraseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1EraseActionPerformed
        String prim_key = jTextField2PrimKey.getText();
        String[] arr = jTextField1Tables.getText().split(",");
        String q = jTextArea1.getText();
        selectiveErase(q, prim_key, arr);
    }//GEN-LAST:event_jButton1EraseActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String q = jTextArea1.getText();
        testDb(q);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        HelpA.err_output_to_file();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Eraser().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton1Erase;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3OutPut;
    private javax.swing.JTextField jTextField1Tables;
    private javax.swing.JTextField jTextField2PrimKey;
    // End of variables declaration//GEN-END:variables
}
