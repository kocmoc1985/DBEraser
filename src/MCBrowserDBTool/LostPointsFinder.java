/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCBrowserDBTool;

import forall.HelpA;
import forall.Sql_B;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Libraries needed: jtds-1.3.1.jar, MyCommons.jar,
 * mysql-connector-java-5.1.10-bin.jar, NetProcMonitor.jar, sqljdbc4.jar
 *
 * @author KOCMOC
 */
public class LostPointsFinder extends javax.swing.JFrame {

    private ArrayList<Batch> batches = new ArrayList<>();
    private Sql_B sql = new Sql_B(true, false);

    private String date_more_then = "2023-01-01";
    private String date_less_then = "2023-03-21";
    public static int DELAY_MORE_THEN = 2100; // 1000 is not enough #CHANGABLE-PARAMETER#
    public static int SHOW_OUTPUT_IF_DELAYS_MORE_THEN = 0; // #CHANGABLE-PARAMETER#

    private boolean CEAT = true; // #CHANGABLE-PARAMETER#
    private boolean FEDMOG = false; // #CHANGABLE-PARAMETER#

    private String ORDER_NAME_COLUMN;

    /**
     * Creates new form LostPointsFinder
     */
    public LostPointsFinder() {
        initComponents();
        connect_sql();
        this.setTitle("Delay finder");
    }

    private void go() {
        date_more_then = jText_date_more.getText();
        date_less_then = jTextField_date_less.getText();
        Thread x = new Thread(new CalcThread());
        x.start();
    }

    class CalcThread implements Runnable {

        @Override
        public void run() {
            mc_batchinfo_loop();
        }

    }

    public static void output(String text) {
        jTextArea1.append("\n " + HelpA.get_proper_date_dd_MM_yyyy() + "  " + text);
    }

    private void connect_sql() {
        //
        if (FEDMOG) {
            //
            ORDER_NAME_COLUMN = "ARBEITSREF";
            //
            try {
                sql.connect_jdbc("10.4.188.9", "1433", "MIXCONT", "mixcont", "newmixcont");
            } catch (SQLException ex) {
                Logger.getLogger(LostPointsFinder.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LostPointsFinder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //
        if (CEAT) {
            //
            ORDER_NAME_COLUMN = "OrderName";
            //
            try {
                sql.connectMySql("localhost", "3306", "ceat", "root", "0000");
            } catch (SQLException ex) {
                Logger.getLogger(LostPointsFinder.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LostPointsFinder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //

    }

    private void mc_batchinfo_loop() {
        //
        output("Started");
        //
        String q = String.format("SELECT * FROM mc_batchinfo where PROD_DATE > '%s' AND PROD_DATE < '%s' ORDER BY PROD_DATE DESC", date_more_then, date_less_then);
        //
        try {
            //
            ResultSet rs = sql.execute(q);
            //
            while (rs.next()) {
                //
                int id = rs.getInt("ID");
                //
                String recipe = rs.getString("RECIPEID");
                String orderName = rs.getString(ORDER_NAME_COLUMN);
                String batch = rs.getString("BATCHNO");
                String date = rs.getString("PROD_DATE");
                //
//                System.out.println(recipe + " / " + orderName + " / " + batch + " / " + date);
                //
                mc_trend_loop(id, recipe, orderName, batch, date);
                //
            }
            //
            prepare_delete_sql();
            //
            output("\n\nEnded");
            //
        } catch (SQLException ex) {
            Logger.getLogger(LostPointsFinder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void prepare_delete_sql() {
        //
        HashSet<Integer> set = Batch.ids_to_remove;
        String output = "";
        //
        for (Iterator<Integer> iterator = set.iterator(); iterator.hasNext();) {
            Integer id = iterator.next();
            output += id + ",";
        }
        //
        output = output.substring(0,output.length() - 1);
        //
        output("\n\n delete from mc_batchinfo where ID in (" + output + ")");
        //
    }

    private void mc_trend_loop(int id, String recipe, String order, String batch, String date) {
        //
        String q = String.format("SELECT * FROM mc_trend where IDBATCHINFO=%s ORDER BY TICKS ASC", id);
        //
        try {
            //
            ResultSet rs = sql.execute_2(q);
            //
            Batch batch__ = new Batch(id, recipe, order, batch, date);
            //
            while (rs.next()) {
                //
                batch__.addTick(rs);
                //
            }
            //
//            batches.add(batch__); // IS CAUSING JAVA HEAP-SPACE problem
            //
            jTextField1.setText(date + " " + recipe + " " + order + " " + batch + " \n");
            //
        } catch (SQLException ex) {
            Logger.getLogger(LostPointsFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField_date_less = new javax.swing.JTextField();
        jText_date_more = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField_date_less.setText("2023-03-07");
        jTextField_date_less.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_date_lessActionPerformed(evt);
            }
        });

        jText_date_more.setText("2023-03-01");

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jText_date_more, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(522, 522, 522))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jTextField_date_less, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(28, 28, 28)
                            .addComponent(jTextField1))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jText_date_more, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_date_less, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_date_lessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_date_lessActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_date_lessActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        go();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(LostPointsFinder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LostPointsFinder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LostPointsFinder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LostPointsFinder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        if (HelpA.runningInNetBeans() == false) {
            HelpA.err_output_to_file();
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LostPointsFinder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField_date_less;
    private javax.swing.JTextField jText_date_more;
    // End of variables declaration//GEN-END:variables
}
