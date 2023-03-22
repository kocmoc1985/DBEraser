/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCBrowserDBTool;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private String r_time;

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
        } catch (SQLException ex) {
            Logger.getLogger(Tick.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public long getR_time__long() {
        return r_time__long;
    }
    
    
    

}
