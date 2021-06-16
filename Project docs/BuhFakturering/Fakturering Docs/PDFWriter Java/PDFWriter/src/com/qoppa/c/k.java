// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:30
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.c;

import com.qoppa.b.a;
import com.qoppa.b.n;
import com.qoppa.b.p;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class k
{

    public k()
    {
    }

    public static String a(String s, int i)
    {
        StringBuffer stringbuffer = new StringBuffer();
        if(i == 0)
        {
            stringbuffer.append('(');
            for(int j = 0; j < s.length(); j++)
            {
                char c = s.charAt(j);
                if(c == '(' || c == ')' || c == '\\')
                {
                    stringbuffer.append('\\');
                    stringbuffer.append(c);
                } else
                if(c == '\n')
                    stringbuffer.append("\\n");
                else
                if(c == '\r')
                    stringbuffer.append("\\r");
                else
                if(c == '\t')
                    stringbuffer.append("\\t");
                else
                if(c == '\b')
                    stringbuffer.append("\\b");
                else
                if(c == '\f')
                    stringbuffer.append("\\f");
                else
                    stringbuffer.append(c);
            }

            stringbuffer.append(')');
        } else
        {
            stringbuffer.append('<');
            for(int l = 0; l < s.length(); l++)
            {
                int i1 = 0xff & s.charAt(l);
                if(i1 < 16)
                {
                    stringbuffer.append('0');
                    stringbuffer.append(Integer.toHexString(i1));
                } else
                {
                    stringbuffer.append(Integer.toHexString(i1));
                }
            }

            stringbuffer.append('>');
        }
        return stringbuffer.toString();
    }

    public static int a(Object obj)
    {
        if(obj != null)
        {
            if(obj instanceof a)
                return (int)((a)obj).f();
            if(obj instanceof n)
                return ((n)obj).c();
            try
            {
                return Double.valueOf(obj.toString()).intValue();
            }
            catch(Exception exception)
            {
                return 0;
            }
        } else
        {
            return 0;
        }
    }

    public static p a(Date date, com.qoppa.b.k k1)
    {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = "D:" + simpledateformat.format(date);
        TimeZone timezone = TimeZone.getDefault();
        int i = timezone.getOffset(date.getTime()) / 1000;
        if(i > 0)
            s = s + '+';
        else
        if(i < 0)
        {
            s = s + '-';
            i *= -1;
        } else
        {
            s = s + 'Z';
        }
        DecimalFormat decimalformat = new DecimalFormat("00");
        s = s + decimalformat.format(i / 3600) + '\'';
        s = s + decimalformat.format((i % 3600) / 60) + '\'';
        return new p(s.getBytes(), 0, k1);
    }

    public static final int _fldif = 0;
    public static final int a = 1;
}