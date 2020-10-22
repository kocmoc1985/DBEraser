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
 * @author KOCMOC
 */
public abstract class Status {

    protected final String responce;
    private String messageToShow;
    private String errorMessage;
    
    private final String VALIDATION__V_ERR_0 = "V_ERR_0"; // user or pass parameters not set
    private final String VALIDATION__V_ERR_01 = "V_ERR_01"; // user does not exist
    private final String VALIDATION__V_ERR_02 = "V_ERR_02"; // password wrong
    //
    private final String ARS_ERR_02__USER_NOT_EXIST = "ARS_ERR_01";
    private final String ARS_ERR_01__OTHER = "ARS_ERR_02";
    //
    private final String CAS_ERR_01__USER_EXIST = "CASS_ERR_01";
    private final String CAS_ERR_02__MORE_THEN_TWO_SAME_MAC = "CASS_ERR_02";
    //
    private final String SAS_ERR_01__KUND_ID__NOT_DEFINED = "SAS_ERR_01";
    private final String SAS_ERR_02__NOT_MASTER_ACCOUNT = "SAS_ERR_02";
    private final String SAS_ERR_03__OTHER = "SAS_ERR_03";
    private final String SAS_ERR_04__USER_EXIST_ALREADY = "SAS_ERR_04";
    
//    V_ERR_0###]\n"; // user_or_pass_is_not_set
//    echo "[###status:V_ERR_01###]\n"; //user does not exist
//    echo "[###status:V_ERR_02###]\n"; //password wrong
//    echo "###status:SAS_ERR_01###";
//    echo "###status:SAS_ERR_02###";
//    echo "###status:SAS_ERR_04###";
//    echo "[###status:SAS_ERR_03###]\n";
//    echo "[###status:ARS_ERR_01###]\n";
//    echo "[###status:ARS_ERR_02###]\n";
//    echo "###status:CASS_ERR_01###";
//    echo "###status:CASS_ERR_02###";
    
    private void initMap(){
        //
        HashMap<String,String>map = new  HashMap<>();
        //
        //OBS!! The "V_ERR_XX" are defined in Home.class
        map.put("V_ERR_0",  VALIDATION__V_ERR_0 ); //user_or_pass_is_not_set
        map.put("V_ERR_01",  VALIDATION__V_ERR_01 );//user does not exist
        map.put("V_ERR_02",  VALIDATION__V_ERR_02 );//password wrong
        
        map.put("SAS_ERR_01",  SAS_ERR_01__KUND_ID__NOT_DEFINED);
        map.put("SAS_ERR_02",  SAS_ERR_02__NOT_MASTER_ACCOUNT );
        map.put("SAS_ERR_03",  SAS_ERR_03__OTHER);
        map.put("SAS_ERR_04",  SAS_ERR_04__USER_EXIST_ALREADY);
        
        map.put("ARS_ERR_01",  ARS_ERR_01__OTHER);
        map.put("ARS_ERR_02",  ARS_ERR_02__USER_NOT_EXIST);
        
        map.put("CAS_ERR_01", CAS_ERR_01__USER_EXIST);
        map.put("CAS_ERR_02", CAS_ERR_02__MORE_THEN_TWO_SAME_MAC);
    }

    public Status(String responce) {
        this.responce = responce;
        init();
    }
    
    private void init(){
        defineStatus();
    }
    
    protected abstract void defineStatus();
    
    public String getMessage() {
        return messageToShow;
    }
    
    public void setMessage(String errMsg,String infoMsg){
        this.errorMessage = errMsg;
        this.messageToShow = infoMsg;
    }
    
    public String getErrorMessage(){
        return this.errorMessage;
    }
    
    public void showNotification(){
        HelpA.showNotification(messageToShow);
    }

    
    
}
