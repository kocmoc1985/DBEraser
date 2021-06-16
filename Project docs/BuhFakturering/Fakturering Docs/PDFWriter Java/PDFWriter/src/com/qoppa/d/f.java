// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:31
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.d;

import com.qoppa.pdfWriter.PDFDocument;
import com.qoppa.pdfWriter.PDFPage;
import java.awt.*;
import java.awt.print.PageFormat;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.swing.text.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.rtf.RTFEditorKit;

// Referenced classes of package com.qoppa.d:
//            e, c, b

public class f
{

    public f()
    {
    }

    public void a(PDFDocument pdfdocument, InputStream inputstream, URL url, PageFormat pageformat, boolean flag)
        throws IOException, BadLocationException
    {
        _flddo = pdfdocument;
        _fldfor = pageformat;
        e e1 = new e();
        HTMLDocument htmldocument = (HTMLDocument)e1.createDefaultDocument();
        htmldocument.putProperty("IgnoreCharsetDirective", new Boolean(true));
        htmldocument.setBase(url);
        e1.read(inputstream, htmldocument, 0);
        javax.swing.text.Element element = htmldocument.getDefaultRootElement();
        c c1 = new c(element, e1.getViewFactory());
        View view = e1.getViewFactory().create(element);
        view.setParent(c1);
        _fldint = 1.0D;
        if(flag)
        {
            if((double)view.getPreferredSpan(0) > pageformat.getImageableWidth())
                _fldint = pageformat.getImageableWidth() / (double)view.getPreferredSpan(0);
            view.setSize(view.getPreferredSpan(0), (float)pageformat.getImageableHeight());
        } else
        {
            view.setSize((float)pageformat.getImageableWidth(), (float)pageformat.getImageableHeight());
        }
        PDFPage pdfpage = _flddo.createPage(_fldfor);
        _flddo.addPage(pdfpage);
        a = pdfpage.createGraphics();
        a.setClip((int)_fldfor.getImageableX(), (int)_fldfor.getImageableY(), (int)_fldfor.getImageableWidth(), (int)_fldfor.getImageableHeight());
        a.translate(_fldfor.getImageableX(), _fldfor.getImageableY());
        a.scale(_fldint, _fldint);
        _fldif = 0;
        Rectangle rectangle = new Rectangle(0, 0, (int)view.getPreferredSpan(0), (int)view.getPreferredSpan(1));
        _mthif(view, rectangle);
    }

    public void a(PDFDocument pdfdocument, InputStream inputstream, PageFormat pageformat)
        throws IOException, BadLocationException
    {
        _flddo = pdfdocument;
        _fldfor = pageformat;
        _fldint = 1.0D;
        RTFEditorKit rtfeditorkit = new RTFEditorKit();
        DefaultStyledDocument defaultstyleddocument = new DefaultStyledDocument();
        rtfeditorkit.read(inputstream, defaultstyleddocument, 0);
        javax.swing.text.Element element = defaultstyleddocument.getDefaultRootElement();
        c c1 = new c(element, rtfeditorkit.getViewFactory());
        View view = rtfeditorkit.getViewFactory().create(element);
        view.setParent(c1);
        view.setSize((float)_fldfor.getImageableWidth(), (float)_fldfor.getImageableHeight());
        PDFPage pdfpage = _flddo.createPage(_fldfor);
        _flddo.addPage(pdfpage);
        a = pdfpage.createGraphics();
        a.setClip((int)_fldfor.getImageableX(), (int)_fldfor.getImageableY(), (int)_fldfor.getImageableWidth(), (int)_fldfor.getImageableHeight());
        a.translate(_fldfor.getImageableX(), _fldfor.getImageableY());
        a.scale(_fldint, _fldint);
        _fldif = 0;
        Rectangle rectangle = new Rectangle(0, 0, (int)_fldfor.getImageableWidth(), (int)_fldfor.getImageableHeight());
        _mthdo(view, rectangle);
    }

