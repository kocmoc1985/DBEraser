/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import MyObjectTable.RowData;
import forall.HelpA;
import forall.SqlBasicLocal;
import javax.swing.JPasswordField;

/**
 *
 * @author mcab
 */
public class RowDataInvert extends RowData {

    public static final int TYPE_COMMON = 0;
    public static final int TYPE_JCOMBOBOX = 1;
    public static final int TYPE_JBUTTON = 2;
    public static final int TYPE_JLABEL = 3;
    public static final int TYPE_JCOMBOBOX_PREFILED = 4;
    public static final int TYPE_JPASSWORD_FIELD = 5;
    //
    protected int type = 0;
    protected String additionalInfo;
    private SqlBasicLocal sql;
    private String updateOtherTablesBefore = "";
    //
    private String tableName;
    private String primaryOrForeignKey;
    protected String fieldOrigName;
    protected String fieldNickName;
    protected Object unitOrObject;
    protected boolean visible;
    protected boolean important;
    protected boolean isString;
    private boolean keyIsString;
    //
    private boolean editable = true;
    private boolean enabled = true;
    private boolean jTextFieldToolTipText = false;
    private String  toolTipTextFixed = null;
    private boolean comboBoxMultipleValue = false;
    private boolean comboBoxFakeValue = false;
    private boolean comboBoxFixedValue = false;
    private boolean comboBoxFixedValueAdvanced = false;
    //
    private boolean aquire_tableInvertToHashMap = true; // Look at method Basic.class-> "tableInvertToHashMap"
    //
    private boolean save_EmptyStringValue = false;
    //
    private boolean enableEmptyValue = false;

    public RowDataInvert() {
    }

    /**
     *
     * @param tableName
     * @param primaryOrForeignKey = this key shall be present in the query with
     * the same name as in the original table to make it work
     * @param keyIsString
     * @param field_original_name = it's very important that it's the original
     * name, be careful when using "AS" in query
     * @param field_nick_name
     * @param unitOrObject
     * @param string
     * @param visible
     * @param important
     */
    public RowDataInvert(
            String tableName,
            String primaryOrForeignKey,
            boolean keyIsString,
            String field_original_name,
            String field_nick_name,
            Object unitOrObject,
            boolean string,
            boolean visible,
            boolean important) {
        this.tableName = tableName;
        this.primaryOrForeignKey = primaryOrForeignKey;
        this.keyIsString = keyIsString;
        this.fieldOrigName = field_original_name;
        this.fieldNickName = field_nick_name;
        this.unitOrObject = unitOrObject;
        this.isString = string;
        this.visible = visible;
        this.important = important;
    }

    /**
     * Extended constructor for Objects other then "String", for example
     * JComboBoxes or...
     *
     * @param type - type is an "int" which defines the Component type to be
     * used
     * @param additionalInfo - in case of JComboBox it's a sql query
     * @param sql
     * @param updateOtherTablesBefore - you shall pass a command like
     * "sql_cmd_1", which will mean that other tables must be updated before you
     * update your primary table
     */
    public RowDataInvert(
            int type,
            String additionalInfo,
            SqlBasicLocal sql,
            String updateOtherTablesBefore,
            String tableName,
            String primaryOrForeignKey,
            boolean keyIsString,
            String field_original_name,
            String field_nick_name,
            String unit,
            boolean string,
            boolean visible,
            boolean important) {
        //
        this.type = type;
        this.additionalInfo = additionalInfo;
        this.sql = sql;
        this.updateOtherTablesBefore = updateOtherTablesBefore;
        //
        this.tableName = tableName;
        this.primaryOrForeignKey = primaryOrForeignKey;
        this.keyIsString = keyIsString;
        this.fieldOrigName = field_original_name;
        this.fieldNickName = field_nick_name;
        this.unitOrObject = unit;
        this.isString = string;
        this.visible = visible;
        this.important = important;
    }
    
    private boolean save_EmptyNumber = false;
    public void setSaveEmptyNumber(){
        this.save_EmptyNumber = true;
    }
    
    public boolean saveEmptyNumber(){
        return save_EmptyNumber;
    }

    public void setSaveEmptyStringValue() {
        save_EmptyStringValue = true;
    }

    public boolean saveEmptyStringValue() {
        return save_EmptyStringValue;
    }

    public void setDontAquireTableInvertToHashMap() {
        aquire_tableInvertToHashMap = false;
    }

