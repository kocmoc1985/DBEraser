/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.LANG;
import forall.GP;
import forall.HelpA;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import net.coobird.thumbnailator.Thumbnails;

/**
 *
 * @author KOCMOC
 */
public class GP_BUH {

    public static final String VERSION = "1.01";
    public static final String PRODUCT_NAME = "LAFakturering";
    public static String CUSTOMER_COMPANY_NAME = "";
    public static boolean CUSTOMER_MODE = true;
    public static boolean TRACKING_TOOL_TIP_ENABLED = true;
    //
    public static String KUND_ID;
    //
    public static boolean loggedIn(){
       return KUND_ID != null;
    }
    /*
     * By [2020-10-07]
     * For MixCont test Bolag "kundId=1" use: "mixcont"/"mixcont4765"
     * For Alex Bolag "kundId=2" use: "alex"/"alex1980"
     * Auto creted on [2020-10-14] BuhInvoice use:"andrej.brassas@gmail.com" / "L5xpPomI41"
     */
    //
    public static String USER = "";
    public static String PASS = "";
    //
    public static final String DATE_FORMAT_BASIC = "yyyy-MM-dd";
    private static final String LOGO_PATH = "io/logo.png";

    public static final String LOGO_PATH() {
        return "io/logo_" + KUND_ID + ".png";
    }
    //
    // OBS! Have also look in "Basic_Buh.class" for "FREQUENTLY USED METHODS" ****************
    //
    public static final int MAX_AMMOUNT_ARTICLES__FAKTURA = 14;

    public static String replaceColon(String text, boolean reverse) {
        if (reverse == false) {
            return text.replaceAll(":", "#");
        } else {
            return text.replaceAll("#", ":");
        }
    }

    public static String replaceComma(String text, boolean reverse) {
        if (reverse == false) {
            return text.replaceAll(",", "¤");
        } else {
            return text.replaceAll("¤", ",");
        }
    }
    
    public static String replacePlus(String text, boolean reverse) {
        if (reverse == false) {
            return text.replaceAll("\\+", "£");
        } else {
            return text.replaceAll("£", "+");
        }
    }

    public static String translateJaNejEmptyLineChar(String staticJaNej, String value) {
        HashMap<String, String> map = JSon.JSONToHashMap(staticJaNej, false);
        return getValNoNull(map.get(value));
    }

    public static String getValNoNull(String value) {
        if (value == null || value.isEmpty() || value.equals("null") || value.equals("NULL")) {
            return "";
        } else {
            return value;
        }
    }

    public static String _get(HashMap<String, String> map, String param) {
        return _get(map, param, false);
    }

    public static String _get(HashMap<String, String> map, String param, boolean replaceSpecialChars) {
        //
        String val = map.get(param);
        //
        if (val == null || val.isEmpty() || val.equals("null") || val.equals("NULL")) {
            return "";
        } else {
            //
            if (replaceSpecialChars) {
                val = replaceColon(val, true);
                val = replaceComma(val, true);
            }
            //
            return val;
        }
    }

    public static void enableDisableButtons(JPanel parent, boolean enabled) {
        java.awt.EventQueue.invokeLater(() -> {
            Component[] components = parent.getComponents();
            for (Component c : components) {
                if (c instanceof JButton) {
                    c.setEnabled(enabled);
                }
            }
        });

    }

    public static void setEnabled(JComponent c, boolean enabled) {
        java.awt.EventQueue.invokeLater(() -> {
            c.setEnabled(enabled);
        });
    }

    public static double round_double(double rst) {
        return Double.parseDouble(String.format("%2.2f", rst).replace(",", "."));
    }

    public static double round_double_b(double rst) {
        return Double.parseDouble(String.format("%2.0f", rst).replace(",", "."));
    }

