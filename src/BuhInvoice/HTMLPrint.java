package BuhInvoice;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import static BuhInvoice.GP_BUH._get;
import static BuhInvoice.HelpBuh.COMPANY_MIXCONT;
import static BuhInvoice.HelpBuh.EU_CUSTOMER;
import static BuhInvoice.HelpBuh.FOREIGN_CUSTOMER;
import static BuhInvoice.HelpBuh.LANG_ENG;
import BuhInvoice.sec.ChooseStamp;
import BuhInvoice.sec.EmailSendingStatus;
import BuhInvoice.sec.HTMLBasic;
import BuhInvoice.sec.HeadersValuesHTMLPrint;
import BuhInvoice.sec.IO;
import BuhInvoice.sec.LANG;
import BuhInvoice.sec.SMTP;
import BuhInvoice.sec.Stamp;
import com.qoppa.pdfWriter.ImageParam;
import com.qoppa.pdfWriter.PDFDocument;
import com.qoppa.pdfWriter.PDFPage;
import com.qoppa.pdfWriter.PDFPrinterJob;
import forall.HelpA;
import forall.TextFieldCheck;
import icons.IconUrls;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public abstract class HTMLPrint extends HTMLBasic {

    public static final boolean NO_BORDER = true; //[#NO-BORDER-PROPPER#]
    private Stamp MANUAL_STAMP;
    protected boolean STAMP_IN_USE = false;
    protected int h;

    protected final LAFakturering bim;
    protected final ArrayList<HashMap<String, String>> articles_map_list;
    protected final HashMap<String, String> map_a_0;
    protected final HashMap<String, String> map_a;
    protected final HashMap<String, String> map_b;
    protected final HashMap<String, String> map_c;
    protected final HashMap<String, String> map_d;
    protected final HashMap<String, String> map_e;
    protected final HashMap<String, String> map_e_2__lev_data;
    protected final HashMap<String, String> map_f;
    protected final HashMap<String, String> map_g;
    protected final HashMap<String, String> map_rut;
    protected final ArrayList<HashMap<String, String>> map_rut_pers;
    protected final String FAKTURA_TYPE;
    protected final boolean preview;
    //
    protected final static Dimension A4_PAPER = new Dimension(545, 842);
    //
    //
    //
    public static final String T__FAKTURA_NR = "Fakturanr";
    public static final String T__KUND_NR = "Kundnr";
    public static final String T__FAKTURA_DATUM = "Fakturadatum";
    //
    public static final String T__FAKTURA_ER_REF = "Er referens";
    public static final String T__FAKTURA_ERT_ORDER_NR = "Order / Notis";
    public static final String T__FAKTURA_LEV_VILKOR = "Lveransvilkor";
    public static final String T__FAKTURA_LEV_SATT = "Leveranssätt";
    public static final String T__FAKTURA_ERT_VAT_NR = "Ert VAT nummer";
    //
    public static final String T__FAKTURA_VAR_REF = "Vår referens";
    public static final String T__FAKTURA_BETAL_VILKOR__FLEX = "Betalningsvilkor";
    public static final String T__FAKTURA_DAGAR_NETTO = "dgr netto";
    public static final String T__FAKTURA_FORFALLODATUM__FLEX = "Förfallodag";
    public static final String T__FAKTURA_DROJMALSRANTA__FLEX = "Dröjsmålsränta";
    public static final String T__FAKTURA_BETAL_METOD = "Betalmetod"; // only for "kontantfaktura"
    public static final String T__FAKTURA_UTSKRIVET = "Utskrivet"; // 
    public static final String T__FAKTURA_VALFRI_TEXT = "Valfri text:"; // only for "kontantfaktura"
    public static final String T__FAKTURA_KREDITERAR_FAKTURA_NR = "Krediterar fakturanr";
    public static final String T__FAKTURA_XXXXXXX = "Ledig*";
    //
    public static final String T__FAKTURA_RUT_AVDRAG_TOTAL = "Skattereduktion"; // Rut/Rot
    public static final String T__FAKTURA_RUT_TOTAL_BELOPP = "Total belopp"; // Rut/Rot
    public static final String T__FAKTURA_FRAKT = "Frakt";
    public static final String T__FAKTURA_EXP_AVG = "Exp avg";
    public static final String T__FAKTURA_EXKL_MOMS = "Exkl moms";
    public static final String T__FAKTURA_MOMS_PERCENT = "Moms %";
    public static final String T__FAKTURA_MOMS_KR = "Moms kr";
    public static final String T__FAKTURA_RABATT_KR = "Rabatt kr";
    private static final String T__FAKTURA_ATT_BETALA = "ATT BETALA";
    //
    public static final String COL_0 = DB.BUH_FAKTURA_KUND___NAMN;
    public static final String COL_1 = DB.BUH_ADDR__ADDR_A;
    public static final String COL_1_2 = DB.BUH_ADDR__ADDR_B;
    public static final String COL_2 = DB.BUH_ADDR__POSTNR_ZIP;
    public static final String COL_3 = DB.BUH_ADDR__ORT;
    public static final String COL_3_1 = DB.BUH_ADDR__LAND;
    public static final String COL_4 = DB.BUH_ADDR__TEL_A;
    //
    public static final String T__ARTIKEL_NAMN = "Artikel";
    public static final String T__ARTIKEL_KOMMENT = "Beskrivning";
    public static final String T__ARTIKEL_ANTAL = "Antal";
    public static final String T__ARTIKEL_ENHET = "Enhet";
    
    public static final String T__ARTIKEL_RABATT = "Rabatt%";
    private static final String T__ARTIKEL_MOMS_SATS = "Moms%";
    private static final String T__ARTIKEL_MOMS_KR = "Moms Kr";
    public static final String T__ARTIKEL_PRIS = "A`Pris";
    public static final String T__ARTIKEL_OMVANT_SKATT = "Omvänt skattskyldighet";
    //
    public static final String T__FTG_KONTAKTA_OSS = "Kontakta oss";
    public static final String T__FTG_BETALA_TILL = "Betala till";
    public static final String T__FTG_TELEFON = "Telefon";
    public static final String T__FTG_EPOST = "Mejla";
    public static final String T__FTG_BANKGIRO = "BG";
    public static final String T__FTG_POSTGIRO = "PG";
    public static final String T__FTG_IBAN = "IBAN";
    public static final String T__FTG_BIC = "BIC";
    public static final String T__FTG_SWIFT = "SWIFT";
    public static final String T__FTG_KONTO = "Konto";
    public static final String T__FTG_SWISH = "Swish";
    public static final String T__FTG_ORGNR = "Organisationsnr";
    public static final String T__FTG_MOMS_REG_NR = "Momsregnr";
    public static final String T__FTG_F_SKATT = "Godkänd för F-skatt";

    public static final String T__RUT_PERS = "Preliminär skattereduktion";

    /**
     * Creates new form HTMLPrint_A
     */
    public HTMLPrint(
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
        initComponents_();
        //
        //
        this.articles_map_list = articles_map_list;
        //
        this.bim = bim;
        this.FAKTURA_TYPE = fakturatype;
        this.preview = preview;
        //
        this.map_a_0 = map_a_0;
        this.map_a = map_a;
        this.map_b = map_b;
        this.map_c = map_c;
        this.map_d = map_d;
        this.map_e = map_e;
        this.map_e_2__lev_data = map_e_2;
        this.map_f = map_f;
        this.map_g = map_g;
        this.map_rut = map_rut;
        this.map_rut_pers = map_rut_pers;
        //
        init();
        //
    } // 

    public String T__FAKTURA_DAGAR_NETTO() {
        return LANG_ENG == false ? T__FAKTURA_DAGAR_NETTO : "days net";
    }
    
    public String T__FAKTURA_FRAKT() {
        return LANG_ENG == false ? T__FAKTURA_FRAKT : "Shipping";
    }

    public String T__FAKTURA_EXP_AVG() {
        return LANG_ENG == false ? T__FAKTURA_EXP_AVG : "Handling fee";
    }

    public String T__FAKTURA_MOMS_PERCENT() {
        return LANG_ENG == false ? T__FAKTURA_MOMS_PERCENT : "VAT %";
    }

    public String T__FAKTURA_VALFRI_TEXT() {
        return LANG_ENG == false ? T__FAKTURA_VALFRI_TEXT : "Optional notes";
    }

    public String T__ARTIKEL_RABATT() {
        return LANG_ENG == false ? T__ARTIKEL_RABATT : "Discount%";
    }
    
     public String T__ARTIKEL_MOMS_SATS() {
        return LANG_ENG == false ? T__ARTIKEL_MOMS_SATS : "VAT%";
    }
     
      public String T__ARTIKEL_MOMS_KR() {
        return LANG_ENG == false ? T__ARTIKEL_MOMS_KR : "VAT EUR";
    }

    public String T__FAKTURA_RABATT_KR() {
        return LANG_ENG == false ? T__FAKTURA_RABATT_KR : "Discount EUR";
    }

    public String T__FAKTURA_DROJMALSRANTA__FLEX() {
        return LANG_ENG == false ? T__FAKTURA_DROJMALSRANTA__FLEX : "Penalty EUR";
    }

    public String T__FAKTURA_NR() {
        return LANG_ENG == false ? T__FAKTURA_NR : "Invoice No.";
    } // T__FAKTURA_DROJMALSRANTA__FLEX

    public String T__KUND_NR() {
        return LANG_ENG == false ? T__KUND_NR : "Custommer No.";
    }

    public String T__FAKTURA_DATUM() {
        return LANG_ENG == false ? T__FAKTURA_DATUM : "Invoice date";
    }

    public String T__FAKTURA_ER_REF() {
        return LANG_ENG == false ? T__FAKTURA_ER_REF : "Your reference";
    }

    public String T__FAKTURA_VAR_REF() {
        return LANG_ENG == false ? T__FAKTURA_VAR_REF : "Our reference";
    }

    public String T__FAKTURA_ERT_ORDER_NR() {
        return LANG_ENG == false ? T__FAKTURA_ERT_ORDER_NR : "Order / Note";
    }

    public String T__FAKTURA_LEV_VILKOR() {
        return LANG_ENG == false ? T__FAKTURA_LEV_VILKOR : "Without VAT";
    }

    public String T__FAKTURA_EXKL_MOMS() {
        return LANG_ENG == false ? T__FAKTURA_EXKL_MOMS : "Without VAT";
    }

    public String T__FAKTURA_MOMS_KR() {
        return LANG_ENG == false ? T__FAKTURA_MOMS_KR : "VAT EUR";
    }

    public String T__ARTIKEL_NAMN() {
        return LANG_ENG == false ? T__ARTIKEL_NAMN : "Article";
    }

    public String T__ARTIKEL_KOMMENT() {
        return LANG_ENG == false ? T__ARTIKEL_KOMMENT : "Description";
    }

    public String T__ARTIKEL_ANTAL() {
        return LANG_ENG == false ? T__ARTIKEL_ANTAL : "Ammount";
    }

    public String T__ARTIKEL_ENHET() {
        return LANG_ENG == false ? T__ARTIKEL_ENHET : "Unit";
    }

    public String T__ARTIKEL_PRIS() {
        return LANG_ENG == false ? T__ARTIKEL_PRIS : "Unit price";
    }

    public String T__FTG_KONTAKTA_OSS() {
        return LANG_ENG == false ? T__FTG_KONTAKTA_OSS : "Contact us";
    }

    public String T__FTG_BETALA_TILL() {
        return LANG_ENG == false ? T__FTG_BETALA_TILL : "Pay to";
    }

    public String T__FTG_TELEFON() {
        return LANG_ENG == false ? T__FTG_TELEFON : "Phone";
    }

    public String T__FTG_EPOST() {
        return LANG_ENG == false ? T__FTG_EPOST : "E-mail";
    }

    public String T__FTG_KONTO() {
        return LANG_ENG == false && HelpBuh.FOREIGN_CUSTOMER ? T__FTG_KONTO : "BIC";
    }

//    public String T__FTG_SWISH() {
//        return LANG_ENG == false && HelpBuh.FOREIGN_CUSTOMER ? T__FTG_SWISH : "SWIFT";
//    }
    public String T__FTG_MOMS_REG_NR() {
        return LANG_ENG == false && HelpBuh.FOREIGN_CUSTOMER == false ? T__FTG_MOMS_REG_NR : "Our VAT";
    }

    public String get_ENHET(HashMap<String, String> map) {
        //
        //
        HashMap<String, String> dict = new HashMap<>();
        //
        dict.put("st", "Pcs");
        dict.put("Förp", "Pkg");
        dict.put("Tim", "h");
        //
        //
        String enhet = map.get(DB.BUH_F_ARTIKEL__ENHET);
        //
        if (LANG_ENG == false) {
            return enhet;
        } else {
            return dict.get(enhet);
        }
        //
    }

    protected String roundBetalaTotal(String value) {
        double dbl = Double.parseDouble(value);
        return "" + (int) GP_BUH.round_double__whole_number(dbl);
    }

    protected String getSpecial_foreign_customer(String value) {
        //[2021-08-09]
        //[#EUR-SEK#]
        double dbl = Double.parseDouble(value);
        //
        if (FOREIGN_CUSTOMER) {
            return "" + GP_BUH.round_double_b((dbl / bim.getCurrencyRateA()));
        } else {
            return value;
        }
        //
    }

    public void setManualStamp(Stamp stamp) {
        this.MANUAL_STAMP = stamp;
    }

    public Stamp getManualStamp() {
        return MANUAL_STAMP;
    }

    protected String insert_stamp() {
        //
        String img_a;
        int w;
        //
        if (MANUAL_STAMP != null) {
            STAMP_IN_USE = true;
            img_a = MANUAL_STAMP.getPath();
            w = MANUAL_STAMP.getW();
            h = MANUAL_STAMP.getH();
        } //
        //
        else if (EU_CUSTOMER && bim.isOffert() == false && bim.isKreditFaktura() == false) {
            img_a = IconUrls.STAMP_REVERSER_CHARGE.toString();
            STAMP_IN_USE = true;
            w = 128;
            h = 128;
        } else if (COMPANY_MIXCONT) {
            img_a = IconUrls.STAMP_C02_FREE.toString();
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

    protected String faktura_data_C_to_html__addr() {
        //
        String html_ = "<div class='marginTop'>";//<table class='marginTop'>
        //
        html_ += "<p class='fontStd' style='text-align:center'>";
        html_ += _get(map_f, DB.BUH_KUND__NAMN) + _get_exist_c(_get(map_g, DB.BUH_ADDR__ADDR_A)) + _get_exist_c(_get(map_g, DB.BUH_ADDR__POSTNR_ZIP)) + _get_exist_c(_get(map_g, DB.BUH_ADDR__ORT)) + ".";
        html_ += "</p>";
        //
        html_ += "<div class='fontStd' style='text-align:center'>";
        html_ += T__FTG_KONTAKTA_OSS() + _get_exist_b(T__FTG_TELEFON(), _get(map_g, DB.BUH_ADDR__TEL_A))
                + _get_exist_b(T__FTG_EPOST(), _get(map_f, DB.BUH_KUND__EPOST)) + ".";

//        html_ += T__FTG_TELEFON + " " + _get(map_g, DB.BUH_ADDR__TEL_A) + ". " + T__FTG_EPOST + " " + _get(map_f, DB.BUH_KUND__EPOST);
        html_ += "</div>";
        //
        html_ += "<div class='fontStd' style='text-align:center'>";
        //
        if (HelpBuh.FOREIGN_CUSTOMER) {
            html_ += T__FTG_MOMS_REG_NR() + ": " + _get(map_f, DB.BUH_KUND__VATNR) + "";
        } else {
            //
            String fskatt = define_godkand_for_fskatt(map_f);
            //
            html_ += fskatt + T__FTG_ORGNR + ": " + _get(map_f, DB.BUH_KUND__ORGNR) + ". "
                    + T__FTG_MOMS_REG_NR() + ": " + _get(map_f, DB.BUH_KUND__VATNR) + ".";
        }
        //
        html_ += "</div>";
        //
        html_ += "</div>";
        //
//        System.out.println("" + html_);
        //
        return html_;
    }

    private String define_godkand_for_fskatt(HashMap<String, String> map_f) {
        String godkand_for_fskatt = _get_longname(map_f, DB.BUH_KUND__F_SKATT, DB.STATIC__JA_NEJ);
        return godkand_for_fskatt.equals(DB.STATIC__YES) ? T__FTG_F_SKATT + "." : "";
    }

    protected String betalAlternativStringBuilder() {
        //
        String html_ = "";
        //
        if (HelpBuh.FOREIGN_CUSTOMER) {
            html_ += T__FTG_BETALA_TILL()
                    + _get_exist_d(T__FTG_IBAN, _get(map_f, DB.BUH_KUND__IBAN))
                    + _get_exist_a(T__FTG_BIC, _get(map_f, DB.BUH_KUND__BIC))
                    + _get_exist_a(T__FTG_SWIFT, _get(map_f, DB.BUH_KUND__SWIFT));
        } else {
            html_ += T__FTG_BETALA_TILL()
                    + _get_exist_d(T__FTG_BANKGIRO, _get(map_f, DB.BUH_KUND__BANK_GIRO))
                    + _get_exist_a(T__FTG_POSTGIRO, _get(map_f, DB.BUH_KUND__POST_GIRO))
                    + _get_exist_a(T__FTG_SWISH, _get(map_f, DB.BUH_KUND__SWISH))
                    + _get_exist_a(T__FTG_KONTO, _get(map_f, DB.BUH_KUND__KONTO));
        }
        //
        return html_;
    }

    protected abstract void buttonLogic();

    protected abstract void initComponents_();

    private void init() {
        //
        this.setTitle(getWindowTitle());
        this.setIconImage(GP_BUH.getBuhInvoicePrimIcon());
        //
        buttonLogic();
        //
        initOther();
        go();
        scrollToTop();
    }

    protected void initOther() {
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

    protected String getFakturaId() {
        return map_a_0.get(DB.BUH_FAKTURA__ID__);
    }

    protected String getPdfFileName(boolean appendKundId) {
        //
        String fileName;
        //
        if (this instanceof HTMLPrint_A && bim.isOffert()) {
            fileName = LANG.OFFERT.toLowerCase();
        } else if (this instanceof HTMLPrint_A) {
            fileName = LANG.FAKTURA.toLowerCase();
        } else if (this instanceof HTMLPrint_B) {
            fileName = LANG.PAMINNELSE.replaceAll("å", "a").toLowerCase(); // I can't have "å" as it's not accepted on server side[2020-09-30]
        } else {
            fileName = "undefined";
        }
        //
        if (appendKundId) {
            return fileName + "_" + bim.getKundId() + ".pdf";
        } else {
            return fileName + ".pdf";
        }
        //
    }

    protected String getFakturaKundEmail() {
        return _get(map_e_2__lev_data, DB.BUH_FAKTURA_KUND___EMAIL);
    }

    protected String getForetagsEmail() {
        return _get(map_f, DB.BUH_KUND__EPOST);
    }

    protected String getForetagsNamn() {
        return _get(map_f, DB.BUH_KUND__NAMN);
    }

    protected String getFakturaPath() {
//        return System.getProperty("user.home") + "/Desktop/" + getPdfFileName(false);
        //
        HelpA.create_dir_if_missing("la-dokument");
        //
        return "la-dokument/" + getPdfFileName(false);
        //
//        if (HelpBuh.IS_MAC_OS) {
//            return "mina dokument/" + getPdfFileName(false);
//        } else {
//            return getPdfFileName(false);
////            return javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory() + "/" + getPdfFileName(false);
//        }

    }

    protected String getEmailBody() {
        //Du har fått offert från: Pico AB
        // + "\n\n\n Fakturera enkelt och gratis! Besök oss på www.lafakturering.se"; -> NOT WORKING!![2021-06-18]
        // + "<br><br><br>Fakturera enkelt och gratis! Besök oss på www.lafakturering.se" -> NOT WORKING!![2021-06-18]
        //
        String body = "Hej! Du har fått " + getHTMLPrintTitle().toLowerCase() + " från: " + getForetagsNamn();
        //
        if (FOREIGN_CUSTOMER) {
            body = "Dear customer! This " + getHTMLPrintTitle().toLowerCase() + " is automatically sent from " + getForetagsNamn();
        }
        //
        return body;
    }

    protected String _get_colon_sep(String key, HashMap<String, String> map) {
        //
        HashMap<String, String> dict = new HashMap<>();
        //
        dict.put(T__FAKTURA_FORFALLODATUM__FLEX, "Due date");
        dict.put(T__FAKTURA_BETAL_VILKOR__FLEX, "Terms of payment");
        dict.put(T__FAKTURA_DROJMALSRANTA__FLEX, "Penalty interest");
        dict.put(T__FAKTURA_UTSKRIVET, "Printed");
        dict.put(T__FAKTURA_BETAL_METOD, "Payment method");
        dict.put(T__FAKTURA_KREDITERAR_FAKTURA_NR, "Credits invoice");
        //
        String key_ = key;
        //
        if (LANG_ENG) {
            key_ = dict.get(key);
        }
        //
        return key_ + ": " + map.get(key);
    }

    protected String _get_exist_a(String name, String value) {
        if (value.isEmpty() == false) {
            return ", <span class='bold'>" + name + "</span>: " + value;
        } else {
            return "";
        }
    }

    protected String _get_exist_d(String name, String value) {
        if (value.isEmpty() == false) {
            return " <span class='bold'>" + name + "</span>: " + value;
        } else {
            return "";
        }
    }

    protected String _get_exist_b(String name, String value) {
        if (value.isEmpty() == false) {
            return ", " + name + ": " + value;
        } else {
            return "";
        }
    }

    protected String _get_exist_c(String value) {
        if (value.isEmpty() == false) {
            return ", " + value;
        } else {
            return "";
        }
    }

    protected String _get_longname(HashMap<String, String> map, String param, String statics) {
        //
        String val = map.get(param);
        //
        if (val == null || val.isEmpty() || val.equals("null") || val.equals("NULL")) {
            return "";
        } else {
            return JSon.getLongName(statics, val);
        }
    }

    protected boolean listContainsSameEntries(ArrayList<HashMap<String, String>> list, String param) {
        //
        HashSet<String> set = new HashSet<>();
        //
        for (HashMap<String, String> map : list) {
            //
            set.add(_get(map, param));
            //
        }
        //
        return set.size() == 1;
        //
    }

    protected boolean listContainsAtLeastOne(ArrayList<HashMap<String, String>> list, String param) {
        for (HashMap<String, String> map : list) {
            //
            String val = _get(map, param);
            //
            if (val.isEmpty() == false) {
                return true;
            }
        }
        return false;
    }

    protected boolean listContainsAtLeastOne_b(ArrayList<HashMap<String, String>> list, String param) {
        //
        int i = 0;
        //
        for (HashMap<String, String> map : list) {
            //
            String val = _get(map, param);
            //
            if (val.equals("0")) {
                //
            } else {
                i++;
            }
            //
        }
        //
        return i != 0;
    }

    protected String getRutAvdragTotal() {
        //
        if (isRut() == false) {
            return "0";
        } else {
            return map_rut.get(DB.BUH_FAKTURA_RUT__SKATTEREDUKTION);
        }
        //
    }

    protected boolean isRut() {
        //
        if (map_rut != null && map_rut.isEmpty() == false) {
            return true;
        } else {
            return false;
        }
        //
    }

    protected String getHTMLPrintTitle() {
        if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_NORMAL)) {
            return LANG_ENG == false ? LANG.FAKTURA : "Invoice";
        } else if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_KREDIT)) {
            return LANG_ENG == false ? LANG.KREDIT_FAKTURA : "Credit Invoice";
        } else if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_KONTANT)) {
            return LANG_ENG == false ? LANG.KONTANT_FAKTURA : "Receipt";
        } else if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_OFFERT)) {
            return LANG_ENG == false ? LANG.OFFERT : "Offer"; //[#OFFERT#]
        } else {
            return null;
        }
    }

    protected abstract void displayStatus(String msg, Color c);

    protected HeadersValuesHTMLPrint excludeIfZero(String[] headers, String[] values, int colToMakeBold, String moms_kr,
            String frakt, String exp, String rabbat_kr, String rutAvdragTotal, String totalBeloppInnanAvdrag) {
        //
        List<String> headers_ = new ArrayList<>();
        Collections.addAll(headers_, headers);
        //
        List<String> values_ = new ArrayList<>();
        Collections.addAll(values_, values);
        //
        if (moms_kr.equals("0") || moms_kr.equals("0.0")) {
            colToMakeBold--;
            int index = headers_.indexOf(T__FAKTURA_MOMS_PERCENT());
            headers_.remove(index);
            values_.remove(index);
        }
        //
        if (frakt.equals("0") || frakt.equals("0.0")) {
            colToMakeBold--;
            int index = headers_.indexOf(T__FAKTURA_FRAKT());
            headers_.remove(index);
            values_.remove(index);
        }
        //
        if (exp.equals("0") || exp.equals("0.0")) {
            colToMakeBold--;
            int index = headers_.indexOf(T__FAKTURA_EXP_AVG());
            headers_.remove(index);
            values_.remove(index);
        }
        //
        if (rabbat_kr.equals("0") || rabbat_kr.equals("0.0")) {
            colToMakeBold--;
            int index = headers_.indexOf(T__FAKTURA_RABATT_KR());
            headers_.remove(index);
            values_.remove(index);
        }
        //
        if (rutAvdragTotal.equals("0") || rutAvdragTotal.equals("0.0")) {
            colToMakeBold--;
            int index = headers_.indexOf(T__FAKTURA_RUT_AVDRAG_TOTAL);
            headers_.remove(index);
            values_.remove(index);
        }
        //
        if (totalBeloppInnanAvdrag.equals("0") || totalBeloppInnanAvdrag.equals("0.0")) {
            colToMakeBold--;
            int index = headers_.indexOf(T__FAKTURA_RUT_TOTAL_BELOPP);
            headers_.remove(index);
            values_.remove(index);
        }
        //
        return new HeadersValuesHTMLPrint(headers_.toArray(new String[headers_.size()]),
                values_.toArray(new String[values_.size()]), colToMakeBold);
        //
    }

    protected void setAndUseStamp() {
        //
        ChooseStamp stamp = new ChooseStamp(this);
        //
    }

    /**
     * Marks as is the invoice was sent by the common/physical post
     */
    protected void setSentByCommonPost() {
        //
        if (this instanceof HTMLPrint_A && bim.isOffert()) {
            //[#OFFERT#]
            loggDocumentSent(getFakturaId(), DB.STATIC__SENT_STATUS__SKICKAD_COMMON_POST, DB.STATIC__SENT_TYPE_OFFERT,null);
            HelpA.showNotification(LANG.MSG_10_4(true));
        } else if (this instanceof HTMLPrint_A) {
            loggDocumentSent(getFakturaId(), DB.STATIC__SENT_STATUS__SKICKAD_COMMON_POST, DB.STATIC__SENT_TYPE_FAKTURA,null);
            HelpA.showNotification(LANG.MSG_10_4(false));
        }
        //
//        bim.setValueAllInvoicesJTable(InvoiceB.TABLE_ALL_INVOICES__SKICKAD, DB.STATIC__YES);
        this.dispose();
        bim.invoiceB.refresh_c();
        //
    }

    protected void sendMailTargeted() {
        //
        TextFieldCheck tfc = new TextFieldCheck(getForetagsEmail(), Validator.EMAIL, 25);
        boolean yesNo = HelpA.chooseFromJTextFieldWithCheck(tfc, LANG.MSG_7_2);
        String toEmail = tfc.getText_();
        //
        if (yesNo && toEmail != null && tfc.getValidated()) {
            sendMail(toEmail);
        }
    }

    protected void sendMail(String toEmail) {
        //
        String ftg_name = getForetagsNamn();
        //
        if (toEmail == null || toEmail.isEmpty()) {
            HelpA.showNotification(LANG.MSG_7);
            return;
        }
        //
        if (GP_BUH.confirmWarning(LANG.CONFIRM_SEND_MAIL(toEmail, this, bim.isOffert())) == false) {
            return;
        }
        //
        String fakturaFileName = getPdfFileName(true);
        //
        print_upload_sendmail__thr(
                "uploads/",
                fakturaFileName,
                toEmail,
                ftg_name
        );
        //
    }

    /**
     *
     * @param serverPath
     * @param fileName
     * @param sendToEmail
     * @param ftgName
     */
    private void print_upload_sendmail__thr(String serverPath, String fileName, String sendToEmail, String ftgName) {
        //
        HTMLPrint htmlprint = this;
        //
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
                    if (htmlprint instanceof HTMLPrint_A && bim.isOffert()) {
                        //[#OFFERT#]
                        loggDocumentSent(fakturaId, DB.STATIC__SENT_STATUS__SKICKAD, DB.STATIC__SENT_TYPE_OFFERT,sendToEmail);
                    } else if (htmlprint instanceof HTMLPrint_A) {
                        loggDocumentSent(fakturaId, DB.STATIC__SENT_STATUS__SKICKAD, DB.STATIC__SENT_TYPE_FAKTURA,sendToEmail);
                    } else {
                        loggDocumentSent(fakturaId, DB.STATIC__SENT_STATUS__SKICKAD, DB.STATIC__SENT_TYPE_PAMMINELSE,sendToEmail);
                    }
                    //
                } else {
                    if (htmlprint instanceof HTMLPrint_A && bim.isOffert()) {
                        //[#OFFERT#]
                        EditPanel_Send.insert(fakturaId, DB.STATIC__SENT_STATUS__EJ_SKICKAD,
                                DB.STATIC__SENT_TYPE_OFFERT,sendToEmail);
                    } else if (htmlprint instanceof HTMLPrint_A) {
                        EditPanel_Send.insert(fakturaId, DB.STATIC__SENT_STATUS__EJ_SKICKAD,
                                DB.STATIC__SENT_TYPE_FAKTURA,sendToEmail);
                    } else {
                        EditPanel_Send.insert(fakturaId, DB.STATIC__SENT_STATUS__EJ_SKICKAD,
                                DB.STATIC__SENT_TYPE_PAMMINELSE,sendToEmail);
                    }

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
     * @return
     */
    protected boolean print_upload_sendmail(String serverPath, String fileName, String sendToEmail, String ftgName) {
        //
        displayStatus(LANG.MSG_10, null);
        //
        print_java(fileName);
        //
//        System.out.println("Print pdf complete");
        displayStatus(LANG.MSG_10_1, Color.white);
        //
        //
        boolean upload_success = false;
        //
        try {
            upload_success = HelpBuh.uploadFile(fileName, serverPath + fileName); //[clientPath][ServerPath]
        } catch (ProtocolException ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
//        System.out.println("Upload to PHP: " + upload_success);
        //
        //
        Boolean email_sending_ok = false;
        EmailSendingStatus ess = null;
        //
        String subject = getHTMLPrintTitle();
        String body = getEmailBody();
        //
        if (upload_success) {
            //
            SMTP smtp = IO.loadSMTP();
            //
            if (smtp != null && smtp.allFilled()) {
                ess = HelpBuh.sendEmailWithAttachment_SMTP(smtp,
                        sendToEmail,
                        subject,
                        body,
                        serverPath + fileName
                );
            } else {
                ess = HelpBuh.sendEmailWithAttachment(
                        GP_BUH.CUSTOMER_COMPANY_NAME, // This one is shown as name instead of the email it's self
                        sendToEmail,
                        subject,
                        body,
                        serverPath + fileName
                );
            }
            //
        }
        //
        if (upload_success && ess != null && ess.allSuccessful()) {
            email_sending_ok = true;
            System.out.println("Email sending: " + email_sending_ok);
            displayStatus(LANG.MSG_10_2, Color.black);
            return true;
        } else {
            displayStatus(LANG.MSG_10_3, Color.red);
            return false;
        }
        //
        // OBS! The uploaded file is deleted from: _http_buh.php -> sendMailHelp(...) -> unlink($filePathServerSide);
        //
    }

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
     *
     * @param reminder
     */
    protected void sendWithStandardEmailClient(boolean reminder) {
        //
        String mailto = getFakturaKundEmail();
        String subject = getHTMLPrintTitle();
        String body = getEmailBody();
        String documentPath = getFakturaPath();
        //
        print_java(documentPath);
        //
        String fileName = getPdfFileName(false);
        //
        HelpA.showNotification(LANG.FAKTURA_UTSKRIVEN_OUTLOOK(fileName, reminder, bim.isOffert(), new File(documentPath)));
        //
        HelpA.open_dir(new File(documentPath).getParent());
        HelpA.run_application_with_associated_application__b(new File(documentPath));
        //
        Desktop desktop = Desktop.getDesktop();
        String url;
        URI mailTo;
        //
        // The below does not work for MAC OS X
        try {
            // Attachments not working with "mailTo:" 100% verified [2020-09-23]
            url = mailTo(mailto, subject, body);
            //
//            System.out.println("URL: " + url);
            //
            mailTo = new URI(url);
            desktop.mail(mailTo);
            //
            //
            if (this instanceof HTMLPrint_A && bim.isOffert()) {
                //[#OFFERT#]
                loggDocumentSent(getFakturaId(), DB.STATIC__SENT_STATUS__SKICKAD_OUTLOOK, DB.STATIC__SENT_TYPE_OFFERT,mailto);
            } else if (this instanceof HTMLPrint_A) {
                loggDocumentSent(getFakturaId(), DB.STATIC__SENT_STATUS__SKICKAD_OUTLOOK, DB.STATIC__SENT_TYPE_FAKTURA,mailto);
            } else if (this instanceof HTMLPrint_B) {
                loggDocumentSent(getFakturaId(), DB.STATIC__SENT_STATUS__SKICKAD_OUTLOOK, DB.STATIC__SENT_TYPE_PAMMINELSE,mailto);
            }
            //
            //
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    protected void loggDocumentSent(String fakturaId, String sendStatus, String sendType, String sendToEmail) {
        //
        EditPanel_Send.insert(fakturaId, sendStatus, sendType,sendToEmail); // "buh_faktura_send" table
        //
        Basic_Buh.executeSetFakturaSentPerEmail(fakturaId, true); // "buh_faktura" table -> update sent status
        bim.setValueAllInvoicesJTable(InvoiceB.TABLE_ALL_INVOICES__SKICKAD, DB.STATIC__YES);
        //
    }

    protected void print_help(boolean printJava) {
        //
        boolean print_ok;
        //
        if (printJava) {
            //
            print_ok = print_java(getFakturaPath());
            //
            if (print_ok) {
                HelpA.open_dir(new File(getFakturaPath()).getParent());
                HelpA.run_application_with_associated_application__b(new File(getFakturaPath()));
            }
            //
        } else {
            print_ok = print_normal();
        }
        //
        String fakturaId = bim.getFakturaId();
        //
        if (print_ok) {
            //
            if (this instanceof HTMLPrint_A && bim.isOffert()) {
                //[#OFFERT#]
                EditPanel_Send.insert(bim.getFakturaId(), DB.STATIC__SENT_STATUS__UTSKRIVEN, DB.STATIC__SENT_TYPE_OFFERT,null);
            } else if (this instanceof HTMLPrint_A) {
                EditPanel_Send.insert(bim.getFakturaId(), DB.STATIC__SENT_STATUS__UTSKRIVEN, DB.STATIC__SENT_TYPE_FAKTURA,null);
            } else if (this instanceof HTMLPrint_B) {
                EditPanel_Send.insert(bim.getFakturaId(), DB.STATIC__SENT_STATUS__UTSKRIVEN, DB.STATIC__SENT_TYPE_PAMMINELSE,null);
            }
            //
            // Here implement update of "is_printed" [#IS_PRINTED#]
            if (this instanceof HTMLPrint_A) { // Faktura OR Offert
                setPrinted(fakturaId);
            }
            //
        }
    }

    private void setPrinted(String fakturaId) {
        //[#IS_PRINTED#]
        HashMap<String, String> update_map = bim.getUPDATE(DB.BUH_FAKTURA__ID__, fakturaId, DB.TABLE__BUH_FAKTURA);
        //
        update_map.put(DB.BUH_FAKTURA__PRINTED, "1");
        //
        //
        String json = JSon.hashMapToJSON(update_map);
        //
        HelpBuh.update(json);
        //
        bim.invoiceB.refresh_c();
    }

    protected boolean print_normal() {
        //
        JEditorPane jep = getEditorPane();
        //
        int actHeight = jep.getHeight();
        System.out.println("DOC LENGHT: " + getEditorPane().getDocument().getEndPosition());
        //
        System.out.println("jeditorPane height: " + jep.getHeight());
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
        pj.setPrintable(jep.getPrintable(null, null), validatedFormat);
        //
        // This one shows additional Dialog displaying the margins, can be skipped
        PageFormat pf = pj.pageDialog(pageFormat);
        //
        if (pj.printDialog()) {
            try {
                if (this instanceof HTMLPrint_A) {
                    pj.setJobName(LANG.FAKTURA); // This changes the name of file if printed to ".pdf"
                } else if (this instanceof HTMLPrint_B) {
                    pj.setJobName(LANG.PAMINNELSE); // This changes the name of file if printed to ".pdf"
                } else {
                    pj.setJobName("Print job undefined");
                }
                //
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
     * @return
     */
    protected boolean print_java(String filename) {
        //
        JEditorPane jep = getEditorPane();
        //
        int actHeight = jep.getHeight();
        //
        System.out.println("jeditorPane height: " + jep.getHeight());
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
        pj.setPrintable(jep.getPrintable(null, null), validatedFormat);
        //
        //
        pj.setJobName(filename);
        //
        try {
//            pj.print_java();
            pj.print(filename); // [JAVA PDF PRINT]******[SILENT PRINT]
            return true;
        } catch (PrinterException ex) {
            Logger.getLogger(HTMLPrint_A.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //
    }

    /**
     * Not making A4
     *
     * @deprecated
     * @param filename
     * @return
     */
    protected boolean print_java_b(String filename) {
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
        //
        JEditorPane jep = getEditorPane();
        //
        PDFDocument pdfDoc = new PDFDocument();
        //
        PDFPage page = pdfDoc.createPage(pageFormat);
        //
        pdfDoc.addPage(page);
        //
        Graphics2D g2d = page.createGraphics();
        //
        jep.print(g2d);
        //
        File outFile = new File(filename);
        //
        // save document
        if (outFile != null) {
            try {
                pdfDoc.saveDocument(outFile.getAbsolutePath());
                return true;
            } catch (IOException ex) {
                Logger.getLogger(HTMLPrint.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return false;
    }

    private static double fromCMToPPI(double cm) {
        return toPPI(cm * 0.393700787);
    }

    private static double toPPI(double inch) {
        return inch * 72d;
    }

}
