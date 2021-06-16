// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2021-06-15 14:38:30
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package com.qoppa.c;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class f extends FileFilter
{

    public f()
    {
    }

    public boolean accept(File file)
    {
        if(file.getName().length() > 4)
        {
            String s = file.getName().substring(file.getName().length() - 4, file.getName().length());
            if(".pdf".equalsIgnoreCase(s))
                return true;
        }
        return false;
    }

    public String getDescription()
    {
        return "PDF Files (*.pdf)";
    }
}