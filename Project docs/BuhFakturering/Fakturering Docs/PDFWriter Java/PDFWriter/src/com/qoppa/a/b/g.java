// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:26
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.b;

import com.qoppa.a.a.a;
import com.qoppa.a.a.d;
import java.awt.Image;
import java.io.IOException;
import java.io.OutputStream;

// Referenced classes of package com.qoppa.a.b:
//            c, d, h, e, 
//            a, f

public class g
{

    public g()
    {
    }

    public static void a(OutputStream outputstream, Image image, int i, int j, boolean flag, boolean flag1)
        throws IOException, c
    {
        a a1 = new a(image);
        a(outputstream, a1, i, j, flag, flag1);
    }

    public static void a(OutputStream outputstream, a a1, int i, int j, boolean flag, boolean flag1)
        throws IOException, c
    {
        if(flag1)
        {
            outputstream.write(_fldif);
            outputstream.write(1);
            outputstream.write(new byte[] {
                0, 0, 0, 1
            });
        }
        int k = 0;
        com.qoppa.a.b.d d1 = new com.qoppa.a.b.d();
        h h1 = new h();
        d1._fldif = k++;
        d1._fldint = 48;
        d1._fldtry = 1;
        d1._flddo = com.qoppa.a.b.h.a();
        h1._fldfor = a1._mthnew();
        h1._fldgoto = a1._mthtry();
        h1._fldelse = i;
        h1._fldcase = j;
        h1._fldchar = 1;
        e e1 = new e();
        com.qoppa.a.b.a.a(e1, a1, flag);
        com.qoppa.a.b.a._mthint(e1);
        com.qoppa.a.b.d d2 = new com.qoppa.a.b.d();
        f f1 = new f();
        d2._fldif = k++;
        d2._fldint = 38;
        d2._fldtry = 1;
        d2._flddo = com.qoppa.a.b.f.a() + e1.a();
        f1.b = a1._mthnew();
        f1._fldlong = a1._mthtry();
        if(flag)
            f1._fldtry = 1;
        f1._fldcase = 3;
        f1.d = -1;
        f1._fldbyte = -3;
        f1.c = -1;
        f1._fldnew = 2;
        f1._fldvoid = -2;
        f1._fldint = -2;
        f1._fldnull = -2;
        d1.a(outputstream);
        h1.a(outputstream);
        d2.a(outputstream);
        f1.a(outputstream);
        e1._mthif().a(outputstream);
        if(flag1)
        {
            com.qoppa.a.b.d d3 = new com.qoppa.a.b.d();
            d3._fldint = 49;
            d3._fldtry = 1;
            d3.a(outputstream);
            d3._fldint = 51;
            d3._fldtry = 0;
            d3.a(outputstream);
        }
    }

    private static final byte _fldif[] = {
        -105, 74, 66, 50, 13, 10, 26, 10
    };
    private static final int _fldfor = 38;
    private static final int _flddo = 48;
    private static final int a = 49;
    private static final int _fldint = 51;

}