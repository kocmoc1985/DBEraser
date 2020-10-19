/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import static BuhInvoice.GP_BUH._get;
import static BuhInvoice.HTMLPrint.T__FAKTURA_MOMS_PERCENT;
import BuhInvoice.sec.HeadersValuesHTMLPrint;
import BuhInvoice.sec.LANG;
import forall.HelpA;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import org.apache.commons.lang.ArrayUtils;

/**
 *
 * @author KOCMOC
 */
public class HTMLPrint_B extends HTMLPrint {

    public HTMLPrint_B(
            BUH_INVOICE_MAIN bim,
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
        super(bim, fakturatype, preview, articles_map_list, map_a_0, map_a, map_b, map_c, map_d, map_e, map_e_2, map_f, map_g);
    }

    @Override
    protected void buttonLogic() {
        //
    }

    @Override
    protected String getWindowTitle() {
        if (preview) {
            return LANG.FRAME_TITLE_1_3;
        } else {
            return LANG.FRAME_TITLE_1_2;
        }
    }

    @Override
    protected JEditorPane getEditorPane() {
        return this.jEditorPane1;
    }

    @Override
    protected JScrollPane getJScrollPane() {
        return jScrollPane2;
    }

    @Override
    protected String[] getCssRules() {
        String[] CSSRules = {
            //            "table {margin-bottom:10px;}",
            "table {width: 99%;}",
            //            "img {width: 20px}", not working from here
            ".fontStd {font-size:9pt; color:gray;}",
            "table {font-size:9pt; color:gray;}", // 9pt seems to be optimal
            //            "table {border: 1px solid black}",
            "td {border: 1px solid black;}",
            "td {padding-left: 4px;}",
            //
            ".marginTop {margin-top: 5px;}",
            ".marginLeft {margin-left: 10px;}",
            ".paddingLeft {padding-left: 5px;}",
            ".bold {font-weight:800;}", // font-weight:800;
            ".no-border {border: 0px}",
            ".border-a {border: 1px solid black;}",
            ".text-md {font-size:11pt; color:black;}"
        //    
        };
        //
        return CSSRules;
        //
    }

