/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import BuhInvoice.sec.LANG;
import MyObjectTable.OutPut;
import MyObjectTableInvert.RowDataInvert;
import MyObjectTableInvert.RowDataInvertB;
import MyObjectTableInvert.TableBuilderInvert_;
import forall.HelpA;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class Home extends Basic_Buh {

    public Home(BUH_INVOICE_MAIN bim) {
        super(bim);
    }

    @Override
    protected void startUp() {
        refresh();
    }

    protected void refresh() {
        showTableInvert();
    }

    protected void loggaIn() {
        //
        HashMap<String, String> map = tableInvertToHashMap(TABLE_INVERT, DB.START_COLUMN);
        //
        GP_BUH.USER = map.get(DB.BUH_LICENS__USER);
        GP_BUH.PASS = map.get(DB.BUH_LICENS__PASS);
        //
        if (validateAndefineKundId()) {

        } else {
            HelpA.showNotification(LANG.VALIDATION_MSG_1);
//            System.exit(0);
        }
        //
    }

    private boolean validateAndefineKundId() {
        //[#SEQURITY#]
        HashMap<String, String> map = new HashMap();
        //
        try {
            GP_BUH.KUND_ID = HelpBuh.executePHP(DB.PHP_SCRIPT_MAIN,
                    DB.PHP_FUNC_DEFINE_KUNDID, JSon.hashMapToJSON(map));
        } catch (Exception ex) {
            Logger.getLogger(BUH_INVOICE_MAIN.class.getName()).log(Level.SEVERE, null, ex);
            GP_BUH.KUND_ID = "-1";

        }
        //
        return GP_BUH.KUND_ID.equals("-1") == false;
    }

    @Override
    public void showTableInvert() {
        //
        TableBuilderInvert_ tableBuilder = new TableBuilderInvert_(new OutPut(), null, getConfigTableInvert(), false, "buh_faktura_a");
        TABLE_INVERT = null;
        TABLE_INVERT = tableBuilder.buildTable_B(this);
        setMargin(TABLE_INVERT, 5, 0, 5, 0);
        showTableInvert(bim.jPanel_inloggning);
        //
        addTableInvertRowListener(TABLE_INVERT, this);
        //
    }

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        //
        RowDataInvert user = new RowDataInvertB("", DB.BUH_LICENS__USER, "ANVÄNDARNAMN", "", true, true, true);
        //
        RowDataInvert pass = new RowDataInvertB(RowDataInvert.TYPE_JPASSWORD_FIELD, "", DB.BUH_LICENS__PASS, "LÖSENORD", "", true, true, true);
        //
        RowDataInvert[] rows = {
            user,
            pass
        };
        //
        return rows;
    }

    @Override
    protected boolean fieldsValidated(boolean insert) {
        //
        if (containsEmptyObligatoryFields(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert())) {
            HelpA.showNotification(LANG.MSG_2);
            return false;
        }
        //
        if (containsInvalidatedFields(TABLE_INVERT, DB.START_COLUMN, getConfigTableInvert())) {
            HelpA.showNotification(LANG.MSG_1);
            return false;
        }
        //
        return true;
        //
    }

}
