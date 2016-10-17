/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FixedQueryTool;

import forall.GP;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

/**
 *
 * @author KOCMOC
 */
public class BuildWorkInstrTable {

    private static final String WI_00_0_0000 =
            "speed  25.0, add  10000,30242,18839,17687,17971,30822; \n"
            + "time  30.0, speed  20.0, add  ; \n"
            + "temp  100.0, speed  15.0, add  ; \n"
            + "time  30.0, sweep  2.0, temp  120.0, speed  15.0, disch  1.0; \n";
    //
    private static final String WI_34_0_1637 = "speed  30.0, add  31008,10068,11188,11504,30409,30617; "
            + "time  45.0, speed  18.0, add  10100,30889;"
            + "time  30.0, sweep  2.0, temp  100.0, speed  14.0, add  12013,17856,30771;"
            + "time  30.0, sweep  2.0, temp  117.0, speed  15.0, ram  up, disch  1.0;";
    //
    private static final String WI_02_8_1595__DOUBLE_SWEEP =
            "speed  30.0, add  30589;"
            + "time  45.0, speed  18.0, add  30071,11537,12396,11250,11418,30273,12070;"
            + "time  30.0, sweep  3.0, time  30.0, sweep  3.0, temp  135.0, speed  15.0, ram  up, disch  1.0";
    //
    private static final String WI_00_0_0333 = "\"speed  40.0, add  30026,17460,17203; \n"
            + "time  45.0, speed  20.0, add  10068,11188,11170,11418,17751; \n"
            + "temp  90.0, add  11080,30581,17857,17687; \n"
            + "temp  105.0, sweep  0.0, temp  125.0, ram  up, disch  1.0; ";
    //
    private static final String WI_27_0_N911 =
            "speed  19.0, add  12467,30798,10068,11188,17203,15837,10100,30807,17687,30771,30776; \n"
            + "time  30.0, sweep  2.0, time  30.0, sweep  1.0, temp  125.0, speed  15.0, ram  up, disch  1.0;";
    //
    private static final String WI_00_0_1803 =
            "speed  13.0, time  30.0, sweep  2.0, temp  95.0, ram  up, disch  1.0; ";
    //
    private static final String WI_00_0_1134 = "speed  12.0, add  00-8-1134,17687,17899,17905,30776;"
            + "time  20.0, add  00-8-1134;"
            + "temp  95.0, disch  1.0;";
    //==========================================================================
    private static final String TIME = "time";
    private static final String TEMP = "temp";
    private static final String SPEED = "speed";
    private static final String ADD = "add";
    private static final String SWEEP = "sweep";
    private static final String DISCH = "disch";

