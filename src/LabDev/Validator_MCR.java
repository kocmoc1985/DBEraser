/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import BuhInvoice.*;
import BuhInvoice.sec.LANG;
import MCRecipe.Lang.MSG;
import MyObjectTableInvert.HeaderInvert;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.JTextFieldInvert;
import MyObjectTableInvert.TableRowInvert;
import forall.GP;
import forall.HelpA_;
import static forall.HelpA_.getColByName;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author KOCMOC
 */
public class Validator_MCR {

    // SWE VAT: SE + XXXXXXXXXX + 01
    private static final Pattern ORGNR = Pattern.compile("\\d{6}-\\d{4}");
    private static final Pattern DATE_YYYY_MM_DD = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
    private static final Pattern DATE_DD_MM_YYYY = Pattern.compile("\\d{2}.\\d{2}.\\d{4}");
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
            //
            setValidated(jli);
            //
            return true;
        } else {
            //
            setNotValidated(jli, Color.blue);
            //
            setToolTip(jli, MSG.MSG_3_2() + " " + length);
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
        unsetToolTip(jli);
        //
        TableRowInvert tri = jli.getParentObj();
        HeaderInvert hi = tri.getHeaderInvert();
        hi.setValidated();
        //
        jli.setValidated(true);
        return true;
    }

    private static boolean setNotValidated(JLinkInvert jli) {
        TableRowInvert tri = jli.getParentObj();
        HeaderInvert hi = tri.getHeaderInvert();
        hi.setNotValidated();
        jli.setValidated(false);
        return false;
    }

    private static boolean setNotValidated(JLinkInvert jli, Color color) {
        TableRowInvert tri = jli.getParentObj();
        HeaderInvert hi = tri.getHeaderInvert();
        hi.setNotValidated(color);
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
        if (val.isEmpty()) {
            return setValidated(jli);
        }
        //
        if (HelpA_.isNumber(val)) {
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
        boolean validated;
        //
        if (GP.IS_DATE_FORMAT_DE) {
            validated = validate_(DATE_DD_MM_YYYY, val);
        } else {
            validated = validate_(DATE_YYYY_MM_DD, val);
        }
        //
        if (validated) { // HelpA_.isDateValid(val)
            return setValidated(jli);
        } else {
            setToolTip(jli, "Date format not correct");
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