// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:30
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.c;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;

public class l extends FontMetrics
{

    public l(Font font, FontRenderContext fontrendercontext)
    {
        super(font);
        a = font;
        _fldif = fontrendercontext;
        LineMetrics linemetrics = font.getLineMetrics(_fldfor, 0, _fldfor.length, fontrendercontext);
        _flddo = (int)linemetrics.getAscent();
        _fldnew = (int)linemetrics.getDescent();
        _fldint = (int)linemetrics.getLeading();
    }

    public int stringWidth(String s)
    {
        return (int)a.getStringBounds(s, _fldif).getWidth();
    }

    public int getAscent()
    {
        return _flddo;
    }

    public int getMaxAscent()
    {
        return _flddo;
    }

    public int getDescent()
    {
        return _fldnew;
    }

    public int getMaxDescent()
    {
        return _fldnew;
    }

    public int getLeading()
    {
        return _fldint;
    }

    public int getMaxAdvance()
    {
        return (int)a.getMaxCharBounds(_fldif).getWidth();
    }

    public int charWidth(char c)
    {
        return (int)a.getStringBounds((new Character(c)).toString(), _fldif).getWidth();
    }

    public int charsWidth(char ac[], int i, int j)
    {
        return (int)a.getStringBounds(ac, i, i + j, _fldif).getWidth();
    }

    private Font a;
    private FontRenderContext _fldif;
    private int _flddo;
    private int _fldnew;
    private int _fldint;
    private static final char _fldfor[];

    static 
    {
        _fldfor = new char[256];
        for(int i = 0; i < 256; i++)
            _fldfor[i] = (char)i;

    }
}