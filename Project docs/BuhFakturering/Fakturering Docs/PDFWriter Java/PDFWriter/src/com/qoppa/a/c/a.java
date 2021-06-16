// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:26
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.c;


// Referenced classes of package com.qoppa.a.c:
//            q, b

public class com.qoppa.a.c.a
{
    private static class a
    {

        private void a(byte abyte0[], byte abyte1[])
        {
            int i = -1;
            boolean flag = false;
            do
            {
                if(i >= _fldnew)
                    break;
                if(_fldint >> 29 == 1L)
                {
                    a(3);
                    if(i == -1)
                        i = 0;
                    if(!flag)
                    {
                        int k3 = a(com.qoppa.a.c.a.a, 8);
                        int i4 = a(com.qoppa.a.c.a._fldfor, 7);
                        int j = i + k3;
                        int l = j + i4;
                        if(j >= _fldnew)
                            break;
                        if(l > _fldnew)
                            l = _fldnew;
                        a(abyte0, j, l);
                        i = l;
                    } else
                    {
                        int j4 = a(com.qoppa.a.c.a._fldfor, 7);
                        int l3 = a(com.qoppa.a.c.a.a, 8);
                        int k = i + j4;
                        int i1 = k + l3;
                        if(k > _fldnew)
                            k = _fldnew;
                        if(i1 > _fldnew)
                            i1 = _fldnew;
                        a(abyte0, i, k);
                        i = i1;
                    }
                    continue;
                }
                if(_fldint >> 28 == 1L)
                {
                    a(4);
                    int j1 = a(abyte1, i, _fldnew, !flag);
                    int j3 = _mthif(abyte1, j1, _fldnew);
                    if(flag)
                        a(abyte0, i, j3);
                    i = j3;
                    continue;
                }
                if(_fldint >> 31 == 1L)
                {
                    a(1);
                    int k1 = a(abyte1, i, _fldnew, !flag);
                    if(flag)
                        a(abyte0, i, k1);
                    i = k1;
                    flag = !flag;
                    continue;
                }
                if(_fldint >> 29 == 3L)
                {
                    a(3);
                    int l1 = a(abyte1, i, _fldnew, !flag);
                    if(l1 + 1 > _fldnew)
                        break;
                    if(flag)
                        a(abyte0, i, l1 + 1);
                    i = l1 + 1;
                    flag = !flag;
                    continue;
                }
                if(_fldint >> 26 == 3L)
                {
                    a(6);
                    int i2 = a(abyte1, i, _fldnew, !flag);
                    if(i2 + 2 > _fldnew)
                        break;
                    if(flag)
                        a(abyte0, i, i2 + 2);
                    i = i2 + 2;
                    flag = !flag;
                    continue;
                }
                if(_fldint >> 25 == 3L)
                {
                    a(7);
                    int j2 = a(abyte1, i, _fldnew, !flag);
                    if(j2 + 3 > _fldnew)
                        break;
                    if(flag)
                        a(abyte0, i, j2 + 3);
                    i = j2 + 3;
                    flag = !flag;
                    continue;
                }
                if(_fldint >> 29 == 2L)
                {
                    a(3);
                    int k2 = a(abyte1, i, _fldnew, !flag);
                    if(k2 - 1 < 0)
                        break;
                    if(flag)
                        a(abyte0, i, k2 - 1);
                    i = k2 - 1;
                    flag = !flag;
                    continue;
                }
                if(_fldint >> 26 == 2L)
                {
                    a(6);
                    int l2 = a(abyte1, i, _fldnew, !flag);
                    if(l2 - 2 < 0)
                        break;
                    if(flag)
                        a(abyte0, i, l2 - 2);
                    i = l2 - 2;
                    flag = !flag;
                    continue;
                }
                if(_fldint >> 25 != 2L)
                    break;
                a(7);
                int i3 = a(abyte1, i, _fldnew, !flag);
                if(i3 - 3 < 0)
                    break;
                if(flag)
                    a(abyte0, i, i3 - 3);
                i = i3 - 3;
                flag = !flag;
            } while(true);
        }

        private void a(int i)
        {
            _fldint = _fldint << i & 0xffffffffL;
            for(_fldtry += i; _fldtry >= 8;)
            {
                _fldtry -= 8;
                if((a - _flddo) + 4 < _fldif)
                    _fldint |= (_fldfor[a + 4] & 0xff) << _fldtry;
                a++;
            }

        }

