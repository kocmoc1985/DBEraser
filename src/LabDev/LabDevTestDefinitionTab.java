/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import MCRecipe.SQL_A;
import MCRecipe.Sec.PROC;
import MyObjectTable.ShowMessage;
import MyObjectTableInvert.RowDataInvert;
import forall.SqlBasicLocal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KOCMOC
 */
public class LabDevTestDefinitionTab extends LabDevTab {

    public LabDevTestDefinitionTab(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment labDev) {
        super(sql, sql_additional, OUT, labDev);
        init();
    }

    private void init() {
        fillJTableHeader();
        build();
    }

    private JTable getTable() {
        return mcRecipe.jTable_test_definitions;
    }

    private void build() {
        ArrayList<String> codes_list = getCodesList();
        ArrayList<TestDefinitionEntry> table_data = buildJTableData(codes_list);
        fillJTable(table_data);
        System.out.println("");
    }

    private ArrayList<TestDefinitionEntry> buildJTableData(ArrayList<String> codes_list) {
        //
        ArrayList<TestDefinitionEntry> listToReturn = new ArrayList<>();
        //
        String material = labDev.getMaterial();
        String order = labDev.getOrderNo();
        //
        for (String code : codes_list) {
            //
            String q = SQL_A.lab_dev_proc69_proc70(PROC.PROC_69, material, order, code);
            //
            try {
                //
                ResultSet rs = sql.execute(q, OUT);
                //
                TestDefinitionEntryB tdeb = new TestDefinitionEntryB();
                //
                String description = "";
                //
                while (rs.next()) {
                    //
                    description = rs.getString("DESCRIPT");
                    //
                    String condition = rs.getString("Condition");
                    String test_condition = rs.getString("TESTCOND");
                    String unit = rs.getString("Unit");
                    //
                    tdeb.add(condition, test_condition, unit);
                    //
                }
                //
                TestDefinitionEntry tde = new TestDefinitionEntry(code, description, tdeb.getConditionString());
                //
                listToReturn.add(tde);
                //
            } catch (SQLException ex) {
                Logger.getLogger(LabDevTestDefinitionTab.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
        }
        //
        return listToReturn;
    }

    private ArrayList<String> getCodesList() {
        //
        ArrayList<String> list = new ArrayList<>();
        //
        String material = labDev.getMaterial();
        String order = labDev.getOrderNo();
        //
        String q = SQL_A.lab_dev_test_definitions_tab__getCodes(PROC.PROC_69, material, order, null);
        //
        ResultSet rs;
        //
        try {
            //
            rs = sql.execute(q, OUT);
            //
            while (rs.next()) {
                //
                list.add(rs.getString("CODE"));
                //
            }
            //
        } catch (SQLException ex) {
            Logger.getLogger(LabDevTestDefinitionTab.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        return list;
    }

    private void fillJTableHeader() {
        //
        JTable table = getTable();
        //
        String[] headers = {
            "Code",
            "Description",
            "Conditions"
        };
        //
        table.setModel(new DefaultTableModel(null, headers));
        //
    }

    private void fillJTable(ArrayList<TestDefinitionEntry> tableData) {
        //
        JTable table = getTable();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        //
        for (TestDefinitionEntry tde : tableData) {
            //
            Object[] jtableRow = new Object[]{
            tde.getCode(),
            tde.getDescr(),
            tde.getConditionString()
            };
            //
            model.addRow(jtableRow);
            //
        }
        //
    }

    public void refresh() {

    }

    @Override
    public void fillNotes() {
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        return null;
    }

    @Override
    public void showTableInvert() {
    }

    @Override
    public void initializeSaveIndicators() {
    }

    @Override
    public boolean getUnsaved(int nr) {
        return false;
    }

}
