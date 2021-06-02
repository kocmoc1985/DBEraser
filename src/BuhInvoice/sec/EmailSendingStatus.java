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
public class EmailSendingStatus {

    private int attachmentStatus;
    private int emailStatus;

    /**
     * Format: -1;1 where [-1 -> Status of sending attachment] and [1 -> Status
     * of sending email]
     *
     * @param status
     */
    public EmailSendingStatus(String status) {
        extract(status);
    }

    private void extract(String status) {
        //
        if(status.toLowerCase().contains("exception")){ // 2021-06-02
            attachmentStatus = 0;
            emailStatus = 0;
            return;
        }
        //
        String[] arr = status.split(";");
        //
        attachmentStatus = Integer.parseInt(arr[0]);
        //
        emailStatus = Integer.parseInt(arr[1]);
        //
    }

    public boolean allSuccessful() {
        toString();
        return attachmentStatus == 1 && emailStatus == 1;
    }
    
    public boolean emailSendingSuccessful(){
        return emailStatus == 1;
    }

    @Override
    public String toString() {
        return "ATTACHMENT: " + attachmentStatus + "  EMAIL: " + emailStatus; 
    }
    
    
}
