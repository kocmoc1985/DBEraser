/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice.sec;

import BuhInvoice.DB;
import static BuhInvoice.GP_BUH._get;
import java.util.HashMap;
import java.util.Objects;

/**
 * [#SAME-ARTICLE-ADDED-TWICE#]
 * @author MCREMOTE
 */
public class JTableRowData {

    //
    private String artikelNamn; // Not used for "equals()" and "hashCode()"
    //
    private final String artikelId;
    private final String artikelKomment;
    private final String artikelAntal;
    private final String artikelEnhet;
    private final String artikelPris;
    private final String artikelRabatt;
    private final String artikelRabattKr;
    private final String artikelMomsSats;
    private final String artikelOmvantSkatt;

    public JTableRowData(HashMap<String, String> map) {
        this.artikelId =_get(map, DB.BUH_F_ARTIKEL__ARTIKELID, true);
        this.artikelKomment = _get(map, DB.BUH_F_ARTIKEL__KOMMENT, true);
        this.artikelAntal = map.get(DB.BUH_F_ARTIKEL__ANTAL);
        this.artikelEnhet = map.get(DB.BUH_F_ARTIKEL__ENHET);
        this.artikelPris = map.get(DB.BUH_F_ARTIKEL__PRIS);
        this.artikelRabatt = map.get(DB.BUH_F_ARTIKEL__RABATT);
        this.artikelRabattKr = map.get(DB.BUH_F_ARTIKEL__RABATT_KR);
        this.artikelMomsSats = map.get(DB.BUH_F_ARTIKEL__MOMS_SATS).replaceAll("%", "");
        this.artikelOmvantSkatt = map.get(DB.BUH_F_ARTIKEL__OMVANT_SKATT);
    }
    
    public void setArtikelNamn(String namn){
        this.artikelNamn = namn;
    }

    public String getArtikelNamn() {
        return artikelNamn;
    }

    public String getArtikelId() {
        return artikelId;
    }

    public String getArtikelKomment() {
        return artikelKomment;
    }

    public String getArtikelAntal() {
        return artikelAntal;
    }

    public String getArtikelEnhet() {
        return artikelEnhet;
    }

    public String getArtikelPris() {
        return artikelPris;
    }

    public String getArtikelRabatt() {
        return artikelRabatt;
    }

    public String getArtikelRabattKr() {
        return artikelRabattKr;
    }

    public String getArtikelMomsSats() {
        return artikelMomsSats;
    }

    public String getArtikelOmvantSkatt() {
        return artikelOmvantSkatt;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.artikelId);
        hash = 17 * hash + Objects.hashCode(this.artikelKomment);
        hash = 17 * hash + Objects.hashCode(this.artikelEnhet);
        hash = 17 * hash + Objects.hashCode(this.artikelPris);
        hash = 17 * hash + Objects.hashCode(this.artikelRabatt);
        hash = 17 * hash + Objects.hashCode(this.artikelRabattKr);
        hash = 17 * hash + Objects.hashCode(this.artikelMomsSats);
        hash = 17 * hash + Objects.hashCode(this.artikelOmvantSkatt);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JTableRowData other = (JTableRowData) obj;
        if (!Objects.equals(this.artikelId, other.artikelId)) {
            return false;
        }
        if (!Objects.equals(this.artikelKomment, other.artikelKomment)) {
            return false;
        }
        if (!Objects.equals(this.artikelEnhet, other.artikelEnhet)) {
            return false;
        }
        if (!Objects.equals(this.artikelPris, other.artikelPris)) {
            return false;
        }
        if (!Objects.equals(this.artikelRabatt, other.artikelRabatt)) {
            return false;
        }
        if (!Objects.equals(this.artikelRabattKr, other.artikelRabattKr)) {
            return false;
        }
        if (!Objects.equals(this.artikelMomsSats, other.artikelMomsSats)) {
            return false;
        }
        if (!Objects.equals(this.artikelOmvantSkatt, other.artikelOmvantSkatt)) {
            return false;
        }
        return true;
    }

}
