/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import MyObjectTableInvert.BasicTab;
import MyObjectTableInvert.RowDataInvert;
import forall.HelpA;
import forall.SqlBasicLocal;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.rtf.RTFEditorKit;

/**
 *
 * @author KOCMOC
 */
public class RecipeInitial extends BasicTab {

    private final MC_RECIPE mCRecipe2;
    private CompareRecipes compareRecipes;
    public static final String T1_RECIPE_VERSION = "Recipe Version";
    public static final String T1_RECIPE_ID = "Recipe_ID";
    public static final String T1_RECIPE_ADDITIONAL = "Recipe_Additional";
    public static final String T1_MIXER_CODE = "Mixer_Code";
    public static final String T1_LOAD_FACTOR = "Loadfactor";
    public static final String T1_STATUS = "Status";
    public static final String T1_RECIPE_ORIGIN = "Recipe_Origin";
    public static final String T1_RECIPE_STAGE = "Recipe Stage";
    public static final String T1_MIX_TIME = "MixTime";
    public static final String T1_DESCRIPTION = "Descr";
    public static final String T1_DETAILED_GROUP = "Detailed_Group";
    public static final String T1_CLASS = "Class";
    public static final String T1_UPDATED_ON = "UpdatedOn";
    public static final String T1_UPDATED_BY = "UpdatedBy";
    private LinkedList<Integer> ADDED_TO_COMPARE_LIST = new LinkedList<Integer>();

    public RecipeInitial(MC_RECIPE mCRecipe2, SqlBasicLocal sql) {
        super(sql, sql, mCRecipe2);
        this.mCRecipe2 = mCRecipe2;
        go();
    }

