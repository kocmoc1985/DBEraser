/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.IO;
import BuhInvoice.sec.LANG;
import MyObjectTableInvert.JLinkInvert;
import forall.BackgroundPanel;
import forall.GP;
import forall.HelpA;
import static forall.HelpA.dateToMillisConverter3;
import static forall.HelpA.file_exists;
import static forall.HelpA.millisToDateConverter;
import icons.ICON;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParsePosition;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import net.coobird.thumbnailator.Thumbnails;


/**
 *
 * @author KOCMOC
 */
public class GP_BUH {

    public static final String VERSION = "1.10"; // prev: 1.09
    public static final int VERSION_INTEGER = 110; // prev: 109
    public static final String PRODUCT_NAME = "LAFakturering";
    public static String CUSTOMER_COMPANY_NAME = "";
    public static boolean CUSTOMER_MODE = true;
    public static boolean TRACKING_TOOL_TIP_ENABLED = false;
    //
    public static String KUND_ID;
    //
    public static String LA_WEB_ADDR = "www.lafakturering.se";
    
    /*
     * By [2021-03-09]
     * For the TEST-DB: andrej.brassas@gmail.com / 09WYJK1aUy [domain la.se][php-test]
     * For the TEST-DB: guest_1_kocmoc1985@gmail.com / 41zKTnoAwU  [domain la.se][php-test]
     *
     * For demoDemo.. use "demo@lafakturering.se" -> p:e2gK0knpf9
     * For demo use: "exempel@lafakturering.se" ->  p:"YxCa3i1EgM" [Verified on 2021-04-08][domain la.se] [not test]
     * Auto created on [2021-05-27] andrei.brassas@mixcont.com / "sgycPkSeFY" -> GUEST ACCOUNT:
     * guest_31_kocmoc1985@gmail.com / "BMZYyw2kv4"
     * Auto created on [2020-10-14] BuhInvoice use:"andrej.brassas@gmail.com" / "8RDoPnvugb" --> www.la.se [not test]
     * Auto created on [2021-04-30] "kocmoc1985@gmail.com / ZEzdghFNRP" --> www.la.se [not test]
     *
     * For MixCont test Bolag "kundId=1" use: "ask@mixcont.com"/"mixcont4765" ---> THE REAL ONE****
     * For Alex Bolag "kundId=2" use: "alex_breicht@yahoo.se "/"alex1980"
     * 
     * (andrej.brassas@) -> Guest Account "guest_21_kocmoc1985@gmail.com" "g2Dpo3kGXK"
     */
    //
    public static String USER = "";
    public static String PASS = "";

    //
    public static String getChangedBy() {
        return USER.split("@")[0];
    }
    //
    public static final String DATE_FORMAT_BASIC = "yyyy-MM-dd";

    //
    // OBS! Have also look in "Basic_Buh.class" for "FREQUENTLY USED METHODS" ****************
    //
    public static final int MAX_AMMOUNT_ARTICLES__FAKTURA = 14;

    public static final String GDPR_ACCEPTED_FILE_PATH = IO.IO_DIR + "gdpr";

    public static boolean isGdprAccepted() {
        //
        return !file_exists(new File(GDPR_ACCEPTED_FILE_PATH));
        //
    }

    public static final String LOGO_PATH() {
        return IO.IO_DIR + "logo_" + KUND_ID + ".png";
    }


    public static final String SMTP_PATH() {
        return IO.IO_DIR + "smtp_" + KUND_ID;
    }

    public static Image getBuhInvoicePrimIcon() {
        return new ImageIcon(GP.IMAGE_ICON_URL_LAFAKTURERING).getImage();
    }

    public static String BASIC_BACKGROUND_IMG__PATH = IO.IO_DIR + "bg.jpg";

    
    private static void setPageBackground(JPanel panel, String path) {
        //
        BackgroundPanel bg = (BackgroundPanel) panel;
        //
        try {
            Image image = ImageIO.read(new File(path)); //"io/bg.jpg"
            bg.go(image);
        } catch (Exception ex) {
            // Will set the initial background
        }
    }
    
    public static void setPageBackground(JPanel panel, URL url) {
        //
        BackgroundPanel bg = (BackgroundPanel) panel;
        //
        try {
            Image image = ImageIO.read(url); //"io/bg.jpg"
            bg.go(image);
        } catch (Exception ex) {
            // Will set the initial background
        }
        //
    }

