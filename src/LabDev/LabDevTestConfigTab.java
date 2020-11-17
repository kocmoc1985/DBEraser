/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import static LabDev.LabDevelopment.TABLE__MC_CPWORDER;
import MCRecipe.Lang.T_INV;
import MCRecipe.MC_RECIPE_;
import MCRecipe.SQL_A;
import MCRecipe.Sec.PROC;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
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
        temp();
        showTableInvert();
//        getConditions();
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

    private ArrayList<TestConfigEntry> getConditions() {
        //
        ArrayList<TestConfigEntry> list = new ArrayList<>();
        //
        String colCondition = "Condition";
        String colUnit = "Unit";
        //
        String q = SQL_A.lab_dev_test_config_tab__getTestConditions(PROC.PROC_69, getMaterial(), getOrder(), getTestCode());
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
        ArrayList<TestConfigEntry> list = getConditions();
        //
        RowDataInvert[] rows = new RowDataInvert[list.size()];
        //
        for (int i = 0; i < list.size(); i++) {
            TestConfigEntry tce = list.get(i);
            RowDataInvert rdi = new RowDataInvert("MCcpwotest", "ID_Wotest", false, "TESTCOND", tce.getCondition(), tce.getUnit(), true, true, false);
            rows[i] = rdi;
        }
        //
        return rows;
    }

    @Override
    public void showTableInvert() {
        //
        TABLE_BUILDER_INVERT = new TableBuilderInvert_(OUT, sql, getConfigTableInvert(), false, "lab_dev_test_config");
        //
        TABLE_INVERT = null;
        //
        //
        try {
            String q = SQL_A.lab_dev_test_config_tab__getTestConditions(PROC.PROC_69, getMaterial(), getOrder(), getTestCode());
            OUT.showMessage(q);
            TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable_C(q, this);
        } catch (SQLException ex) {
            Logger.getLogger(LabDevTestConfigTab.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT.showMessage(ex.toString());
        }
        //
//        setMargin(TABLE_INVERT, 10, 0, 0, 0);
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

    @Override
    public void initializeSaveIndicators() {
    }

    @Override
    public boolean getUnsaved(int nr) {
        return false;
    }

}
