// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:31
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.d;

import javax.swing.text.Document;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

// Referenced classes of package com.qoppa.d:
//            h, a

public class e extends HTMLEditorKit
{

    public e()
    {
    }

    public Document createDefaultDocument()
    {
        StyleSheet stylesheet = new StyleSheet();
        try
        {
            stylesheet.importStyleSheet(Class.forName("javax.swing.text.html.HTMLEditorKit").getResource("default.css"));
        }
        catch(Exception exception) { }
        a a1 = new a(stylesheet);
        a1.setParser(getParser());
        a1.setAsynchronousLoadPriority(4);
        a1.setTokenThreshold(100);
        return a1;
    }

    public ViewFactory getViewFactory()
    {
        return a;
    }

    private static final h a = new h();

}