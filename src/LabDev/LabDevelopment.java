/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import MCRecipe.ChangeSaver;
import MCRecipe.Lang.LNG;
import MCRecipe.Lang.MSG;
import MCRecipe.Lang.NOTIFICATIONS;
import MCRecipe.Lang.T_INV;
import MCRecipe.MC_RECIPE;
import MCRecipe.RecipeDetailed_;
import MCRecipe.SQL_A;
import MCRecipe.Sec.PROC;
import MCRecipe.TestParameters_;
import MCRecipe.UpdateEntry;
import MyObjectTable.SaveIndicator;
import MyObjectTable.ShowMessage;
import MyObjectTable.Table;
import MyObjectTableInvert.BasicTab;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.JTextFieldInvert;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert_;
import MyObjectTableInvert.TableInvert;
import forall.GP;
import forall.HelpA_;
import forall.SqlBasicLocal;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author KOCMOC
 */
public class LabDevelopment extends BasicTab {

    public static String TABLE__MC_CPWORDER = "MC_Cpworder";
    public static String TABLE__MAT_INFO = "MC_Cpworder_OrderMaterials";

    public static String TABLE_NOTES_1 = "MC_Cpworder_SendTo";
    public static String TABLE_NOTES_2 = "MC_Cpworder_ActDept";

    private TableBuilderInvert_ TABLE_BUILDER_INVERT;
    private TableBuilderInvert_ TABLE_BUILDER_INVERT_2;
    private TableBuilderInvert_ TABLE_BUILDER_INVERT_3;
    private TableBuilderInvert_ TABLE_BUILDER_INVERT_4;
    private TableBuilderInvert_ TABLE_BUILDER_INVERT_5;
    private TableBuilderInvert_ TABLE_BUILDER_INVERT_6;
    private Table TABLE_INVERT_2;
    private Table TABLE_INVERT_3;
    private Table TABLE_INVERT_4;
    private Table TABLE_INVERT_5;
    private Table TABLE_INVERT_6;
    private final MC_RECIPE mCRecipe;
    private final ChangeSaver changeSaver;
    private LabDevHeaderComponent labDevHeaderComponent;

    private String ACTUAL_TAB_NAME;

    private final String ORDER_FOR_TESTING = "ENTW002106";

    public LabDevelopment(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, ChangeSaver saver) {
        super(sql, sql_additional, OUT);
        this.mCRecipe = (MC_RECIPE) OUT;
        this.changeSaver = saver;
        init();
    }

    private void init() {
        labDevHeaderComponent = new LabDevHeaderComponent(mCRecipe.jPanel_lab_dev_header, sql, this);
        initializeSaveIndicators();
        fill_jtable_1_2();
    }

    public String getOrderNo() {
        return ORDER_FOR_TESTING;
    }

    public String getRequester() {
        return HelpA_.getSingleParamSql(sql, TABLE__MC_CPWORDER, "WORDERNO", getOrderNo(), "REQUESTER", false);
    }

    public String getUpdatedOn() {
        //
        String date = HelpA_.getSingleParamSql(sql, TABLE__MC_CPWORDER, "WORDERNO", getOrderNo(), "UpdatedOn", false);
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
        return HelpA_.getSingleParamSql(sql, TABLE__MC_CPWORDER, "WORDERNO", getOrderNo(), "UpdatedBy", false);
        // 
    }

