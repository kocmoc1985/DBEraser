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
import MCRecipe.SQL_A;
import MCRecipe.TestParameters_;
import MyObjectTable.SaveIndicator;
import MyObjectTable.ShowMessage;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import forall.HelpA_;
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
        getComboBox().addItemListener(this);
        table.addMouseListener(this);
        //
        fillComboBox();
        //
//        HelpA_.markFirstRowJtable(table);
//        mouseClickedOnTable(table);
        //
    }

    public void refresh() {
        java.awt.EventQueue.invokeLater(() -> {
            showTableInvert();
            labDev.refreshHeader();
        });
    }

    public String getCurrentId() {
        return ID_PROC;
    }

    private JButton getSaveBtn() {
        return mcRecipe.jButton__lab__dev__test_proc;
    }

    private JComboBox getComboBox() {
        return mcRecipe.jComboBox_lab_dev_test_proc;
    }

    private JTable getTable() {
        return mcRecipe.jTable_lab_dev__test_proc;
    }

    private void fillJTable() {
        //
        JTable table = getTable();
        //
        String q = "SELECT ID_Proc,CODE,TESTVAR,DESCRIPT,NORM FROM " + TABLE__TEST_PROCEDURE + " WHERE CODE=" + SQL_A.quotes(TEST_CODE, false) + " ORDER BY NUM ASC";
        //
        HelpA_.build_table_common(sql, OUT, table, q, new String[]{"ID_Proc"});
        //
        HelpA_.setColumnWidthByName("TESTVAR", table, 0.28);
    }

    private void fillComboBox() {
        //
        String q = SQL_A.lab_dev__test__proc__build__testcode_combo();
        HelpA_.fillComboBox(sql, getComboBox(), q, null, false, false);
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
        RowDataInvert tname = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "TName", T_INV.LANG("PART") + " 1", "", true, true, true);
        tname.setInputLenthValidation(20);//[$TEST-VAR-SAVE$]
        RowDataInvert tmin = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "TMin", T_INV.LANG("PART") + " 2", "", true, true, true);
        tmin.setInputLenthValidation(5);//[$TEST-VAR-SAVE$]
        RowDataInvert tmax = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "TMax", T_INV.LANG("PART") + " 3", "", true, true, true);
        tmax.setInputLenthValidation(5);//[$TEST-VAR-SAVE$]
        RowDataInvert tunit = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "TUnit", T_INV.LANG("PART") + " 4", "", true, true, true);
        tunit.setInputLenthValidation(9);//[$TEST-VAR-SAVE$]
        RowDataInvert tdigit = new RowDataInvert(TABLE__TEST_PROCEDURE, "ID_Proc", false, "TDigit", T_INV.LANG("PART") + " 5", "", true, true, true);
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
        //
        RowDataInvert[] rows = {code, testvar, tname, tmin, tmax, tunit, tdigit, descr, num, norm, status, class_, group,
            report, version, note
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

    @Override
    public void actionPerformed(ActionEvent e) {
        //
        if (e.getSource().equals(getSaveBtn())) {
            saveTableInvert();
        }
        //
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
        saveTestVar();
        //
        JTable table = getTable();
        fillJTable();
        HelpA_.markFirstRowJtable(table);
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
        HelpA_.ComboBoxObject cbo = (HelpA_.ComboBoxObject) e.getItem();
        String value = cbo.getParamAuto();
        //  
        if (e.getSource().equals(getComboBox())) {
            //
            this.TEST_CODE = value;
            //
            fillJTable();
            HelpA_.markFirstRowJtable(table);
            mouseClickedOnTable(table);
            //
        }
        //
    }

    @Override
    public void mouseClickedForward(MouseEvent me, int column, int row, String tableName, TableInvert ti) {
        super.mouseClickedForward(me, column, row, tableName, ti); //To change body of generated methods, choose Tools | Templates.
        // 
        JLinkInvert jli = (JLinkInvert) me.getSource();
        //
        String col_name = ti.getCurrentColumnName(me.getSource());
        //[$TEST-VAR-SAVE$]
        if (col_name.equals("TName") || col_name.equals("TMin") || col_name.equals("TMax")
                || col_name.equals("TUnit") || col_name.equals("TDigit")) {
            //
            trimValueTableInvert(col_name, TABLE_INVERT); // Trims String automatically on click
            //
        }
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
        //
        ID_PROC = HelpA_.getValueSelectedRow(table, "ID_Proc");
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

}
