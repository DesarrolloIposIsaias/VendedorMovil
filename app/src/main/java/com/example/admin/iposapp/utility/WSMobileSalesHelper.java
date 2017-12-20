package com.example.admin.iposapp.utility;

import android.content.Context;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Client;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.admin.iposapp.model.Bank;
import com.example.admin.iposapp.model.Crep;
import com.example.admin.iposapp.model.Kit;
import com.example.admin.iposapp.model.Product;
import com.example.admin.iposapp.model.State;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

public class WSMobileSalesHelper {

    private Context context;
    private String baseUrl;
    private Database database;

    public WSMobileSalesHelper(Context ctx){
        context = ctx;
        baseUrl = CurrentData.getSettings().getServer();
        database = new Database(context);
    }

    public void getClients(){
        String methodName = "WSClients.svc/GetClients";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, baseUrl + methodName, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                JSONArray jarray = response.getJSONArray("Data");
                                Gson objGson = new Gson();
                                Type listType = new TypeToken<List<Client>>(){}.getType();
                                List<Client> clientes = objGson.fromJson(
                                        jarray.toString(),
                                        listType
                                );
                                database.open();
                                if(!Database.clientDao.addClients(clientes))
                                {
                                    throw new SQLException("No se pudieron insertar los clientes");
                                }
                                //List<Client> testData = database.clientDao.fetchAllClients();
                                Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (SQLException e) {
                                database.upgrade();
                                Toast.makeText(
                                        context,
                                        "Error al Importar Clientes, vuelva a importar",
                                        Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                            finally {
                                database.close();
                            }

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

                headers.put("company", CurrentData.getSettings().getCompany());
                headers.put("branch", CurrentData.getSettings().getBranch());
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
                                database.open();
                                if(!Database.bankDAO.addBanks(bancos)) {
                                    throw new SQLException("No se pudieron insertar los bancos");
                                }
                                //List<Bank> testData = database.bankDAO.fetchAllBanks();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (SQLException e) {
                                database.upgrade();
                                Toast.makeText(
                                        context,
                                        "Error al Importar Bancos, vuelva a importar",
                                        Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                            finally {
                                database.close();
                            }

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
                headers.put("company", CurrentData.getSettings().getCompany());
                headers.put("branch", CurrentData.getSettings().getBranch());
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
                                List<Crep> cobranzas = objGson.fromJson(
                                        jarray.toString(),
                                        listType
                                );
                                database.open();
                                if(!Database.crepDAO.addCreps(cobranzas))
                                {
                                    throw new SQLException("No se pudieron insertar las cobranzas");
                                }
                                //List<Crep> testData = database.crepDAO.fetchAllCreps();
                                Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (SQLException e) {
                                database.upgrade();
                                Toast.makeText(
                                        context,
                                        "Error al Importar Cobranzas, vuelva a importar",
                                        Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                            finally {
                                database.close();
                            }
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

                headers.put("company", CurrentData.getSettings().getCompany());
                headers.put("branch", CurrentData.getSettings().getBranch());
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
                                List<State> estados = objGson.fromJson(
                                        jarray.toString(),
                                        listType
                                );
                                database.open();
                                if(!Database.stateDAO.addStates(estados))
                                {
                                    throw new SQLException("No se pudieron insertar los Estados");
                                }
                                //List<State> testData = database.stateDAO.fetchAllStates();
                                Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (SQLException e) {
                                database.upgrade();
                                Toast.makeText(
                                        context,
                                        "Error al Importar Estados, vuelva a importar",
                                        Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                            finally {
                                database.close();
                            }
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

                headers.put("company", CurrentData.getSettings().getCompany());
                headers.put("branch", CurrentData.getSettings().getBranch());
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
                                List<Product> productos = objGson.fromJson(
                                        jarray.toString(),
                                        listType
                                );
                                database.open();
                                if(!Database.productDao.addProducts(productos))
                                {
                                    throw new SQLException("No se pudieron insertar los Productos");
                                }
                                //List<Product> testData = database.productDao.fetchAllProducts();
                                Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (SQLException e) {
                                database.upgrade();
                                Toast.makeText(
                                        context,
                                        "Error al Importar Productos, vuelva a importar",
                                        Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                            finally {
                                database.close();
                            }
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

                headers.put("company", CurrentData.getSettings().getCompany());
                headers.put("branch", CurrentData.getSettings().getBranch());
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
                                database.open();
                                if(!Database.kitDAO.addKits(kits))
                                {
                                    throw new SQLException("No se pudieron insertar los Kits");
                                }
                                //List<Kit> testData = database.kitDAO.fetchAllKits();
                                Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (SQLException e) {
                                database.upgrade();
                                Toast.makeText(
                                        context,
                                        "Error al Importar Kits, vuelva a importar",
                                        Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                            finally {
                                database.close();
                            }
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

                headers.put("company", CurrentData.getSettings().getCompany());
                headers.put("branch", CurrentData.getSettings().getBranch());
                headers.put("db", "VENMOV.FDB");

                return headers;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
}
