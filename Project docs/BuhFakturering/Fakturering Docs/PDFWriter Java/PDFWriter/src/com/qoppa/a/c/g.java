// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:27
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.c;

import com.qoppa.a.a.a;
import com.qoppa.a.b.c;
import java.util.Vector;

// Referenced classes of package com.qoppa.a.c:
//            b, a, h, o, 
//            v, q, n, d, 
//            l

public class g
{
    private static class a
    {

        byte _fldfor;
        long _fldif;
        long a;
        long _flddo;
        long _fldelse;
        int _fldtry;
        int _fldint;
        boolean _fldcase;
        int _fldnew;
        boolean _fldbyte;
        int _fldgoto;
        boolean _fldchar;

        private a()
        {
        }

        a(a a1)
        {
            this();
        }
    }

    private static class c
    {

        int a;
        com.qoppa.a.a.a _fldif[];

        private c()
        {
        }

        c(c c1)
        {
            this();
        }
    }

    private static class b
    {

        boolean _flddo;
        long _fldif;
        long _fldfor;
        long _fldint;
        int a;

        private b()
        {
        }

        b(b b1)
        {
            this();
        }
    }


    public g()
    {
    }

    private static c a(d d1, b b1, com.qoppa.a.a.a a1)
    {
        int i = (int)b1._fldint + 1;
        int j = (int)b1._fldif;
        int k = (int)b1._fldfor;
        c c1 = new c(null);
        c1._fldif = new com.qoppa.a.a.a[i];
        c1.a = i;
        for(int i1 = 0; i1 < i; i1++)
        {
            c1._fldif[i1] = new com.qoppa.a.a.a(j, k);
            c1._fldif[i1].a(a1, -i1 * j, 0, 4);
        }

        return c1;
    }

    private static c a(d d1, q q1, b b1, byte abyte0[], int i, int j, byte abyte1[])
    {
        com.qoppa.a.c.b b2 = new com.qoppa.a.c.b();
        com.qoppa.a.a.a a1 = new com.qoppa.a.a.a((int)(b1._fldif * (b1._fldint + 1L)), (int)b1._fldfor);
        b2._fldint = b1._flddo;
        b2._fldfor = b1.a;
        b2._flddo = false;
        b2.a = false;
        b2._fldif[0] = (byte)(int)(-b1._fldif);
        b2._fldif[1] = 0;
        b2._fldif[2] = -3;
        b2._fldif[3] = -1;
        b2._fldif[4] = 2;
        b2._fldif[5] = -2;
        b2._fldif[6] = -2;
        b2._fldif[7] = -2;
        int k;
        if(b1._flddo)
        {
            k = com.qoppa.a.c.a.a(q1, b2, abyte0, i, j, a1);
        } else
        {
            h h1 = new h(abyte0, i, j);
            o o1 = new o(h1);
            int i1 = com.qoppa.a.c.v.a(b2, o1, a1, abyte1);
        }
        return a(d1, b1, a1);
    }

    public static int a(d d1, q q1, byte abyte0[], int i)
        throws com.qoppa.a.b.c
    {
        b b1 = new b(null);
        byte abyte1[] = null;
        int j = 0;
        if(q1._flddo < 7)
            throw new com.qoppa.a.b.c("Segment too short");
        byte byte0 = abyte0[i];
        b1._flddo = (byte0 & 1) != 0;
        b1.a = (byte0 & 6) >> 1;
        b1._fldif = abyte0[i + 1];
        b1._fldfor = abyte0[i + 2];
        b1._fldint = com.qoppa.a.a.c.a(abyte0, i + 3);
        j += 7;
        if(b1._flddo)
        {
            int _tmp = b1.a;
        }
        if(!b1._flddo)
        {
            int k = com.qoppa.a.c.v.a(b1.a);
            abyte1 = new byte[k];
        }
        q1._fldchar = a(d1, q1, b1, abyte0, i + j, q1._flddo - j, abyte1);
        return q1._fldchar == null ? 1 : 0;
    }

    private static int a(d d1, q q1, a a1, c c1, byte abyte0[], int i, int j, com.qoppa.a.a.a a2, 
            byte abyte1[])
        throws com.qoppa.a.b.c
    {
        a2._mthif(a1._fldchar);
        boolean _tmp = a1._fldbyte;
        int k = (int)Math.ceil(Math.log(c1.a) / Math.log(2D));
        int ai[] = a(a1, k, abyte0, i, j, abyte1);
        if(ai == null)
            throw new com.qoppa.a.b.c("Missing gray scale information in halftone region.");
        for(int i1 = 0; (long)i1 < a1._fldelse; i1++)
        {
            for(int j1 = 0; (long)j1 < a1._flddo; j1++)
            {
                int k1 = ai[(int)((long)i1 * a1._flddo + (long)j1)];
                k1 = Math.min(c1.a - 1, k1);
                com.qoppa.a.a.a a3 = c1._fldif[k1];
                int l1 = (int)((a1._fldif + (long)(i1 * a1._fldint) + (long)(j1 * a1._fldtry)) / 256L);
                int i2 = (int)(((a1.a + (long)(i1 * a1._fldtry)) - (long)(j1 * a1._fldint)) / 256L);
                a2.a(a3, l1, i2, a1._fldgoto);
            }

        }

        return 0;
    }

