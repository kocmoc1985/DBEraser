/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import MyObjectTableInvert.ColumnDataEntryInvert;
import MyObjectTableInvert.JLinkInvert;
import MyObjectTableInvert.TableRowInvert;
import javax.swing.JTextArea;

/**
 * OBS! Not used by "TableInvert"
 * @author KOCMOC
 */
public class JTextAreaJLink extends JTextArea implements JLinkInvert{

    private boolean validated;
    
    @Override
    public void setChildObject(ColumnDataEntryInvert child) {
    }

    @Override
    public ColumnDataEntryInvert getChildObject() {
        return null;
    }

    @Override
    public void setParentObj(TableRowInvert rdi) {
    }

    @Override
    public TableRowInvert getParentObj() {
        return null;
    }

    @Override
    public String getValue() {
        return getText();
    }

    @Override
    public boolean valueUpdated() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setFieldUpdatedAuto() {
        
    }

    @Override
    public void setValidated(boolean validated) {
        this.validated = validated;
    }
    
    public boolean getValidated(){
        return validated;
    }
    
}
