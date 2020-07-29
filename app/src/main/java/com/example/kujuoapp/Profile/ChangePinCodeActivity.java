package com.example.kujuoapp.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
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

public class ChangePinCodeActivity extends AppCompatActivity {

    ImageView back;
    Button save;
    EditText et1,et2,et3,et4,et5,et6;
    String data = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin_code);
//        getSupportActionBar().hide();
        back = findViewById(R.id.cp_backzcp);
        et1 = findViewById(R.id.codesss);
        et2 = findViewById(R.id.codesss2);
        et3 = findViewById(R.id.codesss3);
        et4 = findViewById(R.id.codesss4);
        et5 = findViewById(R.id.codesss5);
        et6 = findViewById(R.id.codesss6);
        save = findViewById(R.id.btn_save_changepincode);
        initUI();

        nextmove(et1,et2);
        nextmove(et2,et3);
        nextmove(et3,et4);
        nextmove(et4,et5);
        nextmove(et5,et6);
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

                if (!et1.getText().toString().equals("") ||
                        !et2.getText().toString().equals("") ||
                        !et3.getText().toString().equals("") ||
                        !et4.getText().toString() .equals("") ||
                        !et5.getText().toString() .equals("") ||
                        !et6.getText().toString().equals("") )
                {
                    data= et1.getText().toString() +""+
                            et2.getText().toString() +""+
                            et3.getText().toString() +""+
                            et4.getText().toString() +""+
                            et5.getText().toString() +""+
                            et6.getText().toString();
                    saveChangedPin(data);
                }else {
                    BaseClass.toast(getApplicationContext(),"Enter the valid pin code!");
                }

            }
        });
    }

    private void saveChangedPin(final String data) {
        BaseClass.progress(ChangePinCodeActivity.this);
        BaseClass.progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"updatePin.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        //progress_spinner.dismiss();
                        //    Toast.makeText(getApplicationContext(),ServerResponse.toString(),Toast.LENGTH_SHORT).show();
                        if(ServerResponse.trim().equals("1")){
                            BaseClass.toast(getApplicationContext(),"Pin Code Updated Successfully!");
                            BaseClass.progressDialog.hide();
                            finish();
                        }else if (ServerResponse.trim().equals("0")){
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
                params.put("userpin", data);
                return params;

            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);
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