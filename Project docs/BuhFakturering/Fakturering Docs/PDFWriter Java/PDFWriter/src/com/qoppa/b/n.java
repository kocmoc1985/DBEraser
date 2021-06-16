// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:29
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.b;

import com.qoppa.c.m;
import java.io.IOException;

// Referenced classes of package com.qoppa.b:
//            k, h

public class n extends k
{

    public n(int i)
    {
        as = i;
    }

    public int c()
    {
        return as;
    }

    public Object b()
    {
        return new Integer(as);
    }

    public void a(m m1, h h)
        throws IOException
    {
        m1.a(Integer.toString(as));
    }

    public String toString()
    {
        return Integer.toString(as);
    }

    private int as;
}