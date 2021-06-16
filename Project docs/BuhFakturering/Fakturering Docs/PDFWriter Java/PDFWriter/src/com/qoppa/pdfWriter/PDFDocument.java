// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:32
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.pdfWriter;

import com.qoppa.b.c;
import com.qoppa.b.g;
import com.qoppa.b.i;
import com.qoppa.b.j;
import com.qoppa.b.l;
import com.qoppa.b.n;
import com.qoppa.b.o;
import com.qoppa.b.p;
import com.qoppa.b.q;
import com.qoppa.c.e;
import com.qoppa.c.h;
import com.qoppa.c.k;
import com.qoppa.c.m;
import com.qoppa.c.s;
import com.qoppa.d.f;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.text.BadLocationException;

// Referenced classes of package com.qoppa.pdfWriter:
//            PDFGraphics, DocumentInfo, PDFPage, ImageParam

public class PDFDocument
{

    public PDFDocument()
    {
        a = new DocumentInfo();
        _fldint = new Hashtable();
        _fldfor = new Hashtable();
        PAGEMODE_USENONE = "UseNone";
        PAGEMODE_USEOUTLINES = "UseOutlines";
        PAGEMODE_USETHUMBS = "UseThumbs";
        PAGEMODE_FULLSCREEN = "FullScreen";
        PAGEMODE_USEOC = "UseOC";
        PAGEMODE_USEATTACHMENTS = "UseAttachments";
        _fldelse = new Vector();
        _fldchar = "Qoppa" + Long.toString(System.currentTimeMillis());
        _fldchar = _fldchar.substring(0, Math.min(16, _fldchar.length()));
    }

    PDFDocument(PrinterJob printerjob)
    {
        PDFDocument();
        _fldif = printerjob;
    }

    public static PDFDocument loadRTF(String s1, PageFormat pageformat)
        throws IOException, BadLocationException
    {
        PDFDocument pdfdocument = new PDFDocument();
        FileInputStream fileinputstream = new FileInputStream(s1);
        f f1 = new f();
        f1.a(pdfdocument, fileinputstream, pageformat);
        fileinputstream.close();
        return pdfdocument;
    }

    public static PDFDocument loadHTML(URL url, PageFormat pageformat, boolean flag)
        throws IOException, BadLocationException
    {
        PDFDocument pdfdocument = new PDFDocument();
        InputStream inputstream = url.openStream();
        f f1 = new f();
        f1.a(pdfdocument, inputstream, url, pageformat, flag);
        inputstream.close();
        return pdfdocument;
    }

