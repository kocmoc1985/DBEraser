// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:32
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.pdfWriter;

import com.qoppa.b.b;
import com.qoppa.c.*;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.image.renderable.RenderableImage;
import java.awt.print.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.AttributedCharacterIterator;
import java.text.Bidi;
import java.util.HashSet;
import java.util.Map;

// Referenced classes of package com.qoppa.pdfWriter:
//            PDFPage, ImageParam, PDFDocument

public class PDFGraphics extends Graphics2D
{

    protected PDFGraphics(b b1, PrinterJob printerjob, PDFPage pdfpage)
    {
        _fldbyte = pdfpage;
        _fldtry = _fldbyte._mthif().getPaper().getHeight();
        b = HELVETICA;
        a = Color.black;
        _fldvoid = a;
        _fldgoto = Color.white;
        _fldnew = new Area(new Double(pdfpage._mthif().getPaper().getImageableX(), pdfpage._mthif().getPaper().getImageableY(), pdfpage._mthif().getPaper().getImageableWidth(), pdfpage._mthif().getPaper().getImageableHeight()));
        c = pdfpage.a(1.0D, 1.0D);
        _flddo = new AffineTransform(pdfpage._mthif().getMatrix());
        _fldelse = new AffineTransform(pdfpage._mthif().getMatrix());
        _fldchar = new ImageParam();
        _fldif = printerjob;
        if(_fldlong == null)
            _fldlong = new BasicStroke();
        _fldnull = _fldlong;
        _fldint = AlphaComposite.SrcOver;
        _fldfor = new RenderingHints(null);
        _fldcase = b1;
    }

    public void addRenderingHints(Map map)
    {
        _fldfor.add(new RenderingHints(map));
    }

    public void clearRect(int i, int j, int i1, int j1)
    {
        a(new Double(i, j, i1, j1), _fldgoto, true, 0.0D, 0.0D);
    }

    public void clip(Shape shape)
    {
        Area area = (new Area(shape)).createTransformedArea(_fldelse);
        area.intersect(_fldnew);
        _fldnew = area;
    }

    public void clipRect(int i, int j, int i1, int j1)
    {
        Area area = (new Area(new Double(i, j, i1, j1))).createTransformedArea(_fldelse);
        area.intersect(_fldnew);
        _fldnew = area;
    }

    private boolean a(String s1)
    {
        for(int i = 0; i < s1.length(); i++)
        {
            char c1 = s1.charAt(i);
            if(c1 > '\377')
                return true;
        }

        return false;
    }

    public void copyArea(int i, int j, int i1, int j1, int k1, int l1)
    {
    }

    public Graphics create()
    {
        PDFGraphics pdfgraphics = new PDFGraphics(_fldcase, _fldif, _fldbyte);
        pdfgraphics.b = b;
        pdfgraphics._fldnull = _fldnull;
        pdfgraphics._fldint = _fldint;
        pdfgraphics.a = a;
        pdfgraphics._fldvoid = _fldvoid;
        pdfgraphics._fldgoto = _fldgoto;
        pdfgraphics._fldnew = _fldnew;
        pdfgraphics.c = c;
        pdfgraphics._fldfor = _fldfor;
        pdfgraphics._fldelse = (AffineTransform)_fldelse.clone();
        pdfgraphics._flddo = (AffineTransform)_flddo.clone();
        pdfgraphics._fldchar = (ImageParam)_fldchar.clone();
        return pdfgraphics;
    }

    public void dispose()
    {
        _fldbyte = null;
        b = null;
        a = null;
        _fldvoid = null;
        _fldgoto = null;
        _fldnew = null;
    }

    public void draw(Shape shape)
    {
        if(_fldnull != _fldlong)
        {
            shape = _fldnull.createStrokedShape(shape);
            a(shape, _fldvoid, true);
        } else
        {
            a(shape, _fldvoid, false);
        }
    }

