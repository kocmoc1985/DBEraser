/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import forall.HelpA_;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author mcab
 */
public class UnsavedEntryInvert {

    private final Object dataField;
    private final String tableName;
    private final String primareyOrForeignKeyName;
    private final String db_id;
    private final String columnName;
    private final int columnNr;
    private String value;
    private final boolean isString;
    private final boolean keyIsString;
    private final String updateOtherTablesBefore;

    public UnsavedEntryInvert(Object data_field,
            String tableName,
            String primareyOrForeignKeyName,
            String db_id,
            String columnName,
            int columnNr,
            boolean isString,
            boolean keyIsString,
            String updateOtherTablesBefore) {
        this.dataField = data_field;
        this.tableName = tableName;
        this.primareyOrForeignKeyName = primareyOrForeignKeyName;
        this.db_id = db_id;
        this.columnName = columnName;
        this.columnNr = columnNr;
        this.isString = isString;
        this.keyIsString = keyIsString;
        this.updateOtherTablesBefore = updateOtherTablesBefore;
        getValueFromDataField(dataField);
    }

    private void getValueFromDataField(Object dataField) {
        if (dataField instanceof JLabel) {
            JLabel jLabel = (JLabel) dataField;
            this.value = jLabel.getText();
        } else if (dataField instanceof JTextField) {
            JTextField jtf = (JTextField) dataField;
//            jtf.setForeground(Color.red);
            HelpA_.font_change_type(jtf, Font.ITALIC);
            this.value = jtf.getText();
        } else if (dataField instanceof JComboBox) {
            JComboBox comboBox = (JComboBox) dataField;
            this.value = HelpA_.getComboBoxSelectedValue(comboBox);
//            comboBox.setBorder(BorderFactory.createLineBorder(Color.red,2));
            HelpA_.font_change_type(comboBox, Font.ITALIC);
        }
    }

    public int getColumnNr() {
        return columnNr;
    }

    public String getUpdateOtherTablesBefore() {
        return updateOtherTablesBefore;
    }
    
    public boolean isString() {
        return this.isString;
    }

    public boolean keyIsString() {
        return keyIsString;
    }

    public Object getDataField() {
        return dataField;
    }

    public String getTableName() {
        return tableName;
    }

    public String getPrimareyOrForeignKeyName() {
        return primareyOrForeignKeyName;
    }

    public String getDbID() {
        return db_id;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getValue() {
        return value;
    }

    public static String delete_last_letter_in_string(String str) {
        int a = str.length() - 1;
        return str.substring(0, a);
    }
}
