/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Lang;

import forall.HelpA_;
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
    public static final String INGREDIENTS_TAB_B = "INGREDIENTS B";
    public static final String VENDORS_TAB = "VENDORS";
    public static final String VENDORS_B_TAB = "VENDORS B";
    public static final String SEQUENCE_TAB = "SEQUENCE";
    public static final String RECIPE_ADD_TAB = "RECIPE ADD.";
    public static final String TEST_PARAMETERS_TAB = "TEST PARAMETERS";
    public static final String LABDEVELOPMENT_TAB = "LAB DEVELOPMENT";
    public static final String LOG_TAB = "LOG";
    //
    public static final String LABDEVELOPMENT_TAB__TAB_FIND_ORDER = "FIND ORDER";
    public static final String LABDEVELOPMENT_TAB__TAB_MAIN_DATA = "MAIN DATA";
    public static final String LABDEVELOPMENT_TAB__TAB_STATUS = "STATUS";
    public static final String LABDEVELOPMENT_TAB__TAB_NOTES = "NOTES";
    public static final String LABDEVELOPMENT_TAB__TAB_MATERIALINFO = "MATERIAL INFO";
    public static final String LABDEVELOPMENT_TAB__TAB_TEST_DEFINITIONS = "TEST DEFINITIONS";
    //
    public static String HOME_TAB_DE = "*START";
    public static String RECIPE_INITIAL_TAB_DE = "REZEPT ÜBERSICHT";
    public static String RECIPE_DETAILED_TAB_DE = "REZEPT ERWEITERT";
    public static String INGREDIENTS_TAB_DE = "INGREDIENZEN";
    public static String INGREDIENTS_TAB_DE_B = "INGREDIENZEN B";
    public static String VENDORS_TAB_DE = "LIEFERANT A";
    public static String VENDORS_B_TAB_DE = "LIEFERANT B";
    public static String SEQUENCE_TAB_DE = "SEQUENZ";
    public static String RECIPE_ADD_TAB_DE = "REZEPT ZUSATZ";
    public static final String TEST_PARAMETERS_TAB_DE = "PRÜFUNGEN";
    public static final String LABDEVELOPMENT_TAB_DE = "LAB ENTWICKLUNG";
    //
    public static final String LABDEVELOPMENT_TAB__TAB_FIND_ORDER_DE = "AUFTRAG FINDEN";
    public static final String LABDEVELOPMENT_TAB__TAB_MAIN_DATA_DE = "KOPFDATEN";
    public static final String LABDEVELOPMENT_TAB__TAB_STATUS_DE = "STATUS";
    public static final String LABDEVELOPMENT_TAB__TAB_NOTES_DE = "NOTIZEN";
    public static final String LABDEVELOPMENT_TAB__TAB_MATERIALINFO_DE = "MATERIALINFORMATION";
    public static final String LABDEVELOPMENT_TAB__TAB_TEST_DEFINITIONS_DE = "PRÜFDEFINITIONEN";
    

    public static void GO(JTabbedPane jtp, JTabbedPane jtp_lab_dev) {
        TAB_NAMES_GENERAL(jtp);
        TAB_NAMES_LABDEV(jtp_lab_dev);
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

    public static String INGREDIENTS_TAB_B() {
        return LANG_ENG ? INGREDIENTS_TAB_B : INGREDIENTS_TAB_DE_B;
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

    public static String LAB_DEVELOPMENT_TAB() {
        return LANG_ENG ? LABDEVELOPMENT_TAB : LABDEVELOPMENT_TAB_DE;
    }

    public static String LAB_DEVELOPMENT_TAB__TAB_MAIN_DATA() {
        return LANG_ENG ? LABDEVELOPMENT_TAB__TAB_MAIN_DATA : LABDEVELOPMENT_TAB__TAB_MAIN_DATA_DE;
    }

    public static String LAB_DEVELOPMENT_TAB__TAB_STATUS() {
        return LANG_ENG ? LABDEVELOPMENT_TAB__TAB_STATUS : LABDEVELOPMENT_TAB__TAB_STATUS_DE;
    }

    public static String LAB_DEVELOPMENT_TAB__TAB_NOTES() {
        return LANG_ENG ? LABDEVELOPMENT_TAB__TAB_NOTES : LABDEVELOPMENT_TAB__TAB_NOTES_DE;
    }

    public static String LAB_DEVELOPMENT_TAB__TAB_MATERIALINFO() {
        return LANG_ENG ? LABDEVELOPMENT_TAB__TAB_MATERIALINFO : LABDEVELOPMENT_TAB__TAB_MATERIALINFO_DE;
    }

    public static String LAB_DEVELOPMENT_TAB__TAB_FIND_ORDER() {
        return LANG_ENG ? LABDEVELOPMENT_TAB__TAB_FIND_ORDER : LABDEVELOPMENT_TAB__TAB_FIND_ORDER_DE;
    }
    
    public static String LAB_DEVELOPMENT_TAB__TAB_TEST_DEFINITION() {
        return LANG_ENG ? LABDEVELOPMENT_TAB__TAB_TEST_DEFINITIONS : LABDEVELOPMENT_TAB__TAB_TEST_DEFINITIONS_DE;
    }

    private static void TAB_NAMES_GENERAL(JTabbedPane jtp) {
        if (LANG_ENG == false) {
            HelpA_.changeTabName(jtp, HOME_TAB, HOME_TAB_DE);
            HelpA_.changeTabName(jtp, RECIPE_INITIAL_TAB, RECIPE_INITIAL_TAB_DE);
            HelpA_.changeTabName(jtp, RECIPE_DETAILED_TAB, RECIPE_DETAILED_TAB_DE);
            HelpA_.changeTabName(jtp, INGREDIENTS_TAB, INGREDIENTS_TAB_DE);
            HelpA_.changeTabName(jtp, INGREDIENTS_TAB_B, INGREDIENTS_TAB_DE_B);
            HelpA_.changeTabName(jtp, VENDORS_TAB, VENDORS_TAB_DE);
            HelpA_.changeTabName(jtp, VENDORS_B_TAB, VENDORS_B_TAB_DE);
            HelpA_.changeTabName(jtp, SEQUENCE_TAB, SEQUENCE_TAB_DE);
            HelpA_.changeTabName(jtp, RECIPE_ADD_TAB, RECIPE_ADD_TAB_DE);
            HelpA_.changeTabName(jtp, TEST_PARAMETERS_TAB, TEST_PARAMETERS_TAB_DE);
            HelpA_.changeTabName(jtp, LABDEVELOPMENT_TAB, LABDEVELOPMENT_TAB_DE);
            //
        }

    }

    private static void TAB_NAMES_LABDEV(JTabbedPane jtp) {
        //
        if (LANG_ENG == false) {
            //
            HelpA_.changeTabName(jtp, LABDEVELOPMENT_TAB__TAB_FIND_ORDER, LABDEVELOPMENT_TAB__TAB_FIND_ORDER_DE);
            HelpA_.changeTabName(jtp, LABDEVELOPMENT_TAB__TAB_MAIN_DATA, LABDEVELOPMENT_TAB__TAB_MAIN_DATA_DE);
            HelpA_.changeTabName(jtp, LABDEVELOPMENT_TAB__TAB_STATUS, LABDEVELOPMENT_TAB__TAB_STATUS_DE);
            HelpA_.changeTabName(jtp, LABDEVELOPMENT_TAB__TAB_NOTES, LABDEVELOPMENT_TAB__TAB_NOTES_DE);
            HelpA_.changeTabName(jtp, LABDEVELOPMENT_TAB__TAB_MATERIALINFO, LABDEVELOPMENT_TAB__TAB_MATERIALINFO_DE);
            HelpA_.changeTabName(jtp, LABDEVELOPMENT_TAB__TAB_TEST_DEFINITIONS, LABDEVELOPMENT_TAB__TAB_TEST_DEFINITIONS_DE);
            //
        }
        //
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
