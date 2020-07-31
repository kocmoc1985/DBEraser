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

    private static final Pattern DATE_YYYY_MM_DD = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

    public static boolean validateNumberInput(JLinkInvert jli) {
        //
        String val = jli.getValue();
        //
        JTextField jtf = (JTextField) jli;
        //
        if(val.contains(",")){
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

    public static boolean validateDate(JLinkInvert jli) {
        //
        String val = jli.getValue();
        //
        boolean validated = validate(DATE_YYYY_MM_DD, val);
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

    private static boolean validate(Pattern pattern, String stringToCheck) {
        Matcher matcher = pattern.matcher(stringToCheck);
        return matcher.find();
    }

    private static Color getJTextFieldInitialColor() {
        JTextField field = new JTextField();
        return field.getForeground();
    }
}
