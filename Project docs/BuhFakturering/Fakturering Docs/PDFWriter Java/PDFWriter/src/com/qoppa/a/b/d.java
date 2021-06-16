// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:26
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.b;

import com.qoppa.a.a.c;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

public class d
{
    private static class a
    {

        public void a(OutputStream outputstream)
            throws IOException
        {
            c.a(outputstream, _fldif, 4);
            byte byte0 = (byte)(_fldfor << 7);
            byte0 |= a << 6 & 0x40;
            byte0 |= _flddo & 0x3f;
            outputstream.write(byte0);
            byte0 = (byte)(_fldnew << 5 & 0xe0);
            byte0 |= _fldint & 0x1f;
            outputstream.write(byte0);
        }

        int _fldif;
        int _fldfor;
        int a;
        int _flddo;
        int _fldnew;
        int _fldint;

        private a()
        {
        }

        a(a a1)
        {
            this();
        }
    }


    public d()
    {
        a = new Vector();
    }

    public int a()
    {
        if(_fldif <= 256)
            return 1;
        return _fldif > 0x10000 ? 4 : 2;
    }

    public void a(OutputStream outputstream)
        throws IOException
    {
        a a1 = new a(null);
        a1._fldif = _fldif;
        a1._flddo = _fldint;
        a1._fldfor = _fldfor;
        a1._fldint = _fldnew;
        a1._fldnew = a.size();
        byte byte0 = ((byte)(_fldtry > 255 ? 2 : 1));
        int i = a();
        if(byte0 == 2)
            a1.a = 1;
        a1.a(outputstream);
        for(int j = 0; j < a.size(); j++)
        {
            int k = ((Integer)a.get(j)).intValue();
            c.a(outputstream, k, i);
        }

        c.a(outputstream, _fldtry, byte0);
        c.a(outputstream, _flddo, 4);
    }

    int _fldif;
    int _fldint;
    int _fldfor;
    int _fldnew;
    Vector a;
    int _fldtry;
    int _flddo;
}