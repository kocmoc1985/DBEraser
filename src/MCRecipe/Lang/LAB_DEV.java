/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Lang;

import static MCRecipe.Lang.LNG.LANG_ENG;
import java.util.Properties;

/**
 *
 * @author KOCMOC
 */
public class LAB_DEV {
    
    private static final Properties DICT = new Properties();
   

    public static String TABLE_INVERT_NAME_3() {
        return LANG_ENG ? "SERVICES" : "DIENSTE";
    }

    public static String TABLE_INVERT_NAME_4() {
        return LANG_ENG ? "PROCESSING" : "VERARBEITUNG";
    }

    public static String TABLE_INVERT_NAME_5() {
        return LANG_ENG ? "TEST" : "PRÜFT";
    }

    public static String LBL_1() {
        return LANG_ENG ? "Order" : "Auftrag";
    }

    public static String LBL_2() {
        return LANG_ENG ? "Status" : "Status";
    }

    public static String LBL_3() {
        return LANG_ENG ? "Delay Code" : "Verzögerung Code";
    }

    public static String LBL_4() {
        return LANG_ENG ? "Requester" : "Antragsteller";
    }

    public static String LBL_5() {
        return LANG_ENG ? "Updated on" : "Aktualisiert";
    }

    public static String LBL_6() {
        return LANG_ENG ? "Updated by" : "Aktualisiert von";
    }
    
    
}
