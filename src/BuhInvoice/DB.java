/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import java.util.HashMap;

/**
 *
 * @author MCREMOTE
 */
public class DB {

    /**
     * OBS! Has nothing with Database to to, it's for getting values/other out
     * of the tableInvert
     */
    public static final int START_COLUMN = 1;
    //
    public static final String PHP_SCRIPT_RETURN_EMPTY = "empty";
    //
    public static final String PHP_SCRIPT_MAIN = "_http_buh";

    //
    /**
     * OBS! CHANGING OF STATIC VALUES MUST BE DONE WITH CAUTION
     */
    /**
     * The value in table "buh_constants" should be stored without using ","
     *
     * @param strToProcess
     * @return
     */
//    public static String convert(String strToProcess) {
//        ////[#BUH-CONSTANTS#]
//        strToProcess = strToProcess.replaceAll(",", "¤"); // is replaced from "JSon.java -> JSONToHashMap(String json, boolean reverse)"
//        strToProcess = strToProcess.replaceAll("\\;", "*"); // is replaced from "HelpBuh.class -> get_constants()"
//        return strToProcess;
//    }

    private static HashMap<String, String> BUH_CONSTANTS = new HashMap<>();

    public final static double ROT_PERCENT = 0.3; // Verified for 2021
    public final static double RUT_PERCENT = 0.5; // Verified for 2021
    public final static double ROT_MAX = 50000; // Verified for 2021
    public final static double RUT_MAX = 75000; // Verified for 2021

    public static final String STATIC__YES = "Ja";
    public static final String STATIC__NO = "Nej";

    public static final String GET_CONSTANT(String alias, String defaultVal) {
        //
        if (BUH_CONSTANTS.isEmpty()) {
            BUH_CONSTANTS = HelpBuh.get_constants();
        }
        //
        String key = alias; //[#BUH-CONSTANTS#]
        //
        if (BUH_CONSTANTS == null || BUH_CONSTANTS.isEmpty() || BUH_CONSTANTS.containsKey(alias) == false) {
            return defaultVal;
        } else {
            return BUH_CONSTANTS.get(key);
        }
        //
    }

    public static final double GET_CONSTANT__DOUBLE(String alias, double defaultVal) {
        String key = alias; //[#BUH-CONSTANTS#]
        if (BUH_CONSTANTS == null || BUH_CONSTANTS.isEmpty() || BUH_CONSTANTS.containsKey(alias) == false) {
            return defaultVal;
        } else {
            return Double.parseDouble(BUH_CONSTANTS.get(key));
        }
    }

    public static final String STATIC__BETAL_VILKOR = "30,60,20,15,10,5";
    public static final String STATIC__LEV_VILKOR = "Fritt vårt lager;FVL,CIF;CIF,FAS;FAS,Fritt Kund;FK,FOB;FOB";

