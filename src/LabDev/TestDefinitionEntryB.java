/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

/**
 * Build the test condition string like: "100C 4min 1*Rohkl. *MW 1*Techn
 * 1*MW-Lab"
 *
 * @author KOCMOC
 */
public class TestDefinitionEntryB {

    private String conditionString = "";
    private boolean firstTime = false;

    public void add(String condition, String testCondition, String unit) {
        if (testCondition != null && testCondition.isEmpty() == false) {
            //
            String entry = testCondition.trim() + "" + unit.trim();
            //
            if (firstTime == false) {
                firstTime = true;
                conditionString += entry;
            } else {
                conditionString += "   " + entry;
            }
        }
    }

    public String getConditionString() {
        return conditionString;
    }

}
