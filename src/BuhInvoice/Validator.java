/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.LANG;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.JTextFieldInvert;
import forall.HelpA;
import static forall.HelpA.getColByName;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author KOCMOC
 */
public class Validator {

    // SWE VAT: SE + XXXXXXXXXX + 01
    private static final Pattern ORGNR = Pattern.compile("\\d{6}-\\d{4}");
    private static final Pattern PNR = Pattern.compile("\\d{6}-\\d{4}");
    private static final Pattern DATE_YYYY_MM_DD = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
    public static final Pattern EMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

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
        if (colName.equals(CustomersA_.TABLE_FAKTURA_KUNDER__KUND_NAMN)) {
            // 2021-05-03 // [#AUTOMATIC-COMMA-WITH-POINT-REPLACEMENT--ARTICLE-NAME#][THIS WAS HERE THE PROBLEM STARTED]
            GP_BUH.onFlightReplaceComma(jli, valToCheck);
            //
        }
        //
        if (valToCheck == null || valToCheck.isEmpty() || table.getRowCount() == 0) {
            return setValidated(jli);
        }
        //
        for (int x = 0; x < table.getRowCount(); x++) {
            //
            int col = getColByName(table, colName);
            //
            String val = (String) table.getValueAt(x, col);
            //
            if (val == null) {
                return setValidated(jli);
            }
            //
            if (val.equals(valToCheck)) {
                setToolTip(jli, LANG.MSG_14_2);
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
    public static boolean checkIfExistInDB(LAFakturering bim, JLinkInvert jli, String colName, String tableName) {
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

    /**
     * [2020-10-01]
     *
     * @param box
     */
    public static void validateJComboInput(JComboBox box) {
        if (box.getSelectedIndex() == -1) {
            // OBS! The border is set here and not from "setNotValidater()" because
            // setting border is the only method that works in this situation
            box.setBorder(BorderFactory.createLineBorder(Color.red, 3));
            setNotValidated((JLinkInvert) box);
        } else {
            box.setBorder(null);
            setValidated((JLinkInvert) box);
        }
    }

    private static void unsetToolTip(JLinkInvert jli) {
        if (jli instanceof JComponent) {
            JComponent jc = (JComponent) jli;
            jc.setToolTipText(null);
        }
    }

    private static void setToolTip(JLinkInvert jli, String message) {
        if (jli instanceof JComponent) {
            JComponent jc = (JComponent) jli;
            jc.setToolTipText(message);
        }
    }

    public static boolean validateMaxInputLength(JLinkInvert jli, int length) {
        //
        String val = jli.getValue();
        //
        if (val.length() <= length) {
            setValidated(jli);
            //
            return true;
        } else {
            //
            setNotValidated(jli, new Color(140, 218, 255)); // 223, 243, 248
            //
            setToolTip(jli, LANG.MSG_14 + " " + length);
            //
            return false;
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

    public static void resetValidation(JComponent jc) {
        if (jc instanceof JLinkInvert) {
            JLinkInvert jli = (JLinkInvert) jc;
            setValidated(jli);
        }
    }

    private static boolean setValidated(JLinkInvert jli) {
        //
        if (jli instanceof JTextFieldInvert) {
            //
            JTextFieldInvert jtf = (JTextFieldInvert) jli;
            //
            jtf.setSaveEmptyStringValue();
            //
            jtf.setSaveEmptyNumber();
            //
        }
        //
        unsetToolTip(jli);
        //
        JComponent c = (JComponent) jli;
        //
        c.setBackground(Color.WHITE);
        jli.setValidated(true);
        return true;
    }

    private static boolean setNotValidated(JLinkInvert jli) {
        JComponent c = (JComponent) jli;
        c.setBackground(Color.RED);
        jli.setValidated(false);
        return false;
    }

    private static boolean setNotValidated(JLinkInvert jli, Color color) {
        JComponent c = (JComponent) jli;
        c.setBackground(color);
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
        GP_BUH.onFlightReplaceComma(jli, val); // 2021-05-03
        //
        if (val.isEmpty()) {
            return setValidated(jli);
        }
        //
        if (HelpA.isNumber(val)) {
            return setValidated(jli);
        } else {
            return setNotValidated(jli);
        }
        //
    }

    public static boolean validatePnr(JLinkInvert jli) {
        return validate(jli, PNR);
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
        if (validated && GP_BUH.isDateValid(val)) {
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
