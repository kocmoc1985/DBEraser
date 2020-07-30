/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import javax.swing.JTextField;

/**
 *
 * @author KOCMOC
 */
public class JTextFieldInvert extends JTextField implements JParentInvert {

    private ColumnDataEntryInvert child;

    public JTextFieldInvert(String text) {
        super(text);
    }
    
    @Override
    public void setChild(ColumnDataEntryInvert child) {
        this.child = child;
    }

    @Override
    public ColumnDataEntryInvert getChildObject() {
        return child;
    }

}
