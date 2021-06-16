// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:31
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.pdfWriter;

import java.util.Date;

// Referenced classes of package com.qoppa.pdfWriter:
//            PDFDocument

public class DocumentInfo
{

    public DocumentInfo()
    {
        _fldtry = new Date();
        _fldint = _fldtry;
        _fldfor = PDFDocument.getVersion() + " - http://www.qoppa.com";
    }

    public void setTitle(String s)
    {
        _fldnew = s;
    }

    public void setAuthor(String s)
    {
        _flddo = s;
    }

    public void setCreationDate(Date date)
    {
        _fldtry = date;
    }

    public void setCreator(String s)
    {
        _fldbyte = s;
    }

    public void setKeywords(String s)
    {
        a = s;
    }

    public void setModifiedDate(Date date)
    {
        _fldint = date;
    }

    public void setProducer(String s)
    {
        _fldfor = s;
    }

    public void setSubject(String s)
    {
        _fldif = s;
    }

    public Date getModDate()
    {
        return _fldint;
    }

    public void setModDate(Date date)
    {
        _fldint = date;
    }

    public String getAuthor()
    {
        return _flddo;
    }

    public Date getCreationDate()
    {
        return _fldtry;
    }

    public String getCreator()
    {
        return _fldbyte;
    }

    public String getKeywords()
    {
        return a;
    }

    public String getProducer()
    {
        return _fldfor;
    }

    public String getSubject()
    {
        return _fldif;
    }

    public String getTitle()
    {
        return _fldnew;
    }

    private String _fldnew;
    private String _flddo;
    private String _fldif;
    private String a;
    private String _fldbyte;
    private String _fldfor;
    private Date _fldtry;
    private Date _fldint;
    public static final String KEY_TITLE = "Title";
    public static final String KEY_AUTHOR = "Author";
    public static final String KEY_SUBJECT = "Subject";
    public static final String KEY_KEYWORDS = "Keywords";
    public static final String KEY_CREATOR = "Creator";
    public static final String KEY_PRODUCER = "Producer";
    public static final String KEY_CREATIONDATE = "CreationDate";
    public static final String KEY_MODDATE = "ModDate";
}