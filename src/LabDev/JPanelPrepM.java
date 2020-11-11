/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

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
public class JPanelPrepM extends JPanel{

    private JTextField jtf;
    private JCheckBox chkBox;
    private JLabel lbl;
    private int sequenceNr;
    private final boolean extractSequenceNr;
    
    public JPanelPrepM(LayoutManager lm,boolean extractSequenceNr) {
        super(lm);
        this.extractSequenceNr = extractSequenceNr;
    }

    @Override
    public String toString() {
        return "" + sequenceNr + " / " + isSelected() + " / " + jtf.getText();
    }
    

    @Override
    public Component add(Component comp) {
        //
        if(comp instanceof JTextField){
            this.jtf = (JTextField)comp;
        }else if(comp instanceof JCheckBox){
            this.chkBox = (JCheckBox)comp;
        }else if(comp instanceof JLabel){
            this.lbl = (JLabel)comp;
            extractSequenceNr(lbl);
        }
        //
        return super.add(comp); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public boolean isSelected(){
        return this.chkBox.isSelected();
    }
    
    public String JTextField(){
        return this.jtf.getText();
    }
    
    private void extractSequenceNr(JLabel lbl){
        //
        if(extractSequenceNr == false){
            this.sequenceNr = -1;
            return;
        }
        //
        String txt = lbl.getText();
        String rst = txt.replaceAll(":", "").trim();
        //
        if(rst.isEmpty() == false){
            this.sequenceNr = Integer.parseInt(rst);
        }else{
            this.sequenceNr = 0;
        }
        //
//        System.out.println("seq nr: " + this.sequenceNr);
        //
    }
    
    
    
}
