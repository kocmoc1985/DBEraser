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
import BuhInvoice.sec.EmailSendingStatus;
import BuhInvoice.sec.HTMLBasic;
import BuhInvoice.sec.HeadersValuesHTMLPrint;
import BuhInvoice.sec.IO;
import BuhInvoice.sec.LANG;
import BuhInvoice.sec.SMTP;
import com.qoppa.pdfWriter.ImageParam;
import com.qoppa.pdfWriter.PDFDocument;
import com.qoppa.pdfWriter.PDFPage;
import com.qoppa.pdfWriter.PDFPrinterJob;
import forall.HelpA;
import forall.TextFieldCheck;
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
    public static final String T__FAKTURA_ATT_BETALA = "ATT BETALA";
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
    public static final String T__ARTIKEL_MOMS_SATS = "Moms%";
    public static final String T__ARTIKEL_MOMS_KR = "Moms Kr";
    public static final String T__ARTIKEL_PRIS = "A`Pris";
    public static final String T__ARTIKEL_OMVANT_SKATT = "Omvänt skattskyldighet";
    //
    public static final String T__FTG_KONTAKTA_OSS = "Kontakta oss";
    public static final String T__FTG_BETALA_TILL = "Betala till";
    public static final String T__FTG_TELEFON = "Telefon";
    public static final String T__FTG_EPOST = "Mejla";
    public static final String T__FTG_BANKGIRO = "BG";
    public static final String T__FTG_POSTGIRO = "PG";
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
        String body = "Du har fått " + getHTMLPrintTitle().toLowerCase() + " från: " + getForetagsNamn();
        return body;
    }

    protected String _get_colon_sep(String key, HashMap<String, String> map) {
        return key + ": " + map.get(key);
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
            return LANG.FAKTURA;
        } else if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_KREDIT)) {
            return "Kreditfaktura";
        } else if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_KONTANT)) {
            return "Kvitto";
        } else if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_OFFERT)) {
            //[#OFFERT#]
            return "Offert";
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
            int index = headers_.indexOf(T__FAKTURA_MOMS_PERCENT);
            headers_.remove(index);
            values_.remove(index);
        }
        //
        if (frakt.equals("0") || frakt.equals("0.0")) {
            colToMakeBold--;
            int index = headers_.indexOf(T__FAKTURA_FRAKT);
            headers_.remove(index);
            values_.remove(index);
        }
        //
        if (exp.equals("0") || exp.equals("0.0")) {
            colToMakeBold--;
            int index = headers_.indexOf(T__FAKTURA_EXP_AVG);
            headers_.remove(index);
            values_.remove(index);
        }
        //
        if (rabbat_kr.equals("0") || rabbat_kr.equals("0.0")) {
            colToMakeBold--;
            int index = headers_.indexOf(T__FAKTURA_RABATT_KR);
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

    /**
     * Marks as is the invoice was sent by the common/physical post
     */
    protected void setSentByCommonPost() {
        //
        if (this instanceof HTMLPrint_A && bim.isOffert()) {
            //[#OFFERT#]
            loggDocumentSent(getFakturaId(), DB.STATIC__SENT_STATUS__SKICKAD_COMMON_POST, DB.STATIC__SENT_TYPE_OFFERT);
            HelpA.showNotification(LANG.MSG_10_4(true));
        } else if (this instanceof HTMLPrint_A) {
            loggDocumentSent(getFakturaId(), DB.STATIC__SENT_STATUS__SKICKAD_COMMON_POST, DB.STATIC__SENT_TYPE_FAKTURA);
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
                        loggDocumentSent(fakturaId, DB.STATIC__SENT_STATUS__SKICKAD, DB.STATIC__SENT_TYPE_OFFERT);
                    } else if (htmlprint instanceof HTMLPrint_A) {
                        loggDocumentSent(fakturaId, DB.STATIC__SENT_STATUS__SKICKAD, DB.STATIC__SENT_TYPE_FAKTURA);
                    } else {
                        loggDocumentSent(fakturaId, DB.STATIC__SENT_STATUS__SKICKAD, DB.STATIC__SENT_TYPE_PAMMINELSE);
                    }

                    //
                } else {
                    if (htmlprint instanceof HTMLPrint_A && bim.isOffert()) {
                        //[#OFFERT#]
                        EditPanel_Send.insert(fakturaId, DB.STATIC__SENT_STATUS__EJ_SKICKAD,
                                DB.STATIC__SENT_TYPE_OFFERT);
                    } else if (htmlprint instanceof HTMLPrint_A) {
                        EditPanel_Send.insert(fakturaId, DB.STATIC__SENT_STATUS__EJ_SKICKAD,
                                DB.STATIC__SENT_TYPE_FAKTURA);
                    } else {
                        EditPanel_Send.insert(fakturaId, DB.STATIC__SENT_STATUS__EJ_SKICKAD,
                                DB.STATIC__SENT_TYPE_PAMMINELSE);
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
        HelpA.showNotification(LANG.FAKTURA_UTSKRIVEN_OUTLOOK(fileName, reminder, bim.isOffert(),new File(documentPath)));
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
                loggDocumentSent(getFakturaId(), DB.STATIC__SENT_STATUS__SKICKAD_OUTLOOK, DB.STATIC__SENT_TYPE_OFFERT);
            } else if (this instanceof HTMLPrint_A) {
                loggDocumentSent(getFakturaId(), DB.STATIC__SENT_STATUS__SKICKAD_OUTLOOK, DB.STATIC__SENT_TYPE_FAKTURA);
            } else if (this instanceof HTMLPrint_B) {
                loggDocumentSent(getFakturaId(), DB.STATIC__SENT_STATUS__SKICKAD_OUTLOOK, DB.STATIC__SENT_TYPE_PAMMINELSE);
            }
            //
            //
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    protected void loggDocumentSent(String fakturaId, String sendStatus, String sendType) {
        //
        EditPanel_Send.insert(fakturaId, sendStatus, sendType); // "buh_faktura_send" table
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
            if(print_ok){
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
                EditPanel_Send.insert(bim.getFakturaId(), DB.STATIC__SENT_STATUS__UTSKRIVEN, DB.STATIC__SENT_TYPE_OFFERT);
            } else if (this instanceof HTMLPrint_A) {
                EditPanel_Send.insert(bim.getFakturaId(), DB.STATIC__SENT_STATUS__UTSKRIVEN, DB.STATIC__SENT_TYPE_FAKTURA);
            } else if (this instanceof HTMLPrint_B) {
                EditPanel_Send.insert(bim.getFakturaId(), DB.STATIC__SENT_STATUS__UTSKRIVEN, DB.STATIC__SENT_TYPE_PAMMINELSE);
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