    public static final String STATIC__LEV_SATT = "Post;P,Hämtas;HAM"; // ,Digitalt;D
    public static final String STATIC__INKL_EXKL_MOMS = "Inkl moms;1,Exkl moms;0";
    public static final String STATIC__MOMS_SATS = "25%;25,12%;12,6%;6,0%;0";
    public static final String STATIC__JA_NEJ = "Nej;0,Ja;1";
    public static final String STATIC__JA_NEJ__EMPTY_NEJ = "-;0,Ja;1";
    //===
    public static final String STATIC__SENT_STATUS = "Ej Skickad;0,Skickad;1,Makulerad;2,Utskriven;3,Kopierad;4,Skapad;5,Skickad med Outlook;6,Skickad med vanlig post;7,Offert omvandlat till faktura;8";
    public static final String STATIC__SEND_TYPES = "Faktura;0,Påminnelse;1,Offert;2";
    //
    public static final String STATIC__SENT_STATUS__EJ_SKICKAD = "0";
    public static final String STATIC__SENT_STATUS__SKICKAD = "1";
    public static final String STATIC__SENT_STATUS__MAKULERAD = "2";
    public static final String STATIC__SENT_STATUS__UTSKRIVEN = "3";
    public static final String STATIC__SENT_STATUS__KOPIERAD = "4";
    public static final String STATIC__SENT_STATUS__SKAPAD = "5";
    public static final String STATIC__SENT_STATUS__SKICKAD_OUTLOOK = "6"; // Det betyder att försöket gjordes, men resultatet är ej känt
    public static final String STATIC__SENT_STATUS__SKICKAD_COMMON_POST = "7"; // [2020-10-28]
    public static final String STATIC__SENT_STATUS__OMVANDLAT = "8";
    //
    public static final String STATIC__SENT_TYPE_FAKTURA = "0";
    public static final String STATIC__SENT_TYPE_PAMMINELSE = "1";
    public static final String STATIC__SENT_TYPE_OFFERT = "2";
    //===
    public static final String STATIC__FAKTURA_TYPE_NORMAL__NUM = "0";
    public static final String STATIC__FAKTURA_TYPE_KREDIT__NUM = "1";
    public static final String STATIC__FAKTURA_TYPE_KONTANT__NUM = "2";
    public static final String STATIC__FAKTURA_TYPE_OFFERT__NUM = "3";
    public static final String STATIC__FAKTURA_TYPE_NORMAL = "NORMAL";
    public static final String STATIC__FAKTURA_TYPE_KREDIT = "KREDIT";
    public static final String STATIC__FAKTURA_TYPE_KONTANT = "KONTANT";
    public static final String STATIC__FAKTURA_TYPE_OFFERT = "OFFERT";//[#OFFERT#]
    public static final String STATIC__FAKTURA_TYPES = STATIC__FAKTURA_TYPE_NORMAL + ";0,"
            + STATIC__FAKTURA_TYPE_KREDIT + ";1,"
            + STATIC__FAKTURA_TYPE_KONTANT + ";2," // OBS! PAY ATTENTION AT ","
            + STATIC__FAKTURA_TYPE_OFFERT + ";3";// "NORMAL;0,KREDIT;1,KONTANT;2";OFFERT;3

