// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:29
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.b;

import com.qoppa.c.m;
import java.io.IOException;

// Referenced classes of package com.qoppa.b:
//            k, h, l

public class p extends k
{

    public p(byte abyte0[], int i, k k1)
    {
        ax = abyte0;
        au = i;
        at = k1;
    }

    public void a(m m1, h h1)
        throws IOException
    {
        byte abyte0[] = ax;
        if(h1.a() != null)
            if(_mthif() != null)
                abyte0 = h1.a().a(_mthif().intValue(), ax);
            else
            if(at != null)
                abyte0 = h1.a().a(at._mthif().intValue(), ax);
        a(m1, abyte0);
    }

    protected void a(m m1, byte abyte0[])
        throws IOException
    {
        if(au == 0)
        {
            m1.write(40);
            for(int i = 0; i < abyte0.length; i++)
            {
                byte byte0 = abyte0[i];
                if(byte0 == 40 || byte0 == 41 || byte0 == 92)
                {
                    m1.write(92);
                    m1.write(byte0);
                } else
                if(byte0 == 10)
                    m1.a("\\n");
                else
                if(byte0 == 13)
                    m1.a("\\r");
                else
                if(byte0 == 9)
                    m1.a("\\t");
                else
                if(byte0 == 8)
                    m1.a("\\b");
                else
                if(byte0 == 12)
                    m1.a("\\f");
                else
                    m1.write(byte0);
            }

            m1.write(41);
        } else
        {
            m1.write(60);
            for(int j = 0; j < abyte0.length; j++)
            {
                int i1 = 0xff & abyte0[j];
                if(i1 < 16)
                {
                    m1.write(48);
                    m1.a(Integer.toHexString(i1));
                } else
                {
                    m1.a(Integer.toHexString(i1));
                }
            }

            m1.write(62);
        }
    }

    public static final int aw = 0;
    public static final int av = 1;
    private int au;
    protected byte ax[];
    private k at;
}