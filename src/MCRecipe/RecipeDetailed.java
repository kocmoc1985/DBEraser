/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import MCRecipe.Sec.HelpM;
import MCRecipe.Lang.LNG;
import MCRecipe.Lang.R_DETAILED;
import MCRecipe.Lang.T_INV;
import static MCRecipe.RecipeInitial.T1_RECIPE_ID;
import static MCRecipe.RecipeInitial.T1_STATUS;
import MCRecipe.Sec.PROC;
import MyObjectTableInvert.BasicTab;
import MyObjectTable.SaveIndicator;
import MyObjectTableInvert.Run_Invert;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.RowDataInvert;
import forall.HTMLPrint;
import forall.HelpA;
import forall.JComboBoxA;
import forall.SqlBasicLocal;
import images.IconUrls;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import mySwing.JTableM;

/**
 *
 * @author KOCMOC
 */
public class RecipeDetailed extends BasicTab {

    private final MC_RECIPE mCRecipe2;
    private final ChangeSaver changeSaver;
    private final RecipeInitial recipeInitial;
    protected LinkedList<Integer> unsavedChanges_table4 = new LinkedList<Integer>();//This should contain the "Id" of the row
    protected LinkedList<Integer> insertedMaterials_table4 = new LinkedList<Integer>();//This should contain the "Id" of the row
    protected LinkedList<Integer> addedRows_table_2 = new LinkedList<Integer>();
    private TableBuilderInvert TABLE_BUILDER_INVERT;
    private LinkedList<IngredientToDelete> ingredientsToDelete_table_4 = new LinkedList<IngredientToDelete>();
    //
    public static final String t4_id = "Id";
    public static final String t4_recipeId = "RecipeID";
    public static final String t4_material = "material";
    public static final String t4_release = "Release";
    public static final String t4_phr = "PHR";
    public static final String t4_weight = "weight";
    public static final String t4_containerNb = "Weighing S.";//ContainerNB
    public static final String t4_phase = "Loading S.";//Phase
    public static final String t4_matIndex = "MatIndex";
    public static final String t4_volume = "Volume";
    public static final String t4_loadingSeq = "Loading S.";
    public static final String t4_Descr = "Descr";
    public static final String t4_density = "density";
    public static final String t4_phr_recalc = "PHR_recalc";
    public static final String t4_weight_recalc = "weight_Recalc";
    public static final String t4_volume_recalc = "volume_Recalc";
    //
    public static final String t4_material_nick = "Ingredient";
    public static final String t4_Descr_nick = "Description";
    public static final String t4_matIndex_nick = "Mat Index";
    public static final String t4_density_nick = "Density";
    public static final String t4_phr_recalc_nick = "PHR Recalc";
    public static final String t4_weight_nick = "Weight";
    public static final String t4_weight_recalc_nick = "Weight Recalc";
    public static final String t4_volume_recalc_nick = "Volume Recalc";
    //
    private boolean CHANGES_TEMP_T4_APPLIED = false;
    private boolean MARKED_FOR_DELITION = false;
    //

    public RecipeDetailed(MC_RECIPE mCRecipe2, SqlBasicLocal sql, SqlBasicLocal sql_additional, ChangeSaver changeSaver, RecipeInitial recipeInitial) {
        super(sql, sql_additional, mCRecipe2);
        this.mCRecipe2 = mCRecipe2;
        this.changeSaver = changeSaver;
        this.recipeInitial = recipeInitial;
        //
        initializeSaveIndicators();
        fill_table4_recalc_combobox();
    }

    public void showLockedUnlocked() {
        if (mCRecipe2.recipeInitial.checkIfRecipeDisabled()) {
            setLocked(true);
        } else {
            setLocked(false);
        }
    }

    private void setLocked(boolean locked) {
        if (locked) {
            HelpA.setJLabelIcon(mCRecipe2.jLabelLockedUnlocked, IconUrls.LOCKED_ICON);
        } else {
            HelpA.setJLabelIcon(mCRecipe2.jLabelLockedUnlocked, IconUrls.UNLOCKED_ICON);
        }
    }

    @Override
    public void initializeSaveIndicators() {
        SaveIndicator saveIndicator2 = new SaveIndicator(mCRecipe2.jButtonRecipeDetailedSaveTable4, this, 2);
        SaveIndicator saveIndicator3 = new SaveIndicator(mCRecipe2.jButton7, this, 3);
        SaveIndicator saveIndicator4 = new SaveIndicator(mCRecipe2.jButton_Recipe_Detailed_Save_Invert, this, 4);
    }