        private int _mthif(b ab[], int i)
        {
            long l = _fldint;
            int j = (int)(l >> 32 - i);
            int i1 = ab[j]._fldif;
            int j1 = ab[j].a;
            if(j1 > i)
            {
                int k1 = (1 << 32 - i) - 1;
                int k = (int)((long)i1 + ((l & (long)k1) >> 32 - j1));
                i1 = ab[k]._fldif;
                j1 = i + ab[k].a;
            }
            a(j1);
            return i1;
        }

        private int a(b ab[], int i)
        {
            int j = 0;
            int k;
            do
            {
                k = _mthif(ab, i);
                j += k;
            } while(k >= 64);
            return j;
        }

        private void a(byte abyte0[], int i, int j)
        {
            int k = i >> 3;
            int l = j >> 3;
            int i1 = i & 7;
            int j1 = j & 7;
            if(k == l)
            {
                abyte0[k] |= com.qoppa.a.c.a._fldif[i1] & com.qoppa.a.c.a._flddo[j1];
            } else
            {
                abyte0[k] |= com.qoppa.a.c.a._fldif[i1];
                for(int k1 = k + 1; k1 < l; k1++)
                    abyte0[k1] = -1;

                if(l < abyte0.length)
                    abyte0[l] |= com.qoppa.a.c.a._flddo[j1];
            }
        }

        private int a(byte abyte0[], int i)
        {
            return (abyte0[i >> 3] & 0xff) >> 7 - (i & 7) & 1;
        }

        private int _mthif(byte abyte0[], int i, int j)
        {
            if(abyte0 == null)
                return j;
            int k;
            if(i == -1)
            {
                k = 0;
                i = 0;
            } else
            {
                k = a(abyte0, i);
                i++;
            }
            for(; i < j; i++)
            {
                int l = a(abyte0, i);
                if(k != l)
                    break;
            }

            return i;
        }

        private int a(byte abyte0[], int i, int j, boolean flag)
        {
            int k = 0;
            if(flag)
                k = 1;
            if(abyte0 == null)
                return j;
            i = _mthif(abyte0, i, j);
            if(i < j && a(abyte0, i) != k)
                i = _mthif(abyte0, i, j);
            return i;
        }

        int _fldnew;
        int _fldif;
        byte _fldfor[];
        int a;
        int _flddo;
        int _fldtry;
        long _fldint;


        a(int i, int j, byte abyte0[], int k, int l)
        {
            _fldnew = i;
            _fldfor = abyte0;
            _fldif = l;
            a = k;
            _flddo = k;
            _fldtry = 0;
            _fldint = 0L;
            for(int i1 = 0; i1 < l && i1 < 4; i1++)
                _fldint |= (abyte0[i1 + k] & 0xff) << (3 - i1 << 3);

        }
    }

    private static class b
    {

        short _fldif;
        short a;

        public b(int i, int j)
        {
            _fldif = (short)i;
            a = (short)j;
        }
    }


    public com.qoppa.a.c.a()
    {
    }

    public static int a(q q, com.qoppa.a.c.b b1, byte abyte0[], int i, int j, com.qoppa.a.a.a a1)
    {
        a a2 = new a(a1._mthnew(), a1._mthtry(), abyte0, i, j);
        byte abyte1[] = null;
        for(int k = 0; k < a1._mthtry(); k++)
        {
            byte abyte2[] = new byte[a1._mthfor()];
            a2.a(abyte2, abyte1);
            System.arraycopy(abyte2, 0, a1._fldnew, k * a1._mthfor(), a1._mthfor());
            abyte1 = abyte2;
        }

        return 0;
    }

