// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:28
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.c;

import com.qoppa.a.a.a;
import com.qoppa.a.b.c;
import java.util.Arrays;
import java.util.Vector;

// Referenced classes of package com.qoppa.a.c:
//            q, w, t, d, 
//            h, o, k, e, 
//            i, u, b, v, 
//            f, s, c, r, 
//            a

public class p
{
    private static class a
    {

        boolean _fldelse;
        boolean a;
        long _fldfor;
        p _fldcase;
        long _fldnull;
        long _flddo;
        w _fldtry;
        w _fldif;
        w _fldnew;
        w _fldlong;
        int _fldint;
        byte _fldgoto[];
        boolean _fldbyte;
        byte _fldchar[];

        private a()
        {
            _fldgoto = new byte[8];
            _fldchar = new byte[4];
        }

        a(a a1)
        {
            this();
        }
    }


    public p(int j)
    {
        a = j;
        _fldif = new com.qoppa.a.a.a[j];
    }

    public static int a(d d1, q q1, byte abyte0[], int j)
        throws c
    {
        a a1 = new a(null);
        byte abyte1[] = null;
        byte abyte2[] = null;
        if(q1._flddo < 10)
            throw new c("Symbol Dictionary Segment too short.");
        int l = com.qoppa.a.a.c._mthif(abyte0, j);
        a1._fldelse = (l & 1) != 0;
        a1.a = (l >> 1 & 1) != 0;
        a1._fldint = l >> 10 & 3;
        a1._fldbyte = (l >> 12 & 1) != 0;
        a1._fldtry = null;
        a1._fldif = null;
        a1._fldnew = null;
        a1._fldlong = null;
        if(a1._fldelse)
        {
            switch((l & 0xc) >> 2)
            {
            case 0: // '\0'
                a1._fldtry = new w(t.o);
                break;

            case 1: // '\001'
                a1._fldtry = new w(t.n);
                break;

            case 3: // '\003'
                throw new c("symbol dictionary uses custom DH huffman table (NYI)");

            case 2: // '\002'
            default:
                throw new c("symbol dictionary specified invalid huffman table");
            }
            switch((l & 0x30) >> 4)
            {
            case 0: // '\0'
                a1._fldif = new w(t.q);
                break;

            case 1: // '\001'
                a1._fldif = new w(t.p);
                break;

            case 3: // '\003'
                throw new c("symbol dictionary uses custom DW huffman table (NYI)");

            case 2: // '\002'
            default:
                throw new c("symbol dictionary specified invalid huffman table");
            }
            if((l & 0x40) != 0)
                throw new c("symbol dictionary uses custom BMSIZE huffman table (NYI)");
            a1._fldnew = new w(t.r);
            if((l & 0x80) != 0)
                throw new c("symbol dictionary uses custom REFAGG huffman table (NYI)");
            a1._fldlong = new w(t.r);
        }
        if(a1._fldelse);
        byte byte0 = a1._fldelse ? 0 : ((byte)(a1._fldint != 0 ? 2 : 8));
        System.arraycopy(abyte0, j + 2, a1._fldgoto, 0, byte0);
        int i1 = 2 + byte0;
        if(a1.a && !a1._fldbyte)
        {
            if(i1 + 4 > q1._flddo)
                throw new c("Symbol Dictionary Segment too short.");
            System.arraycopy(abyte0, j + i1, a1._fldchar, 0, 4);
            i1 += 4;
        } else
        {
            Arrays.fill(a1._fldchar, (byte)0);
        }
        if(i1 + 8 > q1._flddo)
            throw new c("Symbol Dictionary Segment too short.");
        a1._flddo = com.qoppa.a.a.c.a(abyte0, j + i1);
        a1._fldnull = com.qoppa.a.a.c.a(abyte0, j + i1 + 4);
        i1 += 8;
        int j1 = _mthif(d1, q1);
        a1._fldcase = null;
        if(j1 > 0)
        {
            Vector vector = a(d1, q1);
            a1._fldcase = a(d1, vector);
        }
        if(a1._fldcase != null)
            a1._fldfor = a1._fldcase.a;
        else
            a1._fldfor = 0L;
        if(!a1._fldelse)
        {
            int k1 = a1._fldint != 0 ? ((int) (a1._fldint != 1 ? 1024 : 8192)) : 0x10000;
            abyte1 = new byte[k1];
        }
        if(a1.a)
        {
            char c1 = a1._fldbyte ? '\u0400' : '\u2000';
            abyte2 = new byte[c1];
        }
        q1._fldchar = a(d1, q1, a1, abyte0, j + i1, q1._flddo - i1, abyte1, abyte2);
        return q1._fldchar == null ? -1 : 0;
    }

