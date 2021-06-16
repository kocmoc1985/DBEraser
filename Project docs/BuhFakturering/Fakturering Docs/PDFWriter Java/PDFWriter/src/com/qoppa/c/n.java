// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:30
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.c;


// Referenced classes of package com.qoppa.c:
//            o

public class n
{

    public n(byte abyte0[], int i, int j)
    {
        _fldfor = new o();
        _fldfor.a(abyte0, 0, 5);
        a(i, 3);
        a(j, 2);
        _fldif = _fldfor._mthnew();
        _fldfor = null;
    }

    private void a(int i, int j)
    {
        while(j-- > 0) 
        {
            _fldfor._mthif((byte)i);
            i >>>= 8;
        }
    }

    public byte[] a()
    {
        return _fldif;
    }

    private byte _fldif[];
    public static final int a = 10;
    private o _fldfor;
    static final int _flddo = 3;
    static final int _fldint = 2;
}