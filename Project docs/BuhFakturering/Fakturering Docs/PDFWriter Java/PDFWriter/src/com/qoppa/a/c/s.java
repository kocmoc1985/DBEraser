// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:28
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.c;

import com.qoppa.a.a.a;
import com.qoppa.a.b.c;
import java.util.Vector;

// Referenced classes of package com.qoppa.a.c:
//            f, q, n, w, 
//            t, p, h, o, 
//            k, e, d, l, 
//            i, u, c, r

public class s
{

    public s()
    {
    }

    public static int a(d d1, q q1, byte abyte0[], int j)
        throws c
    {
        f f1 = new f();
        int j1 = 0;
        byte abyte1[] = null;
        int k1 = j;
        if(q1._flddo < 17)
            throw new c("Segment too short");
        n n1 = new n(abyte0, k1);
        k1 += 17;
        int i1 = com.qoppa.a.a.c._mthif(abyte0, k1);
        k1 += 2;
        f1.p = (i1 & 1) != 0;
        f1._fldint = (i1 & 2) != 0;
        f1.g = (i1 & 0xc) >> 2;
        f1.j = 1 << f1.g;
        f1._fldnull = (i1 & 0x30) >> 4;
        f1.q = (i1 & 0x40) != 0;
        f1.h = (i1 & 0x180) >> 7;
        f1.f = (i1 & 0x200) != 0;
        f1._fldfor = (i1 & 0x7c00) >> 10;
        if(f1._fldfor > 15)
            f1._fldfor -= 32;
        f1.i = (i1 & 0x8000) != 0;
        if(f1.p)
        {
            j1 = com.qoppa.a.a.c._mthif(abyte0, k1);
            k1 += 2;
        } else
        if(f1._fldint && !f1.i)
        {
            f1._fldbyte[0] = abyte0[k1];
            f1._fldbyte[1] = abyte0[k1 + 1];
            f1._fldbyte[2] = abyte0[k1 + 2];
            f1._fldbyte[3] = abyte0[k1 + 3];
            k1 += 4;
        }
        f1.o = com.qoppa.a.a.c.a(abyte0, k1);
        k1 += 4;
        if(f1.p)
        {
            Vector vector = q1.a(d1, 53);
            int l1 = 0;
            switch(j1 & 3)
            {
            case 0: // '\0'
                f1.a = new w(t.m);
                break;

            case 1: // '\001'
                f1.a = new w(t.l);
                break;

            case 3: // '\003'
                if(vector != null && l1 < vector.size())
                {
                    q q2 = (q)vector.get(l1);
                    f1.a = (w)q2._fldchar;
                    l1++;
                } else
                {
                    throw new c("Text region uses undefined custom FS Huffman table");
                }
                break;

            case 2: // '\002'
            default:
                throw new c("text region specified invalid FS Huffman table");
            }
            switch((j1 & 0xc) >> 2)
            {
            case 0: // '\0'
                f1.c = new w(t.k);
                break;

            case 1: // '\001'
                f1.c = new w(t.j);
                break;

            case 2: // '\002'
                f1.c = new w(t.i);
                break;

            case 3: // '\003'
                if(vector != null && l1 < vector.size())
                {
                    q q3 = (q)vector.get(l1);
                    f1.c = (w)q3._fldchar;
                    l1++;
                } else
                {
                    throw new c("Text region uses undefined custom DS Huffman table");
                }
                break;
            }
            switch((j1 & 0x30) >> 4)
            {
            case 0: // '\0'
                f1._fldvoid = new w(t.h);
                break;

            case 1: // '\001'
                f1._fldvoid = new w(t.g);
                break;

            case 2: // '\002'
                f1._fldvoid = new w(t.f);
                break;

            case 3: // '\003'
                if(vector != null && l1 < vector.size())
                {
                    q q4 = (q)vector.get(l1);
                    f1._fldvoid = (w)q4._fldchar;
                    l1++;
                } else
                {
                    throw new c("Text region uses undefined custom DT Huffman table");
                }
                break;
            }
            switch((j1 & 0xc0) >> 6)
            {
            case 0: // '\0'
                f1.n = new w(t.e);
                break;

            case 1: // '\001'
                f1.n = new w(t.d);
                break;

            case 3: // '\003'
                if(vector != null && l1 < vector.size())
                {
                    q q5 = (q)vector.get(l1);
                    f1.n = (w)q5._fldchar;
                    l1++;
                } else
                {
                    throw new c("Text region uses undefined custom RDW Huffman table");
                }
                break;

            case 2: // '\002'
            default:
                throw new c("text region specified invalid RDW Huffman table");
            }
            switch((j1 & 0x300) >> 8)
            {
            case 0: // '\0'
                f1._fldtry = new w(t.e);
                break;

            case 1: // '\001'
                f1._fldtry = new w(t.d);
                break;

            case 3: // '\003'
                if(vector != null && l1 < vector.size())
                {
                    q q6 = (q)vector.get(l1);
                    f1._fldtry = (w)q6._fldchar;
                    l1++;
                } else
                {
                    throw new c("Text region uses undefined custom RDH Huffman table");
                }
                break;

            case 2: // '\002'
            default:
                throw new c("text region specified invalid RDH Huffman table");
            }
            switch((j1 & 0xc00) >> 10)
            {
            case 0: // '\0'
                f1.m = new w(t.e);
                break;

            case 1: // '\001'
                f1.m = new w(t.d);
                break;

            case 3: // '\003'
                if(vector != null && l1 < vector.size())
                {
                    q q7 = (q)vector.get(l1);
                    f1.m = (w)q7._fldchar;
                    l1++;
                } else
                {
                    throw new c("Text region uses undefined custom RDX Huffman table");
                }
                break;

            case 2: // '\002'
            default:
                throw new c("text region specified invalid RDX Huffman table");
            }
            switch((j1 & 0x3000) >> 12)
            {
            case 0: // '\0'
                f1.l = new w(t.e);
                break;

            case 1: // '\001'
                f1.l = new w(t.d);
                break;

            case 3: // '\003'
                if(vector != null && l1 < vector.size())
                {
                    q q8 = (q)vector.get(l1);
                    f1.l = (w)q8._fldchar;
                    l1++;
                } else
                {
                    throw new c("Text region uses undefined custom RDY Huffman table");
                }
                break;

            case 2: // '\002'
            default:
                throw new c("text region specified invalid RDY Huffman table");
            }
            switch((j1 & 0x4000) >> 14)
            {
            default:
                break;

            case 0: // '\0'
                f1._fldcase = new w(t.r);
                break;

            case 1: // '\001'
                if(vector != null && l1 < vector.size())
                {
                    q q9 = (q)vector.get(l1);
                    f1._fldcase = (w)q9._fldchar;
                    l1++;
                } else
                {
                    throw new c("Text region uses undefined custom RSIZE Huffman table");
                }
                break;
            }
        }
        int i2 = p._mthif(d1, q1);
        Vector vector1;
        if(i2 != 0)
            vector1 = com.qoppa.a.c.p.a(d1, q1);
        else
            throw new c("text region refers to no symbol dictionaries!");
        if(vector1 == null)
            throw new c("unable to retrive symbol dictionaries! previous parsing error?");
        vector1.get(0);
        for(int j2 = 1; j2 < i2; j2++)
        {
            if(vector1.get(j2) != null)
                continue;
            i2 = j2;
            break;
        }

        if(!f1.p && f1._fldint)
        {
            char c1 = f1.i ? '\u0400' : '\u2000';
            abyte1 = new byte[c1];
        }
        a a1 = new a(n1._fldfor, n1._fldif);
        o o1 = null;
        h h1 = new h(abyte0, k1, q1._flddo - (k1 - j));
        if(!f1.p)
        {
            int i3 = 0;
            for(int k2 = 0; k2 < i2; k2++)
                i3 += ((p)vector1.get(k2)).a;

            o1 = new o(h1);
            h1 = null;
            f1._fldelse = new k();
            f1.t = new k();
            f1._fldgoto = new k();
            f1.s = new k();
            int l2;
            for(l2 = 0; 1 << l2 < i3; l2++);
            f1._fldlong = new e(l2);
            f1._fldchar = new k();
            f1.e = new k();
            f1.k = new k();
            f1.d = new k();
            f1.b = new k();
        }
        a(d1, q1, f1, vector1, a1, abyte0, k1, q1._flddo - (k1 - j), abyte1, o1, h1);
        if((q1._fldfor & 0x3f) == 4)
            q1._fldchar = a1;
        else
            d1._mthdo().a(a1, n1._flddo, n1.a, n1._fldint);
        return 0;
    }

