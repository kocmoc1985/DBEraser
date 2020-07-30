/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjectTableInvert;

import javax.swing.JTextArea;

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

    public ColumnDataEntryInvert(Object object, String database_id, String originalColumnName, String columnNickName) {
        this.object = object;
        this.database_id = database_id;
        this.original_column_name = originalColumnName;
        this.column_nick_name = columnNickName;
    }

    public boolean isIsUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean isUpdated) {
        this.isUpdated = isUpdated;
    }
    
    public void setObject(Object object) {
        this.object = object;
    }

    public Object getObject() {
        //
        // Setting this as child of parent JComponent here [2020-07-30]
        //
        if(object instanceof JParentInvert){
            JParentInvert jpi = (JParentInvert)object;
            jpi.setChild(this);
        }
        //
        // OBS! OBS! For the JTextFieldInvert the "setChild()" is done from
        // "TableRowInvert.class -> addColumn(Object obj) -> line: 112" [2020-07-30]
        //
        return object;
        //
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