    public static final String STATIC__ENHET = "Styck;st,Förp;Förp,Timmar;Tim";
    public static final String STATIC__BETAL_METHODS = "Bank Giro;bg,Kort;kt,Kontant;ko,Plus Giro;pg,Bank Konto;bk,Swish;sw";
    public static final String STATIC_BET_STATUS_KREDIT = "-";
    public static final String STATIC__BETAL_STATUS = STATIC__NO + ";0,Ja;1,Delvis;2,Ja - Överbetald;3," + STATIC_BET_STATUS_KREDIT + ";4";
    //
    public static final String STATIC__ARTICLE__KATEGORI = "A,B,C,D,E,F, MOMS 12%";
    public static final String STATIC__KUND__KATEGORI = "A,B,C,D,E,F,EU EUR,UTL EUR";
    public static final String STATIC__KUND__KATEGORI__EU_EUR = "EU EUR";
    public static final String STATIC__KUND__KATEGORI__UTL_EUR = "UTL EUR";
    //
    //
    public static final String TABLE__BUH_F_ARTIKEL = "buh_f_artikel";
    public static final String TABLE__BUH_FAKTURA = "buh_faktura";
    public static final String TABLE__BUH_FAKTURA_RUT = "buh_faktura_rut";
    public static final String TABLE__BUH_FAKTURA_RUT_PERSON = "buh_faktura_rut_person";
    public static final String TABLE__BUH_FAKTURA_KUND = "buh_faktura_kund";
    public static final String TABLE__BUH_FAKTURA_ARTIKEL = "buh_faktura_artikel";
    public static final String TABLE__BUH_ADDRESS = "buh_address";
    public static final String TABLE__BUH_KUND = "buh_kund";
    public static final String TABLE__BUH_FAKTURA_INBET = "buh_faktura_inbet";
    public static final String TABLE__BUH_FAKTURA_SEND = "buh_faktura_send";
    //
    public static final String PHP_FUNC_GET_NEWEST_AVAILABLE_VERSION = "get_newest_version";
    //
    public static final String PHP_FUNC_GET_CONSTANTS = "get_constants";
    //
    public static final String PHP_FUNC_DEFINE_KUNDID__LOGIN = "define_kundid"; // using php-function: "defineKundId__login()"
    public static final String PHP_FUNC_CREATE_ACCOUNT_MAIN = "create_account_main"; // using php-function: "createAccount()"
    public static final String PHP_FUNC_CREATE_ACCOUNT_EXISTING_CUSTOMER = "create_account_existing_customer";
    public static final String PHP_FUNC_RESTORE_PWD = "restore_pwd";
    public static final String PHP_FUNC_SHARE_ACCOUNT = "share_user_account";
    //
    public static final String PHP_FUNC_DELETE_CUSTOMER__B = "delete_customer_b"; // for a user to delete the account bu him self
    public static final String PHP_FUNC_DELETE_CUSTOMER__A = "delete_customer_a"; //  
    public static final String PHP_FUNC_DELETE_GUEST = "delete_guest";
    //
    public static final String PHP_FUNC_EMAIL_WITH_ATTACHMENT = "send_email_with_attachment"; // using php-function: "email()"
    public static final String PHP_FUNC_EMAIL_WITH_ATTACHMENT__SMTP = "send_email_with_attachment_smtp"; // using php-function: "email_smtp()"
    //
    public static final String PHP_FUNC_FAKTURA_TO_DB = "faktura_to_db"; // using php-function: "insert()"
    public static final String PHP_FUNC_FAKTURA_INBET_TO_DB = "faktura_inbet_to_db"; // using php-function: "insert()"
    public static final String PHP_FUNC_FAKTURA_SEND_TO_DB = "faktura_send_to_db"; // using php-function: "insert()"
    public static final String PHP_FUNC_ARTICLES_TO_DB = "articles_to_db"; // using php-function: "insert()"
    public static final String PHP_FUNC_FAKTURA_KUND_TO_DB = "faktura_kund_to_db"; // using php-function: "insert()"
    public static final String PHP_FUNC_FAKTURA_KUND_ADDR_TO_DB = "faktura_kund_address_to_db"; // using php-function: "insert()"
    public static final String PHP_FUNC_ARTIKEL_TO_DB = "artikel_to_db"; // using php-function: "insert()"
    public static final String PHP_FUNC_FAKTURA_RUT_ENTRY_TO_DB = "faktura_rut_to_db"; // using php-function: "insert()"
    public static final String PHP_FUNC_FAKTURA_RUT_PERSON_ENTRY_TO_DB = "faktura_rut_person_to_db"; // using php-function: "insert()"
    //
    public static final String PHP_FUNC_GET_LATEST_FAKTURA_NR = "get_latest_faktura_nr"; // using php-finction: "getLatestFakturaNr()"
    //
    public static final String PHP_FUNC_EXIST = "check_if_exist"; // using php-finction: "exist()"
    //
    public static final String PHP_FUNC_LATEST = "get_latest"; // using php-finction: "latest()"
    public static final String PHP_FUNC_DELETE = "delete_entry"; // using php-finction: "latest()"
    //
    public static final String PHP_FUNC_PARAM__SHARED_USERS = "get_shared_users"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM__GET_FTG_NAME = "get_ftg_name"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM__GET_KUNDER = "get_faktura_kunder"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM__GET_FAKTURA_INBET = "get_faktura_inbet"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM__GET_FAKTURA_SEND = "get_faktura_send"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_FAKTURA_KUNDER_ALL_DATA = "get_faktura_kunder_all_data"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_FAKTURA_KUNDER_ALL_DATA__PERSON = "get_faktura_kunder_all_data__person"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_ONE_FAKTURA_KUND_ALL_DATA = "get_one_faktura_kund_all_data"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_ONE_FAKTURA_ALL_DATA = "get_one_faktura_all_data"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_ONE_FAKTURA_ARTICLE_ALL_DATA = "get_one_faktura_article_all_data"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_FORETAG_DATA = "get_kund_data"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_KUND_ARTICLES = "get_kund_articles"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_KUND_ARTICLES_ALL_DATA = "get_kund_articles_all_data"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_RUT_PERSON = "get_rut_person"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_RUT = "get_rut"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET__FAKTURA_KUND__IS_PERSON = "faktura_kund_is_person";
    //Search filters below..
    public static final String PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ONE_YEAR_BACK = "get_kund_fakturor"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ALL = "get_kund_fakturor_all"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_KUND_FAKTUROR__OBETALD = "get_kund_fakturor__obetald"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FORFALLEN = "get_kund_fakturor__forfallen"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_KUND_FAKTUROR__DELVIS_BETALD__UNUSED = "get_kund_fakturor__delvis"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_KUND_FAKTUROR__EJ_SKICKAD = "get_kund_fakturor__unsend"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_KUND_FAKTUROR__MAKULERAD = "get_kund_fakturor__makulerad"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FAKTURA_KUND = "get_kund_fakturor__fakturakund"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ACT_MONTH = "get_kund_fakturor__actmonth"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_ARTICLE_TOTALS_CURR_YEAR = "get_article_totals_curr_year"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_KUND_FAKTUROR_GIVEN_ARTICLE__CURR_YEAR = "get_fakturor_given_article_curr_year"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_FAKTUROR_TOTALS__GIVEN_KUND_CURR_YEAR = "get_fakturor_totals_given_kund_curr_year"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FAKTURA_KUND_CURR_YEAR = "get_kund_fakturor__fakturakund_curr_year"; // using php-function: "select()"
    //
    public static HashMap<String, String> FILTER_DICT_MAP = new HashMap<>();
    //
    static {
        FILTER_DICT_MAP.put(PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ONE_YEAR_BACK, "sedan årsskiftet");
        FILTER_DICT_MAP.put(PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ALL, "samtliga fakturor");
        FILTER_DICT_MAP.put(PHP_FUNC_PARAM_GET_KUND_FAKTUROR__OBETALD, "obetalda fakturor");
        FILTER_DICT_MAP.put(PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FORFALLEN, "förfallna fakturor");
        FILTER_DICT_MAP.put(PHP_FUNC_PARAM_GET_KUND_FAKTUROR__DELVIS_BETALD__UNUSED, "delvis betalda fakturor");
        FILTER_DICT_MAP.put(PHP_FUNC_PARAM_GET_KUND_FAKTUROR__EJ_SKICKAD, "ej skickade fakturor");
        FILTER_DICT_MAP.put(PHP_FUNC_PARAM_GET_KUND_FAKTUROR__MAKULERAD, "makulerade fakturor");
        FILTER_DICT_MAP.put(PHP_FUNC_PARAM_GET_KUND_FAKTUROR__FAKTURA_KUND, "angiven kund");
        FILTER_DICT_MAP.put(PHP_FUNC_PARAM_GET_KUND_FAKTUROR__ACT_MONTH, "aktuell månad");
    }
    //
    public static final String PHP_FUNC_PARAM_GET_FAKTURA_ARTICLES = "get_faktura_articles"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_FAKTURA_KUND_ADDRESSES = "get_faktura_kund_addr"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_FORETAG_ADDRESS = "get_kund_addr"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_INVOICES_GIVEN_FKID = "get_invoices_given_fakturakundid"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_INVOICES_GIVEN_ARTICLEID = "get_invoices_given_artikelId"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_INVOICES_USING_ARTICLE = "get_invoices_using_article"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_INVOICES_USING_CUSTOMER = "get_invoices_using_customer"; // using php-function: "select()"
    //
    public static final String PHP_FUNC_UPDATE_AUTO = "update_auto"; // using php-function: "update()"
    //
    public static final String BUH_FAKTURA__ID__ = "fakturaId";
    public static final String BUH_FAKTURA__KUNDID__ = "kundId"; // "__" additional prefix on the end means not aquired form TableInvert
    public static final String BUH_FAKTURA__FAKTURANR__ = "fakturanr";
    public static final String BUH_FAKTURA__FAKTURANR_ALT = "fakturanr_alt"; // [2020-10-21]
    public static final String BUH_FAKTURA__FAKTURATYP = "fakturatyp";
    public static final String BUH_FAKTURA__FAKTURAKUND_ID = "fakturaKundId";
    public static final String BUH_FAKTURA__FAKTURA_DATUM = "fakturadatum";
    public static final String BUH_FAKTURA__FORFALLO_DATUM = "forfallodatum";
    public static final String BUH_FAKTURA__ER_REFERENS = "er_referens";
    public static final String BUH_FAKTURA__ERT_ORDER = "ert_ordernr";
    public static final String BUH_FAKTURA__VAR_REFERENS = "var_referens";
    public static final String BUH_FAKTURA__BETAL_VILKOR = "betal_vilkor";
    public static final String BUH_FAKTURA__LEV_VILKOR = "lev_vilkor";
    public static final String BUH_FAKTURA__LEV_SATT = "lev_satt";
    public static final String BUH_FAKTURA__EXP_AVG = "exp_avg";
    public static final String BUH_FAKTURA__DROJSMALSRANTA = "drojsmalsranta";
    public static final String BUH_FAKTURA__FRAKT = "frakt";
//    public static final String BUH_FAKTURA__INKL_MOMS = "inkl_moms"; // deleted [2020-10-02]
    public static final String BUH_FAKTURA__MAKULERAD = "makulerad";
    public static final String BUH_FAKTURA__RUT = "rutavdrag";
//    public static final String BUH_FAKTURA__MOMS_SATS = "moms_sats"; // deleted [2020-10-02]
    public static final String BUH_FAKTURA__TOTAL__ = "total_ink_moms";
    public static final String BUH_FAKTURA__TOTAL_EXKL_MOMS__ = "total_exkl_moms";
    public static final String BUH_FAKTURA__RABATT_TOTAL = "rabatt_total";
    public static final String BUH_FAKTURA__MOMS_TOTAL__ = "moms_total";
    public static final String BUH_FAKTURA__VALUTA = "valuta";
    public static final String BUH_FAKTURA__BETALD = "betald";
    public static final String BUH_FAKTURA__SENT = "sent_with_email";
    public static final String BUH_FAKTURA__IMPORTANT_KOMMENT = "important_komment"; // length 100
    public static final String BUH_FAKTURA__KOMMENT = "komment"; // length 200 -> OBS! Writing to this field is only possbile internaly - from code
    public static final String BUH_FAKTURA__DATE_CREATED__ = "date_created";
    public static final String BUH_FAKTURA__CHANGED_BY = "changed_by"; // [2020-10-28]
    public static final String BUH_FAKTURA__COPIED_FROM_ID = "copied_from_id";
    public static final String BUH_FAKTURA__OMVANT_SKATT = "omvant_skatt"; // [#INVOICE-HAS-OMVAND-SKATT#]
    public static final String BUH_FAKTURA__PRINTED = "is_printed";
    public static final String BUH_FAKTURA__CURRENCY_RATE_A = "currency_rate_a";
    //
    public static final String BUH_FAKTURA_KUND__ID = "fakturaKundId";
    public static final String BUH_FAKTURA_KUND__KUND_ID = "kundId";
    public static final String BUH_FAKTURA_KUND___NAMN = "namn";
    public static final String BUH_FAKTURA_KUND___KUNDNR = "kundnr";
    public static final String BUH_FAKTURA_KUND___ORGNR = "orgnr";
    public static final String BUH_FAKTURA_KUND___VATNR = "vatnr";
    public static final String BUH_FAKTURA_KUND___EMAIL = "email";
    public static final String BUH_FAKTURA_KUND___KATEGORI = "kund_kategori";
    public static final String BUH_FAKTURA_KUND___IS_PERSON = "is_person";
    public static final String BUH_FAKTURA_KUND__DATE_CREATED = "date_created";