    public void drawArc(int i, int j, int i1, int j1, int k1, int l1)
    {
        draw(new Double(i, j, i1, j1, k1, l1, 0));
    }

    public void drawGlyphVector(GlyphVector glyphvector, float f, float f1)
    {
        a(glyphvector.getOutline(f, f1), _fldvoid, true);
    }

    public void drawImage(BufferedImage bufferedimage, BufferedImageOp bufferedimageop, int i, int j)
    {
        BufferedImage bufferedimage1 = bufferedimageop.filter(bufferedimage, null);
        drawImage(((Image) (bufferedimage1)), i, j, ((ImageObserver) (null)));
    }

    public boolean drawImage(Image image, AffineTransform affinetransform, ImageObserver imageobserver)
    {
        AffineTransform affinetransform1 = new AffineTransform(_fldelse);
        affinetransform1.concatenate(affinetransform);
        AffineTransform affinetransform2 = _fldelse;
        _fldelse = affinetransform1;
        boolean flag = drawImage(image, 0, 0, imageobserver);
        _fldelse = affinetransform2;
        return flag;
    }

    public boolean drawImage(Image image, int i, int j, Color color, ImageObserver imageobserver)
    {
        try
        {
            q q1 = _fldbyte.a(image, _fldchar);
            a(image, q1, i, j, q1._flddo, q1._fldif, color);
        }
        catch(s s1)
        {
            throw new RuntimeException(s1.getMessage());
        }
        return false;
    }

    public boolean drawImage(Image image, int i, int j, ImageObserver imageobserver)
    {
        return drawImage(image, i, j, null, imageobserver);
    }

    public boolean drawImage(Image image, int i, int j, int i1, int j1, Color color, ImageObserver imageobserver)
    {
        try
        {
            q q1 = _fldbyte.a(image, _fldchar);
            a(image, q1, i, j, i1, j1, color);
        }
        catch(s s1)
        {
            throw new RuntimeException(s1.getMessage());
        }
        return true;
    }

    public boolean drawImage(Image image, int i, int j, int i1, int j1, int k1, int l1, 
            int i2, int j2, Color color, ImageObserver imageobserver)
    {
        int k2 = (i2 - k1) + 1;
        int l2 = (j2 - l1) + 1;
        BufferedImage bufferedimage = new BufferedImage(k2, l2, 2);
        Graphics2D graphics2d = bufferedimage.createGraphics();
        graphics2d.drawImage(image, 0, 0, k2, l2, k1, l1, i2, j2, color, imageobserver);
        graphics2d.dispose();
        int i3 = (i1 - i) + 1;
        int j3 = (j1 - j) + 1;
        try
        {
            q q1 = _fldbyte.a(bufferedimage, _fldchar);
            a(bufferedimage, q1, i, j, i3, j3, color);
        }
        catch(s s1)
        {
            throw new RuntimeException(s1.getMessage());
        }
        return true;
    }

    public boolean drawImage(Image image, int i, int j, int i1, int j1, int k1, int l1, 
            int i2, int j2, ImageObserver imageobserver)
    {
        return drawImage(image, i, j, i1, j1, k1, l1, i2, j2, null, imageobserver);
    }

    public boolean drawImage(Image image, int i, int j, int i1, int j1, ImageObserver imageobserver)
    {
        return drawImage(image, i, j, i1, j1, null, imageobserver);
    }

    public void drawLine(int i, int j, int i1, int j1)
    {
        draw(new Double(i, j, i1, j1));
    }

    public void drawOval(int i, int j, int i1, int j1)
    {
        draw(new Double(i, j, i1, j1, 0.0D, 360D, 0));
    }

    public void drawPolygon(int ai[], int ai1[], int i)
    {
        draw(new Polygon(ai, ai1, i));
    }

    public void drawPolyline(int ai[], int ai1[], int i)
    {
        for(int j = 0; j < i - 1; j++)
            drawLine(ai[j], ai1[j], ai[j + 1], ai1[j + 1]);

    }

