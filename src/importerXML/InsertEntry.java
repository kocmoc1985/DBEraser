/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package importerXML;

import forall.GP;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author mcab
 */
public class InsertEntry {

    private ArrayList table_records_entry = new ArrayList();
    private final String table_name;
    private boolean entry_complete;
    private static final HashMap<String, Boolean> TYPE_MAP = new HashMap();
    private final HashMap<String, Boolean> AUTO_INCREMENT_MAP = new HashMap();

    static {
        TYPE_MAP.put("int", false);
        TYPE_MAP.put("float", false);
    }

    public InsertEntry(String table_name) {
        this.table_name = table_name;
        define_auto_increment_tables();
    }

    private void define_auto_increment_tables() {
        String auto_incr = ImporterXML.properties.getProperty("tables_with_auto_increment", "");
        String[] arr = auto_incr.split(",");
        //
        ImporterXML.show_info("Tables w. auto incr. :" + auto_incr);
        //
        for (String table : arr) {
            AUTO_INCREMENT_MAP.put(table, true);
        }
    }


    public void add(Object record) {
        if (entry_complete) {
            entry_complete = false;
            table_records_entry = new ArrayList();
        }
        //
        // One "table_records_entry" represents values for an insert query like:
        // insert into MainTable values(131,'2014-05-21 15:37:47','93000141072','129408','23','75.9','20','507')
        table_records_entry.add(record);
    }

    public void entry_complete() {
        entry_complete = true;
    }

    public String build_insert_query() {
        StringBuilder stringBuilder = new StringBuilder("insert into " + table_name + " values(");
        //
        boolean first_record = true;
        // means that table implements auto increment
        boolean auto_increment = AUTO_INCREMENT_MAP.containsKey(table_name);
        //
        for (Object table_record : table_records_entry) {
            //
            // This section is about tables which do have auto increment
            // which means that first record is not taken into account
            if (first_record && auto_increment) {
                first_record = false;
                continue;
            }
            //
            String formatted_value = format_value((TableRecord) table_record);
            stringBuilder.append(formatted_value);
            stringBuilder.append(",");
        }
        //
        stringBuilder.deleteCharAt(stringBuilder.toString().length() - 1);
        stringBuilder.append(")");
//        System.out.println("s_builer = " + stringBuilder.toString());
        return stringBuilder.toString();
    }

    /**
     * This function defines if the value is a "number" or a "String". If String
     * it returns value with quotes "'99005A5343'" if the value is "number" dont
     * matter if it is int, float, double it returns the value without quotes
     * ex: "758.963"
     *
     * @param record
     * @return
     */
    public String format_value(TableRecord record) {
        String type = record.getType();
        String record_value = record.getValue();

        boolean type_is_string;

        try {
            type_is_string = TYPE_MAP.get(type);
        } catch (Exception ex) {
            type_is_string = true;
        }
        if (type_is_string == false) {
            return record_value;
        } else {
            return "'" + record_value + "'";
        }
    }

    public static String delete_last_char(String str) {
        int a = str.length() - 1;
        return str.substring(0, a);
    }
}
