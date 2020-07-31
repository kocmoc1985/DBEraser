/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTableInvert.JLinkInvert;
import forall.HelpA;
import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
            jtf.setForeground(getJTextFieldInitialColor());
            jli.setValidated(true);
            return true;
        } else {
            jtf.setForeground(Color.RED);
            jli.setValidated(false);
            return false;
        }
        //
    }

    public static boolean validateOrgnr(String orgnr) {
        return validate_(ORGNR, orgnr);
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
        boolean validated = validate_(pattern, val);
        //
        JTextField jtf = (JTextField) jli;
        //
        if (validated) {
            jtf.setForeground(getJTextFieldInitialColor());
            jli.setValidated(true);
            return true;
        } else {
            jtf.setForeground(Color.RED);
            jli.setValidated(false);
            return false;
        }
        //
    }

    public static boolean validateDate(JLinkInvert jli) {
        //
        String val = jli.getValue();
        //
        boolean validated = validate_(DATE_YYYY_MM_DD, val);
        //
        JTextField jtf = (JTextField) jli;
        //
        if (validated && HelpA.isDateValid(val)) {
            jtf.setForeground(getJTextFieldInitialColor());
            jli.setValidated(true);
            return true;
        } else {
            jtf.setForeground(Color.RED);
            jli.setValidated(false);
            return false;
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
