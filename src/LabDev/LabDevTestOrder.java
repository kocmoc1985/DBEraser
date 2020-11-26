/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import static LabDev.LabDevelopment_.TABLE__AGEMENT;
import static LabDev.LabDevelopment_.TABLE__MCCPWOTEST;
import static LabDev.LabDevelopment_.TABLE__TEST_PROCEDURE;
import static LabDev.LabDevelopment_.TABLE__VULC;
import LabDev.sec.CreateNewFromTable;
import MCRecipe.Lang.MSG;
import MCRecipe.Lang.T_INV;
import MCRecipe.SQL_A;
import MCRecipe.SQL_B;
import MCRecipe.Sec.PROC;
import MCRecipe.TestParameters_;
import MyObjectTable.SaveIndicator;
import MyObjectTable.ShowMessage;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert;
import forall.HelpA_;
import forall.JComboBoxA;
import forall.SqlBasicLocal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public class LabDevTestOrder extends LabDevTab_ implements ActionListener, ItemListener, MouseListener {

    private TableBuilderInvert TABLE_BUILDER_INVERT;

    public LabDevTestOrder(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment_ labDev) {
        super(sql, sql_additional, OUT, labDev);
        init();
    }

    private void init() {
        //
        initializeSaveIndicators();
        getSaveButton().addActionListener(this);
        getFilterBtn().addActionListener(this);
        getRemoveFilterBtn().addActionListener(this);
        getCreateNewButton().addActionListener(this);
        getComboBoxTestCode().addItemListener(this);
        getComboBoxMaterial().addItemListener(this);
        getTable().addMouseListener(this);
        //
        addMouseListenerJComboBox__mcs(getComboBoxMaterial(), this);
        addMouseListenerJComboBox__mcs(getComboBoxTestCode(), this);
        //
    }

    private String getTestCode() {
        // EX: VUG01
        HelpA_.ComboBoxObject cbo = (HelpA_.ComboBoxObject) HelpA_.getSelectedComboBoxObject(getComboBoxTestCode());
        if (cbo == null) {
            return "NULL";
        } else {
            return cbo.getParamAuto();
        }
    }

    private String getMaterial() {
        // EX: WE8486
        HelpA_.ComboBoxObject cbo = (HelpA_.ComboBoxObject) HelpA_.getSelectedComboBoxObject(getComboBoxMaterial());
        if (cbo == null) {
            return "NULL";
        } else {
            return cbo.getParamAuto();
        }
    }

    public void refresh() {
        showTableInvert();
    }

    private void refresh_b(JTable table) {
        fillJTable();
        HelpA_.markFirstRowJtable(table);
        mouseClickedOnTable(table);
    }

    private JButton getSaveButton() {
        return mcRecipe.jButton__lab_dev__new;
    }

    private JButton getCreateNewButton() {
        return mcRecipe.jButton_lab_dev__test_order_create_new;
    }

    private JButton getRemoveFilterBtn() {
        return mcRecipe.jButton_lab_dev__test_order__clear_filter;
    }

    private JButton getFilterBtn() {
        return mcRecipe.jButton_lab_dev__test_order__filter_btn;
    }

    public JTable getTable() {
        return mcRecipe.jTable_lab_dev__new;
    }

    private JComboBox getComboBoxTestCode() {
        return mcRecipe.jComboBox_lab_dev__test_order__testcode;
    }

    private JComboBox getComboBoxMaterial() {
        return mcRecipe.jComboBox_lab_dev__test_order__material;
    }

    private void fillJTable() {
        //
        JTable table = getTable();
        //
        String q = SQL_A.lab_dev__test_order(PROC.PROC_75, labDev.getOrderNo(), getMaterial(), getTestCode(), null);
        //
        HelpA_.build_table_common(sql, OUT, table, q, new String[]{"ORDERNO", "ID_Wotest", "UpdatedOn",
            "UpdatedBy", "TESTREM1", "TESTREM2", "SCOPE", "TagId"});
        //
//        HelpA_.setColumnWidthByName("TESTVAR", table, 0.28);
        //
    }

    @Override
    public void fillNotes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
//       [ORDERNO] ---> ??? Needed ?
//      ,[SCOPE]
//      ,[CODE]
//      ,[PREFVULC]
//      ,[PREFAGE]
//      ,[TESTCODE]
//      ,[TESTCOND]
//      ,[TESTREM1]
//      ,[TESTREM2]
//      ,[UpdatedOn]
//      ,[Test_Condition_NUM]
//      ,[UpdatedBy]
//      ,[ID_Wotest]
//      ,[TagId]
        //
        RowDataInvert scope = new RowDataInvert(TABLE__MCCPWOTEST, "ID_Wotest", false, "SCOPE", T_INV.LANG("SCOPE"), "", true, true, false);
        RowDataInvert code = new RowDataInvert(TABLE__MCCPWOTEST, "ID_Wotest", false, "CODE", T_INV.LANG("CODE"), "", true, true, false);
        code.setUneditable();
        //
        String q_1 = SQL_B.basic_combobox_query("VULCCODE", TABLE__VULC);
        RowDataInvert prefvulc = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_1, sql_additional, "", TABLE__MCCPWOTEST, "ID_Wotest", false, "PREFVULC", T_INV.LANG("PREFVULC"), "", true, true, false);
        //
        String q_2 = SQL_B.basic_combobox_query("AGEINGCODE", TABLE__AGEMENT);
        RowDataInvert prefage = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_2, sql_additional, "", TABLE__MCCPWOTEST, "ID_Wotest", false, "PREFAGE", T_INV.LANG("PREFAGE"), "", true, true, false);
        //
        RowDataInvert testcode = new RowDataInvert(TABLE__MCCPWOTEST, "ID_Wotest", false, "TESTCODE", T_INV.LANG("TESTCODE"), "", true, true, false);
        testcode.setUneditable();
        RowDataInvert testvar = new RowDataInvert(TABLE__MCCPWOTEST, "ID_Wotest", false, "TESTVAR", T_INV.LANG("TESTVAR"), "", true, true, false);
        testvar.setDisabled();
        RowDataInvert testcond = new RowDataInvert(TABLE__MCCPWOTEST, "ID_Wotest", false, "TESTCOND", T_INV.LANG("TESTCOND"), "", true, true, true);
        RowDataInvert testrem1 = new RowDataInvert(TABLE__MCCPWOTEST, "ID_Wotest", false, "TESTREM1", T_INV.LANG("TESTREM1"), "", true, true, false);
        RowDataInvert testrem2 = new RowDataInvert(TABLE__MCCPWOTEST, "ID_Wotest", false, "TESTREM2", T_INV.LANG("TESTREM2"), "", true, true, false);
