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
public class TestVarEntry {

    private final String part_1; // TNAME
    private final String part_2; //TMIN
    private final String part_3; // TMAX
    private final String part_4; // TUNIT
    private final String part_5; // TDIGIT

    public TestVarEntry(String part_1, String part_2, String part_3, String part_4, String part_5) {
        this.part_1 = part_1.trim();
        this.part_2 = part_2.trim();
        this.part_3 = part_3.trim();
        this.part_4 = part_4.trim();
        this.part_5 = part_5.trim();
    }

    public String buildString() {
        String format = "%1$-20.20s  %2$-5.5s  %3$-5.5s  %4$-9.9s  %5$-5.5s";
        String arr[] = {part_1, part_2, part_3, part_4, part_5};
        String formated = String.format(format, (Object[]) arr);
        System.out.println("" + formated);
        System.out.println("LENGTH: " + formated.length() + " ************************************************************************");
        return formated;
    }

    

}
