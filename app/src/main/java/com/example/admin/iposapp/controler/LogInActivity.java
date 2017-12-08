package com.example.admin.iposapp.controler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.BaseResponse;
import com.example.admin.iposapp.model.Settings;
import com.example.admin.iposapp.utility.CurrentData;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends Activity implements View.OnClickListener
{

    //private MenuActivity menu;
    private EditText user;
    private EditText pswd;
    private Button btnLogin;
    private Context ctx;
    private Database database;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        user = (EditText)findViewById(R.id.userET);
        pswd = (EditText)findViewById(R.id.pswdET);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        ctx = getApplicationContext();

        database = new Database(ctx);

        btnLogin.setOnClickListener(this);

        requestQueue = Volley.newRequestQueue(this);
        String url =
                "http://10.0.2.2:8085/ServiceContract/Implementation/WSClients.svc/PostTest";

        BaseResponse<String> body = new BaseResponse<>();
        body.setData("Hola Mundo");
        body.setMessage("SUCCESS");

        HashMap<String, String> postBody = new HashMap<>();
        postBody.put("Data", body.getData());
        postBody.put("Message", body.getMessage());

        JSONObject jsonBody = new JSONObject(postBody);

        JsonObjectRequest postRequest= new JsonObjectRequest(
                Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Server error..", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        }){
            @Override
            public Map<String, String> getHeaders(){

                return new HashMap<>();
            }
        };

        requestQueue.add(postRequest);

        Log.w("ISAIAS", "ISAIAS");
    }


    @Override
    public void onClick(View v)
    {
            try
            {
               database.open();
                Settings setting = Database.settingsDAO.fetchSettingsBySeller(
                        user.getText().toString(),
                        pswd.getText().toString());

                Log.w("CREDENCIALES: ",
                        setting.getAppUser() + "-" + setting.getAppUserPass() + "-"
                        + setting.getClientSerie());

                if(user.getText().toString().equals(setting.getAppUser()) &&
                        pswd.getText().toString().equals(setting.getAppUserPass()))
                {

                    if(setting.getClientSerie() == null)
                    {
                        setting.setClientSerie("0");
                    }
                    CurrentData.setSettings(setting);
                    CurrentData.setIsSync(true);
                    CurrentData.setLastClientInserted(Integer.valueOf(setting.getClientSerie()));
                    Intent intent = new Intent(this, MenuActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(ctx, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }

                /*Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);*/
            }
            catch(Exception ex)
            {
                Toast.makeText(ctx, "Usuario invalido", Toast.LENGTH_SHORT).show();
                ex.printStackTrace();
            }
    }
}
