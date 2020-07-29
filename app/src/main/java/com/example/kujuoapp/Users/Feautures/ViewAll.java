package com.example.kujuoapp.Users.Feautures;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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
import com.example.kujuoapp.Users.Adapter.AllTransHistoryAdapter;
import com.example.kujuoapp.Users.Adapter.TransHistoryAdapter;
import com.example.kujuoapp.Users.BaseClass;
import com.example.kujuoapp.Users.DataClass.AllTrans;
import com.example.kujuoapp.Users.DataClass.TransHistoryData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewAll extends AppCompatActivity {

    List<AllTrans> data=new ArrayList<>();
    RecyclerView recyclerView;
    Button viewall;
    TextView no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        no=findViewById(R.id.nohistory);
        statusbar();
        recyclerView();
        back();
    }

    private void recyclerView()
    {
        recyclerView=findViewById(R.id.transhistory);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,true);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        if(BaseClass.isNetworkConnected(ViewAll.this))
            fetchdata();
        else
            BaseClass.toast(ViewAll.this,"Check Your Internet Connection");


    }
    private void statusbar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = ViewAll.this.getWindow();

            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.TRANSPARENT);

        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    private void fetchdata() {
        BaseClass.progress(ViewAll.this);

        BaseClass.progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"trans_history.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        BaseClass.progressDialog.dismiss();                     //   Toast.makeText(getApplicationContext(),ServerResponse.toString(),Toast.LENGTH_SHORT).show();
                        if(ServerResponse.trim().equals("0")){
                            no.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(),"Not Found",Toast.LENGTH_SHORT).show();
                        }
                        else{

                            try {
                                JSONArray jsonArray = new JSONArray(ServerResponse);
                                if (jsonArray.length() > 0) {
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                      /*  'trans_id'=>$row['trans_id'],
                                                'send_amount'=>$row['send_amount'],
                                                'sender_message'=>$row['sender_message'],
                                                'receiver_id'=>$row['receiver_id'],
                                                'transaction_type'=>$row['transaction_type'],
                                                'transaction_charges'=>$row['transaction_charges'],
                                                'trsacted_amount'=>$row['trsacted_amount'],
                                                'date_time'=>$row['date_time'],
                                                'rec_name'=>$row['user_name'],
                                                'rec_pic'=>$row['profile_pic'],
                                                'rec_phone'=>$row['user_phoneno'],*/
                                        JSONObject info = jsonArray.getJSONObject(j);
                                        data.add(new AllTrans(info.getString("trans_id"),
                                                info.getString("rec_pic"),info.getString("rec_name"),
                                                info.getString("rec_phone"),info.getString("date_time"),
                                                info.getString("date_time"),info.getString("trsacted_amount")
                                                ,info.getString("sender_message")
                                                ,info.getString("transaction_charges"),
                                                info.getString("transaction_type"),
                                                info.getString("send_amount"),
                                                info.getString("receiver_id")));

                                    }// levelAdapter.notifyDataSetChanged();
                                    AllTransHistoryAdapter adapter=new AllTransHistoryAdapter(ViewAll.this,data);
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                String userid=preferences1.getString("user_id", "");
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                if(userid!=null)
                    params.put("userid", userid);
                else
                    params.put("userid","0");

                return params;

            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);

    }


    private void back() {
        ImageView back=findViewById(R.id.tback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}