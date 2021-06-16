// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:30
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.c;

import java.util.Hashtable;

public class d
{

    public d()
    {
    }

    public static String a(String s)
    {
        StringBuffer stringbuffer = new StringBuffer(s.length());
        for(int i = 0; i < s.length(); i++)
            stringbuffer.append(a(s.charAt(i)));

        return stringbuffer.toString();
    }

    public static char[] a(char ac[])
    {
        char ac1[] = new char[ac.length];
        for(int i = 0; i < ac.length; i++)
            ac1[i] = a(ac[i]);

        return ac1;
    }

    public static char a(char c)
    {
        Character character = (Character)a.get(new Character(c));
        if(character == null)
            return c;
        else
            return character.charValue();
    }

    private static Hashtable a;

    static 
    {
        a = new Hashtable();
        a.put(new Character('\u2022'), new Character('\177'));
        a.put(new Character('\u20AC'), new Character('\200'));
        a.put(new Character('\u2022'), new Character('\201'));
        a.put(new Character('\u201A'), new Character('\202'));
        a.put(new Character('\u0192'), new Character('\203'));
        a.put(new Character('\u201E'), new Character('\204'));
        a.put(new Character('\u2026'), new Character('\205'));
        a.put(new Character('\u2020'), new Character('\206'));
        a.put(new Character('\u2021'), new Character('\207'));
        a.put(new Character('\u02C6'), new Character('\210'));
        a.put(new Character('\u2030'), new Character('\211'));
        a.put(new Character('\u0160'), new Character('\212'));
        a.put(new Character('\u2039'), new Character('\213'));
        a.put(new Character('\u0152'), new Character('\214'));
        a.put(new Character('\u2022'), new Character('\215'));
        a.put(new Character('\u2022'), new Character('\216'));
        a.put(new Character('\u2022'), new Character('\217'));
        a.put(new Character('\u2022'), new Character('\220'));
        a.put(new Character('\u2018'), new Character('\221'));
        a.put(new Character('\u2019'), new Character('\222'));
        a.put(new Character('\u201C'), new Character('\223'));
        a.put(new Character('\u201D'), new Character('\224'));
        a.put(new Character('\u2022'), new Character('\225'));
        a.put(new Character('\u2013'), new Character('\226'));
        a.put(new Character('\u2014'), new Character('\227'));
        a.put(new Character('\u0303'), new Character('\230'));
        a.put(new Character('\u2122'), new Character('\231'));
        a.put(new Character('\u0161'), new Character('\232'));
        a.put(new Character('\u203A'), new Character('\233'));
        a.put(new Character('\u0153'), new Character('\234'));
        a.put(new Character('\u2022'), new Character('\235'));
        a.put(new Character('\u2022'), new Character('\236'));
        a.put(new Character('\u0178'), new Character('\237'));
    }
}