    public void drawRect(int i, int j, int i1, int j1)
    {
        draw(new Rectangle(i, j, i1, j1));
    }

    public void drawRenderableImage(RenderableImage renderableimage, AffineTransform affinetransform)
    {
        BufferedImage bufferedimage = new BufferedImage((int)renderableimage.getWidth(), (int)renderableimage.getHeight(), 2);
        bufferedimage.createGraphics().drawImage(bufferedimage, 0, 0, null);
        drawImage(bufferedimage, 0, 0, ((ImageObserver) (null)));
    }

    public void drawRenderedImage(RenderedImage renderedimage, AffineTransform affinetransform)
    {
        BufferedImage bufferedimage = new BufferedImage(renderedimage.getWidth(), renderedimage.getHeight(), 2);
        bufferedimage.createGraphics().drawImage(bufferedimage, 0, 0, null);
        drawImage(bufferedimage, 0, 0, ((ImageObserver) (null)));
    }

    public void drawRoundRect(int i, int j, int i1, int j1, int k1, int l1)
    {
        draw(new Double(i, j, i1, j1, k1, l1));
    }

    public void drawString(String s1, float f, float f1)
    {
        try
        {
            a(_fldnew);
            String s2 = _fldbyte.a(b);
            String s4 = d.a(s1);
            if(s2 == null || a(s4))
            {
                FontRenderContext fontrendercontext = new FontRenderContext(_fldelse, true, true);
                Bidi bidi = new Bidi(s1, -2);
                if(!bidi.isMixed())
                {
                    int i = 0;
                    if(bidi.isRightToLeft())
                        i = 1;
                    GlyphVector glyphvector = b.layoutGlyphVector(fontrendercontext, s1.toCharArray(), 0, s1.length(), i);
                    drawGlyphVector(glyphvector, f, f1);
                } else
                {
                    int j = bidi.getRunCount();
                    for(int i1 = 0; i1 < j; i1++)
                    {
                        int j1 = bidi.getRunStart(i1);
                        int k1 = bidi.getRunLimit(i1);
                        int l1 = 0;
                        if(bidi.getRunLevel(i1) == 1)
                            l1 = 1;
                        GlyphVector glyphvector1 = b.layoutGlyphVector(fontrendercontext, s1.toCharArray(), j1, k1, l1);
                        drawGlyphVector(glyphvector1, f, f1);
                        f = (float)((double)f + glyphvector1.getVisualBounds().getWidth());
                    }

                }
            } else
            {
                a(s4, s2, f, f1, b);
            }
        }
        catch(s s3)
        {
            throw new RuntimeException(s3.getMessage());
        }
    }

    public void drawString(String s1, int i, int j)
    {
        drawString(s1, i, j);
    }

