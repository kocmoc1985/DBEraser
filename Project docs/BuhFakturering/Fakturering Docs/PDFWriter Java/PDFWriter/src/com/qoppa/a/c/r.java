// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:28
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.c;

import com.qoppa.a.a.a;
import com.qoppa.a.b.c;

// Referenced classes of package com.qoppa.a.c:
//            c, q, n, d, 
//            l, h, o

public class r
{

    public r()
    {
    }

    public static int a(d d1, q q1, byte abyte0[], int i)
        throws c
    {
        com.qoppa.a.c.c c1 = new com.qoppa.a.c.c();
        if(q1._flddo < 18)
            throw new c("Segment too short");
        n n1 = new n(abyte0, i);
        byte byte0 = abyte0[i + 17];
        c1._fldint = (byte0 & 1) != 0;
        c1._fldif = (byte0 & 2) != 0;
        int j = i + 18;
        if(!c1._fldint)
        {
            if(q1._flddo < 22)
                throw new c("Segment too short");
            c1._fldnew[0] = abyte0[j + 0];
            c1._fldnew[1] = abyte0[j + 1];
            c1._fldnew[2] = abyte0[j + 2];
            c1._fldnew[3] = abyte0[j + 3];
            j += 4;
        }
        if(q1._fldif != 0)
        {
            q q2 = a(d1, q1);
            if(q2 == null)
                throw new c("could not find reference bitmap!");
            c1.a = (a)q2._fldchar;
            q2._fldchar = null;
        } else
        {
            c1.a = d1._mthdo()._fldnew;
        }
        c1._fldfor = 0;
        c1._flddo = 0;
        a a1 = new a(n1._fldfor, n1._fldif);
        char c2 = c1._fldint ? '\u0400' : '\u2000';
        byte abyte1[] = new byte[c2];
        h h1 = new h(abyte0, j, q1._flddo - (j - i));
        o o1 = new o(h1);
        _mthif(d1, q1, c1, o1, a1, abyte1);
        if((q1._fldfor & 0x3f) == 40)
            q1._fldchar = a1;
        else
            d1._mthdo().a(a1, n1._flddo, n1.a, n1._fldint);
        return 0;
    }

    public static int _mthif(d d1, q q1, com.qoppa.a.c.c c1, o o1, a a1, byte abyte0[])
    {
        if(c1._fldint)
            return _mthdo(d1, q1, c1, o1, a1, abyte0);
        else
            return a(d1, q1, c1, o1, a1, abyte0);
    }

    private static int a(d d1, q q1, com.qoppa.a.c.c c1, o o1, a a1, byte abyte0[])
    {
        int i = a1._mthnew();
        int j = a1._mthtry();
        int k = c1._fldfor;
        int i1 = c1._flddo;
        a a2 = c1.a;
        boolean flag1 = false;
        for(int k1 = 0; k1 < j; k1++)
        {
            if(c1._fldif)
            {
                char c2 = '\u0100';
                boolean flag2 = o1.a(abyte0, c2);
                flag1 ^= flag2;
            }
            for(int l1 = 0; l1 < i; l1++)
            {
                boolean flag3 = false;
                boolean flag4 = false;
                if(flag1)
                {
                    int i2 = a(a2, l1 - k, k1 - i1);
                    flag3 = i2 != -1;
                    flag4 = i2 != 0;
                }
                if(flag3)
                {
                    if(flag4)
                        a1._mthif(l1, k1, flag4);
                } else
                {
                    int j1 = a1.a(l1 - 1, k1 + 0) << 0;
                    j1 |= a1.a(l1 + 1, k1 - 1) << 1;
                    j1 |= a1.a(l1 + 0, k1 - 1) << 2;
                    j1 |= a1.a(l1 + c1._fldnew[0], k1 + c1._fldnew[1]) << 3;
                    j1 |= a2.a((l1 - k) + 1, (k1 - i1) + 1) << 4;
                    j1 |= a2.a((l1 - k) + 0, (k1 - i1) + 1) << 5;
                    j1 |= a2.a(l1 - k - 1, (k1 - i1) + 1) << 6;
                    j1 |= a2.a((l1 - k) + 1, (k1 - i1) + 0) << 7;
                    j1 |= a2.a((l1 - k) + 0, (k1 - i1) + 0) << 8;
                    j1 |= a2.a(l1 - k - 1, (k1 - i1) + 0) << 9;
                    j1 |= a2.a((l1 - k) + 1, k1 - i1 - 1) << 10;
                    j1 |= a2.a((l1 - k) + 0, k1 - i1 - 1) << 11;
                    j1 |= a2.a((l1 - k) + c1._fldnew[2], (k1 - i1) + c1._fldnew[3]) << 12;
                    boolean flag = o1.a(abyte0, j1);
                    if(flag)
                        a1._mthif(l1, k1, flag);
                }
            }

        }

        return 0;
    }

