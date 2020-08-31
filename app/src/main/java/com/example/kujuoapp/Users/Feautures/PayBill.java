package com.example.kujuoapp.Users.Feautures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.kujuoapp.R;

public class PayBill extends AppCompatActivity {

    RelativeLayout electric,gas,internet,water;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_pay_bill);

        back();

        statusbar();

        init();
    }

    private void init()
    {
       electric=findViewById(R.id.electric);
        gas=findViewById(R.id.gas);
        internet=findViewById(R.id.internet);
        water=findViewById(R.id.waterbill);

        electric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PayBill.this,EletricCity.class));
            }
        });
        gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PayBill.this,GasBill.class));
            }
        });
        internet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PayBill.this,InternetBill.class));
            }
        });
        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PayBill.this,WaterAndSewerage.class));
            }
        });
    }

    private void back()
    {
        ImageView imageView=findViewById(R.id.tback);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void statusbar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = PayBill.this.getWindow();

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
}