/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.LANG;
import com.qoppa.pdfWriter.PDFPrinterJob;
import forall.GP;
import forall.HelpA;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author KOCMOC
 */
public class HTMLPrint_A extends javax.swing.JFrame {

    private final BUH_INVOICE_MAIN bim;
    private final ArrayList<HashMap<String, String>> articles_map_list;
    private final HashMap<String, String> map_a_0;
    private final HashMap<String, String> map_a;
    private final HashMap<String, String> map_b;
    private final HashMap<String, String> map_c;
    private final HashMap<String, String> map_d;
    private final HashMap<String, String> map_e;
    private final HashMap<String, String> map_e_2__lev_data;
    private final HashMap<String, String> map_f;
    private final HashMap<String, String> map_g;

    private final static Dimension A4_PAPER = new Dimension(545, 842);

    /**
     * Creates new form HTMLPrint_A
     */
    public HTMLPrint_A(
            BUH_INVOICE_MAIN bim,
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
        //
        initComponents();
        //
        this.setTitle("Skriv ut faktura");
        this.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL_PROD_PLAN).getImage());
        //
        this.articles_map_list = articles_map_list;
        //
        this.bim = bim;
        this.map_a_0 = map_a_0;
        this.map_a = map_a;
        this.map_b = map_b;
        this.map_c = map_c;
        this.map_d = map_d;
        this.map_e = map_e;
        this.map_e_2__lev_data = map_e_2;
        this.map_f = map_f;
        this.map_g = map_g;
        //
        initOther();
        //
        go();
        //
        scrollToTop();
    }

    private void initOther() {
        //
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        //
        int height = (int) (d.height * 0.9);
        //
        setSize(getWidth(), height);
        //
    }

    private void scrollToTop() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                jScrollPane2.getVerticalScrollBar().setValue(0);
            }
        });
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
            ".marginLeft {margin-left: 10px;}",
            ".paddingLeft {padding-left: 5px;}",
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
    public static final String T__FAKTURA_ATT_BETALA = "ATT BETALA"; //Leveransadress
    //
    public static final String T__FAKTURA_LEV_ADDR_TITLE = "Leveransadress";
    public static final String T__FAKTURA_INVOICE_ADDR_TITLE = "Fakturaadress";
    public static final String COL_1 = DB.BUH_ADDR__ADDR_A;
    public static final String COL_2 = DB.BUH_ADDR__POSTNR_ZIP;
    public static final String COL_3 = DB.BUH_ADDR__ORT;
    public static final String COL_4 = DB.BUH_ADDR__TEL_A;
    //
    public static final String T__ARTIKEL_NAMN = "Artikel";
    public static final String T__ARTIKEL_KOMMENT = "Beskrivning";
    public static final String T__ARTIKEL_ANTAL = "Antal";
    public static final String T__ARTIKEL_ENHET = "Enhet";
    public static final String T__ARTIKEL_PRIS = "A`Pris";
    //
    public static final String T__FTG_TELEFON = "Telefon"; // Innehar F-skattebevis
    public static final String T__FTG_EPOST = "E-post";
    public static final String T__FTG_BANKGIRO = "Bankgiro";
    public static final String T__FTG_ORGNR = "Organisationsnr";
    public static final String T__FTG_MOMS_REG_NR = "Momsreg.nr";
    public static final String T__FTG_F_SKATT = "Innehar F-skattebevis";

    /**
     * Use this one when, getting the image from the "inside project / .jar
     * file"
     *
     * @param path
     * @param imgName
     * @return - like: "file:/J:/MyDocs/src/...."
     */
    private String getPathResources(String path, String imgName) {
        return getImageIconURL(path, imgName).toString();
    }

    /**
     * Use this one when, getting the image from inside the "project dir / root"
     *
     * @param pathAndFileName
     * @return - like: "file:/J:/MyDocs/src/...."
     */
    private String getPathNormal(String pathAndFileName) {
        //
        File f = new File(pathAndFileName);
        //
        if (f.exists() == false) {
            return null;
        }
        //
        try {
            return new File(pathAndFileName).toURI().toURL().toString();
        } catch (MalformedURLException ex) {
            Logger.getLogger(HTMLPrint_A.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

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

    private String _get(HashMap<String, String> map, String param) {
        //
        String val = map.get(param);
        //
        if (val == null || val.isEmpty() || val.equals("null") || val.equals("NULL")) {
            return "";
        } else {
            return val;
        }
    }

    private String _get_longname(HashMap<String, String> map, String param, String statics) {
        //
        String val = map.get(param);
        //
        if (val == null || val.isEmpty() || val.equals("null") || val.equals("NULL")) {
            return "";
        } else {
            return JSon.getLongName(statics, val);
        }
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
            return "<td rowspan='2' class='paddingLeft'><img src='" + imgPath + "' alt='image'></td>" // width='32' height='32'
                    + "<td><h1 class='marginLeft'>" + T__FAKTURA_TITLE + "</h1></td>";
        } else {
            return "<td rowspan='2'><h1 class='marginLeft'>" + COMPANY_NAME + "</h1></td>"
                    + "<td><h1 class='marginLeft'>" + T__FAKTURA_TITLE + "</h1></td>";

        }
    }

    private String adresses_to_html() {
        //
        String html_ = "<table class='marginTop'>";
        //

        String[] values_a = new String[]{T__FAKTURA_LEV_ADDR_TITLE, _get(map_e, COL_1), _get(map_e, COL_2), _get(map_e, COL_3), _get(map_e, COL_4)};
        String[] values_b = new String[]{T__FAKTURA_INVOICE_ADDR_TITLE, _get(map_e, COL_1), _get(map_e, COL_2), _get(map_e, COL_3), _get(map_e, COL_4)};
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
        String[] headers = new String[]{T__FTG_TELEFON, T__FTG_EPOST, T__FTG_BANKGIRO, T__FTG_ORGNR, T__FTG_MOMS_REG_NR, T__FTG_F_SKATT};
        String[] values = new String[]{_get(map_g, DB.BUH_ADDR__TEL_A), _get(map_f, DB.BUH_KUND__EPOST),
            _get(map_f, DB.BUH_KUND__BANK_GIRO), _get(map_f, DB.BUH_KUND__ORGNR),
            _get(map_f, DB.BUH_KUND__IBAN), _get_longname(map_f, DB.BUH_KUND__F_SKATT, DB.STATIC__JA_NEJ)};
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
        html_ += "<td>" + T__ARTIKEL_NAMN + "</td>";
        html_ += "<td>" + T__ARTIKEL_KOMMENT + "</td>";
        html_ += "<td>" + T__ARTIKEL_ANTAL + "</td>";
        html_ += "<td>" + T__ARTIKEL_ENHET + "</td>";
        html_ += "<td>" + T__ARTIKEL_PRIS + "</td>";
        //
        html_ += "</tr>";
        //
        //
        for (HashMap<String, String> map : list) {
            //
            html_ += "<tr>";
            //
            html_ += "<td>" + _get(map, DB.BUH_FAKTURA_ARTIKEL___NAMN) + "</td>";
            html_ += "<td>" + _get(map, DB.BUH_F_ARTIKEL__KOMMENT) + "</td>";
            html_ += "<td>" + _get(map, DB.BUH_F_ARTIKEL__ANTAL) + "</td>";
            html_ += "<td>" + _get(map, DB.BUH_F_ARTIKEL__ENHET) + "</td>";
            html_ += "<td>" + _get(map, DB.BUH_F_ARTIKEL__PRIS) + "</td>";
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

    protected URL getImageIconURL(String picName) {
        //OBS! YES the first "/" is NEEDED - 100% [2020-06-09]
        return HTMLPrint_A.class.getResource("../../../../../" + picName);
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jEditorPane1 = new javax.swing.JEditorPane();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1_separator = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel_status = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jEditorPane1.setMaximumSize(new java.awt.Dimension(545, 842));
        jEditorPane1.setMinimumSize(new java.awt.Dimension(545, 842));

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/post.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jPanel1.add(jLabel1_separator);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/image.png"))); // NOI18N
        jButton3.setToolTipText("Välj logotyp");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);

        jLabel_status.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_status.setForeground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jEditorPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel_status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_status, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jEditorPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 842, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jScrollPane2.setViewportView(jPanel2);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        boolean print_ok = print_normal();
        //
        if(print_ok){
            EditPanel_Send.insert(bim.getFakturaId(), DB.STATIC__SENT_STATUS__UTSKRIVEN, DB.STATIC__SENT_TYPE_FAKTURA);
        }
        //
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //
        String faktura_kund_email = _get(map_e_2__lev_data, DB.BUH_FAKTURA_KUND___EMAIL);
        String ftg_name = _get(map_f, DB.BUH_KUND__NAMN);
        //
        if (faktura_kund_email == null || faktura_kund_email.isEmpty()) {
            HelpA.showNotification(LANG.MSG_7);
            return;
        }
        //
        if (GP_BUH.confirmWarning(LANG.CONFIRM_SEND_MAIL(faktura_kund_email)) == false) {
            return;
        }
        //
//        System.out.println("faktura_kund_email: " + faktura_kund_email);
//        System.out.println("ftg_name: " + ftg_name);
        //
        String fakturaFileName =  "faktura_" + bim.getKundId() + ".pdf";
        //
        print_upload_sendmail__thr(
                "uploads/",
                fakturaFileName,
                faktura_kund_email,
                ftg_name
        );
        //

        //
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //
        GP_BUH.chooseLogo(this);
        //
        go();
    }//GEN-LAST:event_jButton3ActionPerformed

    public static void displayStatus(String msg, Color c) {
        //
        if (c != null) {
            jLabel_status.setForeground(c);
        } 
        //
        jLabel_status.setText(msg);
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
                String fakturaId = map_a_0.get(DB.BUH_FAKTURA__ID__);
                // 
                // [2020-09-08]
                if (ok) {
                    //
                    EditPanel_Send.insert(fakturaId, DB.STATIC__SENT_STATUS__SKICKAD,
                            DB.STATIC__SENT_TYPE_FAKTURA); // "buh_faktura_send" table
                    //
                    Basic_Buh_.executeSetFakturaSentPerEmail(fakturaId); // "buh_faktura" table -> update sent status
                    bim.setValueAllInvoicesJTable(InvoiceB.TABLE_ALL_INVOICES__EPOST_SENT, DB.STATIC__YES);
                    //
                } else {
                    EditPanel_Send.insert(fakturaId, DB.STATIC__SENT_STATUS__EJ_SKICKAD,
                            DB.STATIC__SENT_TYPE_FAKTURA);
                }
            }
        });
        //
        x.start();
        //
    }

    /**
     * [2020-09-03]
     *
     * @param serverPath - must end with "/"
     * @param fileName - like: "test.pdf"
     * @param sendToEmail
     * @param ftgName - The company from which this email is sent
     */
    protected boolean print_upload_sendmail(String serverPath, String fileName, String sendToEmail, String ftgName) {
        //
        displayStatus(LANG.MSG_10,null);
        //
        print_java(fileName);
        //
//        System.out.println("Print pdf complete");
        displayStatus(LANG.MSG_10_1,null);
        //
        //
        boolean upload_success = false;
        //
        try {
           upload_success = HelpBuh.uploadFile(fileName, serverPath + fileName); //[clientPath][ServerPath]
        } catch (ProtocolException ex) {
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
//        System.out.println("Upload to PHP: " + upload_success);
        //
        //
        Boolean email_sending_ok = false;
        //
        String body = "Du har fått faktura från: " + ftgName;
        //
        if (upload_success) {
            //
            email_sending_ok = HelpBuh.sendEmailWithAttachment("ask@mixcont.com",
                    GP_BUH.PRODUCT_NAME, // This one is shown as name instead of the email it's self
                    sendToEmail,
                    "Faktura",
                    body,
                    serverPath + fileName
            );
            //
        }
        //
        if (upload_success && email_sending_ok) {
            System.out.println("Email sending: " + email_sending_ok);
            displayStatus(LANG.MSG_10_2, null);
            return true;
        } else {
            displayStatus(LANG.MSG_10_3,Color.red);
            return false;
        }
        //
    }

    protected boolean print_normal() {
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
                return true;
            } catch (PrinterException ex) {
                Logger.getLogger(HTMLPrint_A.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        //
        return false;
        //
    }

    /**
     * [2020-09-03] uses: jPDFWriter.v2016R1.00.jar Enables silent print_java
     *
     * @param filename
     */
    protected void print_java(String filename) {
        //
        int actHeight = jEditorPane1.getHeight();
        //
        System.out.println("jeditorPane height: " + jEditorPane1.getHeight());
        //
//        if (actHeight >= A4_PAPER.getHeight()) {
//            HelpA.showNotification("A4 Height exceeded");
//        }
        //
        Paper paper = new Paper();
        paper.setSize(fromCMToPPI(21.0), fromCMToPPI(29.7)); // A4
        //
        // This one sets the margins
        paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight());
        //
        PageFormat pageFormat = new PageFormat();
        pageFormat.setPaper(paper);
        //
//        PrinterJob pj = PrinterJob.getPrinterJob(); // old
        PDFPrinterJob pj = (PDFPrinterJob) PDFPrinterJob.getPrinterJob(); // ******[JAVA PDF PRINT][2020-09-03]
        //
        PageFormat validatedFormat = pj.validatePage(pageFormat);
        //
        pj.setPrintable(jEditorPane1.getPrintable(null, null), validatedFormat);
        //
        //
        pj.setJobName(filename);
        //
        try {
//            pj.print_java();
            pj.print(filename); // [JAVA PDF PRINT]******[SILENT PRINT]
        } catch (PrinterException ex) {
            Logger.getLogger(HTMLPrint_A.class.getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(HTMLPrint_A.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HTMLPrint_A.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HTMLPrint_A.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HTMLPrint_A.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HTMLPrint_A(null, null, null, null, null, null, null, null, null, null, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    protected javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1_separator;
    protected static javax.swing.JLabel jLabel_status;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
