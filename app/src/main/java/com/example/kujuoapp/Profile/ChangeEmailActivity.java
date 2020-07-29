package com.example.kujuoapp.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.BaseClass;

import java.util.HashMap;
import java.util.Map;

public class ChangeEmailActivity extends AppCompatActivity {

    ImageView back;
    EditText email;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        back = findViewById(R.id.back_ce);
        email = findViewById(R.id.change_email_ce);
        save = findViewById(R.id.btn_save_ce);

        initUI();
    }

    private void initUI() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty())
                {
                    BaseClass.toast(getApplicationContext(),"Email field cannot be empty");
                }else if((!email.getText().toString().contains("@") && !email.getText().toString().contains("."))){
                    BaseClass.toast(getApplicationContext(),"Email Enter a valid email address");
                }else {
                    isEmailAlreadrExists(email.getText().toString());
                }
            }
        });
    }

    private void isEmailAlreadrExists(final String email) {
        BaseClass.progress(ChangeEmailActivity.this);
        BaseClass.progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"isemailalreadyexist.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        //progress_spinner.dismiss();
                        //    Toast.makeText(getApplicationContext(),ServerResponse.toString(),Toast.LENGTH_SHORT).show();
                        if(ServerResponse.trim().equals("1")){
                            BaseClass.toast(getApplicationContext(),"This email is already registered to an account!");
                            BaseClass.progressDialog.hide();

                        }else if (ServerResponse.trim().equals("0")){
                            changeEmail(email);
                            BaseClass.progressDialog.hide();
                        }
                        else {
                            BaseClass.toast(getApplicationContext(),"Error : Something went wrong!");
                            BaseClass.progressDialog.hide();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(getApplicationContext(),"Check Your Internet Connection", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                params.put("newemail", email);
                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);
    }

    private void changeEmail(final String email) {
        BaseClass.progress(ChangeEmailActivity.this);
        BaseClass.progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"updateEmail.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        //progress_spinner.dismiss();
                        //    Toast.makeText(getApplicationContext(),ServerResponse.toString(),Toast.LENGTH_SHORT).show();
                        if(ServerResponse.trim().equals("1")){
                            BaseClass.toast(getApplicationContext(),"Email saved successfully");
                            BaseClass.progressDialog.hide();
                            finish();

                        }else if (ServerResponse.trim().equals("0")){
                            BaseClass.toast(getApplicationContext(),"Error : something went wrong");
                            BaseClass.progressDialog.hide();
                        }
                        else {
                            BaseClass.toast(getApplicationContext(),"Error : Something went wrong!");
                            BaseClass.progressDialog.hide();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(getApplicationContext(),"Check Your Internet Connection", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                String userid=preferences1.getString("user_id", "");
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                params.put("userid", userid);
                params.put("newemail", email);
                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}