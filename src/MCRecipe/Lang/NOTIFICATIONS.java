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
public class NOTIFICATIONS {

    public static String NOTE_1() {
        return LANG_ENG ? "Table row not chosen" : "Tabellenzeile nicht ausgewählt";
    }
    
    public static String NOTE_2() {
        return LANG_ENG ? "Specify new note value" : "Bitte, einen neuen Notenwert eingeben ";
    }
    
    public static String NOTE_3() {
        return LANG_ENG ? "Id is missing, cannot continue" : "Id fehlt, kann nicht weitergehen";
    }
}
