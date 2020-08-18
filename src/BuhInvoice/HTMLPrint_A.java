/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import forall.HelpA;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
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

    private final ArrayList<HashMap<String, String>> articles_map_list;
    private final HashMap<String, String> map_a;
    private final HashMap<String, String> map_b;
    private final HashMap<String, String> map_c;
    private final HashMap<String, String> map_d;

    private final static Dimension A4_PAPER = new Dimension(545, 842);

    /**
     * Creates new form HTMLPrint_A
     */
    public HTMLPrint_A(
            ArrayList<HashMap<String, String>> articles_map_list,
            HashMap<String, String> map_a,
            HashMap<String, String> map_b,
            HashMap<String, String> map_c,
            HashMap<String, String> map_d
    ) {
        //
        initComponents();
        //
        this.articles_map_list = articles_map_list;
        //
        this.map_a = map_a;
        this.map_b = map_b;
        this.map_c = map_c;
        this.map_d = map_d;
        //
        go();
    }

    private void go() {
        //
        String[] CSSRules = {
            //            "table {margin-bottom:10px;}",
            "table {width: 99%;}",
            //            "img {width: 20px}", not working from here
            "table {font-size:9pt; color:gray;}", // 9pt seems to be optimal
            //            "table {border: 1px solid black}",
            "td {border: 1px solid black;}",
            "td {padding-left: 4px;}",
            //
            ".marginTop {margin-top: 5px;}",
            ".bold {font-weight:800;}"
        //    
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
        //
    }

    //
    private static final String T__FAKTURA_TITLE = "Faktura";
    private static final String COMPANY_NAME = "MixCont AB";
    private static final String LONG_TEXT = "Adaddsvs dfsdfkdsöfk lkflödkfldsöf dlfkslödfklödsf dllkdöslfksödlfkd  dsöfkdsöf";
    //
    public static final String T__FAKTURA_NR = "Faktura nr";
    public static final String T__KUND_NR = "Kundnr";
    public static final String T__FAKTURA_DATUM = "Fakturadatum";
    //
    public static final String T__FAKTURA_ER_REF = "Er referens";
    public static final String T__FAKTURA_ERT_ORDER_NR = "Ert ordernr";
    public static final String T__FAKTURA_LEV_VILKOR = "Leveransvilkor";
    public static final String T__FAKTURA_LEV_SATT = "Leveranssätt";
    public static final String T__FAKTURA_ERT_VAT_NR = "Ert VAT nummer";
    //
    public static final String T__FAKTURA_VAR_REF = "Vår referens";
    public static final String T__FAKTURA_BETAL_VILKOR = "Betalningvilkor";
    public static final String T__FAKTURA_FORFALLODATUM = "Förfallodatum";
    public static final String T__FAKTURA_DROJMALSRANTA = "Dröjsmålsränta";
    public static final String T__FAKTURA_XXXXXXX = "Ledig*";
    //
    public static final String T__FAKTURA_FRAKT = "Frakt";
    public static final String T__FAKTURA_EXP_AVG = "Exp avg";
    public static final String T__FAKTURA_EXKL_MOMS = "Exkl moms";
    public static final String T__FAKTURA_MOMS_PERCENT = "Moms %";
    public static final String T__FAKTURA_MOMS_KR = "Moms kr";
    public static final String T__FAKTURA_ATT_BETALA = "ATT BETALA";

    protected String buildHTML() {
        //
        String img_a = getImageIconURL("images", "mixcont_logo_md.png").toString();
//        String img_b = getImageIconURL("images", "rado.png").toString();
//        String img_c = getImageIconURL("images/images_b", "star.png").toString();
        //
        return "<html>"
                + "<body>" //style='background-color:#F1F3F6'
                + "<div style='margin-left:10px;padding:5 5 5 5px;'>" // ;background-color:#EEF0F4
                //
                + faktura_header_with_logo_to_html(img_a)
                //
                + adresses_to_html()
                //
                + faktura_data_A_to_html()
                //
                + articles_to_html(articles_map_list)
                //
                + brElements()
                //
                + faktura_data_B_to_html()
                //
                + faktura_data_C_to_html()
                //
                + "<br><br>"
                //
                + "</div>"
                + "</body>"
                + "</html>";
        //
    }

    /**
     * Temporary fix [2020-07-23]
     *
     * @return
     */
    private String brElements() {
        //
        if (articles_map_list == null) {
            return "";
        }
        //
        String html = "";
        //
        int br_to_add = 10 - articles_map_list.size();
        //
        for (int i = 0; i < br_to_add; i++) {
            html += "<br>";
        }
        //
        return html;
    }

    private String faktura_header_with_logo_to_html(String imgPath) {
        //
        String[] headers = new String[]{T__FAKTURA_NR, T__KUND_NR, T__FAKTURA_DATUM};
        String[] values = new String[]{map_a.get(T__FAKTURA_NR), map_a.get(T__KUND_NR), map_a.get(T__FAKTURA_DATUM)};
        //
        return "<table style='margin-top:15px;'>"
                //
                + "<tr>"
                + titleOrLogoIfExist(imgPath)
                + "</tr>"
                //
                + "<tr>"
                + internal_table_2r_xc(headers, values, -1, "")
                + "</tr>"
                //
                + "</table>";
    }

    private String titleOrLogoIfExist(String imgPath) {
        if (imgPath != null) {
            return "<td rowspan='2'><img src='" + imgPath + "' alt='MCRemote'></td>" // width='32' height='32'
                    + "<td><h2>" + T__FAKTURA_TITLE + "</h2></td>";
        } else {
            return "<td rowspan='2'><h1>" + COMPANY_NAME + "</h1></td>"
                    + "<td><h1>" + T__FAKTURA_TITLE + "</h1></td>";

        }
    }

    private String adresses_to_html() {
        //
        String html_ = "<table class='marginTop'>";
        //
        String[] values_a = new String[]{"Leveransadress", "Sveagatan 19", "231-55", "Trelleborg", "Russia"};
        String[] values_b = new String[]{"Fakturaadress", "Sveagatan 19", "231-55", "Trelleborg", "Russia"};
        //
        html_ += "<tr>"
                //
                + "<td>"
                + internal_table_x_r_1c(5, values_a, true)
                + "</td>"
                //
                + "<td>"
                + internal_table_x_r_1c(5, values_b, true)
                + "</td>"
                //
                + "</tr>";
        //
        html_ += "</table>";
        //
        return html_;
    }

    private String faktura_data_A_to_html() {
        //
        String html_ = "<table class='marginTop'>";
        //
        String[] headers_t_1 = new String[]{T__FAKTURA_ER_REF, T__FAKTURA_ERT_ORDER_NR, T__FAKTURA_LEV_VILKOR, T__FAKTURA_LEV_SATT, T__FAKTURA_ERT_VAT_NR};
        String[] values_t_1 = new String[]{map_b.get(T__FAKTURA_ER_REF), map_b.get(T__FAKTURA_ERT_ORDER_NR), map_b.get(T__FAKTURA_LEV_VILKOR), map_b.get(T__FAKTURA_LEV_SATT), map_b.get(T__FAKTURA_ERT_VAT_NR)};
        //
        String[] headers_t_2 = new String[]{T__FAKTURA_VAR_REF, T__FAKTURA_BETAL_VILKOR, T__FAKTURA_FORFALLODATUM, T__FAKTURA_DROJMALSRANTA, T__FAKTURA_XXXXXXX};
        String[] values_t_2 = new String[]{map_c.get(T__FAKTURA_VAR_REF), map_c.get(T__FAKTURA_BETAL_VILKOR), map_c.get(T__FAKTURA_FORFALLODATUM), map_c.get(T__FAKTURA_DROJMALSRANTA), map_c.get(T__FAKTURA_XXXXXXX)};
        //
        html_ += "<tr>"
                //
                + "<td>"
                + internal_table_xr_2c(headers_t_1, values_t_1)
                + "</td>"
                //
                + "<td>"
                + internal_table_xr_2c(headers_t_2, values_t_2)
                + "</td>"
                //
                + "</tr>";
        //
        html_ += "</table>";
        //
        return html_;
    }

    private String faktura_data_B_to_html() {
        //
        String html_ = "<div class='marginTop'>";//<table class='marginTop'>
        //
        String[] headers = new String[]{T__FAKTURA_FRAKT, T__FAKTURA_EXP_AVG, T__FAKTURA_EXKL_MOMS, T__FAKTURA_MOMS_PERCENT, T__FAKTURA_MOMS_KR, T__FAKTURA_ATT_BETALA};
        String[] values = new String[]{map_d.get(T__FAKTURA_FRAKT), map_d.get(T__FAKTURA_EXP_AVG), map_d.get(T__FAKTURA_EXKL_MOMS), map_d.get(T__FAKTURA_MOMS_PERCENT), map_d.get(T__FAKTURA_MOMS_KR), map_d.get(T__FAKTURA_ATT_BETALA)};
        //
        html_ += internal_table_2r_xc(headers, values, 6, "");
        //
        html_ += "</div>";//</table>
        //
//        System.out.println("" + html_);
        //
        return html_;
    }

    private String faktura_data_C_to_html() {
        //
        String html_ = "<div class='marginTop'>";//<table class='marginTop'>
        //
        String[] headers = new String[]{"Telefon", "E-post", "Bankgiro", "Organisationsnr", "Momsreg.nr", "Innehar F-skattebevis"};
        String[] values = new String[]{"014051764", "ask@mixcont.com", "5129-0542", "556251-6806", "SE556251680601", "Ja"};
        //
        html_ += internal_table_2r_xc(headers, values, -1, "");
        //
        html_ += "</div>";
        //
//        System.out.println("" + html_);
        //
        return html_;
    }

    private String articles_to_html(ArrayList<HashMap<String, String>> list) {
        //
        String html_ = "<table class='marginTop' style='border: 1px solid black'>";
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
                html_ += "<td class='bold'>" + values[i] + "</td>";
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

    private String internal_table_xr_2c(String[] headers_t_1, String[] values_t_1) {
        //
        String html_ = "<table>";
        //
        int rows_t1_col1 = headers_t_1.length;
        //
        for (int i = 0; i < rows_t1_col1; i++) {
            //
            html_ += "<tr>";
            //
            html_ += "<td>" + headers_t_1[i] + "</td>";
            html_ += "<td>" + values_t_1[i] + "</td>";
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
     * OBS! The structure of this table is intended for 2 ROWS ONLY
     *
     * @param rows
     * @param cols
     * @param headers
     * @param values
     * @return
     */
    private String internal_table_2r_xc(String[] headers, String[] values, int colToMakeBold, String tableClass) {
        //
        int rows = 2;
        //
        int cols = headers.length;
        //
        String html_ = "<table class='" + tableClass + "'>";
        //
        for (int i = 0; i < rows; i++) {
            //
            html_ += "<tr>";
            //
            if (i == 0) {
                //
                for (int j = 0; j < cols; j++) {
                    if (j == (colToMakeBold - 1)) {
                        html_ += "<td class='bold'>" + headers[j] + "</td>";
                    } else {
                        html_ += "<td>" + headers[j] + "</td>";
                    }
                }
                //
            } else {
                //
                for (int j = 0; j < cols; j++) {
                    if (j == (colToMakeBold - 1)) {
                        html_ += "<td class='bold'>" + values[j] + "</td>";
                    } else {
                        html_ += "<td>" + values[j] + "</td>";
                    }
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
        setResizable(false);

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
                    .addComponent(jButton1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 842, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        print();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void print() {
        //
        int actHeight = jEditorPane1.getHeight();
        //
        System.out.println("jeditorPane height: " + jEditorPane1.getHeight());
        //
        if (actHeight >= A4_PAPER.getHeight()) {
            HelpA.showNotification("A4 Heigh exceeded");
        }
        //
        Paper paper = new Paper();
        paper.setSize(fromCMToPPI(21.0), fromCMToPPI(29.7)); // A4
        //
//        paper.setImageableArea(fromCMToPPI(5.0), fromCMToPPI(5.0),
//                fromCMToPPI(21.0) - fromCMToPPI(10.0), fromCMToPPI(29.7) - fromCMToPPI(10.0));
        //
        // This one sets the margins
        paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight());
        //
        PageFormat pageFormat = new PageFormat();
        pageFormat.setPaper(paper);
        //
        PrinterJob pj = PrinterJob.getPrinterJob();
        //
        PageFormat validatedFormat = pj.validatePage(pageFormat);
        //
        pj.setPrintable(jEditorPane1.getPrintable(null, null), validatedFormat);
        //
        // This one shows additional Dialog displaying the margins, can be skipped
        PageFormat pf = pj.pageDialog(pageFormat);
        //
        if (pj.printDialog()) {
            try {
                pj.setJobName("Faktura"); // This changes the name of file if printed to ".pdf"
                pj.print();
            } catch (PrinterException ex) {
                Logger.getLogger(HTMLPrint_A.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //
    }

    private static double fromCMToPPI(double cm) {
        return toPPI(cm * 0.393700787);
    }

    private static double toPPI(double inch) {
        return inch * 72d;
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
                new HTMLPrint_A(null, null, null, null, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
