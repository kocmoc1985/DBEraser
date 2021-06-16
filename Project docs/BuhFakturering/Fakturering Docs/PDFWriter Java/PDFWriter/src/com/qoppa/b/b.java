// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:28
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.b;

import com.qoppa.c.m;
import java.awt.Color;
import java.awt.Paint;
import java.awt.geom.Area;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;

// Referenced classes of package com.qoppa.b:
//            m, h, l, n, 
//            f

public class b extends com.qoppa.b.m
{

    public b()
    {
        f = new com.qoppa.c.b(512);
        g = _fldnull;
        f.a("1.0 1.0 1.0 rg\n".getBytes());
        h = d;
        f.a("0.0 0.0 0.0 RG\n".getBytes());
        f.a("q\n".getBytes());
    }

    public void _mthcase()
    {
        _mthdo("Q q\n");
        h = d;
        g = _fldnull;
    }

    public void a(m m1, h h1)
        throws IOException
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        DeflaterOutputStream deflateroutputstream = new DeflaterOutputStream(bytearrayoutputstream);
        f.a(deflateroutputstream);
        deflateroutputstream.write(b);
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

    public void _mthdo(String s)
    {
        f.a(s.getBytes());
    }

    public void a(byte abyte0[])
    {
        f.a(abyte0);
    }

    private static final String _fldlong = "Length";
    private static final String _fldgoto = "Filter";
    private static final String _fldvoid = "FlateDecode";
    private static final byte b[] = "\nQ\n".getBytes();
    public static final int _fldelse = 0;
    public static final int c = 1;
    public Paint h;
    public Paint g;
    public Area e;
    private com.qoppa.c.b f;
    private static final Color d;
    private static final Color _fldnull;

    static 
    {
        d = Color.black;
        _fldnull = Color.white;
    }
}