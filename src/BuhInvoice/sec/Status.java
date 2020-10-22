/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import forall.HelpA;

/**
 *
 * @author KOCMOC
 */
public abstract class Status {

    protected final String responce;
    private String messageToShow;
    private String errorMessage;

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
