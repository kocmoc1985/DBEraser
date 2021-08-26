/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import BuhInvoice.GP_BUH;
import BuhInvoice.LAFakturering;
import forall.GP;
import icons.IconUrls;
import java.awt.Desktop;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 * IF you plan to use it for another project, take the example from
 * "MyCommons.myDialogs" [2020-06-09]
 *
 * @author
 */
public class HTMLDialog extends javax.swing.JDialog implements HyperlinkListener {

    protected final String html;
    protected String title = "";
    protected final int w;
    protected final int h;
    protected String imgPath = IconUrls.TIPS_IMAGE_ICON_URL.toString();

    /**
     * Creates new form AboutDialog
     *
     * @param parent
     * @param modal
     */
    public HTMLDialog(java.awt.Frame parent, boolean modal, int w, int h, String title, String html) {
        super(parent, modal);
        initComponents();
        this.w = w;
        this.h = h;
        this.title = title;
        this.html = html;
        initOther();
    }

    @Override
    public synchronized void hyperlinkUpdate(HyperlinkEvent e) {
        //
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            try {
                Desktop.getDesktop().browse(e.getURL().toURI());
            } catch (Exception ex) {
                Logger.getLogger(HTMLDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //
    }

    public void initAdditional() {
        this.setAlwaysOnTop(true);
    }

    public String[] getCssRules() {
        String[] CSSRules = {
            "table {width: auto;}",
            "td {border: 0px solid gray;}"
        };

        return CSSRules;
    }

    private void initOther() {
        //
        jEditorPane_1.setEditable(false);
        jEditorPane_1.setContentType("text/html");
        jEditorPane_1.addHyperlinkListener(this);
        //
        this.setSize(w, h);
        //
        this.setTitle(title);
        //
        GP_BUH.centerAndBringToFront(this);
        //
        initAdditional();
        //
        HTMLEditorKit kit = new HTMLEditorKit();
        StyleSheet styleSheet = kit.getStyleSheet();
        String[] CSSRules = getCssRules();
        //
        for (int i = 0; i < CSSRules.length; i++) {
            styleSheet.addRule(CSSRules[i]);
        }
        jEditorPane_1.setEditorKit(kit);
        //
        Document doc = kit.createDefaultDocument();
        jEditorPane_1.setDocument(doc);
        //
        if (html == null || html.isEmpty()) {
            jEditorPane_1.setText(buildHTML());
        } else {
            jEditorPane_1.setText(html);
        }
        //
        setVisible(true);
        //
        jEditorPane_1.invalidate();
        jEditorPane_1.validate();
        jEditorPane_1.repaint();
        //
    }

    protected String footerHTML() {
        return "<tr></tr><tr><td><a style='text-decoration:none;font-size:12px' href='https://" + GP_BUH.LA_WEB_ADDR + "/?page_id=800'>Läs mer på www.lafakturering.se</a></td></tr>";
    }

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
                + "<td><h1>" + "Hej och välkommen till LAFakturering" + "</h1></td>"
                + "</tr>"
                + "</table>"
                //
                + "<table style='font-size:14px'>"
                + "<tr><td>Det är första gången du kör LAFakturering, trevligt!</td></tr>"
                + "<tr><td>Bara inom några minuter är du redo at skapa din första faktura.</td></tr>"
                + "<tr><td>Följ gärna de enkla stegen nedan för att komma igång så fort som möjligt:</td></tr>"
                + "</table>"
                + "</div>"
                //
                //
                + "<br>"
                //
                + "<div style='margin-left:10px;color:gray;background-color:#EEF0F4;padding:5 5 5 5px;'>" //EEF0F4, EEF0F4
                + "<table style='font-size:14px'>"
                //
                + "<tr>"
                + "<td>1. Gå till flik \"" + LAFakturering.TAB_FTG_SETUP + "\" och fyll i dina företagsuppgifter."
                + " Glöm ej att spara ändringar genom att klicka på <img src='" + path_save_icon + "' alt='save' width='32' height='32' >"
                + "<td>"
                + "</tr>"
                //
                + "<tr>"
                + "<td>2. Gå till flik \"" + LAFakturering.TAB_KUDNER + "\" och skapa åtminstone en kund."
                + " Glöm ej att spara ändringar genom att klicka på <img src='" + path_save_icon + "' alt='save' width='32' height='32' >"
                + "<td>"
                + "</tr>"
                //
                + "<tr>"
                + "<td>"
                + "3. Du kan redan nu börja fakturera genom att klicka på " + "<img src='" + path_new_icon + "' alt='new' width='32' height='32' >" + " under fliken \"" + LAFakturering.TAB_INVOICES_OVERVIEW + "\". "
                + " Vi skulle dock rekommendera att också skapar några artiklar genom att gå till fliken \"" + LAFakturering.TAB_ARTIKLAR + "\"."
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane_1 = new javax.swing.JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jScrollPane2.setViewportView(jEditorPane_1);

        getContentPane().add(jScrollPane2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane jEditorPane_1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

}
