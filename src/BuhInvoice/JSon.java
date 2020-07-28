/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import forall.HelpA;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * This is taken from JkocmocLin -> MyJSON.class
 *
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

    public static String getValueFromJSonString(String json, String key, boolean reverse) {
        HashMap<String, String> map = JSONToHashMap(json, reverse);
        return map.get(key);
    }

    public static HashMap<String, String> JSONToHashMap(String json, boolean reverse) {
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
            //
//            if (keyIsInteger(key)) {
////                System.out.println("Continue");
//                continue;
//            }
            //
            String value;
            //
            if (jsonObj.length == 1) {
//                System.out.println("key, emptie value=" + key);
                value = null;
            } else {
                value = jsonObj[1];
            }
            //
            if (reverse) {
                map.put(value, key);
            } else {
                map.put(key, value);
            }

        }
        //
        return map;
    }

    /**
     * I have a problem that the PHP sends me some strange key and value and
     * namely for each "normal" column it also send me a "key/value pair" were
     * both are numbers - and those i don't want to have. [2020-07-24]
     *
     * @param key
     * @return
     */
    private static boolean keyIsInteger(String key) {
        //
        try {
            Double.parseDouble(key);
            return true;
        } catch (Exception ex) {
            return false;
        }
        //
    }

    /**
     * [2020-07-24]
     *
     * @param phpJsonString
     * @return
     */
    public static ArrayList<HashMap<String, String>> phpJsonResponseToHashMap(String phpJsonString) {
        //
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        //
        ArrayList<String> jsonEnriesList = phpJsonStringSplit(phpJsonString);
        //
        for (String json : jsonEnriesList) {
            //
            HashMap<String, String> map = JSONToHashMap(json, false);
            //
            System.out.println("map: " + map);
            //
            list.add(map);
            //
        }
        //
        return list;
    }

    /**
     * [2020-07-23]
     *
     * @param phpJsonString
     * @param keyOne
     * @param keyTwo
     * @return
     */
    public static String phpJsonResponseToComboBoxString(String phpJsonString, String keyOne, String keyTwo) {
        //
        String jcomboStr = "";
        //
        ArrayList<String> jsons = phpJsonStringSplit(phpJsonString);
        //
        for (String json : jsons) {
            //
            HashMap<String, String> map = JSONToHashMap(json, false);
            //
            jcomboStr += map.get(keyOne) + ";" + map.get(keyTwo) + ",";
            //
        }
        //
        return jcomboStr;
        //
    }

    private static ArrayList<String> phpJsonStringSplit(String jsonStr) {
        //
        String[] arr = jsonStr.split("\\}");
        //
        ArrayList<String> list = new ArrayList<>();
        //
        for (String string : arr) {
            string = string.replaceAll(",\\{", "");
            string = string.replaceAll("\\{", "");
            list.add(string);
        }
        //
        for (String str : list) {
            System.out.println("" + str);
        }
        //
        return list;
    }

    /**
     * The idea behind all this special "_get" methods
     * is to make a given "element" first in the String
     * @param jsonStr -> "Telenor;2,Securitas;1,Telia;3"
     * @param returnValue -> "2"
     * @return -> "Telenor;2,Securitas;1,Telia;3"
     */
    public static String _get_special(String jsonStr, String returnValue) {
        String toShowValue = getValueFromJSonString(jsonStr, returnValue, true);
        return _get(toShowValue,returnValue, jsonStr);
    }
    
    
    /**
     * 
     * @param showVal "Telenor"
     * @param returnVal "2"
     * @param all "Telenor;2,Securitas;1,Telia;3"
     * @return -> "Telenor;2,Securitas;1,Telia;3"
     */
    public static String _get(String showVal,String returnVal, String all){
        String showValPlusReturnVal = showVal + ";" + returnVal;
        all = all.replaceAll(showValPlusReturnVal, "");
        String rst = showValPlusReturnVal + "," + all;
        rst = rst.replaceAll(",,", ",");
        rst = delete_last_letter_in_string(rst, ",");
//      System.out.println("rst: " + rst);
        return rst;
    }
    
    /**
     * 
     * @param singleVal -> "10"
     * @param all -> "30,60,20,15,10,5"
     * @return -> "10,30,60,20,15,5"
     */
    public static String _get_simple(String singleVal, String all){
        all = all.replaceAll(singleVal, "");
        String rst = singleVal + "," + all;
        rst = rst.replaceAll(",,", ",");
        rst = delete_last_letter_in_string(rst, ",");
//      System.out.println("rst: " + rst);
        return rst;
    }
    
    

    public static void main(String[] args) {
        //
//        String json = "{\"name\";\"myname\",\"age\";\"20\"}";
//        //
//        System.out.println("" + JSONToHashMap(json));
        //
        String phpJsonStr = "{\"0\";\"Telia\",\"namn\";\"Telia\",\"1\";\"3\",\"fakturaKundId\";\"3\"},{\"0\";\"Eon\",\"namn\";\"Eon\",\"1\";\"4\",\"fakturaKundId\";\"4\"},{\"0\";\"Akelius\",\"namn\";\"Akelius\",\"1\";\"5\",\"fakturaKundId\";\"5\"},{\"0\";\"Telenor\",\"namn\";\"Telenor\",\"1\";\"6\",\"fakturaKundId\";\"6\"}";
        //
        String phpJsonStr_b = "{\"fakturaId\";\"6\",\"kundId\";\"2\",\"fakturaKundId\";\"1\",\"fakturanr\";\"2\",\"namn\";\"FAKTURA BB\",\"fakturatyp\";\"NORMAL\",\"inkl_moms\";\"1\",\"fakturadatum\";\"2020-07-09\",\"forfallodatum\";\"2020-07-09\",\"valuta\";\"SEK\",\"ert_ordernr\";\"\",\"er_referens\";\"\",\"var_referens\";\"\",\"frakt\";\"0\",\"betal_vilkor\";\"10\",\"lev_vilkor\";\"FVL\",\"lev_satt\";\"P\",\"exp_avg\";\"0\",\"total_ink_moms\";\"0\",\"total_exkl_moms\";\"0\",\"moms_total\";\"0\",\"moms_sats\";\"0\",\"komment\";\"\",\"important_komment\";\"\",\"ska_bokforas\";\"0\",\"request_factoring\";\"0\",\"factoring_status\";\"0\",\"makulerad\";\"0\",\"date_created\";\"n\\/a\"},{\"fakturaId\";\"5\",\"kundId\";\"2\",\"fakturaKundId\";\"3\",\"fakturanr\";\"1\",\"namn\";\"FAKTURA AA\",\"fakturatyp\";\"NORMAL\",\"inkl_moms\";\"1\",\"fakturadatum\";\"2020-07-09\",\"forfallodatum\";\"2020-07-09\",\"valuta\";\"SEK\",\"ert_ordernr\";\"\",\"er_referens\";\"\",\"var_referens\";\"\",\"frakt\";\"0\",\"betal_vilkor\";\"10\",\"lev_vilkor\";\"FVL\",\"lev_satt\";\"P\",\"exp_avg\";\"0\",\"total_ink_moms\";\"0\",\"total_exkl_moms\";\"0\",\"moms_total\";\"0\",\"moms_sats\";\"0\",\"komment\";\"\",\"important_komment\";\"\",\"ska_bokforas\";\"0\",\"request_factoring\";\"0\",\"factoring_status\";\"0\",\"makulerad\";\"0\",\"date_created\";\"n\\/a\"}";
        //
//        phpJsonStringSplit(phpJsonStr_b);
        //
//        System.out.println("" + phpJsonResponseToComboBoxString(phpJsonStr, "namn", "fakturaKundId"));
        //
//        phpJsonResponseToHashMap(phpJsonStr_b);
    
            System.out.println("" + _get("Telenor","2", "Securitas;1,Telenor;2,Telia;3"));
            
            System.out.println("" + _get_simple("10", "30,60,20,15,10,5"));
            
    }
    
    
    private static String delete_last_letter_in_string(String str,String letter) {
        //
        int a = str.length() - 1;
        //
        if(getLastChar(str).equals(letter)){
            return str.substring(0, a);
        }else{
            return str;
        }
        //
    }
    
    private static String getLastChar(String str) {
        int a = str.length() - 1;
        int b = str.length();
        return str.substring(a, b);
    }

}
