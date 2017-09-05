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
        DICT.put("NAME", "NAME");
        DICT.put("CROSS REFERENCE", "KREUZREFERENZ");
        DICT.put("PRICE", "PREISE");
        DICT.put("CASNO", "CASNR");
        DICT.put("CHEMICAL NAME", "CHEMISHE NAME");
        DICT.put("CHEMICAL NAME", "CHEMISHE NAME");
        DICT.put("GROUP", "GRUPPE");
        DICT.put("GROUP NAME", "GRUPPENAME");
        DICT.put("APPEARANCE", "ERSCHEINUNGSFORM");
        DICT.put("PERCENTAGE RUBBER", "GUMMI IN PROZENTEN");
        DICT.put("RUBBER TOLERANCES", "GUMMI TOLERANZ");
        DICT.put("ACTIVITY", "AKTIVITÄT");
        DICT.put("DENSITY", "DICHTE");
        DICT.put("DENSITY TOLERANCE", "DICHTE TOLERANZ");
        DICT.put("MOONEY TEMPEARTURE", "MOONEY TEMPERATUR");
        DICT.put("MOONEY TIME", "MOONEY ZEIT");
        DICT.put("MOONEY VISCOSITY", "MOONEY VISKOSITÄT");
        DICT.put("MOONEY TOLERANCES", "MOONEY TOLERANZ");
        DICT.put("TECHNICAL DATASHEET", "TECH. DATENBLATT");
        DICT.put("CODE ID", "KODE ID");
        //
        
        //
        DICT.put("UPDATED ON", "AKTUALISERT AM");
        DICT.put("UPDATED BY", "AKTUALISERT VON");
        DICT.put("CREATED ON", "ERSTELLT AM");
        DICT.put("CREATED BY", "ERSTELLT VON");
        //
    }

    public static String LANG(String word) {
        return LANG_ENG ? word : DICT.getProperty(word, "*" + word);
    }
}
