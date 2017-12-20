package com.example.admin.iposapp.backgroundTask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.controler.ExistencesDialogFragment;
import com.example.admin.iposapp.controler.ListViewAdapterExistence;
import com.example.admin.iposapp.utility.CurrentData;
import com.example.admin.iposapp.utility.JsonSale;
import com.example.admin.iposapp.controler.SalesFragment;
import com.example.admin.iposapp.model.Product;
import com.example.admin.iposapp.model.SaleDetail;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
public class GetStockSoapTask extends AsyncTask<Void, Void, String>
{

    protected Context context;
    private static final String namespace = "http://tempuri.org/";
    private static final String methodName = "ValidarVentaMovil_2";
    private static final String soapAction = "http://tempuri.org/ValidarVentaMovil_2";
    private List<SaleDetail> saleDetail;
    private List<Product> products;
    private static String answer;
    private ProgressDialog dialog;


    public GetStockSoapTask(Context ctx, List<SaleDetail> sd)
    {
        context = ctx;
        saleDetail = sd;
    }

    public String getAnswer()
    {
        return answer;
    }

    public void setAnswer(String a)
    {
        answer = a;
    }

    public List<Product> getExistences()
    {
        return products;
    }

    @Override
    protected void onPreExecute()
    {
        dialog = new ProgressDialog(context);
        dialog.setTitle("Espere un momento");
        dialog.setMessage("Consultando existencias");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected String doInBackground(Void... params)
    {
        //String url = CurrentData.getSettings().getSoapServer();

        SoapObject request = new SoapObject(namespace, methodName);

        JsonSale jsonSale = new JsonSale();
        String jsonStr = JsonSale.toJson(saleDetail);

        request.addProperty("jsonStr", jsonStr);
        request.addProperty("strConnection", "C:\\test\\IPOSDB.fdb");

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        try {
            HttpTransportSE transport;
            transport = new HttpTransportSE("");
            transport.debug = true;
            transport.call(soapAction, envelope);
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.w("SOAP ERROR: ", e.getMessage());
        }

        try {
            String strResponse = "Null response";
            SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
            if(response != null) {
                strResponse = response.toString();
            }

            Log.w("SOAP Response: ", strResponse);

            return  strResponse;

        }
        catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result)
    {

        Log.w("SOAP Response:", result);

        products = JsonSale.jsonToList(result);

        CurrentData.setProductList(products);

        if(dialog.isShowing()) dialog.dismiss();

    }
}
