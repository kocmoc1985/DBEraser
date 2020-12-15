/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import LabDev.sec.ChkBoxItemListComponent;
import LabDev.sec.JPanelPrepM_C;
import LabDev.sec.JPanelPrepM;
import LabDev.sec.PrepOrAgingEntry;
import LabDev.sec.TestConfigEntry;
import MCRecipe.SQL_A_;
import MCRecipe.Sec.PROC;
import MyObjectTable.SaveIndicator;
import MyObjectTable.ShowMessage;
import MyObjectTable.TableRow;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert;
import MyObjectTableInvert.TableInvert;
import forall.SqlBasicLocal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author KOCMOC
 */
public class LabDevTestConfigTab extends ChkBoxItemListComponent implements ActionListener {

    private TableBuilderInvert TABLE_BUILDER_INVERT;

    public LabDevTestConfigTab(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment_ labDev) {
        super(sql, sql_additional, OUT, labDev);
        //
        init();
    }

    private void init() {
        getSaveBtn().addActionListener(this);
        getPrintBtn().addActionListener(this);
        initializeSaveIndicators();
        refresh();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(getSaveBtn())) {
            saveTableInvert();
        } else if (e.getSource().equals(getPrintBtn())) {
            tableInvertExportOrRepport(TABLE_INVERT, 1, getConfigTableInvert());
        }
    }

    private JButton getSaveBtn() {
        return mcRecipe.jButton_lab_dev_tab__save_config_btn;
    }

    private JButton getPrintBtn() {
        return mcRecipe.jButton_lab_dev__test_conf__print_invert;
    }

    public void refresh() {
        //
        if (getTestCode() == null || getTestCode().isEmpty()) {
            //
            if(defineAndSetTestCode() == false){
                return;
            }
            //
        }
        //
        buildPrepAndAgingMethodsTables();
        //
        java.awt.EventQueue.invokeLater(() -> {
            showTableInvert();
        });
        //
    }

    private boolean defineAndSetTestCode() {
        //
        String order = getOrder();
        //
        if(order == null || order.isEmpty()){
            return false;
        }
        //
        ArrayList<String> codes_list = labDev.getTestCodesList_given_order_and_material(order,getMaterial());
        //
        if(codes_list == null || codes_list.isEmpty()){
            return false;
        }
        //
        if (codes_list.get(0) != null && codes_list.get(0).isEmpty() == false) {
            //
            labDev.setTestCode(codes_list.get(0));
            //
            return true;
            //
        }
        //
        return false;
        //
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

    private JPanel getPreparationPanel() {
        return mcRecipe.jPanel65;
    }

    private JPanel getAgingPanel() {
        return mcRecipe.jPanel66;
    }

    private void buildPrepAndAgingMethodsTables() {
        //
        Object[] prep_methods = getPreparationOrAgingMethods(true, false);
        Object[] aging_methods = getPreparationOrAgingMethods(false, false);
        //
        //
        Object[] prep_methods__marked_chkboxes = getPreparationOrAgingMethods(true, true);
        Object[] aging_methods__marked_chkboxes = getPreparationOrAgingMethods(false, true);
        //
        //
        addRows(prep_methods, getPreparationPanel(), null);
        addRows(aging_methods, getAgingPanel(), null);
        //
        //
        setMarkedBoxes(prep_methods__marked_chkboxes, true);
        setMarkedBoxes(aging_methods__marked_chkboxes, false);
        //
    }

    private void setMarkedBoxes(Object[] prepOrAgingMethods, boolean preparationMethod) {
        //
        JPanel container = null;
        //
        if (preparationMethod) {
            container = getPreparationPanel();
        } else {
            container = getAgingPanel();
        }
        //
        for (Object obj : prepOrAgingMethods) {
            //
            ArrayList<JPanelPrepM> list = getAllEntriesFromTable(container, false);
            //
            PrepOrAgingEntry poae = (PrepOrAgingEntry) obj;
            //
            for (JPanelPrepM jp : list) { // Iterating through all components of "CheckBoxTable"
                //
                JPanelPrepM_C prepM_C = (JPanelPrepM_C) jp;
                //
                if (prepM_C.getTestCode().trim().equals(poae.getCode())) {
                    //
                    prepM_C.setHighLighted();
                    //
                }
                //
            }
            //
        }
        //
    }

    private ArrayList<TestConfigEntry> getValuesPruff_ValuesTest(String procedure) {
        //
        ArrayList<TestConfigEntry> list = new ArrayList<>();
        //
        String colCondition = "Condition";
        String colUnit = "Unit";
        //
        String q = SQL_A_.lab_dev_proc69_proc70(procedure, getMaterial(), getOrder(), getTestCode());
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
        ArrayList<JPanelPrepM> list = getAllEntriesFromTable(mcRecipe.jPanel65, true);
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
        TABLE_BUILDER_INVERT = new TableBuilderInvert(OUT, sql, getConfigTableInvert(), true, "lab_dev_test_config");
        //
        TABLE_INVERT = null;
        //
        try {
            //
            String q = SQL_A_.lab_dev_proc69_proc70(PROC.PROC_69, getMaterial(), getOrder(), getTestCode());
            String q_2 = SQL_A_.lab_dev_proc69_proc70(PROC.PROC_70, getMaterial(), getOrder(), getTestCode());
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

    private Object[] getPreparationOrAgingMethods(boolean preparation, boolean getChkBoxMarked) {
        //
        String code_column_name = null;
        String procedure = null;
        String test_code = null;
        //
        if (getChkBoxMarked) {
            test_code = getTestCode();
        }
        //
        if (preparation && getChkBoxMarked == false) { // "preparation method" -> get table entries
            procedure = PROC.PROC_71;
            code_column_name = "CODE";
        } else if (preparation == false && getChkBoxMarked == false) { // "aging method" -> get table entries
            procedure = PROC.PROC_72;
            code_column_name = "TESTCODE";
        } else if (preparation && getChkBoxMarked) { // get marked check boxes "preparation methods"
            procedure = PROC.PROC_73;
            code_column_name = "CODE";
        } else if (preparation == false && getChkBoxMarked) { // get marked check boxes "aging methods"
            procedure = PROC.PROC_74;
            code_column_name = "TESTCODE";
        }
        //
        ArrayList<PrepOrAgingEntry> list = new ArrayList<>();
        //
        String order = labDev.getOrderNo();
        String material = labDev.getMaterial();
        //
        String q = SQL_A_.lab_dev_test_config__get_preparation_aging_methods(procedure, material, order, test_code);
        //
        try {
            //
            ResultSet rs = sql.execute(q, OUT);
            //
            while (rs.next()) {
                //
                String descr = rs.getString("DESCR");
                String code = rs.getString(code_column_name);
                //
                PrepOrAgingEntry pme = new PrepOrAgingEntry(descr, code);
                //
                list.add(pme);
                //
            }
            //
        } catch (SQLException ex) {
            Logger.getLogger(LabDevTestConfigTab.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        PrepOrAgingEntry[] pme_arr = new PrepOrAgingEntry[list.size()];
        //
        return list.toArray(pme_arr);
    }

    @Override
    public void fillNotes() {
    }

    private void saveTableInvert() {
        //
        saveChangesTableInvert_C_C((TableInvert) TABLE_INVERT);
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

    private void buildCheckBoxTables_test_only() {
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
            "Presse / Ammi platte / 15 min / 170c "}, getPreparationPanel(), null);
        //
        addRows(new String[]{
            "- Keine -",
            "DVR in luft / 1.0 d / 70c ",
            "DVR in luft / 1.0 d / 70c ",
            "DVR in luft / 1.0 d / 70c ",
            "DVR in luft / 1.0 d / 70c "
        }, getAgingPanel(), null);
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
