/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import MCRecipe.Sec.CustomPanelCp;
import MCRecipe.Sec.PROC;
import forall.HelpA;
import forall.SqlBasicLocal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JComboBox;

/**
 *
 * @author KOCMOC
 */
public class RecipeInitialCp extends RecipeInitial {

    private final CustomPanelCp customPanel;

    public RecipeInitialCp(MC_RECIPE mCRecipe2, SqlBasicLocal sql) {
        super(mCRecipe2, sql);
        this.customPanel = (CustomPanelCp) mCRecipe2.customPanelRecipeInitial;
    }

    @Override
    public ArrayList<String> getComboParamsA_h_two() {
//        //
        ArrayList<String> list = new ArrayList<String>();
        //
        String cost_from = HelpA.getComboBoxSelectedValue(customPanel.jComboBoxRecipeInitial_Cost_from);
        String cost_to = HelpA.getComboBoxSelectedValue(customPanel.jComboBoxRecipeInitial_Cost_to);
        String vk_preis_from = HelpA.getComboBoxSelectedValue(customPanel.jComboBox_vk_preis_von);
        String vk_preis_to = HelpA.getComboBoxSelectedValue(customPanel.jComboBox_vk_preis_bis);
        String density_from = HelpA.getComboBoxSelectedValue(customPanel.jComboBox_dichte_von);
        String density_to = HelpA.getComboBoxSelectedValue(customPanel.jComboBox_dichte_bis);
        String charge_from = HelpA.getComboBoxSelectedValue(customPanel.jComboBox_charge_von);
        String charge_to = HelpA.getComboBoxSelectedValue(customPanel.jComboBox_charge_bis);
        String shelflife_from = HelpA.getComboBoxSelectedValue(customPanel.jComboBox_haltbarkeit_von);
        String shelflife_to = HelpA.getComboBoxSelectedValue(customPanel.jComboBox_haltbarkeit_bis);
        String hardness_from = HelpA.getComboBoxSelectedValue(customPanel.jComboBox_hardness_from);
        String hardness_to = HelpA.getComboBoxSelectedValue(customPanel.jComboBox_hardness_to);
        //
        String[] arr = new String[]{cost_from, cost_to, vk_preis_from, vk_preis_to, density_from, density_to,
            charge_from, charge_to, shelflife_from, shelflife_to, hardness_from, hardness_to};
        //
        list.addAll(Arrays.asList(arr));
        //
        return list;

        //
        //
        // It would be nice to solve it like that but the order of parameters is 
        // important!  (PROC_33 = "prc_ITF_Recipes_Z_A ")
        //
//        ArrayList<String> list = new ArrayList<String>();
//        //
//        for(JComboBox box: mCRecipe2.recipeInitialGroupC){
//            list.add(HelpA.getComboBoxSelectedValue(box));
//        }
//        //
//        return list;
    }

}
