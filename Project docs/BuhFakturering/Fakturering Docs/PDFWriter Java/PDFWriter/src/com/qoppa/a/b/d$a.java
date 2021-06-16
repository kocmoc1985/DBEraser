// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:26
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.b;

import com.qoppa.a.a.c;
import java.io.IOException;
import java.io.OutputStream;

// Referenced classes of package com.qoppa.a.b:
//            d

private static class d$a
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

    private d$a()
    {
    }

    d$a(d$a d$a1)
    {
        this();
    }
}