/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

/**
 *
 * @author MCREMOTE
 */
public class DB {

    /**
     * OBS! CHANGING OF STATIC VALUES MUST BE DONE WITH CAUTION
     */
    public static final String STATIC__BETAL_VILKOR = "30,60,20,15,10,5";
    public static final String STATIC__LEV_VILKOR = "Fritt vårt lager;FVL,CIF;CIF,FAS;FAS,Fritt Kund;FK,FOB;FOB";
    public static final String STATIC__LEV_SATT = "Post;P,Hämtas;HAM";
    public static final String STATIC__INKL_EXKL_MOMS = "Inkl moms;1,Exkl moms;0";
    public static final String STATIC__MOMS_SATS = "25%;0.25,12%;0.12,6%;0.06,0%;0";
    public static final String STATIC__MAKULERAD_JA_NEJ = "Nej;0,Ja;1";
    public static final String STATIC__ENHET = "Styck;st,Förp;Förp,Timmar;Tim";
    //
    public static final String STATIC__KUND_KATEGORI = "A,B,C,D,E,F,G";
    //
    //
    public static final String TABLE__BUH_F_ARTIKEL = "buh_f_artikel";
    public static final String TABLE__BUH_FAKTURA = "buh_faktura";
    public static final String TABLE__BUH_FAKTURA_KUND = "buh_faktura_kund";
    public static final String TABLE__BUH_FAKTURA_ARTIKEL = "buh_faktura_artikel";
    //
    public static final String PHP_SCRIPT_MAIN = "_http_buh";
    public static final String PHP_FUNC_FAKTURA_TO_DB = "faktura_to_db"; // using php-function: "insert()"
    public static final String PHP_FUNC_ARTICLES_TO_DB = "articles_to_db"; // using php-function: "insert()"
    public static final String PHP_FUNC_FAKTURA_KUND_TO_DB = "faktura_kund_to_db"; // using php-function: "insert()"
    public static final String PHP_FUNC_ARTIKEL_TO_DB = "artikel_to_db"; // using php-function: "insert()"
    public static final String PHP_FUNC_GET_LATEST_FAKTURA_NR = "get_latest_faktura_nr"; // using php-finction: "getLatestFakturaNr()"
    public static final String PHP_FUNC_EXIST = "check_if_exist"; // using php-finction: "exist()"
    public static final String PHP_FUNC_LATEST = "get_latest"; // using php-finction: "latest()"
    //
    public static final String PHP_FUNC_PARAM__GET_KUNDER = "get_faktura_kunder"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_KUDNER_ALL_DATA = "get_faktura_kunder_all_data"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_KUND_ARTICLES = "get_kund_articles"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_KUND_FAKTUROR = "get_kund_fakturor"; // using php-function: "select()"
    public static final String PHP_FUNC_PARAM_GET_FAKTURA_ARTICLES = "get_faktura_articles"; // using php-function: "select()"
    
    //
    public static final String PHP_FUNC_UPDATE_AUTO = "update_auto"; // using php-function: "update()"
    //
    public static final String BUH_FAKTURA__ID__ = "fakturaId";
    public static final String BUH_FAKTURA__KUNDID__ = "kundId"; // "__" additional prefix on the end means not aquired form TableInvert
    public static final String BUH_FAKTURA__FAKTURANR__ = "fakturanr";
    public static final String BUH_FAKTURA__FAKTURATYP = "fakturatyp";
    public static final String BUH_FAKTURA__FAKTURAKUND_ID = "fakturaKundId";
    public static final String BUH_FAKTURA__FAKTURA_DATUM = "fakturadatum";
    public static final String BUH_FAKTURA__FORFALLO_DATUM = "forfallodatum";
    public static final String BUH_FAKTURA__ER_REFERENS = "er_referens";
    public static final String BUH_FAKTURA__VAR_REFERENS = "var_referens";
    public static final String BUH_FAKTURA__BETAL_VILKOR = "betal_vilkor";
    public static final String BUH_FAKTURA__LEV_VILKOR = "lev_vilkor";
    public static final String BUH_FAKTURA__LEV_SATT = "lev_satt";
    public static final String BUH_FAKTURA__EXP_AVG = "exp_avg";
    public static final String BUH_FAKTURA__FRAKT = "frakt";
    public static final String BUH_FAKTURA__INKL_MOMS = "inkl_moms";
    public static final String BUH_FAKTURA__MAKULERAD = "makulerad";
    public static final String BUH_FAKTURA__MOMS_SATS = "moms_sats";
    public static final String BUH_FAKTURA__TOTAL__ = "total_ink_moms";
    public static final String BUH_FAKTURA__TOTAL_EXKL_MOMS__ = "total_exkl_moms";
    public static final String BUH_FAKTURA__MOMS_TOTAL__ = "moms_total";
    public static final String BUH_FAKTURA__VALUTA = "valuta";
    public static final String BUH_FAKTURA__BETALD = "betald";
    public static final String BUH_FAKTURA__DATE_CREATED__ = "date_created";
    //
    public static final String BUH_FAKTURA_KUND__ID = "fakturaKundId";
    public static final String BUH_FAKTURA_KUND__KUND_ID = "kundId";
    public static final String BUH_FAKTURA_KUND___NAMN = "namn";
    public static final String BUH_FAKTURA_KUND___KUNDNR = "kundnr";
    public static final String BUH_FAKTURA_KUND___ORGNR = "orgnr";
    public static final String BUH_FAKTURA_KUND___VATNR = "vatnr";
    public static final String BUH_FAKTURA_KUND___EMAIL = "email";
    public static final String BUH_FAKTURA_KUND___KATEGORI = "kund_kategori";
    
    //
    public static final String BUH_FAKTURA_ARTIKEL___ID = "artikelId";
    public static final String BUH_FAKTURA_ARTIKEL___KUND_ID = "kundId";
    public static final String BUH_FAKTURA_ARTIKEL___NAMN = "namn";
    public static final String BUH_FAKTURA_ARTIKEL___LAGER = "lager";
    public static final String BUH_FAKTURA_ARTIKEL___PRIS = "pris";
    public static final String BUH_FAKTURA_ARTIKEL___INKOPS_PRIS = "inkopspris";
    public static final String BUH_FAKTURA_ARTIKEL___KOMMENT = "komment";
    //
    public static final String BUH_F_ARTIKEL__ID = "id";
    public static final String BUH_F_ARTIKEL__FAKTURAID = "fakturaId";
    public static final String BUH_F_ARTIKEL__ARTIKELID = "artikelId";
    public static final String BUH_F_ARTIKEL__KOMMENT = "komment";
    public static final String BUH_F_ARTIKEL__ANTAL = "antal";
    public static final String BUH_F_ARTIKEL__ENHET = "enhet";
    public static final String BUH_F_ARTIKEL__PRIS = "pris";
    public static final String BUH_F_ARTIKEL__RABATT = "rabatt";
    //
    //
    public static final String BUH_KUND__ID = "kundId";
}
