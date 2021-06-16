// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:26
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.c;

import java.util.Vector;

// Referenced classes of package com.qoppa.a.c:
//            q, l

public class d
{

    public d(d d1)
    {
        a = d1;
        _fldcase = 1;
        _flddo = null;
        _fldif = new Vector();
        _fldint = 0;
        _fldnew = new Vector();
    }

    public void a(q q1)
    {
        _fldif.add(q1);
    }

    public q _mthif()
    {
        if(_fldif.size() > 0)
            return (q)_fldif.get(_fldif.size() - 1);
        else
            return null;
    }

    public q a(int i)
    {
        for(int j = _fldif.size() - 1; j >= 0; j--)
        {
            q q1 = (q)_fldif.get(j);
            if(q1._fldnew == i)
                return q1;
        }

        if(a != null)
            return a.a(i);
        else
            return null;
    }

    public int _mthfor()
    {
        return _fldif.size();
    }

    public q _mthif(int i)
    {
        return (q)_fldif.get(i);
    }

    public l _mthdo()
    {
        return (l)_fldnew.get(_fldnew.size() - 1);
    }

    public Vector a()
    {
        return _fldnew;
    }

    public void a(l l1)
    {
        _fldnew.add(l1);
    }

    d a;
    byte _flddo[];
    int b;
    int _fldlong;
    int _fldgoto;
    public static final int c = 0;
    public static final int _fldfor = 1;
    public static final int _fldelse = 2;
    public static final int _fldbyte = 3;
    public static final int _fldtry = 4;
    public static final int _fldchar = -1;
    int _fldcase;
    byte _fldnull;
    Vector _fldif;
    int _fldint;
    int _fldvoid;
    private Vector _fldnew;
}