package com.example.kujuoapp.Users.Feautures;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kujuoapp.R;
import com.example.kujuoapp.Users.BaseClass;
import com.example.kujuoapp.Users.QRCodeGenerator;

import java.util.Date;

public class QrSetter extends AppCompatActivity {

    TextView reciever_name,reciever_contactno,rec_amount,rec_date,rec_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_setter);

        init();
        stausbar();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                BaseClass.toast(this,"SomeThing Wrong");
                finish();
            } else {

                String data= extras.getString("rec_data");

                String[] separatedData = data.split("@-");

                reciever_name.setText(": "+separatedData[3]);
                rec_amount.setText(": "+separatedData[0]);
                reciever_contactno.setText(": "+separatedData[2]);
                rec_msg.setText(": "+separatedData[1]);
                settext();
            }
        }

    }

    private void settext() {
        Date d = new Date();
        CharSequence s = DateFormat.format("yyyy.MM.dd hh:mm", d.getTime());
        rec_date.setText(": "+s);
    }

    private void init() {
        rec_amount=findViewById(R.id.qr_req);
        rec_date=findViewById(R.id.qr_date);
        rec_msg=findViewById(R.id.qr_msg);
        reciever_name=findViewById(R.id.qr_name);
        reciever_contactno=findViewById(R.id.qr_no);
    }

    private void stausbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = QrSetter.this.getWindow();

            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.TRANSPARENT);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

}