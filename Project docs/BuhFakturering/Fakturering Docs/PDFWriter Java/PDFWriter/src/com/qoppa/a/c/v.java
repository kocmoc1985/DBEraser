// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:28
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.c;

import com.qoppa.a.a.a;
import com.qoppa.a.b.c;

// Referenced classes of package com.qoppa.a.c:
//            b, q, n, a, 
//            h, o, d, l

public class v
{

    public v()
    {
    }

    public static int a(d d1, q q1, byte abyte0[], int i)
        throws c
    {
        byte abyte1[] = new byte[8];
        byte byte1 = 0;
        b b1 = new b();
        Object obj = null;
        if(q1._flddo < 18 && q1._flddo != -1)
            throw new c("Segment too short");
        n n1 = new n(abyte0, i);
        byte byte0 = abyte0[i + 17];
        if((byte0 & 1) == 0);
        if((byte0 & 1) == 0)
        {
            byte1 = ((byte)((byte0 & 6) == 0 ? 8 : 2));
            if(18 + byte1 > q1._flddo && q1._flddo != -1)
                throw new c("Segment too short");
            System.arraycopy(abyte0, i + 18, abyte1, 0, byte1);
        }
        int k = i + 18 + byte1;
        b1._fldint = (byte0 & 1) != 0;
        b1._fldfor = (byte0 & 6) >> 1;
        b1._flddo = (byte0 & 8) >> 3 != 0;
        b1.a = false;
        System.arraycopy(abyte1, 0, b1._fldif, 0, byte1);
        a a1 = new a(n1._fldfor, n1._fldif);
        int j;
        if(b1._fldint)
        {
            int i1 = -1;
            if(q1._flddo != -1)
                i1 = q1._flddo - (k - i);
            j = com.qoppa.a.c.a.a(q1, b1, abyte0, k, i1, a1);
            if(q1._flddo == -1)
                throw new c("MMR Segment Length of -1");
        } else
        {
            int j1 = a(b1._fldfor);
            byte abyte2[] = new byte[j1];
            int k1 = abyte0.length - k;
            if(q1._flddo != -1)
                k1 = q1._flddo - (18 + byte1);
            h h1 = new h(abyte0, k, k1);
            o o1 = new o(h1);
            j = a(b1, o1, a1, abyte2);
            if(q1._flddo == -1)
                q1._flddo = (k - i) + o1.a();
        }
        d1._mthdo().a(a1, n1._flddo, n1.a, n1._fldint);
        return j;
    }

    public static int a(int i)
    {
        if(i == 0)
            return 0x10000;
        return i != 1 ? 1024 : 16384;
    }

    private static int _mthtry(b b1, o o1, a a1, byte abyte0[])
    {
        int i = a1._mthnew();
        int j = a1._mthtry();
        boolean flag1 = false;
        for(int j1 = 0; j1 < j; j1++)
        {
            flag1 ^= o1.a(abyte0, 39717);
            if(!flag1)
            {
                for(int i1 = 0; i1 < i; i1++)
                {
                    int k = a1.a(i1 - 1, j1);
                    k |= a1.a(i1 - 2, j1) << 1;
                    k |= a1.a(i1 - 3, j1) << 2;
                    k |= a1.a(i1 - 4, j1) << 3;
                    k |= a1.a(i1 + b1._fldif[0], j1 + b1._fldif[1]) << 4;
                    k |= a1.a(i1 + 2, j1 - 1) << 5;
                    k |= a1.a(i1 + 1, j1 - 1) << 6;
                    k |= a1.a(i1, j1 - 1) << 7;
                    k |= a1.a(i1 - 1, j1 - 1) << 8;
                    k |= a1.a(i1 - 2, j1 - 1) << 9;
                    k |= a1.a(i1 + b1._fldif[2], j1 + b1._fldif[3]) << 10;
                    k |= a1.a(i1 + b1._fldif[4], j1 + b1._fldif[5]) << 11;
                    k |= a1.a(i1 + 1, j1 - 2) << 12;
                    k |= a1.a(i1, j1 - 2) << 13;
                    k |= a1.a(i1 - 1, j1 - 2) << 14;
                    k |= a1.a(i1 + b1._fldif[6], j1 + b1._fldif[7]) << 15;
                    boolean flag = o1.a(abyte0, k);
                    a1._mthif(i1, j1, flag);
                }

            } else
            {
                a1._mthdo(j1 - 1, j1);
            }
        }

        return 0;
    }

