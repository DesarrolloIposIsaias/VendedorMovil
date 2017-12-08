package com.example.admin.iposapp.backgroundTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.iposapp.utility.CurrentData;
import com.example.admin.iposapp.utility.JsonSale;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Sale;
import com.example.admin.iposapp.model.SaleDetail;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;

/**
 * Created by admin on 26/07/2016.
 */
public class AddSaleSoapTask extends AsyncTask<Void, Void, String>
{

    private Context ctx;
    private static final String namespace = "http://tempuri.org/";
    private static final String methodName = "AgregarVentaMovil_2";
    private static final String soapAction = "http://tempuri.org/AgregarVentaMovil_2";
    private ProgressDialog progressDialog;

    public AddSaleSoapTask(Context context)
    {
        ctx = context;
        progressDialog = new ProgressDialog(ctx);
    }

    @Override
    protected String doInBackground(Void... params)
    {

        String url = CurrentData.getSettings().getSoapServer();

        SoapObject request = new SoapObject(namespace, methodName);

        Database database = new Database(ctx);
        database.open();
        List<Sale> sales = Database.saleDAO.fetchAllSales();

        JsonSale jsonSale = new JsonSale();
        String strJson = null;
        String strResponse = "notSended";

        for (int i = 0;  i < sales.size(); i++)
        {
            if(sales.get(i).getSend().equals("P"))
            {
                String id = sales.get(i).getId();
                List<SaleDetail> saleDetails = Database.saleDetailDAO.fetchSaleDetailsBySale(id);

                strJson = JsonSale.toJson(saleDetails) + "||||" + sales.get(i).getTotal();
                Log.w("JSON: ", strJson);

                request.addProperty("jsonStr", strJson );
                request.addProperty("nota1", sales.get(i).getDescription1());
                request.addProperty("nota2", sales.get(i).getDescription2());
                request.addProperty("estatus", sales.get(i).getStatus1());
                request.addProperty("plazos", "N");
                if (sales.get(i).getPaymentMethod() == "Contado")
                {
                    request.addProperty("contado", "S");
                }
                else
                {
                    request.addProperty("contado", "N");
                }
                request.addProperty("strConnection", "C:\\test\\IPOSDB.fdb");
                request.addProperty("p_IVENDEDORD", CurrentData.getSettings().getSoapSellerId());
                request.addProperty("ftpFolder", CurrentData.getSettings().getFolder());
                request.addProperty("ftpPass", CurrentData.getSettings().getFolderPass());

                SoapSerializationEnvelope envelope =
                        new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                try
                {
                    HttpTransportSE transport;
                    transport = new HttpTransportSE(url);
                    transport.debug = true;
                    transport.call(soapAction, envelope);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                try
                {
                    SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
                    if(response != null)
                        strResponse = response.toString();
                    else strResponse = "error";

                    if (strResponse.equals("exito"))
                    {
                        sales.get(i).setSend("Y");
                        Database.saleDAO.updateSale(sales.get(i).getId(), sales.get(i));
                    }
                    else
                    {
                        Log.w("Problema SOAP:", "Venta pendiente");
                        sales.get(i).setSend("P");
                        Database.saleDAO.updateSale(sales.get(i).getId(), sales.get(i));
                    }

                    Log.w("SOAP", strResponse);

                }
                catch (SoapFault soapFault)
                {
                    strResponse = "error";
                    soapFault.printStackTrace();
                }
            }
        }

        database.close();

        return strResponse;
    }

    @Override
    protected void onPreExecute()
    {
        progressDialog.setTitle("Sincronizando informacion");
        progressDialog.setMessage("Espere por favor");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String result)
    {
        if (progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        Log.w("SOAP: ", "Coneccion terminada");
    }
}
