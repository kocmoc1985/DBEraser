/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCBrowserDBTool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class Tick {

    private int tick;
    private double analog_1__torq;
    private double analog_2__power;
    private double analog_3__temp;
    private double analog_6__speed;
    private double analog_7__ram;
    private double analog_8__pressure;
    private long r_time__long;
    private HashMap<String, Integer> digital_signals_map;
    private String r_time;
    //
    public final static String BATCH_MC_MODE = "BATCH_MC_MODE";
    public final static String CONTROL_START = "CONTROL_START";
    public final static String DISCHARGE_CONTROL = "DISCHARGE_CONTROL";
    public final static String RAM_IS_UP = "RAM_IS_UP";
    public final static String RAM_IS_DOWN = "RAM_IS_DOWN";
    public final static String HOOPER_DOOR_IS_OPEN = "HOOPER_DOOR_IS_OPEN";
    public final static String HOOPER_DOOR_IS_CLOSED = "HOOPER_DOOR_IS_CLOSED";
    public final static String DOOR_IS_OPEN = "DOOR_IS_OPEN";
    public final static String DOOR_IS_CLOSE = "DOOR_IS_CLOSE";
    public final static String OIL = "OIL";
    public final static String RUBBER = "RUBBER";
    public final static String CARBON = "CARBON";
    public final static String FILLERS = "FILLERS";
    public final static String SMALL_CH = "SMALL_CH";
    public final static String FEED_CONTROL = "FEED_CONTROL";
    public final static String MIXER_IS_READY = "MIXER_IS_READY";
    public final static String MOTOR = "MOTOR";
    public final static String MANUAL = "MANUAL";
    public final static String FINISH_LOADING = "FINISH_LOADING";

    public Tick(ResultSet rs) {
        try {
            this.tick = rs.getInt("TICKS");
            this.analog_1__torq = rs.getDouble("ANALOG1");
            this.analog_2__power = rs.getDouble("ANALOG2");
            this.analog_3__temp = rs.getDouble("ANALOG3");
            this.analog_6__speed = rs.getDouble("ANALOG6");
            this.analog_7__ram = rs.getDouble("ANALOG7");
            this.analog_8__pressure = rs.getDouble("ANALOG8");
            this.r_time = rs.getString("RTime");
            this.r_time__long = dateToMillisConverter2(r_time);
            this.digital_signals_map = prepare_dig_signals_map(rs.getInt("DIGITAL_IN"));
//            System.out.println("");
        } catch (SQLException ex) {
            Logger.getLogger(Tick.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private HashMap<String, Integer> prepare_dig_signals_map(int decimal) {
        String binaryString = decimal_to_binary__string(decimal);
        return translateBinaryString(binaryString);
    }

    private HashMap<String, Integer> translateBinaryString(String binaryString) {
        ArrayList<String> list = new ArrayList<>();
        HashMap<String, Integer> map = new HashMap<>();
        list.add(BATCH_MC_MODE);
        list.add(CONTROL_START);
        list.add(DISCHARGE_CONTROL);
        list.add(RAM_IS_UP);
        list.add(RAM_IS_DOWN);
        list.add(HOOPER_DOOR_IS_OPEN);
        list.add(HOOPER_DOOR_IS_CLOSED);
        list.add(DOOR_IS_OPEN);
        list.add(DOOR_IS_CLOSE);
        list.add(OIL);
        list.add(RUBBER);
        list.add(CARBON);
        list.add(FILLERS);
        list.add(SMALL_CH);
        list.add(FEED_CONTROL); // TOTAL_ADD
        list.add(MIXER_IS_READY);
        list.add(MOTOR);
        list.add(MANUAL);
        list.add(FINISH_LOADING);
        //
        char[] arr = binaryString.toCharArray();
        //
        int x = 0;
        //
        for (int i = arr.length - 1; i >= 0; i--) {
//            System.out.println(list.get(x) + " " + arr[i]);
            map.put(list.get(x), Integer.parseInt("" + arr[i]));
            x++;
        }
        //
        return map;
    }

    private String decimal_to_binary__string(int dig_singals_in) {
        return String.format("%19s", Integer.toBinaryString(dig_singals_in)).replace(' ', '0'); // %19s is the right one 2022-07-06
    }

    private long dateToMillisConverter2(String date_yyyy_MM_dd__HH_mm_ss) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return formatter.parse(date_yyyy_MM_dd__HH_mm_ss).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(Tick.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public int getDigSignal(String dig_signal_name) {
        return digital_signals_map.get(dig_signal_name);
    }

    public long getR_time__long() {
        return r_time__long;
    }

    public double getAnalog_1__torq() {
        return analog_1__torq;
    }

    public double getAnalog_3__temp() {
        return analog_3__temp;
    }

}
