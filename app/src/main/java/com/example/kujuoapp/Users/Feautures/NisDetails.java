package com.example.kujuoapp.Users.Feautures;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.TextView;

import com.example.kujuoapp.R;

public class NisDetails extends AppCompatActivity {


    TextView rec_id,rec_mobileno,amount,fees,totalamount;
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
        rec_id=findViewById(R.id.ccnic);

    }



}