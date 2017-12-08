package com.example.admin.iposapp.backgroundTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.admin.iposapp.utility.CurrentData;
import com.example.admin.iposapp.utility.FTPConnection;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.utility.ReadDbf;
import com.example.admin.iposapp.utility.UnzipUtility;

import org.apache.commons.net.ftp.FTPClient;

/**
 * Created by admin on 19/07/2016.
 */
public class GetFtpDataTask extends AsyncTask<Void, Void, FTPClient>
{

    Context context;
    ProgressDialog progressDialog;
    Database database;
    UnzipUtility unziper;
    ReadDbf dbfReader;

    public GetFtpDataTask(Context ctx)
    {
        context = ctx;
        progressDialog = new ProgressDialog(context);
        database = new Database(context);
    }

    @Override
    protected FTPClient doInBackground(Void... params)
    {
        FTPConnection ftpConnection = new FTPConnection(
                CurrentData.getSettings().getServer(), CurrentData.getSettings().getUser(),
                CurrentData.getSettings().getPassword());

        FTPClient ftp = ftpConnection.openConnection();

        if(ftpConnection.downloadFile("prec.zip"))
            Log.w("FTP", "Archivo retornado: true");
        else Log.w("FTP", "Archivo retornado: false");

        ftpConnection.closeConnection();

        return ftp;
    }

    @Override
    protected void onPreExecute()
    {
        progressDialog.setTitle("Sincronizando información");
        progressDialog.setMessage("Espere por favor");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(FTPClient result)
    {
        Log.v("FTPConection: ", "Conection complete");
        progressDialog.setTitle("Descompriendo");
        String path = CurrentData.getInternalStoragePath();
        try
        {
            unziper = new UnzipUtility("prec.zip", path);
            unziper.unZip();
            dbfReader = new ReadDbf();

            database.open();
            Database.clientDao.addClients(dbfReader.getClientData("M_CLIP.dbf"));
            Database.productDao.addProducts(dbfReader.getProductData("PROD.dbf"));
            //Database.settingsDAO.updateLasClient(0, CurrentData.getSettings().getAppUser());
            database.close();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        //database.open();
        //Database.clientDao.updateClients(context);
        //Database.productDao.updateProducts(context);
        //database.close();
        if(progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
    }
}
