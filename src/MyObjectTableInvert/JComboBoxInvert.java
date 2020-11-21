/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import forall.HelpA_;
import forall.JComboBoxA;

/**
 * This class is just to be able to distinguish between a box used for
 * TableInvert and one not used there
 *
 * @author KOCMOC
 */
public class JComboBoxInvert extends JComboBoxA implements JLinkInvert {

    private ColumnDataEntryInvert child;
    private TableRowInvert_ parent;

    @Override
    public void setChildObject(ColumnDataEntryInvert child) {
        this.child = child;
    }

    @Override
    public ColumnDataEntryInvert getChildObject() {
        return child;
    }

    @Override
    public void setParentObj(TableRowInvert_ rdi) {
        this.parent = rdi;
    }

    @Override
    public TableRowInvert_ getParentObj() {
        return this.parent;
    }

    public String getValue(int paramToReturn){
        return HelpA_.getComboBoxSelectedValue(this,paramToReturn);
    }
    
    @Override
    public String getValue() {
        return HelpA_.getComboBoxSelectedValue(this);
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

    @Override
    public boolean getValidateDate() {
        return false;
    }

    @Override
    public int getInputLengthValidation() {
        return 0;
    }
    
     @Override
    public void setFieldVarcharLength(int length) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getFieldVarcharLength() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
