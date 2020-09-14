/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.LANG;
import forall.HelpA;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.coobird.thumbnailator.Thumbnails;

/**
 *
 * @author KOCMOC
 */
public class GP_BUH {

    public static boolean CUSTOMER_MODE = true;

    public static String replaceColon(String text){
        return text.replaceAll(":", "#");
    }
    
    public static String getValHashMap(String value) {
        if (value == null || value.isEmpty() || value.equals("null") || value.equals("NULL")) {
            return "";
        } else {
            return value;
        }
    }

    public static double round_double(double rst) {
        return Double.parseDouble(String.format("%2.2f", rst).replace(",", "."));
    }

    public static boolean confirmWarning(String message) {
        return JOptionPane.showConfirmDialog(null, message, "Bekräfta", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
    }
    
      public static boolean confirm(String message) {
        return JOptionPane.showConfirmDialog(null, message, "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static String getDateCreated() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss.SSS");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

    public static boolean verifyId(String fakturaId) {
        //
        int id;
        //
        try {
            id = Integer.parseInt(fakturaId);
        } catch (Exception ex) {
            id = -1;
        }
        //
        return id != -1;
        //
    }

    public static void resizeImage_example() throws IOException {
        Thumbnails.of(new File("mc_logo.png"))
                .size(160, 69)
                .toFile(new File("logo.png"));
    }
    
    public static void chooseLogo(Component parent){
         //
        String path = chooseFile(null);
        //
        try {
            resizeLogo(path);
        } catch (IOException ex) {
            Logger.getLogger(GP_BUH.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
    }

    private static void resizeLogo(String path) throws IOException {
        //
        if(path == null || path.isEmpty()){
            return;
        }
        //
        int MAX_WIDTH = 170;
        int MIN_WIDTH = 120;
        int MAX_HEIGHT = 90;
        //
        BufferedImage img = ImageIO.read(new File(path));
        //
        int w_orig = img.getWidth();
        int h_orig = img.getHeight();
        //
        if(w_orig < MIN_WIDTH){
            HelpA.showNotification(LANG.LOGOTYP_TO_SMALL("" + MIN_WIDTH, "" + w_orig));
            return;
        }
        //
        double wh_proportion = (double)w_orig / (double)h_orig;
        //
        System.out.println("Original img w: " + w_orig + " height: " + h_orig);
        System.out.println("wh_proportion: " + wh_proportion);
        //
        int w_new = 0;
        int h_new = 0;
        //
        if(w_orig > MAX_WIDTH ){
           w_new = MAX_WIDTH;
           //
           if(h_orig > MAX_HEIGHT){
             h_new = MAX_HEIGHT; // This should be made more flexible***********[2020-09-04]
           }else{
             h_new = (int) (w_new /wh_proportion);
           }
           //
          
        }
        //
        System.out.println("w_new: " + w_new);
        System.out.println("h_new: " + h_new);
        //
        File f = new File(path);
        //
        //
        Thumbnails.of(f)
                .size(w_new, h_new)
                .toFile(new File("logo.png"));
        //
    }

    private static String chooseFile(Component parent) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG, PNG, BMP Images", "jpg", "png", "bmp");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(parent); // this one is needed to test icon
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getPath();
            System.out.println("You chose to open this file: " + path);
            return path;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        //
        String path = chooseFile(null);
        //
        try {
            resizeLogo(path);
        } catch (IOException ex) {
            Logger.getLogger(GP_BUH.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
    }

    /**
     * [2020-08-12] This one keeps the "marking line" on the same row as before
     * the refresh
     */
//    protected void refresh_b() {
//        JTable table = bim.jTable_invoiceB_alla_fakturor;
//        int row = table.getSelectedRow();
//        fillFakturaTable();
//        HelpA.markGivenRow(bim.jTable_invoiceB_alla_fakturor, row);
//        String fakturaId = bim.getFakturaId();
//        all_invoices_table_clicked(fakturaId);
//    }
}
