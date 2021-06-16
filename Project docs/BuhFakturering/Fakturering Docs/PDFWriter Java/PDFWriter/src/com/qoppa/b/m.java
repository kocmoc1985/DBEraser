// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:29
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.b;

import com.qoppa.c.h;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

// Referenced classes of package com.qoppa.b:
//            k, p, q, h

public class m extends k
{

    public m()
    {
        _fldchar = new Hashtable();
    }

    public void a(String s, k k1)
    {
        _fldchar.put(s, k1);
    }

    public void _mthif(String s)
    {
        if(_fldchar.containsKey(s))
            _fldchar.remove(s);
    }

    public void a(String s, String s1)
    {
        if(s1 != null)
            _fldchar.put(s, new p(s1.getBytes(), 0, this));
        else
        if(_fldchar.get(s) != null)
            _fldchar.remove(s);
    }

    public k a(String s)
    {
        return (k)_fldchar.get(s);
    }

    protected void a(Hashtable hashtable)
    {
        _fldchar = hashtable;
    }

    public void _mthif(com.qoppa.c.m m1, h h1, com.qoppa.b.h h2)
        throws IOException
    {
        for(Enumeration enumeration = _fldchar.keys(); enumeration.hasMoreElements();)
        {
            String s = (String)enumeration.nextElement();
            k k1 = (k)_fldchar.get(s);
            if(k1 instanceof q)
            {
                k1 = h2.a(k1);
                if(k1.a() != h2._mthdo())
                    k1.a(m1, h1, h2);
            } else
            {
                k1._mthif(m1, h1, h2);
            }
        }

    }

    public void a(com.qoppa.c.m m1, com.qoppa.b.h h1)
        throws IOException
    {
        m1.a("<<\n");
        for(Enumeration enumeration = _fldchar.keys(); enumeration.hasMoreElements(); m1.a("\n"))
        {
            String s = (String)enumeration.nextElement();
            k k1 = (k)_fldchar.get(s);
            m1.a("/" + s + " ");
            k1.a(m1, h1);
        }

        m1.a(">>");
    }

    protected Hashtable _fldchar;
}