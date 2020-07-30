/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

/**
 * It's named JLinkInvert -> "Link" because it's a link between a Graphical 
 * component and it's child and parent: [Child: ColumnDataEntryInvert]<--[JTextFieldInvert]-->[Parent: TableRowInvert]
 * @author KOCMOC
 */
public interface JLinkInvert {

    public void setChildObject(ColumnDataEntryInvert child);
    
    public ColumnDataEntryInvert getChildObject();
    
    public void setParentObj(TableRowInvert rdi);
    
    public TableRowInvert getParentObj();
    
}
