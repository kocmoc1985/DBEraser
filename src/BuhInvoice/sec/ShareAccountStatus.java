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
public class ShareAccountStatus extends Status{

    private final String SAS_ERR_01__KUND_ID__NOT_DEFINED = "SAS_ERR_01";
    private final String SAS_ERR_02__NOT_MASTER_ACCOUNT = "SAS_ERR_02";
    private final String SAS_ERR_03__OTHER = "SAS_ERR_03";
    private final String SAS_ERR_04__USER_EXIST_ALREADY = "SAS_ERR_04";

    public ShareAccountStatus(String responce) {
        super(responce);
    }
    
    @Override
    protected void defineStatus() {
        //
        if (responce == null || responce.isEmpty() || responce.equals("null")) {
            setMessage(null, LANG.MSG_20);
        } else if (responce.equals(SAS_ERR_01__KUND_ID__NOT_DEFINED)) {
            setMessage(SAS_ERR_01__KUND_ID__NOT_DEFINED, LANG.MSG_20);
        } else if (responce.equals(SAS_ERR_02__NOT_MASTER_ACCOUNT)) {
            setMessage(SAS_ERR_02__NOT_MASTER_ACCOUNT, LANG.MSG_20_1);
        }else if (responce.equals(SAS_ERR_03__OTHER)) {
            setMessage(SAS_ERR_03__OTHER, LANG.MSG_20);
        } else if (responce.equals(SAS_ERR_04__USER_EXIST_ALREADY)) {
            setMessage(SAS_ERR_04__USER_EXIST_ALREADY, LANG.MSG_20_2);
        }else {
            setMessage(null, LANG.MSG_20_0);
        }
        //
    }
}
