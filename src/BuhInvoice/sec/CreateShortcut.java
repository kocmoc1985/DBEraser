/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import BuhInvoice.HelpBuh;
import forall.HelpA;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class CreateShortcut {

    private final String iconNameWithExt = "ic.ico";
    private final String shortCutName = "LAFakturering";
    private final String appNameWithExtToLaunch = "la.jar";
    private boolean createBothOnDesktopAndLaFolder = true; // By default it will create at both locations

    public static void main(String[] args) {
        //
        CreateShortcut cs = new CreateShortcut();
        //
    }

    public CreateShortcut(boolean createBothOnDesktopAndLaFolder) {
        this.createBothOnDesktopAndLaFolder = createBothOnDesktopAndLaFolder;
        go();
    }

    public CreateShortcut() {
        go();
    }

    private void go() {
        //
        if (HelpBuh.IS_MAC_OS) {
            return;
        }
        //
        HelpA.create_dir_if_missing(IO.IO_DIR);
        //
        try {
            copy_file_b(getClass().getResourceAsStream("/icons/ic.ico"), IO.IO_DIR + iconNameWithExt);
        } catch (Exception ex) {
            Logger.getLogger(CreateShortcut.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        //
        File f2 = new File(IO.IO_DIR + "shortcut_.log");
        String fileName = IO.IO_DIR + "shortcut.cmd";
        File f1 = new File(fileName);
        //
        if (this.createBothOnDesktopAndLaFolder) {
            // Create on desktop
            write(fileName, createShortCutScript_(shortCutName, appNameWithExtToLaunch, "\\" + IO.getIO_DIR() + "\\" + iconNameWithExt, true));
            HelpA.run_application_with_associated_application__b(f1);
            startDeleteThread(f1, f2);
            //
        }
        //
        // Create in the la folder (root)
        write(fileName, createShortCutScript_(shortCutName, appNameWithExtToLaunch, "\\" + IO.getIO_DIR() + "\\" + iconNameWithExt, false));
        HelpA.run_application_with_associated_application__b(f1);
        startDeleteThread(f1, f2);
        //
    }

    private void startDeleteThread(File f1, File f2) {
        //
        Thread x = new Thread(new DeleteThred(f1, f2));
        x.start();
        //
        try {
            x.join(); // Very important!! [2021-10-08]
        } catch (InterruptedException ex) {
            Logger.getLogger(CreateShortcut.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
    }

    private String[] createShortCutScript_(String shortcutName, String appNameWithExt, String logoPath, boolean desktop) {
        //
        String destinationPath;
        //
        if (desktop) {
            destinationPath = "SET Esc_LinkDest=%%HOMEDRIVE%%%%HOMEPATH%%\\Desktop\\!LinkName!.lnk"; // Skapar ikonen på skrivborder
        } else {
            destinationPath = "SET Esc_LinkDest=!LinkName!.lnk"; // Skapar ikonen i samma map/root
        }
        //
        return new String[]{
            "@echo off",
            "SETLOCAL ENABLEDELAYEDEXPANSION",
            "SET LinkName=" + shortcutName,
            destinationPath,
            "SET Esc_LinkTarget=%CD%\\" + appNameWithExt, //OBS! %CD% = current dir
            "SET cSctVBS=CreateShortcut.vbs",
            "SET LOG=\"la\\%~N0_.log\"", // "SET LOG=\".\\%~N0_.log\"" --> OBS! Here is the output of the "shortcut_.log" defined
            "((",
            "echo Set oWS = WScript.CreateObject^(\"WScript.Shell\"^) ",
            "echo sLinkFile = oWS.ExpandEnvironmentStrings^(\"!Esc_LinkDest!\"^)",
            "echo Set oLink = oWS.CreateShortcut^(sLinkFile^)",
            "echo oLink.TargetPath = oWS.ExpandEnvironmentStrings^(\"!Esc_LinkTarget!\"^)",
            "echo oLink.IconLocation = \"%CD%" + logoPath + "\"", //  \"%CD%\\la\\ic.ico\""
            "echo oLink.WorkingDirectory = \"%CD%\"",
            "echo oLink.Save",
            ")1>!cSctVBS!",
            "cscript //nologo .\\!cSctVBS!",
            "DEL !cSctVBS! /f /q",
            ")1>>!LOG! 2>>&1"
        };
        //
    }

    private void write(String fileToWriteTO, String[] signalArr) {
        try {
            // Create file
            FileWriter fstream = new FileWriter(fileToWriteTO, false);
            BufferedWriter out = new BufferedWriter(fstream);
            for (int i = 0; i < signalArr.length; i++) {
                if (signalArr[i] != null) {
                    out.write(signalArr[i]);
                } else {
                    out.write("EMPTY");
                }
                out.newLine();
                out.flush();
                //Close the output stream
            }
            out.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    class DeleteThred implements Runnable {

        private final File script;
        private final File log;

        public DeleteThred(File script, File log) {
            this.script = script;
            this.log = log;
        }

        @Override
        public void run() {
            //
            wait_(1000);
            //
            this.script.delete();
            this.log.delete();
        }

        private synchronized void wait_(int millis) {
            try {
                wait(millis);
            } catch (InterruptedException ex) {
                Logger.getLogger(CreateShortcut.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean copy_file_b(InputStream source, String destination) {
        //
        boolean succeess = true;
        //
//        System.out.println("Copying ->" + source + "\n\tto ->" + destination);
        //
        try {
            Files.copy(source, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            succeess = false;
        }
        //
        return succeess;

    }

}
