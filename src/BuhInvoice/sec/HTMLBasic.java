/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import BuhInvoice.GP_BUH;
import BuhInvoice.HTMLPrint_A;
import java.awt.Point;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author KOCMOC
 */
public abstract class HTMLBasic extends JFrame implements DocumentListener, ChangeListener {

    public abstract String buildHTML();

    public abstract String[] getCssRules();

    public abstract JEditorPane getEditorPane();

    public abstract JScrollPane getJScrollPane();

    public abstract String getWindowTitle();

    private boolean flag_a = false;
    private final int MAX_ALLOWED = 815;

    private JEditorPane jep;
    private Caret caret;

    /**
     * Use this one when, getting the image from inside the "project dir / root"
     *
     * @param pathAndFileName
     * @return - like: "file:/J:/MyDocs/src/...."
     */
    public String getPathNormal(String pathAndFileName) {
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

    /**
     * Use this one when, getting the image from the "inside project / .jar
     * file"
     *
     * @param path
     * @param imgName
     * @return - like: "file:/J:/MyDocs/src/...."
     */
    public String getPathResources(String path, String imgName) {
        return getImageIconURL(path, imgName).toString();
    }

    /**
     *
     * @param path - path to image folder, play around to get the path working
     * @param picName
     * @return
     */
    public URL getImageIconURL(String path, String picName) {
        //OBS! YES the first "/" is NEEDED - 100% [2020-06-09]
        return HTMLBasic.class.getResource("/" + path + "/" + picName);
    }

    protected void scrollToTop() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                getJScrollPane().getVerticalScrollBar().setValue(0);
            }
        });
    }

    public void go() {
        //
        jep = getEditorPane();
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
        //
        doc.addDocumentListener(this); // [2021-09-10]
        caret = jep.getCaret();
        caret.addChangeListener(this);//[#DOCUMENT-HEIGHT#]
        //
        jep.setDocument(doc);
        //
        jep.setText(buildHTML());
        //
        jep.invalidate();
        jep.validate();
        jep.repaint();
        //
        // The one below triggeres the "stateChanged(...)"
        stateChangedTrigger(doc.getEndPosition().getOffset()); // [#DOCUMENT-HEIGHT#]
        //
    }

    @Override
    public void stateChanged(ChangeEvent e) { // ChengeListener
        //
        // SUPER IMPORTANT, this regards to Caret
        //
        System.out.println("Caret EVENT: stateChanged() *******************");
        Point point = caret.getMagicCaretPosition();
        //
        System.out.println("MAGIC POINT**: " + point); // y: height, x: width
        System.out.println("CARRET POS**: " + caret.getDot());
        //
        if (point != null && point.getY() > MAX_ALLOWED && flag_a == false) { // [#DOCUMENT-HEIGHT#]
            flag_a = true;
            GP_BUH.showNotification("Utskriftens innehåll är större än tillåtet");
        }
        //
    }
    
    private void stateChangedTrigger(){
        stateChangedTrigger(jep.getDocument().getEndPosition().getOffset());
    }

    private void stateChangedTrigger(int pos) {
        // [#DOCUMENT-HEIGHT#]
        java.awt.EventQueue.invokeLater(() -> {
            //
            jep.getCaret().moveDot(pos);
            jep.getCaret().setBlinkRate(500);
            jep.getCaret().setVisible(true);
            //
        });
        //
    }

    // protected final static Dimension A4_PAPER = new Dimension(545, 842);
    @Override
    public void insertUpdate(DocumentEvent e) { // DocumentListener
        Document doc = e.getDocument();
        //
        stateChangedTrigger(jep.getCaretPosition());
        //
//        System.out.println("#insertUpdate#");
//        System.out.println("Document length: " + doc.getLength());
//        System.out.println("Document End Position: " + doc.getEndPosition());
//        System.out.println("Document Start Position: " + doc.getStartPosition());
//        //
//        System.out.println("JEditorPane CaretPosition: " + jep.getCaretPosition());
//        System.out.println("JEditorPane Height: " + jep.getHeight());
//        System.out.println("JEditorPane Width: " + jep.getWidth()); //jep.getVisibleRect()
//        System.out.println("JEditorPane Visible Rect: " + jep.getVisibleRect());
//        //
//        System.out.println("Carret pos Insert: " + jep.getCaret().getMagicCaretPosition()); // jep.getCaret().getMagicCaretPosition()
        //
    }

    @Override
    public void removeUpdate(DocumentEvent e) { // DocumentListener
        stateChangedTrigger(jep.getCaretPosition());
    }

    @Override
    public void changedUpdate(DocumentEvent e) { // DocumentListener
        // This one is triggered only when the document is loaded.
        // So it's not triggered when you edit the text directly on the invoice.
        // But if you insert a stamp it will be triggered because the document is reloaded/rebuild in this case.
        System.out.println("");
    }

    private void getLineCount() {
        //OBS! Counting lines of an HTML doc seems to be super difficult because of the tags -> so <html> tag considered to be a line which in fact is not
//        return jep.getText().split("\r\n").length;
        //
    }

    private void listAllTags() {
        //Working great
        Pattern p = Pattern.compile("<([^\\s>/]+)");
        Matcher m = p.matcher(jep.getText());
        while (m.find()) {
            String tag = m.group(1);
            System.out.println(tag);
        }
    }

    private void findAndProcessElemnt_example() {
        //
        HTMLDocument htmldoc = (HTMLDocument) jep.getDocument();
        //        
        Element elem = htmldoc.getElement("adressdata");
        //
        if (elem != null) {
            //
            System.out.println("Elem: " + elem.getName());
            System.out.println("Elem startoffset: " + elem.getStartOffset());
            AttributeSet a = elem.getAttributes(); // a.getAttribute(HTML.Attribute.NAME);
            Enumeration enumm = a.getAttributeNames();
            //
            while (enumm.hasMoreElements()) {
                System.out.println("Enum: " + enumm.nextElement()); // Attributes are: id, class, name
            }
            //
        }
    }

}
