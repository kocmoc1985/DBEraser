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
import javax.swing.border.Border;

/**
 *
 * @author KOCMOC
 */
public class BlinkThread implements Runnable {

    private final JComponent component;
    private final boolean red;
    private static boolean ready = true;
    private boolean blinkText = false;

    public BlinkThread(JComponent component, boolean red) {
        this.component = component;
        this.red = red;
        startThread();
    }

    public void setBlinkText() {
        this.blinkText = true;
    }

    private void startThread() {
        if (ready == false) {
            return;
        }
        Thread x = new Thread(this);
        x.start();
    }

    @Override
    public void run() {
        if (blinkText) {
            if (red) {
                blinkText(component, Color.red);
            } else {
                blinkText(component, Color.green);
            }
        } else {
            if (red) {
                blink(component, Color.red);
            } else {
                blink(component, Color.green);
            }
        }

    }

    private void blink(JComponent jc, Color color) {
        ready = false;
        if (jc != null) {
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

    private void blinkText(JComponent jc, Color color) {
        ready = false;
        if (jc != null) {
            Color prevColor = jc.getForeground();
            jc.setForeground(color);
            wait_(500);
            jc.setForeground(prevColor);
            wait_(500);
            jc.setForeground(color);
            wait_(500);
            jc.setForeground(prevColor);
            //
            ready = true;
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
