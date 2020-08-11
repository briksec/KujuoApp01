package com.example.kujuoapp.Users.Feautures;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.example.kujuoapp.Users.Adapter.TransHistoryAdapter;
import com.example.kujuoapp.Users.BaseClass;
import com.example.kujuoapp.Users.DataClass.TransHistoryData;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class Transfer extends AppCompatActivity {

    ImageView scanQRCode;
    RelativeLayout walTowal, walTonic;
    List<TransHistoryData> data=new ArrayList<>();
    RecyclerView recyclerView;
    Button viewall;
    TextView no;
    TransHistoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        scanQRCode = findViewById(R.id.scanqrcode);
        walTowal=findViewById(R.id.walletToWallet);
        walTonic=findViewById(R.id.walletToCnic);
        no=findViewById(R.id.nohistory);
        viewall=findViewById(R.id.viewAll);

        walTowal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Transfer.this,WalletToWallet.class).putExtra("active","www"));
                finish();
            }
        });
        walTonic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Transfer.this,WalletToCnic.class));
            }
        });
        statusbar();

        recyclerView();

        back();

        scanQRMethod();

        viewAll();
    }

    private void viewAll()
    {
        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Transfer.this,ViewAll.class));

            }
        });

    }


    private void scanQRMethod() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);
        scanQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(Transfer.this);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.setPrompt("");
                intentIntegrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if (intentResult != null){

            if (intentResult.getContents() != null){
                Toasty.success(getApplicationContext(),intentResult.getContents().toString(),Toasty.LENGTH_LONG).show();
            }else {
                // Toasty.error(getApplicationContext(),"Error: Something went wrong!",Toasty.LENGTH_LONG).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }



    private void recyclerView()
    {
         recyclerView=findViewById(R.id.transhistorys);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,RecyclerView.HORIZONTAL,true);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
         adapter=new TransHistoryAdapter(Transfer.this,data);
        recyclerView.setAdapter(adapter);
        if(BaseClass.isNetworkConnected(Transfer.this))
            fetchdata();
        else
            BaseClass.toast(Transfer.this,"Check Your Internet Connection");


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

    private void statusbar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = Transfer.this.getWindow();

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
        BaseClass.progress(Transfer.this);

        BaseClass.progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"trans_history.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        BaseClass.progressDialog.dismiss();
                        if(ServerResponse.trim().equals("0")){
                            no.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(),"Not Found",Toast.LENGTH_SHORT).show();
                        }
                        else{

                        try {

                            JSONArray jsonArray = new JSONArray(ServerResponse);
                            if (jsonArray.length() > 0) {

                                for (int j = 0; j < jsonArray.length(); j++) {
                                    JSONObject info = jsonArray.getJSONObject(j);
                                    Toast.makeText(getApplicationContext(),info.getString("trans_id"),Toast.LENGTH_SHORT).show();


                                    data.add(new TransHistoryData(info.getString("trans_id"),
                                            info.getString("rec_pic"),info.getString("rec_name"),
                                            info.getString("rec_phone"),info.getString("date_time"),
                                            info.getString("date_time"),info.getString("send_amount")));
                           //         Toast.makeText(getApplicationContext(),info.getString("trans_id"),Toast.LENGTH_SHORT).show();
                                    recyclerView.setVisibility(View.VISIBLE);
                                }// levelAdapter.notifyDataSetChanged();

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
                        Toast.makeText(getApplicationContext(),"Check Your Internet Connection", Toast.LENGTH_LONG).show();

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

}