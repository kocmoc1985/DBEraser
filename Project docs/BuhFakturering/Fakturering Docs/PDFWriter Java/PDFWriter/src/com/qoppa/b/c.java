// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:28
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.b;

import com.qoppa.c.h;
import com.qoppa.c.m;
import java.io.IOException;
import java.util.Vector;

// Referenced classes of package com.qoppa.b:
//            k, q, h

public class c extends k
{

    public c()
    {
        _flddo = new Vector();
    }

    public void a(k k1)
    {
        _flddo.addElement(k1);
    }

    public void a(k k1, int i)
    {
        _flddo.insertElementAt(k1, i);
    }

    public void _mthif(k k1, int i)
    {
        _flddo.set(i, k1);
    }

    public int _mthfor()
    {
        return _flddo.size();
    }

    public k a(int i)
    {
        return (k)_flddo.elementAt(i);
    }

    public Object _mthdo()
    {
        return _flddo;
    }

    public void a(m m1, com.qoppa.b.h h1)
        throws IOException
    {
        m1.write(91);
        for(int i = 0; i < _flddo.size(); i++)
        {
            k k1 = (k)_flddo.get(i);
            if(i > 0)
                if((i + 1) % 16 == 0)
                    m1.write(10);
                else
                    m1.write(32);
            k1.a(m1, h1);
        }

        m1.write(93);
    }

    public void _mthif(m m1, h h1, com.qoppa.b.h h2)
        throws IOException
    {
        for(int i = 0; i < _flddo.size(); i++)
        {
            k k1 = (k)_flddo.get(i);
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

    private Vector _flddo;
}