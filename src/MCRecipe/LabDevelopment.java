/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import MCRecipe.Lang.T_INV;
import MyObjectTable.SaveIndicator;
import MyObjectTable.ShowMessage;
import MyObjectTable.Table;
import MyObjectTableInvert.Basic;
import MyObjectTableInvert.BasicTab;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert_;
import forall.SqlBasicLocal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class LabDevelopment extends BasicTab {

    private TableBuilderInvert_ TABLE_BUILDER_INVERT;
    private TableBuilderInvert_ TABLE_BUILDER_INVERT_2;
    private TableBuilderInvert_ TABLE_BUILDER_INVERT_3;
    private TableBuilderInvert_ TABLE_BUILDER_INVERT_4;
    private TableBuilderInvert_ TABLE_BUILDER_INVERT_5;
    private Table TABLE_INVERT_2;
    private Table TABLE_INVERT_3;
    private Table TABLE_INVERT_4;
    private Table TABLE_INVERT_5;
    private final MC_RECIPE_ mCRecipe;

    private String ORDER_FOR_TESTING = "ENTW002106";

    public LabDevelopment(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT) {
        super(sql, sql_additional, OUT);
        this.mCRecipe = (MC_RECIPE_) OUT;
        init();
    }

    private void init() {
        initializeSaveIndicators();
    }

    @Override
    public void fillNotes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void saveTableInvert() {
//        if () {
//            HelpA_.showNotification("");
//            return;
//        }
        saveChangesTableInvert();
    }

    public void saveTableInvert_2_3_4_5() {
        saveChangesTableInvert(TABLE_INVERT_2);
        saveChangesTableInvert(TABLE_INVERT_3);
        saveChangesTableInvert(TABLE_INVERT_4);
        saveChangesTableInvert(TABLE_INVERT_5);
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        //( tableName, primaryOrForeignKey,keyIsString,field_original_name, field_nick_name, unitOrObject, isstring, visible, important)
        //
        // Name format [ECLIPSE-NAME_COLNAME-DB-TABLE]
        //
        RowDataInvert antragstel__requester = new RowDataInvert("MC_Cpworder", "ID", false, "REQUESTER", T_INV.LANG("REQUESTER"), "", true, true, false);
        //
        RowDataInvert abteilung_department = new RowDataInvert("MC_Cpworder", "ID", false, "DEPARTMENT", T_INV.LANG("DEPARTMENT"), "", true, true, false);
        //
        RowDataInvert tel_reqphone = new RowDataInvert("MC_Cpworder", "ID", false, "REQPHONE", T_INV.LANG("REQPHONE"), "", true, true, false);
        //
        RowDataInvert kunde_customer = new RowDataInvert("MC_Cpworder", "ID", false, "CUSTOMER", T_INV.LANG("CUSTOMER"), "", true, true, false);
        //
        RowDataInvert projektnr_projectno = new RowDataInvert("MC_Cpworder", "ID", false, "PROJECTNO", T_INV.LANG("PROJECTNO"), "", true, true, false);
        //
        RowDataInvert fertigwunsch_expready = new RowDataInvert("MC_Cpworder", "ID", false, "EXPREADY", T_INV.LANG("EXPREADY"), "", true, true, false);
        //
        RowDataInvert ziel1_aimline1 = new RowDataInvert("MC_Cpworder", "ID", false, "AIMLINE1", T_INV.LANG("TARGET 1"), "", true, true, false);
        ziel1_aimline1.enableToolTipTextJTextField();
        //
        RowDataInvert ziel2_aimline2 = new RowDataInvert("MC_Cpworder", "ID", false, "AIMLINE2", T_INV.LANG("TARGET 2"), "", true, true, false);
        ziel2_aimline2.enableToolTipTextJTextField();
        //
        RowDataInvert updated_on = new RowDataInvert("MC_Cpworder", "ID", false, "UpdatedOn", T_INV.LANG("UPDATED ON"), "", true, true, false); // UpdatedOn
        RowDataInvert updated_by = new RowDataInvert("MC_Cpworder", "ID", false, "UpdatedBy", T_INV.LANG("UPDATED BY"), "", true, true, false);
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
        String order = ORDER_FOR_TESTING;
        //
        try {
            String q = SQL_A.select_all_from_MC_Cpworder(order);
            OUT.showMessage(q);
            TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q);
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
     * [NO NAME]
     */
    public RowDataInvert[] getConfigTableInvert_2() {
        //
        RowDataInvert fertigwunsch_acqdeliver = new RowDataInvert("MC_Cpworder", "ID", false, "ACQDELIVER", T_INV.LANG("TO BE DELIEVERD"), "", true, true, false);
        //
        RowDataInvert gewunscht_requested = new RowDataInvert("MC_Cpworder", "ID", false, "REQUESTED", T_INV.LANG("REQUESTED"), "", true, true, false);
        //
        RowDataInvert genehmigt_approved = new RowDataInvert("MC_Cpworder", "ID", false, "APPROVED", T_INV.LANG("APPROVED"), "", true, true, false);
        //
        RowDataInvert ausfuhrung_execute = new RowDataInvert("MC_Cpworder", "ID", false, "EXECUTE", T_INV.LANG("EXECUTE"), "", true, true, false);
        //
        RowDataInvert fertig_erwarted_expready = new RowDataInvert("MC_Cpworder", "ID", false, "EXPREADY", T_INV.LANG("EXPECTED"), "", true, true, false);
        //
        RowDataInvert fertig_ready = new RowDataInvert("MC_Cpworder", "ID", false, "READY", T_INV.LANG("READY"), "", true, true, false);
        //
        RowDataInvert updated_on = new RowDataInvert("MC_Cpworder", "ID", false, "UpdatedOn", T_INV.LANG("UPDATED ON"), "", true, true, false); // UpdatedOn
        RowDataInvert updated_by = new RowDataInvert("MC_Cpworder", "ID", false, "UpdatedBy", T_INV.LANG("UPDATED BY"), "", true, true, false);
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
     * [NO NAME]
     */
    public void showTableInvert_2() {
        //
        TABLE_BUILDER_INVERT_2 = new TableBuilderInvert_(OUT, sql, getConfigTableInvert_2(), false, "lab_development_2");
        //
        TABLE_INVERT_2 = null;
        //
        String order = ORDER_FOR_TESTING;
        //
        try {
            String q = SQL_A.select_all_from_MC_Cpworder(order);
            OUT.showMessage(q);
            TABLE_INVERT_2 = TABLE_BUILDER_INVERT_2.buildTable(q);
        } catch (SQLException ex) {
            Logger.getLogger(TestParameters_.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT_2.showMessage(ex.toString());
        }
        //
        showTableInvert(mCRecipe.jPanel_lab_development_2, TABLE_INVERT_2);
        //
    }

    /**
     * [DIENSTE]
     *
     * @return
     */
    public RowDataInvert[] getConfigTableInvert_3() {
        //
        RowDataInvert datum_geplant_servplan = new RowDataInvert("MC_Cpworder", "ID", false, "SERVPLAN", T_INV.LANG("PLANNED DATE"), "", true, true, false);
        //
        RowDataInvert datum_augefu_servexec = new RowDataInvert("MC_Cpworder", "ID", false, "SERVEXEC", T_INV.LANG("DATE EXECUTED"), "", true, true, false);
        //
        RowDataInvert dat_vervollst_servexec = new RowDataInvert("MC_Cpworder", "ID", false, "SERVCOMPL", T_INV.LANG("DATE COMPLETED"), "", true, true, false);
        //
        RowDataInvert updated_on = new RowDataInvert("MC_Cpworder", "ID", false, "UpdatedOn", T_INV.LANG("UPDATED ON"), "", true, true, false); // UpdatedOn
        RowDataInvert updated_by = new RowDataInvert("MC_Cpworder", "ID", false, "UpdatedBy", T_INV.LANG("UPDATED BY"), "", true, true, false);
        //
        updated_on.setUneditable();
        updated_by.setUneditable();
        //
        RowDataInvert[] rows = {datum_geplant_servplan, datum_augefu_servexec, dat_vervollst_servexec, updated_on, updated_by};
        //
        return rows;
    }

    /**
     * [DIENSTE]
     */
    public void showTableInvert_3() {
        //
        TABLE_BUILDER_INVERT_3 = new TableBuilderInvert_(OUT, sql, getConfigTableInvert_3(), false, "lab_development_3");
        //
        TABLE_INVERT_3 = null;
        //
        String order = ORDER_FOR_TESTING;
        //
        try {
            String q = SQL_A.select_all_from_MC_Cpworder(order);
            OUT.showMessage(q);
            TABLE_INVERT_3 = TABLE_BUILDER_INVERT_3.buildTable(q);
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
     * [VERARBEITUNG]
     *
     * @return
     */
    public RowDataInvert[] getConfigTableInvert_4() {
        //
        RowDataInvert datum_geplant_procplan = new RowDataInvert("MC_Cpworder", "ID", false, "PROCPLAN", T_INV.LANG("PLANNED DATE"), "", true, true, false);
        //
        RowDataInvert datum_augefu_procexec = new RowDataInvert("MC_Cpworder", "ID", false, "PROCEXEC", T_INV.LANG("DATE EXECUTED"), "", true, true, false);
        //
        RowDataInvert dat_vervollst_proccompl = new RowDataInvert("MC_Cpworder", "ID", false, "PROCCOMPL", T_INV.LANG("DATE COMPLETED"), "", true, true, false);
        //
        RowDataInvert updated_on = new RowDataInvert("MC_Cpworder", "ID", false, "UpdatedOn", T_INV.LANG("UPDATED ON"), "", true, true, false); // UpdatedOn
        RowDataInvert updated_by = new RowDataInvert("MC_Cpworder", "ID", false, "UpdatedBy", T_INV.LANG("UPDATED BY"), "", true, true, false);
        //
        updated_on.setUneditable();
        updated_by.setUneditable();
        //
        RowDataInvert[] rows = {datum_geplant_procplan, datum_augefu_procexec, dat_vervollst_proccompl, updated_on, updated_by};
        //
        return rows;
    }

    /**
     * [VERARBEITUNG]
     */
    public void showTableInvert_4() {
        //
        TABLE_BUILDER_INVERT_4 = new TableBuilderInvert_(OUT, sql, getConfigTableInvert_4(), false, "lab_development_4");
        //
        TABLE_INVERT_4 = null;
        //
        String order = ORDER_FOR_TESTING;
        //
        try {
            String q = SQL_A.select_all_from_MC_Cpworder(order);
            OUT.showMessage(q);
            TABLE_INVERT_4 = TABLE_BUILDER_INVERT_4.buildTable(q);
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
     * [PRUFT]
     *
     * @return
     */
    public RowDataInvert[] getConfigTableInvert_5() {
        //
        RowDataInvert datum_geplant_testplan = new RowDataInvert("MC_Cpworder", "ID", false, "TESTPLAN", T_INV.LANG("PLANNED DATE"), "", true, true, false);
        //
        RowDataInvert datum_augefu_testexec = new RowDataInvert("MC_Cpworder", "ID", false, "TESTEXEC", T_INV.LANG("DATE EXECUTED"), "", true, true, false);
        //
        RowDataInvert dat_vervollst_testcompl = new RowDataInvert("MC_Cpworder", "ID", false, "TESTCOMPL", T_INV.LANG("DATE COMPLETED"), "", true, true, false);
        //
        RowDataInvert updated_on = new RowDataInvert("MC_Cpworder", "ID", false, "UpdatedOn", T_INV.LANG("UPDATED ON"), "", true, true, false); // UpdatedOn
        RowDataInvert updated_by = new RowDataInvert("MC_Cpworder", "ID", false, "UpdatedBy", T_INV.LANG("UPDATED BY"), "", true, true, false);
        //
        updated_on.setUneditable();
        updated_by.setUneditable();
        //
        RowDataInvert[] rows = {datum_geplant_testplan, datum_augefu_testexec, dat_vervollst_testcompl, updated_on, updated_by};
        //
        return rows;
    }

    /**
     * [PRUFT]
     */
    public void showTableInvert_5() {
        //
        TABLE_BUILDER_INVERT_5 = new TableBuilderInvert_(OUT, sql, getConfigTableInvert_5(), false, "lab_development_5");
        //
        TABLE_INVERT_5 = null;
        //
        String order = ORDER_FOR_TESTING;
        //
        try {
            String q = SQL_A.select_all_from_MC_Cpworder(order);
            OUT.showMessage(q);
            TABLE_INVERT_5 = TABLE_BUILDER_INVERT_5.buildTable(q);
        } catch (SQLException ex) {
            Logger.getLogger(TestParameters_.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT_5.showMessage(ex.toString());
        }
        //
//        setMargin(TABLE_INVERT_2, 10, 0, 0, 0);
        //
        showTableInvert(mCRecipe.jPanel_lab_development_5, TABLE_INVERT_5);
        //
    }

    @Override
    public void initializeSaveIndicators() {
        SaveIndicator saveIndicator1 = new SaveIndicator(mCRecipe.jButton_lab_dev_save_btn_1, this, 1);
        SaveIndicator saveIndicator2 = new SaveIndicator(mCRecipe.jButton_lab_dev_save_btn_2, this, 2);
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
        } else if (nr == 2) {
            if (TABLE_INVERT_2 == null || TABLE_INVERT_3 == null || TABLE_INVERT_4 == null || TABLE_INVERT_5 == null) {
                return false;
            } else if (unsavedEntriesExist(TABLE_INVERT_2)
                    || unsavedEntriesExist(TABLE_INVERT_3)
                    || unsavedEntriesExist(TABLE_INVERT_4)
                    || unsavedEntriesExist(TABLE_INVERT_5)) {
                return true;
            }
        }
        return false;
    }

}
