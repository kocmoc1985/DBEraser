/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.JTextFieldInvert;
import forall.HelpA;
import static forall.HelpA.getColByName;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author KOCMOC
 */
public class Validator {

    // SWE VAT: SE + XXXXXXXXXX + 01
    private static final Pattern ORGNR = Pattern.compile("\\d{6}-\\d{4}");
    private static final Pattern DATE_YYYY_MM_DD = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
    private static final Pattern EMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /**
     * [2020-08-19]
     *
     * @param table
     * @param jli
     * @param colName
     * @return
     */
    public static boolean checkIfExistInJTable(JTable table, JLinkInvert jli, String colName) {
        //
        JTextFieldInvert jtfi = (JTextFieldInvert) jli;
        //
        String valToCheck = jtfi.getText();
        //
        if (valToCheck.isEmpty() || table.getRowCount() == 0) {
            return setValidated(jli);
        }
        //
        for (int x = 0; x < table.getRowCount(); x++) {
            //
            int col = getColByName(table, colName);
            //
            String val = (String) table.getValueAt(x, col);
            //
            if (val.equals(valToCheck)) {
                return setNotValidated(jli, Color.orange);
            }
            //
        }
        return setValidated(jli);
    }

    /**
     * [2020-07-31] It's ok to use this method, but make note that it consumes
     * much traffic
     *
     * @deprecated
     * @param bim
     * @param jli
     * @param colName
     * @param tableName
     * @return
     */
    public static boolean checkIfExistInDB(BUH_INVOICE_MAIN bim, JLinkInvert jli, String colName, String tableName) {
        //
        JTextFieldInvert jtfi = (JTextFieldInvert) jli;
        //
        String val = jtfi.getText();
        //
        if (val.isEmpty()) {
            return setValidated(jli);
        }
        //
        String json = bim.getExist(colName, val, tableName);
        //
        String exist;
        //
        try {
            //
            exist = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN, DB.PHP_FUNC_EXIST, json);
            //
            System.out.println("VALUE: " + val + " exists: " + exist);
            //
        } catch (Exception ex) {
            Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
            exist = "0";
        }
        //
        int exist_ = Integer.parseInt(exist.trim());
        //
        if (exist_ == 0) {
            return setValidated(jli);
        } else {
            return setNotValidated(jli, Color.ORANGE);
        }
    }

    public static void validateMaxInputLength(JLinkInvert jli, int length) {
        //
        String val = jli.getValue();
        //
        if (val.length() <= length) {
            setValidated(jli);
        } else {
            setNotValidated(jli, new Color(140, 218, 255)); // 223, 243, 248
        }
    }

    public static boolean validatePercentInput(JLinkInvert jli) {
        //
        String val = jli.getValue();
        //
        try {
            //
            double percent = Double.parseDouble(val);
            //
            if (percent >= 0 && percent < 100) {
                return setValidated(jli);
            }
            //
        } catch (Exception ex) {
            //
        }
        //
        return setNotValidated(jli);
    }

    private static boolean setValidated(JLinkInvert jli) {
        //
        JTextFieldInvert jtf = (JTextFieldInvert) jli;
        //
        jtf.setSaveEmptyStringValue();
        //
//        String val = jtf.getValue();
//        val = val.replaceAll(":", ";");
//        jtf.setValue(val);
        //
        jtf.setBackground(Color.WHITE);
        jli.setValidated(true);
        return true;
    }

    private static boolean setNotValidated(JLinkInvert jli) {
        JTextField jtf = (JTextField) jli;
        jtf.setBackground(Color.RED);
        jli.setValidated(false);
        return false;
    }

    private static boolean setNotValidated(JLinkInvert jli, Color color) {
        JTextField jtf = (JTextField) jli;
        jtf.setBackground(color);
        jli.setValidated(false);
        return false;
    }

    /**
     * Verify input of digits/numbers
     *
     * @param jli
     * @return
     */
    public static boolean validateDigitalInput(JLinkInvert jli) {
        //
        String val = jli.getValue();
        //
        JTextField jtf = (JTextField) jli;
        //
        if (val.contains(",")) {
            val = val.replaceAll(",", ".");
            jtf.setText(val);
        }
        //
        if (HelpA.isNumber(val)) {
            return setValidated(jli);
        } else {
            return setNotValidated(jli);
        }
        //
    }

    public static boolean validateOrgnr(JLinkInvert jli) {
        return validate(jli, ORGNR);
    }

    public static boolean validateEmail(JLinkInvert jli) {
        return validate(jli, EMAIL);
    }

    public static boolean validate(JLinkInvert jli, Pattern pattern) {
        //
        String val = jli.getValue();
        //
        if (val.isEmpty()) {
            return setValidated(jli);
        }
        //
        boolean validated = validate_(pattern, val);
        //
        if (validated) {
            return setValidated(jli);
        } else {
            return setNotValidated(jli);
        }
        //
    }

    public static boolean validateDate(JLinkInvert jli) {
        //
        String val = jli.getValue();
        //
        boolean validated = validate_(DATE_YYYY_MM_DD, val);
        //
        if (validated && HelpA.isDateValid(val)) {
            return setValidated(jli);
        } else {
            return setNotValidated(jli);
        }
        //
    }

    private static boolean validate_(Pattern pattern, String stringToCheck) {
        Matcher matcher = pattern.matcher(stringToCheck);
        return matcher.matches();
    }

    private static Color getJTextFieldInitialColor() {
        JTextField field = new JTextField();
        return field.getForeground();
    }
}
