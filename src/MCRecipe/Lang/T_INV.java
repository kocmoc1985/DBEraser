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
        DICT.put("CODE", "CODE");
        DICT.put("RELEASE", "VERSION");
        DICT.put("DESCRIPTION", "BESCHREIBUNG");
        DICT.put("CUSTOMER", "KUNDE");
        DICT.put("STATUS", "STATUS");
        DICT.put("CLASS", "KATEGORIE");
        DICT.put("POLYMER GROUP", "POLYMER GRUPPE");
        DICT.put("MIXER", "MISCHER");
        DICT.put("LOADFACTOR", "FÜLLGRAD");
        DICT.put("MIXTIME", "MISCHZEIT");
        DICT.put("PRICE/KG", "PREIS/KG");
        DICT.put("PRICE/L", "PREIS/L");
        //
        DICT.put("NAME", "CODE");
        DICT.put("CROSS REFERENCE", "QUERVERWEIS");
        DICT.put("PRICE", "PREIS");
        DICT.put("CASNO", "CAS NR");
        DICT.put("CHEMICAL NAME", "CHEMISHER NAME");
        DICT.put("CHEMICAL NAME", "CHEMISHER NAME");
        DICT.put("GROUP", "GRUPPE");
        DICT.put("GROUP NAME", "GRUPPENNAME");
        DICT.put("APPEARANCE", "ERSCHEINUNGSFORM");
        DICT.put("PERCENTAGE RUBBER", "KAUTSCHUKGEHALT");
        DICT.put("RUBBER TOLERANCES", "KAUTSCHUK TOLERANZ");
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
        DICT.put("LOCATION", "LAGE");
        DICT.put("SILO ID", "SILO ID");
        DICT.put("BALANCE ID", "BALANCE ID");
        //
        DICT.put("VENDOR", "LIEFERANT");
        DICT.put("TRADENAME", "HANDELSNAME");
        DICT.put("SUPP. / MAN.", "SUPP. / MAN.");
        //
        DICT.put("VENDOR ID", "LIEFERANT ID");
        DICT.put("VENDOR NO", "LIEFERANT NR");
        DICT.put("ADRESS", "ADRESSE");
        DICT.put("POSTAL CODE", "POSTLEITZAHL");
        DICT.put("CITY", "STADT");
        DICT.put("LAND", "LAND");
        DICT.put("TELEPHONE", "TELEFON");
        DICT.put("FAX", "FAX");
        DICT.put("EMAIL", "EMAIL");
        DICT.put("WEBSITE", "WEBSEITE");
        DICT.put("FREEINFO", "INFO");
        //
        DICT.put("CONTACT NAME", "ANSPRECHPARTNER");
        DICT.put("POSITION", "STELLUNG");
        DICT.put("POSITION", "STELLUNG");
        DICT.put("PHONE", "TELEFON");
        //
        DICT.put("CASNO ID", "CAS NR");
        DICT.put("MSDS", "MSDS");
        //
        //
        DICT.put("ORDER", "AUFTRAG");
        DICT.put("RECIPE", "CODE");
        DICT.put("TEST CODE", "PRÜFCODE");
    }

    public static String LANG(String word) {
        return LANG_ENG ? word : DICT.getProperty(word, "*" + word);
    }
}