    public void drawString(AttributedCharacterIterator attributedcharacteriterator, float f, float f1)
    {
        a(_fldnew);
        String s1 = new String();
        for(char c1 = attributedcharacteriterator.first(); c1 != '\uFFFF'; c1 = attributedcharacteriterator.next())
            s1 = s1 + c1;

        attributedcharacteriterator.first();
        HashSet hashset = new HashSet();
        hashset.add(TextAttribute.FONT);
        hashset.add(TextAttribute.SIZE);
        hashset.add(TextAttribute.WEIGHT);
        hashset.add(TextAttribute.POSTURE);
        hashset.add(TextAttribute.FAMILY);
        do
        {
            int i = attributedcharacteriterator.getRunStart(hashset);
            int j = attributedcharacteriterator.getRunLimit(hashset);
            Font font = b;
            if(attributedcharacteriterator.getAttribute(TextAttribute.FONT) != null)
            {
                font = (Font)attributedcharacteriterator.getAttribute(TextAttribute.FONT);
            } else
            {
                if(attributedcharacteriterator.getAttribute(TextAttribute.FAMILY) != null)
                {
                    String s2 = (String)attributedcharacteriterator.getAttribute(TextAttribute.FAMILY);
                    font = new Font(s2, b.getStyle(), 12);
                    font = font.deriveFont(b.getSize2D());
                }
                if(attributedcharacteriterator.getAttribute(TextAttribute.SIZE) != null)
                    font = b.deriveFont(((Float)attributedcharacteriterator.getAttribute(TextAttribute.SIZE)).floatValue());
                if(attributedcharacteriterator.getAttribute(TextAttribute.WEIGHT) != null)
                {
                    Float float1 = (Float)attributedcharacteriterator.getAttribute(TextAttribute.WEIGHT);
                    if(float1.floatValue() >= TextAttribute.WEIGHT_DEMIBOLD.floatValue())
                        font = b.deriveFont(font.getStyle() | 1);
                    else
                        font = b.deriveFont(font.getStyle() & -2);
                }
                if(attributedcharacteriterator.getAttribute(TextAttribute.POSTURE) != null)
                {
                    float f2 = ((Float)attributedcharacteriterator.getAttribute(TextAttribute.POSTURE)).floatValue();
                    if(TextAttribute.POSTURE_OBLIQUE.floatValue() == f2)
                        font = b.deriveFont(font.getStyle() | 2);
                    else
                        font = b.deriveFont(font.getStyle() & -3);
                }
            }
            attributedcharacteriterator.setIndex(j);
            GlyphVector glyphvector = font.createGlyphVector(new FontRenderContext(_fldelse, true, true), s1.substring(i - attributedcharacteriterator.getBeginIndex(), j - attributedcharacteriterator.getBeginIndex()));
            drawGlyphVector(glyphvector, f, f1);
            f = (float)((double)f + glyphvector.getLogicalBounds().getWidth());
        } while(attributedcharacteriterator.getIndex() < attributedcharacteriterator.getEndIndex());
    }

    public void drawString(AttributedCharacterIterator attributedcharacteriterator, int i, int j)
    {
        drawString(attributedcharacteriterator, i, j);
    }

    public void fill(Shape shape)
    {
        a(shape, _fldvoid, true);
    }

    public void fillArc(int i, int j, int i1, int j1, int k1, int l1)
    {
        java.awt.geom.Arc2D.Double double1 = new Double(i, j, i1, j1, k1, l1, 2);
        a(double1, _fldvoid, true);
    }

    public void fillOval(int i, int j, int i1, int j1)
    {
        java.awt.geom.Arc2D.Double double1 = new Double(i, j, i1, j1, 0.0D, 360D, 0);
        a(double1, _fldvoid, true);
    }

    public void fillPolygon(int ai[], int ai1[], int i)
    {
        Polygon polygon = new Polygon(ai, ai1, i);
        a(polygon, _fldvoid, true);
    }

    public void fillRect(int i, int j, int i1, int j1)
    {
        java.awt.geom.Rectangle2D.Double double1 = new Double(i, j, i1, j1);
        a(double1, _fldvoid, true);
    }

    public void fillRoundRect(int i, int j, int i1, int j1, int k1, int l1)
    {
        java.awt.geom.RoundRectangle2D.Double double1 = new Double(i, j, i1, j1, k1, l1);
        a(double1, _fldvoid, true);
    }

    public void finalize()
    {
    }

    public Color getBackground()
    {
        return _fldgoto;
    }

    public Shape getClip()
    {
        try
        {
            return _fldnew.createTransformedArea(_fldelse.createInverse());
        }
        catch(NoninvertibleTransformException noninvertibletransformexception)
        {
            return null;
        }
    }

    public Rectangle getClipBounds()
    {
        try
        {
            return _fldnew.createTransformedArea(_fldelse.createInverse()).getBounds();
        }
        catch(NoninvertibleTransformException noninvertibletransformexception)
        {
            return null;
        }
    }

    public Color getColor()
    {
        return a;
    }

