/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import MCRecipe.Sec.HelpM;
import MCRecipe.Lang.INGR;
import MCRecipe.Lang.JTB;
import MCRecipe.Lang.LNG;
import MCRecipe.Lang.REGEX;
import MCRecipe.Lang.T_INV;
import MCRecipe.Sec.PROC;
import MyObjectTableInvert.BasicTab;
import images.IconUrls;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTable.SaveIndicator;
import forall.HelpA;
import forall.JComboBoxA;
import forall.SqlBasicLocal;
import forall.TextFieldCheck;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.text.rtf.RTFEditorKit;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import mySwing.JTableM;

/**
 *
 * @author KOCMOC
 */
public class Ingredients extends BasicTab {

    private final MC_RECIPE mCRecipe;
    private TableBuilderInvert TABLE_BUILDER_INVERT;
    protected LinkedList<Integer> unsavedRows_table_3 = new LinkedList<Integer>();
    protected LinkedList<Integer> addedRows_table_3 = new LinkedList<Integer>();
    public boolean table1Build = false;
    public static String INGRED_NAME;

    public Ingredients(SqlBasicLocal sql, SqlBasicLocal sql_additional, MC_RECIPE mCRecipe) {
        super(sql, sql_additional, mCRecipe);
        this.mCRecipe = mCRecipe;
        //
        go();
        initializeSaveIndicators();
    }

    public void table1Repport() {
        tableInvertExportOrRepport(TABLE_INVERT, 2, getConfigTableInvert());
    }

    public void table2Repport() {
        tableCommonExportOrRepport(mCRecipe.jTable_Ingred_Table1, false);
    }

    @Override
    public void initializeSaveIndicators() {
        SaveIndicator saveIndicator = new SaveIndicator(mCRecipe.jButton_Ingredients_Save_Invert, this, 1);
        SaveIndicator saveIndicator2 = new SaveIndicator(mCRecipe.jButton_Ingredients_Save_Table3, this, 2);
        SaveIndicator saveIndicator3 = new SaveIndicator(mCRecipe.jButton_Ingredients_Save_Comments, this, 3);
    }

    @Override
    public boolean getUnsaved(int nr) {
        if (nr == 1) {
            //
            if (TABLE_INVERT == null) {
                return false;
            } else if (unsavedEntriesExist(TABLE_INVERT)) {
                return true;
            }
            //
        } else if (nr == 2) {
            if (unsavedRows_table_3.isEmpty() == false || addedRows_table_3.isEmpty() == false) {
                return true;
            }
        } else if (nr == 3) {
            return notesUnsaved;
        }
        return false;
    }

    public void saveTableInvert() {
        //
        if (getGroup().equals("NULL")) {
            HelpA.showNotification("Cannot proceed, GROUP must be chosen");
            return;
        }
        //
        saveChangesTableInvert();
        refreshTable1AfterSaving();
    }

