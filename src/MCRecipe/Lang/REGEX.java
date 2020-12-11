/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Lang;

import forall.GP;

/**
 *
 * @author KOCMOC
 */
public class REGEX {

    private static final String INGRED_REGEX_DESCR__QEW = "55555";
    private static final String INGRED_REGEX__QEW = "\\d{5}"; //5 digits no spaces: 00000
    private static final String RECIPE_REGEX_DESCR__QEW = "22-1-A333";
    private static final String RECIPE_REGEX__QEW = "(\\d{2})(-)(\\d{1})(-)(\\w{1})(\\d{3})"; // 00-8-N752: two digits - one digit - digit or letter - 3 digits

    private static final String AGING_CODE__VULC_CODE_REGEX__DW = "\\d{5}"; //5 digits no spaces: 00000
    
    public static String getAgeingVulcCodeRegex() {
        if (GP.COMPANY_NAME.equals(GP.COMPANY_NAME_DATWILLER)) {
            return AGING_CODE__VULC_CODE_REGEX__DW;
        } else {
            return null;
        }
    }
    
    
    public static String getIngredRegexDescr() {
        if (GP.COMPANY_NAME.equals(GP.COMPANY_NAME_QEW)) {
            return INGRED_REGEX_DESCR__QEW;
        } else {
           return "undefined";
        }
    }

    public static String getIngredRegex() {
        if (GP.COMPANY_NAME.equals(GP.COMPANY_NAME_QEW)) {
            return INGRED_REGEX__QEW;
        } else {
            return null;
        }
    }

    public static String getRecipeRegex() {
        if (GP.COMPANY_NAME.equals(GP.COMPANY_NAME_QEW)) {
            return RECIPE_REGEX__QEW;
        } else {
            return null;
        }
    }

    public static String getRecipeRegexDescr() {
        if (GP.COMPANY_NAME.equals(GP.COMPANY_NAME_QEW)) {
            return RECIPE_REGEX_DESCR__QEW;
        } else {
            return "undefined";
        }
    }
}