    private void refreshHeader() {
        //
        if (ACTUAL_TAB_NAME.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_MAIN_DATA())) {
            labDevHeaderComponent.tab_main_data();
        } else if (ACTUAL_TAB_NAME.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_STATUS())) {
            labDevHeaderComponent.tab_status();
        } else if (ACTUAL_TAB_NAME.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_NOTES())) {
            labDevHeaderComponent.tab_notes();
        } else if (ACTUAL_TAB_NAME.equals(LNG.LAB_DEVELOPMENT_TAB__TAB_MATERIALINFO())) {
            labDevHeaderComponent.tab_material_info();
        }
        //
    }

    public void lab_dev_tab_tab_material_info() {
        ACTUAL_TAB_NAME = LNG.LAB_DEVELOPMENT_TAB__TAB_MATERIALINFO();
        refreshHeader();
        fillJTableMaterialInfoTab();
    }

    public void lab_dev_tab__tab_notes_clicked() {
        ACTUAL_TAB_NAME = LNG.LAB_DEVELOPMENT_TAB__TAB_NOTES();
        fillNotes();
        refreshHeader();
    }

    public void lab_dev_tab__tab_main_data_clicked() {
        ACTUAL_TAB_NAME = LNG.LAB_DEVELOPMENT_TAB__TAB_MAIN_DATA();
        refreshHeader();
        showTableInvert();
//        REQUESTER_ANTRAGSTELLER = getValueTableInvert("REQUESTER", TABLE_INVERT);
    }

    public void lab_dev_tab__tab_status_clicked() {
        ACTUAL_TAB_NAME = LNG.LAB_DEVELOPMENT_TAB__TAB_STATUS();
        refreshHeader();
        showTableInvert_2();
        showTableInvert_3();
        showTableInvert_4();
        showTableInvert_5();
    }

    private JTextArea getNotesJTextArea() {
        return mCRecipe.jTextArea_notes__lab_dev_tab;
    }

    private void fill_jtable_1_2() {
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

    public void saveTableInvert_2_3_4_5__tab_status() {
        //
        if (containsInvalidatedFields(TABLE_INVERT_2, 1, getConfigTableInvert_2())
                || containsInvalidatedFields(TABLE_INVERT_3, 1, getConfigTableInvert_3())
                || containsInvalidatedFields(TABLE_INVERT_4, 1, getConfigTableInvert_4())
                || containsInvalidatedFields(TABLE_INVERT_5, 1, getConfigTableInvert_5())) {
            HelpA_.showNotification(MSG.MSG_3());
            return;
        }
        //
        saveChangesTableInvert(TABLE_INVERT_2);
        saveChangesTableInvert(TABLE_INVERT_3);
        saveChangesTableInvert(TABLE_INVERT_4);
        saveChangesTableInvert(TABLE_INVERT_5);
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
     * [TAB: STATUS] [NO NAME]
     *
     * @return
     */
    public RowDataInvert[] getConfigTableInvert_2() {
        //
        RowDataInvert fertigwunsch_acqdeliver = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "ACQDELIVER", T_INV.LANG("TO BE DELIEVERD"), "", true, true, false);
        //
        RowDataInvert gewunscht_requested = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "REQUESTED", T_INV.LANG("REQUESTED"), "", true, true, false);
        //
        RowDataInvert genehmigt_approved = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "APPROVED", T_INV.LANG("APPROVED"), "", true, true, false);
        //
        RowDataInvert ausfuhrung_execute = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "EXECUTE", T_INV.LANG("EXECUTE"), "", true, true, false);
        //
        RowDataInvert fertig_erwarted_expready = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "EXPREADY", T_INV.LANG("EXPECTED"), "", true, true, false);
        //
        RowDataInvert fertig_ready = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "READY", T_INV.LANG("READY"), "", true, true, false);
        //
        RowDataInvert updated_on = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "UpdatedOn", T_INV.LANG("UPDATED ON"), "", true, false, false); // UpdatedOn
        RowDataInvert updated_by = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "UpdatedBy", T_INV.LANG("UPDATED BY"), "", true, false, false);
        //
        fertigwunsch_acqdeliver.setValidateDate();
        gewunscht_requested.setValidateDate();
        genehmigt_approved.setValidateDate();
        ausfuhrung_execute.setValidateDate();
        fertig_erwarted_expready.setValidateDate();
        fertig_ready.setValidateDate();
        //
        updated_on.setUneditable();
        updated_by.setUneditable();
        //
        RowDataInvert[] rows = {fertigwunsch_acqdeliver, gewunscht_requested, genehmigt_approved, ausfuhrung_execute,
            fertig_erwarted_expready, fertig_ready, updated_on, updated_by};
        //
        return rows;
    }

    /**
     * [TAB: STATUS] [NO NAME]
     */
    public void showTableInvert_2() {
        //
        TABLE_BUILDER_INVERT_2 = new TableBuilderInvert_(OUT, sql, getConfigTableInvert_2(), false, "lab_development_2");
        //
        TABLE_INVERT_2 = null;
        //
        String order = getOrderNo();
        //
        try {
            String q = SQL_A.select_all_from_MC_Cpworder(order);
            OUT.showMessage(q);
            TABLE_INVERT_2 = TABLE_BUILDER_INVERT_2.buildTable(q, this);
        } catch (SQLException ex) {
            Logger.getLogger(TestParameters_.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT_2.showMessage(ex.toString());
        }
        //
        setVerticalScrollBarDisabled(TABLE_INVERT_2);
        //
        showTableInvert(mCRecipe.jPanel_lab_development_2, TABLE_INVERT_2);
        //
    }

    /**
     * [TAB: STATUS] [DIENSTE]
     *
     * @return
     */
    public RowDataInvert[] getConfigTableInvert_3() {
        //
        RowDataInvert datum_geplant_servplan = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "SERVPLAN", T_INV.LANG("PLANNED DATE"), "", true, true, false);
        //
        RowDataInvert datum_augefu_servexec = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "SERVEXEC", T_INV.LANG("DATE EXECUTED"), "", true, true, false);
        //
        RowDataInvert dat_vervollst_servexec = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "SERVCOMPL", T_INV.LANG("DATE COMPLETED"), "", true, true, false);
        //
        datum_geplant_servplan.setValidateDate();
        datum_augefu_servexec.setValidateDate();
        dat_vervollst_servexec.setValidateDate();
        //
        RowDataInvert updated_on = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "UpdatedOn", T_INV.LANG("UPDATED ON"), "", true, false, false); // UpdatedOn
        RowDataInvert updated_by = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "UpdatedBy", T_INV.LANG("UPDATED BY"), "", true, false, false);
        //
        updated_on.setUneditable();
        updated_by.setUneditable();
        //
        RowDataInvert[] rows = {datum_geplant_servplan, datum_augefu_servexec, dat_vervollst_servexec, updated_on, updated_by};
        //
        return rows;
    }

    /**
     * [TAB: STATUS] [DIENSTE]
     */
    public void showTableInvert_3() {
        //
        TABLE_BUILDER_INVERT_3 = new TableBuilderInvert_(OUT, sql, getConfigTableInvert_3(), false, "lab_development_3");
        //
        TABLE_INVERT_3 = null;
        //
        String order = getOrderNo();
        //
        try {
            String q = SQL_A.select_all_from_MC_Cpworder(order);
            OUT.showMessage(q);
            TABLE_INVERT_3 = TABLE_BUILDER_INVERT_3.buildTable(q, this);
        } catch (SQLException ex) {
            Logger.getLogger(TestParameters_.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT_3.showMessage(ex.toString());
        }
        //
//        setMargin(TABLE_INVERT_2, 10, 0, 0, 0);
        //
        showTableInvert(mCRecipe.jPanel_lab_development_3, TABLE_INVERT_3);
        //
    }

    /**
     * [TAB: STATUS] [VERARBEITUNG]
     *
     * @return
     */
    public RowDataInvert[] getConfigTableInvert_4() {
        //
        RowDataInvert datum_geplant_procplan = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "PROCPLAN", T_INV.LANG("PLANNED DATE"), "", true, true, false);
        //
        RowDataInvert datum_augefu_procexec = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "PROCEXEC", T_INV.LANG("DATE EXECUTED"), "", true, true, false);
        //
        RowDataInvert dat_vervollst_proccompl = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "PROCCOMPL", T_INV.LANG("DATE COMPLETED"), "", true, true, false);
        //
        RowDataInvert notice = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "NOTE", T_INV.LANG("NOTE"), "", true, true, false);
        //
