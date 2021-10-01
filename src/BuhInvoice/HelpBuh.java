/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import static BuhInvoice.JSon.JSONToHashMap;
import BuhInvoice.sec.Backup_All;
import BuhInvoice.sec.Backup_Invoice;
import BuhInvoice.sec.EmailSendingStatus;
import BuhInvoice.sec.HttpResponce;
import BuhInvoice.sec.IO;
import BuhInvoice.sec.LANG;
import BuhInvoice.sec.SMTP;
import forall.HelpA;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.JLabel;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author MCREMOTE
 */
public class HelpBuh {

    // I'am now not using "save to desktop" both for win & mac. 
    // Also remember that for win10 it can also be a trouble saving to desktop
    // OBS! No Desktop icon creation for the MAC-OS
    public final static boolean IS_MAC_OS = false;
    //
    private final static boolean IS_DISTRIBUTION = false;
    //
    public static boolean ERR_OUTPUT_TO_FILE__DISTRIBUTED = true; // Is "false" if IS_DISTRIBUTION = true
    private static boolean HTTPS = false;
    private static boolean DOMAIN_LA = true; // false = "mixcont.com", true = "lafakturering.se"
    //Yes you can use test scripts but not use the "test-db"
    public static boolean USE_TEST_DB = true; // [#TEST-DB#] 
    public static boolean USE_TEST_SCRIPTS = true; // [#TEST-SCRIPTS#] - folder "php-test" on FTP
    //
    //
    //
    public static final String CURRENCY__EUR = "EUR";
    public static boolean LANG_ENG = false; // DON'T CHANGE MANUALLY
    public static boolean FOREIGN_CUSTOMER = false; // DON'T CHANGE MANUALLY
    public static boolean EU_CUSTOMER = false; // DON'T CHANGE MANUALLY
    public static boolean COMPANY_MIXCONT = false; // DON'T CHANGE MANUALLY

    public static void defineIfCompanyMixcont() {
        //
        if (GP_BUH.CUSTOMER_COMPANY_NAME.toLowerCase().contains("mixcont") == false) {
            return;
        }
        //
        COMPANY_MIXCONT = true;
        //
    }

    public static void defineForeignCustomers(String fakturaKundKategori) {
        //[#KUND-KATEGORI-CONDITION#][#EUR-SEK#]
        if (fakturaKundKategori.equals(DB.STATIC__KUND__KATEGORI__EU_EUR)) { // KATEGORI B: EU Invoices like QEW and Fedmog
            LANG_ENG = true;
            FOREIGN_CUSTOMER = true;
            EU_CUSTOMER = true;
        } else if (fakturaKundKategori.equals(DB.STATIC__KUND__KATEGORI__UTL_EUR)) {
            LANG_ENG = true;
            FOREIGN_CUSTOMER = true;
            EU_CUSTOMER = false;
        } else {
            LANG_ENG = false;
            FOREIGN_CUSTOMER = false;
            EU_CUSTOMER = false;
        }

    }

    //
    static {
        //
        if (IS_DISTRIBUTION) {
            //
            System.setOut(new PrintStream(new OutputStream() {
                @Override
                public void write(int b) {
                    // nothing / DISABLE OF System.out.println()
                }
            }));
            //
            DOMAIN_LA = true;
            HTTPS = true;
            ERR_OUTPUT_TO_FILE__DISTRIBUTED = false;
            //
            USE_TEST_DB = false;
            USE_TEST_SCRIPTS = false;
            //
            GP_BUH.TRACKING_TOOL_TIP_ENABLED = false;
            GP_BUH.CUSTOMER_MODE = true;
            //
        }
        //
    }

    /**
     * @deprecated @throws Exception
     */
    public static String executePHP__prev(String phpScriptName, String phpFunctionName, String json) throws Exception {
        String url = buildUrl(phpScriptName, phpFunctionName, json);
        return http_get_content_post(url);
    }

