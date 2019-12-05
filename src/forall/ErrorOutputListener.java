/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forall;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

/**
 *
 * @author KOCMOC
 */
public class ErrorOutputListener implements Runnable,ActionListener {

    private File file;
    private double prevFileSize = 0;
    private JComponent blinkRedComponent;
    private static final ArrayList<Long> OFFSET_LIST = new ArrayList<Long>();
    private JPanel choosePanel;
    private boolean oneTimeFlag = false;
    private static JTextArea OUTPUT;
    private static String LATEST_ERR_OUTPUT_FILE_PATH;
    
    /**
     * 
     * @param errOutPutFilePath - The path of the latest "err_output file" - usually defined in "HelpM.err_output_to_file()"
     * @param blinkRedComponent - The component which blinks in case of a new exception is written to the file
     * @param output - The JTextArea in which the failures are shown
     * @param choosePanel - The panel for choosing the exception to show. This one can be manually added to the gui
     */
    public ErrorOutputListener(String errOutPutFilePath, JComponent blinkRedComponent, JTextArea output, JPanel choosePanel) {
         //
        if (errOutPutFilePath == null || errOutPutFilePath.isEmpty()) {
            return;
        }
        //
        this.file = new File(errOutPutFilePath);
        this.blinkRedComponent = blinkRedComponent;
        OUTPUT = output;
        this.choosePanel = choosePanel;
        LATEST_ERR_OUTPUT_FILE_PATH = errOutPutFilePath;
        //
        if (file.exists() == false) {
            return;
        }
        //
        prevFileSize = file.length(); // size in bytes
        //
        startThread();
    }

    private void startThread() {
        Thread x = new Thread(this);
        x.start();
    }

    @Override
    public void run() {
        //
        while (true) {
            //
            double fileSize = file.length();
            //
            if (prevFileSize != fileSize) {
                //
                blinkRedOnErrorAddedToFile();
                //
                prevFileSize = fileSize;
                OFFSET_LIST.add((long) prevFileSize);
                //
                if (choosePanel != null) {
                    //
                    if (oneTimeFlag == false) {
                        oneTimeFlag = true;
                        choosePanel.setLayout(new GridLayout(1, 100));
                    }
                    //
                    choosePanel.add(buildButton());
                    //
                }
            }
            //
            wait_(1000);
            //
        }
    }

    private JButton buildButton(){
        //
        int nr = OFFSET_LIST.size();
        //
        JButton btn = new JButton(""+nr);
        btn.addActionListener(this);
        //
        if (OFFSET_LIST.size() == 1) {
            btn.setName("" + 0); // saving offset
            return btn;
        } else {
            btn.setName(""+ OFFSET_LIST.get(OFFSET_LIST.size() - 2));// saving offset
            return btn;
        }
    }

    private static long getOffset() {
        //
        if (OFFSET_LIST.isEmpty()) {
            return -1;
        }
        //
        if (OFFSET_LIST.size() == 1) {
            return 0;
        } else {
            return OFFSET_LIST.get(OFFSET_LIST.size() - 2);
        }
    }

    private void blinkRedOnErrorAddedToFile() {
//        System.out.println("FILE CHANGED");
        if (blinkRedComponent != null) {
            Border prevBorder = blinkRedComponent.getBorder();
            blinkRedComponent.setBorder(BorderFactory.createLineBorder(Color.red, 5));
            wait_(500);
            blinkRedComponent.setBorder(prevBorder);
            wait_(500);
            blinkRedComponent.setBorder(BorderFactory.createLineBorder(Color.red, 5));
            wait_(500);
            blinkRedComponent.setBorder(prevBorder);
        }
    }

    private synchronized void wait_(int millis) {
        try {
            wait(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(ErrorOutputListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void showAll() {
        //
        ArrayList<String> list = read_Txt_To_ArrayList(LATEST_ERR_OUTPUT_FILE_PATH);
        //
        OUTPUT.setText("");
        //
        for (String string : list) {
            OUTPUT.append(string + "\n");
        }
        //
    }

    public static void showLatest() {
        //
        long offset = getOffset();
        //
        if (offset == -1) {
            return;
        }
        //
        ArrayList<String> list = read_Txt_To_ArrayList(LATEST_ERR_OUTPUT_FILE_PATH, offset);
        //
        OUTPUT.setText("");
        //
        for (String string : list) {
            OUTPUT.append(string + "\n");
        }
        //
        OUTPUT.setCaretPosition(0);
        //
    }
    
    public static void showGiven(long offset) {
        //
        if (offset == -1) {
            return;
        }
        //
        ArrayList<String> list = read_Txt_To_ArrayList(LATEST_ERR_OUTPUT_FILE_PATH, offset);
        //
        OUTPUT.setText("");
        //
        for (String string : list) {
            OUTPUT.append(string + "\n");
        }
        //
        OUTPUT.setCaretPosition(0);
        //
    }
    
    

    private static ArrayList<String> read_Txt_To_ArrayList(String filename, long offset) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            RandomAccessFile raf = new RandomAccessFile(filename, "r");
            raf.seek(offset);
            String rs = raf.readLine();
            while (rs != null) {
                list.add(rs);
                rs = raf.readLine();
            }
            //
        } catch (IOException e) {
            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, e);
        }
        //
        return list;
    }

    private static ArrayList<String> read_Txt_To_ArrayList(String filename) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String rs = br.readLine();
            while (rs != null) {
                list.add(rs);
                rs = br.readLine();
            }

        } catch (IOException e) {
//            Logger.getLogger(HelpA.class.getName()).log(Level.SEVERE, null, e);
        }
        //
        return list;
    }

//    public static void main(String[] args) {
//        ErrorOutputListener el = new ErrorOutputListener("err_output/err_2018-02-20 14_20.txt",null);
//    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() instanceof JButton){
            JButton btn = (JButton)ae.getSource();
            Long offset = Long.parseLong(btn.getName());
//            System.out.println("OFFSET: " + offset);
            showGiven(offset);
        }
    }
}
