/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exporterXML;

/**
 *
 * @author KOCMOC
 */
public class Table {

    private String TABLE_NAME;
    private String SQL_QUERY;

    public Table(String TABLE_NAME, String SQL_QUERY) {
        this.TABLE_NAME = TABLE_NAME;
        this.SQL_QUERY = SQL_QUERY;
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public String getSqlQuery() {
        return SQL_QUERY;
    }
}
