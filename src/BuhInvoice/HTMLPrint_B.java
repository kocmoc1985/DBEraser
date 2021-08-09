/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import static BuhInvoice.GP_BUH._get;
import static BuhInvoice.HTMLPrint.NO_BORDER;
import static BuhInvoice.HTMLPrint.T__FAKTURA_MOMS_PERCENT;
import static BuhInvoice.HelpBuh.FOREIGN_CUSTOMER;
import static BuhInvoice.HelpBuh.LANG_ENG;
import BuhInvoice.sec.HeadersValuesHTMLPrint;
import BuhInvoice.sec.LANG;
import forall.HelpA;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

/**
 * This class is for "REMINDER"
 *
 * @author KOCMOC
 */
public class HTMLPrint_B extends HTMLPrint {

    public HTMLPrint_B(
            LAFakturering bim,
            String fakturatype,
            boolean preview,
            ArrayList<HashMap<String, String>> articles_map_list,
            HashMap<String, String> map_a_0,
            HashMap<String, String> map_a,
            HashMap<String, String> map_b,
            HashMap<String, String> map_c,
            HashMap<String, String> map_d,
            HashMap<String, String> map_e,
            HashMap<String, String> map_e_2,
            HashMap<String, String> map_f,
            HashMap<String, String> map_g
    ) {
        super(bim, fakturatype, preview, articles_map_list, map_a_0, map_a, map_b,
                map_c, map_d, map_e, map_e_2, map_f, map_g, null, null);
    }

    @Override
    protected void buttonLogic() {
        //
        if (HelpBuh.IS_MAC_OS) {
            GP_BUH.setEnabled(jButton_send_with_outlook, false);
        }
        //
    }

    @Override
    public String getWindowTitle() {
        if (preview) {
            return LANG.FRAME_TITLE_1_3;
        } else {
            return LANG.FRAME_TITLE_1_2;
        }
    }

    @Override
    public JEditorPane getEditorPane() {
        return this.jEditorPane1;
    }

    @Override
    public JScrollPane getJScrollPane() {
        return jScrollPane2;
    }

    @Override
    public String[] getCssRules() {
        //
        String border__or_no_border;
        //
        if (NO_BORDER) { //[#NO-BORDER-PROPPER#]
            border__or_no_border = "td {border: 0px solid gray;}";
        } else {
            border__or_no_border = "td {border: 1px solid gray;}";
        }
        //
        String[] CSSRules = {
            //            "table {margin-bottom:10px;}",
            "table {width: 99%;}",
            //            "img {width: 20px}", not working from here
            ".fontStd {font-size:9pt; color:gray;}",
            "table {font-size:9pt; color:gray;}", // 9pt seems to be optimal
            //            "table {border: 1px solid black}",
            border__or_no_border,
            "td {padding-left: 4px;}",
            //
            ".marginTop {margin-top: 5px;}",
            ".marginLeft {margin-left: 10px;}",
            ".marginLeftB {margin-left: 5px;}",
            ".paddingLeft {padding-left: 5px;}",
            ".bold {font-weight:800;}", // font-weight:800;
            ".no-border {border: 0px}",
            ".border-a {border: 1px solid gray;}",
            ".text-md {font-size:11pt; color:black;}"
        //    
        };
        //
        return CSSRules;
        //
    }

    protected final static String getAttBetalaTitle() {
        return LANG_ENG == false ? "ATT BETALA" : "TO PAY";
    }

    private String getForfalloDatumFlexCol() {
        return _get_colon_sep(T__FAKTURA_FORFALLODATUM__FLEX, map_c);
    }

    private String getBetalVilkorFlexCol() {
        return _get_colon_sep(T__FAKTURA_UTSKRIVET, map_c);
    }

    private String getDrojsmalsrantaFlexCol() {
        return _get_colon_sep(T__FAKTURA_DROJMALSRANTA__FLEX, map_c) + " %";
    }

    @Override
    public String buildHTML() {
        // 
//        String img_a = getPathResources("images", "mixcont_logo_md.png"); // WORKING
        // 
        String img_a = getPathNormal(GP_BUH.LOGO_PATH());
        //
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
                + reminder_message()
                //
                + betal_alternativ_to_html()
                //
                + faktura_data_B_to_html__totals()
                //
                //                + articles_to_html(articles_map_list)
                //
                + brElements()
                //
                + faktura_data_C_to_html__addr()
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
        int br_to_add = 15;
        //
        for (int i = 0; i < br_to_add; i++) {
            html += "<br>";
        }
        //
        return html;
    }

