package com.example.kujuoapp.Users.Feautures;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WalletToCnic extends AppCompatActivity {


    java.util.HashMap<String,String> HashMap;
    Spinner balchk;
    List<String> countryName=new ArrayList<String>();
    TextView balance,tcuurency;
    ImageView open_eye,close_eye;
    String contactno,percentage,walletamount;
    boolean showBalance=false;
    CountryCodePicker countryCodePicker;
    EditText phoneno,amount,nis;
    Button cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_to_cnic);

        if(BaseClass.isNetworkConnected(WalletToCnic.this))
        {
            fetch_percentage();
            first_fetch_wallet_balance();
        }
        else
            {
                BaseClass.toast(WalletToCnic.this,"Check Your Internet Connection");
                finish();
            }

        init();
        statusbar();
        FetchCurrency();
        back();
        hideAndShow();

        continues();
        //attach the listener to the spinner
        balchk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String value=HashMap.get(balchk.getSelectedItem().toString());
              //  BaseClass.toast(WalletToCnic.this,value);
                TextView selectedText = (TextView) parent.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(Color.WHITE);
                }
                if(balchk.getSelectedItem()!=null && value != null && balance.getText().toString()!= null && balance.getText().toString()!= "" && showBalance==true )
                {
                    tcuurency.setText(balchk.getSelectedItem().toString());

                    Double cal= Double.parseDouble("1.00") * Double.parseDouble(value);

                    balance.setText(String.valueOf(cal));
                }


              //  ((TextView) view).setTextColor(Color.WHITE); //Change selected text color

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void continues()
    {
        if(phoneno.getText().length()==10)
        {
            if(nis.getText().length()>11)
            {
                if(amount.getText().length()>0)
                {
                    contactno=countryCodePicker.getSelectedCountryCode()+phoneno.getText().toString();
                }
                else
                {
                    amount.setError("Complete Fast");
                }
            }
            else
            {
                nis.setError("Complete Fast");
            }
        }
        else
            {
                phoneno.setError("Must Be 10 Digit");
            }
    }

    private void init() {
        HashMap=new HashMap<String, String>();
        balchk=findViewById(R.id.balCurr);
        balance=findViewById(R.id.t_balance);
        tcuurency=findViewById(R.id.t_currency);
        open_eye=findViewById(R.id.openeye);
        close_eye=findViewById(R.id.closeeye);
        countryCodePicker=findViewById(R.id.ccp1);
        phoneno=findViewById(R.id.t_no);
        nis=findViewById(R.id.t_cnicno);
        amount=findViewById(R.id.t_amount);
        cont=findViewById(R.id.t_cont);

    }



    private void hideAndShow() {


        close_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseClass.toast(WalletToCnic.this,"dsa");

                    pwdpopup(true);
            }
        });
        open_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseClass.toast(WalletToCnic.this,"dsa");

                balance.setText("****");
                open_eye.setVisibility(View.GONE);
                close_eye.setVisibility(View.VISIBLE);
                showBalance=false;
            }
        });
    }

    private void pwdpopup(final boolean showpass)
    {
        final Dialog dialog = new Dialog(WalletToCnic.this);
        dialog.setContentView(R.layout.pwd_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);

        final EditText p1=dialog.findViewById(R.id.pin1);
        final EditText p2=dialog.findViewById(R.id.pin2);
        final EditText p3=dialog.findViewById(R.id.pin3);
        final EditText p4=dialog.findViewById(R.id.pin4);
        final EditText p5=dialog.findViewById(R.id.pin5);
        final EditText p6=dialog.findViewById(R.id.pin6);

        final Button proceed=dialog.findViewById(R.id.p_proceed);
        final Button cancel=dialog.findViewById(R.id.p_cancel);


        nextmove(p1,p2);
        nextmove(p2,p3);
        nextmove(p3,p4);
        nextmove(p4,p5);
        nextmove(p5,p6);


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String grt_pin=p1.getText().toString()+p2.getText().toString()+p3.getText().toString()+
                        p4.getText().toString()+p5.getText().toString()+p6.getText().toString();
                if(grt_pin.equals("123456"))
                {
                    if(showpass==true)
                    {
                        dialog.dismiss();
                        close_eye.setVisibility(View.GONE);
                        open_eye.setVisibility(View.VISIBLE);
                        balance.setText("1");
                        showBalance=true;
                    }
                    else
                        {

                        }
                }
                else
                    {
                        BaseClass.toast(WalletToCnic.this,"Wrong Pin");
                    }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void nextmove(final EditText e1,final EditText e2)
    {
        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // specify length of your editext here to move on next edittext
                if(e1.getText().toString().trim().length()==0){
                    e2.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void statusbar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = WalletToCnic.this.getWindow();

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


    private void FetchCurrency() {
        BaseClass.progress(WalletToCnic.this);
        BaseClass.progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.exchangeratesapi.io/latest?base=USD",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        BaseClass.progressDialog.show();

                        // BaseClass.toast(WalletToWallet.this,ServerResponse.toString());
                        if(ServerResponse.trim().equals("Something Wrong"))
                        {
                            Toast.makeText(getApplicationContext(),"Try Again".toString(),Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            try {
                                JSONObject jsonObject = new JSONObject(ServerResponse);

                                JSONObject jsonObject1=new JSONObject(jsonObject.getJSONObject("rates").toString());


                                HashMap.put("USD",jsonObject1.getString("USD"));
                                HashMap.put("CAD",jsonObject1.getString("CAD"));
                                HashMap.put("HKD",jsonObject1.getString("HKD"));
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
                                HashMap.put("MXN",jsonObject1.getString("MXN"));
                                HashMap.put("SGD",jsonObject1.getString("SGD"));
                                HashMap.put("AUD",jsonObject1.getString("AUD"));
                                HashMap.put("ILS",jsonObject1.getString("ILS"));
                                HashMap.put("KRW",jsonObject1.getString("KRW"));
                                HashMap.put("PLN",jsonObject1.getString("PLN"));

                                for(Map.Entry map  :  HashMap.entrySet() )

                                {
                                   countryName.add(String.valueOf(map.getKey()));
                                }
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(WalletToCnic.this, android.R.layout.simple_spinner_item, countryName);
                                //set the view for the Drop down list
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //set the ArrayAdapter to the spinner
                                balchk.setAdapter(dataAdapter);

                                balchk.setSelection(dataAdapter.getPosition("USD"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        BaseClass.progressDialog.show();

                        Toast.makeText(getApplicationContext(),"Check your Internet Connection", Toast.LENGTH_LONG).show();

                        finish();
                    }
                }) {

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);
    }


    private void back() {

        ImageView back = findViewById(R.id.tback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void fetch_percentage() {
        BaseClass.progress(WalletToCnic.this);
        BaseClass.progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"fetch_admin_percentage.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        BaseClass.progressDialog.dismiss();
                        percentage=ServerResponse.trim();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        BaseClass.progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Check your Internet Connection", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }) {

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);
    }

    public void first_fetch_wallet_balance() {

        BaseClass.progress(WalletToCnic.this);
        BaseClass.progressDialog.show();
        BaseClass.progressDialog.setCancelable(true);

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"fetch_wallet_ballance.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        BaseClass.progressDialog.dismiss();
                        //  Toast.makeText(getApplicationContext(),ServerResponse.trim(),Toast.LENGTH_SHORT).show();


                        if(ServerResponse.trim().equals("false"))
                        {
                            BaseClass.toast(WalletToCnic.this,"Try again");
                        }
                        else
                        {
                            walletamount=ServerResponse.trim();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        BaseClass.progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Restart Your App...", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                String userid=preferences1.getString("user_id", "");
                params.put("userid",userid);

                return params;

            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);

    }

}