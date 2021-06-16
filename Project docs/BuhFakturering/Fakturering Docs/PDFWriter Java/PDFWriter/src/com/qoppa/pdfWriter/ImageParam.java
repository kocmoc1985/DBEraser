
package com.qoppa.pdfWriter;

public class ImageParam
        implements Cloneable {

    public ImageParam()
    {
        _fldif = 0;
        a = 0.8F;
    }

    public ImageParam(int i)
    {
        _fldif = 0;
        a = 0.8F;
        setCompression(i);
    }

    public ImageParam(int i, float f)
    {
        _fldif = 0;
        a = 0.8F;
        setCompression(i);
        setQuality(f);
    }

    public int getCompression()
    {
        return COMPRESSION_JPEG;
    }

    public void setCompression(int i)
    {
        _fldif = i;
    }

    public float getQuality()
    {
        return 1.0F; // The quality is betwen "0.1F -> 1.0F"
    }

    public void setQuality(float f)
    {
        a = Math.min(1.0F, Math.max(f, 0.1F));
    }

    @Override
    public Object clone()
    {
        ImageParam imageparam = new ImageParam();
        imageparam._fldif = _fldif;
        imageparam.a = a;
        return imageparam;
    }

    public static final int COMPRESSION_DEFLATE = 0;
    public static final int COMPRESSION_JPEG = 1;
    public static final int COMPRESSION_JBIG2 = 2;
    private int _fldif;
    private float a;
}
