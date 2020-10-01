/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import static BuhInvoice.GP_BUH._get;
import BuhInvoice.sec.LANG;
import com.qoppa.pdfWriter.PDFPrinterJob;
import forall.HelpA;
import java.awt.Color;
import java.awt.Desktop;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public abstract class HTMLPrint extends JFrame {

    protected final BUH_INVOICE_MAIN bim;
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
    protected final String FAKTURA_TYPE;
    protected final boolean preview;
    //
    protected final static Dimension A4_PAPER = new Dimension(545, 842);
    //
    //
    //
    public static final String T__FAKTURA_NR = "Faktura nr";
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
    public static final String COL_3_1 = DB.BUH_ADDR__LAND;
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
    public static final String T__FTG_TELEFON = "Telefon";
    public static final String T__FTG_EPOST = "Mejla";
    public static final String T__FTG_BANKGIRO = "BG";
    public static final String T__FTG_POSTGIRO = "PG";
    public static final String T__FTG_KONTO = "Konto";
    public static final String T__FTG_SWISH = "Swish";
    public static final String T__FTG_ORGNR = "Organisationsnr";
    public static final String T__FTG_MOMS_REG_NR = "Momsregnr";
    public static final String T__FTG_F_SKATT = "Godkänd för F-skatt";

    /**
     * Creates new form HTMLPrint_A
     */
    public HTMLPrint(
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
        //
        init();
        //
    }
    
    protected abstract void buttonLogic();

    protected abstract String getWindowTitle();

    protected abstract void initComponents_();

    protected abstract JEditorPane getEditorPane();

    protected abstract JScrollPane getJScrollPane();

    protected abstract String[] getCssRules();

    protected abstract String buildHTML();

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

    protected static ArrayList<HashMap<String, String>> jTableToList(JTable table) {
        //
        for (int x = 0; x < table.getRowCount(); x++) {
            //
            ArrayList rowValues = HelpA.getLineValuesVisibleColsOnly(table, x);
            //
        }
        //
        return null;
    }

    protected void scrollToTop() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                getJScrollPane().getVerticalScrollBar().setValue(0);
            }
        });
    }

    protected void go() {
        //
        JEditorPane jep = getEditorPane();
        //
        String[] CSSRules = getCssRules();
        //
        HTMLEditorKit kit = new HTMLEditorKit();
        jep.setEditorKit(kit);
        //
        StyleSheet styleSheet = kit.getStyleSheet();
        //
        //
        for (int i = 0; i < CSSRules.length; i++) {
            styleSheet.addRule(CSSRules[i]);
        }
        //
        Document doc = kit.createDefaultDocument();
        jep.setDocument(doc);
        //
        jep.setText(buildHTML());
        //
    }

    /**
     * Use this one when, getting the image from the "inside project / .jar
     * file"
     *
     * @param path
     * @param imgName
     * @return - like: "file:/J:/MyDocs/src/...."
     */
    protected String getPathResources(String path, String imgName) {
        return getImageIconURL(path, imgName).toString();
    }

    /**
     *
     * @param path - path to image folder, play around to get the path working
     * @param picName
     * @return
     */
    protected URL getImageIconURL(String path, String picName) {
        //OBS! YES the first "/" is NEEDED - 100% [2020-06-09]
        return HTMLPrint.class.getResource("/" + path + "/" + picName);
    }

    /**
     * Use this one when, getting the image from inside the "project dir / root"
     *
     * @param pathAndFileName
     * @return - like: "file:/J:/MyDocs/src/...."
     */
    protected String getPathNormal(String pathAndFileName) {
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

    protected String getFakturaId() {
        return map_a_0.get(DB.BUH_FAKTURA__ID__);
    }

    protected String getPdfFileName(boolean appendKundId) {
        //
        String fileName;
        //
        if (this instanceof HTMLPrint_A) {
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

    protected String getForetagsNamn() {
        return _get(map_f, DB.BUH_KUND__NAMN);
    }

    protected String getFakturaDesktopPath() {
        return System.getProperty("user.home") + "/Desktop/" + getPdfFileName(false);
    }

    protected String getEmailBody() {
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

    protected String getHTMLPrintTitle() {
        if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_NORMAL)) {
            return LANG.FAKTURA;
        } else if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_KREDIT)) {
            return "Kreditfaktura";
        } else if (FAKTURA_TYPE.equals(DB.STATIC__FAKTURA_TYPE_KONTANT)) {
            return "Kvitto";
        } else {
            return null;
        }
    }

    protected abstract void displayStatus(String msg, Color c);

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
        String subject = getHTMLPrintTitle();
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
    protected void sendWithStandardEmailClient() {
        //
        String mailto = getFakturaKundEmail();
        String subject = getHTMLPrintTitle();
        String body = getEmailBody();
        String desktopPath = getFakturaDesktopPath();
        //
        print_java(desktopPath);
        HelpA.showNotification(LANG.FAKTURA_UTSKRIVEN_OUTLOOK(getPdfFileName(false)));
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
            if (this instanceof HTMLPrint_A) {
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
        Basic_Buh.executeSetFakturaSentPerEmail(fakturaId); // "buh_faktura" table -> update sent status
        bim.setValueAllInvoicesJTable(InvoiceB.TABLE_ALL_INVOICES__EPOST_SENT, DB.STATIC__YES);
        //
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
     */
    protected void print_java(String filename) {
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

}