    public Composite getComposite()
    {
        return _fldint;
    }

    public GraphicsConfiguration getDeviceConfiguration()
    {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    }

    public Font getFont()
    {
        return b;
    }

    public FontMetrics getFontMetrics(Font font)
    {
        if(font instanceof a)
            return ((a)font).a();
        else
            return new l(font, new FontRenderContext(_fldelse, true, true));
    }

    public FontRenderContext getFontRenderContext()
    {
        return new FontRenderContext(_fldelse, true, true);
    }

    public Paint getPaint()
    {
        return _fldvoid;
    }

    public PrinterJob getPrinterJob()
    {
        return _fldif;
    }

    public Object getRenderingHint(java.awt.RenderingHints.Key key)
    {
        return _fldfor.get(key);
    }

    public RenderingHints getRenderingHints()
    {
        return _fldfor;
    }

    public Stroke getStroke()
    {
        return _fldnull;
    }

    public AffineTransform getTransform()
    {
        return (AffineTransform)_fldelse.clone();
    }

    public boolean hit(Rectangle rectangle, Shape shape, boolean flag)
    {
        return shape.intersects(new Double(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight()));
    }

    private void a(Area area)
    {
        if(area == null)
        {
            if(_fldcase.e != null)
            {
                _fldcase._mthcase();
                _fldcase.e = null;
            }
        } else
        if(!area.equals(_fldcase.e))
        {
            _fldcase._mthcase();
            if(area.isEmpty())
                _fldcase._mthdo("0 0 0 0 re\nW n\n");
            else
                a(area.getPathIterator(null), 0.0D, 0.0D, "W n");
            _fldcase.e = area;
        }
    }

    public void rotate(double d1)
    {
        AffineTransform affinetransform = new AffineTransform();
        affinetransform.setToRotation(d1);
        _fldelse.concatenate(affinetransform);
    }

    public void rotate(double d1, double d2, double d3)
    {
        AffineTransform affinetransform = new AffineTransform();
        affinetransform.setToRotation(d1, d2, d3);
        _fldelse.concatenate(affinetransform);
    }

    public void scale(double d1, double d2)
    {
        AffineTransform affinetransform = new AffineTransform();
        affinetransform.setToScale(d1, d2);
        _fldelse.concatenate(affinetransform);
    }

    public void setBackground(Color color)
    {
        _fldgoto = color;
    }

    public void setClip(int i, int j, int i1, int j1)
    {
        Area area = (new Area(new Double(i, j, i1, j1))).createTransformedArea(_fldelse);
        _fldnew = area;
    }

    public void setClip(Shape shape)
    {
        if(shape == null)
            shape = new Area(new Double(_fldbyte._mthif().getPaper().getImageableX(), _fldbyte._mthif().getPaper().getImageableY(), _fldbyte._mthif().getPaper().getImageableWidth(), _fldbyte._mthif().getPaper().getImageableHeight()));
        Area area = (new Area(shape)).createTransformedArea(_fldelse);
        _fldnew = area;
    }

    public void setColor(Color color)
    {
        a = color;
        _fldvoid = color;
    }

    public void setComposite(Composite composite)
    {
        _fldint = composite;
    }

    public void setFont(Font font)
    {
        b = font;
        if(b == null)
            b = HELVETICA;
    }

    public void setPaint(Paint paint)
    {
        _fldvoid = paint;
        if(paint instanceof Color)
            a = (Color)paint;
    }

    public void setPaintMode()
    {
    }

    public void setRenderingHint(java.awt.RenderingHints.Key key, Object obj)
    {
        if(obj == null)
            _fldfor.remove(key);
        else
            _fldfor.put(key, obj);
    }

    public void setRenderingHints(Map map)
    {
        _fldfor.clear();
        _fldfor.putAll(map);
    }

    public void setStroke(Stroke stroke)
    {
        if(stroke == null)
            _fldnull = _fldlong;
        else
            _fldnull = stroke;
    }

