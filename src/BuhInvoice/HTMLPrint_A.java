/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import static BuhInvoice.GP_BUH._get;
import static BuhInvoice.HelpBuh.COMPANY_MIXCONT;
import static BuhInvoice.HelpBuh.EU_CUSTOMER;
import static BuhInvoice.HelpBuh.FOREIGN_CUSTOMER;
import static BuhInvoice.HelpBuh.LANG_ENG;
import BuhInvoice.sec.ChooseStamp;
import BuhInvoice.sec.HeadersValuesHTMLPrint;
import BuhInvoice.sec.LANG;
import forall.HelpA;
import icons.IconUrls;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

/**
 * "COMMON INVOICE" & "INVOICE PREVIEW"
 *
 * @author KOCMOC
 */
public class HTMLPrint_A extends HTMLPrint {

    private boolean OMVANT_SKATT__EXIST = false;
    private boolean STAMP_IN_USE = false;
    private int h;

    public HTMLPrint_A(
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
            HashMap<String, String> map_g,
            HashMap<String, String> map_rut,
            ArrayList<HashMap<String, String>> map_rut_pers
    ) {
        //
        super(bim, fakturatype, preview, articles_map_list, map_a_0, map_a, map_b, map_c, map_d, map_e, map_e_2, map_f, map_g, map_rut, map_rut_pers);
        //
    }

    @Override
    protected void buttonLogic() {
        //
        if (HelpBuh.IS_MAC_OS) {
            GP_BUH.setEnabled(jButton_send_with_outlook, false);
        }
        //
        if (preview) {
            GP_BUH.setEnabled(jButton_send_faktura_email, false);
            GP_BUH.setEnabled(jButton_send_with_outlook, false);
            GP_BUH.setEnabled(jButton_send_faktura_any_email, false);
            GP_BUH.setEnabled(jButton_send_with_common_post, false);
        }
    }

    @Override
    public String getWindowTitle() {
        if (preview) {
            return LANG.FRAME_TITLE_1_3;
        } else {
            return LANG.FRAME_TITLE_1;
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
            //            "table {border: 1px solid black}",//----------------------------->!!!!!!
            border__or_no_border,//------------------------------------>!!!!!
            "td {padding-left: 4px;}",
            //
            ".marginTop {margin-top: 5px;}",
            ".marginTopB {margin-top: 50px;}",
            ".marginLeft {margin-left: 10px;}",
            ".marginLeftB {margin-left: 5px;}",
            ".paddingLeft {padding-left: 5px;}",
            ".bold {font-weight:800;}", // font-weight:800;
            ".no-border {border: 0px}", // search for: [#no-border#]
        //            ".border-a {border: 1px solid gray;}"
        //    
        };
        //
        return CSSRules;
        //
    }

    private boolean ertVatNrExist() {
        if (getErtVatNr() != null && getErtVatNr().isEmpty() == false) {
            return true;
        } else {
            return false;
        }
    }

    private String getErtVatNr() {
        String ert_vatt = _get(map_b, T__FAKTURA_ERT_VAT_NR);
        return _get_exist_a("Ert Vatnr", ert_vatt);
    }

