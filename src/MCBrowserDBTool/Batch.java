/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCBrowserDBTool;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author KOCMOC
 */
public class Batch {

    private ArrayList<Tick> ticks = new ArrayList<>();
    private final int id;
    private final String recipe;
    private final String order;
    private final String batch;
    private final String date;
    private int lost_points_type_a = 0;

    public Batch(int id,String recipe, String order, String batch, String date) {
        this.id = id;
        this.recipe = recipe;
        this.order = order;
        this.batch = batch;
        this.date = date;
    }

    public void addTick(ResultSet rs) {
        Tick tick = new Tick(rs);
        ticks.add(tick);
        check_lost_points_a();
    }

    private void check_lost_points_a() {
        //
        if (ticks.size() < 2) {
            return;
        }
        //
        long r_time_act = ticks.get(ticks.size() - 1).getR_time__long();
        long r_time_prev = ticks.get(ticks.size() - 2).getR_time__long();
        //
        long diff = Math.abs(r_time_act - r_time_prev);
        //
        if (diff > 1500) {
//            System.out.println("time_act: " + r_time_act);
//            System.out.println("time_prev: " + r_time_prev);
//            System.out.println("diff: " + diff + " / " + recipe + " " + order + " " + batch + " date: " + date + " tick: " + (ticks.size() - 1) + " id: " + id);
            lost_points_type_a++;
            if(lost_points_type_a > 5){
//               System.out.println("diff: " + diff + " / " + recipe + " " + order + " " + batch + " date: " + date + " tick: " + (ticks.size() - 1) + " id: " + id);
               String str = "diff: " + diff + " / " + recipe + " " + order + " " + batch + " date: " + date + " tick: " + (ticks.size() - 1) + " id: " + id;
               LostPointsFinder.output(str);
            }
        } else {
//            System.out.println("time_act: " + r_time_act);
//            System.out.println("time_prev: " + r_time_prev);
        }

    }

    public static String millisToDateConverter(String millis) {

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS"); //this works!
        //note if to write hh instead of HH it will show like 03:15:16 and not 15:15:16
//        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // this works to!

        long now = Long.parseLong(millis);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now);

//        System.out.println(now + " = " + formatter.format(calendar.getTime()));
        return formatter.format(calendar.getTime());
    }

}
