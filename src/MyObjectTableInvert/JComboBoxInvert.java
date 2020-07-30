/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import forall.JComboBoxA;


/**
 * This class is just to be able to distinguish between a box used for
 * TableInvert and one not used there
 *
 * @author KOCMOC
 */
public class JComboBoxInvert extends JComboBoxA implements JLinkInvert{
 
    private ColumnDataEntryInvert child;
    private TableRowInvert parent;
    
    @Override
    public void setChildObject(ColumnDataEntryInvert child){
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
  
}
