// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:25
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.a;


// Referenced classes of package com.qoppa.a.a:
//            a

class a$3
    implements a$a
{

    public byte a(byte byte0, byte byte1)
    {
        return (byte)(byte0 ^ byte1);
    }

    public byte a(byte byte0, byte byte1, int i)
    {
        return (byte)(byte0 ^ byte1 & a.access$0()[i]);
    }

    public byte _mthif(byte byte0, byte byte1, int i)
    {
        return (byte)(byte0 ^ byte1 & a.access$1()[i]);
    }

    public byte a(byte byte0, byte byte1, int i, int j)
    {
        byte byte2 = (byte)(a.access$1()[j] << 8 - j - i);
        return (byte)(byte0 ^ byte1 & byte2);
    }

    a$3()
    {
    }
}