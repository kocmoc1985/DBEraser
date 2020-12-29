/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import BuhInvoice.HTMLPrint_A;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author KOCMOC
 */
public abstract class HTMLBasic extends JFrame{

    public abstract String buildHTML();

    public abstract String[] getCssRules();

    public abstract JEditorPane getEditorPane();

    public abstract JScrollPane getJScrollPane();
    
    public abstract String getWindowTitle();
    
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
        jep.invalidate();
        jep.validate();
        jep.repaint();
    }

}
