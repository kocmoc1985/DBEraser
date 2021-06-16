// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:31
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.d;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.html.*;

public class g extends SimpleAttributeSet
{

    public g()
    {
    }

    public g(AttributeSet attributeset)
    {
        SimpleAttributeSet(attributeset);
    }

    public AttributeSet a(int i)
    {
        switch(i)
        {
        case 1: // '\001'
            a();
            break;

        case 2: // '\002'
            _mthif();
            break;
        }
        return this;
    }

    private void a()
    {
        Object obj = getAttribute(javax.swing.text.html.CSS.Attribute.FONT_FAMILY);
        if(obj != null)
            if(obj.toString().equalsIgnoreCase("SansSerif"))
                addAttribute(javax.swing.text.html.CSS.Attribute.FONT_FAMILY, "SansSerif, Sans-Serif");
            else
            if(obj.toString().indexOf("Monospaced") > -1)
                addAttribute(javax.swing.text.html.CSS.Attribute.FONT_FAMILY, "Monospace, Monospaced");
    }

    private void _mthif()
    {
        Object obj = getAttribute(javax.swing.text.html.HTML.Attribute.FACE);
        Object obj1 = getAttribute(javax.swing.text.html.CSS.Attribute.FONT_FAMILY);
        if(obj != null)
        {
            if(obj1 != null)
            {
                removeAttribute(javax.swing.text.html.HTML.Attribute.FACE);
                if(obj1.toString().indexOf("Sans-Serif") > -1)
                    _flddo.addCSSAttribute(this, javax.swing.text.html.CSS.Attribute.FONT_FAMILY, "SansSerif");
                else
                if(obj1.toString().indexOf("Monospace") > -1)
                    _flddo.addCSSAttribute(this, javax.swing.text.html.CSS.Attribute.FONT_FAMILY, "Monospaced");
            } else
            {
                removeAttribute(javax.swing.text.html.HTML.Attribute.FACE);
                if(obj.toString().indexOf("Sans-Serif") > -1)
                    _flddo.addCSSAttribute(this, javax.swing.text.html.CSS.Attribute.FONT_FAMILY, "SansSerif");
                else
                if(obj.toString().indexOf("Monospace") > -1)
                    _flddo.addCSSAttribute(this, javax.swing.text.html.CSS.Attribute.FONT_FAMILY, "Monospaced");
                else
                    _flddo.addCSSAttribute(this, javax.swing.text.html.CSS.Attribute.FONT_FAMILY, obj.toString());
            }
        } else
        if(obj1 != null)
            if(obj1.toString().indexOf("Sans-Serif") > -1)
                _flddo.addCSSAttribute(this, javax.swing.text.html.CSS.Attribute.FONT_FAMILY, "SansSerif");
            else
            if(obj1.toString().indexOf("Monospace") > -1)
                _flddo.addCSSAttribute(this, javax.swing.text.html.CSS.Attribute.FONT_FAMILY, "Monospaced");
    }

    public static final int a = 1;
    public static final int _fldif = 2;
    private static StyleSheet _flddo = new StyleSheet();

}