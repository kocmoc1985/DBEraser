/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import forall.HelpA;

/**
 *
 * @author mcab
 */
public class ColumnValue {

    private String columnNickName;
    private String columnOriginalName;
    final Object value;

    public ColumnValue(Object value) {
        this.value = value;
    }

    public ColumnValue(String columnNickName, String columnOriginalName, Object value) {
        this.columnNickName = columnNickName;
        this.columnOriginalName = columnOriginalName;
        this.value = value;
    }

    public String getColumnNickName() {
        return columnNickName;
    }


    public ColumnDataEntryInvert getColumnDataEntryInvert() {
        if (value instanceof JLinkInvert) {
            JLinkInvert jpi = (JLinkInvert) value;
            return jpi.getChildObject();
        } else {
            return null;
        }
    }

    public String getValue() {
        if (value instanceof JLabelInvert) {
            JLabelInvert label = (JLabelInvert) value;
            return label.getText();
        } else if (value instanceof JComboBoxInvert) {
            JComboBoxInvert comboBox = (JComboBoxInvert) value;
            return HelpA.getComboBoxSelectedValue(comboBox);
        } else if (value instanceof JTextFieldInvert || value instanceof JPassWordFieldInvert) {
            JLinkInvert jli = (JLinkInvert)value;
            return jli.getValue();
        } else if (value instanceof JButtonInvert) {
            JButtonInvert jb = (JButtonInvert) value;
            return (String) jb.getText();
        } else {
            return null;
        }
    }

    public String getValue(int paramToReturn) {
        if (value instanceof JLabelInvert) {
            JLabelInvert label = (JLabelInvert) value;
            return label.getText();
        } else if (value instanceof JComboBoxInvert) {
            JComboBoxInvert comboBox = (JComboBoxInvert) value;
//            return HelpA.getComboBoxSelectedValue(comboBox);
            return HelpA.getComboBoxSelectedValue(comboBox, paramToReturn);
        } else if (value instanceof JTextFieldInvert) {
            JTextFieldInvert jtf = (JTextFieldInvert) value;
            return (String) jtf.getText();
        } else if (value instanceof JButtonInvert) {
            JButtonInvert jb = (JButtonInvert) value;
            return (String) jb.getText();
        } else {
            return null;
        }
    }

}
