/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Lang;

import static MCRecipe.Lang.GENERAL.LANG_ENG;
import forall.HelpA;
import javax.swing.JTabbedPane;

/**
 *
 * @author KOCMOC
 */
public class RECIPE_OVERVIEW {

    //=======================RECIPE_OVEVIEW=====================================
    public static void RECIPE_OVERVIEW_TABS(JTabbedPane jtp) {
        if (LANG_ENG == false) {
            HelpA.changeTabName(jtp, "NOTES", "NOTIZEN");
            HelpA.changeTabName(jtp, "NOTES DETAILS", "NOTIZEN DETALLIERT");

        }
    }

    public static String RECIPE_OVERVIEW__RECIPE_ORIGIN() {
        return LANG_ENG ? "RECIPE ORIGIN" : "REZEPT ORIGINEL";
    }

    public static String RECIPE_OVERVIEW__RECIPE_STAGE() {
        return LANG_ENG ? "RECIPE STAGE" : "REZEPT PHASE";
    }

    public static String RECIPE_OVERVIEW__RECIPE_VERSION() {
        return LANG_ENG ? "RECIPE VERSION" : "REZEPT VERSION";
    }

    public static String RECIPE_OVERVIEW__RECIPE_ADDITIONAL() {
        return LANG_ENG ? "RECIPE ADDITIONAL" : "REZEPT ZUSATZ";
    }

    public static String RECIPE_OVERVIEW__RECIPE_DESCRIPTION() {
        return LANG_ENG ? "RECIPE DESCRIPTION" : "REZEPT BESCHREIBUNG";
    }

    public static String RECIPE_OVERVIEW__POLYMER_GROUP() {
        return LANG_ENG ? "POLYMER GROUP" : "POLYMER GRUPPE";
    }

    public static String RECIPE_OVERVIEW__MIXER() {
        return LANG_ENG ? "MIXER" : "MISCHER";
    }

    public static String RECIPE_OVERVIEW__STATUS() {
        return LANG_ENG ? "STATUS" : "STATUS";
    }

    public static String RECIPE_OVERVIEW__CLASS() {
        return LANG_ENG ? "CLASS" : "KATEGORIE";
    }

    //===
    public static String RECIPE_OVERVIEW__INGREDIENTS_CHKBOX() {
        return LANG_ENG ? "Ingredients" : "INGREDIENZEN";
    }

    public static String RECIPE_OVERVIEW__OR_CHKBOX() {
        return LANG_ENG ? "OR" : "ODER";
    }

    //===
    public static String RECIPE_OVERVIEW__INGREDIENT_1() {
        return LANG_ENG ? "INGREDIENT 1" : "INGREDIENZ 1";
    }

    public static String RECIPE_OVERVIEW__INGREDIENT_2() {
        return LANG_ENG ? "INGREDIENT 2" : "INGREDIENZ 1";
    }

    //===
    public static String RECIPE_OVERVIEW__RECIPE_SET() {
        return LANG_ENG ? "RECIPES SET" : "SATZ DER REZEPTE";
    }

    public static String RECIPE_OVERVIEW__RECIPE_INFO() {
        return LANG_ENG ? "INFO" : "INFO";
    }

    public static String RECIPE_OVERVIEW__SORT_BY_NOTE_VALUE() {
        return LANG_ENG ? "SORT BY NOTE VALUE" : "SORTIEREN NACH NOTENWERT";
    }

    //===
    public static String RECIPE_OVERVIEW__COLOR() {
        return LANG_ENG ? "COLOR" : "FARBE";
    }

    public static String RECIPE_OVERVIEW__INDUSTRY() {
        return LANG_ENG ? "INDUSTRY" : "INDUSTRIE";
    }
    

}
