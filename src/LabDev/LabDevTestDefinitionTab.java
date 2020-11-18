/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabDev;

import MyObjectTable.ShowMessage;
import MyObjectTableInvert.RowDataInvert;
import forall.SqlBasicLocal;

/**
 *
 * @author KOCMOC
 */
public class LabDevTestDefinitionTab extends LabDevTab {

    public LabDevTestDefinitionTab(SqlBasicLocal sql, SqlBasicLocal sql_additional, ShowMessage OUT, LabDevelopment labDev) {
        super(sql, sql_additional, OUT, labDev);
    }

    public void refresh() {

    }

    @Override
    public void fillNotes() {
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        return null;
    }

    @Override
    public void showTableInvert() {
    }

    @Override
    public void initializeSaveIndicators() {
    }

    @Override
    public boolean getUnsaved(int nr) {
        return false;
    }

}
