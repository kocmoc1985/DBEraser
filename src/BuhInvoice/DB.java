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

    //
    public static final String PHP_SCRIPT_MAIN = "_http_buh";
    public static final String PHP_FUNC_FAKTURA_TO_DB = "faktura_to_db";
    public static final String PHP_FUNC_ARTICLES_TO_DB = "articles_to_db";
    public static final String PHP_FUNC_GET_LATEST_FAKTURA_NR = "get_latest_faktura_nr";
    //
    public static final String PHP_FUNC_PARAM__GET_KUNDER__$ = "get_faktura_kunder"; // using php-function: "get_by_kundid()"
    public static final String PHP_FUNC_PARAM_GET_KUND_ARTICLES__$ = "get_kund_articles"; // using php-function: "get_by_kundid()"
    public static final String PHP_FUNC_PARAM_GET_KUND_FAKTUROR__$ = "get_kund_fakturor"; // using php-function: "get_by_kundid()"
    //
    public static final String PHP_FUNC_GET_AUTO = "update_auto"; // using php-function: "updateAuto()"
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
    public static final String BUH_FAKTURA_KUND___NAMN = "namn";
    public static final String BUH_FAKTURA_KUND___KUNDNR = "kundnr";
    //
    public static final String BUH_FAKTURA_ARTIKEL___NAMN = "namn";
    public static final String BUH_FAKTURA_ARTIKEL___ID = "artikelId";
    //
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
