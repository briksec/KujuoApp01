package com.example.kujuoapp.Users.Feautures;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.BaseClass;

public class NisDetails extends AppCompatActivity {


    TextView rec_id,rec_mobileno,amount,fees,totalamount;
    Button sent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nis_details);

        init();

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
        float fee=Integer.parseInt(WalletToCnic.amount.getText().toString())*Float.parseFloat(WalletToCnic.percentage);
        fees.setText("$ "+fee+"");
       float tf=Integer.parseInt(WalletToCnic.amount.getText().toString()) + fee ;
       totalamount.setText(tf+"");
    }



}