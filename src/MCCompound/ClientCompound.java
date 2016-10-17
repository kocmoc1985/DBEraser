/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MCCompound;

import MyObjectTable.ShowMessage;
import CSCom.CRC;
import ForSending.CSMessage;
import Interfaces.ClientAbstrakt;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author KOCMOC
 */
public class ClientCompound extends ClientAbstrakt {

    private final ClientProtocolCompound protocolCompound = new ClientProtocolCompound(this);
    private final ShowMessage showMessage;
    private final String host;

    public ClientCompound(ShowMessage showMessage, String host) {
        this.showMessage = showMessage;
        this.host = host;
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

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientCompound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ClientProtocolCompound getProtocol() {
        return protocolCompound;
    }

    @Override
    public void authorization() {
        send(new CSMessage(CRC.SET_CLIENT_INFO, ""));
    }

    public void sendCSV(String csvContent) {
        queMessageSend(new CSMessage(CRC.QEW_COMPOUND_SEND_CSV, csvContent));
    }

    public void sendDoCopyDbfFiles() {
        queMessageSend(new CSMessage(CRC.QEW_COMPOUND_COPY_DBF, ""));
    }

    @Override
    public synchronized void send(CSMessage clMsg) {
         try {
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(clMsg);
            output.flush();
        } catch (IOException e) {
             JOptionPane.showMessageDialog(null, "Failed communicating with server");
        }
    }
    

    @Override
    public void recieve() throws ClassNotFoundException {
        try {
            if (socket != null) {
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                CSMessage sMsg = (CSMessage) input.readObject();
                client_protocol.queMessageRecieve(sMsg);
            }
        } catch (IOException e) {
            RUN = false;
        }
    }
}
