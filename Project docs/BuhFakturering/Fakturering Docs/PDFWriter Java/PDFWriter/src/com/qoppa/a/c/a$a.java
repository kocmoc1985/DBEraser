// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:26
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.c;


// Referenced classes of package com.qoppa.a.c:
//            a

private static class a$a
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
                    int k3 = a(a.access$0(), 8);
                    int i4 = a(a.access$1(), 7);
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
                    int j4 = a(a.access$1(), 7);
                    int l3 = a(a.access$0(), 8);
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

    private int _mthif(a$b aa$b[], int i)
    {
        long l = _fldint;
        int j = (int)(l >> 32 - i);
        int i1 = aa$b[j]._fldif;
        int j1 = aa$b[j].a;
        if(j1 > i)
        {
            int k1 = (1 << 32 - i) - 1;
            int k = (int)((long)i1 + ((l & (long)k1) >> 32 - j1));
            i1 = aa$b[k]._fldif;
            j1 = i + aa$b[k].a;
        }
        a(j1);
        return i1;
    }

    private int a(a$b aa$b[], int i)
    {
        int j = 0;
        int k;
        do
        {
            k = _mthif(aa$b, i);
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
            abyte0[k] |= a._fldif[i1] & a._flddo[j1];
        } else
        {
            abyte0[k] |= a._fldif[i1];
            for(int k1 = k + 1; k1 < l; k1++)
                abyte0[k1] = -1;

            if(l < abyte0.length)
                abyte0[l] |= a._flddo[j1];
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


    a$a(int i, int j, byte abyte0[], int k, int l)
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