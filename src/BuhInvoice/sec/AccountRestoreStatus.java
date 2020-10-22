/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

/**
 *
 * @author KOCMOC
 */
public class AccountRestoreStatus {

    private final String responce;
    private final String ARS_ERR_02__USER_NOT_EXIST = "ARS_ERR_01";
    private final String ARS_ERR_01__OTHER = "ARS_ERR_02";
    private String messageToShow;

    public AccountRestoreStatus(String responce) {
        this.responce = responce;
        defineStatus();
    }

    public String getMessage() {
        return messageToShow;
    }

    private void defineStatus() {
        //
        if (responce == null || responce.isEmpty() || responce.equals("null")) {
            this.messageToShow = LANG.MSG_18_1;
        } else if (responce.equals(ARS_ERR_02__USER_NOT_EXIST)) {
            this.messageToShow = LANG.MSG_18;
        } else if (responce.equals(ARS_ERR_01__OTHER)) {
            this.messageToShow = LANG.MSG_18_1;
        } else {
            this.messageToShow = LANG.MSG_18_0;
        }
        //
    }

}
