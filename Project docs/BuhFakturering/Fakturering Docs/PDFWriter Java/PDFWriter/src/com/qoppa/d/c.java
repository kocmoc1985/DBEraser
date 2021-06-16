// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:31
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.d;

import java.awt.*;
import javax.swing.text.*;

// Referenced classes of package com.qoppa.d:
//            d

public class c extends View
{

    public c(Element element, ViewFactory viewfactory)
    {
        View(element);
        a = viewfactory;
        _fldif = new d();
    }

    public ViewFactory getViewFactory()
    {
        return a;
    }

    public Container getContainer()
    {
        return _fldif;
    }

    public void paint(Graphics g, Shape shape)
    {
    }

    public float getPreferredSpan(int i)
    {
        return 0.0F;
    }

    public int viewToModel(float f, float f1, Shape shape, javax.swing.text.Position.Bias abias[])
    {
        return 0;
    }

    public Shape modelToView(int i, Shape shape, javax.swing.text.Position.Bias bias)
        throws BadLocationException
    {
        return shape;
    }

    ViewFactory a;
    private d _fldif;
}