    private void _mthif(View view, Shape shape)
    {
        int i = _fldif;
        int j = (int)Math.ceil((double)_fldif + _fldfor.getImageableHeight() / _fldint);
        int k = shape.getBounds().y;
        int l = k + shape.getBounds().height;
        if(l < i)
            return;
        for(; k > j; j = (int)Math.ceil((double)_fldif + _fldfor.getImageableHeight() / _fldint))
        {
            i += (int)Math.ceil((double)i + _fldfor.getImageableHeight() / _fldint);
            a(i);
        }

        if(l <= j)
            view.paint(a, shape);
        else
        if(view.getViewCount() == 0)
        {
            if(k > i)
            {
                a(k);
                _mthdo(view, shape);
            } else
            {
                view.paint(a, shape);
                a(j + 1);
                _mthdo(view, shape);
            }
        } else
        {
            int i1 = a(view, shape);
            if(i1 > _fldif)
            {
                Rectangle rectangle = new Rectangle(0, _fldif, (int)((_fldfor.getImageableWidth() + 4D) / _fldint), i1 - _fldif);
                a.clip(rectangle);
                view.paint(a, shape);
                a(j + 1);
                _mthdo(view, shape);
            }
        }
    }

    private int a(View view, Shape shape)
    {
        int i = _fldif;
        int j = (int)Math.ceil((double)_fldif + _fldfor.getImageableHeight() / _fldint);
        for(int k = 0; k < view.getViewCount(); k++)
        {
            Shape shape1 = view.getChildAllocation(k, shape);
            if(shape1 != null)
            {
                Rectangle rectangle = shape1.getBounds();
                if(rectangle.getMaxY() < (double)j)
                {
                    i = (int)Math.ceil(Math.max(rectangle.getMaxY(), i));
                } else
                {
                    View view1 = view.getView(k);
                    if(view1.getViewCount() > 0)
                        i = (int)Math.ceil(Math.max(a(view1, shape1), i));
                    else
                        return i;
                }
            }
        }

        return i;
    }

    private void _mthdo(View view, Shape shape)
    {
        int i = _fldif;
        int j = (int)Math.ceil((double)_fldif + _fldfor.getImageableHeight() / _fldint);
        int k = shape.getBounds().y;
        int l = k + shape.getBounds().height;
        if(l < i)
            return;
        for(; k > j; j = (int)Math.ceil((double)_fldif + _fldfor.getImageableHeight() / _fldint))
        {
            i += (int)Math.ceil((double)i + _fldfor.getImageableHeight() / _fldint);
            a(i);
        }

        if(l <= j)
            view.paint(a, shape);
        else
        if(view.getViewCount() > 0)
        {
            for(int i1 = 0; i1 < view.getViewCount(); i1++)
            {
                Shape shape1 = view.getChildAllocation(i1, shape);
                if(shape1 != null)
                {
                    View view1 = view.getView(i1);
                    _mthdo(view1, shape1);
                    if(view instanceof b)
                        ((b)view).a(a, shape1.getBounds(), i1);
                }
            }

        } else
        if(k > i)
        {
            a(k);
            _mthdo(view, shape);
        } else
        {
            view.paint(a, shape);
            a(j + 1);
            _mthdo(view, shape);
        }
    }

    private PDFPage a(int i)
    {
        PDFPage pdfpage = _flddo.createPage(_fldfor);
        _flddo.addPage(pdfpage);
        a = pdfpage.createGraphics();
        a.setClip((int)_fldfor.getImageableX() - 2, (int)_fldfor.getImageableY() - 2, (int)_fldfor.getImageableWidth() + 4, (int)_fldfor.getImageableHeight() + 4);
        a.translate(_fldfor.getImageableX(), _fldfor.getImageableY());
        _fldif = i;
        a.scale(_fldint, _fldint);
        a.translate(0, -_fldif);
        return pdfpage;
    }

    private PageFormat _fldfor;
    private PDFDocument _flddo;
    private int _fldif;
    private Graphics2D a;
    double _fldint;
}