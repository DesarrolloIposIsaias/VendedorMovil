package com.example.admin.iposapp.backgroundTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.controler.MenuSales;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Sale;
import com.example.admin.iposapp.model.SaleDetail;
import com.example.admin.iposapp.utility.CurrentData;
import com.example.admin.iposapp.utility.JsonSale;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;

/**
 * Created by admin on 22/08/2016.
 */
public class AddIndividualSaleSoapTask extends AsyncTask<Void, Void, String>
{
    private Context ctx;
    private static final String namespace = "http://tempuri.org/";
    private static final String methodName = "AgregarVentaMovil_2";
    private static final String soapAction = "http://tempuri.org/AgregarVentaMovil_2";
    private ProgressDialog progressDialog;

    public AddIndividualSaleSoapTask(Context context)
    {
        ctx = context;
        progressDialog = new ProgressDialog(ctx);
    }

    @Override
    protected void onPreExecute()
    {
        progressDialog.setTitle("Enviando Venta");
        progressDialog.setMessage("Por favor espere");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params)
    {

        SoapObject request = new SoapObject(namespace, methodName);

        Database database = new Database(ctx);
        database.open();
        Sale sale = Database.saleDAO.fetchSaleById(CurrentData.getSaleId());
        List<SaleDetail> saleDetail = Database.saleDetailDAO.fetchSaleDetailsBySale(
                CurrentData.getSaleId());
        //JsonSale jsonSale = new JsonSale();
        String strJson = JsonSale.toJson(saleDetail) + "||||" + sale.getTotal();

        request.addProperty("jsonStr", strJson );
        request.addProperty("nota1", sale.getDescription1());
        request.addProperty("nota2", sale.getDescription2());
        request.addProperty("estatus", sale.getStatus1());
        request.addProperty("plazos", "N");
        if (sale.getPaymentMethod() == "Contado")
        {
            request.addProperty("contado", "S");
        }
        else
        {
            request.addProperty("contado", "N");
        }
        request.addProperty("strConnection", "C:\\test\\IPOSDB.fdb");

        SoapSerializationEnvelope envelope =
                new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        try
        {
            HttpTransportSE transport;
            transport = new HttpTransportSE("");
            transport.debug = true;
            transport.call(soapAction, envelope);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        String strResponse;
        try
        {
            SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
            if (response != null)
                strResponse = response.toString();
            else strResponse = "error";

            if (strResponse.equals("exito"))
            {
                sale.setSend("Y");
                Database.saleDAO.updateSale(sale.getId(), sale);
            }
            else
            {
                Log.w("Problema SOAP: ", "Venta pendiente");
                sale.setSend("P");
                Database.saleDAO.updateSale(sale.getId(), sale);
            }

            Log.w("SOAP", strResponse);

        }
        catch (SoapFault soapFault)
        {
            strResponse = "error";
            soapFault.printStackTrace();
        }

        database.close();

        return strResponse;
    }

    @Override
    protected void onPostExecute(String result)
    {
        if(progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        if(result.equals("exito"))
        {
            Toast.makeText(ctx, "Venta enviada con exito", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(ctx,"Problema al enviar la venta", Toast.LENGTH_SHORT).show();
        }
    }
}
