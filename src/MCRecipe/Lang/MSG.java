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

    public static final String VAL_MISSING_REPORT = "*";
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
        DICT.put("Login failed", "Anmeldung fehlgeschlagen");
        DICT.put("Login successful", "Anmeldung erfolgreich");
        DICT.put("Note Name", "Notiz Name");
        DICT.put("Note Value", "Notiz Wert");
        DICT.put("Create new note", "Neue Notiz erstellen");
        DICT.put("Confirm", "Bestätigen");
        DICT.put("Confirm action?", "Handlung bestätigen?");
        DICT.put("Input contains errors", "Die Eingabe enthält Fehler");
        DICT.put("Input is to long", "Die Eingabe ist zu lang");
        DICT.put("Please choose a row from table", "Bitte eine Reihe von Tabelle auswählen");
        DICT.put("Confirm deletion of: ", "Löschung bestätigen, Code: ");
        DICT.put("Create new ageing code", "Neuen Alterungscode erstellen");
        DICT.put("Create new order", "Neuen Auftrag erstellen");
        DICT.put("Create new test procedure", "Neue Prüfmethode erstellen");
        DICT.put("Operation successful" , "Erfolgreich");
        DICT.put("Operation failed" , "Fehlgeschlagen");
        DICT.put("Add new" , "Neu ergänzen");
        DICT.put("Test code not chosen" , "Prüffcode nicht ausgewählt");
        DICT.put("Already exist! The entry will not be added" , "Gibt schon! Der Eintrag wurde nicht ergänzt");
        DICT.put("Confirm deletion?" , "Löschung bestätigen?");
        DICT.put("Access not allowed with user role: " , "Zugang nicht eraubt für: ");
        DICT.put("Not allowed", "Nicht erlaubt");
        DICT.put("Table row not chosen" , "Tabellenzeile nicht ausgewählt");
        DICT.put("Specify new note value" , "Bitte, einen neuen Notenwert eingeben");
        DICT.put("Id is missing, cannot continue" , "Id fehlt, kann nicht weitergehen");
        DICT.put("Copy order " , "Auftrag kopieren "); // Create new test procedure
        //
        DICT.put("The length of input exceeded the limit" , "Die Länge der Dateneigabe ist zu lang");
        DICT.put("SQL ERROR, could not update" , "SQL FEHLER, Speicherung nicht möglich");
        //
        DICT.put("Confirm deletion of marked row" , "Löschung der ausgewählten Reihe bestätigen");
        //
    }

    public static String LANG(String word) {
        return LANG_ENG ? word : DICT.getProperty(word, "*" + word);
    }


    public static String MSG_4(String status) {
        return LANG_ENG ? "The status will be changed to: " + "\"" + status + "\"" : "Diese Handlung wird den Status ändern. Soll Status: " + "\"" + status + "\"" + " akzeptiert werden?";
    }
    
    public static String MSG_6_3(String code) {
        return LANG_ENG ? "Copy code: " + code + "?" : "Code: " + code + " kopieren?";
    }
}