    public static boolean loggedIn() {
        return KUND_ID != null;
    }

    public static boolean isGuestUser() {
        return !(USER == null || !USER.contains("guest_"));
    }

    /**
     * // [2021-05-03] For the strings like below used for filling comboboxes.
     * So the "," is such Strings is the SEPARATOR
     * arbetskostnad;119;300;,byggnadsmaterial;120;6544;,cola 0.33
     * burk;132;10;,dill chips olw;128;29;,ekologiska gårds
     * chips;130;49.9;,fanta 0.33 burk;133;10;05.345.901,grill chips
     * olw;129;34.9;,hammare;117;99;,millersättning;121;544;,moped
     * bmw;126;36799;,moped vw;127;29589;
     */
    public static void onFlightReplaceComma(JLinkInvert jli, String val) {
        //
        JTextField jtf = (JTextField) jli;
        //
        if (val.contains(",")) {
            val = val.replaceAll(",", ".");
            jtf.setText(val);
        }
        //
    }

    public static String replaceComma(String text, boolean reverse) {
        if (reverse == false) {
            return text.replaceAll(",", "¤");
        } else {
            return text.replaceAll("¤", ",");
        }
    }

    public static String replaceColon(String text, boolean reverse) {
        if (reverse == false) {
            return text.replaceAll(":", "#");
        } else {
            return text.replaceAll("#", ":");
        }
    }

    public static String replacePlus(String text, boolean reverse) {
        if (reverse == false) {
            return text.replaceAll("\\+", "£");
        } else {
            return text.replaceAll("£", "+");
        }
    }

    public static String replaceAnd(String text, boolean reverse) {
        if (reverse == false) {
            return text.replaceAll("&", "~");
        } else {
            return text.replaceAll("~", "&");
        }
    }

//    public static String getShortName(String staticJaNej, String value) {
//        HashMap<String, String> map = JSon.JSONToHashMap(staticJaNej, false);
//        return getValNoNull(map.get(value));
//    }
//    
//    public static void main(String[] args) {
//        System.out.println("" + getShortName(DB.STATIC__JA_NEJ, "Ja"));
//    }
//
//    public static String getValNoNull(String value) {
//        if (value == null || value.isEmpty() || value.equals("null") || value.equals("NULL")) {
//            return "";
//        } else {
//            return value;
//        }
//    }
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

    public static void setLabelIconAndToolTip(JLabel label, String iconFileName, String toolTip) {
        label.setToolTipText(toolTip);
        label.setIcon(ICON.getImageIcon(iconFileName, 32, 32));
    }
    
    public static void setLabelIconAndToolTip(JLabel label, String iconFileName, int w, int h) {
        label.setIcon(ICON.getImageIcon(iconFileName, w, h));
    }

    public static void reminder_btn_adjustment(JButton btn, boolean forfallnaFakturorFinns) {
        //
        int wh = 32;
        //
        if (forfallnaFakturorFinns) {
            btn.setIcon(ICON.getImageIcon("bell_b.png", wh, wh));
        } else {
            btn.setIcon(ICON.getImageIcon("bell.png", wh, wh));
        }
    }

    public static void copy_btn_adjustment(JButton btn, boolean isOffert) {
        if (isOffert) {
            btn.setIcon(ICON.getImageIcon("swap.png", 32, 32));
            btn.setToolTipText("Omvandla till faktura eller kopiera");
        } else {
            btn.setIcon(ICON.getImageIcon("copy.png", 32, 32));
            btn.setToolTipText("Kopiera");
        }
    }

    public static void setEnabled(JComponent c, boolean enabled) {
        java.awt.EventQueue.invokeLater(() -> {
            c.setEnabled(enabled);
        });
    }

    public static void setVisible(JComponent c, boolean visible) {
        java.awt.EventQueue.invokeLater(() -> {
            c.setVisible(visible);
        });
    }
    
    /**
     *  This one is used only for the "Print" of the invoice
     */
    public static double round_double__whole_number(double rst) {
        return Double.parseDouble(String.format("%2.0f", rst).replace(",", "."));
    }

    public static double round_double(double rst) {
        return Double.parseDouble(String.format("%2.2f", rst).replace(",", "."));
    }
    
