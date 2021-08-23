/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import BuhInvoice.LAFakturering;
import icons.IconUrls;

/**
 *
 * @author KOCMOC
 */
public class HTMLDialog_C extends HTMLDialog {
    
    public HTMLDialog_C(java.awt.Frame parent, boolean modal, int w, int h, String title) {
       super(parent, modal, w, h, title);
    }

    @Override
    protected String buildHTML() {
        //
        String path_new_icon = IconUrls.CREATE_NEW_IMAGE_ICON_URL.toString();
        String path_save_icon = IconUrls.ACCEPT_SAVE_IMAGE_ICON_URL.toString();
        //
        return "<html>"
                + "<body style='background-color:#F1F3F6'>" //style='background-color:#F1F3F6'
                + "<div style='margin-left:10px;color:gray;background-color:#EEF0F4;padding:5 5 5 5px;'>"
                + "<table>"
                + "<tr>"
                + "<td style='width:5%'><img src='" + imgPath + "' alt='LAFakturering' width='32' height='32' >" + "</td>"
                + "<td><h1>" + "Oj!" + "</h1></td>"
                + "</tr>"
                + "</table>"
                //
                + "<table style='font-size:14px'>"
                + "<tr><td>Du saknar registrerade kunder. Var god gör följande:</td></tr>"
                + "</table>"
                + "</div>"
                //
                + "<br>"
                //
                + "<div style='margin-left:10px;color:gray;background-color:#EEF0F4;padding:5 5 5 5px;'>" //EEF0F4, EEF0F4
                + "<table style='font-size:14px'>"
                //
                + "<tr>"
                + "<td>2. Gå till flik \"" + LAFakturering.TAB_KUDNER + "\" och skapa åtminstone en kund." 
                + " Glöm ej att spara ändringar genom att klicka på <img src='" + path_save_icon + "' alt='save' width='32' height='32' >"
                + "<td>"
                + "</tr>"
                //
                + "<tr>"
                + "<td>"
                + "3. När du har skapat åtminstone en kund kan du börja fakturera genom att klicka på " + "<img src='" + path_new_icon + "' alt='new' width='32' height='32' >" + " under fliken \"" + LAFakturering.TAB_INVOICES_OVERVIEW + "\". "
                + " <td>"
                + "</tr>"
                //
                + footerHTML()
                //
                + "</table>"
                + "</div>"
                + "</body>"
                + "</html>";
    }
}
