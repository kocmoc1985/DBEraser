/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import MyObjectTableInvert.Basic;
import MyObjectTableInvert.RowDataInvert;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public abstract class Basic_Buh extends Basic {

    protected final BUH_INVOICE_MAIN bim;

    public Basic_Buh(BUH_INVOICE_MAIN bim) {
        this.bim = bim;
        initOther();
    }

    private void initOther() {
        startUp();
    }

    protected abstract void startUp();

    protected String getKundId() {
        return bim.getKundId();
    }

    protected String requestJComboValuesHttp(String php_function, String keyOne, String keyTwo) {
        //
        String comboString;
        //
        String json = bim.getSELECT_kundId();
        //
        try {
            //
            String json_str_return = HelpBuh.http_get_content_post(HelpBuh.execute(DB.PHP_SCRIPT_MAIN,
                    php_function, json));
            //
            //
            comboString = JSon.phpJsonResponseToComboBoxString(json_str_return, keyOne, keyTwo);
            //
//            System.out.println("combo string: " + comboString);
            //
        } catch (Exception ex) {
            Logger.getLogger(Basic_Buh.class.getName()).log(Level.SEVERE, null, ex);
            comboString = null;
        }
        //
        return comboString;
    }
    
    

    @Override
    public RowDataInvert[] getConfigTableInvert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showTableInvert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initializeSaveIndicators() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getUnsaved(int nr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
