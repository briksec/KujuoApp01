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

public class UberClasification extends AppCompatActivity {

    RelativeLayout uberx,ubergo,ubermini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uber_clasification);

        init();

        back();

        statusbar();
    }

    private void init()
    {
        uberx = findViewById(R.id.uberx);
        ubergo = findViewById(R.id.ubergo);
        ubermini = findViewById(R.id.ubermini);


        onclick(uberx,"UberX","50");
        onclick(ubermini,"UberMini","20");
        onclick(ubergo,"UberGo","500");


    }

    private void onclick(RelativeLayout uber, final String tag, final String rate)
    {
        uber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(UberClasification.this,UberVoucher.class);

                i.putExtra("header",tag);
                i.putExtra("rate",rate);

                startActivity(i);
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
            Window window = UberClasification.this.getWindow();

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