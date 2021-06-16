// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:29
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.b;


// Referenced classes of package com.qoppa.b:
//            q, l, k

public class h
{

    public h(int i, l l)
    {
        a = i;
        _fldif = l;
        _flddo = System.currentTimeMillis();
    }

    public int _mthif()
    {
        return a++;
    }

    public long _mthdo()
    {
        return _flddo;
    }

    public k a(k k)
    {
        for(; k != null && (k instanceof q); k = ((q)k).d());
        return k;
    }

    public l a()
    {
        return _fldif;
    }

    private int a;
    private long _flddo;
    private l _fldif;
}