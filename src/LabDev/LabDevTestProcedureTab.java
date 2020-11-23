/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import MCRecipe.Lang.MSG;
import MyObjectTable.SaveIndicator;
import MyObjectTable.ShowMessage;
import MyObjectTableInvert.RowDataInvert;
import forall.HelpA_;
import forall.SqlBasicLocal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JComboBox;

/**
 *
 * @author MCREMOTE
 */
public class LabDevTestProcedureTab extends LabDevTab_ implements ActionListener, ItemListener {

    private String CODE;
    
    public LabDevTestProcedureTab(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment_ labDev) {
        super(sql, sql_additional, OUT, labDev);
        init();
    }

    private void init() {
        //
        initializeSaveIndicators();
        //
        getSaveBtnTableInvert1().addActionListener(this);
        getComboBox().addItemListener(this);
        //
        fillComboBox();
        //
        refresh();
    }

    public void refresh() {

    }

    private JButton getSaveBtnTableInvert1() {
        return mcRecipe.jButton__lab__dev__new;
    }
    
    private JComboBox getComboBox(){
        return mcRecipe.jComboBox_lab_dev_test_proc;
    }
    
    private void fillComboBox() {
        //
        String q = "select DISTINCT CODE from " + LabDevelopment_.TABLE__TEST_PROCEDURE;
        HelpA_.fillComboBox(sql, getComboBox(), q, null, false, false);
        //
    }

    @Override
    public void fillNotes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        
        return null;
    }

    @Override
    public void showTableInvert() {
        
    }

    @Override
    public void initializeSaveIndicators() {
        SaveIndicator saveIndicator1 = new SaveIndicator(getSaveBtnTableInvert1(), this, 1);
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
        if (e.getSource().equals(getSaveBtnTableInvert1())) {
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
            this.CODE = value;
            //
            showTableInvert();
            //
        }
        //
    }

}
