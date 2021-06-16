// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:30
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.c;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Vector;

// Referenced classes of package com.qoppa.c:
//            i, m

public class h
{

    public h()
    {
        a = new Vector();
    }

    public void a(int j, long l)
    {
        a.addElement(new i(j, l));
    }

    public int a()
    {
        return a.size();
    }

    public long a(m m1)
        throws IOException
    {
        long l = m1.a();
        Collections.sort(a);
        m1.a("xref\n0 " + (a.size() + 1) + "\n");
        m1.a("0000000000 65535 f \n");
        DecimalFormat decimalformat = new DecimalFormat("0000000000");
        for(int j = 0; j < a.size(); j++)
        {
            i k = (i)a.elementAt(j);
            m1.a(decimalformat.format(k.a()) + " 00000 n \n");
        }

        m1.a(" \n");
        return l;
    }

    private Vector a;
}