    public static boolean confirmWarning(String message) {
        return JOptionPane.showConfirmDialog(null, message, "Bekräfta", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
    }

    public static boolean confirm(String message) {
        return JOptionPane.showConfirmDialog(null, message, "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static String getDate_yyyy_MM_dd() {
        return HelpA.get_proper_date_yyyy_MM_dd();
    }

    public static String getDateTime_yyyy_MM_dd() {
        return HelpA.get_proper_date_time_same_format_on_all_computers();
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

    public static void centerAndBringToFront(JFrame frame) {
        Point p = HelpA.position_window_in_center_of_the_screen(frame);
        frame.setLocation(p);
        frame.setVisible(true);
        //
        java.awt.EventQueue.invokeLater(() -> {
            frame.toFront();
            frame.repaint();
        });
    }

    private static boolean logoExistAlready() {
        File f = new File(GP_BUH.LOGO_PATH());
        return f.exists();
    }

    public static void chooseLogo(Component parent) {
        //
        if (logoExistAlready()) {
            if (confirm(LANG.MSG_17)) {
                File f = new File(GP_BUH.LOGO_PATH());
                f.delete();
                return;
            }
        }
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
        if (path == null || path.isEmpty()) {
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
        if (w_orig < MIN_WIDTH) {
            HelpA.showNotification(LANG.LOGOTYP_TO_SMALL("" + MIN_WIDTH, "" + w_orig));
            return;
        }
        //
        double wh_proportion = (double) w_orig / (double) h_orig;
        //
        System.out.println("Original img w: " + w_orig + " height: " + h_orig);
        System.out.println("wh_proportion: " + wh_proportion);
        //
        int w_new = 0;
        int h_new = 0;
        //
        if (w_orig > MAX_WIDTH) {
            w_new = MAX_WIDTH;
            //
            if (h_orig > MAX_HEIGHT) {
                h_new = MAX_HEIGHT; // This should be made more flexible***********[2020-09-04]
            } else {
                h_new = (int) (w_new / wh_proportion);
            }
            //

        } else {
            w_new = w_orig;
            h_new = h_orig;
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
                .toFile(new File(GP_BUH.LOGO_PATH()));
        //
    }

    public static Image getBuhInvoicePrimIcon() {
        return new ImageIcon(GP.IMAGE_ICON_URL_PROD_PLAN).getImage();
    }

    private static String chooseFile(Component parent) {
        JFileChooser chooser = new JFileChooser();
        //
        Frame window = new Frame();
        window.setIconImage(getBuhInvoicePrimIcon());
        //
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG, PNG, BMP Images", "jpg", "png", "bmp");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(window); // this one is needed to test icon
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getPath();
            System.out.println("You chose to open this file: " + path);
            return path;
        } else {
            return null;
        }
    }

    public static DefaultTableCellRenderer getRendererForfalloDatum() {
        //
        return new DefaultTableCellRenderer() {
            //
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                //
                boolean isSelctedRow = table.getSelectedRow() == row;
                boolean isBetald = HelpA.getValueGivenRow(table, row, InvoiceB.TABLE_ALL_INVOICES__BETALD).contains(DB.STATIC__YES);
                boolean isMakulerad = HelpA.getValueGivenRow(table, row, InvoiceB.TABLE_ALL_INVOICES__MAKULERAD).equals(DB.STATIC__YES);
                boolean isFakturaTypeNormal = HelpA.getValueGivenRow(table, row, InvoiceB.TABLE_ALL_INVOICES__FAKTURA_TYP).equals(DB.STATIC__FAKTURA_TYPE_NORMAL);
                //
                if (HelpA.getColumnName(table, col).equals(InvoiceB.TABLE_ALL_INVOICES__FORFALLODATUM) == false) {
                    //
                    if (isSelctedRow) {
                        this.setForeground(Color.white);
                    } else {
                        this.setForeground(Color.black);
                    }
                    //
                    return this;
                }
                //
                String cellValue = (String) table.getModel().getValueAt(row, col);
                //
                String dateNow = getDate_yyyy_MM_dd();
                //
                boolean forfallen = HelpA.compareDates(dateNow, DATE_FORMAT_BASIC, cellValue, DATE_FORMAT_BASIC);
                //
                if (forfallen && isFakturaTypeNormal && isBetald == false && isMakulerad == false && isSelctedRow == false) {
                    this.setForeground(Color.red);
                    return this;
                } else {
                    if (isSelctedRow) {
                        this.setForeground(Color.white);
                    } else {
                        this.setForeground(Color.black);
                    }
                    return this;
                }
            }
        };
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
