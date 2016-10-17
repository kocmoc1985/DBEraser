/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package importerXML;

/**
 *
 * @author mcab
 */
public class TableRecord {

    private final String value;
    private final String type;

    public TableRecord(String record, String type) {
        this.value = record;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }
}
