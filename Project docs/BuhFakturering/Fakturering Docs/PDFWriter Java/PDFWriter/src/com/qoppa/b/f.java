// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:29
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.b;

import com.qoppa.c.m;
import java.io.IOException;

// Referenced classes of package com.qoppa.b:
//            k, h

public class f extends k
{

    public f(String s)
    {
        _fldint = s;
    }

    public String _mthbyte()
    {
        return _fldint;
    }

    public boolean equals(Object obj)
    {
        if(obj == null || !(obj instanceof f))
            return false;
        else
            return _fldint.equals(((f)obj)._fldint);
    }

    public int hashCode()
    {
        return _fldint.hashCode();
    }

    public String toString()
    {
        return _fldint;
    }

    public Object _mthtry()
    {
        return _fldint;
    }

    public void a(m m1, h h)
        throws IOException
    {
        if(_fldint.indexOf(' ') == -1)
        {
            m1.a("/" + _fldint);
        } else
        {
            m1.write(47);
            for(int i = 0; i < _fldint.length(); i++)
                if(_fldint.charAt(i) != ' ')
                    m1.write(_fldint.charAt(i));
                else
                    m1.a("#20");

        }
    }

    private String _fldint;
}