    private String getFakturaNr() {
        return map_a.get(T__FAKTURA_NR);
    }

    private String faktura_header_with_logo_to_html(String imgPath) {
        //
        String[] headers = new String[]{T__FAKTURA_NR(), T__KUND_NR(), T__FAKTURA_DATUM()};
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

    @Override
    protected String getHTMLPrintTitle() {
        return LANG_ENG == false ? LANG.PAMINNELSE : "Invoice Reminder";
//          return "Påminnelse";
    }

    private String titleOrLogoIfExist(String imgPath) {
        //
        Dimension imgD = GP_BUH.calculate_w_h__proportionalScaling(GP_BUH.LOGO_PATH());
        //
        if (imgPath != null) {
            return "<td rowspan='2' class='paddingLeft' width='50%'><img src='" + imgPath + "' alt='image' width='" + imgD.width + "' height='" + imgD.height + "'></td>" // width='32' height='32'
                    + "<td width='50%'><h1 class='marginLeft'>" + getHTMLPrintTitle() + "</h1></td>";
        } else {
            return "<td rowspan='2' width='50%'><h1 class='marginLeft'>" + map_f.get(DB.BUH_KUND__NAMN) + "</h1></td>"
                    + "<td width='50%'><h1 class='marginLeft'>" + getHTMLPrintTitle() + "</h1></td>";

        }
        //
    }

    private String adresses_to_html() {
        //
        String html_ = "<table class='marginTop'>";
        //
        String[] values_a = new String[]{
            _get(map_e_2__lev_data, COL_0),
            _get(map_e, COL_1) + ", " + _get(map_e, COL_1_2),
            _get(map_e, COL_2) + " " + _get(map_e, COL_3) + _get_exist_c(_get(map_e, COL_3_1))
        };
        //
        String[] values_b = new String[]{
            getForfalloDatumFlexCol(),
            getBetalVilkorFlexCol(),
            getDrojsmalsrantaFlexCol()
        };
        //
        html_ += "<tr>"
                //
                + "<td style='width:50%'>"
                + internal_table_x_r_1c(3, values_a, false)
                + "</td>"
                //
                + "<td style='width:50%'>"
                + internal_table_x_r_1c(3, values_b, true)
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
        String[] values_t_1 = new String[]{
            T__FAKTURA_VAR_REF() + ": " + map_c.get(T__FAKTURA_VAR_REF),
            T__FTG_TELEFON() + ": " + _get(map_g, DB.BUH_ADDR__TEL_A)};
        //
        String[] values_t_2 = new String[]{
            T__FTG_EPOST() + ": " + _get(map_f, DB.BUH_KUND__EPOST),
            T__FAKTURA_ERT_ORDER_NR() + ": "
        };
        //
        html_ += "<tr>"
                //
                + "<td style='width:50%'>"
                + internal_table_x_r_1c(2, values_t_1, false)
                + "</td>"
                //
                + "<td style='width:50%'>"
                + internal_table_x_r_1c(2, values_t_2, false)
                + "</td>"
                //
                + "</tr>";
        //
        html_ += "</table>";
        //
        return html_;
    }

    private String reminder_message() {
        //
        String html_ = "<table class='marginTop'>";
        //
        //
        html_ += "<tr>";
        //
        html_ += "<td class='text-md' style='padding:10 10 10 10px;'>";
        html_ += LANG.PAMMINELSE_MSG_MAIN__AUTO(getFakturaNr());
        html_ += "</td>";
        //
        html_ += "</tr>";
        //
        html_ += "</table>";
        //
        return html_;
    }

    private String betal_alternativ_to_html() {
        //
        if (bim.isKreditFaktura() || bim.isKontantFaktura()) {
            return "";
        }
        //
        String html_ = "<table class='marginTop'>";
        //
        //
        html_ += "<tr>";
        //
        html_ += "<td>"; // style='padding:5 5 5 5px;'
        //
        //[2021-05-18] This additional construction with <table> is used for lining-up with previous sections
        html_ += "<table>";
        html_ += "<tr>";
        html_ += "<td>";
        //
        html_ += betalAlternativStringBuilder();
        //
        html_ += "</table>";
        html_ += "</tr>";
        html_ += "</td>";
        //
        html_ += "</td>";
        //
        html_ += "</tr>";
        //
        html_ += "</table>";
        //
        return html_;
    }

    private double countDrojsmalsAvgift() {
        String dateNow = GP_BUH.getDate_yyyy_MM_dd();
        String drojmalsranta = _get(map_c, T__FAKTURA_DROJMALSRANTA__FLEX);
        String forfallodatum = _get(map_c, T__FAKTURA_FORFALLODATUM__FLEX);
        String total_inkl_moms = map_d.get(getAttBetalaTitle());
        total_inkl_moms = getSpecial_foreign_customer(total_inkl_moms);
        double totalInklMoms = Double.parseDouble(total_inkl_moms);
        String dateFormat = GP_BUH.DATE_FORMAT_BASIC;
        //
        int daysForfallen = GP_BUH.get_diff_in_days__two_dates(dateNow, dateFormat, forfallodatum, dateFormat);
        //
        if (daysForfallen <= 0) {
            return 0;
        }
        //
//        System.out.println("Days forfallen: " + daysForfallen);
        double percent = Double.parseDouble(drojmalsranta) / 100;
        double oneDayRanta = (totalInklMoms * percent) / 365;
        //
        return daysForfallen * oneDayRanta;
        //
    }

    private String faktura_data_B_to_html__totals() {
        //
        String html_ = "<div class='marginTop'>";//<table class='marginTop'>
        //
        double drojAvg = GP_BUH.round_double(countDrojsmalsAvgift());
        System.out.println("Dröj avg************************************************: " + drojAvg);
        //
        int colToMakeBold = 10;
        //
        String moms_kr = map_d.get(T__FAKTURA_MOMS_KR);
        String frakt = map_d.get(T__FAKTURA_FRAKT);
        String exp = map_d.get(T__FAKTURA_EXP_AVG);
        String rabatt = map_d.get(T__FAKTURA_RABATT_KR);
        String rut_avdrag_total = getRutAvdragTotal();
        String total_belopp_innan_avdrag = "0";
        String total_exkl_moms = map_d.get(T__FAKTURA_EXKL_MOMS);
        total_exkl_moms = getSpecial_foreign_customer(total_exkl_moms);
        //
        String ATT_BETALA_TITLE = getAttBetalaTitle();
        //
        String total = ""+ Double.parseDouble(map_d.get(ATT_BETALA_TITLE));
        total = getSpecial_foreign_customer(total);
        Double totalInkDrojAvg = GP_BUH.round_double(Double.parseDouble(total) + drojAvg);
        //
        String currencyUnit = FOREIGN_CUSTOMER ? "EUR" : "";
        //
        String[] headers = new String[]{T__FAKTURA_RUT_TOTAL_BELOPP, T__FAKTURA_RUT_AVDRAG_TOTAL, T__FAKTURA_FRAKT, T__FAKTURA_EXP_AVG, T__FAKTURA_EXKL_MOMS(), T__FAKTURA_MOMS_PERCENT, T__FAKTURA_MOMS_KR(), T__FAKTURA_RABATT_KR, T__FAKTURA_DROJMALSRANTA__FLEX(), ATT_BETALA_TITLE};
        String[] values = new String[]{total_belopp_innan_avdrag, rut_avdrag_total, frakt, exp, total_exkl_moms, map_d.get(T__FAKTURA_MOMS_PERCENT), moms_kr, rabatt, "" + drojAvg, "" + roundBetalaTotal(""+totalInkDrojAvg) + " " + currencyUnit};
        //
        HeadersValuesHTMLPrint hvp = excludeIfZero(headers, values, colToMakeBold, moms_kr, frakt, exp, rabatt, rut_avdrag_total, total_belopp_innan_avdrag);
        //
        html_ += internal_table_2r_xc(hvp.getHeaders(), hvp.getValues(), hvp.getColToMakeBold(), "marginLeftB");
        //
        html_ += "</div>";//</table>
        //
        if (NO_BORDER) { //[#NO-BORDER-PROPPER#]
            html_ += "<div style='width:95%;height:5px;border-bottom:1px solid gray;margin-right:15px'></div>";
        }
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
    public Point position_window_in_center_of_the_screen(JDialog window) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        return new Point((d.width - window.getSize().width) / 2, (d.height - window.getSize().height) / 2);
    }

    @Override
    protected void initComponents_() {
        initComponents();
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
        jPanel2 = new javax.swing.JPanel();
        jEditorPane1 = new javax.swing.JEditorPane();
        jPanel1 = new javax.swing.JPanel();
        jButton_print = new javax.swing.JButton();
        jButton_pdf = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton_send_faktura_email = new javax.swing.JButton();
        jButton_send_faktura_any_email = new javax.swing.JButton();
        jButton_send_with_outlook = new javax.swing.JButton();
        jLabel_status_reminder = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jEditorPane1.setMaximumSize(new java.awt.Dimension(545, 842));
        jEditorPane1.setMinimumSize(new java.awt.Dimension(545, 842));

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jButton_print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        jButton_print.setToolTipText("Skriv ut påminnelse");
        jButton_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_printActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_print);

        jButton_pdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pdf-icon-c.png"))); // NOI18N
        jButton_pdf.setToolTipText("Spara faktura i .pdf format på skrivbordet");
        jButton_pdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_pdfActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_pdf);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/image.png"))); // NOI18N
        jButton3.setToolTipText("Välj logotyp / bild");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);

        jButton_send_faktura_email.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/post.png"))); // NOI18N
        jButton_send_faktura_email.setToolTipText("Skicka påminnelse per E-post, automatiskt");
        jButton_send_faktura_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_send_faktura_emailActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_send_faktura_email);

        jButton_send_faktura_any_email.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/target.png"))); // NOI18N
        jButton_send_faktura_any_email.setToolTipText("Skicka påminnelse  till valfri e-post");
        jButton_send_faktura_any_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_send_faktura_any_emailActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_send_faktura_any_email);

        jButton_send_with_outlook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/send_b.png"))); // NOI18N
        jButton_send_with_outlook.setToolTipText("Skicka påminnelse per E-post med Outlook");
        jButton_send_with_outlook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_send_with_outlookActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_send_with_outlook);

        jLabel_status_reminder.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_status_reminder.setForeground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_status_reminder, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jEditorPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_status_reminder, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jEditorPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 842, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jScrollPane2.setViewportView(jPanel2);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jButton_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_printActionPerformed
        //
        print_help(false);
        //
