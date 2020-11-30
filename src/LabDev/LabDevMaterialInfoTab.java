/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import static LabDev.LabDevelopment_.TABLE__MAT_INFO;
import MCRecipe.Lang.LAB_DEV;
import MCRecipe.Lang.T_INV;
import MCRecipe.SQL_A;
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
import javax.swing.JTable;

/**
 *
 * @author KOCMOC
 */
public class LabDevMaterialInfoTab extends LabDevTab_ implements ActionListener, MouseListener {

    private TableBuilderInvert TABLE_BUILDER_INVERT;

    public LabDevMaterialInfoTab(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment_ labDev) {
        super(sql, sql_additional, OUT, labDev);
        init();
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
            removeMaterial();
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

    private void removeMaterial() {

    }

    private void addMaterial() {
        //
        String order = labDev.getOrderNo();
        String material = HelpA.getComboBoxSelectedValue(getComboBox());
        //
        String q = SQL_A.lab_dev__material_info__add_material(PROC.PROC_79, order, material);
        //
        try {
            sql.execute(q, OUT);
        } catch (SQLException ex) {
            Logger.getLogger(LabDevelopment_.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        fillJTable();
        //
    }

    private void fillJTable() {
        //
        JTable table = getJTable();
        //
        String q = SQL_A.get_lab_dev_jtable_material_info(PROC.PROC_68, labDev.getOrderNo(), null);
        HelpA.build_table_common(sql, OUT, table, q, new String[]{"ID", "MCcode", "UpdatedOn", "UpdatedBy", "WORDERNO", "PlanID"});
        //
        LAB_DEV.material_information_tab_change_jtable__header(table);
        //
        HelpA.markFirstRowJtable(table);
        materialInfoJTableClicked();
        //
    }

    public void materialInfoJTableClicked() {
        showTableInvert();
        String material = HelpA.getValueSelectedRow(getJTable(), "Material").trim();
        labDev.setMaterial(material);
        labDev.refreshHeader();
    }

    private void fillComboBox() {
        //
        String q = SQL_A.lab_dev__material_info__add_material_combo();
        HelpA.fillComboBox(sql, getComboBox(), q, null, true, false);
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
//            String q = SQL_A.get_lab_dev_tinvert_material_info(id);
            String q = SQL_A.get_lab_dev_jtable_material_info(PROC.PROC_68, null, id);
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

}