    private static int _mthdo(d d1, q q1, com.qoppa.a.c.c c1, o o1, a a1, byte abyte0[])
    {
        int i = a1._mthnew();
        int j = a1._mthtry();
        int k = c1._fldfor;
        int i1 = c1._flddo;
        a a2 = c1.a;
        boolean flag1 = false;
        for(int j1 = 0; j1 < j; j1++)
        {
            if(c1._fldif)
            {
                long l1 = 128L;
                boolean flag2 = o1.a(abyte0, (int)l1);
                flag1 ^= flag2;
            }
            for(int k1 = 0; k1 < i; k1++)
            {
                boolean flag3 = false;
                boolean flag4 = false;
                if(flag1)
                {
                    int i2 = a(a2, k1 - k, j1 - i1);
                    flag3 = i2 != -1;
                    flag4 = i2 != 0;
                }
                if(flag3)
                {
                    if(flag4)
                        a1._mthif(k1, j1, flag4);
                } else
                {
                    long l2 = 0L;
                    l2 |= a1.a(k1 - 1, j1 + 0) << 0;
                    l2 |= a1.a(k1 + 1, j1 - 1) << 1;
                    l2 |= a1.a(k1 + 0, j1 - 1) << 2;
                    l2 |= a1.a(k1 - 1, j1 - 1) << 3;
                    l2 |= a2.a((k1 - k) + 1, (j1 - i1) + 1) << 4;
                    l2 |= a2.a((k1 - k) + 0, (j1 - i1) + 1) << 5;
                    l2 |= a2.a((k1 - k) + 1, (j1 - i1) + 0) << 6;
                    l2 |= a2.a((k1 - k) + 0, (j1 - i1) + 0) << 7;
                    l2 |= a2.a(k1 - k - 1, (j1 - i1) + 0) << 8;
                    l2 |= a2.a((k1 - k) + 0, j1 - i1 - 1) << 9;
                    boolean flag = o1.a(abyte0, (int)l2);
                    if(flag)
                        a1._mthif(k1, j1, flag);
                }
            }

        }

        return 0;
    }

    private static int a(a a1, int i, int j)
    {
        int k = a1.a(i, j);
        boolean flag = k == a1.a(i - 1, j - 1) && k == a1.a(i + 0, j - 1) && k == a1.a(i + 1, j - 1) && k == a1.a(i - 1, j + 0) && k == a1.a(i + 1, j + 0) && k == a1.a(i - 1, j + 1) && k == a1.a(i + 0, j + 1) && k == a1.a(i + 1, j + 1);
        if(flag)
            return k;
        else
            return -1;
    }

    private static q a(d d1, q q1)
    {
        int i = q1._fldif;
        for(int j = 0; j < i; j++)
        {
            q q2 = d1.a(q1._fldbyte[j]);
            if(q2 != null)
                switch(q2._fldfor & 0x3f)
                {
                case 4: // '\004'
                case 20: // '\024'
                case 36: // '$'
                case 40: // '('
                    if(q2._fldchar != null)
                        return q2;
                    break;
                }
        }

        return null;
    }
}