    private void go() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                mCRecipe2.addJComboListenersRecipeInitial();
                mCRecipe2.addUpperSearchCriteriaToListRecipeInitial();
                fill_table_1(null, null, null, null);
                HelpA.markGivenRow(mCRecipe2.jTable1, 0);
                mCRecipe2.clickedOnTable1RecipeInitial();
            }
        });
    }

    public void table1Repport() {
        tableCommonExportOrRepport(mCRecipe2.jTable1, true);
    }

    public void showIngredInfoOnValueChange(String ingredName) {
        String q = SQL_A.recipe_initial_build_info_table(ingredName);
        //
        JTable table = mCRecipe2.jTableIngredientInfoTable;
        //
        try {
            ResultSet rs = sql.execute(q);// no need to have output here, to avoid flooding
            HelpA.build_table_common(rs, mCRecipe2.jTableIngredientInfoTable, q);
        } catch (SQLException ex) {
            Logger.getLogger(RecipeInitial.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        HelpA.setColumnWidthByIndex(0, table, 0.35);
        HelpA.setColumnWidthByIndex(1, table, 0.5);
        HelpA.setColumnWidthByIndex(2, table, 0.15);
        //
    }

    public void setCompareRecipes(CompareRecipes compareRecipes) {
        this.compareRecipes = compareRecipes;
    }

    public void remoweAllFromCompare() {
        //
        if (HelpA.confirm() == false) {
            return;
        }
        //
        dropCompareTable();
        unpaintAllRows_a(mCRecipe2.jTable1);
        ADDED_TO_COMPARE_LIST = new LinkedList<Integer>();
    }

    public void addToCompare() {
        //
        if (ADDED_TO_COMPARE_LIST.size() >= 6) {
            HelpA.showNotification("Maximum of 6 recipes reached");
            return;
        }
        //
        JTable table1 = mCRecipe2.jTable1;
        //
        String recipeVersion = HelpA.getValueSelectedRow(table1, T1_RECIPE_VERSION);
        String recipeAdditional = HelpA.getValueSelectedRow(table1, T1_RECIPE_ADDITIONAL);
        //
        boolean x = compareRecipes.addToCompare(recipeVersion, recipeAdditional, HelpA.updatedBy());
//        System.out.println("ADD TO COMPARE: " + x);
        //
        if (x == false) {
            HelpA.showNotification("No records found for recipe: " + recipeVersion);
            return;
        }
        //
        ADDED_TO_COMPARE_LIST.add(table1.getSelectedRow());
        paint_selected_rows_a(ADDED_TO_COMPARE_LIST, table1, Color.lightGray);
    }

    public void showComparedRecipes() {
        //
        if (compareRecipes == null || ADDED_TO_COMPARE_LIST.size() == 0) {
            return;
        }
        //
        compareRecipes.fillAndShow();
    }

    public void dropCompareTable() {
        compareRecipes.dropCompareTable(HelpA.updatedBy());
    }

    public boolean checkIfRecipeDisabled() {
        //
        String status = HelpA.getValueSelectedRow(mCRecipe2.jTable1, T1_STATUS);
        //
        boolean cond_1 = status.equals("S") || status.equals("O") || status.equals("Inactive");
        //
        if (cond_1) {
            return true;
        } else {
            return false;
        }
        //
    }

    public void delete_record_table_1(JTable table) {
        //
        if (checkIfRecipeDisabled()) {
            HelpA.showNotification("Cannot delete Recipe with status S, O, Inactive");
            return;
        }
        //
        if (HelpA.confirm() == false) {
            return;
        }
        //
        String recipeID = HelpA.getValueSelectedRow(table, T1_RECIPE_ID);
        //
        String code = HelpA.getValueSelectedRow(table, T1_RECIPE_VERSION);
        String release = HelpA.getValueSelectedRow(table, T1_RECIPE_ADDITIONAL);
        String mixer_code = HelpA.getValueSelectedRow(table, T1_MIXER_CODE);
        //
        try {
            //
            ResultSet rs = sql.execute(SQL_B.get_recipe_sequence_main_id(code, release, mixer_code), OUT);
            if (rs.next()) {
                int recipe_seq_main_id = rs.getInt("Recipe_Sequence_Main_ID");
                sql.execute(SQL_B.delete_record_table_1(recipeID, recipe_seq_main_id), OUT);
            } else {
                sql.execute(SQL_B.delete_record_table_1(recipeID, HelpA.getColByName(table, T1_RECIPE_VERSION)), OUT);
            }
            //
//            fill_table_1(null, null, null, null);
//            HelpA.markGivenRow(mCRecipe2.jTable1, 0);
//            mCRecipe2.clickedOnTable1RecipeInitial();
            //
            int row = table.getSelectedRow();
            HelpA.removeRowJTable(table, row);
            HelpA.markGivenRow(table, row);
            //
            mCRecipe2.clickedOnTable1RecipeInitial();
            mCRecipe2.recipeDetailedTabbClicked();
            //
        } catch (SQLException ex) {
            Logger.getLogger(RecipeInitial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete_record_table_2_or_3(JTable table, int row_index, String tableName, String idColumnName) {
        //
        if (HelpA.confirm() == false) {
            return;
        }
        //
        int currRow = table.getSelectedRow();
        //
        String recipe_Prop_Free_Info_ID = (String) table.getValueAt(currRow, row_index);
        //
        try {
            String q = SQL_B.delete_record_table_2_or_3(recipe_Prop_Free_Info_ID, tableName, idColumnName);
            //
            sql.execute(q, OUT);
            //
            fill_table_2_and_3();
            //
//            OUT.showMessage("Delete OK: " + recipe_Prop_Free_Info_ID);
        } catch (SQLException ex) {
            Logger.getLogger(RecipeInitial.class.getName()).log(Level.SEVERE, null, ex);
//            OUT.showMessage("Delete Failed: " + recipe_Prop_Free_Info_ID);
        }
    }

    public void fill_table_2_and_3() {
        //
        JTable table1 = mCRecipe2.jTable1;
        JTable table2 = mCRecipe2.jTable2;
        JTable table3 = mCRecipe2.jTable3;
        //
        String recipeVersion = HelpA.getValueSelectedRow(table1, T1_RECIPE_VERSION);
        String recipeAdditional = HelpA.getValueSelectedRow(table1, T1_RECIPE_ADDITIONAL);
        String mixerCode = HelpA.getValueSelectedRow(table1, T1_MIXER_CODE);
        //
        String q = SQL_A.fillTable2RecipeInitial(recipeVersion, recipeAdditional, mixerCode);
        fill_table_2_3__(q, table2);
        //
        q = SQL_A.fillTable3RecipeInitial(recipeVersion, recipeAdditional, mixerCode);
        fill_table_2_3__(q, table3);
        //
        //
        HelpA.hideColumnByName(table2, "Recipe_Version");
        HelpA.hideColumnByName(table2, "Recipe_Addditional");
        HelpA.hideColumnByName(table2, "Mixer_Code");
        HelpA.hideColumnByName(table2, "UpdatedOn");
        HelpA.hideColumnByName(table2, "UpdatedBy");
        HelpA.hideColumnByName(table2, "Recipe_Prop_Free_Info_ID");
        //
        HelpA.moveRowTo(table2, "NoteName", "Color:", 0);
        HelpA.moveRowTo(table2, "NoteName", "Hardnes Sha:", 1);
        HelpA.moveRowTo(table2, "NoteName", "recept type:", 2);
        //        HelpA.moveRowTo(table2, "NoteName:", "Polymer Blend:", 1); // the comented ones are to be added later when the exist
        //        HelpA.moveRowTo(table2, "NoteName:", "IP:", 1);
        HelpA.moveRowTo(table2, "NoteName", "curing system:", 3);
        HelpA.moveRowTo(table2, "NoteName", "filler:", 4);
        //        HelpA.moveRowTo(table2, "NoteName:", "Approval:", 1);
        //        HelpA.moveRowTo(table2, "NoteName:", "Food Grade:", 1);
        HelpA.moveRowTo(table2, "NoteName", "certificate:", 5);
        HelpA.moveRowTo(table2, "NoteName", "schelflife(weeks):", 6);
        //        HelpA.moveRowTo(table2, "NoteName:", "Delivery Form:", 1);
        //        HelpA.moveRowTo(table2, "NoteName:", "Packaging:", 1);
        HelpA.moveRowTo(table2, "NoteName", "curing system:", 7);
        HelpA.moveRowTo(table2, "NoteName", "industry:", 8);
        //
        HelpA.changeTableHeaderTitleOfOneColumn(table2, "NoteName", "Name");
        HelpA.changeTableHeaderTitleOfOneColumn(table2, "Note_Value", "Value");
        //
        //
        HelpA.hideColumnByName(table3, "Recipe_Version");
        HelpA.hideColumnByName(table3, "Recipe_Addditional");
        HelpA.hideColumnByName(table3, "Mixer_Code");
        HelpA.hideColumnByName(table3, "UpdatedOn");
        HelpA.hideColumnByName(table3, "UpdatedBy");
        HelpA.hideColumnByName(table3, "Recipe_Prop_Free_Text_ID");
        //
        HelpA.changeTableHeaderTitleOfOneColumn(table3, "NoteName", "Name");
        HelpA.changeTableHeaderTitleOfOneColumn(table3, "NoteValue", "Value");
        //
        //Just to look better visually
        for (int i = 0; i < 13; i++) {
            HelpA.addRowJTable(table3);
        }
        //
    }

    private void fill_table_2_3__(String q, JTable table) {
        try {
            //
            ResultSet rs = sql.execute(q, OUT);
            //
            HelpA.build_table_common(rs, table, q);
            //
        } catch (SQLException ex) {
            Logger.getLogger(RecipeInitial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void fillNotes() {
        //
        //OBS!
        if (mCRecipe2.jEditorPaneRecipeInitialNotes.getEditorKit() instanceof RTFEditorKit == false) {
            mCRecipe2.jEditorPaneRecipeInitialNotes.setEditorKit(new RTFEditorKit());
            //
        }
        //
        JTable table3 = mCRecipe2.jTable3;
        //
        String noteValue = HelpA.getValueSelectedRow(table3, "NoteValue");
        //
        if (noteValue == null || noteValue.equals("null")) {
            noteValue = "";
        }
        //
        if (noteValue.contains("fs16") || noteValue.contains("rtf1")) {
            //
            replaceComponent(mCRecipe2.jScrollPaneRecipeInitialNotes.getViewport(), mCRecipe2.jScrollPaneRecipeInitialNotes,
                    mCRecipe2.textAreaRecipeInitialNotes, mCRecipe2.jEditorPaneRecipeInitialNotes);
            //
//            noteValue = noteValue.replace("fs16", "fs35");
            noteValue = HelpM.resizeTextRtf(noteValue);
            mCRecipe2.jEditorPaneRecipeInitialNotes.setText(noteValue);
            //
        } else {
            //
            replaceComponent(mCRecipe2.jScrollPaneRecipeInitialNotes.getViewport(), mCRecipe2.jScrollPaneRecipeInitialNotes,
                    mCRecipe2.jEditorPaneRecipeInitialNotes, mCRecipe2.textAreaRecipeInitialNotes);
            //
            //
            mCRecipe2.textAreaRecipeInitialNotes.setText(noteValue);
        }
    }

    public void unpaintTable1() {
        unpaintAllRows_a(mCRecipe2.jTable1);
    }

    public void fill_table_1(String recipe_code, String release, String mixer_code, ActionEvent event) {
        //
        unpaintTable1();
        //
        String[] params = getComboParams(event);
        //
        //
        if (recipe_code != null && recipe_code.isEmpty() == false) {
            params[2] = recipe_code; //params[2] = recipeVersion
            params[1] = release; //params[1] = recipeAdditional
            params[7] = mixer_code;//params[7] = mixerCode
        }
        //
        //
        String q = "";
        //
        try {
            //
            if (event != null && event.getSource() == mCRecipe2.jButtonRecipeInitialGo2) {// Sort by note value
                q = SQL_A.recipeInitialBuildTable1_B(params);
                HelpA.runProcedureIntegerReturn_A(sql.getConnection(), q);
                fillTable1HelpM();
                OUT.showMessage(q);
            } else {
                q = SQL_A.recipeInitialBuildTable1(params);
                HelpA.runProcedureIntegerReturn_A(sql.getConnection(), q);
                fillTable1HelpM();
                OUT.showMessage(q);
            }
            //
        } catch (SQLException ex) {
            Logger.getLogger(RecipeInitial.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        if (event != null && event.getSource() != null) {
            HelpA.setTrackingToolTip((JComponent) event.getSource(), q);
        }
        //

    }

    private void fillTable1HelpM() {
        //
        String q = SQL_A.recipe_initial_main();
        //
        JTable table = mCRecipe2.jTable1;
        try {
            ResultSet rs = sql.execute(q, OUT);
            HelpA.build_table_common(rs, table, q);
        } catch (SQLException ex) {
            Logger.getLogger(RecipeInitial.class.getName()).log(Level.SEVERE, null, ex);
        }
        //OBS! Remember that Titles are not the same as "Real" Sql column names 
        // so even when i change the titles they will still have the "Real" column names
        HelpA.changeTableHeaderTitleOfOneColumn(table, T1_RECIPE_ADDITIONAL, "Add.");
        HelpA.changeTableHeaderTitleOfOneColumn(table, T1_DESCRIPTION, "Description");
        HelpA.changeTableHeaderTitleOfOneColumn(table, T1_DETAILED_GROUP, "Group");
        HelpA.changeTableHeaderTitleOfOneColumn(table, T1_MIXER_CODE, "Mixer");
        HelpA.changeTableHeaderTitleOfOneColumn(table, T1_UPDATED_ON, "Updated On");
        HelpA.changeTableHeaderTitleOfOneColumn(table, T1_UPDATED_BY, "Updated By");
        //
        HelpA.hideColumnByName(table, T1_RECIPE_ID);
        HelpA.hideColumnByName(table, T1_RECIPE_ORIGIN);
        HelpA.hideColumnByName(table, T1_RECIPE_STAGE);
        HelpA.hideColumnByName(table, T1_LOAD_FACTOR);
        HelpA.hideColumnByName(table, T1_MIX_TIME);
    }

    private String[] getComboParamsA() {
        String recipeOrigin = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBox1_Recipe_Origin);
        String detailedGroup = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBox2_Detailed_Group);
        String recipeStage = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBox3_Recipe_Stage);
        String mixerCode = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBox4_Mixer_Code);
        String recipeVersion = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBox5_Recipe_Version);
        String status = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBox6_Status);
        String recipeAdditional = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBox7_RecipeAdditional);
        String class_ = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBox8_Class);
        String description = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBox_Description1);
        //
        String color = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBoxRecipeInitial_Color);
        String industry = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBoxRecipeInitial_Industry);
        String recipeType = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBoxRecipeInitial_Recipe_type);
        String curingSystem = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBoxRecipeInitial_CuringSystem);
        String curingProcess = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBoxRecipeInitial_CuringProcess);
        String filler = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBoxRecipeInitial_Filler);
        String certificat = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBoxRecipeInitial_Certificat);
        String shelflife1 = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBoxRecipeInitial_Shelflife1);
        String shelflife2 = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBoxRecipeInitial_Shelflife2);
        String hardnessSha1 = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBoxRecipeInitial_Hardnes_sha1);
        String hardnessSha2 = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBoxRecipeInitial_Hardnes_sha2);
        String customer = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBoxRecipeInitial_Customer);
        //
        return new String[]{recipeOrigin, recipeAdditional, recipeVersion,
            recipeStage, detailedGroup, status, class_, mixerCode, description,
            color, industry, recipeType, curingSystem, curingProcess,
            filler, certificat, shelflife1, shelflife2, hardnessSha1, hardnessSha2, customer
        };
    }

    private String[] getComboParamsB() {
        String[] comboParams = getComboParamsA();
        //
        String[] comboParamsB = new String[comboParams.length + 2];
        //
        String ingred_1 = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBox_Ingred_1);
        String ingred_2 = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBox_Ingred_2);
        //
        System.arraycopy(comboParams, 0, comboParamsB, 0, comboParams.length);
        //
        comboParamsB[comboParamsB.length - 2] = ingred_1;
        comboParamsB[comboParamsB.length - 1] = ingred_2;
        //
        return comboParamsB;
    }

    private String[] getComboParams(ActionEvent event) {
        //
        if (event != null && event.getSource() == mCRecipe2.jButtonRecipeInitialGo2) {
            return getComboParamsA();
        }
        //
        boolean checkedIngreds = MC_RECIPE.jCheckBoxRecipeInitialSearchByIngredients.isSelected(); 
        boolean boxesEmpty = upperSearchCriteriasEmpty() == true;
        boolean checkedOr = MC_RECIPE.jCheckBoxRecipeInitialOR.isSelected();
        //
//        boolean cond_2 = mCRecipe2.jComboBox_Ingred_1.getSelectedItem() == null
//                && mCRecipe2.jComboBox_Ingred_2.getSelectedItem() == null;
        //
        if (checkedIngreds && boxesEmpty == false) {
//            OUT.showMessage("-----------------------------------------> 23 PARAMS Recipes_Z_X");
            return getComboParamsB();
        } else {
//            OUT.showMessage("-----------------------------------------> 21 PARAMS (Recipes_Z_X_IngredName)");
            return getComboParamsA(); // not selected
        }
    }

    /**
     * @deprecated - is not used, but possibly can be used i future
     * @param box
     */
    public void allowIngredSearch(JCheckBox box) {
        boolean condition_2_3 = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBox5_Recipe_Version).equals("NULL");
        boolean condition_2_4 = HelpA.getComboBoxSelectedValue(mCRecipe2.jComboBox1_Recipe_Origin).equals("NULL");
        //
        if (box.isSelected()) {
            if (condition_2_3 && condition_2_4) {
                System.out.println("NOT Allowed");
                HelpA.showNotification("RECIPE ORIGIN or RECIPE VERSION must be chosen before using this option!");
                box.setSelected(false);
            }
        }
    }

    /**
     * If unchecked, Ingred comboboxes should not be filled
     *
     * @param box
     */
    private boolean checkIfToFill(JComboBox box) {
        //
        boolean condition_1 = MC_RECIPE.jCheckBoxRecipeInitialSearchByIngredients.isSelected();
        boolean condition_2 = box == mCRecipe2.jComboBox_Ingred_1;
        boolean condition_2_2 = box == mCRecipe2.jComboBox_Ingred_2;
        //
        if ((condition_1 == false && condition_2) || (condition_1 == false && condition_2_2)) {
            return false;
        } else {
            return true;
        }
    }

    public void fillComboBoxMultiple(final JComboBox box, final String colName, final String colName2) {
        //
        if (checkIfToFill(box) == false) {
            return;
        }
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Object selection = box.getSelectedItem();
                //
                //
                String q = SQL_A.fill_comboboxes_recipe_initial_multiple(colName, colName2, getComboParams(null));
                OUT.showMessage(q);
                //
                HelpA.fillComboBox(sql, box, q, null, true, false);
                // box.setBorder(BorderFactory.createLineBorder(Color.green));
                //
                box.setSelectedItem(selection);
            }
        });
    }

    public boolean upperSearchCriteriasEmpty() {
        //
        for (JComboBox box : mCRecipe2.upperSearchListRecipeInitial) {
            //
            if (box.getSelectedItem() != null) {
                return false;
            }
            //
        }
        //
        return true;
    }
    //
    private long flagWait;

    /**
     * Not used so far
     *
     * @param box
     * @param colName
     */
    public void fillComboBoxIngredients_with_wait(final JComboBox box, final String colName) {
        //
        if (checkIfToFill(box) == false) {
            return;
        }
        //
        if (HelpA.fillAllowedComboBox(flagWait) == false) {
//            System.out.println("Fill not allowed");
            return;
        } else {
            flagWait = 0;
        }
        //
        Object selection = box.getSelectedItem();
        //
        boolean boxesEmpty = upperSearchCriteriasEmpty();
        //
        String q = SQL_A.fill_comboboxes_recipe_initial_b(colName, getComboParams(null));
        OUT.showMessage(q);
        //
        //
        HelpA.fillComboBox(sql, box, q, null, false, false);
        //
        box.showPopup();
        //
        flagWait = System.currentTimeMillis();
        //
        box.setSelectedItem(selection);

    }

    public void fillComboBox(final JComboBox box, final String colName) {
        //
        if (checkIfToFill(box) == false) {
            return;
        }
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Object selection = box.getSelectedItem();
                //
                //
                String q = SQL_A.fill_comboboxes_recipe_initial(colName, getComboParams(null), mCRecipe2);
                OUT.showMessage(q);
                //
                HelpA.fillComboBox(sql, box, q, null, false, false);
                // box.setBorder(BorderFactory.createLineBorder(Color.green));
                //
                box.setSelectedItem(selection);
            }
        });
    }

    public void fillComboBoxB(final JComboBox box, final String colName) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //
                Object selection = box.getSelectedItem();
                //
                //
                String q = SQL_A.fill_comboboxes_recipe_initial_B(colName);
                OUT.showMessage(q);
                HelpA.fillComboBox(sql, box, q, null, false, false);
                //
                box.setSelectedItem(selection);
            }
        });
    }

    public void fillComboBoxCustomer(final JComboBox box, final String colName) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //
                Object selection = box.getSelectedItem();
                //
                //
                String q = SQL_A.fill_combobox_recipe_initial_customer(colName);
                OUT.showMessage(q);
                HelpA.fillComboBox(sql, box, q, null, false, false);
                //
                box.setSelectedItem(selection);
            }
        });
    }

    public void clearBoxes() {
        //
        mCRecipe2.jComboBox1_Recipe_Origin.setSelectedItem(null);
        mCRecipe2.jComboBox3_Recipe_Stage.setSelectedItem(null);
        mCRecipe2.jComboBox5_Recipe_Version.setSelectedItem(null);
        mCRecipe2.jComboBox7_RecipeAdditional.setSelectedItem(null);
        mCRecipe2.jComboBox2_Detailed_Group.setSelectedItem(null);
        mCRecipe2.jComboBox4_Mixer_Code.setSelectedItem(null);
        mCRecipe2.jComboBox6_Status.setSelectedItem(null);
        mCRecipe2.jComboBox8_Class.setSelectedItem(null);
        mCRecipe2.jComboBox_Description1.setSelectedItem(null);
        mCRecipe2.jComboBox_Ingred_1.setSelectedItem(null);
        mCRecipe2.jComboBox_Ingred_2.setSelectedItem(null);
        //
        mCRecipe2.jComboBox1_Recipe_Origin.setEditable(false);
        mCRecipe2.jComboBox3_Recipe_Stage.setEditable(false);
        mCRecipe2.jComboBox5_Recipe_Version.setEditable(false);
        mCRecipe2.jComboBox7_RecipeAdditional.setEditable(false);
        mCRecipe2.jComboBox2_Detailed_Group.setEditable(false);
        mCRecipe2.jComboBox4_Mixer_Code.setEditable(false);
        mCRecipe2.jComboBox6_Status.setEditable(false);
        mCRecipe2.jComboBox8_Class.setEditable(false);
        mCRecipe2.jComboBox_Description1.setEditable(false);
        mCRecipe2.jComboBox_Ingred_1.setEditable(false);
        mCRecipe2.jComboBox_Ingred_2.setEditable(false);
        //
        MC_RECIPE.jCheckBoxRecipeInitialSearchByIngredients.setSelected(false);
        MC_RECIPE.jCheckBoxRecipeInitialOR.setSelected(false);
        //
        //
        HelpA.clearAllRowsJTable(mCRecipe2.jTableIngredientInfoTable);
    }

    public void clearBoxesB() {
        mCRecipe2.jComboBoxRecipeInitial_Color.setSelectedItem(null);
        mCRecipe2.jComboBoxRecipeInitial_Industry.setSelectedItem(null);
        mCRecipe2.jComboBoxRecipeInitial_Recipe_type.setSelectedItem(null);
        mCRecipe2.jComboBoxRecipeInitial_CuringSystem.setSelectedItem(null);
        mCRecipe2.jComboBoxRecipeInitial_CuringProcess.setSelectedItem(null);
        mCRecipe2.jComboBoxRecipeInitial_Filler.setSelectedItem(null);
        mCRecipe2.jComboBoxRecipeInitial_Certificat.setSelectedItem(null);
        mCRecipe2.jComboBoxRecipeInitial_Shelflife1.setSelectedItem(null);
        mCRecipe2.jComboBoxRecipeInitial_Shelflife2.setSelectedItem(null);
        mCRecipe2.jComboBoxRecipeInitial_Hardnes_sha1.setSelectedItem(null);
        mCRecipe2.jComboBoxRecipeInitial_Hardnes_sha2.setSelectedItem(null);
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
}
