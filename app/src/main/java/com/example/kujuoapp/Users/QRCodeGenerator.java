package com.example.kujuoapp.Users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;

import com.example.kujuoapp.R;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCodeGenerator extends AppCompatActivity {

    EditText message;
    EditText amount;
    Button generate;
    ImageView qrcodeShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_generator);
        message = findViewById(R.id.z_messsage);
        amount = findViewById(R.id.z_amount);
        generate = findViewById(R.id.btn_gen);
        qrcodeShow = findViewById(R.id.qr_show);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);
        initUI();
        stausbar();
    }

    private void stausbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = QRCodeGenerator.this.getWindow();

            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.TRANSPARENT);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void initUI() {




        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (message.getText().toString().isEmpty() && amount.getText().toString().isEmpty()){
                    BaseClass.toast(getApplicationContext(),"Please Fill all the fields");
                }else if (message.getText().toString().isEmpty()){
                    BaseClass.toast(getApplicationContext(),"Please enter message");
                }else if (amount.getText().toString().isEmpty()){
                    BaseClass.toast(getApplicationContext(),"Please enter amount");
                }else {
                    checkQR();
                }

            }
        });

    }

    private void checkQR() {
        MultiFormatWriter mfw = new MultiFormatWriter();
        String mylink = amount.getText().toString()
                +"@-"+message.getText().toString()
                +"@-"+UserDashboard.user_phoneno
                +"@-" +UserDashboard.user_name;
        try{

            BitMatrix bitMatrix = mfw.encode(mylink
                    , BarcodeFormat.QR_CODE,300,300);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

            qrcodeShow.setImageBitmap(bitmap);
            amount.setText("");
            message.setText("");
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
        BaseClass.toast(getApplicationContext(),mylink);
    }
}