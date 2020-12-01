/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import static LabDev.LabDevelopment_.TABLE__TEST_PROCEDURE;
import LabDev.sec.TestVarEntry;
import MCRecipe.Lang.MSG;
import MCRecipe.Lang.T_INV;
import MCRecipe.MC_RECIPE;
import MCRecipe.SQL_A_;
import MCRecipe.Sec.PROC;
import MCRecipe.TestParameters_;
import MyObjectTable.SaveIndicator;
import MyObjectTable.ShowMessage;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert;
import forall.HelpA;
import forall.SqlBasicLocal;
import forall.TextFieldCheck;
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
 * @author MCREMOTE
 */
public class LabDevTestProcedureTab extends LabDevTab_ implements ActionListener, ItemListener, MouseListener {

    private String TEST_CODE;
    private String ID_PROC;
    private TableBuilderInvert TABLE_BUILDER_INVERT;

    public LabDevTestProcedureTab(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment_ labDev) {
        super(sql, sql_additional, OUT, labDev);
        init();
    }

    private void init() {
        //
        JTable table = getTable();
        //
        initializeSaveIndicators();
        //
        getSaveBtn().addActionListener(this);
        getPrintJTableBtn().addActionListener(this);
        getPrintTableInvertBtn().addActionListener(this);
        getNewBtn().addActionListener(this);
        getCopyBtn().addActionListener(this);
        getDeleteBtn().addActionListener(this);
        getDeleteBtn_b().addActionListener(this);
        //
        getComboBox().addItemListener(this);
        table.addMouseListener(this);
        //
        fillComboBox();
        //
//        HelpA.markFirstRowJtable(table);
//        mouseClickedOnTable(table);
        //
    }

    public void refresh() {
        java.awt.EventQueue.invokeLater(() -> {
            showTableInvert();
            labDev.refreshHeader();
        });
    }

