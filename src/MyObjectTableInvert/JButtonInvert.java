/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import javax.swing.JButton;

/**
 *
 * @author KOCMOC
 */
public class JButtonInvert extends JButton implements JLinkInvert {

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
        return getText();
    }

    @Override
    public boolean valueUpdated() {
        return !child.getInitialValue().equals(getValue());
    }

}
