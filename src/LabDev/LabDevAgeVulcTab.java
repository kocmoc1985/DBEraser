/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import static LabDev.LabDevelopment_.TABLE__AGEMENT;
import static LabDev.LabDevelopment_.TABLE__VULC;
import MCRecipe.Lang.T_INV;
import MCRecipe.SQL_A;
import MCRecipe.TestParameters_;
import MyObjectTable.ShowMessage;
import MyObjectTable.Table;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert_;
import forall.HelpA_;
import forall.SqlBasicLocal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;

/**
 *
 * @author KOCMOC
 */
public class LabDevAgeVulcTab extends LabDevTab {

    private Table TABLE_INVERT_2;
    private TableBuilderInvert_ TABLE_BUILDER_INVERT;
    private TableBuilderInvert_ TABLE_BUILDER_INVERT_2;

    public LabDevAgeVulcTab(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment_ labDev) {
        super(sql, sql_additional, OUT, labDev);
        init();
    }

    private void init() {
        refresh();
    }

    public void refresh() {
        java.awt.EventQueue.invokeLater(() -> {
            fillAgeComboBox();
            showTableInvert();
            showTableInvert_2();
        });

    }

    private String getAgeCode() {
        return "00011";
    }

    private String getVulcCode() {
        return "00014";
    }

    private JComboBox getAgeComboBox() {
        return mcRecipe.jComboBox_lab_dev__age;
    }

    private void fillAgeComboBox() {
        //
        String q = "select DISTINCT AGEINGCODE from MC_CPAGEMET";
        //
        HelpA_.fillComboBox(sql, getAgeComboBox(), q, null, false, false);
        //
    }

    @Override
    public void fillNotes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showTableInvert() {
        //
        TABLE_BUILDER_INVERT = new TableBuilderInvert_(OUT, sql, getConfigTableInvert(), false, "lab_dev_aging");
        //
        TABLE_INVERT = null;
        //
        String agecode = getAgeCode();
        //
        try {
            String q = "SELECT * FROM MC_CPAGEMET WHERE AGEINGCODE=" + SQL_A.quotes(agecode, false);
            OUT.showMessage(q);
            TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q, this);
        } catch (SQLException ex) {
            Logger.getLogger(TestParameters_.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT.showMessage(ex.toString());
        }
        //
//        setMargin(TABLE_INVERT, 10, 0, 0, 0);
        //
        showTableInvert(mcRecipe.jPanel68);
        //
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        RowDataInvert ageingcode = new RowDataInvert(TABLE__AGEMENT, "ID", false, "AGEINGCODE", T_INV.LANG("AGEING CODE"), "", true, true, false);
        RowDataInvert type = new RowDataInvert(TABLE__AGEMENT, "ID", false, "TYPE", T_INV.LANG("AGEING CODE"), "", true, true, false);
        RowDataInvert descr = new RowDataInvert(TABLE__AGEMENT, "ID", false, "DESCR", T_INV.LANG("DESCRIPTION"), "", true, true, false);
        descr.enableToolTipTextJTextField();
        RowDataInvert method = new RowDataInvert(TABLE__AGEMENT, "ID", false, "METHOD", T_INV.LANG("METHOD"), "", true, true, false);
        RowDataInvert temp = new RowDataInvert(TABLE__AGEMENT, "ID", false, "TEMP", T_INV.LANG("TEMP"), "", true, true, false);
        RowDataInvert time = new RowDataInvert(TABLE__AGEMENT, "ID", false, "TIME", T_INV.LANG("TIME"), "", true, true, false);
        RowDataInvert timeunit = new RowDataInvert(TABLE__AGEMENT, "ID", false, "TIMEUNIT", T_INV.LANG("TIME UNIT"), "", true, true, false);
        RowDataInvert status = new RowDataInvert(TABLE__AGEMENT, "ID", false, "STATUS", T_INV.LANG("STATUS"), "", true, true, false);
        RowDataInvert note = new RowDataInvert(TABLE__AGEMENT, "ID", false, "NOTE", T_INV.LANG("NOTE"), "", true, true, false);
        //
        RowDataInvert updatedOn = new RowDataInvert(TABLE__AGEMENT, "ID", false, "UpdatedOn", T_INV.LANG("UPDATED ON"), "", true, true, false);
        RowDataInvert updatedBy = new RowDataInvert(TABLE__AGEMENT, "ID", false, "UpdatedBy", T_INV.LANG("UPDATED BY"), "", true, true, false);
        //
        RowDataInvert[] rows = {ageingcode, type, descr, method, temp, time, timeunit, status, note, updatedOn, updatedBy};
        //
        return rows;
    }

