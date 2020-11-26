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

    public static String MSG_4(String status) {
        return LANG_ENG ? "The status will be changed to: " + "\"" + status + "\"" : "Diese Handlung wird den Status ändern. Soll Status: " + "\"" + status + "\"" + " akzeptiert werden?";
    }

    public static String MSG_5() {
        return LANG_ENG ? "Please choose a row from table" : "Bitte eine Reihe von Tabelle auswählen";
    }

    public static String MSG_6(String code) {
        return LANG_ENG ? "Confirm deletion of: " + code : "Löschung bestätigen, Code: " + code;
    }
    
    public static String MSG_6_2() {
        return LANG_ENG ? "Create new ageing code" : "Neuen Alterungscode erstellen";
    }
    
    public static String MSG_6_3(String code) {
        return LANG_ENG ? "Copy code: " + code + "?" : "Code: " + code + " kopieren?";
    }
    
    public static String MSG_7() {
        return LANG_ENG ? "Operation successful" : "Erfolgreich";
    }
    
    public static String MSG_7_2() {
        return LANG_ENG ? "Operation failed" : "Fehlgeschlagen";
    }
    
    public static String MSG_7_3() {
        return LANG_ENG ? "Add new" : "Neu ergänzen";
    }
    
    public static String MSG_7_4() {
        return LANG_ENG ? "Test code not chosen" : "Prüffcode nicht ausgewählt";
    }
    
    public static String MSG_7_5() {
        return LANG_ENG ? "Already exist! The entry will not be added" : "Gibt schon! Der Eintrag wurde nicht ergänzt";
    }
    
     public static String MSG_7_6() {
        return LANG_ENG ? "Confirm deletion?" : "Löschung bestätigen?";
    }
}
