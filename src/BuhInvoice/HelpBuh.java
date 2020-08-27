/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author MCREMOTE
 */
public class HelpBuh {

    public static void update(String json) {
        try {
            //
            HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_UPDATE_AUTO, json));
            //
        } catch (Exception ex) {
            Logger.getLogger(Faktura_Entry_Update.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * [2020-08-27]
     *
     * @param phpScriptName - example: "_http_buh"
     * @param phpFunctionName - example: "delete_entry"
     * @param json
     * @return
     * @throws Exception
     */
    public static String executePHPScript(String phpScriptName, String phpFunctionName, String json) throws Exception {
        String url = execute(phpScriptName, phpFunctionName, json);
        return http_get_content_post(url);
    }

    /**
     * [2020-07-22] Universal function for inserting into HTTP DB
     *
     * @param phpScriptName - example: "_http_buh"
     * @param phpFunctionName - example: "delete_entry"
     * @param json
     * @return
     */
    public static String execute(String phpScriptName, String phpFunctionName, String json) {
        return String.format("http://www.mixcont.com/index.php?link=%s&%s=true&json=%s", phpScriptName, phpFunctionName, json);
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
     * Implemented [2020-06-02] Yes this one is working
     *
     * @param url_
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public static String http_get_content_post(String url_) throws Exception {
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
        //OBS! OBS! [2020-08-03] Without "StringEscapeUtils.unescapeJava(value)" i am getting
        // "\u00e5" instead of "å", so what unescaping dose is that it removes one the "\"
        // because an unescaped string which i recieve looks like "\\u00e5" indeed 
        return StringEscapeUtils.unescapeJava(value);
        //
//        String temp = arr[1];
//        System.out.println("HTTP REQ VAL: " + temp);
//        return temp;
    }

    /**
     * [2020-08-27]
     *
     * @throws MalformedURLException
     * @throws ProtocolException
     * @throws IOException
     * @throws InterruptedException
     */
    public static void http_send_image() throws MalformedURLException, ProtocolException, IOException, InterruptedException {
        //
//        HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL("http://www.mypage.org/upload.php").openConnection();
        HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL("http://www.mixcont.com/_u_u_u_x_upload.php?filename=php/test.pdf").openConnection();
        httpUrlConnection.setDoOutput(true);
        httpUrlConnection.setRequestMethod("POST");
        OutputStream os = httpUrlConnection.getOutputStream();
        //
        Thread.sleep(1000);
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream("test.pdf"));
        //
        long totalByte = fis.available();
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
        String s = null;
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

}