    /**
     * [2020-09-07] OBS! All calls to PHP do bypass this method, verified on
     * [2021-03-21]
     *
     * @param phpScriptName - example: "_http_buh"
     * @param phpFunctionName - example: "delete_entry"
     * @param json
     * @return
     * @throws Exception
     */
    public static String executePHP(String phpScriptName, String phpFunctionName, String json) throws Exception {
        //
        // OBS! Prev-Last "false" means don't replace special chars - very important [2020-10-11]
        HashMap<String, String> map = JSONToHashMap(json, false, 1, false, false, null);
        //
        map.put(DB.BUH_LICENS__USER, GP_BUH.USER); // [#SEQURITY#] Required by the PHP (_http_buh.php->validate(..))
        map.put(DB.BUH_LICENS__PASS, GP_BUH.PASS); // [#SEQURITY#]
        //
        // [#TEST-DB#]
        if (USE_TEST_DB) {
            map.put("use_test_db", "1");
        } else {
            map.put("use_test_db", "0");
        }
        //
        String url = buildUrl(phpScriptName, phpFunctionName, JSon.hashMapToJSON(map));
        //
        if (HTTPS) {
            return https_get_content_post(url);
        } else {
            return http_get_content_post(url);
        }
        //
    }

    /**
     * OBS! It does require login
     *
     * @return
     */
    public static HashMap<String, String> get_constants() {
        //[#BUH-CONSTANTS#]
        //In fact the "where" is fake, i use it just because the select statement uses a "where"
        String json = LAFakturering.getSELECT_(DB.BUH_CONSTANTS__ID, "0"); // 
        //
        HashMap<String, String> constants = new HashMap<>();
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_GET_CONSTANTS, json);
            //
            if (json_str_return.equals("V_ERR_0")) {
                return null;
            }
            //
            ArrayList<HashMap<String, String>> entries = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            for (HashMap<String, String> val : entries) {
                System.out.println("out: " + val);
                constants.put(val.get("name"), val.get("val").replaceAll("\\*", ";"));
            }
            //
            //
            return constants;
            //
        } catch (Exception ex) {
            Logger.getLogger(HelpBuh.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        //
    }

    private static ArrayList<Backup_Invoice> backup_4_5() { // Backup of all invoices with all articles
        //
        String json = LAFakturering.getSELECT_(DB.BUH_FAKTURA__KUNDID__, "777");
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ALL__SIMPLE, json);
            //
            ArrayList<HashMap<String, String>> allInvoices = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            System.out.println("");
            //
            ArrayList<Backup_Invoice> allInvoicesList = new ArrayList<>();
            //
            for (HashMap<String, String> invoice : allInvoices) {
                //
                String fakturaId = invoice.get("fakturaId");
                //
                String json_ = LAFakturering.getSELECT_doubleWhere_(DB.BUH_F_ARTIKEL__KUND_ID, "777", DB.BUH_F_ARTIKEL__FAKTURAID, fakturaId);
                //
                String json_str_return_ = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                        DB.PHP_FUNC_PARAM_GET_FAKTURA_ARTICLES__BUH_F_ARTIKEL, json_);
                //
                if (json_str_return_.equals(DB.PHP_SCRIPT_RETURN_EMPTY)) { // this value='empty' is returned by PHP script
                    continue;
                }
                //
                ArrayList<HashMap<String, String>> articles = JSon.phpJsonResponseToHashMap(json_str_return_);
                //
                Backup_Invoice bie = new Backup_Invoice(0, invoice, articles);
                //
                allInvoicesList.add(bie);
                //
            }
            //
            return allInvoicesList;
            //
