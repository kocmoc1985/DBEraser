/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import BuhInvoice.BUH_INVOICE_MAIN;
import BuhInvoice.GP_BUH;
import forall.HelpA;
import static forall.HelpA.objectToFile;
import java.util.HashSet;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 *
 * @author KOCMOC
 */
public class GDPR extends HTMLBasic {

    private final BUH_INVOICE_MAIN bim;

    /**
     * Creates new form GDPR
     */
    public GDPR(BUH_INVOICE_MAIN bim) {
        this.bim = bim;
        initComponents();
        init();
    }

    private void init() {
        go();
        this.setTitle("Personuppgiftshantering - GDPR");
        this.setIconImage(GP_BUH.getBuhInvoicePrimIcon());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setAlwaysOnTop(true);
        this.jButton_forsatt.setEnabled(false);
    }

    @Override
    public void go() {
        //
        super.go(); //To change body of generated methods, choose Tools | Templates.
        //
        jEditorPane1.setCaretPosition(0);
        //
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton_forsatt = new javax.swing.JButton();
        jButton_avbryt = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(jEditorPane1);

        jCheckBox1.setText("Jag samtycker");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jButton_forsatt.setText("Fortsätt");
        jButton_forsatt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_forsattActionPerformed(evt);
            }
        });

        jButton_avbryt.setText("Avbryt");
        jButton_avbryt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_avbrytActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addGap(78, 78, 78)
                        .addComponent(jButton_forsatt, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_avbryt, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 200, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(jButton_forsatt)
                    .addComponent(jButton_avbryt))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if (jCheckBox1.isSelected()) {
            this.jButton_forsatt.setEnabled(true);
        } else {
            this.jButton_forsatt.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton_forsattActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_forsattActionPerformed
        continueBtnClicked_accepted();
    }//GEN-LAST:event_jButton_forsattActionPerformed

    private void continueBtnClicked_accepted() {
        this.dispose();
        bim.setEnabled(true);
        //
        // creating files as a "flag"
        HashSet<String> set = new HashSet<String>();
        set.add(GP_BUH.updatedOn());
        objectToFile(GP_BUH.GDPR_ACCEPTED_FILE_PATH, set);
        //
    }

    private void jButton_avbrytActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_avbrytActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton_avbrytActionPerformed

    @Override
    public String[] getCssRules() {
        //
        String[] CSSRules = {
            //
            //            "body {font-size:12pt}",
            //            "table {margin-bottom:10px;}",
            "table {width: 99%;}",
            //            "img {width: 20px}", not working from here
            ".fontStd {font-size:9pt; color:gray;}",
            "table {font-size:9pt; color:gray;}", // 9pt seems to be optimal
            //            "table {border: 1px solid black}",//----------------------------->!!!!!!
            "td {border: 1px solid gray;}",//------------------------------------>!!!!!
            "td {padding-left: 4px;}",
            //
            ".marginTop {margin-top: 5px;}",
            ".marginLeft {margin-left: 10px;}",
            ".paddingLeft {padding-left: 5px;}",
            ".bold {font-weight:800;}", // font-weight:800;
            ".no-border {border: 0px}", // search for: [#no-border#]
            ".border-a {border: 1px solid gray;}"
        //    
        };
        //
        return CSSRules;
        //
    }

    @Override
    public String buildHTML() {
        // 
        String img_a = getPathNormal(GP_BUH.LOGO_PATH());
        //
        return "<html>"
                + "<body>" //style='background-color:#F1F3F6'
                + "<div style='margin-left:10px;padding:5 5 5 5px;font-size:12px;'>" // ;background-color:#EEF0F4
                + integritetsPolicy()
                + "</div>"
                //
                + "<br>"
                //
                + "<div style='margin-left:10px;padding:5 5 5 5px;font-size:9px;'>"
                + personUppgiftsAnsvarig()
                + "</div>"
                //
                + "<br>"
                + "</body>"
                + "</html>";
        //
    }

    private String integritetsPolicy() {
        return ""
                + "<h2>Integritetspolicy</h2>"
                + "<p>"
                + "Vi arbetar för att säkerställa en hög nivå av dataskydd som värnar om din personliga integritet vid användning av vår webbplats. Denna integritetspolicy förklarar därför hur dina personuppgifter bearbetas och skyddas, dina rättigheter och hur du kan göra dem gällande. Vi strävar efter att du ska känna dig trygg i vår behandling av dina personuppgifter. Vid eventuella frågor är du välkommen att kontakta oss. Våra kontaktuppgifter hittar du längst ned på denna sida."
                + "</p>"
                //
                + "<h2>Var lagras dina personuppgifter?</h2>"
                + "<p>"
                + "Vi eftersträvar att dina personuppgifter i så hög grad som möjligt ska behandlas inom det europeiska ekonomiska samarbetsområdet (EES) och alla våra egna IT-system, men kan även överföras och bearbetas i ett land utanför EES (s.k. ”tredje land”). Sådan överföring sker endast till mottagarland med adekvat skyddsnivå och i enlighet med gällande lagstiftning. Om överföring sker till partner för en tjänst i USA sker detta endast till företag som omfattas av det så kallade ”Privacy Shield”, vilken är en överenskommelse om skydd för personuppgifter mellan EU och USA."
                + "</p>"
                //
                + "<h2>Hur använder vi dina personuppgifter?</h2>"
                + "<ul>"
                + "<li>Vid fakturautskick via e-post"
                + "<li>Vid kontakter angående eventuella drift problem"
                + "<li>Vid korrespondens om frågor och övrig information"
                + "<li>Vid utskick om erbjudanden i form av nyhetsbrev, då alltid med instruktioner om hur du kan avanmäla dig ifrån vidare utskick"
                + "<li>Vid analyser av köpmönster för att ge relevanta erbjudanden och information"
                + "<li>Vid eventuella enkätutskick för att förbättra våra tjänster och erbjudanden"
                + "<li>Vid arbete som förhindrar missbruk eller annan olämplig användning av vår hemsida"
                + "</ul"
                //
                + "<p>"
                + "Vi sparar dina uppgifter så länge som det krävs för att uppfylla ovanstående ändamål eller så länge som vi enligt lag är skyldiga att göra detta. Därefter raderas dina personuppgifter"
                + "</p>"
                //
                + "<h2>Vilka är dina rättigheter?</h2>"
                + "<p>"
                + "Du har rätt att när som helst radera all data som är kopplat till ditt konto. Du har rätt att när som helst begära information om de personuppgifter vi har om dig. Om dina uppgifter är felaktiga, ofullständiga eller irrelevanta, kan du begära att få dem rättade eller raderade. Vi kan inte radera dina uppgifter då det föreligger ett lagstadgat krav på lagring, som exempelvis bokföringsregler, eller när det finns andra legitima skäl till varför uppgifterna måste sparas, till exempel obetalda fakturor. Du kan när som helst återkalla ditt samtycke till att låta oss använda uppgifterna för marknadsföring genom att avanmäla dig från vårt nyhetsbrev. Du kan också kontakta oss genom att skicka brev, e-post eller ringa. Se kontaktuppgifter nedan."
                + "</p>"
                //
                + "<h2>Vilka kan vi komma att dela dina personuppgifter med?</h2>"
                + "<p>"
                + "För att vi ska kunna erbjuda ovannämnda tjänster och våra åtaganden gentemot dig som kund delar vi dina personuppgifter med företag som är s.k. personuppgiftsbiträden för oss. Personuppgiftsbiträden behandlar informationen för vår räkning och enligt våra instruktioner och hjälper oss med IT-tjänster, betaltjänster och marknadsföring. Detta sker endast för ändamål för vilka vi har samlat informationen och endast till företag som kan lämna tillräckliga garantier gällande säkerhet och sekretess för personuppgifter. Vi vidarebefordrar, säljer eller byter aldrig dina personuppgifter för marknadsföringsändamål till tredje part."
                + "</p>"
                //
                + "<h2>Hur skyddar vi dina personuppgifter?</h2>"
                + "<p>"
                + "För att dina personuppgifter som skickas till vår webbtjänst inte ska läsas av utomstående, krypteras all sådan information och skickas via en säker anslutning."
                + "</p>";
        //
    }

    private String personUppgiftsAnsvarig() {
        return ""
                + "<h2>Personuppgiftsansvarig</h2>"
                + "<p>"
                + "MixCont AB <br>"
//                + "Organisationsnummer: 556251-6806 <br>"
                + "Gatuadress: Sanktagertudsväg 10 <br>"
                + "Ort: Trelleborg <br>"
                + "Land: Sverige <br>"
                + "E-Post: info@lafakturering.se <br><br>"
                + "Denna integritetspolicy uppdateras senast: 2020-12-26"
                + "</p>";
    }

    @Override
    public JEditorPane getEditorPane() {
        return this.jEditorPane1;
    }

    @Override
    public JScrollPane getJScrollPane() {
        return this.jScrollPane1;
    }

    @Override
    public String getWindowTitle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_avbryt;
    private javax.swing.JButton jButton_forsatt;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
