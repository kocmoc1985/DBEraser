/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MCRecipe;

import MCRecipe.Lang.T_INV;
import MyObjectTableInvert.RowDataInvert;

/**
 *
 * @author KOCMOC
 */
public class TableInvertConfigsCompany {
    
    public static RowDataInvert[] testParameters_qew() {
        return null;
    }

    public static RowDataInvert[] testParameters_compounds() {
        //MCcpwotest
        RowDataInvert recipe = new RowDataInvert("MCcpwotest", "ID_Wotest", false, "CODE", T_INV.LANG("RECIPE"), "", true, true, false);
        recipe.setUneditable();
        RowDataInvert order = new RowDataInvert("MCcpwotest", "ID_Wotest", false, "ORDERNO", T_INV.LANG("ORDER"), "", true, true, false);
        order.setUneditable();
        RowDataInvert testCode = new RowDataInvert("MCcpwotest", "ID_Wotest", false, "TESTCODE", T_INV.LANG("TEST CODE"), "", true, true, false);
        testCode.setUneditable();
        RowDataInvert prefvulc = new RowDataInvert("MCcpwotest", "ID_Wotest", false, "PREFVULC", T_INV.LANG("PREFVULC"), "", true, true, false);
        RowDataInvert prefage = new RowDataInvert("MCcpwotest", "ID_Wotest", false, "PREFAGE", T_INV.LANG("PREFAGE"), "", true, true, false);
        RowDataInvert testCond = new RowDataInvert("MCcpwotest", "ID_Wotest", false, "TESTCOND", T_INV.LANG("TEST CONDITION"), "", true, true, false);
        //
        //MCCPTproc
        RowDataInvert testVar = new RowDataInvert("MCCPTproc", "ID_Proc", false, "TESTVAR", T_INV.LANG("TESTVAR"), "", true, true, false);
        testVar.setUneditable();
        testVar.enableToolTipTextJTextField();
        //
        RowDataInvert version = new RowDataInvert("MCCPTproc", "ID_Proc", false, "VERSION", T_INV.LANG("VERSION"), "", true, true, false);
        RowDataInvert norm = new RowDataInvert("MCCPTproc", "ID_Proc", false, "NORM", T_INV.LANG("NORM"), "", true, true, false);
        RowDataInvert group = new RowDataInvert("MCCPTproc", "ID_Proc", false, "GROUP", T_INV.LANG("GROUP"), "", true, true, false);
        RowDataInvert report = new RowDataInvert("MCCPTproc", "ID_Proc", false, "REPORT", T_INV.LANG("REPORT"), "", true, true, false);
        RowDataInvert note = new RowDataInvert("MCCPTproc", "ID_Proc", false, "NOTE", T_INV.LANG("NOTE"), "", true, true, false);
        note.enableToolTipTextJTextField();
        //Ingredient_Vulco_Code
        RowDataInvert vulcoinfo = new RowDataInvert("Ingredient_Vulco_Code", "Ingredient_Vulco_Code_ID", false, "Descr", T_INV.LANG("VULCO INFO"), "", true, true, false);

        //Ingredient_Aeging_Code
        RowDataInvert ageinfo = new RowDataInvert("Ingredient_Aeging_Code", "Ingredient_Aeging_Code_ID", false, "Aeging_Info", T_INV.LANG("AEGING INFO"), "", true, true, false);
        ageinfo.setUneditable();
        //
        RowDataInvert[] rows = {
            recipe, order, testCode, testVar, testCond,
            version, norm, group, prefvulc,
            vulcoinfo,
            prefage, ageinfo, report, note
        };
        //
        return rows;
    }

}
