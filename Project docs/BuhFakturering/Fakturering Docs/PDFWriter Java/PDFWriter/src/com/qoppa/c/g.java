// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:30
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.c;

import com.qoppa.b.*;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;

// Referenced classes of package com.qoppa.c:
//            r, m

public class g extends r
{

    public g(String s, TexturePaint texturepaint, q q)
    {
        super(s, texturepaint);
        a("PatternType", new n(1));
        a("PaintType", new n(1));
        a("TilingType", new n(1));
        c c1 = new c();
        c1.a(new n(0));
        c1.a(new n(0));
        c1.a(new n(texturepaint.getImage().getWidth()));
        c1.a(new n(texturepaint.getImage().getHeight()));
        a("BBox", c1);
        a("XStep", new n(texturepaint.getImage().getWidth()));
        a("YStep", new n(texturepaint.getImage().getHeight()));
        a("Resources", a(texturepaint, q));
    }

    private m a(TexturePaint texturepaint, q q)
    {
        m m1 = new m();
        m1.a("Img1", q);
        m m2 = new m();
        m2.a("XObject", m1);
        return m2;
    }

    public static boolean a(TexturePaint texturepaint, TexturePaint texturepaint1)
    {
        return texturepaint.getAnchorRect().equals(texturepaint1.getAnchorRect()) && texturepaint.getImage() == texturepaint1.getImage();
    }

    public void a(com.qoppa.c.m m1, h h1)
        throws IOException
    {
        byte abyte0[] = _mthvoid();
        if(h1.a() != null)
            abyte0 = h1.a().a(_mthif().intValue(), abyte0);
        a("Length", ((com.qoppa.b.k) (new n(abyte0.length))));
        a("Filter", ((com.qoppa.b.k) (new f("FlateDecode"))));
        super.a(m1, h1);
        m1.a("\nstream\n");
        m1.write(abyte0);
        m1.a("\nendstream");
    }

    private byte[] _mthvoid()
        throws IOException
    {
        if(ag == null)
        {
            StringBuffer stringbuffer = new StringBuffer();
            stringbuffer.append("q\n");
            BufferedImage bufferedimage = ((TexturePaint)af).getImage();
            stringbuffer.append(bufferedimage.getWidth());
            stringbuffer.append(" 0 0 ");
            stringbuffer.append(bufferedimage.getHeight());
            stringbuffer.append(" 0 0 cm\n");
            stringbuffer.append("/Img1 Do\n");
            stringbuffer.append("Q\n");
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            DeflaterOutputStream deflateroutputstream = new DeflaterOutputStream(bytearrayoutputstream);
            deflateroutputstream.write(stringbuffer.toString().getBytes());
            deflateroutputstream.flush();
            deflateroutputstream.close();
            ag = bytearrayoutputstream.toByteArray();
        }
        return ag;
    }

    private static final String ah = "PaintType";
    private static final String al = "TilingType";
    private static final String ak = "BBox";
    private static final String aj = "XStep";
    private static final String am = "YStep";
    private static final String ai = "Resources";
    private byte ag[];
}