    public static int _mthif(d d1, q q1)
    {
        int l = 0;
        for(int j = 0; j < q1._fldif; j++)
        {
            q q2 = d1.a(q1._fldbyte[j]);
            if(q2 != null && (q2._fldfor & 0x3f) == 0)
                l++;
        }

        return l;
    }

    public static Vector a(d d1, q q1)
    {
        Vector vector = new Vector();
        for(int j = 0; j < q1._fldif; j++)
        {
            q q2 = d1.a(q1._fldbyte[j]);
            if(q2 != null && (q2._fldfor & 0x3f) == 0)
                vector.add(q2._fldchar);
        }

        return vector;
    }

    private static p a(d d1, Vector vector)
    {
        p p1 = null;
        int k1 = 0;
        for(int j = 0; j < vector.size(); j++)
            k1 += ((p)vector.get(j)).a;

        p1 = new p(k1);
        if(p1 != null)
        {
            int j1 = 0;
            for(int l = 0; l < vector.size(); l++)
            {
                p p2 = (p)vector.get(l);
                for(int i1 = 0; i1 < p2.a; i1++)
                    p1._fldif[j1++] = p2._fldif[i1];

            }

        }
        return p1;
    }

    private static p a(d d1, q q1, a a1, byte abyte0[], int j, int l, byte abyte1[], byte abyte2[])
        throws c
    {
        long al[] = null;
        int i2 = 0;
        i j2 = null;
        w w1 = null;
        o o1 = null;
        k k2 = null;
        k k3 = null;
        k k4 = null;
        k k5 = null;
        e e1 = null;
        k k6 = null;
        k k7 = null;
        boolean flag = false;
        f f1 = null;
        Vector vector = null;
        boolean aflag[] = new boolean[1];
        int ai[] = new int[1];
        int i1 = 0;
        long l1 = 0L;
        h h1 = new h(abyte0, j, l);
        if(a1.a)
        {
            long l5 = (long)a1._fldcase.a + a1._fldnull;
            for(i2 = 0; (long)(1 << i2) < l5; i2++);
        }
        if(!a1._fldelse)
        {
            o1 = new o(h1);
            k2 = new k();
            k3 = new k();
            k4 = new k();
            k5 = new k();
            if(a1.a)
            {
                e1 = new e(i2);
                k6 = new k();
                k7 = new k();
            }
        } else
        {
            j2 = new i(h1);
            w1 = new w(t.d);
            if(!a1.a)
                al = new long[(int)a1._fldnull];
            if(a1.a)
            {
                u au[] = new u[(int)((long)a1._fldcase.a + a1._fldnull)];
                for(int l6 = 0; l6 < au.length; l6++)
                    au[l6] = new u(i2, 0, l6);

            }
        }
        p p1 = new p((int)a1._fldnull);
        while(l1 < a1._fldnull) 
        {
            int i3;
            int i6;
            if(a1._fldelse)
            {
                i6 = a1._fldtry.a(j2, aflag);
                i3 = com.qoppa.a.a.c.a(aflag[0]);
            } else
            {
                i3 = com.qoppa.a.a.c.a(k2.a(o1, ai));
                i6 = ai[0];
            }
            com.qoppa.a.a.c.a(i3);
            i1 += i6;
            int j1 = 0;
            int k1 = 0;
            long l2 = l1;
            if(i1 < 0)
                throw new c("Invalid HCHEIGHT value");
            for(; l1 <= a1._fldnull; l1++)
            {
                int j3;
                int i7;
                if(a1._fldelse)
                {
                    i7 = a1._fldif.a(j2, aflag);
                    j3 = com.qoppa.a.a.c.a(aflag[0]);
                } else
                {
                    j3 = com.qoppa.a.a.c.a(k3.a(o1, ai));
                    i7 = ai[0];
                }
                if(com.qoppa.a.a.c.a(j3))
                    break;
                j1 += i7;
                k1 += j1;
                if(j1 < 0)
                    throw new c("Invalid SYMWIDTH value (" + j1 + ") at symbol " + (l1 + 1L));
                if(!a1._fldelse || a1.a)
                    if(!a1.a)
                    {
                        b b1 = new b();
                        b1._fldint = false;
                        b1._fldfor = a1._fldint;
                        b1._flddo = false;
                        b1.a = false;
                        byte byte0 = ((byte)(a1._fldint != 0 ? 2 : 8));
                        System.arraycopy(a1._fldgoto, 0, b1._fldif, 0, byte0);
                        com.qoppa.a.a.a a2 = new com.qoppa.a.a.a(j1, i1);
                        int l3 = com.qoppa.a.c.v.a(b1, o1, a2, abyte1);
                        p1._fldif[(int)l1] = a2;
                    } else
                    {
                        int i4;
                        long l7;
                        if(a1._fldelse)
                        {
                            l7 = a1._fldlong.a(j2, aflag);
                            i4 = com.qoppa.a.a.c.a(aflag[0]);
                        } else
                        {
                            i4 = com.qoppa.a.a.c.a(k5.a(o1, ai));
                            l7 = ai[0];
                        }
                        if(com.qoppa.a.a.c.a(i4) || (int)l7 <= 0)
                            throw new c("invalid number of symbols or OOB in aggregate glyph");
                        if(l7 > 1L)
                        {
                            if(f1 == null)
                            {
                                vector = new Vector();
                                p p3 = new p((int)(a1._fldfor + a1._fldnull));
                                vector.add(p3);
                                p3.a = (int)(a1._fldfor + a1._fldnull);
                                for(int l8 = 0; (long)l8 < a1._fldfor; l8++)
                                    p3._fldif[l8] = a1._fldcase._fldif[l8].a();

                                f1 = new f();
                                if(!a1._fldelse)
                                {
                                    f1._fldelse = new k();
                                    f1.t = new k();
                                    f1._fldgoto = new k();
                                    f1.s = new k();
                                    for(i2 = 0; 1 << i2 < (int)(a1._fldfor + a1._fldnull); i2++);
                                    f1._fldlong = new e(i2);
                                    f1._fldchar = new k();
                                    f1.e = new k();
                                    f1.k = new k();
                                    f1.d = new k();
                                    f1.b = new k();
                                } else
                                {
                                    f1.a = new w(t.m);
                                    f1.c = new w(t.k);
                                    f1._fldvoid = new w(t.h);
                                    f1.n = new w(t.d);
                                    f1._fldtry = new w(t.d);
                                    f1.m = new w(t.d);
                                    f1.l = new w(t.d);
                                }
                                f1.p = a1._fldelse;
                                f1._fldint = true;
                                f1.j = 1;
                                f1.f = false;
                                f1.h = 0;
                                f1.q = false;
                                f1._fldnull = 1;
                                f1._fldfor = 0;
                                f1.i = a1._fldbyte;
                            }
                            f1.o = l7;
                            com.qoppa.a.a.a a3 = new com.qoppa.a.a.a(j1, i1);
                            com.qoppa.a.c.s.a(d1, q1, f1, vector, a3, abyte0, j, l, abyte2, o1, null);
                            p1._fldif[(int)l1] = a3;
                            ((p)vector.get(0))._fldif[(int)(a1._fldfor + l1)] = p1._fldif[(int)l1].a();
                        } else
                        {
                            com.qoppa.a.c.c c1 = new com.qoppa.a.c.c();
                            long l9;
                            int i10;
                            int k10;
                            if(a1._fldelse)
                            {
                                l9 = j2.a(i2);
                                i10 = w1.a(j2, aflag);
                                k10 = w1.a(j2, aflag);
                                a1._fldnew.a(j2, aflag);
                                j2._mthif();
                            } else
                            {
                                int j4 = e1.a(o1, ai);
                                l9 = ai[0];
                                j4 = com.qoppa.a.a.c.a(k6.a(o1, ai));
                                i10 = ai[0];
                                j4 = com.qoppa.a.a.c.a(k7.a(o1, ai));
                                k10 = ai[0];
                            }
                            if(l9 >= (long)a1._fldcase.a + l1)
                                throw new c("refinement references unknown symbol " + l9);
                            com.qoppa.a.a.a a6 = new com.qoppa.a.a.a(j1, i1);
                            c1._fldint = a1._fldbyte;
                            c1.a = l9 >= (long)a1._fldcase.a ? p1._fldif[(int)(l9 - (long)a1._fldcase.a)] : a1._fldcase._fldif[(int)l9];
                            c1._fldfor = i10;
                            c1._flddo = k10;
                            c1._fldif = false;
                            System.arraycopy(a1._fldchar, 0, c1._fldnew, 0, 4);
                            r._mthif(d1, q1, c1, o1, a6, abyte2);
                            p1._fldif[(int)l1] = a6;
                            if(a1._fldelse)
                                j2._mthif();
                        }
                    }
                if(a1._fldelse && !a1.a)
                    al[(int)l1] = j1;
            }

            if(a1._fldelse && !a1.a)
            {
                int i8 = a1._fldnew.a(j2, aflag);
                int l4 = com.qoppa.a.a.c.a(aflag[0]);
                if(com.qoppa.a.a.c.a(l4) || i8 < 0)
                    throw new c("error decoding size of collective bitmap!");
                j2._mthif();
                com.qoppa.a.a.a a4 = new com.qoppa.a.a.a(k1, i1);
                if(i8 == 0)
                {
                    int j10 = j + j2.a();
                    i8 = a4._fldnew.length;
                    System.arraycopy(abyte0, j10, a4._fldnew, 0, i8);
                } else
                {
                    b b2 = new b();
                    b2._fldint = true;
                    int i5 = com.qoppa.a.c.a.a(q1, b2, abyte0, j + j2.a(), i8, a4);
                    if(com.qoppa.a.a.c.a(i5))
                        throw new c("error decoding MMR bitmap image!");
                }
                j2._mthif(i8);
                int i9 = 0;
                for(int k8 = (int)l2; (long)k8 < l1; k8++)
                {
                    com.qoppa.a.a.a a5 = new com.qoppa.a.a.a((int)al[k8], i1);
                    a5.a(a4, -i9, 0, 4);
                    i9 = (int)((long)i9 + al[k8]);
                    p1._fldif[k8] = a5;
                }

            }
        }
        p p2 = new p((int)a1._flddo);
        int j6 = 0;
        boolean flag1 = false;
        int j9 = 0;
        int j8;
        if(a1._fldcase != null)
            j8 = a1._fldcase.a;
        else
            j8 = 0;
        w w2 = null;
        if(a1._fldelse)
            w2 = new w(t.r);
        while((long)j9 < a1._flddo) 
        {
            int k9;
            if(a1._fldelse)
            {
                k9 = w2.a(j2, aflag);
            } else
            {
                int j5 = com.qoppa.a.a.c.a(k4.a(o1, ai));
                k9 = ai[0];
            }
            if(flag1)
            {
                if((long)k9 > a1._flddo - (long)j9)
                    k9 = (int)(a1._flddo - (long)j9 - 1L);
                for(int j7 = 0; j7 < k9; j7++)
                {
                    p2._fldif[j9++] = j6 >= j8 ? p1._fldif[j6 - j8] : a1._fldcase._fldif[j6];
                    j6++;
                }

            } else
            {
                j6 += k9;
            }
            flag1 = !flag1;
        }
        return p2;
    }

    int a;
    com.qoppa.a.a.a _fldif[];
}