// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:31
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.d;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;

// Referenced classes of package com.qoppa.d:
//            g, a

class a$a$a extends javax.swing.text.html.cument.HTMLReader.CharacterAction
{

    public void start(javax.swing.text.html.ag ag, MutableAttributeSet mutableattributeset)
    {
        a.a.access$0(a.a.this);
        if(mutableattributeset.isDefined(a.a.IMPLIED))
            mutableattributeset.removeAttribute(a.a.IMPLIED);
        a.a.access$1(a.a.this).addAttribute(ag, mutableattributeset.copyAttributes());
        if(styleAttributes != null)
            a.a.access$1(a.a.this).addAttributes(styleAttributes);
        if(a.a.access$1(a.a.this).isDefined(javax.swing.text.html.ag.SPAN))
            a.a.access$1(a.a.this).removeAttribute(javax.swing.text.html.ag.SPAN);
        a.a.access$2(a.a.this, (MutableAttributeSet)(new g(a.a.access$1(a.a.this))).a(2));
    }

    public void end(javax.swing.text.html.ag ag)
    {
        a.a.access$3(a.a.this);
    }

    a$a$a()
    {
        super(a.a.this);
    }
}