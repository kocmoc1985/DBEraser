/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import MCRecipe.Lang.LAB_DEV;
import forall.HelpA_;
import forall.SqlBasicLocal;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author KOCMOC
 */
public class LabDevHeaderComponent {

    private final JPanel header;
    private final SqlBasicLocal sql;
    private final LabDevelopment labDev;

    public LabDevHeaderComponent(JPanel header, SqlBasicLocal sql, LabDevelopment labDev) {
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
        header.updateUI();
    }

    private void clear() {
        getUpper().removeAll();
        getLower().removeAll();
        updateGraphics();
    }

    public void tab_notes() {
        tab_main_data();
    }
    
    public void tab_material_info(){
        tab_main_data();
    }

    public void tab_main_data() {
        clear();
        showStandard();
        updateGraphics();
    }

    public void tab_status() {
        clear();
        showStatusTab();
        updateGraphics();
    }

    private void showStatusTab() {
        //
        showStandard();
        //
        //
        JPanel lower = getLower();
        //
        buildJLabelJTextFieldComonent(lower, LAB_DEV.LBL_4(), labDev.getRequester());
        //
        buildJLabelJTextFieldComonent(lower, LAB_DEV.LBL_3(), "");
        //
        for (int i = 0; i < 3; i++) {
            lower.add(new JPanel()); // Adding empty for compacting 
        }
        //
    }

    /**
     * For "Kopfdaten / Main Data" also probably suits for other
     */
    private void showStandard() {
        //
        JPanel upper = getUpper();
        //
        buildJLabelJTextFieldComonent(upper, LAB_DEV.LBL_1(), labDev.getOrderNo());
        //
//        buildJLabelJTextFieldComonent(upper, LAB_DEV.LBL_2(), "Ausführen");
        String[] statuses = new String[]{LAB_DEV.LANG("Input"), LAB_DEV.LANG("Requested"),
            LAB_DEV.LANG("Approved"), LAB_DEV.LANG("Execute"), LAB_DEV.LANG("Ready"),
            LAB_DEV.LANG("Archive"), LAB_DEV.LANG("Abort")};
        //
        buildJLabelJComboComponent(upper, LAB_DEV.LBL_2(), statuses);
        //
        buildJLabelJTextFieldComonent(upper, LAB_DEV.LBL_5(), labDev.getUpdatedOn());
        //
        buildJLabelJTextFieldComonent(upper, LAB_DEV.LBL_6(), labDev.getUpdatedBy());
        //
        for (int i = 0; i < 1; i++) {
            upper.add(new JPanel()); // Adding empty for compacting 
        }
        //
    }

    private void buildJLabelJTextFieldComonent(JPanel upperOrLower, String jLabelVal, String jTextFieldVal) {
        //
        JLabel label = buildStandardLabel(jLabelVal);
        //
        JTextField val = new JTextField(jTextFieldVal);
        val.setEditable(false);
        JPanel container = new JPanel(new GridLayout(1, 2));
        container.add(label);
        container.add(val);
        upperOrLower.add(container);
    }

    private void buildJLabelJComboComponent(JPanel upperOrLower, String jLabelVal, String[] values) {
        //
        JLabel label = buildStandardLabel(jLabelVal);
        //
        JComboBox box = new JComboBox();
        box = HelpA_.fillComboBox_simple(box, values, null);
        box.setEditable(true);
        //
        JPanel container = new JPanel(new GridLayout(1, 2));
        container.add(label);
        container.add(box);
        upperOrLower.add(container);
    }

    private JLabel buildStandardLabel(String jLabelVal) {
        JLabel label = new JLabel(jLabelVal);
        label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        HelpA_.increase_font(label, 14);
        return label;
    }

}
