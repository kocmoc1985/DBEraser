// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:27
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.c;

import com.qoppa.a.a.c;

public class h
{

    public h(byte abyte0[], int i, int j)
    {
        _flddo = abyte0;
        a = i;
        _fldif = j;
    }

    public long a(int i)
    {
        if(i + 4 < _fldif)
            return c.a(_flddo, a + i);
        if(i >= _fldif)
            return 0L;
        long l = 0L;
        for(int j = 0; j < _fldif - i; j++)
            l |= ((long)_flddo[a + i + j] & 255L) << (3 - j << 3);

        return l;
    }

    private byte _flddo[];
    private int a;
    private int _fldif;
}