// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:28
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.c;

import com.qoppa.a.b.c;
import java.util.Vector;

// Referenced classes of package com.qoppa.a.c:
//            p, s, g, v, 
//            r, l, d, w, 
//            m

public class q
{

    public q()
    {
    }

    public static q a(d d1, byte abyte0[], int i, int j)
        throws c
    {
        if(j < 11)
            return null;
        q q1 = new q();
        q1._fldnew = (int)com.qoppa.a.a.c.a(abyte0, i);
        q1._fldfor = abyte0[4 + i];
        int k = abyte0[5 + i] & 0xff;
        int j1;
        int k1;
        if((k & 0xe0) == 224)
        {
            int i1 = (int)com.qoppa.a.a.c.a(abyte0, i + 5);
            j1 = i1 & 0x1fffffff;
            k1 = i + 5 + 4 + (j1 + 1) / 8;
        } else
        {
            j1 = k >> 5;
            k1 = i + 5 + 1;
        }
        q1._fldif = j1;
        byte byte0 = q1._fldnew > 256 ? ((byte)(q1._fldnew > 0x10000 ? 4 : 2)) : 1;
        byte byte1 = ((byte)((q1._fldfor & 0x40) == 0 ? 1 : 4));
        if(k1 + j1 * byte0 + byte1 + 4 > i + j)
            throw new c("jbig2_parse_segment_header() called with insufficient data");
        if(j1 != 0)
        {
            int ai[] = new int[j1];
            for(int l1 = 0; l1 < j1; l1++)
            {
                ai[l1] = byte0 != 1 ? byte0 != 2 ? (int)com.qoppa.a.a.c.a(abyte0, k1) : com.qoppa.a.a.c._mthif(abyte0, k1) : abyte0[k1] & 0xff;
                k1 += byte0;
            }

            q1._fldbyte = ai;
        } else
        {
            q1._fldbyte = null;
        }
        if((q1._fldfor & 0x40) != 0)
        {
            q1._fldcase = (int)com.qoppa.a.a.c.a(abyte0, k1);
            k1 += 4;
        } else
        {
            q1._fldcase = abyte0[k1++] & 0xff;
        }
        q1._flddo = (int)com.qoppa.a.a.c.a(abyte0, k1);
        q1.a = (k1 - i) + 4;
        return q1;
    }

    public static int a(d d1, q q1, byte abyte0[], int i)
        throws c
    {
        switch(q1._fldfor & 0x3f)
        {
        case 0: // '\0'
            return p.a(d1, q1, abyte0, i);

        case 4: // '\004'
        case 6: // '\006'
        case 7: // '\007'
            return s.a(d1, q1, abyte0, i);

        case 16: // '\020'
            return g.a(d1, q1, abyte0, i);

        case 20: // '\024'
        case 22: // '\026'
        case 23: // '\027'
            return g._mthif(d1, q1, abyte0, i);

        case 36: // '$'
            throw new c("unhandled segment type 'intermediate generic region'");

        case 38: // '&'
            return v.a(d1, q1, abyte0, i);

        case 39: // '\''
            return v.a(d1, q1, abyte0, i);

        case 40: // '('
            return r.a(d1, q1, abyte0, i);

        case 42: // '*'
            return r.a(d1, q1, abyte0, i);

        case 43: // '+'
            return r.a(d1, q1, abyte0, i);

        case 48: // '0'
            return l._mthdo(d1, q1, abyte0, i);

        case 49: // '1'
            return l._mthif(d1, q1, abyte0, i);

        case 50: // '2'
            return l.a(d1, q1, abyte0, i);

        case 51: // '3'
            d1._fldcase = -1;
            return 0;

        case 52: // '4'
            throw new c("Unhandled Segment Type: Profile");

        case 53: // '5'
            q1._fldchar = w.a(d1, q1, abyte0, i);
            return 0;

        case 62: // '>'
            return _mthif(d1, q1, abyte0, i);
        }
        throw new c("unknown segment type " + (q1._fldfor & 0x3f));
    }

    private static int _mthif(d d1, q q1, byte abyte0[], int i)
        throws c
    {
        int j = (int)com.qoppa.a.a.c.a(abyte0, i);
        boolean flag = (j & 0x20000000) != 0;
        boolean flag1 = (j & 0x80000000) != 0;
        if(!flag1);
        switch(j)
        {
        case 536870912: 
            return m._mthif(d1, q1, abyte0, i);

        case 536870914: 
            return m.a(d1, q1, abyte0, i);
        }
        if(flag1)
            throw new c("unhandled necessary extension segment type " + j);
        else
            return 0;
    }

    public Vector a(d d1)
    {
        Vector vector = new Vector();
        if(_fldif != 0)
        {
            for(int i = 0; i < _fldif; i++)
            {
                q q1 = d1.a(_fldbyte[i]);
                if(q1 != null)
                    vector.add(q1);
            }

        }
        return vector;
    }

    public Vector a(d d1, int i)
    {
        Vector vector = new Vector();
        if(_fldif != 0)
        {
            for(int j = 0; j < _fldif; j++)
            {
                q q1 = d1.a(_fldbyte[j]);
                if(q1 != null && i == (q1._fldfor & 0x3f))
                    vector.add(q1);
            }

        }
        return vector;
    }

    int a;
    int _fldnew;
    byte _fldfor;
    int _fldcase;
    int _flddo;
    int _fldif;
    int _fldbyte[];
    Object _fldchar;
    public static final int _fldint = 16;
    public static final int _fldtry = 53;
}