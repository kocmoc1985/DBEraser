// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:30
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.c;

import java.awt.Image;
import java.awt.image.*;
import java.util.Hashtable;

// Referenced classes of package com.qoppa.c:
//            s

public class c
    implements ImageConsumer
{

    public c()
    {
        _fldtry = false;
        _fldnew = -1;
        _fldint = -1;
        _fldbyte = false;
    }

    public synchronized void a(Image image)
        throws s
    {
        _fldfor = image.getSource();
        _fldtry = true;
        _fldfor.startProduction(this);
        if(_fldtry)
            try
            {
                wait();
            }
            catch(InterruptedException interruptedexception)
            {
                throw new s("Error grabbing image.");
            }
        if(_fldif != null)
            throw _fldif;
        else
            return;
    }

    public synchronized void imageComplete(int i)
    {
        if(i == 1 || i == 4)
            _fldif = new s("Error grabbing image.");
        _fldfor.removeConsumer(this);
        _fldtry = false;
        notifyAll();
    }

    public void setColorModel(ColorModel colormodel)
    {
        _fldbyte = colormodel.hasAlpha();
        if(_fldbyte)
            a = new DirectColorModel(32, 0xff0000, 65280, 255, 0xff000000);
        else
            a = new DirectColorModel(24, 0xff0000, 65280, 255);
    }

    public void setDimensions(int i, int j)
    {
        _fldnew = i;
        _fldint = j;
        _flddo = new int[i * j];
    }

    public void setHints(int i)
    {
    }

    public void setPixels(int i, int j, int k, int l, ColorModel colormodel, byte abyte0[], int i1, 
            int j1)
    {
        for(int k1 = 0; k1 < l; k1++)
        {
            int l1 = (j + k1) * _fldnew;
            for(int i2 = 0; i2 < k; i2++)
            {
                _flddo[l1] = colormodel.getRGB(abyte0[i1 + k1 * j1 + i2] & 0xff);
                l1++;
            }

        }

    }

    public void setPixels(int i, int j, int k, int l, ColorModel colormodel, int ai[], int i1, 
            int j1)
    {
        for(int k1 = 0; k1 < l; k1++)
        {
            int l1 = (j + k1) * _fldnew;
            for(int i2 = 0; i2 < k; i2++)
            {
                _flddo[l1] = colormodel.getRGB(ai[i1 + k1 * j1 + i2]);
                l1++;
            }

        }

    }

    public void setProperties(Hashtable hashtable)
    {
    }

    public int _mthif()
    {
        return _fldnew;
    }

    public int _mthint()
    {
        return _fldint;
    }

    public boolean _mthfor()
    {
        return _fldbyte;
    }

    public int[] _mthdo()
    {
        return _flddo;
    }

    public ColorModel a()
    {
        return a;
    }

    private boolean _fldtry;
    private s _fldif;
    private ImageProducer _fldfor;
    private int _fldnew;
    private int _fldint;
    private boolean _fldbyte;
    private int _flddo[];
    private ColorModel a;
}