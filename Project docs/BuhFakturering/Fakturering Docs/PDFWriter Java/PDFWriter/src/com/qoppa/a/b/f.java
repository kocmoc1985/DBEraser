// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:26
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.b;

import com.qoppa.a.a.c;
import java.io.IOException;
import java.io.OutputStream;

public class f
{

    public f()
    {
    }

    public static int a()
    {
        return 26;
    }

    public void a(OutputStream outputstream)
        throws IOException
    {
        com.qoppa.a.a.c.a(outputstream, b, 4);
        com.qoppa.a.a.c.a(outputstream, _fldlong, 4);
        com.qoppa.a.a.c.a(outputstream, _fldgoto, 4);
        com.qoppa.a.a.c.a(outputstream, _fldelse, 4);
        outputstream.write((byte)(_fldchar & 7));
        byte byte0 = (byte)((_fldtry & 1) << 3);
        byte0 |= (_fldif & 3) << 1;
        byte0 |= _flddo & 1;
        outputstream.write(byte0);
        if(_flddo == 0)
        {
            outputstream.write(_fldcase);
            outputstream.write(d);
            if(_fldif == 0)
            {
                outputstream.write(_fldbyte);
                outputstream.write(c);
                outputstream.write(_fldnew);
                outputstream.write(_fldvoid);
                outputstream.write(_fldint);
                outputstream.write(_fldnull);
            }
        }
    }

    public int b;
    public int _fldlong;
    public int _fldgoto;
    public int _fldelse;
    public byte _fldfor;
    public byte _fldchar;
    public byte a;
    public byte _fldtry;
    public byte _fldif;
    public byte _flddo;
    public byte _fldcase;
    public byte d;
    public byte _fldbyte;
    public byte c;
    public byte _fldnew;
    public byte _fldvoid;
    public byte _fldint;
    public byte _fldnull;
}