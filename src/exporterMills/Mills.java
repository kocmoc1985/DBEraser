/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exporterMills;

import forall.GP;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Administrator
 */
public class Mills {

    public final HashMap<String, LinkedList<Dbentry>> table_map = new HashMap<String, LinkedList<Dbentry>>();
    public final LinkedList<Dbentry> MainTable = new LinkedList<Dbentry>();
    public final LinkedList<Dbentry> CurvesTable = new LinkedList<Dbentry>();
    public final LinkedList<Dbentry> Calculations = new LinkedList<Dbentry>();
    public final ArrayList<String> table_names_bound = new ArrayList<String>(); // tables bound with id like Maintable,Calculations,CurvesTable
    public final ArrayList<String> table_names_unbound = new ArrayList<String>(); // tables bound with id like Maintable,Calculations,CurvesTable

    public static class MT{
         public static String datum = "Datum";
    }
    
    public static class DAYRECORD {

        public static String tablename = Exporter.EXPORTER_MILLS__DAYRECORD_TABLE_NAME;
        public static String recordid = "r_id";
        public static String datum = "datum";
        public static String dayrecord = "dayRecord";
        public static String line = "line";
        public static String millnr = "millsNr";
    }

    public Mills() {
        //====================================================
        MainTable.add(new Dbentry("BatchID", "int"));
        MainTable.add(new Dbentry("Datum", "String"));
        MainTable.add(new Dbentry("RecipeID", "String"));
        MainTable.add(new Dbentry("OrderNr", "String"));
        MainTable.add(new Dbentry("BatchNr", "String"));
        MainTable.add(new Dbentry("TorqActL", "String"));
        MainTable.add(new Dbentry("Line", "String"));
        MainTable.add(new Dbentry("Duration", "String"));
        //
        table_map.put(Exporter.EXPORTER_MILLS__MAIN_TABLE_NAME, MainTable);
        table_names_bound.add(Exporter.EXPORTER_MILLS__MAIN_TABLE_NAME);
        //====================================================
        CurvesTable.add(new Dbentry("BatchID", "int"));
        CurvesTable.add(new Dbentry("Tik", "int"));
        CurvesTable.add(new Dbentry("torque", "String"));
        CurvesTable.add(new Dbentry("speed", "String"));
        CurvesTable.add(new Dbentry("gapLeft", "String"));
        CurvesTable.add(new Dbentry("gapLeft", "String"));
        //
        table_map.put("CurvesTable", CurvesTable);
        table_names_bound.add(Exporter.EXPORTER_MILLS__CURVES_TABLE_NAME);
        //====================================================
        Calculations.add(new Dbentry("batchID", "int"));
        Calculations.add(new Dbentry("totaltime", "int"));
        Calculations.add(new Dbentry("torqueav", "int"));
        Calculations.add(new Dbentry("gapLav", "int"));
        Calculations.add(new Dbentry("gapRav", "int"));
        Calculations.add(new Dbentry("speedav", "int"));
        Calculations.add(new Dbentry("energySum", "int"));
        //
        table_map.put(Exporter.EXPORTER_MILLS__CALCULATIONS_TABLE_NAME, Calculations);
        table_names_bound.add(Exporter.EXPORTER_MILLS__CALCULATIONS_TABLE_NAME);
        //====================================================
        //====================================================
        table_names_unbound.add(Exporter.EXPORTER_MILLS__DAYRECORD_TABLE_NAME);
    }

    public class Dbentry {

        private String column_name;
        private String data_type;

        public Dbentry(String column_name, String data_type) {
            this.column_name = column_name;
            this.data_type = data_type;
        }

        public String getColumnName() {
            return this.column_name;
        }

        public String getDataType() {
            return this.data_type;
        }
    }
}
