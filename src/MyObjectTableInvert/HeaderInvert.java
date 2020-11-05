/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import java.awt.Color;
import javax.swing.JLabel;

/**
 *
 * @author mcab
 */
public class HeaderInvert {

    private final Object header;
    private boolean unitHeader = false;
    private String realColName;
    private String tableName;
    private JLabel displayLabel;
    private boolean isImportant = false;
    private Color initialFontColor;

    public HeaderInvert(String header, String realColName, String tableName) {
        this.header = header;
        this.realColName = realColName;
        this.tableName = tableName;
    }

    public HeaderInvert(Object header, boolean unitHeader) {
        this.header = header;
        this.unitHeader = unitHeader;
    }

    public void setHeaderLabelComponent(JLabel label, boolean isImportant) {
        this.displayLabel = label;
        this.isImportant = isImportant;
        this.initialFontColor = label.getForeground();
    }

    public void setNotValidated() {
        displayLabel.setForeground(Color.red);
    }
    
    public void setNotValidated(Color color) {
        displayLabel.setForeground(color);
    }
    
    public void setValidated(){
        displayLabel.setForeground(Color.black);
    }


    public JLabel getHeaderPanelComponent() {
        return this.displayLabel;
    }

    public Object getHeader() {
        return header;
    }

    public boolean isUnitHeader() {
        return unitHeader;
    }

    public String getRealColName() {
        return realColName;
    }

    public String getTableName() {
        return tableName;
    }

}