    public static int a(d d1, q q1, f f1, Vector vector, a a1, byte abyte0[], int j, int i1, 
            byte abyte1[], o o1, h h1)
        throws c
    {
        int i3 = 0;
        i j5 = null;
        w w1 = null;
        boolean flag1 = false;
        boolean aflag[] = new boolean[1];
        int ai[] = new int[1];
        int i5 = 0;
        for(int i4 = 0; i4 < vector.size(); i4++)
            i5 += ((p)vector.get(i4)).a;

        if(f1.p)
        {
            int l8 = 0;
            j5 = new i(h1);
            u au[] = new u[35];
            for(int j4 = 0; j4 < 35; j4++)
                au[j4] = new u(j5.a(4), 0, j4);

            t t1 = new t(false, au);
            w w2 = new w(t1);
            u au1[] = new u[i5];
            int k8;
            for(int k4 = 0; k4 < i5; k4 += k8)
            {
                int k5 = w2.a(j5, aflag);
                if(aflag[0] || k5 < 0 || k5 >= 35)
                    throw new c("error reading symbol ID huffman table!");
                int i8;
                if(k5 < 32)
                {
                    i8 = k5;
                    l8 = 1;
                } else
                {
                    if(k5 == 32)
                    {
                        i8 = au1[k4 - 1]._flddo;
                        if(k4 < 1)
                            throw new c("error decoding symbol id table: run length with no antecedent!");
                    } else
                    {
                        i8 = 0;
                    }
                    if(k5 == 32)
                        l8 = j5.a(2) + 3;
                    else
                    if(k5 == 33)
                        l8 = j5.a(3) + 3;
                    else
                    if(k5 == 34)
                        l8 = j5.a(7) + 11;
                }
                if(k4 + l8 > i5)
                    l8 = i5 - k4;
                for(k8 = 0; k8 < l8; k8++)
                    au1[k4 + k8] = new u(i8, 0, k4 + k8);

            }

            t t2 = new t(false, au1);
            j5._mthif();
            w1 = new w(t2);
        }
        a1._mthif(f1.f);
        int k1;
        if(f1.p)
        {
            k1 = f1._fldvoid.a(j5, null);
        } else
        {
            int l5 = com.qoppa.a.a.c.a(f1._fldelse.a(o1, ai));
            k1 = ai[0];
        }
        k1 *= -f1.j;
        int i2 = 0;
        for(long l1 = 0L; l1 < f1.o;)
        {
            int j2;
            if(f1.p)
            {
                j2 = f1._fldvoid.a(j5, aflag);
            } else
            {
                int i6 = com.qoppa.a.a.c.a(f1._fldelse.a(o1, ai));
                j2 = ai[0];
            }
            j2 *= f1.j;
            k1 += j2;
            boolean flag = true;
            do
            {
                if(flag)
                {
                    int k2;
                    if(f1.p)
                    {
                        k2 = f1.a.a(j5, aflag);
                        int j6 = com.qoppa.a.a.c.a(aflag[0]);
                    } else
                    {
                        int k6 = com.qoppa.a.a.c.a(f1.t.a(o1, ai));
                        k2 = ai[0];
                    }
                    i2 += k2;
                    i3 = i2;
                    flag = false;
                } else
                {
                    int l2;
                    int l6;
                    if(f1.p)
                    {
                        l2 = f1.c.a(j5, aflag);
                        l6 = com.qoppa.a.a.c.a(aflag[0]);
                    } else
                    {
                        l6 = com.qoppa.a.a.c.a(f1._fldgoto.a(o1, ai));
                        l2 = ai[0];
                    }
                    if(l6 != 0)
                        break;
                    i3 += l2 + f1._fldfor;
                }
                int j3;
                if(f1.j == 1)
                    j3 = 0;
                else
                if(f1.p)
                {
                    j3 = j5.a(f1.g);
                } else
                {
                    int i7 = com.qoppa.a.a.c.a(f1.s.a(o1, ai));
                    j3 = ai[0];
                }
                int l3 = k1 + j3;
                int j1;
                if(f1.p)
                {
                    j1 = w1.a(j5, aflag);
                    int j7 = com.qoppa.a.a.c.a(aflag[0]);
                } else
                {
                    int k7 = f1._fldlong.a(o1, ai);
                    j1 = ai[0];
                }
                if(j1 >= i5)
                    throw new c("symbol id out of range! (" + j1 + "/" + i5 + ")");
                int j8 = j1;
                int l4 = 0;
                p p1;
                for(p1 = (p)vector.get(l4++); j8 >= p1.a; p1 = (p)vector.get(l4++))
                    j8 -= p1.a;

                a a2 = p1._fldif[j8];
                if(f1._fldint)
                {
                    int i9;
                    if(f1.p)
                    {
                        i9 = j5.a(1);
                    } else
                    {
                        int l7 = com.qoppa.a.a.c.a(f1._fldchar.a(o1, ai));
                        i9 = ai[0];
                    }
                    if(i9 != 0)
                    {
                        int l10 = 0;
                        int k9;
                        int i10;
                        int j10;
                        int k10;
                        if(!f1.p)
                        {
                            f1.e.a(o1, ai);
                            k9 = ai[0];
                            f1.k.a(o1, ai);
                            i10 = ai[0];
                            f1.d.a(o1, ai);
                            j10 = ai[0];
                            f1.b.a(o1, ai);
                            k10 = ai[0];
                        } else
                        {
                            k9 = f1.n.a(j5, null);
                            i10 = f1._fldtry.a(j5, null);
                            j10 = f1.m.a(j5, null);
                            k10 = f1.l.a(j5, null);
                            l10 = f1._fldcase.a(j5, null);
                            j5._mthif();
                        }
                        a a4 = a2;
                        a a3 = new a(a4._mthnew() + k9, a4._mthtry() + i10);
                        com.qoppa.a.c.c c1 = new com.qoppa.a.c.c();
                        c1._fldint = f1.i;
                        c1.a = a4;
                        c1._fldfor = (k9 >> 1) + j10;
                        c1._flddo = (i10 >> 1) + k10;
                        c1._fldif = false;
                        System.arraycopy(f1._fldbyte, 0, c1._fldnew, 0, 4);
                        r._mthif(d1, q1, c1, o1, a3, abyte1);
                        a2 = a3;
                        if(f1.p)
                            j5._mthif(l10);
                    }
                }
                if(!f1.q && f1._fldnull > 1)
                    i3 += a2._mthnew() - 1;
                else
                if(f1.q && (f1._fldnull & 1) == 0)
                    i3 += a2._mthtry() - 1;
                int k3 = i3;
                int j9 = 0;
                int l9 = 0;
                if(!f1.q)
                    switch(f1._fldnull)
                    {
                    case 1: // '\001'
                        j9 = k3;
                        l9 = l3;
                        break;

                    case 3: // '\003'
                        j9 = (k3 - a2._mthnew()) + 1;
                        l9 = l3;
                        break;

                    case 0: // '\0'
                        j9 = k3;
                        l9 = (l3 - a2._mthtry()) + 1;
                        break;

                    case 2: // '\002'
                        j9 = (k3 - a2._mthnew()) + 1;
                        l9 = (l3 - a2._mthtry()) + 1;
                        break;
                    }
                else
                    switch(f1._fldnull)
                    {
                    case 1: // '\001'
                        j9 = l3;
                        l9 = k3;
                        break;

                    case 3: // '\003'
                        j9 = (l3 - a2._mthnew()) + 1;
                        l9 = k3;
                        break;

                    case 0: // '\0'
                        j9 = l3;
                        l9 = (k3 - a2._mthtry()) + 1;
                        break;

                    case 2: // '\002'
                        j9 = (l3 - a2._mthnew()) + 1;
                        l9 = (k3 - a2._mthtry()) + 1;
                        break;
                    }
                a1.a(a2, j9, l9, f1.h);
                if(!f1.q && f1._fldnull < 2)
                    i3 += a2._mthnew() - 1;
                else
                if(f1.q && (f1._fldnull & 1) != 0)
                    i3 += a2._mthtry() - 1;
                l1++;
            } while(true);
        }

        return 0;
    }
}