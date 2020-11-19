/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author KOCMOC
 */
public class JPanelPrepM_C extends JPanelPrepM {

    private int counter = 0;
    private String description;
    private String testCode;
    private JTextField jtf2_test_code;
    private JTextField jtf_description;
    
    public JPanelPrepM_C(LayoutManager lm, boolean extractSequenceNr) {
        super(lm, extractSequenceNr);
    }

    public String getTestCode() {
        return testCode;
    }
    
    public void setHighLighted(){
        //
//        jtf_description.setEnabled(false);
//        jtf2_test_code.setEnabled(false);
        //
        jtf_description.setBackground(new Color(224, 226, 231));
        jtf2_test_code.setBackground(new Color(224, 226, 231));
        //
        setSetSelectedCheckBox();
    }

    @Override
    public Component add(Component comp) {
        //
        if (comp instanceof JTextField) {
            //
            if (counter == 0) {
                //
                this.jtf = (JTextField) comp;
                //
                this.jtf_description = (JTextField) comp;
                this.description = jtf_description.getText();
                //
            } else if (counter == 1) {
                this.jtf2_test_code = (JTextField) comp;
                this.testCode = jtf2_test_code.getText();
            }
            //
            counter++;
            //
        } else if (comp instanceof JCheckBox) {
            this.chkBox = (JCheckBox) comp;
        } else if (comp instanceof JLabel) {
            this.lbl = (JLabel) comp;
            extractSequenceNr(lbl);
        }
        //
        return super.add(comp); //To change body of generated methods, choose Tools | Templates.
    }

}
