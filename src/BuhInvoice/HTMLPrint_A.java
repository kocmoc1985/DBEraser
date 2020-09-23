/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import static BuhInvoice.GP_BUH._get;
import BuhInvoice.sec.LANG;
import com.qoppa.pdfWriter.PDFPrinterJob;
import forall.HelpA;
import java.awt.Color;
import java.awt.Desktop;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private final String FAKTURA_TYPE;

    private final static Dimension A4_PAPER = new Dimension(545, 842);

    /**
     * Creates new form HTMLPrint_A
     */
    public HTMLPrint_A(
            BUH_INVOICE_MAIN bim,
            String fakturatype,
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
        this.setIconImage(GP_BUH.getBuhInvoicePrimIcon());
        //
        this.articles_map_list = articles_map_list;
        //
        this.bim = bim;
        this.FAKTURA_TYPE = fakturatype;
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
            ".fontStd {font-size:9pt; color:gray;}",
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
    
    
    private String getFakturaId(){
        return  map_a_0.get(DB.BUH_FAKTURA__ID__);
    }

    private String getPdfFakturaFileName(boolean appendKundId) {
        //
        if (appendKundId) {
            return "faktura_" + bim.getKundId() + ".pdf";
        } else {
            return "faktura.pdf";
        }
        //
    }

    private String getFakturaKundEmail() {
        return _get(map_e_2__lev_data, DB.BUH_FAKTURA_KUND___EMAIL);
    }

    private String getForetagsNamn() {
        return _get(map_f, DB.BUH_KUND__NAMN);
    }

    private String getFakturaDesktopPath() {
        return System.getProperty("user.home") + "/Desktop/" + getPdfFakturaFileName(false);
    }

    private String getEmailBody() {
        String body = "Du har fått " + getFakturaTitleBasedOnType_subject().toLowerCase() + " från: " + getForetagsNamn();
        return body;
    }

    private String _get_colon_sep(String key, HashMap<String, String> map) {
        return key + ": " + map.get(key);
    }

    private String _get_exist_a(String name, String value) {
        if (value.isEmpty() == false) {
            return ", <span class='bold'>" + name + "</span>: " + value;
        } else {
            return "";
        }
    }

    private String _get_exist_b(String name, String value) {
        if (value.isEmpty() == false) {
            return ", " + name + ": " + value;
        } else {
            return "";
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

    private String getFakturaTitleBasedOnType_subject() {
        if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_NORMAL)) {
            return "Faktura";
        } else if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_KREDIT)) {
            return "Kreditfaktura";
        } else if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_KONTANT)) {
            return "Kvitto";
        } else {
            return null;
        }
    }

    protected final static String getAttBetalaTitle(String fakturatype) {
        if (fakturatype.equals(DB.STATIC__FAKTURA_TYPE_NORMAL)) {
            return "ATT BETALA";
        } else if (fakturatype.equals(DB.STATIC__FAKTURA_TYPE_KREDIT)) {
            return "ATT ERHÅLLA";
        } else if (fakturatype.equals(DB.STATIC__FAKTURA_TYPE_KONTANT)) {
            return "BETALD";
        } else {
            return null;
        }
    }

    private String getForfalloDatumFlexCol() {
        if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_NORMAL)) { // NORMAL
            return _get_colon_sep(T__FAKTURA_FORFALLODATUM__FLEX, map_c);
        } else if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_KREDIT)) { // KREDIT
            return _get_colon_sep(T__FAKTURA_FORFALLODATUM__FLEX, map_c);
        } else if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_KONTANT)) { // KONTANT
            return _get_colon_sep(T__FAKTURA_BETAL_METOD, map_c);
        } else {
            return null;
        }
    }

    private String getBetalVilkorFlexCol() {
        if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_NORMAL)) { // NORMAL
            return _get_colon_sep(T__FAKTURA_BETAL_VILKOR__FLEX, map_c);
        } else if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_KREDIT)) { // KREDIT
            return _get_colon_sep(T__FAKTURA_BETAL_VILKOR__FLEX, map_c);
        } else if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_KONTANT)) { // KONTANT
            return _get_colon_sep(T__FAKTURA_UTSKRIVET, map_c);
        } else {
            return null;
        }
    }

    private String getDrojsmalsrantaFlexCol() {
        if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_NORMAL)) { // NORMAL
            return _get_colon_sep(T__FAKTURA_DROJMALSRANTA__FLEX, map_c);
        } else if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_KREDIT)) { // KREDIT
            return _get_colon_sep(T__FAKTURA_KREDITERAR_FAKTURA_NR, map_c);
        } else if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_KONTANT)) { // KONTANT
            return "";
        } else {
            return null;
        }
    }

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
    public static final String T__FAKTURA_BETAL_VILKOR__FLEX = "Betalningsvilkor";
    public static final String T__FAKTURA_FORFALLODATUM__FLEX = "Förfallodag";
    public static final String T__FAKTURA_DROJMALSRANTA__FLEX = "Dröjsmålsränta";
    public static final String T__FAKTURA_BETAL_METOD = "Betalmetod"; // only for "kontantfaktura"
    public static final String T__FAKTURA_UTSKRIVET = "Utskrivet"; // only for "kontantfaktura"
    public static final String T__FAKTURA_KREDITERAR_FAKTURA_NR = "Krediterar fakturanr";
    public static final String T__FAKTURA_XXXXXXX = "Ledig*";
    //
    public static final String T__FAKTURA_FRAKT = "Frakt";
    public static final String T__FAKTURA_EXP_AVG = "Exp avg";
    public static final String T__FAKTURA_EXKL_MOMS = "Exkl moms";
    public static final String T__FAKTURA_MOMS_PERCENT = "Moms %";
    public static final String T__FAKTURA_MOMS_KR = "Moms kr";
    public static final String T__FAKTURA_RABATT_KR = "Rabatt kr";
    public static final String T__FAKTURA_ATT_BETALA = "ATT BETALA";
    //
    public static final String COL_0 = DB.BUH_FAKTURA_KUND___NAMN;
    public static final String COL_1 = DB.BUH_ADDR__ADDR_A;
    public static final String COL_2 = DB.BUH_ADDR__POSTNR_ZIP;
    public static final String COL_3 = DB.BUH_ADDR__ORT;
    public static final String COL_4 = DB.BUH_ADDR__TEL_A;
    //
    public static final String T__ARTIKEL_NAMN = "Artikel";
    public static final String T__ARTIKEL_KOMMENT = "Beskrivning";
    public static final String T__ARTIKEL_ANTAL = "Antal";
    public static final String T__ARTIKEL_ENHET = "Enhet";
    public static final String T__ARTIKEL_RABATT = "Rabatt%";
    public static final String T__ARTIKEL_PRIS = "A`Pris";
    //
    public static final String T__FTG_KONTAKTA_OSS = "Kontakta oss";
    public static final String T__FTG_BETALA_TILL = "Betala till";
    public static final String T__FTG_TELEFON = "Telefon, ring";
    public static final String T__FTG_EPOST = "Mejla oss på";
    public static final String T__FTG_BANKGIRO = "BG";
    public static final String T__FTG_POSTGIRO = "PG";
    public static final String T__FTG_KONTO = "Konto";
    public static final String T__FTG_SWISH = "Swish";
    public static final String T__FTG_ORGNR = "Organisationsnr";
    public static final String T__FTG_MOMS_REG_NR = "Momsregnr";
    public static final String T__FTG_F_SKATT = "Godkänd för F-skatt";

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
                + betal_alternativ_to_html()
                //
                + faktura_data_B_to_html__totals()
                //
                + articles_to_html(articles_map_list)
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

