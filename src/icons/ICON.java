/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icons;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author KOCMOC
 */
public class ICON {

    public static ImageIcon getImageIcon(String imageName, int width, int height) {
        ImageIcon icon = new ImageIcon(ICON.class.getResource(imageName));
        icon = scaleImageIconSmooth(icon, width, height);
        return icon;
    }
    
    private static ImageIcon scaleImageIconSmooth(ImageIcon ic, int width, int height) {
        Image img = ic.getImage();
        img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ic.setImage(img);
        return ic;
    }
}
