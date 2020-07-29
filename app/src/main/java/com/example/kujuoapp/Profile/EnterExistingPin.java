package com.example.kujuoapp.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.BaseClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class EnterExistingPin extends AppCompatActivity {

    ImageView back;
    TextView forgotPass;
    EditText et1,et2,et3,et4,et5,et6;
    String data = "";
    TextView incorrectpass;
    Intent intent;
    String mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_existing_pin);
        //getSupportActionBar().hide();
        back = findViewById(R.id.cp_backzep);
        forgotPass = findViewById(R.id.forgot_pass);
        incorrectpass = findViewById(R.id.incorrect_pass);
        et1 = findViewById(R.id.codess);
        et2 = findViewById(R.id.codess2);
        et3 = findViewById(R.id.codess3);
        et4 = findViewById(R.id.codess4);
        et5 = findViewById(R.id.codess5);
        et6 = findViewById(R.id.codess6);

        initUI();

        nextmove(et1,et2);
        nextmove(et2,et3);
        nextmove(et3,et4);
        nextmove(et4,et5);
        nextmove(et5,et6);

        intent = getIntent();
        mode = intent.getStringExtra("mode");


        et6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                 data= et1.getText().toString() +""+
                        et2.getText().toString() +""+
                        et3.getText().toString() +""+
                        et4.getText().toString() +""+
                        et5.getText().toString() +""+
                        et6.getText().toString();

                     verify_pincode(data);


               // BaseClass.toast(getApplicationContext(),""+toMD5(data));
            }
        });
    }

    private void verify_pincode(final String pin) {
        BaseClass.progress(EnterExistingPin.this);
        BaseClass.progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"verifyPin.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        //progress_spinner.dismiss();
                        //    Toast.makeText(getApplicationContext(),ServerResponse.toString(),Toast.LENGTH_SHORT).show();
                        if(ServerResponse.trim().equals("1")){

                            if ("change_pin_code".equals(mode)){
                                startActivity(new Intent(getApplicationContext(),ChangePinCodeActivity.class));
                                BaseClass.progressDialog.hide();
                                finish();

                            }else if ("change_email".equals(mode)){
                                startActivity(new Intent(getApplicationContext(),ChangeEmailActivity.class));
                                BaseClass.progressDialog.hide();
                                finish();
                            }
                            }else if (ServerResponse.trim().equals("0")){
                            incorrectpass.setVisibility(View.VISIBLE);
                            BaseClass.progressDialog.hide();
                            et1.getText().clear();
                            et2.getText().clear();
                            et3.getText().clear();
                            et4.getText().clear();
                            et5.getText().clear();
                            et6.getText().clear();
                            et6.setCursorVisible(false);

                        }
                        else {
                          BaseClass.toast(getApplicationContext(),"Error : Something went wrong!");
                            BaseClass.progressDialog.hide();
                            et1.getText().clear();
                            et2.getText().clear();
                            et3.getText().clear();
                            et4.getText().clear();
                            et5.getText().clear();
                            et6.getText().clear();
                            et6.setCursorVisible(false);
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
                params.put("userpin", toMD5(pin));
                return params;

            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);

    }

    public String toMD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    private void initUI() {
    back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    });

    forgotPass.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            BaseClass.toast(getApplicationContext(),"forget pass!");
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

}