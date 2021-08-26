/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import forall.ErrorOutputListener;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 *
 * @author KOCMOC
 */
public class BlinkThread implements Runnable {

    private final JComponent component;
    private final boolean red;
    public static boolean ready = true;
//    private boolean blinkText = false;
    private boolean blinkIcon = false;
    private boolean run = true;
    private String fakturaId;

    public BlinkThread(JComponent component, boolean red) {
        this.component = component;
        this.red = red;
        startThread();
    }

    public BlinkThread(JComponent component, String fakturaId) {
        this.component = component;
        this.fakturaId = fakturaId;
        red = false;
        blinkIcon = true;
        startThreadB(); // [#INFO-ICON-BLINK#]
    }

    public BlinkThread(JComponent component) {
        this.component = component;
        red = false;
        blinkIcon = true;
        //
        if (ready == true) {
            System.out.println("NEW BLINK THREAD STARTED****");
            ready = false;
            startThreadB();   
        }
        //
    }

    private void startThreadB() {
        Thread x = new Thread(this);
        x.start();
    }

    public String getFakturaId() {
        return fakturaId; // [#INFO-ICON-BLINK#]
    }

    public void abortThreadB() {
        run = false;
    }

    private void startThread() {
        //
        if (ready == false) {
            return;
        }
        //
        Thread x = new Thread(this);
        x.start();
        //
    }

    @Override
    public void run() {
        //
        if (blinkIcon) {
            blinkIcon(component);
        } else {
            if (red) {
                blink(component, Color.red);
            } else {
                blink(component, Color.green);
            }
        }
        //
    }

    private void blink(JComponent jc, Color color) {
        ready = false;
        if (jc != null) {
            //
            Border prevBorder = jc.getBorder();
            jc.setBorder(BorderFactory.createLineBorder(color, 2));
            wait_(500);
            jc.setBorder(prevBorder);
            wait_(500);
            jc.setBorder(BorderFactory.createLineBorder(color, 2));
            wait_(500);
            jc.setBorder(prevBorder);
            //
            ready = true;
        }
    }

    private void blinkIcon(JComponent jc) {
        // [#INFO-ICON-BLINK#]
        int millis = 800;
        //
        if (jc != null) {
            //
            for (int i = 0; i < 8; i++) {
                //
                wait_(millis);
                //
                if (run == false) {
                    break;
                }
                //
                if (jc.isVisible()) {
                    jc.setVisible(false);
                } else {
                    jc.setVisible(true);
                }
            }
            //
            ready = true;
            //
        }
    }

    private synchronized void wait_(int millis) {
        try {
            wait(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(ErrorOutputListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
