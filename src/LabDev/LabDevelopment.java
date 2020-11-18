/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import MCRecipe.ChangeSaver;
import MCRecipe.Lang.LAB_DEV;
import MCRecipe.Lang.LNG;
import MCRecipe.Lang.MSG;
import MCRecipe.Lang.NOTIFICATIONS;
import MCRecipe.Lang.T_INV;
import MCRecipe.MC_RECIPE_;
import MCRecipe.RecipeDetailed_;
import MCRecipe.SQL_A;
import MCRecipe.Sec.PROC;
import MCRecipe.TestParameters_;
import MCRecipe.UpdateEntry;
import MyObjectTable.SaveIndicator;
import MyObjectTable.ShowMessage;
import MyObjectTable.Table;
import MyObjectTableInvert.BasicTab;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert_;
import forall.GP;
import forall.HelpA_;
import forall.SqlBasicLocal;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author KOCMOC
 */
public class LabDevelopment extends BasicTab implements MouseListener {

    public static String TABLE__MC_CPWORDER = "MC_Cpworder";
    public static String TABLE__MAT_INFO = "MC_Cpworder_OrderMaterials";

    public static String TABLE_NOTES_1 = "MC_Cpworder_SendTo";
    public static String TABLE_NOTES_2 = "MC_Cpworder_ActDept";

    private TableBuilderInvert_ TABLE_BUILDER_INVERT;
    private TableBuilderInvert_ TABLE_BUILDER_INVERT_6;
    private Table TABLE_INVERT_6;
    private final MC_RECIPE_ mCRecipe;
    private final ChangeSaver changeSaver;
    private LabDevHeaderComponent labDevHeaderComponent;
    private LabDevFindOrderTab labDevFindOrderTab;
    public LabDevTestConfigTab labDevTestConfigTab;
    public LabDevStatusTab labDevStatusTab;
    private String ORDER_FOR_TESTING = "ENTW002106"; // ENTW002106
    private String ACTUAL_TAB_NAME = "";
    private String PREV_TAB_NAME = "";

    public LabDevelopment(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, ChangeSaver saver) {
        super(sql, sql_additional, OUT);
        this.mCRecipe = (MC_RECIPE_) OUT;
        this.changeSaver = saver;
        init();
    }

    private void init() {
        labDevHeaderComponent = new LabDevHeaderComponent(mCRecipe.jPanel_lab_dev_header, sql, this);
        labDevFindOrderTab = new LabDevFindOrderTab(this, sql, mCRecipe);

        getTabbedPane().addMouseListener(this);
        initializeSaveIndicators();
        fill_jtable_1_2__tab__main_data();
    }

    private JTabbedPane getTabbedPane() {
        return mCRecipe.jTabbedPane3_Lab_Dev;
    }

    public String getOrderNo() {
        return ORDER_FOR_TESTING;
    }

    public String getMaterial() {
        return "WE8487";
    }

    public String getTestCode() {
        return "MOV01";
    }

    public void setOrderNo(String order) {
        this.ORDER_FOR_TESTING = order;
    }

    public String getRequester() {
        return HelpA_.getSingleParamSql(sql, TABLE__MC_CPWORDER, "WORDERNO", getOrderNo(), "REQUESTER", false);
    }

    public String getRezeptur() {
        return "";
    }

