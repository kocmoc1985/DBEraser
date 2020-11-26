/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Sec;

import forall.HelpA;
import static forall.HelpA.getValueGivenRow;
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public class HelpM {

    public static String resizeTextRtf(String textToResize) {
        //
        for (int i = 10; i <= 34; i++) {
            textToResize = textToResize.replace("fs" + i, "fs35");
        }
        //
        return textToResize;
    }

    public static String checkValuesTable4(String value) {
        if (value == null) {
            return "0";
        }
        if (value.contains(",")) {
            return value.replace(",", ".");
        } else {
            return value;
        }
    }

    public static int searchForRecipeIdTable4(JTable table) {
        for (int i = 0; i < table.getRowCount(); i++) {
            int recipeId = searchForRecipeIdTable4_h(table, i);
            if (recipeId != -1) {
                return recipeId;
            }
        }
        return -1;
    }

    private static int searchForRecipeIdTable4_h(JTable table, int row) {
        String recipeId = (String) table.getValueAt(row, HelpA.getColByName(table, "RecipeID"));
        if (recipeId == null) {
            return -1;
        } else {
            return Integer.parseInt(recipeId);
        }
    }

    public static String recipeDetailedGetLatestAddedId(JTable table, String colName) {
        int max = 0;
        int act_val;
        for (int i = 0; i < table.getRowCount(); i++) {
            try {
                act_val = Integer.parseInt(getValueGivenRow(table, i, colName));
            } catch (Exception ex) {
                act_val = 0;
            }
            if (act_val > max) {
                max = act_val;
            }
        }
        return "" + max;
    }
//    public static void main(String[] args) {
//        System.out.println("" + checkValuesTable4("0,25"));
//    }
}
