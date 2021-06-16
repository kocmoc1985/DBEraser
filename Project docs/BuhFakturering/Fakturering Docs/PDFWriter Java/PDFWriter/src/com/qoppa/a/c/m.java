// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:27
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.c;

import java.util.Hashtable;

// Referenced classes of package com.qoppa.a.c:
//            q, d

public class m
{

    public m()
    {
    }

    public static int _mthif(d d, q q1, byte abyte0[], int i)
    {
        return a(d, q1, abyte0, i, "US-ASCII");
    }

    public static int a(d d, q q1, byte abyte0[], int i, String s)
    {
        int j = 4;
        Hashtable hashtable = new Hashtable();
        while(j < q1._flddo - 1) 
        {
            String s1 = a(abyte0, i + j, i + q1._flddo, s);
            if(s1 == null || s1.length() <= 0)
                break;
            j += s1.length() + 1;
            String s2 = a(abyte0, i + j, i + q1._flddo, s);
            if(s2 == null)
                break;
            j += s2.length() + 1;
            hashtable.put(s1, s2);
        }
        q1._fldchar = hashtable;
        return 0;
    }

    private static String a(byte abyte0[], int i, int j, String s)
    {
        int k = 0;
        int l;
        for(l = i; l < j && abyte0[l] != 0; l++)
            k++;

        if(l >= j)
            return null;
        try
        {
            return new String(abyte0, i, k, s);
        }
        catch(Throwable throwable)
        {
            return new String(abyte0, i, k);
        }
    }

    public static int a(d d, q q1, byte abyte0[], int i)
    {
        return a(d, q1, abyte0, i, "UTF-16");
    }
}