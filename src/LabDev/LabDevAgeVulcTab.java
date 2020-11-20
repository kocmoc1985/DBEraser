/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import static LabDev.LabDevelopment_.TABLE__AGEMENT;
import static LabDev.LabDevelopment_.TABLE__VULC;
import MCRecipe.Lang.MSG;
import MCRecipe.Lang.REGEX;
import MCRecipe.Lang.T_INV;
import MCRecipe.SQL_A;
import MCRecipe.TestParameters_;
import MyObjectTable.SaveIndicator;
import MyObjectTable.ShowMessage;
import MyObjectTable.Table;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert_;
import forall.HelpA_;
import forall.SqlBasicLocal;
import forall.TextFieldCheck;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;

/**
 *
 * @author KOCMOC
 */
public class LabDevAgeVulcTab extends LabDevTab implements ItemListener, ActionListener {

    private Table TABLE_INVERT_2;
    private TableBuilderInvert_ TABLE_BUILDER_INVERT;
    private TableBuilderInvert_ TABLE_BUILDER_INVERT_2;
    private String AGE_CODE;
    private String VULC_CODE;

    public LabDevAgeVulcTab(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment_ labDev) {
        super(sql, sql_additional, OUT, labDev);
        init();
    }

    private void init() {
        //
        initializeSaveIndicators();
        //
        getSaveButtonAge().addActionListener(this);
        getSaveButtonVulc().addActionListener(this);
        getCreateNewButtonAge().addActionListener(this);
        getDeleteButtonAge().addActionListener(this);
        //
        getAgeComboBox().addItemListener(this);
        getVulcComboBox().addItemListener(this);
        //
        fillComboBoxes(); // OBS! refresh is called on "ItemStateChanged" evt
        //
    }
    
    private void refresh_b(){
        java.awt.EventQueue.invokeLater(() -> {
            fillComboBoxes(); // OBS! refresh is called on "ItemStateChanged" evt
        });
    }

    public void refresh() {
        //
        java.awt.EventQueue.invokeLater(() -> {
            showTableInvert();
            showTableInvert_2();
        });
        //
    }

    private String getAgeCode() {
        return AGE_CODE;
    }
    
    private String getAgeCodeTableInvert(){
        return getValueTableInvert("AGEINGCODE");
    }

    private String getVulcCode() {
        return VULC_CODE;
    }

    private JButton getSaveButtonAge() {
        return mcRecipe.jButton_lab_dev__save_aging;
    }

    private JButton getSaveButtonVulc() {
        return mcRecipe.jButton_lab_dev__save_vulc;
    }

    private JButton getCreateNewButtonAge() {
        return mcRecipe.jButton_lab_dev_aging__create_new;
    }
    
    private JButton getDeleteButtonAge(){
        return mcRecipe.jButton_lab_dev_aging__delete;
    }

    private JComboBox getAgeComboBox() {
        return mcRecipe.jComboBox_lab_dev__age;
    }

    private JComboBox getVulcComboBox() {
        return mcRecipe.jComboBox_lab_dev_vulc;
    }

    private void fillComboBoxes() {
        //
        String q = "select DISTINCT AGEINGCODE from " + TABLE__AGEMENT;
        HelpA_.fillComboBox(sql, getAgeComboBox(), q, null, false, false);
        //
        String q_2 = "select DISTINCT VULCCODE from " + TABLE__VULC;
        HelpA_.fillComboBox(sql, getVulcComboBox(), q_2, null, false, false);
        //
        this.AGE_CODE = HelpA_.getSelectedComboBoxObject(getAgeComboBox()).getParamAuto();
        this.VULC_CODE = HelpA_.getSelectedComboBoxObject(getAgeComboBox()).getParamAuto();
        //
    }

    @Override
    public void fillNotes() {
    }