    protected final static String getAttBetalaTitle() {
        return "ATT BETALA";
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
    protected String buildHTML() {
        // 
//        String img_a = getPathResources("images", "mixcont_logo_md.png"); // WORKING
        // 
        String img_a = getPathNormal("logo.png");
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

    @Override
    protected String getHTMLPrintTitle() {
        return LANG.PAMINNELSE;
//          return "Påminnelse";
    }

    private String titleOrLogoIfExist(String imgPath) {
        //
        if (imgPath != null) {
            return "<td rowspan='2' class='paddingLeft'><img src='" + imgPath + "' alt='image'></td>" // width='32' height='32'
                    + "<td><h1 class='marginLeft'>" + getHTMLPrintTitle() + "</h1></td>";
        } else {
            return "<td rowspan='2'><h1 class='marginLeft'>" + map_f.get(DB.BUH_KUND__NAMN) + "</h1></td>"
                    + "<td><h1 class='marginLeft'>" + getHTMLPrintTitle() + "</h1></td>";

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
                + "<td>"
                + internal_table_x_r_1c(3, values_a, false)
                + "</td>"
                //
                + "<td>"
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
            T__FAKTURA_VAR_REF + ": " + map_c.get(T__FAKTURA_VAR_REF),
            T__FTG_TELEFON + ": " + _get(map_g, DB.BUH_ADDR__TEL_A)};
        //
        String[] values_t_2 = new String[]{
            T__FTG_EPOST + ": " + _get(map_f, DB.BUH_KUND__EPOST),
            "Notis" + ": "
        };
        //
        html_ += "<tr>"
                //
                + "<td>"
                + internal_table_x_r_1c(2, values_t_1, false)
                + "</td>"
                //
                + "<td>"
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
        html_ += "<td style='padding:5 5 5 5px;'>";
        html_ += T__FTG_BETALA_TILL
                + _get_exist_a(T__FTG_BANKGIRO, _get(map_f, DB.BUH_KUND__BANK_GIRO))
                + _get_exist_a(T__FTG_POSTGIRO, _get(map_f, DB.BUH_KUND__POST_GIRO))
                + _get_exist_a(T__FTG_SWISH, _get(map_f, DB.BUH_KUND__SWISH))
                + _get_exist_a(T__FTG_KONTO, _get(map_f, DB.BUH_KUND__KONTO));
        html_ += "</td>";
        //
        html_ += "</tr>";
        //
        html_ += "</table>";
        //
        return html_;
    }
    
    private double countDrojsmalsAvgift(){
       String dateNow = HelpA.get_proper_date_yyyy_MM_dd();
       String drojmalsranta = _get(map_c, T__FAKTURA_DROJMALSRANTA__FLEX);
       String forfallodatum = _get(map_c, T__FAKTURA_FORFALLODATUM__FLEX);
       double totalInklMoms = Double.parseDouble(map_d.get(getAttBetalaTitle()));
       String dateFormat = GP_BUH.DATE_FORMAT_BASIC;
       //
       int daysForfallen = HelpA.get_diff_in_days__two_dates(dateNow, dateFormat, forfallodatum, dateFormat);
       //
       if(daysForfallen <=0){
           return 0;
       }
       //
       System.out.println("Days forfallen: " + daysForfallen);
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
        int colToMakeBold = 8;
        //
        String moms_kr = map_d.get(T__FAKTURA_MOMS_KR);
        String frakt = map_d.get(T__FAKTURA_FRAKT);
        String exp = map_d.get(T__FAKTURA_EXP_AVG);
        String rabatt = map_d.get(T__FAKTURA_RABATT_KR);
        //
        String ATT_BETALA_TITLE = getAttBetalaTitle();
        Double totalInkDrojAvg = GP_BUH.round_double(Double.parseDouble(map_d.get(ATT_BETALA_TITLE)) + drojAvg);
        //
        String[] headers = new String[]{T__FAKTURA_FRAKT, T__FAKTURA_EXP_AVG, T__FAKTURA_EXKL_MOMS, T__FAKTURA_MOMS_PERCENT, T__FAKTURA_MOMS_KR, T__FAKTURA_RABATT_KR,T__FAKTURA_DROJMALSRANTA__FLEX, ATT_BETALA_TITLE};
        String[] values = new String[]{frakt, exp, map_d.get(T__FAKTURA_EXKL_MOMS), map_d.get(T__FAKTURA_MOMS_PERCENT), moms_kr, rabatt,""+drojAvg, ""+totalInkDrojAvg};
        //
        HeadersValuesHTMLPrint hvp = excludeIfZero(headers, values, colToMakeBold, moms_kr, frakt, exp, rabatt);
        //
        html_ += internal_table_2r_xc(hvp.getHeaders(), hvp.getValues(), hvp.getColToMakeBold(), "");
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
        html_ += "<p class='fontStd' style='text-align:center'>";
        html_ += _get(map_f, DB.BUH_KUND__NAMN) + "," + _get(map_g, DB.BUH_ADDR__POSTNR_ZIP) + " " + _get(map_g, DB.BUH_ADDR__ADDR_A) + ".";
        html_ += "</p>";
        //
        html_ += "<div class='fontStd' style='text-align:center'>";
        html_ += T__FTG_KONTAKTA_OSS + _get_exist_b(T__FTG_TELEFON, _get(map_g, DB.BUH_ADDR__TEL_A))
                + _get_exist_b(T__FTG_EPOST, _get(map_f, DB.BUH_KUND__EPOST)) + ".";

//        html_ += T__FTG_TELEFON + " " + _get(map_g, DB.BUH_ADDR__TEL_A) + ". " + T__FTG_EPOST + " " + _get(map_f, DB.BUH_KUND__EPOST);
        html_ += "</div>";
        //
        html_ += "<div class='fontStd' style='text-align:center'>";
        html_ += T__FTG_F_SKATT + ": " + _get_longname(map_f, DB.BUH_KUND__F_SKATT, DB.STATIC__JA_NEJ) + ". "
                + T__FTG_ORGNR + ": " + _get(map_f, DB.BUH_KUND__ORGNR) + ". "
                + T__FTG_MOMS_REG_NR + ": " + _get(map_f, DB.BUH_KUND__VATNR) + ".";
        html_ += "</div>";
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
        html_ += "<span class='no-border'>";
        //
        if (list == null || list.isEmpty()) {
            return "";
        }
        //
        html_ += "<tr class='bold'>";
        //
        html_ += "<td class='no-border'>" + T__ARTIKEL_NAMN + "</td>";
        html_ += "<td class='no-border'>" + T__ARTIKEL_KOMMENT + "</td>";
        html_ += "<td class='no-border'>" + T__ARTIKEL_ANTAL + "</td>";
        html_ += "<td class='no-border'>" + T__ARTIKEL_ENHET + "</td>";
        html_ += "<td class='no-border'>" + T__ARTIKEL_RABATT + "</td>";
        html_ += "<td class='no-border'>" + T__ARTIKEL_PRIS + "</td>";
        //
        html_ += "</tr>";
        //
        //
        for (HashMap<String, String> map : list) {
            //
            html_ += "<tr>";
            //
            html_ += "<td class='no-border'>" + _get(map, DB.BUH_FAKTURA_ARTIKEL___NAMN) + "</td>";
            html_ += "<td class='no-border'>" + _get(map, DB.BUH_F_ARTIKEL__KOMMENT) + "</td>";
            html_ += "<td class='no-border'>" + _get(map, DB.BUH_F_ARTIKEL__ANTAL) + "</td>";
            html_ += "<td class='no-border'>" + _get(map, DB.BUH_F_ARTIKEL__ENHET) + "</td>";
            html_ += "<td class='no-border'>" + _get(map, DB.BUH_F_ARTIKEL__RABATT) + "</td>";
            html_ += "<td class='no-border'>" + _get(map, DB.BUH_F_ARTIKEL__PRIS) + "</td>";
            //
            html_ += "</tr>";
            //
        }
        //
        html_ += "</span>"; // "class='no-border'"
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
        jButton1 = new javax.swing.JButton();
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

        jPanel1.setLayout(new java.awt.GridLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        jButton1.setToolTipText("Skriv ut påminnelse");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

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
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
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


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //
        boolean print_ok = print_normal();
        //
        if (print_ok) {
            EditPanel_Send.insert(bim.getFakturaId(), DB.STATIC__SENT_STATUS__UTSKRIVEN, DB.STATIC__SENT_TYPE_FAKTURA);
        }
        //
    }//GEN-LAST:event_jButton1ActionPerformed

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
        sendWithStandardEmailClient();
    }//GEN-LAST:event_jButton_send_with_outlookActionPerformed

    private void jButton_send_faktura_any_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_send_faktura_any_emailActionPerformed
        sendMailTargeted();
    }//GEN-LAST:event_jButton_send_faktura_any_emailActionPerformed

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

    private void print_upload_sendmail__thr(String serverPath, String fileName, String sendToEmail, String ftgName) {
        Thread x = new Thread(new Runnable() {
            @Override
            public void run() {
                //
                //
                boolean ok = print_upload_sendmail(serverPath, fileName, sendToEmail, ftgName);
                //
                String fakturaId = getFakturaId();
                // 
                // [2020-09-08]
                if (ok) {
                    //
                    loggDocumentSent(fakturaId, DB.STATIC__SENT_STATUS__SKICKAD, DB.STATIC__SENT_TYPE_PAMMINELSE);
                    //
                } else {
                    EditPanel_Send.insert(fakturaId, DB.STATIC__SENT_STATUS__EJ_SKICKAD,
                            DB.STATIC__SENT_TYPE_PAMMINELSE);
                }
            }
        });
        //
        x.start();
        //
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
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