    static int _mthgoto(b b1, o o1, a a1, byte abyte0[])
    {
        int i = a1._mthnew();
        int j = a1._mthtry();
        boolean flag1 = false;
        for(int j1 = 0; j1 < j; j1++)
        {
            flag1 ^= o1.a(abyte0, 1941);
            if(!flag1)
            {
                for(int i1 = 0; i1 < i; i1++)
                {
                    int k = a1.a(i1 - 1, j1);
                    k |= a1.a(i1 - 2, j1) << 1;
                    k |= a1.a(i1 - 3, j1) << 2;
                    k |= a1.a(i1 + b1._fldif[0], j1 + b1._fldif[1]) << 3;
                    k |= a1.a(i1 + 2, j1 - 1) << 4;
                    k |= a1.a(i1 + 1, j1 - 1) << 5;
                    k |= a1.a(i1, j1 - 1) << 6;
                    k |= a1.a(i1 - 1, j1 - 1) << 7;
                    k |= a1.a(i1 - 2, j1 - 1) << 8;
                    k |= a1.a(i1 + 2, j1 - 2) << 9;
                    k |= a1.a(i1 + 1, j1 - 2) << 10;
                    k |= a1.a(i1, j1 - 2) << 11;
                    k |= a1.a(i1 - 1, j1 - 2) << 12;
                    boolean flag = o1.a(abyte0, k);
                    a1._mthif(i1, j1, flag);
                }

            } else
            {
                a1._mthdo(j1 - 1, j1);
            }
        }

        return 0;
    }

    static int _mthcase(b b1, o o1, a a1, byte abyte0[])
    {
        int i = a1._mthnew();
        int j = a1._mthtry();
        boolean flag1 = false;
        for(int j1 = 0; j1 < j; j1++)
        {
            flag1 ^= o1.a(abyte0, 229);
            if(!flag1)
            {
                for(int i1 = 0; i1 < i; i1++)
                {
                    int k = a1.a(i1 - 1, j1);
                    k |= a1.a(i1 - 2, j1) << 1;
                    k |= a1.a(i1 + b1._fldif[0], j1 + b1._fldif[1]) << 2;
                    k |= a1.a(i1 + 1, j1 - 1) << 3;
                    k |= a1.a(i1, j1 - 1) << 4;
                    k |= a1.a(i1 - 1, j1 - 1) << 5;
                    k |= a1.a(i1 - 2, j1 - 1) << 6;
                    k |= a1.a(i1 + 1, j1 - 2) << 7;
                    k |= a1.a(i1, j1 - 2) << 8;
                    k |= a1.a(i1 - 1, j1 - 2) << 9;
                    boolean flag = o1.a(abyte0, k);
                    a1._mthif(i1, j1, flag);
                }

            } else
            {
                a1._mthdo(j1 - 1, j1);
            }
        }

        return 0;
    }

    static int _mthnew(b b1, o o1, a a1, byte abyte0[])
    {
        int i = a1._mthnew();
        int j = a1._mthtry();
        boolean flag1 = false;
        for(int j1 = 0; j1 < j; j1++)
        {
            flag1 ^= o1.a(abyte0, 405);
            if(!flag1)
            {
                for(int i1 = 0; i1 < i; i1++)
                {
                    int k = a1.a(i1 - 1, j1);
                    k |= a1.a(i1 - 2, j1) << 1;
                    k |= a1.a(i1 - 3, j1) << 2;
                    k |= a1.a(i1 - 4, j1) << 3;
                    k |= a1.a(i1 + b1._fldif[0], j1 + b1._fldif[1]) << 4;
                    k |= a1.a(i1 + 1, j1 - 1) << 5;
                    k |= a1.a(i1, j1 - 1) << 6;
                    k |= a1.a(i1 - 1, j1 - 1) << 7;
                    k |= a1.a(i1 - 2, j1 - 1) << 8;
                    k |= a1.a(i1 - 3, j1 - 1) << 9;
                    boolean flag = o1.a(abyte0, k);
                    a1._mthif(i1, j1, flag);
                }

            } else
            {
                a1._mthdo(j1 - 1, j1);
            }
        }

        return 0;
    }

    static int _mthfor(b b1, o o1, a a1, byte abyte0[])
    {
        switch(b1._fldfor)
        {
        case 0: // '\0'
            return _mthtry(b1, o1, a1, abyte0);

        case 1: // '\001'
            return _mthgoto(b1, o1, a1, abyte0);

        case 2: // '\002'
            return _mthcase(b1, o1, a1, abyte0);

        case 3: // '\003'
            return _mthnew(b1, o1, a1, abyte0);
        }
        return -1;
    }

