/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import static LabDev.LabDevelopment_.TABLE__MCCPWOTEST;
import static LabDev.LabDevelopment_.TABLE__TEST_PROCEDURE;
import MCRecipe.Lang.T_INV;
import MCRecipe.SQL_A;
import MCRecipe.TestParameters_;
import MyObjectTable.SaveIndicator;
import MyObjectTable.ShowMessage;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert;
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
 * @author KOCMOC
 */
public class LabDevNew extends LabDevTab_ implements ActionListener, ItemListener, MouseListener {

    private TableBuilderInvert TABLE_BUILDER_INVERT;
    private String TEST_CODE;

    public LabDevNew(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment_ labDev) {
        super(sql, sql_additional, OUT, labDev);
        init();
    }

    private void init() {
        //
        initializeSaveIndicators();
        getSaveButton().addActionListener(this);
        getComboBox().addItemListener(this);
        //
        fillComboBox();
        fillJTable();
        //
    }

    public void refresh() {

    }

    private JButton getSaveButton() {
        return mcRecipe.jButton__lab_dev__new;
    }

    private JTable getTable() {
        return null;
    }

    private JComboBox getComboBox() {
        return null;
    }

    private void fillJTable() {
        //
        JTable table = getTable();
        //
        String q = "SELECT * FROM " + TABLE__MCCPWOTEST + " WHERE ORDERNO='" + labDev.getOrderNo() + "' AND TESTCODE='" + TEST_CODE + "'" + " ORDER BY Test_Condition_NUM ASC";
        //
        HelpA_.build_table_common(sql, OUT, table, q, new String[]{"ID_Wotest"});
        //
//        HelpA_.setColumnWidthByName("TESTVAR", table, 0.28);
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
        RowDataInvert prefvulc = new RowDataInvert(TABLE__MCCPWOTEST, "ID_Wotest", false, "PREFVULC", T_INV.LANG("PREFVULC"), "", true, true, false);
        RowDataInvert prefage = new RowDataInvert(TABLE__MCCPWOTEST, "ID_Wotest", false, "PREFAGE", T_INV.LANG("PREFAGE"), "", true, true, false);
        RowDataInvert testcode = new RowDataInvert(TABLE__MCCPWOTEST, "ID_Wotest", false, "TESTCODE", T_INV.LANG("TESTCODE"), "", true, true, false);
        RowDataInvert testcond = new RowDataInvert(TABLE__MCCPWOTEST, "ID_Wotest", false, "TESTCOND", T_INV.LANG("TESTCOND"), "", true, true, false);
        RowDataInvert testrem1 = new RowDataInvert(TABLE__MCCPWOTEST, "ID_Wotest", false, "TESTREM1", T_INV.LANG("TESTREM1"), "", true, true, false);
        RowDataInvert testrem2 = new RowDataInvert(TABLE__MCCPWOTEST, "ID_Wotest", false, "TESTREM2", T_INV.LANG("TESTREM2"), "", true, true, false);
        RowDataInvert test_cond_num = new RowDataInvert(TABLE__MCCPWOTEST, "ID_Wotest", false, "Test_Condition_NUM", T_INV.LANG("TEST CONDITION NUM"), "", true, true, false);
        RowDataInvert tagId = new RowDataInvert(TABLE__MCCPWOTEST, "ID_Wotest", false, "TagId", T_INV.LANG("TAG ID"), "", true, true, false);
        //
        RowDataInvert[] rows = {scope, code, prefvulc, prefage, testcode, testcond, testrem1, testrem2, test_cond_num, tagId};
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
            String q = "SELECT * FROM " + TABLE__MCCPWOTEST + " WHERE ID_Wotest='" + id + "'";
            OUT.showMessage(q);
            TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q, this);

        } catch (SQLException ex) {
            Logger.getLogger(TestParameters_.class
                    .getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT.showMessage(ex.toString());
        }
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

    @Override
    public void actionPerformed(ActionEvent e) {
        //
        if (e.getSource().equals(getSaveButton())) {

        }
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
        if (e.getSource().equals(getComboBox())) {
            //
            this.TEST_CODE = value;
            //
        }
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

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
