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
import MCRecipe.SQL_A_;
import MCRecipe.SQL_B;
import MCRecipe.Sec.PROC;
import MCRecipe.TestParameters_;
import MyObjectTable.SaveIndicator;
import MyObjectTable.ShowMessage;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert;
import forall.HelpA;
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
public class LabDevTestVariables extends LabDevTab_ implements ActionListener, ItemListener, MouseListener {

    private TableBuilderInvert TABLE_BUILDER_INVERT;

    public LabDevTestVariables(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment_ labDev) {
        super(sql, sql_additional, OUT, labDev);
        init();
    }

    private void init() {
        //
        initializeSaveIndicators();
        getSaveButton().addActionListener(this);
        getPrintJTableBtn().addActionListener(this);
        getPrintTableInvertBtn().addActionListener(this);
        getFilterBtn().addActionListener(this);
        getRemoveFilterBtn().addActionListener(this);
        getAddNewButton().addActionListener(this);
        getAddNewButton_B().addActionListener(this);
        getDeleteButton().addActionListener(this);
        getComboBoxTestCode().addItemListener(this);
        getComboBoxMaterial().addItemListener(this);
        getTable().addMouseListener(this);
        //
        addMouseListenerJComboBox__mcs(getComboBoxMaterial(), this);
        addMouseListenerJComboBox__mcs(getComboBoxTestCode(), this);
        //
        java.awt.EventQueue.invokeLater(() -> { // OBS! Important to run here with invoke later
            refresh();
        });
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
            refresh_b();
        } else if (e.getSource().equals(getRemoveFilterBtn())) {
            removeFilterButtonClicked(table);
        } else if (e.getSource().equals(getAddNewButton())) {
            createNewButtonClicked(false);
        } else if (e.getSource().equals(getAddNewButton_B())) {
            createNewButtonClicked(true);
        } else if (e.getSource().equals(getDeleteButton())) {
            deleteButtonClicked();
        } else if (e.getSource().equals(getPrintTableInvertBtn())) {
            tableInvertExportOrRepport(TABLE_INVERT, 1, getConfigTableInvert());
        } else if (e.getSource().equals(getPrintJTableBtn())) {
            tableCommonExportOrRepport(table, false);
        }
        //
    }

    private String getTestCode() {
        // EX: VUG01
        JTable table = getTable();
        //
        if (HelpA.rowSelected(table)) {
            return HelpA.getValueSelectedRow(table, "TestCode");
        }
        // 
        HelpA.ComboBoxObject cbo = (HelpA.ComboBoxObject) HelpA.getSelectedComboBoxObject_b(getComboBoxTestCode());
        if (cbo == null) {
            return "NULL";
        } else {
            return cbo.getParamAuto();
        }
    }

    private String getMaterial() {
        // EX: WE8486
        JTable table = getTable();
        //
        if (HelpA.rowSelected(table)) {
            return HelpA.getValueSelectedRow(table, "CODE");
        }
        // 
        HelpA.ComboBoxObject cbo = (HelpA.ComboBoxObject) HelpA.getSelectedComboBoxObject_b(getComboBoxMaterial());
        if (cbo == null) {
            return "NULL";
        } else {
            return cbo.getParamAuto();
        }
    }

    public void refresh() {
        refresh_a();
    }

    private void refresh_a() {
        //
        removeFilter__mcs(getComboBoxMaterial());
        removeFilter__mcs(getComboBoxTestCode());
        //
        JTable table = getTable();
        fillJTable(getTestCode(), labDev.getMaterial());
        HelpA.markFirstRowJtable(table);
        mouseClickedOnTable(table);
    }

    private void refresh_b() {
        JTable table = getTable();
        fillJTable(getTestCode(), getMaterial());
        HelpA.markFirstRowJtable(table);
        mouseClickedOnTable(table);
    }

    private void refresh_c(String testCode) {
        JTable table = getTable();
        fillJTable(testCode, getMaterial());
        HelpA.markFirstRowJtable(table);
        mouseClickedOnTable(table);
        //
        removeFilter__mcs(getComboBoxMaterial());
        removeFilter__mcs(getComboBoxTestCode());
        //
    }

    private JButton getSaveButton() {
        return mcRecipe.jButton__lab_dev__new;
    }

    private JButton getPrintTableInvertBtn() {
        return mcRecipe.jButton_lab_dev__test_vars_print_invert;
    }

    private JButton getPrintJTableBtn() {
        return mcRecipe.jButton_lab_dev_test_vars__print_jtable;
    }

    private JButton getDeleteButton() {
        return mcRecipe.jButton_lab_dev__delete_btn;
    }

    private JButton getAddNewButton_B() {
        return mcRecipe.jButton_lab_dev_test_order__add_new_b;
    }

    private JButton getAddNewButton() {
        return mcRecipe.jButton_lab_dev__test_order_add_new;
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

    private void fillJTable(String testCode, String material) {
        //
        JTable table = getTable();
        //
        HelpA.clearAllRowsJTable(table);
        //
        String q = SQL_A_.lab_dev__test_variable(PROC.PROC_75, labDev.getOrderNo(), material, testCode, null);
        //
        HelpA.build_table_common(sql, OUT, table, q, new String[]{"ORDERNO", "ID_Wotest", "UpdatedOn",
            "UpdatedBy", "TESTREM1", "TESTREM2", "SCOPE", "TagId"}); // "TagId"
        //
        HelpA.setColumnWidthByName("TESTVAR", table, 0.28);
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
        testvar.enableToolTipTextJTextField();
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
        String id = HelpA.getValueSelectedRow(getTable(), "ID_Wotest");
        //
        if (id == null || id.isEmpty()) {
            return;
        }
        //
        JTable table = getTable();
        //
        try {
            //
            String material = HelpA.getValueSelectedRow(table, "CODE");
            String testCode = HelpA.getValueSelectedRow(table, "TestCode");
            //
            String q = SQL_A_.lab_dev__test_variable(PROC.PROC_75, labDev.getOrderNo(), material, testCode, id);
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
            HelpA.showNotification(MSG.LANG("Input contains errors"));
            return;
        }
        //
        saveChangesTableInvert(TABLE_INVERT);
        //
        JTable table = getTable();
        //
        refresh_b();
        //
    }

    private void deleteButtonClicked() {
        //
        if (HelpA.confirm(MSG.LANG("Confirm deletion?")) == false) {
            return;
        }
        //
        JTable table = getTable();
        //
        String id = HelpA.getValueSelectedRow(table, "TagId");
        String order = HelpA.getValueSelectedRow(table, "ORDERNO");
        String material = HelpA.getValueSelectedRow(table, "CODE");
        //
        String q = SQL_A_.lab_dev__test_variable__delete_button(id, order, material);
        //
        try {
            sql.execute(q, OUT);
            System.out.println("DELETE SUCCESS: ************************");
        } catch (SQLException ex) {
            Logger.getLogger(LabDevTestVariables.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("DELETE FAILED: ************************");
        }
        //
        refresh_b();
        //
    }

    private boolean addNewB_ = false;

    private void createNewButtonClicked(boolean addNewB) {
        //
        // addNewB == true -> "Double plus icon" -> Add new test procedure
        // addNewB == false -> "Single plus icon" -> Add test variable
        //
        String testCode = getTestCode();
        String material = getMaterial();
        //
        if (addNewB && (material == null || material.isEmpty() || material.equals("NULL"))) {
            HelpA.showNotification(MSG.LANG("Material not chosen"));
            return;
        }
        //
        if (addNewB == false && (testCode == null || testCode.isEmpty() || testCode.equals("NULL"))) {
            HelpA.showNotification(MSG.LANG("Test code not chosen"));
            return;
        }
        //
        String title = "Add test variable";
        String param1 = testCode;
        //
        if (addNewB) {
            addNewB_ = true;
            title = "Add new test procedure";
            param1 = null;
        }
        //
        //
        String q = SQL_A_.lab_dev_test_variable__get_list_for_creating_new(PROC.PROC_76, param1);
        //
        CreateNewFromTable cnft = new CreateNewFromTable(this, sql, q,
                new String[]{"ID_Proc"}, OUT, MSG.LANG(title)); // "ID_Proc"
        //
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
        String order = labDev.getOrderNo();
        String material = getMaterial();
        String testcode = paramters[0];
        String id = paramters[1];
        //
//        System.out.println("CATCH, ID: " + id);
        //
        String q_exist = SQL_A_.lab_dev_test__test_variable_check_exist(PROC.PROC_78, order, material, testcode, id);
        //
        boolean entry_exist = HelpA.entryExistsSql(sql, q_exist);
        //
        if (entry_exist) {
            HelpA.showNotification(MSG.LANG("Already exist! The entry will not be added"));
            return;
        }
        //
        String insert_q = SQL_A_.lab_dev_test_variable__insert_new(PROC.PROC_77, order, material, testcode, id);
        //
        try {
            sql.execute(insert_q, OUT);
            HelpA.showNotification(MSG.LANG("Operation successful"));
        } catch (SQLException ex) {
            HelpA.showNotification(MSG.LANG("Operation failed"));
            Logger.getLogger(LabDevTestVariables.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        if (this.addNewB_) {
            refresh_c(testcode);
            addNewB_ = false;
        } else {
            refresh_b();
        }
        //
        addToUnsaved(TABLE_INVERT, "PREFVULC", 1);
        addToUnsaved(TABLE_INVERT, "PREFAGE", 1);
        //
    }

    private void removeFilterButtonClicked(JTable table) {
        //
        removeFilter__mcs(getComboBoxMaterial());
        removeFilter__mcs(getComboBoxTestCode());
        //
        HelpA.clearAllRowsJTable(table);
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
        return SQL_A_.lab_dev__test_variable__fill__combos(procedure, colName, comboParameters);
    }

    @Override
    public String[] getComboParams__mcs() {
        //
        String ordernr = labDev.getOrderNo();
        String material = HelpA.getComboBoxSelectedValue(getComboBoxMaterial());
        String testcode = HelpA.getComboBoxSelectedValue(getComboBoxTestCode());
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
        HelpA.ComboBoxObject cbo = (HelpA.ComboBoxObject) e.getItem();
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
