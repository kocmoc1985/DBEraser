/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import BuhInvoice.DB;
import BuhInvoice.GP_BUH;
import forall.HelpA;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MCREMOTE
 */
public class IO {

    public final static String IO_DIR = "la/";

    //
    static {
        HelpA.create_dir_if_missing(IO_DIR);
    }
    

    public static final boolean exist(String fileName) {
        return HelpA.file_exists(IO_DIR + fileName);
    }

    public static final String get_universal(String DB__PARAMETER, String kundFakturaId) {
        return DB__PARAMETER + "_" + kundFakturaId;
    }

    //
    public static final String getErReferens(String kundFakturaId) {
        return DB.BUH_FAKTURA__ER_REFERENS + "_" + kundFakturaId;
    }

    public static final String _get(String DBparameter) {
        return IO_DIR + DBparameter;
    }

    public static final void writeToFile(String fileName, String value) {
        try {
            HelpA.writeToFile(IO_DIR + fileName, value, false);
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String loadLastEntered(String fileName, String defaultValue) {
        return HelpA.loadLastEntered(IO_DIR + fileName, defaultValue);
    }

    public static boolean delete(String fileName) {
        File f = new File(IO_DIR + fileName);
        return f.delete();
    }

    public static boolean saveSMTP(Object obj) {
        try {
            objectToFile(GP_BUH.SMTP_PATH(), obj);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static SMTP loadSMTP() {
        try {
            return (SMTP) fileToObject(GP_BUH.SMTP_PATH());
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }

    public static boolean deleteSMTP() {
        return delete(GP_BUH.SMTP_PATH());
    }

    public static String loadReminderMsg() {
        try {
            return (String) fileToObject(IO_DIR + "remindermsg");
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }

    public static String loadRutMsg() {
        try {
            return (String) fileToObject(IO_DIR + "rutrotmsg");
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }

    public static boolean saveRutMsg(Object obj) {
        try {
            objectToFile(IO_DIR + "rutrotmsg", obj);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean saveReminderMsg(Object obj) {
        try {
            objectToFile(IO_DIR + "remindermsg", obj);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static void deleteRutMsg() {
        delete("rutrotmsg");
    }

    public static void deleteReminderMsg() {
        delete("remindermsg");
    }

    private static void objectToFile(String fileName, Object obj) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(obj);
        oos.close();
    }

    private static Object fileToObject(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fas = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fas);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }

}
