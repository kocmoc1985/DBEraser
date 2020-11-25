/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import static LabDev.LabDevelopment_.TABLE__AGEMENT;
import static LabDev.LabDevelopment_.TABLE__MCCPWOTEST;
import static LabDev.LabDevelopment_.TABLE__VULC;
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
    private String TEST_CODE; // EX: VUG01
    private String CODE__MATERIAL; // EX: WE8486

    public LabDevTestOrder(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment_ labDev) {
        super(sql, sql_additional, OUT, labDev);
        init();
    }

    private void init() {
        //
        initializeSaveIndicators();
        getSaveButton().addActionListener(this);
        getComboBoxTestCode().addItemListener(this);
        getTable().addMouseListener(this);
        //
        fillComboBox();
//        fillJTable();
        //
    }

    public void refresh() {
        showTableInvert();
    }

    private JButton getSaveButton() {
        return mcRecipe.jButton__lab_dev__new;
    }

    public JTable getTable() {
        return mcRecipe.jTable_lab_dev__new;
    }

    private JComboBox getComboBoxTestCode() {
        return mcRecipe.jComboBox_lab_dev__new;
    }
    
    private JComboBox getComboBoxMaterial(){
        return null;
    }

    private void fillJTable() {
        //
        JTable table = getTable();
        //
//        String q = "SELECT * FROM " + TABLE__MCCPWOTEST + " WHERE ORDERNO='" + labDev.getOrderNo() + "' AND TESTCODE='" + TEST_CODE + "'" + " ORDER BY Test_Condition_NUM ASC";
        String q = SQL_A.lab_dev__test_order(PROC.PROC_75, labDev.getOrderNo(), CODE__MATERIAL, TEST_CODE);
        //
        HelpA_.build_table_common(sql, OUT, table, q, new String[]{"ORDERNO","ID_Wotest","UpdatedOn",
            "UpdatedBy","TESTREM1","TESTREM2","SCOPE"});
        //
//        HelpA_.setColumnWidthByName("TESTVAR", table, 0.28);
        //
    }

    private void fillComboBox() {
        //
        String q = SQL_A.lab_dev__test__proc__build__testcode_combo();
        HelpA_.fillComboBox(sql, getComboBoxTestCode(), q, null, false, false);
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
            testcond, testrem1, testrem2, updatedOn, updatedBy};
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
        fillJTable();
        HelpA_.markFirstRowJtable(table);
        mouseClickedOnTable(table);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //
        if (e.getSource().equals(getSaveButton())) {
            saveTableInvert();
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
        if (e.getSource().equals(getComboBoxTestCode())) {
            //
            this.TEST_CODE = value;
            //
            itemStateChangedAction(table);
            //
        }else if(e.getSource().equals(getComboBoxMaterial())){
            //
            this.CODE__MATERIAL = value;
            //
            itemStateChangedAction(table);
            //
        }
    }
    
    private void itemStateChangedAction(JTable table){
            fillJTable();
            HelpA_.markFirstRowJtable(table);
            mouseClickedOnTable(table); 
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
        System.out.println("MOUSE CLICKED ON TABLE --- LAB DEV NEW");
        showTableInvert();
        labDev.refreshHeader();
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
