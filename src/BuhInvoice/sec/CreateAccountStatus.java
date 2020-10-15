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
    //
    private String messageToShow;

    public CreateAccountStatus(String responce) {
        this.responce = responce;
        defineStatus();
    }

    
    public boolean isSuccessful(){
        return successful;
    }
    
    public String getMessage(){
        return messageToShow;
    }
    
    private void defineStatus(){
        //
        if(responce == null || responce.isEmpty() || responce.equals("null")){
             this.messageToShow = LANG.MSG_16;
        }else if(responce.equals(CAS_ERR_01__USER_EXIST)){
            this.messageToShow = LANG.MSG_16_1;
        }else if(responce.equals(CAS_ERR_02__MORE_THEN_TWO_SAME_MAC)){
            this.messageToShow = LANG.MSG_16_2;
        }
        //
    }

}