    @Override
    public void showTableInvert() {
        //
        TABLE_BUILDER_INVERT = new TableBuilderInvert_(OUT, sql, getConfigTableInvert(), false, "lab_dev_aging");
        //
        TABLE_INVERT = null;
        //
        String agecode = getAgeCode();
        //
        try {
            String q = "SELECT * FROM " + TABLE__AGEMENT + " WHERE AGEINGCODE=" + SQL_A.quotes(agecode, false);
            OUT.showMessage(q);
            TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q, this);
        } catch (SQLException ex) {
            Logger.getLogger(TestParameters_.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT.showMessage(ex.toString());
        }
        //
//        setMargin(TABLE_INVERT, 10, 0, 0, 0);
        //
        showTableInvert(mcRecipe.jPanel68);
        //
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        RowDataInvert ageingcode = new RowDataInvert(TABLE__AGEMENT, "ID", false, "AGEINGCODE", T_INV.LANG("AGEING CODE"), "", true, true, false);
        ageingcode.setUneditable();
        RowDataInvert type = new RowDataInvert(TABLE__AGEMENT, "ID", false, "TYPE", T_INV.LANG("AGEING CODE"), "", true, true, false);
        RowDataInvert descr = new RowDataInvert(TABLE__AGEMENT, "ID", false, "DESCR", T_INV.LANG("DESCRIPTION"), "", true, true, false);
        descr.enableToolTipTextJTextField();
        RowDataInvert method = new RowDataInvert(TABLE__AGEMENT, "ID", false, "METHOD", T_INV.LANG("METHOD"), "", true, true, false);
        RowDataInvert temp = new RowDataInvert(TABLE__AGEMENT, "ID", false, "TEMP", T_INV.LANG("TEMP"), "", true, true, false);
        RowDataInvert time = new RowDataInvert(TABLE__AGEMENT, "ID", false, "TIME", T_INV.LANG("TIME"), "", true, true, false);
        RowDataInvert timeunit = new RowDataInvert(TABLE__AGEMENT, "ID", false, "TIMEUNIT", T_INV.LANG("TIME UNIT"), "", true, true, false);
        RowDataInvert status = new RowDataInvert(TABLE__AGEMENT, "ID", false, "STATUS", T_INV.LANG("STATUS"), "", true, true, false);
        RowDataInvert note = new RowDataInvert(TABLE__AGEMENT, "ID", false, "NOTE", T_INV.LANG("NOTE"), "", true, true, false);
        //
        RowDataInvert updatedOn = new RowDataInvert(TABLE__AGEMENT, "ID", false, "UpdatedOn", T_INV.LANG("UPDATED ON"), "", true, true, false);
        RowDataInvert updatedBy = new RowDataInvert(TABLE__AGEMENT, "ID", false, "UpdatedBy", T_INV.LANG("UPDATED BY"), "", true, true, false);
        //
        RowDataInvert[] rows = {ageingcode, type, descr, method, temp, time, timeunit, status, note, updatedOn, updatedBy};
        //
        return rows;
    }

    public void showTableInvert_2() {
        //
        TABLE_BUILDER_INVERT_2 = new TableBuilderInvert_(OUT, sql, getConfigTableInvert_2(), false, "lab_dev_vulc");
        //
        TABLE_INVERT_2 = null;
        //
        String vulccode = getVulcCode();
        //
        try {
            String q = "SELECT * FROM " + TABLE__VULC + " WHERE VULCCODE=" + SQL_A.quotes(vulccode, false);
            OUT.showMessage(q);
            TABLE_INVERT_2 = TABLE_BUILDER_INVERT_2.buildTable(q, this); // TableRow.FLOW_LAYOUT
        } catch (SQLException ex) {
            Logger.getLogger(TestParameters_.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT_2.showMessage(ex.toString());
        }
        //
//        setVerticalScrollBarDisabled(TABLE_INVERT_2);
        //
        showTableInvert(mcRecipe.jPanel69, TABLE_INVERT_2);
    }

    public RowDataInvert[] getConfigTableInvert_2() {
        //
        RowDataInvert vulccode = new RowDataInvert(TABLE__VULC, "ID", false, "VULCCODE", T_INV.LANG("VULCANISATION CODE"), "", true, true, false);
        vulccode.setUneditable();
        RowDataInvert type = new RowDataInvert(TABLE__VULC, "ID", false, "TYPE", T_INV.LANG("TYPE"), "", true, true, false);
        RowDataInvert descr = new RowDataInvert(TABLE__VULC, "ID", false, "DESCR", T_INV.LANG("DESCRIPTION"), "", true, true, false);
        descr.enableToolTipTextJTextField();
        RowDataInvert method = new RowDataInvert(TABLE__VULC, "ID", false, "METHOD", T_INV.LANG("METHOD"), "", true, true, false);
        RowDataInvert temp = new RowDataInvert(TABLE__VULC, "ID", false, "TEMP", T_INV.LANG("TEMP"), "", true, true, false);
        RowDataInvert time = new RowDataInvert(TABLE__VULC, "ID", false, "TIME", T_INV.LANG("TIME"), "", true, true, false);
        RowDataInvert article = new RowDataInvert(TABLE__VULC, "ID", false, "ARTICLE", T_INV.LANG("ARTICLE"), "", true, true, false);
        RowDataInvert status = new RowDataInvert(TABLE__VULC, "ID", false, "STATUS", T_INV.LANG("STATUS"), "", true, true, false);
        RowDataInvert note = new RowDataInvert(TABLE__VULC, "ID", false, "NOTE", T_INV.LANG("NOTE"), "", true, true, false);
        //
        RowDataInvert updatedOn = new RowDataInvert(TABLE__VULC, "ID", false, "UpdatedOn", T_INV.LANG("UPDATED ON"), "", true, true, false);
        RowDataInvert updatedBy = new RowDataInvert(TABLE__VULC, "ID", false, "UpdatedBy", T_INV.LANG("UPDATED BY"), "", true, true, false);
        //
        RowDataInvert[] rows = {vulccode, type, descr, method, temp, time, article, status, note, updatedOn, updatedBy};
        //
        return rows;
    }

    @Override
    public void initializeSaveIndicators() {
        SaveIndicator saveIndicator1 = new SaveIndicator(getSaveButtonAge(), this, 1);
        SaveIndicator saveIndicator2 = new SaveIndicator(getSaveButtonVulc(), this, 2);
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
        } else if (nr == 2) {
            //
            if (TABLE_INVERT_2 == null) {
                return false;
            } else if (unsavedEntriesExist(TABLE_INVERT_2)) { //TABLE_INVERT.unsaved_entries_map.isEmpty() == false
                return true;
            }
            //
        }
        //
        return false;
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
        if (e.getSource().equals(getAgeComboBox())) {
            //
            this.AGE_CODE = value;
            //
            showTableInvert();
            //
        } else if (e.getSource().equals(getVulcComboBox())) {
            //
            this.VULC_CODE = value;
            //
            showTableInvert_2();
            //
        }
        //
    }

