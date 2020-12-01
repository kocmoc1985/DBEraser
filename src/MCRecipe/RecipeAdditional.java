/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import MCRecipe.Sec.PROC;
import MyObjectTableInvert.BasicTab;
import MyObjectTableInvert.RowDataInvert;
import forall.HelpA;
import forall.SqlBasicLocal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public class RecipeAdditional extends BasicTab {

    private final MC_RECIPE mCRecipe;

    public RecipeAdditional(SqlBasicLocal sql, SqlBasicLocal sql_additional, MC_RECIPE mCRecipe) {
        super(sql, sql_additional, mCRecipe);
        this.mCRecipe = mCRecipe;
        go();
    }

    public void table1Repport() {
        tableCommonExportOrRepport(mCRecipe.jTable_1_RecipeAdd, false);
   }

    public void table2Repport() {
        tableCommonExportOrRepport(mCRecipe.jTable_2_RecipeAdd, true);
    }

    private void go() {
        //
        mCRecipe.addListenersRecipeAdditional();
        //
        recipeAdditionalTabClicked();
        //
    }

    public void recipeAdditionalTabClicked() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                fillComboOrders();
                mCRecipe.jComboBoxRecipeAdditionalOrders.setSelectedIndex(0);
                fillTable1();
                fillTable2();
            }
        });
    }

    private void fillComboOrders() {
        //
        String code = getRecipeCode();
        //
        if (code == null) {
            return;
        }
        //
        String q = SQL_A_.recipe_additional_fill_combo_orders(PROC.PROC_03,code);
        //
        OUT.showMessage(q);
        //
        HelpA.fillComboBox(sql, mCRecipe.jComboBoxRecipeAdditionalOrders, q, null, true,false);
    }

    public void fillTable2() {
        //
        String code = getRecipeCode();
        String order = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxRecipeAdditionalOrders);
        //
        if (code == null || order == null) {
            return;
        }
        //
        String q = SQL_A_.recipe_additional_build_table_2(PROC.PROC_02,code, order);
        //
        try {
            ResultSet rs = sql.execute(q,mCRecipe);
            //
            HelpA.build_table_common(rs, mCRecipe.jTable_2_RecipeAdd,q);
            //
            HelpA.setTrackingToolTip(mCRecipe.jTable_2_RecipeAdd, q);
            //
        } catch (SQLException ex) {
            Logger.getLogger(RecipeAdditional.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fillTable1() {
        //
        String code = getRecipeCode();
        //
        if (code == null) {
            return;
        }
        //
        String q = SQL_A_.recipe_additional_build_table_1(PROC.PROC_04,code);
        //
        try {
            ResultSet rs = sql.execute(q,mCRecipe);
            //
            HelpA.build_table_common(rs, mCRecipe.jTable_1_RecipeAdd,q);
            //
            HelpA.setTrackingToolTip(mCRecipe.jTable_1_RecipeAdd, q);
            //
        } catch (SQLException ex) {
            Logger.getLogger(RecipeAdditional.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getRecipeCode() {
        //
        JTable table = mCRecipe.jTable1;
        //
        int currRow = table.getSelectedRow();
        //
        if (currRow == -1) {
            return null;
        }
        //
        return HelpA.getValueSelectedRow(table, RecipeInitial.T1_RECIPE_VERSION);
    }

    @Override
    public void initializeSaveIndicators() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showTableInvert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fillNotes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getUnsaved(int nr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
