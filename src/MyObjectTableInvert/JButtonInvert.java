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
public class JButtonInvert extends JButton implements JParentInvert { 

    private ColumnDataEntryInvert child;

    @Override
    public void setChild(ColumnDataEntryInvert child) {
        this.child = child;
    }

    @Override
    public ColumnDataEntryInvert getChildObject() {
        return child;
    }
}
