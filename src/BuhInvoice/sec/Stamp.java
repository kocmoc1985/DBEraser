/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MCREMOTE
 */
public class Stamp {

    private final URL url;
    private final int w;
    private final int h;

    public Stamp(URL url) {
        this.url = url;
        w = 128;
        h = 128;
    }

    public Stamp(URL url, int w, int h) {
        this.url = url;
        this.w = w;
        this.h = h;
    }

    public String getPath() {
        return url.toString();
    }

    public String getFileName() {
        try {
            File f = new File(url.toURI());
            return f.getName();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Stamp.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

}
