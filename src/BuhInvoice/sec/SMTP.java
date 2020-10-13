/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author KOCMOC
 */
public class SMTP implements Serializable {

    public static final long serialVersionUID = -7737096419321204313L;
    private final String host; //"smtp.gmail.com"
    
    private final String u; // (u) user is also used as "from"
    private final String p; // (p) password
    private final String port;
    private final String from_name;
    private final static String CRYPT = "STARTTLS";

    public SMTP(String host, String u, String p, String port, String from_name) {
        this.host = host;
        this.u = u;
        this.p = p;
        this.port = port;
        this.from_name = from_name;
    }

    public boolean allFilled() {
        boolean filled = (host != null && host.isEmpty() == false) && (u != null && u.isEmpty() == false)
                && (p != null && p.isEmpty() == false) && (port != null && port.isEmpty() == false);
        if (filled) {
            return true;
        } else {
            return false;
        }
    }

    public HashMap<String, String> getMap() {
        HashMap<String, String> map = new HashMap<>();
        //OBS! parameter names "host,u,p..." are the ones used on PHP side
        map.put("host", host);
        map.put("u", u);
        map.put("p", p);
        map.put("port", port);
        map.put("from_name", from_name);
        return map;
    }

    public String getHost() {
        return host;
    }

    public String getU() {
        return u;
    }

    public String getP() {
        return p;
    }

    public String getPort() {
        return port;
    }

    public String getFrom_name() {
        return from_name;
    }

    public static String getCrypt() {
        return CRYPT;
    }
    
    
}
