// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:29
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.b;

import com.qoppa.c.h;
import com.qoppa.c.m;
import java.io.IOException;

// Referenced classes of package com.qoppa.b:
//            h

public abstract class k
{

    public k()
    {
        a = null;
        _fldif = -1L;
    }

    public Integer _mthif()
    {
        return a;
    }

    public long a()
    {
        return _fldif;
    }

    public final void a(m m1, h h1, com.qoppa.b.h h2)
        throws IOException
    {
        a = new Integer(h2._mthif());
        _fldif = h2._mthdo();
        _mthif(m1, h1, h2);
        h1.a(a.intValue(), m1.a());
        m1.a(a + " 0 obj\n");
        a(m1, h2);
        m1.a("\nendobj\n");
    }

    public abstract void a(m m1, com.qoppa.b.h h1)
        throws IOException;

    public void _mthif(m m1, h h1, com.qoppa.b.h h2)
        throws IOException
    {
    }

    private Integer a;
    private long _fldif;
}