//            HelpA.objectToFile("BACKUP_4_5", allInvoicesList);
            //
        } catch (Exception ex) {
            Logger.getLogger(HelpBuh.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private static ArrayList<HashMap<String, String>> backup_single_table(String phpFunc) {
        //
        String json = LAFakturering.getSELECT_(DB.BUH_KUND__ID, "777");
        //
        try {
            //
            String json_str_return = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    phpFunc, json);
            //
            ArrayList<HashMap<String, String>> entries = JSon.phpJsonResponseToHashMap(json_str_return);
            //
            return entries;
            //
        } catch (Exception ex) {
            Logger.getLogger(HelpBuh.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private static void backup() {
        //
        ArrayList<HashMap<String, String>> buh_kund__1 = backup_single_table(DB.PHP_FUNC_PARAM_GET_FORETAG_DATA);
        //
        ArrayList<HashMap<String, String>> buh_faktura_artikel__2 = backup_single_table(DB.PHP_FUNC_PARAM_GET_KUND_ARTICLES_ALL_DATA);
        //
        ArrayList<HashMap<String, String>> buh_faktura_kund__3 = backup_single_table(DB.PHP_FUNC_PARAM_GET_FAKTURA_KUNDER_ALL_DATA_SIMPLE);
        //
        ArrayList<Backup_Invoice> allInvoicesList__4_5 = backup_4_5();
        //
        ArrayList<HashMap<String, String>> buh_faktura_inbet__6 = backup_single_table(DB.PHP_FUNC_PARAM__GET_FAKTURA_INBET_SIMPLE);
        //
        ArrayList<HashMap<String, String>> buh_address__7 = backup_single_table(DB.PHP_FUNC_PARAM_GET_FAKTURA_KUND_ADDRESSES_SIMPLE);
        //
        Backup_All backup_All = new Backup_All(buh_kund__1, buh_faktura_artikel__2, buh_faktura_kund__3, allInvoicesList__4_5, buh_faktura_inbet__6, buh_address__7);
        //
        System.out.println("");
        //
    }

    public static void main(String[] args) {
        //
//        checkUpdates(null);
        //
//        GP_BUH.USER = "ask@mixcont.com";
//        GP_BUH.PASS = "mixcont4765";
        //
        GP_BUH.USER = "andrej.brassas@gmail.com";
        GP_BUH.PASS = "KpxHs5jufF";
        //
        backup();
        //
//        GP_BUH.USER = "kocmoc1985@gmail.com";
//        GP_BUH.PASS = "dbJztp1PR9";
        //
//        createAccountPHP_existing_customer("1");
        //
//        test__sendEmailWithAttachment();
        //
//        createAccountPHP_main("andrej.brassas@gmail.com", "BuhInvoice", "556251-6806");
        //
        //
//        deleteCustomer_a("25", "Vxuw6lpMzF");
        //
        //
//        restorePwd("andrej.brassas@gmail.com");
        //
//        buh_faktura_rut_person__test_insert("1");
        //    
//        deleteCustomer_b("dbJztp1PR9"); //dbJztp1PR9
        //
//        test__uploadFile();
        //
//        get_constants();
        //
    }

    public static void update(String json) {
        //
        try {
            //
            executePHP(DB.PHP_SCRIPT_MAIN, DB.PHP_FUNC_UPDATE_AUTO, json);
            //
        } catch (Exception ex) {
            Logger.getLogger(Faktura_Entry_Update.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
    }

    public static boolean OBLIGATORY_UPDATE__INSTALL_NEW_REQUIRED = false;

    /**
     * JSON: {"version":"versionVersion"}
     *
     * @return
     */
    public static void checkUpdates(JLabel label) {
        //
        HashMap<String, String> map = new HashMap();
        //
        String version = "";
        String obligatory = "";
        //
        try {
            //
            String responce = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_GET_NEWEST_AVAILABLE_VERSION, JSon.hashMapToJSON(map));
            //
            ArrayList<HashMap<String, String>> version__ = JSon.phpJsonResponseToHashMap(responce);
            //
            version = version__.get(0).get("version");
            //
            obligatory = version__.get(0).get("obligatory");
            //
        } catch (Exception ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
            obligatory = "false";
        }
        //
        System.out.println("" + version);
        //
        boolean newVerAvailable = Integer.parseInt(version) > GP_BUH.VERSION_INTEGER;
        //
        if (label != null && newVerAvailable) {
            label.setText("Ny version är tillgänglig på " + GP_BUH.LA_WEB_ADDR);
        }
        //
        boolean oblig = Boolean.parseBoolean(obligatory);
        //
        if (newVerAvailable && obligatory != null && oblig) {
            //[#OBLIGATORY-UPDATE#]
            OBLIGATORY_UPDATE__INSTALL_NEW_REQUIRED = Boolean.parseBoolean(obligatory);
        }
        //
    }

    public static HttpResponce shareAccount(String userEmailToShareWith) {
        //
        HashMap<String, String> map = new HashMap();
        //
        map.put("share_with_user", userEmailToShareWith);
        map.put(DB.BUH_KUND__NAMN, GP_BUH.CUSTOMER_COMPANY_NAME);
        //
        // It does not make sence sending this parameters as they are the same as for master account
//        map.put(DB.BUH_LICENS__MAC_ADDR, HelpA.getMacAddress());
//        map.put(DB.BUH_LICENS__OS, HelpA.getOperatingSystem());
//        map.put(DB.BUH_LICENS__LANG, HelpA.getUserLanguge());
//        map.put(DB.BUH_LICENS__PC_USER_NAME, HelpA.getUserName());
//        map.put(DB.BUH_LICENS__JAVA, HelpA.getJavaVersionAndBitAndVendor_b());
        //
        try {
            //
            String responce = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_SHARE_ACCOUNT, JSon.hashMapToJSON(map));
            //
            return new HttpResponce(responce, LANG.MSG_20_0);
            //
        } catch (Exception ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
            return new HttpResponce(HttpResponce.GENERAL_ERR_0, LANG.MSG_20_0);
        }
        //
        //
        //
        //
    }

    public static HttpResponce restorePwd(String emailUserName) {
        //
        HashMap<String, String> map = new HashMap();
        //
        GP_BUH.USER = emailUserName;
        GP_BUH.PASS = null;
        //
        try {
            //
            String responce = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_RESTORE_PWD, JSon.hashMapToJSON(map));
            //
            return new HttpResponce(responce, LANG.MSG_18_0);
            //
        } catch (Exception ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
            return new HttpResponce(HttpResponce.GENERAL_ERR_0, LANG.MSG_18_0);
        }
    }

    /**
     * This oe is used for creating of account + creating of "buh_kund" +
     * creating of "buh_address"
     *
     * @param emailUserName
     * @param ftgname
     * @param orgnr
     * @return
     */
    public static HttpResponce createAccountPHP_main(String emailUserName, String ftgname, String orgnr) {
        //[#SEQURITY#]
        //
        HashMap<String, String> map = new HashMap();
        //
        //User and pass are added when calling executePHP
        //
        GP_BUH.USER = emailUserName;
        GP_BUH.PASS = null;
//        map.put(DB.BUH_LICENS__USER, emailUserName);
        //
        map.put(DB.BUH_KUND__NAMN, ftgname);
        map.put(DB.BUH_KUND__ORGNR, orgnr);
        //
        map.put(DB.BUH_KUND__DATE_CREATED, GP_BUH.getDateCreated_special());
        //
        map.put(DB.BUH_LICENS__MAC_ADDR, HelpA.getMacAddress());
        map.put(DB.BUH_LICENS__OS, HelpA.getOperatingSystem());
        map.put(DB.BUH_LICENS__LANG, HelpA.getUserLanguge());
        map.put(DB.BUH_LICENS__PC_USER_NAME, HelpA.getUserName());
        map.put(DB.BUH_LICENS__JAVA, HelpA.getJavaVersionAndBitAndVendor_b());
        //
        try {
            //
            String responce = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_CREATE_ACCOUNT_MAIN, JSon.hashMapToJSON(map));
            //
            return new HttpResponce(responce, LANG.MSG_16_0_3_HTML()); // [#HTML-DIALOG#]
            //
        } catch (Exception ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
            return new HttpResponce(HttpResponce.GENERAL_ERR_0, LANG.MSG_16_0_3_HTML()); // [#HTML-DIALOG#]
        }
        //
        //
    }

    public static HttpResponce deleteCustomer_b(String userPassConfirm) {
        //
        HashMap<String, String> map = new HashMap();
        map.put("pass_confirm", userPassConfirm);
        //
        try {
            //
            String response = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_DELETE_CUSTOMER__B, JSon.hashMapToJSON(map));
            //
            System.out.println("response: " + response);
            //
            return new HttpResponce(response, LANG.MSG_23);
            //
        } catch (Exception ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
            return new HttpResponce(HttpResponce.GENERAL_ERR_0, LANG.MSG_23);
        }
        //        
    }
    
     public static HttpResponce deleteBeforeRestore(String userPassConfirm) {
        //
        HashMap<String, String> map = new HashMap();
        map.put("pass_confirm", userPassConfirm);
        //
        try {
            //
            String response = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_DELETE_BEFORE_RESTORE, JSon.hashMapToJSON(map));
            //
            System.out.println("response: " + response);
            //
            return new HttpResponce(response, LANG.MSG_23);
            //
        } catch (Exception ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
            return new HttpResponce(HttpResponce.GENERAL_ERR_0, LANG.MSG_23);
        }
        //        
    }

    /**
     * OBS! Deletes ALL tables since [2020-10-20]
     *
     * @param kundId
     * @param admPass
     */
    private void deleteCustomer_a(String kundId, String admPass) {
        //
        HashMap<String, String> map = new HashMap();
        map.put(DB.BUH_KUND__ID, kundId);
        map.put("admpass", admPass);
        //
        try {
            //
            String response = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_DELETE_CUSTOMER__A, JSon.hashMapToJSON(map));
            //
            System.out.println("response: " + response);
            //
        } catch (Exception ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
    }

    public static HttpResponce deleteGuestAccount(String guestUser) {
        //
        HashMap<String, String> map = new HashMap();
        //
        map.put("guest", guestUser);
        //
        try {
            //
            String response = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_DELETE_GUEST, JSon.hashMapToJSON(map));
            //
            System.out.println("response: " + response);
            //
            return new HttpResponce(response, LANG.MSG_21_0);
            //
        } catch (Exception ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
            return new HttpResponce(HttpResponce.GENERAL_ERR_0, LANG.MSG_21_0);
        }
        //
        //
        //
        //
    }

    /**
     * This one is used for creating of an account for an existing "buh_kund" So
     * if a "buh_licens" already exist, you will not be able to use this.
     *
     * @deprecated
     * @param kundId
     * @return
     */
    private boolean createAccountPHP_existing_customer(String kundId) {
        //[#SEQURITY#]
        HashMap<String, String> map = new HashMap();
        //
        map.put(DB.BUH_KUND__ID, kundId);
        //User and pass are added when calling executePHP
        map.put(DB.BUH_LICENS__MAC_ADDR, HelpA.getMacAddress());
        map.put(DB.BUH_LICENS__OS, HelpA.getOperatingSystem());
        map.put(DB.BUH_LICENS__LANG, HelpA.getUserLanguge());
        map.put(DB.BUH_LICENS__PC_USER_NAME, HelpA.getUserName());
        map.put(DB.BUH_LICENS__JAVA, HelpA.getJavaVersionAndBitAndVendor_b());
        //
        try {
            GP_BUH.KUND_ID = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_CREATE_ACCOUNT_EXISTING_CUSTOMER, JSon.hashMapToJSON(map));
        } catch (Exception ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    private static String useTestDB() {
        if (USE_TEST_DB) {
            return "1";
        } else {
            return "0";
        }
    }

    private static String getDomain() {
        if (DOMAIN_LA) {
            return GP_BUH.LA_WEB_ADDR;
        } else {
            return "www.mixcont.com";
        }
    }

    private static String getScriptsFolder() {
        //[#TEST-SCRIPTS#]
        if (USE_TEST_SCRIPTS) {
            return "php-test";
        } else {
            return "php";
        }
    }

    /**
     * [2020-08-28] OBS! This one does not go via "index.php" This reduces
     * traffic alot as the "html" which was otherwise generated by "index.php",
     * is not created now
     *
     * @param phpScriptName - example: "_http_buh"
     * @param phpFunctionName - example: "delete_entry"
     * @param json
     * @return
     */
    private static String buildUrl(String phpScriptName, String phpFunctionName, String json) {
        if (HTTPS) {
            return String.format("https://" + getDomain() + "/" + getScriptsFolder() + "/%s?%s=true&json=%s", phpScriptName + ".php", phpFunctionName, json);
        } else {
            return String.format("http://" + getDomain() + "/" + getScriptsFolder() + "/%s?%s=true&json=%s", phpScriptName + ".php", phpFunctionName, json);
        }
    }

    /**
     * [2020-08-28]
     *
     * By [2020-08-28] i only now how to upload the file inside the dir where
     * the "upload" script is placed. Or also inside which is inside the dir
     * where the script is. So working paths ("fileNameAndPathServerSide") for
     * the moment are: "test.pdf" OR "uploads/test.pdf"
     *
     * OBS! Is called from HTMLPrint.class -> print_upload_sendmail(...)
     *
     * @param from
     * @param fromNameOptional
     * @param to
     * @param subject
     * @param body
     * @param filePathAttachment - attachment file path on php-server, OBS! The
     * path is relative to the "upload" script
     * @return
     */
    public static EmailSendingStatus sendEmailWithAttachment(String fromNameOptional, String to, String subject, String body, String filePathAttachment) {
        //
        HashMap<String, String> map = new HashMap<>();
        //
//        map.put("from_email", from); // smtp
        map.put("from_name", fromNameOptional); // smtp 
        map.put("to", to);
        map.put("subject", subject);
        map.put("body", body);
        //
        map.put("path", filePathAttachment);
        //
        String json = JSon.hashMapToJSON(map);
        //
        EmailSendingStatus ess = null;
        //
        try {
            //
            String response = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN, DB.PHP_FUNC_EMAIL_WITH_ATTACHMENT, json);
            //
            ess = new EmailSendingStatus(response);
            //
//            System.out.println("EMAIL RESP: " + response);
            //
        } catch (Exception ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        return ess;
        //
    }

    /**
     * [2020-10-12]
     *
     * @param smtp
     * @param to
     * @param subject
     * @param body
     * @param filePathAttachment
     * @return
     */
    public static EmailSendingStatus sendEmailWithAttachment_SMTP(SMTP smtp, String to, String subject, String body, String filePathAttachment) {
        //
        HashMap<String, String> map = smtp.getMap();
        //
        map.put("to", to);
        map.put("subject", subject);
        map.put("body", body);
        //
        map.put("path", filePathAttachment);
        //
        String json = JSon.hashMapToJSON(map);
        //
        EmailSendingStatus ess = null;
        //
        try {
            //
            String response = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN, DB.PHP_FUNC_EMAIL_WITH_ATTACHMENT__SMTP, json);
            //
            System.out.println("RESPOSNCE: " + response);
            //
            ess = new EmailSendingStatus(response);
            //
        } catch (Exception ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        return ess;
        //
    }

    /**
     * [2020-08-28]
     *
     * By [2020-08-28] i only now how to upload the file inside the dir where
     * the "upload" script is placed. Or also inside which is inside the dir
     * where the script is. So working paths ("fileNameAndPathServerSide") for
     * the moment are: "test.pdf" OR "uploads/test.pdf"
     *
     * @param fileNameAndPathClientSide - specifying file name without path
     * results in, taking the file from the root dir of the "netbeans" project
     * @param fileNameAndPathServerSide - specifying file name without path
     * results in, placing the file in the same dir as the "upload" script
     * @return
     * @throws ProtocolException
     * @throws IOException
     * @throws MalformedURLException
     * @throws InterruptedException
     */
    public static boolean uploadFile(String fileNameAndPathClientSide, String fileNameAndPathServerSide) throws ProtocolException, IOException, MalformedURLException, InterruptedException {
        if (HTTPS) {
            return https_send_image("https://" + getDomain() + "/" + getScriptsFolder() + "/_u_u_u_x_upload.php?filename=", fileNameAndPathClientSide, fileNameAndPathServerSide);
        } else {
            return http_send_image("http://" + getDomain() + "/" + getScriptsFolder() + "/_u_u_u_x_upload.php?filename=", fileNameAndPathClientSide, fileNameAndPathServerSide);
        }
    }

    public static final String SERVER_UPLOAD_PATH = "uploads/";
//    public static final String SERVER_UPLOAD_PATH = "";

    private static void test__uploadFile() {
        //
        boolean upload_success = false;
        //
//        for (int i = 0; i < 10; i++) {
        try {
            upload_success = HelpBuh.uploadFile("påminnelse_1.pdf", SERVER_UPLOAD_PATH + "påminnelse_1.pdf"); //[clientPath][ServerPath]
        } catch (ProtocolException ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(LAFakturering.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        System.out.println("Upload Succeded: " + upload_success);
//        }
        //
    }

    private static void test__sendEmailWithAttachment() {
//        boolean sent = HelpBuh.sendEmailWithAttachment("ask@mixcont.com",
//                "BuhInvoice", // This one is shown as name instead of the email it's self
//                "andrej.brassas@gmail.com",
//                "Faktura",
//                "This is a test email for testing attachment sending",
//                SERVER_UPLOAD_PATH + "faktura.pdf"
//        );
//        //
//        System.out.println("Email sending status: " + sent);
        //
        //
        //
        SMTP smtp = IO.loadSMTP();
        EmailSendingStatus ess = HelpBuh.sendEmailWithAttachment_SMTP(smtp, "andrei.brassas@mixcont.com", "Faktura", "This is a test email for testing attachment sending", SERVER_UPLOAD_PATH + "faktura.pdf");
        //
        System.out.println("Email sending status: " + ess.allSuccessful());
    }

    /**
     * [2020-07-22] Universal function for inserting into HTTP DB This one goes
     * through "index.php" generating additional useless traffic
     *
     * @deprecated
     * @param phpScriptName - example: "_http_buh"
     * @param phpFunctionName - example: "delete_entry"
     * @param json
     * @return
     */
    private static String buildUrl_b(String phpScriptName, String phpFunctionName, String json) {
        return String.format("http://www.mixcont.com/index.php?link=%s&%s=true&json=%s", phpScriptName, phpFunctionName, json);
    }

    /**
     * Implemented [2020-06-02] Yes this one is working
     *
     * @deprecated - https shall be used always when possible
     * @param url_
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public static synchronized String http_get_content_post(String url_) throws Exception {
        //
        String urlParameters = url_.split("\\?")[1];
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        String request = url_.split("\\?")[0];
        //
//        CookieHandler.setDefault(new CookieManager()); // OBS! 2021-06-08
        URL url = new URL(request);
        URLConnection conn = url.openConnection();
        //
        conn.setDoOutput(true);
        ((HttpURLConnection) conn).setInstanceFollowRedirects(false);
        ((HttpURLConnection) conn).setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        conn.setUseCaches(false);
        //
        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(postData);
        }
        //
        InputStream ins = conn.getInputStream();
        InputStreamReader isr = new InputStreamReader(ins, "UTF-8");
        BufferedReader in = new BufferedReader(isr);
        String inputLine;
        String result = "";
        while ((inputLine = in.readLine()) != null) {
            result += inputLine;
        }
        //
        System.out.println("BYTES TOTAl: " + result.getBytes().length + "   URL: " + url_.toString());
        //
        String[] arr = result.split("###");
        //
        if (arr.length == 0) {
            System.out.println("HTTP REQ FAILED");
            return "";
        }
        //
        String temp = arr[1];
        String value = temp.split(":")[1];
        //
        System.out.println("HTTP REQ VAL: " + value);
        //
        //[#SEQURITY#]
//        if(value.equals(VALIDATION_ERROR_01_U_P_NOT_SET) 
//                || value.equals(VALIDATION_ERROR_02_U_P_NOT_MATCH)){
//            //
//             HelpA.showNotification(LANG.VALIDATION_MSG_2);
//            //
//            System.exit(0);
//            //
//        }
        //
        //OBS! OBS! [2020-08-03] Without "StringEscapeUtils.unescapeJava(value)" i am getting
        // "\u00e5" instead of "å", so what unescaping dose is that it removes one the "\"
        // because an unescaped string which i recieve looks like "\\u00e5" indeed 
        return StringEscapeUtils.unescapeJava(value);//[#ESCAPE#]
        //
//        String temp = arr[1];
//        System.out.println("HTTP REQ VAL: " + temp);
//        return temp;
    }

    /**
     * SUPER IMPORTANT [2020-10-13] WORKING
     *
     * @param url_
     * @return
     * @throws Exception
     */
    public static synchronized String https_get_content_post(String url_) throws Exception {
        //
        String urlParameters = url_.split("\\?")[1];
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        String request = url_.split("\\?")[0];
        //
        URL url = new URL(request);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        //
        conn.setDoOutput(true);
        //
        ((HttpsURLConnection) conn).setInstanceFollowRedirects(false);
        ((HttpsURLConnection) conn).setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        conn.setUseCaches(false);
        //
        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(postData);
        }
        //
        InputStream ins = conn.getInputStream();
        InputStreamReader isr = new InputStreamReader(ins, "UTF-8");
        BufferedReader in = new BufferedReader(isr);
        String inputLine;
        String result = "";
        while ((inputLine = in.readLine()) != null) {
            result += inputLine;
        }
        //
        System.out.println("BYTES TOTAl: " + result.getBytes().length + "   URL: " + url_.toString());
        //
        String[] arr = result.split("###");
        //
        if (arr.length == 0) {
            System.out.println("HTTP REQ FAILED");
            return "";
        }
        //
        String temp = arr[1];
        String value = temp.split(":")[1];
        //
        System.out.println("HTTP REQ VAL: " + value);
        //
        //
        //OBS! OBS! [2020-08-03] Without "StringEscapeUtils.unescapeJava(value)" i am getting
        // "\u00e5" instead of "å", so what unescaping dose is that it removes one the "\"
        // because an unescaped string which i recieve looks like "\\u00e5" indeed 
        return StringEscapeUtils.unescapeJava(value);//[#ESCAPE#]
        //
    }

    /**
     * https://stackoverflow.com/questions/6927427/how-to-send-https-post-request-in-java
     * OBS! HTTPS! Not Used so far and also not tested yet [2020-10-13]
     *
     * @param url_
     * @return
     * @throws Exception
     */
    private static synchronized String https____get_content_post___leave__as_exmaple(String url_) throws Exception {
        //
        String httpsURL = "https://www.abcd.com/auth/login/";
        //
        String query = "email=" + URLEncoder.encode("abc@xyz.com", "UTF-8");
        query += "&";
        query += "password=" + URLEncoder.encode("abcd", "UTF-8");
        //
        URL myurl = new URL(httpsURL);
        HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();
        con.setRequestMethod("POST");
        //
        con.setRequestProperty("Content-length", String.valueOf(query.length()));
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");
        con.setDoOutput(true);
        con.setDoInput(true);
        //
        DataOutputStream output = new DataOutputStream(con.getOutputStream());
        //
        output.writeBytes(query);
        //
        output.close();
        //
        DataInputStream input = new DataInputStream(con.getInputStream());
        //
        for (int c = input.read(); c != -1; c = input.read()) {
            System.out.print((char) c);
        }
        //
        input.close();
        //
        System.out.println("Resp Code:" + con.getResponseCode());
        System.out.println("Resp Message:" + con.getResponseMessage());
        //
//        return StringEscapeUtils.unescapeJava(value);//[#ESCAPE#]
        return "";
    }

    /**
     * Introduced [2021-02-12]
     *
     * @param url_
     * @param fileNameAndPathClientSide - were to take on client side (Java)
     * @param fileNameAndPathServerSide - were to place on the server side (PHP)
     * @throws MalformedURLException
     * @throws ProtocolException
     * @throws IOException
     * @throws InterruptedException
     * @return
     */
    private static boolean https_send_image(String url_, String fileNameAndPathClientSide, String fileNameAndPathServerSide) throws MalformedURLException, ProtocolException, IOException, InterruptedException {
        //
        String link = url_ + fileNameAndPathServerSide + "&user=" + GP_BUH.USER + "&pass=" + GP_BUH.PASS + "&use_test_db=" + useTestDB();
        //
        URL url = new URL(link);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        //
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        OutputStream os = conn.getOutputStream();
        //
//        Thread.sleep(1000); // Seems not to be needed
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(fileNameAndPathClientSide));
        //
        long totalByte = fis.available();
        //
        long byteTrasferred = 0;
        //
        for (int i = 0; i < totalByte; i++) {
            os.write(fis.read());
            byteTrasferred = i + 1;
//            System.out.println("" + byteTrasferred + " / " + totalByte);
        }
        //
        os.close();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        //
        String s;
        String retur = "";
        //
        while ((s = in.readLine()) != null) {
            System.out.println(s);
            retur = "" + s; // yes it's needed [2020-08-28]
        }
        //
        in.close();
        fis.close();
        //
        return retur.equals("1");
        //
    }

    /**
     * [2020-08-27] working initial example [2021-02-11] introduced validation
     * of the user performing the upload
     *
     * @deprecated
     * @param url_ - http://www.mixcont.com/php/_u_u_u_x_upload.php?filename=
     * @param fileNameAndPathClientSide - were to take on client side (Java)
     * @param fileNameAndPathServerSide - were to place on the server side (PHP)
     * @throws MalformedURLException
     * @throws ProtocolException
     * @throws IOException
     * @throws InterruptedException
     */
    private static boolean http_send_image(String url_, String fileNameAndPathClientSide, String fileNameAndPathServerSide) throws MalformedURLException, ProtocolException, IOException, InterruptedException {
        //
        String url = url_ + fileNameAndPathServerSide + "&user=" + GP_BUH.USER + "&pass=" + GP_BUH.PASS + "&use_test_db=" + useTestDB();
        //
        //
        HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL(url).openConnection();
        httpUrlConnection.setDoOutput(true);
        httpUrlConnection.setRequestMethod("POST");
        OutputStream os = httpUrlConnection.getOutputStream();
        //
//        Thread.sleep(1000); // // Seems not to be needed
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(fileNameAndPathClientSide));
        //
        long totalByte = fis.available();
        //
        long byteTrasferred = 0;
        //
        for (int i = 0; i < totalByte; i++) {
            os.write(fis.read());
            byteTrasferred = i + 1;
//            System.out.println("" + byteTrasferred + " / " + totalByte);
        }
        //
        os.close();
        BufferedReader in = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
        //
        String s;
        String retur = "";
        //
        while ((s = in.readLine()) != null) {
            System.out.println(s);
            retur = "" + s; // yes it's needed [2020-08-28]
        }
        //
        in.close();
        fis.close();
        //
        return retur.equals("1");
        //
    }

    /**
     * For testing only Used by "Run_Invert_Example_C.java" [2020-07-24]
     *
     * @param tablename
     * @param min
     * @param max
     * @return
     */
    public static String getAllClientsInInterval(String tablename, int min, int max) {
        return String.format("http://www.mixcont.com/index.php?link=_http_buh&getininterval=true&min=%s&max=%s&tablename=%s",
                "" + min, "" + max, tablename);
    }

    /**
     * [2020-07-22] The first real method for inserting data to HTTP db
     *
     * @param json
     * @deprecated
     * @return
     */
    private static String sendArticles(String json) {
        return String.format("http://www.mixcont.com/index.php?link=_http_buh&articles_to_db=true&json=%s", json);
    }

}
