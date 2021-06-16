// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:31
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.d;

import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.*;

// Referenced classes of package com.qoppa.d:
//            a, g

public class a$a extends javax.swing.text.html.Document.HTMLReader
{

    public void handleStartTag(javax.swing.text.html..Tag tag, MutableAttributeSet mutableattributeset, int i)
    {
        if(tag == javax.swing.text.html..Tag.SPAN)
        {
            if(mutableattributeset.isDefined(javax.swing.text.html..Attribute.STYLE))
            {
                String s = (String)mutableattributeset.getAttribute(javax.swing.text.html..Attribute.STYLE);
                mutableattributeset.removeAttribute(javax.swing.text.html..Attribute.STYLE);
                styleAttributes = getStyleSheet().getDeclaration(s);
                mutableattributeset.addAttributes(styleAttributes);
            } else
            {
                styleAttributes = null;
            }
            a a1 = a;
            if(a1 != null)
            {
                _fldif = true;
                a1.start(tag, mutableattributeset);
            }
        } else
        {
            handleStartTag(tag, mutableattributeset, i);
        }
    }

    public void handleSimpleTag(javax.swing.text.html..Tag tag, MutableAttributeSet mutableattributeset, int i)
    {
        if(tag == javax.swing.text.html..Tag.SPAN)
        {
            if(_fldif)
                handleEndTag(tag, i);
            else
                handleStartTag(tag, mutableattributeset, i);
        } else
        {
            handleSimpleTag(tag, mutableattributeset, i);
        }
    }

    public void handleEndTag(javax.swing.text.html..Tag tag, int i)
    {
        if(tag == javax.swing.text.html..Tag.SPAN)
        {
            a a1 = a;
            if(a1 != null)
            {
                _fldif = false;
                a1.end(tag);
            }
        } else
        {
            handleEndTag(tag, i);
        }
    }

    a a;
    AttributeSet styleAttributes;
    boolean _fldif;





    public a$a(int i)
    {
        super(com.qoppa.d.a.this, i, 0, 0, null);
        a = new a();
        _fldif = false;
    }
}