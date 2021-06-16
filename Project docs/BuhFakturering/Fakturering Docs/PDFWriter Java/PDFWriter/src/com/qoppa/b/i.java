// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:29
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.b;

import com.qoppa.a.a.a;
import com.qoppa.a.b.g;
import com.qoppa.c.c;
import com.qoppa.c.m;
import com.qoppa.c.s;
import com.qoppa.pdfWriter.ImageParam;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;

// Referenced classes of package com.qoppa.b:
//            m, f, n, q, 
//            d, h, l

public class i extends com.qoppa.b.m
{

    private i(int i1)
    {
    }

    public i(Image image, ImageParam imageparam)
        throws s
    {
        try
        {
            a("Type", new f("XObject"));
            a("Subtype", new f("Image"));
            if(imageparam.getCompression() != 2)
            {
                c c1 = new c();
                c1.a(image);
                a("Width", new n(c1._mthif()));
                a("Height", new n(c1._mthint()));
                if(imageparam.getCompression() == 1)
                {
                    a("ColorSpace", new f("DeviceRGB"));
                    a("BitsPerComponent", new n(8));
                    a("Filter", new f("DCTDecode"));
                    t = a(c1, imageparam.getQuality());
                } else
                {
                    a("ColorSpace", new f("DeviceRGB"));
                    a("BitsPerComponent", new n(8));
                    a("Filter", new f("FlateDecode"));
                    t = _mthif(c1);
                }
                a("Length", new n(t.length));
                if(c1._mthfor())
                {
                    i i1 = a(a(c1), c1._mthif(), c1._mthint());
                    a("Mask", new q(i1));
                }
            } else
            {
                a("ColorSpace", new f("DeviceGray"));
                a("BitsPerComponent", new n(1));
                a("Filter", new f("JBIG2Decode"));
                a a1 = new a(image);
                ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
                g.a(bytearrayoutputstream, a1, 72, 72, true, false);
                a("Width", new n(a1._mthnew()));
                a("Height", new n(a1._mthtry()));
                t = bytearrayoutputstream.toByteArray();
                a("Length", new n(t.length));
                if(a1._mthdo())
                {
                    ByteArrayOutputStream bytearrayoutputstream1 = new ByteArrayOutputStream();
                    DeflaterOutputStream deflateroutputstream = new DeflaterOutputStream(bytearrayoutputstream1);
                    deflateroutputstream.write(a1._flddo);
                    deflateroutputstream.finish();
                    byte abyte0[] = bytearrayoutputstream1.toByteArray();
                    i j1 = a(abyte0, a1._mthnew(), a1._mthtry());
                    a("Mask", new q(j1));
                }
            }
        }
        catch(IOException ioexception)
        {
            throw new s("Error get image pixels.");
        }
        catch(com.qoppa.a.b.c c2)
        {
            throw new s("Error encoding JBIG2 image: " + c2.getMessage());
        }
    }

    private byte[] a(c c1, float f1)
        throws s
    {
        DirectColorModel directcolormodel = new DirectColorModel(24, 0xff0000, 65280, 255);
        DataBufferInt databufferint = new DataBufferInt(c1._mthdo(), c1._mthdo().length);
        java.awt.image.WritableRaster writableraster = Raster.createPackedRaster(databufferint, c1._mthif(), c1._mthint(), c1._mthif(), new int[] {
            0xff0000, 65280, 255
        }, null);
        BufferedImage bufferedimage = new BufferedImage(directcolormodel, writableraster, true, null);
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        JPEGImageEncoder jpegimageencoder = JPEGCodec.createJPEGEncoder(bytearrayoutputstream);
        JPEGEncodeParam jpegencodeparam = JPEGCodec.getDefaultJPEGEncodeParam(bufferedimage);
        jpegencodeparam.setQuality(f1, true);
        try
        {
            jpegimageencoder.encode(bufferedimage, jpegencodeparam);
            return bytearrayoutputstream.toByteArray();
        }
        catch(IOException ioexception)
        {
            throw new s("Error encoding JPEG image: " + ioexception.getMessage());
        }
    }

    private i a(byte abyte0[], int i1, int j1)
    {
        i k1 = new i(0);
        k1.t = abyte0;
        k1.a("Type", ((k) (new f("XObject"))));
        k1.a("Subtype", ((k) (new f("Image"))));
        k1.a("ImageMask", ((k) (new d(true))));
        k1.a("Width", ((k) (new n(i1))));
        k1.a("Height", ((k) (new n(j1))));
        k1.a("Length", ((k) (new n(k1.t.length))));
        k1.a("Filter", ((k) (new f("FlateDecode"))));
        k1.a("ColorSpace", ((k) (new f("DeviceGray"))));
        k1.a("BitsPerComponent", ((k) (new n(1))));
        return k1;
    }

    private byte[] a(c c1)
        throws IOException
    {
        int ai[] = c1._mthdo();
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        DeflaterOutputStream deflateroutputstream = new DeflaterOutputStream(bytearrayoutputstream);
        for(int i1 = 0; i1 < c1._mthint(); i1++)
        {
            int j1 = i1 * c1._mthif();
            for(int k1 = 0; k1 < c1._mthif(); k1 += 8)
            {
                int l1 = 0;
                int i2;
                for(i2 = 0; i2 < 8 && k1 + i2 < c1._mthif(); i2++)
                {
                    int j2 = ai[j1 + k1 + i2] >> 24 & 0xff;
                    l1 <<= 1;
                    if(j2 < 128)
                        l1 |= 1;
                }

                l1 <<= 8 - i2;
                deflateroutputstream.write(l1);
            }

        }

        deflateroutputstream.finish();
        return bytearrayoutputstream.toByteArray();
    }

    private byte[] _mthif(c c1)
        throws IOException
    {
        int ai[] = c1._mthdo();
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        DeflaterOutputStream deflateroutputstream = new DeflaterOutputStream(bytearrayoutputstream);
        for(int i1 = 0; i1 < c1._mthif() * c1._mthint(); i1++)
        {
            int j1 = ai[i1] >> 16 & 0xff;
            int k1 = ai[i1] >> 8 & 0xff;
            int l1 = ai[i1] & 0xff;
            deflateroutputstream.write(j1);
            deflateroutputstream.write(k1);
            deflateroutputstream.write(l1);
        }

        deflateroutputstream.finish();
        return bytearrayoutputstream.toByteArray();
    }

    public void a(m m1, h h1)
        throws IOException
    {
        super.a(m1, h1);
        byte abyte0[] = t;
        if(h1.a() != null)
            abyte0 = h1.a().a(_mthif().intValue(), t);
        m1.a("\nstream\n");
        m1.write(abyte0);
        m1.a("\nendstream");
    }

    public static final int m = 0;
    public static final int l = 1;
    public static final int B = 2;
    private static final String C = "Type";
    private static final String r = "Subtype";
    private static final String n = "Width";
    private static final String p = "Height";
    private static final String A = "Length";
    private static final String z = "BitsPerComponent";
    private static final String D = "XObject";
    private static final String k = "Image";
    private static final String j = "ImageMask";
    private static final String s = "Mask";
    private static final String o = "Filter";
    private static final String u = "FlateDecode";
    private static final String y = "DCTDecode";
    private static final String x = "JBIG2Decode";
    private static final String w = "ColorSpace";
    private static final String q = "DeviceRGB";
    private static final String v = "DeviceGray";
    protected byte t[];
}