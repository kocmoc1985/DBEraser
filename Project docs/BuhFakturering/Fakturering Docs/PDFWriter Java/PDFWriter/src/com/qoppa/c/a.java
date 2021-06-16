// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:29
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.c;

import com.qoppa.pdfWriter.a.b;
import com.qoppa.pdfWriter.a.c;
import com.qoppa.pdfWriter.a.d;
import com.qoppa.pdfWriter.a.e;
import com.qoppa.pdfWriter.a.f;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.font.TextAttribute;
import java.awt.font.TransformAttribute;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class com.qoppa.c.a extends Font
{
    private static class a
    {

        public String _fldnew;
        public c _fldint;
        public c a;
        public c _fldfor;
        public c _fldif;
        public String _flddo;
        public String _fldcase;
        public String _fldbyte;
        public String _fldtry;

        public a(String s)
        {
            _fldnew = s;
        }
    }


    public com.qoppa.c.a(int i, int j, int k)
    {
        super(_fldint[i]._fldnew, j, k);
        a = b._fldfor;
        _flddo = i;
        a(j);
    }

    public String getFamily()
    {
        return _fldint[_flddo]._fldnew;
    }

    public String getFontName()
    {
        if((getStyle() & 1) != 0 && (getStyle() & 2) != 0)
            return _fldint[_flddo]._fldtry;
        if((getStyle() & 1) != 0)
            return _fldint[_flddo]._fldcase;
        if((getStyle() & 2) != 0)
            return _fldint[_flddo]._fldbyte;
        else
            return _fldint[_flddo]._flddo;
    }

    public String getFontName(Locale locale)
    {
        return getFontName();
    }

    public String getName()
    {
        return getFontName();
    }

    public com.qoppa.c.a(Map map)
    {
        super(map);
        a = b._fldfor;
    }

    public boolean canDisplay(char c1)
    {
        return c1 <= '\377';
    }

    public FontMetrics a()
    {
        return a;
    }

    public Font deriveFont(AffineTransform affinetransform)
    {
        HashMap hashmap = new HashMap(getAttributes());
        a(hashmap, affinetransform);
        com.qoppa.c.a a1 = new com.qoppa.c.a(hashmap);
        a1._flddo = _flddo;
        a1.a(a1.getStyle());
        return a1;
    }

    private void a(HashMap hashmap, AffineTransform affinetransform)
    {
        if(affinetransform.isIdentity())
            hashmap.remove(TextAttribute.TRANSFORM);
        else
            hashmap.put(TextAttribute.TRANSFORM, new TransformAttribute(affinetransform));
    }

    public Font deriveFont(float f1)
    {
        HashMap hashmap = new HashMap(getAttributes());
        hashmap.put(TextAttribute.SIZE, new Float(f1));
        com.qoppa.c.a a1 = new com.qoppa.c.a(hashmap);
        a1._flddo = _flddo;
        a1.a(a1.getStyle());
        return a1;
    }

    public Font deriveFont(int i)
    {
        HashMap hashmap = new HashMap(getAttributes());
        a(hashmap, i);
        com.qoppa.c.a a1 = new com.qoppa.c.a(hashmap);
        a1._flddo = _flddo;
        a1.a(a1.getStyle());
        return a1;
    }

    private void a(HashMap hashmap, int i)
    {
        if((i & 1) != 0)
            hashmap.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        else
            hashmap.remove(TextAttribute.WEIGHT);
        if((i & 2) != 0)
            hashmap.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
        else
            hashmap.remove(TextAttribute.POSTURE);
    }

    public Font deriveFont(int i, AffineTransform affinetransform)
    {
        HashMap hashmap = new HashMap(getAttributes());
        a(hashmap, affinetransform);
        a(hashmap, i);
        com.qoppa.c.a a1 = new com.qoppa.c.a(hashmap);
        a1._flddo = _flddo;
        a1.a(a1.getStyle());
        return a1;
    }

    public Font deriveFont(int i, float f1)
    {
        HashMap hashmap = new HashMap(getAttributes());
        a(hashmap, i);
        hashmap.put(TextAttribute.SIZE, new Float(f1));
        com.qoppa.c.a a1 = new com.qoppa.c.a(hashmap);
        a1._flddo = _flddo;
        a1.a(a1.getStyle());
        return a1;
    }

    public Font deriveFont(Map map)
    {
        com.qoppa.c.a a1 = new com.qoppa.c.a(new HashMap(map));
        a1._flddo = _flddo;
        a1.a(a1.getStyle());
        return a1;
    }

    private void a(int i)
    {
        if((i & 1) != 0 && (i & 2) != 0)
            a = _fldint[_flddo]._fldif.a(getSize2D());
        else
        if((i & 1) != 0)
            a = _fldint[_flddo].a.a(getSize2D());
        else
        if((i & 2) != 0)
            a = _fldint[_flddo]._fldfor.a(getSize2D());
        else
            a = _fldint[_flddo]._fldint.a(getSize2D());
    }

    private int _flddo;
    public c a;
    public static final int _fldif = 0;
    public static final int _fldtry = 1;
    public static final int _fldnew = 2;
    public static final int _fldfor = 3;
    public static final int _fldbyte = 4;
    private static final a _fldint[];

    static 
    {
        _fldint = new a[5];
        _fldint[0] = new a("Courier");
        _fldint[0]._fldint = b._fldfor;
        _fldint[0].a = b._fldif;
        _fldint[0]._fldfor = b.a;
        _fldint[0]._fldif = b._flddo;
        _fldint[0]._flddo = "Courier";
        _fldint[0]._fldcase = "Courier-Oblique";
        _fldint[0]._fldbyte = "Courier-Bold";
        _fldint[0]._fldtry = "Courier-BoldOblique";
        _fldint[1] = new a("Times-Roman");
        _fldint[1]._fldint = e._fldfor;
        _fldint[1].a = e._flddo;
        _fldint[1]._fldfor = e._fldif;
        _fldint[1]._fldif = e.a;
        _fldint[1]._flddo = "Times-Roman";
        _fldint[1]._fldcase = "Times-Bold";
        _fldint[1]._fldbyte = "Times-Italic";
        _fldint[1]._fldtry = "Times-BoldItalic";
        _fldint[2] = new a("Helvetica");
        _fldint[2]._fldint = d._fldif;
        _fldint[2].a = d._flddo;
        _fldint[2]._fldfor = d._fldfor;
        _fldint[2]._fldif = d.a;
        _fldint[2]._flddo = "Helvetica";
        _fldint[2]._fldcase = "Helvetica-Bold";
        _fldint[2]._fldbyte = "Helvetica-Oblique";
        _fldint[2]._fldtry = "Helvetica-BoldOblique";
        _fldint[4] = new a("Symbol");
        _fldint[4]._fldint = com.qoppa.pdfWriter.a.a._flddo;
        _fldint[4].a = com.qoppa.pdfWriter.a.a._fldif;
        _fldint[4]._fldfor = com.qoppa.pdfWriter.a.a._fldfor;
        _fldint[4]._fldif = com.qoppa.pdfWriter.a.a.a;
        _fldint[4]._flddo = "Symbol";
        _fldint[4]._fldcase = "Symbol";
        _fldint[4]._fldbyte = "Symbol";
        _fldint[4]._fldtry = "Symbol";
        _fldint[3] = new a("ZapfDingbats");
        _fldint[3]._fldint = f._fldif;
        _fldint[3].a = f.a;
        _fldint[3]._fldfor = f._fldfor;
        _fldint[3]._fldif = f._flddo;
        _fldint[3]._flddo = "ZapfDingbats";
        _fldint[3]._fldcase = "ZapfDingbats";
        _fldint[3]._fldbyte = "ZapfDingbats";
        _fldint[3]._fldtry = "ZapfDingbats";
    }
}