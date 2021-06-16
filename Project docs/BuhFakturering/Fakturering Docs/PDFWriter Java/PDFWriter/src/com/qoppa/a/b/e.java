// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:26
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.b;

import com.qoppa.a.a.d;

public class e
{

    public e()
    {
        _fldif = new byte[0x10000];
        _fldfor = new byte[13][512];
        _fldchar = '\u8000';
        _fldbyte = 0L;
        _fldnew = 12;
        a = -1;
        _fldcase = 0;
        _flddo = new d();
        _fldint = null;
    }

    public void _mthdo()
    {
        _flddo.a(_fldcase);
    }

    public int a()
    {
        return _flddo.a();
    }

    public d _mthif()
    {
        return _flddo;
    }

    private static final int _fldtry = 0x10000;
    long _fldbyte;
    char _fldchar;
    byte _fldnew;
    byte _fldcase;
    int a;
    byte _fldif[];
    byte _fldfor[][];
    byte _fldint[];
    private d _flddo;
}