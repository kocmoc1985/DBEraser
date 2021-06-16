// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:32
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.pdfWriter;

import com.qoppa.c.f;
import java.awt.print.*;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

// Referenced classes of package com.qoppa.pdfWriter:
//            PDFDocument, PDFPage

public class PDFPrinterJob extends PrinterJob
    implements Runnable
{

    private PDFPrinterJob()
    {
        _flddo = "PDFPrinterJob";
        _fldnew = 1;
        _fldtry = PDFDocument.getDefaultPageFormat();
    }

    public void cancel()
    {
        _fldcase = true;
    }

    public PageFormat defaultPage()
    {
        return PDFDocument.getDefaultPageFormat();
    }

    public PageFormat defaultPage(PageFormat pageformat)
    {
        PageFormat pageformat1 = (PageFormat)pageformat.clone();
        PageFormat pageformat2 = PDFDocument.getDefaultPageFormat();
        pageformat1.setOrientation(pageformat2.getOrientation());
        pageformat1.setPaper(pageformat2.getPaper());
        return pageformat1;
    }

    public int getCopies()
    {
        return _fldnew;
    }

    public String getJobName()
    {
        return _flddo;
    }

    public static PrinterJob getPrinterJob()
    {
        return new PDFPrinterJob();
    }

    public String getUserName()
    {
        return "PDFUser";
    }

    public static String getVersion()
    {
        return PDFDocument.getVersion();
    }

    public boolean isCancelled()
    {
        return _fldcase;
    }

    public PageFormat pageDialog(PageFormat pageformat)
    {
        PrinterJob printerjob = PrinterJob.getPrinterJob();
        PageFormat pageformat1 = printerjob.pageDialog(pageformat);
        return pageformat1;
    }

    public void print()
        throws PrinterException
    {
        if(_fldint == null && _fldchar == null)
            throw new PrinterException("No printable/pageable set.");
        if(_fldif == null)
        {
            boolean flag = printDialog();
            if(!flag || _fldif == null)
                return;
        }
        try
        {
            PDFDocument pdfdocument = null;
            if(_fldint != null)
                pdfdocument = _mthif();
            else
            if(_fldchar != null)
                pdfdocument = a();
            pdfdocument.saveDocument(_fldif, _fldnew);
        }
        catch(IOException ioexception)
        {
            throw new PrinterException("Error opening " + _fldif);
        }
    }

    public void print(OutputStream outputstream)
        throws PrinterException
    {
        PDFDocument pdfdocument = null;
        if(_fldint != null)
            pdfdocument = _mthif();
        else
        if(_fldchar != null)
            pdfdocument = a();
        else
            throw new PrinterException("No printable/pageable set.");
        try
        {
            pdfdocument.a(outputstream, _fldnew);
        }
        catch(IOException ioexception)
        {
            throw new PrinterException("Error writing to PDF.");
        }
    }

    public void print(String s)
        throws PrinterException
    {
        _fldif = s;
        print();
    }

    public PDFDocument printToDocument()
        throws PrinterException
    {
        if(_fldint != null)
            return _mthif();
        if(_fldchar != null)
            return a();
        else
            throw new PrinterException("No printable/pageable set.");
    }

    public boolean printDialog()
    {
        _fldfor = false;
        if(SwingUtilities.isEventDispatchThread())
            run();
        else
            try
            {
                SwingUtilities.invokeAndWait(this);
            }
            catch(Throwable throwable) { }
        return _fldfor;
    }

    private PDFDocument a()
        throws PrinterException
    {
        PDFDocument pdfdocument = new PDFDocument(this);
        int i = 0;
        for(int j = 0; j < _fldchar.getNumberOfPages() && i == 0 && !_fldcase; j++)
        {
            PageFormat pageformat = _fldchar.getPageFormat(j);
            Printable printable = _fldchar.getPrintable(j);
            PDFPage pdfpage = pdfdocument.createPage(pageformat);
            java.awt.Graphics2D graphics2d = pdfpage.createGraphics();
            i = printable.print(graphics2d, pageformat, j);
            if(i == 0)
                pdfdocument.addPage(pdfpage);
        }

        return pdfdocument;
    }

    private PDFDocument _mthif()
        throws PrinterException
    {
        int i = 0;
        int j = 0;
        PDFDocument pdfdocument = new PDFDocument(this);
        while(j == 0 && !_fldcase) 
        {
            PDFPage pdfpage = pdfdocument.createPage(_fldtry);
            java.awt.Graphics2D graphics2d = pdfpage.createGraphics();
            j = _fldint.print(graphics2d, _fldtry, i);
            if(j == 0)
                pdfdocument.addPage(pdfpage);
            i++;
        }
        return pdfdocument;
    }

    public void run()
    {
        JFileChooser jfilechooser = new JFileChooser();
        jfilechooser.setFileFilter(new f());
        int i = jfilechooser.showSaveDialog(null);
        if(i == 0)
        {
            _fldif = jfilechooser.getSelectedFile().getAbsolutePath();
            _fldfor = true;
        } else
        {
            _fldfor = false;
        }
    }

    public void setCopies(int i)
    {
        _fldnew = i;
    }

    public void setJobName(String s)
    {
        _flddo = s;
    }

    public void setPageable(Pageable pageable)
    {
        _fldchar = pageable;
    }

    public void setPrintable(Printable printable)
    {
        _fldint = printable;
    }

    public void setPrintable(Printable printable, PageFormat pageformat)
    {
        _fldint = printable;
        _fldtry = pageformat;
    }

    public PageFormat validatePage(PageFormat pageformat)
    {
        PageFormat pageformat1 = (PageFormat)pageformat.clone();
        Paper paper = pageformat1.getPaper();
        paper.setImageableArea(0.0D, 0.0D, paper.getWidth(), paper.getHeight());
        pageformat1.setPaper(paper);
        return pageformat1;
    }

    private static final String _fldbyte = "PDFUser";
    private static final String a = "PDFPrinterJob";
    private boolean _fldfor;
    private String _fldif;
    private Printable _fldint;
    private Pageable _fldchar;
    private PageFormat _fldtry;
    private String _flddo;
    private boolean _fldcase;
    private int _fldnew;
}