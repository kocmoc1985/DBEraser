// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:31
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.d;

import javax.swing.text.*;
import javax.swing.text.html.*;

// Referenced classes of package com.qoppa.d:
//            b

public class h extends javax.swing.text.html.HTMLEditorKit.HTMLFactory
{

    public h()
    {
    }

    public View create(Element element)
    {
        Object obj = element.getAttributes().getAttribute(StyleConstants.NameAttribute);
        if(obj instanceof javax.swing.text.html.HTML.Tag)
        {
            javax.swing.text.html.HTML.Tag tag = (javax.swing.text.html.HTML.Tag)obj;
            if(tag == javax.swing.text.html.HTML.Tag.UL || tag == javax.swing.text.html.HTML.Tag.OL)
                return new b(element);
        }
        View view = create(element);
        if(view instanceof ImageView)
            ((ImageView)view).setLoadsSynchronously(true);
        return view;
    }
}