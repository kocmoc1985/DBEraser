// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:27
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.c;


// Referenced classes of package com.qoppa.a.c:
//            h

public class o
{
    private static class a
    {

        int a;
        byte _flddo;
        byte _fldif;

        public a(int i, int j, int k)
        {
            a = i;
            _flddo = (byte)j;
            _fldif = (byte)k;
        }
    }


    public o(h h1)
    {
        _fldbyte = h1;
        _fldnew = _fldbyte.a(0);
        _fldint = 4;
        _flddo = 4;
        _fldtry = _fldnew >> 8 & 0xff0000L;
        _mthdo();
        _fldtry <<= 7;
        _fldfor -= 7;
        a = 32768;
    }

    private void _mthdo()
    {
        int i = (int)(_fldnew >> 24 & 255L);
        if(i == 255)
        {
            if(_fldint == 1)
            {
                _fldnew = _fldbyte.a(_flddo);
                _flddo += 4;
                int k = (int)(_fldnew >> 24 & 255L);
                if(k > 143)
                {
                    _fldtry += 65280L;
                    _fldfor = 8;
                    _fldnew = (0xff00 | k) << 16;
                    _fldint = 2;
                } else
                {
                    _fldtry += k << 9;
                    _fldfor = 7;
                    _fldint = 4;
                }
            } else
            {
                int l = (int)(_fldnew >> 16 & 255L);
                if(l > 143)
                {
                    _fldtry += 65280L;
                    _fldfor = 8;
                } else
                {
                    _fldint--;
                    _fldnew <<= 8;
                    _fldtry += l << 9;
                    _fldfor = 7;
                }
            }
        } else
        {
            _fldfor = 8;
            _fldnew = _fldnew << 8 & 0xffffffffL;
            _fldint--;
            if(_fldint == 0)
            {
                _fldnew = _fldbyte.a(_flddo);
                _flddo += 4;
                _fldint = 4;
            }
            int j = (int)(_fldnew >> 24 & 255L);
            _fldtry += j << 8;
        }
    }

    public boolean a(byte abyte0[], int i)
    {
        int j = abyte0[i] & 0xff;
        a a1 = _fldif[abyte0[i] & 0x7f];
        a -= a1.a;
        if(_fldtry >> 16 >= (long)a1.a)
        {
            _fldtry -= a1.a << 16;
            if((a & 0x8000) == 0)
            {
                boolean flag;
                if(a < a1.a)
                {
                    flag = 1 - (j >> 7) != 0;
                    abyte0[i] ^= a1._fldif;
                } else
                {
                    flag = j >> 7 != 0;
                    abyte0[i] ^= a1._flddo;
                }
                _mthif();
                return flag;
            }
            return j >> 7 != 0;
        }
        boolean flag1;
        if(a < a1.a)
        {
            a = a1.a;
            flag1 = j >> 7 != 0;
            abyte0[i] ^= a1._flddo;
        } else
        {
            a = a1.a;
            flag1 = 1 - (j >> 7) != 0;
            abyte0[i] ^= a1._fldif;
        }
        _mthif();
        return flag1;
    }

    private void _mthif()
    {
        do
        {
            if(_fldfor == 0)
                _mthdo();
            a <<= 1;
            _fldtry <<= 1;
            _fldfor--;
        } while((a & 0x8000) == 0);
    }

    public int a()
    {
        return _flddo;
    }

    private long _fldtry;
    private int a;
    private int _fldfor;
    private long _fldnew;
    private int _fldint;
    private h _fldbyte;
    private int _flddo;
    private static a _fldif[] = {
        new a(22017, 1, 129), new a(13313, 3, 7), new a(6145, 1, 11), new a(2753, 7, 15), new a(1313, 1, 25), new a(545, 35, 36), new a(22017, 1, 128), new a(21505, 15, 9), new a(18433, 1, 6), new a(14337, 3, 7), 
        new a(12289, 1, 27), new a(9217, 7, 25), new a(7169, 1, 24), new a(5633, 16, 24), new a(22017, 1, 128), new a(21505, 31, 1), new a(20737, 1, 31), new a(18433, 3, 1), new a(14337, 1, 3), new a(13313, 7, 1), 
        new a(12289, 1, 7), new a(10241, 3, 6), new a(9217, 1, 2), new a(8705, 15, 2), new a(7169, 1, 14), new a(6145, 3, 14), new a(5633, 1, 2), new a(5121, 7, 2), new a(4609, 1, 6), new a(4353, 3, 6), 
        new a(2753, 1, 2), new a(2497, 63, 2), new a(2209, 1, 62), new a(1313, 3, 62), new a(1089, 1, 2), new a(673, 7, 2), new a(545, 1, 6), new a(321, 3, 6), new a(273, 1, 2), new a(133, 15, 2), 
        new a(73, 1, 14), new a(37, 3, 14), new a(21, 1, 2), new a(9, 7, 2), new a(5, 1, 6), new a(1, 0, 6), new a(22017, 0, 0)
    };

}