// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:31
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.c;

import com.qoppa.b.*;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.geom.Point2D;

// Referenced classes of package com.qoppa.c:
//            r

public class v extends r
{

    public v(String s, GradientPaint gradientpaint, double d1)
    {
        super(s, gradientpaint);
        a("PatternType", new n(2));
        a("Shading", a(gradientpaint, d1));
    }

    private m a(GradientPaint gradientpaint, double d1)
    {
        GradientPaint gradientpaint1 = (GradientPaint)this.af;
        m m1 = new m();
        m1.a("ShadingType", new n(2));
        m1.a("ColorSpace", new f("DeviceRGB"));
        c c1 = new c();
        c1.a(new a(gradientpaint1.getPoint1().getX()));
        c1.a(new a(d1 - gradientpaint1.getPoint1().getY()));
        c1.a(new a(gradientpaint1.getPoint2().getX()));
        c1.a(new a(d1 - gradientpaint1.getPoint2().getY()));
        m1.a("Coords", c1);
        m m2 = new m();
        m2.a("FunctionType", new n(2));
        c c2 = new c();
        c2.a(new n(0));
        c2.a(new n(1));
        m2.a("Domain", c2);
        c c3 = new c();
        float af[] = gradientpaint1.getColor1().getComponents(null);
        c3.a(new a(af[0]));
        c3.a(new a(af[1]));
        c3.a(new a(af[2]));
        m2.a("C0", c3);
        c c4 = new c();
        af = gradientpaint1.getColor2().getComponents(null);
        c4.a(new a(af[0]));
        c4.a(new a(af[1]));
        c4.a(new a(af[2]));
        m2.a("C1", c4);
        m2.a("N", new n(1));
        m1.a("Function", m2);
        c c5 = new c();
        c5.a(new d(true));
        c5.a(new d(true));
        m1.a("Extend", c5);
        return m1;
    }

    protected static boolean a(GradientPaint gradientpaint, GradientPaint gradientpaint1)
    {
        return gradientpaint.getColor1().equals(gradientpaint1.getColor1()) && gradientpaint.getColor2().equals(gradientpaint1.getColor2()) && gradientpaint.getPoint1().equals(gradientpaint1.getPoint1()) && gradientpaint.getPoint2().equals(gradientpaint1.getPoint2());
    }

    private static final String ar = "Shading";
    private static final String an = "ShadingType";
    private static final String ap = "ColorSpace";
    private static final String ao = "Coords";
    private static final String aq = "Extend";
}