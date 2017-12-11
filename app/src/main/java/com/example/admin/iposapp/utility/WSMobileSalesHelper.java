package com.example.admin.iposapp.utility;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