    public static int a(b b1, o o1, a a1, byte abyte0[])
    {
        byte abyte1[] = b1._fldif;
        if(!b1._fldint && b1._flddo)
            return _mthfor(b1, o1, a1, abyte0);
        if(!b1._fldint && b1._fldfor == 0)
            if(abyte1[0] == 3 && abyte1[1] == -1 && abyte1[2] == -3 && abyte1[3] == -1 && abyte1[4] == 2 && abyte1[5] == -2 && abyte1[6] == -2 && abyte1[7] == -2)
                return _mthif(b1, o1, a1, abyte0);
            else
                return _mthelse(b1, o1, a1, abyte0);
        if(!b1._fldint && b1._fldfor == 1)
            return _mthint(b1, o1, a1, abyte0);
        if(!b1._fldint && b1._fldfor == 2)
            if(abyte1[0] == 3 && abyte1[1] == -1)
                return _mthchar(b1, o1, a1, abyte0);
            else
                return _mthdo(b1, o1, a1, abyte0);
        if(!b1._fldint && b1._fldfor == 3)
        {
            if(abyte1[0] == 2 && abyte1[1] == -1)
                return _mthbyte(b1, o1, a1, abyte0);
            else
                return _mthbyte(b1, o1, a1, abyte0);
        } else
        {
            return -1;
        }
    }

    private static int _mthif(b b1, o o1, a a1, byte abyte0[])
    {
        int i = a1._mthnew();
        int j = a1._mthtry();
        int j1 = a1._mthfor();
        byte abyte1[] = a1._fldnew;
        int k1 = 0;
        boolean flag = false;
        for(int i1 = 0; i1 < j; i1++)
        {
            int l2 = i + 7 & -8;
            if(b1._flddo)
            {
                int l1 = 39717;
                boolean flag1 = o1.a(abyte0, l1);
                flag ^= flag1;
            }
            if(flag)
            {
                System.arraycopy(a1._fldnew, (i1 - 1) * a1._fldelse, a1._fldnew, i1 * a1._fldelse, a1._fldelse);
            } else
            {
                int j2 = i1 < 1 ? 0 : abyte1[k1 - j1] & 0xff;
                int k2 = i1 < 2 ? 0 : (abyte1[k1 - (j1 << 1)] & 0xff) << 6;
                int i2 = j2 & 0x7f0 | k2 & 0xf800;
                for(int k = 0; k < l2; k += 8)
                {
                    byte byte0 = 0;
                    int j3 = i - k <= 8 ? i - k : 8;
                    if(i1 >= 1)
                        j2 = j2 << 8 | (k + 8 >= i ? 0 : abyte1[(k1 - j1) + (k >> 3) + 1] & 0xff);
                    if(i1 >= 2)
                        k2 = k2 << 8 | (k + 8 >= i ? 0 : (abyte1[(k1 - (j1 << 1)) + (k >> 3) + 1] & 0xff) << 6);
                    for(int i3 = 0; i3 < j3; i3++)
                    {
                        boolean flag2 = o1.a(abyte0, i2);
                        int k3 = flag2 ? 1 : 0;
                        byte0 |= k3 << 7 - i3;
                        i2 = (i2 & 0x7bf7) << 1 | k3 | j2 >> 7 - i3 & 0x10 | k2 >> 7 - i3 & 0x800;
                    }

                    abyte1[k1 + (k >> 3)] = byte0;
                }

            }
            k1 += j1;
        }

        return 0;
    }

    private static int _mthelse(b b1, o o1, a a1, byte abyte0[])
    {
        int i = a1._mthnew();
        int j = a1._mthtry();
        boolean flag1 = false;
        for(int k1 = 0; k1 < j; k1++)
        {
            if(b1._flddo)
            {
                int k = 39717;
                boolean flag2 = o1.a(abyte0, k);
                flag1 ^= flag2;
            }
            if(flag1)
            {
                System.arraycopy(a1._fldnew, (k1 - 1) * a1._fldelse, a1._fldnew, k1 * a1._fldelse, a1._fldelse);
            } else
            {
                for(int j1 = 0; j1 < i; j1++)
                {
                    int i1 = 0;
                    i1 |= a1.a(j1 - 1, k1) << 0;
                    i1 |= a1.a(j1 - 2, k1) << 1;
                    i1 |= a1.a(j1 - 3, k1) << 2;
                    i1 |= a1.a(j1 - 4, k1) << 3;
                    i1 |= a1.a(j1 + b1._fldif[0], k1 + b1._fldif[1]) << 4;
                    i1 |= a1.a(j1 + 2, k1 - 1) << 5;
                    i1 |= a1.a(j1 + 1, k1 - 1) << 6;
                    i1 |= a1.a(j1 + 0, k1 - 1) << 7;
                    i1 |= a1.a(j1 - 1, k1 - 1) << 8;
                    i1 |= a1.a(j1 - 2, k1 - 1) << 9;
                    i1 |= a1.a(j1 + b1._fldif[2], k1 + b1._fldif[3]) << 10;
                    i1 |= a1.a(j1 + b1._fldif[4], k1 + b1._fldif[5]) << 11;
                    i1 |= a1.a(j1 + 1, k1 - 2) << 12;
                    i1 |= a1.a(j1 + 0, k1 - 2) << 13;
                    i1 |= a1.a(j1 - 1, k1 - 2) << 14;
                    i1 |= a1.a(j1 + b1._fldif[6], k1 + b1._fldif[7]) << 15;
                    boolean flag = o1.a(abyte0, i1);
                    if(flag)
                        a1._mthif(j1, k1, flag);
                }

            }
        }

        return 0;
    }

