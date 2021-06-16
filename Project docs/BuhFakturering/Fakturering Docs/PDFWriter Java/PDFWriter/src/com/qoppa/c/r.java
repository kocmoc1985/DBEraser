// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:30
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.c;

import com.qoppa.b.f;
import com.qoppa.b.m;
import java.awt.*;

// Referenced classes of package com.qoppa.c:
//            v, g

public class r extends m
{

    protected r(String s, Paint paint)
    {
        ac = s;
        af = paint;
        a("Type", new f("Pattern"));
    }

    public String _mthlong()
    {
        return ac;
    }

    public Paint _mthnull()
    {
        return af;
    }

    public static boolean a(Paint paint, Paint paint1)
    {
        if((paint instanceof GradientPaint) && (paint1 instanceof GradientPaint))
            return v.a((GradientPaint)paint, (GradientPaint)paint1);
        if((paint instanceof TexturePaint) && (paint1 instanceof TexturePaint))
            return g.a((TexturePaint)paint, (TexturePaint)paint1);
        else
            return paint.equals(paint1);
    }

    protected String ac;
    protected Paint af;
    protected static final String ae = "Type";
    protected static final String ad = "PatternType";
}