    @Override
    public boolean getUnsaved(int nr) {
        if (nr == 2) {
            //
            if (unsavedChanges_table4.isEmpty() == false) {
                return true;
            } else if (CHANGES_TEMP_T4_APPLIED) {
                return true;
            } else if (MARKED_FOR_DELITION) {
                return true;
            }
            //
        } else if (nr == 3) {
            //
            return notesUnsaved;
            //
        } else if (nr == 4) {
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

    public boolean checkIfDisabled() {
        //
        String status = getStatus();
        //
        boolean cond_1 = status.equals("S") || status.equals("O") || status.equals("Inactive");
        //
        if (cond_1) {
            setBtnsDisabled(false); // this one disables
            return true;
        } else {
            setBtnsDisabled(true);// this one enables
            return false;
        }
    }

    public void unblockRecipe(JTable table) {
        //
        String status = HelpA.getValueSelectedRow(table, T1_STATUS);
        //
        boolean cond_1 = status.equals("S") == false && status.equals("O") == false
                && status.equals("Inactive") == false;
        //
        //
        if (cond_1) {
            HelpA.showNotification("The recipe status is not S or O or Inactive");
            return;
        }
        //
        if (HelpA.confirm("Unblock recipe?") == false) {
            return;
        }
        //
        String recipeID = HelpA.getValueSelectedRow(table, T1_RECIPE_ID);
        //
        String q = SQL_A.recipe_initial_unblock_recipe(recipeID);
        //
        try {
            sql.execute(q, OUT);
        } catch (SQLException ex) {
            Logger.getLogger(RecipeInitial.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        HelpA.setValueGivenRow(table, table.getSelectedRow(), T1_STATUS, "I");
        //
        mCRecipe2.recipeDetailedTabbClicked();
        //
        HelpA.openTabByName(mCRecipe2.jTabbedPane1, LNG.RECIPE_DETAILED_TAB);
        //
        showLockedUnlocked();
    }

    public void nextRecipe() {
        HelpA.selectNextRow(mCRecipe2.jTable1);
        mCRecipe2.clickedOnTable1RecipeInitial();
        mCRecipe2.recipeDetailedTabbClicked();
    }

    public void prevRecipe() {
        HelpA.selectPrevRow(mCRecipe2.jTable1);
        mCRecipe2.clickedOnTable1RecipeInitial();
        mCRecipe2.recipeDetailedTabbClicked();
    }

    public void table1Repport() {
        tableInvertExportOrRepport(TABLE_INVERT, 1, getConfigTableInvert());
    }

    public void table4RepportCSV() {
        jTableToCSV(mCRecipe2.jTable4RecipeDetailed, true, new String[]{"material", "PHR", "weight"});
    }

    public void table4Repport() {
        //
//        tableCommonExportOrRepport(mCRecipe2.jTable4RecipeDetailed, true);
        //
        String[] CSSRules = {
            "table {margin-bottom:10px;}",
            "tr {border-bottom: 1px solid black;}",
            ".jtable {font-size:7pt;}",
            ".table-invert {font-size:8pt;}",};
        //
        String[] colsToInclude = {"Ingredient", "Description", "Density", "PHR", "Weight", "Volume",
            "Loading S.", "PHR Recalc", "Weight Recalc", "Volume Recalc"};
        //
        HTMLPrint print = new HTMLPrint(getRecipeCode(), mCRecipe2.jTable4RecipeDetailed,
                mCRecipe2.jTableRecipeDetailedTable4HelpTable,
                TABLE_INVERT, 1, getConfigTableInvert(), CSSRules, colsToInclude);
        print.setVisible(true);
    }

    public void autoSelectFirstRowTable4() {
        //
        boolean cond_1 = HelpA.isEmtyJTable(mCRecipe2.jTable4RecipeDetailed);
        //
        if (cond_1 == false) {

            for (int i = 0; i < mCRecipe2.jTable4RecipeDetailed.getRowCount(); i++) {
                //
                HelpA.markGivenRow(mCRecipe2.jTable4RecipeDetailed, i);
                //
                String ingred = HelpA.getValueSelectedRow(mCRecipe2.jTable4RecipeDetailed, t4_material);
                //
                if (HelpA.isIngred(ingred)) {
                    jTable4RecipeDetailedClicked();
                    break;
                }
                //
            }
        }
    }

    public void jTable4RecipeDetailedClicked() {
        //
        String ingredName = HelpA.getValueSelectedRow(mCRecipe2.jTable4RecipeDetailed, "material");
        //
        if (ingredName != null && ingredName.isEmpty() == false) {
            Ingredients.INGRED_NAME = ingredName;
        }
        //
    }

    public void refreshRecipeInitialTable1AfterSaving() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //
                //
                JTable table = mCRecipe2.jTable1;
                //
                //
                if (RECIPE_ID_REFRESH_TABLE != null) {
                    recipeInitial.fill_table_1(RECIPE_CODE_REFRESH_TABLE, null, null, null);
                    HelpA.markGivenRow(table, HelpA.getRowByValue(table, RecipeInitial.T1_RECIPE_ID, RECIPE_ID_REFRESH_TABLE));
                    RECIPE_ID_REFRESH_TABLE = null;
                    RECIPE_CODE_REFRESH_TABLE = null;
                } else {
                    int row = table.getSelectedRow();
                    recipeInitial.fill_table_1(null, null, null, null);
                    HelpA.setSelectedRow(table, row);
                }
                //
                mCRecipe2.clickedOnTable1RecipeInitial();
                //
                mCRecipe2.recipeDetailedTabbClicked();
                //

            }
        });
    }

    public void reset() {
        unsavedChanges_table4 = new LinkedList<Integer>();
        //
        ingredientsToDelete_table_4 = new LinkedList<IngredientToDelete>();
        paint_selected_rows(ingredientsToDelete_table_4, mCRecipe2.jTable4RecipeDetailed, null);
        //
        CHANGES_TEMP_T4_APPLIED = false;
        //
        MARKED_FOR_DELITION = false;
    }
    //
    private static final String C_INGRED_WEIGHT = R_DETAILED.CHANGE_INGR_WEIGHTS_COMBO();
    private static final String C_INGRED_PHR = R_DETAILED.CHANGE_INGR_PHR_COMBO();
    private static final String C_TOTAL_VOLUME = R_DETAILED.CHANGE_TOTAL_VOLUME_COMBO();
    private static final String C_TOTAL_WEIGHT = R_DETAILED.CHANGE_TOTAL_WEIGHT_COMBO();
    private static final String C_LOAD_FACTOR = R_DETAILED.CHANGE_LOAD_FACTOR_COMBO();

    private void fill_table4_recalc_combobox() {
        String[] values = new String[5];
        values[0] = C_INGRED_WEIGHT;
        values[1] = C_INGRED_PHR;
        values[2] = C_TOTAL_VOLUME;
        values[3] = C_TOTAL_WEIGHT;
        values[4] = C_LOAD_FACTOR;
        HelpA.fillComboBox(mCRecipe2.jComboBox1, values, null);
    }

    public String getRecipeCode() {
        return getValueTableInvert("Code");
    }

    public String getRelease() {
        return getValueTableInvert("Release");
    }
    
    public String getVersion() {
        return getRelease();
    }

    public String getRecipeId() {
        return getValueTableInvert("Recipe_ID");
    }

    public String getMixerCode() {
        return getValueTableInvert("Mixer_Code");
    }

    public String getDetailedGroup() {
        return getValueTableInvert("Detailed_Group");
    }

    public String getLoadFactor() {
        return getValueTableInvert("Loadfactor");
    }

    public String getStatus() {
        return getValueTableInvert("Status");
    }

    public void saveTableInvert() {
        if (getDetailedGroup().equals("NULL") || getMixerCode().equals("NULL")) {
            HelpA.showNotification("Cannot proceed, Polymer group and Mixer must be chosen");
            return;
        }
        //
        saveChangesTableInvert();
        //
        refreshRecipeInitialTable1AfterSaving();
    }

