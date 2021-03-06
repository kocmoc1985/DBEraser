/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import MCRecipeLang.LAB_DEV;
import MCRecipeLang.LNG;
import MCRecipeLang.MSG;
import MCRecipe.MC_RECIPE;
import MCRecipe.SQL_A_;
import forall.HelpA;
import forall.SqlBasicLocal;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author KOCMOC
 */
public class LabDevHeaderComponent implements ItemListener {

    private final JPanel header;
    private final SqlBasicLocal sql;
    private final LabDevelopment_ labDev;

    public LabDevHeaderComponent(JPanel header, SqlBasicLocal sql, LabDevelopment_ labDev) {
        this.header = header;
        this.sql = sql;
        this.labDev = labDev;
    }

    private JPanel getUpper() {
        return (JPanel) header.getComponent(0);
    }

    private JPanel getLower() {
        return (JPanel) header.getComponent(1);
    }

    private void updateGraphics() {
        header.repaint();
        header.updateUI(); // OBS! Needed
    }

    private void clear() {
        getUpper().removeAll();
        getLower().removeAll();
        updateGraphics();
    }

    public void tab_notes() {
        tab_main_data();
    }

    public void tab_material_info() {
//        tab_main_data();
        tab_test_defenition();
    }

    public void tab_main_data() {
        clear();
        showStandard(false);
        updateGraphics();
    }

    public void tab_status() {
        clear();
        showStandard(true);
        showStatusTab();
        updateGraphics();
    }

    public void tab_find_order() {
        clear();
        showOrderNo_and_material();
        updateGraphics();
    }

    public void tab_test_defenition() {
        clear();
        showStandard(false);
        showTestDefTab();
        updateGraphics();
    }

    public void tab_test_config() {
        clear();
//        showOrderNo_and_material();
        showStandard(false);
        showTestConfigTab();
        updateGraphics();
    }

    public void tab_age_vulc() {
        clear();
//        showStandard(false);
        updateGraphics();
    }

    public void tab_test_procedure() {
        clear();
        showStandard(false);
        updateGraphics();
    }

    public void tab_test_variables() {
//        clear();
//        showStandard(false);
//        updateGraphics();
        tab_test_defenition();
    }

    private void showTestConfigTab() {
        //
        JPanel lower = getLower();
        //
        buildJLabelJTextFieldComonent(lower, LAB_DEV.LBL_7(), labDev.getMaterial(), false);
        //
        buildJLabelJTextFieldComonent(lower, LAB_DEV.LBL_7_2(), labDev.getTestCode(), false);
        //
        for (int i = 0; i < 3; i++) {
            lower.add(new JPanel()); // Adding empty for compacting 
        }
        //
    }

    private void showTestDefTab() {
        //
        JPanel lower = getLower();
        //
        buildJLabelJTextFieldComonent(lower, LAB_DEV.LBL_7(), labDev.getMaterial(), false);
        //
        buildJLabelJTextFieldComonent(lower, LAB_DEV.LBL_8(), labDev.getMaterial_description(), true);
        //
        buildJLabelJTextFieldComonent(lower, LAB_DEV.LBL_9(), labDev.getMaterial_batchammount(), false);
        //
        buildJLabelJTextFieldComonent(lower, LAB_DEV.LBL_7_2(), labDev.getTestCode(), false);
        //
        for (int i = 0; i < 1; i++) {
            lower.add(new JPanel()); // Adding empty for compacting 
        }
        //
    }

    private void showStatusTab() {
        //
        JPanel lower = getLower();
        //
        buildJLabelJTextFieldComonent(lower, LAB_DEV.LBL_4(), labDev.getRequester(), false);
        //
        for (int i = 0; i < 4; i++) {
            lower.add(new JPanel()); // Adding empty for compacting 
        }
        //
    }

    private void showOrderNo_and_material() {
        //
        JPanel upper = getUpper();
        //
        buildJLabelJTextFieldComonent(upper, LAB_DEV.LBL_1(), labDev.getOrderNo(), false);
        buildJLabelJTextFieldComonent(upper, LAB_DEV.LBL_7(), labDev.getMaterial(), false);
        buildJLabelJTextFieldComonent(upper, LAB_DEV.LBL_8(), labDev.getMaterial_description(), true);
        buildJLabelJTextFieldComonent(upper, LAB_DEV.LBL_9(), labDev.getMaterial_batchammount(), false);
        //
        for (int i = 0; i < 1; i++) {
            upper.add(new JPanel()); // Adding empty for compacting 
        }
        //
    }

