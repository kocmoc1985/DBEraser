// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:28
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.b;

import com.qoppa.c.m;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

// Referenced classes of package com.qoppa.b:
//            k, h

public class a extends k
{

    public a(double d)
    {
        aA = d;
    }

    public double f()
    {
        return aA;
    }

    public Object e()
    {
        return new Double(aA);
    }

    public void a(m m1, h h)
        throws IOException
    {
        m1.a(az.format(aA));
    }

    private double aA;
    private static final DecimalFormat az;

    static 
    {
        az = new DecimalFormat("0.0#######", new DecimalFormatSymbols(Locale.US));
    }
}