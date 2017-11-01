/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Lang;

import static MCRecipe.Lang.LNG.LANG_ENG;

/**
 *
 * @author mcab
 */
public class VENDOR_A_B {

    public static String VENDOR_A__CHOOSE_INGREDIENT_TO_START() {
        return LANG_ENG ? "Choose igredient to start" : "Wählen Sie eine Ingredienz aus";
    }

    public static String INGREDIENT_TABLE() {
        return LANG_ENG ? "INGREDIENT" : "INGREDIENZ";
    }

    public static String WAREHOUSE_TABLE() {
        return LANG_ENG ? "WAREHOUSE" : "LAGER";
    }

    public static String PURCHASE_TABLE() {
        return LANG_ENG ? "PURCHASE" : "EINKAUF";
    }

    public static String VENDOR_TABLE() {
        return LANG_ENG ? "VENDOR" : "LIEFERANT";
    }

    public static String PERSONAL_TABLE() {
        return LANG_ENG ? "PERSONNEL" : "PERSONAL";
    }
    
    public static String TRADE_NAME(){
        return LANG_ENG ? "TRADE NAME" : "HANDELSNAME";
    }
}
