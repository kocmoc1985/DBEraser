/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import static MCRecipe.Lang.LNG.LANG_ENG;
import forall.HelpA_;
import java.util.Properties;

/**
 *
 * @author KOCMOC
 */
public class LAB_DEV__STATUS {

    // 
    // Below the original statuses from E..
    //
    // Input
    // Execute **
    // Enter -----> Which of them does it correspond -> Gewünscht, Genehmigt, Abort?
    // Ready **
    // Archive **
    //
    private static final Properties DICT_DE_ENG = new Properties();
    private static Properties DICT_ENG_DE = new Properties();
    //
    public static String[] getLabDevStatusesAuto(boolean langEng) {
        if (langEng == false) {
            return LAB_DEV_STATUSES__DE;
        } else {
            return LAB_DEV_STATUSES__ENG;
        }
    }
    //
    public static String getStatusEng(boolean langEng,String status){
        if(langEng == false){
            return ENG(status);
        }else{
            return status;
        }
    }
    //
     public static String getStatusActLang(boolean langEng,String status_eng){
        //
        if(status_eng == null){
            return "";
        }
        //
        if(langEng == false){
            return DICT_ENG_DE.getProperty(status_eng, "*" + status_eng);
        }else{
            return status_eng;
        }
    }
    //
    private static final String[] LAB_DEV_STATUSES__ENG = new String[]{
        "Input", // Eingabe **
        "Requested", // Gewünscht
        "Approved", // Genehmigt
        "Execute", // Ausführung **
        "Ready", // Fertig **
        "Archive", // Archiv **
        "Abort" // Abbrechen
    };

    private static final String[] LAB_DEV_STATUSES__DE = new String[]{
        "Eingabe", // Input
        "Gewünscht", // Requested
        "Genehmigt", // Approved
        "Ausführung", // Execute
        "Fertig", // Ready
        "Archiv", // Archive
        "Abbrechen" // Abort
    };

    static {
        //
        DICT_DE_ENG.put("Eingabe", "Input");
        DICT_DE_ENG.put("Gewünscht", "Requested");
        DICT_DE_ENG.put("Genehmigt", "Approved");
        DICT_DE_ENG.put("Ausführung", "Execute");
        DICT_DE_ENG.put("Fertig", "Ready");
        DICT_DE_ENG.put("Archiv", "Archive");
        DICT_DE_ENG.put("Abbrechen", "Abort");
        //
        DICT_ENG_DE = HelpA_.swap_keys_and_values__properties(DICT_DE_ENG);
        //
    }

    private static String ENG(String status) {
        return LANG_ENG ? status : DICT_DE_ENG.getProperty(status, "*" + status);
    }
    
}
