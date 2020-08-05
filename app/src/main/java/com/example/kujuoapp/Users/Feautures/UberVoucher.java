package com.example.kujuoapp.Users.Feautures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kujuoapp.R;

public class UberVoucher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uber_voucher);

        String data = getIntent().getExtras().getString("header","defaultKey");

        TextView toolbertext=findViewById(R.id.toolbar_text);
        toolbertext.setText(data);

        back();
        statusbar();
        init();
    }

    private void init()
    {
        TextView consumername,consumer_phoneno,voucher_amount,voucher_total;

        consumername=findViewById(R.id.consumer_Name);
        consumer_phoneno=findViewById(R.id.consumer_Number);
        voucher_amount=findViewById(R.id.voucher_amount);
        voucher_total=findViewById(R.id.voucher_tamount);

        String rate = getIntent().getExtras().getString("rate","defaultKey");

        voucher_amount.setText(rate);
        voucher_total.setText(rate);

        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String user_name=preferences1.getString("user_name", "");

       String user_phoneno=preferences1.getString("user_phoneno", "");

       consumername.setText(user_name);
       consumer_phoneno.setText(user_phoneno);

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
            Window window = UberVoucher.this.getWindow();

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