    private void go() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //
                mCRecipe.ingredients_GroupA_Boxes_to_list();
                mCRecipe.addJComboListenersIngredients();
                //
                fill_table_1(INGRED_NAME);
                INGRED_NAME = null;
                //
                simulateClickOnTable1();
            }
        });
    }

    public void refreshTable1AfterSaving() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                fill_table_1(getIngredCode());// this to renew the list after saving
            }
        });
    }

    public void simulateClickOnTable1() {
        HelpA.markGivenRow(mCRecipe.jTable_Ingred_Table1, 0);
        mCRecipe.clickedTable1Ingredients();
    }

    public String getGroup() {
        return getValueTableInvert("Group", 2, TABLE_INVERT);
    }

    public String getIngredCode() {
        return getValueTableInvert("Name", 2, TABLE_INVERT);
    }

    public String getIngredCodeId() {
        return getValueTableInvert("IngredientCode_ID", 2, TABLE_INVERT);
    }

    private String getIngredCodeIdByName() {
        //
        String q = SQL_B.getIngredCodeIdByName(getIngredCode());
        //
        try {
            ResultSet rs = sql.execute(q, mCRecipe);
            if (rs.next()) {
                return rs.getString("IngredientCode_ID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return null;
    }

    public boolean deleteIngredient() {
        //
        if (HelpA.confirm() == false) {
            return false;
        }
        //
        String ingredCode = getIngredCode();
        //
        if (ingredCode == null || ingredCode.isEmpty()) {
            return false;
        }
        //
        String q = SQL_A.ingredients_delete_ingred(PROC.PROC_65, ingredCode);
        //
        try {
            sql.execute(q, mCRecipe);
            //
            updateTables(null);
            //
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void addIngredientToTable4RecipeDetailed() {
        //
        if (HelpA.confirm(INGR.CINFIRM_MSG_1()) == false) {
            return;
        }
        //
        if (mCRecipe.recipeInitial.checkIfRecipeDisabled()) {
            HelpA.showNotification("Cannot do this action for Recipe with status S, O, Inactive");
        }
        //
        RecipeDetailed rd = mCRecipe.recipeDetailed;
        //
        String ingredCode = getIngredCode();
        //
        if (rd == null) {
            return;
        }
        //
        rd.add_ingredient_table_4(ingredCode);
        //
        HelpA.openTabByName(mCRecipe.jTabbedPane1, LNG.RECIPE_DETAILED_TAB);
        //
        mCRecipe.recipeDetailedTabbClicked();
        //
        JTable table = mCRecipe.jTable4RecipeDetailed;
        //
        HelpA.markGivenRow(table, HelpA.getRowByValue(table, RecipeDetailed.t4_material, ingredCode));
    }

    public void setIngredComboBoxRecipeInitial(JComboBox box) {
        String ingred = getIngredCode();
        //
        if (ingred == null || ingred.isEmpty()) {
            return;
        }
        //
        MC_RECIPE.jCheckBoxRecipeInitialSearchByIngredients.setSelected(true);
        box.addItem(ingred);
        box.setSelectedItem(ingred);
        HelpA.openTabByName(mCRecipe.jTabbedPane1, LNG.RECIPE_INITIAL_TAB);
        mCRecipe.recipeInitialTabClicked();
    }
    //
    //
    private String ingredient_code_update;

    private boolean updateIngredientName() {
        //
        String q = "select Name from Ingredient_Code where Name = ?";
        //
        TextFieldCheck tfc = new TextFieldCheck(sql, q, REGEX.INGRED_REGEX, 20);
        //
        boolean yesNo = HelpA.chooseFromJTextFieldWithCheck(tfc, "Specify Ingredient Code, format: " + REGEX.INGRED_REGEX_DESCR);
        ingredient_code_update = tfc.getText();
        //
        if (ingredient_code_update == null || yesNo == false) {
            //
            return false;
            //
        }
        //
        String q2 = SQL_A.ingredient_update_ingred_name(ingredient_code_update);
        //
        try {
            sql.execute(q2, mCRecipe);
        } catch (SQLException ex) {
            Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //
        updateTables(ingredient_code_update);
        //
        return true;
    }

    public void addIngredient(boolean fromScratch) {
        //
        if (getIngredCode().trim().equals("NEW")) {
            return;
        }
        //
        String q;
        //
        if (fromScratch) {
            q = SQL_A.ingredients_add_new_ingredient_scratch(PROC.PROC_10, getIngredCodeIdByName(), HelpA.updatedOn(), HelpA.updatedBy());
        } else {
            q = SQL_A.ingredients_add_new_ingredient(PROC.PROC_11, getIngredCodeIdByName(), HelpA.updatedOn(), HelpA.updatedBy());
        }
        //
        try {
            sql.execute(q, mCRecipe);
        } catch (SQLException ex) {
            Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        if (updateIngredientName() == false) {
            return;
        }
        //
        if (fromScratch) {
            clearRows(TABLE_INVERT, 1, 4);
        }
        //
    }

    private void updateTables(String ingredCode) {
        //
        fill_table_1(SQL_A.quotes(ingredCode, false));
        //
        JTable table = mCRecipe.jTable_Ingred_Table1;
        //
        HelpA.markFirstRowJtable(table);
        //
        simulateClickOnTable1();
        //
    }

    public void delete_from_table_3() {
        //
        if (HelpA.confirm() == false) {
            return;
        }
        //
        int selected_row = mCRecipe.jTable_Ingred_Table3.getSelectedRow();
        //
        String ingred_free_info_id = HelpA.getValueGivenRow(mCRecipe.jTable_Ingred_Table3, selected_row, "Ingred_Free_Info_ID");
        //
        try {
            sql.execute(SQL_A.delete_from_ingred_table_3(PROC.PROC_44, ingred_free_info_id), mCRecipe);
        } catch (SQLException ex) {
            Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        fill_tables_2_and_3();
    }

    public void addToTable3() {
        //
        JTable table = mCRecipe.jTable_Ingred_Table3;
        //
        addRow(table, addedRows_table_3);
        //
        HelpA.markLastRowJtable(mCRecipe.jTable_Ingred_Table3);
        //
        int selectedRow = table.getSelectedRow();
        //
        //
        JTextField jtf1_note_name = new JTextField();
        JTextField jtf2_note_value = new JTextField();
        //
        HelpA.chooseFrom2Textfields(jtf1_note_name, jtf2_note_value, "NoteName", "NoteValue", "Specify NoteName & NoteValue");
        //
        //
        if (jtf1_note_name.getText().isEmpty() || jtf2_note_value.getText().isEmpty()) {
            HelpA.showNotification("Both values must be specified!");
            return;
        }
        //
        HelpA.setValueGivenRow(table, selectedRow, "NoteName", jtf1_note_name.getText());
        HelpA.setValueGivenRow(table, selectedRow, "NoteValue", jtf2_note_value.getText());
        //
        HelpA.setValueGivenRow(table, selectedRow, "IngredientCode_ID", getIngredCodeIdByName());
        //
        HelpA.setValueGivenRow(table, selectedRow, "Name", getIngredCode());
        //
        HelpA.setValueGivenRow(table, selectedRow, "UpdatedOn", HelpA.updatedOn());
        //
        save_changes_table_3();
    }

    /**
     * This one handles added rows to
     */
    public void save_changes_table_3() {
        //
        JTable table3 = mCRecipe.jTable_Ingred_Table3;
        //
        HelpA.stopEditJTable(table3);
        //
        for (Integer row : unsavedRows_table_3) {
            //
            String ingred_free_info_id = HelpA.getValueGivenRow(table3, row, "Ingred_Free_Info_ID");
            String ingred_code = HelpA.getValueGivenRow(table3, row, "IngredientCode_ID");
            String note_name = HelpA.getValueGivenRow(table3, row, "NoteName");
            String note_value = HelpA.getValueGivenRow(table3, row, "NoteValue");
            String updatedOn = HelpA.getValueGivenRow(table3, row, "UpdatedOn");
            //
            try {
                //
                sql.execute(SQL_A.update_ingred_table_3(PROC.PROC_45, ingred_free_info_id, ingred_code, note_name, note_value, updatedOn, HelpA.updatedBy()), mCRecipe);
            } catch (SQLException ex) {
                Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
        }
        //
        unsavedRows_table_3.clear();
        //
        //
        //
        for (Integer row : addedRows_table_3) {
            String ingred_code = HelpA.getValueGivenRow(table3, row, "IngredientCode_ID");
            String note_name = HelpA.getValueGivenRow(table3, row, "NoteName");
            String note_value = HelpA.getValueGivenRow(table3, row, "NoteValue");
            String updatedOn = HelpA.getValueGivenRow(table3, row, "UpdatedOn");
//            String updatedBy = HelpA.getValueGivenRow(table3, row, "UpdatedBy");
            //
            if (ingred_code == null) {
                System.out.println("\n\nCONTINUE");
                continue;
            }
            //
            try {
                String q = SQL_A.add_to_ingred_table_3(PROC.PROC_43, ingred_code, note_name, note_value, updatedOn, HelpA.updatedBy());
                sql.execute(q, mCRecipe);
            } catch (SQLException ex) {
                Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //
        addedRows_table_3.clear();
        //
        //
        fill_tables_2_and_3();
    }

    @Override
    public void fillNotes() {
        //
        String comments = "";
        //OBS!
        if (mCRecipe.jEditorPane_Ingred.getEditorKit() instanceof RTFEditorKit == false) {
            mCRecipe.jEditorPane_Ingred.setEditorKit(new RTFEditorKit());
            //
        }
        //
        //
        String id = getIngredCodeId();
        //
        if (id == null || addedRows_table_3.contains(mCRecipe.jTable_Ingred_Table3.getSelectedRow())) {
            fill_notes_b(comments);
            return;
        }
        //
        try {
            //
            String q = SQL_A.build_ingred_textarea(PROC.PROC_46, id);
            //
            ResultSet rs = sql.execute(q, mCRecipe);
            //
            if (rs.next()) {
                //
                comments = rs.getString("Comments");
                //
                if (comments == null || comments.isEmpty()) {
                    comments = "";
                }
                //
            }
            //
            fill_notes_b(comments);
            //
        } catch (Exception ex) {
            Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fill_notes_b(String comments) {
        //
        if (comments.contains("fs16") || comments.contains("rtf1")) {
            //
            replaceComponent(mCRecipe.jScrollPane_Ingred_Comments.getViewport(), mCRecipe.jScrollPane_Ingred_Comments,
                    mCRecipe.textAreaIngredComments, mCRecipe.jEditorPane_Ingred);
            //
//            comments = comments.replace("fs16", "fs35");
            comments = HelpM.resizeTextRtf(comments);
            mCRecipe.jEditorPane_Ingred.setText(comments);
            //
        } else {
            //
            replaceComponent(mCRecipe.jScrollPane_Ingred_Comments.getViewport(), mCRecipe.jScrollPane_Ingred_Comments,
                    mCRecipe.jEditorPane_Ingred, mCRecipe.textAreaIngredComments);
            //
            //
            mCRecipe.textAreaIngredComments.setText(comments);
        }
        //
    }

    public void fill_tables_2_and_3() {
        fill_table_2();
        fill_table_3();
    }

    private void fill_table_2() {
        //
        JTable table2 = mCRecipe.jTable_Ingred_table2;
        //
        try {
            //
            String q = SQL_A.build_ingred_table_2(PROC.PROC_48, getIngredCode());
            //
            ResultSet rs = sql.execute(q, mCRecipe);
            //
            HelpA.build_table_common(rs, table2, q);
            //
        } catch (SQLException ex) {
            Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    private void fill_table_3() {
        //
        JTable table3 = mCRecipe.jTable_Ingred_Table3;
        //
        try {
            String q = SQL_A.build_ingred_table_3(PROC.PROC_47, getIngredCode());
            //
            ResultSet rs = sql_additional.execute(q, mCRecipe);
            //
            HelpA.build_table_common(rs, table3, q);
            //
        } catch (SQLException ex) {
            Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        //
        HelpA.hideColumnByName(table3, "Name");
        HelpA.hideColumnByName(table3, "IngredientCode_ID");
        HelpA.hideColumnByName(table3, "Ingred_Free_Info_ID");
        //
        //Just to look better visually
        for (int i = 0; i < 14; i++) {
            HelpA.addRowJTable(table3);
        }
        //
    }

    public void fill_table_1(String name_) {
        String name = HelpA.getComboBoxSelectedValue(mCRecipe.jCombo_Ingred_Name);
        String clasS = HelpA.getComboBoxSelectedValue(mCRecipe.jCombo_Ingred_Class);
        String status = HelpA.getComboBoxSelectedValue(mCRecipe.jCombo_Ingred_Status);
        String group = HelpA.getComboBoxSelectedValue(mCRecipe.jCombo_Ingred_Group);
        String descr = HelpA.getComboBoxSelectedValue(mCRecipe.jCombo_Ingred_Descr);
        String form = HelpA.getComboBoxSelectedValue(mCRecipe.jCombo_Ingred_Form);
        String percRubber = HelpA.getComboBoxSelectedValue(mCRecipe.jCombo_Ingred_Perc_Rubber);//?????????????
        String tradeName = HelpA.getComboBoxSelectedValue(mCRecipe.jCombo_Ingred_TradeName);
        String vendor = HelpA.getComboBoxSelectedValue(mCRecipe.jCombo_Ingred_VendorName);
        String casNr = HelpA.getComboBoxSelectedValue(mCRecipe.jCombo_Ingred_Cas_Number);
        //
        JTableM table = (JTableM) mCRecipe.jTable_Ingred_Table1;
        //
        if (name_ != null && name_.isEmpty() == false) {
            name = name_;
        }
        //
        try {
            //
            String q = SQL_A.ingredientListFunction(
                    PROC.PROC_50,
                    name,
                    clasS,
                    group,
                    status,
                    descr,
                    form,
                    percRubber,
                    tradeName,
                    vendor,
                    casNr);
            //
            ResultSet rs = sql.execute(q, mCRecipe);
            //
//            HelpA.build_table_common(rs, table, q);
            table.build_table_common(rs, q);
            //
            table1Build = true;
            //
        } catch (SQLException ex) {
            Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
            table1Build = false;
        }
        //
        //
//        HelpA.INGRED_TABLE_1_LIST = HelpA.restoreListFromObject(HelpA.INGRED_TABLE_1_OBJ);
//        //
//        HelpA.restoreColumnWidths(table, HelpA.INGRED_TABLE_1_LIST);
        //
        //
        HelpA.hideColumnByName(table, "VendorNo");
        HelpA.hideColumnByName(table, "VENDOR_ID");
        //
        HelpA.changeTableHeaderTitleOfOneColumn(table, "Name", JTB.LANG("Name"));
        HelpA.changeTableHeaderTitleOfOneColumn(table, "class", JTB.LANG("class"));
        HelpA.changeTableHeaderTitleOfOneColumn(table, "Grupp", JTB.LANG("Grupp"));
        HelpA.changeTableHeaderTitleOfOneColumn(table, "Status", JTB.LANG("Status"));
        HelpA.changeTableHeaderTitleOfOneColumn(table, "Descr", JTB.LANG("Descr"));
        HelpA.changeTableHeaderTitleOfOneColumn(table, "Form", JTB.LANG("Form"));
        HelpA.changeTableHeaderTitleOfOneColumn(table, "percRubber", JTB.LANG("percRubber"));
        HelpA.changeTableHeaderTitleOfOneColumn(table, "TradeName", JTB.LANG("TradeName"));
        HelpA.changeTableHeaderTitleOfOneColumn(table, "VendorName", JTB.LANG("VendorName"));
        HelpA.changeTableHeaderTitleOfOneColumn(table, "Cas_Number", JTB.LANG("Cas_Number"));
        HelpA.changeTableHeaderTitleOfOneColumn(table, "VM", JTB.LANG("VM"));
    }

    private String[] getComboParams() {
        String ingredName = HelpA.getComboBoxSelectedValue(mCRecipe.jCombo_Ingred_Name);
        String ingredGroup = HelpA.getComboBoxSelectedValue(mCRecipe.jCombo_Ingred_Group);
        String ingredDescr = HelpA.getComboBoxSelectedValue(mCRecipe.jCombo_Ingred_Descr);
        String ingredClass = HelpA.getComboBoxSelectedValue(mCRecipe.jCombo_Ingred_Class);
        String ingredStatus = HelpA.getComboBoxSelectedValue(mCRecipe.jCombo_Ingred_Status);
        String ingredForm = HelpA.getComboBoxSelectedValue(mCRecipe.jCombo_Ingred_Form);
        String percRubber = HelpA.getComboBoxSelectedValue(mCRecipe.jCombo_Ingred_Perc_Rubber);
        String ingredCasNr = HelpA.getComboBoxSelectedValue(mCRecipe.jCombo_Ingred_Cas_Number);
        String ingredTradeName = HelpA.getComboBoxSelectedValue(mCRecipe.jCombo_Ingred_TradeName);
        String ingredVendorName = HelpA.getComboBoxSelectedValue(mCRecipe.jCombo_Ingred_VendorName);
        return new String[]{ingredName, ingredClass, ingredGroup, ingredStatus, ingredDescr, ingredForm, percRubber, ingredTradeName, ingredVendorName, ingredCasNr};
    }
    private static long prevCall;

    private boolean delay() {
        if (Math.abs(System.currentTimeMillis() - prevCall) < 1000) {
            prevCall = System.currentTimeMillis();
            return false;
        } else {
            prevCall = System.currentTimeMillis();
            return true;
        }
    }

    public void fillComboBox(final JComboBox box, final String colName) {
        // 
        if (delay() == false) {
            return;
        }
        //
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Object selection = box.getSelectedItem();
                //
                //
                String q = SQL_A.fill_comboboxes_ingred(PROC.PROC_24, colName, getComboParams());
                OUT.showMessage(q);
                //
                JComboBoxA boxA = (JComboBoxA) box;
                //
                boxA.fillComboBox(sql, box, q, null, false, false);
                //
                box.setSelectedItem(selection);
            }
        });

    }

    public void clearBoxes() {
        //
        for (JComboBox box : mCRecipe.ingredientsGroupList) {
            box.setSelectedItem(null);
            box.setEditable(false);
        }
        //
        mCRecipe.revalidate();
        mCRecipe.repaint();
        //
    }

    /**
     * String tableName, String primaryOrForeignKey, boolean keyIsString, String
     * field_original_name, String field_nick_name, String unit, boolean string,
     * boolean visible, boolean important
     *
     * @return
     */
    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        JLabel label = new JLabel(new ImageIcon(IconUrls.OK_ICON_URL));
        JLabel labe2 = new JLabel(new ImageIcon(IconUrls.INFO_ICON_URL));
        JLabel labe3 = new JLabel(new ImageIcon(IconUrls.UNSAVED_ICON_URL));
        JPanel icon_cont = new JPanel(new GridLayout(1, 2)); // This one should be set instead of unit parameter
        icon_cont.add(label);
        icon_cont.add(labe2);
        icon_cont.add(labe3);
        //
        RowDataInvert name = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "Name", T_INV.LANG("NAME"), "", true, true, false);
        name.setUneditable();
        //
        RowDataInvert cross_reference = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "Info_01", T_INV.LANG("CROSS REFERENCE"), "", true, true, false);
        //
        String q_1 = SQL_B.basic_combobox_query("Descr", "Ingredient_Code");
        RowDataInvert description = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_1, sql_additional, "", "Ingredient_Code", "IngredientCode_ID", false, "Descr", T_INV.LANG("DESCRIPTION"), "", true, true, false);
        //
        RowDataInvert priceKg = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "ActualPreisePerKg", T_INV.LANG("PRICE"), "Euro/kg", true, true, false);
        //
        RowDataInvert casno = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "Cas_Number", T_INV.LANG("CASNO"), "", true, true, false);
        //
        //
        RowDataInvert chem_number = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "ChemName", T_INV.LANG("CHEMICAL NAME"), "", true, true, false);
        chem_number.enableToolTipTextJTextField();
        //
        //
        String q_2 = SQL_B.basic_combobox_query("Class", "Ingredient_Code");
        RowDataInvert clasS = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_2, sql_additional, "", "Ingredient_Code", "IngredientCode_ID", false, "Class", T_INV.LANG("CLASS"), "", true, true, false);
        //
        String q_3 = SQL_B.basic_combobox_query("Status", "Ingredient_Code");
        RowDataInvert status = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_3, sql_additional, "", "Ingredient_Code", "IngredientCode_ID", false, "Status", T_INV.LANG("STATUS"), "", true, true, false);
        //
        String q_4 = SQL_B.basic_combobox_query("[Group]", "Ingredient_Code");
        RowDataInvert group = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_4, sql_additional, "", "Ingredient_Code", "IngredientCode_ID", false, "Group", T_INV.LANG("GROUP"), "", true, true, false);
        //
        RowDataInvert group_name = new RowDataInvert("Ingred_Group", "Id", false, "GroupName", T_INV.LANG("GROUP NAME"), "", true, true, false);
        group_name.enableToolTipTextJTextField();
        //
        //
        String q_5 = SQL_B.basic_combobox_query("Form", "Ingredient_Code");
        RowDataInvert appereance = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_5, sql_additional, "", "Ingredient_Code", "IngredientCode_ID", false, "Form", T_INV.LANG("APPEARANCE"), "", true, true, false);
        //
        //
        RowDataInvert perc_rubber = new RowDataInvert("Ingredient_phys_Properties", "Ingredient_phys_Properties_ID", false, "percRubber", T_INV.LANG("PERCENTAGE RUBBER"), "%", true, true, false);
        RowDataInvert rubber_tolerances = new RowDataInvert("Ingredient_phys_Properties", "Ingredient_phys_Properties_ID", false, "PercRubtOl", T_INV.LANG("RUBBER TOLERANCES"), "%", true, true, false);
        RowDataInvert perc_act_mat = new RowDataInvert("Ingredient_phys_Properties", "Ingredient_phys_Properties_ID", false, "PercActMat", T_INV.LANG("ACTIVITY"), "%", true, true, false);
        RowDataInvert density = new RowDataInvert("Ingredient_phys_Properties", "Ingredient_phys_Properties_ID", false, "density", T_INV.LANG("DENSITY"), "kg/m&#179", true, true, false);
        RowDataInvert density_tol = new RowDataInvert("Ingredient_phys_Properties", "Ingredient_phys_Properties_ID", false, "DensityTOl", T_INV.LANG("DENSITY TOLERANCE"), "kg/m&#179", true, true, false);
        RowDataInvert visc_temp = new RowDataInvert("Ingredient_phys_Properties", "Ingredient_phys_Properties_ID", false, "ViscTemp", T_INV.LANG("MOONEY TEMPEARTURE"), "\u00b0C", true, true, false);
        RowDataInvert visc_time = new RowDataInvert("Ingredient_phys_Properties", "Ingredient_phys_Properties_ID", false, "ViscTime", T_INV.LANG("MOONEY TIME"), "min", true, true, false);
        RowDataInvert visc_ml = new RowDataInvert("Ingredient_phys_Properties", "Ingredient_phys_Properties_ID", false, "ViscML", T_INV.LANG("MOONEY VISCOSITY"), "MU", true, true, false);
        RowDataInvert visc_ml_tol = new RowDataInvert("Ingredient_phys_Properties", "Ingredient_phys_Properties_ID", false, "ViscMLTOl", T_INV.LANG("MOONEY TOLERANCES"), "MU", true, true, false);
        //
        //
        RowDataInvert updated_on = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "UpdatedOn", T_INV.LANG("UPDATED ON"), "", true, true, false);
        RowDataInvert updated_by = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "UpdatedBy", T_INV.LANG("UPDATED BY"), "", true, true, false);
        //
        RowDataInvert created_on = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "CreatedOn", T_INV.LANG("CREATED ON"), "", true, true, false);
        RowDataInvert created_by = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "CreatedBy", T_INV.LANG("CREATED BY"), "", true, true, false);
        //
        String q_6 = SQL_B.basic_combobox_query("Info_02", "Ingredient_Code");
        RowDataInvert technical_datasheet = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_6, sql_additional, "", "Ingredient_Code", "IngredientCode_ID", false, "Info_02", T_INV.LANG("TECHNICAL DATASHEET"), "", true, true, false);
        //
        RowDataInvert ingred_code_id = new RowDataInvert("Ingredient_Code", "IngredientCode_ID", false, "IngredientCode_ID", T_INV.LANG("CODE ID"), "", true, true, false);
        ingred_code_id.setUneditable();
        //
        group_name.setUneditable();
        updated_on.setUneditable();
        updated_by.setUneditable();
        created_on.setUneditable();
        created_by.setUneditable();
        //
        RowDataInvert[] rows = {name, cross_reference, description, priceKg,
            casno, chem_number, clasS, status, group, group_name,
            appereance, perc_rubber, rubber_tolerances, perc_act_mat, density,
            density_tol, visc_temp, visc_time, visc_ml, visc_ml_tol, technical_datasheet, updated_on,
            updated_by, created_on, created_by, ingred_code_id};
        //
        return rows;
    }

    @Override
    public void showTableInvert() {
        //
        JTable table = mCRecipe.jTable_Ingred_Table1;
        int currRow = table.getSelectedRow();
        //
        if (currRow == -1) {
            return;
        }
        //
        String name = HelpA.getValueSelectedRow(table, "Name");
        //
        //
        TABLE_BUILDER_INVERT = new TableBuilderInvert(OUT, sql, getConfigTableInvert(), true, "ingredients_1");
        //
        TABLE_INVERT = null;
        //
        try {
            String q = SQL_A.prc_ITF_Igredients_main_Select(PROC.PROC_49, name);
            OUT.showMessage(q);
            TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q);
        } catch (SQLException ex) {
            Logger.getLogger(Ingredients.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        setMargin(TABLE_INVERT, 10, 0, 0, 0);
        //
        showTableInvert(mCRecipe.jPanel_Ingred_Invert_Table);
    }
}
