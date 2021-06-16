// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:29
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.b;

import com.qoppa.c.m;
import java.io.IOException;

// Referenced classes of package com.qoppa.b:
//            k, h

public class g extends k
{

    public g(int i, int j, int l, int i1)
    {
        _fldcase = i;
        _fldtry = j;
        _fldbyte = l;
        _fldnew = i1;
    }

    public void a(m m1, h h)
        throws IOException
    {
        m1.a("[" + _fldcase + " " + _fldtry + " " + ((_fldcase + _fldbyte) - 1) + " " + ((_fldtry + _fldnew) - 1) + "]");
    }

    private int _fldcase;
    private int _fldtry;
    private int _fldbyte;
    private int _fldnew;
}