//        RowDataInvert test_cond_num = new RowDataInvert(TABLE__MCCPWOTEST, "ID_Wotest", false, "Test_Condition_NUM", T_INV.LANG("TEST CONDITION NUM"), "", true, true, false);
//        RowDataInvert tagId = new RowDataInvert(TABLE__MCCPWOTEST, "ID_Wotest", false, "TagId", T_INV.LANG("TAG ID"), "", true, true, false);
        //
        RowDataInvert updatedOn = new RowDataInvert(TABLE__MCCPWOTEST, "ID_Wotest", false, "UpdatedOn", T_INV.LANG("UPDATED ON"), "", true, false, false);
        RowDataInvert updatedBy = new RowDataInvert(TABLE__MCCPWOTEST, "ID_Wotest", false, "UpdatedBy", T_INV.LANG("UPDATED BY"), "", true, false, false);
        //
        RowDataInvert[] rows = {code, testcode, prefvulc, prefage, scope,
            testvar, testcond, testrem1, testrem2, updatedOn, updatedBy};
        //
        return rows;
    }

    @Override
    public void showTableInvert() {
        //
        TABLE_BUILDER_INVERT = new TableBuilderInvert(OUT, sql, getConfigTableInvert(), false, "lab_dev__new");
        //
        TABLE_INVERT = null;
        //
        String id = HelpA_.getValueSelectedRow(getTable(), "ID_Wotest");
        //
        try {
//            String q = "SELECT * FROM " + TABLE__MCCPWOTEST + " WHERE ID_Wotest='" + id + "'";
            String q = SQL_A.lab_dev__test_order(PROC.PROC_75, labDev.getOrderNo(), getMaterial(), getTestCode(), id);
            OUT.showMessage(q);
            TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q, this);
        } catch (SQLException ex) {
            Logger.getLogger(TestParameters_.class
                    .getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT.showMessage(ex.toString());
        }
        //
        setVerticalScrollBarDisabled(TABLE_INVERT);
        //
        showTableInvert(mcRecipe.jPanel77);
        //
    }

    @Override
    public void initializeSaveIndicators() {
        SaveIndicator saveIndicator1 = new SaveIndicator(getSaveButton(), this, 1);
    }

    @Override
    public boolean getUnsaved(int nr) {
        //
        if (nr == 1) {
            //
            if (TABLE_INVERT == null) {
                return false;
            } else if (unsavedEntriesExist(TABLE_INVERT)) { //TABLE_INVERT.unsaved_entries_map.isEmpty() == false
                return true;
            }
            //
        }
        //
        return false;
    }

    private void saveTableInvert() {
        //
        if (containsInvalidatedFields(TABLE_INVERT, 1, getConfigTableInvert())) {
            HelpA_.showNotification(MSG.MSG_3());
            return;
        }
        //
        saveChangesTableInvert(TABLE_INVERT);
        //
        JTable table = getTable();
        //
        refresh_b(table);
        //
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //
        JTable table = getTable();
        //
        if (e.getSource().equals(getSaveButton())) {
            saveTableInvert();
        } else if (e.getSource().equals(getFilterBtn())) {
            refresh_b(getTable());
        } else if (e.getSource().equals(getRemoveFilterBtn())) {
            removeFilterButtonClicked(table);
        } else if (e.getSource().equals(getCreateNewButton())) {
            createNewButtonClicked();
        }
        //
    }

    private void createNewButtonClicked() {
        //
        String testCode = getTestCode();
        //
        if(testCode == null || testCode.isEmpty() || testCode.equals("NULL")){
            HelpA_.showNotification(MSG.MSG_7_4());
            return;
        }
        //
        String q = SQL_A.lab_dev_test_order__get_list_for_creating_new(PROC.PROC_76, getTestCode());
        //
        CreateNewFromTable cnft = new CreateNewFromTable(this, sql, q, new String[]{"ID_Proc"}, OUT,MSG.MSG_7_3());
        cnft.setVisible(true);
        //
    }

    /**
     * forwarded from "CreateNewFromTable.class"
     *
     * @param paramters
     * @param cnft
     */
    public void selectButtonClicked_a(String[] paramters, CreateNewFromTable cnft) {
        //
        cnft.dispose();
        //
        String order = paramters[0];
        String material = paramters[1];
        String testcode = paramters[2];
        String id = paramters[3];
        //
        System.out.println("CATCH, ID: " + id);
        //
        String insert_q = SQL_A.lab_dev_test_order__insert_new(PROC.PROC_77, order, material, testcode, id);
        //
        try {
            sql.execute(insert_q, OUT);
            HelpA_.showNotification(MSG.MSG_7());
        } catch (SQLException ex) {
            HelpA_.showNotification(MSG.MSG_7_2());
            Logger.getLogger(LabDevTestOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        refresh_b(getTable());
        //
    }

    private void removeFilterButtonClicked(JTable table) {
        //
        removeFilter__mcs(getComboBoxMaterial());
        removeFilter__mcs(getComboBoxTestCode());
        //
        HelpA_.clearAllRowsJTable(table);
        //
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //
        JTable table = getTable();
        //
        if (e.getSource() == table && e.getClickCount() == 1) {
            //
            mouseClickedOnTable(table);
            //
        }
        //
    }

    private void mouseClickedOnTable(JTable table) {
        showTableInvert();
        labDev.refreshHeader();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
        if (e.getSource() instanceof JButton) {
            //
            JButton button = (JButton) e.getSource();
            //
            if (button.getParent() instanceof JComboBox) {
                //
                JComboBoxA box = (JComboBoxA) button.getParent();
                String param = box.getPARAMETER();
                //
                String query = getQuery__mcs(PROC.PROC_75, param, getComboParams__mcs());
                fillComboBox__mcs(box, param, sql_additional, query);
                //
            }
        }
        //
    }

    @Override
    public String getQuery__mcs(String procedure, String colName, String[] comboParameters) {
        return SQL_A.lab_dev__test_order__fill__combos(procedure, colName, comboParameters);
    }

    @Override
    public String[] getComboParams__mcs() {
        //
        String ordernr = labDev.getOrderNo();
        String material = HelpA_.getComboBoxSelectedValue(getComboBoxMaterial());
        String testcode = HelpA_.getComboBoxSelectedValue(getComboBoxTestCode());
        //
        return new String[]{ordernr, material, testcode};
        //
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        //
        if (e.getStateChange() != 1) {
            return;
        }
        //
        HelpA_.ComboBoxObject cbo = (HelpA_.ComboBoxObject) e.getItem();
        String value = cbo.getParamAuto();
        //  
        if (e.getSource().equals(getComboBoxTestCode())) {
            //
            //
        } else if (e.getSource().equals(getComboBoxMaterial())) {
            //
            //
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }
}
