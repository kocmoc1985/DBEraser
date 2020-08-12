/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.JTextFieldInvert;
import forall.HelpA;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
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
     * [2020-07-31]
     * 
     * @param bim
     * @param jli
     * @param colName
     * @param tableName
     * @return 
     */
    public static boolean checkIfExistInDB(BUH_INVOICE_MAIN bim,JLinkInvert jli, String colName,String tableName) {
        //
        JTextFieldInvert jtfi = (JTextFieldInvert) jli;
        //
        String val = jtfi.getText();
        //
        if(val.isEmpty()){
            jtfi.setForeground(getJTextFieldInitialColor());
            return false;
        }
        //
        String json = bim.getExist(colName, val, tableName);
        //
        String exist;
        //
        try {
            //
            exist = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_EXIST, json));
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
            jtfi.setForeground(getJTextFieldInitialColor());
            jli.setValidated(true);
            return true;
        } else {
            jtfi.setForeground(Color.ORANGE);
            jli.setValidated(false);
            return false;
        }
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