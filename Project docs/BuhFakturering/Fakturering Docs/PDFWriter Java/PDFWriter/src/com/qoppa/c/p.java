// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:30
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.c;

import com.qoppa.b.*;

public class p extends m
{

    public p(String s)
    {
        Z = s;
        a("Type", new f("ExtGState"));
    }

    public double _mthgoto()
    {
        a a1 = (a)a("ca");
        if(a1 == null)
            return 1.0D;
        else
            return a1.f();
    }

    public double _mthelse()
    {
        a a1 = (a)a("CA");
        if(a1 == null)
            return 1.0D;
        else
            return a1.f();
    }

    public void _mthif(double d)
    {
        a("ca", new a(d));
    }

    public void a(double d)
    {
        a("CA", ((com.qoppa.b.k) (new a(d))));
    }

    public String _mthchar()
    {
        return Z;
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof p))
            return false;
        p p1 = (p)obj;
        return p1._mthgoto() == _mthgoto() && p1._mthelse() == _mthelse();
    }

    private static final String ab = "CA";
    private static final String aa = "ca";
    private String Z;
}