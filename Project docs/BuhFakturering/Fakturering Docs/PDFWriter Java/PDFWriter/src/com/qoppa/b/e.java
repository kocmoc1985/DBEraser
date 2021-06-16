// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:29
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.b;

import com.qoppa.c.b;
import com.qoppa.c.m;
import java.io.IOException;

// Referenced classes of package com.qoppa.b:
//            m, n, h

public class e extends com.qoppa.b.m
{

    public e()
    {
        i = new b(256);
    }

    public void a(m m1, h h)
        throws IOException
    {
        a("Length", ((k) (new n(i.a()))));
        super.a(m1, h);
        m1.a("\nstream\n");
        i.a(m1);
        m1.a("\nendstream");
    }

    public void _mthfor(String s)
    {
        i.a(s.getBytes());
    }

    protected b i;
}