    public static void main(String[] args) {
        System.out.println("" + round_double(24.889));
    }

    public static int round_double_b(double rst) {
        return (int) Math.round(rst);
    }

    public static void showNotification(String message) {
        JLabel label = new JLabel(message);
        label.setFont(new Font(label.getFont().getFontName(), Font.BOLD, 14));
        JOptionPane.showMessageDialog(null, label);
    }

    public static boolean confirmWarning(String message) {
        JLabel label = new JLabel(message);
        label.setFont(new Font(label.getFont().getFontName(), Font.BOLD, 14));
        return JOptionPane.showConfirmDialog(null, label, "Bekräfta", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
    }

    public static boolean confirm(String message) {
        return JOptionPane.showConfirmDialog(null, message, "Bekräfta", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static String getDate_yyyy_MM_dd() {
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT_BASIC);
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

    public static String updatedOn() {
        return getDate_yyyy_MM_dd();
    }

    public static String getDateTime_yyyy_MM_dd() {
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT_BASIC + " HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

    public static String getDateCreated_special() {
        //OBS! Pay attention att ".SSS"
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT_BASIC + " HH.mm.ss.SSS");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

    public static boolean isDateValid(String date_yyyy_mm_dd) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_BASIC);
        sdf.setLenient(false);
        return sdf.parse(date_yyyy_mm_dd, new ParsePosition(0)) != null;
    }

    public static synchronized String get_date_time_plus_some_time_in_days(String date, long days) {
        //
        String date_format = DATE_FORMAT_BASIC;
        //
        if (days > 10) { // Yes it's needed [2020-09-21] Otherwise 2020-10-02 "+30days" = 2020-10-31
            days += 1;
        }
        //
        long time_to_plus = (long) 86400000 * days; // 86400000 = 1 day, 115200000 = 1 and 1/3 days
        long ms = HelpA.dateToMillisConverter3(date, date_format);
        long new_date_in_ms = ms + time_to_plus;
        String new_date = HelpA.millisToDateConverter("" + new_date_in_ms, date_format);
        //
        if (new_date.equals(date)) {
            new_date_in_ms += 28800000;
            return HelpA.millisToDateConverter("" + new_date_in_ms, date_format);
        }
        //
        return new_date;
        //
    }

    public static String get_date_time_minus_some_time_in_days(String date, long days) {
        String date_format = DATE_FORMAT_BASIC;
        long time_to_minus = 86400000 * Math.abs(days);
        long ms = dateToMillisConverter3(date, date_format);
        long new_date_in_ms = ms - time_to_minus;
        String new_date = millisToDateConverter("" + new_date_in_ms, date_format);
        return new_date;
    }

    public static int get_diff_in_days__two_dates(String date1, String date_format1, String date2, String date_format2) {
        return HelpA.get_diff_in_days__two_dates(date1, date_format1, date2, date_format2);
    }

    public static boolean compareDates_b(String date1, String date_format1, String date2, String date_format2) {
        //
        long ms_date1 = dateToMillisConverter3(date1, date_format1);
        long ms_date2 = dateToMillisConverter3(date2, date_format2);
        //
        if (ms_date2 >= ms_date1) {
            return true;
        } else {
            return false;
        }
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
            copyAndConvertImage(path);
//            Files.copy(new File(path).toPath(), new File(LOGO_PATH()).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(GP_BUH.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        //
        //
        //
    }

    public static Dimension calculate_w_h__proportionalScaling(String path) {
        //
        if (path == null || path.isEmpty() || HelpA.file_exists(new File(path)) == false) {
            return new Dimension(0, 0);
        }
        //
        int MIN_WIDTH = 120;
//        int MAX_WIDTH = 180;
//        int MAX_HEIGHT = 70;
        //
        int MAX_WIDTH = 200;
        int MAX_HEIGHT = 85;
        //
        BufferedImage img;
        //
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException ex) {
            Logger.getLogger(GP_BUH.class.getName()).log(Level.SEVERE, null, ex);
            return new Dimension(0, 0);
        }
        //
        int w_orig = img.getWidth();
        int h_orig = img.getHeight();
        //
        if (w_orig < MIN_WIDTH) {
            HelpA.showNotification(LANG.LOGOTYP_TO_SMALL("" + MIN_WIDTH, "" + w_orig));
            return null;
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
        if (w_orig > MAX_WIDTH || h_orig > MAX_HEIGHT) {
            return proportionalRescaling(wh_proportion, w_orig, h_orig, MAX_WIDTH, MAX_HEIGHT);
        } else {
            return new Dimension(w_orig, h_orig);
        }
        //
//        if (w_orig > MAX_WIDTH) {
//            //
//            w_new = MAX_WIDTH;
//            //
//            if (h_orig > MAX_HEIGHT) {
//                h_new = MAX_HEIGHT; // This should be made more flexible***********[2020-09-04]
//            } else {
//                h_new = (int) (w_new / wh_proportion);
//            }
//            //
//            //
//        } else {
//            w_new = w_orig;
//            h_new = h_orig;
//        }
//        //
//        return new Dimension(w_new, h_new);
        //
    }

    private static Dimension proportionalRescaling(double wh_proportion, int w_orig, int h_orig, int max_w, int max_h) {
        //
        double MAX_WIDTH = max_w;
        double MAX_HEIGHT = max_h;
        //
        double w = w_orig;
        double h = h_orig;
        //
        while (w > MAX_WIDTH || h > MAX_HEIGHT) {
            w -= wh_proportion;
            h -= 1;
        }
        //
        System.out.println("");
        //
        return new Dimension((int)w, (int)h);
    }

    /**
     *
     * @param path
     * @throws IOException
     */
    private static void copyAndConvertImage(String path) throws IOException {
        //
        if (path == null || path.isEmpty()) {
            return;
        }
        //
        int MIN_WIDTH = 120;
        //
        BufferedImage img = ImageIO.read(new File(path));
        //
        int w_orig = img.getWidth();
        int h_orig = img.getHeight();
        //
        if (w_orig < MIN_WIDTH) {
            showNotification(LANG.LOGOTYP_TO_SMALL("" + MIN_WIDTH, "" + w_orig));
            return;
        }
        //
        Double fileSizeMb = HelpA.get_file_size_mb(path);
        //
        if (fileSizeMb > 3) {
            showNotification(LANG.LOGOTYP_FILE_SIZE_TO_BIG("" + fileSizeMb, VERSION));
            return;
        }
        //
        File f = new File(path);
        //
        //
        // For ".png" below:
        Thumbnails.of(f)
                //                .scale(1f)
                .size(w_orig, h_orig)
                .imageType(BufferedImage.TYPE_INT_ARGB) // should make the quality better
                .outputFormat("png") // Not needed in fact..
                .toFile(new File(GP_BUH.LOGO_PATH()));
        //
        //
        //For the ".jpeg, jpg" below, not working good
//         Thumbnails.of(f)
//                .size(w_new, h_new)
//                .outputQuality(0.5F) // OBS! Important! Working with .jpg only!
//                .toFile(new File(GP_BUH.LOGO_PATH())); 
        //
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

    public static int countForfallnaFakturorJTable(JTable table, String colName) {
        //
        int forfallna = 0;
        //
        for (int x = 0; x < table.getRowCount(); x++) {
            //
            int col = HelpA.getColByName(table, colName);
            //
            String val = (String) table.getValueAt(x, col);
            //
            if (val == null || val.isEmpty() || val.equals("null")) {
                continue;
            }
            //
            String dateNow = getDate_yyyy_MM_dd();
            boolean forfallen = HelpA.compareDates(dateNow, DATE_FORMAT_BASIC, val, DATE_FORMAT_BASIC);
            boolean isBetald = HelpA.getValueGivenRow(table, x, InvoiceB.TABLE_ALL_INVOICES__BETALD).contains(DB.STATIC__YES);
            boolean isMakulerad = HelpA.getValueGivenRow(table, x, InvoiceB.TABLE_ALL_INVOICES__MAKULERAD).equals(DB.STATIC__YES);
            boolean isFakturaTypeNormal = HelpA.getValueGivenRow(table, x, InvoiceB.TABLE_ALL_INVOICES__FAKTURA_TYP).equals(DB.STATIC__FAKTURA_TYPE_NORMAL);
            //
            if (forfallen && isFakturaTypeNormal && isBetald == false && isMakulerad == false) {
                forfallna++;
            }
            //
        }
        //
        return forfallna;
        //
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
