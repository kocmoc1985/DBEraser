/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import forall.HelpA;
import javax.swing.JTabbedPane;

/**
 *
 * @author KOCMOC
 */
public class LANG {

    public static boolean LANG_ENG = true;
    //
    public static String RECIPE_OVERVIEW__RECIPE_ORIGIN;

    public static void GO(JTabbedPane jtp) {
        TAB_NAMES(jtp);
    }
    
    //=======================RECIPE_OVEVIEW=====================================

    public static String RECIPE_OVERVIEW__RECIPE_ORIGIN() {
        return LANG_ENG ? "RECIPE ORIGIN" : "REZEPT ORIGINEL";
    }
    
    public static String RECIPE_OVERVIEW__RECIPE_STAGE() {
        return LANG_ENG ? "RECIPE STAGE" : "REZEPT PHASE";
    }
    
    public static String RECIPE_OVERVIEW__RESET_BOXES_BTN_TOOLTIP() {
        return LANG_ENG ? "Clear all" : "Alle reinigen";
    }
    
    //=======================RECIPE_DETAILED=====================================
     public static String RECIPE_DETAILED__DELETE_RECIPE_BTN_TOOLTIP() {
        return LANG_ENG ? "Delete recipe" : "Rezept löschen";
    }
    
    //=======================VENDOR A===========================================
    
     public static String VENDOR_A__CHOOSE_INGREDIENT_TO_START() {
        return LANG_ENG ? "Choose igredient to start" : "Wählen Sie eine Ingredienz aus";
    }

    private static void TAB_NAMES(JTabbedPane jtp) {
        if (LANG_ENG == false) {
            HelpA.changeTabName(jtp, MC_RECIPE.HOME_TAB, "*START");
            HelpA.changeTabName(jtp, MC_RECIPE.RECIPE_INITIAL_TAB, "REZEPT ÜBERSICHT");
            HelpA.changeTabName(jtp, MC_RECIPE.RECIPE_DETAILED_TAB, "REZEPT ERWEITERT");
            HelpA.changeTabName(jtp, MC_RECIPE.INGREDIENTS_TAB, "INGREDIENZEN");
            HelpA.changeTabName(jtp, MC_RECIPE.VENDORS_TAB, "LIEFERANT A");
            HelpA.changeTabName(jtp, MC_RECIPE.VENDORS_B_TAB, "LIEFERANT B");
            HelpA.changeTabName(jtp, MC_RECIPE.SEQUENCE_TAB, "SEQUENZ");
            HelpA.changeTabName(jtp, MC_RECIPE.RECIPE_ADD_TAB, "REZEPT ZUSATZ");
        }

    }

    public static String RECIPE_OVERVIEW() {
        if (LANG_ENG) {
            return "RECIPE_OVERVIEW";
        } else {
            return "REZEPT_ÜBERSICHT";
        }
    }
}
