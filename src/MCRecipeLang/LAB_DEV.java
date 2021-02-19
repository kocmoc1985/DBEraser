/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipeLang;

import LabDev.LAB_DEV__STATUS;
import static MCRecipeLang.LNG.LANG_ENG;
import forall.HelpA;
import java.util.HashMap;
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public class LAB_DEV {

    public static void find_order_tab_translate_status(JTable table, String colName) {
        //
        for (String status : LAB_DEV__STATUS.getLabDevStatusesAuto(true)) {
            String replace = LAB_DEV__STATUS.getStatusActLang(LANG_ENG, status);
            HelpA.setValueAllRows(table, colName, status, replace);
        }
        //
    }

    public static void material_information_tab_change_jtable__header(JTable table) {
        if (LNG.LANG_ENG) {
            HelpA.changeTableHeaderTitleOfOneColumn(table, "Material", "Material");
            HelpA.changeTableHeaderTitleOfOneColumn(table, "BatchMenge", "Batch Ammount");
            HelpA.changeTableHeaderTitleOfOneColumn(table, "Misch", "Mix");
            HelpA.changeTableHeaderTitleOfOneColumn(table, "FIRSTBNO", "First batchno");
            HelpA.changeTableHeaderTitleOfOneColumn(table, "Beschreibung", "Descr");
            HelpA.changeTableHeaderTitleOfOneColumn(table, "Mischer", "Mixer");
        } else {
            HelpA.changeTableHeaderTitleOfOneColumn(table, "Material", "Material");
            HelpA.changeTableHeaderTitleOfOneColumn(table, "BatchMenge", "Chargenmenge");
            HelpA.changeTableHeaderTitleOfOneColumn(table, "Misch", "Misch");
            HelpA.changeTableHeaderTitleOfOneColumn(table, "FIRSTBNO", "Erste chargenr");
            HelpA.changeTableHeaderTitleOfOneColumn(table, "Beschreibung", "Beschreibung");
            HelpA.changeTableHeaderTitleOfOneColumn(table, "Mischer", "Mischer");
        }
    }

    public static void find_order_tab_change_jtable__header(JTable table) {
        if (LNG.LANG_ENG) {
            HelpA.changeTableHeaderTitleOfOneColumn(table, "WORDERNO", "Order");
            HelpA.changeTableHeaderTitleOfOneColumn(table, "WOSTATUS", "Status");
            HelpA.changeTableHeaderTitleOfOneColumn(table, "REQUESTER", "Requester");
            HelpA.changeTableHeaderTitleOfOneColumn(table, "CUSTOMER", "Customer");
            HelpA.changeTableHeaderTitleOfOneColumn(table, "EXPREADY", "To be delievered");
            HelpA.changeTableHeaderTitleOfOneColumn(table, "UpdatedOn", "Updated on");
            HelpA.changeTableHeaderTitleOfOneColumn(table, "UpdatedBy", "Updated by");
        } else {
            HelpA.changeTableHeaderTitleOfOneColumn(table, "WORDERNO", "Auftrag");
            HelpA.changeTableHeaderTitleOfOneColumn(table, "WOSTATUS", "Status");
            HelpA.changeTableHeaderTitleOfOneColumn(table, "REQUESTER", "Antragsteller");
            HelpA.changeTableHeaderTitleOfOneColumn(table, "CUSTOMER", "Kunde");
            HelpA.changeTableHeaderTitleOfOneColumn(table, "EXPREADY", "Fertigwunsch");
            HelpA.changeTableHeaderTitleOfOneColumn(table, "UpdatedOn", "Aktualisiert");
            HelpA.changeTableHeaderTitleOfOneColumn(table, "UpdatedBy", "Aktualisiert von");
        }
    }

    public static String test_definition_tab__get_col_1() {
        return LANG_ENG ? "Code" : "Code";
    }

    public static String test_definition_tab__get_col_2() {
        return LANG_ENG ? "Description" : "Beschreibung";
    }

    public static String test_definition_tab__get_col_3() {
        return LANG_ENG ? "Conditions" : "Bedingungen";
    }

    public static String TABLE_INVERT_NAME_3() {
        return LANG_ENG ? "SERVICES" : "DIENSTE";
    }

    public static String TABLE_INVERT_NAME_4() {
        return LANG_ENG ? "PROCESSING" : "VERARBEITUNG";
    }

    public static String TEST_CONFIG_LBL_1() {
        return LANG_ENG ? "PREPARATION METODS" : "PRÄPARATIONSMETHODEN";
    }

    public static String TEST_CONFIG_LBL_2() {
        return LANG_ENG ? "AGING METHODS" : "ALTERUNGSMETHODEN";
    }

    public static String TABLE_INVERT_NAME_5() {
        return LANG_ENG ? "TEST" : "PRÜFT";
    }

    public static String STATUS_TAB_JTABLE_1() {
        return LANG_ENG ? "SEND COPY TO" : "KOPIE SENDEN AN";
    }

    public static String STATUS_TAB_JTABLE_2() {
        return LANG_ENG ? "EXECUTED BY" : "AUSGEFÜHRT VON";
    }

    public static String TEST_CONFIG_TAB__PREP_METHOD() {
        return LANG_ENG ? "PREPARATION METHODS" : "PREPARATIONSMETHODEN";
    }

    public static String TEST_CONFIG_TAB__AGING_METHOD() {
        return LANG_ENG ? "AGING METHODS" : "ALTHERUNGSMETHODEN";
    }

    public static String LBL_1() {
        return LANG_ENG ? "Order" : "Auftrag";
    }

    public static String LBL_2() {
        return LANG_ENG ? "Status" : "Status";
    }

    public static String LBL_4() {
        return LANG_ENG ? "Requester" : "Antragsteller";
    }

    public static String LBL_5() {
        return LANG_ENG ? "Updated on" : "Aktualisiert";
    }

    public static String LBL_6() {
        return LANG_ENG ? "Updated by" : "Aktualisiert von";
    }

    public static String LBL_7() {
        return LANG_ENG ? "Recipe" : "Rezeptur";
    }

    public static String LBL_7_2() {
        return LANG_ENG ? "Code" : "Code";
    }

    public static String LBL_8() {
        return LANG_ENG ? "Description" : "Beschreibung";
    }

    public static String LBL_9() {
        return LANG_ENG ? "Batches" : "Chargenmenge";
    }

}
