package com.example.admin.iposapp.utility;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.example.admin.iposapp.model.Bank;
import com.example.admin.iposapp.model.Crep;
import com.example.admin.iposapp.model.Kit;
import com.example.admin.iposapp.model.Product;
import com.example.admin.iposapp.model.State;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.Set;

import com.google.gson.reflect.TypeToken;
import com.google.gson.GsonBuilder;

/**
 * Created by sopor on 11/12/2017.
 */

public class WSMobileSalesHelper {

    private Context context;
    private String baseUrl;

    public WSMobileSalesHelper(Context ctx){
        context = ctx;
        baseUrl = "http://10.0.2.2:8085/ServiceContract/Implementation/";
    }

    public void getClients(){
        String methodName = "WSClients.svc/GetClients";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, baseUrl + methodName, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Server error..", Toast.LENGTH_SHORT).show();

                error.printStackTrace();

            }
        }){
            @Override
            public Map<String, String> getHeaders(){

                HashMap<String, String> headers = new HashMap<>();

                headers.put("company", "testburro");
                headers.put("branch", "MATRIZ");
                headers.put("db", "VENMOV.FDB");

                return headers;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getBanks(){
        String methodName = "WSBanks.svc/GetBanks";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, baseUrl + methodName, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                JSONArray jarray = response.getJSONArray("Data");
                                Gson objGson = new Gson();
                                Type listType = new TypeToken<List<Bank>>(){}.getType();
                                List<Bank> bancos = objGson.fromJson(jarray.toString(), listType);
                                System.out.println(bancos.size());
                                System.out.println(bancos);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //List<Bank> myObject = objectMapper.readValue(response.getEntity().getContent(), List<Bank>.cla);
                            /*Iterator<String> keys = response.keys();
                            while(keys.hasNext())
                            {
                                String key = keys.next();
                                String val = null;
                                val = response.getString(key);
                            }*/
                            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Server error..", Toast.LENGTH_SHORT).show();

                error.printStackTrace();

            }
        }){
            @Override
            public Map<String, String> getHeaders(){

                HashMap<String, String> headers = new HashMap<>();

                headers.put("company", "testburro");
                headers.put("branch", "MATRIZ");
                headers.put("db", "VENMOV.FDB");

                return headers;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getCrep(){
        String methodName = "WSCrep.svc/GetCrep";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, baseUrl + methodName, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                JSONArray jarray = response.getJSONArray("Data");
                                Gson objGson = new Gson();
                                Type listType = new TypeToken<List<Crep>>(){}.getType();
                                List<Crep> cobranzas = objGson.fromJson(jarray.toString(), listType);
                                System.out.println(cobranzas.size());
                                System.out.println(cobranzas);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Server error..", Toast.LENGTH_SHORT).show();

                error.printStackTrace();

            }
        }){
            @Override
            public Map<String, String> getHeaders(){

                HashMap<String, String> headers = new HashMap<>();

                headers.put("company", "testburro");
                headers.put("branch", "MATRIZ");
                headers.put("db", "VENMOV.FDB");

                return headers;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getStates(){
        String methodName = "WSStates.svc/GetStates";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, baseUrl + methodName, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                JSONArray jarray = response.getJSONArray("Data");
                                Gson objGson = new Gson();
                                Type listType = new TypeToken<List<State>>(){}.getType();
                                List<State> estados = objGson.fromJson(jarray.toString(), listType);
                                System.out.println(estados.size());
                                System.out.println(estados);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Server error..", Toast.LENGTH_SHORT).show();

                error.printStackTrace();

            }
        }){
            @Override
            public Map<String, String> getHeaders(){

                HashMap<String, String> headers = new HashMap<>();

                headers.put("company", "testburro");
                headers.put("branch", "MATRIZ");
                headers.put("db", "VENMOV.FDB");

                return headers;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getProducts(){
        String methodName = "WSProducts.svc/GetProducts";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, baseUrl + methodName, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                JSONArray jarray = response.getJSONArray("Data");
                                Gson objGson = new Gson();
                                Type listType = new TypeToken<List<Product>>(){}.getType();
                                List<Product> productos = objGson.fromJson(jarray.toString(), listType);
                                System.out.println(productos.size());
                                System.out.println(productos);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Server error..", Toast.LENGTH_SHORT).show();

                error.printStackTrace();

            }
        }){
            @Override
            public Map<String, String> getHeaders(){

                HashMap<String, String> headers = new HashMap<>();

                headers.put("company", "testburro");
                headers.put("branch", "MATRIZ");
                headers.put("db", "VENMOV.FDB");

                return headers;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getKits(){
        String methodName = "WSKits.svc/GetKits";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, baseUrl + methodName, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                JSONArray jarray = response.getJSONArray("Data");
                                Gson objGson = new Gson();
                                Type listType = new TypeToken<List<Kit>>(){}.getType();
                                List<Kit> kits = objGson.fromJson(jarray.toString(), listType);
                                System.out.println(kits.size());
                                System.out.println(kits);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Server error..", Toast.LENGTH_SHORT).show();

                error.printStackTrace();

            }
        }){
            @Override
            public Map<String, String> getHeaders(){

                HashMap<String, String> headers = new HashMap<>();

                headers.put("company", "testburro");
                headers.put("branch", "MATRIZ");
                headers.put("db", "VENMOV.FDB");

                return headers;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
}
