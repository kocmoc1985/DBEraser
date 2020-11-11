/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import MCRecipe.Lang.LNG;
import MCRecipe.MC_RECIPE;
import forall.SqlBasicLocal;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author KOCMOC
 */
public class LabDevFindOrderTab extends ChkBoxItemListComponent {

    private final LabDevelopment labDev;
    private final SqlBasicLocal sql;
    private final MC_RECIPE mcRecipe;

    public LabDevFindOrderTab(LabDevelopment labDev, SqlBasicLocal sql, MC_RECIPE mcRecipe) {
        this.labDev = labDev;
        this.sql = sql;
        this.mcRecipe = mcRecipe;
    }
    
    public void go(){
        showCheckBoxComponent();
    }
    
    private JPanel getPanel(){
        return mcRecipe.jPanel_lab_dev__find_order;
    }
    
    private void showCheckBoxComponent(){
        String[]status_list = LAB_DEV__STATUS.getLabDevStatusesAuto(LNG.LANG_ENG);
        addRows_B(status_list, getPanel(), new Dimension(200, LabDevFindOrderTab.HEIGHT));
    }
    
    
    public void testBtnClicked(){
        //
        ArrayList<JPanelPrepM> list = getSelectedFromTable(getPanel());
        //
        for (JPanelPrepM jp : list) {
            System.out.println("" + jp);
        }
        //
    }
     
    
    
}
