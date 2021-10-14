/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import BuhInvoice.LAFakturering;
import BuhInvoice.CustomersA_;
import BuhInvoice.GP_BUH;
import BuhInvoice.HTMLPrint;
import BuhInvoice.HTMLPrint_A;
import BuhInvoice.HTMLPrint_B;
import BuhInvoice.HelpBuh;
import icons.IconUrls;
import java.io.File;
import java.net.URL;

/**
 *
 * @author KOCMOC
 */
public class LANG {

    public final static String INTERNET_CONNECTION_MISSING = "Kan ej starta LAFakturering. Internetanslutning saknas!";
    //
    private final static String Fakturan = "Fakturan";
    private final static String Faktura = "Faktura";
    private final static String fakturan = "fakturan";
    private final static String faktura = "faktura";
    private final static String Offerten = "Offerten";
    private final static String Offert = "Offert";
    private final static String offerten = "offerten";
    private final static String offert = "offert";

    //
    public final static String MESSAGE = "Meddelande";
    //
    public final static String VALIDATION_MSG_1 = "Inloggning misslyckades";
    public final static String VALIDATION_MSG__V_ERR_0 = "Autentiseringsproblem";
    public final static String VALIDATION_MSG__V_ERR_01 = "Fel användarnamn";
    public final static String VALIDATION_MSG__V_ERR_02 = "Fel lösenord";
    public final static String VALIDATION_MSG__V_ERR_03 = "Din IP-Adress är förbjuden";

    public final static String OMVANDLADES_TILL = "omvandlades till fknr ";
    public final static String SKAPADES_FRAN = "skapades från fknr ";
    public final static String NY_FAKTURANR = "ny fknr ";
    public final static String KREDITERAR = "krediterar fknr ";
    
    
    
    public static final String getInloggningsMsg(String customerCompanyName) {
        return "Inloggad som: " + customerCompanyName;
    }

    public final static String FAKTURA = "Faktura";
    public final static String KONTANT_FAKTURA = "Kvitto";
    public final static String KREDIT_FAKTURA = "Kreditfaktura";
    public final static String PAMINNELSE = "Påminnelse";
    public final static String OFFERT = "Offert";

    public final static String ATT_BETALA = "Att betala";
    public final static String ATT_ERHALLA = "Att erhålla";

    public final static String FRAME_TITLE_1 = "Skriv ut"; //+ FAKTURA.toLowerCase()
    public final static String FRAME_TITLE_1_2 = "Skriv ut " + PAMINNELSE.toLowerCase();
    public final static String FRAME_TITLE_1_3 = "Förhandsgranska";

    public final static String MSG_1 = "Kunde inte spara data (kontrollera färgmarkerade rader)";
    public final static String MSG_1_1 = "Kunde inte lägga till artikel (kontrollera färgmarkerade rader)";
    public final static String MSG_2 = "Obligatoriska fält markerade med * måste vara ifyllda";
    public final static String MSG_3 = "Data kommer att raderas permanent! Vill du fortsätta?";
    public final static String MSG_3_1 = " kommer att raderas permanent! Vill du fortsätta?";

