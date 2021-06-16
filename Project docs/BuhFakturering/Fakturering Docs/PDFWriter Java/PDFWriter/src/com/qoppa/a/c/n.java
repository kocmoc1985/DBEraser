// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:27
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.c;

import com.qoppa.a.a.c;

public class n
{

    public n(byte abyte0[], int i)
    {
        _fldfor = (int)c.a(abyte0, i);
        _fldif = (int)c.a(abyte0, i + 4);
        _flddo = (int)c.a(abyte0, i + 8);
        a = (int)c.a(abyte0, i + 12);
        _fldint = abyte0[i + 16] & 7;
    }

    int _fldfor;
    int _fldif;
    int _flddo;
    int a;
    int _fldint;
}