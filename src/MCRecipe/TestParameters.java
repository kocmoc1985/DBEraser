/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import MCRecipe.Lang.T_INV;
import MCRecipe.Sec.PROC;
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
        fillTable1();
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

    private void fillTable1() {
        //
        String order = "ENTW000001";
        String recipe = "E 4373/B3 80";
        //
        try {
            String q = SQL_A.fn_ITF_Test_Related(PROC.PROC_66, order, recipe);
            OUT.showMessage(q);
            ResultSet rs = sql.execute(q, OUT);
            jTable_1.build_table_common(rs, q);
            //
            jTable_1.setSelectedRow(0);
            //
            jTable_1.validate();
            jTable_1.revalidate();
            jTable_1.repaint();
        } catch (SQLException ex) {
            Logger.getLogger(TestParameters.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getOrder() {
        return HelpA.getComboBoxSelectedValueB(mCRecipe.jComboBoxTestParams_Order);
//        return "ENTW000001";
    }

    private String getRecipe() {
        return HelpA.getComboBoxSelectedValueB(mCRecipe.jComboBoxTestPararams_Recipe);
//        return "E 4373/B3 80";
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        RowDataInvert order = new RowDataInvert("MCcpwotest", "ID_Wotest", false, "ORDERNO", T_INV.LANG("ORDER"), "", true, true, false);
        //
        //
        RowDataInvert[] rows = {order};
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
