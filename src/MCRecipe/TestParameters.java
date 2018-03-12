/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import MCRecipe.Lang.T_INV;
import MCRecipe.Sec.PROC;
import MyObjectTable.SaveIndicator;
import MyObjectTableInvert.BasicTab;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert;
import forall.HelpA;
import forall.JComboBoxA;
import forall.SqlBasicLocal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JTable;
import mySwing.JTableM;

/**
 *
 * @author KOCMOC
 */
public class TestParameters extends BasicTab {

    private final MC_RECIPE mCRecipe;
    private TableBuilderInvert TABLE_BUILDER_INVERT;
    private final JTableM jTable_1;

    public TestParameters(SqlBasicLocal sql, SqlBasicLocal sql_additional, MC_RECIPE mCRecipe) {
        super(sql, sql_additional, mCRecipe);
        this.mCRecipe = mCRecipe;
        //
        this.jTable_1 = (JTableM) this.mCRecipe.jTable_1_test_parameters;
        //
        init();
    }

    private void init() {
        mCRecipe.testParameters_GroupA_Boxes_to_list();
        mCRecipe.addJComboListenersTestParameters();
        //
        initializeSaveIndicators();
//        fillTable1();
    }

    private static long prevCall;

    private boolean delay() {
        if (Math.abs(System.currentTimeMillis() - prevCall) < 1000) {
            prevCall = System.currentTimeMillis();
            return false;
        } else {
            prevCall = System.currentTimeMillis();
            return true;
        }
    }