    public void setTransform(AffineTransform affinetransform)
    {
        _fldelse = (AffineTransform)affinetransform.clone();
    }

    public void setXORMode(Color color)
    {
    }

    public void shear(double d1, double d2)
    {
        AffineTransform affinetransform = new AffineTransform();
        affinetransform.setToShear(d1, d2);
        _fldelse.concatenate(affinetransform);
    }

    public void transform(AffineTransform affinetransform)
    {
        _fldelse.concatenate(affinetransform);
    }

    public void translate(double d1, double d2)
    {
        _fldelse.translate(d1, d2);
    }

    public void translate(int i, int j)
    {
        translate(i, j);
    }

    private void a(b b1, double d1)
    {
        if(d1 < 0.0D)
        {
            b1._mthdo("-");
            d1 = -d1;
        }
        long l1 = (long)d1;
        long l2 = Math.round((d1 - (double)l1) * 100D);
        if(l2 == 100L)
        {
            b1._mthdo(Long.toString(l1 + 1L));
        } else
        {
            b1._mthdo(Long.toString(l1));
            b1._mthdo(".");
            if(l2 < 10L)
                b1._mthdo("0");
            b1._mthdo(Long.toString(l2));
        }
    }

    private void a(Color color, String s1)
    {
        a(_fldcase, (double)color.getRed() / 255D);
        _fldcase._mthdo(" ");
        a(_fldcase, (double)color.getGreen() / 255D);
        _fldcase._mthdo(" ");
        a(_fldcase, (double)color.getBlue() / 255D);
        _fldcase._mthdo(" ");
        _fldcase._mthdo(s1);
        _fldcase._mthdo("\n");
    }

    private void a(Paint paint, Paint paint1)
    {
        double d1 = 1.0D;
        if(paint instanceof Color)
            d1 = (double)((Color)paint).getAlpha() / 255D;
        double d2 = 1.0D;
        if(paint1 instanceof Color)
            d2 = (double)((Color)paint1).getAlpha() / 255D;
        if((_fldint instanceof AlphaComposite) && ((AlphaComposite)_fldint).getRule() == 3)
        {
            d1 *= ((AlphaComposite)_fldint).getAlpha();
            d2 *= ((AlphaComposite)_fldint).getAlpha();
        }
        p p1 = _fldbyte.a(d1, d2);
        if(!c.equals(p1))
        {
            _fldcase._mthdo("/");
            _fldcase._mthdo(p1._mthchar());
            _fldcase._mthdo(" gs\n");
            c = p1;
        }
    }

    private void a(int i, Paint paint)
        throws s
    {
        if(i == 0)
        {
            if(!r.a(paint, _fldcase.g))
            {
                a(_fldcase.h, paint);
                if((paint instanceof GradientPaint) || (paint instanceof TexturePaint))
                {
                    String s1 = _fldbyte.a(paint);
                    _fldcase._mthdo("/Pattern cs\n");
                    _fldcase._mthdo("/");
                    _fldcase._mthdo(s1);
                    _fldcase._mthdo(" scn\n");
                } else
                if(paint instanceof Color)
                {
                    Color color = (Color)paint;
                    a(color, "rg");
                } else
                {
                    a(a, "rg");
                }
                _fldcase.g = paint;
            }
        } else
        if(!r.a(paint, _fldcase.h))
        {
            a(paint, _fldcase.g);
            if((paint instanceof GradientPaint) || (paint instanceof TexturePaint))
            {
                String s2 = _fldbyte.a(paint);
                _fldcase._mthdo("/Pattern CS\n");
                _fldcase._mthdo("/");
                _fldcase._mthdo(s2);
                _fldcase._mthdo(" SCN\n");
            } else
            if(paint instanceof Color)
            {
                Color color1 = (Color)paint;
                a(color1, "RG");
            } else
            {
                a(a, "RG");
            }
            _fldcase.h = paint;
        }
    }

