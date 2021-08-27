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
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author MCREMOTE
 */
public class IO {

    public final static String IO_DIR = "la/";

    // Without "/"
    public final static String getIO_DIR() {
        int a = IO_DIR.length() - 1;
        return IO_DIR.substring(0, a);
    }

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
    
    public static final void writeToFile_with_encrypt(String fileName, String value) {
        //
        String encr_val = encrypt(value, "qEp4lylfuMQ=");
        //
        try {
            HelpA.writeToFile(IO_DIR + fileName, encr_val, false);
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String loadLastEntered_decrypt(String fileName, String defaultValue) {
        String val = HelpA.loadLastEntered(IO_DIR + fileName, defaultValue);
        return decrypt(val, "qEp4lylfuMQ=");
    }
    
    //==========================================================================
    //==========================================================================
    //==========================================================================
    
    private static SecretKeySpec secretKeySpec;
    private static byte[] key;
    
    private static String encrypt(String strToEncrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    private static String decrypt(String strToDecrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
    
    private static void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1"); // SHA-1 // OBS! THINK TWICE BEFORE CHANGING IT
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKeySpec = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    //==========================================================================
    //==========================================================================
    //==========================================================================

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
