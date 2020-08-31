package com.example.kujuoapp.Users.Feautures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class NisDetails extends AppCompatActivity {


    TextView rec_id,rec_mobileno,amount,fees,totalamount;
    Button sent;
    float fee,tf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nis_details);

        init();
        statusbar();
        sentMoney();
    }

    private void sentMoney()
    {
        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(BaseClass.isNetworkConnected(NisDetails.this))
                    sent();
                else
                    BaseClass.toast(getApplicationContext(),"Check your Internet Connection");
            }
        });
    }

    private void statusbar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = NisDetails.this.getWindow();

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

    private void init()
    {
        rec_id=findViewById(R.id.ccnic);
        rec_mobileno=findViewById(R.id.mobileno);
        amount=findViewById(R.id.amount);
        fees=findViewById(R.id.fees);
        sent=findViewById(R.id.sent);
        totalamount=findViewById(R.id.totalamount);

        rec_id.setText(WalletToCnic.nis.getText().toString());
        rec_mobileno.setText(WalletToCnic.contactno);
        amount.setText("$ "+WalletToCnic.amount.getText().toString());
         fee=Integer.parseInt(WalletToCnic.amount.getText().toString())*Float.parseFloat(WalletToCnic.percentage);
        fees.setText("$ "+fee+"");
        tf=Integer.parseInt(WalletToCnic.amount.getText().toString()) + fee ;
       totalamount.setText(tf+"");
    }


    public void sent() {
        BaseClass.progress(NisDetails.this);
        BaseClass.progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseClass.domain+"wallet_to_nis.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        BaseClass.progressDialog.dismiss();
                        //  Toast.makeText(getApplicationContext(),ServerResponse.trim(),Toast.LENGTH_SHORT).show();
                        BaseClass.toast(getApplicationContext(),ServerResponse.trim());

                        if(ServerResponse.trim().equals("false"))
                        {
                        }
                        else
                        {

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

               /* $sender_id = 25; $_POST['sender_id'];
                $send_amount = 2;$_POST['send_amount'];
                $transaction_charges = 2;$_POST['transaction_charges'];
                $transacted_amount = 2;$_POST['transacted_amount'];
                $receiver_cnic =2; $_POST['receiver_cnic'];
                $receiver_phoneno =2; $_POST['receiver_phoneno'];*/
                Map<String, String> params = new HashMap<String, String>();

                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                String userid=preferences1.getString("user_id", "");
                params.put("sender_id",userid);
                params.put("send_amount",WalletToCnic.amount.getText().toString());
                params.put("transaction_charges",WalletToCnic.percentage);
                params.put("receiver_cnic",WalletToCnic.nis.getText().toString());
                params.put("receiver_phoneno",WalletToCnic.contactno);

                return params;

            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Adding the StringRequest object into requestQueue.

        requestQueue.add(stringRequest);

    }



}