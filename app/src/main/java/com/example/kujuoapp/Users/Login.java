package com.example.kujuoapp.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.util.Util;
import com.example.kujuoapp.R;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    public static EditText userphoneno,password;
    Button cont;
    String phoneno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       /* SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String check = preferences.getString("userlogin", "");
        if(check.equals("true"))
        {
            startActivity(new Intent(Login.this,UserDashboard.class));
            finish();
        }*/



            userphoneno=findViewById(R.id.loginphone);
        password=findViewById(R.id.loginpass);
        cont=findViewById(R.id.btn_cont);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.ap_bg, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.ap_bg));
        }

        intentSigup();

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(userphoneno.getText().toString()) && !TextUtils.isEmpty(password.getText().toString()))
                {
                    if(BaseClass.isNetworkConnected(Login.this))
                    {
                        countrtyCode();
                        userCredentials();
                    }
                    else
                        {
                            BaseClass.toast(Login.this,"Check Your Internet Connection");
                        }
                }
                else
                    {
                        BaseClass.toast(Login.this,"Fill All First");
                    }

            }
        });
    }

    private void intentSigup() {
        TextView sigup=findViewById(R.id.signup);
        sigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,SignUp.class));
                finish();
            }
        });
    }

    private void countrtyCode() {
        CountryCodePicker countryCodePicker;
        countryCodePicker =findViewById(R.id.ccp1);
        countryCodePicker.showFlag(true);
        countryCodePicker.hideNameCode(true);

        phoneno = "+"+countryCodePicker.getSelectedCountryCode()+userphoneno.getText().toString();


    }


    public void userCredentials() {

        BaseClass.progress(Login.this);
        BaseClass.progressDialog.show();
        BaseClass.progressDialog.setCancelable(true);

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"user_login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        BaseClass.progressDialog.dismiss();
                      //  Toast.makeText(getApplicationContext(),ServerResponse.trim(),Toast.LENGTH_SHORT).show();

                        if(ServerResponse.trim().equals("0"))
                        {
                            BaseClass.toast(Login.this,"Register First");
                        }
                        else if(ServerResponse.trim().equals("1"))
                        {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("userlogin","true");
                            editor.apply();

                            startActivity(new Intent(Login.this,UserDashboard.class));
                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        BaseClass.progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),volleyError.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                params.put("phone",phoneno);
                params.put("password",password.getText().toString().trim());

                return params;

            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);

    }
}