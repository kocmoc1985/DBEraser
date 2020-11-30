/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Lang;

import static MCRecipe.Lang.LNG.LANG_ENG;
import forall.HelpA;
import javax.swing.JTabbedPane;

/**
 *
 * @author KOCMOC
 */
public class RECIPE_OVERVIEW_ {

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
        return LANG_ENG ? "INGREDIENT 2" : "INGREDIENZ 2";
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

    //<===Company = QEW====>
    public static String RECIPE_OVERVIEW__COLOR() {
        return LANG_ENG ? "COLOR" : "FARBE";
    }

    public static String RECIPE_OVERVIEW__INDUSTRY() {
        return LANG_ENG ? "INDUSTRY" : "INDUSTRIE";
    }

    public static String RECIPE_TYPE() {
        return LANG_ENG ? "RECIPE TYPE" : "REZEPT TYP";
    }

    public static String CURING_SYSTEM() {
        return LANG_ENG ? "CURING SYSTEM" : "VULK. SYSTEM";
    }

    public static String CURING_PROCESS() {
        return LANG_ENG ? "CURING PROCESS" : "VULK. PROZESS";
    }

    public static String FILTER_TYPE() {
        return LANG_ENG ? "FILTER TYPE" : "FILTER TYP";
    }

    public static String CERTIFICATE() {
        return LANG_ENG ? "CERTIFICATE" : "ZERTIFIKAT";
    }

    public static String SHELF_LIFE_MIN() {
        return LANG_ENG ? "SHELF LIFE MIN" : "HALTBARKEIT MIN";
    }

    public static String SHELF_LIFE_MAX() {
        return LANG_ENG ? "SHELF LIFE MAX" : "HALTBARKEIT MAX";
    }

    public static String HARDNESS_SHA_MIN() {
        return LANG_ENG ? "HARDNESS SHA MIN" : "HÄRTE SHA MIN";
    }

    public static String HARDNESS_SHA_MAX() {
        return LANG_ENG ? "HARDNESS SHA MAX" : "HÄRTE SHA MAX";
    }

    public static String CUSTOMER() {
        return LANG_ENG ? "CUSTOMER" : "KUNDE";
    }
    //</===Company = QEW====>

    //<===Company = CP====>
    public static String KOST_VON() {
        return LANG_ENG ? "COST FROM" : "KOSTEN VON";
    }

    public static String KOST_BIS() {
        return LANG_ENG ? "COST TO" : "KOSTEN BIS";
    }

    public static String VK_PREIS_VON() {
        return LANG_ENG ? "VK-PRICE FROM" : "VK-PREIS VON";
    }

    public static String VK_PREIS_BIS() {
        return LANG_ENG ? "VK-PRICE TO" : "VK-PREIS BIS";
    }

    public static String DICHTE_VON() {
        return LANG_ENG ? "DENSITY FROM" : "DICHTE VON";
    }

    public static String DICHTE_BIS() {
        return LANG_ENG ? "DENSITY TO" : "DICHTE BIS";
    }

    public static String CHARGE_KG_VON() {
        return LANG_ENG ? "BATCH FROM" : "CHARGE VON";
    }

    public static String CHARGE_KG_BIS() {
        return LANG_ENG ? "BATCH TO" : "CHARGE BIS";
    }
    
    public static String HALTBARKEIT_VON() {
        return LANG_ENG ? "SHELFLIFE FROM" : "HALTBARKEIT VON";
    }
    
    public static String HALTBARKEIT_BIS() {
        return LANG_ENG ? "SHELFLIFE TO" : "HALTBARKEIT BIS";
    }
    
    public static String HÄRTE_VON() {
        return LANG_ENG ? "HARDNESS FROM" : "HÄRTE VON";
    }
    
    public static String HÄRTE_BIS() {
        return LANG_ENG ? "HARDNESS TO" : "HÄRTE BIS";
    }

    //</===Company = CP====>
}
