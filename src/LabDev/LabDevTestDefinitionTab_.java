/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import LabDev.sec.TestDefinitionEntry;
import LabDev.sec.TestDefinitionEntryB;
import MCRecipeLang.LAB_DEV;
import MCRecipeLang.LNG;
import MCRecipe.SQL_A_;
import MCRecipe.Sec.PROC;
import MyObjectTable.ShowMessage;
import MyObjectTableInvert.RowDataInvert;
import forall.HelpA;
import forall.SqlBasicLocal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KOCMOC
 */
public class LabDevTestDefinitionTab_ extends LabDevTab_ implements MouseListener, ActionListener {

    private final String COL_1_NAME = LAB_DEV.test_definition_tab__get_col_1();
    private final String COL_2_NAME = LAB_DEV.test_definition_tab__get_col_2();
    private final String COL_3_NAME = LAB_DEV.test_definition_tab__get_col_3();

    public LabDevTestDefinitionTab_(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment_ labDev) {
        super(sql, sql_additional, OUT, labDev);
        init();
    }

    private void init() {
        getEditBtn().addActionListener(this);
        getPrintBtn().addActionListener(this);
        JTable table = getTable();
        fillJTableHeader();
        table.addMouseListener(this);
        HelpA.setUneditableJTable(table);
        refresh();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //
        if (e.getSource().equals(getEditBtn())) {
            lab_dev_test_definitions_table_clicked();
        } else if (e.getSource().equals(getPrintBtn())) {
            tableCommonRepport(getTable(), false);
        }
        //
    }

    private JButton getEditBtn() {
        return mcRecipe.jButton_lab_dev_test_definition_goto;
    }

    private JButton getPrintBtn() {
        return mcRecipe.jButton_lab_dev__test_def__print_jtable;
    }

    public void refresh() {
        //
        JTable table = getTable();
        //
        build();
        //
        HelpA.setColumnWidthByName(COL_1_NAME, table, 0.1);
        HelpA.setColumnWidthByName(COL_2_NAME, table, 0.25);
        HelpA.setColumnWidthByName(COL_3_NAME, table, 0.65);
        //
        //
    }

    private JTable getTable() {
        return mcRecipe.jTable_test_definitions;
    }

    private void build() {
        ArrayList<String> codes_list = getTestCodesList_given_order_and_material(labDev.getOrderNo(), labDev.getMaterial());
        ArrayList<TestDefinitionEntry> table_data = buildJTableData(codes_list);
        fillJTable(table_data);
        //
        String testCode = labDev.getTestCode();
        //
        JTable table = getTable();
        //
        if (testCode.isEmpty() == false && table.getRowCount() > 0) {
            HelpA.markRowByValue(table, "Code", testCode);
        } else if (testCode.isEmpty() && table.getRowCount() > 0) {
            HelpA.markFirstRowJtable(table);
            setTestCode__();
        }
        //
//        System.out.println("");
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
            String q = SQL_A_.lab_dev_proc69_proc70(PROC.PROC_69, material, order, code);
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
                Logger.getLogger(LabDevTestDefinitionTab_.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
            //
        }
        //
        return listToReturn;
    }

    private void fillJTableHeader() {
        //
        JTable table = getTable();
        //
        String[] headers = {
            COL_1_NAME, // Code
            COL_2_NAME, // Desc
            COL_3_NAME // Conditions
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
        HelpA.clearAllRowsJTable(table);
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

    @Override
    public void mousePressed(MouseEvent me) {
        //
        JTable table = getTable();
        //
        if (me.getSource() == table && (me.getClickCount() == 2)) {
            //
            lab_dev_test_definitions_table_clicked();
            //
        } else if (me.getSource() == table && (me.getClickCount() == 1)) {
            //
            System.out.println("CLIKED: 1");
            setTestCode__();
            //
        }
        //
    }

    private void lab_dev_test_definitions_table_clicked() {
        //
        setTestCode__();
        //
        HelpA.openTabByName(labDev.getTabbedPane(), LNG.LAB_DEVELOPMENT_TAB__TAB_TEST_CONFIG());
        //
        labDev.lab_dev_tab_test_config__clicked();
        //
    }
    
     private void setTestCode__() {
        JTable table = getTable();
        String testCode = HelpA.getValueSelectedRow(table, "Code");
        labDev.setTestCode(testCode);
        labDev.refreshHeader();
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public String[] getComboParams__mcs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getQuery__mcs(String procedure, String colName, String[] comboParameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
