/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Sec;

import MCRecipe.MC_RECIPE;
import forall.GP;
import forall.HelpA;
import forall.SqlBasicLocal;
import forall.Sql_B;
import icons.IconUrls;
import images.ImgUrls;
import java.awt.print.PrinterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author KOCMOC
 */
public class PROC_ANALYZER extends javax.swing.JFrame {

    private Properties PROPS = HelpA.properties_load_properties(MC_RECIPE.PROPERTIES_PATH, false);
    private Sql_B sql = new Sql_B(false, false);

    /**
     * Creates new form PROC_ANALYZER
     */
    public PROC_ANALYZER() {
        initComponents();
        sqlConnect();
        this.setTitle("MCRecipe procedure analyzer");
        this.setIconImage(new ImageIcon(IconUrls.OK_ICON_URL).getImage());
        displayFullList();
    }

    private SqlBasicLocal sqlConnect() {
        //==========
        GP.MSSQL_CREATE_STATEMENT_SIMPLE = PROPS.getProperty("mssql_create_statement_simple", "false");
        GP.MSSQL_LOGIN_TIME_OUT = Integer.parseInt(PROPS.getProperty("login_time_out", "60"));
        GP.SQL_LIBRARY_JTDS = Boolean.parseBoolean(PROPS.getProperty("use_jtds_library", "true"));
        GP.JTDS_USE_NAMED_PIPES = Boolean.parseBoolean(PROPS.getProperty("use_named_pipes", "false"));
        GP.JTDS_DOMAIN_WORKGROUP = PROPS.getProperty("domain_or_workgroup", "workgroup");
        GP.JTDS_INSTANCE_PARAMETER = PROPS.getProperty("jtds_instance", "");
        //==========
        //==========
//        String odbc = PROPS.getProperty("odbc", "");
//        String odbc_user = PROPS.getProperty("odbc_user", "");
//        String odbc_name = PROPS.getProperty("odbc_name", "");
        //==========================================
        String dbtype = PROPS.getProperty("db_type", "");
        String host = PROPS.getProperty("host", "");
        String port = PROPS.getProperty("port", "");
        String db_name = PROPS.getProperty("db_name", "");
        String user = PROPS.getProperty("user", "");
        String pass = PROPS.getProperty("pass", "");
        //
        //
        try {
            //
            sql.connect_tds(host, port, db_name, user, pass, GP.JTDS_USE_NAMED_PIPES, GP.JTDS_DOMAIN_WORKGROUP, GP.JTDS_INSTANCE_PARAMETER);
            //
            //
        } catch (Exception ex) {
            Logger.getLogger(PROC_ANALYZER.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Connection to SQL failed!");
            System.exit(0);
        }
        //
        return sql;
    }

    private String getAllQ() {
        return "SELECT name, Type "
                + "FROM dbo.sysobjects "
                + "WHERE (type = 'IF') "
                + "OR (type='TF') "
                + "OR (type='FN') "
                + "OR (type='P') "
                + "ORDER by name asc";
    }

    private ArrayList<String> getAllProcedures() {
        //
        ArrayList<String> list = new ArrayList<String>();
        //
        try {
            //
            ResultSet rs = sql.execute(getAllQ());
            //
            while (rs.next()) {
                list.add(rs.getString("name"));
            }
            //
        } catch (SQLException ex) {
            Logger.getLogger(PROC_ANALYZER.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        Collections.sort(list);
        //
        return list;
        //
    }

    private void tableView() {
        //
        String html;
        //
        HTMLEditorKit kit = new HTMLEditorKit();
        jEditorPane1.setEditorKit(kit);
        //
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule("table, th, td {border: 1px solid black}");
        //
        //
        html = "<table style='width:100%;'>";
        //
        html += "<tr>";
        html += "<th>NAME</th>";
        html += "<th>TYPE</th>";
        html += "</tr>";
        //
        //
        try {
            //
            ResultSet rs = sql.execute(getAllQ());
            //
            while (rs.next()) {
                html += "<tr>";
                html += "<td>" + rs.getString("name") + "</td>";
                html += "<td>" + rs.getString("type") + "</td>";
                html += "</tr>";
            }
            //
        } catch (SQLException ex) {
            Logger.getLogger(PROC_ANALYZER.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        //
        html += "</table>";
        //
        //
        Document doc = kit.createDefaultDocument();
        jEditorPane1.setDocument(doc);
        //
        jEditorPane1.setText(html);
        //
    }

    private void displayUsed() {
        //
        ArrayList<String> list = PROC.procList;
        //
        String html = "";
        //
        //
        int x = 0;
        //
        for (int i = 0; i < list.size(); i++) {
            //
            //
            x++;
            //
            html += htmlBuilder(NONE, x, list.get(i));
            //
            //
        }
        //
        jEditorPane1.setText(html);
        //
    }

    private void displayNotUsed() {
        //
        ArrayList<String> list = getAllProcedures();
        //
        String html = "";
        //
        //
        int x = 0;
        //
        for (int i = 0; i < list.size(); i++) {
            //
            String proc = list.get(i);
            //
            //
            if (PROC.exists(proc) == false) {
                //
                x++;
                //
                if (proc.toLowerCase().contains("sys_") || proc.toLowerCase().contains("sp_")) {
                    html += htmlBuilder(CONTAINS_SYS_PREFIX, x, proc);
                } else {
                    html += htmlBuilder(NONE, x, proc);
                }
                //
            }
            //
        }
        //
        jEditorPane1.setText(html);
        //
    }

    private void displayFullList() {
        //
        ArrayList<String> list = getAllProcedures();
        //
        String html = "";
        //
        //
        for (int i = 0; i < list.size(); i++) {
            //
            String proc = list.get(i);
            //
            if (PROC.exists(proc)) {

                if (proc.toLowerCase().contains("sys_") || proc.toLowerCase().contains("sp_")) {
                    html += htmlBuilder(CONTAINS_SYS_PREFIX, i, proc);
                } else {
                    html += htmlBuilder(NONE, i, proc);
                }

            } else {
                if (proc.toLowerCase().contains("sys_") || proc.toLowerCase().contains("sp_")) {
                    html += htmlBuilder(CONTAINS_SYS_PREFIX, i, proc);
                } else {
                    html += htmlBuilder(PROC_MISSING, i, proc);
                }
            }
        }
        //
        jEditorPane1.setText(html);
        //
    }
    private static final int NONE = 0;
    private static final int PROC_MISSING = 1;
    private static final int CONTAINS_SYS_PREFIX = 2;
    private int FONT_SIZE;

    private String htmlBuilder(int option, int index, String value) {
        //
        FONT_SIZE = Integer.parseInt(jTextField1.getText());
        //
        String html = "<div style='font-size:" + FONT_SIZE + "pt;"
                + "margin-left:40px;"
                + "margin-bottom: 5px;";

        if (option == PROC_MISSING) {
            html += "color:red;";
        } else if (option == CONTAINS_SYS_PREFIX) {
            html += "color:blue;";
        }
        //
        html += "'>";
        //
        html += "<span style='font-size:14pt'>" + index + "</span>" + ". " + value;
        //
        html += "</div>";
        //
        return html;
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
        jEditorPane1 = new javax.swing.JEditorPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jEditorPane1.setContentType("text/html"); // NOI18N
        jScrollPane2.setViewportView(jEditorPane1);

        jButton1.setText("Full List");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Unused");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Used");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("All table view");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTextField1.setText("18");

        jLabel1.setText("font-size");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButton2)
                        .addGap(27, 27, 27)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88)
                        .addComponent(jButton4)
                        .addGap(100, 100, 100)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 171, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton2)
                        .addComponent(jButton3)
                        .addComponent(jButton4)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        displayFullList();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        displayNotUsed();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        displayUsed();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        tableView();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            jEditorPane1.print();
        } catch (PrinterException ex) {
            Logger.getLogger(PROC_ANALYZER.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

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
            java.util.logging.Logger.getLogger(PROC_ANALYZER.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PROC_ANALYZER.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PROC_ANALYZER.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PROC_ANALYZER.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        HelpA.err_output_to_file();
        
        /* Create and displayFullList the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PROC_ANALYZER().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
