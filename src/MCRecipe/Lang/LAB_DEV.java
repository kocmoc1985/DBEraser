/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Lang;

import static MCRecipe.Lang.LNG.LANG_ENG;
import forall.HelpA_;
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public class LAB_DEV {

    public static void find_order_tab_change_jtable__header(JTable table) {
        if (LNG.LANG_ENG) {
            HelpA_.changeTableHeaderTitleOfOneColumn(table, "WORDERNO", "ORDER");
            HelpA_.changeTableHeaderTitleOfOneColumn(table, "WOSTATUS", "STATUS");
            HelpA_.changeTableHeaderTitleOfOneColumn(table, "REQUESTER", "REQUESTER");
            HelpA_.changeTableHeaderTitleOfOneColumn(table, "CUSTOMER", "CUSTOMER");
            HelpA_.changeTableHeaderTitleOfOneColumn(table, "EXPREADY", "TO BE DELIEVERED");
            HelpA_.changeTableHeaderTitleOfOneColumn(table, "UpdatedOn", "UPDATED ON");
            HelpA_.changeTableHeaderTitleOfOneColumn(table, "UpdatedBy", "UPDATED BY");
        } else {
            HelpA_.changeTableHeaderTitleOfOneColumn(table, "WORDERNO", "AUFTRAG");
            HelpA_.changeTableHeaderTitleOfOneColumn(table, "WOSTATUS", "STATUS");
            HelpA_.changeTableHeaderTitleOfOneColumn(table, "REQUESTER", "ANTRAGSTELLER");
            HelpA_.changeTableHeaderTitleOfOneColumn(table, "CUSTOMER", "KUNDE");
            HelpA_.changeTableHeaderTitleOfOneColumn(table, "EXPREADY", "FERTIGWUNSCH");
            HelpA_.changeTableHeaderTitleOfOneColumn(table, "UpdatedOn", "AKTUALISIERT");
            HelpA_.changeTableHeaderTitleOfOneColumn(table, "UpdatedBy", "AKTUALISIERT VON");
        }
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

}
