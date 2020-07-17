/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import forall.HTMLPrint;
import forall.JSon;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.print.PrinterException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author KOCMOC
 */
public class HTMLPrint_A extends javax.swing.JFrame {

    private ArrayList<HashMap<String, String>> articles_map_list;

    /**
     * Creates new form HTMLPrint_A
     */
    public HTMLPrint_A(ArrayList<HashMap<String, String>> articles_map_list) {
        initComponents();
        this.articles_map_list = articles_map_list;
        go();
    }

    private void go() {
        //
        String[] CSSRules = {
            //            "table {margin-bottom:10px;}",
            "table {width: 99%}",
            //            "table {border: 1px solid black}",
            "td {border: 1px solid black}",
            "td {padding-left: 4px}", //            "tr {border-bottom: 1px solid black;}",
        //            ".jtable {font-size:7pt;}",
        //            ".table-invert {font-size:14pt;}",
        };
        //
        HTMLEditorKit kit = new HTMLEditorKit();
        jEditorPane1.setEditorKit(kit);
        //
        StyleSheet styleSheet = kit.getStyleSheet();
        //
        //
        for (int i = 0; i < CSSRules.length; i++) {
            styleSheet.addRule(CSSRules[i]);
        }
        //
        Document doc = kit.createDefaultDocument();
        jEditorPane1.setDocument(doc);
        //
        jEditorPane1.setText(buildHTML());
    }

    private static final String T__FAKTURA = "Faktura";
    private static final String T__FAKTURA_NR = "Faktura nr";
    private static final String T__KUND_NR = "Kundnr";
    private static final String T__FAKTURA_DATUM = "Fakturadatum";
    //
    private static final String COMPANY_NAME = "MixCont AB";
    private static final String FAKTURA_NR = "424";
    private static final String KUND_NR = "114103307";
    private static final String FAKTURA_DATUM = "2020-05-01";

    private static final String LONG_TEXT = "Adaddsvs dfsdfkdsöfk lkflödkfldsöf dlfkslödfklödsf dllkdöslfksödlfkd  dsöfkdsöf";

    protected String buildHTML() {
        //
//        String img_a = getImageIconURL("images", "file.png").toString();
//        String img_b = getImageIconURL("images", "file.png").toString();
//        String img_c = getImageIconURL("images/images_b", "star.png").toString();
        //
        return "<html>"
                + "<body>" //style='background-color:#F1F3F6'
                + "<div style='margin-left:10px;color:gray;padding:5 5 5 5px;'>" // ;background-color:#EEF0F4
                + "<table style='margin-top:15px'>"
                //
                + "<tr>"
                + "<td rowspan='2'><h1>" + COMPANY_NAME + "</h1></td>"
                + "<td><h2>" + T__FAKTURA + "</h2></td>"
                + "</tr>"
                //
                + "<tr>"
                + internal_table_2r_3c(new String[]{T__FAKTURA_NR, T__KUND_NR, T__FAKTURA_DATUM},
                new String[]{FAKTURA_NR, KUND_NR, FAKTURA_DATUM})
                + "</tr>"
                //
                //
                + "<tr></tr>"
                //
                //
                + "<tr>"
                + "<td>"
                + internal_table_x_r_1c(5, new String[]{"Leveransadress", "Sveagatan 19", "231-55", "Trelleborg", "Russia"}, true)
                + "</td>"
                + "<td>"
                + internal_table_x_r_1c(5, new String[]{"Fakturaadress", "Sveagatan 19", "231-55", "Trelleborg", "Russia"}, true)
                + "</td>"
                + "</tr>"
                //
                //
                + "<tr></tr>"
                //
                //
                //
                + "</table>"
                //
                //
                + articles_to_html(articles_map_list)
                //
                //
                + "</div>"
                + "</body>"
                + "</html>";
    }

    private String articles_to_html(ArrayList<HashMap<String, String>> list) {
        //
        String html_ = "<table>";
        //
        if (list == null || list.isEmpty()) {
            return "";
        }
        //
        html_ += "<tr>";
        //
        html_ += "<td>" + "Artnr" + "</td>";
        html_ += "<td>" + "Benämning" + "</td>";
        html_ += "<td>" + "Antal" + "</td>";
        html_ += "<td>" + "Enhet" + "</td>";
        html_ += "<td>" + "A´Pris" + "</td>";
        //
        html_ += "</tr>";
        //
        //
        for (HashMap<String, String> map : list) {
            //
            html_ += "<tr>";
            //
            html_ += "<td>" + map.get(DB.BUH_F_ARTIKEL__ARTIKELID) + "</td>";
            html_ += "<td>" + map.get(DB.BUH_F_ARTIKEL__KOMMENT) + "</td>";
            html_ += "<td>" + map.get(DB.BUH_F_ARTIKEL__ANTAL) + "</td>";
            html_ += "<td>" + map.get(DB.BUH_F_ARTIKEL__ENHET) + "</td>";
            html_ += "<td>" + map.get(DB.BUH_F_ARTIKEL__PRIS) + "</td>";
            //
            html_ += "</tr>";
            //
        }
        //
        html_ += "</table>";
        //
        return html_;
    }

