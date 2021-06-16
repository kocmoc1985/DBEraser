// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:31
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.d;

import java.net.URL;
import javax.swing.event.DocumentEvent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.text.*;
import javax.swing.text.html.*;

// Referenced classes of package com.qoppa.d:
//            g

public class com.qoppa.d.a extends HTMLDocument
{
    public class a extends javax.swing.text.html.HTMLDocument.HTMLReader
    {

        public void handleStartTag(javax.swing.text.html.HTML.Tag tag, MutableAttributeSet mutableattributeset, int i)
        {
            if(tag == javax.swing.text.html.HTML.Tag.SPAN)
            {
                if(mutableattributeset.isDefined(javax.swing.text.html.HTML.Attribute.STYLE))
                {
                    String s = (String)mutableattributeset.getAttribute(javax.swing.text.html.HTML.Attribute.STYLE);
                    mutableattributeset.removeAttribute(javax.swing.text.html.HTML.Attribute.STYLE);
                    styleAttributes = getStyleSheet().getDeclaration(s);
                    mutableattributeset.addAttributes(styleAttributes);
                } else
                {
                    styleAttributes = null;
                }
                a a1 = a;
                if(a1 != null)
                {
                    _fldif = true;
                    a1.start(tag, mutableattributeset);
                }
            } else
            {
                handleStartTag(tag, mutableattributeset, i);
            }
        }

        public void handleSimpleTag(javax.swing.text.html.HTML.Tag tag, MutableAttributeSet mutableattributeset, int i)
        {
            if(tag == javax.swing.text.html.HTML.Tag.SPAN)
            {
                if(_fldif)
                    handleEndTag(tag, i);
                else
                    handleStartTag(tag, mutableattributeset, i);
            } else
            {
                handleSimpleTag(tag, mutableattributeset, i);
            }
        }

        public void handleEndTag(javax.swing.text.html.HTML.Tag tag, int i)
        {
            if(tag == javax.swing.text.html.HTML.Tag.SPAN)
            {
                a a1 = a;
                if(a1 != null)
                {
                    _fldif = false;
                    a1.end(tag);
                }
            } else
            {
                handleEndTag(tag, i);
            }
        }

        a a;
        AttributeSet styleAttributes;
        boolean _fldif;





        public a(int i)
        {
            super(com.qoppa.d.a.this, i, 0, 0, null);
            a = new a();
            _fldif = false;
        }
    }


    public com.qoppa.d.a()
    {
        a(((javax.swing.text.AbstractDocument.Content) (new GapContent(4096))), new StyleSheet());
    }

    public com.qoppa.d.a(StyleSheet stylesheet)
    {
        a(((javax.swing.text.AbstractDocument.Content) (new GapContent(4096))), stylesheet);
    }

    public com.qoppa.d.a(javax.swing.text.AbstractDocument.Content content, StyleSheet stylesheet)
    {
        HTMLDocument(content, stylesheet);
    }

    public void a(Element element, AttributeSet attributeset)
    {
        if(element == null || attributeset == null)
            break MISSING_BLOCK_LABEL_132;
        try
        {
            writeLock();
            int i = element.getStartOffset();
            javax.swing.text.AbstractDocument.DefaultDocumentEvent defaultdocumentevent = new DefaultDocumentEvent(this, i, element.getEndOffset() - i, javax.swing.event.DocumentEvent.EventType.CHANGE);
            AttributeSet attributeset1 = attributeset.copyAttributes();
            MutableAttributeSet mutableattributeset = (MutableAttributeSet)element.getAttributes();
            defaultdocumentevent.addEdit(new AttributeUndoableEdit(element, attributeset1, false));
            mutableattributeset.addAttributes(attributeset);
            defaultdocumentevent.end();
            fireChangedUpdate(defaultdocumentevent);
            fireUndoableEditUpdate(new UndoableEditEvent(this, defaultdocumentevent));
        }
        finally
        {
            writeUnlock();
        }
    }

    public void a(Element element, int i, int j)
        throws BadLocationException
    {
        writeLock();
        int k = element.getElement(i).getStartOffset();
        int l = element.getElement((i + j) - 1).getEndOffset();
        try
        {
            Element aelement[] = new Element[j];
            Element aelement1[] = new Element[0];
            for(int i1 = 0; i1 < j; i1++)
                aelement[i1] = element.getElement(i1 + i);

            javax.swing.text.AbstractDocument.DefaultDocumentEvent defaultdocumentevent = new DefaultDocumentEvent(this, k, l - k, javax.swing.event.DocumentEvent.EventType.REMOVE);
            ((javax.swing.text.AbstractDocument.BranchElement)element).replace(i, aelement.length, aelement1);
            defaultdocumentevent.addEdit(new ElementEdit(element, i, aelement, aelement1));
            javax.swing.undo.UndoableEdit undoableedit = getContent().remove(k, l - k);
            if(undoableedit != null)
                defaultdocumentevent.addEdit(undoableedit);
            postRemoveUpdate(defaultdocumentevent);
            defaultdocumentevent.end();
            fireRemoveUpdate(defaultdocumentevent);
            if(undoableedit != null)
                fireUndoableEditUpdate(new UndoableEditEvent(this, defaultdocumentevent));
        }
        finally
        {
            writeUnlock();
        }
        return;
    }

    public void a()
        throws Exception
    {
        String s = "  <link rel=stylesheet type=\"text/css\" href=\"" + a + "\">";
        Element element = getDefaultRootElement();
        Element element1 = a(javax.swing.text.html.HTML.Tag.HEAD.toString(), element);
        if(element1 != null)
        {
            Element element2 = a(javax.swing.text.html.HTML.Tag.IMPLIED.toString(), element1);
            if(element2 != null)
            {
                Element element4 = a(javax.swing.text.html.HTML.Tag.LINK.toString(), element2);
                if(element4 != null)
                    setOuterHTML(element4, s);
                else
                    insertBeforeEnd(element2, s);
            }
        } else
        {
            Element element3 = a(javax.swing.text.html.HTML.Tag.BODY.toString(), element);
            insertBeforeStart(element3, "<head>" + s + "</head>");
        }
    }

    public boolean _mthdo()
    {
        return _mthif() != null;
    }

    public String _mthif()
    {
        String s = null;
        Element element = a(javax.swing.text.html.HTML.Tag.LINK.toString(), getDefaultRootElement());
        if(element != null)
        {
            Object obj = element.getAttributes().getAttribute(javax.swing.text.html.HTML.Attribute.HREF);
            if(obj != null)
                s = obj.toString();
        }
        return s;
    }

    public javax.swing.text.html.HTMLEditorKit.ParserCallback getReader(int i)
    {
        Object obj = getProperty("stream");
        if(obj instanceof URL)
            setBase((URL)obj);
        a a1 = new a(i);
        return a1;
    }

    public static Element a(String s, Element element)
    {
        Element element1 = null;
        ElementIterator elementiterator = new ElementIterator(element);
        for(Element element2 = elementiterator.first(); element2 != null && element1 == null; element2 = elementiterator.next())
            if(element2.getName().equalsIgnoreCase(s))
                element1 = element2;

        return element1;
    }

    public static String a = "style.css";

}