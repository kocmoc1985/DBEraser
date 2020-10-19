/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

/**
 *
 * @author MCREMOTE
 */
public class HeadersValuesHTMLPrint {
    
   private final String[]headers;
   private final String[]values;
   private final int colToMakeBold;

    public HeadersValuesHTMLPrint(String[] headers, String[] values,int colToMakeBold) {
        this.headers = headers;
        this.values = values;
        this.colToMakeBold = colToMakeBold;
    }

    public String[] getHeaders() {
        return headers;
    }

    public String[] getValues() {
        return values;
    }

    public int getColToMakeBold() {
        return colToMakeBold;
    }
       
    
}
