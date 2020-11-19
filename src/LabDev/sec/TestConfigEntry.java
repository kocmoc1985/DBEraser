/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev.sec;

/**
 *
 * @author KOCMOC
 */
public class TestConfigEntry {

    private final String condition;
    private final String unit;

    public TestConfigEntry(String condition, String unit) {
        this.condition = condition;
        this.unit = unit;
    }

    public String getCondition() {
        return condition;
    }

    public String getUnit() {
        return unit;
    }

}
