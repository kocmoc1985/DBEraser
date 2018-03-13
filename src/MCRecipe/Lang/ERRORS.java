/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Lang;

import static MCRecipe.Lang.LNG.LANG_ENG;

/**
 *
 * @author KOCMOC
 */
public class ERRORS {

    public static final String VAL_MISSING_REPORT = "*";

    public static String SQL_ERROR_DATA_TRUNCATION() {
        return LANG_ENG ? "The length of input exceeded the limit" : "Die Länge der Dateneigabe ist zu lang";
    }

    public static String SQL_ERROR() {
        return LANG_ENG ? "SQL ERROR, could not update" : "SQL FEHLER, Speicherung nicht möglich";
    }
}