    public static final String MSG_3_1(boolean offert) {
        return offert ? Offerten + MSG_3_1 : Fakturan + MSG_3_1;
    }
    public final static String MSG_3_2 = "Inbetalningen kommer att raderas permanent! Vill du fortsätta?";
    public final static String MSG_3_2_2 = "Ingen inbetalning vald! Markera en i tabellen nedan och prova igen";
    public final static String MSG_3_3 = "Radera kommentar?";
    public final static String MSG_4 = "Radera rad?";
    public final static String MSG_4_2 = "Radera konto delning?";
    public final static String MSG_4_3 = "OBS! Kontot och all relaterat data kommer att raderas permanent, vill du fortsätta ändå?";
    public final static String MSG_4_4 = "Ange lösenordet som du loggar in med!";
    public final static String MSG_5 = "Ingen artikel vald! Markera en artikel i tabellen till höger och prova igen";
    public final static String MSG_5_1 = "Alla osparade ändringar kommer att förloras! Forsätt med att skapa ny faktura?";
    public final static String MSG_5_2 = "Du kan inte lägga till fler artiklar!";
    public final static String MSG_7 = "Kund e-post saknas, var god gå till flik"
            + " \"" + LAFakturering.TAB_KUDNER + "\" och fyll i \"" + CustomersA_.TABLE_FAKTURA_KUNDER__EPOST + "\" ";
    public final static String MSG_7_2 = "Skriv en e-post du vill skicka den till";
    public final static String MSG_8 = "Kan ej spara, kommentaren är för lång";
    public final static String MSG_8_2 = "Kan ej spara, anteckningen är för lång";
    public final static String MSG_9 = "Var god observera, fakturan är MAKULERAD";

    public static String MSG_10 = "Skapar PDF";
    public static String MSG_10_1 = "Laddar upp";
    public static String MSG_10_2 = "Filen skickad!";
    public static String MSG_10_3 = "Filen ej skickad!";

    public static final String MSG_10_4(boolean offert) {
        return offert ? Offerten + MSG_10_4 : Fakturan + MSG_10_4;
    }

    public static final String MSG_10_5(boolean offert) {
        String part1 = "Vill du att ";
        String part2 = " ska markeras som skickad?";
        return offert ? part1 + offerten + part2 : part1 + fakturan + part2;
    }

    private static String MSG_10_4 = " markerad som skickad med vanlig post!";

    private static String MSG_11 = "Du saknar registrerade kunder, var god gå till fliken " + LAFakturering.TAB_KUDNER + " och registrera en.";
    public static String MSG_12 = "Var god observera, fakturan är betald eller delvis betald!";
    public static String MSG_12_2 = "Var god observera, fakturan har inte förfallit till betalning!";

    public static String MSG_14 = "För många tecken, max antal:";
    public static String MSG_14_2 = "Det angivna värdet finns redan";

    public static String MSG_15 = "E-Post inställningar sparades";
    public static String MSG_15_2 = "E-Post inställningar kunde ej sparas";

    public static String MSG_15_3 = "SMTP inställningar saknas eller är felaktiga";
    public static String MSG_15_4 = "E-Post skickades, SMTP inställningar fungerar!";
    public static String MSG_15_5 = "E-Post skickades ej, prova att ändra inställningar";

    public static String MSG_16_0_0 = "Ditt konto är nu skapat!";
    public static String MSG_16_0_1 = "Vi har skickat lösenordet till den angivna E-posten.<br>Kolla \"skräppost\" om du inte hittar E-post från LAFakturering inom 5-10 minuter.";

    public static String MSG_16_0_3_HTML() {
        return HTML_MSG_BASIC(IconUrls.THUMB_UP_ICON_URL, MSG_16_0_0, MSG_16_0_1); // [#HTML-DIALOG#]
    }

    public static String HTML_MSG_BASIC(URL mainImage, String title, String text) {
        //[#HTML-DIALOG#]
        String imgPath = mainImage.toString();
        //
        return "<html>"
                + "<body style='background-color:#F1F3F6'>" //style='background-color:#F1F3F6'
                + "<div style='margin-left:10px;color:gray;background-color:#EEF0F4;padding:5 5 5 5px;'>"
                + "<table>"
                + "<tr>"
                + "<td style='width:5%'><img src='" + imgPath + "' alt='LAFakturering' width='32' height='32' >" + "</td>"
                + "<td><h1>" + title + "</h1></td>"
                + "</tr>"
                + "</table>"
                //
                + "<table style='font-size:14px'>"
                + "<tr><td>" + text + "</td></tr>"
                + "</table>"
                + "</div>"
                //
                + "</body>"
                + "</html>";
    }

