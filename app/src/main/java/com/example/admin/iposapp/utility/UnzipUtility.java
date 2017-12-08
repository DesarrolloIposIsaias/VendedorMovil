package com.example.admin.iposapp.utility;

import android.util.Log;
import android.widget.Toast;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.FileHeader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by admin on 14/09/2016.
 */
public class UnzipUtility
{
    private String zipName;
    private String location;

    public UnzipUtility(String zipName, String location)
    {
        this.zipName = zipName;
        this.location = location;
    }

    public void unZip()
    {
        try
        {
            ZipFile zipFile = new ZipFile(location + "/" + zipName);
            zipFile.extractAll(location);
            Log.w("Unziped: ", "true");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

}
