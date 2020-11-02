/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import MCRecipe.Lang.T_INV;
import MyObjectTable.SaveIndicator;
import MyObjectTable.ShowMessage;
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
    private final MC_RECIPE mCRecipe;

    public LabDevelopment(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT) {
        super(sql, sql_additional, OUT);
        this.mCRecipe = (MC_RECIPE) OUT;
        init();
    }
    
    private void init(){
        initializeSaveIndicators();
    }

    @Override
    public void fillNotes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        //
        RowDataInvert ziel2_aimline2 = new RowDataInvert("MC_Cpworder", "ID", false, "AIMLINE2", T_INV.LANG("TARGET 2"), "", true, true, false);
        //
        RowDataInvert[] rows = {antragstel__requester, abteilung_department,
            tel_reqphone, kunde_customer, projektnr_projectno,
            fertigwunsch_expready, ziel1_aimline1, ziel2_aimline2};
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
        String order = "ENTW002106";
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
        setMargin(TABLE_INVERT, 10, 0, 0, 0);
        //
        showTableInvert(mCRecipe.jPanel_lab_development);
        //
//        showTableInvert(mCRecipe.jPanel_lab_development, TABLE_INVERT);
        //
    }

    @Override
    public void initializeSaveIndicators() {
        SaveIndicator saveIndicator1 = new SaveIndicator(mCRecipe.jButton_lab_dev_save_btn_1, this, 1);
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
        }
        return false;
    }

}
