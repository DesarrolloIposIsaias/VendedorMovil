package com.example.admin.iposapp.utility;

import android.util.Log;

import com.example.admin.iposapp.model.Client;
import com.example.admin.iposapp.model.Product;
import com.example.admin.iposapp.model.Sale;
import com.example.admin.iposapp.model.SaleDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 26/07/2016.
 */
public class JsonSale
{
    public static JSONObject jsonObject;
    public static JSONArray jsonArray;

    public JsonSale()
    {
        jsonObject = new JSONObject();
        jsonArray = new JSONArray();
    }

    public static List<Product> jsonToList(String strJson)
    {

        List<Product> listProducto = new ArrayList<Product>();

        try
        {
            JSONArray json = new JSONArray(strJson);

            for(int i=0; i<json.length(); i++){
                JSONObject e = json.getJSONObject(i);
                Product prod = new Product();
                prod.setClave(e.getString("IPRODUCTO"));
                prod.setExistencia((float)e.getDouble("IEXIS1"));
                listProducto.add(prod);

            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return listProducto;
    }

    public static String toJson(List<SaleDetail> saleDetails)
    {

        jsonArray = new JSONArray();
        jsonObject = new JSONObject();
        try
        {
            for (int i = 0; i < saleDetails.size(); i++)
            {
                jsonObject = new JSONObject();
                jsonObject.put("IVENTA", saleDetails.get(i).getSaleId());
                jsonObject.put("ICLIENTE", "FM000121");
                jsonObject.put("IPRODUCTO", saleDetails.get(i).getProductId());
                jsonObject.put("ICANTIDAD", saleDetails.get(i).getAmount());
                jsonObject.put("IDESCUENTO", saleDetails.get(i).getDiscount());
                jsonObject.put("IID", saleDetails.get(i).getId());
                jsonObject.put("IID_FECHA", "2016-08-01T00:00:00");
                jsonObject.put("IID_HORA", "09:59:51");
                jsonObject.put("IPRECIO", saleDetails.get(i).getPrice());
                jsonArray.put(jsonObject);
            }

            Log.w("SOAP Request:", jsonArray.toString());

            return jsonArray.toString();
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }

        return null;
    }


}
