/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCBrowserDBTool;

import java.sql.ResultSet;
import java.util.ArrayList;

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
    private int signals_not_changing = 0;
    private static int id_prev;

    public Batch(int id, String recipe, String order, String batch, String date) {
        this.id = id;
        this.recipe = recipe;
        this.order = order;
        this.batch = batch;
        this.date = date;
    }

    public boolean addTick__check_lost_points_a(ResultSet rs) {
        Tick tick = new Tick(rs);
        ticks.add(tick);
        return check_lost_points_a();
    }

    public boolean addTick__check_not_changing_signals(ResultSet rs) {
        Tick tick = new Tick(rs);
        ticks.add(tick);
        return check_not_changing_signal();
    }

    private boolean check_not_changing_signal() {
        //
        if (ticks.size() < 2) {
            return true;
        }
        //
        double torq_act = ticks.get(ticks.size() - 1).getAnalog_1__torq();
        double torq_prev = ticks.get(ticks.size() - 2).getAnalog_1__torq();
        //
        double temp_act = ticks.get(ticks.size() - 1).getAnalog_3__temp();
        double temp_prev = ticks.get(ticks.size() - 2).getAnalog_3__temp();
        //
        //
        double diff_torq = Math.abs(torq_act - torq_prev);
        double diff_temp = Math.abs(temp_act - temp_prev);
        //
        int motor_start = ticks.get(ticks.size() - 1).getDigSignal(Tick.MOTOR);
        //
        if (diff_torq == 0 && diff_temp == 0 && motor_start == 1) { //&& motor_start == 1
            //
            signals_not_changing++;
            //
            if (signals_not_changing > 27) {
//                String str = "not changing: " + signals_not_changing + " / " + recipe + " / " + order + " / " + batch + " date: " + date + " tick: " + (ticks.size() - 1) + " id: " + id;
                String str_ = string_to_table_view("" + signals_not_changing, recipe, order, batch, date, "" + (ticks.size() - 1), "" + id);
                LostPointsFinder.output(str_);
                LostPointsFinder.ids_to_remove.add(id);
                return false;
            }
            //

        } else {
            signals_not_changing = 0;
        }
        return true;
    }

    private static String string_to_table_view(String param1, String param2, String param3, String param4, String param5, String param6, String param7) {
        String format = "%1$5s %2$10s %3$30s %4$10s %5$30s %6$10s %7$10s \n";
        String arr[] = {param1, param2, param3, param4, param5, param6, param7};
        return String.format(format, (Object[]) arr);
    }

    private boolean check_lost_points_a() {
        //
        if (ticks.size() < 2) {
            return true;
        }
        //
        long r_time_act = ticks.get(ticks.size() - 1).getR_time__long();
        long r_time_prev = ticks.get(ticks.size() - 2).getR_time__long();
        //
        long diff = Math.abs(r_time_act - r_time_prev);
        //
        if (diff > LostPointsFinder.DELAY_MORE_THEN) {
            //
            lost_points_type_a++;
            //
            if (lost_points_type_a > LostPointsFinder.SHOW_OUTPUT_IF_DELAYS_MORE_THEN) {
                String str = "diff: " + diff + " / " + recipe + " / " + order + " / " + batch + " date: " + date + " tick: " + (ticks.size() - 1) + " id: " + id;
                LostPointsFinder.output(str);
                LostPointsFinder.ids_to_remove.add(id);
            }
            //

        }
        return true;

    }

}