    private static int _mthint(b b1, o o1, a a1, byte abyte0[])
    {
        int i = a1._mthnew();
        int j = a1._mthtry();
        int j1 = a1._mthfor();
        byte abyte1[] = a1._fldnew;
        int k1 = 0;
        boolean flag = false;
        for(int i1 = 0; i1 < j; i1++)
        {
            int k2 = i + 7 & -8;
            if(b1._flddo)
            {
                char c1 = '\u0795';
                boolean flag1 = o1.a(abyte0, c1);
                flag ^= flag1;
            }
            if(flag)
            {
                System.arraycopy(a1._fldnew, (i1 - 1) * a1._fldelse, a1._fldnew, i1 * a1._fldelse, a1._fldelse);
            } else
            {
                int i2 = i1 < 1 ? 0 : abyte1[k1 - j1] & 0xff;
                int j2 = i1 < 2 ? 0 : (abyte1[k1 - (j1 << 1)] & 0xff) << 5;
                int l1 = i2 >> 1 & 0x1f8 | j2 >> 1 & 0x1e00;
                for(int k = 0; k < k2; k += 8)
                {
                    byte byte0 = 0;
                    int i3 = i - k <= 8 ? i - k : 8;
                    if(i1 >= 1)
                        i2 = i2 << 8 | (k + 8 >= i ? 0 : abyte1[(k1 - j1) + (k >> 3) + 1] & 0xff);
                    if(i1 >= 2)
                        j2 = j2 << 8 | (k + 8 >= i ? 0 : (abyte1[(k1 - (j1 << 1)) + (k >> 3) + 1] & 0xff) << 5);
                    for(int l2 = 0; l2 < i3; l2++)
                    {
                        boolean flag2 = o1.a(abyte0, l1);
                        int j3 = flag2 ? 1 : 0;
                        byte0 |= j3 << 7 - l2;
                        l1 = (l1 & 0xefb) << 1 | j3 | i2 >> 8 - l2 & 8 | j2 >> 8 - l2 & 0x200;
                    }

                    abyte1[k1 + (k >> 3)] = byte0;
                }

            }
            k1 += j1;
        }

        return 0;
    }

    private static int _mthdo(b b1, o o1, a a1, byte abyte0[])
    {
        int i = a1._mthnew();
        int j = a1._mthtry();
        int j1 = a1._mthfor();
        byte abyte1[] = a1._fldnew;
        int k1 = 0;
        boolean flag = false;
        for(int i1 = 0; i1 < j; i1++)
        {
            int k2 = i + 7 & -8;
            if(b1._flddo)
            {
                char c1 = '\345';
                boolean flag1 = o1.a(abyte0, c1);
                flag ^= flag1;
            }
            if(flag)
            {
                System.arraycopy(a1._fldnew, (i1 - 1) * a1._fldelse, a1._fldnew, i1 * a1._fldelse, a1._fldelse);
            } else
            {
                int i2 = i1 < 1 ? 0 : abyte1[k1 - j1] & 0xff;
                int j2 = i1 < 2 ? 0 : (abyte1[k1 - (j1 << 1)] & 0xff) << 4;
                int l1 = i2 >> 3 & 0x7c | j2 >> 3 & 0x380;
                for(int k = 0; k < k2; k += 8)
                {
                    byte byte0 = 0;
                    int i3 = i - k <= 8 ? i - k : 8;
                    if(i1 >= 1)
                        i2 = i2 << 8 | (k + 8 >= i ? 0 : abyte1[(k1 - j1) + (k >> 3) + 1] & 0xff);
                    if(i1 >= 2)
                        j2 = j2 << 8 | (k + 8 >= i ? 0 : (abyte1[(k1 - (j1 << 1)) + (k >> 3) + 1] & 0xff) << 4);
                    for(int l2 = 0; l2 < i3; l2++)
                    {
                        boolean flag2 = o1.a(abyte0, l1);
                        int j3 = flag2 ? 1 : 0;
                        byte0 |= j3 << 7 - l2;
                        l1 = (l1 & 0x1bd) << 1 | j3 | i2 >> 10 - l2 & 4 | j2 >> 10 - l2 & 0x80;
                    }

                    abyte1[k1 + (k >> 3)] = byte0;
                }

            }
            k1 += j1;
        }

        return 0;
    }

