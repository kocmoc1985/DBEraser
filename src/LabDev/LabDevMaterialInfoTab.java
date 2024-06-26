/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import static LabDev.LabDevelopment_.TABLE__MAT_INFO;
import MCRecipeLang.LAB_DEV;
import MCRecipeLang.MSG;
import MCRecipeLang.T_INV;
import MCRecipe.MC_RECIPE;
import MCRecipe.SQL_A_;
import MCRecipe.Sec.PROC;
import MCRecipe.TestParameters_;
import MyObjectTable.SaveIndicator;
import MyObjectTable.ShowMessage;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.TableBuilderInvert;
import forall.HelpA;
import forall.JComboBoxA;
import forall.SqlBasicLocal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public class LabDevMaterialInfoTab extends LabDevTab_ implements ActionListener, MouseListener {

    private TableBuilderInvert TABLE_BUILDER_INVERT;
    private int LAST_SELECTED_ROW = -1;

    public LabDevMaterialInfoTab(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment_ labDev) {
        super(sql, sql_additional, OUT, labDev);
        init();
    }

    public void resetLastSelectedRow() {
        this.LAST_SELECTED_ROW = -1;
    }

    private void init() {
        //
        initializeSaveIndicators();
        //
        getSaveButton().addActionListener(this);
        getPrintInvertButton().addActionListener(this);
        getPrintJTableButton().addActionListener(this);
        getAddMaterialBtn().addActionListener(this);
        getRemoveMaterialButton().addActionListener(this);
        //
        getJTable().addMouseListener(this);
        //
    }

    public void refresh() {
        fillJTable();
        fillComboBox(); //refreshHeader(); is done from: fillJTable() -> materialInfoJTableClicked()
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //
        if (e.getSource().equals(getAddMaterialBtn())) {
            addMaterial();
        } else if (e.getSource().equals(getRemoveMaterialButton())) {
            deleteMaterial();
        } else if (e.getSource().equals(getSaveButton())) {
            saveTableInvert();
        } else if (e.getSource().equals(getPrintInvertButton())) {
            tableInvertExportOrRepport(TABLE_INVERT, 1, getConfigTableInvert());
        } else if (e.getSource().equals(getPrintJTableButton())) {
            tableCommonExportOrRepport(getJTable(), false);
        }
        //
    }

    public String getIdMaterialInfoTable() {
        return HelpA.getValueSelectedRow(getJTable(), "ID");
    }

    private JButton getPrintJTableButton() {
        return mcRecipe.jButton_lab_dev__mat_info__print_jtable;
    }

    private JButton getPrintInvertButton() {
        return mcRecipe.jButton_lab_dev__mat_info_print_invert;
    }

    private JButton getSaveButton() {
        return mcRecipe.jButton_lab_dev__material_info_save;
    }

    private JButton getRemoveMaterialButton() {
        return mcRecipe.jButton_lab_dev__mat_info__delete_material_btn;
    }

    private JButton getAddMaterialBtn() {
        return mcRecipe.jButton_lab_dev__mat_info__add_material_btn;
    }

    private JTable getJTable() {
        return mcRecipe.jTable_lab_dev__material_info;
    }

    private JComboBoxA getComboBox() {
        return (JComboBoxA) mcRecipe.jComboBox_lab_dev__mat_info__add_material;
    }

    private void saveTableInvert() {
        saveChangesTableInvert(TABLE_INVERT);
        labDev.refreshHeader();
        fillJTable();
    }

    private boolean deleteMaterial() {
        //
        JTable table = getJTable();
        //
        String order = labDev.getOrderNo();
        String material = HelpA.getValueSelectedRow(table, "Material");
        //
        if (HelpA.rowSelected(table) == false) {
            HelpA.showNotification(MSG.LANG("Table row not chosen"));
            return false;
        }
        //
        if (MC_RECIPE.isAdminOrDeveloper() == false) {
            return false;
        }
        //
        if (HelpA.confirm(MSG.LANG("Confirm deletion of: ") + material + "?") == false) {
            return false;
        }
        //
        String q = SQL_A_.lab_dev_material_info__delete(PROC.PROC_81, order, material);
        //
        try {
//            sql.execute(q, OUT);
//            sql.executeProcedure(q, OUT);
            HelpA.runProcedureIntegerReturn_A_2(sql, q);
            fillJTable();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(LabDevMaterialInfoTab.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //
    }

    private void addMaterial() {
        //
        String order = labDev.getOrderNo();
        String material_new = HelpA.getComboBoxSelectedValue(getComboBox());
        //
        String q = SQL_A_.lab_dev__material_info__add_material(PROC.PROC_79, order, material_new);
        //
        try {
            sql.execute(q, OUT);
        } catch (SQLException ex) {
            Logger.getLogger(LabDevelopment_.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        addMaterial_copy();
        //
        fillJTable();
        //
    }

    private void addMaterial_copy() {
        //
        JTable table = getJTable();
        //
        String order = labDev.getOrderNo();
        String material_new = HelpA.getComboBoxSelectedValue(getComboBox());
        HelpA.markLastRowJtable(table);
        String material_ref = HelpA.getValueSelectedRow(getJTable(), "Material");
        //
        String q = SQL_A_.lab_dev_material_info__copy(PROC.PROC_87, order, material_ref, material_new);
        //
        try {
            sql.execute(q, OUT);
        } catch (SQLException ex) {
            Logger.getLogger(LabDevelopment_.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
    }

    private void fillJTable() {
        //
        JTable table = getJTable();
        //
        String q = SQL_A_.get_lab_dev_jtable_material_info(PROC.PROC_68, labDev.getOrderNo(), null);
        HelpA.build_table_common(sql, OUT, table, q, new String[]{"ID", "MCcode", "UpdatedOn", "UpdatedBy", "WORDERNO", "PlanID"});
        //
        LAB_DEV.material_information_tab_change_jtable__header(table);
        //
        markRowLogic();
        //
        materialInfoJTableClicked();
        //
    }

    private void markRowLogic() {
        //
        JTable table = getJTable();
        //
        if (HelpA.isEmtyJTable(table)) {
            return;
        }
        //
        if (LAST_SELECTED_ROW == -1) {
            HelpA.markFirstRowJtable(table);
        } else {
            //
            try {
                HelpA.setSelectedRow(table, LAST_SELECTED_ROW);
            } catch (Exception ex) {
                HelpA.markFirstRowJtable(table);
            }
            //
            //
            //
            //
        }
        //
    }

    public void materialInfoJTableClicked() {
        JTable table = getJTable();
        LAST_SELECTED_ROW = table.getSelectedRow();
        showTableInvert();
        String material = HelpA.getValueSelectedRow(table, "Material").trim();
        labDev.setMaterial(material);
        labDev.refreshHeader();
    }

    private void fillComboBox() {
        //
        JTable table = getJTable();
        JComboBox box = getComboBox();
        //
        String q = SQL_A_.lab_dev__material_info__add_material_combo();
        HelpA.fillComboBox(sql, box, q, null, true, false);
        //
        //
        if (HelpA.isEmtyJTable(table) == false) {
            String value_first_row = HelpA.getValueSelectedRow(table, "Material");
            //".trim() is needed 100% verified"
            HelpA.ComboBoxObject cbo = new HelpA.ComboBoxObject(value_first_row.trim(), "", "", "");
            box.setSelectedItem(cbo);
        }
        //
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        // Material, Beshreibung, Mischer, 1er Batch, Misch, Batchmenge
        //
        RowDataInvert material = new RowDataInvert(TABLE__MAT_INFO, "ID", false, "Material", T_INV.LANG("MATERIAL"), "", true, true, false);
        //
        RowDataInvert description = new RowDataInvert("NOT_NEEDED", "ID", false, "Beschreibung", T_INV.LANG("DESCRIPTION"), "", true, true, false);
        description.setDisabled();
        //
        RowDataInvert mixer = new RowDataInvert("NOT_NEEDED", "ID", false, "Mischer", T_INV.LANG("MIXER"), "", true, true, false);
        mixer.setDisabled();
        //
        RowDataInvert first_batch = new RowDataInvert(TABLE__MAT_INFO, "ID", false, "FIRSTBNO", T_INV.LANG("FIRST BATCH"), "", true, true, false);
        //
        RowDataInvert mix = new RowDataInvert(TABLE__MAT_INFO, "ID", false, "Misch", T_INV.LANG("MIX"), "", true, true, false);
        //
        RowDataInvert batch_ammount = new RowDataInvert(TABLE__MAT_INFO, "ID", false, "BatchMenge", T_INV.LANG("BATCH AMMOUNT"), "", true, true, false);
        //
        RowDataInvert updated_on = new RowDataInvert(TABLE__MAT_INFO, "ID", false, "UpdatedOn", T_INV.LANG("UPDATED ON"), "", true, false, false); // UpdatedOn
        RowDataInvert updated_by = new RowDataInvert(TABLE__MAT_INFO, "ID", false, "UpdatedBy", T_INV.LANG("UPDATED BY"), "", true, false, false);
        //
        //
        RowDataInvert[] rows = {material, description, mixer, first_batch, mix, batch_ammount, updated_on, updated_by};
        //
        return rows;
        //
    }

    @Override
    public void showTableInvert() {
        //
        TABLE_BUILDER_INVERT = new TableBuilderInvert(OUT, sql, getConfigTableInvert(), false, "lab_development_6");
        //
        TABLE_INVERT = null;
        //
        String id = HelpA.getValueSelectedRow(getJTable(), "ID");
        //
        try {
//            String q = SQL_A_.get_lab_dev_tinvert_material_info(id);
            String q = SQL_A_.get_lab_dev_jtable_material_info(PROC.PROC_68, null, id);
            OUT.showMessage(q);
            TABLE_INVERT = TABLE_BUILDER_INVERT.buildTable(q, this); // TableRow.FLOW_LAYOUT
        } catch (SQLException ex) {
            Logger.getLogger(TestParameters_.class.getName()).log(Level.SEVERE, null, ex);
            TABLE_BUILDER_INVERT.showMessage(ex.toString());
        }
        //
        setVerticalScrollBarDisabled(TABLE_INVERT);
        //
        showTableInvert(mcRecipe.jPanel_lab_dev_material_info, TABLE_INVERT);
        //
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //
        if (e.getSource() == getJTable() && (e.getClickCount() == 1)) {
            materialInfoJTableClicked();
        }
        //
    }

    @Override
    public void initializeSaveIndicators() {
        SaveIndicator saveIndicator = new SaveIndicator(mcRecipe.jButton_lab_dev__material_info_save, this, 1);
    }

    @Override
    public boolean getUnsaved(int nr) {
        //
        if (nr == 1) {
            //
            if (TABLE_INVERT == null) {
                return false;
            } else if (unsavedEntriesExist(TABLE_INVERT)) {
                return true;
            }
            //
        }
        return false;
        //
    }

    @Override
    public String getQuery__mcs(String procedure, String colName, String[] comboParameters) {
        return null;
    }

    @Override
    public String[] getComboParams__mcs() {
        return null;
    }

    @Override
    public void fillNotes() {
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
    public boolean fieldsValidated(boolean insert) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
