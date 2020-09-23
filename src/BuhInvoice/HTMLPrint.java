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
import forall.HelpA;
import java.io.File;
import java.net.MalformedURLException;
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
        init();
        //
    }
    
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
        String body = "Du har fått " + getFakturaTitleBasedOnType_subject().toLowerCase() + " från: " + getForetagsNamn();
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
    
    protected String getFakturaTitleBasedOnType_subject() {
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
    
}
