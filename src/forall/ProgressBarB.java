/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forall;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import supplementary.GP;

/**
 *
 * @author Administrator
 */
public class ProgressBarB extends JFrame {

    private JProgressBar pbar;
    private JPanel panel;
    private boolean first_time = true;
    private boolean updateReady = false;
    public int max;
    private String title;
    private URL imageIconUrl;

    public ProgressBarB(String title) {
        this.title = title;
        init(title);
    }

    public ProgressBarB(String title, int max, URL imageIconUrl) {
        this.title = title;
        this.max = max;
        this.imageIconUrl = imageIconUrl;
        init(title);
    }

    public void setTitleText(String text) {
        this.setTitle(text);
    }

    private void init(String title) {
        pbar = new JProgressBar(0, 10);
        pbar.setPreferredSize(new Dimension(400, 50));
        panel = new JPanel();
//        panel.setLayout(new GridLayout(1, 1));
        panel.add(pbar);
        this.setContentPane(panel);
        //
        this.setTitle(title);
        //
        if (imageIconUrl == null) {
            this.setIconImage(new ImageIcon(GP.PROGRESS_BAR_B_IMAGE_PATH).getImage());
        } else {
            this.setIconImage(new ImageIcon(imageIconUrl).getImage());
        }
        //
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //is different for different moduls!
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - 400, (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - 50);
        this.pack();
        this.setVisible(false);
        this.setAlwaysOnTop(false);
    }

    public void hideProgressBar() {
        this.setVisible(false);
    }

    public synchronized void go(int value, int max) {
        updateBar(value, max);
    }

    public synchronized void go(int value) {
        go(value, max);
    }

    /**
     * Note that this method is launched by a separate thread
     *
     * @param value - current value
     * @param max - max limit
     */
    public void updateBar(int value, int max) {
        if (first_time) {
            this.max = max;
            pbar.setMaximum(max);
            pbar.setMinimum(1);
            this.setVisible(true);
            first_time = false;
            this.paint(this.getGraphics());
        }

        if (value == max) {
            this.paint(this.getGraphics());
            updateReady = true;
            pbar.setValue(value);
            wait_(50);
            this.setVisible(false);
        } else {
            pbar.setValue(value);
            this.paint(this.getGraphics());
            wait_(50);
        }
    }

    public boolean updateReady() {
        return this.updateReady;
    }

    private synchronized void wait_(int millis) {
        try {
            wait(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(ProgressBarB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