    public void fillComboBox(final JComboBox box, final String colName) {
        //
        if (delay() == false) {
            return;
        }
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //
                Object selection = box.getSelectedItem();
                //
                //
                String q = SQL_A.fill_comboboxes_test_parameters_a(PROC.PROC_66, colName, getOrder(), getRecipe());
                OUT.showMessage(q);
                //
                JComboBoxA boxA = (JComboBoxA) box;
                boxA.fillComboBox(sql, box, q, null, false, false);
                //
                box.setSelectedItem(selection);
            }
        });
    }

    public void fillTable1() {
        //
        String order = getOrder();
        String recipe = getRecipe();
        //
        if (order == null && recipe == null) {
            return;
        }
        //
        try {
            String q = SQL_A.fn_ITF_Test_Related(PROC.PROC_66, getOrder(), getRecipe());
            OUT.showMessage(q);
            ResultSet rs = sql.execute(q, OUT);
            //
            if(rs.next() == false){
                return;
            }
            //
            jTable_1.build_table_common(rs, q);
            //
            jTable_1.setSelectedRow(0);
            //
            jTable_1.validate();
            jTable_1.revalidate();
            jTable_1.repaint();
            //
            hideColsJTable();
            //
        } catch (SQLException ex) {
            Logger.getLogger(TestParameters.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void hideColsJTable() {
        jTable_1.hideColumnByName("ID_Wotest");
        jTable_1.hideColumnByName("ID_Proc");
        jTable_1.hideColumnByName("Ingredient_Vulco_Code_ID");
        jTable_1.hideColumnByName("Ingredient_Aeging_Code_ID");
    }

    public String getOrder() {
        //
        String val = HelpA.getComboBoxSelectedValueB(mCRecipe.jComboBoxTestParams_Order);
        //
        return val;
//        return "ENTW000001";
    }

    //mCRecipe.jComboBoxTestPararams_Recipe
    public String getRecipe() {
        //
        String val = HelpA.getComboBoxSelectedValueB(mCRecipe.jComboBoxTestPararams_Recipe);
        //
        if (val == null) {
            //
            JTable table = mCRecipe.jTable1;
            //
            if (mCRecipe.recipeInitial != null) {
                return HelpA.getValueSelectedRow(table, RecipeInitial.T1_RECIPE_VERSION);
            }
        }
        //
        return val;
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //MCcpwotest
        RowDataInvert recipe = new RowDataInvert("MCcpwotest", "ID_Wotest", false, "CODE", T_INV.LANG("RECIPE"), "", true, true, false);
        recipe.setUneditable();
        RowDataInvert order = new RowDataInvert("MCcpwotest", "ID_Wotest", false, "ORDERNO", T_INV.LANG("ORDER"), "", true, true, false);
        order.setUneditable();
        RowDataInvert testCode = new RowDataInvert("MCcpwotest", "ID_Wotest", false, "TESTCODE", T_INV.LANG("TEST CODE"), "", true, true, false);
        RowDataInvert prefvulc = new RowDataInvert("MCcpwotest", "ID_Wotest", false, "PREFVULC", T_INV.LANG("PREFVULC"), "", true, true, false);
        RowDataInvert prefage = new RowDataInvert("MCcpwotest", "ID_Wotest", false, "PREFAGE", T_INV.LANG("PREFAGE"), "", true, true, false);

        //MCCPTproc
        RowDataInvert testVar = new RowDataInvert("MCCPTproc", "ID_Proc", false, "TESTVAR", T_INV.LANG("TESTVAR"), "", true, true, false);
        testVar.enableToolTipTextJTextField();
        //
        RowDataInvert testCond = new RowDataInvert("MCCPTproc", "ID_Proc", false, "TESTCOND", T_INV.LANG("TEST CONDITION"), "", true, true, false);
        RowDataInvert version = new RowDataInvert("MCCPTproc", "ID_Proc", false, "VERSION", T_INV.LANG("VERSION"), "", true, true, false);
        RowDataInvert norm = new RowDataInvert("MCCPTproc", "ID_Proc", false, "NORM", T_INV.LANG("NORM"), "", true, true, false);
        RowDataInvert group = new RowDataInvert("MCCPTproc", "ID_Proc", false, "GROUP", T_INV.LANG("GROUP"), "", true, true, false);
        RowDataInvert report = new RowDataInvert("MCCPTproc", "ID_Proc", false, "REPORT", T_INV.LANG("REPORT"), "", true, true, false);
        RowDataInvert note = new RowDataInvert("MCCPTproc", "ID_Proc", false, "NOTE", T_INV.LANG("NOTE"), "", true, true, false);

        //Ingredient_Vulco_Code
        RowDataInvert vulcoinfo = new RowDataInvert("Ingredient_Vulco_Code", "Ingredient_Vulco_Code_ID", false, "Descr", T_INV.LANG("VULCO INFO"), "", true, true, false);

        //Ingredient_Aeging_Code
        RowDataInvert ageinfo = new RowDataInvert("Ingredient_Aeging_Code", "Ingredient_Aeging_Code_ID", false, "Aeging_Info", T_INV.LANG("AEGING INFO"), "", true, true, false);
        ageinfo.setUneditable();
        //
        RowDataInvert[] rows = {
            recipe, order, testCode, testVar, testCond,
            version, norm, group, prefvulc,
            vulcoinfo,
            prefage, ageinfo, report, note
        };
        //
        return rows;
    }

    @Override
    public void showTableInvert() {
        //
        //
        TABLE_BUILDER_INVERT = new TableBuilderInvert(OUT, sql, getConfigTableInvert(), false, "test_parameters_table");
        //
        TABLE_INVERT = null;
        //
        String order = getOrder();
        String recipe = getRecipe();
        //
        System.out.println("RECIPE: " + recipe);
        //
        String id = jTable_1.getValueSelectedRow("ID");
        //
        try {
            String q = SQL_A.fn_ITF_Test_Related_ID(PROC.PROC_67, order, recipe, id);
            OUT.showMessage(q);
            TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q);
        } catch (SQLException ex) {
            Logger.getLogger(TestParameters.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT.showMessage(ex.toString());
        }
        //
        showTableInvert(mCRecipe.jPanel_Test_Params_Inv_Table_1);
        //
    }

    @Override
    public void initializeSaveIndicators() {
        SaveIndicator saveIndicator = new SaveIndicator(mCRecipe.jButtonTestParametersSave_InvertTable, this, 1);
    }

    @Override
    public void fillNotes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getUnsaved(int nr) {
        if (nr == 1) {
            //
            if (TABLE_INVERT == null) {
                return false;
            } else if (TABLE_INVERT.unsaved_entries_map.isEmpty() == false) {
                return true;
            }
            //
        }
        //
        return false;
    }

}