    public void refresh_b(String code, JTable table) {
        java.awt.EventQueue.invokeLater(() -> {
            //
            this.TEST_CODE = code;
            //
            fillJTable();
            HelpA.markFirstRowJtable(table);
            mouseClickedOnTable(table);
            //
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //
        if (e.getSource().equals(getSaveBtn())) {
            saveTableInvert();
        } else if (e.getSource().equals(getPrintJTableBtn())) {
            tableCommonExportOrRepport(getTable(), false);
        } else if (e.getSource().equals(getPrintTableInvertBtn())) {
            tableInvertExportOrRepport(TABLE_INVERT, 1, getConfigTableInvert());
        } else if (e.getSource().equals(getNewBtn())) {
            createNew(LabDevelopment_.TABLE__TEST_PROCEDURE, "CODE", null);
        } else if (e.getSource().equals(getCopyBtn())) {
            copy(LabDevelopment_.TABLE__TEST_PROCEDURE, "CODE", null);
        } else if (e.getSource().equals(getDeleteBtn())) {
            delete(true);
        } else if (e.getSource().equals(getDeleteBtn_b())) {
            delete(false);
        }
        //
    }

    private boolean delete(boolean deleteAll) {
        //
        JTable table = getTable();
        //
        String id = HelpA.getValueSelectedRow(table, "ID_Proc");
        String code = HelpA.getValueSelectedRow(table, "CODE");
        //

        //
        if (HelpA.rowSelected(table) == false) {
            HelpA.showNotification(MSG.LANG("Table row not chosen"));
            return false;
        }
        //
        if (MC_RECIPE.isAdminOrDeveloper() == false) {
            return false;
        }
        //
        String msg = deleteAll ? MSG.LANG("Confirm deletion of: ") + code + "?" : MSG.LANG("Confirm deletion of marked row");
        //
        if (HelpA.confirm(msg) == false) {
            return false;
        }
        //
        String q = SQL_A_.lab_dev_test_proc__delete(PROC.PROC_84, deleteAll ? code : null, id);
        //
        try {
            sql.execute(q, OUT);
            refresh_b(code, table);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LabDevMaterialInfoTab.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //
    }

    private boolean copy(String tableName, String colName, String regex) {
        //
        JTable table = getTable();
        //
        String code_old = HelpA.getValueSelectedRow(table, "CODE");
        //
        if (HelpA.rowSelected(table) == false) {
            HelpA.showNotification(MSG.LANG("Table row not chosen"));
            return false;
        }
        //
        String q = "SELECT DISTINCT " + colName + " from " + tableName + " WHERE " + colName + " = ?";
        //
        TextFieldCheck tfc = new TextFieldCheck(sql, q, regex, 15, 22);
        //
        boolean yesNo = HelpA.chooseFromJTextFieldWithCheck(tfc, MSG.LANG("Copy test procedure, type new code"));
        String code_new = tfc.getText();
        //
        if (code_new == null || yesNo == false) {
            return false;
        }
        //
        String q_copy = SQL_A_.lab_dev_test_proc__copy(PROC.PROC_83, code_new, code_old);
        //
        try {
            sql.execute(q_copy);
            refresh_b(code_new, table);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LabDevTestProcedureTab.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //
    }

    private boolean createNew(String tableName, String colName, String regex) {
        //
        String q = "SELECT DISTINCT " + colName + " from " + tableName + " WHERE " + colName + " = ?";
        //
        TextFieldCheck tfc = new TextFieldCheck(sql, q, regex, 15, 22);
        //
        boolean yesNo = HelpA.chooseFromJTextFieldWithCheck(tfc, MSG.LANG("Create new test procedure"));
        String code = tfc.getText();
        //
        if (code == null || yesNo == false) {
            return false;
        }
        //
        String q_insert = SQL_A_.lab_dev_test_proc__new(PROC.PROC_82, code);
        //
        try {
            sql.execute(q_insert, OUT);
            refresh_b(code, getTable());
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LabDevTestProcedureTab.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        return false;
        //
    }

    public String getCurrentId() {
        return ID_PROC;
    }

    private JButton getPrintTableInvertBtn() {
        return mcRecipe.jButton_lab_dev__testproc__print_invert;
    }

    private JButton getPrintJTableBtn() {
        return mcRecipe.jButton_lab_dev__testproc_print_jtable;
    }

    private JButton getSaveBtn() {
        return mcRecipe.jButton__lab__dev__test_proc__save;
    }

    private JButton getNewBtn() {
        return mcRecipe.jButton_lab_dev__test_procedure__new;
    }

    private JButton getDeleteBtn() {
        return mcRecipe.jButton_lab_dev__material_info_delete_proc;
    }

    private JButton getDeleteBtn_b() {
        return mcRecipe.jButton_lab_dev__material_info_delete_entry;
    }

    private JButton getCopyBtn() {
        return mcRecipe.jButton_lab_dev__test_proc__copy;
    }

    private JComboBox getComboBox() {
        return mcRecipe.jComboBox_lab_dev_test_proc;
    }

    public JTable getTable() {
        return mcRecipe.jTable_lab_dev__test_proc;
    }

    private void fillJTable() {
        //
        JTable table = getTable();
        //
        String q = "SELECT ID_Proc,CODE,TESTVAR,DESCRIPT,NORM, UpdatedOn,UpdatedBy FROM " + TABLE__TEST_PROCEDURE + " WHERE CODE=" + SQL_A_.quotes(TEST_CODE, false) + " ORDER BY NUM ASC";
        //
        HelpA.build_table_common(sql, OUT, table, q, new String[]{"UpdatedOn", "UpdatedBy"}); // "ID_Proc",
        //
        HelpA.setColumnWidthByName("TESTVAR", table, 0.28);
    }

    private void fillComboBox() {
        //
        String q = SQL_A_.lab_dev__test__proc__build__testcode_combo();
        HelpA.fillComboBox(sql, getComboBox(), q, null, false, false);
        //
    }

    @Override
    public void fillNotes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        RowDataInvert code = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "CODE", T_INV.LANG("CODE"), "", true, true, false);
        code.setUneditable();
        //
        RowDataInvert testvar = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "TESTVAR", T_INV.LANG("TESTVAR"), "", true, true, false);
        testvar.setDisabled();
        testvar.enableToolTipTextJTextField();
        RowDataInvert tname = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "TName", T_INV.LANG("NAME"), "", true, true, true);
        tname.setInputLenthValidation(20);//[$TEST-VAR-SAVE$]
        RowDataInvert tmin = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "TMin", T_INV.LANG("MIN"), "", true, true, true);
        tmin.setInputLenthValidation(5);//[$TEST-VAR-SAVE$]
        RowDataInvert tmax = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "TMax", T_INV.LANG("MAX"), "", true, true, true);
        tmax.setInputLenthValidation(5);//[$TEST-VAR-SAVE$]
        RowDataInvert tunit = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "TUnit", T_INV.LANG("UNIT"), "", true, true, true);
        tunit.setInputLenthValidation(9);//[$TEST-VAR-SAVE$]
        RowDataInvert tdigit = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "TDigit", T_INV.LANG("DIGIT"), "", true, true, true);
        tdigit.setInputLenthValidation(5);//[$TEST-VAR-SAVE$]
        //
        RowDataInvert descr = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "DESCRIPT", T_INV.LANG("DESCRIPTION"), "", true, true, false);
        RowDataInvert num = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "NUM", T_INV.LANG("NUM"), "", true, true, false);
        RowDataInvert norm = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "NORM", T_INV.LANG("NORM"), "", true, true, false);
        RowDataInvert status = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "STATUS", T_INV.LANG("STATUS"), "", true, true, false);
        RowDataInvert class_ = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "CLASS", T_INV.LANG("CLASS"), "", true, true, false);
        RowDataInvert group = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "GROUP", T_INV.LANG("GROUP"), "", true, true, false);
        RowDataInvert report = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "REPORT", T_INV.LANG("REPORT"), "", true, true, false);
        RowDataInvert version = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "VERSION", T_INV.LANG("VERSION"), "", true, true, false);
        RowDataInvert note = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "NOTE", T_INV.LANG("NOTE"), "", true, true, false);
        //
        RowDataInvert updatedOn = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "UpdatedOn", T_INV.LANG("UPDATED ON"), "", true, false, false);
        RowDataInvert updatedBy = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "UpdatedBy", T_INV.LANG("UPDATED BY"), "", true, false, false);
        //
        RowDataInvert[] rows = {code, testvar, tname, tmin, tmax, tunit, tdigit, descr, num, norm, status, class_, group,
            report, version, note, updatedOn, updatedBy
        };
        //
        return rows;
    }

    @Override
    public void showTableInvert() {
        //
        TABLE_BUILDER_INVERT = new TableBuilderInvert(OUT, sql, getConfigTableInvert(), false, "lab_dev__test_proc");
        //
        TABLE_INVERT = null;
        //
        String id = getCurrentId();
        //
        try {
            String q = "SELECT * FROM MCCPTProc where ID_Proc=" + id;
            OUT.showMessage(q);
            TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q, this);

        } catch (SQLException ex) {
            Logger.getLogger(TestParameters_.class
                    .getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT.showMessage(ex.toString());
        }
        //
        showTableInvert(mcRecipe.jPanel75);
        //
    }

    @Override
    public void initializeSaveIndicators() {
        SaveIndicator saveIndicator1 = new SaveIndicator(getSaveBtn(), this, 1);
    }

    @Override
    public boolean getUnsaved(int nr) {
        if (nr == 1) {
            //
            if (TABLE_INVERT == null) {
                return false;
            } else if (unsavedEntriesExist(TABLE_INVERT)) { //TABLE_INVERT.unsaved_entries_map.isEmpty() == false
                return true;
            }
            //
        }
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
        saveTestVar();
        //
        JTable table = getTable();
        fillJTable();
        HelpA.markFirstRowJtable(table);
        mouseClickedOnTable(table);
    }

    private void saveTestVar() {
        //[$TEST-VAR-SAVE$]
        String part1 = getValueTableInvert("TName");
        String part2 = getValueTableInvert("TMin");
        String part3 = getValueTableInvert("TMax");
        String part4 = getValueTableInvert("TUnit");
        String part5 = getValueTableInvert("TDigit");
        //
        TestVarEntry tve = new TestVarEntry(part1, part2, part3, part4, part5);
        //
        String q = "UPDATE " + TABLE__TEST_PROCEDURE
                + " SET TESTVAR='"
                + tve.buildString()
                + "' WHERE ID_Proc=" + getCurrentId();
        //
        try {
            sql.execute(q, OUT);
        } catch (SQLException ex) {
            Logger.getLogger(LabDevTestProcedureTab.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        //
        JTable table = getTable();
        //
        if (e.getStateChange() != 1) {
            return;
        }
        //
        HelpA.ComboBoxObject cbo = (HelpA.ComboBoxObject) e.getItem();
        String value = cbo.getParamAuto();
        //  
        if (e.getSource().equals(getComboBox())) {
            //
            refresh_b(value, table);
            //
//            this.TEST_CODE = value;
//            //
//            fillJTable();
//            HelpA.markFirstRowJtable(table);
//            mouseClickedOnTable(table);
            //
        }
        //
    }

//    @Override
//    public void mouseClickedForward(MouseEvent me, int column, int row, String tableName, TableInvert ti) {
//        super.mouseClickedForward(me, column, row, tableName, ti); //To change body of generated methods, choose Tools | Templates.
//        // 
//        JLinkInvert jli = (JLinkInvert) me.getSource();
//        //
//        String col_name = ti.getCurrentColumnName(me.getSource());
//        //[$TEST-VAR-SAVE$]
//        if (col_name.equals("TName") || col_name.equals("TMin") || col_name.equals("TMax")
//                || col_name.equals("TUnit") || col_name.equals("TDigit")) {
//            //
//            trimValueTableInvert(col_name, ti); // Trims String automatically on click
//            //
//        }
//        //
//    }
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
        //
        ID_PROC = HelpA.getValueSelectedRow(table, "ID_Proc");
        //
        refresh();
        //
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public String[] getComboParams__mcs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getQuery__mcs(String procedure, String colName, String[] comboParameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
