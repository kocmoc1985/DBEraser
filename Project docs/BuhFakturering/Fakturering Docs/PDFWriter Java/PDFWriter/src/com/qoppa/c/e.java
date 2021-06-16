// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:30
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.c;

import java.awt.Font;
import java.util.Hashtable;

public class e
{

    public e()
    {
    }

    public static String a(Font font)
    {
        Hashtable hashtable = a;
        switch(font.getStyle())
        {
        case 1: // '\001'
            hashtable = _fldfor;
            break;

        case 2: // '\002'
            hashtable = _flddo;
            break;

        case 3: // '\003'
            hashtable = _fldif;
            break;
        }
        return (String)hashtable.get(font.getFamily().toLowerCase());
    }

    private static Hashtable a;
    private static Hashtable _fldfor;
    private static Hashtable _flddo;
    private static Hashtable _fldif;

    static 
    {
        a = new Hashtable();
        a.put("helvetica", "Helvetica");
        a.put("courier", "Courier");
        a.put("times-roman", "Times-Roman");
        a.put("symbol", "Symbol");
        a.put("zapfdingbats", "ZapfDingbats");
        a.put("dialog", "Helvetica");
        a.put("dialoginput", "Courier");
        a.put("serif", "Times-Roman");
        a.put("sansserif", "Helvetica");
        a.put("monospaced", "Courier");
        a.put("timesroman", "Times-Roman");
        a.put("times new roman", "Times-Roman");
        a.put("arial", "Helvetica");
        _fldfor = new Hashtable();
        _fldfor.put("helvetica", "Helvetica-Bold");
        _fldfor.put("courier", "Courier-Bold");
        _fldfor.put("times-roman", "Times-Bold");
        _fldfor.put("symbol", "Symbol");
        _fldfor.put("zapfdingbats", "ZapfDingbats");
        _fldfor.put("dialog", "Helvetica-Bold");
        _fldfor.put("dialoginput", "Courier-Bold");
        _fldfor.put("serif", "Times-Bold");
        _fldfor.put("sansserif", "Helvetica-Bold");
        _fldfor.put("monospaced", "Courier-Bold");
        _fldfor.put("timesroman", "Times-Bold");
        _fldfor.put("times new roman", "Times-Bold");
        _fldfor.put("arial", "Helvetica-Bold");
        _flddo = new Hashtable();
        _flddo.put("helvetica", "Helvetica-Oblique");
        _flddo.put("courier", "Courier-Oblique");
        _flddo.put("times-roman", "Times-Italic");
        _flddo.put("symbol", "Symbol");
        _flddo.put("zapfdingbats", "ZapfDingbats");
        _flddo.put("dialog", "Helvetica-Oblique");
        _flddo.put("dialoginput", "Courier-Oblique");
        _flddo.put("serif", "Times-Italic");
        _flddo.put("sansserif", "Helvetica-Oblique");
        _flddo.put("monospaced", "Courier-Oblique");
        _flddo.put("timesroman", "Times-Italic");
        _flddo.put("times new roman", "Times-Italic");
        _flddo.put("arial", "Helvetica-Oblique");
        _fldif = new Hashtable();
        _fldif.put("helvetica", "Helvetica-BoldOblique");
        _fldif.put("courier", "Courier-BoldOblique");
        _fldif.put("times-roman", "Times-BoldItalic");
        _fldif.put("symbol", "Symbol");
        _fldif.put("zapfdingbats", "ZapfDingbats");
        _fldif.put("dialog", "Helvetica-BoldOblique");
        _fldif.put("dialoginput", "Courier-BoldOblique");
        _fldif.put("serif", "Times-BoldItalic");
        _fldif.put("sansserif", "Helvetica-BoldOblique");
        _fldif.put("monospaced", "Courier-BoldOblique");
        _fldif.put("timesroman", "Times-BoldItalic");
        _fldif.put("times new roman", "Times-BoldItalic");
        _fldif.put("arial", "Helvetica-BoldOblique");
    }
}