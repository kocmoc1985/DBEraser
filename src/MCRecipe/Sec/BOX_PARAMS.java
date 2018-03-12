/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Sec;

/**
 *
 * @author KOCMOC
 */
public class BOX_PARAMS {
    //RECIPE_INITIAL GROUP A

    public static final CBoxParam RECIPE_ORIGIN = new CBoxParam("Recipe_Origin", false);
    public static final CBoxParam RECIPE_STAGE = new CBoxParam("[Recipe Stage]", false);
    public static final CBoxParam RECIPE_VERSION = new CBoxParam("[Recipe Version]", false);
    public static final CBoxParam RECIPE_ADDITIONAL = new CBoxParam("Recipe_Addditional", false);
    public static final CBoxParam DESCR = new CBoxParam("Descr", false);
    public static final CBoxParam DETAILED_GROUP = new CBoxParam("Detailed_Group", false);
    public static final CBoxParam MIXER_CODE = new CBoxParam("Mixer_Code", "Name", false);
    public static final CBoxParam STATUS = new CBoxParam("Status", false);
    public static final CBoxParam CLASS = new CBoxParam("Class", false);
    //RECIPE_INITIAL GROUP C
    public static final CBoxParam COLOR = new CBoxParam("Color:", false);
    public static final CBoxParam INDUSTRY = new CBoxParam("industry:", false);
    public static final CBoxParam RECIPE_TYPE = new CBoxParam("recept type:", false);
    public static final CBoxParam CURING_SYSTEM = new CBoxParam("curing system:", false);
    public static final CBoxParam CURING_PROCESS = new CBoxParam("curing process:", false);
    public static final CBoxParam FILLER = new CBoxParam("filler:", false);
    public static final CBoxParam CERTIFICATE = new CBoxParam("certificate:", false);
    public static final CBoxParam SCHELFLIFE_1 = new CBoxParam("schelflife(weeks):", false);
    public static final CBoxParam SCHELFLIFE_2 = new CBoxParam("schelflife(weeks):", false);
    public static final CBoxParam HARDNESS_SHA_1 = new CBoxParam("Hardnes Sha:", false);
    public static final CBoxParam HARDNESS_SHA_2 = new CBoxParam("Hardnes Sha:", false);
    public static final CBoxParam CUSTOMER = new CBoxParam("Customer", false);
    //
    //
    //INGREDIENTS GROUP A
    public static final CBoxParam INGRED_NAME = new CBoxParam("Name", false);
    public static final CBoxParam INGRED_GROUP = new CBoxParam("Grupp", false);
    public static final CBoxParam INGRED_DESCR = new CBoxParam("Descr", false);
    public static final CBoxParam INGRED_CLASS = new CBoxParam("Class", false);
    public static final CBoxParam INGRED_STATUS = new CBoxParam("Status", false);
    public static final CBoxParam INGRED_FORM = new CBoxParam("Form", false);
    public static final CBoxParam CAS_NUMBER = new CBoxParam("Cas_Number", false);
    public static final CBoxParam TRADE_NAME = new CBoxParam("TradeName", false);
    public static final CBoxParam VENDOR_NAME = new CBoxParam("VendorName", false);
    public static final CBoxParam PERC_RUBBER = new CBoxParam("percRubber", false);
    //
    //
    //TEST PARAMETERS
    public static final CBoxParam TP_ORDER = new CBoxParam("ORDERNO", false);
    public static final CBoxParam TP_RECIPE = new CBoxParam("CODE", false);
    
}
