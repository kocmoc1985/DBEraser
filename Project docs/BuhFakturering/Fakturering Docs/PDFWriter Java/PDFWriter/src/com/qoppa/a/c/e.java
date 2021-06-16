// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:26
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.c;

import com.qoppa.a.a.c;

// Referenced classes of package com.qoppa.a.c:
//            o

public class e
{

    public e(int i)
    {
        int j = 1 << i;
        a = i;
        _fldif = new byte[j];
    }

    public int a(o o1, int ai[])
    {
        int i = 1;
        for(int k = 0; k < a; k++)
        {
            int j = c.a(o1.a(_fldif, i));
            i = i << 1 | j;
        }

        i -= 1 << a;
        ai[0] = i;
        return 0;
    }

    int a;
    byte _fldif[];
}