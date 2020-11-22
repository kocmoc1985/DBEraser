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
import javax.swing.JButton;

/**
 *
 * @author MCREMOTE
 */
public class LabDevNew extends LabDevTab implements ActionListener {

    public LabDevNew(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment_ labDev) {
        super(sql, sql_additional, OUT, labDev);
        init();
    }

    private void init() {
        //
        initializeSaveIndicators();
        //
        getSaveBtnTableInvert1().addActionListener(this);
        //
        refresh();
    }

    public void refresh() {

    }

    private JButton getSaveBtnTableInvert1() {
        return mcRecipe.jButton__lab__dev__new;
    }

    @Override
    public void fillNotes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showTableInvert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        System.out.println("");
        //
        if (e.getSource().equals(getSaveBtnTableInvert1())) {
            saveTableInvert_1();
        }
        //
    }

    private void saveTableInvert_1() {
        //
        if (containsInvalidatedFields(TABLE_INVERT, 1, getConfigTableInvert())) {
            HelpA_.showNotification(MSG.MSG_3());
            return;
        }
        //
        saveChangesTableInvert(TABLE_INVERT);
        //
    }

}
