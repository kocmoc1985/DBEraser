/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import BuhInvoice.BUH_INVOICE_MAIN;
import BuhInvoice.CustomersA_;

/**
 *
 * @author KOCMOC
 */
public class LANG {

    public static String MSG_1 = "Kunde inte spara data (kontrollera färgmarkerade rader)";
    public static String MSG_1_1 = "Kunde inte lägga till artikel (kontrollera färgmarkerade rader)";
    public static String MSG_2 = "Obligatoriska fält markerade med * måste vara ifyllda";
    public static String MSG_3 = "Data kommer att raderas permanent! Vill du forsätta?";
    public static String MSG_3_1 = "Fakturan kommer att raderas permanent! Vill du forsätta?";
    public static String MSG_3_2 = "Inbetalningen kommer att raderas permanent! Vill du forsätta?";
    public static String MSG_3_3 = "Radera kommentar?";
    public static String MSG_4 = "Radera rad?";
    public static String MSG_5 = "Ingen artikel vald! Markera en artikel i tabellen till höger och prova igen";
    public static String MSG_5_1 = "Alla osparade ändringar kommer att förloras! Forsätt med att skapa ny faktura?";
    public static String MSG_7 = "Kund e-post saknas, var god gå till flik"
            + " \"" + BUH_INVOICE_MAIN.TAB_KUDNER + "\" och fyll i \"" + CustomersA_.TABLE_FAKTURA_KUNDER__EPOST + "\" ";
    public static String MSG_8 = "Kommentaren är för lång och kommer därmed inte sparas";
    public static String MSG_9 = "Var god observera, fakturan är MAKULERAD";

    public static String LBL_MSG_1 = "SKAPA NY FAKTURA";
    public static String LBL_MSG_2 = "BEARBETA FAKTURA";
    public static String LBL_MSG_2_2 = "FAKTURA BETALD - REDIGERING EJ MÖJLIG";
    public static String LBL_MSG_3 = "SKAPA NY KUND";
    public static String LBL_MSG_4 = "BEARBETA KUND";
    public static String LBL_MSG_5 = "SKAPA ARTIKEL";
    public static String LBL_MSG_6 = "BEARBETA ARTIKEL";
    
    public static String LBL_MSG_2_3(String fakturaCopy) {
        return "KREDITERAR FAKTURA: " + fakturaCopy;
    }
    
    /**
     * VERY IMPORTANT: If it happens even once, actions shall be taken
     * [2020-08-06] This one shall be only used in cases when you insert an
     * entry and retrieve the newly inserted id
     */
    public static String MSG_ERROR_1 = "Uppladning misslyckades helt eller delvis";

    
    
     public static String FAKTURA_KREDIT_MSG(String fakturaCopy) {
        return "Vill du verkligen kreditera fakturanummer: " + fakturaCopy + "?";
     }
    
    public static String FAKTURA_COPY_MSG_A(String fakturaCopy) {
        return "Vill du verkligen kopiera fakturanummer: " + fakturaCopy + "?";
    }
    
    public static String FAKTURA_COPY_MSG_B(String fakturaCopy, String fakturaNew) {
        return "Du kopierade precis fakturanummer: " + fakturaCopy + ", det nya fakturanumret är: " + fakturaNew;
    }

    public static String LOGOTYP_TO_SMALL(String requiredMinWidth, String widthActual) {
        return "Logotyp är för liten, minimal bredd: " + requiredMinWidth + ", den aktuella är: " + widthActual;
    }

    public static String CONFIRM_SEND_MAIL(String sendTo) {
        return "Skicka faktura till: " + sendTo + " ?";
    }

    public static String MSG_DELETE_WARNING_ARTICLE(String fakturor) {
        return "OBS! Den valda artikeln används i följande fakturor: " + fakturor + "\n Om du raderar artikeln, raderas också de ovannämnda fakturor permanent!";
    }

    public static String MSG_DELETE_WARNING_CUSTOMER(String fakturor) {
        return "OBS! Den valda kunden används i följande fakturor: " + fakturor + "\n Om du raderar kunden, raderas också de ovannämnda fakturor permanent!";
    }

    /**
     * For cases when i don't know yet
     */
    public static String MSG_UNDEF = "?????";
}