    /**
     * For "Kopfdaten / Main Data" also probably suits for other
     */
    private void showStandard(boolean statusJComboEnabled) {
        //
        JPanel upper = getUpper();
        //
        buildJLabelJTextFieldComonent(upper, LAB_DEV.LBL_1(), labDev.getOrderNo(), false);
        //
        //
        buildJLabelJComboComponent(upper, LAB_DEV.LBL_2(), LAB_DEV__STATUS.getLabDevStatusesAuto(LNG.LANG_ENG), statusJComboEnabled);
        //
        buildJLabelJTextFieldComonent(upper, LAB_DEV.LBL_5(), labDev.getUpdatedOn(), false);
        //
        buildJLabelJTextFieldComonent(upper, LAB_DEV.LBL_6(), labDev.getUpdatedBy(), false);
        //
        for (int i = 0; i < 1; i++) {
            upper.add(new JPanel()); // Adding empty for compacting 
        }
        //
    }

    private void buildJLabelJTextFieldComonent(JPanel upperOrLower, String jLabelVal, String jTextFieldVal, boolean toolTip) {
        //
        JLabel label = buildStandardLabel(jLabelVal);
        //
        JTextField val = new JTextField(jTextFieldVal);
        val.setEditable(false);
        //
        if (toolTip == true) {
            val.setToolTipText(jTextFieldVal);
        }
        //
        JPanel container = new JPanel(new GridLayout(1, 2));
        container.add(label);
        container.add(val);
        upperOrLower.add(container);
    }

    private void buildJLabelJComboComponent(JPanel upperOrLower, String jLabelVal, String[] values, boolean statusJComboEnabled) {
        //
        JLabel label = buildStandardLabel(jLabelVal);
        //
        JComboBox box = new JComboBox();
        box = HelpA.fillComboBox_simple(box, values, null);
        box.setEditable(true);
        box.setEnabled(statusJComboEnabled);
        //
        String actual_status = getStatusInActLang();
        box.setSelectedItem(actual_status);
        //
        JPanel container = new JPanel(new GridLayout(1, 2));
        container.add(label);
        box.addItemListener(this);
        container.add(box);
        upperOrLower.add(container);
    }

    private JLabel buildStandardLabel(String jLabelVal) {
        JLabel label = new JLabel(jLabelVal);
        label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        HelpA.increase_font(label, 14);
        return label;
    }

    private String getStatusInActLang() {
        String status_eng = HelpA.getSingleParamSql(sql, LabDevelopment_.TABLE__MC_CPWORDER, "WORDERNO", labDev.getOrderNo(), "WOSTATUS", false);
        String status_act_lang = LAB_DEV__STATUS.getStatusActLang(LNG.LANG_ENG, status_eng);
        return status_act_lang;
    }

    private boolean saveStatus(Object item) {
        //
        if (MC_RECIPE.isAdminOrDeveloper() == false) {
            return false;
        }
        //
        String status = item.toString();
        //
        //OBS! VERY IMPORATN -> WHEN SAVING STATUS IT SHOULD BE ALWAYS "ENG"
        String status_eng = LAB_DEV__STATUS.getStatusEng(LNG.LANG_ENG, status);
        //
        if (HelpA.confirm(MSG.MSG_4(status)) == false) {
            return false;
        }
        //
        String q = SQL_A_.save_status_lab_dev(status_eng, labDev.getOrderNo());
        //
        System.out.println("q: " + q);
        //
        try {
            sql.execute(q);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LabDevHeaderComponent.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //
    }

    private Object prevItemComboBox = "";
    private boolean skipOnce = false;

    @Override
    public void itemStateChanged(ItemEvent e) {
        //
//        System.out.println("BOX ITEM STATE CHANGED: " + e.getItem() + " / " + e.getStateChange());
        //
        if (e.getStateChange() == 2) {
            prevItemComboBox = e.getItem();
        }
        //
        if (e.getStateChange() == 1) {
            //
            if (skipOnce) {
                skipOnce = false;
                return;
            }
            //
            if (saveStatus(e.getItem()) == false) { // if user choses "No"
                //
                skipOnce = true;
                //
                JComboBox box = (JComboBox) e.getSource();
                box.setSelectedItem(prevItemComboBox);
                //
            } else { // if user choses "Yes"
                //
                labDev.refreshHeader();
                //
            }
        }
        //
    }

}
