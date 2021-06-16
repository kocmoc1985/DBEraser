// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:31
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.d;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Locale;

public class d extends Container
{

    public d()
    {
        _fldif = new BufferedImage(1, 1, 1);
        a = _fldif.createGraphics();
        a.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    }

    public FontMetrics getFontMetrics(Font font)
    {
        return a.getFontMetrics(font);
    }

    public Locale getLocale()
    {
        return Locale.getDefault();
    }

    private BufferedImage _fldif;
    private Graphics2D a;
}