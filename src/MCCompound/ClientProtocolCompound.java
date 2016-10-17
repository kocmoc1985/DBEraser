/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCCompound;

import CSCom.CRC;
import CSCom.SRC;
import Interfaces.ClientBasic;
import Interfaces.ClientProtocolAbstrakt;
import Interfaces.MyCSMessage;
import forall.GP;
import javax.swing.JOptionPane;

/**
 *
 * @author KOCMOC
 */
public class ClientProtocolCompound extends ClientProtocolAbstrakt {

    private ShowProgress showProgress;
    private PROD_PLAN PROD_PLAN;
    private ClientCompound clientCompound;
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
        if (msg.getCommando().equals(CRC.QEW_COMPOUND_SEND_CSV)) {
            //
            processGetResponseOnSendCsv(msg);
            //
            clientCompound.disconnect();
            //
        } else if (msg.getCommando().equals(CRC.QEW_COMPOUND_COPY_DBF_OK)) {
            //
            showProgress.setVisible(true);
            //
            processCopyDbfDoneOrFailed(msg);
            //
            clientCompound.disconnect();
            //
        } else if (msg.getCommando().equals(SRC.FEEDBACK_ANY_CLIENT)) {
            //
            showProgress.setVisible(true);
            //
            processFeedBack(msg);
        }
    }

    private void processFeedBack(MyCSMessage msg) {
        String feed_back = (String) msg.getObject();
        showProgress.showMesasge(CURRENT_OPERATION_COPY, feed_back);
    }

    private void processGetResponseOnSendCsv(MyCSMessage msg) {
        Boolean send_ok = (Boolean) msg.getObject();
        //
        if (send_ok == false) {
            JOptionPane.showMessageDialog(null, "Failed sending .csv file to server");
        } else {
            JOptionPane.showMessageDialog(null, ".csv File was successfully sent to server");
        }
    }

    private void processCopyDbfDoneOrFailed(MyCSMessage msg) {
        Boolean copy_ok = (Boolean) msg.getObject();
        Boolean replicate_ok = false;
        //
        if (copy_ok) {
            //
            showProgress.showMessageAppend("", "Copy operation ended without failures");
            //
            //
            showProgress.startCountDown();
            //
            if (PROD_PLAN.getTestMode() == false) {
                replicate_ok = PROD_PLAN.sql_replication_procedure(CURRENT_OPERATION_REPLICATE, showProgress);
            }
            //
        } else {
            showProgress.showMessageAppend("", "Copy operation failed");
            showProgress.setTitle("Failed");

        }
        //
        //
        if (replicate_ok) {
            showProgress.showMessageAppend("COMPLETE!", "Operations ended without failures");
            //
            showProgress.setComplete();
            //
            showProgress.setTitle("Complete");
            //
            showProgress.setIconImage(GP.COMPLETE_IMAGE_ICON_URL);
            //
            PROD_PLAN.buildProdPlanTable_forward();
            //
            showProgress.setTitle("Complete"); // i repeat it here once more time and it's not failure
        } else {
            showProgress.setComplete();
            showProgress.setTitle("Failed");
        }
        //
        showProgress.goToEnd();
        //
        showProgress.enableHideOnClose();
    }
}
