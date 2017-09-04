/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Lang;

import MCRecipe.MC_RECIPE;
import forall.HelpA;
import javax.swing.JTabbedPane;

/**
 *
 * @author mcab
 */
public class GENERAL {

    public static boolean LANG_ENG = false;
    //

    public static void GO(JTabbedPane jtp) {
        TAB_NAMES_GENERAL(jtp);
    }
    
    private static void TAB_NAMES_GENERAL(JTabbedPane jtp) {
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
}
