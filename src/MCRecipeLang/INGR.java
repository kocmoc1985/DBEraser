/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipeLang;

import static MCRecipeLang.LNG.LANG_ENG;

/**
 *
 * @author KOCMOC
 */
public class INGR {

    public static String INGRED_DETAILED_TBL() {
        return LANG_ENG ? "INGRED. DETAILED" : "INGRED. ERWEITERT";//INGRED. ERWEITERT
    }

    public static String EXPRESS_INFO_TBL() {
        return LANG_ENG ? "EXPRESS INFO" : "EXPRESS-INFO";
    }

    public static String WAREHOUSE_TBL() {
        return LANG_ENG ? "WAREHOUSE" : "LAGER";
    }

    public static String NOTES_TBL() {
        return LANG_ENG ? "NOTES" : "NOTIZEN";
    }

    public static String NOTES_DETAILED_TBL() {
        return LANG_ENG ? "NOTE DETAILED" : "NOTIZEN DETALLIERT";
    }

    //====
    public static String NAME() {
        return LANG_ENG ? "NAME" : "CODE";
    }

    public static String DESCR() {
        return LANG_ENG ? "DESCR" : "BESCHREIBUNG";
    }

    public static String STATUS() {
        return LANG_ENG ? "STATUS" : "STATUS";
    }

    public static String CAS_NR() {
        return LANG_ENG ? "CAS NR" : "CAS NR";
    }

    public static String VENDOR() {
        return LANG_ENG ? "VENDOR" : "LIEFERANT";
    }

    public static String GROUP() {
        return LANG_ENG ? "GROUP" : "GRUPPE";
    }

    public static String CLASS() {
        return LANG_ENG ? "CLASS" : "KATEGORIE";
    }

    public static String FORM() {
        return LANG_ENG ? "FORM" : "FORM";
    }

    public static String TRADE_NAME() {
        return LANG_ENG ? "TRADE NAME" : "HANDELSNAME";
    }

    public static String PERC_RUBBER() {
        return LANG_ENG ? "PERC RUBBER" : "KAUTSCHUK %";
    }
    //

    public static String PASTE_INGRED_1_BTN() {
        return LANG_ENG ? "PASTE INGRED 1" : "INGREDIENZ 1 -> R. ÜBERSICHT";
    }

    public static String PASTE_INGRED_1_BTN_TOOLTIP() {
        return LANG_ENG ? "Paste ingred 1 to RECIPE OVERVIEW" : "Ingredienz 1 ins REZEPT ÜBERSICHT einfügen";
    }

    public static String PASTE_INGRED_2_BTN() {
        return LANG_ENG ? "PASTE INGRED 2" : "INGREDIENZ 2 -> R. ÜBERSICHT";
    }

    public static String PASTE_INGRED_2_BTN_TOOLTIP() {
        return LANG_ENG ? "Paste ingred 2 to RECIPE OVERVIEW" : "Ingredienz 2 ins REZEPT ÜBERSICHT einfügen";
    }

    public static String ADD_INGREDIENT_TO_R_DETAILED() {
        return LANG_ENG ? "ADD INGR. TO R. DETAILED" : "INGREDIENZ -> R. ERWEITERT";
    }

    public static String ADD_INGREDIENT_TO_R_DETAILED_TOOLTIP() {
        return LANG_ENG ? "Add ingredient to RECIPE DETAILED -> Recipe Manager" : "Ingredienz nach REZEPT ERWEITERT verscheiben (REZEPT VERWALTUNG)";
    }
    
     public static String CINFIRM_MSG_1() {
        return LANG_ENG ? "Caution!\nThis action will add this igredient to the opened recipe, do you want to proceed?" : "Achtung!\nDiese Operation ergänzt diese Ingredienz zum geöffneten Rezept, möchten Sie es machen?";
    }
}
