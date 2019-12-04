/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forall;

import java.awt.Color;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

/**
 *
 * @author KOCMOC
 */
public class ErrorOutputListener implements Runnable {

    private File file;
    private double prevFileSize = 0;
    private JComponent component;

    public ErrorOutputListener(String errOutPutFilePath, JComponent c) {
        //
        if(errOutPutFilePath == null || errOutPutFilePath.isEmpty()){
            return;
        }
        //
        this.file = new File(errOutPutFilePath);
        this.component = c;
        //
        if (file.exists() == false) {
            return;
        }
        //
        prevFileSize = file.length(); // size in bytes
        //
        startThread();
    }

    private void startThread() {
        Thread x = new Thread(this);
        x.start();
    }

    @Override
    public void run() {
        //
        while (true) {
            //
            double fileSize = file.length();
            //
            if (prevFileSize != fileSize) {
                
                actionOnChanged();
                prevFileSize = fileSize;
            } 
//            else {
//                System.out.println("ExceptionListener working");
//            }
            //
            wait_(1000);
            //
        }
    }

    private void actionOnChanged() {
//        System.out.println("FILE CHANGED");
        if (component != null) {
            Border prevBorder = component.getBorder();
            component.setBorder(BorderFactory.createLineBorder(Color.red, 5));
            wait_(500);
            component.setBorder(prevBorder);
            wait_(500);
            component.setBorder(BorderFactory.createLineBorder(Color.red, 5));
            wait_(500);
            component.setBorder(prevBorder);
        }
    }

    private synchronized void wait_(int millis) {
        try {
            wait(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(ErrorOutputListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public static void main(String[] args) {
//        ErrorOutputListener el = new ErrorOutputListener("err_output/err_2018-02-20 14_20.txt",null);
//    }

}
