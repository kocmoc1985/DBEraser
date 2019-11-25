/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCCompound;

import MyObjectTable.ShowMessage;
import CSCom.CRC;
import ForSending.CSMessage;
import Interfaces.ClientAbstrakt;
import supplementary.GP;

/**
 *
 * @author KOCMOC
 */
public class ClientCompound extends ClientAbstrakt {

    private final ClientProtocolCompound protocolCompound = new ClientProtocolCompound(this);
    private final ShowMessage showMessage;

    public ClientCompound(ShowMessage showMessage, String host) {
        this.showMessage = showMessage;
        this.host = host;
        GP.USE_SSL_SOCKETS = false;
        go();
    }

    private void go() {
        //
        start(host, 1111, protocolCompound);
        //
        if (connected()) {
            showMessage.showMessage("Connection to NPMS ok: " + host);
        }
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
        send(new CSMessage(CRC.SET_CLIENT_INFO, ""));
    }

    /**
     * OBS! This one is triggered manually by clicking a button
     * Sends the .csv file to the central server running NPMS.
     * The path and file ("Z:/prod_plan.csv") name on server to which the file is sent, is defined in 
     * "properties/npms_prod_plan.properties" which is on NPMS server side.
     * @param csvContent 
     */
    public void sendCSV(String csvContent) {
        queMessageSend(new CSMessage(CRC.QEW_COMPOUND_SEND_CSV, csvContent));
    }

    /**
     * Triggers file copying on remote server with help of NPMS server
     */
    public void sendDoCopyDbfFiles() {
        queMessageSend(new CSMessage(CRC.QEW_COMPOUND_COPY_DBF, ""));
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
        closeConnectionOnClose();
    }
}
