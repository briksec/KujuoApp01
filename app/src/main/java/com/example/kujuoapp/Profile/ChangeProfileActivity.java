package com.example.kujuoapp.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangeProfileActivity extends AppCompatActivity {

    ImageView arrowright,arrowbottom;
    EditText phoneEdit, nameEdit;
    TextView usersetdname,usersetdphone;
    LinearLayout fnameline,phoneline;
    ExpandGetterSetter eGSetter;
    Button save_changes_btn;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        getSupportActionBar().hide();
        arrowright = findViewById(R.id.fname_arrow);
        arrowbottom = findViewById(R.id.phno_arrow);
        usersetdname = findViewById(R.id.my_username);
        fnameline = findViewById(R.id.fname_line);
        phoneline = findViewById(R.id.phoneline);
        usersetdphone = findViewById(R.id.my_userphone);
        phoneEdit = findViewById(R.id.ph_edit);
        nameEdit = findViewById(R.id.fullname_edit);
        save_changes_btn = findViewById(R.id.save_changes);
        back = findViewById(R.id.cp_backz);


        fetchUData();
        eGSetter = new ExpandGetterSetter();
        eGSetter.setCheckname(true);
        eGSetter.setCheckphone(true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*ProfileFragment.fetchUData();*/
                finish();
            }
        });
        fnameline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (eGSetter.isCheckname()){
                    arrowright.setBackgroundResource(R.drawable.ic_arrow_down);
                    usersetdname.setVisibility(View.GONE);
                    nameEdit.setVisibility(View.VISIBLE);
                    save_changes_btn.setEnabled(true);
                    eGSetter.setCheckname(false);
                }else {
                    arrowright.setBackgroundResource(R.drawable.ic_right_arrow);
                    usersetdname.setVisibility(View.VISIBLE);
                    nameEdit.setVisibility(View.GONE);
                    eGSetter.setCheckname(true);
                }

            }
        });

        phoneline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eGSetter.isCheckphone()){
                    arrowbottom.setBackgroundResource(R.drawable.ic_arrow_down);
                    usersetdphone.setVisibility(View.GONE);
                    phoneEdit.setVisibility(View.VISIBLE);
                    save_changes_btn.setEnabled(true);
                    eGSetter.setCheckphone(false);
                }else {
                    arrowbottom.setBackgroundResource(R.drawable.ic_right_arrow);
                    usersetdphone.setVisibility(View.VISIBLE);
                    phoneEdit.setVisibility(View.GONE);
                    eGSetter.setCheckphone(true);
                }

            }
        });

        save_changes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phoneEdit.getText().toString().isEmpty() || nameEdit.getText().toString().isEmpty()){
                    return;
                }else {
                    updateUData(nameEdit.getText().toString(),phoneEdit.getText().toString());
                    fetchUData();
                }
            }
        });
    }

    private void updateUData(final String name, final String phone) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"update_profile_data.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        //progress_spinner.dismiss();
                         Toast.makeText(getApplicationContext(),ServerResponse.toString(),Toast.LENGTH_SHORT).show();

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
                params.put("username", name);
                params.put("phone", phone);
                return params;

            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);
    }


    private void fetchUData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"fetch_profile_data1.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        //progress_spinner.dismiss();
                        // Toast.makeText(getContext(),ServerResponse.toString(),Toast.LENGTH_SHORT).show();
                        if(ServerResponse.trim().equals("0")){
                            Toast.makeText(getApplicationContext(),"Not Found",Toast.LENGTH_SHORT).show();
                        }


                        try {
                            JSONArray jsonArray = new JSONArray(ServerResponse);
                            if (jsonArray.length() > 0) {
                                for (int j = 0; j < jsonArray.length(); j++) {

                                    JSONObject info = jsonArray.getJSONObject(j);
                                    usersetdname.setText(info.getString("user_name"));
                                    usersetdphone.setText(info.getString("user_phoneno"));

                                    nameEdit.setText(info.getString("user_name"));
                                    phoneEdit.setText(info.getString("user_phoneno"));

                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
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


                return params;

            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);

    }
}