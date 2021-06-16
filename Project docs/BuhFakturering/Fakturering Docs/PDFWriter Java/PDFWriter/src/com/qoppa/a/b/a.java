// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:25
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.b;

import com.qoppa.a.a.c;

// Referenced classes of package com.qoppa.a.b:
//            e

public class com.qoppa.a.b.a
{
    private static class a
    {

        char a;
        byte _fldif;
        byte _flddo;

        public a(char c1, byte byte0, byte byte1)
        {
            a = c1;
            _fldif = byte0;
            _flddo = byte1;
        }
    }


    public com.qoppa.a.b.a()
    {
    }

    public static void a(e e1, com.qoppa.a.a.a a1, boolean flag)
    {
        boolean flag1 = false;
        boolean flag2 = false;
        for(int i = 0; i < a1._mthtry(); i++)
        {
            boolean flag3 = false;
            long l1 = 0L;
            long l2 = 0L;
            long l3 = 0L;
            if(i >= 2)
                l1 = c.a(a1._fldnew, (i - 2) * a1._fldelse);
            if(i >= 1)
            {
                l2 = c.a(a1._fldnew, (i - 1) * a1._fldelse);
                if(flag)
                    if(c.a(a1._fldnew, i * a1._fldelse, a1._fldnew, (i - 1) * a1._fldelse, a1._fldelse) == 0)
                    {
                        flag2 = flag1 ^ true;
                        flag1 = true;
                    } else
                    {
                        flag2 = flag1;
                        flag1 = false;
                    }
            }
            if(flag)
            {
                a(e1, 39717, (byte)c.a(flag2));
                if(flag1)
                    continue;
            }
            l3 = c.a(a1._fldnew, i * a1._fldelse);
            int k = (char)(int)(l1 >> 29 & 7L);
            int l = (char)(int)(l2 >> 28 & 15L);
            l1 = c.a(l1, 3);
            l2 = c.a(l2, 4);
            char c1 = '\0';
            for(int j = 0; j < a1._mthnew(); j++)
            {
                int i1 = k << 11 | l << 4 | c1;
                byte byte0 = (byte)(int)((l3 & 0xffffffff80000000L) >> 31);
                a(e1, i1, byte0);
                k = (char)(k << 1);
                l = (char)(l << 1);
                c1 <<= '\001';
                k = (char)(int)((long)k | (l1 & 0xffffffff80000000L) >> 31);
                l = (char)(int)((long)l | (l2 & 0xffffffff80000000L) >> 31);
                c1 |= byte0;
                int j1 = j % 32;
                if(j1 == 28 && i >= 2)
                {
                    int k1 = j / 32 + 1;
                    l1 = a1._mthfor(i - 2, k1);
                } else
                {
                    l1 = c.a(l1, 1);
                }
                if(j1 == 27 && i >= 1)
                {
                    int i2 = j / 32 + 1;
                    l2 = a1._mthfor(i - 1, i2);
                } else
                {
                    l2 = c.a(l2, 1);
                }
                if(j1 == 31)
                {
                    int j2 = j / 32 + 1;
                    l3 = a1._mthfor(i, j2);
                } else
                {
                    l3 = c.a(l3, 1);
                }
                k = (char)(k & 0x1f);
                l = (char)(l & 0x7f);
                c1 &= '\017';
            }

        }

    }

    private static void a(e e1, int i, byte byte0)
    {
        int j = e1._fldif[i] & 0xff;
        byte byte1 = (byte)(j <= 46 ? 0 : 1);
        int k = a[j].a;
        if(byte0 == byte1)
        {
            e1._fldchar -= k;
            if((e1._fldchar & 0x8000) == 0)
            {
                if(e1._fldchar < k)
                    e1._fldchar = k;
                else
                    e1._fldbyte += k;
                e1._fldif[i] = a[j]._fldif;
                _mthif(e1);
            } else
            {
                e1._fldbyte += k;
            }
            return;
        }
        e1._fldchar -= k;
        if(e1._fldchar < k)
            e1._fldbyte += k;
        else
            e1._fldchar = k;
        e1._fldif[i] = a[j]._flddo;
        _mthif(e1);
    }

    private static void _mthif(e e1)
    {
        do
        {
            e1._fldchar <<= '\001';
            e1._fldbyte = e1._fldbyte << 1 & -1L;
            e1._fldnew--;
            if(e1._fldnew == 0)
                _mthdo(e1);
        } while((e1._fldchar & 0x8000) == 0);
    }

    public static void _mthint(e e1)
    {
        long l = e1._fldbyte + (long)e1._fldchar;
        e1._fldbyte |= 65535L;
        if(e1._fldbyte >= l)
            e1._fldbyte -= 32768L;
        e1._fldbyte = e1._fldbyte << e1._fldnew & -1L;
        _mthdo(e1);
        e1._fldbyte = e1._fldbyte << e1._fldnew & -1L;
        _mthdo(e1);
        e1._mthdo();
        if(e1._fldcase != -1)
        {
            e1._fldcase = -1;
            e1._mthdo();
        }
        e1._fldcase = -84;
        e1._mthdo();
    }

    private static void _mthdo(e e1)
    {
        if(e1._fldcase == -1)
        {
            _mthfor(e1);
            return;
        }
        if(e1._fldbyte < 0x8000000L)
        {
            a(e1);
            return;
        }
        e1._fldcase++;
        if(e1._fldcase != -1)
        {
            a(e1);
            return;
        } else
        {
            e1._fldbyte &= 0x7ffffffL;
            _mthfor(e1);
            return;
        }
    }

