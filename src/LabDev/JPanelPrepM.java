/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import MCRecipe.Lang.LNG;
import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author KOCMOC
 */
public class JPanelPrepM extends JPanel {

    protected JTextField jtf;
    protected JCheckBox chkBox;
    protected JLabel lbl;
    protected int sequenceNr;
    private final boolean extractSequenceNr;

    public JPanelPrepM(LayoutManager lm, boolean extractSequenceNr) {
        super(lm);
        this.extractSequenceNr = extractSequenceNr;
    }

    @Override
    public String toString() {
        if (extractSequenceNr == false) {
            return isSelected() + " / " + lbl.getText();
        } else {
            return "" + sequenceNr + " / " + isSelected() + " / " + jtf.getText();
        }

    }

    @Override
    public Component add(Component comp) {
        //
        if (comp instanceof JTextField) {
            this.jtf = (JTextField) comp;
        } else if (comp instanceof JCheckBox) {
            this.chkBox = (JCheckBox) comp;
        } else if (comp instanceof JLabel) {
            this.lbl = (JLabel) comp;
            extractSequenceNr(lbl);
        }
        //
        return super.add(comp); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isSelected() {
        return this.chkBox.isSelected();
    }
    
    public void setSetSelectedCheckBox(){
        this.chkBox.setSelected(true);
    }

    public String getValue() {
        if (extractSequenceNr == false) {
            return this.lbl.getText();
        } else {
            return this.jtf.getText();
        }
    }

    public String getStatusEng() {
        if (extractSequenceNr == false) {
            return LAB_DEV__STATUS.getStatusEng(LNG.LANG_ENG, this.lbl.getText());
        } else {
            return LAB_DEV__STATUS.getStatusEng(LNG.LANG_ENG, this.jtf.getText());
        }
    }

    protected void extractSequenceNr(JLabel lbl) {
        //
        if (extractSequenceNr == false) {
            this.sequenceNr = -1;
            return;
        }
        //
        String txt = lbl.getText();
        String rst = txt.replaceAll(":", "").trim();
        //
        if (rst.isEmpty() == false) {
            this.sequenceNr = Integer.parseInt(rst);
        } else {
            this.sequenceNr = 0;
        }
        //
//        System.out.println("seq nr: " + this.sequenceNr);
        //
    }

}
