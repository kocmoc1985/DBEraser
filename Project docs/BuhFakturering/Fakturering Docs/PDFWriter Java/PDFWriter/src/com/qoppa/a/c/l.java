// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:27
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.c;

import com.qoppa.a.a.a;
import com.qoppa.a.b.c;
import java.util.Arrays;
import java.util.Vector;

// Referenced classes of package com.qoppa.a.c:
//            d, q

public class l
{

    public l()
    {
        _fldint = 0;
        _fldtry = 1;
    }

    public void a(d d1)
        throws c
    {
        if(d1._fldint != d1._fldif.size() - 1)
        {
            q q1 = d1._mthif(d1._fldint);
            if((q1._flddo & -1) == -1)
            {
                q1._flddo = d1._fldgoto - d1._fldlong;
                com.qoppa.a.c.q.a(d1, q1, d1._flddo, d1._fldlong);
                d1._fldlong += q1._flddo;
                d1._fldint++;
            }
        }
        _fldtry = 2;
    }

    public static int _mthdo(d d1, q q1, byte abyte0[], int i)
        throws c
    {
        l l1 = new l();
        l1._fldint = q1._fldcase;
        d1.a(l1);
        if(q1._flddo < 19)
            throw new c("segment too short");
        l1.a = (int)com.qoppa.a.a.c.a(abyte0, i);
        l1._fldvoid = (int)com.qoppa.a.a.c.a(abyte0, i + 4);
        l1._fldbyte = (int)com.qoppa.a.a.c.a(abyte0, i + 8);
        l1._fldchar = (int)com.qoppa.a.a.c.a(abyte0, i + 12);
        l1._fldif = abyte0[i + 16];
        int j = com.qoppa.a.a.c._mthif(abyte0, i + 17);
        if((j & 0x8000) != 0)
        {
            l1._fldgoto = true;
            l1._fldcase = j & 0x7fff;
        } else
        {
            l1._fldgoto = false;
            l1._fldcase = 0;
        }
        if(l1._fldvoid == -1 && !l1._fldgoto)
            l1._fldgoto = true;
        int _tmp = q1._flddo;
        if(l1._fldvoid == -1)
            l1._fldnew = new a(l1.a, l1._fldcase);
        else
            l1._fldnew = new a(l1.a, l1._fldvoid);
        if((l1._fldif & 4) != 0)
            Arrays.fill(l1._fldnew._fldnew, (byte)-1);
        return 0;
    }

    public static int _mthif(d d1, q q1, byte abyte0[], int i)
        throws c
    {
        l l1 = d1._mthdo();
        int j = l1._fldint;
        int _tmp = q1._fldcase;
        l1.a(d1);
        return 0;
    }

    public static int a(d d1, q q1, byte abyte0[], int i)
        throws c
    {
        return 0;
    }

    public void a(a a1, int i, int j, int k)
    {
        if(_fldgoto)
        {
            int i1 = j + a1._mthtry();
            if(_fldnew._mthtry() < i1)
            {
                a a2 = new a(_fldnew._mthnew(), i1);
                System.arraycopy(_fldnew._fldnew, 0, a2._fldnew, 0, _fldnew._fldnew.length);
                _fldnew = a2;
            }
        }
        _fldnew.a(a1, i, j, k);
    }

    public static final int _fldnull = 0;
    public static final int _flddo = 1;
    public static final int _fldelse = 2;
    public static final int _fldlong = 3;
    public static final int _fldfor = 4;
    int _fldtry;
    int _fldint;
    int _fldvoid;
    int a;
    int _fldbyte;
    int _fldchar;
    int _fldcase;
    boolean _fldgoto;
    byte _fldif;
    a _fldnew;
}