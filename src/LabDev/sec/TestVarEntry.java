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

    /**
     * @deprecated
     * @return 
     */
    public String buildString___() {
        return getPart(part_1, true) + getPart(part_2, false) + getPart(part_3, false)
                + getPart(part_4, false) + getPart(part_5, false);
    }
    
    public String buildString() {
        String format = "%1$-25s %2$-7s %3$-7s %4$-7s \n";
        String arr[] = {part_1, part_2, part_3,part_4, part_5};
        System.out.println("");
        return String.format(format, (Object[]) arr);
    }

    private String getPart(String part, boolean partOne) {
        if (part.isEmpty() == false && partOne == false) {
            return "     " + part; // 5 spaces
        } else if (part.isEmpty() == false && partOne == true) {
            return part;
        } else {
            return "";
        }
    }

}