    private static int _mthchar(b b1, o o1, a a1, byte abyte0[])
    {
        int i = a1._mthnew();
        int j = a1._mthtry();
        int k = a1._mthfor();
        byte abyte1[] = a1._fldnew;
        int k1 = 0;
        boolean flag = false;
        for(int j1 = 0; j1 < j; j1++)
        {
            int k2 = i + 7 & -8;
            if(b1._flddo)
            {
                char c1 = '\345';
                boolean flag1 = o1.a(abyte0, c1);
                flag ^= flag1;
            }
            if(flag)
            {
                System.arraycopy(a1._fldnew, (j1 - 1) * a1._fldelse, a1._fldnew, j1 * a1._fldelse, a1._fldelse);
            } else
            {
                int i2 = j1 < 1 ? 0 : abyte1[k1 - k] & 0xff;
                int j2 = j1 < 2 ? 0 : (abyte1[k1 - (k << 1)] & 0xff) << 4;
                int l1 = i2 >> 3 & 0x78 | i2 >> 2 & 4 | j2 >> 3 & 0x380;
                for(int i1 = 0; i1 < k2; i1 += 8)
                {
                    byte byte0 = 0;
                    int i3 = i - i1 <= 8 ? i - i1 : 8;
                    if(j1 >= 1)
                        i2 = i2 << 8 | (i1 + 8 >= i ? 0 : abyte1[(k1 - k) + (i1 >> 3) + 1] & 0xff);
                    if(j1 >= 2)
                        j2 = j2 << 8 | (i1 + 8 >= i ? 0 : (abyte1[(k1 - (k << 1)) + (i1 >> 3) + 1] & 0xff) << 4);
                    for(int l2 = 0; l2 < i3; l2++)
                    {
                        boolean flag2 = o1.a(abyte0, l1);
                        int j3 = flag2 ? 1 : 0;
                        byte0 |= j3 << 7 - l2;
                        l1 = (l1 & 0x1b9) << 1 | j3 | i2 >> 10 - l2 & 8 | i2 >> 9 - l2 & 4 | j2 >> 10 - l2 & 0x80;
                    }

                    abyte1[k1 + (i1 >> 3)] = byte0;
                }

            }
            k1 += k;
        }

        return 0;
    }

    private static int _mthbyte(b b1, o o1, a a1, byte abyte0[])
    {
        int i = a1._mthnew();
        int j = a1._mthtry();
        boolean flag = false;
        for(int j1 = 0; j1 < j; j1++)
        {
            if(b1._flddo)
            {
                char c1 = '\u0195';
                boolean flag1 = o1.a(abyte0, c1);
                flag ^= flag1;
            }
            if(flag)
            {
                System.arraycopy(a1._fldnew, (j1 - 1) * a1._fldelse, a1._fldnew, j1 * a1._fldelse, a1._fldelse);
            } else
            {
                for(int i1 = 0; i1 < i; i1++)
                {
                    int k = 0;
                    k |= a1.a(i1 - 1, j1) << 0;
                    k |= a1.a(i1 - 2, j1) << 1;
                    k |= a1.a(i1 - 3, j1) << 2;
                    k |= a1.a(i1 - 4, j1) << 3;
                    k |= a1.a(i1 + b1._fldif[0], j1 + b1._fldif[1]) << 4;
                    k |= a1.a(i1 + 1, j1 - 1) << 5;
                    k |= a1.a(i1 + 0, j1 - 1) << 6;
                    k |= a1.a(i1 - 1, j1 - 1) << 7;
                    k |= a1.a(i1 - 2, j1 - 1) << 8;
                    k |= a1.a(i1 - 3, j1 - 1) << 9;
                    boolean flag2 = o1.a(abyte0, k);
                    if(flag2)
                        a1._mthif(i1, j1, flag2);
                }

            }
        }

        return 0;
    }
}