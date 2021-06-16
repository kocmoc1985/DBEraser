// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:26
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.b;

import com.qoppa.a.a.c;
import java.io.IOException;
import java.io.OutputStream;

public class h
{

    public h()
    {
    }

    public static int a()
    {
        return 19;
    }

    public void a(OutputStream outputstream)
        throws IOException
    {
        c.a(outputstream, _fldfor, 4);
        c.a(outputstream, _fldgoto, 4);
        c.a(outputstream, _fldelse, 4);
        c.a(outputstream, _fldcase, 4);
        byte byte0 = (byte)((_fldif & 1) << 7);
        byte0 |= (a & 1) << 6;
        byte0 |= (_fldnew & 1) << 5;
        byte0 |= (_fldbyte & 3) << 4;
        byte0 |= (_flddo & 1) << 2;
        byte0 |= (_fldtry & 1) << 1;
        byte0 |= _fldchar & 1;
        outputstream.write(byte0);
        if(_fldint > 0)
        {
            int i = 0x8000 | _fldint & 0x7fff;
            c.a(outputstream, i, 2);
        } else
        {
            c.a(outputstream, 0, 2);
        }
    }

    int _fldfor;
    int _fldgoto;
    int _fldelse;
    int _fldcase;
    int _fldif;
    int a;
    int _fldnew;
    int _fldbyte;
    int _flddo;
    int _fldtry;
    int _fldchar;
    int _fldint;
}