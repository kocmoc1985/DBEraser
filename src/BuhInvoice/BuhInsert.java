/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

/**
 *
 * @author MCREMOTE
 */
public class BuhInsert {

    /**
     * In the future i shall use the encryptedKundId to prevent that another
     * user can make some modifications to the program like it's done in
     * "RDPComm" project
     *
     * This is ONLY a "Sketch" so far [2020-07-13]
     *
     * @return
     */
    public static String buh_f_artikel__insert(int fakturaId, int artikelId, String komment, int antal, double pris, String enhet, double rabatt) {
        return String.format("http://www.mixcont.com/index.php?link=_http_buh&func=f_artikel_insert&fakturaId=%s&artikelId=%s&komment=%s&antal=%s&enhet=%s&rabatt=%s",
                fakturaId, artikelId, komment, antal, pris, enhet, rabatt);
    }

    public static void main(String[] args) {
        System.out.println("" + buh_f_artikel__insert(1, 2, "Spik", 15, 10, "St", 0));
        System.out.println("" + test(901, "httpcom", "278912"));
    }
    
    
    
     public static String test(int clientId, String tableName, String npmsVersion) {
        return String.format("http://www.mixcont.com/index.php?link=_http_com&setparam=versionnpms&client=%s&tablename=%s&paramvalue=%s",
               clientId, tableName, npmsVersion);
    }

}
