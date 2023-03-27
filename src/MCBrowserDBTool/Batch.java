/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCBrowserDBTool;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;

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

    public Batch(int id, String recipe, String order, String batch, String date) {
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

    private static int id_prev;
    public static HashSet<Integer>ids_to_remove = new HashSet<>();

    private void check_lost_points_a() {
        //
        if (ticks.size() < 2) {
            return;
        }
        //

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
                ids_to_remove.add(id);
            }
            //

        }

    }

}