//    private String _get(HashMap<String, String> map, String param) {
//        //
//        String val = map.get(param);
//        //
//        if (val == null || val.isEmpty() || val.equals("null") || val.equals("NULL")) {
//            return "";
//        } else {
//            return val;
//        }
//    }
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
        int br_to_add = 17 - articles_map_list.size();
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
        //
        if (imgPath != null) {
            return "<td rowspan='2' class='paddingLeft'><img src='" + imgPath + "' alt='image'></td>" // width='32' height='32'
                    + "<td><h1 class='marginLeft'>" + getFakturaTitleBasedOnType_subject() + "</h1></td>";
        } else {
            return "<td rowspan='2'><h1 class='marginLeft'>" + map_f.get(DB.BUH_KUND__NAMN) + "</h1></td>"
                    + "<td><h1 class='marginLeft'>" + getFakturaTitleBasedOnType_subject() + "</h1></td>";

        }
        //
    }

    private String adresses_to_html() {
        //
        String html_ = "<table class='marginTop'>";
        //
        String[] values_a = new String[]{
            _get(map_e_2__lev_data, COL_0),
            _get(map_e, COL_1),
            _get(map_e, COL_2) + " " + _get(map_e, COL_3)
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
            T__FAKTURA_ER_REF + ": " + map_b.get(T__FAKTURA_ER_REF),
            T__FAKTURA_VAR_REF + ": " + map_c.get(T__FAKTURA_VAR_REF),};
        //
        String[] values_t_2 = new String[]{
            T__FAKTURA_ERT_ORDER_NR + ": " + map_b.get(T__FAKTURA_ERT_ORDER_NR),
            T__FAKTURA_LEV_VILKOR + ": " + map_b.get(T__FAKTURA_LEV_VILKOR) + " / " + T__FAKTURA_LEV_SATT + ": " + map_b.get(T__FAKTURA_LEV_SATT)};
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
        html_ += "<td>";
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

    private String faktura_data_B_to_html__totals() {
        //
        String html_ = "<div class='marginTop'>";//<table class='marginTop'>
        //
        String ATT_BETALA_TITLE = getAttBetalaTitle(FAKTURA_TYPE);
        //
        String[] headers = new String[]{T__FAKTURA_FRAKT, T__FAKTURA_EXP_AVG, T__FAKTURA_EXKL_MOMS, T__FAKTURA_MOMS_PERCENT, T__FAKTURA_MOMS_KR, T__FAKTURA_RABATT_KR, ATT_BETALA_TITLE};
        String[] values = new String[]{map_d.get(T__FAKTURA_FRAKT), map_d.get(T__FAKTURA_EXP_AVG), map_d.get(T__FAKTURA_EXKL_MOMS), map_d.get(T__FAKTURA_MOMS_PERCENT), map_d.get(T__FAKTURA_MOMS_KR), map_d.get(T__FAKTURA_RABATT_KR), map_d.get(ATT_BETALA_TITLE)};
        //
        html_ += internal_table_2r_xc(headers, values, 7, "");
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
        html_ += "<td>" + T__ARTIKEL_RABATT + "</td>";
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
            html_ += "<td>" + _get(map, DB.BUH_F_ARTIKEL__RABATT) + "</td>";
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
        jLabel2_separator = new javax.swing.JLabel();
        jButton_send_faktura_email = new javax.swing.JButton();
        jButton_send_with_outlook = new javax.swing.JButton();
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
        jButton1.setToolTipText("Skriv ut faktura");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jPanel1.add(jLabel2_separator);

        jButton_send_faktura_email.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/post.png"))); // NOI18N
        jButton_send_faktura_email.setToolTipText("Skicka faktura per E-post, automatiskt");
        jButton_send_faktura_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_send_faktura_emailActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_send_faktura_email);

        jButton_send_with_outlook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/send_b.png"))); // NOI18N
        jButton_send_with_outlook.setToolTipText("Skicka faktura per E-post med Outlook");
        jButton_send_with_outlook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_send_with_outlookActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_send_with_outlook);
        jPanel1.add(jLabel1_separator);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/image.png"))); // NOI18N
        jButton3.setToolTipText("Välj logotyp / bild");
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
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jEditorPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(82, Short.MAX_VALUE))
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
        //
        boolean print_ok = print_normal();
        //
        if (print_ok) {
            EditPanel_Send.insert(bim.getFakturaId(), DB.STATIC__SENT_STATUS__UTSKRIVEN, DB.STATIC__SENT_TYPE_FAKTURA);
        }
        //
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton_send_faktura_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_send_faktura_emailActionPerformed
        //
        String faktura_kund_email = getFakturaKundEmail();
        String ftg_name = getForetagsNamn();
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
        String fakturaFileName = getPdfFakturaFileName(true);
        //
        print_upload_sendmail__thr(
                "uploads/",
                fakturaFileName,
                faktura_kund_email,
                ftg_name
        );
        //

        //
    }//GEN-LAST:event_jButton_send_faktura_emailActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //
        GP_BUH.chooseLogo(this);
        //
        go();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton_send_with_outlookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_send_with_outlookActionPerformed
        sendWithStandardEmailClient();
    }//GEN-LAST:event_jButton_send_with_outlookActionPerformed

    private String mailTo(String mailto, String subject, String body) {
        //
        String mailToFunc = "mailTo:" + mailto + "?subject=" + subject.replaceAll(" ", "%20")
                + "&body=" + body.replaceAll(" ", "%20");
        //
        return mailToFunc;
    }

    /**
     * This will work with all mail clients, but it does not attach
     * automatically. So the solution is to silently write ".pdf" to desktop,
     * and give the user a message where to find the file
     */
    private void sendWithStandardEmailClient() {
        //
        String mailto = getFakturaKundEmail();
        String subject = getFakturaTitleBasedOnType_subject();
        String body = getEmailBody();
        String desktopPath = getFakturaDesktopPath();
        //
        print_java(desktopPath);
        HelpA.showNotification(LANG.FAKTURA_UTSKRIVEN_OUTLOOK(getPdfFakturaFileName(false)));
        //
        Desktop desktop = Desktop.getDesktop();
        String url;
        URI mailTo;
        //
        try {
            // Attachments not working with "mailTo:" 100% verified [2020-09-23]
            url = mailTo(mailto, subject, body);
            //
            System.out.println("URL: " + url);
            //
            mailTo = new URI(url);
            desktop.mail(mailTo);
            //
            //
            fakturaSentPerEpost_saveToDb(getFakturaId(), DB.STATIC__SENT_STATUS__SKICKAD_OUTLOOK);
            //
            //
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private void sendWithOutlookB() {
        ///"path/to/Outlook.exe /c ipm.note /a \"path/to/attachment\""
        // see also:  https://stackoverflow.com/questions/6045816/to-open-outlook-mail-from-java-program-and-to-attach-file-to-the-mail-from-direc
        String[] commands = new String[5];
        commands[0] = "C:\\Program Files\\Outlook Express\\msimn.exe";
        commands[1] = "/c";
        commands[2] = "ipm.note";
        commands[3] = "/a";
        commands[4] = getPathNormal(getPdfFakturaFileName(true));
        ProcessBuilder builder = new ProcessBuilder(commands);
        try {
            builder.start();
        } catch (IOException ex) {
            Logger.getLogger(HTMLPrint_A.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
                String fakturaId = getFakturaId();
                // 
                // [2020-09-08]
                if (ok) {
                    //
                    fakturaSentPerEpost_saveToDb(fakturaId,DB.STATIC__SENT_STATUS__SKICKAD);
                    //
//                    EditPanel_Send.insert(fakturaId, DB.STATIC__SENT_STATUS__SKICKAD,
//                            DB.STATIC__SENT_TYPE_FAKTURA); // "buh_faktura_send" table
//                    //
//                    Basic_Buh_.executeSetFakturaSentPerEmail(fakturaId); // "buh_faktura" table -> update sent status
//                    bim.setValueAllInvoicesJTable(InvoiceB.TABLE_ALL_INVOICES__EPOST_SENT, DB.STATIC__YES);
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

    private void fakturaSentPerEpost_saveToDb(String fakturaId,String sendStatus) {
        //
        EditPanel_Send.insert(fakturaId, sendStatus,
                DB.STATIC__SENT_TYPE_FAKTURA); // "buh_faktura_send" table
        //
        Basic_Buh_.executeSetFakturaSentPerEmail(fakturaId); // "buh_faktura" table -> update sent status
        bim.setValueAllInvoicesJTable(InvoiceB.TABLE_ALL_INVOICES__EPOST_SENT, DB.STATIC__YES);
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
        displayStatus(LANG.MSG_10, null);
        //
        print_java(fileName);
        //
//        System.out.println("Print pdf complete");
        displayStatus(LANG.MSG_10_1, null);
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
        String subject = getFakturaTitleBasedOnType_subject();
        String body = getEmailBody();
        //
        if (upload_success) {
            //
            email_sending_ok = HelpBuh.sendEmailWithAttachment("ask@mixcont.com",
                    GP_BUH.PRODUCT_NAME, // This one is shown as name instead of the email it's self
                    sendToEmail,
                    subject,
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
            displayStatus(LANG.MSG_10_3, Color.red);
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
        if (actHeight > A4_PAPER.getHeight()) {
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
                new HTMLPrint_A(null, null, null, null, null, null, null, null, null, null, null, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton_send_faktura_email;
    private javax.swing.JButton jButton_send_with_outlook;
    protected javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1_separator;
    private javax.swing.JLabel jLabel2_separator;
    protected static javax.swing.JLabel jLabel_status;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
