/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import MCRecipe.Sec.PROC;
import MyObjectTable.SaveIndicator;
import MyObjectTableInvert.BasicTab;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert_;
import forall.GP;
import forall.HelpA_;
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
public class TestParameters_ extends BasicTab {

    private final MC_RECIPE_ mCRecipe;
    private TableBuilderInvert_ TABLE_BUILDER_INVERT;
    private final JTableM jTable_1;

    public TestParameters_(SqlBasicLocal sql, SqlBasicLocal sql_additional, MC_RECIPE_ mCRecipe) {
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
//        hideOrderComboBox();
        //
        initializeSaveIndicators();
//        fillTable1();
    }
    
    /**
     * Added [2020-03-24]
     * Not sure it will be used..
     */
    private void hideOrderComboBox(){
        if(GP.COMPANY_NAME.equals(GP.COMPANY_NAME_QEW)){
            mCRecipe.jComboBoxTestParams_Order.setVisible(false);
        }
    }

    public void clearBoxes() {
        //
        for (JComboBox box : mCRecipe.testParametersGroupA) {
            box.setSelectedItem(null);
            box.setEditable(false);
        }
        //
        mCRecipe.revalidate();
        mCRecipe.repaint();
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
                String q = SQL_A.fill_comboboxes_test_parameters_a(PROC.PROC_66, colName, getOrder(), getRecipe_B());
                OUT.showMessage(q);
                //
                JComboBoxA boxA = (JComboBoxA) box;
                HelpA_.fillComboBox(sql, box, q, null, false, false);
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
            //
            String q = SQL_A.fn_ITF_Test_Related(PROC.PROC_66, order, recipe);
            OUT.showMessage(q);
            //
            ResultSet rs = sql.execute(q, OUT);
            //
            boolean recordsExist = false;
            //
            if (rs.next()) {
                recordsExist = true;
            }
            //
            jTable_1.build_table_common(rs, q);
            //
            if(recordsExist){
                jTable_1.setSelectedRow(0);
            }
            //
            jTable_1.validate();
            jTable_1.revalidate();
            jTable_1.repaint();
            //
            if (jTable_1 != null) {
                HelpA_.setTrackingToolTip(jTable_1, q);
            }
            //
            hideColsJTable();
            //
        } catch (SQLException ex) {
            Logger.getLogger(TestParameters_.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    private final static String ID_1 = "ID_Wotest";
    private final static String ID_2 = "ID_Proc";
    private final static String ID_3 = "Ingredient_Vulco_Code_ID";
    private final static String ID_4 = "Ingredient_Aeging_Code_ID";
    
    
    private void hideColsJTable() {
        jTable_1.hideColumnByName(ID_1);
        jTable_1.hideColumnByName(ID_2);
        jTable_1.hideColumnByName(ID_3);
        jTable_1.hideColumnByName(ID_4);
    }

    private String getOrder() {
        //
        String val = HelpA_.getComboBoxSelectedValueB(mCRecipe.jComboBoxTestParams_Order);
        //
        return val;
//        return "ENTW000001";
    }

    //mCRecipe.jComboBoxTestPararams_Recipe
    private String getRecipe() {
        //
        String val = HelpA_.getComboBoxSelectedValueB(mCRecipe.jComboBoxTestPararams_Recipe);
        //
        //
        if (val == null) {
            //
            JTable table = mCRecipe.jTable1;
            //
            if (mCRecipe.recipeInitial != null) {
                //
                String rst = HelpA_.getValueSelectedRow(table, RecipeInitial.T1_RECIPE_VERSION);
                //
                mCRecipe.jComboBoxTestPararams_Recipe.setEnabled(true);
                mCRecipe.jComboBoxTestPararams_Recipe.setEditable(true);
                mCRecipe.jComboBoxTestPararams_Recipe.setSelectedItem(rst);
                //
                return rst;
            }
        }
        //
        return val;
    }

    private String getRecipe_B() {
        String val = HelpA_.getComboBoxSelectedValueB(mCRecipe.jComboBoxTestPararams_Recipe);
        return val;
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        if (GP.COMPANY_NAME.equals(GP.COMPANY_NAME_COMPOUNDS)) {
            return TableInvertConfigsCompany.testParameters_compounds();
        }else if(GP.COMPANY_NAME.equals(GP.COMPANY_NAME_QEW)){
            return TableInvertConfigsCompany.testParameters_qew();
        }else{
            return null;
        }
        //
    }

    @Override
    public void showTableInvert() {
        //
        //
        TABLE_BUILDER_INVERT = new TableBuilderInvert_(OUT, sql, getConfigTableInvert(), false, "test_parameters_table");
        //
        TABLE_INVERT = null;
        //
        String order = getOrder();
        String recipe = getRecipe();
        //
        if (order == null && recipe == null) {
            return;
        }
        //
//        System.out.println("RECIPE: " + recipe);
        //
        String id_1 = jTable_1.getValueSelectedRow(ID_1);
        String id_2 = jTable_1.getValueSelectedRow(ID_2);
        String id_3 = jTable_1.getValueSelectedRow(ID_3);
        String id_4 = jTable_1.getValueSelectedRow(ID_4);
        //
        try {
            String q = SQL_A.fn_ITF_Test_Related_ID(PROC.PROC_67, order, recipe, id_1, id_2, id_3, id_4);
            OUT.showMessage(q);
            TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q,this);
        } catch (SQLException ex) {
            Logger.getLogger(TestParameters_.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT.showMessage(ex.toString());
        }
        //
        showTableInvert(mCRecipe.jPanel_Test_Params_Inv_Table_1);
        //
    }

    public void searchButtonClicked() {
        fillTable1();
        showTableInvert();
    }

    public void tableInvertRepport() {
        tableInvertExportOrRepport(TABLE_INVERT, 1, getConfigTableInvert());
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
