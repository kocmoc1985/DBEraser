// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:30
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.c;

import java.io.*;

public class m extends BufferedOutputStream
{

    public m(OutputStream outputstream)
    {
        super(outputstream);
        a = 0L;
    }

    public m(OutputStream outputstream, int i)
    {
        super(outputstream, i);
        a = 0L;
    }

    public long a()
    {
        return a;
    }

    public void write(byte abyte0[], int i, int j)
        throws IOException
    {
        a += j;
        super.write(abyte0, i, j);
    }

    public void write(int i)
        throws IOException
    {
        a++;
        super.write(i);
    }

    public void a(String s)
        throws IOException
    {
        write(s.getBytes(), 0, s.length());
    }

    private long a;
}