    public static PDFDocument loadHTML(byte abyte0[], URL url, PageFormat pageformat, boolean flag)
        throws IOException, BadLocationException
    {
        PDFDocument pdfdocument = new PDFDocument();
        ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte0);
        f f1 = new f();
        f1.a(pdfdocument, bytearrayinputstream, url, pageformat, flag);
        bytearrayinputstream.close();
        return pdfdocument;
    }

    PrinterJob _mthif()
    {
        return _fldif;
    }

    public PDFPage createPage(PageFormat pageformat)
    {
        if(pageformat == null)
            pageformat = getDefaultPageFormat();
        PDFPage pdfpage = new PDFPage(this, pageformat, a(_fldtry));
        return pdfpage;
    }

    public void addPage(PDFPage pdfpage)
    {
        _fldelse.add(pdfpage);
    }

    public PDFPage getPage(int i1)
    {
        return (PDFPage)_fldelse.get(i1);
    }

    public int getPageCount()
    {
        return _fldelse.size();
    }

    public void saveDocument(String s1)
        throws IOException
    {
        m m1 = new m(new FileOutputStream(s1));
        a(m1, 1);
        m1.close();
    }

    public void saveDocument(OutputStream outputstream)
        throws IOException
    {
        m m1 = new m(outputstream);
        a(m1, 1);
        m1.flush();
    }

    void a(OutputStream outputstream, int i1)
        throws IOException
    {
        m m1 = new m(outputstream);
        a(m1, i1);
        m1.flush();
    }

    public void saveDocument(String s1, int i1)
        throws IOException
    {
        m m1 = new m(new FileOutputStream(s1));
        a(m1, i1);
        m1.close();
    }

    private void a(m m1, int i1)
        throws IOException
    {
        m1.a("%PDF-1.3\n");
        h h1 = new h();
        c c1 = new c();
        c1.a(new f("PDF"));
        c1.a(new f("Text"));
        c1.a(new f("ImageB"));
        c1.a(new f("ImageC"));
        c1.a(new f("ImageI"));
        com.qoppa.b.m m2 = new m();
        m2.a("Type", new f("Pages"));
        c c2 = new c();
        a(c2, c1, m2, i1);
        m2.a("Kids", c2);
        m2.a("Count", new n(c2._mthfor()));
        com.qoppa.b.m m3 = new m();
        m3.a("Type", new f("Catalog"));
        m3.a("Pages", new q(m2));
        if(_fldnew != null)
            m3.a("PageMode", new f(_fldnew));
        com.qoppa.b.h h2 = new h(1, _flddo);
        m3.a(m1, h1, h2);
        if(_flddo != null)
            _flddo.a(m1, h1, h2);
        com.qoppa.b.m m4 = a();
        if(m4 != null)
            m4.a(m1, h1, h2);
        long l1 = h1.a(m1);
        j j1 = new j(_fldchar.getBytes(), 1);
        m1.a("trailer\n<<\n/Size " + (h1.a() + 1));
        m1.a("\n/Root " + m3._mthif() + " 0 R");
        m1.a("\n/ID [");
        j1.a(m1, h2);
        j1.a(m1, h2);
        m1.a("]");
        if(m4 != null)
            m1.a("\n/Info " + m4._mthif() + " 0 R");
        if(_flddo != null)
            m1.a("\n/Encrypt " + _flddo._mthif() + " 0 R\n");
        m1.a(">>\n");
        m1.a("startxref\n" + l1 + "\n");
        m1.a("%%EOF\n");
    }

    private com.qoppa.b.m a()
    {
        if(a != null)
        {
            com.qoppa.b.m m1 = new m();
            m1.a("Author", a.getAuthor());
            m1.a("Creator", a.getCreator());
            m1.a("Keywords", a.getKeywords());
            m1.a("Producer", a.getProducer());
            m1.a("Subject", a.getSubject());
            m1.a("Title", a.getTitle());
            if(a.getCreationDate() != null)
                m1.a("CreationDate", k.a(a.getCreationDate(), m1));
            if(a.getModDate() != null)
                m1.a("ModDate", k.a(a.getModDate(), m1));
            return m1;
        } else
        {
            return null;
        }
    }

    private void a(c c1, c c2, com.qoppa.b.m m1, int i1)
    {
        int j1 = _fldelse.size();
        for(int k1 = 0; k1 < i1; k1++)
        {
            for(int l1 = 0; l1 < j1; l1++)
            {
                PDFPage pdfpage = (PDFPage)_fldelse.elementAt(l1);
                c1.a(new q(pdfpage.a(c2, m1)));
            }

        }

    }

    public static final String getVersion()
    {
        return "jPDFWriter v2016R1";
    }

    public static PageFormat getDefaultPageFormat()
    {
        return (PageFormat)_fldcase.clone();
    }

    protected q a(Font font)
    {
        q q1 = (q)_fldint.get(font.getFontName());
        if(q1 == null)
        {
            String s1 = e.a(font);
            if(s1 == null)
                return null;
            com.qoppa.b.m m1 = new m();
            m1.a("Type", new f("Font"));
            m1.a("Subtype", new f("Type1"));
            m1.a("Name", new f(s1));
            m1.a("BaseFont", new f(s1));
            if(!"ZapfDingbats".equalsIgnoreCase(font.getName()) && !"Symbol".equalsIgnoreCase(font.getName()))
                m1.a("Encoding", new f("WinAnsiEncoding"));
            q1 = new q(m1);
            _fldint.put(font.getFontName(), q1);
        }
        return q1;
    }

    q a(Image image, ImageParam imageparam)
        throws s
    {
        q q1 = (q)_fldfor.get(image);
        if(q1 == null)
        {
            if(imageparam == null)
                imageparam = new ImageParam();
            i i1 = new i(image, imageparam);
            q1 = new q(i1);
            _fldfor.put(image, q1);
        }
        return q1;
    }

    public void setEncryption(String s1, String s2, int i1)
    {
        _flddo = new l(s1, s2, i1, _fldchar);
    }

    public void setDocumentInfo(DocumentInfo documentinfo)
    {
        a = documentinfo;
    }

    public Font embedFont(String s1, int i1, float f1)
        throws IOException, FontFormatException
    {
        return embedFont(((InputStream) (new FileInputStream(s1))), i1, f1);
    }

    public Font embedFont(InputStream inputstream, int i1, float f1)
        throws IOException, FontFormatException
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        byte abyte0[] = new byte[4196];
        for(int j1 = inputstream.read(abyte0); j1 > 0; j1 = inputstream.read(abyte0))
            bytearrayoutputstream.write(abyte0, 0, j1);

        byte abyte1[] = bytearrayoutputstream.toByteArray();
        Font font = Font.createFont(i1, new ByteArrayInputStream(abyte1)).deriveFont(f1);
        o o1 = new o(abyte1);
        o1.a("Length1", new n(abyte1.length));
        c c1 = new c();
        JButton jbutton = new JButton();
        FontMetrics fontmetrics = jbutton.getFontMetrics(font.deriveFont(1000F));
        int k1 = 0;
        for(int l1 = 0; l1 < 256; l1++)
        {
            c1.a(new n(fontmetrics.charWidth((char)l1)));
            k1 = Math.max(k1, fontmetrics.charWidth((char)l1));
        }

        com.qoppa.b.m m1 = new m();
        m1.a("Type", new f("FontDescriptor"));
        m1.a("Ascent", new n(fontmetrics.getAscent()));
        m1.a("CapHeight", new n(fontmetrics.getAscent()));
        m1.a("Descent", new n(-fontmetrics.getDescent()));
        m1.a("Flags", new n(4));
        m1.a("FontBBox", new g(0, 0, k1, fontmetrics.getHeight()));
        m1.a("FontName", new f(font.getPSName()));
        m1.a("ItalicAngle", new n(0));
        m1.a("StemV", new n(1));
        if(i1 == 0)
            m1.a("FontFile2", new q(o1));
        else
            m1.a("FontFile", new q(o1));
        com.qoppa.b.m m2 = new m();
        m2.a("Type", new f("Font"));
        if(i1 == 0)
            m2.a("Subtype", new f("TrueType"));
        else
            m2.a("Subtype", new f("Type1"));
        m2.a("Name", new f(font.getPSName()));
        m2.a("BaseFont", new f(font.getPSName()));
        m2.a("FirstChar", new n(0));
        m2.a("LastChar", new n(255));
        m2.a("Widths", new q(c1));
        m2.a("FontDescriptor", new q(m1));
        m2.a("Encoding", new f("WinAnsiEncoding"));
        q q1 = new q(m2);
        _fldint.put(font.getFontName(), q1);
        return font;
    }

    public void setPageMode(String s1)
    {
        _fldnew = s1;
    }

    private static final String _fldbyte = "jPDFWriter v2016R1";
    private Vector _fldelse;
    private l _flddo;
    private String _fldchar;
    private DocumentInfo a;
    private Hashtable _fldint;
    private Hashtable _fldfor;
    private PrinterJob _fldif;
    private String _fldnew;
    public static final int PERMISSION_PRINT_DOCUMENT = 4;
    public static final int PERMISSION_MODIFY_CONTENTS = 8;
    public static final int PERMISSION_COPY_TEXT_GRAPHICS = 16;
    public static final int PERMISSION_MODIFY_ANNOTATIONS = 32;
    public String PAGEMODE_USENONE;
    public String PAGEMODE_USEOUTLINES;
    public String PAGEMODE_USETHUMBS;
    public String PAGEMODE_FULLSCREEN;
    public String PAGEMODE_USEOC;
    public String PAGEMODE_USEATTACHMENTS;
    private static PageFormat _fldcase;
    private static Font _fldtry;

    static 
    {
        Paper paper = new Paper();
        paper.setSize(612D, 792D);
        paper.setImageableArea(0.0D, 0.0D, paper.getWidth(), paper.getHeight());
        _fldcase = new PageFormat();
        _fldcase.setPaper(paper);
        _fldtry = PDFGraphics.HELVETICA.deriveFont(1, 24F);
    }
}