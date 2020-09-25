/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import BuhInvoice.DB;

/**
 *
 * @author MCREMOTE
 */
public class IO {

    private final static String LAST_INPUT_DIR = "";
    public static final String VAR_REFERENS = LAST_INPUT_DIR + DB.BUH_FAKTURA__VAR_REFERENS;
    public static final String ER_REFERENS = LAST_INPUT_DIR + DB.BUH_FAKTURA__ER_REFERENS;
    public static final String DROJSMALSRANTA = LAST_INPUT_DIR + DB.BUH_FAKTURA__DROJSMALSRANTA;

    public static final String getErReferens(String kundFakturaId) {
        return ER_REFERENS + "_" + kundFakturaId;
    }
}
