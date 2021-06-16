// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:27
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.c;

import com.qoppa.a.a.a;
import com.qoppa.a.b.c;
import java.util.Vector;

// Referenced classes of package com.qoppa.a.c:
//            d, l, q

public class j
{

    public j()
    {
    }

    public static a _mthif(d d1, byte abyte0[], boolean flag)
        throws c
    {
        d d2 = new d(d1);
        a(d2, abyte0, flag);
        if(d2.a().size() > 0)
            return ((l)d2.a().get(0))._fldnew;
        else
            return null;
    }

    public static d a(byte abyte0[], boolean flag)
        throws c
    {
        d d1 = new d(null);
        a(d1, abyte0, flag);
        return d1;
    }

    public static Vector a(byte abyte0[], byte abyte1[], boolean flag)
        throws c
    {
        d d1 = new d(null);
        if(abyte0 != null)
            a(d1, abyte0, flag);
        if(abyte1 != null)
        {
            d1 = new d(d1);
            a(d1, abyte1, flag);
        }
        Vector vector = new Vector();
        Vector vector1 = d1.a();
        for(int i = 0; i < vector1.size(); i++)
        {
            l l1 = (l)vector1.get(i);
            if(l1._fldnew != null)
                vector.add(l1._fldnew);
        }

        return vector;
    }

    private static int a(d d1, byte abyte0[], boolean flag)
        throws c
    {
        d1._flddo = abyte0;
        d1._fldgoto += abyte0.length;
        if(!flag)
        {
            if(com.qoppa.a.a.c.a(d1._flddo, d1._fldlong, a, 0, 8) != 0)
                throw new c("Not a JBIG2 file header");
            d1._fldlong += 8;
            d1._fldnull = d1._flddo[d1._fldlong];
            if((d1._fldnull & 0xfc) != 0)
                throw new c("reserved bits (2-7) of file header flags are not zero (" + d1._fldnull + ")");
            d1._fldlong++;
            if((d1._fldnull & 2) == 0)
            {
                d1._fldvoid = (int)com.qoppa.a.a.c.a(d1._flddo, d1._fldlong);
                d1._fldlong += 4;
            } else
            {
                d1._fldvoid = 0;
            }
            if((d1._fldnull & 1) != 0)
                d1._fldcase = 1;
            else
                d1._fldcase = 3;
        }
        do
        {
            q q1 = com.qoppa.a.c.q.a(d1, d1._flddo, d1._fldlong, d1._fldgoto - d1._fldlong);
            if(q1 == null)
                return -1;
            d1._fldlong += q1.a;
            d1.a(q1);
            if(d1._fldcase == 3)
            {
                if((q1._fldfor & 0x3f) == 51)
                    break;
            } else
            {
                if(q1._flddo > d1._fldgoto - d1._fldlong)
                    return 0;
                int k = com.qoppa.a.c.q.a(d1, q1, d1._flddo, d1._fldlong);
                d1._fldlong += q1._flddo;
                if(d1._fldcase == -1 || k < 0)
                    return -1;
            }
        } while(true);
        for(int i = 0; i < d1._mthfor(); i++)
        {
            q q2 = d1._mthif(i);
            d1._fldint = i;
            int i1 = com.qoppa.a.c.q.a(d1, q2, d1._flddo, d1._fldlong);
            d1._fldlong += q2._flddo;
            if(d1._fldcase == -1 || i1 < 0)
                return -1;
        }

        return -1;
    }

    private static byte a[] = {
        -105, 74, 66, 50, 13, 10, 26, 10
    };

}