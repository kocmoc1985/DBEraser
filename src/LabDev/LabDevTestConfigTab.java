/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import MCRecipe.MC_RECIPE;
import forall.SqlBasicLocal;

/**
 *
 * @author KOCMOC
 */
public class LabDevTestConfigTab {

    private final LabDevelopment labDev;
    private final SqlBasicLocal sql;
    private final MC_RECIPE mcRecipe;

    public LabDevTestConfigTab(LabDevelopment labDev, SqlBasicLocal sql, MC_RECIPE mcRecipe) {
        this.labDev = labDev;
        this.sql = sql;
        this.mcRecipe = mcRecipe;
    }

}
