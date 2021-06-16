// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:29
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.b;

import com.qoppa.c.*;

// Referenced classes of package com.qoppa.b:
//            m, f, n, j

public class l extends m
{

    public l(String s, String s1, int i, String s2)
    {
        if(s1 != null || s != null)
        {
            int k = i;
            k |= 0xffffffc0;
            k &= -4;
            a("Filter", F);
            a("V", U);
            a("R", J);
            a("P", new com.qoppa.b.n(k));
            a("Length", M);
            a(s, s1, k, s2);
        }
    }

    private void a(String s, String s1, int i, String s2)
    {
        byte abyte0[] = a(s.getBytes(), H);
        byte abyte1[] = a(s1.getBytes(), H);
        byte abyte2[] = new byte[32];
        byte abyte3[] = new byte[32];
        o o1 = new o();
        o1.a(abyte0);
        byte abyte4[] = o1._mthnew();
        t t1 = new t(abyte4, 0, 5);
        t1.a(abyte1, abyte2);
        a("O", ((k) (new com.qoppa.b.j(abyte2, 0))));
        o1 = new o();
        o1.a(abyte1);
        o1.a(abyte2);
        o1._mthif((byte)(i & 0xff));
        o1._mthif((byte)(i >> 8 & 0xff));
        o1._mthif((byte)(i >> 16 & 0xff));
        o1._mthif((byte)(i >> 24));
        o1.a(s2.getBytes());
        T = o1._mthnew();
        t1 = new t(T, 0, 5);
        t1.a(H, abyte3);
        a("U", ((k) (new com.qoppa.b.j(abyte3, 0))));
    }

    private byte[] a(byte abyte0[], byte abyte1[])
    {
        byte abyte2[] = new byte[32];
        if(abyte0.length >= 32)
        {
            System.arraycopy(abyte0, 0, abyte2, 0, 32);
        } else
        {
            System.arraycopy(abyte0, 0, abyte2, 0, abyte0.length);
            System.arraycopy(abyte1, 0, abyte2, abyte0.length, 32 - abyte0.length);
        }
        return abyte2;
    }

    public byte[] a(int i, byte abyte0[])
    {
        n n1 = new n(T, i, 0);
        j j1 = new j(n1.a());
        return j1.a(abyte0);
    }

    private static final String I = "Filter";
    private static final f F = new f("Standard");
    private static final String E = "V";
    private static final com.qoppa.b.n U = new com.qoppa.b.n(1);
    private static final String L = "R";
    private static final com.qoppa.b.n J = new com.qoppa.b.n(2);
    private static final String O = "O";
    private static final String G = "U";
    private static final String N = "P";
    private static final String K = "Length";
    private static final com.qoppa.b.n M = new com.qoppa.b.n(40);
    public static final int S = 4;
    public static final int Q = 8;
    public static final int R = 16;
    public static final int P = 32;
    private final byte H[] = {
        40, -65, 78, 94, 78, 117, -118, 65, 100, 0, 
        78, 86, -1, -6, 1, 8, 46, 46, 0, -74, 
        -48, 104, 62, -128, 47, 12, -87, -2, 100, 83, 
        105, 122
    };
    private byte T[];

}