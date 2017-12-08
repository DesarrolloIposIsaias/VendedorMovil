package com.example.admin.iposapp.utility;

import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by admin on 14/07/2016.
 */
public class FTPConnection
{
    private  String server;
    private String user;
    private String password;
    private int port = 21;
    private FTPClient client;

    public FTPConnection(String server, String user, String password)
    {
        client = new FTPClient();

        this.server = server;
        this.user = user;
        this.password = password;
    }

    public FTPClient openConnection()
    {
        try
        {
            client.connect(server, port);
            client.login(user, password);
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.setFileTransferMode(FTP.BLOCK_TRANSFER_MODE);
            client.enterLocalPassiveMode();

            return client;
        }
        catch (IOException ex)
        {
            Log.w("FTP Problem", "openConnection: " + ex.getMessage());

            return client;
        }
    }

    public void closeConnection()
    {
        try
        {
            if (client.isConnected())
            {
                client.logout();
                client.disconnect();
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public boolean downloadFile(String fileName)
    {
        FileOutputStream output;
        File file = new File(CurrentData.getInternalStoragePath(), fileName);
        boolean result = false;

        Log.w("Path descarga: ", file.getAbsolutePath());

        try
        {
            client.enterLocalPassiveMode();
            output = new FileOutputStream(file);
            result = client.retrieveFile("/farma/vendedormovil/sistemas/out/" + fileName, output);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return result;
    }

}