    //
    public static final String BUH_FAKTURA_ARTIKEL___ID = "artikelId";
    public static final String BUH_FAKTURA_ARTIKEL___KUND_ID = "kundId";
    public static final String BUH_FAKTURA_ARTIKEL___NAMN = "namn";
    public static final String BUH_FAKTURA_ARTIKEL___ARTNR = "artnr";
    public static final String BUH_FAKTURA_ARTIKEL___LAGER = "lager";
    public static final String BUH_FAKTURA_ARTIKEL___PRIS = "pris";
    public static final String BUH_FAKTURA_ARTIKEL___INKOPS_PRIS = "inkopspris";
    public static final String BUH_FAKTURA_ARTIKEL___KOMMENT = "komment";
    public static final String BUH_FAKTURA_ARTIKEL___KOMMENT_B = "komment_b";
    public static final String BUH_FAKTURA_ARTIKEL___KOMMENT_C = "komment_c";
    public static final String BUH_FAKTURA_ARTIKEL___KATEGORI = "artikel_kategori";
    //
    public static final String BUH_F_ARTIKEL__ID = "id";
    public static final String BUH_F_ARTIKEL__KUND_ID = "kundId"; // added on[2020-10-15]
    public static final String BUH_F_ARTIKEL__FAKTURAID = "fakturaId";
    public static final String BUH_F_ARTIKEL__ARTIKELID = "artikelId";
    public static final String BUH_F_ARTIKEL__KOMMENT = "komment";
    public static final String BUH_F_ARTIKEL__ANTAL = "antal";
    public static final String BUH_F_ARTIKEL__ENHET = "enhet";
    public static final String BUH_F_ARTIKEL__PRIS = "pris";
    public static final String BUH_F_ARTIKEL__RABATT = "rabatt";
    public static final String BUH_F_ARTIKEL__RABATT_KR = "rabatt_kr";
    public static final String BUH_F_ARTIKEL__MOMS_SATS = "moms_sats";
    public static final String BUH_F_ARTIKEL__OMVANT_SKATT = "omvant_skatt"; // [2020-10-29]OMVANT_SKATTSKYLDIGHET
    //
    public static final String BUH_ADDR__ID = "id";
    public static final String BUH_ADDR__KUND_ID = "kundId";
    public static final String BUH_ADDR__FAKTURAKUND_ID = "fakturaKundId";
    public static final String BUH_ADDR__IS_PRIMARY_ADDR = "primary_addr";
    public static final String BUH_ADDR__ADDR_A = "postaddr_a";
    public static final String BUH_ADDR__ADDR_B = "postaddr_b";
    public static final String BUH_ADDR__BESOKS_ADDR = "visit_addr";
    public static final String BUH_ADDR__POSTNR_ZIP = "zip";
    public static final String BUH_ADDR__ORT = "ort";
    public static final String BUH_ADDR__LAND = "land";
    public static final String BUH_ADDR__TEL_A = "tel_a";
    public static final String BUH_ADDR__TEL_B = "tel_b";
    public static final String BUH_ADDR__OTHER = "other";
    //
    //
    public static final String BUH_KUND__ID = "kundId";
    public static final String BUH_KUND__NAMN = "namn";
    public static final String BUH_KUND__ORGNR = "orgnr";
    public static final String BUH_KUND__VATNR = "vatnr";
    public static final String BUH_KUND__EPOST = "email";
    public static final String BUH_KUND__BANK_GIRO = "bank_giro";
    public static final String BUH_KUND__POST_GIRO = "post_giro";
    public static final String BUH_KUND__KONTO = "bank_konto";
    public static final String BUH_KUND__IBAN = "iban";
    public static final String BUH_KUND__BIC = "bic";
    public static final String BUH_KUND__SWIFT = "swift";
    public static final String BUH_KUND__SWISH = "swish";
    public static final String BUH_KUND__F_SKATT = "f_skatt";
    public static final String BUH_KUND__DATE_CREATED = "date_created";
    //
    //
    public static final String BUH_FAKTURA_INBET__INBET_ID = "inbetId";
    public static final String BUH_FAKTURA_INBET__KUND_ID = "kundId";
    public static final String BUH_FAKTURA_INBET__FAKTURA_ID = "fakturaId";
    public static final String BUH_FAKTURA_INBET__INBETALD = "inbetald";
    public static final String BUH_FAKTURA_INBET__BETAL_METHOD = "betal_metod";
    public static final String BUH_FAKTURA_INBET__BETAL_DATUM = "betal_datum";
    public static final String BUH_FAKTURA_INBET__ANNAT = "annat";
    public static final String BUH_FAKTURA_INBET__DONE_BY = "done_by";
    //
    public static final String BUH_FAKTURA_SEND__ID = "sendId";
    public static final String BUH_FAKTURA_SEND__KUND_ID = "kundId";
    public static final String BUH_FAKTURA_SEND__FAKTURA_ID = "fakturaId";
    public static final String BUH_FAKTURA_SEND__SEND_TYPE = "send_type";
    public static final String BUH_FAKTURA_SEND__SEND_OK = "send_ok"; // --> It's STATUS like: "STATIC__SENT_STATUS__UTSKRIVEN"
    public static final String BUH_FAKTURA_SEND__SEND_DATUM = "send_datum";
    public static final String BUH_FAKTURA_SEND__ANNAT = "annat";
    public static final String BUH_FAKTURA_SEND__DONE_BY = "done_by";
    //
    public static final String BUH_LICENS__LICENS_ID = "licensId";
    public static final String BUH_LICENS__KUND_ID = "kundId";
    public static final String BUH_LICENS__MASTER = "master";
    public static final String BUH_LICENS__USER = "user";
    public static final String BUH_LICENS__PASS = "pass";
    public static final String BUH_LICENS__MAC_ADDR = "mac";
    public static final String BUH_LICENS__OS = "os";
    public static final String BUH_LICENS__LANG = "lang";
    public static final String BUH_LICENS__PC_USER_NAME = "pc_user_name";
    public static final String BUH_LICENS__JAVA = "java";
    public static final String BUH_LICENS__DATE_CREATED = "date_created";
    //
    public static final String BUH_VISITORS__PROGRAM_VER = "program_ver";
    //
    public static final String BUH_FAKTURA_RUT__KUNDID = "kundId";
    public static final String BUH_FAKTURA_RUT__FAKTURAID = "fakturaId";
    public static final String BUH_FAKTURA_RUT__SKATTEREDUKTION = "skattereduktion";
    public static final String BUH_FAKTURA_RUT__FASTIGHETS_BETECKNING = "fastighets_beteckning";
    public static final String BUH_FAKTURA_RUT__DATE_CREATED = "date_created";
    //
    public static final String BUH_FAKTURA_RUT_PERSON__RUTID = "rutId";
    public static final String BUH_FAKTURA_RUT_PERSON__FAKTURAID = "fakturaId";
    public static final String BUH_FAKTURA_RUT_PERSON__KUNDID = "kundId";
    public static final String BUH_FAKTURA_RUT_PERSON__FORNAMN = "namn";
    public static final String BUH_FAKTURA_RUT_PERSON__EFTERNAMN = "efternamn";
    public static final String BUH_FAKTURA_RUT_PERSON__PNR = "pnr";
    public static final String BUH_FAKTURA_RUT_PERSON__SKATTEREDUKTION = "skattereduktion";
    public static final String BUH_FAKTURA_RUT_PERSON__AVDRAGSTAK_VALUE_NOT_AQUIRE = "avdragstak"; // OBS! This one is not present in DB [2021-04-30]
    //
    public static final String BUH_CONSTANTS__ID = "constant_id";
    public static final String BUH_CONSTANTS__NAME = "name";
    public static final String BUH_CONSTANTS__VAL = "name";
}