//        boolean print_ok = print_normal();
//        //
//        if (print_ok) {
//            EditPanel_Send.insert(bim.getFakturaId(), DB.STATIC__SENT_STATUS__UTSKRIVEN, DB.STATIC__SENT_TYPE_PAMMINELSE);
//        }
        //
    }//GEN-LAST:event_jButton_printActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //
        GP_BUH.chooseLogo(this);
        //
        go();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton_send_faktura_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_send_faktura_emailActionPerformed
        //
        String faktura_kund_email = getFakturaKundEmail();
        //
        sendMail(faktura_kund_email);
        //
    }//GEN-LAST:event_jButton_send_faktura_emailActionPerformed

    private void jButton_send_with_outlookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_send_with_outlookActionPerformed
        sendWithStandardEmailClient(true);
    }//GEN-LAST:event_jButton_send_with_outlookActionPerformed

    private void jButton_send_faktura_any_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_send_faktura_any_emailActionPerformed
        sendMailTargeted();
    }//GEN-LAST:event_jButton_send_faktura_any_emailActionPerformed

    private void jButton_pdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_pdfActionPerformed
        print_help(true);
    }//GEN-LAST:event_jButton_pdfActionPerformed

    @Override
    protected void displayStatus(String msg, Color c) {
        //
        if (c != null) {
            jLabel_status_reminder.setForeground(c);
        }
        //
        jLabel_status_reminder.setText(msg);
        //
    }

//    private void print_upload_sendmail__thr(String serverPath, String fileName, String sendToEmail, String ftgName) {
//        Thread x = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //
//                //
//                boolean ok = print_upload_sendmail(serverPath, fileName, sendToEmail, ftgName);
//                //
//                String fakturaId = getFakturaId();
//                // 
//                // [2020-09-08]
//                if (ok) {
//                    //
//                    loggDocumentSent(fakturaId, DB.STATIC__SENT_STATUS__SKICKAD, DB.STATIC__SENT_TYPE_PAMMINELSE);
//                    //
//                } else {
//                    EditPanel_Send.insert(fakturaId, DB.STATIC__SENT_STATUS__EJ_SKICKAD,
//                            DB.STATIC__SENT_TYPE_PAMMINELSE);
//                }
//            }
//        });
//        //
//        x.start();
//        //
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton_pdf;
    private javax.swing.JButton jButton_print;
    private javax.swing.JButton jButton_send_faktura_any_email;
    private javax.swing.JButton jButton_send_faktura_email;
    private javax.swing.JButton jButton_send_with_outlook;
    protected javax.swing.JEditorPane jEditorPane1;
    protected static javax.swing.JLabel jLabel_status_reminder;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
