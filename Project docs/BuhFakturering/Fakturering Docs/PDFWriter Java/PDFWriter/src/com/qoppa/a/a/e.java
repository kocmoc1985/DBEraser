// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:25
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.a;


public class e
{

    public e(byte abyte0[], int i)
    {
        _fldfor = abyte0;
        _flddo = i / 8;
        a = 8 - i % 8;
    }

    public void _mthif(int i)
    {
        _flddo = i / 8;
        a = 8 - i % 8;
    }

    public byte a()
    {
        if(a != 8)
        {
            int i = a;
            int j = 8 - i;
            byte byte0 = (byte)((_fldfor[_flddo] & _fldif[i]) << j);
            byte0 |= (byte)(_fldfor[_flddo + 1] >>> i & _fldif[j]);
            _flddo++;
            return byte0;
        } else
        {
            _flddo++;
            return _fldfor[_flddo - 1];
        }
    }

    public byte a(int i)
    {
        if(a >= i)
        {
            byte byte0 = (byte)(_fldfor[_flddo] >>> a - i & _fldif[i]);
            a -= i;
            return byte0;
        } else
        {
            byte byte1 = (byte)(_fldfor[_flddo] & _fldif[a]);
            _flddo++;
            i -= a;
            a = 8;
            byte1 <<= i;
            byte1 |= (byte)(_fldfor[_flddo] >>> 8 - i & _fldif[i]);
            a -= i;
            return byte1;
        }
    }

    private byte _fldfor[];
    private int _flddo;
    private int a;
    private static final byte _fldif[] = {
        0, 1, 3, 7, 15, 31, 63, 127, -1
    };

}