    public boolean aquire() {
        return aquire_tableInvertToHashMap;
    }

    public void enableFixedValues() {
        comboBoxFixedValue = true;
    }

    public void enableEmptyValue() {
        this.enableEmptyValue = true;
    }

    /**
     * Accepts objects in following format: "Skruv;1,Spik;2,Hammare;3"
     */
    public void enableFixedValuesAdvanced() {
        comboBoxFixedValueAdvanced = true;
    }

    public void enableFakeValue() {
        comboBoxFakeValue = true;
    }

    public void enableComboBoxMultipleValue() {
        comboBoxMultipleValue = true;
    }

    public boolean comboBoxMultipleValueEnabled() {
        return comboBoxMultipleValue;
    }

    public void enableToolTipTextJTextField() {
        jTextFieldToolTipText = true;
    }
    
    //===============================<TOOL TIP FIXED>[2020-10-21]===========================
    
    public void setToolTipFixed(String text){
        this.toolTipTextFixed = text;
    }
    
    public boolean toolTipFixedTextPresent(){
        return toolTipTextFixed != null && toolTipTextFixed.isEmpty() == false;
    }
    
    
    public String getToolTipFixedText(){
        return this.toolTipTextFixed;
    }
    
    //========================</TOOL TIP FIXED>=================================

    public boolean toolTipTextEnabled() {
        return jTextFieldToolTipText;
    }

    public void setDisabled() {
        enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setUneditable() {
        editable = false;
    }

    public boolean isEditable() {
        return editable;
    }

    public String getUpdateOtherTablesBefore() {
        return updateOtherTablesBefore;
    }

    public int getType() {
        return type;
    }

    public Object getSpecialComponent(Object value) {
        //
        if (type == TYPE_JCOMBOBOX) {
            JComboBoxInvert jcb = new JComboBoxInvert();
            if (comboBoxMultipleValue) {
                jcb = (JComboBoxInvert) HelpA.fillComboBox(sql, jcb, additionalInfo, value, true, false);
            } else if (comboBoxFakeValue) {
                jcb = (JComboBoxInvert) HelpA.fillComboBox(sql, jcb, additionalInfo, value, false, true);
            } else if (comboBoxFixedValue) {
                String comboboxValues[] = HelpA.extract_comma_separated_values(additionalInfo);
                jcb = (JComboBoxInvert) HelpA.fillComboBox(jcb, comboboxValues, value);
            } else if (comboBoxFixedValueAdvanced) {
                //
                HelpA.ComboBoxObject[] boxObjects = HelpA.extract_comma_separated_objects(additionalInfo, 2);
                //
                if (enableEmptyValue) {
                    jcb = (JComboBoxInvert) HelpA.fillComboBox(jcb, boxObjects, new HelpA.ComboBoxObject("-", "", "",""));
                } else {
                    jcb = (JComboBoxInvert) HelpA.fillComboBox(jcb, boxObjects, value);
                }
                //
            } else {
                jcb = (JComboBoxInvert) HelpA.fillComboBox(sql, jcb, additionalInfo, value, false, false);
            }
            //
            return jcb;
            //
        } else if (type == TYPE_JBUTTON) {
            JButtonInvert jbi = new JButtonInvert();
            return jbi;
        } else if (type == TYPE_JLABEL) {
            JLabelInvert jli = new JLabelInvert();
            return jli;
        }else if(type == TYPE_JPASSWORD_FIELD){
            JPassWordFieldInvert jpf = new JPassWordFieldInvert();
            jpf.setText(additionalInfo);
            return jpf;
        }
        //
        return null;
    }

    public String getFieldNickName() {
        return fieldNickName;
    }

    public String getFieldOriginalName() {
        return fieldOrigName;
    }

    public Object getUnit() {
        return unitOrObject;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible_(boolean visible) {
        this.visible = visible;
    }

    public boolean getImportant() {
        return important;
    }

    public boolean isString() {
        return isString;
    }

    public String getTableName() {
        return tableName;
    }

    public String getPrimaryOrForeignKeyName() {
        return primaryOrForeignKey;
    }

    public boolean getKeyIsString() {
        return keyIsString;
    }

    @Override
    public String toString() {
        return fieldOrigName + ", " + fieldNickName + ", " + unitOrObject + ", " + visible;
    }
}
