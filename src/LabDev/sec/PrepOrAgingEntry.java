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
public class PrepOrAgingEntry {

    private final String description;
    private final String code;

    public PrepOrAgingEntry(String description, String code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

}
