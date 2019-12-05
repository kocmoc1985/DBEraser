/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCCompound;

import CSCom.CRC;
import CSCom.SRC;
import ForSending.CSMessage;
import Interfaces.ClientBasic;
import Interfaces.ClientProtocolAbstrakt;
import Interfaces.MyCSMessage;
import forall.GP;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author KOCMOC
 */
public class ClientProtocolCompound extends ClientProtocolAbstrakt {

    private ShowProgress showProgress;
    private PROD_PLAN PROD_PLAN;
    private final ClientCompound clientCompound;
    private final static String CURRENT_OPERATION_COPY = "COPY 1/2";
    private final static String CURRENT_OPERATION_REPLICATE = "REPLICATE 2/2";

    public ClientProtocolCompound(ClientBasic cl) {
        super(cl);
        clientCompound = (ClientCompound) cl;
    }

    public void setShowProgress(ShowProgress showProgress) {
        this.showProgress = showProgress;
    }

    public void setProdPlan(PROD_PLAN pp) {
        this.PROD_PLAN = pp;
    }

    @Override
    public void handleRequest(MyCSMessage msg) {
        if (msg.getCommando().equals(CRC.PROD_PLAN_SEND_CSV)) {
            //
            processGetResponseOnSendCsv(msg);
            //
            disconnect(); // The disconnect is done in order to "release" file lock
            //
        } else if (msg.getCommando().equals(CRC.PROD_PLAN_COPY_DBF_OK)) {
            //
            processCopyDbfDoneOrFailed(msg);
            //
            disconnect(); // The disconnect is done in order to "release" file lock
            //
        } else if (msg.getCommando().equals(SRC.FEEDBACK_ANY_CLIENT)) {
            //
            processFeedBack(msg);
            //
        } else if(msg.getCommando().equals(CRC.PROD_PLAN_OPERATION_IN_PROGRESS)){
            //
            processFeedBack(msg);
            //
        }
    }
    
    private void disconnect(){
          try { 
                clientCompound.disconnect();
            } catch (IOException ex) {
                Logger.getLogger(ClientProtocolCompound.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    private void processFeedBack(MyCSMessage msg) {
        String feed_back = (String) msg.getObject();
        showProgress.showMessageAppend(feed_back);
    }
    
    private void processGetResponseOnSendCsv(MyCSMessage msg) {
        //
        Boolean send_ok = (Boolean) msg.getObject();
        //
        if (send_ok == false) {
            JOptionPane.showMessageDialog(null, "Failed sending .csv file to server");
        } else {
            JOptionPane.showMessageDialog(null, ".csv File was successfully sent to server");
        }
    }

    private void processCopyDbfDoneOrFailed(MyCSMessage msg) {
        //
        Boolean copy_ok = (Boolean) msg.getObject();
        Boolean replicate_ok = false;
        //
        if (copy_ok) {
            //
            showProgress.showMessageAppend("Copy operation ended without failures");
            //
            //
            showProgress.startCountDown();
            //
            if (PROD_PLAN.getTestMode() == false) {
                replicate_ok = PROD_PLAN.sql_replication_procedure(CURRENT_OPERATION_REPLICATE, showProgress);
            }
            //
        } else {
            showProgress.showMessageAppend("Copy operation failed");
            showProgress.setTitle("Failed");
            //
            // Not needed to be called here, it's called a litle bit below
//            clientCompound.send(new CSMessage(CRC.PROD_PLAN_OPERATION_COMPLETE, false));
            //
        }
        //
        if (replicate_ok) {
            //
            showProgress.showMessageAppend("Operations ended without failures");
            //
            showProgress.setComplete("Complete");
            //
            showProgress.setIconImage(GP.COMPLETE_IMAGE_ICON_URL);
            //
            PROD_PLAN.buildProdPlanTable_forward();
            //
//            clientCompound.queMessageSend(new CSMessage(CRC.PROD_PLAN_OPERATION_COMPLETE, true));
            clientCompound.send(new CSMessage(CRC.PROD_PLAN_OPERATION_COMPLETE, true));
            //
        } else {
            //
            showProgress.showMessageAppend("Replication procedure failed");
            showProgress.setComplete("Ended with failures");
            //
//            clientCompound.queMessageSend(new CSMessage(CRC.PROD_PLAN_OPERATION_COMPLETE, false));
            clientCompound.send(new CSMessage(CRC.PROD_PLAN_OPERATION_COMPLETE, false));
            //
        }
        //
        showProgress.goToEnd();
        //
        showProgress.enableHideOnClose();
    }
}