    public String getUpdatedOn() {
        //
        String date = "";
        //
        if (ACTUAL_TAB_NAME.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_MAIN_DATA())
                || ACTUAL_TAB_NAME.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_STATUS())
                || ACTUAL_TAB_NAME.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_NOTES())) {
            //
            date = HelpA_.getSingleParamSql(sql, TABLE__MC_CPWORDER, "WORDERNO", getOrderNo(), "UpdatedOn", false);
            //
        } else if (ACTUAL_TAB_NAME.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_MATERIALINFO())) {
            //
            String id = getIdMaterialInfoTable();
            //
            if (id == null || id.isEmpty()) {
                return "";
            }
            //
            date = HelpA_.getSingleParamSql_with_and(sql, TABLE__MAT_INFO,
                    "WORDERNO", getOrderNo(), "ID", id, "UpdatedOn", false);
            //
        } else if (ACTUAL_TAB_NAME.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_TEST_CONFIG())) {
            // Not needed
        }
        //
        if (date == null || date.isEmpty()) {
            return "";
        }
        //
        if (GP.IS_DATE_FORMAT_DE) {
            try {
                return HelpA_.dateToDateConverter(date, GP.DATE_FORMAT_COMMON, GP.DATE_FORMAT_DE);
            } catch (ParseException ex) {
                Logger.getLogger(LabDevelopment.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } else {
            return date;
        }
    }

    public String getUpdatedBy() {
        //
        String updatedBy = "";
        //
        if (ACTUAL_TAB_NAME.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_MAIN_DATA())
                || ACTUAL_TAB_NAME.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_STATUS())
                || ACTUAL_TAB_NAME.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_NOTES())) {
            //
            updatedBy = HelpA_.getSingleParamSql(sql, TABLE__MC_CPWORDER, "WORDERNO", getOrderNo(), "UpdatedBy", false);
            //
        } else if (ACTUAL_TAB_NAME.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_MATERIALINFO())) {
            //
            String id = getIdMaterialInfoTable();
            //
            if (id == null || id.isEmpty()) {
                return "";
            }
            //
            updatedBy = HelpA_.getSingleParamSql_with_and(sql, TABLE__MAT_INFO, "WORDERNO", getOrderNo(), "ID", id, "UpdatedBy", false);
        }
        //
        return updatedBy;
        // 
    }

    private String getIdMaterialInfoTable() {
        return HelpA_.getValueSelectedRow(mCRecipe.jTable_lab_dev__material_info, "ID");
    }

    public void lab_dev_tab_tab_find_order__set_order_clicked() {
        labDevFindOrderTab.setOrderBtnClicked();
        refreshHeader();
    }

    public void lab_dev_tab_tab_find_order__test_btn_clicked() {
        labDevFindOrderTab.filterButtonClicked();
    }

    protected void refreshHeader() {
        //
        if (ACTUAL_TAB_NAME.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_FIND_ORDER()) == false && getOrderNo().isEmpty()) {
            HelpA_.showNotification_separate_thread("Kein Auftrag ausgewählt!");
        }
        //
        if (ACTUAL_TAB_NAME.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_MAIN_DATA())) {
            labDevHeaderComponent.tab_main_data();
        } else if (ACTUAL_TAB_NAME.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_STATUS())) {
            labDevHeaderComponent.tab_status();
        } else if (ACTUAL_TAB_NAME.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_NOTES())) {
            labDevHeaderComponent.tab_notes();
        } else if (ACTUAL_TAB_NAME.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_MATERIALINFO())) {
            labDevHeaderComponent.tab_material_info();
        } else if (ACTUAL_TAB_NAME.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_FIND_ORDER())) {
            labDevHeaderComponent.tab_find_order();
        } else if (ACTUAL_TAB_NAME.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_TEST_DEFINITION())) {
            labDevHeaderComponent.tab_test_defenition();
        } else if (ACTUAL_TAB_NAME.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_TEST_CONFIG())) {
            labDevHeaderComponent.tab_test_config();
        }
        //
    }

    public void lab_dev_tab__tab_status__clicked() {
        //
        ACTUAL_TAB_NAME = LNG.LAB_DEVELOPMENT_TAB__TAB_STATUS();
        refreshHeader();
        //
        if (labDevStatusTab == null) {
            labDevStatusTab = new LabDevStatusTab(sql, sql_additional, OUT, this);
        } else {
            labDevStatusTab.refresh();
        }
        //
    }

    public void lab_dev_tab_test_config__clicked() {
        //
        ACTUAL_TAB_NAME = LNG.LAB_DEVELOPMENT_TAB__TAB_TEST_CONFIG();
        refreshHeader();
        //
        if (labDevTestConfigTab == null) {
            labDevTestConfigTab = new LabDevTestConfigTab(this, sql, mCRecipe);
        } else {
            labDevTestConfigTab.refresh();
        }
        //

    }

    public void lab_dev_tab_test_definition__clicked() {
        ACTUAL_TAB_NAME = LNG.LAB_DEVELOPMENT_TAB__TAB_TEST_DEFINITION();
        refreshHeader();
    }

    public void lab_dev_tab_tab_find_order__clicked() {
        ACTUAL_TAB_NAME = LNG.LAB_DEVELOPMENT_TAB__TAB_FIND_ORDER();
        refreshHeader();
        //
        labDevFindOrderTab.go();
        //
    }

    public void lab_dev_tab_tab_material_info__clicked() {
        ACTUAL_TAB_NAME = LNG.LAB_DEVELOPMENT_TAB__TAB_MATERIALINFO();
        fillJTableMaterialInfoTab();
//        refreshHeader(); // is done from: fillJTableMaterialInfoTab() -> materialInfoJTableClicked()
    }

    public void lab_dev_tab__tab_notes__clicked() {
        ACTUAL_TAB_NAME = LNG.LAB_DEVELOPMENT_TAB__TAB_NOTES();
        fillNotes();
        refreshHeader();
    }

    public void lab_dev_tab__tab_main_data__clicked() {
        ACTUAL_TAB_NAME = LNG.LAB_DEVELOPMENT_TAB__TAB_MAIN_DATA();
        refreshHeader();
        showTableInvert();
//        REQUESTER_ANTRAGSTELLER = getValueTableInvert("REQUESTER", TABLE_INVERT);
    }

    private JTextArea getNotesJTextArea() {
        return mCRecipe.jTextArea_notes__lab_dev_tab;
    }

    private void fill_jtable_1_2__tab__main_data() {
        //
        String[] colsToHide = new String[]{"WORDERNO", "UpdatedBy", "UpdatedOn", "ID"};
        //
        String q1 = SQL_A.get_lab_dev_table_1(getOrderNo(), TABLE_NOTES_1);
        HelpA_.build_table_common(sql, OUT, mCRecipe.jTable_lab_dev_1, q1, colsToHide);
        //
        String q2 = SQL_A.get_lab_dev_table_2(getOrderNo(), TABLE_NOTES_2);
        HelpA_.build_table_common(sql, OUT, mCRecipe.jTable_lab_dev_2, q2, colsToHide);
        //
    }

    @Override
    public void fillNotes() {
        String notes = HelpA_.getSingleParamSql(sql, TABLE__MC_CPWORDER, "WORDERNO", getOrderNo(), "NOTE", false);
        getNotesJTextArea().setText(notes);
    }

    public void saveTableInvert__tab_main_data() {
        //
        if (containsInvalidatedFields(TABLE_INVERT, 1, getConfigTableInvert())) {
            HelpA_.showNotification(MSG.MSG_3());
            return;
        }
        //
        saveChangesTableInvert();
        refreshHeader();
    }

    public void saveTableInvert_6() {
        saveChangesTableInvert(TABLE_INVERT_6);
        refreshHeader();
        fillJTableMaterialInfoTab();
    }

    public void saveNotesJTexArea() {
        //
        notesUnsaved = false;
        //
        String changedNotes = getNotesJTextArea().getText();
        //
        String q = SQL_A.save_notes_jtextarea_lab_dev(TABLE__MC_CPWORDER, changedNotes, getOrderNo());
        //
        try {
            sql.execute(q, OUT);
        } catch (SQLException ex) {
            Logger.getLogger(LabDevelopment.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        refreshHeader();
        //
    }

    /**
     * [TAB: KOPFDATEN - MAIN DATA]
     *
     * @return
     */
    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        //( tableName, primaryOrForeignKey,keyIsString,field_original_name, field_nick_name, unitOrObject, isstring, visible, important)
        //
        // Name format [ECLIPSE-NAME_COLNAME-DB-TABLE]
        //
        RowDataInvert antragstel__requester = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "REQUESTER", T_INV.LANG("REQUESTER"), "", true, true, false);
        //
        RowDataInvert abteilung_department = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "DEPARTMENT", T_INV.LANG("DEPARTMENT"), "", true, true, false);
        //
        RowDataInvert tel_reqphone = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "REQPHONE", T_INV.LANG("REQPHONE"), "", true, true, false);
        //
        RowDataInvert kunde_customer = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "CUSTOMER", T_INV.LANG("CUSTOMER"), "", true, true, false);
        //
        RowDataInvert projektnr_projectno = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "PROJECTNO", T_INV.LANG("PROJECTNO"), "", true, true, false);
