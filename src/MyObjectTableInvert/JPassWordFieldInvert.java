/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import forall.JPasswordFieldCopyPaste;

/**
 *
 * @author KOCMOC
 */
public class JPassWordFieldInvert extends JPasswordFieldCopyPaste implements JLinkInvert{

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
    
    @Override
    public String getValue() {
        return new String(getPassword());
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
    
   public void setSaveEmptyStringValue(){
       //
       RowDataInvert rdi = parent.getRowConfig();
       //
       if(rdi.isString() && getValue().isEmpty()){
           rdi.setSaveEmptyStringValue();
       }
       //
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
