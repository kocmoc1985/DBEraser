// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:25
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.a;

import com.qoppa.a.b.c;
import java.awt.Image;
import java.awt.image.*;
import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import javax.imageio.ImageIO;

// Referenced classes of package com.qoppa.a.a:
//            b, e

public class com.qoppa.a.a.a
{
    private static interface a
    {

        public abstract byte a(byte byte0, byte byte1);

        public abstract byte _mthif(byte byte0, byte byte1, int i);

        public abstract byte a(byte byte0, byte byte1, int i);

        public abstract byte a(byte byte0, byte byte1, int i, int j);
    }


    public com.qoppa.a.a.a(int i, int j)
    {
        _fldgoto = false;
        _mthif(i, j);
    }

    public com.qoppa.a.a.a(Image image)
        throws c
    {
        _fldgoto = false;
        b b1 = new b();
        b1.a(image, this);
    }

    public com.qoppa.a.a.a(byte abyte0[], int i, int j, boolean flag)
    {
        _fldgoto = false;
        d = i;
        b = j;
        _fldelse = (int)Math.ceil((float)i / 8F);
        _fldgoto = false;
        if(flag)
            _fldnew = abyte0;
        else
            _fldnew = a(abyte0, d, b);
        if(i % 8 != 0)
        {
            int k = 8 - i % 8;
            int l = (i + 7) / 8;
            byte byte0 = _fldbyte[k];
            for(int i1 = 0; i1 < j; i1++)
                _fldnew[(i1 + 1) * l - 1] &= byte0;

        }
    }

    public com.qoppa.a.a.a a()
    {
        com.qoppa.a.a.a a1 = new com.qoppa.a.a.a(d, b);
        System.arraycopy(_fldnew, 0, a1._fldnew, 0, _fldnew.length);
        return a1;
    }

    private byte[] a(byte abyte0[], int i, int j)
    {
        byte abyte1[] = new byte[abyte0.length];
        for(int k = 0; k < abyte0.length; k++)
            abyte1[k] = (byte)(~abyte0[k]);

        return abyte1;
    }

    public void _mthif(int i, int j)
    {
        d = i;
        b = j;
        _fldelse = (int)Math.ceil((float)i / 8F);
        _fldnew = new byte[_fldelse * b];
        if(_fldgoto)
            _flddo = new byte[_fldelse * b];
    }

    public int _mthnew()
    {
        return d;
    }

    public int _mthtry()
    {
        return b;
    }

    public int _mthfor()
    {
        return _fldelse;
    }

    public void _mthif(int i, int j, boolean flag)
    {
        if(i < 0 || i >= d || j < 0 || j >= b)
        {
            return;
        } else
        {
            int k = j * _fldelse + i / 8;
            int l = 7 - (i & 7);
            int i1 = 1 << l ^ 0xff;
            int j1 = _fldnew[k] & i1;
            int k1 = flag ? 1 : 0;
            _fldnew[k] = (byte)(j1 | k1 << l);
            return;
        }
    }

    public int a(int i, int j)
    {
        if(i < 0 || i >= d || j < 0 || j >= b)
        {
            return 0;
        } else
        {
            int k = j * _fldelse + i / 8;
            int l = 7 - (i & 7);
            return _fldnew[k] >> l & 1;
        }
    }

    public void a(int i, int j, boolean flag)
    {
        if(i < 0 || i >= d || j < 0 || j >= b)
        {
            return;
        } else
        {
            int k = j * _fldelse + i / 8;
            int l = 7 - (i & 7);
            int i1 = 1 << l ^ 0xff;
            int j1 = _flddo[k] & i1;
            int k1 = flag ? 1 : 0;
            _flddo[k] = (byte)(j1 | k1 << l);
            return;
        }
    }

    public void _mthif(boolean flag)
    {
        byte byte0 = ((byte)(flag ? -1 : 0));
        Arrays.fill(_fldnew, byte0);
    }

    public void _mthdo(int i, int j)
    {
        if(i >= 0 && j >= 0)
            System.arraycopy(_fldnew, i * _fldelse, _fldnew, j * _fldelse, _fldelse);
    }

    public byte[] _mthint()
    {
        return _fldnew;
    }

