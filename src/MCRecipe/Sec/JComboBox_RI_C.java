/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe.Sec;

import forall.JComboBoxA;

/**
 * RI_C RecipeInitial section C, the section at the bottom in the middle
 *
 * @author KOCMOC
 */
public class JComboBox_RI_C extends JComboBoxA implements Comparable<JComboBox_RI_C> {

    private final int NR;

    public JComboBox_RI_C(CBoxParam param, int nr) {
        super(param);
        this.NR = nr;
    }

    @Override
    public int compareTo(JComboBox_RI_C t) {
        if (this.NR > t.NR) {
            return 1;
        } else if (this.NR == t.NR) {
            return 0;
        } else {
            return -1;
        }
    }

}
