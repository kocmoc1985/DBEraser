/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.coobird.thumbnailator.Thumbnails;

/**
 *
 * @author KOCMOC
 */
public class GP_BUH {

    public static boolean CUSTOMER_MODE = true;

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

    public static void resizeImage() throws IOException {
        Thumbnails.of(new File("mc_logo.png"))
        .size(160, 69)
        .toFile(new File("logo.png"));
    }
    
    public static void main(String[] args) {
        try {
            resizeImage();
        } catch (IOException ex) {
            Logger.getLogger(GP_BUH.class.getName()).log(Level.SEVERE, null, ex);
        }
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
