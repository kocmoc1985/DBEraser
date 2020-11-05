/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Lang;

import static MCRecipe.Lang.LNG.LANG_ENG;

/**
 *
 * @author KOCMOC
 */
public class MSG {

    public static String MSG_1() {
        return LANG_ENG ? "Note Name" : "Notiz Name";
    }

    public static String MSG_1_2() {
        return LANG_ENG ? "Note Value" : "Notiz Wert";
    }

    public static String MSG_1_3() {
        return LANG_ENG ? "Create new note" : "Neue Notiz erstellen";
    }

    public static String MSG_2() {
        return LANG_ENG ? "Confirm" : "Bestätigen";
    }

    public static String MSG_2_2() {
        return LANG_ENG ? "Confirm action?" : "Handlung bestätigen?";
    }

    public static String MSG_3() {
        return LANG_ENG ? "Input contains errors" : "Die Eingabe enthält Fehler";
    }

    public static String MSG_3_2() {
        return LANG_ENG ? "Input is to long" : "Die Eingabe ist zu lang";
    }
}
