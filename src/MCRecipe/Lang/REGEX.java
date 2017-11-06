/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Lang;

/**
 *
 * @author KOCMOC
 */
public class REGEX {
    
    public static String INGREDIENT_CODE_REGEX = "\\d{5}"; //5 digits no spaces: 00000
    public static String RECIPE_REGEX = "(\\d{2})(-)(\\d{1})(-)(\\w{1})(\\d{3})"; // 00-8-N752: two digits - one digit - digit or letter - 3 digits
}