    private a a(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            return _fldcase;

        case 1: // '\001'
            return _fldtry;

        case 2: // '\002'
            return c;

        case 3: // '\003'
            return _fldint;

        case 4: // '\004'
            return _fldfor;
        }
        return null;
    }

    public void a(com.qoppa.a.a.a a1, int i, int j, int k)
    {
        int l = 0;
        int i1 = 0;
        int j1 = a1._mthnew();
        int k1 = a1._mthtry();
        int l1 = i;
        int i2 = j;
        if(l1 < 0)
        {
            l += -l1;
            j1 -= -l1;
            l1 = 0;
        }
        if(i2 < 0)
        {
            i1 += -i2;
            k1 -= -i2;
            i2 = 0;
        }
        if(l1 + j1 >= d)
            j1 = d - l1;
        if(i2 + k1 >= b)
            k1 = b - i2;
        int j2 = l % 8;
        int k2 = l1 % 8;
        if(j2 == k2)
            a(a1, l, i1, j1, k1, l1, i2, k);
        else
            _mthif(a1, l, i1, j1, k1, l1, i2, k);
    }

    private void a(com.qoppa.a.a.a a1, int i, int j, int k, int l, int i1, int j1, 
            int k1)
    {
        int l1 = i % 8;
        int i2 = (i + k) % 8;
        int j2 = k - i2;
        a a2 = a(k1);
        for(int k2 = 0; k2 < l; k2++)
        {
            int l2 = (j + k2) * a1._fldelse + i / 8;
            int i3 = (j1 + k2) * _fldelse + i1 / 8;
            if(l1 != 0)
            {
                int j3 = 8 - l1;
                _fldnew[i3] = a2._mthif(_fldnew[i3], a1._fldnew[l2], j3);
                l2++;
                i3++;
            }
            for(int k3 = l1; k3 < j2; k3 += 8)
            {
                _fldnew[i3] = a2.a(_fldnew[i3], a1._fldnew[l2]);
                l2++;
                i3++;
            }

            if(i2 != 0)
            {
                if(l2 >= a1._fldnew.length)
                    System.out.println("Problem");
                _fldnew[i3] = a2.a(_fldnew[i3], a1._fldnew[l2], i2);
            }
        }

    }

    private void _mthif(com.qoppa.a.a.a a1, int i, int j, int k, int l, int i1, int j1, 
            int k1)
    {
        a a2 = a(k1);
        e e1 = new e(a1._fldnew, i);
        int l1 = 8 - i1 % 8;
        if(l1 > k)
        {
            l1 = k;
            int i2 = j1 * _fldelse + i1 / 8;
            int k2 = i1 % 8;
            int i3 = 8 - l1 - k2;
            for(int k3 = 0; k3 < l; k3++)
            {
                int i4 = (j + k3) * a1._fldelse * 8 + i;
                e1._mthif(i4);
                _fldnew[i2] = a2.a(_fldnew[i2], (byte)(e1.a(l1) << i3), k2, l1);
                i2 += _fldelse;
            }

            return;
        }
        int j2 = (i1 + k) % 8;
        for(int l2 = 0; l2 < l; l2++)
        {
            int j3 = (j1 + l2) * _fldelse + i1 / 8;
            int l3 = (j1 + l2) * _fldelse + (i1 + k) / 8;
            int j4 = (j + l2) * a1._fldelse * 8 + i;
            e1._mthif(j4);
            if(l1 != 8)
            {
                _fldnew[j3] = a2._mthif(_fldnew[j3], e1.a(l1), l1);
                j3++;
            }
            for(int k4 = j3; k4 < l3; k4++)
                _fldnew[k4] = a2.a(_fldnew[k4], e1.a());

            if(j2 != 0)
                _fldnew[l3] = a2.a(_fldnew[l3], (byte)(e1.a(j2) << 8 - j2), j2);
        }

    }

    public long _mthfor(int i, int j)
    {
        long l = 0L;
        for(int k = j * 4; k < j * 4 + 4; k++)
        {
            l <<= 8;
            if(k < _fldelse)
                l |= _fldnew[i * _fldelse + k] & 0xff;
        }

        return l;
    }

    public boolean _mthdo()
    {
        return _fldgoto;
    }

    public void a(boolean flag)
    {
        _fldgoto = flag;
    }

    public BufferedImage _mthif()
    {
        byte abyte0[] = {
            -1, 0
        };
        IndexColorModel indexcolormodel = new IndexColorModel(1, 2, abyte0, abyte0, abyte0);
        DataBufferByte databufferbyte = new DataBufferByte(_fldnew, _fldnew.length);
        java.awt.image.WritableRaster writableraster = Raster.createPackedRaster(databufferbyte, d, b, 1, null);
        return new BufferedImage(indexcolormodel, writableraster, false, null);
    }

    public void a(String s)
    {
        try
        {
            ImageIO.write(_mthif(), "png", new File(s));
        }
        catch(Throwable throwable)
        {
            throwable.printStackTrace();
        }
    }

    public static final int _fldlong = 0;
    public static final int _fldvoid = 1;
    public static final int _fldchar = 2;
    public static final int _fldnull = 3;
    public static final int e = 4;
    public int d;
    public int b;
    public int _fldelse;
    public byte _fldnew[];
    private boolean _fldgoto;
    public byte _flddo[];
    private static final byte _fldbyte[] = {
        -1, -2, -4, -8, -16, -32, -64, -128
    };
    private static final byte a[] = {
        0, 1, 3, 7, 15, 31, 63, 127, -1
    };
    private static final byte _fldif[] = {
        0, -128, -64, -32, -16, -8, -4, -2, -1
    };
    private static final a _fldcase = new a() {

        public byte a(byte byte0, byte byte1)
        {
            return (byte)(byte0 | byte1);
        }

        public byte a(byte byte0, byte byte1, int i)
        {
            return (byte)(byte0 | byte1 & com.qoppa.a.a.a._fldif[i]);
        }

        public byte _mthif(byte byte0, byte byte1, int i)
        {
            return (byte)(byte0 | byte1 & com.qoppa.a.a.a.a[i]);
        }

        public byte a(byte byte0, byte byte1, int i, int j)
        {
            byte byte2 = (byte)(com.qoppa.a.a.a.a[j] << 8 - j - i);
            return (byte)(byte0 | byte1 & byte2);
        }

    }