    private static int[] a(a a1, int i, byte abyte0[], int j, int k, byte abyte1[])
    {
        com.qoppa.a.a.a aa[] = new com.qoppa.a.a.a[i];
        com.qoppa.a.c.b b1 = new com.qoppa.a.c.b();
        b1._fldint = a1._fldcase;
        b1._fldfor = a1._fldnew;
        b1._flddo = false;
        b1.a = a1._fldbyte;
        b1._fldif = (new byte[] {
            3, -1, -3, -1, 2, -2, -2, -2
        });
        if(b1._fldfor >= 2)
            b1._fldif[0] = 2;
        h h1 = new h(abyte0, j, k);
        o o1 = new o(h1);
        aa[i - 1] = new com.qoppa.a.a.a((int)a1._flddo, (int)a1._fldelse);
        com.qoppa.a.c.v.a(b1, o1, aa[i - 1], abyte1);
        for(int i1 = i - 2; i1 >= 0; i1--)
        {
            aa[i1] = new com.qoppa.a.a.a((int)a1._flddo, (int)a1._fldelse);
            com.qoppa.a.c.v.a(b1, o1, aa[i1], abyte1);
            byte abyte2[] = aa[i1]._mthint();
            byte abyte3[] = aa[i1 + 1]._mthint();
            for(int l1 = 0; l1 < abyte2.length; l1++)
                abyte2[l1] ^= abyte3[l1];

        }

        int ai[] = new int[(int)(a1._flddo * a1._fldelse)];
        for(int j1 = 0; (long)j1 < a1._fldelse; j1++)
        {
            for(int k1 = 0; (long)k1 < a1._flddo; k1++)
                ai[(int)((long)j1 * a1._flddo + (long)k1)] = a(aa, k1, j1);

        }

        return ai;
    }

    private static int a(com.qoppa.a.a.a aa[], int i, int j)
    {
        int k = 0;
        for(int i1 = aa.length - 1; i1 >= 0; i1--)
        {
            k <<= 1;
            k |= aa[i1].a(i, j);
        }

        return k;
    }

    public static int _mthif(d d1, q q1, byte abyte0[], int i)
        throws com.qoppa.a.b.c
    {
        int j = i;
        a a1 = new a(null);
        byte abyte1[] = null;
        if(q1._flddo < 18)
            throw new com.qoppa.a.b.c("Segment too short");
        n n1 = new n(abyte0, j);
        j += 17;
        a1._fldfor = abyte0[j];
        a1._fldcase = (a1._fldfor & 1) != 0;
        a1._fldnew = (a1._fldfor & 6) >> 1;
        a1._fldbyte = (a1._fldfor & 8) >> 3 != 0;
        a1._fldgoto = (a1._fldfor & 0x70) >> 4;
        a1._fldchar = (a1._fldfor & 0x80) >> 7 != 0;
        j++;
        if(a1._fldcase)
        {
            int _tmp = a1._fldnew;
        }
        if(a1._fldcase)
        {
            boolean _tmp1 = a1._fldbyte;
        }
        if(q1._flddo - (j - i) < 16)
            throw new com.qoppa.a.b.c("Segment too short");
        a1._flddo = com.qoppa.a.a.c.a(abyte0, j);
        a1._fldelse = com.qoppa.a.a.c.a(abyte0, j + 4);
        a1._fldif = com.qoppa.a.a.c.a(abyte0, j + 8);
        a1.a = com.qoppa.a.a.c.a(abyte0, j + 12);
        j += 16;
        if(q1._flddo - (j - i) < 4)
            throw new com.qoppa.a.b.c("Segment too short");
        a1._fldtry = com.qoppa.a.a.c._mthif(abyte0, j);
        a1._fldint = com.qoppa.a.a.c._mthif(abyte0, j + 2);
        j += 4;
        if(!a1._fldcase)
        {
            int k = com.qoppa.a.c.v.a(a1._fldnew);
            abyte1 = new byte[k];
        }
        com.qoppa.a.a.a a2 = new com.qoppa.a.a.a(n1._fldfor, n1._fldif);
        Vector vector = q1.a(d1, 16);
        if(vector == null || vector.size() == 0)
            throw new com.qoppa.a.b.c("Missing pattern dictionary for halftone region.");
        q q2 = (q)vector.get(0);
        c c1 = (c)q2._fldchar;
        int i1 = a(d1, q1, a1, c1, abyte0, j, q1._flddo - (j - i), a2, abyte1);
        if(i1 == 0)
            d1._mthdo().a(a2, n1._flddo, n1.a, n1._fldint);
        return i1;
    }
}