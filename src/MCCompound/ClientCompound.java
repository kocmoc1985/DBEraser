/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCCompound;

import MyObjectTable.ShowMessage;
import CSCom.CRC;
import ForSending.CSMessage;
import Interfaces.ClientAbstrakt;
import Interfaces.ClientProtocolIF;
import supplementary.GP;
import supplementary.HelpM2;

/**
 *
 * @author KOCMOC
 */
public class ClientCompound extends ClientAbstrakt {

    private final ClientProtocolCompound protocolCompound = new ClientProtocolCompound(this);
    private final ShowMessage showMessage;
    private boolean CONNECTED_TO_NPMS = false;

    public ClientCompound(ShowMessage showMessage, String host) {
        this.showMessage = showMessage;
        this.host = host;
        GP.USE_SSL_SOCKETS = false;
        go();
    }
    
    public boolean isConnected(){
        return CONNECTED_TO_NPMS;
    }

    private void go() {
        //
        start(host, 1111, protocolCompound);
        //
        if (CONNECTED_TO_NPMS) {
            showMessage.showMessage("Connection to NPMS ok: " + host);
        }
    }

    @Override
    public void start(String host, int port, ClientProtocolIF client_protocol) {
        //
        this.client_protocol = client_protocol;
        //================
        if (connect(host, port)) {
            CONNECTED_TO_NPMS = true;
            authorization();
            requestLastReplicationOperationTime();
            //=================
            Thread thisThr = new Thread(this);
            thisThr.setName("ClientCompound-Thr");
            thisThr.start();
        }
        //
    }

//    public void disconnect() {
//        try {
//            socket.close();
//        } catch (IOException ex) {
//            Logger.getLogger(ClientCompound.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public ClientProtocolCompound getProtocol() {
        return protocolCompound;
    }

    @Override
    public void authorization() {
        send(new CSMessage(CRC.SET_CLIENT_INFO, GP.CLIENT_TYPE_PROD_PLAN));
    }
    
    public void requestLastReplicationOperationTime(){
        queMessageSend(new CSMessage(CRC.PROD_PLAN_GET_LAST_OP_TIME, null));
    }

    /**
     * OBS! This one is triggered manually by clicking a button Sends the .csv
     * file to the central server running NPMS. The path and file
     * ("Z:/prod_plan.csv") name on server to which the file is sent, is defined
     * in "properties/npms_prod_plan.properties" which is on NPMS server side.
     *
     * @param csvContent
     */
    public void sendCSV(String csvContent) {
        if(CONNECTED_TO_NPMS){
           queMessageSend(new CSMessage(CRC.PROD_PLAN_SEND_CSV, csvContent)); 
        }
    }

    /**
     * Triggers file copying on remote server with help of NPMS server
     */
    public void sendDoCopyDbfFiles() {
        if(CONNECTED_TO_NPMS){
            queMessageSend(new CSMessage(CRC.PROD_PLAN_COPY_DBF, ""));
        }else{
            HelpM2.showInformationMessage("Connected to NPMS: " + CONNECTED_TO_NPMS);
        }
        
    }

//    @Override
//    public synchronized void send(CSMessage clMsg) {
//         try {
//            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
//            output.writeObject(clMsg);
//            output.flush();
//        } catch (IOException e) {
//             JOptionPane.showMessageDialog(null, "Failed communicating with server");
//        }
//    }
//    @Override
//    public void recieve() throws ClassNotFoundException {
//        try {
//            if (socket != null) {
//                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
//                CSMessage sMsg = (CSMessage) input.readObject();
//                client_protocol.queMessageRecieve(sMsg);
//            }
//        } catch (IOException e) {
//            RUN = false;
//        }
//    }
    @Override
    public void actionsBeforeConnect() {

    }

    @Override
    public void actionsAfterConnect() {

    }

    @Override
    public void errorActionOnConnectionLost() {
        CONNECTED_TO_NPMS = false;
        closeConnectionOnClose();
    }

    @Override
    public void errorActionsOnConnectionFailed(Exception ex) {
        CONNECTED_TO_NPMS = false;
        showMessage.showMessage("Connection to NPMS failed: " + host);
    }

}
