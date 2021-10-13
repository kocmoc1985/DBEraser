/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icons;

import BuhInvoice.ForetagA;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author KOCMOC
 */
public class ICON {

    public static ImageIcon getImageIcon_b(String filePathAndName, int width, int height) {
        ImageIcon icon = getImageIcon(new File(filePathAndName));
        if(icon != null){
            icon = scaleImageIconSmooth(icon, width, height);
        }
        return icon;
    }
    
    private static ImageIcon getImageIcon(File f) {
        try {
           Image im = ImageIO.read(f);
           return new ImageIcon(im);
        } catch (IOException ex) {
//            Logger.getLogger(ForetagA.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
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