    private static b a[] = {
        new b(256, 12), new b(272, 12), new b(29, 8), new b(30, 8), new b(45, 8), new b(46, 8), new b(22, 7), new b(22, 7), new b(23, 7), new b(23, 7), 
        new b(47, 8), new b(48, 8), new b(13, 6), new b(13, 6), new b(13, 6), new b(13, 6), new b(20, 7), new b(20, 7), new b(33, 8), new b(34, 8), 
        new b(35, 8), new b(36, 8), new b(37, 8), new b(38, 8), new b(19, 7), new b(19, 7), new b(31, 8), new b(32, 8), new b(1, 6), new b(1, 6), 
        new b(1, 6), new b(1, 6), new b(12, 6), new b(12, 6), new b(12, 6), new b(12, 6), new b(53, 8), new b(54, 8), new b(26, 7), new b(26, 7), 
        new b(39, 8), new b(40, 8), new b(41, 8), new b(42, 8), new b(43, 8), new b(44, 8), new b(21, 7), new b(21, 7), new b(28, 7), new b(28, 7), 
        new b(61, 8), new b(62, 8), new b(63, 8), new b(0, 8), new b(320, 8), new b(384, 8), new b(10, 5), new b(10, 5), new b(10, 5), new b(10, 5), 
        new b(10, 5), new b(10, 5), new b(10, 5), new b(10, 5), new b(11, 5), new b(11, 5), new b(11, 5), new b(11, 5), new b(11, 5), new b(11, 5), 
        new b(11, 5), new b(11, 5), new b(27, 7), new b(27, 7), new b(59, 8), new b(60, 8), new b(288, 9), new b(290, 9), new b(18, 7), new b(18, 7), 
        new b(24, 7), new b(24, 7), new b(49, 8), new b(50, 8), new b(51, 8), new b(52, 8), new b(25, 7), new b(25, 7), new b(55, 8), new b(56, 8), 
        new b(57, 8), new b(58, 8), new b(192, 6), new b(192, 6), new b(192, 6), new b(192, 6), new b(1664, 6), new b(1664, 6), new b(1664, 6), new b(1664, 6), 
        new b(448, 8), new b(512, 8), new b(292, 9), new b(640, 8), new b(576, 8), new b(294, 9), new b(296, 9), new b(298, 9), new b(300, 9), new b(302, 9), 
        new b(256, 7), new b(256, 7), new b(2, 4), new b(2, 4), new b(2, 4), new b(2, 4), new b(2, 4), new b(2, 4), new b(2, 4), new b(2, 4), 
        new b(2, 4), new b(2, 4), new b(2, 4), new b(2, 4), new b(2, 4), new b(2, 4), new b(2, 4), new b(2, 4), new b(3, 4), new b(3, 4), 
        new b(3, 4), new b(3, 4), new b(3, 4), new b(3, 4), new b(3, 4), new b(3, 4), new b(3, 4), new b(3, 4), new b(3, 4), new b(3, 4), 
        new b(3, 4), new b(3, 4), new b(3, 4), new b(3, 4), new b(128, 5), new b(128, 5), new b(128, 5), new b(128, 5), new b(128, 5), new b(128, 5), 
        new b(128, 5), new b(128, 5), new b(8, 5), new b(8, 5), new b(8, 5), new b(8, 5), new b(8, 5), new b(8, 5), new b(8, 5), new b(8, 5), 
        new b(9, 5), new b(9, 5), new b(9, 5), new b(9, 5), new b(9, 5), new b(9, 5), new b(9, 5), new b(9, 5), new b(16, 6), new b(16, 6), 
        new b(16, 6), new b(16, 6), new b(17, 6), new b(17, 6), new b(17, 6), new b(17, 6), new b(4, 4), new b(4, 4), new b(4, 4), new b(4, 4), 
        new b(4, 4), new b(4, 4), new b(4, 4), new b(4, 4), new b(4, 4), new b(4, 4), new b(4, 4), new b(4, 4), new b(4, 4), new b(4, 4), 
        new b(4, 4), new b(4, 4), new b(5, 4), new b(5, 4), new b(5, 4), new b(5, 4), new b(5, 4), new b(5, 4), new b(5, 4), new b(5, 4), 
        new b(5, 4), new b(5, 4), new b(5, 4), new b(5, 4), new b(5, 4), new b(5, 4), new b(5, 4), new b(5, 4), new b(14, 6), new b(14, 6), 
        new b(14, 6), new b(14, 6), new b(15, 6), new b(15, 6), new b(15, 6), new b(15, 6), new b(64, 5), new b(64, 5), new b(64, 5), new b(64, 5), 
        new b(64, 5), new b(64, 5), new b(64, 5), new b(64, 5), new b(6, 4), new b(6, 4), new b(6, 4), new b(6, 4), new b(6, 4), new b(6, 4), 
        new b(6, 4), new b(6, 4), new b(6, 4), new b(6, 4), new b(6, 4), new b(6, 4), new b(6, 4), new b(6, 4), new b(6, 4), new b(6, 4), 
        new b(7, 4), new b(7, 4), new b(7, 4), new b(7, 4), new b(7, 4), new b(7, 4), new b(7, 4), new b(7, 4), new b(7, 4), new b(7, 4), 
        new b(7, 4), new b(7, 4), new b(7, 4), new b(7, 4), new b(7, 4), new b(7, 4), new b(-2, 3), new b(-2, 3), new b(-1, 0), new b(-1, 0), 
        new b(-1, 0), new b(-1, 0), new b(-1, 0), new b(-1, 0), new b(-1, 0), new b(-1, 0), new b(-1, 0), new b(-1, 0), new b(-1, 0), new b(-1, 0), 
        new b(-1, 0), new b(-3, 4), new b(1792, 3), new b(1792, 3), new b(1984, 4), new b(2048, 4), new b(2112, 4), new b(2176, 4), new b(2240, 4), new b(2304, 4), 
        new b(1856, 3), new b(1856, 3), new b(1920, 3), new b(1920, 3), new b(2368, 4), new b(2432, 4), new b(2496, 4), new b(2560, 4), new b(1472, 1), new b(1536, 1), 
        new b(1600, 1), new b(1728, 1), new b(704, 1), new b(768, 1), new b(832, 1), new b(896, 1), new b(960, 1), new b(1024, 1), new b(1088, 1), new b(1152, 1), 
        new b(1216, 1), new b(1280, 1), new b(1344, 1), new b(1408, 1)
    };
    private static b _fldfor[] = {
        new b(128, 12), new b(160, 13), new b(224, 12), new b(256, 12), new b(10, 7), new b(11, 7), new b(288, 12), new b(12, 7), new b(9, 6), new b(9, 6), 
        new b(8, 6), new b(8, 6), new b(7, 5), new b(7, 5), new b(7, 5), new b(7, 5), new b(6, 4), new b(6, 4), new b(6, 4), new b(6, 4), 
        new b(6, 4), new b(6, 4), new b(6, 4), new b(6, 4), new b(5, 4), new b(5, 4), new b(5, 4), new b(5, 4), new b(5, 4), new b(5, 4), 
        new b(5, 4), new b(5, 4), new b(1, 3), new b(1, 3), new b(1, 3), new b(1, 3), new b(1, 3), new b(1, 3), new b(1, 3), new b(1, 3), 
        new b(1, 3), new b(1, 3), new b(1, 3), new b(1, 3), new b(1, 3), new b(1, 3), new b(1, 3), new b(1, 3), new b(4, 3), new b(4, 3), 
        new b(4, 3), new b(4, 3), new b(4, 3), new b(4, 3), new b(4, 3), new b(4, 3), new b(4, 3), new b(4, 3), new b(4, 3), new b(4, 3), 
        new b(4, 3), new b(4, 3), new b(4, 3), new b(4, 3), new b(3, 2), new b(3, 2), new b(3, 2), new b(3, 2), new b(3, 2), new b(3, 2), 
        new b(3, 2), new b(3, 2), new b(3, 2), new b(3, 2), new b(3, 2), new b(3, 2), new b(3, 2), new b(3, 2), new b(3, 2), new b(3, 2), 
        new b(3, 2), new b(3, 2), new b(3, 2), new b(3, 2), new b(3, 2), new b(3, 2), new b(3, 2), new b(3, 2), new b(3, 2), new b(3, 2), 
        new b(3, 2), new b(3, 2), new b(3, 2), new b(3, 2), new b(3, 2), new b(3, 2), new b(2, 2), new b(2, 2), new b(2, 2), new b(2, 2), 
        new b(2, 2), new b(2, 2), new b(2, 2), new b(2, 2), new b(2, 2), new b(2, 2), new b(2, 2), new b(2, 2), new b(2, 2), new b(2, 2), 
        new b(2, 2), new b(2, 2), new b(2, 2), new b(2, 2), new b(2, 2), new b(2, 2), new b(2, 2), new b(2, 2), new b(2, 2), new b(2, 2), 
        new b(2, 2), new b(2, 2), new b(2, 2), new b(2, 2), new b(2, 2), new b(2, 2), new b(2, 2), new b(2, 2), new b(-2, 4), new b(-2, 4), 
        new b(-1, 0), new b(-1, 0), new b(-1, 0), new b(-1, 0), new b(-1, 0), new b(-1, 0), new b(-1, 0), new b(-1, 0), new b(-1, 0), new b(-1, 0), 
        new b(-1, 0), new b(-1, 0), new b(-1, 0), new b(-3, 5), new b(1792, 4), new b(1792, 4), new b(1984, 5), new b(2048, 5), new b(2112, 5), new b(2176, 5), 
        new b(2240, 5), new b(2304, 5), new b(1856, 4), new b(1856, 4), new b(1920, 4), new b(1920, 4), new b(2368, 5), new b(2432, 5), new b(2496, 5), new b(2560, 5), 
        new b(18, 3), new b(18, 3), new b(18, 3), new b(18, 3), new b(18, 3), new b(18, 3), new b(18, 3), new b(18, 3), new b(52, 5), new b(52, 5), 
        new b(640, 6), new b(704, 6), new b(768, 6), new b(832, 6), new b(55, 5), new b(55, 5), new b(56, 5), new b(56, 5), new b(1280, 6), new b(1344, 6), 
        new b(1408, 6), new b(1472, 6), new b(59, 5), new b(59, 5), new b(60, 5), new b(60, 5), new b(1536, 6), new b(1600, 6), new b(24, 4), new b(24, 4), 
        new b(24, 4), new b(24, 4), new b(25, 4), new b(25, 4), new b(25, 4), new b(25, 4), new b(1664, 6), new b(1728, 6), new b(320, 5), new b(320, 5), 
        new b(384, 5), new b(384, 5), new b(448, 5), new b(448, 5), new b(512, 6), new b(576, 6), new b(53, 5), new b(53, 5), new b(54, 5), new b(54, 5), 
        new b(896, 6), new b(960, 6), new b(1024, 6), new b(1088, 6), new b(1152, 6), new b(1216, 6), new b(64, 3), new b(64, 3), new b(64, 3), new b(64, 3), 
        new b(64, 3), new b(64, 3), new b(64, 3), new b(64, 3), new b(13, 1), new b(13, 1), new b(13, 1), new b(13, 1), new b(13, 1), new b(13, 1), 
        new b(13, 1), new b(13, 1), new b(13, 1), new b(13, 1), new b(13, 1), new b(13, 1), new b(13, 1), new b(13, 1), new b(13, 1), new b(13, 1), 
        new b(23, 4), new b(23, 4), new b(50, 5), new b(51, 5), new b(44, 5), new b(45, 5), new b(46, 5), new b(47, 5), new b(57, 5), new b(58, 5), 
        new b(61, 5), new b(256, 5), new b(16, 3), new b(16, 3), new b(16, 3), new b(16, 3), new b(17, 3), new b(17, 3), new b(17, 3), new b(17, 3), 
        new b(48, 5), new b(49, 5), new b(62, 5), new b(63, 5), new b(30, 5), new b(31, 5), new b(32, 5), new b(33, 5), new b(40, 5), new b(41, 5), 
        new b(22, 4), new b(22, 4), new b(14, 1), new b(14, 1), new b(14, 1), new b(14, 1), new b(14, 1), new b(14, 1), new b(14, 1), new b(14, 1), 
        new b(14, 1), new b(14, 1), new b(14, 1), new b(14, 1), new b(14, 1), new b(14, 1), new b(14, 1), new b(14, 1), new b(15, 2), new b(15, 2), 
        new b(15, 2), new b(15, 2), new b(15, 2), new b(15, 2), new b(15, 2), new b(15, 2), new b(128, 5), new b(192, 5), new b(26, 5), new b(27, 5), 
        new b(28, 5), new b(29, 5), new b(19, 4), new b(19, 4), new b(20, 4), new b(20, 4), new b(34, 5), new b(35, 5), new b(36, 5), new b(37, 5), 
        new b(38, 5), new b(39, 5), new b(21, 4), new b(21, 4), new b(42, 5), new b(43, 5), new b(0, 3), new b(0, 3), new b(0, 3), new b(0, 3)
    };
    static byte _fldif[] = {
        -1, 127, 63, 31, 15, 7, 3, 1
    };
    static byte _flddo[] = {
        0, -128, -64, -32, -16, -8, -4, -2
    };



}