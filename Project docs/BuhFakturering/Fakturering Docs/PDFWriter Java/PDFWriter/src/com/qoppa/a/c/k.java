// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:27
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.c;

import com.qoppa.a.a.c;

// Referenced classes of package com.qoppa.a.c:
//            o

public class k
{

    public k()
    {
        a = new byte[512];
    }

    public boolean a(o o1, int ai[])
    {
        int i = 1;
        boolean flag6 = o1.a(a, i);
        i = i << 1 | c.a(flag6);
        boolean flag = o1.a(a, i);
        i = i << 1 | c.a(flag);
        byte byte0;
        char c1;
        if(flag)
        {
            boolean flag1 = o1.a(a, i);
            i = i << 1 | c.a(flag1);
            if(flag1)
            {
                boolean flag2 = o1.a(a, i);
                i = i << 1 | c.a(flag2);
                if(flag2)
                {
                    boolean flag3 = o1.a(a, i);
                    i = i << 1 | c.a(flag3);
                    if(flag3)
                    {
                        boolean flag4 = o1.a(a, i);
                        i = i << 1 | c.a(flag4);
                        if(flag4)
                        {
                            byte0 = 32;
                            c1 = '\u1154';
                        } else
                        {
                            byte0 = 12;
                            c1 = '\u0154';
                        }
                    } else
                    {
                        byte0 = 8;
                        c1 = 'T';
                    }
                } else
                {
                    byte0 = 6;
                    c1 = '\024';
                }
            } else
            {
                byte0 = 4;
                c1 = '\004';
            }
        } else
        {
            byte0 = 2;
            c1 = '\0';
        }
        int j = 0;
        for(int l = 0; l < byte0; l++)
        {
            boolean flag5 = o1.a(a, i);
            i = i << 1 & 0x1ff | i & 0x100 | c.a(flag5);
            j = j << 1 | c.a(flag5);
        }

        j += c1;
        j = flag6 ? -j : j;
        ai[0] = j;
        return flag6 && j == 0;
    }

    public byte a[];
}