    private String internal_table_x_r_1c(int rows, String[] values, boolean markFirstTd) {
        //
        String html_ = "<table>";
        //
        for (int i = 0; i < rows; i++) {
            //
            html_ += "<tr>";
            //
            if (markFirstTd && i == 0) {
                html_ += "<td style='font-weight:bold'>" + values[i] + "</td>";
            } else {
                html_ += "<td>" + values[i] + "</td>";
            }
            //
            html_ += "</tr>";
            //
        }
        //
        html_ += "</table>";
        //
        return html_;
    }

    /**
     * Build a table which is inserted into "<tr>" element of another table
     *
     * @param rows
     * @param cols
     * @param headers
     * @param values
     * @return
     */
    private String internal_table_2r_3c(String[] headers, String[] values) {
        //
        int rows = 2;
        int cols = headers.length;
        //
        String html_ = "<table>";
        //
        for (int i = 0; i < rows; i++) {
            //
            html_ += "<tr>";
            //
            if (i == 0) {
                //
                for (int j = 0; j < cols; j++) {
                    html_ += "<td>" + headers[j] + "</td>";
                }
                //
            } else {
                //
                for (int j = 0; j < cols; j++) {
                    html_ += "<td>" + values[j] + "</td>";
                }
                //
            }
            //
            html_ += "</tr>";
            //
        }
        //
        html_ += "</table>";
        //
        return html_;
    }

    //==========================================================================
    /**
     * @deprecated @return
     */
    private String get_2row_3col_table(String header_a, String header_b, String header_c, String value_a, String value_b, String value_c) {
        return "<table>"
                + "<tr>"
                + "<td><h3>" + header_a + "</h3></td>"
                + "<td><h3>" + header_b + "</h3></td>"
                + "<td><h3>" + header_c + "</h3></td>"
                + "</tr>"
                //
                + "<tr>"
                + "<td>" + value_a + "</td>"
                + "<td>" + value_b + "</td>"
                + "<td>" + value_c + "</td>"
                + "</tr>"
                + "</table>";
    }

    /**
     * @deprecated @return
     */
    private String get_2row_2col_table(String header_a, String header_b, String value_a, String value_b) {
        return "<table>"
                + "<tr>"
                + "<td><h3>" + header_a + "</h3></td>"
                + "<td><h3>" + header_b + "</h3></td>"
                + "</tr>"
                //
                + "<tr>"
                + "<td>" + value_a + "</td>"
                + "<td>" + value_b + "</td>"
                + "</tr>"
                + "</table>";
    }

    private String get_2row_2col_table__th(String header_a, String header_b, String value_a, String value_b) {
        return "<table>"
                + "<tr>"
                + "<th>" + header_a + "</th>"
                + "<th>" + header_b + "</th>"
                + "</tr>"
                //
                + "<tr>"
                + "<td>" + value_a + "</td>"
                + "<td>" + value_b + "</td>"
                + "</tr>"
                + "</table>";
    }

    /**
     *
     * @param path - path to image folder, play around to get the path working
     * @param picName
     * @return
     */
    protected URL getImageIconURL(String path, String picName) {
        //OBS! YES the first "/" is NEEDED - 100% [2020-06-09]
        return HTMLPrint_A.class.getResource("/" + path + "/" + picName);
    }

    public Point position_window_in_center_of_the_screen(JDialog window) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        return new Point((d.width - window.getSize().width) / 2, (d.height - window.getSize().height) / 2);
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
        jEditorPane1 = new javax.swing.JEditorPane();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(jEditorPane1);

        jButton1.setText("PRINT");
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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(24, 24, 24))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addContainerGap(641, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            //
//            getContentPane().print(getGraphics());
            // https://stackoverflow.com/questions/47147662/changing-print-margins-on-jtextpane
            jEditorPane1.print();
            //
//            PrinterJob job = PrinterJob.getPrinterJob();
//            job.setJobName("Recipe");
            //
        } catch (PrinterException ex) {
            Logger.getLogger(HTMLPrint.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            java.util.logging.Logger.getLogger(HTMLPrint_A.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HTMLPrint_A.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HTMLPrint_A.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HTMLPrint_A.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HTMLPrint_A(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
