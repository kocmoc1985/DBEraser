// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:30
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.c;


// Referenced classes of package com.qoppa.c:
//            t

public class j
{

    public j(byte abyte0[])
    {
        a = new t(abyte0, 0, 10);
    }

    public byte[] a(byte abyte0[])
    {
        byte abyte1[] = new byte[abyte0.length];
        a.a(abyte0, abyte1);
        return abyte1;
    }

    private t a;
}