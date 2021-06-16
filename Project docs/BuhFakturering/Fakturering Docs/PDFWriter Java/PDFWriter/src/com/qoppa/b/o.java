// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:29
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.b;

import com.qoppa.c.m;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;

// Referenced classes of package com.qoppa.b:
//            m, h, l, n, 
//            f

public class o extends com.qoppa.b.m
{

    public o(byte abyte0[])
    {
        W = abyte0;
    }

    public void a(m m1, h h1)
        throws IOException
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        DeflaterOutputStream deflateroutputstream = new DeflaterOutputStream(bytearrayoutputstream);
        deflateroutputstream.write(W);
        deflateroutputstream.flush();
        deflateroutputstream.close();
        byte abyte0[] = bytearrayoutputstream.toByteArray();
        if(h1.a() != null)
            abyte0 = h1.a().a(_mthif().intValue(), abyte0);
        a("Length", ((k) (new n(abyte0.length))));
        a("Filter", ((k) (new f("FlateDecode"))));
        super.a(m1, h1);
        m1.a("\nstream\n");
        m1.write(abyte0);
        m1.a("\nendstream");
    }

    private byte W[];
    private static final String V = "Length";
    private static final String Y = "Filter";
    private static final String X = "FlateDecode";
}