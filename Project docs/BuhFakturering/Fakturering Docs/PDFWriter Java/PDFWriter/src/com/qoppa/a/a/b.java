// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:25
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.a.a;

import com.qoppa.a.b.c;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.util.Hashtable;

// Referenced classes of package com.qoppa.a.a:
//            a

public class b
    implements ImageConsumer
{

    public b()
    {
    }

    public void a(Image image, a a1)
        throws c
    {
        _fldbyte = a1;
        if(image instanceof BufferedImage)
        {
            BufferedImage bufferedimage = (BufferedImage)image;
            _fldbyte.a(bufferedimage.getColorModel().hasAlpha());
            if(bufferedimage.getColorModel().getColorSpace().getType() == 6 && bufferedimage.getType() == 10)
            {
                setDimensions(bufferedimage.getWidth(), bufferedimage.getHeight());
                a(bufferedimage);
            } else
            {
                a(image.getSource());
            }
        } else
        {
            a(image.getSource());
        }
    }

    private synchronized void a(ImageProducer imageproducer)
        throws c
    {
        _fldif = imageproducer;
        _fldfor = true;
        imageproducer.startProduction(this);
        if(_fldfor)
            try
            {
                wait();
            }
            catch(InterruptedException interruptedexception)
            {
                throw new c("Error grabbing image.");
            }
        if(a != null)
            throw a;
        else
            return;
    }

    public synchronized void imageComplete(int i)
    {
        if(i == 1 || i == 4)
            a = new c("Error grabbing image.");
        _fldif.removeConsumer(this);
        _fldfor = false;
        notifyAll();
    }

    public void setColorModel(ColorModel colormodel)
    {
        _fldbyte.a(colormodel.hasAlpha());
    }

    public void setDimensions(int i, int j)
    {
        _fldbyte._mthif(i, j);
    }

    public void setHints(int i)
    {
    }

    public void setPixels(int i, int j, int k, int l, ColorModel colormodel, byte abyte0[], int i1, 
            int j1)
    {
        for(int k1 = 0; k1 < l; k1++)
        {
            for(int l1 = 0; l1 < k; l1++)
            {
                int i2 = abyte0[i1 + k1 * j1 + l1] & 0xff;
                float f = (float)colormodel.getRed(i2) * 0.3F + (float)colormodel.getGreen(i2) * 0.5F + (float)colormodel.getBlue(i2) * 0.2F;
                if(f < 188F)
                    _fldbyte._mthif(i + l1, j + k1, true);
                if(colormodel.hasAlpha() && colormodel.getAlpha(i2) < 128)
                    _fldbyte.a(i + l1, j + k1, true);
            }

        }

    }

    public void setPixels(int i, int j, int k, int l, ColorModel colormodel, int ai[], int i1, 
            int j1)
    {
        for(int k1 = 0; k1 < l; k1++)
        {
            for(int l1 = 0; l1 < k; l1++)
            {
                int i2 = ai[i1 + k1 * j1 + l1];
                float f = (float)colormodel.getRed(i2) * 0.3F + (float)colormodel.getGreen(i2) * 0.5F + (float)colormodel.getBlue(i2) * 0.2F;
                if(f < 188F)
                    _fldbyte._mthif(i + l1, j + k1, true);
                if(colormodel.hasAlpha() && colormodel.getAlpha(i2) < 128)
                    _fldbyte.a(i + l1, j + k1, true);
            }

        }

    }

    public void setProperties(Hashtable hashtable)
    {
    }

    private void a(BufferedImage bufferedimage)
    {
        byte abyte0[] = ((DataBufferByte)bufferedimage.getRaster().getDataBuffer()).getData();
        for(int i = 0; i < bufferedimage.getHeight(); i++)
        {
            int j = i * bufferedimage.getWidth();
            for(int k = 0; k < bufferedimage.getWidth(); k++)
                if((abyte0[j++] & 0xff) < 188)
                    _fldbyte._mthif(k, i, true);

        }

        if(bufferedimage.getColorModel().hasAlpha())
            _fldbyte._flddo = ((DataBufferByte)bufferedimage.getRaster().getDataBuffer()).getData();
    }

    private a _fldbyte;
    private ImageProducer _fldif;
    private static final float _flddo = 0.3F;
    private static final float _fldint = 0.5F;
    private static final float _fldtry = 0.2F;
    private static final int _fldnew = 188;
    private boolean _fldfor;
    private c a;
}