// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:30
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.c;


public class o
    implements Cloneable
{

    public o()
    {
        _fldtry = new int[16];
        _fldif = new int[4];
        _fldcase = new int[_fldif.length];
        _fldfor = 0;
        a();
    }

    public synchronized Object clone()
    {
        try
        {
            o o1 = (o)super.clone();
            o1._fldtry = (int[])_fldtry.clone();
            o1._fldif = (int[])_fldif.clone();
            o1._fldcase = new int[_fldcase.length];
            return o1;
        }
        catch(CloneNotSupportedException clonenotsupportedexception)
        {
            throw new InternalError();
        }
    }

    public byte[] _mthnew()
    {
        return _mthint();
    }

    public byte[] _mthdo(byte abyte0[])
    {
        a(abyte0);
        return _mthint();
    }

    public final synchronized byte[] _mthint()
    {
        int i = _fldfor;
        int j = _fldfor & 0x3f;
        int k = (j < 56 ? 56 : 120) - j;
        _mthif(_fldbyte, 0, k);
        _fldtry[14] = i << 3;
        _fldtry[15] = i >>> 29;
        _mthfor();
        byte abyte0[] = new byte[16];
        for(int l = 0; l < 16; l++)
            abyte0[l] = (byte)(_fldif[l >> 2] >>> ((l & 3) << 3) & 0xff);

        _mthdo();
        return abyte0;
    }

    public final synchronized void _mthdo()
    {
        _fldfor = 0;
        _fldif[0] = 0x67452301;
        _fldif[1] = 0xefcdab89;
        _fldif[2] = 0x98badcfe;
        _fldif[3] = 0x10325476;
    }

    public final synchronized void a(byte byte0)
    {
        int i = _fldfor++ & 0x3f;
        int j = i >>> 2;
        _fldtry[j] = _fldtry[j] >>> 8 | (byte0 & 0xff) << 24;
        if(i == 63)
            _mthfor();
    }

    public final synchronized void _mthif(byte abyte0[], int i, int j)
    {
        while(j > 0) 
        {
            int k = _fldfor & 0x3f;
            int l = Math.min(j, 64 - k);
            i -= k;
            if((k & 3) == 0 && l >= 4)
            {
                l &= -4;
                int i1 = k + l;
                for(int k1 = k; k1 < i1; k1 += 4)
                {
                    int i2 = i + k1;
                    _fldtry[k1 >> 2] = abyte0[i2] & 0xff | (abyte0[i2 + 1] & 0xff) << 8 | (abyte0[i2 + 2] & 0xff) << 16 | (abyte0[i2 + 3] & 0xff) << 24;
                }

            } else
            {
                if(l > 12)
                    l = 4 - (k & 3);
                int j1 = k + l;
                for(int l1 = k; l1 < j1; l1++)
                    _fldtry[l1 >> 2] = _fldtry[l1 >> 2] >>> 8 | (abyte0[i + l1] & 0xff) << 24;

            }
            j -= l;
            _fldfor += l;
            i += k + l;
            if((_fldfor & 0x3f) == 0)
                _mthfor();
        }
    }

    public String _mthif()
    {
        return "MD5";
    }

    public static boolean a(byte abyte0[], byte abyte1[])
    {
        if(abyte0 == abyte1)
            return true;
        if(abyte0 == null || abyte1 == null || abyte0.length != abyte1.length)
            return false;
        for(int i = abyte0.length; i-- > 0;)
            if(abyte0[i] != abyte1[i])
                return false;

        return true;
    }

    public void a()
    {
        _mthdo();
    }

    public static String _mthif(byte abyte0[])
    {
        if(abyte0 == null)
            return "null";
        char ac[] = new char[2 * abyte0.length];
        for(int i = abyte0.length; i-- > 0;)
        {
            ac[2 * i] = _fldint[abyte0[i] >>> 4 & 0xf];
            ac[2 * i + 1] = _fldint[abyte0[i] & 0xf];
        }

        return new String(ac, 0, ac.length);
    }

    private void _mthfor()
    {
        System.arraycopy(_fldif, 0, _fldcase, 0, _fldif.length);
        for(int i = 0; i < 4; i++)
        {
            int k = i << 4;
            int l = k + 16;
            for(int i1 = k; i1 < l; i1++)
            {
                int j1 = -i1 & 3;
                int k1 = _fldcase[j1 + 1 & 3];
                int l1 = _fldcase[j1 + 2 & 3];
                int i2 = _fldcase[j1 + 3 & 3];
                byte byte0 = a[i1];
                int j2;
                switch(i)
                {
                case 0: // '\0'
                    j2 = k1 & l1 | ~k1 & i2;
                    break;

                case 1: // '\001'
                    j2 = k1 & i2 | l1 & ~i2;
                    break;

                case 2: // '\002'
                    j2 = k1 ^ l1 ^ i2;
                    break;

                default:
                    j2 = l1 ^ (k1 | ~i2);
                    break;
                }
                j2 += _fldtry[_fldnew[i1]] + _flddo[i1] + _fldcase[j1];
                j2 = j2 << byte0 | j2 >>> 32 - byte0;
                _fldcase[j1] = j2 + k1;
            }

        }

        for(int j = 0; j < 4; j++)
            _fldif[j] += _fldcase[j];

    }

    public void _mthif(byte byte0)
    {
        a(byte0);
    }

    public void a(byte abyte0[])
    {
        _mthif(abyte0, 0, abyte0.length);
    }

    public void a(byte abyte0[], int i, int j)
    {
        _mthif(abyte0, i, j);
    }

    private static final char _fldint[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'A', 'B', 'C', 'D', 'E', 'F'
    };
    private int _fldtry[];
    private int _fldif[];
    private int _fldcase[];
    private int _fldfor;
    private static final int _flddo[] = {
        0xd76aa478, 0xe8c7b756, 0x242070db, 0xc1bdceee, 0xf57c0faf, 0x4787c62a, 0xa8304613, 0xfd469501, 0x698098d8, 0x8b44f7af, 
        -42063, 0x895cd7be, 0x6b901122, 0xfd987193, 0xa679438e, 0x49b40821, 0xf61e2562, 0xc040b340, 0x265e5a51, 0xe9b6c7aa, 
        0xd62f105d, 0x2441453, 0xd8a1e681, 0xe7d3fbc8, 0x21e1cde6, 0xc33707d6, 0xf4d50d87, 0x455a14ed, 0xa9e3e905, 0xfcefa3f8, 
        0x676f02d9, 0x8d2a4c8a, 0xfffa3942, 0x8771f681, 0x6d9d6122, 0xfde5380c, 0xa4beea44, 0x4bdecfa9, 0xf6bb4b60, 0xbebfbc70, 
        0x289b7ec6, 0xeaa127fa, 0xd4ef3085, 0x4881d05, 0xd9d4d039, 0xe6db99e5, 0x1fa27cf8, 0xc4ac5665, 0xf4292244, 0x432aff97, 
        0xab9423a7, 0xfc93a039, 0x655b59c3, 0x8f0ccc92, 0xffeff47d, 0x85845dd1, 0x6fa87e4f, 0xfe2ce6e0, 0xa3014314, 0x4e0811a1, 
        0xf7537e82, 0xbd3af235, 0x2ad7d2bb, 0xeb86d391
    };
    private static final byte _fldnew[] = {
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 
        10, 11, 12, 13, 14, 15, 1, 6, 11, 0, 
        5, 10, 15, 4, 9, 14, 3, 8, 13, 2, 
        7, 12, 5, 8, 11, 14, 1, 4, 7, 10, 
        13, 0, 3, 6, 9, 12, 15, 2, 0, 7, 
        14, 5, 12, 3, 10, 1, 8, 15, 6, 13, 
        4, 11, 2, 9
    };
    private static final byte a[] = {
        7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 
        17, 22, 7, 12, 17, 22, 5, 9, 14, 20, 
        5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 
        14, 20, 4, 11, 16, 23, 4, 11, 16, 23, 
        4, 11, 16, 23, 4, 11, 16, 23, 6, 10, 
        15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 
        6, 10, 15, 21
    };
    private static final byte _fldbyte[] = {
        -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
        0, 0, 0, 0
    };

}