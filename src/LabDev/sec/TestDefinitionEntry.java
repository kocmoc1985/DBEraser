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
public class TestDefinitionEntry {

    private final String code;
    private final String descr;
    private final String conditionString;

    public TestDefinitionEntry(String code, String descr, String conditionString) {
        this.code = code;
        this.descr = descr;
        this.conditionString = conditionString;
    }

    public String getCode() {
        return code;
    }

    public String getDescr() {
        return descr;
    }

    public String getConditionString() {
        return conditionString;
    }

    
}
