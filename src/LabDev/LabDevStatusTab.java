/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import static LabDev.LabDevelopment.TABLE__MC_CPWORDER;
import MCRecipe.Lang.LNG;
import MCRecipe.Lang.MSG;
import MCRecipe.Lang.T_INV;
import MCRecipe.MC_RECIPE_;
import MCRecipe.SQL_A;
import MCRecipe.TestParameters_;
import MyObjectTable.SaveIndicator;
import MyObjectTable.Table;
import MyObjectTableInvert.BasicTab;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert_;
import forall.HelpA_;
import forall.SqlBasicLocal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class LabDevStatusTab extends BasicTab {

    private TableBuilderInvert_ TABLE_BUILDER_INVERT_2;
    private TableBuilderInvert_ TABLE_BUILDER_INVERT_3;
    private TableBuilderInvert_ TABLE_BUILDER_INVERT_4;
    private TableBuilderInvert_ TABLE_BUILDER_INVERT_5;
    private Table TABLE_INVERT_2;
    private Table TABLE_INVERT_3;
    private Table TABLE_INVERT_4;
    private Table TABLE_INVERT_5;
    private final LabDevelopment labDev;
    private final MC_RECIPE_ mcRecipe;

    public LabDevStatusTab(LabDevelopment labDev, SqlBasicLocal sql, MC_RECIPE_ mcRecipe) {
        super(sql, sql, mcRecipe);
        this.labDev = labDev;
        this.mcRecipe = mcRecipe;
        init();
    }

    private void init() {
        initializeSaveIndicators();
        refresh();
    }

    public void refresh() {
        showTableInvert_2();
        showTableInvert_3();
        showTableInvert_4();
        showTableInvert_5();
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
        //
        labDev.refreshHeader();
        //
    }

    /**
     * [TAB: STATUS] [NO NAME]
     *
     * @return
     */
    public RowDataInvert[] getConfigTableInvert_2() {
        //
        RowDataInvert fertigwunsch_acqdeliver = new RowDataInvert(TABLE__MC_CPWORDER, "ID", false, "ACQDELIVER", T_INV.LANG("TO BE DELIEVERED"), "", true, true, false);
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
        String order = labDev.getOrderNo();
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
        showTableInvert(mcRecipe.jPanel_lab_development_2, TABLE_INVERT_2);
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
        String order = labDev.getOrderNo();
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
        showTableInvert(mcRecipe.jPanel_lab_development_3, TABLE_INVERT_3);
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
        notice.enableToolTipTextJTextField();
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
        String order = labDev.getOrderNo();
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
        showTableInvert(mcRecipe.jPanel_lab_development_4, TABLE_INVERT_4);
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
        notice.enableToolTipTextJTextField();
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
        String order = labDev.getOrderNo();
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
        showTableInvert(mcRecipe.jPanel_lab_development_5, TABLE_INVERT_5);
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
        SaveIndicator saveIndicator = new SaveIndicator(mcRecipe.jButton_lab_dev_save_btn_2, this, 1);
    }

    @Override
    public boolean getUnsaved(int nr) {
        if (nr == 1) {
            if (TABLE_INVERT_2 == null || TABLE_INVERT_3 == null || TABLE_INVERT_4 == null || TABLE_INVERT_5 == null) {
                return false;
            } else if (unsavedEntriesExist(TABLE_INVERT_2)
                    || unsavedEntriesExist(TABLE_INVERT_3)
                    || unsavedEntriesExist(TABLE_INVERT_4)
                    || unsavedEntriesExist(TABLE_INVERT_5)) {
                return true;
            }
        }
        //
        return false;
    }

}