    private static void _mthfor(e e1)
    {
        if(e1.a >= 0)
            e1._mthdo();
        e1._fldcase = (byte)(int)(e1._fldbyte >> 20);
        e1.a++;
        e1._fldbyte &= 0xfffffL;
        e1._fldnew = 7;
    }

    private static void a(e e1)
    {
        if(e1.a >= 0)
            e1._mthdo();
        e1._fldcase = (byte)(int)(e1._fldbyte >> 19);
        e1.a++;
        e1._fldbyte &= 0x7ffffL;
        e1._fldnew = 8;
    }

    private static final int _fldif = 39717;
    private static a a[] = {
        new a('\u5601', (byte)1, (byte)47), new a('\u3401', (byte)2, (byte)6), new a('\u1801', (byte)3, (byte)9), new a('\u0AC1', (byte)4, (byte)12), new a('\u0521', (byte)5, (byte)29), new a('\u0221', (byte)38, (byte)33), new a('\u5601', (byte)7, (byte)52), new a('\u5401', (byte)8, (byte)14), new a('\u4801', (byte)9, (byte)14), new a('\u3801', (byte)10, (byte)14), 
        new a('\u3001', (byte)11, (byte)17), new a('\u2401', (byte)12, (byte)18), new a('\u1C01', (byte)13, (byte)20), new a('\u1601', (byte)29, (byte)21), new a('\u5601', (byte)15, (byte)60), new a('\u5401', (byte)16, (byte)14), new a('\u5101', (byte)17, (byte)15), new a('\u4801', (byte)18, (byte)16), new a('\u3801', (byte)19, (byte)17), new a('\u3401', (byte)20, (byte)18), 
        new a('\u3001', (byte)21, (byte)19), new a('\u2801', (byte)22, (byte)19), new a('\u2401', (byte)23, (byte)20), new a('\u2201', (byte)24, (byte)21), new a('\u1C01', (byte)25, (byte)22), new a('\u1801', (byte)26, (byte)23), new a('\u1601', (byte)27, (byte)24), new a('\u1401', (byte)28, (byte)25), new a('\u1201', (byte)29, (byte)26), new a('\u1101', (byte)30, (byte)27), 
        new a('\u0AC1', (byte)31, (byte)28), new a('\u09C1', (byte)32, (byte)29), new a('\u08A1', (byte)33, (byte)30), new a('\u0521', (byte)34, (byte)31), new a('\u0441', (byte)35, (byte)32), new a('\u02A1', (byte)36, (byte)33), new a('\u0221', (byte)37, (byte)34), new a('\u0141', (byte)38, (byte)35), new a('\u0111', (byte)39, (byte)36), new a('\205', (byte)40, (byte)37), 
        new a('I', (byte)41, (byte)38), new a('%', (byte)42, (byte)39), new a('\025', (byte)43, (byte)40), new a('\t', (byte)44, (byte)41), new a('\005', (byte)45, (byte)42), new a('\001', (byte)45, (byte)43), new a('\u5601', (byte)47, (byte)1), new a('\u3401', (byte)48, (byte)52), new a('\u1801', (byte)49, (byte)55), new a('\u0AC1', (byte)50, (byte)58), 
        new a('\u0521', (byte)51, (byte)75), new a('\u0221', (byte)84, (byte)79), new a('\u5601', (byte)53, (byte)6), new a('\u5401', (byte)54, (byte)60), new a('\u4801', (byte)55, (byte)60), new a('\u3801', (byte)56, (byte)60), new a('\u3001', (byte)57, (byte)63), new a('\u2401', (byte)58, (byte)64), new a('\u1C01', (byte)59, (byte)66), new a('\u1601', (byte)75, (byte)67), 
        new a('\u5601', (byte)61, (byte)14), new a('\u5401', (byte)62, (byte)60), new a('\u5101', (byte)63, (byte)61), new a('\u4801', (byte)64, (byte)62), new a('\u3801', (byte)65, (byte)63), new a('\u3401', (byte)66, (byte)64), new a('\u3001', (byte)67, (byte)65), new a('\u2801', (byte)68, (byte)65), new a('\u2401', (byte)69, (byte)66), new a('\u2201', (byte)70, (byte)67), 
        new a('\u1C01', (byte)71, (byte)68), new a('\u1801', (byte)72, (byte)69), new a('\u1601', (byte)73, (byte)70), new a('\u1401', (byte)74, (byte)71), new a('\u1201', (byte)75, (byte)72), new a('\u1101', (byte)76, (byte)73), new a('\u0AC1', (byte)77, (byte)74), new a('\u09C1', (byte)78, (byte)75), new a('\u08A1', (byte)79, (byte)76), new a('\u0521', (byte)80, (byte)77), 
        new a('\u0441', (byte)81, (byte)78), new a('\u02A1', (byte)82, (byte)79), new a('\u0221', (byte)83, (byte)80), new a('\u0141', (byte)84, (byte)81), new a('\u0111', (byte)85, (byte)82), new a('\205', (byte)86, (byte)83), new a('I', (byte)87, (byte)84), new a('%', (byte)88, (byte)85), new a('\025', (byte)89, (byte)86), new a('\t', (byte)90, (byte)87), 
        new a('\005', (byte)91, (byte)88), new a('\001', (byte)91, (byte)89)
    };

}