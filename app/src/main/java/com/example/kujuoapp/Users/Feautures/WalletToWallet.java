package com.example.kujuoapp.Users.Feautures;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.kujuoapp.Users.Login;
import com.example.kujuoapp.Users.UserDashboard;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class WalletToWallet extends AppCompatActivity {

    LinearLayout phonelayout,walletlayout,phoneparentlaout;
    TextView phontext;
    ImageView phoneicon;
    HashMap<String,String> HashMap;
    Spinner balchk,convert2;
    ArrayList<String> countryName=new ArrayList<>();
    TextView balance,tcuurency,view_currency;

    ImageView open_eye,close_eye;
    boolean showBalance=false;

    boolean editable=false;

    LinearLayout qr_scan;

    EditText transfer_amount;

    Button transaction;

    EditText walletid,phone_no,message;

    String receiverid=null, transaction_type=null,messages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_to_wallet);
        balchk=findViewById(R.id.balCurr);

        HashMap=new HashMap<String, String>();

        init();
        statusbar();
        FetchCurrency();

        hideAndShow();

        //attach the listener to the spinner
        balchk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String value=HashMap.get(balchk.getSelectedItem().toString());
                //  BaseClass.toast(WalletToWallet.this,value);
                TextView selectedText = (TextView) parent.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(Color.WHITE);
                }
                if(balchk.getSelectedItem()!=null && value != null && balance.getText().toString()!= null && balance.getText().toString()!= "" && showBalance==true )
                {
                    tcuurency.setText(balchk.getSelectedItem().toString());

                    Double cal= Double.parseDouble(UserDashboard.user_wallet) * Double.parseDouble(value);

                    balance.setText(String.valueOf(cal));
                }


                //  ((TextView) view).setTextColor(Color.WHITE); //Change selected text color

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
         convert2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String value=HashMap.get(convert2.getSelectedItem().toString());
                //  BaseClass.toast(WalletToWallet.this,value);
                TextView selectedText = (TextView) parent.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(Color.WHITE);
                }
                if(convert2.getSelectedItem()!=null && value != null
                        && transfer_amount.getText().toString()!= null
                        && transfer_amount.getText().toString()!= ""
                        && transfer_amount.getText().toString().length()>0 )
                {
                    BaseClass.toast(WalletToWallet.this,value);

                    Double cal= Double.parseDouble(transfer_amount.getText().toString().trim()) * Double.parseDouble(value);

                    view_currency.setText(": "+String.valueOf(cal) );
                }


                //  ((TextView) view).setTextColor(Color.WHITE); //Change selected text color

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

         back();

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

    private void init() {


        phonelayout=findViewById(R.id.phonumlayout);
        phontext=findViewById(R.id.phonetext);
        phoneicon=findViewById(R.id.phoneicon);
        phoneparentlaout=findViewById(R.id.phoneparentlayout);
        walletlayout=findViewById(R.id.walletlayout);
        HashMap=new HashMap<String, String>();
        balchk=findViewById(R.id.balCurr);
        balance=findViewById(R.id.t_balance);
        tcuurency=findViewById(R.id.t_currency);
        open_eye=findViewById(R.id.openeye);
        close_eye=findViewById(R.id.closeeye);
        qr_scan=findViewById(R.id.qr_scan);
        convert2=findViewById(R.id.s2);
        transfer_amount=findViewById(R.id.t_amount);
        view_currency=findViewById(R.id.view_currency);
        transaction=findViewById(R.id.trans);
        walletid=findViewById(R.id.wallet_id);
        phone_no=findViewById(R.id.phoneno);
        message=findViewById(R.id.msg);

        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(walletlayout.getVisibility()==View.VISIBLE)
                {
                    if(walletid.length()==10)
                    {
                        receiverid = walletid.getText().toString();
                        transaction_type="wallet-to-wallet";
                        if(message.getText().length()>0)
                        {
                            if(transfer_amount.getText().toString().length()>0)
                            {
                                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                                String userid=preferences1.getString("user_id", "");
                                check_walletid_balance(userid,walletid.getText().toString(),transfer_amount.getText().toString());
                            }
                            else
                            {
                                transfer_amount.setError("Amount Required");
                            }
                        }
                        else
                        {
                            message.setError("Message Required");

                        }
                    }
                    else
                    {
                        walletid.setError("Wallet ID Must Be Ten(10).");
                    }
                }
                else
                {
                    if(phone_no.length()>9) {
                        receiverid = phone_no.getText().toString();
                        transaction_type="wallet-to-contactno";
                    }
                    else
                    {
                        phone_no.setError("Wallet ID Must Be Ten(10).");
                    }
                }

            }
        });

        qr_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanQRMethod();
                finish();
            }
        });

        phone_no.setOnClickListener(new View.OnClickListener() {
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


    private void hideAndShow() {


        close_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseClass.toast(WalletToWallet.this,"dsa");

                pwdpopup();
            }
        });
        open_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseClass.toast(WalletToWallet.this,"dsa");

                balance.setText("****");
                open_eye.setVisibility(View.GONE);
                close_eye.setVisibility(View.VISIBLE);
                showBalance=false;
            }
        });
    }

    private void pwdpopup()
    {
        final Dialog dialog = new Dialog(WalletToWallet.this);
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
                if(grt_pin.equals(UserDashboard.user_password))
                {
                    dialog.dismiss();
                    close_eye.setVisibility(View.GONE);
                    open_eye.setVisibility(View.VISIBLE);
                    balance.setText(UserDashboard.user_wallet);
                    showBalance=true;
                }
                else
                {
                    BaseClass.toast(WalletToWallet.this,"Wrong Pin");
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
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(WalletToWallet.this, android.R.layout.simple_spinner_item, countryName);
                                //set the view for the Drop down list
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //set the ArrayAdapter to the spinner
                                balchk.setAdapter(dataAdapter);

                                balchk.setSelection(dataAdapter.getPosition("USD"));

                                convert2.setAdapter(dataAdapter);

                                convert2.setSelection(dataAdapter.getPosition("USD"));
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

    public void check_walletid_balance(final String senderid, final String wallet_id, final String amount) {

        BaseClass.progress(WalletToWallet.this);
        BaseClass.progressDialog.show();
        BaseClass.progressDialog.setCancelable(true);

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"check-wallet-id-ballance.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        BaseClass.progressDialog.dismiss();
                       Toast.makeText(getApplicationContext(),ServerResponse.trim(),Toast.LENGTH_SHORT).show();

                        if(ServerResponse.trim().equals("0"))
                        {
                            BaseClass.toast(WalletToWallet.this,"Wrong Wallet ID");
                        }
                        else if(ServerResponse.trim().equals("1"))
                        {
                            BaseClass.toast(WalletToWallet.this,"You Have No Ballance");
                        }
                        else if(ServerResponse.trim().equals("ok"))
                        {
                            transactionInfoPopup();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        BaseClass.progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"check your internet connection", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                params.put("sender_id",senderid);
                params.put("wallet_id",wallet_id);
                params.put("amount",amount);

                return params;

            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);

    }

    private void transactionInfoPopup()
    {
        final Dialog dialog=new Dialog(WalletToWallet.this);
        dialog.setContentView(R.layout.transaction_slip_popup);
        dialog.setCancelable(true);
        final TextView recid=dialog.findViewById(R.id.recid);
        final TextView sendamount=dialog.findViewById(R.id.sendamount);
        final TextView msg=dialog.findViewById(R.id.dmsg);
        final TextView adminDetect=dialog.findViewById(R.id.adminfees);
        final TextView totalamount=dialog.findViewById(R.id.totalamount);
        final ImageView x=dialog.findViewById(R.id.cros);
        final Button pay=dialog.findViewById(R.id.payes);


        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        recid.setText(receiverid);
        sendamount.setText(transfer_amount.getText().toString());
        msg.setText(message.getText().toString());

        final float cal=Integer.parseInt(transfer_amount.getText().toString())*Float.parseFloat(adminDetect.getText().toString());
       final float total=Integer.parseInt(transfer_amount.getText().toString())-cal;

        totalamount.setText(total+"");


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                String userid=preferences1.getString("user_id", "");
                perform_transaction(String.valueOf(total),userid);
            }
        });
        dialog.show();

    }
    public void perform_transaction(final String transacted, final String userid) {

        BaseClass.progress(WalletToWallet.this);
        BaseClass.progressDialog.show();
        BaseClass.progressDialog.setCancelable(true);

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"wallet-to-wallet.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        BaseClass.progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),ServerResponse.trim(),Toast.LENGTH_SHORT).show();

                        if(ServerResponse.trim().equals("0"))
                        {
                            BaseClass.toast(WalletToWallet.this,"Wrong Wallet ID");
                        }
                        else if(ServerResponse.trim().equals("1"))
                        {
                            BaseClass.toast(WalletToWallet.this,"You Have No Ballance");
                        }
                        else if(ServerResponse.trim().equals("ok"))
                        {
                            transactionInfoPopup();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        BaseClass.progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"check your internet connection", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
              /*  $transaction_type=$_POST['type'];
                $reciever_id=$_POST['recieverid'];
                $sender_id=$_POST['senderid'];
                $amount=$_POST['amount'];
                $message=$_POST['message'];*/

              //  $transfer_amount=$_POST['transfer_amount'];
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                params.put("type",transaction_type);
                params.put("recieverid",receiverid);
                params.put("senderid",userid);
                params.put("amount",transfer_amount.getText().toString());
                params.put("message",message.getText().toString());
                params.put("transfer_amount",transacted);

                return params;

            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);

    }

    private void scanQRMethod() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);

        IntentIntegrator intentIntegrator = new IntentIntegrator(WalletToWallet.this);
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.setPrompt("");
        intentIntegrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        BaseClass.progress(this);
        BaseClass.progressDialog.show();
        BaseClass.progressDialog.setCancelable(true);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if (intentResult != null){

            if (intentResult.getContents() != null){
                Toasty.success(getApplicationContext(),intentResult.getContents().toString(),Toasty.LENGTH_LONG).show();
                Intent intent=new Intent(WalletToWallet.this, QrSetter.class);
                intent.putExtra("rec_data",intentResult.getContents().toString());
                startActivity(intent);
                BaseClass.progressDialog.dismiss();
            }
            else {
                BaseClass.progressDialog.dismiss();
                // Toasty.error(getApplicationContext(),"Error: Something went wrong!",Toasty.LENGTH_LONG).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}