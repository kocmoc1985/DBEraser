/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuhInvoice;

import java.util.Objects;

/**
 * This class 
 * @author KOCMOC
 */
public class HashMapKeyCaseInsensitive {

    private final String keyValue;

    public HashMapKeyCaseInsensitive(String value) {
        this.keyValue = value;
    }

    public String getKeyValue() {
        return keyValue;
    }
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.keyValue.toLowerCase()); // ".toLowerCase()"**************
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
        final HashMapKeyCaseInsensitive other = (HashMapKeyCaseInsensitive) obj;
        if (!Objects.equals(this.keyValue.toLowerCase(), other.keyValue.toLowerCase())) { // ".toLowerCase()"**************
            return false;
        }
        return true;
    }

}
