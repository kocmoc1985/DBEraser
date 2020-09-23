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
import static BuhInvoice.HTMLPrint_A.displayStatus;
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
    //
    protected final static Dimension A4_PAPER = new Dimension(545, 842);

    /**
     * Creates new form HTMLPrint_A
     */
    public HTMLPrint(
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
        initComponents_();
        //
        this.setTitle(getWindowTitle());
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
        init();
        //
    }
    
    protected abstract String getWindowTitle();
    
    protected abstract void initComponents_();

    protected abstract JEditorPane getEditorPane();
    
    protected abstract JScrollPane getJScrollPane();
    
    protected abstract String[] getCssRules();
    
    protected abstract String buildHTML();
    
    private void init() {
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
    
    protected String getFakturaId(){
        return  map_a_0.get(DB.BUH_FAKTURA__ID__);
    }

    protected String getPdfFakturaFileName(boolean appendKundId) {
        //
        if (appendKundId) {
            return "faktura_" + bim.getKundId() + ".pdf";
        } else {
            return "faktura.pdf";
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
        return System.getProperty("user.home") + "/Desktop/" + getPdfFakturaFileName(false);
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
    
    protected String getHTMLPrintTitle() {
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
    
    protected void fakturaSentPerEpost_saveToDb(String fakturaId,String sendStatus) {
        //
        EditPanel_Send.insert(fakturaId, sendStatus,
                DB.STATIC__SENT_TYPE_FAKTURA); // "buh_faktura_send" table
        //
        Basic_Buh_.executeSetFakturaSentPerEmail(fakturaId); // "buh_faktura" table -> update sent status
        bim.setValueAllInvoicesJTable(InvoiceB.TABLE_ALL_INVOICES__EPOST_SENT, DB.STATIC__YES);
        //
    }

    protected boolean print_normal() {
         //
        JEditorPane jep = getEditorPane();
        //
        int actHeight = jep.getHeight();
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
                if(this instanceof HTMLPrint_A){
                    pj.setJobName(LANG.FAKTURA); // This changes the name of file if printed to ".pdf"
                }else if(this instanceof HTMLPrint_B){
                    pj.setJobName(LANG.PAMINNELSE); // This changes the name of file if printed to ".pdf"
                }else{
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
