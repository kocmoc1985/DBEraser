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
public class JTextFieldInvert extends JTextField implements JLinkInvert {
    
    private ColumnDataEntryInvert child;
    private TableRowInvert parent;
    
    public JTextFieldInvert(String text) {
        super(text);
    }
    
    @Override
    public void setChildObject(ColumnDataEntryInvert child) {
        this.child = child;
    }
    
    @Override
    public ColumnDataEntryInvert getChildObject() {
        return child;
    }
    
    @Override
    public void setParentObj(TableRowInvert rdi) {
        this.parent = rdi;
    }
    
    @Override
    public TableRowInvert getParentObj() {
        return this.parent;
    }
    
    @Override
    public String getValue() {
        return getText();
    }
    
    public void setValue(String value){
        setText(value);
    }
    
    @Override
    public boolean valueUpdated() {
        return !child.getInitialValue().equals(getValue());
    }
    
    @Override
    public void setFieldUpdatedAuto() {
        if (valueUpdated()) {
            child.setUpdated(true);
        } else {
            child.setUpdated(false);
        }
    }
    
    @Override
    public void setValidated(boolean validated) {
        child.setValidated(validated);
    }
    
}
