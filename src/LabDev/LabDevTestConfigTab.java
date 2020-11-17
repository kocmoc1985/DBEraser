/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import MCRecipe.MC_RECIPE_;
import forall.SqlBasicLocal;
import java.util.ArrayList;

/**
 *
 * @author KOCMOC
 */
public class LabDevTestConfigTab extends ChkBoxItemListComponent {

    private final LabDevelopment labDev;
    private final SqlBasicLocal sql;
    private final MC_RECIPE_ mcRecipe;

    public LabDevTestConfigTab(LabDevelopment labDev, SqlBasicLocal sql, MC_RECIPE_ mcRecipe) {
        this.labDev = labDev;
        this.sql = sql;
        this.mcRecipe = mcRecipe;
        init();
    }

    private void init() {
        temp();
    }
    
    /**
     * Example of getting values from a "CheckBox"
     */
    public void testGetValuesCheckBoxTableOne() {
        //
        ArrayList<JPanelPrepM> list = getSelectedFromTable(mcRecipe.jPanel65);
        //
        for (JPanelPrepM jp : list) {
            System.out.println("" + jp);
        }
        //
    }

    private void temp() {
        //
        addRows(new String[]{
            "- Keine - ",
            "Presse / Klappe 6mm / 10 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c ",
            "Presse / Ammi platte / 15 min / 170c "}, mcRecipe.jPanel65, null);
        //
        addRows(new String[]{
            "- Keine -",
            "DVR in luft / 1.0 d / 70c ",
            "DVR in luft / 1.0 d / 70c ",
            "DVR in luft / 1.0 d / 70c ",
            "DVR in luft / 1.0 d / 70c "
        }, mcRecipe.jPanel66, null);
    }

}
