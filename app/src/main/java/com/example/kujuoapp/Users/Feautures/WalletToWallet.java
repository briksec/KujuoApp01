package com.example.kujuoapp.Users.Feautures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WalletToWallet extends AppCompatActivity {

     LinearLayout phonelayout,walletlayout,phoneparentlaout;
    TextView phontext;
    ImageView phoneicon;
    HashMap<String,String> HashMap;
    Spinner balchk;
    List<String> countryName=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_to_wallet);
        balchk=findViewById(R.id.balCurr);

        HashMap=new HashMap<String, String>();

        init();
        statusbar();
        checkPhone();

/*        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(WalletToWallet.this,android.R.layout.simple_spinner_item,countryName);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        balchk.setAdapter(arrayAdapter);
        balchk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {        BaseClass.toast(WalletToWallet.this,"ss");

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });*/

    }

    private void init() {


        phonelayout=findViewById(R.id.phonumlayout);
        phontext=findViewById(R.id.phonetext);
        phoneicon=findViewById(R.id.phoneicon);
        phoneparentlaout=findViewById(R.id.phoneparentlayout);
        walletlayout=findViewById(R.id.walletlayout);



        phonelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(walletlayout.getVisibility()==View.VISIBLE)
                {
                    walletlayout.setVisibility(View.GONE);
                    phoneparentlaout.setVisibility(View.VISIBLE);
                    phontext.setText("Wallet ID");
                    phoneicon.setImageResource(R.drawable.wallet_24);
                }
                else
                    {
                        walletlayout.setVisibility(View.VISIBLE);
                        phoneparentlaout.setVisibility(View.GONE);
                        phontext.setText("Phone Number");
                        phoneicon.setImageResource(R.drawable.phoneicon);
                    }

            }
        });



    }


    private void statusbar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = WalletToWallet.this.getWindow();

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

    private void checkPhone() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.exchangeratesapi.io/latest?base=USD",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                       // BaseClass.toast(WalletToWallet.this,ServerResponse.toString());
                        if(ServerResponse.trim().equals("Something Wrong"))
                        {
                            Toast.makeText(getApplicationContext(),"Try Again".toString(),Toast.LENGTH_SHORT).show();
                        }
                        else{
                            try {
                                JSONObject jsonObject = new JSONObject(ServerResponse);

                                JSONObject jsonObject1=new JSONObject(jsonObject.getJSONObject("rates").toString());



                                HashMap.put("CAD",jsonObject1.getString("CAD"));
                                HashMap.put("HKD",jsonObject1.getString("HKD"));
/*
                                HashMap.put("ISK",jsonObject1.getString("ISK"));
                                HashMap.put("PHP",jsonObject1.getString("PHP"));
                                HashMap.put("DKK",jsonObject1.getString("DKK"));
                                HashMap.put("HUF",jsonObject1.getString("HUF"));
                                HashMap.put("CZK",jsonObject1.getString("CZK"));
                                HashMap.put("GBP",jsonObject1.getString("GBP"));
                                HashMap.put("RON",jsonObject1.getString("RON"));
                                HashMap.put("SEK",jsonObject1.getString("SEK"));
                                HashMap.put("IDR",jsonObject1.getString("IDR"));
                                HashMap.put("INR",jsonObject1.getString("INR"));
                                HashMap.put("BRL",jsonObject1.getString("BRL"));
                                HashMap.put("RUB",jsonObject1.getString("RUB"));
                                HashMap.put("HRK",jsonObject1.getString("HRK"));
                                HashMap.put("JPY",jsonObject1.getString("JPY"));
                                HashMap.put("THB",jsonObject1.getString("THB"));
                                HashMap.put("CHF",jsonObject1.getString("CHF"));
                                HashMap.put("EUR",jsonObject1.getString("EUR"));
                                HashMap.put("MYR",jsonObject1.getString("MYR"));
                                HashMap.put("BGN",jsonObject1.getString("BGN"));
                                HashMap.put("TRY",jsonObject1.getString("TRY"));
                                HashMap.put("CNY",jsonObject1.getString("CNY"));
                                HashMap.put("NOK",jsonObject1.getString("NOK"));
                                HashMap.put("NZD",jsonObject1.getString("NZD"));
                                HashMap.put("ZAR",jsonObject1.getString("ZAR"));
                                HashMap.put("USD",jsonObject1.getString("USD"));
                                HashMap.put("MXN",jsonObject1.getString("MXN"));
                                HashMap.put("SGD",jsonObject1.getString("SGD"));
                                HashMap.put("AUD",jsonObject1.getString("AUD"));
                                HashMap.put("ILS",jsonObject1.getString("ILS"));
                                HashMap.put("KRW",jsonObject1.getString("KRW"));
                                HashMap.put("PLN",jsonObject1.getString("PLN"));
*/

                                for(Map.Entry map  :  HashMap.entrySet() )

                                {
                                  //  BaseClass.toast(WalletToWallet.this,map.getKey()+"");
                                    countryName.add(map.getKey()+"");
                                    //   System.out.println(map.getKey()+" "+map.getValue());
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

                        Toast.makeText(getApplicationContext(),"Check your Internet Connection", Toast.LENGTH_LONG).show();

                    }
                }) {

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);
    }


}