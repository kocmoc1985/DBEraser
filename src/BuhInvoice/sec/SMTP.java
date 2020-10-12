/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import BuhInvoice.JSon;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author KOCMOC
 */
public class SMTP implements Serializable{

    private final String host; //"smtp.gmail.com"
    private final String u; // (u) user is also used as "from"
    private final String p; // (p) password
    private final String port;
    private final String from_name;

    public SMTP(String host, String u, String p, String port, String from_name) {
        this.host = host;
        this.u = u;
        this.p = p;
        this.port = port;
        this.from_name = from_name;
    }

    public String toJSon() {
        HashMap<String, String> values = new HashMap<>();
        values.put("host", host);
        values.put("u", u);
        values.put("p", p);
        values.put("port", port);
        values.put("from_name", from_name);
        //
        return JSon.hashMapToJSON(values);
    }

}