    public void table3_add_note() {
        //
        String recipe_id = getRecipeId();
        //
        //
        JTextField noteName = new JTextField();
        JTextField noteValue = new JTextField();
        //
        HelpA.chooseFrom2Textfields(noteName, noteValue, "NoteName", "NoteValue", "Add note");
        //
        if (noteName.getText().isEmpty() || noteValue.getText().isEmpty()) {
            HelpA.showNotification("Both values must be specified");
            return;
        }
        //
        String q = SQL_A.recipe_detailed_insert_note_table_3(PROC.PROC_08, recipe_id, noteName.getText(), noteValue.getText(), HelpA.updatedOn(), HelpA.updatedBy());
        //
        try {
            sql.execute(q, mCRecipe2);
        } catch (SQLException ex) {
            Logger.getLogger(RecipeDetailed.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        recipeInitial.fill_table_2_and_3(); // Refresh
    }

    public void apply_changes_table4_tempory() {
        //
        HelpA.stopEditJTable(mCRecipe2.jTable4RecipeDetailed);
        //
        save_changes_table_4_tempory(mCRecipe2.jTable4RecipeDetailed);
        //
        delete_from_table_4_tempory(); // Deletes entries marked for deleting
        //
        fill_table_4(false); // must fill after delete
        //
        recalculate_table_4_temp(); // Makes Recalc and updates "Recipe_Tempory"
        //
        fill_table_4(false);
        //
        CHANGES_TEMP_T4_APPLIED = true;
        //
        setAllEntriesChangedTable4(mCRecipe2.jTable4RecipeDetailed, t4_id);
    }

    private void setAllEntriesChangedTable4(JTable table, String rowIdColumnName) {
        for (int row = 0; row < (table.getRowCount()); row++) {
            String ROW_ID = "" + table.getValueAt(row, HelpA.getColByName(table, rowIdColumnName));
            addPotentiallyUnsavedEntries(Integer.parseInt(ROW_ID), unsavedChanges_table4, null, table);
        }
    }

    public void apply_changes_table4_real(boolean confirm) {
        //
        if (confirm) {
            if (HelpA.confirm() == false) {
                return;
            }
        }
        //
        HelpA.stopEditJTable(mCRecipe2.jTable4RecipeDetailed);
        //
        JTable table4 = mCRecipe2.jTable4RecipeDetailed;
        //
        saveLoadFactor();
        //
        save_changes_table_4_real(table4);
        //
        insert_materials_table_4_real(table4);
        //
        delete_from_table_4_real();
        //
        CHANGES_TEMP_T4_APPLIED = false;
        //
        fill_table_4(true);
        //
        reset();
    }

    private void saveLoadFactor() {
        //
        String newFillFactor;
        //    
        try {
            newFillFactor = HelpA.getValueGivenRow(mCRecipe2.jTableRecipeDetailedTable4HelpTable, 0, t4_Descr);
        } catch (Exception ex) {
            return;
        }
        //
        if (newFillFactor == null || newFillFactor.isEmpty()) {
            return;
        }
        //
        changeValueAndSave(TABLE_INVERT, newFillFactor, "Loadfactor", 1);
    }

    private void delete_from_table_4_tempory() {
        for (IngredientToDelete ingred : ingredientsToDelete_table_4) {
            //
            String q = SQL_A.recipyTemporaryDelete(PROC.PROC_57, ingred.getRecipe_recipe_id(), HelpA.updatedBy());
            //
            try {
                sql.execute(q, mCRecipe2);
            } catch (SQLException ex) {
                Logger.getLogger(RecipeDetailed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void delete_from_table_4_real() {
        while (ingredientsToDelete_table_4.isEmpty() == false) {
            //
            IngredientToDelete ingred = ingredientsToDelete_table_4.pollLast();
            //
            double recipe_id = Double.parseDouble(ingred.getRecipe_recipe_id());
            //
            String q = SQL_B.delete_record_table_4("" + (int) recipe_id);
            //
            try {
                sql.execute(q, mCRecipe2);
            } catch (SQLException ex) {
                Logger.getLogger(RecipeDetailed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean checkDensity(String ingredient) {
        //
        String density = null;
        //
        String q = SQL_A.recipe_detailed_find_density_ingred(PROC.PROC_06, ingredient);
        //
        try {
            ResultSet rs = sql.execute(q, mCRecipe2);
            if (rs.next()) {
                density = rs.getString("density");
            }
            //
        } catch (SQLException ex) {
            Logger.getLogger(RecipeDetailed.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //
        if (density == null || density.equals("0") || density.equals("null") || density.equals("NULL")) {
            return false;
        } else {
            return true;
        }
    }

    public void add_ingredient_table_4(String ingred) {
        //
        String ingredient;
        //
        if (ingred == null) {
            //
            JComboBoxA box = new JComboBoxA();
            //
            box.fillComboBox(sql, box, SQL_B.fill_ingredients_combo_box_for_table_4(), null, false, false);
            //
            if (HelpA.chooseFromComboBoxDialog(box, "Choose material") == false) {
                return;
            }
            //
            ingredient = HelpA.getComboBoxSelectedValue(box);
            // 
        } else {
            ingredient = ingred;
        }
        //
        //
        if (checkDensity(ingredient) == false) {
            HelpA.showNotification("Cannot add material, density = 0. Go to Ingredients and change density");
            return;
        }
        //
        String recipe_code = getRecipeCode();
        String release = getRelease();
        //
        //
        String q = SQL_A.recipyTemporaryInsert(PROC.PROC_59, recipe_code, release, ingredient, HelpA.updatedBy());
        //
        try {
            sql.execute(q, mCRecipe2);
        } catch (SQLException ex) {
            Logger.getLogger(RecipeDetailed.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        //
        fill_table_4(false);
        //
        //
        JTable table4 = mCRecipe2.jTable4RecipeDetailed;
        //
        String ROW_ID = HelpM.recipeDetailedGetLatestAddedId(table4, t4_id);
        //
//        HelpA.moveRowTo(table4, HelpA.getRowByValue(table4, t4_id, ROW_ID), table4.getRowCount()-1);
        //
        if (insertedMaterials_table4.contains(Integer.parseInt(ROW_ID)) == false) {
            insertedMaterials_table4.add(Integer.parseInt(ROW_ID));
        }
        //
        //Move already added entries to the end of the list, before adding others
        for (Integer row_id : insertedMaterials_table4) {
            HelpA.moveRowTo(table4, HelpA.getRowByValue(table4, t4_id, "" + row_id), table4.getRowCount() - 1);
        }
        //
        HelpA.markLastRowJtable(table4);
        //
        jTable4RecipeDetailedClicked();
        //
        CHANGES_TEMP_T4_APPLIED = true;
        //
        apply_changes_table4_real(false);
        //
    }

    public void undo_mark_to_delete_ingredient_table_4() {
        ingredientsToDelete_table_4.pollLast();
        paint_selected_rows(ingredientsToDelete_table_4, mCRecipe2.jTable4RecipeDetailed, null);
    }

    public void mark_to_delete_ingredient_table_4() {
        //
        JTable table = mCRecipe2.jTable4RecipeDetailed;
        int currRow = table.getSelectedRow();
        //
        String ROW_ID = HelpA.getValueSelectedRow(table, t4_id);
        //
        //Note that Recipe_Recipe_ID is hidden
        String recipe_recipe_id = HelpA.getValueSelectedRow(table, "Recipe_Recipe_ID");
        //
        if (rowToDeleteExists(currRow)) {
            return;
        }
        //
        HelpA.moveRowToEnd(table, currRow);
        //
        ingredientsToDelete_table_4.add(new IngredientToDelete(Integer.parseInt(ROW_ID), recipe_recipe_id));
        //
        paint_selected_rows(ingredientsToDelete_table_4, table, Color.red);
        //
        MARKED_FOR_DELITION = true;
    }

    public void fill_table_4(boolean recreateRecipeTempTable) {
        //
        JTableM table4 = (JTableM) mCRecipe2.jTable4RecipeDetailed;
        //
        if (TABLE_INVERT == null) {
            return;
        }
        //
        String mixerCode = getMixerCode();
        String loadFactor = getLoadFactor();
        //
        //
        String q0 = SQL_A.createRecipeTempTable(PROC.PROC_60, getRecipeCode(), getRelease(), HelpA.updatedBy());
        //
        String q_0_1 = SQL_A.RecipeTemporaryPrepareSelect(PROC.PROC_61, mixerCode, loadFactor, HelpA.updatedBy());
        //
        String q1 = SQL_A.RecipeTemporarySelect(HelpA.updatedBy());
        //
        //
        try {
            //
            if (recreateRecipeTempTable) {
                sql.execute(q0, mCRecipe2);
            }
            //
            OUT.showMessage(q_0_1);
            //
            if (HelpA.runProcedureIntegerReturn_A_2(sql_additional, q_0_1) != 0) {
                return;
            }
            //
            ResultSet rs = sql.execute(q1, mCRecipe2);
            //
            //
//            HelpA.build_table_common_with_rounding(rs, q1, table4, "%2.2f",
//                    new String[]{t4_id, t4_loadingSeq, t4_material},
//                    new String[]{t4_density},
//                    new String[]{t4_id});
            //
            table4.build_table_common_with_rounding(rs, q1, table4, "%2.2f",
                    new String[]{t4_id, t4_loadingSeq, t4_material},
                    new String[]{t4_density},
                    new String[]{t4_id});
            //
        } catch (SQLException ex) {
            Logger.getLogger(RecipeDetailed.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        //
        fillTable4HelpTable();
        //
        //
        HelpA.paintTableHeaderBorderOneColumn(table4, HelpA.getColByName(table4, t4_matIndex), Color.green);
        HelpA.paintTableHeaderBorderOneColumn(table4, HelpA.getColByName(table4, t4_phr), Color.green);
        HelpA.paintTableHeaderBorderOneColumn(table4, HelpA.getColByName(table4, t4_weight), Color.green);
        HelpA.paintTableHeaderBorderOneColumn(table4, HelpA.getColByName(table4, t4_containerNb), Color.green);
        HelpA.paintTableHeaderBorderOneColumn(table4, HelpA.getColByName(table4, t4_phase), Color.green);
        //
        hideColumnsTable4AndTable4Help(table4);
        //HelpA.hideColumnByName(table4, "Id");
        //
        renameColumnsTable4AndTable4Help(table4);
        //
        //Reset all painting
        paint_selected_rows(new LinkedList<IngredientToDelete>(), table4, null);
        //
        //
//        HelpA.R_DETAILED_TABLE_4_LIST = HelpA.restoreListFromObject(HelpA.R_DETAILED_TABLE_4_OBJ);
//        //
//        HelpA.restoreColumnWidths(table4, HelpA.R_DETAILED_TABLE_4_LIST);
//        //
//        HelpA.synchColumnWidths(mCRecipe2.jTable4RecipeDetailed, mCRecipe2.jTableRecipeDetailedTable4HelpTable);
        //
    }

    private void fillTable4HelpTable() {
        //
        // OBS! Hide/Unhide of table header is done via "Customize code" 
        // jTableRecipeDetailedTable4HelpTable.setTableHeader(null);
        //
        checkIfEmpty();// This makes that 
        //
        JTable table = mCRecipe2.jTableRecipeDetailedTable4HelpTable;
        //
        String q = SQL_A.RecipeTemporarySelectForTable4SummTable(HelpA.updatedBy());
        //
        try {
            ResultSet rs = sql.execute(q, mCRecipe2);
            HelpA.build_table_common_with_rounding(rs, q, table, "%2.2f", new String[]{t4_id,
                t4_loadingSeq, t4_material},
                    new String[]{t4_density},
                    new String[]{});
        } catch (SQLException ex) {
            Logger.getLogger(RecipeDetailed.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        //
        try {
            //
//            table.setRowHeight(0, 25);
            table.setSelectionBackground(null);
            table.setSelectionForeground(Color.black);
            //
            hideColumnsTable4AndTable4Help(table);
            //
            renameColumnsTable4AndTable4Help(table);
            //
            HelpA.changeTableHeaderTitleOfOneColumn(table, t4_id, "");
            HelpA.changeTableHeaderTitleOfOneColumn(table, t4_material, "");
            HelpA.changeTableHeaderTitleOfOneColumn(table, t4_loadingSeq, "");
            HelpA.changeTableHeaderTitleOfOneColumn(table, t4_containerNb, "");
            HelpA.changeTableHeaderTitleOfOneColumn(table, t4_Descr, t4_Fillfactor);
            //
            HelpA.setValueGivenRow(table, 0, t4_id, "");
            //
        } catch (Exception ex) {
//            Logger.getLogger(RecipeDetailed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static final String t4_Fillfactor = "Fillfactor";

    private void checkIfEmpty() {
        if (mCRecipe2.jTable4RecipeDetailed.getRowCount() == 0) {
            String q = SQL_A.recipeDetailedDeleteIfEmpty(HelpA.updatedBy());
            try {
                ResultSet rs = sql.execute(q, mCRecipe2);
            } catch (SQLException ex) {
                Logger.getLogger(RecipeDetailed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void renameColumnsTable4AndTable4Help(JTable table) {
        HelpA.changeTableHeaderTitleOfOneColumn_to_hashmap(table, t4_material, t4_material_nick);
        HelpA.changeTableHeaderTitleOfOneColumn_to_hashmap(table, t4_Descr, t4_Descr_nick);
        HelpA.changeTableHeaderTitleOfOneColumn_to_hashmap(table, t4_matIndex, t4_matIndex_nick);
        HelpA.changeTableHeaderTitleOfOneColumn_to_hashmap(table, t4_density, t4_density_nick);
//        HelpA.changeTableHeaderTitleOfOneColumn(table, "density_Recalc", "Density Recalc");
        HelpA.changeTableHeaderTitleOfOneColumn_to_hashmap(table, t4_phr_recalc, t4_phr_recalc_nick);
        HelpA.changeTableHeaderTitleOfOneColumn_to_hashmap(table, t4_weight, t4_weight_nick);
        HelpA.changeTableHeaderTitleOfOneColumn_to_hashmap(table, t4_weight_recalc, t4_weight_recalc_nick);
        HelpA.changeTableHeaderTitleOfOneColumn_to_hashmap(table, t4_volume_recalc, t4_volume_recalc_nick);
    }

    private void hideColumnsTable4AndTable4Help(JTable table) {
        HelpA.hideColumnByName(table, "Recipe_Recipe_ID");
        HelpA.hideColumnByName(table, "RecipeID");
        HelpA.hideColumnByName(table, "RecipeName");
        HelpA.hideColumnByName(table, "Release");
        HelpA.hideColumnByName(table, "weighingID");
        HelpA.hideColumnByName(table, "SiloId");
        HelpA.hideColumnByName(table, "BalanceID");
        HelpA.hideColumnByName(table, "PriceData");
        HelpA.hideColumnByName(table, "GRP");
        HelpA.hideColumnByName(table, "percRubber");
    }

    public void edit_ingredients_parameters_table_4(int row, int col) {
        //
        JTable table4 = mCRecipe2.jTable4RecipeDetailed;
        //
        String ROW_ID = "" + table4.getValueAt(row, HelpA.getColByName(table4, t4_id));
        //
        if (ROW_ID == null || ROW_ID.isEmpty()) {
            HelpA.stopEditJTable(table4);
            return;
        }
        //
        addPotentiallyUnsavedEntries(Integer.parseInt(ROW_ID), unsavedChanges_table4, null, table4);
        //
        JComboBox box = new JComboBox();
        box.setPreferredSize(new Dimension(10, 30));
        //
        if (col == HelpA.getColByName(table4, t4_matIndex)) {
            //        
            HelpA.fillComboBox(box, new String[]{"I", "R"}, null);
            //
            choose_and_set_value_table_4(table4, box, "Choose MatIndex", row, col);
            //
        } else if (col == HelpA.getColByName(table4, t4_containerNb)) {
            //
            HelpA.fillComboBox(sql, box, SQL_B.fill_containerNB_combo_box_table_4(), null, false, false);
            //
            choose_and_set_value_table_4(table4, box, "Choose Container", row, col);
        }
    }

    private void choose_and_set_value_table_4(JTable table, JComboBox box, String msg, int row, int col) {
        //
        if (HelpA.chooseFromComboBoxDialog(box, msg) == false) {
            return;
        }
        //
        table.editCellAt(0, 0);
        //
        table.setValueAt(HelpA.getComboBoxSelectedValue(box).replaceAll("'", ""), row, col);
    }

    private void save_changes_table_4_tempory(JTable table) {

        for (Integer row_id : unsavedChanges_table4) {
            //
            int row = HelpA.getRowByValue(table, t4_id, "" + row_id);
            //
            String rowId = "" + table.getValueAt(row, HelpA.getColByName(table, t4_id));
            //
            String phr = (String) table.getValueAt(row, HelpA.getColByName(table, t4_phr));
            String weight = (String) table.getValueAt(row, HelpA.getColByName(table, t4_weight));
            String container_nb = (String) table.getValueAt(row, HelpA.getColByName(table, t4_containerNb));
            String phase = (String) table.getValueAt(row, HelpA.getColByName(table, t4_phase));
            String mat_index = (String) table.getValueAt(row, HelpA.getColByName(table, t4_matIndex));
            //
            phr = HelpM.checkValuesTable4(phr);
            weight = HelpM.checkValuesTable4(weight);
            container_nb = HelpM.checkValuesTable4(container_nb);
            phase = HelpM.checkValuesTable4(phase);
            mat_index = HelpM.checkValuesTable4(mat_index);
            //
            String q = SQL_A.recipyTemporaryUpdate(PROC.PROC_58, rowId, phr, weight, container_nb, phase, mat_index, HelpA.updatedBy());
            //
            //
            try {
                sql.execute(q, mCRecipe2);
            } catch (SQLException ex) {
                Logger.getLogger(RecipeDetailed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void recalculate_table_4_temp() {
        //
        String command = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBox1);
        //
        String q = "";
        //
        String recipe_code = getRecipeCode();
        String release = getRelease();
        //
        if (command.equals(C_INGRED_WEIGHT)) {
            //
            q = SQL_A.changeIngredWeights(PROC.PROC_55, recipe_code, release, HelpA.updatedBy());
            //
        } else if (command.equals(C_INGRED_PHR)) {
            //
            q = SQL_A.changeIngredPHR(PROC.PROC_54, recipe_code, release, HelpA.updatedBy());
            //
        } else if (command.equals(C_TOTAL_VOLUME)) {
            //
            String totalVolume = JOptionPane.showInputDialog(null, "Specify total volume");
            //
            if (HelpA.checkIfNumber(totalVolume) == false) {
                return;
            }
            //
            q = SQL_A.changeTotalVolume(PROC.PROC_53, recipe_code, release, totalVolume, HelpA.updatedBy());
            //
        } else if (command.equals(C_TOTAL_WEIGHT)) {
            //
            String totalWeight = JOptionPane.showInputDialog(null, "Specify total weight");
            //
            if (HelpA.checkIfNumber(totalWeight) == false) {
                return;
            }
            //
            q = SQL_A.changeTotalWeight(PROC.PROC_51, recipe_code, release, totalWeight, HelpA.updatedBy());
            //
        } else if (command.equals(C_LOAD_FACTOR)) {
            //
            String newFillFactor = JOptionPane.showInputDialog(null, "Specify new fill factor");
            //
            if (HelpA.checkIfNumber(newFillFactor) == false) {
                return;
            }
            //
            String mixerCode = getMixerCode();
            //
            q = SQL_A.changeLoadFactor(PROC.PROC_52, recipe_code, release, newFillFactor, mixerCode, HelpA.updatedBy());
            //
        }
        //
        //
        try {
            sql.execute(q, mCRecipe2);
        } catch (SQLException ex) {
            Logger.getLogger(RecipeDetailed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void save_changes_table_4_real(JTable table) {

        while (unsavedChanges_table4.isEmpty() == false) {
            //
            Integer row_id = unsavedChanges_table4.pollLast();
            //
            int row = HelpA.getRowByValue(table, t4_id, "" + row_id);
            //
            String recipe_recipe_id = (String) table.getValueAt(row, HelpA.getColByName(table, "Recipe_Recipe_ID"));
            //
            String phr = (String) table.getValueAt(row, HelpA.getColByName(table, t4_phr));//**!!
            String weight = (String) table.getValueAt(row, HelpA.getColByName(table, t4_weight));//**!!
            String container_nb = (String) table.getValueAt(row, HelpA.getColByName(table, t4_containerNb));
            String phase = (String) table.getValueAt(row, HelpA.getColByName(table, t4_phase));
            String mat_index = (String) table.getValueAt(row, HelpA.getColByName(table, t4_matIndex));
            //
            phr = HelpM.checkValuesTable4(phr);
            weight = HelpM.checkValuesTable4(weight);
            container_nb = HelpM.checkValuesTable4(container_nb);
            phase = HelpM.checkValuesTable4(phase);
            mat_index = HelpM.checkValuesTable4(mat_index);
            //
            String q = SQL_B.saveChangesTable4(phr, weight, container_nb, phase, mat_index, recipe_recipe_id);
            //
            //
            try {
                sql.execute(q, mCRecipe2);
            } catch (SQLException ex) {
                Logger.getLogger(RecipeDetailed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void insert_materials_table_4_real(JTable table) {

        while (insertedMaterials_table4.isEmpty() == false) {
            //
            Integer row_id = insertedMaterials_table4.pollLast();
            //
            int row = HelpA.getRowByValue(table, t4_id, "" + row_id);
            //
            String recipeId = (String) table.getValueAt(row, HelpA.getColByName(table, t4_recipeId));
            //
            String material = (String) table.getValueAt(row, HelpA.getColByName(table, t4_material));
            String phr = (String) table.getValueAt(row, HelpA.getColByName(table, t4_phr));
            String weight = (String) table.getValueAt(row, HelpA.getColByName(table, t4_weight));
            String phase_ = (String) table.getValueAt(row, HelpA.getColByName(table, t4_containerNb));
            String container_nb = (String) table.getValueAt(row, HelpA.getColByName(table, t4_phase));
            String mat_index = (String) table.getValueAt(row, HelpA.getColByName(table, t4_matIndex));
            String updatedBy = HelpA.updatedBy();
            //
            phr = HelpM.checkValuesTable4(phr);
            weight = HelpM.checkValuesTable4(weight);
            phase_ = HelpM.checkValuesTable4(phase_);
            container_nb = HelpM.checkValuesTable4(container_nb);
            mat_index = HelpM.checkValuesTable4(mat_index);
            //
            if (mat_index.equals("0")) {
                mat_index = "I";
            }
            //
            if (phase_.equals("0")) {
                phase_ = "A";
            }
            //
            if (container_nb.equals("0")) {
                container_nb = "1";
            }
            //
            String q = SQL_A.recipeRecipeInsertNewMaterial(PROC.PROC_56, recipeId, material, phr, weight, phase_, container_nb, mat_index, updatedBy);
            //
            try {
                sql.execute(q, mCRecipe2);
            } catch (SQLException ex) {
                Logger.getLogger(RecipeDetailed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    //==========================================================================
    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        RowDataInvert code = new RowDataInvert("Recipe_Prop_Main", "Recipe_ID", false, "Code", T_INV.LANG("CODE"), "", true, true, false);
        code.setUneditable();
        //
        String q_1 = SQL_B.basic_combobox_query("Release", "Recipe_Prop_Main");
        RowDataInvert release = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_1, sql_additional, "", "Recipe_Prop_Main", "Recipe_ID", false, "Release", T_INV.LANG("RELEASE"), "", true, true, false);
        //
        String q_2 = SQL_B.basic_combobox_query("Status", "Recipe_Prop_Main");
        RowDataInvert status = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_2, sql_additional, "", "Recipe_Prop_Main", "Recipe_ID", false, "Status", T_INV.LANG("STATUS"), "", true, true, false);
        status.enableFakeValueJComboBox();
        //
        String q_3 = SQL_B.basic_combobox_query("Class", "Recipe_Prop_Main");
        RowDataInvert clas = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_3, sql_additional, "", "Recipe_Prop_Main", "Recipe_ID", false, "Class", T_INV.LANG("CLASS"), "", true, true, false);
        clas.enableFakeValueJComboBox();
        //
        String q_4 = SQL_B.basic_combobox_query("Detailed_Group", "Recipe_Group");
        RowDataInvert detailed_group = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_4, sql_additional, "", "Recipe_Prop_Main", "Recipe_ID", false, "Detailed_Group", T_INV.LANG("POLYMER GROUP"), "", true, true, false);
        //
        String q_5 = SQL_B.basic_combobox_query_double_param("Code", "Name", "Mixer_InfoBasic");
        RowDataInvert mixer_code = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_5, sql_additional, "", "Recipe_Prop_Main", "Recipe_ID", false, "Mixer_Code", T_INV.LANG("MIXER"), "", true, true, false);
        mixer_code.enableComboBoxMultipleValue();
        //
        RowDataInvert mixer_name = new RowDataInvert("Mixer_InfoBasic", "Name", false, "Name", T_INV.LANG("MIXER"), "", true, false, false);
        //
        RowDataInvert load_factor = new RowDataInvert("Recipe_Prop_Main", "Recipe_ID", false, "Loadfactor", T_INV.LANG("LOADFACTOR"), "", true, true, false);

        RowDataInvert mix_time = new RowDataInvert("Recipe_Prop_Main", "Recipe_ID", false, "MixTime", T_INV.LANG("MIXTIME"), "", true, true, false);
        //
        RowDataInvert descr = new RowDataInvert("Recipe_Prop_Main", "Recipe_ID", false, "Descr", T_INV.LANG("DESCRIPTION"), "", true, true, false);
        //
        RowDataInvert customer = new RowDataInvert("Recipe_Prop_Free_Text", "Recipe_Prop_Free_Text_ID", false, "NoteValue", T_INV.LANG("CUSTOMER"), "", true, true, false);
        //
        RowDataInvert priceKg = new RowDataInvert("Recipe_Prop_Main", "Recipe_ID", false, "PriceKG", T_INV.LANG("PRICE/KG"), "", true, true, false);
        RowDataInvert priceL = new RowDataInvert("Recipe_Prop_Main", "Recipe_ID", false, "PriceL", T_INV.LANG("PRICE/L"), "", true, true, false);
        //
        RowDataInvert updated_on = new RowDataInvert("Recipe_Prop_Main", "Recipe_ID", false, "UpdatedOn", T_INV.LANG("UPDATED ON"), "", true, true, false);
        RowDataInvert updated_by = new RowDataInvert("Recipe_Prop_Main", "Recipe_ID", false, "UpdatedBy", T_INV.LANG("UPDATED BY"), "", true, true, false);
        //
        RowDataInvert created_on = new RowDataInvert("Recipe_Prop_Main", "Recipe_ID", false, "CreatedOn", T_INV.LANG("CREATED ON"), "", true, true, false);
        RowDataInvert created_by = new RowDataInvert("Recipe_Prop_Main", "Recipe_ID", false, "CreatedBy", T_INV.LANG("CREATED BY"), "", true, true, false);
        //
        mixer_name.setUneditable();
        updated_by.setUneditable();
        updated_on.setUneditable();
        created_on.setUneditable();
        created_by.setUneditable();
        //Invisible
        RowDataInvert recipe_id = new RowDataInvert("Recipe_Prop_Main", "Recipe_ID", false, "Recipe_ID", "RID", "", true, false, false);
        //
        RowDataInvert[] rows = {code, release, descr, customer, priceKg, priceL, status, clas, detailed_group, mixer_code, mixer_name, load_factor, mix_time, recipe_id, updated_on, updated_by, created_on, created_by};
        //
        //
        if (MC_RECIPE.SHOW_EXTRA_PARAMS_RECIPE_TABLE_INVERT == false) {
            String[] toRemove = new String[]{T_INV.LANG("PRICE/KG"), T_INV.LANG("PRICE/L")};
            return HelpA.removeGivenEntriesFromArray(rows, toRemove);
        } else {
            return rows;
        }
    }
    //
    //

    public void addNewRecipe(boolean fromScratch) {
        //
        String recipe_id = getRecipeId();
        //
        if (getRecipeCode().trim().equals("NEW") && fromScratch == false) {
//            HelpA.showNotification("Cannot create new from \"NEW\"");
            return;
        }
        //
        String q;
        //
        if (fromScratch) {
            q = SQL_A.recipe_detailed_add_new_recipe_scratch(PROC.PROC_12, recipe_id, HelpA.updatedOn(), HelpA.updatedBy());
        } else {
            q = SQL_A.recipe_detailed_add_new_recipe(PROC.PROC_13, recipe_id, HelpA.updatedOn(), HelpA.updatedBy());
        }
        //
        OUT.showMessage(q);
        //
        HelpA.runProcedureIntegerReturn_A_2(sql, q);
        //
        recipeInitial.clearBoxes();
        //
        if (updateRecipeCode() == false) {
            return;
        }
        //
        if (fromScratch) {
            clearRows(TABLE_INVERT, 1, 4);
            changeValueNoSave(TABLE_INVERT, new HelpA.ComboBoxObjectC("I", "UNLOCKED", ""), "Status", 1);
        } else {
            changeValueAndSave(TABLE_INVERT, new HelpA.ComboBoxObjectC("I", "UNLOCKED", ""), "Status", 1);
        }
    }
    //
    //
    private String recipe_code_update;

    private boolean updateRecipeCode() {
        //
        recipe_code_update = JOptionPane.showInputDialog("Specify Recipe Code");
        //
        if (recipe_code_update == null) {
            return false;
        }
        //
        String q = "select * from Recipe_Prop_Main"
                + " where Code ='" + recipe_code_update + "'"
                + " and Release = '" + getRelease() + "'";
        //
        OUT.showMessage(q);
        //
        boolean exists = HelpA.entryExistsSql(sql, q);
        //
        if (exists == true) {
            HelpA.showNotification("Recipe code: " + recipe_code_update
                    + "and Release: " + getRelease() + ","
                    + " exists already!");
            return false;
        }
        //
        String q2 = SQL_A.recipe_detailed_update_recipe_name(recipe_code_update);
        //
        try {
            sql.execute(q2, mCRecipe2);
        } catch (SQLException ex) {
            Logger.getLogger(RecipeDetailed.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        updateTables(recipe_code_update);
        //
        return true;
    }
    private static String RECIPE_ID_REFRESH_TABLE;
    private static String RECIPE_CODE_REFRESH_TABLE;

    private void updateTables(String recipeCode) {
        //
        JTable table = mCRecipe2.jTable1;
        //
        recipeInitial.fill_table_1(SQL_A.quotes(recipeCode, false), null, null, null);
        //
        String recipeId = HelpA.getLastIncrementedId(sql, "Recipe_Prop_Main");
        //
        RECIPE_ID_REFRESH_TABLE = recipeId;
        RECIPE_CODE_REFRESH_TABLE = recipeCode;
        //
        if (recipeId != null) {
            HelpA.markGivenRow(table, HelpA.getRowByValue(table, RecipeInitial.T1_RECIPE_ID, recipeId));
        } else {
            HelpA.markFirstRowJtable(table);
        }
        //
        mCRecipe2.clickedOnTable1RecipeInitial();
        //
        mCRecipe2.recipeDetailedTabbClicked();
        //
    }

    @Override
    public void showTableInvert() {
        //
        JTable table = mCRecipe2.jTable1;
        int currRow = table.getSelectedRow();
        //
        if (currRow == -1) {
            return;
        }
        //
        String code = HelpA.getValueSelectedRow(table, RecipeInitial.T1_RECIPE_VERSION);
        String release = HelpA.getValueSelectedRow(table, RecipeInitial.T1_RECIPE_ADDITIONAL);//Recipe_Addditional
        String mixer_code = HelpA.getValueSelectedRow(table, RecipeInitial.T1_MIXER_CODE);//Mixer_Code
        //
//        RowDataInvert[] config;
        //
//        if (MC_RECIPE.SHOW_EXTRA_PARAMS_RECIPE_TABLE_INVERT == false) {
//            String[] toRemove = new String[]{T_INV.LANG("PRICE/KG"), T_INV.LANG("PRICE/L")};
//            config = HelpA.removeGivenEntriesFromArray(getConfigTableInvert(), toRemove);
//        } else {
//            config = getConfigTableInvert();
//        }
        //
        //
        TABLE_BUILDER_INVERT = new TableBuilderInvert(OUT, sql, getConfigTableInvert(), false, "recipe_detailed_1");
        //
        TABLE_INVERT = null;
        //
        try {
            String q = SQL_A.RecipeInvertFunction(PROC.PROC_62, code, release, mixer_code);
            OUT.showMessage(q);
            TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q);
        } catch (SQLException ex) {
            Logger.getLogger(Run_Invert.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        setMargin(TABLE_INVERT, 10, 0, 0, 0);
        //
        showTableInvert(mCRecipe2.jPanel1);
    }

    @Override
    public void addPotentiallyUnsavedEntries(int row_id, LinkedList<Integer> listChanged, LinkedList<Integer> listAdded, JTable table) {
        //
        boolean cond_1 = listChanged.contains(row_id) == false;
        //
        if (listAdded == null) {
            if (cond_1) {
                listChanged.add(row_id);
                OUT.showMessage("Potentially unsaved row_id added: " + row_id + " / Table: " + table.getName());
            }
        } else if (cond_1 && listAdded.contains(row_id) == false) {
            listChanged.add(row_id);
            OUT.showMessage("Potentially unsaved row_id added: " + row_id + " / Table: " + table.getName());
        }
    }

    public void table3_change_note_name(JTable table) {
        //
        if (HelpA.getIfAnyRowChosen(table) == false) {
            HelpA.showNotification("Please choose a row in the table to perform this action");
            return;
        }
        //
        String recipe_prop_free_text_id = HelpA.getValueSelectedRow(table, "Recipe_Prop_Free_Text_ID");
        //
        if (recipe_prop_free_text_id.equals("null")) {
            HelpA.showNotification("No id for this record, cannot proceed");
            return;
        }
        //
        String noteName = HelpA.getValueSelectedRow(table, "NoteName");
        //
        JTextField jtf = new JTextField(noteName);
        //
        jtf.setPreferredSize(new Dimension(300, 50));
        //
        boolean yes = HelpA.chooseFromJTextField(jtf, "Please specify the new note name");
        //
        String value = jtf.getText();
        //
        if (value == null || value.isEmpty() || yes != true) {
            return;
        }
        //
        UpdateEntry updateEntry = new UpdateEntry(
                "Recipe_Prop_Free_Text",
                "NoteName",
                value,
                "Recipe_Prop_Free_Text_ID",
                recipe_prop_free_text_id,
                true,
                false);
        //
        //
        changeSaver.saveChange(updateEntry);
        //
        recipeInitial.fill_table_2_and_3(); // Refresh
        //
    }

    public void table2_add_new_entry(JTable table) {
        //
        String recipe_id = getRecipeId();
        //
        JTextField jtf1 = new JTextField();
        JTextField jtf2 = new JTextField();
        //
        HelpA.chooseFrom2Textfields(jtf1, jtf2, "Note Name", "Note Value", "Create new note");
        //
        String noteName = jtf1.getText();
        String noteValue = jtf2.getText();
        //
        String q = SQL_A.recipe_detailed_insert_note_table_2(PROC.PROC_07, recipe_id, noteName, noteValue, HelpA.updatedOn(), HelpA.updatedBy());
        //
        try {
            sql.execute(q, mCRecipe2);
        } catch (SQLException ex) {
            Logger.getLogger(RecipeDetailed.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        recipeInitial.fill_table_2_and_3(); // Refresh
        //
    }

    public void table2_change_note_value(JTable table) {
        //
        if (HelpA.getIfAnyRowChosen(table) == false) {
            HelpA.showNotification("Please choose a row in the table to perform this action");
            return;
        }
        //
        String id = HelpA.getValueSelectedRow(table, "Recipe_Prop_Free_Info_ID");
        //
        String noteValue = HelpA.getValueSelectedRow(table, "Note_Value");
        //
        JTextField jtf = new JTextField(noteValue.equals("null") ? "" : noteValue);
        //
        jtf.setPreferredSize(new Dimension(300, 50));
        //
        boolean yes = HelpA.chooseFromJTextField(jtf, "Please specify the new note value");
        //
        String value = jtf.getText();
        //
        if (value == null || yes != true) {
            return;
        }
        //
        if (id.equals("null")) {
            table2_change_notevalue_if_id_missing(table, table.getSelectedRow(), value);
            recipeInitial.fill_table_2_and_3();
            return;
        }
        //
        UpdateEntry updateEntry = new UpdateEntry(
                "Recipe_Prop_Free_Info",
                "NoteValue",
                value,
                "Recipe_Prop_Free_Info_ID",
                id,
                true,
                false);
        //
        //
        changeSaver.saveChange(updateEntry);
        //
        recipeInitial.fill_table_2_and_3(); // Refresh
        //
    }

    private void table2_change_notevalue_if_id_missing(JTable jTable, int row, String noteValue) {
        String recipe_id = getRecipeId();
        String noteName = HelpA.getValueGivenRow(jTable, row, "NoteName");
//        String noteValue = HelpA.getValueGivenRow(jTable, row, "Note_Value");
        //
        String q = SQL_A.recipe_detailed_update_table_2(PROC.PROC_09, recipe_id, noteName, noteValue, HelpA.updatedOn(), HelpA.updatedBy());
        //
        try {
            sql.execute(q, mCRecipe2);
        } catch (SQLException ex) {
            Logger.getLogger(RecipeDetailed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void phrsAndWeights() {
        //
        JTable table = mCRecipe2.jTable4RecipeDetailed;
        //
        for (int row = 0; row < (table.getRowCount()); row++) {
            String phr = (String) table.getValueAt(row, HelpA.getColByName(table, t4_phr));
            String phr_recalc = (String) table.getValueAt(row, HelpA.getColByName(table, "PHR_recalc"));
            //
            HelpA.setValueGivenRow(table, row, t4_phr, phr_recalc);
            HelpA.setValueGivenRow(table, row, "PHR_recalc", phr);
            //
            //
            String weight = (String) table.getValueAt(row, HelpA.getColByName(table, t4_weight));
            String weight_recalc = (String) table.getValueAt(row, HelpA.getColByName(table, "weight_Recalc"));
            //
            HelpA.setValueGivenRow(table, row, t4_weight, weight_recalc);
            HelpA.setValueGivenRow(table, row, "weight_Recalc", weight);
        }
        //
    }

    private void paint_selected_rows(final LinkedList<IngredientToDelete> ingredientsToDelete, final JTable jTable, final Color color) {
        jTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                setBackground(null);
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                for (IngredientToDelete ingred : ingredientsToDelete) {
                    if (row == HelpA.getRowByValue(table, "Id", "" + ingred.getRowId())) {
                        c.setBackground(color);
                        return c;
                    }
                }

                return this;
            }
        });
        //
        jTable.repaint();
    }

    private boolean rowToDeleteExists(int row) {
        for (IngredientToDelete ingred : ingredientsToDelete_table_4) {
            if (ingred.getRowId() == row) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void fillNotes() {
        mCRecipe2.clickedOnTable1RecipeInitial();
        mCRecipe2.recipeDetailedTabbClicked();
    }

    public class IngredientToDelete {

        private int row_id;
        private final String recipe_recipe_id;

        public IngredientToDelete(int row, String recipe_recipe_id) {
            this.row_id = row;
            this.recipe_recipe_id = recipe_recipe_id;
        }

        public String getRecipe_recipe_id() {
            return recipe_recipe_id;
        }

        public void setRowId(int row) {
            this.row_id = row;
        }

        public int getRowId() {
            return row_id;
        }
    }

    private void setBtnsDisabled(boolean disabled) {
        mCRecipe2.jButton_Recipe_Detailed_Save_Invert.setEnabled(disabled);
        mCRecipe2.jButton3.setEnabled(disabled);
        mCRecipe2.jButton7.setEnabled(disabled);
        mCRecipe2.jButtonRecipeDetailedSaveTable4.setEnabled(disabled);
        //
        mCRecipe2.jButton2.setEnabled(disabled);
        mCRecipe2.jButton5.setEnabled(disabled);
        mCRecipe2.jButton4.setEnabled(disabled);
        //
        mCRecipe2.jButton6.setEnabled(disabled);
        mCRecipe2.jButtonRDetailedChangeNoteName.setEnabled(disabled);
        mCRecipe2.jButtonRecipeDetailedAddNote.setEnabled(disabled);
        //
        mCRecipe2.jButton10.setEnabled(disabled);
        mCRecipe2.jButton11.setEnabled(disabled);
    }
}