    public static void build(String work_instruction,String recipe) {
        String[] headers = getHeader(max_sweeps_in_row(work_instruction));
        String[][] content = getContent(work_instruction);

        JFrame frame = new JFrame();
        frame.setLayout(new GridLayout(2, 1));
        frame.setTitle("Work-Instructions table " + "(" + recipe + ")");
        frame.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL).getImage());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JTable table = new JTable(content, headers);
        
        //==========
        String recipe_info = "RECIPE: " + recipe;
        String workInstr_info = "WORK_INSTR.:\r\n" + work_instruction;
        String info = recipe_info + "\r\n\r\n" + workInstr_info;
        JTextArea lbl_info = new JTextArea(info);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(lbl_info);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(1200, 400);
        frame.setLocation(position_window_in_center_of_the_screen(frame));
        frame.setVisible(true);
    }
    
    public static Point position_window_in_center_of_the_screen(JFrame window) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        return new Point((d.width - window.getSize().width) / 2, (d.height - window.getSize().height) / 2);
    }

    public static void main(String[] args) {
        build(WI_34_0_1637,"");
    }

    private static String[] getHeader(int sweeps_in_row) {
        String[] SINGLE_SWEEP_HEADER = {TIME, TEMP, SPEED, ADD, SWEEP, TIME, TEMP, SPEED, ADD, DISCH};
        String[] DOUBLE_SWEEP_HEADER = {TIME, TEMP, SPEED, ADD, SWEEP, TIME, TEMP, SPEED, ADD, SWEEP, TIME, TEMP, SPEED, ADD, DISCH};
        String[] TRIPPLE_SWEEP_HEADER = {TIME, TEMP, SPEED, ADD, SWEEP, TIME, TEMP, SPEED, ADD, SWEEP, TIME, TEMP, SPEED, ADD, SWEEP, TIME, TEMP, SPEED, ADD, DISCH};

        if (sweeps_in_row == 1) {
            return SINGLE_SWEEP_HEADER;
        } else if (sweeps_in_row == 2) {
            return DOUBLE_SWEEP_HEADER;
        } else if (sweeps_in_row == 3) {
            return TRIPPLE_SWEEP_HEADER;
        } else {
            return SINGLE_SWEEP_HEADER;
        }
    }

    private static String[][] getContent(String workInstructions) {
        workInstructions = workInstructions.trim();
        String[] step_arr = workInstructions.split(";");
        int sweeps_in_row = max_sweeps_in_row(workInstructions);
        int steps = count_steps(workInstructions);
        String[] header = getHeader(sweeps_in_row);

        String[][] content = new String[steps][header.length];

        boolean jump_to_sweep = false;

        int row = 0;
        for (String step : step_arr) {
            ArrayList<String> actions = extract_actions_from_step(step);
            int column = 0;
            for (String column_name : header) {
                if (jump_to_sweep) {
                    if (column_name.contains(SWEEP) == false) {
                        column++;
                        continue;
                    } else {
                        jump_to_sweep = false;
                    }
                }
                for (String action : actions) {
                    if (action.contains(SWEEP) && action.contains(column_name) == false && actions.get(0).contains(SWEEP)) {
                        jump_to_sweep = true;
                        break;
                    }
                    if (action.contains(column_name)) {
                        if (content[row][column] == null) {
                            content[row][column] = extract_value_from_action(action);
                            actions.remove(action);
                            break;
                        }
                    }
                }
                column++;
            }
            row++;
        }
        //
        return content;
    }

    private static String extract_value_from_action(String action) {
        String[] arr = action.split(" ");
        System.out.println("");
        return arr[arr.length - 1];
    }

    /**
     * Extracts the action as "temp 15" or "add 11564,56456,64667"
     *
     * @param step
     * @return
     */
    private static ArrayList<String> extract_actions_from_step(String step) {
        step = step.replace(";", "");
        String[] arr_1 = step.split(",");
        ArrayList<String> add_materials_list = new ArrayList();
        ArrayList<String> normal_actions_list = new ArrayList();
        ArrayList<String> to_return_list = new ArrayList<String>();
        //====
        for (String entry : arr_1) {
            try {
                Integer.parseInt(entry);
                add_materials_list.add(entry);
            } catch (Exception ex) {
                normal_actions_list.add(entry);
            }
        }

        for (String curr_action : normal_actions_list) {
            if (curr_action.contains(ADD)) {
                String proper_add_action = "" + curr_action;
                for (String material : add_materials_list) {
                    proper_add_action += "," + material;
                }
                to_return_list.add(proper_add_action);
            } else {
                to_return_list.add(curr_action);
            }
        }

        return to_return_list;
    }

    /**
     * Finds the maximum ammount of sweeps in a row this is very important
     * because the table is build uppon this value
     *
     * @param workInstructions
     * @return
     */
    private static int max_sweeps_in_row(String workInstructions) {
        workInstructions = workInstructions.trim();
        String[] arr = workInstructions.split(";");
        int max = 0;
        for (String step : arr) {
            int nr_sweeps_in_row = countOccurrences("sweep", step);
            if (nr_sweeps_in_row > max) {
                max = nr_sweeps_in_row;
            }
        }
        return max;
    }

    /**
     *
     * @param workInstructions
     * @return
     */
    private static int count_steps(String workInstructions) {
        workInstructions = workInstructions.trim();
        String[] arr = workInstructions.split(";");
        return arr.length;
    }

    private static int countOccurrences(String find, String string) {
        int count = 0;
        int indexOf = 0;

        while (indexOf > -1) {
            indexOf = string.indexOf(find, indexOf + 1);
            if (indexOf > -1) {
                count++;
            }
        }
        return count;
    }
}
