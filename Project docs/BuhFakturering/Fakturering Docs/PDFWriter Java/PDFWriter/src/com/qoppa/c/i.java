// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:30
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.c;


public class i
    implements Comparable
{

    public i(int j, long l)
    {
        _fldif = j;
        a = l;
    }

    public int compareTo(Object obj)
    {
        if(obj instanceof i)
        {
            i j = (i)obj;
            if(_mthif() < j._mthif())
                return -1;
            return _mthif() <= j._mthif() ? 0 : 1;
        } else
        {
            throw new ClassCastException();
        }
    }

    public long a()
    {
        return a;
    }

    public int _mthif()
    {
        return _fldif;
    }

    private int _fldif;
    private long a;
}