/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

/**
 *
 * @author MCREMOTE
 */
public class CreateAccountStatus {

    private final String responce;
    private boolean successful = false;
    //
    private final String CAS_ERR_01__USER_EXIST = "CASS_ERR_01";
    private final String CAS_ERR_02__MORE_THEN_TWO_SAME_MAC = "CASS_ERR_02";

    public CreateAccountStatus(String responce) {
        this.responce = responce;
        defineStatus();
    }

    
    
    private void defineStatus(){
        //
        if(responce == null || responce.isEmpty()){
             this.successful = false;
        }
        //
    }

}
