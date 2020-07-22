/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forall;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * This is taken from JkocmocLin -> MyJSON.class
 * @author KOCMOC
 */
public class JSon {
    
    // OBS! OBS! OBS! [2020-07-16]
    // Pay attention that iam using ";" as key/value separator, in fact
    // the JSON format uses ":". BUT the ":" can not be sent via HTTP request
    // so i use ";" and replace it with ":" on the Server/PHP side
    //
    // Below the teste JSON Strings for Java [2020-07-16]
    // "{\"name\";\"myname\",\"age\";\"20\"}"
    //
    // "{\"fakturaKundId\";\"1\",\"lev_satt\";\"P\",\"var_referens\";\"Andrei Brassas\",\"fakturadatum\";\"2020-07-15\",\"forfallodatum\";\"2020-08-14\",\"lev_vilkor\";\"FVL\",\"er_referens\";\"\",\"betal_vilkor\";\"30\"}"
    //
    
    
    public static String hashMapToJSON(HashMap<String, String> map) {
        //
        String json = "{";
        //
        Set set = map.keySet();
        Iterator it = set.iterator();
        //
        while (it.hasNext()) {
            //
            String key = (String) it.next();
            String value = (String) map.get(key);
            //
            json += "\"" + key + "\"" + ";";
            if (!it.hasNext()) {
                json += "\"" + value + "\"";
            } else {
                json += "\"" + value + "\"" + ",";
            }
            //
        }
        //
        json += "}";
        //
        System.out.println("json: " + json);
        //
        return json;
        //
    }

    public static HashMap<String, String> JSONToHashMap(String json) {
        //
        HashMap<String, String> map = new HashMap<>();
        //
        json = json.replaceAll("\"", "");
        json = json.replaceAll("\\{", "");
        json = json.replaceAll("\\}", "");
        String[] arr = json.split(",");
        //
        for (String entry : arr) {
            //
            String[] jsonObj = entry.split(";");
            String key = jsonObj[0];
            String value = jsonObj[1];
            //
            map.put(key, value);
        }
        //
        return map;
    }

    public static void main(String[] args) {
        //
        String json = "{\"name\";\"myname\",\"age\";\"20\"}";
        //
        System.out.println("" + JSONToHashMap(json));
        //
    }
    
}
