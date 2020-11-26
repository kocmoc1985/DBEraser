/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import MCRecipe.Sec.PROC;
import MyObjectTableInvert.BasicTab;
import MyObjectTableInvert.RowDataInvert;
import forall.HelpA;
import forall.SqlBasicLocal;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public class Sequence extends BasicTab {

    private final MC_RECIPE mCRecipe;
    private String RECIPE;
    private String RELEASE;
    private String MIXER_CODE;
    private boolean SEQUENCE_PRESENT = true;

    public Sequence(SqlBasicLocal sql, SqlBasicLocal sql_additional, MC_RECIPE mCRecipe) {
        super(sql, sql_additional, mCRecipe);
        this.mCRecipe = mCRecipe;
        //
        go();
    }

    public boolean checkHeadParameters() {
        String recipe = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxSequenceRecipe);
        String release = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxSequenceRelease);
        String mixerCode = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxSequenceMixerCode);
        //
        if (recipe.equals("NULL") || release.equals("NULL") || mixerCode.equals("NULL")) {
            return false;
        } else {
            return true;
        }
    }

    public void setSelectedItems() {
        //
        RecipeDetailed_ rd = mCRecipe.recipeDetailed;
        //
        String recipeCode = rd.getRecipeCode();
        String release = rd.getRelease();
        String mixerCode = rd.getMixerCode();
        //
        if (recipeCode != null && release != null && mixerCode != null) {
            //
            mCRecipe.jComboBoxSequenceRecipe.setEditable(true);
            mCRecipe.jComboBoxSequenceRelease.setEditable(true);
            mCRecipe.jComboBoxSequenceMixerCode.setEditable(true);
            //
            mCRecipe.jComboBoxSequenceRecipe.setSelectedItem(recipeCode);
            mCRecipe.jComboBoxSequenceRelease.setSelectedItem(release);
            mCRecipe.jComboBoxSequenceMixerCode.setSelectedItem(mixerCode);
        }
        //
    }

    private void go() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                fillComboBoxes();
                mCRecipe.addJComboListenersSequence();
            }
        });
    }

    private void markPhaseTable2() {
        //
        String command = HelpA.getValueSelectedRow(mCRecipe.jTableSequnece1, "Command Name");
        //
        if (command == null) {
            return;
        }
        //
        if (command.equals("ADD PHASE")) {
            int phase = (int) Double.parseDouble(HelpA.getValueSelectedRow(mCRecipe.jTableSequnece1, "Cmd Parameter"));
            LinkedList<Integer> list = getRowsWithGivenPhase(phase, mCRecipe.jTableSequnce2);
            paint_selected_rows_a(list, mCRecipe.jTableSequnce2, Color.LIGHT_GRAY);
        } else {
            unpaintAllRows_a(mCRecipe.jTableSequnce2);
        }
    }

    private LinkedList<Integer> getRowsWithGivenPhase(int phase, JTable table) {
        LinkedList<Integer> list = new LinkedList<Integer>();
        for (int i = 0; i < table.getRowCount(); i++) {
            int phase_ = Integer.parseInt(HelpA.getValueGivenRow(table, i, "Phase"));
            if (phase == phase_) {
                list.add(i);
            }
        }
        return list;
    }

    public void deleteAllSequenceSteps() {
        String q = SQL_A.deleteOtherSequence(PROC.PROC_34, RECIPE, RELEASE);
        //
        try {
            sql.execute(q, mCRecipe);
        } catch (SQLException ex) {
            Logger.getLogger(Sequence.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        fill_table_1(mCRecipe.jComboBoxSequenceRecipe, mCRecipe.jComboBoxSequenceRelease, mCRecipe.jComboBoxSequenceMixerCode);
    }

    public void updateOther() {
        String sequence_id = getRecipeSequenceMainId(RECIPE, RELEASE, MIXER_CODE);
        String mixer_code = MIXER_CODE;
        String info = mCRecipe.jTextFieldInfoSequence.getText();
        String updated_on = HelpA.updatedOn();
        String updated_by = HelpA.updatedBy();
        //
        String q = SQL_A.updateOtherSequence(PROC.PROC_35, sequence_id, mixer_code, info, updated_on, updated_by);
        //
        try {
            sql.execute(q, mCRecipe);
        } catch (SQLException ex) {
            Logger.getLogger(Sequence.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        fill_table_1(mCRecipe.jComboBoxSequenceRecipe, mCRecipe.jComboBoxSequenceRelease, mCRecipe.jComboBoxSequenceMixerCode);
        //
        HelpA.markFirstRowJtable(mCRecipe.jTableSequnece1);
        //
        mCRecipe.clickedTable1Sequence();
    }

    public void insertOther() {
        String q = SQL_A.insertOtherSequence(PROC.PROC_36, RECIPE, RELEASE, MIXER_CODE);
        //
        try {
            sql.execute(q, mCRecipe);
        } catch (SQLException ex) {
            Logger.getLogger(Sequence.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        fill_table_1(mCRecipe.jComboBoxSequenceRecipe, mCRecipe.jComboBoxSequenceRelease, mCRecipe.jComboBoxSequenceMixerCode);
    }

    public void delete_step() {
        //
        JTable table1 = mCRecipe.jTableSequnece1;
        //
        if (HelpA.confirm() == false) {
            return;
        }
        //
        String step_id = HelpA.getValueSelectedRow(mCRecipe.jTableSequnece1, "Recipe_Sequence_Steps_ID");
        //
        String q = SQL_A.deleteStepSequence(PROC.PROC_37, step_id);
        //
        try {
            sql.execute(q, mCRecipe);
        } catch (SQLException ex) {
            Logger.getLogger(Sequence.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        recalcStepNumbers();
        //
        int row_to_delete = table1.getSelectedRow();
        //
        fill_table_1(mCRecipe.jComboBoxSequenceRecipe, mCRecipe.jComboBoxSequenceRelease, mCRecipe.jComboBoxSequenceMixerCode);
        //
        //
        HelpA.markGivenRow(table1, HelpA.getNextRow(table1, row_to_delete));
        //
        table1Click(table1);
    }

    private void recalcStepNumbers() {
        //
        String seqMainId = getRecipeSequenceMainId(RECIPE, RELEASE, MIXER_CODE);
        //
        String q = SQL_A.sequenceGetStepsWithSeqId(seqMainId);
        //
        try {
            ResultSet rs = sql.execute(q, mCRecipe);
            //
            int stepNumber = 1;
            //
            while (rs.next()) {
                String recipeSeqStepId = rs.getString("Recipe_Sequence_Steps_ID");
                recalcStepNumbersB(recipeSeqStepId, stepNumber);
                stepNumber++;
            }
            //
        } catch (SQLException ex) {
            Logger.getLogger(Sequence.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void recalcStepNumbersB(String recipeSeqStepId, int stepNumber) {
        //
        String q = SQL_A.sequenceRecalcStepNumbers(recipeSeqStepId, "" + stepNumber);
        //
        try {
            sql_additional.execute(q, mCRecipe);
        } catch (SQLException ex) {
            Logger.getLogger(Sequence.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
    }

    public void update_step() {
        //
        if (HelpA.confirm() == false) {
            return;
        }
        //
        String step_id = HelpA.getValueSelectedRow(mCRecipe.jTableSequnece1, "Recipe_Sequence_Steps_ID");
        String commandName = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxCommandNameSequence);
        String stepNr = mCRecipe.jTextFieldStepNrSequence.getText();
        String commandParam = mCRecipe.jTextFieldCommandParamSequence.getText();
        //
        stepNr = processStepNumber(stepNr, false);
        //
        if (step_id == null) {
            HelpA.showNotification("No entry is chosen in the table!");
            return;
        }
        //
        String q = SQL_A.updateStepSequence(PROC.PROC_38, step_id, commandName, stepNr, commandParam);
        //
        try {
            sql.execute(q, mCRecipe);
        } catch (SQLException ex) {
            Logger.getLogger(Sequence.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        recalcStepNumbers();
        //
        fill_table_1(mCRecipe.jComboBoxSequenceRecipe, mCRecipe.jComboBoxSequenceRelease, mCRecipe.jComboBoxSequenceMixerCode);
    }

    public void createNewSequence() {
        //
        if (HelpA.confirm("This action will erase all actual sequence steps") == false) {
            return;
        }
        //
        deleteAllSequenceSteps();
        insertOther();
        updateOther();
        insert_new_step(true, true);
    }

    public void insert_new_step(boolean initial, boolean addLast) {
        //
        String sequence_id = getRecipeSequenceMainId(RECIPE, RELEASE, MIXER_CODE);
        //
        String commandName;
        String stepNr;
        String commandParam;
        //
        //
        if (initial) {
            commandName = "SET ROTOR (speed)";
            stepNr = "1";
            commandParam = "15";
        } else {
            commandName = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxCommandNameSequence);
            stepNr = mCRecipe.jTextFieldStepNrSequence.getText();
            commandParam = mCRecipe.jTextFieldCommandParamSequence.getText();
        }
        //
        if (stepNr == null || stepNr.isEmpty() || stepNr.equals("null")) {
            HelpA.showNotification("Step number not chosen!");
            return;
        }
        //
        stepNr = processStepNumber(stepNr, addLast);
        //
        String q = SQL_A.insertStepSequence(PROC.PROC_39, sequence_id, commandName, stepNr, commandParam);
        //
        try {
            sql.execute(q, mCRecipe);
        } catch (SQLException ex) {
            Logger.getLogger(Sequence.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        recalcStepNumbers();
        //
        fill_table_1(mCRecipe.jComboBoxSequenceRecipe, mCRecipe.jComboBoxSequenceRelease, mCRecipe.jComboBoxSequenceMixerCode);
    }

    private String processStepNumber(String stepNumber, boolean addLast) {
        if (addLast) {
            return stepNumber + ".5";
        } else {
            int stepNumber_ = Integer.parseInt(stepNumber);
            stepNumber_ -= 1;
            return "" + stepNumber_ + ".5";
        }

    }

    public void fill_table_1(JComboBox recipeBox, JComboBox releaseBox, JComboBox mixerBox) {
        //
        JTable table1 = mCRecipe.jTableSequnece1;
        //
        try {
            String recipe = HelpA.getComboBoxSelectedValue(recipeBox);
            String release = HelpA.getComboBoxSelectedValue(releaseBox);
            String mixerCode = HelpA.getComboBoxSelectedValue(mixerBox);
            //
            String q = SQL_A.build_table1_sequence(PROC.PROC_40, recipe, release, mixerCode);
            //
            ResultSet rs = sql.execute(q, mCRecipe);
            //
            HelpA.build_table_common(rs, table1, q);
            //
            HelpA.setTrackingToolTip(table1, RECIPE);
            //
        } catch (SQLException ex) {
            Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        HelpA.hideColumnByName(table1, "Recipe Code");
        HelpA.hideColumnByName(table1, "Mixer Code");
        HelpA.hideColumnByName(table1, "Release");
        HelpA.hideColumnByName(table1, "UpdatedOn");
        HelpA.hideColumnByName(table1, "UpdatedBy");
        HelpA.hideColumnByName(table1, "Recipe_Sequence_Main_ID");
        HelpA.hideColumnByName(table1, "Recipe_Sequence_Steps_ID");
        HelpA.hideColumnByName(table1, "Info");
        //
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        HelpA.setColumnWidthByName("Step Nb", table1, 0.1);
        HelpA.setColumnWidthByName("Command Name", table1, 0.5);
        HelpA.setColumnWidthByName("Cmd Parameter", table1, 0.4);
        //
        HelpA.markFirstRowJtable(mCRecipe.jTableSequnece1);
        mCRecipe.clickedTable1Sequence();
        //
    }

    public void fill_table_2(JComboBox recipeBox, JComboBox releaseBox, JComboBox mixerBox, boolean orig) {
        //
        JTable table2 = mCRecipe.jTableSequnce2;
        //
        try {
            String recipe = HelpA.getComboBoxSelectedValue(recipeBox);
            String release = HelpA.getComboBoxSelectedValue(releaseBox);
            String mixerCode = HelpA.getComboBoxSelectedValue(mixerBox);
            //
            if (orig) {
                RECIPE = SQL_A.quotes(recipe, false);
                RELEASE = SQL_A.quotes(release, false);
                MIXER_CODE = SQL_A.quotes(mixerCode, false);
            }
            //
            String q = SQL_A.build_table2_sequence(recipe, release, mixerCode);
            //
            ResultSet rs = sql_additional.execute(q, mCRecipe);
            //
            HelpA.build_table_common(rs, table2, q);
            //
            HelpA.setTrackingToolTip(table2, RECIPE);
            //
        } catch (SQLException ex) {
            Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        HelpA.setColumnWidthByName("Descr", mCRecipe.jTableSequnce2, 0.2);
        //
        HelpA.hideColumnByName(table2, "percRubber");
        HelpA.hideColumnByName(table2, "density");
        HelpA.hideColumnByName(table2, "weighingID");
        HelpA.hideColumnByName(table2, "BalanceID");
        HelpA.hideColumnByName(table2, "SiloId");
        HelpA.hideColumnByName(table2, "MatIndex");
        HelpA.hideColumnByName(table2, "PriceKG");
        HelpA.hideColumnByName(table2, "PriceData");
        HelpA.hideColumnByName(table2, "RecipeID");
        HelpA.hideColumnByName(table2, "Release");
    }

    public void copySequence() {
        //
        String recipeC = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxSequenceRecipeCopy);
        String releaseC = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxSequenceReleaseCopy);
        String mixerCodeC = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxSequenceMixerCodeCopy);
        //
        String msg = "This action will replace sequence for: \n" + RECIPE + " / " + RELEASE + " / " + MIXER_CODE + "\n"
                + "with: \n" + recipeC + " / " + releaseC + " / " + mixerCodeC;
        //
        if (HelpA.confirm(msg) == false) {
            return;
        }
        //
        deleteAllSequenceSteps();
        //
        String date = HelpA.updatedOn();
        String updatedBy = HelpA.updatedBy();
        //
        String recipe2 = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxSequenceRecipeCopy);
        String release2 = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxSequenceReleaseCopy);
        String mixerCode2 = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxSequenceMixerCodeCopy);
        String q = SQL_A.copy_sequence(PROC.PROC_23, RECIPE, RELEASE, MIXER_CODE, recipe2, release2, mixerCode2, date, updatedBy,"");
        //
        try {
            sql.execute(q, mCRecipe);
        } catch (SQLException ex) {
            Logger.getLogger(Sequence.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        //
        String recipeSequenceMainId = getRecipeSequenceMainId(RECIPE, RELEASE, MIXER_CODE);
        //
        String q2 = SQL_A.sequenceInsertFromOther(PROC.PROC_22, recipeSequenceMainId, recipe2, release2, mixerCode2, date, updatedBy);
        //
        try {
            sql_additional.execute(q2, mCRecipe);
        } catch (SQLException ex) {
            Logger.getLogger(Sequence.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        fill_table_1(mCRecipe.jComboBoxSequenceRecipe, mCRecipe.jComboBoxSequenceRelease, mCRecipe.jComboBoxSequenceMixerCode);
        fill_table_2(mCRecipe.jComboBoxSequenceRecipe, mCRecipe.jComboBoxSequenceRelease, mCRecipe.jComboBoxSequenceMixerCode, true);
        //
    }

    private String getRecipeSequenceMainId(String code, String release, String mixerCode) {
        //
        String q = SQL_A.getRecipeSequenceMainId(code, release, mixerCode);
        //
        try {
            ResultSet rs = sql.execute(q, mCRecipe);
            //
            if (rs.next()) {
                return rs.getString("Recipe_Sequence_Main_ID");
            } else {
                return null;
            }
            //
        } catch (SQLException ex) {
            Logger.getLogger(Sequence.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private String[] getComboParams() {
        String code = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxSequenceRecipe);
        String release = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxSequenceRelease);
        String mixerCode = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxSequenceMixerCode);
        return new String[]{code, release, mixerCode};
    }

    private String[] getComboParams2() {
        String code = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxSequenceRecipeCopy);
        String release = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxSequenceReleaseCopy);
        String mixerCode = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxSequenceMixerCodeCopy);
        return new String[]{code, release, mixerCode};
    }

    public void fillComboBoxes() {
        fillComboCommandName();
    }

    private void fillComboCommandName() {
        String q = SQL_A.fillCommandNameComboBoxSequence();
        HelpA.fillComboBox(sql, mCRecipe.jComboBoxCommandNameSequence, q, null, false, false);
    }

    public void fillComboRecipe() {
        fillComboBox(mCRecipe.jComboBoxSequenceRecipe, "Code", PROC.PROC_41, getComboParams());
    }

    public void fillComboRelease() {
        fillComboBox(mCRecipe.jComboBoxSequenceRelease, "Release", PROC.PROC_41, getComboParams());
    }

    public void fillComboMixer() {
        fillComboBoxMultipleValues(mCRecipe.jComboBoxSequenceMixerCode, "Mixer_Code", "Name", PROC.PROC_41, getComboParams());
    }

    public void fillComboRecipeCopy() {
        fillComboBox(mCRecipe.jComboBoxSequenceRecipeCopy, "Code", PROC.PROC_42, getComboParams2());
    }

    public void fillComboMixerCopy() {
        fillComboBoxMultipleValues(mCRecipe.jComboBoxSequenceMixerCodeCopy, "Mixer_Code", "Name", PROC.PROC_42, getComboParams2());
    }

    public void fillComboReleaseCopy() {
        fillComboBox(mCRecipe.jComboBoxSequenceReleaseCopy, "Release", PROC.PROC_42, getComboParams2());
    }

    private void fillComboBox(final JComboBox boxToFill, final String colName, final String procedureName, final String[] params) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Object selection = boxToFill.getSelectedItem();
                //
                //
                String q = SQL_A.fill_combobox_sequence_2(colName, procedureName, params);
                OUT.showMessage(q);
                HelpA.fillComboBox(sql, boxToFill, q, null, false, false);
                //
                boxToFill.setSelectedItem(selection);
            }
        });
    }

    private void fillComboBoxMultipleValues(final JComboBox boxToFill, final String colName, final String colName2, final String procedureName, final String[] params) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Object selection = boxToFill.getSelectedItem();
                //
                //
                String q = SQL_A.fill_combobox_sequence_2_multiple(colName, colName2, procedureName, params);
                OUT.showMessage(q);
                HelpA.fillComboBox(sql, boxToFill, q, null, true, false);
                //
                boxToFill.setSelectedItem(selection);
            }
        });
    }

    public void table1Click(JTable table) {
        table1Clicked(table);
        markPhaseTable2();
    }

    private void table1Clicked(JTable table) {
        //
        SEQUENCE_PRESENT = true;
        //
        if (table.getRowCount() != 0) {
            mCRecipe.jTextFieldStepNrSequence.setText(HelpA.getValueSelectedRow(table, "Step Nb"));
            mCRecipe.jComboBoxCommandNameSequence.setSelectedItem(HelpA.getValueSelectedRow(table, "Command Name"));
            mCRecipe.jTextFieldCommandParamSequence.setText(HelpA.getValueSelectedRow(table, "Cmd Parameter"));
            mCRecipe.jTextFieldInfoSequence.setText(HelpA.getValueSelectedRow(table, "Info"));
            mCRecipe.jTextFieldUpdateOnSequence.setText(HelpA.getValueSelectedRow(table, "UpdatedOn"));
            mCRecipe.jTextFieldUpdatedBy.setText(HelpA.getValueSelectedRow(table, "UpdatedBy"));
        } else {
            //
            String recipe = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxSequenceRecipe);
            String release = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxSequenceRelease);
            String mixerCode = HelpA.getComboBoxSelectedValue(mCRecipe.jComboBoxSequenceMixerCode);
            //
            String q = SQL_A.sequence_get_sequence_main(PROC.PROC_21, recipe, release, mixerCode);
            //
            JTable table_temp = new JTable();
            //
            try {
                ResultSet rs = sql.execute(q, OUT);
                HelpA.build_table_common(rs, table_temp, q);
                //
                if (table_temp.getRowCount() == 0) {
                    SEQUENCE_PRESENT = false;
                }
                //
            } catch (SQLException ex) {
                Logger.getLogger(Sequence.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            //
            if (table_temp.getRowCount() != 0) {
                mCRecipe.jTextFieldInfoSequence.setText(HelpA.getValueGivenRow(table_temp, 0, "Info"));
                mCRecipe.jTextFieldUpdateOnSequence.setText(HelpA.getValueGivenRow(table_temp, 0, "UpdatedOn"));
                mCRecipe.jTextFieldUpdatedBy.setText(HelpA.getValueGivenRow(table_temp, 0, "UpdatedBy"));
            }
        }
        //
        buttonLogic();
        //
    }

    private void buttonLogic() {
        if (SEQUENCE_PRESENT == false) {
            mCRecipe.jButtonSequenceAddStep.setEnabled(false);
            mCRecipe.jButton13.setEnabled(false);
            mCRecipe.jButton14.setEnabled(false);
            mCRecipe.jButtonSequenceAddLastStep.setEnabled(false);
        } else {
            mCRecipe.jButtonSequenceAddStep.setEnabled(true);
            mCRecipe.jButton13.setEnabled(true);
            mCRecipe.jButton14.setEnabled(true);
            mCRecipe.jButtonSequenceAddLastStep.setEnabled(true);
        }
    }

    public void clearRecipeSequenceInfo() {
        mCRecipe.jTextFieldInfoSequence.setText("");
        mCRecipe.jTextFieldUpdateOnSequence.setText("");
        mCRecipe.jTextFieldUpdatedBy.setText("");
        //
        mCRecipe.jTextFieldUpdateOnSequence.setEditable(false);
        mCRecipe.jTextFieldUpdatedBy.setEditable(false);
    }

    public void clearComboBoxes() {
        mCRecipe.jComboBoxSequenceMixerCode.setSelectedItem(null);
        mCRecipe.jComboBoxSequenceRecipe.setSelectedItem(null);
        mCRecipe.jComboBoxSequenceRelease.setSelectedItem(null);
        //
        mCRecipe.jComboBoxSequenceMixerCode.setEditable(false);
        mCRecipe.jComboBoxSequenceRecipe.setEditable(false);
        mCRecipe.jComboBoxSequenceRelease.setEditable(false);
        //
    }

    public void clearComboBoxesCopy() {
        mCRecipe.jComboBoxSequenceMixerCodeCopy.setSelectedItem(null);
        mCRecipe.jComboBoxSequenceRecipeCopy.setSelectedItem(null);
        mCRecipe.jComboBoxSequenceReleaseCopy.setSelectedItem(null);
        //
        mCRecipe.jComboBoxSequenceMixerCodeCopy.setEditable(false);
        mCRecipe.jComboBoxSequenceRecipeCopy.setEditable(false);
        mCRecipe.jComboBoxSequenceReleaseCopy.setEditable(false);
    }

    @Override
    public void initializeSaveIndicators() {
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
    public boolean getUnsaved(int nr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fillNotes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