//        String q_1 = SQL_B.basic_combobox_query("Name", TABLE_NOTES_1);
//        RowDataInvert notes = new RowDataInvert(RowDataInvert.TYPE_JCOMBOBOX, q_1, sql_additional, "", TABLE_NOTES_1, "ID", false, "Name", T_INV.LANG("NOTES"), "", true, true, false);
        //
        datum_geplant_procplan.setValidateDate();
        datum_augefu_procexec.setValidateDate();
        dat_vervollst_proccompl.setValidateDate();
        //
        RowDataInvert updated_on = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "UpdatedOn", T_INV.LANG("UPDATED ON"), "", true, false, false); // UpdatedOn
        RowDataInvert updated_by = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "UpdatedBy", T_INV.LANG("UPDATED BY"), "", true, false, false);
        //
        updated_on.setUneditable();
        updated_by.setUneditable();
        //
        RowDataInvert[] rows = {datum_geplant_procplan, datum_augefu_procexec, dat_vervollst_proccompl, notice, updated_on, updated_by};
        //
        return rows;
    }

    /**
     * [TAB: STATUS] [VERARBEITUNG]
     */
    public void showTableInvert_4() {
        //
        TABLE_BUILDER_INVERT_4 = new TableBuilderInvert_(OUT, sql, getConfigTableInvert_4(), false, "lab_development_4");
        //
        TABLE_INVERT_4 = null;
        //
        String order = getOrderNo();
        //
        try {
            String q = SQL_A.select_all_from_MC_Cpworder(order);
            OUT.showMessage(q);
            TABLE_INVERT_4 = TABLE_BUILDER_INVERT_4.buildTable(q, this);
        } catch (SQLException ex) {
            Logger.getLogger(TestParameters_.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT_4.showMessage(ex.toString());
        }
        //
//        setMargin(TABLE_INVERT_2, 10, 0, 0, 0);
        //
        showTableInvert(mCRecipe.jPanel_lab_development_4, TABLE_INVERT_4);
        //
    }

    /**
     * [TAB: STATUS] [PRUFT]
     *
     * @return
     */
    public RowDataInvert[] getConfigTableInvert_5() {
        //
        RowDataInvert datum_geplant_testplan = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "TESTPLAN", T_INV.LANG("PLANNED DATE"), "", true, true, false);
        //
        RowDataInvert datum_augefu_testexec = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "TESTEXEC", T_INV.LANG("DATE EXECUTED"), "", true, true, false);
        //
        RowDataInvert dat_vervollst_testcompl = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "TESTCOMPL", T_INV.LANG("DATE COMPLETED"), "", true, true, false);
        //
        RowDataInvert notice = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "NOTE", T_INV.LANG("NOTE"), "", true, true, false);
        //
        datum_geplant_testplan.setValidateDate();
        datum_augefu_testexec.setValidateDate();
        dat_vervollst_testcompl.setValidateDate();
        //
        RowDataInvert updated_on = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "UpdatedOn", T_INV.LANG("UPDATED ON"), "", true, false, false); // UpdatedOn
        RowDataInvert updated_by = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "UpdatedBy", T_INV.LANG("UPDATED BY"), "", true, false, false);
        //
        RowDataInvert[] rows = {datum_geplant_testplan, datum_augefu_testexec, dat_vervollst_testcompl, notice, updated_on, updated_by};
        //
        return rows;
    }

    /**
     * [TAB: STATUS] [PRUFT]
     */
    public void showTableInvert_5() {
        //
        TABLE_BUILDER_INVERT_5 = new TableBuilderInvert_(OUT, sql, getConfigTableInvert_5(), false, "lab_development_5");
        //
        TABLE_INVERT_5 = null;
        //
        String order = getOrderNo();
        //
        try {
            String q = SQL_A.select_all_from_MC_Cpworder(order);
            OUT.showMessage(q);
            TABLE_INVERT_5 = TABLE_BUILDER_INVERT_5.buildTable(q, this);
        } catch (SQLException ex) {
            Logger.getLogger(TestParameters_.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT_5.showMessage(ex.toString());
        }
        //
        //
//        showTableInvert(mCRecipe.jPanel_lab_development_5, TABLE_INVERT_5);
        //
        addTableInvertRowListener(TABLE_INVERT_5, this);
        //
        setVerticalScrollBarDisabled(TABLE_INVERT_5);
        //
        showTableInvert(mCRecipe.jPanel_lab_development_5, TABLE_INVERT_5);
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
            String q = SQL_A.get_lab_dev_jtable_material_info(PROC.PROC_68, null,id);
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
    }

    private void fillJTableMaterialInfoTab() {
        //
        JTable table = mCRecipe.jTable_lab_dev__material_info;
        //
        String q = SQL_A.get_lab_dev_jtable_material_info(PROC.PROC_68, getOrderNo(),null);
        HelpA_.build_table_common(sql, OUT, table, q, new String[]{"ID", "MCcode", "UpdatedOn", "UpdatedBy", "WORDERNO", "PlanID"});
        //
        HelpA_.markFirstRowJtable(table);
        materialInfoJTableClicked();
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
        fill_jtable_1_2();
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
        fill_jtable_1_2();
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
        fill_jtable_1_2();
        //
    }

    @Override
    public void keyReleasedForward(TableInvert ti, KeyEvent ke) {
        //
        super.keyReleasedForward(ti, ke); //To change body of generated methods, choose Tools | Templates.
        //
        JLinkInvert jli = (JLinkInvert) ke.getSource();
        //
        String col_name = ti.getCurrentColumnName(ke.getSource());
        //
        if (jli.getValidateDate()) {
            //
            Validator_MCR.validateDate(jli);
            //
        } else if (jli.getInputLengthValidation() > 0) {
            //
            JTextFieldInvert jtf = (JTextFieldInvert) jli;
            //
            int inputLength = jtf.getInputLengthValidation();
            //
            if (inputLength > 0) {
                Validator_MCR.validateMaxInputLength(jli, inputLength);
            }
            //
        }

        //
    }

    @Override
    public void initializeSaveIndicators() {
        SaveIndicator saveIndicator1 = new SaveIndicator(mCRecipe.jButton_lab_dev_save_btn_1, this, 1);
        SaveIndicator saveIndicator2 = new SaveIndicator(mCRecipe.jButton_lab_dev_save_btn_2, this, 2);
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
        } else if (nr == 2) {
            if (TABLE_INVERT_2 == null || TABLE_INVERT_3 == null || TABLE_INVERT_4 == null || TABLE_INVERT_5 == null) {
                return false;
            } else if (unsavedEntriesExist(TABLE_INVERT_2)
                    || unsavedEntriesExist(TABLE_INVERT_3)
                    || unsavedEntriesExist(TABLE_INVERT_4)
                    || unsavedEntriesExist(TABLE_INVERT_5)) {
                return true;
            }
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

}
