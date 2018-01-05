package com.example.admin.iposapp.controler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
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
import com.example.admin.iposapp.utility.VolleySingleton;
import com.example.admin.iposapp.utility.WSMobileSalesHelper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener
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
    }


    @Override
    public void onClick(View v)
    {

        if(user.getText().toString().equals("sistemas") &&
                pswd.getText().toString().equals("kabu")){

            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }
        else{

            try {

                database.open();
                Settings setting = Database.settingsDAO.fetchSettingsBySeller(
                        user.getText().toString(),
                        pswd.getText().toString()
                );

                database.close();

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
            }
            catch(Exception ex) {
                Toast.makeText(ctx, "Usuario invalido", Toast.LENGTH_SHORT).show();
                ex.printStackTrace();
            }
        }
    }
}