    public static String MSG_16 = "Kontot kunde ej skapas, anledning oklar";
    public static String MSG_16_1 = "Kunde ej skapa, den angivna användaren finns redan";
    public static String MSG_16_2 = "Det är inte möjligt att skapa fler konton";

    public static String MSG_17 = "Logotypen finns redan, vill du radera den befintliga?";

    public static String MSG_18_0 = "Återställning lyckades, kolla din e-post för det nya lösenordet. \nKontrollera \"skräppost\" om du inte hittar mejlet från LAFakturering.";
    public static String MSG_18 = "Återställning misslyckades, den angivna användaren finns ej";
    public static String MSG_18_1 = "Ett okänt fel inträffade, återställning misslyckades";

    public static String MSG_20_0 = "Delning av konto lyckades, e-post med lösenordet har skickats";
    public static String MSG_20 = "Delning av konto misslyckades, anledning oklar";
    public static String MSG_20_1 = "Delning av konto misslyckades, en konto kan inte delas av en gästanvändare";
    public static String MSG_20_2 = "Kunde ej slutföra, den angivna användaren finns redan";

    public static String MSG_21_0 = "Kontodelning borttagen";
    public static String MSG_21_1 = "Ett okänt fel inträffade";

    public static String MSG_22 = "OBS! Du använder \"Omvänd skattskyldighet\" men motpartens VAT nr saknas.\n"
            + "Gå till flik KUNDER och ange VAT nr för den aktuella kunden.";

    public static String MSG_23 = "Ditt konto och all relaterad data är raderat!";
    public static String MSG_23_1 = "Gästanvändare kan inte radera kontot";
    public static String MSG_23_2 = "Fel lösenord, kontot har inte raderats";

    public static String MSG_24 = "Lämna utan att spara fakturan?";

    public static String MSG_25 = "Lägg till samtliga artiklar först och försök igen";
    public static String MSG_25_1 = "Är samtliga artiklar tilllagda?";

    public static String MSG_26 = "Inga personer finns tillagda, lägg till en och försök igen";

    public static String MSG_27 = "Lägg till artiklar först";

    public static String MSG_28(String pdfFileName) {
        return "PDF-dokument med namnet: " + "\"" + pdfFileName + "\"" + " finns på skrivbordet";
    }

    public static String MSG_28_2(String pdfFileName, File f) {
        return "PDF-dokument med namnet: " + "\"" + pdfFileName + "\"" + " finns på här: " + f.getAbsolutePath();
    }

    public static String MSG_29 = "Version: " + GP_BUH.VERSION + " stöds ej längre, hämta en ny version från " + GP_BUH.LA_WEB_ADDR;


    public static String MSG_30(int antal) {
        return "Samma artikel finns redan, kommer att öka antalet med " + antal + " istället";
    }
    
    public static String MSG_31 = "Utskriftens innehåll får inte plats på en A4. Var god försök göra innehållet kortare!";
    public static String MSG_32 = "Kunde inte skapa logotyp! \nMöjlig orsak är att din antivirus blockerar att logotypen kopieras.\nStarta om programmet och försök på nytt, tillåt att filen skapas i din antivirus.";

    public final static String MSG_33 = "En säkerhetskopia kommer nu att skapas. Säkerhetkopiering kan dröja länge vid en stor datamängd! Vill du forsätta?";
    public final static String MSG_34 = "Återställning lyckades!";
    public final static String MSG_35 = "Återställning misslyckades!";
    
    public final static String MSG_36 = "Säkerhetskopiering";
    public final static String MSG_36_2 = "Säkerhetskopierar, var god dröj";
    
    public final static String MSG_37 = "Återställning";
    public final static String MSG_37_2 = "Återställer, var god dröj";
    
