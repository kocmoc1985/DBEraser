/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import BuhInvoice.DB;
import forall.HelpA;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MCREMOTE
 */
public class IO {

    private final static String LAST_INPUT_DIR = "io/";

    //
    static {
        HelpA.create_dir_if_missing(LAST_INPUT_DIR);
    }

    //
    public static final String getErReferens(String kundFakturaId) {
        return DB.BUH_FAKTURA__ER_REFERENS + "_" + kundFakturaId;
    }

    public static final String _get(String DBparameter) {
        return LAST_INPUT_DIR + DBparameter;
    }

    public static final void writeToFile(String fileName, String value) {
        try {
            HelpA.writeToFile(LAST_INPUT_DIR + fileName, value, false);
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String loadLastEntered(String fileName, String defaultValue) {
        return HelpA.loadLastEntered(LAST_INPUT_DIR + fileName, defaultValue);
    }

    public static void delete(String fileName) {
        File f = new File(LAST_INPUT_DIR + fileName);
        f.delete();
    }

}
