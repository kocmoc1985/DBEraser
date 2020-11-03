/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forall;

import java.net.URL;
import java.util.HashSet;
import java.util.Properties;

/**
 *
 * @author Administrator
 */
public class GP {

    public static String COMPANY_NAME;
    public static boolean IS_DATE_FORMAT_DE = false;
    public static String DATE_FORMAT_COMMON = "yyyy-MM-dd";
    public static String DATE_FORMAT_DE  = "dd.MM.yyyy";
    //
    public static final String COMPANY_NAME_UNDEFINED = "undef";
    public static final String COMPANY_NAME_QEW = "qew";
    public static final String COMPANY_NAME_COMPOUNDS = "compounds";
    //
    public static final HashSet<String>COMPANIES = new HashSet<String>();
    //
    static{
        COMPANIES.add(COMPANY_NAME_QEW);
        COMPANIES.add(COMPANY_NAME_COMPOUNDS);
    }
    //
    public static final URL IMAGE_ICON_URL = GP.class.getResource("icon.png");
    public static final URL IMAGE_ICON_URL_RECIPE = GP.class.getResource("icon2.png");
    public static final URL IMAGE_ICON_URL_PROD_PLAN = GP.class.getResource("icon3.png");
    public static final URL IMAGE_ICON_URL_LAFAKTURERING = GP.class.getResource("la.png");
    public static final URL COMPLETE_IMAGE_ICON_URL = GP.class.getResource("ok.png");
    //
    //=================================================
    public final static Properties FREE_QUERY_AND_NOT_ONLY_PROPS = HelpA_.properties_load_properties("freeq.properties", false);
    //=================================================
    public static String SQL_NPMS_HOST = "";
    public static int SQL_NPMS_PORT;
    //=================================================
    public static String MSSQL_CREATE_STATEMENT_SIMPLE = "false";
    public static int MSSQL_LOGIN_TIME_OUT;
    public static boolean SQL_LIBRARY_JTDS = false;
    public static boolean JTDS_USE_NAMED_PIPES = false;
    public static String JTDS_INSTANCE_PARAMETER = "";
    public static String JTDS_DOMAIN_WORKGROUP = "";
    //=================================================
    public static boolean LOGG_CONNECTION_STRING = true;
    //
    public static String CONNECTION_STRING = "";
}