    private String getForfalloDatumFlexCol() {
        //[#OFFERT#]
        if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_NORMAL) || FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_OFFERT)) { // NORMAL
            return _get_colon_sep(T__FAKTURA_FORFALLODATUM__FLEX, map_c);
        } else if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_KREDIT)) { // KREDIT
            return _get_colon_sep(T__FAKTURA_KREDITERAR_FAKTURA_NR, map_c);
        } else if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_KONTANT)) { // KONTANT
            return _get_colon_sep(T__FAKTURA_BETAL_METOD, map_c);
        } else {
            return null;
        }
    }

    private String getBetalVilkorFlexCol() {
        //[#OFFERT#]
        if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_NORMAL) || FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_OFFERT)) { // NORMAL
            return _get_colon_sep(T__FAKTURA_BETAL_VILKOR__FLEX, map_c);
        } else if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_KREDIT)) { // KREDIT
            return _get_colon_sep(T__FAKTURA_UTSKRIVET, map_c);
        } else if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_KONTANT)) { // KONTANT
            return _get_colon_sep(T__FAKTURA_UTSKRIVET, map_c);
        } else {
            return null;
        }
    }

    private String getDrojsmalsrantaFlexCol() {
        //[#OFFERT#]
        if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_NORMAL) || FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_OFFERT)) { // NORMAL
            return _get_colon_sep(T__FAKTURA_DROJMALSRANTA__FLEX, map_c) + " %";
        } else if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_KREDIT)) { // KREDIT
            return HTMLPrint.T__FAKTURA_VALFRI_TEXT;
        } else if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_KONTANT)) { // KONTANT
            return HTMLPrint.T__FAKTURA_VALFRI_TEXT;
        } else {
            return null;
        }
    }

    protected final static String getAttBetalaTitle(String fakturatype) {
        //[#OFFERT#]
        if (fakturatype.equals(DB.STATIC__FAKTURA_TYPE_NORMAL) || fakturatype.equals(DB.STATIC__FAKTURA_TYPE_OFFERT)) {
            return LANG_ENG == false ? "ATT BETALA" : "TO PAY";
        } else if (fakturatype.equals(DB.STATIC__FAKTURA_TYPE_KREDIT)) {
            return LANG_ENG == false ? "ATT ERHÅLLA" : "TO GET";
        } else if (fakturatype.equals(DB.STATIC__FAKTURA_TYPE_KONTANT)) {
            return LANG_ENG == false ? "BETALD" : "PAID";
        } else {
            return null;
        }
    }

    private String getAttBetalaTotal() {
        String att_betala_title = getAttBetalaTitle(FAKTURA_TYPE);
        return map_d.get(att_betala_title);
    }

    private String getTotalBeloppInnanAvdrag() {
        //
        if (isRut() == false) {
            return "0";
        }
        //
        double att_betala_total = Double.parseDouble(getAttBetalaTotal());
        double rut_avdrag_total = Double.parseDouble(getRutAvdragTotal());
        return "" + (att_betala_total + rut_avdrag_total);
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
                + betal_alternativ_to_html()
                //
                + faktura_data_B_to_html__totals()
                //
                + articles_to_html(articles_map_list)
                //
                + insert_invoice_stamp()
                //
                + rutAvdrag() //[#RUTROT#]
                //
                + ovmvantSkattNotation()
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
        int INITIAL_AMMOUNT = 17;
        //
        if (STAMP_IN_USE && h == 128) {
//            INITIAL_AMMOUNT = 12; // If having only text = "REVERSE CHARGE"
            INITIAL_AMMOUNT = 6; // If using the picture: "reverse_charge.png"
        } else if (STAMP_IN_USE && h < 128) {
            INITIAL_AMMOUNT = 9;
        }
        //
        if (articles_map_list == null) {
            return "";
        }
        //
        String html = "";
        //
        int br_to_add = INITIAL_AMMOUNT - articles_map_list.size();
        //
        if (isRut()) {
            br_to_add -= (map_rut_pers.size()) + 3;
        }
        //
        for (int i = 0; i < br_to_add; i++) {
            html += "<br>";
        }
        //
        return html;
    }

    private String faktura_header_with_logo_to_html(String imgPath) {
        //
        String title_nr;
        String title_datum;
        //
        if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_OFFERT)) {
            //[#OFFERT#]
            title_nr = "Offertnr";
            title_datum = "Offertdatum";
        } else {
            title_nr = T__FAKTURA_NR();
            title_datum = T__FAKTURA_DATUM();
        }
        //
        String[] headers = new String[]{title_nr, T__KUND_NR(), title_datum};
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
        //

    }

    private String titleOrLogoIfExist(String imgPath) {
        //
        Dimension imgD = GP_BUH.calculate_w_h__proportionalScaling(GP_BUH.LOGO_PATH());
        //
        if (imgPath != null && imgD.height != 0) {
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
            _get(map_e, COL_1) + _get_exist_c(_get(map_e, COL_1_2)),
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
            T__FAKTURA_ER_REF() + ": " + map_b.get(T__FAKTURA_ER_REF),
            T__FAKTURA_VAR_REF() + ": " + map_c.get(T__FAKTURA_VAR_REF),};
        //
        String[] values_t_2 = new String[]{
            T__FAKTURA_ERT_ORDER_NR() + ": " + map_b.get(T__FAKTURA_ERT_ORDER_NR),
            LANG_ENG == false
            ? T__FAKTURA_LEV_VILKOR() + ": " + map_b.get(T__FAKTURA_LEV_VILKOR) + " / " + T__FAKTURA_LEV_SATT + ": " + map_b.get(T__FAKTURA_LEV_SATT)
            : ""};
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
        //
        //[2021-05-18] This additional construction with <table> is used for lining-up with previous sections
        html_ += "<table>";
        html_ += "<tr>";
        html_ += "<td>";
        //
        html_ += betalAlternativStringBuilder();
        //
        html_ += "</td>";
        html_ += "</tr>";
        html_ += "</table>";
        //
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
        String html_;
        //
        html_ = "<div class='marginTop'>";
        //
        String ATT_BETALA_TITLE = getAttBetalaTitle(FAKTURA_TYPE);
        //
        int colToMakeBold = 9;
        //
        String moms_kr = map_d.get(T__FAKTURA_MOMS_KR);
        String frakt = map_d.get(T__FAKTURA_FRAKT);
        String exp = map_d.get(T__FAKTURA_EXP_AVG);
        String rabatt_kr = map_d.get(T__FAKTURA_RABATT_KR);
        String rut_avdrag_total = getRutAvdragTotal();
        String att_betala_total = getAttBetalaTotal();
        att_betala_total = getSpecial_foreign_customer(att_betala_total);
        String total_belopp_innan_avdrag = getTotalBeloppInnanAvdrag();
        String total_exkl_moms = map_d.get(T__FAKTURA_EXKL_MOMS);
        total_exkl_moms = getSpecial_foreign_customer(total_exkl_moms);
        //
        String currencyUnit = FOREIGN_CUSTOMER ? "EUR" : "";
        //
        String[] headers = new String[]{T__FAKTURA_RUT_TOTAL_BELOPP, T__FAKTURA_RUT_AVDRAG_TOTAL, T__FAKTURA_FRAKT, T__FAKTURA_EXP_AVG, T__FAKTURA_EXKL_MOMS(), T__FAKTURA_MOMS_PERCENT, T__FAKTURA_MOMS_KR(), T__FAKTURA_RABATT_KR(), ATT_BETALA_TITLE};
        String[] values = new String[]{total_belopp_innan_avdrag, rut_avdrag_total, frakt, exp, total_exkl_moms, map_d.get(T__FAKTURA_MOMS_PERCENT), moms_kr, rabatt_kr, roundBetalaTotal(att_betala_total) + " " + currencyUnit};
        //
        //[2020-09-28] Not showing "MOMS %" if "MOMS KR=0" 
        HeadersValuesHTMLPrint hvp = excludeIfZero(headers, values, colToMakeBold, moms_kr, frakt, exp, rabatt_kr, rut_avdrag_total, total_belopp_innan_avdrag);
        //
        html_ += internal_table_2r_xc(hvp.getHeaders(), hvp.getValues(), hvp.getColToMakeBold(), "marginLeftB");
        //
        html_ += "</div>";//</table>
        //
        if (NO_BORDER) { //[#NO-BORDER-PROPPER#]
            html_ += "<div style='width:95%;height:5px;border-bottom:1px solid gray;margin-right:15px'></div>";
        }
//        System.out.println("" + html_);
        //
        return html_;
    }

    private String countMoms(HashMap<String, String> map) {
        int antal = Integer.parseInt(_get(map, DB.BUH_F_ARTIKEL__ANTAL));
        double pris = Double.parseDouble(_get(map, DB.BUH_F_ARTIKEL__PRIS));
        double momsSats = Double.parseDouble(_get(map, DB.BUH_F_ARTIKEL__MOMS_SATS)) / 100;
        double momsKr = (antal * pris) * momsSats;
        return "" + GP_BUH.round_double(momsKr);
    }

    private boolean isOmvantMoms(HashMap<String, String> map) {
        //
        boolean omvant = map.get(DB.BUH_F_ARTIKEL__OMVANT_SKATT).equals("1")
                || map.get(DB.BUH_F_ARTIKEL__OMVANT_SKATT).equals("Ja");
        //
        if (omvant && OMVANT_SKATT__EXIST == false) {
            //
            OMVANT_SKATT__EXIST = true;
            //
            if (ertVatNrExist() == false) {
                HelpA.showNotification_separate_thread(LANG.MSG_22);
            }
            //
        }
        //
        return omvant;
    }

    private String articles_to_html(ArrayList<HashMap<String, String>> list) {
        //
        String html_;
        //
        if (NO_BORDER) { // [#NO-BORDER-PROPPER#]
            html_ = "<table class='marginTop' style='border: 0px solid gray'>"; //style='border: 1px solid gray'
        } else {
            html_ = "<table class='marginTop' style='border: 1px solid gray'>";
        }
        //
        html_ += "<span class='no-border'>";
        //
        if (list == null || list.isEmpty()) {
            return "";
        }
        //
        //
        boolean containsArticleNames = listContainsAtLeastOne(list, DB.BUH_FAKTURA_ARTIKEL___NAMN);
        boolean containsRabatt = listContainsAtLeastOne_b(list, DB.BUH_F_ARTIKEL__RABATT);
        boolean containsKomment = listContainsAtLeastOne(list, DB.BUH_F_ARTIKEL__KOMMENT);
        boolean containsSameMomsSats = listContainsSameEntries(list, DB.BUH_F_ARTIKEL__MOMS_SATS);
        //
        html_ += "<tr class='bold'>";
        //
        if (containsArticleNames) {
            html_ += "<td class='no-border'>" + T__ARTIKEL_NAMN() + "</td>";
        }
        //
        if (containsKomment) {
            html_ += "<td class='no-border'>" + T__ARTIKEL_KOMMENT() + "</td>";
        }
        //
        if (containsSameMomsSats == false || (containsKomment && containsArticleNames) || containsRabatt) {
            html_ += "<td class='no-border'>" + T__ARTIKEL_ANTAL() + " / " + T__ARTIKEL_ENHET() + "</td>";
        } else {
            html_ += "<td class='no-border'>" + T__ARTIKEL_ENHET + "</td>";
            html_ += "<td class='no-border'>" + T__ARTIKEL_ANTAL + "</td>";
        }
        //
        //
        if (containsRabatt) {
            html_ += "<td class='no-border'>" + T__ARTIKEL_RABATT() + "</td>";
        }
        //
        if (containsSameMomsSats == false) {
            html_ += "<td class='no-border'>" + T__ARTIKEL_MOMS_SATS + "</td>";
            html_ += "<td class='no-border'>" + T__ARTIKEL_MOMS_KR + "</td>";
        }
        //
        html_ += "<td class='no-border'>" + T__ARTIKEL_PRIS() + "</td>";
        //
        html_ += "</tr>";
        //
        //
        for (HashMap<String, String> map : list) {
            //
            boolean isOmvantMoms = isOmvantMoms(map);
            //
            String moms_kr = "";
            //
            if (containsSameMomsSats == false) {
                moms_kr = countMoms(map);
            }
            //
            //
            html_ += "<tr>";
            //
            boolean omvantMomsMarkingSet = false;
            //
            if (containsArticleNames) {
                //
                if (isOmvantMoms) {
                    html_ += "<td class='no-border'>" + _get(map, DB.BUH_FAKTURA_ARTIKEL___NAMN) + " **</td>";
                    omvantMomsMarkingSet = true;
                } else {
                    html_ += "<td class='no-border'>" + _get(map, DB.BUH_FAKTURA_ARTIKEL___NAMN) + "</td>";
                }
                //
            }
            //
            if (containsKomment) {
                //
                if (omvantMomsMarkingSet == false && isOmvantMoms) {
                    html_ += "<td class='no-border'>" + _get(map, DB.BUH_F_ARTIKEL__KOMMENT) + " **</td>";
                } else {
                    html_ += "<td class='no-border'>" + _get(map, DB.BUH_F_ARTIKEL__KOMMENT) + "</td>";
                }
                //
            }
            //
            if (containsSameMomsSats == false || (containsKomment && containsArticleNames) || containsRabatt) {
                html_ += "<td class='no-border'>" + _get(map, DB.BUH_F_ARTIKEL__ANTAL) + " (" + get_ENHET(map) + ")" + "</td>";
            } else {
                html_ += "<td class='no-border'>" + _get(map, DB.BUH_F_ARTIKEL__ENHET) + "</td>";
                html_ += "<td class='no-border'>" + _get(map, DB.BUH_F_ARTIKEL__ANTAL) + "</td>";
            }
            //
            //
            if (containsRabatt) {
                html_ += "<td class='no-border'>" + _get(map, DB.BUH_F_ARTIKEL__RABATT) + "</td>";
            }
            //
            if (containsSameMomsSats == false) {
                html_ += "<td class='no-border'>" + _get(map, DB.BUH_F_ARTIKEL__MOMS_SATS) + "</td>";
                html_ += "<td class='no-border'>" + moms_kr + "</td>";
            }
            //
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

    private String insert_invoice_stamp() {
        //
        String img_a;
        int w;
        //
        if (ChooseStamp.STAMP_CO2__NEUTRAL__MANUAL) {
            img_a = IconUrls.C02_FREE.toString();
            STAMP_IN_USE = true;
            w = 128;
            h = 75;
        }else if (ChooseStamp.STAMP_REVERSER_CHARGE_EU) {
            img_a = IconUrls.REVERSER_CHARGE.toString();
            STAMP_IN_USE = true;
            w = 128;
            h = 128;
        }
        //
        //
        else if (EU_CUSTOMER && COMPANY_MIXCONT) {
            img_a = IconUrls.REVERSER_CHARGE.toString();
            STAMP_IN_USE = true;
            w = 128;
            h = 128;
        } else if (COMPANY_MIXCONT) {
            img_a = IconUrls.C02_FREE.toString();
            STAMP_IN_USE = true;
            w = 128;
            h = 75;
        } else {
            STAMP_IN_USE = false;
            return "";
        }

        //
        String html_ = "<table class='marginTopB'>";
        //
        html_ += "<tr>";
        html_ += "<td class='bold'>";
        //
//        html_ += "REVERSE CHARGE";
        // OBS! Note iam using a none cropped image (171 kb) because if iam using one with 
        // lower quality the "pdf" button will produce a low quality image.
        html_ += "<img src='" + img_a + "' alt='image' width='" + w + "' height='" + h + "'>";
        //
        html_ += "</td>";
        html_ += "</tr>";
        html_ += "</table>";
        //
        return html_;
    }

    private String ovmvantSkattNotation() {
        //
        String str = "** Omvänd skattskylldighet" + getErtVatNr();
        //
        if (OMVANT_SKATT__EXIST) {
            return "<p class='fontStd'>" + str + "</p>";
        } else {
            return "";
        }
        //
    }

    private String rutAvdrag() {
        //
        if (isRut() == false) {
            return "";
        }
        //
        String html_ = "";
        html_ += rutAvdrag_a();
        html_ += rutAvdrag_b();
        return html_;
    }

    private String rutAvdrag_a() {
        //
        String html_ = "<table class='marginTop'>";
        html_ += "<tr>";
        html_ += "<td>";
        //
        html_ += T__RUT_PERS + ": ";
        //
        for (int i = 0; i < map_rut_pers.size(); i++) {
            //
            HashMap<String, String> rut_person = map_rut_pers.get(i);
            //
            String namn = rut_person.get(DB.BUH_FAKTURA_RUT_PERSON__FORNAMN);
            String efternamn = rut_person.get(DB.BUH_FAKTURA_RUT_PERSON__EFTERNAMN);
            String pnr = rut_person.get(DB.BUH_FAKTURA_RUT_PERSON__PNR);
            String avdrag = rut_person.get(DB.BUH_FAKTURA_RUT_PERSON__SKATTEREDUKTION);
            //
            String pers = namn + " " + efternamn + " " + pnr + "  " + avdrag + " kr";
            //
            if (i < (map_rut_pers.size() - 1)) {
                html_ += pers + ", ";
            } else {
                html_ += pers;
            }
            //
        }
        //
        html_ += "</td>";
        html_ += "</tr>";
        html_ += "</table>";
        //
        return html_;
    }

    private String rutAvdrag_b() {
        //
        String fastighets_beteckning = map_rut.get(DB.BUH_FAKTURA_RUT__FASTIGHETS_BETECKNING);
        String rut_avdrag_total = getRutAvdragTotal();
        String att_betala_total = getAttBetalaTotal();
        String fakturans_total_belopp_innan_avdrag = getTotalBeloppInnanAvdrag();
        //
        String html_ = "<table class='marginTop'>";
        //
        //
        html_ += "<tr>";
        html_ += "<td class='bold'>";
        //
        html_ += LANG.RUT_MSG_MAIN__AUTO(fastighets_beteckning, rut_avdrag_total, att_betala_total, fakturans_total_belopp_innan_avdrag);
        //
//        html_ += "Denna faktura avser husarbete för fastighet \"" + fastighets_beteckning + "\".";
//        html_ += "Enligt dig som köpare har du rätt till preliminär skattereduktion på " + rut_avdrag_total + " kr.";
//        html_ += "För att vi ska kunna göra ansökan till Skatteverket, ska du betala " + att_betala_total + " kr.";
//        html_ += "Om ansökan om skattereduktion avslås, ska det totala beloppet (" + fakturans_total_belopp_innan_avdrag + " kr) betalas av dig som köpare.";
        //
        html_ += "</td>";
        html_ += "</tr>";
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
                html_ += "<td class='bold'>" + values[i] + "</td>"; // [#no-border#]
            } else {
                html_ += "<td>" + values[i] + "</td>"; // [#no-border#]
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
                        html_ += "<td>" + headers[j] + "</td>"; //[#no-border#]
                    }
                }
                //
            } else {
                //
                for (int j = 0; j < cols; j++) {
                    if (j == (colToMakeBold - 1)) {
                        html_ += "<td class='bold'>" + values[j] + "</td>";
                    } else {
                        html_ += "<td>" + values[j] + "</td>"; //[#no-border#]
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
        jButton_print_btn = new javax.swing.JButton();
        jButton_pdf_btn = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton_send_faktura_email = new javax.swing.JButton();
        jButton_send_faktura_any_email = new javax.swing.JButton();
        jButton_send_with_outlook = new javax.swing.JButton();
        jButton_send_with_common_post = new javax.swing.JButton();
        jButton_make_stamp = new javax.swing.JButton();
        jLabel_status = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jEditorPane1.setMaximumSize(new java.awt.Dimension(545, 842));
        jEditorPane1.setMinimumSize(new java.awt.Dimension(545, 842));

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jButton_print_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        jButton_print_btn.setToolTipText("Skriv ut faktura");
        jButton_print_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_print_btnActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_print_btn);

        jButton_pdf_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pdf-icon-c.png"))); // NOI18N
        jButton_pdf_btn.setToolTipText("Spara faktura i .pdf format på skrivbordet");
        jButton_pdf_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_pdf_btnActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_pdf_btn);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/image.png"))); // NOI18N
        jButton3.setToolTipText("Sätt eller radera logotyp");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);

        jButton_send_faktura_email.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/post.png"))); // NOI18N
        jButton_send_faktura_email.setToolTipText("Skicka faktura per E-post automatiskt, inga inställningar behövs");
        jButton_send_faktura_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_send_faktura_emailActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_send_faktura_email);

        jButton_send_faktura_any_email.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/target.png"))); // NOI18N
        jButton_send_faktura_any_email.setToolTipText("Skicka faktura till valfri e-post");
        jButton_send_faktura_any_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_send_faktura_any_emailActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_send_faktura_any_email);

        jButton_send_with_outlook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/send_b.png"))); // NOI18N
        jButton_send_with_outlook.setToolTipText("Skicka faktura per E-post med Outlook");
        jButton_send_with_outlook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_send_with_outlookActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_send_with_outlook);

        jButton_send_with_common_post.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/common_mail.png"))); // NOI18N
        jButton_send_with_common_post.setToolTipText("Markera fakturan som skickad med vanlig post");
        jButton_send_with_common_post.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_send_with_common_postActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_send_with_common_post);

        jButton_make_stamp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/stamp.png"))); // NOI18N
        jButton_make_stamp.setToolTipText("Välj och infoga en stämpel");
        jButton_make_stamp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_make_stampActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_make_stamp);

        jLabel_status.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_status.setForeground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_status, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jEditorPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_status, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jEditorPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 842, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jScrollPane2.setViewportView(jPanel2);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jButton_print_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_print_btnActionPerformed
        //
        print_help(false);
        //
    }//GEN-LAST:event_jButton_print_btnActionPerformed


    private void jButton_send_faktura_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_send_faktura_emailActionPerformed
        //
        String faktura_kund_email = getFakturaKundEmail();
        //
        sendMail(faktura_kund_email);
        //
    }//GEN-LAST:event_jButton_send_faktura_emailActionPerformed


    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //
        GP_BUH.chooseLogo(this);
        //
        go();
        //
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton_send_with_outlookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_send_with_outlookActionPerformed
        sendWithStandardEmailClient(false);
    }//GEN-LAST:event_jButton_send_with_outlookActionPerformed

    private void jButton_send_faktura_any_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_send_faktura_any_emailActionPerformed
        //
        sendMailTargeted();
        //
    }//GEN-LAST:event_jButton_send_faktura_any_emailActionPerformed

    private void jButton_send_with_common_postActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_send_with_common_postActionPerformed
        //
        if (GP_BUH.confirmWarning(LANG.MSG_10_5(bim.isOffert())) == false) {
            return;
        }
        //
        setSentByCommonPost();
        //
    }//GEN-LAST:event_jButton_send_with_common_postActionPerformed

    private void jButton_pdf_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_pdf_btnActionPerformed
        //
        print_help(true);
        //
    }//GEN-LAST:event_jButton_pdf_btnActionPerformed

    private void jButton_make_stampActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_make_stampActionPerformed
        //
        setAndUseStamp();
        //
    }//GEN-LAST:event_jButton_make_stampActionPerformed

    @Override
    protected void displayStatus(String msg, Color c) {
        //
        if (c != null) {
            jLabel_status.setForeground(c);
        }
        //
        jLabel_status.setText(msg);
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
//                    loggDocumentSent(fakturaId, DB.STATIC__SENT_STATUS__SKICKAD, DB.STATIC__SENT_TYPE_FAKTURA);
//                    //
//                } else {
//                    EditPanel_Send.insert(fakturaId, DB.STATIC__SENT_STATUS__EJ_SKICKAD,
//                            DB.STATIC__SENT_TYPE_FAKTURA);
//                }
//            }
//        });
//        //
//        x.start();
//        //
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton_make_stamp;
    private javax.swing.JButton jButton_pdf_btn;
    private javax.swing.JButton jButton_print_btn;
    private javax.swing.JButton jButton_send_faktura_any_email;
    private javax.swing.JButton jButton_send_faktura_email;
    private javax.swing.JButton jButton_send_with_common_post;
    private javax.swing.JButton jButton_send_with_outlook;
    protected javax.swing.JEditorPane jEditorPane1;
    protected static javax.swing.JLabel jLabel_status;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
