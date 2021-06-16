// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:28
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.b;

import com.qoppa.c.m;
import java.io.IOException;

// Referenced classes of package com.qoppa.b:
//            k, h

public class d extends k
{

    public d(boolean flag)
    {
        _fldfor = flag;
    }

    public boolean _mthnew()
    {
        return _fldfor;
    }

    public String toString()
    {
        return (new Boolean(_fldfor)).toString();
    }

    public Object _mthint()
    {
        return new Boolean(_fldfor);
    }

    public void a(m m1, h h)
        throws IOException
    {
        if(_fldfor)
            m1.a("true");
        else
            m1.a("false");
    }

    private boolean _fldfor;
}