    private void a(String s1, String s2, double d1, double d2, Font font)
    {
        try
        {
            a(0, _fldvoid);
        }
        catch(s s3)
        {
            throw new RuntimeException(s3.getMessage());
        }
        _fldcase._mthdo("BT\n");
        AffineTransform affinetransform = new AffineTransform(1.0D, 0.0D, 0.0D, -1D, 0.0D, _fldtry);
        affinetransform.concatenate(_fldelse);
        affinetransform.translate(d1, d2);
        affinetransform.concatenate(font.getTransform());
        affinetransform.scale(1.0D, -1D);
        a(_fldcase, affinetransform.getScaleX());
        _fldcase._mthdo(" ");
        a(_fldcase, affinetransform.getShearY());
        _fldcase._mthdo(" ");
        a(_fldcase, affinetransform.getShearX());
        _fldcase._mthdo(" ");
        a(_fldcase, affinetransform.getScaleY());
        _fldcase._mthdo(" ");
        a(_fldcase, affinetransform.getTranslateX());
        _fldcase._mthdo(" ");
        a(_fldcase, affinetransform.getTranslateY());
        _fldcase._mthdo(" Tm\n");
        _fldcase._mthdo("/" + s2 + " ");
        a(_fldcase, font.getSize2D());
        _fldcase._mthdo(" Tf\n");
        String s4 = k.a(s1, 0);
        _fldcase.a(_mthif(s4));
        _fldcase._mthdo(" Tj\n");
        _fldcase._mthdo("ET\n");
    }

    private byte[] _mthif(String s1)
    {
        byte abyte0[] = new byte[s1.length()];
        for(int i = 0; i < s1.length(); i++)
            abyte0[i] = (byte)s1.charAt(i);

        return abyte0;
    }

    private void a(Shape shape, Paint paint, boolean flag)
    {
        a(shape, paint, flag, 0.0D, 0.0D);
    }

    private void a(Shape shape, Paint paint, boolean flag, double d1, double d2)
    {
        try
        {
            a(_fldnew);
            String s1 = "S";
            if(flag)
            {
                s1 = "f";
                if(a(shape))
                    s1 = "f*";
                a(0, paint);
            } else
            {
                a(1, paint);
            }
            a(shape.getPathIterator(_fldelse), d1, d2, s1);
        }
        catch(s s2)
        {
            throw new RuntimeException(s2.getMessage());
        }
    }

    private boolean a(Shape shape)
    {
        try
        {
            Method method = shape.getClass().getMethod("getWindingRule", null);
            Integer integer = (Integer)method.invoke(shape, null);
            return integer.intValue() == 0;
        }
        catch(NoSuchMethodException nosuchmethodexception) { }
        catch(InvocationTargetException invocationtargetexception) { }
        catch(IllegalAccessException illegalaccessexception) { }
        return false;
    }

    private void a(Image image, q q1, int i, int j, int i1, int j1, Color color)
    {
        a(_fldnew);
        if(color != null)
        {
            java.awt.geom.Rectangle2D.Double double1 = new Double(i, j, i1, j1);
            a(((Shape) (double1)), ((Paint) (color)), true);
        }
        _fldcase._mthdo("q\n");
        AffineTransform affinetransform = new AffineTransform(1.0D, 0.0D, 0.0D, -1D, 0.0D, _fldtry);
        affinetransform.concatenate(_fldelse);
        affinetransform.concatenate(new AffineTransform(1.0F, 0.0F, 0.0F, 1.0F, i, j));
        affinetransform.concatenate(new AffineTransform(i1, 0.0F, 0.0F, -j1, 0.0F, j1));
        a(_fldcase, affinetransform.getScaleX());
        _fldcase._mthdo(" ");
        a(_fldcase, affinetransform.getShearY());
        _fldcase._mthdo(" ");
        a(_fldcase, affinetransform.getShearX());
        _fldcase._mthdo(" ");
        a(_fldcase, affinetransform.getScaleY());
        _fldcase._mthdo(" ");
        a(_fldcase, affinetransform.getTranslateX());
        _fldcase._mthdo(" ");
        a(_fldcase, affinetransform.getTranslateY());
        _fldcase._mthdo(" cm\n/");
        _fldcase._mthdo(q1.a);
        _fldcase._mthdo(" Do\nQ\n");
    }

