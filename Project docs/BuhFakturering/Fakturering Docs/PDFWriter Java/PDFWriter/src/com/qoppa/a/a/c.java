// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:25
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.a;

import java.io.IOException;
import java.io.OutputStream;

public class c
{

    public c()
    {
    }

    public static int a(byte abyte0[], int i, byte abyte1[], int j, int k)
    {
        for(int l = 0; l < k; l++)
            if(abyte0[i + l] != abyte1[j + l])
                return -1;

        return 0;
    }

    public static void a(byte abyte0[], int i, int j, int k)
    {
        if(k == 4)
        {
            abyte0[i] = (byte)(j >> 24 & 0xff);
            abyte0[i + 1] = (byte)(j >> 16 & 0xff);
            abyte0[i + 2] = (byte)(j >> 8 & 0xff);
            abyte0[i + 3] = (byte)(j & 0xff);
        } else
        if(k == 2)
        {
            abyte0[i] = (byte)(j >> 8 & 0xff);
            abyte0[i + 1] = (byte)(j & 0xff);
        } else
        {
            abyte0[i] = (byte)(j & 0xff);
        }
    }

    public static void a(OutputStream outputstream, int i, int j)
        throws IOException
    {
        if(j == 4)
        {
            outputstream.write(i >> 24 & 0xff);
            outputstream.write(i >> 16 & 0xff);
            outputstream.write(i >> 8 & 0xff);
            outputstream.write(i & 0xff);
        } else
        if(j == 2)
        {
            outputstream.write(i >> 8 & 0xff);
            outputstream.write(i & 0xff);
        } else
        {
            outputstream.write(i & 0xff);
        }
    }

    public static long a(byte abyte0[], int i)
    {
        try
        {
            return ((long)(abyte0[i] & 0xff) << 24) + (long)((abyte0[i + 1] & 0xff) << 16) + (long)((abyte0[i + 2] & 0xff) << 8) + (long)(abyte0[i + 3] & 0xff);
        }
        catch(ArrayIndexOutOfBoundsException arrayindexoutofboundsexception)
        {
            long l = 0L;
            if(i < abyte0.length)
                l = (abyte0[i] & 0xff) << 24;
            if(i + 1 < abyte0.length)
                l |= (abyte0[i + 1] & 0xff) << 16;
            if(i + 2 < abyte0.length)
                l |= (abyte0[i + 2] & 0xff) << 8;
            if(i + 3 < abyte0.length)
                l |= abyte0[i + 3] & 0xff;
            return l;
        }
    }

    public static int _mthif(byte abyte0[], int i)
    {
        return ((abyte0[i] & 0xff) << 8) + (abyte0[i + 1] & 0xff);
    }

    public static int a(boolean flag)
    {
        return flag ? 1 : 0;
    }

    public static boolean a(int i)
    {
        return i != 0;
    }

    public static long a(long l, int i)
    {
        return l << i & 0xffffffffL;
    }
}