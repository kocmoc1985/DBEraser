/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

/**
 *
 * @author KOCMOC
 */
public class UpdateEntry {

    private final String tableName;
    private final String updateColumnName;
    private final String updateColumnValue;
    private final String whereColumnName;
    private final String whereColumnValue;
    private final boolean isStringUpdateColumn;
    private final boolean isStringWhereColumn;

    public UpdateEntry(String tableName, String updateColumnName, String updateColumnValue, String whereColumnName, String whereColumnValue, boolean isStringUpdateColumn, boolean isStringWhereColumn) {
        this.tableName = tableName;
        this.updateColumnName = updateColumnName;
        this.updateColumnValue = updateColumnValue;
        this.whereColumnName = whereColumnName;
        this.whereColumnValue = whereColumnValue;
        this.isStringUpdateColumn = isStringUpdateColumn;
        this.isStringWhereColumn = isStringWhereColumn;
    }


    public String getQuery() {
        return "UPDATE " + tableName
                + " SET " + updateColumnName + "=" + getColumnValue()
                + " WHERE " + whereColumnName + "=" + getWhereValue();
    }

    public String getColumnValue() {
        if (isStringUpdateColumn) {
            return "'" + updateColumnValue + "'";
        } else {
            return updateColumnValue;
        }
    }

    public String getWhereValue() {
        if (isStringWhereColumn) {
            return "'" + whereColumnValue + "'";
        } else {
            return whereColumnValue;
        }
    }
}
