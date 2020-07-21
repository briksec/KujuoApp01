package com.example.kujuoapp.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kujuoapp.R;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    EditText name,contact,pass,email;
    Button  cont;
    String phoneno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.ap_bg, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.ap_bg));
        }
        init();

    }

    private void init()
    {
        name=findViewById(R.id.name);
        contact=findViewById(R.id.contactno);
        pass=findViewById(R.id.pass);
        cont=findViewById(R.id.btn_cont);
        email=findViewById(R.id.email);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty())
                {
                    name.setError("Enter a Name First");
                }
                else if(contact.getText().toString().isEmpty())
                {
                    contact.setError("Enter a Phone Number First");
                }
                  else if(pass.getText().toString().isEmpty())
                {
                    pass.setError("Enter a Password First");
                }
                  else if(contact.getText().toString().length()!=10)
                {
                    contact.setError("Enter a Correct Number");
                }
                  else if(email.getText().toString().isEmpty())
                {
                    email.setError("Enter a Email");
                }
                  else
                      {
                          countrtyCode();
                          userCredentials();
                      }




            }
        });
    }

    private void countrtyCode() {
        CountryCodePicker countryCodePicker;
        countryCodePicker =findViewById(R.id.ccp1);
        countryCodePicker.showFlag(true);
        countryCodePicker.hideNameCode(true);

        phoneno = "+"+countryCodePicker.getSelectedCountryCode()+contact.getText().toString();


    }

    public void userCredentials() {

        BaseClass.progress(SignUp.this);
        BaseClass.progressDialog.show();
        BaseClass.progressDialog.setCancelable(true);

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"insert_user_info.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                       Toast.makeText(getApplicationContext(),ServerResponse.trim(),Toast.LENGTH_SHORT).show();

                        if(ServerResponse.trim().equals("0"))
                        {
                            BaseClass.progressDialog.dismiss();
                            BaseClass.toast(SignUp.this,"Try Again");
                        }
                        else
                        {
                            BaseClass.progressDialog.dismiss();

                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("user_id",ServerResponse.trim());
                            editor.apply();
                            startActivity(new Intent(SignUp.this,UserDashboard.class));
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

                params.put("user_name",name.getText().toString().trim());
                params.put("phoneno",phoneno);
                params.put("pass",pass.getText().toString().trim());
                params.put("email",email.getText().toString().trim());

                return params;

            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);

    }
}