    public void showTableInvert_2() {
        //
        TABLE_BUILDER_INVERT_2 = new TableBuilderInvert_(OUT, sql, getConfigTableInvert_2(), false, "lab_dev_vulc");
        //
        TABLE_INVERT_2 = null;
        //
        String vulccode = getVulcCode();
        //
        try {
            String q = "SELECT * FROM MC_CPVULMET WHERE VULCCODE=" + SQL_A.quotes(vulccode, false);
            OUT.showMessage(q);
            TABLE_INVERT_2 = TABLE_BUILDER_INVERT_2.buildTable(q, this); // TableRow.FLOW_LAYOUT
        } catch (SQLException ex) {
            Logger.getLogger(TestParameters_.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT_2.showMessage(ex.toString());
        }
        //
        setVerticalScrollBarDisabled(TABLE_INVERT_2);
        //
        showTableInvert(mcRecipe.jPanel69, TABLE_INVERT_2);
    }

    public RowDataInvert[] getConfigTableInvert_2() {
        //
        RowDataInvert vulccode = new RowDataInvert(TABLE__VULC, "ID", false, "VULCCODE", T_INV.LANG("VULCANISATION CODE"), "", true, true, false);
        RowDataInvert type = new RowDataInvert(TABLE__VULC, "ID", false, "TYPE", T_INV.LANG("TYPE"), "", true, true, false);
        RowDataInvert descr = new RowDataInvert(TABLE__VULC, "ID", false, "DESCR", T_INV.LANG("DESCR CODE"), "", true, true, false);
        descr.enableToolTipTextJTextField();
        RowDataInvert method = new RowDataInvert(TABLE__VULC, "ID", false, "METHOD", T_INV.LANG("METHOD"), "", true, true, false);
        RowDataInvert temp = new RowDataInvert(TABLE__VULC, "ID", false, "TEMP", T_INV.LANG("TEMP"), "", true, true, false);
        RowDataInvert time = new RowDataInvert(TABLE__VULC, "ID", false, "TIME", T_INV.LANG("TIME"), "", true, true, false);
        RowDataInvert article = new RowDataInvert(TABLE__VULC, "ID", false, "ARTICLE", T_INV.LANG("ARTICLE"), "", true, true, false);
        RowDataInvert status = new RowDataInvert(TABLE__VULC, "ID", false, "STATUS", T_INV.LANG("STATUS"), "", true, true, false);
        RowDataInvert note = new RowDataInvert(TABLE__VULC, "ID", false, "NOTE", T_INV.LANG("NOTE"), "", true, true, false);
        //
        RowDataInvert updatedOn = new RowDataInvert(TABLE__VULC, "ID", false, "UpdatedOn", T_INV.LANG("UPDATED ON"), "", true, true, false);
        RowDataInvert updatedBy = new RowDataInvert(TABLE__VULC, "ID", false, "UpdatedBy", T_INV.LANG("UPDATED BY"), "", true, true, false);
        //
        RowDataInvert[] rows = {vulccode, type, descr, method, temp, time, article, status, note, updatedOn, updatedBy};
        //
        return rows;
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