//        projektnr_projectno.setInputLenthValidation(10);
        //
        RowDataInvert fertigwunsch_expready = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "EXPREADY", T_INV.LANG("EXPREADY"), "", true, true, false);
        fertigwunsch_expready.setValidateDate();
        //
        RowDataInvert ziel1_aimline1 = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "AIMLINE1", T_INV.LANG("TARGET 1"), "", true, true, false);
        ziel1_aimline1.enableToolTipTextJTextField();
        //
        RowDataInvert ziel2_aimline2 = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "AIMLINE2", T_INV.LANG("TARGET 2"), "", true, true, false);
        ziel2_aimline2.enableToolTipTextJTextField();
        //
        RowDataInvert updated_on = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "UpdatedOn", T_INV.LANG("UPDATED ON"), "", true, false, false); // UpdatedOn
        RowDataInvert updated_by = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "UpdatedBy", T_INV.LANG("UPDATED BY"), "", true, false, false);
        //
        updated_on.setUneditable();
        updated_by.setUneditable();
        //
        // OBS! Automatic "Updated On/By" handled by Basic.class -> automaticFieldUpdate(..)
        //
        RowDataInvert[] rows = {antragstel__requester, abteilung_department,
            tel_reqphone, kunde_customer, projektnr_projectno,
            fertigwunsch_expready, ziel1_aimline1, ziel2_aimline2, updated_on, updated_by};
        //
        return rows;
    }

    @Override
    public void showTableInvert() {
        //
        TABLE_BUILDER_INVERT = new TableBuilderInvert_(OUT, sql, getConfigTableInvert(), false, "lab_development");
        //
        TABLE_INVERT = null;
        //
        String order = getOrderNo();
        //
        try {
            String q = SQL_A.select_all_from_MC_Cpworder(order);
            OUT.showMessage(q);
            TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q, this);
        } catch (SQLException ex) {
            Logger.getLogger(TestParameters_.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT.showMessage(ex.toString());
        }
        //
//        setMargin(TABLE_INVERT, 10, 0, 0, 0);
        //
        showTableInvert(mCRecipe.jPanel_lab_development);
        //
    }

    /**
     * [TAB: MATERIA-INFO]
     *
     * @return
     */
    public RowDataInvert[] getConfigTableInvert_6() {
        //
        // Material, Beshreibung, Mischer, 1er Batch, Misch, Batchmenge
        //
        RowDataInvert material = new RowDataInvert(TABLE__MAT_INFO, "ID", false, "Material", T_INV.LANG("MATERIAL"), "", true, true, false);
        //
        RowDataInvert description = new RowDataInvert("NOT_NEEDED", "ID", false, "Beschreibung", T_INV.LANG("DESCRIPTION"), "", true, true, false);
        description.setDisabled();
        //
        RowDataInvert mixer = new RowDataInvert("NOT_NEEDED", "ID", false, "Mischer", T_INV.LANG("MIXER"), "", true, true, false);
        mixer.setDisabled();
        //
        RowDataInvert first_batch = new RowDataInvert(TABLE__MAT_INFO, "ID", false, "FIRSTBNO", T_INV.LANG("FIRST BATCH"), "", true, true, false);
        //
        RowDataInvert mix = new RowDataInvert(TABLE__MAT_INFO, "ID", false, "Misch", T_INV.LANG("MIX"), "", true, true, false);
        //
        RowDataInvert batch_ammount = new RowDataInvert(TABLE__MAT_INFO, "ID", false, "BatchMenge", T_INV.LANG("BATCH AMMOUNT"), "", true, true, false);
        //
        RowDataInvert updated_on = new RowDataInvert(TABLE__MAT_INFO, "ID", false, "UpdatedOn", T_INV.LANG("UPDATED ON"), "", true, false, false); // UpdatedOn
        RowDataInvert updated_by = new RowDataInvert(TABLE__MAT_INFO, "ID", false, "UpdatedBy", T_INV.LANG("UPDATED BY"), "", true, false, false);
        //
        //
        RowDataInvert[] rows = {material, description, mixer, first_batch, mix, batch_ammount, updated_on, updated_by};
        //
        return rows;
        //
    }

    /**
     * [TAB: MATERIA-INFO]
     */
    public void showTableInvert_6() {
        //
        TABLE_BUILDER_INVERT_6 = new TableBuilderInvert_(OUT, sql, getConfigTableInvert_6(), false, "lab_development_6");
        //
        TABLE_INVERT_6 = null;
        //
        String id = HelpA_.getValueSelectedRow(mCRecipe.jTable_lab_dev__material_info, "ID");
        //
        try {
//            String q = SQL_A.get_lab_dev_tinvert_material_info(id);
            String q = SQL_A.get_lab_dev_jtable_material_info(PROC.PROC_68, null, id);
            OUT.showMessage(q);
            TABLE_INVERT_6 = TABLE_BUILDER_INVERT_6.buildTable(q, this); // TableRow.FLOW_LAYOUT
        } catch (SQLException ex) {
            Logger.getLogger(TestParameters_.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT_6.showMessage(ex.toString());
        }
        //
        //
        addTableInvertRowListener(TABLE_INVERT_6, this);
        //
        setVerticalScrollBarDisabled(TABLE_INVERT_6);
        //
        showTableInvert(mCRecipe.jPanel_lab_dev_material_info, TABLE_INVERT_6);
    }

    public void materialInfoJTableClicked() {
        showTableInvert_6();
        refreshHeader(); // Yes shall be here
    }

    private void fillJTableMaterialInfoTab() {
        //
        JTable table = mCRecipe.jTable_lab_dev__material_info;
        //
        String q = SQL_A.get_lab_dev_jtable_material_info(PROC.PROC_68, getOrderNo(), null);
        HelpA_.build_table_common(sql, OUT, table, q, new String[]{"ID", "MCcode", "UpdatedOn", "UpdatedBy", "WORDERNO", "PlanID"});
        //
        LAB_DEV.material_information_tab_change_jtable__header(table);
        //
        HelpA_.markFirstRowJtable(table);
        materialInfoJTableClicked();
        //
    }

    public void deleteJTableNote(JTable table, String dbTableName) {
        //
        if (HelpA_.confirm() == false) {
            return;
        }
        //
        String id = HelpA_.getValueSelectedRow(table, "ID");
        //
        if (id == null || id.isEmpty()) {
            HelpA_.showNotification(NOTIFICATIONS.NOTE_3());
            return;
        }
        //
        String q = SQL_A.delete_lab_dev_jtable(dbTableName, id);
        //
        try {
            sql.execute(q, OUT);
        } catch (SQLException ex) {
            Logger.getLogger(LabDevelopment.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        fill_jtable_1_2__tab__main_data();
        //
    }

    public void addJTableNote(JTable table, String dbTableName) {
        //
        String order = getOrderNo();
        //
        JTextField jtf1 = new JTextField();
        JTextField jtf2 = new JTextField();
        //
        HelpA_.chooseFrom2Textfields(jtf1, jtf2, MSG.MSG_1(), MSG.MSG_1_2(), MSG.MSG_1_3());
        //
        String noteName = jtf1.getText();
        String noteValue = jtf2.getText();
        //
        String q = SQL_A.insert_into_lab_dev_table_1_and_2(dbTableName, order, noteName, noteValue, HelpA_.updatedOn(), HelpA_.updatedBy());
        //
        try {
            sql.execute(q, mCRecipe);
        } catch (SQLException ex) {
            Logger.getLogger(RecipeDetailed_.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        fill_jtable_1_2__tab__main_data();
        //
    }

    public void changeJTableNoteValue(JTable table, String tableName, String noteValColName, String idColName) {
        //
        if (HelpA_.getIfAnyRowChosen(table) == false) {
            HelpA_.showNotification(NOTIFICATIONS.NOTE_1());
            return;
        }
        //
        String id = HelpA_.getValueSelectedRow(table, idColName);
        //
        if (id == null || id.isEmpty()) {
            HelpA_.showNotification(NOTIFICATIONS.NOTE_3());
            return;
        }
        //
        String noteValue = HelpA_.getValueSelectedRow(table, noteValColName);
        //
        JTextField jtf = new JTextField(noteValue.equals("null") ? "" : noteValue);
        //
        jtf.setPreferredSize(new Dimension(300, 50));
        //
        boolean yes = HelpA_.chooseFromJTextField(jtf, NOTIFICATIONS.NOTE_2());
        //
        String value = jtf.getText();
        //
        if (value == null || yes != true) {
            return;
        }
        //
        //
        UpdateEntry updateEntry = new UpdateEntry(
                tableName,
                noteValColName,
                value,
                idColName,
                id,
                true,
                false);
        //
        //
        changeSaver.saveChange(updateEntry);
        //
        fill_jtable_1_2__tab__main_data();
        //
    }

    @Override
    public void initializeSaveIndicators() {
        SaveIndicator saveIndicator1 = new SaveIndicator(mCRecipe.jButton_lab_dev_save_btn_1, this, 1);
        SaveIndicator saveIndicator3 = new SaveIndicator(mCRecipe.jButton_lab_dev_tab__save_notes, this, 3);
        SaveIndicator saveIndicator4 = new SaveIndicator(mCRecipe.jButton_lab_dev__material_info_save, this, 4);
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
        } else if (nr == 3) {
            return notesUnsaved;
        } else if (nr == 4) {
            //
            if (TABLE_INVERT_6 == null) {
                return false;
            } else if (unsavedEntriesExist(TABLE_INVERT_6)) {
                return true;
            }
            //
        }
        return false;
    }

    //=========================================================================
    @Override
    public void mousePressed(MouseEvent me) {
        //
        JTabbedPane jtb = getTabbedPane();
        //
        if (me.getSource() == jtb) {
            //
            String title = jtb.getTitleAt(jtb.getSelectedIndex());
            //
            ACTUAL_TAB_NAME = title;
            //
            if (ACTUAL_TAB_NAME.equals(PREV_TAB_NAME)) {
                return;
            }
            //
            lab_dev_tab__clicked(title, false);
            //
            PREV_TAB_NAME = ACTUAL_TAB_NAME;
            //
        }
    }

    public void lab_dev_tab__clicked(String title, boolean parentCall) {
        //
        if (parentCall == true && PREV_TAB_NAME.isEmpty()) {
            lab_dev_tab_tab_find_order__clicked();
            PREV_TAB_NAME = LNG.LAB_DEVELOPMENT_TAB__TAB_FIND_ORDER();
            return;
        } else if (parentCall == true && PREV_TAB_NAME.isEmpty() == false) {
            title = PREV_TAB_NAME;
        }
        //
        if (title.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_MAIN_DATA())) {
            lab_dev_tab__tab_main_data__clicked();
        } else if (title.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_STATUS())) {
            lab_dev_tab__tab_status__clicked();
        } else if (title.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_NOTES())) {
            lab_dev_tab__tab_notes__clicked();
        } else if (title.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_MATERIALINFO())) {
            lab_dev_tab_tab_material_info__clicked();
        } else if (title.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_FIND_ORDER())) {
            lab_dev_tab_tab_find_order__clicked();
        } else if (title.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_TEST_DEFINITION())) {
            lab_dev_tab_test_definition__clicked();
        } else if (title.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_TEST_CONFIG())) {
            lab_dev_tab_test_config__clicked();
        }
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
