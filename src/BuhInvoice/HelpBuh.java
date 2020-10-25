/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import static BuhInvoice.JSon.JSONToHashMap;
import BuhInvoice.sec.EmailSendingStatus;
import BuhInvoice.sec.HttpResponce;
import BuhInvoice.sec.IO;
import BuhInvoice.sec.LANG;
import BuhInvoice.sec.SMTP;
import forall.HelpA;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author MCREMOTE
 */
public class HelpBuh {

    /**
     * [2020-09-07]
     *
     * @param phpScriptName - example: "_http_buh"
     * @param phpFunctionName - example: "delete_entry"
     * @param json
     * @return
     * @throws Exception
     */
    public static String executePHP(String phpScriptName, String phpFunctionName, String json) throws Exception {
        //
        // OBS! Last "false" means don't replace special chars - very important [2020-10-11]
        HashMap<String, String> map = JSONToHashMap(json, false, 1, false);
        //
        map.put(DB.BUH_LICENS__USER, GP_BUH.USER); // [#SEQURITY#] Required by the PHP (_http_buh.php->validate(..))
        map.put(DB.BUH_LICENS__PASS, GP_BUH.PASS); // [#SEQURITY#]
        //
        String url = buildUrl(phpScriptName, phpFunctionName, JSon.hashMapToJSON(map));
        //
        return http_get_content_post(url);
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
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
            return new HttpResponce(HttpResponce.GENERAL_ERR_0, LANG.MSG_20_0);
        }
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
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
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
        map.put(DB.BUH_KUND__DATE_CREATED, GP_BUH.getDateCreated());
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
            return new HttpResponce(responce, LANG.MSG_16_0);
            //
        } catch (Exception ex) {
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
            return new HttpResponce(HttpResponce.GENERAL_ERR_0, LANG.MSG_16_0);
        }
        //
    }

    /**
     * OBS! Deletes ALL tables since [2020-10-20]
     *
     * @param kundId
     * @param admPass
     */
    public static void deleteCustomer_a(String kundId, String admPass) {
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
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
            return new HttpResponce(null, LANG.MSG_21_0);
        }
        //
    }

    public static void main(String[] args) {
        //
//        GP_BUH.USER = "mixcont";
//        GP_BUH.PASS = "mixcont4765";
//        createAccountPHP_existing_customer("1");
        //
//        test__sendEmailWithAttachment();
        //
//        createAccountPHP_main("andrej.brassas@gmail.com", "BuhInvoice", "556251-6806");
        //
        //
        deleteCustomer_a("20", "Vxuw6lpMzF");
        //
        //
//        restorePwd("andrej.brassas@gmail.com");
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
    public static boolean createAccountPHP_existing_customer(String kundId) {
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
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public static String executePHP__prev(String phpScriptName, String phpFunctionName, String json) throws Exception {
        String url = buildUrl(phpScriptName, phpFunctionName, json);
        return http_get_content_post(url);
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
        return String.format("http://www.mixcont.com/php/%s?%s=true&json=%s", phpScriptName + ".php", phpFunctionName, json);
    }

    /**
     * [2020-08-28]
     *
     * By [2020-08-28] i only now how to upload the file inside the dir where
     * the "upload" script is placed. Or also inside which is inside the dir
     * where the script is. So working paths ("fileNameAndPathServerSide") for
     * the moment are: "test.pdf" OR "uploads/test.pdf"
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
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
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
     * @throws ProtocolException
     * @throws IOException
     * @throws MalformedURLException
     * @throws InterruptedException
     */
    public static boolean uploadFile(String fileNameAndPathClientSide, String fileNameAndPathServerSide) throws ProtocolException, IOException, MalformedURLException, InterruptedException {
        return http_send_image(DB.PHP_SCRIPT_UPLOAD_URL, fileNameAndPathClientSide, fileNameAndPathServerSide);
    }

    public static final String SERVER_UPLOAD_PATH = "uploads/";
//    public static final String SERVER_UPLOAD_PATH = "";

    private static void test__uploadFile() {
        //
        boolean upload_success = false;
        //
        try {
            upload_success = HelpBuh.uploadFile("test.pdf", SERVER_UPLOAD_PATH + "test.pdf"); //[clientPath][ServerPath]
        } catch (ProtocolException ex) {
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        System.out.println("Upload Succeded: " + upload_success);
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
     * [2020-08-27]
     *
     * working initial example
     *
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
        String url = url_ + fileNameAndPathServerSide;
        //
        HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL(url).openConnection();
        httpUrlConnection.setDoOutput(true);
        httpUrlConnection.setRequestMethod("POST");
        OutputStream os = httpUrlConnection.getOutputStream();
        //
//        Thread.sleep(1000); // Needed ????
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
     * [2020-08-27]
     * "http://www.mixcont.com/_u_u_u_x_upload.php?filename=php/test.pdf" -
     * working initial example
     *
     * @deprecated
     * @throws MalformedURLException
     * @throws ProtocolException
     * @throws IOException
     * @throws InterruptedException
     */
    public static void http_send_image__initial_testing() throws MalformedURLException, ProtocolException, IOException, InterruptedException {
        //
        String fileNameAndPathClientSide = "test.pdf";
        //
        String fileNameAndPathServerSide = "php/test.pdf";
        //
        String url = "http://www.mixcont.com/php/_u_u_u_x_upload.php?filename=" + fileNameAndPathServerSide;
        //
        HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL(url).openConnection();
        httpUrlConnection.setDoOutput(true);
        httpUrlConnection.setRequestMethod("POST");
        OutputStream os = httpUrlConnection.getOutputStream();
        //
//        Thread.sleep(1000); // Needed ????
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(fileNameAndPathClientSide));
        //
        long totalByte = fis.available();
        //
        long byteTrasferred = 0;
        //
        for (int i = 0; i < totalByte; i++) {
            os.write(fis.read());
            byteTrasferred = i + 1;
        }
        //
        os.close();
        BufferedReader in = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
        //
        String s;
        //
        while ((s = in.readLine()) != null) {
            System.out.println(s);
        }
        //
        in.close();
        fis.close();
        //
    }

    /**
     * Was trying to make it working for sending JSON but no success, it can be
     * leaved for further investigations
     *
     * @param url_
     * @return
     * @throws Exception
     * @deprecated
     */
    public static String http_get_content_post___test(String url_) throws Exception {
        //
        String urlParameters = url_.split("\\?")[1];
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        String request = url_.split("\\?")[0];
        //
        URL url = new URL(request);
        URLConnection conn = url.openConnection();
        //
        conn.setDoOutput(true);
        ((HttpURLConnection) conn).setInstanceFollowRedirects(false);
        ((HttpURLConnection) conn).setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json"); //
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        conn.setUseCaches(false);
        //
        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(postData);
        }
        //
        InputStream ins = conn.getInputStream();
        InputStreamReader isr = new InputStreamReader(ins);
        BufferedReader in = new BufferedReader(isr);
        String inputLine;
        String result = "";
        while ((inputLine = in.readLine()) != null) {
            result += inputLine;
        }

        String[] arr = result.split("###");
        //
        if (arr.length == 0) {
            System.out.println("HTTP REQ FAILED");
            return "";
        }
        //
//        String temp = arr[1];
//        String value = temp.split(":")[1];
//        System.out.println("HTTP REQ VAL: " + value);
//        return value;
        //
        String temp = arr[1];
        System.out.println("HTTP REQ VAL: " + temp);
        return temp;
    }

    /**
     * Working test example [2020-07-16]
     *
     * @param json
     * @deprecated
     * @return
     */
    private static String testSendJson(String json) {
        return String.format("http://www.mixcont.com/index.php?link=_http_buh&test_json=true&json=%s", json);
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
