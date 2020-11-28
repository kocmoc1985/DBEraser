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
public class MSG {

    private static final Properties DICT = new Properties();
    
    static {
        //Don't change ONLY here, you must change both here and where it's called
        DICT.put("Maximum of 6 recipes reached", "");
        DICT.put("No records found for recipe: ", "");
        DICT.put("Cannot delete Recipe with status S, O, Inactive", "");
        DICT.put("RECIPE ORIGIN or RECIPE VERSION must be chosen before using this option!", "");
        DICT.put("Invalid password", "");
        DICT.put("The recipe status is not S or O or Inactive", "");
        DICT.put("Cannot proceed, Polymer group and Mixer must be chosen", "");
        DICT.put("Both values must be specified", "");
        DICT.put("Cannot add material, density = 0. Go to Ingredients and change density", "");
        DICT.put("Please choose a row in the table to perform this action", "");
        DICT.put("No id for this record, cannot proceed", "");
        DICT.put("Cannot proceed, GROUP must be chosen", "");
        DICT.put("Cannot do this action for Recipe with status S, O, Inactive", "");
        DICT.put("Both values must be specified!", "");
        DICT.put("No entry is chosen in the table!", "");
        DICT.put("Step number not chosen!", "");
        DICT.put("Cannot proceed, GROUP must be chosen", "");
        DICT.put("Nothing chosen, click on a row in the table below", "");
        DICT.put("Nothing chosen plese click on any of corresponding rows", "");
        DICT.put("Operation failed, because of sql error", "");
        DICT.put("Error, please try aggain", "");
        DICT.put("Operation failed, because of sql error", "");
        DICT.put("Please fill in the fields before adding", "");
        DICT.put("Contact not chosen, click on any field corresponding to the contact", "");
        DICT.put("Operation failed, because of sql error", "");
        DICT.put("Nothing chosen, click on a row in the table below", "");
        DICT.put("Cannot proceed,Please choose ingredient", "");
        DICT.put("TADE NAME must be filled in", "");
        DICT.put("Vendor must be filled in", "");
        DICT.put("Maximum ammount reached (16)", "");
        DICT.put("Replicating of production plan from .csv file failed", "");
        DICT.put("Dou you really want to delete tradename: ", "");
        DICT.put("Delete tradename: ", "");
        DICT.put("Delete Vendor: ", "");
        DICT.put("Delete contact: ", "");
        DICT.put("Dou you really want to delete tradename: ", "");
        DICT.put("This action will erase all actual sequence steps", "");
        DICT.put("Unblock recipe?", "");
        DICT.put("Mixing sequence will be deleted including all mixing steps!", "");
        DICT.put("Delete all rows?", "");
        DICT.put("Importing to CSV Table", "");
        DICT.put("Additional info is needed for recipe: ", "");
        DICT.put("Enter password", "");
        DICT.put("", "");
    }
    
     public static String LANG(String word) {
        return LANG_ENG ? word : DICT.getProperty(word, "*" + word);
    }
    
    public static String MSG_0() {
        return LANG_ENG ? "Login failed" : "Anmeldung fehlgeschlagen";
    }

    public static String MSG_0_1() {
        return LANG_ENG ? "Login successful" : "Anmeldung erfolgreich";
    }

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

    public static String MSG_8_0(String userRole) {
        return LANG_ENG ? "Access not allowed with user role: *" + userRole : "Zugang nicht eraubt für: " + userRole;
    }

    public static String MSG_8_1() {
        return LANG_ENG ? "Not allowed" : "Nicht erlaubt";
    }
}
