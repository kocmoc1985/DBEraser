/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import forall.HelpA;
import java.util.HashMap;

/**
 *
 * @author MCREMOTE
 */
public class HttpResponce {

    protected final String responce;
    protected final String successfulMesage;
    protected HashMap<String, String> map = new HashMap<>();

    public static String GENERAL_ERR_0 = "ERR_0"; // This one is when it happens on java side

    public HttpResponce(String responce, String successfulMesage) {
        this.responce = responce;
        this.successfulMesage = successfulMesage;
        initErrorMap();
    }

    private void initErrorMap() {
        //
        map.put(GENERAL_ERR_0, "Okänt fel");
        //
        map.put("V_ERR_0", LANG.VALIDATION_MSG__V_ERR_0); //user_or_pass_is_not_set
        map.put("V_ERR_01", LANG.VALIDATION_MSG__V_ERR_01);//user does not exist
        map.put("V_ERR_02", LANG.VALIDATION_MSG__V_ERR_02);//password wrong
        map.put("V_ERR_03", LANG.VALIDATION_MSG__V_ERR_03);//user and ip combination banned
        //
        map.put("SAS_ERR_01", LANG.MSG_20);
        map.put("SAS_ERR_02", LANG.MSG_20_1);
        map.put("SAS_ERR_03", LANG.MSG_20);
        map.put("SAS_ERR_04", LANG.MSG_20_2);
        //
        map.put("ARS_ERR_01", LANG.MSG_18_1);
        map.put("ARS_ERR_02", LANG.MSG_18);
        //
        map.put("CAS_ERR_01", LANG.MSG_16_1);
        map.put("CAS_ERR_02", LANG.MSG_16_2);
        //
        map.put("DG_ERR_01", LANG.MSG_21_1);
        //
        map.put("DCB_ERR_01", LANG.MSG_23_1);
        map.put("DCB_ERR_02", LANG.MSG_23_2);
    }

    private void showNotification(String errMsg) {
        //
        if (errMsg == null && successfulMesage != null) {
            HelpA.showNotification(successfulMesage);
        } else {
            HelpA.showNotification(errMsg);
        }
        //
    }

    public void defineStatus() {
        // The error = null if the responce does not match any error from the "map" [2020-10-23]
        String errMsg = map.get(responce);
        showNotification(errMsg);
    }

}
