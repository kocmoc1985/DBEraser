/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import java.awt.Color;
import javax.swing.JComponent;

/**
 *
 * @author mcab
 */
public class ColumnDataEntryInvert {

    private Object object;
    private final String database_id;
    private final String original_column_name;
    private final String column_nick_name;
    private boolean isUpdated = false;
    private boolean validated = true;
    private String initialValue;
    private Object parent;

    public ColumnDataEntryInvert(Object object, String database_id, String originalColumnName, String columnNickName) {
        this.object = object;
        this.database_id = database_id;
        this.original_column_name = originalColumnName;
        this.column_nick_name = columnNickName;
    }

    /**
     * [2020-08-03][2020-10-09] This is only in case of "JTextFieldInvert" &
     * "JPassWordFieldInvert"
     *
     * @param parent
     */
    public void setParent(Object parent) {
        this.parent = parent;
    }

    public Object getParent() {
        return this.parent;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public boolean isValidated() {
        return validated;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean isUpdated) {
        this.isUpdated = isUpdated;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void markInvalidated() {
        //
        if (parent != null && parent instanceof JComponent) {
            JComponent jc = (JComponent) parent;
            jc.setBackground(Color.red);
        }
        //
        if (object instanceof JComboBoxInvert) {
            JComboBoxInvert jcbi = (JComboBoxInvert) object;
            jcbi.setBackground(Color.red);
        }
        //
    }

    /**
     * [2020-08-03]
     *
     * @return
     */
    public String getActualValue() {
        //
        if (parent != null && parent instanceof JTextFieldInvert) { // This is only in case of "JTextFieldInvert"
            JTextFieldInvert jpi = (JTextFieldInvert) parent;
            return jpi.getValue();
        } else if (object instanceof JLinkInvert) {
            JLinkInvert jpi = (JLinkInvert) object;
            return jpi.getValue();
        } else {
            return null;
        }
        //
    }
    
    public void setValue(String value){
        if (parent != null && parent instanceof JTextFieldInvert) {
            JTextFieldInvert jpi = (JTextFieldInvert) parent;
            jpi.setValue(value);
        }
    }

    public Object getObject() {
        //
        // Setting this as child of parent JComponent here [2020-07-30]
        //
        if (object instanceof JLinkInvert) {
            JLinkInvert jpi = (JLinkInvert) object;
            jpi.setChildObject(this);
            initialValue = jpi.getValue();
        } else {
            initialValue = (String) object; // This one in case of "JTextFieldInvert" see: "TableRowInvert.class -> addColumn(Object obj) -> line: 112"
        }
        //
        // OBS! OBS! For the JTextFieldInvert the "setChildObject()" is done from
        // "TableRowInvert.class -> addColumn(Object obj) -> line: 112" [2020-07-30]
        //
        return object;
        //
    }

    /**
     * [2020-07-30] Get INITIAL value from JTextFieldInvert, JComboBoxInvert or
     * other components implementing "JLinkInvert" interface.
     *
     * So the initial means the value which was "there" when the table was built
     *
     * @return
     */
    public String getInitialValue() {
        return initialValue;
    }

    public String getDatabase_id() {
        return database_id;
    }

    public String getOriginalColumn_name() {
        return original_column_name;
    }

    public String getColumn_nick_name() {
        return column_nick_name;
    }

}
