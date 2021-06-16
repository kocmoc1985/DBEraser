// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:28
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.c;

import com.qoppa.a.b.c;
import java.util.Vector;

// Referenced classes of package com.qoppa.a.c:
//            i, h, q, u, 
//            t, d

public class w
{
    public static class a
    {

        int _fldint;
        w _fldif;
        byte _fldfor;
        byte _flddo;
        byte a;

        public a()
        {
        }
    }


    public static w a(d d, q q1, byte abyte0[], int j)
        throws c
    {
        int k = j;
        int l = abyte0[k++] & 0xff;
        boolean flag = (l & 1) != 0;
        int i1 = ((l & 0xe) >> 1) + 1;
        int j1 = ((l & 0x70) >> 4) + 1;
        int k1 = (int)com.qoppa.a.a.c.a(abyte0, k);
        k += 4;
        int l1 = (int)com.qoppa.a.a.c.a(abyte0, k);
        k += 4;
        int i2 = k1;
        Vector vector = new Vector();
        i j2 = new i(new h(abyte0, k, q1._flddo - (k - j)));
        do
        {
            int k2 = j2.a(i1);
            int i3 = j2.a(j1);
            int k3 = i2;
            vector.add(new u(k2, i3, k3));
            i2 += (int)Math.pow(2D, i3);
        } while(i2 < l1);
        int l2 = j2.a(i1);
        vector.add(new u(l2, 32, k1 - 1));
        int j3 = j2.a(i1);
        vector.add(new u(j3, 32, l1));
        if(flag)
        {
            int l3 = j2.a(i1);
            vector.add(new u(l3, 0, 0));
        }
        u au[] = new u[vector.size()];
        for(int i4 = 0; i4 < au.length; i4++)
            au[i4] = (u)vector.get(i4);

        t t1 = new t(flag, au);
        return new w(t1);
    }

    public w(t t1)
        throws c
    {
        char c1 = '\u0100';
        int ai[] = new int[c1];
        int j = -1;
        int l1 = 0;
        int j2 = 0;
        for(int k = 0; k < t1.c.length; k++)
        {
            int i3 = t1.c[k]._flddo;
            if(i3 > j)
            {
                for(int l = j + 1; l < i3 + 1; l++)
                    ai[l] = 0;

                j = i3;
            }
            ai[i3]++;
            int l3 = i3 + t1.c[k]._fldif;
            if(l3 > 16)
                l3 = i3;
            if(l3 <= 16 && l1 < l3)
                l1 = l3;
        }

        int k1 = 1 << l1;
        _fldif = l1;
        _fldfor = new a[k1];
        for(int j3 = 0; j3 < k1; j3++)
            _fldfor[j3] = new a();

        ai[0] = 0;
        for(int i2 = 1; i2 <= j; i2++)
        {
            int k3 = l1 - i2;
            j2 = j2 + ai[i2 - 1] << 1;
            int k2 = j2;
            for(int l2 = 0; l2 < t1.c.length; l2++)
            {
                int i4 = t1.c[l2]._flddo;
                if(i4 == i2)
                {
                    int j4 = t1.c[l2]._fldif;
                    int k4 = k2 << k3;
                    int l4 = k2 + 1 << k3;
                    byte byte0 = 0;
                    if(l4 > k1)
                        throw new c("ran off the end of the entries table! (" + l4 + " >= " + k1 + ")");
                    if(t1._fldbyte && l2 == t1.c.length - 1)
                        byte0 |= 1;
                    if(l2 == t1.c.length - (t1._fldbyte ? 3 : 2))
                        byte0 |= 2;
                    if(i4 + j4 > 16)
                    {
                        for(int i1 = k4; i1 < l4; i1++)
                        {
                            _fldfor[i1]._fldint = t1.c[l2].a;
                            _fldfor[i1]._fldfor = (byte)i4;
                            _fldfor[i1]._flddo = (byte)j4;
                            _fldfor[i1].a = byte0;
                        }

                    } else
                    {
                        for(int j1 = k4; j1 < l4; j1++)
                        {
                            long l5 = j1 >> k3 - j4 & (1 << j4) - 1;
                            if((byte0 & 2) != 0)
                                _fldfor[j1]._fldint = (int)((long)t1.c[l2].a - l5);
                            else
                                _fldfor[j1]._fldint = (int)((long)t1.c[l2].a + l5);
                            _fldfor[j1]._fldfor = (byte)(i4 + j4);
                            _fldfor[j1]._flddo = 0;
                            _fldfor[j1].a = byte0;
                        }

                    }
                    k2++;
                }
            }

        }

    }

    public int a(i j, boolean aflag[])
    {
        int k = j._fldint;
        long l = j._fldif;
        w w1 = this;
        a a1;
        byte byte0;
        long l1;
        do
        {
            int k1 = w1._fldif;
            a1 = w1._fldfor[(int)(l >> 32 - k1)];
            byte0 = a1.a;
            int j2 = a1._fldfor;
            l1 = j._flddo;
            k += j2;
            if(k >= 32)
            {
                l = l1;
                j.a += 4;
                l1 = j._fldfor.a(j.a + 4);
                k -= 32;
                j._flddo = l1;
                j2 = k;
            }
            if(j2 != 0)
                l = l << j2 & 0xffffffffL | l1 >> 32 - k;
            if((byte0 & 4) == 0)
                break;
            w1 = a1._fldif;
        } while(true);
        int j1 = a1._fldint;
        int i1 = a1._flddo;
        if(i1 > 0)
        {
            int i2 = (int)(l >> 32 - i1);
            if((byte0 & 2) != 0)
                j1 -= i2;
            else
                j1 += i2;
            k += i1;
            if(k >= 32)
            {
                l = l1;
                j.a += 4;
                l1 = j._fldfor.a(j.a + 4);
                k -= 32;
                j._flddo = l1;
                i1 = k;
            }
            if(i1 != 0)
                l = l << i1 & 0xffffffffL | l1 >> 32 - k;
        }
        j._fldif = l;
        j._fldint = k;
        if(aflag != null)
            aflag[0] = (byte0 & 1) != 0;
        return j1;
    }

    private static final int _fldnew = 16;
    private static final int a = 1;
    private static final int _flddo = 2;
    private static final int _fldint = 4;
    int _fldif;
    a _fldfor[];
}