/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import MCRecipe.Lang.T_INV;
import MCRecipe.Sec.PROC;
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
        this.mCRecipe = (MC_RECIPE)OUT;
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
        RowDataInvert antragstel__requester = new RowDataInvert("MC_Cpworder", "primaryOrForeignKey", false, "REQUESTER", T_INV.LANG("REQUESTER"), "", true, false, false);
        //
        RowDataInvert abteilung_department = new RowDataInvert("MC_Cpworder", "primaryOrForeignKey", false, "DEPARTMENT", T_INV.LANG("DEPARTMENT"), "", true, false, false);
        //
        RowDataInvert tel_reqphone = new RowDataInvert("MC_Cpworder", "primaryOrForeignKey", false, "REQPHONE", T_INV.LANG("REQPHONE"), "", true, false, false);
        //
        RowDataInvert kunde_customer = new RowDataInvert("MC_Cpworder", "primaryOrForeignKey", false, "CUSTOMER", T_INV.LANG("CUSTOMER"), "", true, false, false);
        //
        RowDataInvert projektnr_projectno = new RowDataInvert("MC_Cpworder", "primaryOrForeignKey", false, "PROJECTNO", T_INV.LANG("PROJECTNO"), "", true, false, false);
        //
        RowDataInvert fertigwunsch_expready = new RowDataInvert("MC_Cpworder", "primaryOrForeignKey", false, "EXPREADY", T_INV.LANG("EXPREADY"), "", true, false, false);
        //
        RowDataInvert[] rows = {antragstel__requester, abteilung_department,tel_reqphone,kunde_customer,projektnr_projectno,fertigwunsch_expready};
        //
        return rows;
    }

    @Override
    public void showTableInvert() {
        //
        //
        TABLE_BUILDER_INVERT = new TableBuilderInvert_(OUT, sql, getConfigTableInvert(), false, "lab_development");
        //
        TABLE_INVERT = null;
        //
        try {
            String q = SQL_A.fn_ITF_Test_Related_ID(PROC.PROC_67, "", "", "","" , "", ""); // CHANGE!!!
            OUT.showMessage(q);
            TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q);
        } catch (SQLException ex) {
            Logger.getLogger(TestParameters_.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT.showMessage(ex.toString());
        }
        //
        showTableInvert(mCRecipe.jPanel_lab_development);
        //
    }

    @Override
    public void initializeSaveIndicators() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getUnsaved(int nr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
