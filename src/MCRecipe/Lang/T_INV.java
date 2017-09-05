/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Lang;

import static MCRecipe.Lang.LNG.LANG_ENG;
import java.util.Properties;

/**
 *
 * @author KOCMOC
 */
public class T_INV {

    private static final Properties DICT = new Properties();

    static {
        DICT.put("CODE", "KODE");
        DICT.put("RELEASE", "VERSION");
        DICT.put("DESCRIPTION", "BESHREIBUNG");
        DICT.put("CUSTOMER", "KUNDE");
        DICT.put("STATUS", "STATUS");
        DICT.put("CLASS", "KATEGORIE");
        DICT.put("POLYMER GROUP", "POLYMER GRUPPE");
        DICT.put("MIXER", "MISCHER");
        DICT.put("LOADFACTOR", "AUSLASTUNGSFAKTOR");
        DICT.put("MIXTIME", "MISCHZEIT");
        DICT.put("PRICE/KG", "PREIS/KG");
        DICT.put("PRICE/L", "PREIS/L");
        //
        
        //
        DICT.put("UPDATED ON", "AKTUALISERT AM");
        DICT.put("UPDATED BY", "AKTUALISERT VON");
        DICT.put("CREATED ON", "ERSTELLT AM");
        DICT.put("CREATED BY", "ERSTELLT VON");
        //
    }

    public static String LANG(String word) {
        return LANG_ENG ? word : DICT.getProperty(word, word);
    }
}
