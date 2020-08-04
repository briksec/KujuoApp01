package com.example.kujuoapp.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.example.kujuoapp.SQLiteDatabase.DatabaseHelper;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    EditText name,contact,email,code, code2, code3, code4, code5, code6;
    Button  cont;
    String phoneno;
    DatabaseHelper db;
    String total_pin = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db = new DatabaseHelper(getApplicationContext());
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
        //pass=findViewById(R.id.pass);
        code = findViewById(R.id.codess);
        code2 = findViewById(R.id.codess2);
        code3 = findViewById(R.id.codess3);
        code4 = findViewById(R.id.codess4);
        code5 = findViewById(R.id.codess5);
        code6 = findViewById(R.id.codess6);
        cont=findViewById(R.id.btn_cont);
        email=findViewById(R.id.email);


        nextmove(code,code2);
        nextmove(code2,code3);
        nextmove(code3,code4);
        nextmove(code4,code5);
        nextmove(code5,code6);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total_pin=code.getText().toString().trim()+code2.getText().toString().trim()+code3.getText().toString().trim()+code4.getText().toString().trim()+code5.getText().toString().trim()+code6.getText().toString().trim();

                if(name.getText().toString().isEmpty())
                {
                    name.setError("Enter a Name First");
                }
                else if(contact.getText().toString().isEmpty())
                {
                    contact.setError("Enter a Phone Number First");
                }
                  else if(code.getText().toString().isEmpty() || code2.getText().toString().isEmpty() || code3.getText().toString().isEmpty() || code4.getText().toString().isEmpty() || code5.getText().toString().isEmpty() || code6.getText().toString().isEmpty())
                {
                    code.setError("Enter a Pin No First");
                    code2.setError("Enter a Pin No First");
                    code3.setError("Enter a Pin No First");
                    code4.setError("Enter a Pin No First");
                    code5.setError("Enter a Pin No First");
                    code6.setError("Enter a Pin No First");
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

    private void nextmove(final EditText e1,final EditText e2)
    {
        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // specify length of your editext here to move on next edittext

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(e1.getText().toString().trim().length()==1){
                    e2.requestFocus();
                    e1.hashCode();
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
        //BaseClass.toast(getApplicationContext(),""+total_pin);
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
                            //Edited by Zeeshan
                            String[] split=ServerResponse.split("!");
                            editor.putString("user_id",split[0].toString());
                            editor.putString("userlogin","true");
                            editor.apply();

//                            if (db.insertNewUser(split[0],split[1])){
//                                BaseClass.toast(getApplicationContext(),"sent to sqlite!");
//                             }else {
//                                BaseClass.toast(getApplicationContext(),"Not sent to sqlite!");
//                            }
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
                params.put("pass",total_pin.toString());
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