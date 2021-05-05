/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import forall.HelpA;
import forall.JComboBoxA;

/**
 * This class is just to be able to distinguish between a box used for
 * TableInvert and one not used there
 *
 * @author KOCMOC
 */
public class JComboBoxInvert extends JComboBoxA implements JLinkInvert {

    private ColumnDataEntryInvert child;
    private TableRowInvert parent;

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

    public String getValue(int paramToReturn, int resetParamToReturn) {
        return HelpA.getComboBoxSelectedValue(this, paramToReturn, resetParamToReturn);
    }

    public String getValue(int paramToReturn) {
        return HelpA.getComboBoxSelectedValue(this, paramToReturn, -1);
    }

    @Override
    public String getValue() {
        return HelpA.getComboBoxSelectedValue(this);
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
    public int getInputLengthValidationManual() {
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
