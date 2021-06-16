// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:31
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.c;


public class t
    implements Cloneable
{

    private t(byte abyte0[])
    {
        _fldif = new byte[256];
        abyte0 = (byte[])abyte0.clone();
        _flddo = 0;
        a = 0;
    }

    public t(byte abyte0[], int i, int j)
    {
        _fldif = new byte[256];
        _flddo = 0;
        a = 0;
        for(int k = 0; k < 256; k++)
            _fldif[k] = (byte)k;

        for(int l = 0; l < 256; l++)
        {
            byte byte0 = _fldif[l];
            a = (short)((short)(abyte0[i + _flddo] + byte0) + a & 0xff);
            _fldif[l] = _fldif[a];
            _fldif[a] = byte0;
            if(j <= ++_flddo)
                _flddo = 0;
        }

        _flddo = 0;
        a = 0;
    }

    public synchronized void a(byte abyte0[], int i, byte abyte1[], int j, int k)
    {
        while(k-- > 0) 
        {
            _flddo = (short)(_flddo + 1 & 0xff);
            byte byte0 = _fldif[_flddo];
            a = (short)(byte0 + a & 0xff);
            byte byte1;
            _fldif[_flddo] = byte1 = _fldif[a];
            _fldif[a] = byte0;
            abyte1[j++] = (byte)(abyte0[i++] ^ _fldif[(short)(byte0 + byte1) & 0xff]);
        }
    }

    public void a(byte abyte0[], byte abyte1[])
    {
        a(abyte0, 0, abyte1, 0, abyte0.length);
    }

    public void a(byte abyte0[], byte abyte1[], int i)
    {
        a(abyte0, 0, abyte1, 0, i);
    }

    public Object clone()
    {
        return new t(_fldif);
    }

    private byte _fldif[];
    private short _flddo;
    private short a;
}