    public static String LBL_MSG_1 = "SKAPA NY FAKTURA";
    public static String LBL_MSG_1_2 = "SKAPA NY KONTANTFAKTURA";
    public static String LBL_MSG_1_3 = "SKAPA NY OFFERT";
    public static String LBL_MSG_2 = "BEARBETA FAKTURA";
    public static String LBL_MSG_2_1 = "BEARBETA KONTANTFAKTURA";
    public static String LBL_MSG_2_1_2 = "BEARBETA OFFERT";
    public static String LBL_MSG_2_2 = "FAKTURA BETALD - REDIGERING ÄR INTE TILLÅTET";
    public static String LBL_MSG_2_3 = "REDIGERING AV ARTIKLAR ÄR INTE TILLÅTET";

    public static String LBL_MSG_3 = "SKAPA NY KUND";
    public static String LBL_MSG_4 = "BEARBETA KUND";
    public static String LBL_MSG_5 = "SKAPA ARTIKEL";
    public static String LBL_MSG_6 = "BEARBETA ARTIKEL";

    public static String LBL_MSG_7(String txt) {
        return "skapad: " + txt;
    }

    public static String LBL_MSG_8(String txt) {
        return "ändrad sist av: " + txt;
    }
    
     public static String TOOL_TIP_1(int antalForfallna) {
        //
        String basicStr = "Skicka påminnelse";
        //
        if (antalForfallna == 1) {
            return basicStr + " (du har " + antalForfallna + " förfallen faktura)";
        } else if (antalForfallna > 1) {
            return basicStr + " (du har " + antalForfallna + " förfallna fakturor)";
        } else {
            return basicStr;
        }
    }

    public static String TOOL_TIP_2 = "Inmatningsformat: 123456-7890";

    public static final String TOOL_TIP_3(boolean isOffert) {
        String part1 = "Om det alternativa numret finns angivet, visas den i ";
        String part2 = " istället";
//        return isOffert ? part1 + offerten + part2 : part1 + fakturan + part2; // must be fixed shows offerten hela tiden [2021-05-28]
        return part1 + fakturan + part2;
    }
    public static String TOOL_TIP_4 = "Spara kommentar";
    public static String TOOL_TIP_5 = "Radera kommentar";

   
    
     

    //==========================================================================
    public static String RUT_MSG_MAIN(String fastighetsBeteckning, String rutAvdragTotal, String attBetalaTotal, String fakturansTotalBeloppInnanAvdrag) {
        String str = "Denna faktura avser husarbete för fastighet \"" + fastighetsBeteckning + "\".\n";
        str += "Enligt dig som köpare har du rätt till preliminär skattereduktion på " + rutAvdragTotal + " kr.\n";
        str += "För att vi ska kunna göra ansökan till Skatteverket, ska du betala " + attBetalaTotal + " kr.\n";
        str += "Om ansökan om skattereduktion avslås, ska det totala beloppet " + fakturansTotalBeloppInnanAvdrag + " kr betalas av dig som köpare.";
        return str;
    }

    public static String RUT_MSG_MAIN__AUTO(String fastighetsBeteckning, String rutAvdragTotal, String attBetalaTotal, String fakturansTotalBeloppInnanAvdrag) {
        //
        String savedRutMsg = IO.loadRutMsg();
        //
        if (savedRutMsg != null && savedRutMsg.isEmpty() == false) {
            if (savedRutMsg.contains("%s")) {
                return String.format(savedRutMsg, fastighetsBeteckning, rutAvdragTotal, attBetalaTotal, fakturansTotalBeloppInnanAvdrag);
            } else {
                return savedRutMsg;
            }
        } else {
            return RUT_MSG_MAIN(fastighetsBeteckning, rutAvdragTotal, attBetalaTotal, fakturansTotalBeloppInnanAvdrag);
        }
        //
    }

    private static String PAMMINELSE_MSG_MAIN(String fakturanr) {
        if (HelpBuh.LANG_ENG == false) {
            return "Kära Kund! Fakturanr " + fakturanr + " är enligt våra noteringar fortfarande obetald."
                    + " Därför ber vi er att omgående betala in det förfallna beloppet.\n"
                    + "Vänligen uppge fakturanummer vid betalning."
                    + "Kontakta vår handläggare om du har frågor kring denna betalningspåminnelse.";
        } else {
            return "Dear Customer, our records show that invoice " + fakturanr + " is still not paid.\n"
                    + "Please proceed with the payment as soon as possible. Please specify invoice no. in the payment."
                    + " Contact us if you have any questions. Ignore this message if the payment is already done.";
        }

    }