    private void a(PathIterator pathiterator, double d1, double d2, String s1)
    {
        boolean flag = false;
        double ad[] = new double[6];
        double ad1[] = new double[2];
        ad1[0] = ad1[1] = 0.0D;
        for(; !pathiterator.isDone(); pathiterator.next())
        {
            int i = pathiterator.currentSegment(ad);
            ad[0] += d1;
            ad[1] += d2;
            ad[2] += d1;
            ad[3] += d2;
            ad[4] += d1;
            ad[5] += d2;
            if(flag && i != 0)
            {
                a(_fldcase, ad1[0]);
                _fldcase._mthdo(" ");
                a(_fldcase, _fldtry - ad1[1]);
                _fldcase._mthdo(" m\n");
            }
            flag = false;
            if(i == 0)
            {
                a(_fldcase, ad[0]);
                _fldcase._mthdo(" ");
                a(_fldcase, _fldtry - ad[1]);
                _fldcase._mthdo(" m\n");
                ad1[0] = ad[0];
                ad1[1] = ad[1];
            } else
            if(i == 1)
            {
                a(_fldcase, ad[0]);
                _fldcase._mthdo(" ");
                a(_fldcase, _fldtry - ad[1]);
                _fldcase._mthdo(" l\n");
            } else
            if(i == 3)
            {
                a(_fldcase, ad[0]);
                _fldcase._mthdo(" ");
                a(_fldcase, _fldtry - ad[1]);
                _fldcase._mthdo(" ");
                a(_fldcase, ad[2]);
                _fldcase._mthdo(" ");
                a(_fldcase, _fldtry - ad[3]);
                _fldcase._mthdo(" ");
                a(_fldcase, ad[4]);
                _fldcase._mthdo(" ");
                a(_fldcase, _fldtry - ad[5]);
                _fldcase._mthdo(" c\n");
            } else
            if(i == 2)
            {
                a(_fldcase, ad[0]);
                _fldcase._mthdo(" ");
                a(_fldcase, _fldtry - ad[1]);
                _fldcase._mthdo(" ");
                a(_fldcase, ad[2]);
                _fldcase._mthdo(" ");
                a(_fldcase, _fldtry - ad[3]);
                _fldcase._mthdo(" v\n");
            } else
            if(i == 4)
            {
                _fldcase._mthdo("h\n");
                flag = true;
            }
        }

        _fldcase._mthdo(s1 + "\n");
    }

    public PDFDocument getDocument()
    {
        return _fldbyte.getDocument();
    }

    public ImageParam getImageParams()
    {
        return _fldchar;
    }

    public void setImageParams(ImageParam imageparam)
    {
        _fldchar = imageparam;
    }

    private PDFPage _fldbyte;
    private double _fldtry;
    private b _fldcase;
    private Font b;
    private static Stroke _fldlong;
    private Stroke _fldnull;
    private Composite _fldint;
    private Color a;
    private Color _fldgoto;
    private Paint _fldvoid;
    private p c;
    private Area _fldnew;
    private ImageParam _fldchar;
    private PrinterJob _fldif;
    private RenderingHints _fldfor;
    private AffineTransform _flddo;
    private AffineTransform _fldelse;
    public static final Font HELVETICA = new a(2, 0, 12);
    public static final Font COURIER = new a(0, 0, 12);
    public static final Font TIMESROMAN = new a(1, 0, 12);
    public static final Font SYMBOL = new a(4, 0, 12);
    public static final Font ZAPFDINGBATS = new a(3, 0, 12);

}