// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:31
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.d;

import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.text.Element;
import javax.swing.text.html.ListView;
import javax.swing.text.html.StyleSheet;

public class b extends ListView
{

    public b(Element element)
    {
        ListView(element);
    }

    public void a(Graphics g, Rectangle rectangle, int i)
    {
        a.paint(g, rectangle.x, rectangle.y, rectangle.width, rectangle.height, this, i);
    }

    protected void setPropertiesFromAttributes()
    {
        setPropertiesFromAttributes();
        a = getStyleSheet().getListPainter(getAttributes());
    }

    private javax.swing.text.html.StyleSheet.ListPainter a;
}