;
    private static final a _fldtry = new a() {

        public byte a(byte byte0, byte byte1)
        {
            return (byte)(byte0 & byte1);
        }

        public byte a(byte byte0, byte byte1, int i)
        {
            return (byte)(byte0 & (byte1 | ~com.qoppa.a.a.a._fldif[i]));
        }

        public byte _mthif(byte byte0, byte byte1, int i)
        {
            return (byte)(byte0 & (byte1 | ~com.qoppa.a.a.a.a[i]));
        }

        public byte a(byte byte0, byte byte1, int i, int j)
        {
            byte byte2 = (byte)(com.qoppa.a.a.a.a[j] << 8 - j - i);
            return (byte)(byte0 & (byte1 & byte2 | ~byte2));
        }

    }
;
    private static final a c = new a() {

        public byte a(byte byte0, byte byte1)
        {
            return (byte)(byte0 ^ byte1);
        }

        public byte a(byte byte0, byte byte1, int i)
        {
            return (byte)(byte0 ^ byte1 & com.qoppa.a.a.a._fldif[i]);
        }

        public byte _mthif(byte byte0, byte byte1, int i)
        {
            return (byte)(byte0 ^ byte1 & com.qoppa.a.a.a.a[i]);
        }

        public byte a(byte byte0, byte byte1, int i, int j)
        {
            byte byte2 = (byte)(com.qoppa.a.a.a.a[j] << 8 - j - i);
            return (byte)(byte0 ^ byte1 & byte2);
        }

    }
;
    private static final a _fldint = new a() {

        public byte a(byte byte0, byte byte1)
        {
            return (byte)(byte0 ^ ~byte1);
        }

        public byte a(byte byte0, byte byte1, int i)
        {
            return (byte)(byte0 ^ ~byte1 & com.qoppa.a.a.a._fldif[i]);
        }

        public byte _mthif(byte byte0, byte byte1, int i)
        {
            return (byte)(byte0 ^ ~byte1 & com.qoppa.a.a.a.a[i]);
        }

        public byte a(byte byte0, byte byte1, int i, int j)
        {
            byte byte2 = (byte)(com.qoppa.a.a.a.a[j] << 8 - j - i);
            return (byte)(byte0 ^ ~byte1 & byte2);
        }

    }
;
    private static final a _fldfor = new a() {

        public byte a(byte byte0, byte byte1)
        {
            return byte1;
        }

        public byte a(byte byte0, byte byte1, int i)
        {
            return (byte)(byte0 & ~com.qoppa.a.a.a._fldif[i] | byte1 & com.qoppa.a.a.a._fldif[i]);
        }

        public byte _mthif(byte byte0, byte byte1, int i)
        {
            return (byte)(byte0 & ~com.qoppa.a.a.a.a[i] | byte1 & com.qoppa.a.a.a.a[i]);
        }

        public byte a(byte byte0, byte byte1, int i, int j)
        {
            byte byte2 = (byte)(com.qoppa.a.a.a.a[j] << 8 - j - i);
            return (byte)(byte0 & ~byte2 | byte1 & byte2);
        }

    }
;



}