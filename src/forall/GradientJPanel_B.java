/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forall;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import javax.swing.JPanel;

/**
 *
 * @author KOCMOC
 */
public class GradientJPanel_B extends JPanel {

    public GradientJPanel_B() {
    }

    
    public GradientJPanel_B(LayoutManager layout) {
        super(layout);
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Color color1 = getBackground();
        Color color2 = color1.brighter();
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(
                0, 0, color2, 0, h, color1);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}
