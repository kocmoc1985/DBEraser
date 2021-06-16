// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:32
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.pdfWriter.a;

import com.qoppa.c.d;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.text.CharacterIterator;

public class c extends FontMetrics
{

    public c a(float f)
    {
        c c1 = new c();
        c1._fldbyte = _fldbyte;
        c1._fldcase = _fldcase;
        c1._fldif = _fldif;
        c1._fldnew = _fldnew;
        c1._flddo = _flddo;
        c1._fldtry = _fldtry;
        c1._fldint = _fldint;
        c1.a = f;
        return c1;
    }

    protected c()
    {
        FontMetrics(null);
    }

    public int getAscent()
    {
        return (int)(((float)_fldbyte * a) / 1000F);
    }

    public int getDescent()
    {
        return (int)(((float)_fldcase * a) / 1000F);
    }

    public int getLeading()
    {
        return 0;
    }

    public int getMaxAdvance()
    {
        return (int)(((float)_flddo * a) / 1000F);
    }

    public int getMaxAscent()
    {
        return (int)(((float)_fldbyte * a) / 1000F);
    }

    public int getMaxDescent()
    {
        return (int)(((float)_fldcase * a) / 1000F);
    }

    private int a(int i)
    {
        if(i >= 0 && i <= 255)
            return _fldtry[i];
        else
            return 500;
    }

    public int charWidth(char c1)
    {
        c1 = d.a(c1);
        return (int)(((float)a(c1) * a) / 1000F);
    }

    public int charsWidth(char ac[], int i, int j)
    {
        int k = 0;
        ac = d.a(ac);
        for(int l = 0; l < j; l++)
            k += a(ac[l + i]);

        return (int)(((float)k * a) / 1000F);
    }

    private boolean a(String s)
    {
        for(int i = 0; i < s.length(); i++)
        {
            char c1 = s.charAt(i);
            if(c1 > '\377')
                return true;
        }

        return false;
    }

    public int stringWidth(String s)
    {
        int i = 0;
        String s1 = d.a(s);
        if(a(s1))
        {
            GlyphVector glyphvector = _fldint.layoutGlyphVector(_fldfor, s.toCharArray(), 0, s.length(), 0);
            return (int)(glyphvector.getLogicalBounds().getWidth() * (double)a + 0.5D);
        }
        for(int j = 0; j < s1.length(); j++)
            i += a(s1.charAt(j));

        return (int)(((float)i * a) / 1000F);
    }

    public Rectangle2D getStringBounds(char ac[], int i, int j, Graphics g)
    {
        int k = stringWidth(new String(ac, i, j - i));
        double d1 = (double)((float)_fldbyte * a) / 1000D;
        double d2 = (double)((float)_fldcase * a) / 1000D;
        return new Double(0.0D, -d2, k, d1 + d2);
    }

    public Rectangle2D getStringBounds(CharacterIterator characteriterator, int i, int j, Graphics g)
    {
        int k = characteriterator.getBeginIndex();
        int l = characteriterator.getEndIndex();
        if(i < k)
            throw new IndexOutOfBoundsException("beginIndex: " + i);
        if(j > l)
            throw new IndexOutOfBoundsException("limit: " + j);
        if(i > j)
            throw new IndexOutOfBoundsException("range length: " + (j - i));
        characteriterator.setIndex(i);
        char ac[] = new char[j - i];
        for(int i1 = 0; i1 < j - i; i1++)
        {
            ac[i1] = characteriterator.current();
            characteriterator.next();
        }

        int j1 = stringWidth(new String(ac));
        double d1 = (double)((float)_fldbyte * a) / 1000D;
        double d2 = (double)((float)_fldcase * a) / 1000D;
        return new Double(0.0D, -d2, j1, d1 + d2);
    }

    public Rectangle2D getStringBounds(String s, Graphics g)
    {
        int i = 0;
        for(int j = 0; j < s.length(); j++)
            i += a(d.a(s.charAt(j)));

        double d1 = (double)((float)_fldbyte * a) / 1000D;
        double d2 = (double)((float)_fldcase * a) / 1000D;
        return new Double(0.0D, -d2, ((float)i * a) / 1000F, d1 + d2);
    }

    public Rectangle2D getStringBounds(String s, int i, int j, Graphics g)
    {
        int k = 0;
        for(int l = 0; l < j; l++)
            k += a(d.a(s.charAt(l + i)));

        double d1 = (double)((float)_fldbyte * a) / 1000D;
        double d2 = (double)((float)_fldcase * a) / 1000D;
        return new Double(0.0D, -d2, ((float)k * a) / 1000F, d1 + d2);
    }

    protected int _fldbyte;
    protected int _fldcase;
    protected int _fldif;
    protected int _fldnew;
    protected int _flddo;
    protected int _fldtry[];
    protected Font _fldint;
    protected float a;
    private static final FontRenderContext _fldfor = new FontRenderContext(new AffineTransform(), true, true);

}