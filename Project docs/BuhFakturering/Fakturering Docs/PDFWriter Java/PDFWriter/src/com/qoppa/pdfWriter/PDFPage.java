// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:32
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.pdfWriter;

import com.qoppa.b.a;
import com.qoppa.b.b;
import com.qoppa.b.c;
import com.qoppa.b.f;
import com.qoppa.b.i;
import com.qoppa.b.m;
import com.qoppa.b.n;
import com.qoppa.b.q;
import com.qoppa.c.g;
import com.qoppa.c.k;
import com.qoppa.c.p;
import com.qoppa.c.r;
import com.qoppa.c.s;
import com.qoppa.c.v;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.TexturePaint;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.util.Hashtable;
import java.util.Vector;

// Referenced classes of package com.qoppa.pdfWriter:
//            PDFGraphics, PDFDocument, ImageParam

public class PDFPage
{

    protected PDFPage(PDFDocument pdfdocument, PageFormat pageformat, q q1)
    {
        _fldif = 1;
        _fldcase = new Hashtable();
        _fldlong = 1;
        _fldelse = new Hashtable();
        _fldfor = 1;
        _fldnew = 1;
        _fldint = false;
        _flddo = pdfdocument;
        _fldgoto = pageformat;
        _fldbyte = new m();
        _fldvoid = new m();
        _fldnull = new Vector();
    }

    protected PageFormat _mthif()
    {
        return _fldgoto;
    }

    protected int _mthdo()
    {
        return (int)_fldgoto.getPaper().getWidth();
    }

    protected int a()
    {
        return (int)_fldgoto.getPaper().getHeight();
    }

    protected m a(c c1, com.qoppa.b.k k1)
    {
        c c2 = new c();
        for(int j = 0; j < _fldnull.size(); j++)
        {
            b b1 = (b)_fldnull.get(j);
            c2.a(new q(b1));
        }

        c c3 = new c();
        c3.a(new n(0));
        c3.a(new n(0));
        c3.a(new a(_fldgoto.getPaper().getWidth()));
        c3.a(new a(_fldgoto.getPaper().getHeight()));
        m m1 = new m();
        m1.a("Font", _fldvoid);
        m1.a("XObject", _fldbyte);
        m1.a("ProcSet", new q(c1));
        if(_fldtry != null)
        {
            m m2 = new m();
            for(int l = 0; l < _fldtry.size(); l++)
            {
                p p1 = (p)_fldtry.get(l);
                m2.a(p1._mthchar(), new q(p1));
            }

            m1.a("ExtGState", m2);
        }
        if(_fldchar != null)
        {
            m m3 = new m();
            for(int i1 = 0; i1 < _fldchar.size(); i1++)
            {
                r r1 = (r)_fldchar.get(i1);
                m3.a(r1._mthlong(), new q(r1));
            }

            m1.a("Pattern", m3);
        }
        m m4 = new m();
        m4.a("Type", new f("Group"));
        m4.a("S", new f("Transparency"));
        m4.a("CS", new f("DeviceRGB"));
        m m5 = new m();
        m5.a("Type", new f("Page"));
        m5.a("Parent", new q(k1));
        m5.a("MediaBox", c3);
        m5.a("Contents", c2);
        m5.a("Resources", m1);
        m5.a("Group", m4);
        if(_fldint)
        {
            m m6 = new m();
            m6.a("Type", new f("Trans"));
            m6.a("D", new a(a));
            m6.a("S", new f(c));
            m5.a("Trans", m6);
            m5.a("Dur", new a(b));
        }
        if(_fldgoto.getOrientation() == 0)
            m5.a("Rotate", new n(90));
        else
        if(_fldgoto.getOrientation() == 2)
            m5.a("Rotate", new n(-90));
        return m5;
    }

    public Graphics2D createGraphics()
    {
        b b1 = new b();
        _fldnull.addElement(b1);
        return new PDFGraphics(b1, _flddo._mthif(), this);
    }

    com.qoppa.c.q a(Image image, ImageParam imageparam)
        throws s
    {
        q q1 = _flddo.a(image, imageparam);
        String s1 = a(q1);
        i j = (i)q1.d();
        com.qoppa.c.q q2 = new q();
        q2.a = s1;
        q2._flddo = k.a(j.a("Width"));
        q2._fldif = k.a(j.a("Height"));
        q2._fldfor = q1;
        return q2;
    }

    protected String a(Font font)
        throws s
    {
        q q1 = _flddo.a(font);
        if(q1 != null)
            return _mthif(q1);
        else
            return null;
    }

    private String a(q q1)
    {
        if(_fldelse.containsKey(q1.d()))
            return (String)_fldelse.get(q1.d());
        String s1;
        for(s1 = "Img" + _fldlong++; _fldbyte.a(s1) != null; s1 = "Img" + _fldif++);
        _fldbyte.a(s1, q1);
        _fldelse.put(q1.d(), s1);
        return s1;
    }

    private String _mthif(q q1)
    {
        if(_fldcase.containsKey(q1.d()))
            return (String)_fldcase.get(q1.d());
        String s1;
        for(s1 = "Font" + _fldif++; _fldvoid.a(s1) != null; s1 = "Font" + _fldif++);
        _fldvoid.a(s1, q1);
        _fldcase.put(q1.d(), s1);
        return s1;
    }

    public PDFDocument getDocument()
    {
        return _flddo;
    }

    protected String a(Paint paint)
        throws s
    {
        Object obj = null;
        if(_fldchar != null)
        {
            for(int j = 0; j < _fldchar.size(); j++)
            {
                r r1 = (r)_fldchar.get(j);
                if(!r.a(paint, r1._mthnull()))
                    continue;
                obj = r1;
                break;
            }

        } else
        {
            _fldchar = new Vector();
        }
        if(obj == null)
        {
            String s1 = "GP" + _fldnew;
            if(paint instanceof GradientPaint)
                obj = new v(s1, (GradientPaint)paint, _fldgoto.getPaper().getHeight());
            else
            if(paint instanceof TexturePaint)
            {
                q q1 = _flddo.a(((TexturePaint)paint).getImage(), new ImageParam());
                obj = new g(s1, (TexturePaint)paint, q1);
            }
            _fldnew++;
            _fldchar.add(obj);
        }
        return ((r) (obj))._mthlong();
    }

    protected p a(double d, double d1)
    {
        p p1 = null;
        if(_fldtry != null)
        {
            for(int j = 0; j < _fldtry.size(); j++)
            {
                p p2 = (p)_fldtry.get(j);
                if(p2._mthgoto() != d1 || p2._mthelse() != d)
                    continue;
                p1 = p2;
                break;
            }

        } else
        {
            _fldtry = new Vector();
        }
        if(p1 == null)
        {
            p1 = new p("GS" + _fldfor);
            p1._mthif(d1);
            p1.a(d);
            _fldfor++;
            _fldtry.add(p1);
        }
        return p1;
    }

    public void setTransitionParams(double d, double d1, String s1)
    {
        _fldint = true;
        b = d;
        a = d1;
        c = s1;
    }

    private PDFDocument _flddo;
    private PageFormat _fldgoto;
    private int _fldif;
    private Hashtable _fldcase;
    private int _fldlong;
    private Hashtable _fldelse;
    private int _fldfor;
    private int _fldnew;
    private m _fldbyte;
    private m _fldvoid;
    private Vector _fldnull;
    private Vector _fldtry;
    private Vector _fldchar;
    private boolean _fldint;
    private double b;
    private double a;
    private String c;
}