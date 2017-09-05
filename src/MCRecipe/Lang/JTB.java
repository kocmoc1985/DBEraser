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
public class JTB {

    private static final Properties DICT = new Properties();

    static {
        DICT.put("Recipe Version", "Rezept Version");
        DICT.put("Add.", "Rezept erweitert");
        DICT.put("Description", "Beschreibung");
        DICT.put("Group", "Gruppe");
        DICT.put("Status", "Status");
        DICT.put("Class", "Kategorie");
        DICT.put("Mixer", "Mischer");
        DICT.put("Updated On", "Aktualisiert am");
        DICT.put("Updated By", "Aktualisiert von");
        DICT.put("Created On", "Erstellt am");
        DICT.put("Created By", "Erstellt von");
    }

    public static String LANG(String word) {
        return LANG_ENG ? word : DICT.getProperty(word, "n/a");
    }
}
