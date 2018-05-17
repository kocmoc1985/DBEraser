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
    public void fill_table_1__h1(String q, String[] params) throws SQLException {
        q = SQL_A.recipeInitialBuildTable1_B(PROC.PROC_33_1, params);
        HelpA.runProcedureIntegerReturn_A(sql.getConnection(), q);
        fillTable1HelpM();
        OUT.showMessage(q);
    }
  

    @Override
    public ArrayList<String> getComboParamsA_h_two() {
        //
        ArrayList<String> list = new ArrayList<String>();
        //
        String cost_from = HelpA.getComboBoxSelectedValue(customPanel.jComboBoxRecipeInitial_Cost_from);
        //
        String[] arr = new String[]{cost_from};
        //
        list.addAll(Arrays.asList(arr));
        //
        return list;
    }

    @Override
    public void clearBoxesB() {
        customPanel.jComboBoxRecipeInitial_Cost_from.setSelectedItem(null);
    }

}
