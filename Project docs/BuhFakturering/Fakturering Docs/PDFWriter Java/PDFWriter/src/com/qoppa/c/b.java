// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:29
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.c;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

public class b
{

    public b()
    {
        _fldfor = new byte[5120];
        _flddo = 0;
        _fldif = new Vector();
        _fldif.add(_fldfor);
    }

    public b(int i)
    {
        _fldfor = new byte[i];
        _flddo = 0;
        _fldif = new Vector();
        _fldif.add(_fldfor);
    }

    public void a(byte byte0)
    {
        if(_flddo >= _fldfor.length)
        {
            _fldfor = new byte[_fldfor.length * 2];
            _flddo = 0;
            _fldif.add(_fldfor);
        }
        _fldfor[_flddo++] = byte0;
    }

    public void a(byte abyte0[])
    {
        int i;
        for(i = 0; _flddo + (abyte0.length - i) > _fldfor.length;)
        {
            int j = _fldfor.length - _flddo;
            System.arraycopy(abyte0, i, _fldfor, _flddo, j);
            i += j;
            _fldfor = new byte[_fldfor.length * 2];
            _flddo = 0;
            _fldif.add(_fldfor);
        }

        System.arraycopy(abyte0, i, _fldfor, _flddo, abyte0.length - i);
        _flddo += abyte0.length - i;
    }

    public void a(byte abyte0[], int i, int j)
    {
        int k = i;
        int l;
        for(l = j; _flddo + l > _fldfor.length;)
        {
            int i1 = _fldfor.length - _flddo;
            System.arraycopy(abyte0, k, _fldfor, _flddo, i1);
            k += i1;
            l -= i1;
            _fldfor = new byte[_fldfor.length * 2];
            _flddo = 0;
            _fldif.add(_fldfor);
        }

        System.arraycopy(abyte0, k, _fldfor, _flddo, l);
        _flddo += l;
    }

    public byte[] _mthif()
    {
        byte abyte0[] = new byte[a()];
        int i = 0;
        for(int j = 0; j < _fldif.size() - 1; j++)
        {
            byte abyte1[] = (byte[])_fldif.get(j);
            System.arraycopy(abyte1, 0, abyte0, i, abyte1.length);
            i += abyte1.length;
        }

        System.arraycopy(_fldfor, 0, abyte0, i, _flddo);
        return abyte0;
    }

    public byte[] _mthif(int i)
    {
        byte abyte0[] = new byte[i];
        int j = 0;
        for(int k = 0; k < i;)
        {
            byte abyte1[] = (byte[])_fldif.get(j);
            if(i - k > abyte1.length)
            {
                System.arraycopy(abyte1, 0, abyte0, k, abyte1.length);
                k += abyte1.length;
            } else
            {
                System.arraycopy(abyte1, 0, abyte0, k, i - k);
                k = i;
            }
            j++;
        }

        return abyte0;
    }

    public void a(OutputStream outputstream)
        throws IOException
    {
        for(int i = 0; i < _fldif.size() - 1; i++)
        {
            byte abyte0[] = (byte[])_fldif.get(i);
            outputstream.write(abyte0);
        }

        if(_flddo > 0)
            outputstream.write(_fldfor, 0, _flddo);
    }

    public int a()
    {
        int i = _flddo;
        for(int j = 0; j < _fldif.size() - 1; j++)
            i += ((byte[])_fldif.get(j)).length;

        return i;
    }

    public byte a(int i)
    {
        int j = 0;
        byte abyte0[];
        for(abyte0 = (byte[])_fldif.get(0); i >= abyte0.length; abyte0 = (byte[])_fldif.get(j))
        {
            i -= abyte0.length;
            j++;
        }

        return abyte0[i];
    }

    private byte _fldfor[];
    private int _flddo;
    private Vector _fldif;
    private static final int a = 5120;
}