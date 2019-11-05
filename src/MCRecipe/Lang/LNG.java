/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Lang;

import MCRecipe.MC_RECIPE_;
import forall.HelpA;
import javax.swing.JTabbedPane;

/**
 *
 * @author mcab
 */
public class LNG {
    //
    public static boolean LANG_ENG = true;
    //
    public static final String HOME_TAB = "*HOME";
    public static final String RECIPE_INITIAL_TAB = "RECIPE OVERVIEW";
    public static final String RECIPE_DETAILED_TAB = "RECIPE DETAILED";
    public static final String INGREDIENTS_TAB = "INGREDIENTS";
    public static final String VENDORS_TAB = "VENDORS";
    public static final String VENDORS_B_TAB = "VENDORS B";
    public static final String SEQUENCE_TAB = "SEQUENCE";
    public static final String RECIPE_ADD_TAB = "RECIPE ADD.";
    public static final String TEST_PARAMETERS_TAB = "TEST PARAMETERS";
    public static final String LOG_TAB = "LOG";
    //
    public static String HOME_TAB_DE = "*START";
    public static String RECIPE_INITIAL_TAB_DE = "REZEPT ÜBERSICHT";
    public static String RECIPE_DETAILED_TAB_DE = "REZEPT ERWEITERT";
    public static String INGREDIENTS_TAB_DE = "INGREDIENZEN";
    public static String VENDORS_TAB_DE = "LIEFERANT A";
    public static String VENDORS_B_TAB_DE = "LIEFERANT B";
    public static String SEQUENCE_TAB_DE = "SEQUENZ";
    public static String RECIPE_ADD_TAB_DE = "REZEPT ZUSATZ";
    public static final String TEST_PARAMETERS_TAB_DE = "PRÜFUNGEN";
    //

    public static void GO(JTabbedPane jtp) {
        TAB_NAMES_GENERAL(jtp);
    }

    public static String RECIPE_INITIAL_TAB() {
        return LANG_ENG ? RECIPE_INITIAL_TAB : RECIPE_INITIAL_TAB_DE;
    }

    public static String RECIPE_DETAILED_TAB() {
        return LANG_ENG ? RECIPE_DETAILED_TAB : RECIPE_DETAILED_TAB_DE;
    }

    public static String INGREDIENTS_TAB() {
        return LANG_ENG ? INGREDIENTS_TAB : INGREDIENTS_TAB_DE;
    }

    public static String VENDORS_TAB() {
        return LANG_ENG ? VENDORS_TAB : VENDORS_TAB_DE;
    }

    public static String VENDORS_TAB_B() {
        return LANG_ENG ? VENDORS_B_TAB : VENDORS_B_TAB_DE;
    }

    public static String SEQUENCE_TAB() {
        return LANG_ENG ? SEQUENCE_TAB : SEQUENCE_TAB_DE;
    }

    public static String RECIPE_ADD_TAB() {
        return LANG_ENG ? RECIPE_ADD_TAB : RECIPE_ADD_TAB_DE;
    }
    
    public static String TEST_PARAMS_TAB() {
        return LANG_ENG ? TEST_PARAMETERS_TAB : TEST_PARAMETERS_TAB_DE;
    }

    private static void TAB_NAMES_GENERAL(JTabbedPane jtp) {
        if (LANG_ENG == false) {
            HelpA.changeTabName(jtp, HOME_TAB, HOME_TAB_DE);
            HelpA.changeTabName(jtp, RECIPE_INITIAL_TAB, RECIPE_INITIAL_TAB_DE);
            HelpA.changeTabName(jtp, RECIPE_DETAILED_TAB, RECIPE_DETAILED_TAB_DE);
            HelpA.changeTabName(jtp, INGREDIENTS_TAB, INGREDIENTS_TAB_DE);
            HelpA.changeTabName(jtp, VENDORS_TAB, VENDORS_TAB_DE);
            HelpA.changeTabName(jtp, VENDORS_B_TAB, VENDORS_B_TAB_DE);
            HelpA.changeTabName(jtp, SEQUENCE_TAB, SEQUENCE_TAB_DE);
            HelpA.changeTabName(jtp, RECIPE_ADD_TAB, RECIPE_ADD_TAB_DE);
            HelpA.changeTabName(jtp, TEST_PARAMETERS_TAB, TEST_PARAMETERS_TAB_DE);
        }

    }
    
    //=========
     public static String USERS() {
        return LANG_ENG ? "USERS" : "ANWENDER";
    }
    
    public static String USER_NAME() {
        return LANG_ENG ? "USER NAME" : "BENUTZERNAME";
    }
    
     public static String PASSWORD() {
        return LANG_ENG ? "PASSWORD" : "KENNWORT";
    }
     
     public static String ADMIN_TOOLS() {
        return LANG_ENG ? "ADMINISTRATION TOOLS" : "VERWALTUNGSWERKZEUGE";
    }
     
     
}