    public static String PAMMINELSE_MSG_MAIN__AUTO(String fakturanr) {
        //
        String savedReminderMsg = IO.loadReminderMsg();
        //
        if (savedReminderMsg != null && savedReminderMsg.isEmpty() == false) {
            if (savedReminderMsg.contains("%s")) {
                return String.format(savedReminderMsg, fakturanr);
            } else {
                return savedReminderMsg;
            }
        } else {
            return PAMMINELSE_MSG_MAIN(fakturanr);
        }
        //
    }

    public static String LBL_MSG_2_3(String fakturaCopy) {
        return "KREDITERAR FAKTURA: " + fakturaCopy;
    }

    /**
     * VERY IMPORTANT: If it happens even once, actions shall be taken
     * [2020-08-06] This one shall be only used in cases when you insert an
     * entry and retrieve the newly inserted id
     */
    public static String MSG_ERROR_1 = "Uppladning misslyckades helt eller delvis";

    public static String FAKTURA_UTSKRIVEN_OUTLOOK(String fakturaFileName, boolean remminder, boolean isOffert, File f) {
        //
        String name;
        //
        if (remminder) {
            name = "Påminnelsen";
        } else if (isOffert) {
            name = Offerten;
        } else {
            name = Fakturan;
        }
        //
        return name + " sparades till " + f.getAbsolutePath() + ", glöm ej att bifoga " + name.toLowerCase() + " i din e-post klient";
        //
    }

    public static String FAKTURA_KREDIT_MSG(String fakturaCopy) {
        return "Vill du verkligen kreditera fakturanummer: " + fakturaCopy + "?";
    }

    public static String FAKTURA_COPY_MSG_A(String fakturaCopy) {
        return "Vill du verkligen kopiera fakturanummer: " + fakturaCopy + "?";
    }

    public static String FAKTURA_COPY_MSG_B(String fakturaCopy, String fakturaNew) {
        return "Du kopierade precis fakturanummer: " + fakturaCopy + ", det nya fakturanumret är: " + fakturaNew;
    }

    public static String OFFERT_COPY_MSG(String fakturaCopy, String fakturaNew) {
        return "Du kopierade precis offertnummer: " + fakturaCopy + ", det nya offertnumret är: " + fakturaNew;
    }

    public static String OFFERT_OMVANDLA_MSG(String fakturaCopy, String fakturaNew) {
        return "Du omvandlade precis offertnummer: " + fakturaCopy + ", det nya fakturanumret är: " + fakturaNew;
    }

    public static String LOGOTYP_TO_SMALL(String requiredMinWidth, String widthActual) {
        return "Logotyp för liten, minimal bredd: " + requiredMinWidth + ", den aktuella är: " + widthActual;
    }

    public static String LOGOTYP_FILE_SIZE_TO_BIG(String fileSize, String maxFileSize) {
        return "Logotypen är för stor: " + fileSize + "mb, maximal filstorlek: " + maxFileSize + "mb";
    }

    public static String CONFIRM_SEND_MAIL(String sendTo, HTMLPrint print, boolean offert) {
        if (print instanceof HTMLPrint_A && offert) {
            //[#OFFERT#]
            return "Skicka " + LANG.offerten + " till: " + sendTo + " ?";
        } else if (print instanceof HTMLPrint_A) {
            return "Skicka " + LANG.fakturan + " till: " + sendTo + " ?";
        } else if (print instanceof HTMLPrint_B) {
            return "Skicka " + PAMINNELSE.toLowerCase() + " till: " + sendTo + " ?";
        } else {
            return "undefined";
        }

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
