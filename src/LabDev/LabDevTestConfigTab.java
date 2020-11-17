/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import MCRecipe.MC_RECIPE_;
import MCRecipe.SQL_A;
import MCRecipe.Sec.PROC;
import MyObjectTable.SaveIndicator;
import MyObjectTable.TableRow;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert_;
import forall.SqlBasicLocal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class LabDevTestConfigTab extends ChkBoxItemListComponent {

    private final LabDevelopment labDev;
    private final SqlBasicLocal sql;
    private final MC_RECIPE_ mcRecipe;
    private TableBuilderInvert_ TABLE_BUILDER_INVERT;

    public LabDevTestConfigTab(LabDevelopment labDev, SqlBasicLocal sql, MC_RECIPE_ mcRecipe) {
        super(sql, sql, mcRecipe);
        //
        this.labDev = labDev;
        this.sql = sql;
        this.mcRecipe = mcRecipe;
        init();
    }

    private void init() {
//        temp();
        initializeSaveIndicators();
        refresh();
    }

    public void refresh() {
        java.awt.EventQueue.invokeLater(() -> {
            showTableInvert();
        });
    }

    private String getMaterial() {
        return "WE8487";
    }

    private String getOrder() {
        return "ENTW002106";
    }

    private String getTestCode() {
        return "MOV01";
    }

    private ArrayList<TestConfigEntry> getValuesPruff_ValuesTest(String procedure) {
        //
        ArrayList<TestConfigEntry> list = new ArrayList<>();
        //
        String colCondition = "Condition";
        String colUnit = "Unit";
        //
        String q = SQL_A.lab_dev_test_config_tab__getTestConditions(procedure, getMaterial(), getOrder(), getTestCode());
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
        ArrayList<TestConfigEntry> list_pruff = getValuesPruff_ValuesTest(PROC.PROC_69);
        ArrayList<TestConfigEntry> list_test = getValuesPruff_ValuesTest(PROC.PROC_70);
        //
        ArrayList<RowDataInvert> list = new ArrayList<>();
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
        RowDataInvert[] arr = new RowDataInvert[list.size()];
        //
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        //
        return arr;
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
            String q = SQL_A.lab_dev_test_config_tab__getTestConditions(PROC.PROC_69, getMaterial(), getOrder(), getTestCode());
            String q_2 = SQL_A.lab_dev_test_config_tab__getTestConditions(PROC.PROC_70, getMaterial(), getOrder(), getTestCode());
            //
//            OUT.showMessage(q);
//            OUT.showMessage(q_2);
            //
            TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable_C_C(q, q_2, this, TableRow.GRID_LAYOUT);
            //
        } catch (SQLException ex) {
            Logger.getLogger(LabDevTestConfigTab.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT.showMessage(ex.toString());
        }
        //
        System.out.println("SHOW TABLE INVERT - TEST CONFIG");
        //
        showTableInvert(mcRecipe.jPanel64);
        //
//        refreshTableInvert(TABLE_INVERT);
//        TABLE_INVERT.invalidate();
//        TABLE_INVERT.validate();
//        TABLE_INVERT.repaint();
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
        saveChangesTableInvert();
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
