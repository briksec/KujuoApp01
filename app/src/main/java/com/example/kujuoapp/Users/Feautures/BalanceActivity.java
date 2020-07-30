package com.example.kujuoapp.Users.Feautures;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
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
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.BaseClass;
import com.example.kujuoapp.Users.UserDashboard;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BalanceActivity extends AppCompatActivity {

    ImageView back;
    ImageView open_eye,close_eye;
    boolean qrCode=false,eyeopen=false;
    TextView balance,tcuurency,view_currency;
    HashMap<String,String> HashMap;
    Spinner balchk,convert2;
    ArrayList<String> countryName=new ArrayList<>();
    String walletamount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        back = findViewById(R.id.wbackz);
///        getSupportActionBar().hide();
        statusbar();
        HashMap=new HashMap<String, String>();

        initUI();
        hideAndShow();
        if(BaseClass.isNetworkConnected(BalanceActivity.this))
        {
            FetchCurrency();
        }
        else
        {
            BaseClass.toast(BalanceActivity.this,"Check Your Internet Connection");
            finish();
        }
        spinner_work();



    }
    private void statusbar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = BalanceActivity.this.getWindow();

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

    private void spinner_work()
    {
        //attach the listener to the spinner
        balchk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String value=HashMap.get(balchk.getSelectedItem().toString());
                //  BaseClass.toast(WalletToWallet.this,value);
                TextView selectedText = (TextView) parent.getChildAt(0);


                if (selectedText != null)
                {
                    selectedText.setTextColor(Color.WHITE);
                }
                if(balchk.getSelectedItem()!=null && value != null && balance.getText().toString()!= null && balance.getText().toString()!= ""&& walletamount!=null)
                {
                    tcuurency.setText(balchk.getSelectedItem().toString());

                    Double cal= Double.parseDouble(walletamount) * Double.parseDouble(value);

                    balance.setText(String.valueOf(cal));
                }


                //  ((TextView) view).setTextColor(Color.WHITE); //Change selected text color

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void initUI() {
        open_eye=findViewById(R.id.openeye);
        close_eye=findViewById(R.id.closeeye);
        balchk=findViewById(R.id.balCurr);
        balance=findViewById(R.id.t_balance);
        tcuurency=findViewById(R.id.t_currency);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    
    private void hideAndShow() {


        close_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pwdpopup();
            }
        });
        open_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eyeopen=false;
                tcuurency.setText("$");

                walletamount=null;
                balance.setText("****");
                open_eye.setVisibility(View.GONE);
                close_eye.setVisibility(View.VISIBLE);

            }
        });
    }

    private void pwdpopup()
    {
        final Dialog dialog = new Dialog(BalanceActivity.this);
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
                p1.requestFocus();
                ///      BaseClass.toast(BalanceActivity.this,UserDashboard.user_password);
                if(grt_pin.equals(UserDashboard.user_password))
                {

                    SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                    String userid=preferences1.getString("user_id", "");

                    if(BaseClass.isNetworkConnected(BalanceActivity.this))
                    {
                        fetch_wallet_balance(userid,dialog);

                    }
                    else
                    {

                        dialog.dismiss();
                        BaseClass.toast(BalanceActivity.this,"Check Your Internet Connection");
                    }


                }
                else
                {
                    Vibrator vibrator = (Vibrator) getSystemService(BalanceActivity.VIBRATOR_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        vibrator.vibrate(500);
                    }
                    YoYo.with(Techniques.Shake)
                            .duration(500).repeat(1)
                            .playOn(dialog.findViewById(R.id.layout));

                    p1.setText("");p2.setText("");p3.setText("");p4.setText("");p5.setText("");p6.setText("");

                    p1.requestFocus();

                    BaseClass.toast(BalanceActivity.this,"Wrong Pin");
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

    public void fetch_wallet_balance(final String userid,final Dialog dialog) {

        BaseClass.progress(BalanceActivity.this);
        BaseClass.progressDialog.show();
        BaseClass.progressDialog.setCancelable(true);

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"fetch_wallet_ballance.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        BaseClass.progressDialog.dismiss();
                        //          Toast.makeText(getApplicationContext(),ServerResponse.trim(),Toast.LENGTH_SHORT).show();


                        if(ServerResponse.trim().equals("false"))
                        {
                            BaseClass.toast(BalanceActivity.this,"Try again");
                        }
                        else
                        {
                            dialog.dismiss();
                            close_eye.setVisibility(View.GONE);
                            open_eye.setVisibility(View.VISIBLE);
                            balance.setText(ServerResponse.trim());
                            eyeopen=true;
                            walletamount=ServerResponse.trim();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        BaseClass.progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Restart Your App..."+volleyError.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("userid",userid);

                return params;

            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);

    }

    private void FetchCurrency() {

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
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(BalanceActivity.this, android.R.layout.simple_spinner_item, countryName);
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