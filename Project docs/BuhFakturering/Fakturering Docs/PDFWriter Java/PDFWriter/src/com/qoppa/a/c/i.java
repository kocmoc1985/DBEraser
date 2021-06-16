// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:27
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.c;


// Referenced classes of package com.qoppa.a.c:
//            h

public class i
{

    public i(h h1)
    {
        a = 0;
        _fldint = 0;
        _fldif = h1.a(0);
        _flddo = h1.a(4);
        _fldfor = h1;
    }

    public int a(int j)
    {
        int k = (int)(_fldif >> 32 - j);
        _fldint += j;
        if(_fldint >= 32)
        {
            a += 4;
            _fldint -= 32;
            _fldif = _flddo;
            _flddo = _fldfor.a(a + 4);
            if(_fldint != 0)
                _fldif = _fldif << _fldint & 0xffffffffL | _flddo >> 32 - _fldint;
            else
                _fldif = _fldif << _fldint & 0xffffffffL;
        } else
        {
            _fldif = _fldif << j & 0xffffffffL | _flddo >> 32 - _fldint;
        }
        return k;
    }

    void _mthif()
    {
        int j = _fldint & 7;
        if(j != 0)
        {
            j = 8 - j;
            _fldint += j;
            _fldif = _fldif << j & 0xffffffffL | _flddo >> 32 - _fldint;
        }
        if(_fldint >= 32)
        {
            _fldif = _flddo;
            a += 4;
            _flddo = _fldfor.a(a + 4);
            _fldint -= 32;
            if(_fldint != 0)
                _fldif = _fldif << _fldint & 0xffffffffL | _flddo >> 32 - _fldint;
        }
    }

    public int a()
    {
        return a + (_fldint >> 3);
    }

    public void _mthif(int j)
    {
        a += j & -4;
        _fldint += (j & 3) << 3;
        if(_fldint >= 32)
        {
            a += 4;
            _fldint -= 32;
        }
        _fldif = _fldfor.a(a);
        _flddo = _fldfor.a(a + 4);
        if(_fldint > 0)
            _fldif = _fldif << _fldint & 0xffffffffL | _flddo >> 32 - _fldint;
    }

    long _fldif;
    long _flddo;
    int _fldint;
    int a;
    h _fldfor;
}