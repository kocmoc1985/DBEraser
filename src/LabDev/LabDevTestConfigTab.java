/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import static LabDev.LabDevelopment.TABLE__MC_CPWORDER;
import MCRecipe.Lang.T_INV;
import MCRecipe.SQL_A;
import MCRecipe.Sec.PROC;
import MyObjectTable.SaveIndicator;
import MyObjectTable.ShowMessage;
import MyObjectTable.TableRow;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert_;
import MyObjectTableInvert.UnsavedEntryInvert;
import forall.SqlBasicLocal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class LabDevTestConfigTab extends ChkBoxItemListComponent {

    private TableBuilderInvert_ TABLE_BUILDER_INVERT;

    public LabDevTestConfigTab(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment labDev) {
        super(sql, sql_additional, OUT, labDev);
        //
        init();
    }

    private void init() {
        temp();
        initializeSaveIndicators();
        refresh();
    }

    public void refresh() {
        java.awt.EventQueue.invokeLater(() -> {
            showTableInvert();
        });
    }

    private String getMaterial() {
        return labDev.getMaterial();
    }

    private String getOrder() {
        return labDev.getOrderNo();
    }

    private String getTestCode() {
        return labDev.getTestCode();
    }

    private ArrayList<TestConfigEntry> getValuesPruff_ValuesTest(String procedure) {
        //
        ArrayList<TestConfigEntry> list = new ArrayList<>();
        //
        String colCondition = "Condition";
        String colUnit = "Unit";
        //
        String q = SQL_A.lab_dev_proc69_proc70(procedure, getMaterial(), getOrder(), getTestCode());
        //
        ResultSet rs;
        //
        try {
            rs = sql.execute(q, mcRecipe);
            //
            while (rs.next()) {
                String condition = rs.getString(colCondition).trim();
                String unit = rs.getString(colUnit).trim();
                list.add(new TestConfigEntry(condition, unit));
            }
            //
        } catch (SQLException ex) {
            Logger.getLogger(LabDevTestConfigTab.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        return list;
    }

    /**
     * Example of getting values from a "CheckBox"
     */
    public void testGetValuesCheckBoxTableOne() {
        //
        ArrayList<JPanelPrepM> list = getSelectedFromTable(mcRecipe.jPanel65);
        //
        for (JPanelPrepM jp : list) {
            System.out.println("" + jp);
        }
        //
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        ArrayList<RowDataInvert> list = new ArrayList<>();
        //
        ArrayList<TestConfigEntry> list_pruff = getValuesPruff_ValuesTest(PROC.PROC_69);
        ArrayList<TestConfigEntry> list_test = getValuesPruff_ValuesTest(PROC.PROC_70);
        //
        for (int i = 0; i < list_pruff.size(); i++) {
            TestConfigEntry tce = list_pruff.get(i);
            RowDataInvert rdi = new RowDataInvert("MCcpwotest", "ID_Wotest", false, "TESTCOND", tce.getCondition(), tce.getUnit(), true, true, false);
            list.add(rdi);
        }
        //
        for (int i = 0; i < list_test.size(); i++) {
            TestConfigEntry tce = list_test.get(i);
            RowDataInvert rdi = new RowDataInvert("MCCPTproc", "ID_Proc", false, "TESTCOND", tce.getCondition(), tce.getUnit(), true, true, true);
            rdi.setDisabled();
            list.add(rdi);
        }
        //
        // OBS! Don't have this ones, but you will need to have "UpdateOn & UpdatedBy" fields in the "source" DBTable
//        RowDataInvert updated_on = new RowDataInvert("MCcpwotest", "ID_Wotest", false, "UpdatedOn", T_INV.LANG("UPDATED ON"), "", true, false, true); // UpdatedOn
//        RowDataInvert updated_by = new RowDataInvert("MCcpwotest", "ID_Wotest", false, "UpdatedBy", T_INV.LANG("UPDATED BY"), "", true, false, true);
        //
        RowDataInvert[] arr = new RowDataInvert[list.size()];
        //
        return list.toArray(arr);
        //
    }

    @Override
    public void showTableInvert() {
        //
        TABLE_BUILDER_INVERT = new TableBuilderInvert_(OUT, sql, getConfigTableInvert(), true, "lab_dev_test_config");
        //
        TABLE_INVERT = null;
        //
        try {
            //
            String q = SQL_A.lab_dev_proc69_proc70(PROC.PROC_69, getMaterial(), getOrder(), getTestCode());
            String q_2 = SQL_A.lab_dev_proc69_proc70(PROC.PROC_70, getMaterial(), getOrder(), getTestCode());
            //
            TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable_C_C(q, q_2, this, TableRow.GRID_LAYOUT);
            //
        } catch (SQLException ex) {
            Logger.getLogger(LabDevTestConfigTab.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT.showMessage(ex.toString());
        }
        //
        showTableInvert(mcRecipe.jPanel64);
        //
    }

    private void temp() {
        //
        addRows(new String[]{
            "- Keine - ",
            "Presse / Klappe 6mm / 10 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c "}, mcRecipe.jPanel65, null);
        //
        addRows(new String[]{
            "- Keine -",
            "DVR in luft / 1.0 d / 70c ",
            "DVR in luft / 1.0 d / 70c ",
            "DVR in luft / 1.0 d / 70c ",
            "DVR in luft / 1.0 d / 70c "
        }, mcRecipe.jPanel66, null);
    }

    @Override
    public void fillNotes() {
    }

    public void saveTableInvert() {
        //
//        saveChangesTableInvert();
        //
        saveChangesTableInvert_C_C(sql);
        //
        labDev.refreshHeader();
    }

    @Override
    public void initializeSaveIndicators() {
        SaveIndicator saveIndicator1 = new SaveIndicator(mcRecipe.jButton_lab_dev_tab__save_config_btn, this, 1);
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
        }
        return false;
    }

}