    private void saveTableInvert_aging() {
        //
        if (containsInvalidatedFields(TABLE_INVERT, 1, getConfigTableInvert())) {
            HelpA_.showNotification(MSG.MSG_3());
            return;
        }
        //
        saveChangesTableInvert();
        //
    }

    private void saveTableInvert_2_vulc() {
        //
        if (containsInvalidatedFields(TABLE_INVERT_2, 1, getConfigTableInvert_2())) {
            HelpA_.showNotification(MSG.MSG_3());
            return;
        }
        //
        saveChangesTableInvert(TABLE_INVERT_2);
        //
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //
        if (e.getSource().equals(getSaveButtonAge())) {
            saveTableInvert_aging();
        } else if (e.getSource().equals(getSaveButtonVulc())) {
            saveTableInvert_2_vulc();
        } else if (e.getSource().equals(getCreateNewButtonAge())) {
            createNewEntryAging();
        }else if (e.getSource().equals(getDeleteButtonAge())) {
            deleteAgeingEntry();
        }
        //
    }
    
    private void deleteAgeingEntry(){
        //
        if (HelpA_.confirm() == false) {
            return;
        }
        //
        String ageincode = getAgeCodeTableInvert();
        //
        String q = "DELETE FROM "+ TABLE__AGEMENT + " WHERE AGEINGCODE=" + SQL_A.quotes(ageincode, false);
        //
        try {
            sql.execute(q, OUT);
            refresh_b();
        } catch (SQLException ex) {
            Logger.getLogger(LabDevAgeVulcTab.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
    }

    private boolean createNewEntryAging() {
        //
        String q = "SELECT AGEINGCODE from " + TABLE__AGEMENT + " WHERE AGEINGCODE = ?";
        //
        TextFieldCheck tfc = new TextFieldCheck(sql, q, REGEX.AGING_CODE__VULC_CODE_REGEX, 15);
        //
        boolean yesNo = HelpA_.chooseFromJTextFieldWithCheck(tfc, "Create new ageing code");
        String agecode = tfc.getText();
        //
        if (agecode == null || yesNo == false) {
            return false;
        }
        //
        this.AGE_CODE = agecode;
        //
        String q_insert = "INSERT INTO " + TABLE__AGEMENT + " VALUES("
                + SQL_A.quotes(this.AGE_CODE, false) + ","
                + "NULL,"
                + "'- Keine -',"
                + "NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL)";
        //
        try {
            sql.execute(q_insert, OUT);
            showTableInvert();
        } catch (SQLException ex) {
            Logger.getLogger(LabDevAgeVulcTab.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        return false;
    }

}
